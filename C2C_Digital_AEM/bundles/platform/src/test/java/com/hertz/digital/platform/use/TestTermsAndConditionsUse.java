/**
 * 
 */
package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.commons.json.JSONObject;
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

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.constants.TestConstants;
import com.hertz.digital.platform.service.api.JCRService;


/**
 * @author a.dhingra
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestTermsAndConditionsUse {

	@InjectMocks
	private TermsAndConditionsUse termsAndConditionsUse;
	
	Logger log;
	
	@Mock
	Resource pageResource;
	
	@Mock
	Resource parentPageResource;
	
	@Mock
	Resource destinationCountryResource;
	
	@Mock
	Resource sourceCountryResource;
	
	@Mock
	SlingScriptHelper serviceHelper;
	
	@Mock
	JCRService jcrService;
	
	@Mock
	ResourceResolver resolver;
	
	@Mock
	Session session;
	
	@Mock
	QueryBuilder builder;
	
	@Mock
	Query query;
	
	@Mock
	SearchResult searchResult;
	
	@Mock
	PredicateGroup predicateGroup;
	
	List<Hit> hitList=new ArrayList<>();
	
	@Mock
	Hit hit;
	
	@Mock
	Iterator<Resource> iterator;
	
	@Mock
	Resource childResource;
	
	@Mock
	Resource parResource;
	
	@Mock
	ValueMap map;
	
	@Before
	public final void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		termsAndConditionsUse=PowerMockito.mock(TermsAndConditionsUse.class);
		Mockito.doCallRealMethod().when(termsAndConditionsUse).activate();
		hitList.add(hit);
		
	}
	
	@Test
	public final void testActivate() throws Exception{
		JSONObject object=new JSONObject();
		object.put(HertzConstants.ORDER_NUMBER, "1");
		object.put(HertzConstants.RATE_TYPE, TestConstants.RATE_TYPE_PAGE_PATH);
		String [] array=new String[]{object.toString()};		
		when(termsAndConditionsUse.getResource()).thenReturn(pageResource);
		when(pageResource.getParent()).thenReturn(parentPageResource);
		when(parentPageResource.getParent()).thenReturn(destinationCountryResource);
		when(destinationCountryResource.getParent()).thenReturn(sourceCountryResource);
		when(sourceCountryResource.getPath()).thenReturn(TestConstants.SOURCE_COUNTRY_PAGE_PATH);
		when(destinationCountryResource.getPath()).thenReturn(TestConstants.DESTINATION_COUNTRY_PAGE_PATH);
		when(termsAndConditionsUse.getSlingScriptHelper()).thenReturn(serviceHelper);
		when(serviceHelper.getService(JCRService.class)).thenReturn(jcrService);
		when(jcrService.searchResults(Mockito.eq(resolver), Mockito.any(HashMap.class))).thenReturn(searchResult);
		when(termsAndConditionsUse.getResourceResolver()).thenReturn(resolver);
		when(resolver.adaptTo(Session.class)).thenReturn(session);
		when(resolver.adaptTo(QueryBuilder.class)).thenReturn(builder);
		when(builder.createQuery(any(PredicateGroup.class), Mockito.eq(session))).thenReturn(query);
		when(query.getResult()).thenReturn(searchResult);
		when(query.getPredicates()).thenReturn(predicateGroup);
		when(searchResult.getHits()).thenReturn(hitList);
		when(hit.getResource()).thenReturn(childResource);
		when(childResource.listChildren()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true,false);
		when(iterator.next()).thenReturn(parResource);
		when(parResource.getValueMap()).thenReturn(map);
		when(map.get("text")).thenReturn("text");
		when(map.get(HertzConstants.RATE_TYPE_CONFIG)).thenReturn(array);
		when(parentPageResource.getPath()).thenReturn(TestConstants.RATE_TYPE_PAGE_PATH);
		when(parentPageResource.getName()).thenReturn("prepaid");
		termsAndConditionsUse.activate();		
	}
	
	@Test
	public final void testGetterSetter(){
		Mockito.doCallRealMethod().when(termsAndConditionsUse).getJsonString();
		Mockito.doCallRealMethod().when(termsAndConditionsUse).setJsonString(Mockito.anyString());
		termsAndConditionsUse.setJsonString("jsonString");
		Assert.assertTrue(termsAndConditionsUse.getJsonString().equals("jsonString"));
	}
}
