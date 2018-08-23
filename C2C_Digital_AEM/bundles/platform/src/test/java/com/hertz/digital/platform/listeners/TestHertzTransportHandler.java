
package com.hertz.digital.platform.listeners;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.ByteArrayInputStream;

import javax.jcr.Session;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Assert;
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
import org.osgi.framework.ServiceReference;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.AgentConfig;
import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationLog;
import com.day.cq.replication.ReplicationResult;
import com.day.cq.replication.ReplicationTransaction;
import com.day.cq.replication.TransportContext;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.hertz.digital.platform.bean.CoordinatorConsumable;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.factory.MicroServicesConfigurationFactory;
import com.hertz.digital.platform.service.api.MCDataCoordinatorService;
import com.hertz.digital.platform.service.api.SystemUserService;
import com.hertz.digital.platform.utils.HertzUtils;

import junitx.util.PrivateAccessor;

/**
 * @author deepak.parma
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoggerFactory.class, HertzUtils.class, FrameworkUtil.class, CloseableHttpClient.class,
		HttpClientBuilder.class, CloseableHttpResponse.class, HttpClientBuilder.class })
public class TestHertzTransportHandler {

	@InjectMocks
	private HertzTransportHandler hertTransHandler;

	@Mock
	SystemUserService systemService;

	@Mock
	Page page;
	@Mock
	HttpEntity entity;
	@Mock
	ResourceResolver resolver;

	@Mock
	Bundle bundle;

	@Mock
	CoordinatorConsumable consumable;

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
	MicroServicesConfigurationFactory factory;

	@Mock
	AgentConfig agentConfig;
	@Mock
	TransportContext ctx;

	@Mock
	ReplicationTransaction tx;
	@Mock
	ReplicationAction action;
	@Mock
	ReplicationLog repLog;

	@Mock
	HttpClientBuilder mockClientBuilder;
	@Mock
	CloseableHttpClient mockHttpClient;
	@Mock
	CloseableHttpResponse mockResponse;
	@Mock
	StatusLine statusLine;

	@Before
	public final void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		hertTransHandler = new HertzTransportHandler();

		PrivateAccessor.setField(hertTransHandler, "systemService", systemService);
		PrivateAccessor.setField(hertTransHandler, "hConfigFactory", configFactory);
		PrivateAccessor.setField(hertTransHandler, "mcFactory", factory);
		PowerMockito.mockStatic(FrameworkUtil.class);
		PowerMockito.mockStatic(HertzUtils.class);
		PowerMockito.mock(HttpClientBuilder.class);
		PowerMockito.mock(CloseableHttpClient.class);
		PowerMockito.mock(CloseableHttpResponse.class);
		PowerMockito.mockStatic(HttpClientBuilder.class);
		PowerMockito.when(HttpClientBuilder.class, "create").thenReturn(mockClientBuilder);
		PowerMockito.when(mockClientBuilder.build()).thenReturn(mockHttpClient);
		when(systemService.getServiceResourceResolver()).thenReturn(resolver);
		when(resolver.adaptTo(Session.class)).thenReturn(session);
		when(tx.getAction()).thenReturn(action);
		when(factory.getStringPropertyValue(HertzConstants.HERTZ_TOKEN_INGESTION_PATH)).thenReturn("/var/hertz/token");
		when(factory.getStringPropertyValue(HertzConstants.HERTZ_MC_BASEPATH))
				.thenReturn("http://ec2-54-218-105-35.us-west-2.compute.amazonaws.com:9090");
		when(factory.getStringPropertyValue(HertzConstants.HERTZ_MC_PASSGRANT)).thenReturn("/api/token");
		when(tx.getLog()).thenReturn(repLog);
		when(HertzUtils.getOrCreateAccessToken("/var/hertz/token", HertzConstants.PWD_GRANT_TOKEN_NODE_NAME,
				"http://ec2-54-218-105-35.us-west-2.compute.amazonaws.com:9090/api/token", session))
						.thenReturn("acdDfegerfsfg1234");
		mockResponse.setEntity(entity);
		final ByteArrayInputStream responseStream = new ByteArrayInputStream(new String("{status:'UP'}").getBytes());
		when(entity.getContent()).thenReturn(responseStream);
		when(mockResponse.getStatusLine()).thenReturn(statusLine);

		when(mockResponse.getEntity()).thenReturn(entity);
	}

	@Test
	public final void testCanHandle() {
		when(agentConfig.getTransportURI()).thenReturn("hertz:http://abcd.com");
		Assert.assertTrue(hertTransHandler.canHandle(agentConfig));
	}

	@Test
	public final void testCanHandleNull() {
		when(agentConfig.getTransportURI()).thenReturn(null);
		Assert.assertFalse(hertTransHandler.canHandle(agentConfig));
	}

	@Test
	public final void testDeliverHttpStatus() throws Exception {
		when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
		when(action.getType()).thenReturn(ReplicationActionType.TEST);
		PowerMockito.when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockResponse);
		Assert.assertSame(hertTransHandler.deliver(ctx, tx), ReplicationResult.OK);

	}

	@Test
	public final void testDeliver() throws Exception {
		when(action.getType()).thenReturn(ReplicationActionType.TEST);
		PowerMockito.when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockResponse);
		Assert.assertNotNull(hertTransHandler.deliver(ctx, tx));

	}

	@Test
	public final void testDeliverDeactivate() throws Exception {
		when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_FORBIDDEN);
		when(action.getType()).thenReturn(ReplicationActionType.DEACTIVATE);
		Assert.assertSame(hertTransHandler.deliver(ctx, tx), ReplicationResult.OK);

	}

	@Test
	public final void testDeliverActivateNullEndPoint() throws Exception {
		when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_FORBIDDEN);
		when(action.getType()).thenReturn(ReplicationActionType.ACTIVATE);
		Assert.assertEquals(hertTransHandler.deliver(ctx, tx).isSuccess(), false);

	}

	@Test
	public final void testDeliverActivate() throws Exception {
		when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
		when(FrameworkUtil.getBundle(any(Class.class))).thenReturn(bundle);
		ServiceReference[] references = new ServiceReference[] { reference };
		String array[] = new String[] { "/rates-config/default-rq-codes:rqcodes", "/locations:location" };
		when(bundle.getBundleContext()).thenReturn(bundleContext);
		when(configFactory.getPropertyValue(Mockito.anyString())).thenReturn(array);
		when(bundleContext.getServiceReferences(MCDataCoordinatorService.class.getName(), "(identifier=rqcodes)"))
				.thenReturn(references);
		when(bundleContext.getService(any(ServiceReference.class))).thenReturn(mcDataCoordinatorService);
		when(mcDataCoordinatorService.getCollatedData("/content/TestPath/rates-config/default-rq-codes"))
				.thenReturn(consumable);
		when(consumable.ishealthy()).thenReturn(true);
		when(action.getPath()).thenReturn("/content/TestPath/rates-config/default-rq-codes");
		when(HertzUtils.getIdentifier("/content/TestPath/rates-config/default-rq-codes", array)).thenReturn("rqcodes");

		when(action.getType()).thenReturn(ReplicationActionType.ACTIVATE);
		when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		when(pageManager.getPage(Mockito.anyString())).thenReturn(page);
		when(consumable.getApiUrl()).thenReturn("apiurl");
		when(consumable.getRequestParams()).thenReturn("params");
		when(consumable.getDataPath()).thenReturn("/content/data");
		PowerMockito.when(mockHttpClient.execute(any(HttpPost.class))).thenReturn(mockResponse);

		Assert.assertSame(hertTransHandler.deliver(ctx, tx), ReplicationResult.OK);
	}

	@Test
	public final void testDeliverActivateNullResponse() throws Exception {
		when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
		when(FrameworkUtil.getBundle(any(Class.class))).thenReturn(bundle);
		ServiceReference[] references = new ServiceReference[] { reference };
		String array[] = new String[] { "/rates-config/default-rq-codes:rqcodes", "/locations:location" };
		when(bundle.getBundleContext()).thenReturn(bundleContext);
		when(configFactory.getPropertyValue(Mockito.anyString())).thenReturn(array);
		when(bundleContext.getServiceReferences(MCDataCoordinatorService.class.getName(), "(identifier=rqcodes)"))
				.thenReturn(references);
		when(bundleContext.getService(any(ServiceReference.class))).thenReturn(mcDataCoordinatorService);
		when(mcDataCoordinatorService.getCollatedData("/content/TestPath/rates-config/default-rq-codes"))
				.thenReturn(consumable);
		when(consumable.ishealthy()).thenReturn(true);
		when(action.getPath()).thenReturn("/content/TestPath/rates-config/default-rq-codes");
		when(HertzUtils.getIdentifier("/content/TestPath/rates-config/default-rq-codes", array)).thenReturn("rqcodes");

		when(action.getType()).thenReturn(ReplicationActionType.ACTIVATE);
		when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		when(pageManager.getPage(Mockito.anyString())).thenReturn(page);
		when(consumable.getApiUrl()).thenReturn("apiurl");
		when(consumable.getRequestParams()).thenReturn("params");
		when(consumable.getDataPath()).thenReturn("/content/data");
		PowerMockito.when(mockHttpClient.execute(any(HttpPost.class))).thenReturn(null);

		Assert.assertEquals(hertTransHandler.deliver(ctx, tx).isSuccess(), false);
	}
}