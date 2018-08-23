package com.hertz.digital.platform.service.impl;

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
public class TestLinkTypeCoordinatorServiceImpl {

    @InjectMocks
    private LinkTypeCoordinatorServiceImpl linkTypeCoordinatorServiceImpl;

    @Mock
    private SystemUserService systemService;
    @Mock
    private ResourceResolver mockResResolver;

    @Mock
    PageManager pagemanager;

    @Mock
    Page page;

    @Mock
    Page dataPage;

    @Mock
    Resource childResource;
    
    @Mock
    Resource parResource;

    @Mock
    Session session;

    @Mock
    QueryBuilder builder;

    @Mock
    Query query;

    @Mock
    Resource resource1;

    @Mock
    SearchResult result;

    List<Hit> mockList;

    @Mock
    Hit hit;

    @Mock
    Iterable<Resource> iterable;

    @Mock
    Iterator<Resource> iterator;

    @Mock
    Resource childJcrResource;

    @Mock
    ValueMap valueMap;

    @Mock
    ValueMap valueMap1;

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

    @Before
    public void setUp() throws Exception {
        linkTypeCoordinatorServiceImpl = new LinkTypeCoordinatorServiceImpl();
        MockitoAnnotations.initMocks(this);
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
        servclass = linkTypeCoordinatorServiceImpl.getClass();
        fields = servclass.getDeclaredFields();
        PrivateAccessor.setField(linkTypeCoordinatorServiceImpl,
                "hConfigFactory", hConfigFactory);
        PrivateAccessor.setField(linkTypeCoordinatorServiceImpl, "mcFactory",
                mcFactory);
        PrivateAccessor.setField(linkTypeCoordinatorServiceImpl, "properties",
                properties);
        String[] basePaths = new String[] { TestConstants.CONTENT_DATA_PATH,
                TestConstants.CONTENT_MOBILE_DATA_PATH };
        String[] mappingsDataCoord = new String[] {
                "/rates-config/default-rq-code:rqcodes", "/locations:location",
                "/link-types:linktype" };
        String[] dataApi = new String[] {
                "rqcodes:/api/rateCodeService/defaultRateCode",
                "location:/api/locationService",
                "linktype:/api/cacheUploadService/v1/loadcacheinformation" };
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
        Method activate = linkTypeCoordinatorServiceImpl.getClass()
                .getDeclaredMethod("activate", ComponentContext.class);
        activate.setAccessible(true);
        activate.invoke(linkTypeCoordinatorServiceImpl, mockComponentContext);
    }

    @Test
    public void testGetCollatedData()
            throws JSONException, RepositoryException {
        CoordinatorConsumable consumable = linkTypeCoordinatorServiceImpl
                .getCollatedData(TestConstants.LOCATION_PATH + "/locations");
        Assert.assertNotNull(consumable);
    }

    @Test
    public void testgetSpecificDataPath() {
        String[] mappingsDataCoord = new String[] {
                "/rates-config/default-rq-code:rqcodes", "/locations:location",
                "/link-types:linktype" };
        String path = linkTypeCoordinatorServiceImpl.getSpecificDataPath(
                TestConstants.LOCATION_PATH, TestConstants.CONTENT_DATA_PATH,
                mappingsDataCoord);
        Assert.assertNotNull(path);

        mappingsDataCoord = new String[] { "/rates-config/default-rq-code:",
                "/locations:" };
        path = linkTypeCoordinatorServiceImpl.getSpecificDataPath(
                TestConstants.LOCATION_PATH, TestConstants.CONTENT_DATA_PATH,
                mappingsDataCoord);
        Assert.assertNotNull(path);
    }

    @Test
    public void testformRequestParams()
            throws JSONException, RepositoryException {
        when(mockResResolver.getResource(TestConstants.ERROR_DATA_PATH))
                .thenReturn(resource);
        when(resource.getPath()).thenReturn(TestConstants.ERROR_DATA_PATH);
        when(resource.adaptTo(Page.class)).thenReturn(dataPage);
        when(dataPage.getContentResource()).thenReturn(childJcrResource);
        when(childJcrResource.getChild(HertzConstants.PAR)).thenReturn(parResource);
        when(parResource.getChildren()).thenReturn(iterable);
        when(iterable.iterator()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true, false);
        when(iterator.next()).thenReturn(resource1);
        when(resource1.getResourceType()).thenReturn(TestConstants.LINK_TYPE_RES);
        when(resource1.getValueMap()).thenReturn(valueMap);
        when(valueMap.containsKey(HertzConstants.KEY)).thenReturn(true);
        when(valueMap.containsKey(HertzConstants.VALUE)).thenReturn(true);
        when(valueMap.get(HertzConstants.KEY, String.class)).thenReturn("errorCode");
        when(valueMap.get(HertzConstants.VALUE, String.class)).thenReturn("errorValue");
        when(HertzUtils.isValidResource(resource)).thenReturn(true);
        when(HertzUtils.isValidResource(childJcrResource)).thenReturn(true);
        when(HertzUtils.isValidResource(parResource)).thenReturn(true);
        when(HertzUtils.isValidPage(dataPage)).thenReturn(true);
        linkTypeCoordinatorServiceImpl
                .formRequestParams(TestConstants.ERROR_DATA_PATH, resource);
    }
    
    @Test
    public void testformRequestParams4()
            throws JSONException, RepositoryException {
        when(mockResResolver.getResource(TestConstants.ERROR_DATA_PATH))
                .thenReturn(resource);
        when(resource.getPath()).thenReturn(TestConstants.ERROR_DATA_PATH);
        when(resource.adaptTo(Page.class)).thenReturn(dataPage);
        when(dataPage.getContentResource()).thenReturn(childJcrResource);
        when(childJcrResource.getChild(HertzConstants.PAR)).thenReturn(parResource);
        when(parResource.getChildren()).thenReturn(iterable);
        when(iterable.iterator()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true, false);
        when(iterator.next()).thenReturn(resource1);
        when(resource1.getResourceType()).thenReturn(TestConstants.LINK_TYPE_RES);
        when(resource1.getValueMap()).thenReturn(valueMap);
        when(valueMap.containsKey(HertzConstants.KEY)).thenReturn(true);
        when(valueMap.containsKey(HertzConstants.VALUE)).thenReturn(false);
        when(valueMap.get(HertzConstants.KEY, String.class)).thenReturn("errorCode");
        when(valueMap.get(HertzConstants.VALUE, String.class)).thenReturn("");
        when(HertzUtils.isValidResource(resource)).thenReturn(true);
        when(HertzUtils.isValidResource(childJcrResource)).thenReturn(true);
        when(HertzUtils.isValidResource(parResource)).thenReturn(true);
        when(HertzUtils.isValidPage(dataPage)).thenReturn(true);
        linkTypeCoordinatorServiceImpl
                .formRequestParams(TestConstants.ERROR_DATA_PATH, resource);
    }

    @Test
    public void testformRequestParams1()
            throws JSONException, RepositoryException {
        when(mockResResolver.getResource(TestConstants.ERROR_DATA_PATH))
                .thenReturn(resource);
        when(resource.getPath()).thenReturn(TestConstants.ERROR_DATA_PATH);
        when(resource.adaptTo(Page.class)).thenReturn(dataPage);
        when(dataPage.getContentResource()).thenReturn(childJcrResource);
        when(resource.getChildren()).thenReturn(iterable);
        when(iterable.iterator()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true, false);
        when(iterator.next()).thenReturn(resource1);
        when(resource1.getResourceType()).thenReturn(TestConstants.LINK_TYPE_RES);
        when(HertzUtils.isValidResource(resource)).thenReturn(true);
        when(HertzUtils.isValidResource(childJcrResource)).thenReturn(true);
        when(HertzUtils.isValidResource(resource)).thenReturn(true);
        when(HertzUtils.isValidPage(dataPage)).thenReturn(true);
        linkTypeCoordinatorServiceImpl
                .formRequestParams(TestConstants.ERROR_DATA_PATH, resource);
    }

    @Test
    public void testformRequestParams2()
            throws JSONException, RepositoryException {
        when(mockResResolver.getResource(TestConstants.ERROR_DATA_PATH))
                .thenReturn(resource);
        when(resource.getPath()).thenReturn(TestConstants.ERROR_DATA_PATH);
        when(resource.adaptTo(Page.class)).thenReturn(dataPage);
        when(dataPage.getContentResource()).thenReturn(null);
        when(resource.getChildren()).thenReturn(iterable);
        when(iterable.iterator()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true, false);
        when(iterator.next()).thenReturn(resource1);
        when(HertzUtils.isValidResource(childJcrResource)).thenReturn(false);
        when(HertzUtils.isValidResource(resource)).thenReturn(true);
        when(HertzUtils.isValidPage(dataPage)).thenReturn(true);
        linkTypeCoordinatorServiceImpl
                .formRequestParams(TestConstants.ERROR_DATA_PATH, resource);
    }

    @Test
    public void testformRequestParams3()
            throws JSONException, RepositoryException {
        when(mockResResolver.getResource(TestConstants.ERROR_DATA_PATH))
                .thenReturn(resource);
        when(resource.getPath()).thenReturn(TestConstants.ERROR_DATA_PATH);
        when(resource.adaptTo(Page.class)).thenReturn(null);
        when(dataPage.getContentResource()).thenReturn(null);
        when(resource.getChildren()).thenReturn(iterable);
        when(iterable.iterator()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true, false);
        when(iterator.next()).thenReturn(resource1);
        when(HertzUtils.isValidResource(childJcrResource)).thenReturn(true);
        when(HertzUtils.isValidResource(resource)).thenReturn(true);
        when(HertzUtils.isValidPage(dataPage)).thenReturn(false);
        linkTypeCoordinatorServiceImpl
                .formRequestParams(TestConstants.ERROR_DATA_PATH, resource);
    }

}
