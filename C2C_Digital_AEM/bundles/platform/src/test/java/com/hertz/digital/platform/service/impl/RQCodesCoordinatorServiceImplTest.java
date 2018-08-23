package com.hertz.digital.platform.service.impl;

import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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

import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.hertz.digital.platform.bean.CoordinatorConsumable;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.constants.TestConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.factory.MicroServicesConfigurationFactory;
import com.hertz.digital.platform.service.api.SystemUserService;

import junit.framework.Assert;
import junitx.util.PrivateAccessor;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoggerFactory.class })
public class RQCodesCoordinatorServiceImplTest {
    @InjectMocks
    private RQCodesCoordinatorServiceImpl rqCodesCoordinatorServiceImpl;
    @Mock
    private SystemUserService systemService;
    @Mock
    private ResourceResolver mockResResolver;

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
        rqCodesCoordinatorServiceImpl = new RQCodesCoordinatorServiceImpl();
        MockitoAnnotations.initMocks(this);
        LOGGER = mock(Logger.class);
        PowerMockito.when(systemService.getServiceResourceResolver())
                .thenReturn(mockResResolver);
        PowerMockito.when(mockResResolver.adaptTo(Session.class))
                .thenReturn(mockSession);
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
        when(properties.get(Mockito.anyString())).thenReturn("location");

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
        mockList = new ArrayList<Hit>();
        when(configPageResource.getChildren()).thenReturn(mockIterable);
        when(mockResResolver.getResource(Mockito.anyString()))
                .thenReturn(configPageResource);
        when(configPageResource.getParent()).thenReturn(configPageResource);
        when(mockIterable.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true, true, false);
        when(mockIterator.next()).thenReturn(childResource);
        when(childResource.getName()).thenReturn("us");
        when(childResource.getChild(HertzConstants.JCR_CONTENT))
                .thenReturn(childJcrResource);
        when(childJcrResource.getChild(HertzConstants.PAR))
                .thenReturn(parResource);
        when(parResource.getChildren()).thenReturn(mockIterable1);
        when(mockIterable1.iterator()).thenReturn(mockIterator1);
        when(mockIterator1.hasNext()).thenReturn(true, false);
        when(mockIterator1.next()).thenReturn(childResource);
        when(childResource.getValueMap()).thenReturn(valueMap);
        when(valueMap.containsKey("key")).thenReturn(true);
        when(valueMap.containsKey("value")).thenReturn(true);
        when(valueMap.get("key", String.class)).thenReturn("key");
        when(valueMap.get(HertzConstants.VALUE)).thenReturn("value");
        CoordinatorConsumable consumable = rqCodesCoordinatorServiceImpl
                .getCollatedData(TestConstants.LOCATION_PATH);
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
        mockList = new ArrayList<Hit>();
        when(configPageResource.getChildren()).thenReturn(mockIterable);
        when(mockResResolver.getResource(Mockito.anyString()))
                .thenReturn(configPageResource);
        when(configPageResource.getParent()).thenReturn(configPageResource);
        when(mockIterable.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true, true, false);
        when(mockIterator.next()).thenReturn(childResource);
        when(childResource.getName()).thenReturn("us");
        when(childResource.getChild(HertzConstants.JCR_CONTENT))
                .thenReturn(childJcrResource);
        when(childJcrResource.getChild(HertzConstants.PAR))
                .thenReturn(parResource);
        when(parResource.getChildren()).thenReturn(mockIterable1);
        when(mockIterable1.iterator()).thenReturn(mockIterator1);
        when(mockIterator1.hasNext()).thenReturn(true, false);
        when(mockIterator1.next()).thenReturn(childResource);
        when(childResource.getValueMap()).thenReturn(valueMap);
        when(valueMap.containsKey("key")).thenReturn(true);
        when(valueMap.containsKey("value")).thenReturn(true);
        when(valueMap.get("key", String.class)).thenReturn("key");
        when(valueMap.get(HertzConstants.VALUE)).thenReturn("value");

        String json = rqCodesCoordinatorServiceImpl.formRequestParams(
                TestConstants.LOCATION_PATH, configPageResource);
        Assert.assertNotNull(json);

    }

}
