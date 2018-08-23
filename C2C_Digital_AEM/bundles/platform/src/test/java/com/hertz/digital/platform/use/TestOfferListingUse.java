package com.hertz.digital.platform.use;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.commons.json.JSONException;
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
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.bean.CategoryListingBean;
import com.hertz.digital.platform.bean.OfferCategoryBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.constants.TestConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.service.api.JCRService;
import com.hertz.digital.platform.utils.HertzUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class,HertzUtils.class })
public class TestOfferListingUse {
	
	@InjectMocks
	private OffersListingUse listingUse;
	
	Logger log;
	
	@Mock
	SlingHttpServletRequest request;
	
	@Mock
	Resource resource;
	
	@Mock
	Resource parResource;
	
	@Mock
	Resource parentResource;
	
	@Mock
	ValueMap propMap;
	
	@Mock
	Page page;
	
	@Mock
	SlingScriptHelper scriptHelper;
	
	@Mock
	JCRService jcrService;
	
	@Mock
	ResourceResolver resolver;
	
	@Mock
	Query query;
	
	@Mock
	SearchResult result;
	
	@Mock
	QueryBuilder builder;
	
	@Mock
	HertzConfigFactory hFactory;
	
	@Mock
	Session session;
	
	@Mock
	Resource childResource;
	
	@Mock
	ValueMap childValueMap;
	
	@Mock
	Hit hit;
	
	@Mock
	PredicateGroup predicateGroup;
	
	List<Hit> hits=new ArrayList<>();
	
	@Before
	public void setup() throws JSONException, RepositoryException {
		listingUse=PowerMockito.mock(OffersListingUse.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		Mockito.doCallRealMethod().when(listingUse).activate();
		Mockito.doCallRealMethod().when(listingUse).getJsonString();
		
		Mockito.doCallRealMethod().when(listingUse).setJsonString(Mockito.anyString());
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testActivate() throws RepositoryException, JSONException{
		String [] seoArray=new String[]{"{'configurableSeoKey':'seoKeywords','configurableSeoValue':'SEO Keywords'}"};
		when(listingUse.getRequest()).thenReturn(request);
		when(request.getPathInfo()).thenReturn(TestConstants.OFFER_LANDING_PATH);
		when(listingUse.getResource()).thenReturn(resource);
		when(listingUse.getCurrentPage()).thenReturn(page); 
		when(page.getTitle()).thenReturn("Offer Landing Page");
		when(listingUse.getResource()).thenReturn(resource);
		when(resource.adaptTo(ValueMap.class)).thenReturn(propMap);
		when(propMap.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS, new String[]{})).thenReturn(seoArray);
		when(resource.getResourceType()).thenReturn("/hertz/components/pages/data");
		when(propMap.get(JcrConstants.JCR_TITLE,String.class)).thenReturn("Offer Details Page");
		when(propMap.get(JcrConstants.JCR_DESCRIPTION,String.class)).thenReturn("description");
		when(resource.getChild(HertzConstants.CONFIGTEXT_PARSYS)).thenReturn(parResource);
		
		when(parResource.hasChildren()).thenReturn(true);
		Iterable<Resource> configTextParSysResourceIterable =  Mockito.mock(Iterable.class);
		when(parResource.getChildren()).thenReturn(configTextParSysResourceIterable);
		Iterator configTextParSysResourceIterator = Mockito.mock(Iterator.class);
		when(configTextParSysResourceIterable.iterator()).thenReturn(configTextParSysResourceIterator);
		when(configTextParSysResourceIterator.hasNext()).thenReturn(true, false);
		Resource child = Mockito.mock(Resource.class);
		when(configTextParSysResourceIterator.next()).thenReturn(child);
		when(child.getResourceType()).thenReturn("hertz/components/configtext/textareapair");
		ValueMap valuesChild =  Mockito.mock(ValueMap.class);
		when(child.getValueMap()).thenReturn(valuesChild);
		when(valuesChild.get("key", String.class)).thenReturn("key");
		when(valuesChild.get("value", String.class)).thenReturn("key1");
		 
		when(resource.getParent()).thenReturn(parentResource); 

		Iterable<Resource> parentResourceIterable =  Mockito.mock(Iterable.class);
		when(parentResource.getChildren()).thenReturn(parentResourceIterable);
		Iterator parentResourceIterator = Mockito.mock(Iterator.class);
		when(parentResourceIterable.iterator()).thenReturn(parentResourceIterator);
		when(parentResourceIterator.hasNext()).thenReturn(true, false);
		Resource subResource = Mockito.mock(Resource.class);
		when(parentResourceIterator.next()).thenReturn(subResource);
		
		when(subResource.getName()).thenReturn("another Test");
		Page subPage= Mockito.mock(Page.class);
		when(subResource.adaptTo(Page.class)).thenReturn(subPage);
		ValueMap subPageProperties = Mockito.mock(ValueMap.class);
		when(subPage.getProperties()).thenReturn(subPageProperties);
		when(subPageProperties.get(HertzConstants.JCR_CQ_TEMPLATE, String.class)).thenReturn(HertzConstants.OFFER_CATEGORY_TEMPLATE_PATH);
		Resource subJCRResource = Mockito.mock(Resource.class);
		when(subResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(subJCRResource);
		ValueMap subJCRcomponentProperties =  Mockito.mock(ValueMap.class);
		when(subJCRResource.getValueMap()).thenReturn(subJCRcomponentProperties);
		when(subJCRcomponentProperties.containsKey(HertzConstants.CATEGORYNAME)).thenReturn(true);
		when(subJCRcomponentProperties.get(HertzConstants.CATEGORYNAME)).thenReturn(HertzConstants.CATEGORYNAME);
		when(subJCRcomponentProperties.containsKey(HertzConstants.RANK)).thenReturn(true);
		when(subJCRcomponentProperties.get(HertzConstants.RANK)).thenReturn(HertzConstants.RANK);
		when(subJCRcomponentProperties.containsKey(HertzConstants.HEADLINE)).thenReturn(true);
		when(subJCRcomponentProperties.get(HertzConstants.HEADLINE)).thenReturn(HertzConstants.HEADLINE); 
		when(subJCRcomponentProperties.containsKey(HertzConstants.SUBHEAD)).thenReturn(true);
		when(subJCRcomponentProperties.get(HertzConstants.SUBHEAD)).thenReturn(HertzConstants.SUBHEAD); 
		when(subJCRcomponentProperties.containsKey(HertzConstants.CTA_LABEL)).thenReturn(true);
		when(subJCRcomponentProperties.get(HertzConstants.CTA_LABEL)).thenReturn(HertzConstants.CTA_LABEL); 
		when(subJCRcomponentProperties.containsKey(HertzConstants.CTA_ACTION)).thenReturn(true);
		when(subJCRcomponentProperties.get(HertzConstants.CTA_ACTION)).thenReturn(HertzConstants.CTA_ACTION); 
		
		when(subJCRResource.hasChildren()).thenReturn(true);
		Resource fileImageResource = Mockito.mock(Resource.class);
		when(subJCRResource.getChild(HertzConstants.IMAGE)).thenReturn(fileImageResource);		
		ValueMap fileImageResourceProperties = Mockito.mock(ValueMap.class);		
		when(fileImageResource.getValueMap()).thenReturn(fileImageResourceProperties);
		
		when(fileImageResourceProperties.get(Mockito.anyString(), Mockito.any())).thenReturn("fileReference");
		
		Resource fileLogoResource = Mockito.mock(Resource.class);
		when(subJCRResource.getChild(HertzConstants.LOGO)).thenReturn(fileLogoResource);
		ValueMap fileLogoResourceProperties = Mockito.mock(ValueMap.class);		
		when(fileLogoResource.getValueMap()).thenReturn(fileLogoResourceProperties);
		when(fileLogoResourceProperties.get(Mockito.anyString(), Mockito.any())).thenReturn("fileReference");
		
		Resource fileBadgeResource = Mockito.mock(Resource.class);
		when(subJCRResource.getChild(HertzConstants.BADGE)).thenReturn(fileBadgeResource);
		ValueMap fileBadgeResourceProperties = Mockito.mock(ValueMap.class);		
		when(fileBadgeResource.getValueMap()).thenReturn(fileBadgeResourceProperties);
		when(fileBadgeResourceProperties.get(Mockito.anyString(), Mockito.any())).thenReturn("fileReference");
		
		
		Iterable<Resource> subResourceIterable =  Mockito.mock(Iterable.class);
		when(subResource.getChildren()).thenReturn(subResourceIterable);
		Iterator subResourceIterator = Mockito.mock(Iterator.class);
		when(subResourceIterable.iterator()).thenReturn(subResourceIterator);
		when(subResourceIterator.hasNext()).thenReturn(true, false);
		Resource subSubResource = Mockito.mock(Resource.class);
		when(subResourceIterator.next()).thenReturn(subSubResource);		
		when(subSubResource.getName()).thenReturn("another Test");
		
		Page subSubPage = Mockito.mock(Page.class);
		when(subSubResource.adaptTo(Page.class)).thenReturn(subSubPage);
		
		ValueMap subSubPageProperties = Mockito.mock(ValueMap.class);
		when(subSubPage.getProperties()).thenReturn(subSubPageProperties);
		when(subSubPageProperties.get(HertzConstants.JCR_CQ_TEMPLATE, String.class)).thenReturn(HertzConstants.OFFER_CATEGORY_TEMPLATE_PATH);
		
		Resource subOfferJCRResource = Mockito.mock(Resource.class);
		when(subSubResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(subOfferJCRResource);
		ValueMap subOfferJCRcomponentProperties = Mockito.mock(ValueMap.class);
		when(subOfferJCRResource.getValueMap()).thenReturn(subOfferJCRcomponentProperties);
		
		
		when(parentResource.getPath()).thenReturn(TestConstants.OFFER_LANDING_PATH);
		when(listingUse.getSlingScriptHelper()).thenReturn(scriptHelper);
		when(scriptHelper.getService(JCRService.class)).thenReturn(jcrService);
		when(jcrService.searchResults(Mockito.eq(resolver), Mockito.any(HashMap.class))).thenReturn(result);
		when(listingUse.getResourceResolver()).thenReturn(resolver);
		when(resolver.adaptTo(Session.class)).thenReturn(session);
		when(resolver.adaptTo(QueryBuilder.class)).thenReturn(builder);
		when(builder.createQuery(any(PredicateGroup.class), eq(session))).thenReturn(query);
		when(query.getPredicates()).thenReturn(predicateGroup);
		when(predicateGroup.toString()).thenReturn("predicateGroup");
		when(query.getResult()).thenReturn(result);
		hits.add(hit);
		when(result.getHits()).thenReturn(hits);
		when(hit.getResource()).thenReturn(childResource);
		when(hit.getPath()).thenReturn(TestConstants.OFFER_PATH);
		when(childResource.getValueMap()).thenReturn(childValueMap);
		when(childResource.getParent()).thenReturn(parentResource);
		when(parentResource.getName()).thenReturn("partner");
		when(childValueMap.get(JcrConstants.JCR_TITLE)).thenReturn("Offer Landing Page");
		when(resource.getPath()).thenReturn("/account/rentals");
		listingUse.activate();
		assertNotNull(listingUse.getJsonString());
	}
	
	@Test
	public void testCategoryListingBean() throws RepositoryException, JSONException{
		CategoryListingBean bean = new CategoryListingBean();
		bean.setName("Name");
		bean.setRank("rank");
		OfferCategoryBean categoryBean= new OfferCategoryBean();
		bean.setAttributes(categoryBean);
		bean.setSubCategory(categoryBean);
		assertNotNull(bean.getName());
		assertNotNull(bean.getRank());
		assertNotNull(bean.getAttributes());
		assertNotNull(bean.getSubCategory());
	}
	
	@Test
	public void testActivate1() throws RepositoryException, JSONException{
		Node node=PowerMockito.mock(Node.class);
		PropertyIterator iterator=PowerMockito.mock(PropertyIterator.class);
		Property property=PowerMockito.mock(Property.class);
		NodeIterator nIterator=PowerMockito.mock(NodeIterator.class);
		when(listingUse.getRequest()).thenReturn(request);
		when(request.getPathInfo()).thenReturn(TestConstants.OFFER_LANDING_PATH+"/jcr:content");
		when(listingUse.getResource()).thenReturn(resource);
		when(resource.adaptTo(Node.class)).thenReturn(node);
		when(node.getProperties()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true,false);
		when(iterator.nextProperty()).thenReturn(property);
		when(property.getType()).thenReturn(2);
		when(property.getName()).thenReturn("name");
		when(property.isMultiple()).thenReturn(true);
		when(property.getLength()).thenReturn(new Long(1));
		when(property.getLengths()).thenReturn(new long[]{new Long(1)});
		when(node.getNodes()).thenReturn(nIterator);
		when(nIterator.hasNext()).thenReturn(false);
		listingUse.activate();
	}
}
