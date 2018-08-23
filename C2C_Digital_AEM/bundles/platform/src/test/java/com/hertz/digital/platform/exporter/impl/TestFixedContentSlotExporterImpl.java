
package com.hertz.digital.platform.exporter.impl;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.engine.SlingRequestProcessor;
import org.apache.sling.settings.SlingSettingsService;
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

import com.day.cq.contentsync.handler.util.RequestResponseFactory;
import com.hertz.digital.platform.utils.HertzUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoggerFactory.class, HertzUtils.class})
public class TestFixedContentSlotExporterImpl {
	@InjectMocks
	FixedContentSlotExporterImpl exporterImpl;

	Logger log;

	@Mock
	ResourceResolver resourceResolver;

	@Mock
	Resource component;
	@Mock
	ValueMap properties;

	@Mock
	ResourceResolver resolver;

	@Mock
	RequestResponseFactory rrFactory;
	
	@Mock
	SlingRequestProcessor slingProcessor;
	
	@Before
	public final void setup() throws Exception {
		exporterImpl = new FixedContentSlotExporterImpl();
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);
		when(component.getValueMap()).thenReturn(properties);
		
		when(properties.get("patternchoice")).thenReturn("patternAComponent");
		 when(properties.get("exposeashtml")).thenReturn("true");
		PowerMockito.mockStatic(HertzUtils.class);
		
		when(HertzUtils.getServiceReference(RequestResponseFactory.class)).thenReturn(rrFactory);
		when(HertzUtils.getServiceReference(SlingRequestProcessor.class)).thenReturn(slingProcessor);
		
		StringBuilder value = new StringBuilder();
		when(HertzUtils.getHTMLofComponent(Mockito.any(ResourceResolver.class), Mockito.any(RequestResponseFactory.class), Mockito.any(SlingRequestProcessor.class), Mockito.anyString(),Mockito.any(SlingSettingsService.class))).thenReturn(value );
		
		when(component.getPath()).thenReturn("/content/path");
		when(component.hasChildren()).thenReturn(true);
	}

	@Test
	public final void testexportAsJson() throws Exception {
		String str = exporterImpl.exportAsJson(component, resourceResolver);
		Assert.assertNotNull(str);
	}

	@Test
	public final void testExportAsBean() throws Exception {

		
		Object object = exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNotNull(object);

	}

	@Test
	public final void testExportAsMap() throws Exception {
		Assert.assertNull(exporterImpl.exportAsMap(resolver, component));
	}
}
