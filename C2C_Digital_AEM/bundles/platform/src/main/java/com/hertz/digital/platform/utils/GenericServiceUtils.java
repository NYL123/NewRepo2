/**
 * 
 */
package com.hertz.digital.platform.utils;

import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Iterables;
import com.hertz.digital.platform.constants.HertzConstants;

/**
 * This is the utility class for getting the data json for any of the pages.
 * 
 * @author a.dhingra
 *
 */
public final class GenericServiceUtils {
    /**
     * Default Constructor declaration
     * 
     */
    private GenericServiceUtils() {
        // to prevent instantiation.
    }

    /** LOGGER instantiation. */
    private static final Logger logger = LoggerFactory
            .getLogger(GenericServiceUtils.class);

    /**
     * This method is used to return a JSONObject containing all the data key
     * and value pairs.
     * 
     * @param resolver
     * @param configPageResource
     * @return
     * @throws JSONException
     * @throws RepositoryException
     */
    public static JSONObject setConfigJSONObject(Resource configPageResource)
            throws JSONException, RepositoryException {
        logger.debug("Start:- setConfigJSONObject() of GenericServiceUtils");
        JSONObject configObject = new JSONObject();

        if (null != configPageResource
                && !ResourceUtil.isNonExistingResource(configPageResource)) {
            setConfigObject(configPageResource, configObject);
        }

        logger.debug("Exit:- setConfigJSONObject() of GenericServiceUtils");
        return configObject;
    }

    /**
     * This method is used to set the configObject with the key and values of
     * the data-pair components.
     * 
     * @param pageResource
     * @param object
     * @throws JSONException
     * @throws RepositoryException
     */
    public static void setConfigObject(Resource pageResource, JSONObject object)
            throws JSONException, RepositoryException {
        logger.debug("Start:- setConfigObject() of GenericServiceUtils");
        int size = Iterables.size(pageResource.getChildren());

        if (size > 1) {
            setChildPageValuesInObject(object, pageResource);
        } else {
            Resource jcrContentResource = pageResource
                    .getChild(HertzConstants.JCR_CONTENT);
            if (null != jcrContentResource && !ResourceUtil
                    .isNonExistingResource(jcrContentResource)) {
                setValueInObject(jcrContentResource, object);
            }
        }

        logger.debug("Exit:- setConfigObject() of GenericServiceUtils");
    }

    /**
     * This method is used to find all the child pages created by data template
     * and set all their key-value pairs in the object.
     * 
     * @param object
     * @param pageResource
     * @param resolver
     * @throws RepositoryException
     * @throws JSONException
     */
    private static void setChildPageValuesInObject(JSONObject object,
            Resource pageResource) throws RepositoryException, JSONException {
        logger.debug(
                "Start:- setChildPageValuesInObject() of GenericServiceUtils");
        Iterable<Resource> secondLevelResources = pageResource.getChildren();
        // Create a Query instance
        for (Resource secondLevelResource : secondLevelResources) {
            getSecondLevelJson(secondLevelResource, object);
        }
        logger.debug(
                "End:- setChildPageValuesInObject() of GenericServiceUtils");
    }

    /**
     * This method gets all second level child resources and puts in a json
     * object
     * 
     * @param secondLevelResource
     * @param object
     * @throws JSONException
     */
    public static void getSecondLevelJson(Resource secondLevelResource,
            JSONObject object) throws JSONException {
        logger.debug("Start:- getSecondLevelJson() of GenericServiceUtils");
        if (!secondLevelResource.getName()
                .equalsIgnoreCase(HertzConstants.JCR_CONTENT)) {
            JSONObject childObject = new JSONObject();
            JSONObject jsonObject = new JSONObject();
            Resource jcrContentResource = secondLevelResource
                    .getChild(HertzConstants.JCR_CONTENT);
            setValueInObject(jcrContentResource, childObject);
            if (childObject.length() > 0) {
                object.put(secondLevelResource.getName(), childObject);
            }
            Iterable<Resource> thirdLevelResources = secondLevelResource
                    .getChildren();
            for (Resource thirdLevelResource : thirdLevelResources) {
                getThirdLevelJson(thirdLevelResource, jsonObject);
            }
            if (jsonObject.length() > 0) {
                object.put(secondLevelResource.getName(), jsonObject);
            }
        }
        if (secondLevelResource.getName()
                .equalsIgnoreCase(HertzConstants.JCR_CONTENT)) {
            setValueInObject(secondLevelResource, object);
        }
        logger.debug("End:- getSecondLevelJson() of GenericServiceUtils");
    }

    /**
     * This method gets all third level child resources and puts in a json
     * object
     * 
     * @param thirdLevelResource
     * @param jsonObject
     * @throws JSONException
     */
    public static void getThirdLevelJson(Resource thirdLevelResource,
            JSONObject jsonObject) throws JSONException {
        logger.debug("Start:- getThirdLevelJson() of GenericServiceUtils");
        if (!thirdLevelResource.getName()
                .equalsIgnoreCase(HertzConstants.JCR_CONTENT)) {
            JSONObject childObject = new JSONObject();
            JSONObject object = new JSONObject();
            Resource jcrContentResource = thirdLevelResource
                    .getChild(HertzConstants.JCR_CONTENT);
            setValueInObject(jcrContentResource, childObject);
            if (childObject.length() > 0) {
                jsonObject.put(thirdLevelResource.getName(), childObject);
            }
            Iterable<Resource> fourthLevelResources = thirdLevelResource
                    .getChildren();
            for (Resource fourthLevelResource : fourthLevelResources) {
                getFourthLevelJson(fourthLevelResource, object);
            }
            if (object.length() > 0) {
                jsonObject.put(thirdLevelResource.getName(), object);
            }
        }
        logger.debug("End:- getThirdLevelJson() of GenericServiceUtils");
    }

    /**
     * This method gets all fourth level child resources and puts in a json
     * object
     * 
     * @param fourthLevelResource
     * @param jsonObject
     * @throws JSONException
     */
    public static void getFourthLevelJson(Resource fourthLevelResource,
            JSONObject jsonObject) throws JSONException {
        logger.debug("Start:- getFourthLevelJson() of GenericServiceUtils");
        if (!fourthLevelResource.getName()
                .equalsIgnoreCase(HertzConstants.JCR_CONTENT)) {
            JSONObject childObject = new JSONObject();
            JSONObject object = new JSONObject();
            Resource jcrContentResource = fourthLevelResource
                    .getChild(HertzConstants.JCR_CONTENT);
            setValueInObject(jcrContentResource, childObject);
            if (childObject.length() > 0) {
                jsonObject.put(fourthLevelResource.getName(), childObject);
            }
            Iterable<Resource> fifthLevelResources = fourthLevelResource
                    .getChildren();
            for (Resource fifthLevelResource : fifthLevelResources) {
                getFifthLevelJson(fifthLevelResource, object);
            }
            if (object.length() > 0) {
                jsonObject.put(fourthLevelResource.getName(), object);
            }
        }
        logger.debug("End:- getFourthLevelJson() of GenericServiceUtils");
    }

    /**
     * This method gets all fifth level child resources and puts in a json
     * object
     * 
     * @param fifthLevelResource
     * @param jsonObject
     * @throws JSONException
     */
    public static void getFifthLevelJson(Resource fifthLevelResource,
            JSONObject jsonObject) throws JSONException {
        logger.debug("Start:- getFifthLevelJson() of GenericServiceUtils");
        if (!fifthLevelResource.getName()
                .equalsIgnoreCase(HertzConstants.JCR_CONTENT)) {
            JSONObject childObject = new JSONObject();
            JSONObject object = new JSONObject();
            Resource jcrContentResource = fifthLevelResource
                    .getChild(HertzConstants.JCR_CONTENT);
            setValueInObject(jcrContentResource, childObject);
            if (childObject.length() > 0) {
                jsonObject.put(fifthLevelResource.getName(), childObject);
            }
            Iterable<Resource> sixthLevelResources = fifthLevelResource
                    .getChildren();
            for (Resource sixthLevelResource : sixthLevelResources) {
                getSixthLevelJson(sixthLevelResource, object);
            }
            if (object.length() > 0) {
                jsonObject.put(fifthLevelResource.getName(), object);
            }
        }
        logger.debug("End:- getFifthLevelJson() of GenericServiceUtils");
    }

    /**
     * This method gets all sixth level child resources and puts in a json
     * object
     * 
     * @param sixthLevelResource
     * @param jsonObject
     * @throws JSONException
     */
    public static void getSixthLevelJson(Resource sixthLevelResource,
            JSONObject jsonObject) throws JSONException {
        logger.debug("Start:- getSixthLevelJson() of GenericServiceUtils");
        if (!sixthLevelResource.getName()
                .equalsIgnoreCase(HertzConstants.JCR_CONTENT)) {
            JSONObject childObject = new JSONObject();
            JSONObject object = new JSONObject();
            Resource jcrContentResource = sixthLevelResource
                    .getChild(HertzConstants.JCR_CONTENT);
            setValueInObject(jcrContentResource, childObject);
            if (childObject.length() > 0) {
                jsonObject.put(sixthLevelResource.getName(), childObject);
            }
            Iterable<Resource> seventhLevelResources = sixthLevelResource
                    .getChildren();
            for (Resource seventhLevelResource : seventhLevelResources) {
                getSeventhLevelJson(seventhLevelResource, object);
            }
            if (object.length() > 0) {
                jsonObject.put(sixthLevelResource.getName(), object);
            }
        }
        logger.debug("End:- getSixthLevelJson() of GenericServiceUtils");
    }

    /**
     * This method gets all Seventh level child resources and puts in a json
     * object
     * 
     * @param seventhLevelResource
     * @param jsonObject
     * @throws JSONException
     */
    public static void getSeventhLevelJson(Resource seventhLevelResource,
            JSONObject jsonObject) throws JSONException {
        logger.debug("Start:- getSeventhLevelJson() of GenericServiceUtils");
        if (!seventhLevelResource.getName()
                .equalsIgnoreCase(HertzConstants.JCR_CONTENT)) {
            JSONObject childObject = new JSONObject();
            Resource jcrContentResource = seventhLevelResource
                    .getChild(HertzConstants.JCR_CONTENT);
            setValueInObject(jcrContentResource, childObject);
        }
        if (seventhLevelResource.getName()
                .equalsIgnoreCase(HertzConstants.JCR_CONTENT)) {
            getJCRContentProperties(seventhLevelResource, jsonObject);
        }
        logger.debug("End:- getSeventhLevelJson() of GenericServiceUtils");
    }

    /**
     * Fetches the custom Properties from the jcr:content
     * 
     * @param seventhLevelResource
     *            Seventh Level resource in the Hierarchy
     * @param object
     *            JSOn Object for JSON Creation
     * @throws JSONException
     *             JsonException
     */
    @SuppressWarnings("rawtypes")
    public static void getJCRContentProperties(Resource seventhLevelResource,
            JSONObject object) throws JSONException {
        logger.info("inside getJCRContentProperties of GenericServiceUtils");
        Map<String, Object> configuredPropertiesMap = HertzUtils
                .getPagePropertiesConfigData(seventhLevelResource);
        ValueMap nodeProperties = seventhLevelResource.getValueMap();
        if (configuredPropertiesMap.size() > 0) {
            for (Map.Entry e : configuredPropertiesMap.entrySet()) {
                object.put(e.getKey().toString(), e.getValue());
            }
        } else {
            for (Map.Entry e : nodeProperties.entrySet()) {
                if (!e.getKey().toString().contains(HertzConstants.COLON)) {
                    object.put(e.getKey().toString(), e.getValue());
                }
            }
        }
    }

    /**
     * This method is used to set the key-value pairs in object
     * 
     * @param jcrContentResource
     * @param object
     * @throws JSONException
     */
    @SuppressWarnings("rawtypes")
    private static void setValueInObject(Resource jcrContentResource,
            JSONObject object) throws JSONException {
        logger.debug("Start:- setValueInObject() of GenericServiceUtils");
        ValueMap componentProperties = null;
        Resource parResource = null;
        if (null == jcrContentResource.getChild(HertzConstants.PAR)) {

            Map<String, Object> configuredPropertiesMap = HertzUtils
                    .getPagePropertiesConfigData(jcrContentResource);
            if (configuredPropertiesMap.size() > 0) {
                for (Map.Entry e : configuredPropertiesMap.entrySet()) {
                    object.put(e.getKey().toString(), e.getValue());
                }
            }

        } else {
            parResource = jcrContentResource.getChild(HertzConstants.PAR);
            Iterable<Resource> pairComponents = parResource.getChildren();
            for (Resource componentResource : pairComponents) {
                componentProperties = componentResource.getValueMap();
                setObject(componentResource, componentProperties, object);
            }
        }
        logger.debug("End:- setValueInObject() of GenericServiceUtils");
    }

    /**
     * This method checks if the value is a String or String[] and sets it in
     * the object.
     * 
     * @param componentProperties
     * @param object
     * @throws JSONException
     */
    private static void setObject(Resource componentResource,
            ValueMap componentProperties, JSONObject object)
            throws JSONException {
        logger.debug("Start:- setObject() of GenericServiceUtils");
        if (componentProperties.containsKey(HertzConstants.VALUE)) {
        	setObjectKeyValues(componentProperties, object);
        } else {
        	setComponentPropertiesInJson(componentResource, componentProperties, object);
        }
        logger.debug("End:- setObject() of GenericServiceUtils");
    }
    
    /**
     * This method sets the key and value in json object
     * @param componentProperties
     * @param object
     * @throws JSONException
     */
    private static void setObjectKeyValues(ValueMap componentProperties, 
    		JSONObject object) throws JSONException{
    	logger.debug("Start:- setObjectKeyValues() of GenericServiceUtils");
    	String key = StringUtils.trim(HertzUtils.getValueFromMap(componentProperties,
                HertzConstants.KEY));
        if (componentProperties
                .get(HertzConstants.VALUE) instanceof String) {
            object.put(key, StringUtils.trim(componentProperties.get(HertzConstants.VALUE,
                    String.class)));
        } else if (componentProperties
                .get(HertzConstants.VALUE) instanceof String[]) {
            String[] values = componentProperties.get(HertzConstants.VALUE,
                    String[].class);
            JSONArray jsonValues = getJSONArray(values);
            object.put(key, jsonValues);
        } else if (componentProperties
                .get(HertzConstants.VALUE) instanceof Boolean) {
            object.put(key, componentProperties.get(HertzConstants.VALUE,
                    Boolean.class));
        }
        logger.debug("End:- setObjectKeyValues() of GenericServiceUtils");
    }
    
    /**
     * This method sets the component properties in json object
     * @param componentResource
     * @param componentProperties
     * @param object
     * @throws JSONException
     */
    @SuppressWarnings("rawtypes")
	private static void setComponentPropertiesInJson(Resource componentResource,
            ValueMap componentProperties, JSONObject object) throws JSONException{
    	logger.debug("Start:- setComponentPropertiesInJson() of GenericServiceUtils");
    	Map<String, Object> configuredPropertiesMap = HertzUtils
                .getPagePropertiesConfigData(componentResource);
        if (configuredPropertiesMap.size() > 0) {
            for (Map.Entry e : configuredPropertiesMap.entrySet()) {
                object.put(e.getKey().toString(), e.getValue());
            }
        } else {
            for (Map.Entry e : componentProperties.entrySet()) {
                if (!e.getKey().toString().contains(HertzConstants.COLON)) {
                    object.put(e.getKey().toString(), e.getValue());
                }
            }
        }
        logger.debug("End:- setComponentPropertiesInJson() of GenericServiceUtils");
    }

    /**
     * This method converts array to a json array
     * 
     * @param values
     * @return
     */
    public static JSONArray getJSONArray(String[] values) {
        logger.debug("Start:- getJSONArray() of GenericServiceUtils");
        JSONArray array = new JSONArray();
        for (int index = 0; index < values.length; index++) {
            array.put(StringUtils.trim(values[index]));
        }
        logger.debug("End:- getJSONArray() of GenericServiceUtils");
        return array;
    }

}
