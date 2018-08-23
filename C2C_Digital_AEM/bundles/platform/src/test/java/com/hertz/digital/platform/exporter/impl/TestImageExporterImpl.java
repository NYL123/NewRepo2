package com.hertz.digital.platform.exporter.impl;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
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

import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.utils.HertzUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoggerFactory.class,HertzUtils.class})
public class TestImageExporterImpl {
	@InjectMocks
	private ImageExporterImpl exporterImpl;
	
	Logger log;
	
	@Mock
	Resource  component;
	
	@Mock
	HertzConfigFactory hFactory;
	
	@Mock
	ResourceResolver resourceResolver;

	@Before
	public final void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		exporterImpl=PowerMockito.mock(ImageExporterImpl.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		Mockito.doCallRealMethod().when(exporterImpl).exportAsBean(component, resourceResolver);
		Mockito.doCallRealMethod().when(exporterImpl).exportAsJson(component, resourceResolver);
		Mockito.doCallRealMethod().when(exporterImpl).exportAsMap(resourceResolver, component);
	}
	
	@Test
	public final void testexportAsJson() throws Exception{
		Assert.assertNull(exporterImpl.exportAsJson(component, resourceResolver));		
		Assert.assertNull(exporterImpl.exportAsMap( resourceResolver, component));
	}
	
	@Test
	public final void testexportAsBean() throws Exception{
		ValueMap properties = Mockito.mock(ValueMap.class);
		when(component.getValueMap()).thenReturn(properties);
		PowerMockito.mockStatic(HertzUtils.class);
    	PowerMockito.when(HertzUtils.getServiceReference(HertzConfigFactory.class)).thenReturn(hFactory);
		when(properties.get(HertzConstants.ALT)).thenReturn("test");
		Assert.assertNotNull(exporterImpl.exportAsBean(component, resourceResolver));	
		

	}

}
