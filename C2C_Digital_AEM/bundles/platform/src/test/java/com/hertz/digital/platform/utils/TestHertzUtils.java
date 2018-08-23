package com.hertz.digital.platform.utils;

import static org.junit.Assert.assertTrue;

import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.engine.SlingRequestProcessor;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.apache.sling.settings.SlingSettingsService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.asset.api.Asset;
import com.adobe.granite.asset.api.Rendition;
import com.day.cq.commons.Externalizer;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.contentsync.handler.util.RequestResponseFactory;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.replication.ReplicationStatus;
import com.day.cq.replication.Replicator;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.WCMMode;
import com.day.cq.wcm.api.components.Component;
import com.day.cq.wcm.commons.WCMUtils;
import com.google.gson.JsonElement;
import com.hertz.digital.platform.bean.HeroImageBean;
import com.hertz.digital.platform.bean.spa.SpaPageBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.constants.TestConstants;
import com.hertz.digital.platform.exporter.api.ComponentExporterService;
import com.hertz.digital.platform.exporter.impl.FixedContentSlotExporterImpl;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.service.api.JCRService;
import com.hertz.digital.platform.service.api.SystemUserService;

import junitx.framework.AssertionFailedError;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoggerFactory.class, WCMUtils.class, JcrUtils.class,
        WCMMode.class, FrameworkUtil.class, HttpUtils.class,
        ResourceUtil.class})
public class TestHertzUtils {
    /**
     * Constant for header footer topnav path
     */
    String URL_PATH = "/content/hertz-rac/rac-web/us/en/header-footer/default/header/topnav";
    /**
     * Constant for spa path
     */
    String PATH = "/content/hertz-rac/rac-web/us/en/spa";
    /**
     * Constant for header footer default path
     */
    String HEADER_FOOTER_DEFAULT_PATH = "/content/hertz-rac/rac-web/us/en/header-footer/default";
    /**
     * Constant for reservation config path
     */
    String RESERVATION_CONFIG_PATH = "/content/hertz-rac/rac-web/data/reservationconfig/en/reservations";
    /**
     * Constant for home jcr content node path
     */
    String HOME_JCR_CONTENT_PATH = "/content/hertz-rac/rac-web/us/en/home/jcr:content";
    /**
     * Constant for home path
     */
    String HOME_PATH = "/content/hertz-rac/rac-web/us/en/home";
    /**
     * Constant for locale en path
     */
    String LOCALE_PATH = "hertz-rac/rac-web/us/en";
    /**
     * Constant for data path
     */
    String CONTENT_DATA_PATH = "/content/hertz-rac/data";
    /**
     * Constant for mobile data path
     */
    String CONTENT_MOBILE_DATA_PATH = "/content/hertz-rac/rac-mobile/data";
    /**
     * Constant for location path
     */
    String LOCATION_PATH = "/content/hertz-rac/data/location/in/chicago/ORD1";

    /**
     * Constant for partner page path
     */
    String PARTNER_PAGE_PATH = "/content/hertz-rac/rac-web/us/en/header-footer/default";

    /**
     * Constant for source country page path
     */
    String SOURCE_COUNTRY_PAGE_PATH = "/content/hertz-rac/data/en/termsconditions-config/us";

    /**
     * Constant for destination country page path
     */

    String DESTINATION_COUNTRY_PAGE_PATH = "/content/hertz-rac/data/en/termsconditions-config/us/us";

    /**
     * Constant for rate type page path
     */
    String RATE_TYPE_PAGE_PATH = "/content/hertz-rac/data/en/termsconditions-config/us/us/prepaid";

    /**
     * Constant for data page template path
     */
    String DATA_TEMPLATE_PATH = "/apps/hertz/templates/data";

    /**
     * constant for default-rq-code path
     */
    String DEFAULT_RQ_CODE_PATH = "/content/hertz-rac/data/rates-config/default-rq-codes";

    /**
     * constant for offer details page
     */
    String OFFER_PATH = "/content/hertz-rac/rac-web/en-us/deals/details/offer1";

    /**
     * constant for offer details page
     */
    String OFFER_LANDING_PATH = "/content/hertz-rac/rac-web/en-us/deals";

    /**
     * constant for marketing slot 1 path
     */
    String MARKETING_SLOT_1_PATH = "/content/hertz-rac/rac-web/en-us/deals/details/offer1/jcr:content/marketingslot1";

    /**
     * constant for marketing slot 2 path
     */
    String MARKETING_SLOT_2_PATH = "/content/hertz-rac/rac-web/en-us/deals/details/offer1/jcr:content/marketingslot2";

    /**
     * constant for image path
     */
    String FILE_REFERENCE_PATH = "/content/dam/hertz-rac/dam-workflow-test-folder/Hawaiiv2.jpg";

    /** constant for image path */
    String IMAGE_PATH = "/content/hertz-rac/rac-web/en-us/deals/details/offer1/jcr:content/image";

    /**
     * constant for partner landing page path
     */
    String PARTNER_LANDING_PATH = "/content/hertz-rac/rac-web/en-us/partners";

    /**
     * constant for partner detail page
     */
    String PARTNER_DETAIL_PAGE = "/content/hertz-rac/rac-web/en-us/partners/air/air-china";
    
    /**
     * constant for partner home page
     */
    String PARTNER_HOME_PAGE = "/content/hertz-rac/rac-web/en-us/partnerhome";

    String IMAGE_DAM_PATH = "/content/dam/hertz-rac/rentalrewards/img_FPO_rewards_golds@2x.png";
    @Mock
    ValueMap map;

    @Mock
    ReplicationStatus status;

    @Mock
    JSONObject object;

    @Mock
    Page page;

    @Mock
    Page parentPage;

    @Mock
    ResourceResolver resolver;

    @Mock
    Session session;

    @Mock
    JsonElement jsonElement;
    @Mock
    SlingHttpServletResponse response;

    @Mock
    SlingScriptHelper scriptHelper;
    
    @Mock
    RequestPathInfo requestPathInfo;

    @Mock
    Iterator<Resource> parChildrenItr;

    @Mock
    NodeIterator nodeItr;
    

    @Mock
    Resource jcrResource;

    @Mock
    Resource jcrContentResource;
    
    @Mock
    Resource resource;
    
    @Mock
	SearchResult result;
    
    @Mock
    Query query;

    @Mock
    Hit hit;

    @Mock
    ValueMap valueMap;
    
    @Mock
    ValueMap headerResProperties;

    @Mock
    Resource childResource;
    
    @Mock
    Resource referenceHeaderResource;

    @Mock
    Resource child;

    String path = "/content/hertz-rac/en-us/home";
    @Mock
    InputStream inputStream;

    @Mock
    Iterator<Resource> mockIterator;

    @Mock
    Iterator<Resource> mockIterator2;

    @Mock
    Resource rapidResource;

    @Mock
    SlingScriptHelper helper;

    @Mock
    QueryBuilder builder;

    @Mock
    Resource parentResource;

    @Mock
    Resource pageParentResource;

    @Mock
    Resource defaultResource;
    
    @Mock
    Resource topNavResource;

    @Mock
    Resource defaultJcrResource;

    @Mock
    Resource imageResource;

    @Mock
    Resource heroImageResource;

    @Mock
    ValueMap imageResourceValueMap;

    @Mock
    Resource pageResource;
    
    @Mock
    Resource childHeaderResource;

    @Mock
    Resource rapidChildResource;

    @Mock
    ValueMap valueMap3;

    @Mock
    ValueMap valueMap2;

    @Mock
    Node jcrNode;

    @Mock
    Node pageJcrNode;

    @Mock
    Property property;

    @Mock
    Replicator replicator;

    @Mock
    private SystemUserService systemService;

    @Mock
    Iterable<Resource> hitResourceIterator;

    @Mock
    Iterable<Resource> resourceIterator;

    @Mock
    Iterator<Resource> mockHitResourceIterator;

    @Mock
    HertzConfigFactory configFactory;

    @Mock
    RequestResponseFactory requestResponseFactory;

    @Mock
    SlingRequestProcessor requestProcessor;

    @Mock
    HttpServletRequest request;

    String componentResourceType = "unitedstates";

    String requestPath = "unitedstates.html";

    String selectors[];
    @Mock
    Logger logger;

    @Mock
    Iterator<Element> elementIterator;
    
    @Mock
    SlingSettingsService slingService;

    List<Hit> mockList;

    @Before
    public final void setup() {
        MockitoAnnotations.initMocks(this);
        selectors = new String[] { "hertz-rac", "rac-web", "us", "en" };
    }

    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor<HertzUtils> constructor = HertzUtils.class
                .getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public final void testGetValueFromMap() {
        String key = "sample-key";
        PowerMockito.when(map.containsKey(key)).thenReturn(true);
        PowerMockito.when(map.get(key, String.class))
                .thenReturn("sample-value");
        String value = HertzUtils.getValueFromMap(map, key);
        Assert.assertTrue(value.equals("sample-value"));
        PowerMockito.when(map.containsKey(key)).thenReturn(false);
        value = HertzUtils.getValueFromMap(map, key);
        Assert.assertTrue(value.equals(StringUtils.EMPTY));
    }

    @Test
    public final void testGetIntegerValueFromMap() {
        String key = "sample-key";
        PowerMockito.when(map.containsKey(key)).thenReturn(true);
        PowerMockito.when(map.get(key, Integer.class)).thenReturn(1);
        Integer value = HertzUtils.getIntegerValueFromMap(map, key);
        Assert.assertTrue(value == 1);
        PowerMockito.when(map.containsKey(key)).thenReturn(false);
        value = HertzUtils.getIntegerValueFromMap(map, key);
        Assert.assertTrue(value == 0);
    }

    @Test
    public final void testGetBooleanValueFromMap() {
        String key = "sample-key";
        PowerMockito.when(map.containsKey(key)).thenReturn(true);
        PowerMockito.when(map.get(key, Boolean.class)).thenReturn(true);
        Boolean value = HertzUtils.getBooleanValueFromMap(map, key);
        Assert.assertTrue(value);
        PowerMockito.when(map.containsKey(key)).thenReturn(false);
        value = HertzUtils.getBooleanValueFromMap(map, key);
        Assert.assertFalse(value);
    }

    @Test
    public final void testGetStringValueFromObject() throws JSONException {
        String key = "sample-key";
        PowerMockito.when(object.optString(key))
                .thenReturn("/content/hertz-rac/rac-web/en-us/sample-value");
        PowerMockito.when(object.getString(key))
                .thenReturn("/content/hertz-rac/rac-web/en-us/sample-value");

        PowerMockito.mockStatic(FrameworkUtil.class);
        PowerMockito.mockStatic(ResourceUtil.class);
        Bundle bundle = PowerMockito.mock(Bundle.class);
        ServiceReference serviceReference = PowerMockito
                .mock(ServiceReference.class);
        BundleContext bundleContext = PowerMockito.mock(BundleContext.class);
        when(FrameworkUtil.getBundle(SystemUserService.class))
                .thenReturn(bundle);
        when(resolver.resolve("/content/hertz-rac/rac-web/en-us/sample-value"))
                .thenReturn(childResource);
        when(ResourceUtil.isNonExistingResource(childResource))
                .thenReturn(false);
        when(childResource.isResourceType(DamConstants.NT_DAM_ASSET))
                .thenReturn(false);
        when(bundle.getBundleContext()).thenReturn(bundleContext);
        SystemUserService systemService = PowerMockito
                .mock(SystemUserService.class);
        when(bundleContext
                .getServiceReference(SystemUserService.class.getName()))
                        .thenReturn(serviceReference);
        when(bundleContext.getService(serviceReference))
                .thenReturn(systemService);
        when(systemService.getServiceResourceResolver()).thenReturn(resolver);
        String value = HertzUtils.getStringValueFromObject(object, key);

        PowerMockito.when(object.optString(key)).thenReturn(null);
        value = HertzUtils.getStringValueFromObject(object, key);
        Assert.assertTrue(value.equals(StringUtils.EMPTY));
        value = HertzUtils.getStringValueFromObject(null, key);
        Assert.assertTrue(value.equals(StringUtils.EMPTY));
    }

    @Test
    public final void testCloseResolverSession() {
        PowerMockito.when(resolver.isLive()).thenReturn(true);
        PowerMockito.when(session.isLive()).thenReturn(true);
        HertzUtils.closeResolverSession(resolver, session);
        PowerMockito.when(resolver.isLive()).thenReturn(false);
        PowerMockito.when(session.isLive()).thenReturn(false);
        HertzUtils.closeResolverSession(resolver, session);
        HertzUtils.closeResolverSession(null, null);
    }

    @Test
    public final void testCreatePath() throws Exception {
        String path = HertzUtils.createPath(selectors);
        Assert.assertTrue(path.equals("hertz-rac/rac-web/us/en"));
    }

    @Test
    public final void testSetResponseParameters() {
        HertzUtils.setResponseParameters(response);
        PowerMockito.when(response.getCharacterEncoding())
                .thenReturn(HertzConstants.UTF8);
        PowerMockito.when(response.getContentType())
                .thenReturn(HertzConstants.APPLICATION_JSON);
        Assert.assertTrue(
                response.getCharacterEncoding().equals(HertzConstants.UTF8));
        Assert.assertTrue(response.getContentType()
                .equals(HertzConstants.APPLICATION_JSON));
    }

    @Test
    public final void testInitGsonBuilder() {
        Assert.assertNotNull(HertzUtils.initGsonBuilder(true, true, true));
    }

    @Test
    public final void testtoArray() {
        String input = "test1,test2,test3,test4";
        Assert.assertNotNull(HertzUtils.toArray(input, ","));
        Assert.assertNull(
                HertzUtils.toArray(StringUtils.EMPTY, StringUtils.EMPTY));
        Assert.assertNull(HertzUtils.toArray(input, StringUtils.EMPTY));
        Assert.assertNull(HertzUtils.toArray(StringUtils.EMPTY, ","));
    }

    @Test
    public final void testGetIncludedComponentsAsBeans() throws JSONException,
            RepositoryException, ServletException, IOException {
    	HashSet<String> set=new HashSet<>();
    	set.add("author");
        String components[] = { "hero" };
        Resource jcrResource = PowerMockito.mock(Resource.class);
        Resource parentResource = PowerMockito.mock(Resource.class);
        HeroImageBean heroBeam = PowerMockito.mock(HeroImageBean.class);
        JCRService jcrService = PowerMockito.mock(JCRService.class);
        SearchResult searchResults = PowerMockito.mock(SearchResult.class);
        Page page = PowerMockito.mock(Page.class);
        org.apache.sling.api.resource.Resource res = PowerMockito
                .mock(org.apache.sling.api.resource.Resource.class);
        ComponentExporterService exporterService1 = PowerMockito
                .mock(ComponentExporterService.class);
        ComponentExporterService exporterService2 = PowerMockito
                .mock(ComponentExporterService.class);
        Resource hitResource = PowerMockito.mock(Resource.class);
        Hit hit = PowerMockito.mock(Hit.class);
        List<Hit> hits = new ArrayList<Hit>();
        hits.add(hit);
        ComponentExporterService[] exporterService = ArrayUtils
                .toArray(exporterService1, exporterService2);
        when(jcrResource.getParent()).thenReturn(parentResource);
        when(jcrResource.getPath()).thenReturn("/content/hertz-rac/en-us/home");
        when(parentResource.adaptTo(Page.class)).thenReturn(page);

        when(jcrResource.listChildren()).thenReturn(parChildrenItr);
        when(parChildrenItr.hasNext()).thenReturn(true, false);
        when(parChildrenItr.next()).thenReturn(child);
        when(child.getValueMap()).thenReturn(valueMap);
        when(child.getName()).thenReturn("exportHeroAsBean");
        when(page.getContentResource("exportHeroAsBean")).thenReturn(res);
        when(exporterService[0].exportAsBean(
                page.getContentResource("exportHeroAsBean"), resolver))
                        .thenReturn(heroBeam);
        when((String) valueMap.get(HertzConstants.SLING_RESOURCE_TYPE))
                .thenReturn("hertz/components/content/hero");
        when(scriptHelper.getServices(ComponentExporterService.class,
                "(identifier=hero)")).thenReturn(exporterService);

        when(scriptHelper.getService(RequestResponseFactory.class))
                .thenReturn(requestResponseFactory);
        when(scriptHelper.getService(SlingRequestProcessor.class))
                .thenReturn(requestProcessor);
        when(scriptHelper.getService(JCRService.class)).thenReturn(jcrService);
        when(scriptHelper.getService(HertzConfigFactory.class))
                .thenReturn(configFactory);
        when(configFactory
                .getPropertyValue(HertzConstants.Hertz_SPA_ALLOWED_COMPONENTS))
                        .thenReturn(new String[] { "richtextpair" });
        when(jcrService.searchResults(Mockito.eq(resolver),
                Mockito.any(HashMap.class))).thenReturn(searchResults);
        when(searchResults.getHits()).thenReturn(hits);
        when(hit.getResource()).thenReturn(hitResource);
        when(hitResource.getPath()).thenReturn("Test/path");
        when(hitResource.getResourceType())
                .thenReturn("/apps/hertz/components/content/linkpair");
        when(hitResource.getChildren()).thenReturn(hitResourceIterator);
        when(hitResourceIterator.iterator())
                .thenReturn(mockHitResourceIterator);
        when(hitResource.getName()).thenReturn("hero");
        when(requestResponseFactory.createRequest("GET", "Test/path" + ".html"))
                .thenReturn(request);

        when(jcrResource.getChild(HertzConstants.CONFIGTEXT_PARSYS))
                .thenReturn(childResource);
        when(childResource.getChildren()).thenReturn(resourceIterator);
        when(resourceIterator.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true, false);
        when(mockIterator.next()).thenReturn(rapidResource);
        when(rapidResource.getResourceType())
                .thenReturn("/apps/hertz/components/content/linkpair");
        when(rapidResource.getPath())
                .thenReturn("/apps/hertz/components/content/linkpair");

        PowerMockito.mockStatic(WCMMode.class);

        HttpServletRequest request = PowerMockito
                .mock(HttpServletRequest.class);
        when(requestResponseFactory.createRequest("GET",
                "/apps/hertz/components/content/linkpair" + ".html"))
                        .thenReturn(request);
        
        String[] selectors = {"spa"};
		when(requestPathInfo.getSelectors()).thenReturn(selectors);
		when(scriptHelper.getService(SlingSettingsService.class)).thenReturn(slingService);
		when(slingService.getRunModes()).thenReturn(set);
		
        Assert.assertNotNull(HertzUtils.getIncludedComponentsAsBeans(
        		requestPathInfo,jcrResource, components, resolver, scriptHelper));

    }

    @Test
    public final void testGetStaticIncludedComponentsAsBean()
            throws JSONException, RepositoryException {

        String components[] = { "hero" };

        Resource jcrResource = PowerMockito.mock(Resource.class);
        Resource parentResource = PowerMockito.mock(Resource.class);
        Page page = PowerMockito.mock(Page.class);
        ComponentExporterService exporterService1 = PowerMockito
                .mock(ComponentExporterService.class);
        ComponentExporterService exporterService2 = PowerMockito
                .mock(ComponentExporterService.class);
        ComponentExporterService[] exporterService = ArrayUtils
                .toArray(exporterService1, exporterService2);
        when(jcrResource.getParent()).thenReturn(parentResource);
        when(jcrResource.getPath()).thenReturn("/content/hertz-rac/en-us/home");
        when(parentResource.adaptTo(Page.class)).thenReturn(page);
        when(scriptHelper.getServices(ComponentExporterService.class,
                "(identifier=hero)")).thenReturn(exporterService);
        HeroImageBean heroBeam = PowerMockito.mock(HeroImageBean.class);
        when(exporterService[0].exportAsBean(
                page.getContentResource("exportHeroAsBean"), resolver))
                        .thenReturn(heroBeam);
        Assert.assertNotNull(HertzUtils.getStaticIncludedComponentsAsBean(
                jcrResource, components, resolver, scriptHelper));
    }

    @Test
    public final void testGetServiceReference() {
        PowerMockito.mockStatic(FrameworkUtil.class);
        Bundle bundle = PowerMockito.mock(Bundle.class);
        ServiceReference serviceReference = PowerMockito
                .mock(ServiceReference.class);
        BundleContext bundleContext = PowerMockito.mock(BundleContext.class);
        when(FrameworkUtil.getBundle(RequestResponseFactory.class))
                .thenReturn(bundle);
        when(bundle.getBundleContext()).thenReturn(bundleContext);
        when(bundleContext
                .getServiceReference(RequestResponseFactory.class.getName()))
                        .thenReturn(serviceReference);
        when(bundleContext.getService(serviceReference))
                .thenReturn(RequestResponseFactory.class);

        Assert.assertNotNull(
                HertzUtils.getServiceReference(RequestResponseFactory.class));
    }

    @Test
    public final void testUpdateAccessToken() {
        PowerMockito.mockStatic(JcrUtils.class);
        PowerMockito.mockStatic(HttpUtils.class);
        try {
            when(JcrUtils.getOrCreateByPath("/var/hertz/token/pwd_grant_token",
                    JcrResourceConstants.NT_SLING_FOLDER,
                    JcrConstants.NT_UNSTRUCTURED, session, true))
                            .thenReturn(jcrNode);
            when(jcrNode.hasProperty(HertzConstants.VALUE)).thenReturn(true);
            when(jcrNode.getProperty(HertzConstants.VALUE))
                    .thenReturn(property);
            when(HttpUtils.post(
                    "/content/hertz-rac/data,/content/rac-mobile/data/api/token",
                    "grant_type=client_credentials",
                    "application/x-www-form-urlencoded", false,
                    null)).thenReturn(
                            "{'accessToken':'acdDfegerfsfg1234','tokenType':'abc','expiresIn':'abc','scope':'global','jti':'abc'}");
            Assert.assertNotNull(HertzUtils.updateAccessToken(
                    "/var/hertz/token", "pwd_grant_token",
                    "/content/hertz-rac/data,/content/rac-mobile/data/api/token",
                    session));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }

    }

    @Test
    public final void testGetOrCreateAccessToken() {
        PowerMockito.mockStatic(JcrUtils.class);
        PowerMockito.mockStatic(HttpUtils.class);
        try {
            when(JcrUtils.getOrCreateByPath("/var/hertz/token/pwd_grant_token",
                    JcrResourceConstants.NT_SLING_FOLDER,
                    JcrConstants.NT_UNSTRUCTURED, session, true))
                            .thenReturn(jcrNode);
            when(jcrNode.hasProperty(HertzConstants.VALUE)).thenReturn(false);
            when(jcrNode.getProperty(HertzConstants.VALUE))
                    .thenReturn(property);
            when(property.getString()).thenReturn("test");
            when(HttpUtils.post(
                    "/content/hertz-rac/data,/content/rac-mobile/data/api/token",
                    "grant_type=client_credentials",
                    "application/x-www-form-urlencoded", false,
                    null)).thenReturn(
                            "{'accessToken':'acdDfegerfsfg1234','tokenType':'abc','expiresIn':'abc','scope':'global','jti':'abc'}");
            Assert.assertNotNull(HertzUtils.getOrCreateAccessToken(
                    "/var/hertz/token", "pwd_grant_token",
                    "/content/hertz-rac/data,/content/rac-mobile/data/api/token",
                    session));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }

    }

    @Test
    public final void testgetAllComponentHTMLUnderPar()
            throws RepositoryException, JSONException, ServletException,
            IOException {
        // get JCR Service
        JCRService jcrService = PowerMockito.mock(JCRService.class);
        when(scriptHelper.getService(JCRService.class)).thenReturn(jcrService);
        when(scriptHelper.getService(HertzConfigFactory.class))
                .thenReturn(configFactory);
        when(configFactory
                .getPropertyValue(HertzConstants.Hertz_SPA_ALLOWED_COMPONENTS))
                        .thenReturn(new String[] { "richtextpair", "linkpair",
                                "simplepairtext" });
        // Get Externalizer
        Externalizer externalizer = PowerMockito.mock(Externalizer.class);
        when(scriptHelper.getService(Externalizer.class))
                .thenReturn(externalizer);
        SearchResult searchResults = PowerMockito.mock(SearchResult.class);
        when(jcrService.searchResults(Mockito.eq(resolver),
                Mockito.any(HashMap.class))).thenReturn(searchResults);
        Hit hit = PowerMockito.mock(Hit.class);
        Hit hit1 = PowerMockito.mock(Hit.class);
        List<Hit> hits = new ArrayList<Hit>();
        hits.add(hit);
        // hits.add(hit1);
        when(searchResults.getHits()).thenReturn(hits);
        when(hit.getPath()).thenReturn("Test/path");
        Resource hitResource = PowerMockito.mock(Resource.class);
        when(hit.getResource()).thenReturn(hitResource);
        // Iterable<Resource> iterableComponents = hitResource.getChildren();
        when(hitResource.getResourceType())
                .thenReturn("/apps/hertz/components/content/linkpair");
        when(hitResource.getChildren()).thenReturn(hitResourceIterator);
        when(hitResourceIterator.iterator())
                .thenReturn(mockHitResourceIterator);
        Resource resource1 = PowerMockito.mock(Resource.class);
        when(resolver.getResource(Mockito.anyString())).thenReturn(resource1);
        PowerMockito.when(resource1.listChildren()).thenReturn(parChildrenItr);
        PowerMockito.when(parChildrenItr.hasNext()).thenReturn(true, false);
        Resource child = PowerMockito.mock(Resource.class);
        when(parChildrenItr.next()).thenReturn(child);
        PowerMockito.mockStatic(WCMUtils.class);
        Component component = PowerMockito.mock(Component.class);
        when(WCMUtils.getComponent(child)).thenReturn(component);
        when(component.getName()).thenReturn("TEST");
        Map<String, Object> beanMap = new LinkedHashMap<>();

        // HertzUtils.getAllComponentHTMLUnderPar(hitResource, resolver,
        // scriptHelper, beanMap);
    }

    @Test
    public final void testGetPagePropertiesConfigData() throws JSONException {

        String configuredText = "{\"configurableTextElementName\":\"ExpandedTripSummaryField\",\"configurableTextLabelKey\":\"Label\",\"configurableTextLabelValue\":\"Trip Summary\",\"configurableTextAriaLabelKey\":\"ariaLabel\",\"configurableTextAriaLabelValue\":\"Trip Summary\",\"configurableTextErrorKey\":\"\",\"configurableTextErrorValue\":\"\"}";
        ;
        String configuredTextArea = "{\"configurableTextAreaElementName\":\"textAreaElement\",\"configurableTextAreaKey\":\"textArea\",\"configurableTextAreaValue\":\"Description text\"}";
        String configuredLinks = "{\"configurableLinkElementGroup\":\"SelectDifferentVehicleLinkField\",\"configurableLinkElement\":\"SelectADifferentVehicle\",\"configurableLinkTextKey\":\"linkText\",\"configurableLinkTextValue\":\"Select a different vehicle\",\"configurableLinkAriaLabelKey\":\"ariaLabel\",\"configurableLinkAriaLabelValue\":\"Select a different vehicle\",\"configurableLinkURLKey\":\"\",\"configurableLinkURLValue\":\"\",\"configurableLinkTargetTypeKey\":\"\",\"configurableLinkTargetTypeValue\":false}";
        String configuredCheckboxes = "{\"configurableCheckboxElementGroup\":\"checkoutUserAgreementOfTermsAndConditionsField\",\"configurableCheckboxElementName\":\"checkoutUserAgreementOfTermsAndConditions\",\"configurableCheckboxKey\":\"Text\",\"configurableCheckboxValue\":\"By checking this box you affirm that you have agreed to the Hertz Terms & Conditions\",\"configurableCheckboxAriaLabelKey\":\"ariaLabel\",\"configurableCheckboxAriaLabelValue\":\"By checking this box you affirm that you have agreed to the Hertz Terms & Conditions\",\"configurableCheckboxOrderKey\":\"Order\",\"configurableCheckboxOrderValue\":\"1\",\"configurableCheckboxIsDefaultKey\":\"DefaultSelection\",\"configurableCheckboxIsDefaultValue\":false}";
        String configuredRadioButtons = "{\"configurableRadioButtonElementGroup\":\"checkoutEarnPointsRadioButton1Field\",\"configurableRadioButtonElement\":\"checkoutEarnPointsRadioButton1\",\"configurableRadioButtonKey\":\"Text\",\"configurableRadioButtonValue\":\"I want to earn Hertz Gold Plus Rewards Points\",\"configurableRadioButtonAriaLabelKey\":\"ariaLabel\",\"configurableRadioButtonAriaLabelValue\":\"I want to earn Hertz Gold Plus Rewards Points\",\"configurableRadioButtonOrderKey\":\"Order\",\"configurableRadioButtonOrderValue\":\"1\",\"configurableRadioButtonIsDefaultKey\":\"DefaultSelection\",\"configurableRadioButtonIsDefaultValue\":true}";
        String configuredErrorMessages = "{\"configurableErrorCodeKey\":\"Error Code\",\"configurableErrorCodeValue\":\"Error Code Value\",\"configurableErrorMessageTextKey\":\"errorMessage\",\"configurableErrorMessageTextValue\":\"Please select correct option.\"}";
        String configuredGroupItems = "{\"configurableMultiGroupsElementName\":\"checkoutCountry\",\"configurableMultiGroupsElementGroup\":\"checkoutCountryField\",\"multiGroups\":[{\"configurableMultiGroupCodeKey\":\"checkoutCountryID\",\"configurableMultiGroupCodeValue\":\"<US Country code provided by MS>\",\"configurableMultiGroupTextKey\":\"checkoutCountryText\",\"configurableMultiGroupTextValue\":\"Country\",\"configurableMultiGroupDescriptionKey\":\"checkoutDefaultCountryName\",\"configurableMultiGroupDescriptionValue\":\"United States\"}]}";
        String configuredMultiValuesKey = "tripSummaryAgeButtons";
        String configuredMultiValuesElementName = "tripSummaryAgeButtons";
        String configuredMultiValues = "{\"configurableMultiValueKey\":\"ageRange1\",\"configurableMultiValue\":\"18-19\"}";
        when(jcrResource.getValueMap()).thenReturn(valueMap);
        when(valueMap.get(HertzConstants.CONFIGURED_TEXT))
                .thenReturn(new String[] { configuredText });
        when(valueMap.get(HertzConstants.CONFIGURED_TEXTAREA))
                .thenReturn(new String[] { configuredTextArea });
        when(valueMap.get(HertzConstants.CONFIGURED_LINKS))
                .thenReturn(new String[] { configuredLinks });
        when(valueMap.get(HertzConstants.CONFIGURED_CHECKBOXES))
                .thenReturn(new String[] { configuredCheckboxes });
        when(valueMap.get(HertzConstants.CONFIGURED_RADIO_BUTTONS))
                .thenReturn(new String[] { configuredRadioButtons });
        when(valueMap.get(HertzConstants.CONFIGURED_MULTI_ERROR_MESSAGES_ITEMS))
                .thenReturn(new String[] { configuredErrorMessages });
        when(valueMap.get(HertzConstants.CONFIGURED_MULTI_GROUPS))
                .thenReturn(new String[] { configuredGroupItems });
        when(valueMap.get(HertzConstants.CONFIGURED_MULTI_VALUES_KEY))
                .thenReturn(new String(configuredMultiValuesKey));
        when(valueMap.get(HertzConstants.CONFIGURED_MULTI_VALUES_ELEMENT_NAME))
                .thenReturn(new String(configuredMultiValuesElementName));
        when(valueMap.get(HertzConstants.CONFIGURED_MULTI_VALUES))
                .thenReturn(new String[] { configuredMultiValues });
        when(jcrResource.getChild(HertzConstants.CONFIGURED_IMAGE))
                .thenReturn(childResource);
        when(childResource.listChildren()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true, false);
        when(mockIterator.next()).thenReturn(child);
        when(child.getValueMap()).thenReturn(valueMap2);
        when(valueMap2.get(HertzConstants.KEY_IMAGE, String.class))
                .thenReturn("keyImage");
        when(valueMap2.get(HertzConstants.VALUE_IMAGE, String.class))
                .thenReturn("valueImage");
        when(valueMap2.get(HertzConstants.KEY_IMAGE_URL_LINK, String.class))
                .thenReturn("keyurl");
        when(valueMap2.get(HertzConstants.VALUE_IMAGE_URL_LINK, String.class))
                .thenReturn("valueurl");
        when(valueMap2.get(HertzConstants.KEY_IMAGE_LINK_TARGET_TYPE,
                String.class)).thenReturn("targettype");
        when(valueMap2.get(HertzConstants.VALUE_IMAGE_LINK_TARGET_TYPE,
                Boolean.class)).thenReturn(true);
        Assert.assertNotNull(
                HertzUtils.getPagePropertiesConfigData(jcrResource));
    }

    @Test
    public final void testGetJCRTitleOfANode()
            throws PathNotFoundException, RepositoryException {
        when(jcrNode.getNode(HertzConstants.JCR_CONTENT))
                .thenReturn(pageJcrNode);
        when(pageJcrNode.hasProperty(HertzConstants.JCR_TITLE_PROPERTY))
                .thenReturn(true);
        when(pageJcrNode.getProperty(Mockito.anyString())).thenReturn(property);
        when(property.getString()).thenReturn("property");
        Assert.assertTrue(
                HertzUtils.getJCRTitleOfANode(jcrNode).equals("property"));
    }

    @Test
    public final void testGetDataInMillis() {
        Assert.assertNotNull(
                HertzUtils.getDateInMillis("11/11/2017", "MM/dd/yyyy"));
        try {
            HertzUtils.getDateInMillis("2016-12-30T01:10:01:100-05:30",
                    "MM/dd/yyyy");
        } catch (Exception e) {
            new AssertionFailedError("Error while date conversion",
                    e.getCause());
        }
    }

    @Test
    public final void testConvertToMillis() {
        Assert.assertNotNull(
                HertzUtils.convertToMillis("11/11/2017", "MM/dd/yyyy"));
    }

    @Test
    public final void testGenerateRandom() {
        Assert.assertNotNull(HertzUtils.generateRandom());
    }

    @Test
    public final void TestPreparePredicateMap() {
        Map<String, String> predicateMap = new HashMap<>();
        HertzUtils.preparePredicateMap(PARTNER_PAGE_PATH, predicateMap);
        Assert.assertTrue(MapUtils.isNotEmpty(predicateMap));
    }

    @Test
    public final void testGetRapidComponents() {

        ResourceResolver resolver1 = PowerMockito.mock(ResourceResolver.class);
        Resource myres = PowerMockito.mock(Resource.class);
        // component.getResourceType().equalsIgnoreCase("hertz/components/content/rapidreference")
        PropertyIterator propItr = PowerMockito.mock(PropertyIterator.class);
        Property prop = PowerMockito.mock(Property.class);
        when(resolver.resolve("dummyResource")).thenReturn(childResource);
        when(jcrResource.listChildren()).thenReturn(parChildrenItr);
        when(jcrResource.hasChildren()).thenReturn(true);
        when(parChildrenItr.hasNext()).thenReturn(true, true, false);
        when(parChildrenItr.next()).thenReturn(child);
        when(child.adaptTo(Node.class)).thenReturn(jcrNode);
        String dummyString = "dummyString";
        when(child.getResourceType()).thenReturn(dummyString);
        // when(dummyString.equalsIgnoreCase("hertz/components/content/rapidreference")).thenReturn(false);

        try {
            when(jcrNode.hasProperties()).thenReturn(true);
            when(jcrNode.getName()).thenReturn("childNode");
            when(jcrNode.getProperties()).thenReturn(propItr);
            when(propItr.hasNext()).thenReturn(true, true, false);
            when(propItr.nextProperty()).thenReturn(prop);
            when(propItr.nextProperty()).thenReturn(prop);
            when(prop.getName()).thenReturn("rapidTest");
            when(prop.getString()).thenReturn("rapidTestStreing");
            Assert.assertNotNull(
                    HertzUtils.getRapidComponents(jcrResource, resolver1));
        } catch (RepositoryException e) {
            new AssertionFailedError("Error while date conversion",
                    e.getCause());
        }
    }

    @Test
    public final void testFormatDate() {
        PowerMockito.mockStatic(LoggerFactory.class);
        String inputFormat = "E MMM dd HH:mm:ss Z yyyy";
        String outputFormat = "yyyy-MM-dd'T'HH:mm:ssZZZ";
        when(LoggerFactory.getLogger(HertzUtils.class)).thenReturn(logger);
        when(logger.isDebugEnabled()).thenReturn(true);
        Assert.assertNotNull(HertzUtils.formatDate(
                "Wed Oct 16 00:00:00 CEST 2013", inputFormat, outputFormat));
        try {
            HertzUtils.formatDate("2016-10T01:10:01-05:30", inputFormat,
                    outputFormat);
        } catch (Exception e) {
            new AssertionFailedError(
                    "Throw an exception while ParseException in date",
                    e.getCause());
        }
    }

    @Test
    public final void testDateFormatter() {
        Assert.assertNotNull(
                HertzUtils.dateFormatter("2016-12-30T01:10:01-05:30"));
        try {
            HertzUtils.dateFormatter("2016-12-30T01:10:01:100-05:30");
        } catch (Exception e) {
            new AssertionFailedError(
                    "Throw an exception while ParseException in date",
                    e.getCause());
        }
    }

    @Test
    public final void testReadFileNode() {
        PowerMockito.mockStatic(JcrUtils.class);
        try {
            when(JcrUtils.getNodeIfExists(
                    path + "/jcr:content/renditions/original", session))
                            .thenReturn(jcrNode);
            when(JcrUtils.readFile(jcrNode)).thenReturn(inputStream);
            HertzUtils.readFileNode(path, session);
        } catch (RepositoryException e) {
            new AssertionFailedError("Error occured while reading excel sheet",
                    e.getCause());
        }
    }

    @Test
    public final void testDoesNodeExist() {
        try {
            when(jcrNode.getNodes()).thenReturn(nodeItr);
            when(nodeItr.hasNext()).thenReturn(true, false);
            when(nodeItr.nextNode()).thenReturn(jcrNode);
            when(jcrNode.getName()).thenReturn("nodeName");
            Assert.assertNotNull(HertzUtils.doesNodeExist(jcrNode, "nodeName"));
        } catch (RepositoryException e) {
            new AssertionFailedError("Error occured while reading excel sheet",
                    e.getCause());
        }
    }

    @Test
    public final void testGetHTMLofComponent()
            throws ServletException, IOException {
        PowerMockito.mockStatic(WCMMode.class);
        HttpServletRequest request = PowerMockito
                .mock(HttpServletRequest.class);
        ResourceResolver resolver = PowerMockito.mock(ResourceResolver.class);
        RequestResponseFactory factory = PowerMockito
                .mock(RequestResponseFactory.class);
        SlingRequestProcessor processor = PowerMockito
                .mock(SlingRequestProcessor.class);
        when(factory.createRequest("GET", requestPath)).thenReturn(request);
        Assert.assertNotNull(HertzUtils.getHTMLofComponent(resolver, factory,
                processor, requestPath,slingService));

    }

    @Test
    public final void testClean() {
        try {
            when(jcrResource.listChildren()).thenReturn(parChildrenItr);
            when(parChildrenItr.hasNext()).thenReturn(true, false);
            when(parChildrenItr.next()).thenReturn(child);
            Map<String, Object> maptest = new HashMap<String, Object>();
            maptest.put("key1", "value1");
            maptest.put("sling:resourceType", "value2");
            when(child.getValueMap()).thenReturn(valueMap);
            HertzUtils.clean(jcrResource, resolver,
                    HertzConstants.SLING_RESOURCE_TYPE);
        } catch (PersistenceException e) {
            new AssertionFailedError(
                    "Throw an exception while ParseException in date",
                    e.getCause());
        }
    }

    @Test
    public final void testIsDelivered() throws InterruptedException {
        when(replicator.getReplicationStatus(session, CONTENT_DATA_PATH))
                .thenReturn(status);
        when(status.isDelivered()).thenReturn(true);
        Assert.assertNotNull(
                HertzUtils.isDelivered(replicator, session, CONTENT_DATA_PATH));
    }
    
    @Test
    public final void testIsDeliveredNull() throws InterruptedException {
        when(replicator.getReplicationStatus(session, CONTENT_DATA_PATH))
                .thenReturn(null);
        Assert.assertFalse(HertzUtils.isDelivered(replicator, session, CONTENT_DATA_PATH));
    }

    @Test
    public final void testGetIdentifier() {
        String[] mappings = new String[] { "abcd:abcd", "abcd" };
        String actionPath = "/content/abcd";
        Assert.assertNotNull(HertzUtils.getIdentifier(actionPath, mappings));
    }

    @Test
    public final void testReplaceImageTagWithPicture() {
    	
        ResourceResolver resolver = PowerMockito.mock(ResourceResolver.class);
        ResourceResolver resolver1 = PowerMockito.mock(ResourceResolver.class);
        Resource resource = PowerMockito.mock(Resource.class);
        Resource resource1 = PowerMockito.mock(Resource.class);
        Asset asset = PowerMockito.mock(Asset.class);
        Rendition rendition = PowerMockito.mock(Rendition.class);
        Document doc = Mockito.mock(Document.class);
        Elements elements = Mockito.mock(Elements.class);
        Element element = Mockito.mock(Element.class);
        ValueMap map = PowerMockito.mock(ValueMap.class);
        when(doc.getElementsByTag("img")).thenReturn(elements);
        when(elements.iterator()).thenReturn(elementIterator);
        when(elementIterator.hasNext()).thenReturn(true, false);
        when(elementIterator.next()).thenReturn(element);
        when(element.attr("src")).thenReturn("src");
        when(element.attr("data-path")).thenReturn(IMAGE_PATH);
        when(element.attr("alt")).thenReturn("alt");
        when(element.attr("class")).thenReturn("anchor-image");
        when(resolver.getResource(IMAGE_PATH)).thenReturn(resource);
        when(resource.getValueMap()).thenReturn(map);
        when(map.get("fileReference")).thenReturn(IMAGE_DAM_PATH);
        when(resource.getResourceResolver()).thenReturn(resolver1);
        when(resolver1.getResource(IMAGE_DAM_PATH)).thenReturn(null);
        when(resource.getPath()).thenReturn(IMAGE_PATH);
        HertzUtils.replaceImageTagWithPicture(doc, resolver);
        when(resolver1.getResource(IMAGE_DAM_PATH)).thenReturn(resource1);
        when(resource1.adaptTo(Asset.class)).thenReturn(asset);
        when(asset.getRendition(Mockito.anyString())).thenReturn(rendition);
        when(rendition.getPath()).thenReturn("renditionPath");
        HertzUtils.replaceImageTagWithPicture(doc, resolver);
    }

    @Test
    public final void testReplaceImageTagWithPicture1() {
       ResourceResolver resolver = PowerMockito.mock(ResourceResolver.class);
        ResourceResolver resolver1 = PowerMockito.mock(ResourceResolver.class);
        Resource resource = PowerMockito.mock(Resource.class);
        Resource resource1 = PowerMockito.mock(Resource.class);
        Asset asset = PowerMockito.mock(Asset.class);
        Rendition rendition = PowerMockito.mock(Rendition.class);
        Document doc = Mockito.mock(Document.class);
        Elements elements = Mockito.mock(Elements.class);
        Element element = Mockito.mock(Element.class);
        ValueMap map = PowerMockito.mock(ValueMap.class);
        when(doc.getElementsByTag("img")).thenReturn(elements);
        when(elements.iterator()).thenReturn(elementIterator);
        when(elementIterator.hasNext()).thenReturn(true, false);
        when(elementIterator.next()).thenReturn(element);
        when(element.attr("src")).thenReturn("src");
        when(element.attr("data-path")).thenReturn(IMAGE_PATH);
        when(element.attr("alt")).thenReturn("alt");
        when(element.attr("class")).thenReturn("anchor-image");
        when(resolver.getResource(IMAGE_PATH)).thenReturn(resource);
        when(resource.getValueMap()).thenReturn(map);
        when(map.get("fileReference")).thenReturn(IMAGE_DAM_PATH);
        when(resource.getResourceResolver()).thenReturn(resolver1);
        when(resource.getPath()).thenReturn(IMAGE_PATH);
        when(resolver1.getResource(IMAGE_DAM_PATH)).thenReturn(resource1);
        when(resource1.adaptTo(Asset.class)).thenReturn(asset);
        when(asset.getRendition(Mockito.anyString())).thenReturn(rendition);
        when(rendition.getPath()).thenReturn("renditionPath");
        HertzUtils.replaceImageTagWithPicture(doc, resolver);
    }

    @Test
    public final void testReplaceImageTagWithPicture2() {
    	 ResourceResolver resolver = PowerMockito.mock(ResourceResolver.class);
        ResourceResolver resolver1 = PowerMockito.mock(ResourceResolver.class);
        Resource resource = PowerMockito.mock(Resource.class);
        Resource resource1 = PowerMockito.mock(Resource.class);
        Asset asset = PowerMockito.mock(Asset.class);
        Rendition rendition = PowerMockito.mock(Rendition.class);
        Document doc = Mockito.mock(Document.class);
        Elements elements = Mockito.mock(Elements.class);
        Element element = Mockito.mock(Element.class);
        ValueMap map = PowerMockito.mock(ValueMap.class);
        when(doc.getElementsByTag("img")).thenReturn(elements);
        when(elements.iterator()).thenReturn(elementIterator);
        when(elementIterator.hasNext()).thenReturn(true, false);
        when(elementIterator.next()).thenReturn(element);
        when(element.attr("src")).thenReturn("src");
        when(element.attr("data-path")).thenReturn(IMAGE_PATH);
        when(element.attr("alt")).thenReturn("alt");
        when(element.attr("class")).thenReturn(StringUtils.EMPTY);
        when(resolver.getResource(IMAGE_PATH)).thenReturn(resource);
        when(resource.getValueMap()).thenReturn(map);
        when(map.get("fileReference")).thenReturn(IMAGE_DAM_PATH);
        when(resource.getResourceResolver()).thenReturn(resolver1);
        when(resource.getPath()).thenReturn(IMAGE_PATH);
        when(resolver1.getResource(IMAGE_DAM_PATH)).thenReturn(resource1);
        when(resource1.adaptTo(Asset.class)).thenReturn(asset);
        when(asset.getRendition(Mockito.anyString())).thenReturn(rendition);
        when(rendition.getPath()).thenReturn("renditionPath");
        HertzUtils.replaceImageTagWithPicture(doc, resolver);
    }

    @Test
    public final void testReplaceImageTagWithPicture3() {
        ResourceResolver resolver = PowerMockito.mock(ResourceResolver.class);
        Document doc = Mockito.mock(Document.class);
        Elements elements = Mockito.mock(Elements.class);
        Element element = Mockito.mock(Element.class);
        ValueMap map = PowerMockito.mock(ValueMap.class);
        when(doc.getElementsByTag("img")).thenReturn(elements);
        when(elements.iterator()).thenReturn(elementIterator);
        when(elementIterator.hasNext()).thenReturn(true, false);
        when(elementIterator.next()).thenReturn(element);
        when(element.attr("src")).thenReturn("src");
        when(element.attr("data-path")).thenReturn(IMAGE_PATH);
        when(element.attr("alt")).thenReturn("alt");
        when(element.attr("class")).thenReturn(StringUtils.EMPTY);
        when(resolver.getResource(IMAGE_PATH)).thenReturn(null);
        HertzUtils.replaceImageTagWithPicture(doc, resolver);
    }

    @Test
    public final void testSetSeoMetaDataInBean() throws JSONException {
        SpaPageBean spaBean = PowerMockito.mock(SpaPageBean.class);
        Resource pageResource = PowerMockito.mock(Resource.class);
        ValueMap propMap = PowerMockito.mock(ValueMap.class);
        when(pageResource.adaptTo(ValueMap.class)).thenReturn(propMap);
        when(propMap.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS,
                new String[] {})).thenReturn(new String[] {});
        when(pageResource.getResourceType()).thenReturn("/hertz/components/pages/data");
        when(pageResource.getPath()).thenReturn("/account/rentals");
        HertzUtils.setSeoMetaDataInBean(spaBean, pageResource, requestPathInfo, resolver);
        when(pageResource.getPath()).thenReturn("/accounts/rentals/edit/personal/account/abcd");
        when(pageResource.getResourceResolver()).thenReturn(resolver);
        when(resolver.getResource("/accounts/rentals/edit/personal/account/jcr:content")).thenReturn(childResource);
        when(childResource.adaptTo(ValueMap.class)).thenReturn(valueMap3);
        when(valueMap3.containsKey(NameConstants.PN_HIDE_IN_NAV)).thenReturn(false);
        when(valueMap3.get(NameConstants.PN_HIDE_IN_NAV)).thenReturn("yes");
        when(propMap.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS,
                new String[] {})).thenReturn(new String[] {
                        "{'configurableSeoKey':'og:title','configurableSeoValue':'Title'}",
                        "{'configurableSeoKey':'og:description','configurableSeoValue':'Description'}" });
        
        when(pageResource.getResourceType()).thenReturn("/hertz/components/pages/data");
        when(propMap.containsKey(JcrConstants.JCR_TITLE)).thenReturn(true);
        when(propMap.get(JcrConstants.JCR_TITLE, String.class))
                .thenReturn("title");

        when(propMap.containsKey(HertzConstants.SUB_TITLE)).thenReturn(true);
        when(propMap.get(HertzConstants.SUB_TITLE, String.class))
                .thenReturn("subtitle");
        when(propMap.containsKey(NameConstants.PN_PAGE_TITLE)).thenReturn(true);
        when(propMap.get(NameConstants.PN_PAGE_TITLE, String.class))
                .thenReturn("pagetitle");
        when(propMap.containsKey(NameConstants.PN_NAV_TITLE)).thenReturn(true);
        when(propMap.get(NameConstants.PN_NAV_TITLE, String.class))
                .thenReturn("navTitle");

        when(propMap.containsKey(JcrConstants.JCR_DESCRIPTION))
                .thenReturn(true);
        when(propMap.get(JcrConstants.JCR_DESCRIPTION, String.class))
                .thenReturn("description");
        when(propMap.containsKey(HertzConstants.CONFIGURABLE_SEO_GOOGLE_VERIFICATION_TAG)).thenReturn(true);
        when(propMap.get(HertzConstants.CONFIGURABLE_SEO_GOOGLE_VERIFICATION_TAG,String.class)).thenReturn("google");
        when(propMap.containsKey(HertzConstants.CONFIGURABLE_SEO_BING_VERIFICATION_TAG)).thenReturn(true);
        when(propMap.get(HertzConstants.CONFIGURABLE_SEO_BING_VERIFICATION_TAG,String.class)).thenReturn("bing");
        when(propMap.containsKey(HertzConstants.JSON_SCRIPT)).thenReturn(true);
        when(propMap.get(HertzConstants.JSON_SCRIPT,String.class)).thenReturn("script");
        HertzUtils.setSeoMetaDataInBean(spaBean, pageResource, requestPathInfo, resolver);
        when(propMap.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS,
                new String[] {})).thenReturn(new String[] {
                        "{'configurableSeoKey1':'og:title','configurableSeoValue1':'Title'}",
                        "{'configurableSeoKey':'og:description','configurableSeoValue':'Description'}" });
        HertzUtils.setSeoMetaDataInBean(spaBean, pageResource, requestPathInfo, resolver);
        when(propMap.containsKey(JcrConstants.JCR_TITLE)).thenReturn(false);
        HertzUtils.setSeoMetaDataInBean(spaBean, pageResource, requestPathInfo, resolver);
        when(propMap.containsKey(JcrConstants.JCR_DESCRIPTION))
                .thenReturn(false);
        when(propMap.containsKey(HertzConstants.CONFIGURABLE_SEO_GOOGLE_VERIFICATION_TAG)).thenReturn(false);
        when(propMap.containsKey(HertzConstants.CONFIGURABLE_SEO_BING_VERIFICATION_TAG)).thenReturn(false);
        when(propMap.containsKey(HertzConstants.JSON_SCRIPT)).thenReturn(false);
        when(valueMap3.containsKey(NameConstants.PN_HIDE_IN_NAV)).thenReturn(true);
        HertzUtils.setSeoMetaDataInBean(spaBean, pageResource, requestPathInfo, resolver);

    }

    @Test
    public final void testSetPartnerHomePageValue() {
    	Resource jcrResource = PowerMockito.mock(Resource.class);
    	Resource partnerHomePageResource = PowerMockito.mock(Resource.class);
    	ValueMap pageProperties = PowerMockito.mock(ValueMap.class);
    	String [] selectors=new String[]{"spa","cpCode"};
    	when(requestPathInfo.getSelectors()).thenReturn(selectors);
    	when(jcrResource.getValueMap()).thenReturn(pageProperties);
    	when(pageProperties.get(HertzConstants.PARTNER_BASE_PAGE_PATH, String.class)).thenReturn(PARTNER_HOME_PAGE);
    	when(resolver.getResource(Mockito.anyString())).thenReturn(partnerHomePageResource);
    	when(partnerHomePageResource.getChild(Mockito.anyString())).thenReturn(null);
        HertzUtils.setPartnerHomePageValue(map, jcrResource, requestPathInfo, resolver);

    }
    
    @Test
    public final void testGetGroupName() {
        String payloadPath = "string/string";
        Resource pageResource = PowerMockito.mock(Resource.class);
        Page page = PowerMockito.mock(Page.class);
        Resource contentResource = PowerMockito.mock(Resource.class);
        Resource childResource = PowerMockito.mock(Resource.class);
        Resource parResource = PowerMockito.mock(Resource.class);
        ValueMap valueMap = PowerMockito.mock(ValueMap.class);
        Iterator<Resource> iterator = PowerMockito.mock(Iterator.class);
        when(pageResource.adaptTo(Page.class)).thenReturn(page);
        when(page.getContentResource()).thenReturn(contentResource);
        when(contentResource.getChild(HertzConstants.PAR))
                .thenReturn(parResource);
        when(parResource.listChildren()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true, false);
        when(iterator.next()).thenReturn(childResource);
        when(childResource.getPath()).thenReturn("path");
        when(childResource
                .isResourceType("hertz/components/content/pathtogroup"))
                        .thenReturn(true);
        when(childResource.getValueMap()).thenReturn(valueMap);
        when(valueMap.get(Mockito.anyString())).thenReturn("string");
        HertzUtils.getGroupName(payloadPath, pageResource);

    }

    @Test
    public final void testGetDefaultImageResource() {
        String path = "string/string";
        when(jcrResource.getParent()).thenReturn(parentResource);
        when(parentResource.getPath()).thenReturn(path);
        when(resolver.getResource(Mockito.anyString()))
                .thenReturn(pageResource);
        when(pageResource.getParent()).thenReturn(pageParentResource);
        when(pageParentResource.getChild(HertzConstants.DEFAULT))
                .thenReturn(defaultResource);
        when(defaultResource.getChild(HertzConstants.JCR_CONTENT))
                .thenReturn(defaultJcrResource);
        when(defaultJcrResource.getChild(HertzConstants.IMAGE))
                .thenReturn(imageResource);
        when(imageResource.getValueMap()).thenReturn(imageResourceValueMap);
        when(imageResourceValueMap.containsKey(HertzConstants.FILE_REFERENCE))
                .thenReturn(true);
        HertzUtils.getDefaultImageResource(jcrResource, resolver);
    }

    @Test
    public final void testGetFixedSlotData() {
        String[] compConfigArray = new String[] { "browsepartners" };
        String resourceType = "hertz/components/content/browsepartners";
        Map<String, Object> beanMap = new LinkedHashMap<>();
        ComponentExporterService exporterService1 = PowerMockito
                .mock(ComponentExporterService.class);
        ComponentExporterService exporterService2 = PowerMockito
                .mock(ComponentExporterService.class);
        ComponentExporterService[] exporterService = ArrayUtils
                .toArray(exporterService1, exporterService2);
        HertzUtils.getFixedSlotData(jcrResource, resolver, beanMap, page,
                compConfigArray, exporterService, resourceType);

        String[] compConfigArray1 = new String[] { "image" };
        String resourceType1 = "hertz/components/content/image";
        Map<String, Object> beanMap1 = new LinkedHashMap<>();
        ComponentExporterService exporterService3 = PowerMockito
                .mock(ComponentExporterService.class);
        ComponentExporterService exporterService4 = PowerMockito
                .mock(ComponentExporterService.class);
        ComponentExporterService[] exporterService5 = ArrayUtils
                .toArray(exporterService3, exporterService4);
        when(jcrResource.getChild(HertzConstants.IMAGE))
                .thenReturn(imageResource);
        String path = "string/string";
        when(jcrResource.getParent()).thenReturn(parentResource);
        when(parentResource.getPath()).thenReturn(path);
        when(resolver.getResource(Mockito.anyString()))
                .thenReturn(pageResource);
        when(pageResource.getParent()).thenReturn(pageParentResource);
        when(pageParentResource.getChild(HertzConstants.DEFAULT))
                .thenReturn(defaultResource);
        when(defaultResource.getChild(HertzConstants.JCR_CONTENT))
                .thenReturn(defaultJcrResource);
        when(defaultJcrResource.getChild(HertzConstants.IMAGE))
                .thenReturn(imageResource);
        when(imageResource.getValueMap()).thenReturn(imageResourceValueMap);
        when(imageResourceValueMap.containsKey(HertzConstants.FILE_REFERENCE))
                .thenReturn(true);
        HertzUtils.getFixedSlotData(jcrResource, resolver, beanMap1, page,
                compConfigArray1, exporterService5, resourceType1);
      
        String[] compConfigArray2 = new String[] { "locationdirectoryhero" };
        String resourceType2 = "hertz/components/content/locationdirectoryhero";
        Map<String, Object> beanMap2 = new LinkedHashMap<>();
        ComponentExporterService exporterService6 = PowerMockito
                .mock(ComponentExporterService.class);
        ComponentExporterService exporterService7 = PowerMockito
                .mock(ComponentExporterService.class);
        ComponentExporterService[] exporterService8 = ArrayUtils
                .toArray(exporterService6, exporterService7);
        when(page.getAbsoluteParent(4)).thenReturn(parentPage);
        when(parentPage.getContentResource(HertzConstants.HERO))
                .thenReturn(heroImageResource);
        when(heroImageResource.getValueMap()).thenReturn(imageResourceValueMap);
        when(imageResourceValueMap.containsKey(HertzConstants.ALT_TEXT))
                .thenReturn(true);
        when(imageResourceValueMap.get(HertzConstants.ALT_TEXT))
                .thenReturn("alt txt");
        HertzUtils.getFixedSlotData(jcrResource, resolver, beanMap2, page,
                compConfigArray2, exporterService8, resourceType2);

        String[] compConfigArray3 = new String[] { "locationdirectoryhero" };
        String resourceType3 = "hertz/components/content/locationdirectoryhero";
        Map<String, Object> beanMap3 = new LinkedHashMap<>();
        ComponentExporterService exporterService9 = PowerMockito
                .mock(ComponentExporterService.class);
        ComponentExporterService exporterService10 = PowerMockito
                .mock(ComponentExporterService.class);
        ComponentExporterService[] exporterService11 = ArrayUtils
                .toArray(exporterService9, exporterService10);
        when(jcrResource.getChild(HertzConstants.HERO))
                .thenReturn(heroImageResource);
        HertzUtils.getFixedSlotData(jcrResource, resolver, beanMap3, page,
                compConfigArray3, exporterService11, resourceType3);

        String[] compConfigArray4 = new String[] { "image" };
        String resourceType4 = "hertz/components/content/image";
        Map<String, Object> beanMap4 = new LinkedHashMap<>();
        ComponentExporterService exporterService12 = PowerMockito
                .mock(ComponentExporterService.class);
        ComponentExporterService exporterService13 = PowerMockito
                .mock(ComponentExporterService.class);
        ComponentExporterService[] exporterService14 = ArrayUtils
                .toArray(exporterService12, exporterService13);
        when(jcrResource.getChild(HertzConstants.IMAGE)).thenReturn(null);
        when(jcrResource.getParent()).thenReturn(parentResource);
        when(parentResource.getPath()).thenReturn(path);
        when(resolver.getResource(Mockito.anyString()))
                .thenReturn(pageResource);
        when(pageResource.getParent()).thenReturn(pageParentResource);
        when(pageParentResource.getChild(HertzConstants.DEFAULT))
                .thenReturn(defaultResource);
        when(defaultResource.getChild(HertzConstants.JCR_CONTENT))
                .thenReturn(defaultJcrResource);
        when(defaultJcrResource.getChild(HertzConstants.IMAGE))
                .thenReturn(imageResource);
        when(imageResource.getValueMap()).thenReturn(imageResourceValueMap);
        when(imageResourceValueMap.containsKey(HertzConstants.FILE_REFERENCE))
                .thenReturn(true);
        HertzUtils.getFixedSlotData(jcrResource, resolver, beanMap4, page,
                compConfigArray4, exporterService14, resourceType4);

        String[] compConfigArray5 = new String[] { "image" };
        String resourceType5 = "hertz/components/content/image";
        Map<String, Object> beanMap5 = new LinkedHashMap<>();
        ComponentExporterService exporterService15 = PowerMockito
                .mock(ComponentExporterService.class);
        ComponentExporterService exporterService16 = PowerMockito
                .mock(ComponentExporterService.class);
        ComponentExporterService[] exporterService17 = ArrayUtils
                .toArray(exporterService15, exporterService16);
        when(jcrResource.getChild(HertzConstants.IMAGE))
                .thenReturn(imageResource);
        when(jcrResource.getParent()).thenReturn(parentResource);
        when(parentResource.getPath()).thenReturn(path);
        when(resolver.getResource(Mockito.anyString()))
                .thenReturn(pageResource);
        when(pageResource.getParent()).thenReturn(pageParentResource);
        when(pageParentResource.getChild(HertzConstants.DEFAULT))
                .thenReturn(defaultResource);
        when(defaultResource.getChild(HertzConstants.JCR_CONTENT))
                .thenReturn(defaultJcrResource);
        when(defaultJcrResource.getChild(HertzConstants.IMAGE))
                .thenReturn(imageResource);
        when(imageResource.getValueMap()).thenReturn(imageResourceValueMap);
        when(imageResourceValueMap.containsKey(HertzConstants.FILE_REFERENCE))
                .thenReturn(false);
        HertzUtils.getFixedSlotData(jcrResource, resolver, beanMap5, page,
                compConfigArray5, exporterService17, resourceType5);
    }

    
    @Test
    public final void testSetHeaderFooterContent() {
    	String refPath = "path";
        when(resource.getResourceType()).thenReturn(HertzConstants.HEADER_RES_TYPE);
        when(resource.getValueMap()).thenReturn(headerResProperties);
        when(headerResProperties.containsKey(HertzConstants.HEADER_REFERENCE)).thenReturn(true);
        when(headerResProperties.get(HertzConstants.HEADER_REFERENCE)).thenReturn(refPath);
        when(resolver.getResource(refPath)).thenReturn(referenceHeaderResource);
        when(referenceHeaderResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(jcrContentResource);
        when(jcrContentResource.getChild(HertzConstants.HEADER)).thenReturn(childHeaderResource);
        when(resolver.getResource(Mockito.anyString())).thenReturn(topNavResource);
        HertzUtils.setHeaderFooterContent(resource,resolver,helper);
    }
    


    @Test
    public final void testIsValidResource() {
        Assert.assertTrue(HertzUtils.isValidResource(jcrResource));
        Assert.assertFalse(HertzUtils.isValidResource(null));
    }

    @Test
    public final void testIsValidPage() {
        Assert.assertTrue(HertzUtils.isValidPage(page));
        Assert.assertFalse(HertzUtils.isValidPage(null));
    }
    
    @Test
    public final void testGetHertzLinksProps(){
    	when(resource.getValueMap()).thenReturn(valueMap);
    	when(valueMap.containsKey(HertzConstants.LABEL)).thenReturn(true);
    	when(valueMap.containsKey(HertzConstants.VALUE)).thenReturn(true);
    	when(valueMap.get(HertzConstants.LABEL)).thenReturn("label");
    	when(valueMap.get(HertzConstants.VALUE)).thenReturn("value");
    	HertzUtils.getHertzLinksProps(resource);
    }
    
    @Test
    public final void testGetHertzLinksProps1(){
    	when(resource.getValueMap()).thenReturn(valueMap);
    	when(valueMap.containsKey(HertzConstants.LABEL)).thenReturn(false);
    	when(valueMap.containsKey(HertzConstants.VALUE)).thenReturn(true);
    	when(valueMap.get(HertzConstants.LABEL)).thenReturn("label");
    	when(valueMap.get(HertzConstants.VALUE)).thenReturn("value");
    	HertzUtils.getHertzLinksProps(resource);
    }
    
    @Test
    public final void testGetHertzLinksProps2(){
    	when(resource.getValueMap()).thenReturn(valueMap);
    	when(valueMap.containsKey(HertzConstants.LABEL)).thenReturn(true);
    	when(valueMap.containsKey(HertzConstants.VALUE)).thenReturn(false);
    	when(valueMap.get(HertzConstants.LABEL)).thenReturn("label");
    	when(valueMap.get(HertzConstants.VALUE)).thenReturn("value");
    	HertzUtils.getHertzLinksProps(resource);
    }
    
    @Test
    public final void testGetHertzLinksProps3(){
    	when(resource.getValueMap()).thenReturn(valueMap);
    	when(valueMap.containsKey(HertzConstants.LABEL)).thenReturn(false);
    	when(valueMap.containsKey(HertzConstants.VALUE)).thenReturn(false);
    	when(valueMap.get(HertzConstants.LABEL)).thenReturn("label");
    	when(valueMap.get(HertzConstants.VALUE)).thenReturn("value");
    	HertzUtils.getHertzLinksProps(resource);
    }
    
    @Test
    public final void testSetPartnerHomeDataInJSON(){
    	Map<String, Object> beanMap = new LinkedHashMap<>();
    	ResourceResolver resolver1=PowerMockito.mock(ResourceResolver.class);
    	when(resolver.getResource(TestConstants.PARTNER_DETAIL_PAGE)).thenReturn(resource);
    	when(resource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(jcrContentResource);
    	when(jcrContentResource.listChildren()).thenReturn(mockIterator);
    	when(mockIterator.hasNext()).thenReturn(true,false);
    	when(mockIterator.next()).thenReturn(childResource);
    	when(childResource.getResourceType()).thenReturn(HertzConstants.HERO_RES_TYPE);
    	when(childResource.getValueMap()).thenReturn(valueMap);
    	when(valueMap.containsKey(HertzConstants.ALT_TEXT)).thenReturn(true);
    	when(valueMap.get(HertzConstants.ALT_TEXT)).thenReturn("altText");
    	when(valueMap.containsKey(HertzConstants.TAGLINE_TEXT)).thenReturn(true);
    	when(valueMap.get(HertzConstants.TAGLINE_TEXT)).thenReturn("tagline");
    	when(valueMap.containsKey(HertzConstants.FILE_REFERENCE)).thenReturn(true);
    	when(valueMap.get(HertzConstants.FILE_REFERENCE)).thenReturn("fileReference");
    	when(childResource.getResourceResolver()).thenReturn(resolver1);
    	when(resolver1.getResource("fileReference")).thenReturn(null);
    	HertzUtils.setPartnerHomeDataInJSON(scriptHelper, resolver, beanMap, TestConstants.PARTNER_DETAIL_PAGE);
    }
    
    @Test
    public final void testSetPartnerHomeDataInJSON1(){
    	Map<String, Object> beanMap = new LinkedHashMap<>();
    	ResourceResolver resolver1=PowerMockito.mock(ResourceResolver.class);
    	when(resolver.getResource(TestConstants.PARTNER_DETAIL_PAGE)).thenReturn(resource);
    	when(resource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(jcrContentResource);
    	when(jcrContentResource.listChildren()).thenReturn(mockIterator);
    	when(mockIterator.hasNext()).thenReturn(true,false);
    	when(mockIterator.next()).thenReturn(childResource);
    	when(childResource.getResourceType()).thenReturn(HertzConstants.HERO_RES_TYPE);
    	when(childResource.getValueMap()).thenReturn(valueMap);
    	when(valueMap.containsKey(HertzConstants.ALT_TEXT)).thenReturn(false);
    	when(valueMap.get(HertzConstants.ALT_TEXT)).thenReturn("altText");
    	when(valueMap.containsKey(HertzConstants.TAGLINE_TEXT)).thenReturn(false);
    	when(valueMap.get(HertzConstants.TAGLINE_TEXT)).thenReturn("tagline");
    	when(valueMap.containsKey(HertzConstants.FILE_REFERENCE)).thenReturn(false);
    	when(valueMap.get(HertzConstants.FILE_REFERENCE)).thenReturn("fileReference");
    	when(childResource.getResourceResolver()).thenReturn(resolver1);
    	when(resolver1.getResource("fileReference")).thenReturn(null);
    	HertzUtils.setPartnerHomeDataInJSON(scriptHelper, resolver, beanMap, TestConstants.PARTNER_DETAIL_PAGE);
    }
    
    @Test
    public final void testSetPartnerHomeDataInJSON2(){
    	Map<String, Object> beanMap = new LinkedHashMap<>();
    	ComponentExporterService componentExporterService2 = PowerMockito.mock(ComponentExporterService.class);
    	ComponentExporterService componentExporterService3 = PowerMockito.mock(ComponentExporterService.class);
    	ComponentExporterService[] exporterService1 = ArrayUtils.toArray(
				componentExporterService2, componentExporterService3);
    	when(resolver.getResource(TestConstants.PARTNER_DETAIL_PAGE)).thenReturn(resource);
    	when(resource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(jcrContentResource);
    	when(jcrContentResource.listChildren()).thenReturn(mockIterator);
    	when(mockIterator.hasNext()).thenReturn(true,false);
    	when(mockIterator.next()).thenReturn(childResource);
    	when(childResource.getResourceType()).thenReturn(HertzConstants.FIXED_CONTENT_SLOT_RES_TYPE);
    	when(scriptHelper.getServices(ComponentExporterService.class,
                                "(identifier=" + "fixedcontentslot" + ")")).thenReturn(ArrayUtils.toArray(exporterService1));
    	HertzUtils.setPartnerHomeDataInJSON(scriptHelper, resolver, beanMap, TestConstants.PARTNER_DETAIL_PAGE);
    }
    
    @Test
    public final void testGetConfigPagePath(){
    	when(pageResource.getValueMap()).thenReturn(valueMap);
    	when(valueMap.get("hello",String.class)).thenReturn("hello");
    	when(resolver.getResource(Mockito.anyString())).thenReturn(childResource);
    	Assert.assertNotNull(HertzUtils.getConfigPagePath(pageResource, "hello", resolver));
    }
    
    @Test
    public final void testRemoveHtmlExtensionFromUrl(){
    	Document doc=PowerMockito.mock(Document.class);
    	Elements elements=PowerMockito.mock(Elements.class);
    	Element element=PowerMockito.mock(Element.class);
    	when(doc.select("a[href]")).thenReturn(elements);
    	when(elements.iterator()).thenReturn(elementIterator);
    	when(elementIterator.hasNext()).thenReturn(true,false);
    	when(elementIterator.next()).thenReturn(element);
    	when(element.attr("href")).thenReturn("/content/hertz-rac.html");
    	HertzUtils.removeHtmlExtensionFromUrl(doc, resolver);
    	when(element.attr("href")).thenReturn("/content/hertz-rac");
    	HertzUtils.removeHtmlExtensionFromUrl(doc, resolver);
    	when(element.attr("href")).thenReturn("content/hertz-rac.html");
    	HertzUtils.removeHtmlExtensionFromUrl(doc, resolver);
    	when(element.attr("href")).thenReturn("www.hertz.com");
    	HertzUtils.removeHtmlExtensionFromUrl(doc, resolver);
    	
    }
    
    @Test
    public final void testRemoveHtmlExtensionFromUrlNoHtml(){
    	Document doc=PowerMockito.mock(Document.class);
    	Elements elements=PowerMockito.mock(Elements.class);
    	Element element=PowerMockito.mock(Element.class);
    	when(doc.select("a[href]")).thenReturn(elements);
    	when(elements.iterator()).thenReturn(elementIterator);
    	when(elementIterator.hasNext()).thenReturn(true,false);
    	when(elementIterator.next()).thenReturn(element);
    	when(element.attr("href")).thenReturn("/content/hertz-rac");
    	HertzUtils.removeHtmlExtensionFromUrl(doc, resolver);
    	
    }
    
    @Test
    public final void testRemoveHtmlExtensionFromUrlNoSlash(){
    	Document doc=PowerMockito.mock(Document.class);
    	Elements elements=PowerMockito.mock(Elements.class);
    	Element element=PowerMockito.mock(Element.class);
    	when(doc.select("a[href]")).thenReturn(elements);
    	when(elements.iterator()).thenReturn(elementIterator);
    	when(elementIterator.hasNext()).thenReturn(true,false);
    	when(elementIterator.next()).thenReturn(element);
    	when(element.attr("href")).thenReturn("content/hertz-rac.html");
    	HertzUtils.removeHtmlExtensionFromUrl(doc, resolver);
    	
    }
    
    @Test
    public final void testRemoveHtmlExtensionFromUrlBadCase(){
    	Document doc=PowerMockito.mock(Document.class);
    	Elements elements=PowerMockito.mock(Elements.class);
    	Element element=PowerMockito.mock(Element.class);
    	when(doc.select("a[href]")).thenReturn(elements);
    	when(elements.iterator()).thenReturn(elementIterator);
    	when(elementIterator.hasNext()).thenReturn(true,false);
    	when(elementIterator.next()).thenReturn(element);
    	when(element.attr("href")).thenReturn("www.hertz.com");
    	HertzUtils.removeHtmlExtensionFromUrl(doc, resolver);
    	
    }
}
