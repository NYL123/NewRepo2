package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
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
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.constants.HertzConstants;

import junit.framework.Assert;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestAncillaryCategoriesUse {
	
	@InjectMocks
	private AncillaryCategoriesUse ancillaryCategoriesUse ; 
	Logger log;
	
	@Mock
	Resource pageResource;
	
	@Mock
	Resource childResource;
	
	@Mock
	Resource categoryComponentResource;
	
	@Mock
	Resource preselectedComponentResource;
	
	@Mock
	Resource childJCRResource;
	
	@Mock
	Resource childJCRResource1;
	
	@Mock
	Resource childJCRResource2;
	
	@Mock
	Resource childJCRResource3;
	
	@Mock
	Resource categoryResource;
	
	@Mock
	Resource countryLevelResource;
	
	@Mock
	Resource categoryPageResource;
	
	@Mock
	Resource categoryOptionsResource;
	
	@Mock
	Resource destinationCountryResource;
	
	@Mock
	Resource parentResource;
	
	@Mock
	Resource parResource1;
	
	@Mock
	Resource parResource2;
	
	@Mock
	ValueMap componentProperties;
	
	@Mock
	Iterable<Resource> mockIterable;
	
	@Mock
	Iterator<Resource> mockIterator;
	
	@Mock
	Iterable<Resource> mockIterable1;
	
	@Mock
	Iterator<Resource> mockIterator1;
	
	@Mock
	Iterable<Resource> mockIterable2;
	
	@Mock
	Iterator<Resource> mockIterator2;
	
	@Mock
	Iterable<Resource> mockIterable3;
	
	@Mock
	Iterator<Resource> mockIterator3;
	
	@Mock
	Iterator<Resource> mockIterator4;
	
	@Mock
	Iterable<Resource> mockIterable4;
	
	@Mock
	Iterator<Resource> mockIterator5;
	
	@Mock
	Iterable<Resource> mockIterable5;
	
	@Mock
	Map<String, Object> configuredPropertiesMap;
	
	@Mock
	Page page;
	
	@Mock
	ValueMap valueMap;
	
	@Mock
	ValueMap valueMap1;
	
	@Mock
	ValueMap valueMap2;
	
	@Mock
	ValueMap valueMap3;
	
	@Mock
	Node node;
	
	@Mock
	Node parentNode;
	
	@Mock
	Node sourceCountryNode;
	
	@Mock
	Property property;

	
	@SuppressWarnings("unchecked")
	@Before
	public final void setup() throws Exception {
		ancillaryCategoriesUse =PowerMockito.mock(AncillaryCategoriesUse.class);
		configuredPropertiesMap =PowerMockito.mock(Map.class);
		valueMap1 = PowerMockito.mock(ValueMap.class);
		valueMap2 = PowerMockito.mock(ValueMap.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);
		Mockito.doCallRealMethod().when(ancillaryCategoriesUse).activate();
	}
	
	@Test
	public final void testActivate() throws Exception {
		when(ancillaryCategoriesUse.getResource()).thenReturn(pageResource);
		when(pageResource.getParent()).thenReturn(parentResource);
		when(parentResource.getChildren()).thenReturn(mockIterable);
		when(mockIterable.iterator()).thenReturn(mockIterator);
		when(mockIterator.hasNext()).thenReturn(true,true,true,true,false);
		when(mockIterator.next()).thenReturn(countryLevelResource);
		
		ancillaryCategoriesUse.activate();
				
	}
	
	@Test
	public final void testGetCategoriesPages() throws JSONException, RepositoryException{
		JSONArray jsonObject = new JSONArray();
		when(countryLevelResource.getChildren()).thenReturn(mockIterable);
		when(countryLevelResource.getName()).thenReturn("us");
		when(mockIterable.iterator()).thenReturn(mockIterator);
		when(mockIterator.hasNext()).thenReturn(true,true,true,false);
		when(mockIterator.next()).thenReturn(categoryResource);
		when(categoryResource.getName()).thenReturn("categories");
		when(categoryResource.getChildren()).thenReturn(mockIterable1);
		when(mockIterable1.iterator()).thenReturn(mockIterator1);
		when(mockIterator1.hasNext()).thenReturn(true,true,true,false);
		when(mockIterator1.next()).thenReturn(categoryPageResource);
		when(categoryPageResource.getName()).thenReturn("safety-options");
		when(categoryPageResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(childJCRResource1);
		when(childJCRResource1.getChild(HertzConstants.PAR)).thenReturn(parResource1);
		when(parResource1.getChildren()).thenReturn(mockIterable3);
		when(mockIterable3.iterator()).thenReturn(mockIterator3);
		when(mockIterator3.hasNext()).thenReturn(true,false);
		when(mockIterator3.next()).thenReturn(categoryComponentResource);
		when(categoryComponentResource.getValueMap()).thenReturn(valueMap1);
		when(valueMap1.containsKey(Mockito.anyString())).thenReturn(true);
		when(valueMap1.get("categoryId", String.class)).thenReturn("Category1");
		when(categoryPageResource.getChildren()).thenReturn(mockIterable2);
		when(mockIterable2.iterator()).thenReturn(mockIterator2);
		when(mockIterator2.hasNext()).thenReturn(true,true,true,false);
		when(mockIterator2.next()).thenReturn(categoryOptionsResource);
		when(categoryOptionsResource.getName()).thenReturn("snow-chains");
		when(categoryOptionsResource.getChildren()).thenReturn(mockIterable4);
		when(mockIterable4.iterator()).thenReturn(mockIterator4);
		when(mockIterator4.hasNext()).thenReturn(true,true,true,false);
		when(mockIterator4.next()).thenReturn(destinationCountryResource);
		when(destinationCountryResource.getName()).thenReturn("it");
		when(destinationCountryResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(childJCRResource3);
		when(childJCRResource3.getChild(HertzConstants.PAR)).thenReturn(parResource2);
		when(parResource2.getChildren()).thenReturn(mockIterable5);
		when(mockIterable5.iterator()).thenReturn(mockIterator5);
		when(mockIterator5.hasNext()).thenReturn(true,false);
		when(mockIterator5.next()).thenReturn(preselectedComponentResource);
		when(preselectedComponentResource.getValueMap()).thenReturn(valueMap2);
		when(valueMap2.containsKey(Mockito.anyString())).thenReturn(true);
		when(valueMap2.get("preselectedValue", String.class)).thenReturn("true");
		
		AncillaryCategoriesUse.getCategoriesPages(countryLevelResource, jsonObject);
	}
	
	@Test
	public final void testVariables() {
		Mockito.doCallRealMethod().when(ancillaryCategoriesUse).setJsonString("jsonString");
		Mockito.doCallRealMethod().when(ancillaryCategoriesUse).getJsonString();
		ancillaryCategoriesUse.setJsonString("jsonString");
		Assert.assertNotNull(ancillaryCategoriesUse.getJsonString());
	}
	
	@Test
	public final void testManipulateJSONString() throws JSONException {
		JSONObject jsonObj1 = new JSONObject("{'configurableTextKey':'category-name','configurableTextValue':'convenience-options'}");
		JSONObject jsonObj2 = new JSONObject("{'configurableTextKey':'category-name1','configurableTextValue':'safety-options'}");
		AncillaryCategoriesUse.manipulateJSONString(jsonObj1,jsonObj2);
	}
	
}
