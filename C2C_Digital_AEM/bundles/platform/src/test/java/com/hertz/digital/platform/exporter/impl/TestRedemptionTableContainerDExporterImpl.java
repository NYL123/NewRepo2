package com.hertz.digital.platform.exporter.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
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
public class TestRedemptionTableContainerDExporterImpl {

	private String resourceType = "hertz/components/content/tablecontainer_d";
	@InjectMocks
	RedemptionTableContainerDExporterImpl exporterImpl;
	Logger log;

	@Mock
	ResourceResolver resourceResolver;

	@Mock
	Resource component;

	@Mock
	Resource componentParent;

	@Mock
	Resource tablecontainer_d;

	@Mock
	ValueMap properties;

	@Mock
	ValueMap properties_child;

	@Mock
	ValueMap properties2;

	@Mock
	ResourceResolver resolver;

	@Before
	public final void setup() throws Exception {
		exporterImpl = new RedemptionTableContainerDExporterImpl();
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public final void testExportAsBean() throws Exception {

		when(component.getParent()).thenReturn(componentParent);

		when(componentParent.getValueMap()).thenReturn(properties);
		when(properties.get("jcr:title")).thenReturn("Title");

		when(component.getChild("tablecontainer_d")).thenReturn(tablecontainer_d);
		when(tablecontainer_d.getValueMap()).thenReturn(properties_child);

		String[] column = { "a", "b" };
		when(properties_child.get("columnname")).thenReturn(column);

		String[] modelPath = { "a", "b" };
		when(properties_child.get("columnname")).thenReturn(modelPath);

		Iterator iterator = mock(Iterator.class);
		when(component.listChildren()).thenReturn(iterator);

		when(iterator.hasNext()).thenReturn(true, false);

		Resource child = mock(Resource.class);
		when(iterator.next()).thenReturn(child);

		when(child.getResourceType()).thenReturn(resourceType);

		when(child.getValueMap()).thenReturn(properties2);
		when(properties2.get("columnname")).thenReturn(column);

		when(child.hasChildren()).thenReturn(true);

		Resource resourceTablePar = mock(Resource.class);

		when(child.getChild("tablePar")).thenReturn(resourceTablePar);

		Iterator iterator2 = mock(Iterator.class);
		when(resourceTablePar.listChildren()).thenReturn(iterator2);

		when(iterator2.hasNext()).thenReturn(true, false);

		Resource interimResource = mock(Resource.class);
		when(iterator2.next()).thenReturn(interimResource);

		when(interimResource.hasChildren()).thenReturn(true);
		
		Resource tableRowsResource = mock(Resource.class);
				
		when(interimResource.getChild("rows")).thenReturn(tableRowsResource);
		ValueMap rowsValueMap = mock(ValueMap.class);
				
				when(tableRowsResource.getValueMap()).thenReturn(rowsValueMap);
				when(rowsValueMap.get(Mockito.anyString())).thenReturn("Test"); 
		
		/*
		 * Value value1= mock(Value.class); String str= new String("ALTtext") ;
		 * when(value1.toString()).thenReturn("ALTtext");
		 * when(properties.get(HertzConstants.ALT_TEXT)).thenReturn(str);
		 * when(properties.get(HertzConstants.TAGLINE_TEXT)).thenReturn(str);
		 * when(properties.get(HertzConstants.HERO_SUB_TAGLINE_TEXT)).thenReturn
		 * (str);
		 */
		Object object = exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNotNull(object);

	}

	@Test
	public final void testexportAsJson() throws Exception {
		String str = exporterImpl.exportAsJson(component, resourceResolver);
		Assert.assertNull(str);
	}

}
