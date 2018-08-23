package com.hertz.digital.platform.use;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
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
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.bean.ImageInfoBean;
import com.hertz.digital.platform.bean.OfferCategoryBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.service.api.JCRService;
import com.hertz.digital.platform.utils.HertzUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class})
public class TestOfferCategoryUse {
	
	@InjectMocks
	private OfferCategoryUse categoryUse ; 
	Logger log;
	
	@Mock
	ResourceResolver resourceResolver;
	
	@Mock
	Resource resource;
	
	@Mock
	Page currentPage;
	
	@Mock
	ValueMap propMap;
	
	@Mock
	SlingScriptHelper scriptHelper;
	
	@Mock
	JCRService jcrService;
	
	@Mock
	SearchResult result;
	@Mock
	 Resource hitResource ;
	
	@Mock
	ValueMap componentProperties;
	
	@Mock
	Resource fileImageResource;
	
	@Mock
	Resource fileLogoResource;
	
	@Mock
	Resource fileBadgeResource;
	@Mock
	ValueMap properties;
	
	@Mock
	Resource currentPageResource;
	
	@Mock
	HertzConfigFactory configFactory;
	
	@Mock
	Resource parentCategoryResource;
	
	@Mock
	Resource offerLandingResource;
	
	@Mock
	SlingHttpServletRequest request;
    
    @Mock
    RequestPathInfo requestPathInfo;
	
	
	
	@Before
	public final void setup() throws Exception {
		categoryUse =PowerMockito.mock(OfferCategoryUse.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);
		Mockito.doCallRealMethod().when(categoryUse).activate();
		
	}
	
	@Test
	public final void testActivate() throws Exception {
		when(categoryUse.getResourceResolver()).thenReturn(resourceResolver);
		when(categoryUse.getResource()).thenReturn(resource);
		when(categoryUse.getRequest()).thenReturn(request);
		when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		when(categoryUse.getCurrentPage()).thenReturn(currentPage);
		when(categoryUse.getSlingScriptHelper()).thenReturn(scriptHelper);
		when(currentPage.getTitle()).thenReturn("title");
		when(resource.adaptTo(ValueMap.class)).thenReturn(propMap);
		when(resource.getParent()).thenReturn(parentCategoryResource);
		when(parentCategoryResource.getParent()).thenReturn(offerLandingResource);
		String str ="{\"labeld\": \"12345\"}";
		String[] seoItems ={str, str}; 
		when(propMap.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS, new String[]{})).thenReturn(seoItems);
		when(resource.getResourceType()).thenReturn("/hertz/components/pages/data");
		when(scriptHelper.getService(JCRService.class)).thenReturn(null);
		when(scriptHelper.getService(HertzConfigFactory.class)).thenReturn(configFactory);
		when(configFactory
				.getPropertyValue(HertzConstants.Hertz_SPA_ALLOWED_COMPONENTS)).thenReturn(new String[]{"linkpair"});
		when(jcrService.searchResults(Mockito.any(ResourceResolver.class), Mockito.anyMap())).thenReturn(result);
		List<Hit> hits = new ArrayList();
		Hit hit= Mockito.mock(Hit.class);
		hits.add(hit);
		when(result.getHits()).thenReturn(	hits );
		when(hit.getResource()).thenReturn(hitResource);
		when(hitResource.hasChildren()).thenReturn(true);
		Iterable iterable= Mockito.mock(Iterable.class);
		when(hitResource.getChildren()).thenReturn(iterable);
		
		Iterator iterator = Mockito.mock(Iterator.class);
			when(iterable.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn( false);
		when(resource.getValueMap()).thenReturn(componentProperties);
		when(componentProperties.containsKey(HertzConstants.CATEGORYNAME)).thenReturn(true);
		when(componentProperties.get(HertzConstants.CATEGORYNAME)).thenReturn(HertzConstants.CATEGORYNAME);
		when(componentProperties.containsKey(HertzConstants.HEADLINE)).thenReturn(true);
		when(componentProperties.get(HertzConstants.HEADLINE)).thenReturn(HertzConstants.HEADLINE);
		when(componentProperties.containsKey(HertzConstants.SUBHEAD)).thenReturn(true);
		when(componentProperties.get(HertzConstants.SUBHEAD)).thenReturn(HertzConstants.SUBHEAD);
		when(componentProperties.containsKey(HertzConstants.CTA_LABEL)).thenReturn(true);
		when(componentProperties.get(HertzConstants.CTA_LABEL)).thenReturn(HertzConstants.CTA_LABEL);
		when(componentProperties.containsKey(HertzConstants.CTA_ACTION)).thenReturn(true);
		when(componentProperties.get(HertzConstants.CTA_ACTION)).thenReturn(HertzConstants.CTA_ACTION);
		when(resource.hasChildren()).thenReturn(true);
		when(resource.getChild(HertzConstants.IMAGE)).thenReturn(fileImageResource);
		when(fileImageResource.getValueMap()).thenReturn(properties);
		when(properties.get("fileReference", String.class)).thenReturn("fileReference");
		when(fileImageResource.getPath()).thenReturn("/content");
		when(resource.getChild(HertzConstants.BADGE)).thenReturn(fileBadgeResource);
		when(fileBadgeResource.getValueMap()).thenReturn(properties);
		when(fileBadgeResource.getPath()).thenReturn("/content");
		when(resource.getChild(HertzConstants.LOGO)).thenReturn(fileLogoResource);
		when(fileLogoResource.getValueMap()).thenReturn(properties);
		when(fileLogoResource.getPath()).thenReturn("/content");
		when(currentPage.adaptTo(Resource.class)).thenReturn(currentPageResource);
		Iterable<Resource> resourceIterable= Mockito.mock(Iterable.class);
		when(currentPageResource.getChildren()).thenReturn(resourceIterable);
		Iterator  resourceIterator = Mockito.mock(Iterator.class);
		when(resourceIterable.iterator()).thenReturn(resourceIterator);
		when(resourceIterator.hasNext()).thenReturn(true, false);
		Resource subResource =  Mockito.mock(Resource.class);
		when(resourceIterator.next()).thenReturn(subResource);
		
		when(subResource.getName()).thenReturn("test");
		Page subPage = Mockito.mock(Page.class);
		when(subResource.adaptTo(Page.class)).thenReturn(subPage);
		ValueMap subPageValueMap = Mockito.mock(ValueMap.class);
		when(subPage.getProperties()).thenReturn(subPageValueMap);
		when(subPageValueMap.get(HertzConstants.JCR_CQ_TEMPLATE, String.class)).thenReturn(HertzConstants.OFFER_CATEGORY_TEMPLATE_PATH);
		Resource subJCRResource = Mockito.mock(Resource.class);
		when(subJCRResource.getParent()).thenReturn(subResource);
		when(subResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(subJCRResource);
		ValueMap subJCRcomponentProperties= Mockito.mock(ValueMap.class);
		when(subJCRResource.getValueMap()).thenReturn(subJCRcomponentProperties);
		
		when(subJCRcomponentProperties.containsKey(HertzConstants.HEADLINE)).thenReturn(true);
		when(subJCRcomponentProperties.get(HertzConstants.HEADLINE)).thenReturn(HertzConstants.HEADLINE);
		when(subJCRcomponentProperties.containsKey(HertzConstants.SUBHEAD)).thenReturn(true);
		when(subJCRcomponentProperties.get(HertzConstants.SUBHEAD)).thenReturn(HertzConstants.SUBHEAD);
		
		when(subJCRResource.hasChildren()).thenReturn(true);
		when(subJCRResource.getChild(HertzConstants.IMAGE)).thenReturn(fileImageResource);
	//	when(fileImageResource.getValueMap()).thenReturn(properties);
		//when(properties.get("fileReference", String.class)).thenReturn("fileReference");
	//	when(fileImageResource.getPath()).thenReturn("/content");
		when(subJCRResource.getChild(HertzConstants.BADGE)).thenReturn(fileBadgeResource);
	//	when(fileBadgeResource.getValueMap()).thenReturn(properties);
	//	when(fileBadgeResource.getPath()).thenReturn("/content");
		when(subJCRResource.getChild(HertzConstants.LOGO)).thenReturn(fileLogoResource);
	//	when(fileLogoResource.getValueMap()).thenReturn(properties);
		//when(fileLogoResource.getPath()).thenReturn("/content");
		when(subJCRcomponentProperties.containsKey(HertzConstants.CTA_LABEL)).thenReturn(true);
		when(subJCRcomponentProperties.get(HertzConstants.CTA_LABEL)).thenReturn(HertzConstants.CTA_LABEL);
		when(subJCRcomponentProperties.containsKey(HertzConstants.CTA_ACTION)).thenReturn(true);
		when(subJCRcomponentProperties.get(HertzConstants.CTA_ACTION)).thenReturn(HertzConstants.CTA_ACTION);
		when(resource.getPath()).thenReturn("/account/rentals");
		categoryUse.activate();
		 OfferCategoryBean offerCategoryBean = new OfferCategoryBean();
		 ImageInfoBean badgeBean=new ImageInfoBean(fileBadgeResource);
		 ImageInfoBean imageBean=new ImageInfoBean(fileImageResource);
		 ImageInfoBean logoBean=new ImageInfoBean(fileLogoResource);
		 offerCategoryBean.setBadge(badgeBean);
		 offerCategoryBean.setImage(imageBean);
		 offerCategoryBean.setLogo(logoBean);
		 assertNotNull (offerCategoryBean.getBadge());
		 assertNotNull (offerCategoryBean.getLogo());
		 assertNotNull (offerCategoryBean.getImage());
	}

	@Test
	public final void testSetJsonString() throws Exception {
		Mockito.doCallRealMethod().when(categoryUse).setJsonString(Mockito.anyString());
		Mockito.doCallRealMethod().when(categoryUse).getJsonString();
		categoryUse.setJsonString("Test");
		 assertNotNull(categoryUse.getJsonString());
		 
	}
	
	@Test
	public final void testOfferCategoryBean() throws Exception {
		 OfferCategoryBean offerCategoryBean = new OfferCategoryBean();
		 offerCategoryBean.setCtaAction("Test");
		 offerCategoryBean.setCtaLabel("test");
		 offerCategoryBean.setHeadline("test");
		 offerCategoryBean.setSubhead("test");
		 offerCategoryBean.setRank("test");
		 
		 assertNotNull (offerCategoryBean.getCtaAction());
		 assertNotNull (offerCategoryBean.getCtaLabel());
		 assertNotNull (offerCategoryBean.getHeadline());
		 assertNotNull (offerCategoryBean.getRank());
		 assertNotNull (offerCategoryBean.getSubhead());
	
		 
	}
}
