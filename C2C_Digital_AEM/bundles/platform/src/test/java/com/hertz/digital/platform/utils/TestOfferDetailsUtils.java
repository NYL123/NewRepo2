package com.hertz.digital.platform.utils;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.commons.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.asset.api.Asset;
import com.adobe.granite.asset.api.Rendition;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.bean.CategoryListingBean;
import com.hertz.digital.platform.bean.OfferDetailsBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.constants.TestConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.service.api.JCRService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoggerFactory.class,HertzUtils.class})
public class TestOfferDetailsUtils {

    Logger log;

    @Mock
    Resource resource;

    @Mock
    ValueMap map;

    @Mock
    ValueMap imageMap;

    @Mock
    Page subPage;

    @Mock
    Page childPage;

    @Mock
    Resource subJCRResource;

    @Mock
    Resource childResource;

    @Mock
    Resource childJCRResource;

    @Mock
    ValueMap subPageProperties;

    @Mock
    ValueMap childResourceProperties;

    @Mock
    ValueMap childPageProperties;

    @Mock
    ValueMap componentProperties;

    @Mock
    Resource imageResource;

    @Mock
    Resource subResource;

    @Mock
    Resource logoResource;

    @Mock
    Resource badgeResource;

    @Mock
    Iterable<Resource> iterable;

    @Mock
    Iterator<Resource> iterator;

    @Mock
    Iterable<Resource> iterable1;

    @Mock
    Iterator<Resource> iterator1;

    @Mock
    Resource fileReferenceResource;

    @Mock
    OfferDetailsBean offerDetails;

    @Mock
    ResourceResolver resolver;

    @Mock
    Rendition rendition;

    @Mock
    Resource parentResource;

    @Mock
    Asset asset;
    
    @Mock
    HertzConfigFactory hFactory;

    @Before
    public final void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LoggerFactory.class);
        log = Mockito.mock(Logger.class);
        Field field = OfferUtils.class.getDeclaredField("logger");
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, log);
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
        PowerMockito.mockStatic(HertzUtils.class);
    	PowerMockito.when(HertzUtils.getServiceReference(HertzConfigFactory.class)).thenReturn(hFactory);
    }

    @Test
    public void testSetOfferDetails() throws JSONException {
        String array[] = new String[] { "category1" };
        String partner[] = new String[] {
                "{'partnerName':'Microsoft','partnerProgramCdpCode':'252'}" };
        when(resource.getValueMap()).thenReturn(map);
        when(map.containsKey(HertzConstants.CATEGORY_PATH)).thenReturn(true);
        when(map.get(HertzConstants.CATEGORY_PATH, String[].class))
                .thenReturn(array);
        when(resource.hasChildren()).thenReturn(true);
        when(resource.getChild(HertzConstants.IMAGE)).thenReturn(imageResource);
        when(resource.getChild(HertzConstants.LOGO)).thenReturn(imageResource);
        when(resource.getChild(HertzConstants.BADGE)).thenReturn(imageResource);
        when(imageResource.getValueMap()).thenReturn(imageMap);
        when(imageMap.get("fileReference", String.class))
                .thenReturn(TestConstants.FILE_REFERENCE_PATH);
        when(imageResource.getResourceResolver()).thenReturn(resolver);
        when(imageResource.getPath()).thenReturn(TestConstants.IMAGE_PATH);
        when(resolver.getResource(TestConstants.IMAGE_PATH))
                .thenReturn(fileReferenceResource);
        when(fileReferenceResource.adaptTo(Asset.class)).thenReturn(asset);
        when(asset.getRendition(Mockito.anyString())).thenReturn(rendition);
        when(resource.getParent()).thenReturn(parentResource);
        when(parentResource.getName()).thenReturn("partner");
        when(map.containsKey(HertzConstants.WIDGET_TO_DISPLAY))
                .thenReturn(true);
        when(map.get(HertzConstants.WIDGET_TO_DISPLAY, String.class))
                .thenReturn("Partner Program Lookup");
        when(map.get(HertzConstants.CONFIGURABLE_PARTNER_VALUES))
                .thenReturn(partner);
        when(map.containsKey(HertzConstants.PC_CODE)).thenReturn(true);
        when(map.containsKey(HertzConstants.CDP_CODE)).thenReturn(true);
        when(map.containsKey(HertzConstants.HEADLINE)).thenReturn(true);
        when(map.containsKey(HertzConstants.SUBHEAD)).thenReturn(true);
        when(map.containsKey(HertzConstants.CTA_LABEL_OFFER)).thenReturn(true);
        when(map.get(HertzConstants.PC_CODE, String.class))
                .thenReturn("pc_CODE");
        when(map.get(HertzConstants.CDP_CODE, String.class))
                .thenReturn("cdpcode");
        when(map.get(HertzConstants.HEADLINE, String.class))
                .thenReturn("headline");
        when(map.get(HertzConstants.SUBHEAD, String.class))
                .thenReturn("subhead");
        when(map.get(HertzConstants.CTA_LABEL_OFFER, String.class))
                .thenReturn("cta label");
        when(map.containsKey(HertzConstants.POS_COUNTRIES)).thenReturn(true);
        when(map.containsKey(HertzConstants.CHANNEL)).thenReturn(true);
        when(map.containsKey(HertzConstants.CP_CODE)).thenReturn(true);
        when(map.containsKey(HertzConstants.INDEX_PAGE_START_DATE))
                .thenReturn(true);
        when(map.containsKey(HertzConstants.INDEX_PAGE_EXPIRY_DATE))
                .thenReturn(true);
        when(map.containsKey(HertzConstants.CONTENT_EXPIRY_DATE))
                .thenReturn(true);
        when(map.containsKey(HertzConstants.CDP_RESTRICTION)).thenReturn(true);
        when(map.get(HertzConstants.POS_COUNTRIES, String.class))
                .thenReturn("poscountries");
        when(map.get(HertzConstants.CHANNEL, String.class)).thenReturn("web");
        when(map.get(HertzConstants.CP_CODE, String.class))
                .thenReturn("cpCode");
        when(map.get(HertzConstants.INDEX_PAGE_START_DATE, String.class))
                .thenReturn("2017-10-01T00:00:00.000-04:00");
        when(map.get(HertzConstants.INDEX_PAGE_EXPIRY_DATE, String.class))
                .thenReturn("2017-10-25T00:00:00.000-04:00");
        when(map.get(HertzConstants.CONTENT_EXPIRY_DATE, String.class))
                .thenReturn("2017-11-01T00:00:00.000-04:00");
        when(map.get(HertzConstants.CDP_RESTRICTION, String.class))
                .thenReturn("cdp_restriction");
        when(map.containsKey(HertzConstants.RANK)).thenReturn(true);
        when(map.containsKey(HertzConstants.NEW_BURST_START_DATE))
                .thenReturn(true);
        when(map.containsKey(HertzConstants.NEW_BURST_END_DATE))
                .thenReturn(true);
        when(map.get(HertzConstants.RANK, String.class)).thenReturn("1");
        when(map.get(HertzConstants.NEW_BURST_START_DATE, String.class))
                .thenReturn("2017-10-01T00:00:00.000-04:00");
        when(map.get(HertzConstants.NEW_BURST_END_DATE, String.class))
                .thenReturn("2017-10-25T00:00:00.000-04:00");
        OfferUtils.setOfferDetailsBean(offerDetails, resource);
    }

    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor<OfferUtils> constructor = OfferUtils.class
                .getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public final void getOfferCategoryMap()
            throws RepositoryException, JSONException {

        when(parentResource.getParent()).thenReturn(resource);
        Resource resource = PowerMockito.mock(Resource.class);
        when(resource.getPath()).thenReturn(TestConstants.OFFER_PATH);
        Resource parentResource = PowerMockito.mock(Resource.class);
        when(resource.getParent()).thenReturn(parentResource);
        Resource parentParentResource = PowerMockito.mock(Resource.class);
        when(parentResource.getParent()).thenReturn(parentParentResource);
        when(parentParentResource.getPath())
                .thenReturn(TestConstants.CONTENT_DATA_PATH);
        String array[] = new String[] { "/deals/details/offer1",
                "/deals/details/offer2", "/deals/details/offer2" };
        List<Hit> hits = new ArrayList<Hit>();
        SlingScriptHelper scriptHelper = PowerMockito
                .mock(SlingScriptHelper.class);
        JCRService service = PowerMockito.mock(JCRService.class);
        Hit hit = PowerMockito.mock(Hit.class);
        ValueMap valueMap = PowerMockito.mock(ValueMap.class);
        Resource hitResource = PowerMockito.mock(Resource.class);
        ResourceResolver resolver = PowerMockito.mock(ResourceResolver.class);
        SearchResult searchResults = PowerMockito.mock(SearchResult.class);
        when(scriptHelper.getService(JCRService.class)).thenReturn(service);
        when(service.searchResults(Mockito.eq(resolver),
                Mockito.any(HashMap.class))).thenReturn(searchResults);
        hits.add(hit);
        when(searchResults.getHits()).thenReturn(hits);
        when(hit.getResource()).thenReturn(hitResource);
        when(hitResource.getValueMap()).thenReturn(valueMap);
        when(valueMap.containsKey(HertzConstants.CATEGORY_PATH))
                .thenReturn(true);
        when(valueMap.get(HertzConstants.CATEGORY_PATH, String[].class))
                .thenReturn(array);
        String partner[] = new String[] {
                "{'partnerName':'Microsoft','partnerProgramCdpCode':'252'}" };
        when(hitResource.getValueMap()).thenReturn(map);
        when(map.containsKey(HertzConstants.CATEGORY_PATH)).thenReturn(true);
        when(map.get(HertzConstants.CATEGORY_PATH, String[].class))
                .thenReturn(array);
        when(hitResource.hasChildren()).thenReturn(true);
        when(hitResource.getChild(HertzConstants.IMAGE))
                .thenReturn(imageResource);
        when(hitResource.getChild(HertzConstants.LOGO))
                .thenReturn(imageResource);
        when(hitResource.getChild(HertzConstants.BADGE))
                .thenReturn(imageResource);
        when(imageResource.getValueMap()).thenReturn(imageMap);
        when(imageMap.get("fileReference", String.class))
                .thenReturn(TestConstants.FILE_REFERENCE_PATH);
        when(imageResource.getResourceResolver()).thenReturn(resolver);
        when(imageResource.getPath()).thenReturn(TestConstants.IMAGE_PATH);
        when(resolver.getResource(TestConstants.IMAGE_PATH))
                .thenReturn(fileReferenceResource);
        when(fileReferenceResource.adaptTo(Asset.class)).thenReturn(asset);
        when(asset.getRendition(Mockito.anyString())).thenReturn(rendition);
        when(hitResource.getParent()).thenReturn(parentResource);
        when(parentResource.getName()).thenReturn("partner");
        when(map.containsKey(HertzConstants.WIDGET_TO_DISPLAY))
                .thenReturn(true);
        when(map.get(HertzConstants.WIDGET_TO_DISPLAY, String.class))
                .thenReturn("Partner Program Lookup");
        when(map.get(HertzConstants.CONFIGURABLE_PARTNER_VALUES))
                .thenReturn(partner);
        when(map.containsKey(HertzConstants.PC_CODE)).thenReturn(true);
        when(map.containsKey(HertzConstants.CDP_CODE)).thenReturn(true);
        when(map.containsKey(HertzConstants.HEADLINE)).thenReturn(true);
        when(map.containsKey(HertzConstants.SUBHEAD)).thenReturn(true);
        when(map.containsKey(HertzConstants.CTA_LABEL_OFFER)).thenReturn(true);
        when(map.get(HertzConstants.PC_CODE, String.class))
                .thenReturn("pc_CODE");
        when(map.get(HertzConstants.CDP_CODE, String.class))
                .thenReturn("cdpcode");
        when(map.get(HertzConstants.HEADLINE, String.class))
                .thenReturn("headline");
        when(map.get(HertzConstants.SUBHEAD, String.class))
                .thenReturn("subhead");
        when(map.get(HertzConstants.CTA_LABEL_OFFER, String.class))
                .thenReturn("cta label");
        when(map.containsKey(HertzConstants.POS_COUNTRIES)).thenReturn(true);
        when(map.containsKey(HertzConstants.CHANNEL)).thenReturn(true);
        when(map.containsKey(HertzConstants.CP_CODE)).thenReturn(true);
        when(map.containsKey(HertzConstants.INDEX_PAGE_START_DATE))
                .thenReturn(true);
        when(map.containsKey(HertzConstants.INDEX_PAGE_EXPIRY_DATE))
                .thenReturn(true);
        when(map.containsKey(HertzConstants.CONTENT_EXPIRY_DATE))
                .thenReturn(true);
        when(map.containsKey(HertzConstants.CDP_RESTRICTION)).thenReturn(true);
        when(map.get(HertzConstants.POS_COUNTRIES, String.class))
                .thenReturn("poscountries");
        when(map.get(HertzConstants.CHANNEL, String.class)).thenReturn("web");
        when(map.get(HertzConstants.CP_CODE, String.class))
                .thenReturn("cpCode");
        when(map.get(HertzConstants.INDEX_PAGE_START_DATE, String.class))
                .thenReturn("2017-10-01T00:00:00.000-04:00");
        when(map.get(HertzConstants.INDEX_PAGE_EXPIRY_DATE, String.class))
                .thenReturn("2017-10-25T00:00:00.000-04:00");
        when(map.get(HertzConstants.CONTENT_EXPIRY_DATE, String.class))
                .thenReturn("2017-11-01T00:00:00.000-04:00");
        when(map.get(HertzConstants.CDP_RESTRICTION, String.class))
                .thenReturn("cdp_restriction");
        when(map.containsKey(HertzConstants.RANK)).thenReturn(true);
        when(map.containsKey(HertzConstants.NEW_BURST_START_DATE))
                .thenReturn(true);
        when(map.containsKey(HertzConstants.NEW_BURST_END_DATE))
                .thenReturn(true);
        when(map.get(HertzConstants.RANK, String.class)).thenReturn("1");
        when(map.get(HertzConstants.NEW_BURST_START_DATE, String.class))
                .thenReturn("2017-10-01T00:00:00.000-04:00");
        when(map.get(HertzConstants.NEW_BURST_END_DATE, String.class))
                .thenReturn("2017-10-25T00:00:00.000-04:00");
        OfferUtils.getOfferCategoryMap(resource, scriptHelper, resolver);

    }

    @Test
    public final void testGetOfferListMap()
            throws RepositoryException, JSONException {

        Resource resource = PowerMockito.mock(Resource.class);
        when(resource.getPath()).thenReturn(TestConstants.OFFER_PATH);
        String array[] = new String[] { "/deals/details/offer1",
                "/deals/details/offer2", "/deals/details/offer2" };
        List<Hit> hits = new ArrayList<Hit>();
        SlingScriptHelper scriptHelper = PowerMockito
                .mock(SlingScriptHelper.class);
        JCRService service = PowerMockito.mock(JCRService.class);
        Hit hit = PowerMockito.mock(Hit.class);
        ValueMap valueMap = PowerMockito.mock(ValueMap.class);
        Resource hitResource = PowerMockito.mock(Resource.class);
        ResourceResolver resolver = PowerMockito.mock(ResourceResolver.class);
        SearchResult searchResults = PowerMockito.mock(SearchResult.class);
        when(scriptHelper.getService(JCRService.class)).thenReturn(service);
        when(service.searchResults(Mockito.eq(resolver),
                Mockito.any(HashMap.class))).thenReturn(searchResults);
        hits.add(hit);
        when(searchResults.getHits()).thenReturn(hits);
        when(hit.getResource()).thenReturn(hitResource);
        when(hitResource.getValueMap()).thenReturn(valueMap);
        when(valueMap.containsKey(HertzConstants.CATEGORY_PATH))
                .thenReturn(true);
        when(valueMap.get(HertzConstants.CATEGORY_PATH, String[].class))
                .thenReturn(array);

        String partner[] = new String[] {
                "{'partnerName':'Microsoft','partnerProgramCdpCode':'252'}" };
        when(hitResource.getValueMap()).thenReturn(map);
        when(map.containsKey(HertzConstants.CATEGORY_PATH)).thenReturn(true);
        when(map.get(HertzConstants.CATEGORY_PATH, String[].class))
                .thenReturn(array);
        when(hitResource.hasChildren()).thenReturn(true);
        when(hitResource.getChild(HertzConstants.IMAGE))
                .thenReturn(imageResource);
        when(hitResource.getChild(HertzConstants.LOGO))
                .thenReturn(imageResource);
        when(hitResource.getChild(HertzConstants.BADGE))
                .thenReturn(imageResource);
        when(imageResource.getValueMap()).thenReturn(imageMap);
        when(imageMap.get("fileReference", String.class))
                .thenReturn(TestConstants.FILE_REFERENCE_PATH);
        when(imageResource.getResourceResolver()).thenReturn(resolver);
        when(imageResource.getPath()).thenReturn(TestConstants.IMAGE_PATH);
        when(resolver.getResource(TestConstants.IMAGE_PATH))
                .thenReturn(fileReferenceResource);
        when(fileReferenceResource.adaptTo(Asset.class)).thenReturn(asset);
        when(asset.getRendition(Mockito.anyString())).thenReturn(rendition);
        when(hitResource.getParent()).thenReturn(parentResource);
        when(parentResource.getName()).thenReturn("partner");
        when(map.containsKey(HertzConstants.WIDGET_TO_DISPLAY))
                .thenReturn(true);
        when(map.get(HertzConstants.WIDGET_TO_DISPLAY, String.class))
                .thenReturn("Partner Program Lookup");
        when(map.get(HertzConstants.CONFIGURABLE_PARTNER_VALUES))
                .thenReturn(partner);
        when(map.containsKey(HertzConstants.PC_CODE)).thenReturn(true);
        when(map.containsKey(HertzConstants.CDP_CODE)).thenReturn(true);
        when(map.containsKey(HertzConstants.HEADLINE)).thenReturn(true);
        when(map.containsKey(HertzConstants.SUBHEAD)).thenReturn(true);
        when(map.containsKey(HertzConstants.CTA_LABEL_OFFER)).thenReturn(true);
        when(map.get(HertzConstants.PC_CODE, String.class))
                .thenReturn("pc_CODE");
        when(map.get(HertzConstants.CDP_CODE, String.class))
                .thenReturn("cdpcode");
        when(map.get(HertzConstants.HEADLINE, String.class))
                .thenReturn("headline");
        when(map.get(HertzConstants.SUBHEAD, String.class))
                .thenReturn("subhead");
        when(map.get(HertzConstants.CTA_LABEL_OFFER, String.class))
                .thenReturn("cta label");
        when(map.containsKey(HertzConstants.POS_COUNTRIES)).thenReturn(true);
        when(map.containsKey(HertzConstants.CHANNEL)).thenReturn(true);
        when(map.containsKey(HertzConstants.CP_CODE)).thenReturn(true);
        when(map.containsKey(HertzConstants.INDEX_PAGE_START_DATE))
                .thenReturn(true);
        when(map.containsKey(HertzConstants.INDEX_PAGE_EXPIRY_DATE))
                .thenReturn(true);
        when(map.containsKey(HertzConstants.CONTENT_EXPIRY_DATE))
                .thenReturn(true);
        when(map.containsKey(HertzConstants.CDP_RESTRICTION)).thenReturn(true);
        when(map.get(HertzConstants.POS_COUNTRIES, String.class))
                .thenReturn("poscountries");
        when(map.get(HertzConstants.CHANNEL, String.class)).thenReturn("web");
        when(map.get(HertzConstants.CP_CODE, String.class))
                .thenReturn("cpCode");
        when(map.get(HertzConstants.INDEX_PAGE_START_DATE, String.class))
                .thenReturn("2017-10-01T00:00:00.000-04:00");
        when(map.get(HertzConstants.INDEX_PAGE_EXPIRY_DATE, String.class))
                .thenReturn("2017-10-25T00:00:00.000-04:00");
        when(map.get(HertzConstants.CONTENT_EXPIRY_DATE, String.class))
                .thenReturn("2017-11-01T00:00:00.000-04:00");
        when(map.get(HertzConstants.CDP_RESTRICTION, String.class))
                .thenReturn("cdp_restriction");
        when(map.containsKey(HertzConstants.RANK)).thenReturn(true);
        when(map.containsKey(HertzConstants.NEW_BURST_START_DATE))
                .thenReturn(true);
        when(map.containsKey(HertzConstants.NEW_BURST_END_DATE))
                .thenReturn(true);
        when(map.get(HertzConstants.RANK, String.class)).thenReturn("1");
        when(map.get(HertzConstants.NEW_BURST_START_DATE, String.class))
                .thenReturn("2017-10-01T00:00:00.000-04:00");
        when(map.get(HertzConstants.NEW_BURST_END_DATE, String.class))
                .thenReturn("2017-10-25T00:00:00.000-04:00");
        OfferUtils.getOfferListMap(resource, scriptHelper, resolver);
    }

    @Test
    public void testGetAllOfferListingProperties() throws Exception {
        List<CategoryListingBean> categoryListingList = new ArrayList<>();
        List<OfferDetailsBean> offers = new ArrayList<>();
        Map<String, List<OfferDetailsBean>> map = new HashMap<>();
        Boolean fromOfferListingPage = false;
        when(resource.getParent()).thenReturn(parentResource);
        when(parentResource.getChildren()).thenReturn(iterable);
        when(iterable.iterator()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true, false);
        when(iterator.next()).thenReturn(subResource);
        when(subResource.getName()).thenReturn("subResource");
        when(subResource.adaptTo(Page.class)).thenReturn(subPage);
        when(subPage.getProperties()).thenReturn(subPageProperties);
        when(subPageProperties.get(HertzConstants.JCR_CQ_TEMPLATE,
                String.class)).thenReturn(
                        HertzConstants.OFFER_CATEGORY_TEMPLATE_PATH);
        when(subResource.getChild(HertzConstants.JCR_CONTENT))
                .thenReturn(subJCRResource);
        when(subJCRResource.hasChildren()).thenReturn(true);
        when(subJCRResource.getValueMap()).thenReturn(componentProperties);
        when(subResource.getChildren()).thenReturn(iterable1);
        when(iterable1.iterator()).thenReturn(iterator1);
        when(iterator1.hasNext()).thenReturn(true, false);
        when(iterator1.next()).thenReturn(childResource);
        when(childResource.getName()).thenReturn("childResource");
        when(childResource.adaptTo(Page.class)).thenReturn(childPage);
        when(childPage.getProperties()).thenReturn(childPageProperties);
        when(childPageProperties.get(HertzConstants.JCR_CQ_TEMPLATE,
                String.class)).thenReturn(
                        HertzConstants.OFFER_CATEGORY_TEMPLATE_PATH);
        when(childResource.getChild(HertzConstants.JCR_CONTENT))
                .thenReturn(childJCRResource);
        when(childJCRResource.getValueMap())
                .thenReturn(childResourceProperties);
        when(childResourceProperties.containsKey(HertzConstants.CTA_LABEL))
                .thenReturn(true);
        when(childResourceProperties.get(HertzConstants.CTA_LABEL,
                String.class)).thenReturn("ctaLabel");
        when(childResourceProperties.containsKey(HertzConstants.CTA_ACTION))
                .thenReturn(true);
        when(childResourceProperties.get(HertzConstants.CTA_ACTION,
                String.class)).thenReturn("ctaAction");
        when(childResourceProperties.containsKey(HertzConstants.HEADLINE))
                .thenReturn(true);
        when(childResourceProperties.get(HertzConstants.HEADLINE, String.class))
                .thenReturn("headline");
        when(childResourceProperties.containsKey(HertzConstants.SUBHEAD))
                .thenReturn(true);
        when(childResourceProperties.get(HertzConstants.SUBHEAD, String.class))
                .thenReturn("subhead");

        OfferUtils.getAllOfferListingProperties(resource, categoryListingList,
                fromOfferListingPage, map);
    }
}
