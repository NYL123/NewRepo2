package com.hertz.digital.platform.use;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.google.gson.Gson;
import com.hertz.digital.platform.bean.CategoryListingBean;
import com.hertz.digital.platform.bean.OfferCategoryBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.service.api.JCRService;
import com.hertz.digital.platform.utils.HertzUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class, HertzUtils.class })
public class TestCategoryListingUse {

    @InjectMocks
    CategoryListingUse categoryListingUse;
    Logger log;
    @Mock
    ResourceResolver resourceResolver;
    @Mock
    SlingScriptHelper scriptHelper;
    @Mock
    Page currentPage;
    @Mock
    Resource resource;
    @Mock
    ValueMap propMap;
    @Mock
    JCRService jcrService;
    @Mock
    SearchResult result;
    
    @Mock
	SlingHttpServletRequest request;
    
    @Mock
    RequestPathInfo requestPathInfo;
    
    @Mock
    Resource hitResource;
    @Mock
    Resource fileImageResource;

    @Mock
    Resource fileLogoResource;

    @Mock
    Resource fileBadgeResource;
    @Mock
    ValueMap properties;

    @Mock
    HertzConfigFactory configFactory;

    @Before
    public final void setup() throws Exception {
        categoryListingUse = PowerMockito.mock(CategoryListingUse.class);
        PowerMockito.mockStatic(LoggerFactory.class);
        log = Mockito.mock(Logger.class);
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
        MockitoAnnotations.initMocks(this);
        Mockito.doCallRealMethod().when(categoryListingUse).activate();
    }

    @Test
    public final void testActivate() throws Exception {
        when(categoryListingUse.getResourceResolver())
                .thenReturn(resourceResolver);
        when(categoryListingUse.getResource()).thenReturn(resource);
        when(categoryListingUse.getRequest()).thenReturn(request);
		when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
        when(categoryListingUse.getCurrentPage()).thenReturn(currentPage);
        when(categoryListingUse.getSlingScriptHelper())
                .thenReturn(scriptHelper);
        PowerMockito.mockStatic(HertzUtils.class);
        Map<String,Object> includedMap=new HashMap<String,Object>();
        includedMap.put("subtitle", "subtitle");
        includedMap.put("heading", "heading");
        when(HertzUtils.toArray(categoryListingUse.get(HertzConstants.COMPONENTS, String.class), HertzConstants.COMMA)).thenReturn(new String[]{"hero"});
        when(HertzUtils.getIncludedComponentsAsBeans(requestPathInfo,resource, new String[]{"hero"}, resourceResolver, scriptHelper)).thenReturn(includedMap);
        when(currentPage.getTitle()).thenReturn("title");
        when(currentPage.hasContent()).thenReturn(true);
        Resource configTextParSysResource = Mockito.mock(Resource.class);
        when(currentPage.getContentResource(HertzConstants.CONFIGTEXT_PARSYS))
                .thenReturn(configTextParSysResource);
        when(configTextParSysResource.hasChildren()).thenReturn(true);
        Iterable<Resource> configTextParSysResourceIterable = Mockito
                .mock(Iterable.class);
        when(configTextParSysResource.getChildren())
                .thenReturn(configTextParSysResourceIterable);
        Iterator configTextParSysResourceIterator = Mockito
                .mock(Iterator.class);
        when(configTextParSysResourceIterable.iterator())
                .thenReturn(configTextParSysResourceIterator);
        when(configTextParSysResourceIterator.hasNext()).thenReturn(true,
                false);
        Resource child = Mockito.mock(Resource.class);
        when(configTextParSysResourceIterator.next()).thenReturn(child);
        when(child.getResourceType())
                .thenReturn("hertz/components/configtext/textareapair");
        ValueMap valuesChild = Mockito.mock(ValueMap.class);
        when(child.getValueMap()).thenReturn(valuesChild);
        when(valuesChild.get("key", String.class)).thenReturn("key");
        when(valuesChild.get("value", String.class)).thenReturn("key1");

        when(resource.adaptTo(ValueMap.class)).thenReturn(propMap);
        String str = "{\"labeld\": \"12345\"}";
        String[] seoItems = { str, str };
        when(propMap.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS,
                new String[] {})).thenReturn(seoItems);
        when(resource.getResourceType()).thenReturn("/hertz/components/pages/data");
        when(scriptHelper.getService(JCRService.class)).thenReturn(null);
        when(scriptHelper.getService(HertzConfigFactory.class))
                .thenReturn(configFactory);
        when(configFactory
                .getPropertyValue(HertzConstants.Hertz_SPA_ALLOWED_COMPONENTS))
                        .thenReturn(new String[] { "linkpair" });
    	PowerMockito.when(HertzUtils.getServiceReference(HertzConfigFactory.class)).thenReturn(configFactory);
        when(jcrService.searchResults(Mockito.any(ResourceResolver.class),
                Mockito.anyMap())).thenReturn(result);
        List<Hit> hits = new ArrayList();
        Hit hit = Mockito.mock(Hit.class);
        hits.add(hit);
        when(result.getHits()).thenReturn(hits);
        when(hit.getResource()).thenReturn(hitResource);
        when(hitResource.hasChildren()).thenReturn(true);
        Iterable iterable = Mockito.mock(Iterable.class);
        when(hitResource.getChildren()).thenReturn(iterable);
        Iterator iterator = Mockito.mock(Iterator.class);
        when(iterable.iterator()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(false);
        Resource parentResource = Mockito.mock(Resource.class);
        when(resource.getParent()).thenReturn(parentResource);
        Iterable<
                Resource> parentResourceIterable = Mockito.mock(Iterable.class);
        when(parentResource.getChildren()).thenReturn(parentResourceIterable);
        Iterator parentResourceIterator = Mockito.mock(Iterator.class);
        when(parentResourceIterable.iterator())
                .thenReturn(parentResourceIterator);
        when(parentResourceIterator.hasNext()).thenReturn(true, false);
        Resource subResource = Mockito.mock(Resource.class);
        when(parentResourceIterator.next()).thenReturn(subResource);
        when(subResource.getName()).thenReturn("test");
        Page subPage = Mockito.mock(Page.class);
        when(subResource.adaptTo(Page.class)).thenReturn(subPage);
        ValueMap subPageValueMap = Mockito.mock(ValueMap.class);
        when(subPage.getProperties()).thenReturn(subPageValueMap);
        when(subPageValueMap.get(HertzConstants.JCR_CQ_TEMPLATE, String.class))
                .thenReturn(HertzConstants.OFFER_CATEGORY_TEMPLATE_PATH);
        Resource subJCRResource = Mockito.mock(Resource.class);
        when(subResource.getChild(HertzConstants.JCR_CONTENT))
                .thenReturn(subJCRResource);
        ValueMap subJCRcomponentProperties = Mockito.mock(ValueMap.class);
        when(subJCRResource.getValueMap())
                .thenReturn(subJCRcomponentProperties);
        when(subJCRcomponentProperties.containsKey(HertzConstants.CATEGORYNAME))
                .thenReturn(true);
        when(subJCRcomponentProperties.get(HertzConstants.CATEGORYNAME))
                .thenReturn(HertzConstants.CATEGORYNAME);
        when(subJCRcomponentProperties.containsKey(HertzConstants.RANK))
                .thenReturn(true);
        when(subJCRcomponentProperties.get(HertzConstants.RANK))
                .thenReturn(HertzConstants.RANK);
        when(subJCRcomponentProperties.containsKey(HertzConstants.HEADLINE))
                .thenReturn(true);
        when(subJCRcomponentProperties.get(HertzConstants.HEADLINE))
                .thenReturn(HertzConstants.HEADLINE);
        when(subJCRcomponentProperties.containsKey(HertzConstants.SUBHEAD))
                .thenReturn(true);
        when(subJCRcomponentProperties.get(HertzConstants.SUBHEAD))
                .thenReturn(HertzConstants.SUBHEAD);
        when(subJCRcomponentProperties.containsKey(HertzConstants.CTA_LABEL))
                .thenReturn(true);
        when(subJCRcomponentProperties.get(HertzConstants.CTA_LABEL))
                .thenReturn(HertzConstants.CTA_LABEL);
        when(subJCRcomponentProperties.containsKey(HertzConstants.CTA_ACTION))
                .thenReturn(true);
        when(subJCRcomponentProperties.get(HertzConstants.CTA_ACTION))
                .thenReturn(HertzConstants.CTA_ACTION);
        when(subJCRResource.hasChildren()).thenReturn(true);
        when(subJCRResource.getChild(HertzConstants.IMAGE))
                .thenReturn(fileImageResource);
        when(fileImageResource.getValueMap()).thenReturn(properties);
        when(properties.get("fileReference", String.class))
                .thenReturn("fileReference");
        when(fileImageResource.getPath()).thenReturn("/content");
        when(subJCRResource.getChild(HertzConstants.BADGE))
                .thenReturn(fileBadgeResource);
        when(fileBadgeResource.getValueMap()).thenReturn(properties);
        when(fileBadgeResource.getPath()).thenReturn("/content");
        when(subJCRResource.getChild(HertzConstants.LOGO))
                .thenReturn(fileLogoResource);
        when(fileLogoResource.getValueMap()).thenReturn(properties);
        when(fileLogoResource.getPath()).thenReturn("/content");

        Iterable<Resource> subResourceIterable = Mockito.mock(Iterable.class);
        when(subResource.getChildren()).thenReturn(subResourceIterable);
        Iterator subResourceIterator = Mockito.mock(Iterator.class);
        when(subResourceIterable.iterator()).thenReturn(subResourceIterator);
        when(subResourceIterator.hasNext()).thenReturn(true, false);
        Resource newsubResource = Mockito.mock(Resource.class);
        when(subResourceIterator.next()).thenReturn(newsubResource);
        when(newsubResource.getName()).thenReturn("test");
        Page newsubPage = Mockito.mock(Page.class);
        when(newsubResource.adaptTo(Page.class)).thenReturn(newsubPage);
        ValueMap newsubPageValueMap = Mockito.mock(ValueMap.class);
        when(newsubPage.getProperties()).thenReturn(newsubPageValueMap);
        when(newsubPageValueMap.get(HertzConstants.JCR_CQ_TEMPLATE,
                String.class)).thenReturn(
                        HertzConstants.OFFER_CATEGORY_TEMPLATE_PATH);
        Resource newsubJCRResource = Mockito.mock(Resource.class);
        when(newsubResource.getChild(HertzConstants.JCR_CONTENT))
                .thenReturn(newsubJCRResource);

        ValueMap newsubJCRcomponentProperties = Mockito.mock(ValueMap.class);
        when(newsubJCRResource.getValueMap())
                .thenReturn(newsubJCRcomponentProperties);
        when(newsubJCRcomponentProperties.containsKey(HertzConstants.HEADLINE))
                .thenReturn(true);
        when(newsubJCRcomponentProperties.get(HertzConstants.HEADLINE))
                .thenReturn(HertzConstants.HEADLINE);
        when(newsubJCRcomponentProperties.containsKey(HertzConstants.SUBHEAD))
                .thenReturn(true);
        when(newsubJCRcomponentProperties.get(HertzConstants.SUBHEAD))
                .thenReturn(HertzConstants.SUBHEAD);

        when(newsubJCRResource.hasChildren()).thenReturn(true);
        when(newsubJCRResource.getChild(HertzConstants.IMAGE))
                .thenReturn(fileImageResource);
        when(newsubJCRResource.getChild(HertzConstants.BADGE))
                .thenReturn(fileBadgeResource);
        when(newsubJCRResource.getChild(HertzConstants.LOGO))
                .thenReturn(fileLogoResource);

        when(newsubJCRcomponentProperties.containsKey(HertzConstants.CTA_LABEL))
                .thenReturn(true);
        when(newsubJCRcomponentProperties.get(HertzConstants.CTA_LABEL))
                .thenReturn(HertzConstants.CTA_LABEL);
        when(newsubJCRcomponentProperties
                .containsKey(HertzConstants.CTA_ACTION)).thenReturn(true);
        when(newsubJCRcomponentProperties.get(HertzConstants.CTA_ACTION))
                .thenReturn(HertzConstants.CTA_ACTION);
        when(resource.getPath()).thenReturn("/account/rentals");
        when(HertzUtils.initGsonBuilder(true, true, true)).thenReturn(new Gson());
        categoryListingUse.activate();
        CategoryListingBean offerCategoryBean = new CategoryListingBean();
        offerCategoryBean.setName("test");
        offerCategoryBean.setRank("test");
        OfferCategoryBean offerCategoryBean1 = new OfferCategoryBean();
        offerCategoryBean.setSubCategory(offerCategoryBean1);
        offerCategoryBean.setAttributes(offerCategoryBean1);
        assertNotNull(offerCategoryBean.getName());
        assertNotNull(offerCategoryBean.getRank());
        assertNotNull(offerCategoryBean.getSubCategory());
        assertNotNull(offerCategoryBean.getAttributes());
        when(HertzUtils.getIncludedComponentsAsBeans(requestPathInfo,resource, new String[]{"hero"}, resourceResolver, scriptHelper)).thenReturn(new HashMap<>());
        categoryListingUse.activate();
        when(currentPage.getContentResource(HertzConstants.CONFIGTEXT_PARSYS)).thenReturn(null);
        categoryListingUse.activate();
    }

    @Test
    public final void testSetJsonString() throws Exception {
        Mockito.doCallRealMethod().when(categoryListingUse)
                .setJsonString(Mockito.anyString());
        Mockito.doCallRealMethod().when(categoryListingUse).getJsonString();
        categoryListingUse.setJsonString("Test");
        assertNotNull(categoryListingUse.getJsonString());
    }
}
