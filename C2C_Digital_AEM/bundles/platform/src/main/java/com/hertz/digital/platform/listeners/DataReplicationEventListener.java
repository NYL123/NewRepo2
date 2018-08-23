package com.hertz.digital.platform.listeners;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.apache.sling.discovery.TopologyEvent;
import org.apache.sling.discovery.TopologyEventListener;
import org.apache.sling.event.jobs.JobManager;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.PageManager;
import com.hertz.digital.platform.bean.CoordinatorConsumable;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.factory.MicroServicesConfigurationFactory;
import com.hertz.digital.platform.service.api.MCDataCoordinatorService;
import com.hertz.digital.platform.service.api.SystemUserService;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * This class is responsible for catching the replication action/event and
 * delegating to the job for sending the notification to the Micro Services.
 * team.
 * 
 * @author n.kumar.singhal
 *
 */
@Component(enabled = true, immediate = true, metatype = true)
@Service(value = EventHandler.class)
@Properties({ @Property(name = EventConstants.EVENT_TOPIC, value = ReplicationAction.EVENT_TOPIC) })
public class DataReplicationEventListener implements EventHandler, TopologyEventListener {

	private static final Logger LOG = LoggerFactory.getLogger(DataReplicationEventListener.class);
	private static final boolean DEFAULT_ENABLED = false;
	private boolean enabled = DEFAULT_ENABLED;
	@Property(label = "Disable Service", description = "Disables/Bypass the service.", boolValue = DEFAULT_ENABLED)
	public static final String PROP_ENABLED = "prop.enabled";

	private boolean isLeader = true;

	@Reference
	private transient SystemUserService systemService;

	@Reference
	SlingSettingsService slingSettings;

	@Reference
	Replicator replicator;

	@Reference
	JobManager jobManager;

	@Reference
	HertzConfigFactory hConfigFactory;

	@Reference
	MicroServicesConfigurationFactory mcFactory;

	/**
	 * Default Constructors
	 */
	public DataReplicationEventListener() {
		super();
	}

	@Activate
	protected void activate(ComponentContext componentContext) throws BundleException {
		final Dictionary<?, ?> properties = componentContext.getProperties();
		this.enabled = PropertiesUtil.toBoolean(properties.get(PROP_ENABLED), DEFAULT_ENABLED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.service.event.EventHandler#handleEvent(org.osgi.service.event.
	 * Event)
	 */
	@Override
	public void handleEvent(final Event event) {
		if (!this.enabled) {
			ReplicationAction action = ReplicationAction.fromEvent(event);
			LOG.debug("Data Replicator handle event called for {} for type {}", action.getPath(),
					action.getType().getName());
			ResourceResolver resolver = systemService.getServiceResourceResolver();
			Session session = resolver.adaptTo(Session.class);
			Bundle bundle = FrameworkUtil.getBundle(MCDataCoordinatorService.class);
			if (bundle != null) {
				try {
					BundleContext bundleContext = bundle.getBundleContext();
					ServiceReference[] services = bundleContext.getServiceReferences(
							MCDataCoordinatorService.class.getName(),
							"(identifier="
									+ HertzUtils.getIdentifier(action.getPath(),
											(String[]) hConfigFactory
													.getPropertyValue(HertzConstants.HERTZ_DATA_COORDINATOR_MAPPING))
									+ ")");

					if (null != services && services.length > 0) {
						LOG.debug("Service reference obtained. {}", services[0]);
						MCDataCoordinatorService coordService = (MCDataCoordinatorService) bundleContext
								.getService(services[0]);
						if (null != coordService) {

							CoordinatorConsumable consumable = coordService.getCollatedData(action.getPath());
							LOG.debug("Building Coordinator consumable object. {}", consumable.ishealthy());
							if (checkPreRequisites(action, resolver, session) && consumable.ishealthy()) {
								Map<String, Object> props = new HashMap<>();
								LOG.debug("Setting paramaters from object for job.");
								props.put(HertzConstants.SERVICE_PATH, consumable.getApiUrl());
								props.put(HertzConstants.JSON_STRING, consumable.getRequestParams());
								props.put(HertzConstants.DATA_PATH, consumable.getDataPath());
								props.put(HertzConstants.TOKEN_PATH,
										mcFactory.getStringPropertyValue(HertzConstants.HERTZ_TOKEN_INGESTION_PATH));
								props.put(HertzConstants.BASE_PATH,
										mcFactory.getStringPropertyValue(HertzConstants.HERTZ_MC_BASEPATH));
								props.put(HertzConstants.PASS_GRANT,
										mcFactory.getStringPropertyValue(HertzConstants.HERTZ_MC_PASSGRANT));
								LOG.debug("Firing job.");
								jobManager.addJob(HertzConstants.MC_UPDATE_JOB_TOPIC, props);
							}
						}
					}
				} catch (InvalidSyntaxException | JSONException | RepositoryException | InterruptedException e) {
					LOG.error("Error occured:- {}{}", e.getMessage(), e);
				} finally {
					HertzUtils.closeResolverSession(resolver, session);
				}
			}
			LOG.debug("Ending handle event execution.");
		}
	}

	/**
	 * It checks and filters the initial request before handing over request to
	 * the coordinator service.
	 * 
	 * @param action
	 *            The action.
	 * @param resolver
	 *            Resolver object
	 * @return The true/dalse flag.
	 * @throws InterruptedException
	 */
	private boolean checkPreRequisites(ReplicationAction action, ResourceResolver resolver, Session session)
			throws InterruptedException {
		PageManager pageManager = resolver.adaptTo(PageManager.class);
		LOG.debug(
				"The pre-requisites in order:- isLeader - {}, IsPage - {}, Type Activate - {}, Run Mode Author - {},Is delivered - {}",
				isLeader, pageManager.getPage(action.getPath()),
				action.getType().equals(ReplicationActionType.ACTIVATE), slingSettings.getRunModes().contains("author"),
				HertzUtils.isDelivered(replicator, session, action.getPath()));
		return isLeader && null != pageManager.getPage(action.getPath())
				&& action.getType().equals(ReplicationActionType.ACTIVATE)
				&& slingSettings.getRunModes().contains("author")
				&& HertzUtils.isDelivered(replicator, session, action.getPath());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.sling.discovery.TopologyEventListener#handleTopologyEvent(org.
	 * apache.sling.discovery.TopologyEvent)
	 */
	@Override
	public void handleTopologyEvent(final TopologyEvent event) {
		/*
		 * if (event.getType() == TopologyEvent.Type.TOPOLOGY_CHANGED ||
		 * event.getType() == TopologyEvent.Type.TOPOLOGY_INIT) { this.isLeader
		 * = event.getNewView().getLocalInstance().isLeader(); }
		 */
		LOG.debug("Entry :- handleTopologyEvent method.");
		this.isLeader = true;
		LOG.debug("The value of Leader flag is :- {}", isLeader);
		LOG.debug("Exit :- handleTopologyEvent method.");
	}
}
