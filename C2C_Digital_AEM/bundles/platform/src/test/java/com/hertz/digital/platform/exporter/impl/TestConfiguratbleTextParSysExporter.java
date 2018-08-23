package com.hertz.digital.platform.exporter.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jcr.Value;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
@PrepareForTest({  LoggerFactory.class, ZonedDateTime.class,HertzUtils.class})
public class TestConfiguratbleTextParSysExporter {
	@InjectMocks
	ConfigurableTextParSysExportor exporterImpl;
	@Mock
	Logger logger;
	
	@Mock
	ResourceResolver resourceResolver;
	
	@Mock
	Resource  component;
	@Mock
	Resource  child;
	
	@Mock
	ValueMap values;
	
	@Mock
	ResourceResolver resolver;
	
	@Mock
    HertzConfigFactory hFactory;
	
	String str ="{\"optionValue\":\"reservationConfirmationArrivalInformationRadioButton1\",\"optionDisplayText\":\"I do not have my arrival information at this time.\"}";
	
	@Before
	public final void setup() throws Exception{
	exporterImpl =	new ConfigurableTextParSysExportor();
	PowerMockito.mockStatic(LoggerFactory.class);
	logger = Mockito.mock(Logger.class);
	when(LoggerFactory.getLogger(any(Class.class))).thenReturn(logger);
	when(logger.isDebugEnabled()).thenReturn(true);
	MockitoAnnotations.initMocks(this);
	
	}
	@Test
	public final void testexportAsJson() throws Exception{
		String str =exporterImpl.exportAsJson(component, resourceResolver);
		when(logger.isDebugEnabled()).thenReturn(true);
		Assert.assertNotNull(str);
	}
	@Test
	public final void testExportAsBean() throws Exception{
		when(logger.isDebugEnabled()).thenReturn(true);
		when(component.hasChildren()).thenReturn(true);
		Iterable childIterable = mock(Iterable.class); 
		when(component.getChildren()).thenReturn(childIterable);
		Iterator childIterator = mock(Iterator.class);
		when(	childIterable.iterator() ).thenReturn(childIterator);
		when(childIterator.hasNext()).thenReturn(true, false);
		Resource child = mock(Resource.class);
		when(childIterator.next()).thenReturn(child);
		when(child.getResourceType()).thenReturn("hertz/components/content/simplepair");
		when(child.getValueMap()).thenReturn(values);
		Value value1= mock(Value.class);
		String key= new String("key") ;
		when(value1.toString()).thenReturn("key");
		when(values.get("key", String.class)).thenReturn("key");
		when(values.get("value", String.class)).thenReturn("value");
		Object object =exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNotNull(object); 
	 
		
	}
	@Test
	public final void testExportAsBean1() throws Exception{
		when(logger.isDebugEnabled()).thenReturn(true);
		when(component.hasChildren()).thenReturn(true);
		Iterable childIterable = mock(Iterable.class); 
		when(component.getChildren()).thenReturn(childIterable);
		Iterator childIterator = mock(Iterator.class);
		when(	childIterable.iterator() ).thenReturn(childIterator);
		when(childIterator.hasNext()).thenReturn(true, false);
		Resource child = mock(Resource.class);
		when(childIterator.next()).thenReturn(child);
		when(child.getResourceType()).thenReturn("hertz/components/content/linkpair");
		when(child.getValueMap()).thenReturn(values);

	        
			when(values.get("key", String.class)).thenReturn("key");
			when(values.get("target",String.class)).thenReturn("target");
			when(values.get("href", String.class)).thenReturn("/content/hertz-rac/rac-web/en-us/erf/erf");
			when(values.get("rel", String.class)).thenReturn("rel");
			when(values.get("content", String.class)).thenReturn("content");
			
		Object object =exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNotNull(object);
	 
		
	} 
	@Test
	public final void testExportAsBean2() throws Exception{
		when(logger.isDebugEnabled()).thenReturn(true);
		when(component.hasChildren()).thenReturn(true);
		Iterable childIterable = mock(Iterable.class); 
		when(component.getChildren()).thenReturn(childIterable);
		Iterator childIterator = mock(Iterator.class);
		when(	childIterable.iterator() ).thenReturn(childIterator);
		when(childIterator.hasNext()).thenReturn(true, false);
		Resource child = mock(Resource.class);
		when(childIterator.next()).thenReturn(child);
		when(child.getResourceType()).thenReturn("hertz/components/configtext/configreference");
		when(component.getPath()).thenReturn("/app/hertz/components/");
		when(child.getValueMap()).thenReturn(values);
		when(values.get("reference", String.class)).thenReturn("reference");
		Resource refereenceResource = mock(Resource.class);
		when(resourceResolver.getResource(Mockito.anyString())).thenReturn(component);
		when(refereenceResource.hasChildren()).thenReturn(true);
		/*Iterable childIterable1 = mock(Iterable.class); 
		when(refereenceResource.getChildren()).thenReturn(childIterable1);
		Iterator childIterator1 = mock(Iterator.class);
		when(	childIterable1.iterator() ).thenReturn(childIterator1);
		when(childIterator1.hasNext()).thenReturn(true, false);
		Resource child1 = mock(Resource.class);
		when(childIterator1.next()).thenReturn(child1);*/
		Object object =exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNotNull(object);
	}
	@Test
	public final void testExportAsBean3() throws Exception{
		when(logger.isDebugEnabled()).thenReturn(true);
		when(component.hasChildren()).thenReturn(true);
		Iterable childIterable = mock(Iterable.class); 
		when(component.getChildren()).thenReturn(childIterable);
		Iterator childIterator = mock(Iterator.class);
		when(	childIterable.iterator() ).thenReturn(childIterator);
		when(childIterator.hasNext()).thenReturn(true, false);
		Resource child = mock(Resource.class);
		when(childIterator.next()).thenReturn(child);
		when(component.getPath()).thenReturn("/apps/hertz/components");
		when(child.getResourceType()).thenReturn("/apps/hertz/components/configtext/genericfieldpair");
		when(child.getValueMap()).thenReturn(values);
		when(values.get("key", String.class)).thenReturn("key");
		when(values.get("label", String.class)).thenReturn("label");
		when(values.get("ariaLabel", String.class)).thenReturn("ariaLabel");
		when(values.get("defaultValue", String.class)).thenReturn("defaultValue");
		when(values.get("error", String.class)).thenReturn("error");

		Object object =exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNotNull(object);
	 
	}
	
	@Test
	public final void testExportAsBean11() throws Exception{
		when(logger.isDebugEnabled()).thenReturn(true);
		when(component.hasChildren()).thenReturn(true);
		Iterable childIterable = mock(Iterable.class); 
		when(component.getChildren()).thenReturn(childIterable);
		Iterator childIterator = mock(Iterator.class);
		when(	childIterable.iterator() ).thenReturn(childIterator);
		when(childIterator.hasNext()).thenReturn(true, false);
		Resource child = mock(Resource.class);
		when(childIterator.next()).thenReturn(child);
		when(component.getPath()).thenReturn("/apps/hertz/components");
		when(child.getResourceType()).thenReturn("hertz/components/configtext/genericfieldpair");
		when(child.getValueMap()).thenReturn(values);
		when(values.get("key", String.class)).thenReturn("key");
		when(values.get("label", String.class)).thenReturn("label");
		when(values.get("ariaLabel", String.class)).thenReturn("ariaLabel");
		when(values.get("defaultValue", String.class)).thenReturn("defaultValue");
		when(values.get("error", String.class)).thenReturn("error");
		Object object =exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNotNull(object);
	 
	}
	
	@Test
	public final void testExportAsBean12() throws Exception{
		when(logger.isDebugEnabled()).thenReturn(true);
		when(component.hasChildren()).thenReturn(true);
		Iterable childIterable = mock(Iterable.class); 
		when(component.getChildren()).thenReturn(childIterable);
		Iterator childIterator = mock(Iterator.class);
		when(	childIterable.iterator() ).thenReturn(childIterator);
		when(childIterator.hasNext()).thenReturn(true, false);
		Resource child = mock(Resource.class);
		when(childIterator.next()).thenReturn(child);
		when(component.getPath()).thenReturn("/apps/hertz/components");
		when(child.getResourceType()).thenReturn("hertz/components/configtext/genericfieldpair");
		when(child.getValueMap()).thenReturn(null);
		exporterImpl.exportAsBean(component, resourceResolver);
	 
	}
	
	@Test
	public final void testExportAsBean4() throws Exception{
		when(logger.isDebugEnabled()).thenReturn(true);
		when(component.hasChildren()).thenReturn(true);
		Iterable childIterable = mock(Iterable.class); 
		when(component.getChildren()).thenReturn(childIterable);
		Iterator childIterator = mock(Iterator.class);
		when(	childIterable.iterator() ).thenReturn(childIterator);
		when(childIterator.hasNext()).thenReturn(true, false);
		Resource child = mock(Resource.class);
		when(childIterator.next()).thenReturn(child);
		when(component.getPath()).thenReturn("hertz/components/content/checkboxpair");
		when(child.getResourceType()).thenReturn("hertz/components/content/checkboxpair");
		when(child.getValueMap()).thenReturn(null);
		exporterImpl.exportAsBean(component, resourceResolver);
		when(child.getValueMap()).thenReturn(values);
		when(values.get("key", String.class)).thenReturn("key");
		when(values.get("label", String.class)).thenReturn("label");
		when(values.get("ariaLabel", String.class)).thenReturn("ariaLabel");
		when(values.get("checkedByDefault", String.class)).thenReturn("checkedByDefault");
		when(values.get("error", String.class)).thenReturn("error");

		Object object =exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNotNull(object);
	 
	}
	
	@Test
	public final void testExportAsBean10() throws Exception{
		when(logger.isDebugEnabled()).thenReturn(true);
		when(component.hasChildren()).thenReturn(true);
		Iterable childIterable = mock(Iterable.class); 
		when(component.getChildren()).thenReturn(childIterable);
		Iterator childIterator = mock(Iterator.class);
		when(	childIterable.iterator() ).thenReturn(childIterator);
		when(childIterator.hasNext()).thenReturn(true, false);
		Resource child = mock(Resource.class);
		when(childIterator.next()).thenReturn(child);
		when(component.getPath()).thenReturn("hertz/components/content/checkboxpair");
		when(child.getResourceType()).thenReturn("hertz/components/content/checkboxpair");
		when(child.getValueMap()).thenReturn(values);
		when(values.get("key", String.class)).thenReturn("key");
		when(values.get("label", String.class)).thenReturn("label");
		when(values.get("ariaLabel", String.class)).thenReturn("ariaLabel");
		when(values.get("checkedByDefault", String.class)).thenReturn("checkedByDefault");
		when(values.get("error", String.class)).thenReturn("error");
		Object object =exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNotNull(object);
	 
	}
	
	@Test
	public final void testExportAsBean5() throws Exception{
		when(logger.isDebugEnabled()).thenReturn(true);
		when(component.hasChildren()).thenReturn(true);
		Iterable childIterable = mock(Iterable.class); 
		when(component.getChildren()).thenReturn(childIterable);
		Iterator childIterator = mock(Iterator.class);
		when(	childIterable.iterator() ).thenReturn(childIterator);
		when(childIterator.hasNext()).thenReturn(true, false);
		Resource child = mock(Resource.class);
		when(childIterator.next()).thenReturn(child);
		when(component.getPath()).thenReturn("hertz/components/configtext/selectradiofieldpair");
		when(child.getResourceType()).thenReturn("hertz/components/configtext/selectradiofieldpair");
		when(child.getValueMap()).thenReturn(values);
		when(values.get("key", String.class)).thenReturn("key");
		when(values.get("label", String.class)).thenReturn("label");
		when(values.get("ariaLabel", String.class)).thenReturn("ariaLabel");
		when(values.get("defaultValue", String.class)).thenReturn("defaultValue");
		when(values.get("error", String.class)).thenReturn("error");  
		when(values.get("optionsList", String.class)).thenReturn(str);  
		when(values.get("optionsList")).thenReturn(str); 
		Object object =exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNotNull(object);
	 
	}
	@Test
	public final void testExportAsBean6() throws Exception{
		PowerMockito.mockStatic(HertzUtils.class);
    	PowerMockito.when(HertzUtils.getServiceReference(HertzConfigFactory.class)).thenReturn(hFactory);
		when(logger.isDebugEnabled()).thenReturn(true);
		when(component.hasChildren()).thenReturn(true);
		Iterable childIterable = mock(Iterable.class); 
		when(component.getChildren()).thenReturn(childIterable);
		Iterator childIterator = mock(Iterator.class);
		when(	childIterable.iterator() ).thenReturn(childIterator);
		when(childIterator.hasNext()).thenReturn(true, false);
		Resource child = mock(Resource.class);
		when(childIterator.next()).thenReturn(child);
		when(component.getPath()).thenReturn("hertz/components/content/imagepair");
		when(child.getResourceType()).thenReturn("hertz/components/content/imagepair");
		when(child.getValueMap()).thenReturn(values);
		when(values.get("key", String.class)).thenReturn("key");
		when(values.get("alt", String.class)).thenReturn("alt");
		when(values.get("fileReference", String.class)).thenReturn("fileReference");

		Object object =exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNotNull(object);
	 
	} 
	
	@Test
	public final void testExportAsBean7() throws Exception{
		when(logger.isDebugEnabled()).thenReturn(true);
		when(component.hasChildren()).thenReturn(true);
		Iterable childIterable = mock(Iterable.class); 
		when(component.getChildren()).thenReturn(childIterable);
		Iterator childIterator = mock(Iterator.class);
		when(	childIterable.iterator() ).thenReturn(childIterator);
		when(childIterator.hasNext()).thenReturn(true, false);
		Resource child = mock(Resource.class);
		when(childIterator.next()).thenReturn(child);
		when(component.getPath()).thenReturn("hertz/components/configtext/datepair");
		when(child.getResourceType()).thenReturn("hertz/components/configtext/datepair");
		when(child.getValueMap()).thenReturn(values);
		when(values.get("key", String.class)).thenReturn("key");
		when(values.get("label", String.class)).thenReturn("label");
		when(values.get("ariaLabel", String.class)).thenReturn("ariaLabel");
		String datepair ="2017-11-02T00:00:00.000+05:30";
		when(values.get("datepair", String.class)).thenReturn(datepair);
		PowerMockito.mockStatic(ZonedDateTime.class);
		//when(ZonedDateTime.parse(datepair)).thenReturn(datepair);

		Object object =exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNotNull(object);
	 
	}
	
	@Test
	public final void testExportAsBean8() throws Exception{
		String str1 = "{\"dropdownValue\":\"United States\",\"order\":\"1\",\"pageUrl\":\"/content/hertz-rac/rac-web/en-us/gold-plus-rewards/redemption/americas\"}";
		String str2 = "{\"dropdownValue\":\"Canada\",\"order\":\"2\",\"pageUrl\":\"/content/hertz-rac/rac-web/en-us/gold-plus-rewards/redemption/canada\"}";
		String dropdownLabel = "Country";
		String [] dropDownValueList = {str1,str2};
		when(logger.isDebugEnabled()).thenReturn(true);
		when(component.hasChildren()).thenReturn(true);
		Iterable childIterable = mock(Iterable.class); 
		when(component.getChildren()).thenReturn(childIterable);
		Iterator childIterator = mock(Iterator.class);
		when(	childIterable.iterator() ).thenReturn(childIterator);
		when(childIterator.hasNext()).thenReturn(true, false);
		Resource child = mock(Resource.class);
		when(childIterator.next()).thenReturn(child);
		when(component.getPath()).thenReturn("hertz/components/content/linkdropdown");
		when(child.getResourceType()).thenReturn("hertz/components/content/linkdropdown");
		when(child.getValueMap()).thenReturn(values);
		when(values.get(HertzConstants.DROPDOWN_LABEL)).thenReturn(dropdownLabel);
		when(values.get(HertzConstants.DROPDOWN_VALUE_LIST)).thenReturn(dropDownValueList);

		Object object =exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNotNull(object);
	 
	}
	
	@Test
	public final void testExportAsBean9() throws Exception{
		String[] array=new String[]{"{'label':'label','linkType':'linkType','sequenceId':'sequenceId','targetType':'targetType','cdpCode':'cdpCode','rqCode':'rqCode','pcCode':'pcCode'}"};
		when(logger.isDebugEnabled()).thenReturn(true);
		when(component.hasChildren()).thenReturn(true);
		Iterable<Resource> childIterable = mock(Iterable.class); 
		when(component.getChildren()).thenReturn(childIterable);
		Iterator<Resource> childIterator = mock(Iterator.class);
		when(childIterable.iterator() ).thenReturn(childIterator);
		when(childIterator.hasNext()).thenReturn(true, false);
		Resource child = mock(Resource.class);
		when(childIterator.next()).thenReturn(child);
		when(component.getPath()).thenReturn("hertz/components/content/hertzlinkdropdown");
		when(child.getResourceType()).thenReturn("hertz/components/content/hertzlinkdropdown");
		when(child.getValueMap()).thenReturn(values);
		when(values.containsKey(HertzConstants.LINK)).thenReturn(true);
		when(values.get(HertzConstants.LINK,String[].class)).thenReturn(array);
		Object object =exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNotNull(object);
	 
	}
	
	@Test
	public final void testExportAsMap() throws Exception{
		when(logger.isDebugEnabled()).thenReturn(true);
		Assert.assertNull(exporterImpl.exportAsMap(resolver, component));
	}
	
	@Test
	public final void testaddConfigTextRefereneceToMap() throws Exception{
		when(logger.isDebugEnabled()).thenReturn(true);
		
		Map<String, Object> configTextMap= new HashMap<String, Object>();
		configTextMap.put("Test", new String("Test"));
		exporterImpl.addConfigTextRefereneceToMap(null,  null,
				 null, null);
	}
	
	@Test
	public final void testaddSelectListRadioButtonPairToMap() throws Exception{

		exporterImpl.addSelectListRadioButtonPairToMap(null,  null);
	}

}
