package com.hertz.digital.platform.utils;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.google.common.collect.Iterables;
import com.hertz.digital.platform.constants.HertzConstants;

import junit.framework.Assert;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoggerFactory.class, HertzUtils.class, Iterables.class })
public class TestGenericServiceUtils {

    @Mock
    ResourceResolver resolver;
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
    Map<String, Object> configMap;

    @Mock
    Resource parentResource;

    @Before
    public final void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor<GenericServiceUtils> constructor = GenericServiceUtils.class
                .getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public final void testSetConfigJSONObject()
            throws JSONException, RepositoryException {
        mockList = new ArrayList<Hit>();
        when(configPageResource.getChildren()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockIterator);
        when(configPageResource.getChild(HertzConstants.JCR_CONTENT))
                .thenReturn(childJcrResource);
        when(childJcrResource.getChild(HertzConstants.PAR)).thenReturn(null);
        PowerMockito.mockStatic(Iterables.class);
        PowerMockito.mockStatic(HertzUtils.class);
        Map<String, Object> maptest = new HashMap<String, Object>();
        maptest.put("key1", "value1");
        maptest.put("key2", "value2");
        maptest.put("key3", "value3");
        when(HertzUtils.getPagePropertiesConfigData(childJcrResource))
                .thenReturn(maptest);
        Assert.assertNotNull(
                GenericServiceUtils.setConfigJSONObject(configPageResource));
        when(Iterables.size(mockIterable)).thenReturn(2);
        when(mockIterator.hasNext()).thenReturn(true, true, false);
        when(mockIterator.next()).thenReturn(childResource);
        when(childResource.getName()).thenReturn(HertzConstants.JCR_CONTENT);
        when(childResource.getChild(HertzConstants.JCR_CONTENT))
                .thenReturn(childJcrResource);
        when(childJcrResource.getChild(HertzConstants.PAR))
                .thenReturn(parResource);
        when(parResource.getChildren()).thenReturn(mockIterable1);
        when(mockIterable1.iterator()).thenReturn(mockIterator1);
        when(mockIterator1.hasNext()).thenReturn(true, false);
        when(mockIterator1.next()).thenReturn(sixthLevelResource);
        when(sixthLevelResource.getValueMap()).thenReturn(valueMap);
        when(HertzUtils.getValueFromMap(valueMap, HertzConstants.KEY))
                .thenReturn("key");
        when(HertzUtils.getPagePropertiesConfigData(sixthLevelResource))
                .thenReturn(maptest);
        when(valueMap.containsKey("value")).thenReturn(false);
        when(childJcrResource.getChildren()).thenReturn(mockIterable1);
        when(mockIterable1.iterator()).thenReturn(mockIterator1);
        when(mockIterator1.hasNext()).thenReturn(true, false);
        Assert.assertNotNull(
                GenericServiceUtils.setConfigJSONObject(configPageResource));
    }

    @Test
    public final void testGetThirdLevelJson()
            throws JSONException, RepositoryException {
        JSONObject jsonObject = new JSONObject();
        mockList = new ArrayList<Hit>();
        when(thirdLevelResource.getName()).thenReturn("us");
        when(thirdLevelResource.getChildren()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true, true, false);
        when(mockIterator.next()).thenReturn(sixthLevelResource);
        when(thirdLevelResource.getChild(HertzConstants.JCR_CONTENT))
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
        when(valueMap.get(HertzConstants.VALUE)).thenReturn(true);
        when(valueMap.get(HertzConstants.VALUE, Boolean.class))
                .thenReturn(true);
        when(sixthLevelResource.getName()).thenReturn("jcr:content");
        when(sixthLevelResource.getValueMap()).thenReturn(valueMap1);
        Set<Entry<String,
                Object>> properties = new HashSet<Entry<String, Object>>();
        Map.Entry<String, Object> entry_ignore = new AbstractMap.SimpleEntry<
                String, Object>("jcr:lastModified", "jcr:lastModified");
        Map.Entry<String, Object> entry_accept = new AbstractMap.SimpleEntry<
                String, Object>("testvalue", "testvalue");
        properties.add(entry_ignore);
        properties.add(entry_accept);
        when(valueMap1.entrySet()).thenReturn(properties);

        GenericServiceUtils.getThirdLevelJson(thirdLevelResource, jsonObject);
    }

    @Test
    public final void testGetSecondLevelJson()
            throws JSONException, RepositoryException {
        JSONObject jsonObject = new JSONObject();
        mockList = new ArrayList<Hit>();
        when(fifthLevelResource.getName()).thenReturn("jcr:content");
        when(fifthLevelResource.getValueMap()).thenReturn(valueMap);
        Set<Entry<String,
                Object>> properties = new HashSet<Entry<String, Object>>();
        Map.Entry<String, Object> entry_ignore = new AbstractMap.SimpleEntry<
                String, Object>("jcr:lastModified", "jcr:lastModified");
        Map.Entry<String, Object> entry_accept = new AbstractMap.SimpleEntry<
                String, Object>("testvalue", "testvalue");
        properties.add(entry_ignore);
        properties.add(entry_accept);
        when(valueMap.entrySet()).thenReturn(properties);

        GenericServiceUtils.getSecondLevelJson(fifthLevelResource, jsonObject);
        when(secondLevelResource.getName()).thenReturn("us");
        when(secondLevelResource.getChildren()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true, true, false);
        when(mockIterator.next()).thenReturn(secondLevelResource);
        when(secondLevelResource.getChild(HertzConstants.JCR_CONTENT))
                .thenReturn(childJcrResource);
        when(childJcrResource.getChild(HertzConstants.PAR))
                .thenReturn(parResource);
        when(parResource.getChildren()).thenReturn(mockIterable1);
        when(mockIterable1.iterator()).thenReturn(mockIterator1);
        when(mockIterator1.hasNext()).thenReturn(true, false);
        when(mockIterator1.next()).thenReturn(childResource);
        when(childResource.getValueMap()).thenReturn(valueMap);
        when(valueMap.containsKey("key")).thenReturn(true);
        when(valueMap.containsKey("value")).thenReturn(false);

        Map<String, Object> maptest = new HashMap<String, Object>();
        PowerMockito.mockStatic(HertzUtils.class);
        when(HertzUtils.getPagePropertiesConfigData(secondLevelResource))
                .thenReturn(maptest);
        when(secondLevelResource.getValueMap()).thenReturn(valueMap);
        GenericServiceUtils.getSecondLevelJson(secondLevelResource, jsonObject);

        maptest.put("key1", "value1");
        maptest.put("key2", "value2");
        maptest.put("key3", "value3");
        GenericServiceUtils.getSecondLevelJson(secondLevelResource, jsonObject);
    }

    @Test
    public final void testGetFourthLevelJson()
            throws JSONException, RepositoryException {
        JSONObject jsonObject = new JSONObject();
        mockList = new ArrayList<Hit>();
        when(fourthLevelResource.getName()).thenReturn("us");
        when(fourthLevelResource.getChildren()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true, true, false);
        when(mockIterator.next()).thenReturn(sixthLevelResource);
        when(fourthLevelResource.getChild(HertzConstants.JCR_CONTENT))
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
        when(valueMap.get(HertzConstants.VALUE)).thenReturn(true);
        when(valueMap.get(HertzConstants.VALUE, Boolean.class))
                .thenReturn(true);
        when(sixthLevelResource.getName()).thenReturn("jcr:content");
        when(sixthLevelResource.getValueMap()).thenReturn(valueMap1);
        Set<Entry<String,
                Object>> properties = new HashSet<Entry<String, Object>>();
        Map.Entry<String, Object> entry_ignore = new AbstractMap.SimpleEntry<
                String, Object>("jcr:lastModified", "jcr:lastModified");
        Map.Entry<String, Object> entry_accept = new AbstractMap.SimpleEntry<
                String, Object>("testvalue", "testvalue");
        properties.add(entry_ignore);
        properties.add(entry_accept);
        when(valueMap1.entrySet()).thenReturn(properties);

        GenericServiceUtils.getFourthLevelJson(fourthLevelResource, jsonObject);
    }

    @Test
    public final void testGetFifthLevelJson()
            throws JSONException, RepositoryException {
        JSONObject jsonObject = new JSONObject();
        mockList = new ArrayList<Hit>();
        when(fifthLevelResource.getName()).thenReturn("us");
        when(fifthLevelResource.getChildren()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true, true, false);
        when(mockIterator.next()).thenReturn(sixthLevelResource);
        when(fifthLevelResource.getChild(HertzConstants.JCR_CONTENT))
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
        when(valueMap.get(HertzConstants.VALUE)).thenReturn(true);
        when(valueMap.get(HertzConstants.VALUE, Boolean.class))
                .thenReturn(true);
        when(sixthLevelResource.getName()).thenReturn("jcr:content");
        when(sixthLevelResource.getValueMap()).thenReturn(valueMap1);
        Set<Entry<String,
                Object>> properties = new HashSet<Entry<String, Object>>();
        Map.Entry<String, Object> entry_ignore = new AbstractMap.SimpleEntry<
                String, Object>("jcr:lastModified", "jcr:lastModified");
        Map.Entry<String, Object> entry_accept = new AbstractMap.SimpleEntry<
                String, Object>("testvalue", "testvalue");
        properties.add(entry_ignore);
        properties.add(entry_accept);
        when(valueMap1.entrySet()).thenReturn(properties);

        GenericServiceUtils.getFifthLevelJson(fifthLevelResource, jsonObject);
    }

    @Test
    public final void testGetSixthLevelJson()
            throws JSONException, RepositoryException {
        JSONObject jsonObject = new JSONObject();
        mockList = new ArrayList<Hit>();
        when(sixthLevelResource.getName()).thenReturn("us");
        when(sixthLevelResource.getChild(HertzConstants.JCR_CONTENT))
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
        when(valueMap.get(HertzConstants.VALUE))
                .thenReturn(new String[] { "value1", "value2" });
        when(valueMap.get(HertzConstants.VALUE, String[].class))
                .thenReturn(new String[] { "value1", "value2" });

        when(sixthLevelResource.getChildren()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true, true, false);
        when(mockIterator.next()).thenReturn(fifthLevelResource);

        when(fifthLevelResource.getName()).thenReturn("jcr:content");
        when(fifthLevelResource.getValueMap()).thenReturn(valueMap1);
        Set<Entry<String,
                Object>> properties = new HashSet<Entry<String, Object>>();
        Map.Entry<String, Object> entry_ignore = new AbstractMap.SimpleEntry<
                String, Object>("jcr:lastModified", "jcr:lastModified");
        Map.Entry<String, Object> entry_accept = new AbstractMap.SimpleEntry<
                String, Object>("testvalue", "testvalue");
        properties.add(entry_ignore);
        properties.add(entry_accept);
        when(valueMap1.entrySet()).thenReturn(properties);
        GenericServiceUtils.getSixthLevelJson(sixthLevelResource, jsonObject);
    }

    @Test
    public final void testGetSeventhLevelJson()
            throws JSONException, RepositoryException {
        JSONObject jsonObject = new JSONObject();
        mockList = new ArrayList<Hit>();
        when(fifthLevelResource.getName()).thenReturn("jcr:content");
        when(fifthLevelResource.getValueMap()).thenReturn(valueMap);
        Set<Entry<String,
                Object>> properties = new HashSet<Entry<String, Object>>();
        Map.Entry<String, Object> entry_ignore = new AbstractMap.SimpleEntry<
                String, Object>("jcr:lastModified", "jcr:lastModified");
        Map.Entry<String, Object> entry_accept = new AbstractMap.SimpleEntry<
                String, Object>("testvalue", "testvalue");
        properties.add(entry_ignore);
        properties.add(entry_accept);
        when(valueMap.entrySet()).thenReturn(properties);

        GenericServiceUtils.getSeventhLevelJson(fifthLevelResource, jsonObject);

        when(fifthLevelResource.getName()).thenReturn("us");
        when(fifthLevelResource.getChild(HertzConstants.JCR_CONTENT))
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

        GenericServiceUtils.getSeventhLevelJson(fifthLevelResource, jsonObject);
    }

    @Test
    public final void testGetJCRContentProperties()
            throws JSONException, RepositoryException {
        JSONObject jsonObject = new JSONObject();
        PowerMockito.mockStatic(HertzUtils.class);
        Map<String, Object> maptest = new HashMap<String, Object>();
        when(HertzUtils.getPagePropertiesConfigData(fifthLevelResource))
                .thenReturn(maptest);
        when(fifthLevelResource.getValueMap()).thenReturn(valueMap);

        Set<Entry<String,
                Object>> properties = new HashSet<Entry<String, Object>>();
        Map.Entry<String, Object> entry_ignore = new AbstractMap.SimpleEntry<
                String, Object>("jcr:lastModified", "jcr:lastModified");
        Map.Entry<String, Object> entry_accept = new AbstractMap.SimpleEntry<
                String, Object>("testvalue", "testvalue");
        properties.add(entry_ignore);
        properties.add(entry_accept);
        when(valueMap.entrySet()).thenReturn(properties);
        GenericServiceUtils.getJCRContentProperties(fifthLevelResource,
                jsonObject);
        maptest.put("key1", "value1");
        maptest.put("key2", "value2");
        maptest.put("key3", "value3");
        GenericServiceUtils.getJCRContentProperties(fifthLevelResource,
                jsonObject);
    }

    @Test
    public final void testGetJsonArray()
            throws JSONException, RepositoryException {
        String[] values = new String[] { "value1", "value2", "value3" };
        Assert.assertNotNull(GenericServiceUtils.getJSONArray(values));
    }

    @Test
    public final void testSetConfigJSONObjectArray()
            throws JSONException, RepositoryException {
        valueMap.replace("value", new String[] { "abcd", "abcde" });
        mockList = new ArrayList<Hit>();
        when(configPageResource.getChildren()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true, true, false);
        when(mockIterator.next()).thenReturn(childResource);
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
        when(valueMap.get(HertzConstants.VALUE))
                .thenReturn(new String[] { "value", "value1" });
        when(valueMap.get(HertzConstants.VALUE, String[].class))
                .thenReturn(new String[] { "value", "value1" });
        when(childJcrResource.getParent()).thenReturn(parentResource);
        when(parentResource.getName()).thenReturn("name");
        Assert.assertNotNull(
                GenericServiceUtils.setConfigJSONObject(configPageResource));
    }

    @Test
    public final void testSetConfigJSONObjectBoolean()
            throws JSONException, RepositoryException {
        valueMap.replace("value", true);
        mockList = new ArrayList<Hit>();
        when(configPageResource.getChildren()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true, true, false);
        when(mockIterator.next()).thenReturn(childResource);
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
        when(valueMap.get(HertzConstants.VALUE)).thenReturn(true);
        when(valueMap.get(HertzConstants.VALUE, Boolean.class))
                .thenReturn(true);
        when(childJcrResource.getParent()).thenReturn(parentResource);
        when(parentResource.getName()).thenReturn("name");
        Assert.assertNotNull(
                GenericServiceUtils.setConfigJSONObject(configPageResource));
    }

}
