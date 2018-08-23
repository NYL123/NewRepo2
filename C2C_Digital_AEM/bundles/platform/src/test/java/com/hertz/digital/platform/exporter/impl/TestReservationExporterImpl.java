package com.hertz.digital.platform.exporter.impl;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Iterator;

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

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoggerFactory.class })
public class TestReservationExporterImpl {
	@InjectMocks
	ReservationExporterImpl exporterImpl;

	Logger log;

	@Mock
	ResourceResolver resourceResolver;

	@Mock
	Resource component;
	@Mock
	ValueMap properties;
	
	@Mock
	Resource configPageResource;
	
	@Mock
	Iterable<Resource> iterable;
	
	@Mock
	Iterator<Resource> iterator;
	
	@Mock
	Resource childResource;

	@Before
	public final void setup() throws Exception {
		exporterImpl = new ReservationExporterImpl();
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public final void testexportAsJson() throws Exception {
		String str = exporterImpl.exportAsJson(component, resourceResolver);
		Assert.assertNotNull(str);
	}

	@Test
	public final void testExportAsBean() throws Exception {
		Object object = exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNull(object);
	}
	
	@Test
	public final void testExportAsMap() throws Exception{	
		when(configPageResource.getChildren()).thenReturn(iterable);
		when(iterable.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true,false);
		when(iterator.next()).thenReturn(childResource);
		Assert.assertNotNull(exporterImpl.exportAsMap(resourceResolver, configPageResource));
	}
}