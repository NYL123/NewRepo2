package com.hertz.digital.platform.scheduler;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Set;

import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.osgi.service.component.ComponentContext;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.factory.MicroServicesConfigurationFactory;
import com.hertz.digital.platform.service.api.SystemUserService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoggerFactory.class })
public class HertzTokenImporterSchedulerTest {
	@InjectMocks
	HertzTokenImporterScheduler hertzTokenImporterScheduler = new HertzTokenImporterScheduler();
	@Mock
	private SystemUserService systemService;

	@Mock
	private ResourceResolver mockResResolver;
	@Mock
	Session session;
	@Mock
	SlingSettingsService settings;

	@Mock
	Logger log;
	@Mock
	HertzConfigFactory hConfigFactory;

	@Mock
	MicroServicesConfigurationFactory mcFactory;

	Class<?> servclass;
	Field fields[];
	@Mock
	private static Dictionary<String, Object> properties;
	Method methods[];
	@Mock
	private ComponentContext mockComponentContext;

	@Mock
	private Dictionary<String, Object> mockDictionary;
	Set<String> runModes;

	@Before
	public void setUp() throws Exception {

		// richRelevanceCronJob = Mockito.mock(RichRelevanceCronJob.class);
		MockitoAnnotations.initMocks(this);
		mockStatic(LoggerFactory.class);
		log = mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		PowerMockito.when(systemService.getServiceResourceResolver()).thenReturn(mockResResolver);
		PowerMockito.when(mockResResolver.adaptTo(Session.class)).thenReturn(session);
		servclass = hertzTokenImporterScheduler.getClass();
		fields = servclass.getDeclaredFields();
		runModes = mock(Set.class);
		runModes = PowerMockito.spy(new HashSet<String>());
		runModes.add("author");
		when(settings.getRunModes()).thenReturn(runModes);

	}

	@Test
	public void testActivate() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// ComponentContext context = Mockito.mock(ComponentContext.class);
		Method activate = hertzTokenImporterScheduler.getClass().getDeclaredMethod("activate", ComponentContext.class);
		activate.setAccessible(true);
		activate.invoke(hertzTokenImporterScheduler, mockComponentContext);
	}

	@Test
	public void testRun() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchFieldException {

		Field isMaster = servclass.getDeclaredField("isMaster");
		isMaster.setAccessible(true);
		isMaster.setBoolean(hertzTokenImporterScheduler, false);
		Method run = hertzTokenImporterScheduler.getClass().getDeclaredMethod("run");
		run.setAccessible(true);
		run.invoke(hertzTokenImporterScheduler);

		isMaster.setBoolean(hertzTokenImporterScheduler, true);
		run.invoke(hertzTokenImporterScheduler);

		runModes.remove("author");
		runModes.add("publish");
		run.invoke(hertzTokenImporterScheduler);

	}

	@Test
	public void testHandleTopologyEvent() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		hertzTokenImporterScheduler.handleTopologyEvent(null);
	}

}
