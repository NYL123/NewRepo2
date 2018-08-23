package com.hertz.digital.platform.bean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestCoordinatorConsumable {

	@InjectMocks
	private CoordinatorConsumable coordinatorConsumable;
	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		coordinatorConsumable=Mockito.mock(CoordinatorConsumable.class);
		Mockito.doCallRealMethod().when(coordinatorConsumable).setIshealthy(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(coordinatorConsumable).setApiUrl(Mockito.anyString());
		Mockito.doCallRealMethod().when(coordinatorConsumable).setDataPath(Mockito.anyString());
		Mockito.doCallRealMethod().when(coordinatorConsumable).setRequestParams(Mockito.anyString());
		Mockito.doCallRealMethod().when(coordinatorConsumable).setContentType(Mockito.anyString());
		Mockito.doCallRealMethod().when(coordinatorConsumable).setGrantType(Mockito.anyString());
		Mockito.doCallRealMethod().when(coordinatorConsumable).setDataTemplate(Mockito.anyString());
		Mockito.doCallRealMethod().when(coordinatorConsumable).setRequestType(Mockito.anyString());
		Mockito.doCallRealMethod().when(coordinatorConsumable).setTokenPath(Mockito.anyString());
		Mockito.doCallRealMethod().when(coordinatorConsumable).setBasePath(Mockito.anyString());
		Mockito.doCallRealMethod().when(coordinatorConsumable).setPasswordGrant(Mockito.anyString());
		Mockito.doCallRealMethod().when(coordinatorConsumable).ishealthy();
		Mockito.doCallRealMethod().when(coordinatorConsumable).getApiUrl();
		Mockito.doCallRealMethod().when(coordinatorConsumable).getDataPath();
		Mockito.doCallRealMethod().when(coordinatorConsumable).getRequestParams();
		Mockito.doCallRealMethod().when(coordinatorConsumable).getContentType();
		Mockito.doCallRealMethod().when(coordinatorConsumable).getGrantType();
		Mockito.doCallRealMethod().when(coordinatorConsumable).getDataTemplate();
		Mockito.doCallRealMethod().when(coordinatorConsumable).getRequestType();
		Mockito.doCallRealMethod().when(coordinatorConsumable).getTokenPath();
		Mockito.doCallRealMethod().when(coordinatorConsumable).getBasePath();
		Mockito.doCallRealMethod().when(coordinatorConsumable).getPasswordGrant();
		coordinatorConsumable.setIshealthy(true);
		coordinatorConsumable.setApiUrl("Api url");
		coordinatorConsumable.setDataPath("datapath");
		coordinatorConsumable.setRequestParams("requestparams");
		coordinatorConsumable.setContentType("contenttype");
		coordinatorConsumable.setGrantType("granttype");
		coordinatorConsumable.setDataTemplate("data");
		coordinatorConsumable.setRequestType("requesttype");
		coordinatorConsumable.setTokenPath("tokenpath");
		coordinatorConsumable.setBasePath("basepath");
		coordinatorConsumable.setPasswordGrant("passwordgrant");
	}
	
	@Test
	public final void test(){
		Assert.assertNotNull(coordinatorConsumable.ishealthy());
		Assert.assertNotNull(coordinatorConsumable.getApiUrl());
		Assert.assertNotNull(coordinatorConsumable.getDataPath());
		Assert.assertNotNull(coordinatorConsumable.getRequestParams());
		Assert.assertNotNull(coordinatorConsumable.getContentType());
		Assert.assertNotNull(coordinatorConsumable.getGrantType());
		Assert.assertNotNull(coordinatorConsumable.getDataTemplate());
		Assert.assertNotNull(coordinatorConsumable.getRequestType());
		Assert.assertNotNull(coordinatorConsumable.getTokenPath());
		Assert.assertNotNull(coordinatorConsumable.getBasePath());
		Assert.assertNotNull(coordinatorConsumable.getPasswordGrant());
	}
}
