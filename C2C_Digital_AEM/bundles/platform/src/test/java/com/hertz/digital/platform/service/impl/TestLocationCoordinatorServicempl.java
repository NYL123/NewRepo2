package com.hertz.digital.platform.service.impl;

import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.osgi.service.component.ComponentContext;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.Externalizer;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.hertz.digital.platform.bean.CoordinatorConsumable;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.constants.TestConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.factory.MicroServicesConfigurationFactory;
import com.hertz.digital.platform.service.api.JCRService;
import com.hertz.digital.platform.service.api.SystemUserService;
import com.hertz.digital.platform.use.DataUse;
import com.hertz.digital.platform.utils.HertzUtils;

import junit.framework.Assert;
import junitx.util.PrivateAccessor;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoggerFactory.class, DataUse.class, HertzUtils.class })
public class TestLocationCoordinatorServicempl {
    @InjectMocks
    private LocationCoordinatorServicempl rqCodesCoordinatorServiceImpl;
    @Mock
    private SystemUserService systemService;
    @Mock
    private ResourceResolver mockResResolver;

    @Mock
    PageManager pagemanager;

    @Mock
    Page page;

    @Mock
    Resource configPageResource;

    @Mock
    Resource secondLevelResource;

    @Mock
    Resource thirdLevelResource;

    @Mock
    Resource fourthLevelResource;

    @Mock
    Resource fifthLevelResource;

    @Mock
    Resource sixthLevelResource;

    @Mock
    Iterable<Resource> mockIterable;

    @Mock
    Iterator<Resource> mockIterator;

    @Mock
    Iterable<Resource> mockIterable1;

    @Mock
    Iterator<Resource> mockIterator1;

    @Mock
    Resource childResource;

    @Mock
    Session session;

    @Mock
    QueryBuilder builder;

    @Mock
    Query query;

    @Mock
    SearchResult result;

    List<Hit> mockList;

    @Mock
    Hit hit;

    @Mock
    Resource childJcrResource;

    @Mock
    Resource parResource;

    @Mock
    ValueMap valueMap;

    @Mock
    ValueMap valueMap1;

    @Mock
    Resource parentResource;
    @Mock
    private Session mockSession;
    @Mock
    private Resource resource;

    @Mock
    private ComponentContext mockComponentContext;

    @Mock
    private Dictionary<String, Object> mockDictionary;

    @Mock
    Iterator<Resource> nodes;

    @Mock
    JCRService jcrService;
    @Mock
    Externalizer externalizer;
    @Mock
    Iterator<Resource> statesIterator;
    @Mock
    HertzConfigFactory hConfigFactory;

    @Mock
    MicroServicesConfigurationFactory mcFactory;
    @Mock
    ValueMap valuemap;
    @Mock
    Map<String, Object> map = new HashMap<>();
    Class<?> servclass;
    Field fields[];
    @Mock
    private static Dictionary<String, Object> properties;
    Method methods[];
    Logger LOGGER;

    /**
     * @throws java.lang.Exception
     */
    @SuppressWarnings("deprecation")
    @Before
    public void setUp() throws Exception {
        rqCodesCoordinatorServiceImpl = new LocationCoordinatorServicempl();
        MockitoAnnotations.initMocks(this);
        LOGGER = mock(Logger.class);
        PowerMockito.mockStatic(DataUse.class);
        PowerMockito.mockStatic(HertzUtils.class);
        PowerMockito.when(systemService.getServiceResourceResolver())
                .thenReturn(mockResResolver);
        PowerMockito.when(mockResResolver.adaptTo(Session.class))
                .thenReturn(mockSession);
        PowerMockito.when(mockResResolver.adaptTo(PageManager.class))
                .thenReturn(pagemanager);
        PowerMockito.when(pagemanager.getPage(Mockito.anyString()))
                .thenReturn(page);

        PowerMockito.when(page.getContentResource()).thenReturn(childResource);
        PowerMockito.when(childResource.getValueMap()).thenReturn(valueMap);
        PowerMockito.when(valueMap.get(HertzConstants.JCR_CQ_TEMPLATE))
                .thenReturn("location");

        servclass = rqCodesCoordinatorServiceImpl.getClass();
        fields = servclass.getDeclaredFields();
        // properties.put(HertzConstants.IDENTIFIER, "location");
        // PowerMockito.when(mockComponentContext.getProperties()).thenReturn(properties);

        PrivateAccessor.setField(rqCodesCoordinatorServiceImpl,
                "hConfigFactory", hConfigFactory);
        PrivateAccessor.setField(rqCodesCoordinatorServiceImpl, "mcFactory",
                mcFactory);
        PrivateAccessor.setField(rqCodesCoordinatorServiceImpl, "properties",
                properties);
        String[] basePaths = new String[] { TestConstants.CONTENT_DATA_PATH,
                TestConstants.CONTENT_MOBILE_DATA_PATH };
        String[] mappingsDataCoord = new String[] {
                "/rates-config/default-rq-code:rqcodes",
                "/locations:location" };
        String[] dataApi = new String[] {
                "rqcodes:/api/rateCodeService/defaultRateCode",
                "location:/api/locationService" };
        when(hConfigFactory.getPropertyValue(
                Mockito.eq(HertzConstants.HERTZ_DATA_BASEPATH)))
                        .thenReturn(basePaths);
        when(hConfigFactory.getPropertyValue(
                Mockito.eq(HertzConstants.HERTZ_DATA_COORDINATOR_MAPPING)))
                        .thenReturn(mappingsDataCoord);
        when(hConfigFactory.getPropertyValue(
                Mockito.eq(HertzConstants.HERTZ_DATA_API_MAPPING)))
                        .thenReturn(dataApi);

        when(hConfigFactory.getPropertyValue(
                Mockito.eq(HertzConstants.HERTZ_DATA_TEMPLATE_WHITELIST)))
                        .thenReturn(mappingsDataCoord);

        when(properties.get(Mockito.anyString())).thenReturn("/locations");

    }

    @Test
    public void testActivate() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        // ComponentContext context = Mockito.mock(ComponentContext.class);
        Method activate = rqCodesCoordinatorServiceImpl.getClass()
                .getDeclaredMethod("activate", ComponentContext.class);
        activate.setAccessible(true);
        activate.invoke(rqCodesCoordinatorServiceImpl, mockComponentContext);
    }

    @Test
    public void testGetCollatedData()
            throws JSONException, RepositoryException {
        CoordinatorConsumable consumable = rqCodesCoordinatorServiceImpl
                .getCollatedData(TestConstants.LOCATION_PATH + "/locations");
        Assert.assertNotNull(consumable);
    }

    @Test
    public void testgetSpecificDataPath() {
        String[] mappingsDataCoord = new String[] {
                "/rates-config/default-rq-code:rqcodes",
                "/locations:location" };
        String path = rqCodesCoordinatorServiceImpl.getSpecificDataPath(
                TestConstants.LOCATION_PATH, TestConstants.CONTENT_DATA_PATH,
                mappingsDataCoord);
        Assert.assertNotNull(path);

        mappingsDataCoord = new String[] { "/rates-config/default-rq-code:",
                "/locations:" };
        path = rqCodesCoordinatorServiceImpl.getSpecificDataPath(
                TestConstants.LOCATION_PATH, TestConstants.CONTENT_DATA_PATH,
                mappingsDataCoord);
        Assert.assertNotNull(path);
    }

    @Test
    public void testformRequestParams()
            throws JSONException, RepositoryException {
        JSONObject jsonObj = new JSONObject(
                "{\"location\":{\"hoursofoperation3AriaLabel\":\"Hours Of Operation 3 Aria Label\"}}");
        when(mockResResolver.getResource(TestConstants.LOCATION_PATH))
                .thenReturn(resource);
        when(resource.getName()).thenReturn("location");
        when(resource.getResourceResolver()).thenReturn(mockResResolver);
        when(resource.getPath())
                .thenReturn("/content/hertz-rac/data/location/in/chicago/ORD1");
        when(HertzUtils.getServiceReference(Externalizer.class))
                .thenReturn(externalizer);
        when(HertzUtils.getServiceReference(JCRService.class))
                .thenReturn(jcrService);
        when(DataUse.getComponentlocationOverlay(
                "/content/hertz-rac/data/location/in/chicago/ORD1",
                mockResResolver, jcrService, externalizer)).thenReturn(jsonObj);
        String json = rqCodesCoordinatorServiceImpl
                .formRequestParams(TestConstants.LOCATION_PATH, resource);
        Assert.assertNotNull(json);

    }

}
