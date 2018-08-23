package com.hertz.digital.platform.listeners;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.discovery.TopologyEvent;
import org.apache.sling.event.jobs.JobManager;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationStatus;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.hertz.digital.platform.bean.CoordinatorConsumable;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.factory.MicroServicesConfigurationFactory;
import com.hertz.digital.platform.service.api.MCDataCoordinatorService;
import com.hertz.digital.platform.service.api.SystemUserService;

import junitx.util.PrivateAccessor;

/**
 * @author a.dhingra
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({LoggerFactory.class, FrameworkUtil.class})
public class TestDataReplicationEventListener {

	@InjectMocks
	private DataReplicationEventListener mockListener;
	
	@Mock
	SystemUserService systemService;
	
	
	@Mock
	ResourceResolver resolver;
	
	@Mock
	Bundle bundle;
	
	@Mock
	CoordinatorConsumable consumable;
	
	@Mock
	JobManager jobManager;
	
	@Mock
	BundleContext bundleContext;

	@Mock
	HertzConfigFactory configFactory;
	
	@Mock
	ServiceReference reference;
	
	@Mock
	MCDataCoordinatorService mcDataCoordinatorService;
	
	@Mock
	Session session;

	@Mock
	PageManager pageManager;
	
	@Mock
	Event event;
	
	@Mock
	Page page;
	
	@Mock
	TopologyEvent topologyEvent;
	
	@Mock
	Replicator replicator;
	
	@Mock
	ReplicationStatus status;
	
	@Mock
	SlingSettingsService slingSettings;
	
	@Mock
	MicroServicesConfigurationFactory factory;
	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		mockListener=new DataReplicationEventListener();
		PrivateAccessor.setField(mockListener, "systemService", systemService);
		PrivateAccessor.setField(mockListener, "hConfigFactory",configFactory);
		PrivateAccessor.setField(mockListener, "slingSettings", slingSettings);
		PrivateAccessor.setField(mockListener, "replicator", replicator);
		PrivateAccessor.setField(mockListener, "mcFactory", factory);
		PrivateAccessor.setField(mockListener, "jobManager", jobManager);
		PowerMockito.mockStatic(FrameworkUtil.class);
		when(FrameworkUtil.getBundle(any(Class.class))).thenReturn(bundle);
	}
	
	@Test
	public final void testHandleEvnt() throws LoginException, InvalidSyntaxException, JSONException, RepositoryException{
		ServiceReference[] references=new ServiceReference[]{reference};
		Set<String> value=new HashSet<>();
		value.add("author");
		String array[]=new String[]{"/rates-config/default-rq-codes:rqcodes","/locations:location"};
		when(systemService.getServiceResourceResolver()).thenReturn(resolver);
		Map<String, Object> properties=new HashMap<>();
		properties.put("modificationDate", new Date());
		properties.put("type", ReplicationActionType.ACTIVATE.toString());
		properties.put("paths", new String[]{"/content/TestPath/rates-config/default-rq-codes"});
		properties.put("userId", "admin");
		properties.put("revision", "1.0");
		Event event=new Event("com/day/cq/replication", properties);
		when(bundle.getBundleContext()).thenReturn(bundleContext);
		when(configFactory.getPropertyValue(Mockito.anyString())).thenReturn(array);
		when(bundleContext.getServiceReferences(MCDataCoordinatorService.class.getName(),
						"(identifier=rqcodes)")).thenReturn(references);
		 when(bundleContext.getService(any(ServiceReference.class))).thenReturn(mcDataCoordinatorService);
		 when(mcDataCoordinatorService.getCollatedData("/content/TestPath/rates-config/default-rq-codes")).thenReturn(consumable);
		 when(consumable.ishealthy()).thenReturn(true);
		 when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		 when(resolver.adaptTo(Session.class)).thenReturn(session);
		 when(pageManager.getPage("/content/TestPath/rates-config/default-rq-codes")).thenReturn(page);
		 when(slingSettings.getRunModes()).thenReturn(value);
		 when(replicator.getReplicationStatus(session, "/content/TestPath/rates-config/default-rq-codes")).thenReturn(status);
		 when(status.isDelivered()).thenReturn(true);
		 when(consumable.getApiUrl()).thenReturn("apiurl");
		 when(consumable.getRequestParams()).thenReturn("params");
		 when(consumable.getDataPath()).thenReturn("/content/data");
		 when(factory.getStringPropertyValue(HertzConstants.HERTZ_TOKEN_INGESTION_PATH)).thenReturn("/var/hertz/token");
		 when(factory.getStringPropertyValue(HertzConstants.HERTZ_MC_BASEPATH)).thenReturn("http://ec2-54-218-105-35.us-west-2.compute.amazonaws.com:9090");
		 when(factory.getStringPropertyValue(HertzConstants.HERTZ_MC_PASSGRANT)).thenReturn("/api/token");
		mockListener.handleEvent(event);
	}
	
	@Test
	public final void testhandleTopologyEvent(){
		mockListener.handleTopologyEvent(topologyEvent);
	}
	
}