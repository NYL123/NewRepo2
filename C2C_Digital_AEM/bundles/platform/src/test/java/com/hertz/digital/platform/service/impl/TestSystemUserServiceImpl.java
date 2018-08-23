package com.hertz.digital.platform.service.impl;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import javax.jcr.LoginException;

import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestSystemUserServiceImpl {
	Logger logger;
	@Mock
	private ResourceResolverFactory resolverFactory;

	@InjectMocks
	private SystemUserServiceImpl systemUserServiceImpl;

	@Before
	public final void setup() throws Exception {

		systemUserServiceImpl = new SystemUserServiceImpl();

		mockStatic(LoggerFactory.class);
		logger = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(logger);
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public final void testgetServiceResourceResolver() throws Exception {

		systemUserServiceImpl.getServiceResourceResolver();
	}

	@Test
	public final void testgetServiceResourceResolverException() throws org.apache.sling.api.resource.LoginException {

		when(resolverFactory.getServiceResourceResolver(Mockito.anyMap()))
				.thenThrow(new org.apache.sling.api.resource.LoginException());
		systemUserServiceImpl.getServiceResourceResolver();
	}
}
