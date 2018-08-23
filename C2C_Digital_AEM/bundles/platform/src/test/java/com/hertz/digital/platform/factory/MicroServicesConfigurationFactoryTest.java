package com.hertz.digital.platform.factory;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Dictionary;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.osgi.service.component.ComponentContext;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class MicroServicesConfigurationFactoryTest {
	@InjectMocks
	MicroServicesConfigurationFactory configFactory;

	@Mock
	ComponentContext componentContext;

	@Mock
	private static Dictionary<String, String> properties;

	Logger log;

	@Before
	public void setUp() {
		configFactory = new MicroServicesConfigurationFactory();
		mockStatic(LoggerFactory.class);
		log = mock(Logger.class);

		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void testActivate() throws Exception {

		properties.put("1", "Passion");
		when(componentContext.getProperties()).thenReturn(properties);
		configFactory.activate(componentContext);
		assertNotNull(properties);

	}

	@Test
	public void testModified() throws Exception {
		properties.put("1", "Passion");
		when(componentContext.getProperties()).thenReturn(properties);
		configFactory.modified(componentContext);
		assertNotNull(properties);

	}

	@Test
	public void getStringPropertyValueTest() {
		String key = "1";
		String value = "Passion";
		properties.put(key, value);

		when(componentContext.getProperties()).thenReturn(properties);
		configFactory.activate(componentContext);

		when(properties.get(key)).thenReturn(value);
		String strPropertyValue = configFactory.getStringPropertyValue(key);
		assertNotNull(strPropertyValue);
	}

	@Test
	public void getPropertyValueTest() {
		String key = "1";
		String value = "Passion";
		properties.put(key, value);

		when(componentContext.getProperties()).thenReturn(properties);
		configFactory.activate(componentContext);

		when(properties.get(key)).thenReturn(value);
		Object strPropertyValue = configFactory.getPropertyValue(key);
		assertNotNull(strPropertyValue);
	}
}
