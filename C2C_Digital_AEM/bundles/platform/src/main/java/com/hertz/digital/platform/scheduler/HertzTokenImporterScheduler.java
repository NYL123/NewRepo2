package com.hertz.digital.platform.scheduler;

import java.util.HashSet;
import java.util.Set;

import javax.jcr.Session;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.discovery.TopologyEvent;
import org.apache.sling.discovery.TopologyEventListener;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.factory.MicroServicesConfigurationFactory;
import com.hertz.digital.platform.service.api.SystemUserService;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * Scheduler service to import the password grant token from Micro Services API
 * 
 * <p>
 * Below are the properties defined for the service which will be used to
 * configure the CRON expression,concurrent execution of the service and
 * description of the vendor information.
 * </p>
 * 
 * @author n.kumar.singhal
 *
 */
@Component(label = "Hertz Token Importer", description = "Hertz Token Importer", immediate = true, metatype = true)
@Properties({
		@Property(label = "Cron expression", description = "Cron expression defining when this scheduled service will run", name = "scheduler.expression", value = ""),
		@Property(label = "Allow concurrent executions", description = "Allow concurrent executions of this scheduled service", name = "scheduler.concurrent", boolValue = false),
		@Property(label = "Vendor", name = Constants.SERVICE_VENDOR, value = "Hertz", propertyPrivate = true) })
@Service
/**
 * The runnable is implemented to override the run() method ,which will be
 * executed whenever the scheduler runs as per the information provided inside
 * cron expression.
 *
 */
public class HertzTokenImporterScheduler implements Runnable, TopologyEventListener {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(HertzTokenImporterScheduler.class);

	/** Is the instance master. */
	private boolean isMaster = true;

	/** The resource resolver factory. */
	@Reference
	private transient SystemUserService systemService;

	/**
	 * The sling settings service reference.
	 */
	@Reference
	private SlingSettingsService slingSettingsService;

	/**
	 * The run modes collection. Required to check the value of custom set run
	 * modes.
	 */
	Set<String> runModes = new HashSet<String>();

	@Reference
	HertzConfigFactory hConfigFactory;

	@Reference
	MicroServicesConfigurationFactory mcFactory;

	/**
	 * Default Constructors
	 */
	public HertzTokenImporterScheduler() {
		super();
	}

	/**
	 * This method is used to set the componentContext and the runmodes required
	 * in run() method.
	 * 
	 * @param ctx
	 *            The osgi component context.
	 * 
	 * @throws Exception
	 *             Any exception occurring during start/stop or during
	 *             execution.
	 */
	@Activate
	public void activate(ComponentContext ctx) {
		LOGGER.debug("Start of :- Activate method () taxonomy importer scheduler");
		runModes = slingSettingsService.getRunModes();
		LOGGER.debug("End of :- Activate method () taxonomy importer scheduler");
	}

	/**
	 * This method executes by default when the scheduler thread runs based on
	 * the CRON expression provided inside the #TaxonomyImporterScheduler
	 * configurations.
	 * <p>
	 * The site {@link www.cronmaker.com} can be used to create one such CRON
	 * expression.
	 * </p>
	 *
	 */
	@Override
	public void run() {
		/**
		 * this will check the author instance whether is Master or not ,as the
		 * scheduler will run for the only Master instance
		 *
		 */

		if (!isMaster) {
			LOGGER.debug("Not a master author server. Returning from the Importer.");
			return;
		}

		/*
		 * Configured to run only in author mode and for BWS servers.
		 */
		if (slingSettingsService.getRunModes().contains(HertzConstants.AUTHOR_MODE)) {
			ResourceResolver resolver = systemService.getServiceResourceResolver();
			Session session = resolver.adaptTo(Session.class);
			try{
				LOGGER.debug("Start taxonomy importer scheduler");
				String tokenPath = mcFactory.getStringPropertyValue(HertzConstants.HERTZ_TOKEN_INGESTION_PATH);
				String basePath = mcFactory.getStringPropertyValue(HertzConstants.HERTZ_MC_BASEPATH);
				String passwordGrant = mcFactory.getStringPropertyValue(HertzConstants.HERTZ_MC_PASSGRANT);
				String pwdServiceUrl = basePath + passwordGrant;
				String message = HertzUtils.updateAccessToken(tokenPath, HertzConstants.PWD_GRANT_TOKEN_NODE_NAME,
						pwdServiceUrl, session);
				LOGGER.debug("The status of the update is : - {}", message);
			}finally{
				HertzUtils.closeResolverSession(resolver, session);
			}
			
		}

	}

	@Override
	public void handleTopologyEvent(TopologyEvent topologyEvent) {
		/*
		 * InstanceDescription instances = topologyEvent.getNewView()
		 * .getLocalInstance();
		 */
		LOGGER.debug("Entry :- handleTopologyEvent method.");
		// boolean leader = topologyEvent.
		this.isMaster = true;
		LOGGER.debug("The value of master flag is :- {}", isMaster);
		LOGGER.debug("Exit :- handleTopologyEvent method.");

	}

}
