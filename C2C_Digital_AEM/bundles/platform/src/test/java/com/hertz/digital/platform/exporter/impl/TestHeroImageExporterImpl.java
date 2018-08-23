package com.hertz.digital.platform.exporter.impl;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import javax.jcr.Value;

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
import static org.mockito.Mockito.mock;

import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.utils.HertzUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({  LoggerFactory.class,HertzUtils.class })
public class TestHeroImageExporterImpl {
	@InjectMocks
	HeroImageExporterImpl exporterImpl;
	
	Logger log;
	
	@Mock
	ResourceResolver resourceResolver;
	
	@Mock
    HertzConfigFactory hFactory;
	
	@Mock
	Resource  component;
	@Mock
	ValueMap properties;
	
	@Mock
	ResourceResolver resolver;
	
	@Before
	public final void setup() throws Exception{
	exporterImpl =	new HeroImageExporterImpl();
	PowerMockito.mockStatic(LoggerFactory.class);
	log = Mockito.mock(Logger.class);
	when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
	MockitoAnnotations.initMocks(this);
	
	}
	@Test
	public final void testexportAsJson() throws Exception{
		String str =exporterImpl.exportAsJson(component, resourceResolver);
		Assert.assertNotNull(str);
	}
	@Test
	public final void testExportAsBean() throws Exception{
		PowerMockito.mockStatic(HertzUtils.class);
    	PowerMockito.when(HertzUtils.getServiceReference(HertzConfigFactory.class)).thenReturn(hFactory);
		when(component.getValueMap()).thenReturn(properties);
		Value value1= mock(Value.class);
		String str= new String("ALTtext") ;
		when(value1.toString()).thenReturn("ALTtext");
		when(properties.containsKey(Mockito.anyString())).thenReturn(true);
		when(properties.get(HertzConstants.ALT_TEXT)).thenReturn(str);
		when(properties.get(HertzConstants.TAGLINE_TEXT)).thenReturn(str);
		when(properties.get(HertzConstants.HERO_SUB_TAGLINE_TEXT)).thenReturn(str);
		Object object =exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNotNull(object);
		Object object1 =exporterImpl.exportAsBean(null, resourceResolver);
		Assert.assertNull(object1);
	}
	
	@Test
	public final void testExportAsBean1() throws Exception{
		PowerMockito.mockStatic(HertzUtils.class);
    	PowerMockito.when(HertzUtils.getServiceReference(HertzConfigFactory.class)).thenReturn(hFactory);
		when(component.getValueMap()).thenReturn(properties);
		Value value1= mock(Value.class);
		String str= new String("ALTtext") ;
		when(value1.toString()).thenReturn("ALTtext");
		when(properties.containsKey(Mockito.anyString())).thenReturn(false);
		Object object =exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNotNull(object);
	}
	
	@Test
	public final void testExportAsMap() throws Exception{
		Assert.assertNull(exporterImpl.exportAsMap(resolver, component));
	}
}
