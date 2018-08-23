package com.hertz.digital.platform.service.impl;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
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

import com.adobe.granite.asset.api.Rendition;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.use.SocialLinksUse;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestCountryLanguageSelectorServiceImpl {

	@InjectMocks
	CountryLanguageSelectorServiceImpl countryLanguageSelectorServiceImpl;
	
	Logger log;
	
	@Mock
	ResourceResolverFactory mockResolverFactory;
		
	@Mock	
	ResourceResolver resolver;
	
	@Mock	
	Page countryPage;
	
	@Mock
	Iterator<Resource> iterator;
	
	@Mock
	Iterable<Resource> iterable;
	
	@Mock
	Iterator<Resource> iterator1;
	
	@Mock
	Iterable<Resource> iterable1;
	
	@Mock
	Resource resource;
	
	@Mock
	Resource childResource;
	
	@Mock
	Resource countryLanguageResource;
	
	@Mock
	ValueMap valueMap;
	
	@Mock
	ValueMap componentProperties;
	
	@Mock
	ValueMap textfieldResourceProperties;
	
	@Mock
	ValueMap valueMap1;
	
	@Mock
	Resource languageResource;
	
	@Mock
	Resource jcrContentResource;
	
	@Mock
	Resource headerJcrContentResource;
	
	@Mock
	Resource headerParResource;
	
	@Mock
	Resource textfieldParResource;
	
	@Mock
	Resource parResource;
	
	@Mock
	Resource domainResource;
	
	@Mock
	Resource componentResource;
	
	@Mock
	Session session;
	
	@Mock
	QueryBuilder queryBuilder;
	
	@Mock
	Query query;

	@Mock
	SearchResult result;
	
	@Mock
	Hit hit;
	
	@Mock
	Rendition rendition;
	
	List<Hit> mockList;
	String countryLanguageResPath = HertzConstants.JCR_CONTENT + "/" + HertzConstants.PAR + "/" + HertzConstants.COUNTRYLANGUAGE;
	
	@Before
	public final void setup() throws Exception{
		countryLanguageSelectorServiceImpl=PowerMockito.mock(CountryLanguageSelectorServiceImpl.class);
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(LoggerFactory.class);
		log=Mockito.mock(Logger.class);
		Field field=SocialLinksUse.class.getDeclaredField("logger");
		field.setAccessible(true);
		Field modifiersField=Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL); 
		field.set(null,log);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		Mockito.doCallRealMethod().when(countryLanguageSelectorServiceImpl).getCountryLanguageJson(resource);
		Mockito.doCallRealMethod().when(countryLanguageSelectorServiceImpl).getHeaderCountryLanguageJson(resource);
	}
	
	@Test
	public final void testGetCountryLanguageJson() throws Exception{
		when(resource.getChildren()).thenReturn(iterable);
		when(iterable.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true,false);
		when(iterator.next()).thenReturn(childResource);
		when(childResource.getName()).thenReturn("us");
		when(resource.getChild(Mockito.anyString())).thenReturn(childResource);
		when(childResource.getName()).thenReturn("us");
		when(childResource.adaptTo(Page.class)).thenReturn(countryPage);
		when(countryPage.getProperties()).thenReturn(valueMap);
		when(valueMap.get(HertzConstants.JCR_TITLE_PROPERTY, String.class)).thenReturn("United States");
		when(childResource.getChild(Mockito.anyString())).thenReturn(languageResource);
		when(languageResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(jcrContentResource);
		when(jcrContentResource.getChild(HertzConstants.PAR)).thenReturn(parResource);
		when(parResource.getChildren()).thenReturn(iterable1);
		when(iterable1.iterator()).thenReturn(iterator1);
		when(iterator1.hasNext()).thenReturn(true,false);
		when(iterator1.next()).thenReturn(componentResource);
		when(componentResource.getValueMap()).thenReturn(valueMap1);
		when(valueMap1.containsKey(Mockito.anyString())).thenReturn(true);
		when(valueMap1.get("locale", String.class)).thenReturn("locale");
		when(valueMap1.get("languageName", String.class)).thenReturn("languageName");
		when(valueMap1.get("languageCode", String.class)).thenReturn("languageCode");
		when(valueMap1.get("isDefaultLanguage",Boolean.class)).thenReturn(true);
		when(valueMap1.get(HertzConstants.IRACLINK,String.class)).thenReturn("iraclink");
		when(valueMap1.get("url", String.class)).thenReturn("url");
		Assert.assertNotNull(countryLanguageSelectorServiceImpl.getCountryLanguageJson(resource));
		
		when(resource.getChildren()).thenReturn(iterable);
		when(iterable.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true,false);
		when(iterator.next()).thenReturn(childResource);
		when(childResource.getName()).thenReturn("us");
		when(childResource.adaptTo(Page.class)).thenReturn(countryPage);
		when(countryPage.getProperties()).thenReturn(valueMap);
		when(valueMap.get(HertzConstants.JCR_TITLE_PROPERTY, String.class)).thenReturn("United States");
		when(childResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(headerJcrContentResource);
		when(headerJcrContentResource.getChild(HertzConstants.PAR)).thenReturn(headerParResource);
		when(headerParResource.getChild(Mockito.anyString())).thenReturn(textfieldParResource);
		when(textfieldParResource.getValueMap()).thenReturn(textfieldResourceProperties);
		Assert.assertNotNull(countryLanguageSelectorServiceImpl.getCountryLanguageJson(resource));
	}
	
	@Test
	public final void testGetHeaderCountryLanguageJSON() throws Exception{
		when(resource.getChild(countryLanguageResPath)).thenReturn(countryLanguageResource);
		when(countryLanguageResource.getValueMap()).thenReturn(componentProperties);
		when(componentProperties.containsKey(Mockito.anyString())).thenReturn(true);
		when(componentProperties.get(HertzConstants.COUNTRY_LABEL,String.class)).thenReturn("countrylabel");
		when(componentProperties.get(HertzConstants.LANGUAGE_LABEL,String.class)).thenReturn("languagelabel");
		when(componentProperties.get(HertzConstants.BUTTON_LABEL,String.class)).thenReturn("buttonlabel");
		when(componentProperties.get(HertzConstants.UPDATE_MESSAGE,String.class)).thenReturn("updatemessage");
		when(componentProperties.get(HertzConstants.NO_RESULTS_MESSAGE,String.class)).thenReturn("noresultsmessage");
		when(componentProperties.get(HertzConstants.IRACLINK,String.class)).thenReturn("irac link");
		Assert.assertNotNull(countryLanguageSelectorServiceImpl.getHeaderCountryLanguageJson(resource));
		when(componentProperties.containsKey(Mockito.anyString())).thenReturn(false);
		Assert.assertNotNull(countryLanguageSelectorServiceImpl.getHeaderCountryLanguageJson(resource));
		
	}
	
	@Test
	public final void testGetHeaderCountryLanguageRedirectJson() throws Exception{
		when(resource.getChild("jcr:content/par/countrylangredirect")).thenReturn(countryLanguageResource);
		when(countryLanguageResource.getValueMap()).thenReturn(valueMap);
		when(valueMap.containsKey(HertzConstants.REDIRECT_HEADING)).thenReturn(true);
		when(valueMap.containsKey(HertzConstants.REDIRECT_MESSAGE)).thenReturn(true);
		when(valueMap.containsKey(HertzConstants.CANCEL_BUTTON_LABEL)).thenReturn(true);
		when(valueMap.containsKey(HertzConstants.CONTINUE_BUTTON_LABEL)).thenReturn(true);
		when(valueMap.get(HertzConstants.REDIRECT_HEADING, String.class)).thenReturn("REDIRECT_HEADING");
		when(valueMap.get(HertzConstants.REDIRECT_MESSAGE, String.class)).thenReturn("REDIRECT_MESSAGE");
		when(valueMap.get(HertzConstants.CANCEL_BUTTON_LABEL, String.class)).thenReturn("CANCEL_BUTTON_LABEL");
		when(valueMap.get(HertzConstants.CONTINUE_BUTTON_LABEL, String.class)).thenReturn("CONTINUE_BUTTON_LABEL");
		Mockito.doCallRealMethod().when(countryLanguageSelectorServiceImpl).getHeaderCountryLanguageRedirectJson(resource);
		Assert.assertNotNull(countryLanguageSelectorServiceImpl.getHeaderCountryLanguageRedirectJson(resource));
	}
	
	@Test
	public final void testGetHeaderCountryLanguageRedirectJsonWithChildNull() throws Exception{
		when(resource.getChild("jcr:content/par/countrylangredirect")).thenReturn(null);
		when(countryLanguageResource.getValueMap()).thenReturn(valueMap);
		when(valueMap.containsKey(HertzConstants.REDIRECT_HEADING)).thenReturn(true);
		when(valueMap.containsKey(HertzConstants.REDIRECT_MESSAGE)).thenReturn(true);
		when(valueMap.containsKey(HertzConstants.CANCEL_BUTTON_LABEL)).thenReturn(true);
		when(valueMap.containsKey(HertzConstants.CONTINUE_BUTTON_LABEL)).thenReturn(true);
		when(valueMap.get(HertzConstants.REDIRECT_HEADING, String.class)).thenReturn("REDIRECT_HEADING");
		when(valueMap.get(HertzConstants.REDIRECT_MESSAGE, String.class)).thenReturn("REDIRECT_MESSAGE");
		when(valueMap.get(HertzConstants.CANCEL_BUTTON_LABEL, String.class)).thenReturn("CANCEL_BUTTON_LABEL");
		when(valueMap.get(HertzConstants.CONTINUE_BUTTON_LABEL, String.class)).thenReturn("CONTINUE_BUTTON_LABEL");
		Mockito.doCallRealMethod().when(countryLanguageSelectorServiceImpl).getHeaderCountryLanguageRedirectJson(resource);
		Assert.assertNotNull(countryLanguageSelectorServiceImpl.getHeaderCountryLanguageRedirectJson(resource));
	}
	
	@Test
	public final void testGetHeaderCountryLanguageRedirectJsonWithNoMapParameters() throws Exception{
		when(resource.getChild("jcr:content/par/countrylangredirect")).thenReturn(countryLanguageResource);
		when(countryLanguageResource.getValueMap()).thenReturn(valueMap);
		when(valueMap.containsKey(HertzConstants.REDIRECT_HEADING)).thenReturn(false);
		when(valueMap.containsKey(HertzConstants.REDIRECT_MESSAGE)).thenReturn(false);
		when(valueMap.containsKey(HertzConstants.CANCEL_BUTTON_LABEL)).thenReturn(false);
		when(valueMap.containsKey(HertzConstants.CONTINUE_BUTTON_LABEL)).thenReturn(false);
		Mockito.doCallRealMethod().when(countryLanguageSelectorServiceImpl).getHeaderCountryLanguageRedirectJson(resource);
		Assert.assertNotNull(countryLanguageSelectorServiceImpl.getHeaderCountryLanguageRedirectJson(resource));
	}
	
}