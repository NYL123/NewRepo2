package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;

import junit.framework.Assert;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestVehicleResultsUse {
	
	@InjectMocks
	private VehicleResultsUse vehicleResultsUse ; 
	Logger log;
	
	@Mock
	Resource pageResource;
	
	@Mock
	ValueMap componentProperties;

	
	@Before
	public final void setup() throws Exception {
		vehicleResultsUse =PowerMockito.mock(VehicleResultsUse.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);
		Mockito.doCallRealMethod().when(vehicleResultsUse).activate();
		Mockito.doCallRealMethod().when(vehicleResultsUse).setEmpty(true);
		Mockito.doCallRealMethod().when(vehicleResultsUse).isEmpty();
		

	}
	
	@Test
	public final void testActivate() throws Exception {
		when(vehicleResultsUse.getResource()).thenReturn(pageResource);
		when(pageResource.getValueMap()).thenReturn(componentProperties);
		Set<Entry<String, Object>> properties = new HashSet<Entry<String, Object>>();
		Map.Entry<String, Object> entry_ignore = new AbstractMap.SimpleEntry<String, Object>("jcr:lastModified", "jcr:lastModified");
		Map.Entry<String, Object> entry_accept = new AbstractMap.SimpleEntry<String, Object>("testvalue", "testvalue");
		properties.add(entry_ignore);
		properties.add(entry_accept);
		when(componentProperties.entrySet()).thenReturn(properties);
		vehicleResultsUse.activate();
		Set<Entry<String, Object>> properties1 = new HashSet<Entry<String, Object>>();
		when(componentProperties.entrySet()).thenReturn(properties1);
		vehicleResultsUse.setEmpty(true);
		Assert.assertTrue(vehicleResultsUse.isEmpty());
		vehicleResultsUse.activate();
		
		
		
	}
}
