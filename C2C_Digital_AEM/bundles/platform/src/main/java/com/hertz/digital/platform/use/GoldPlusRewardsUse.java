package com.hertz.digital.platform.use;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.ServletException;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.oak.commons.PropertiesUtil;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.commons.json.jcr.JsonItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.google.gson.Gson;
import com.hertz.digital.platform.bean.spa.SpaPageBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.exporter.impl.ConfigurableTextParSysExportor;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * A use class to generate the GoldPlusRewards json for native apps. This will
 * set a jsonString which will be returned when called from native.json.html
 * 
 * @author himanshu.i.sharma
 *
 */
public class GoldPlusRewardsUse extends WCMUsePojo {

	/**
	 * Default Constructor
	 */
	public GoldPlusRewardsUse() {
		super();
	}

	/**
	 * Logger instantiation
	 */
	protected static final Logger logger = LoggerFactory.getLogger(GoldPlusRewardsUse.class);

	/**
	 * Variable that contains the JSON String
	 */
	private String jsonString;

	/**
	 * This method sets the json string to be returned.
	 * 
	 * @throws JSONException
	 * @throws RepositoryException
	 * @throws IOException
	 * @throws ServletException
	 * 
	 */
	public void activate() throws JSONException, RepositoryException {
		logger.debug("Start:- activate() of GoldPlusRewardsUse");
		JSONObject object = new JSONObject();
		String requestPath = getRequest().getPathInfo();
		RequestPathInfo requestPathInfo = getRequest().getRequestPathInfo();
		if (requestPath.contains(HertzConstants.JCR_CONTENT) || requestPath.contains(HertzConstants.JCR_CONTENT_REQ)) {
			Node node = getResource().adaptTo(Node.class);
			StringWriter stringWriter = new StringWriter();
			JsonItemWriter jsonWriter = new JsonItemWriter(null);
			jsonWriter.dump(node, stringWriter, -1);
			object = new JSONObject(stringWriter.toString());
		} else {
			SpaPageBean spaBean = new SpaPageBean();
			Resource currentResource = getResource();
			spaBean.setPageTitle(getCurrentPage().getTitle());
			HertzUtils.setSeoMetaDataInBean(spaBean, currentResource, requestPathInfo, getResourceResolver());
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> configTextMap = new LinkedHashMap<>();
			Map<String, Object> beanMap = new HashMap<>();
			Resource parResource = currentResource.getChild(HertzConstants.CONFIG_TEXT_PARSYS);
			if (null != parResource) {
				configTextMap = ConfigurableTextParSysExportor.getConfigTextParsysMap(parResource,
						getResourceResolver());
			}
			if (beanMap.size() > 0) {
				list.add(beanMap);
			}
			if (!list.isEmpty()) {
				spaBean.setIncludedComponents(list);
			}
			if (MapUtils.isNotEmpty(configTextMap)) {
				spaBean.setConfiguredProps(configTextMap);
			}
			Gson gson = HertzUtils.initGsonBuilder(true, true, true);
			object.accumulate(HertzConstants.NATIVE_CONTENT, new JSONObject(gson.toJson(spaBean, SpaPageBean.class)));
		}
		setJsonString(object.toString());
		logger.debug("End:- activate() of GoldPlusRewardsUse");
	}

	/**
	 * This method retrieves the property values of link dropdown and sets them
	 * in a bean map
	 * 
	 * @param responsiveGrid
	 * @param beanMap
	 * @throws JSONException
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void createJsonForLinkDropdown(Resource linkDropDownResource, Map<String, Object> beanMap)
			throws JSONException {
		logger.debug("Start:- createJsonForLinkDropdown() of GoldPlusRewardsUse");
		Map<String, Object> dropDownValuesMap = new HashMap<>();
		if (null != linkDropDownResource) {
			ValueMap propertiesMap = linkDropDownResource.getValueMap();
				if (ArrayUtils
						.isNotEmpty(PropertiesUtil.toStringArray(propertiesMap.get(HertzConstants.DROPDOWN_VALUE_LIST)))) {
					String[] dropdownValuesList = PropertiesUtil
							.toStringArray(propertiesMap.get(HertzConstants.DROPDOWN_VALUE_LIST));
					getLinkDropdownValuesMap(dropDownValuesMap, dropdownValuesList);
					beanMap.put(HertzConstants.LINK_DROPDOWN, dropDownValuesMap);
			}
		}
		logger.debug("End:- createJsonForLinkDropdown() of GoldPlusRewardsUse");
	}

	/**
	 * This method retrieves the drop down values map
	 * @param dropDownValuesMap
	 * @param dropdownValuesList
	 * @param dropdownLabel
	 * @throws JSONException
	 */
    public static void getLinkDropdownValuesMap(Map<String, Object> dropDownValuesMap, 
    		String[] dropdownValuesList) throws JSONException {
		logger.debug("Start:- setLinkDropdownValuesInMap() of GoldPlusRewardsUse");
		if (null != dropdownValuesList) {
			List<Map<String, Object>> multiValuesList = new ArrayList<>();
			JSONArray propArray = new JSONArray(Arrays.asList(dropdownValuesList));
			JSONArray sortedJsonArray = new JSONArray();
			List<JSONObject> jsonList = new ArrayList<JSONObject>();
			sortDropDownValuesOnOrder(propArray, jsonList);
			for (int i = 0; i < propArray.length(); i++) {
			    sortedJsonArray.put(jsonList.get(i));
			}
			for (int index = 0; index < sortedJsonArray.length(); index++) {
				Map<String, Object> dropdownValueMap = new HashMap<>();
				JSONObject object = sortedJsonArray.getJSONObject(index);
				if (null != object) {
                    setLinkDropDownValuesInMap(dropdownValueMap, object);
					multiValuesList.add(dropdownValueMap);
				}
			}
			dropDownValuesMap.put(HertzConstants.LINK_DROPDOWN_VALUES, multiValuesList);
		}
		logger.debug("End:- setLinkDropdownValuesInMap() of GoldPlusRewardsUse");
    }
	
	/**
	 * This method sorts the drop down values based on order given by the author
	 * @param propArray
	 * @param jsonList
	 * @throws JSONException
	 */
    public static void sortDropDownValuesOnOrder(JSONArray propArray, List<JSONObject> jsonList) 
    		throws JSONException{
        logger.debug("Start:- sortDropDownValuesOnOrder() of GoldPlusRewardsUse");
		for (int i = 0; i < propArray.length(); i++) {
		    jsonList.add(propArray.getJSONObject(i));
		}
		Collections.sort( jsonList, new Comparator<JSONObject>() {
			public int compare(JSONObject a, JSONObject b) {
                        int valA = Integer.valueOf(0);
		                int valB = Integer.valueOf(0);
	                    try {
	                         valA = (int) a.getInt(HertzConstants.ORDER);
	                         valB = (int) b.getInt(HertzConstants.ORDER);
	                    } 
	                    catch (JSONException e) {
	        	            logger.error("Could not get JSON", e.getMessage());
	                    }
		        return Integer.compare(valA, valB);
		    }
		});
		logger.debug("End:- sortDropDownValuesOnOrder() of GoldPlusRewardsUse");
    }
	
	/**
	 * This method retrieves the drop down values and sets them in a map
	 * @param dropdownValueMap
	 * @param object
	 * @throws JSONException
	 */
    public static void setLinkDropDownValuesInMap(Map<String, Object> dropdownValueMap, 
    		JSONObject object) throws JSONException{
        logger.debug("Start:- setLinkDropDownValuesInMap() of GoldPlusRewardsUse");
        if(StringUtils.isNotEmpty(object.getString(HertzConstants
        		.DROPDOWN_VALUE))
				&& StringUtils.isNotEmpty(object.getString(HertzConstants.ORDER))) {
			dropDownValueUpdate(dropdownValueMap, object);
		}
        logger.debug("End:- setLinkDropDownValuesInMap() of GoldPlusRewardsUse");
	}

	/** 
	 * @param dropdownValueMap
	 * @param object
	 * @throws JSONException
	 */
	private static void dropDownValueUpdate(Map<String, Object> dropdownValueMap, JSONObject object)
			throws JSONException {
		dropdownValueMap.put(HertzConstants.LINK_DROPDOWN_COUNTRY_NAME,
				object.has(HertzConstants.DROPDOWN_VALUE)
						? object.getString(HertzConstants.DROPDOWN_VALUE) : StringUtils.EMPTY);
		dropdownValueMap.put(HertzConstants.LINK_DROPDOWN_COUNTRY_ORDER,
				object.has(HertzConstants.ORDER) 
				        ? object.getInt(HertzConstants.ORDER) : StringUtils.EMPTY);
		dropdownPopulate(dropdownValueMap, object);
	}

	/**
	 * @param dropdownValueMap
	 * @param object
	 * @throws JSONException
	 */
	private static void dropdownPopulate(Map<String, Object> dropdownValueMap, JSONObject object) throws JSONException {
		dropdownValueMap.put(HertzConstants.LINK_DROPDOWN_COUNTRY_URL,
				object.has(HertzConstants.LINK_DROPDOWN_PAGE_URL)
						? HertzUtils.shortenIfPath(object.getString(HertzConstants.LINK_DROPDOWN_PAGE_URL)) : StringUtils.EMPTY);
		dropdownValueMap.put(HertzConstants.LINK_DROPDOWN_COUNTRY_CODE,
				object.has(HertzConstants.LINK_DROPDOWN_CODE)
						? object.getString(HertzConstants.LINK_DROPDOWN_CODE) : StringUtils.EMPTY);
	}

	/**
	 * @return the jsonString
	 */
	public String getJsonString() {
		return jsonString;
	}

	/**
	 * @param jsonString
	 *            the jsonString to set
	 */
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

}
