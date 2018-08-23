package com.hertz.digital.platform.bean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class CoordinatorConsumableTest {
	@InjectMocks
	private CoordinatorConsumable coordinatorConsumable;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(LoggerFactory.class);

		coordinatorConsumable.setApiUrl("apiUrl");
		coordinatorConsumable.setBasePath("basepath");
		coordinatorConsumable.setContentType("application/json");
		coordinatorConsumable.setDataPath("datapath");
		coordinatorConsumable.setDataTemplate("template");
		coordinatorConsumable.setGrantType("grant type");
		coordinatorConsumable.setIshealthy(true);
		coordinatorConsumable.setPasswordGrant("passgrant");
		coordinatorConsumable.setRequestParams("req params");
		coordinatorConsumable.setRequestType("POST");
		coordinatorConsumable.setTokenPath("/var/hertz/token");

	}

	@Test
	public void test() {
		Assert.assertNotNull(coordinatorConsumable.getApiUrl());
		Assert.assertNotNull(coordinatorConsumable.getBasePath());
		Assert.assertNotNull(coordinatorConsumable.getContentType());
		Assert.assertNotNull(coordinatorConsumable.getDataPath());
		Assert.assertNotNull(coordinatorConsumable.getDataTemplate());
		Assert.assertNotNull(coordinatorConsumable.getGrantType());
		Assert.assertNotNull(coordinatorConsumable.getPasswordGrant());
		Assert.assertNotNull(coordinatorConsumable.getRequestParams());
		Assert.assertNotNull(coordinatorConsumable.getRequestType());
		Assert.assertNotNull(coordinatorConsumable.getTokenPath());
		Assert.assertNotNull(coordinatorConsumable.ishealthy());

	}

}
