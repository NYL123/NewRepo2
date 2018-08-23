package com.hertz.digital.platform.use;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.oak.commons.PropertiesUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.google.gson.Gson;
import com.hertz.digital.platform.bean.TermsAndConditionBean;
import com.hertz.digital.platform.bean.TermsAndConditionsSectionBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.service.api.JCRService;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * This class is used for generating the json for important information and
 * terms and conditions of use.
 * 
 * @author a.dhingra
 *
 */
public class TermsAndConditionsUse extends WCMUsePojo {

	/**
	 * Default Constructor Declaration
	 */
	public TermsAndConditionsUse() {
		super();
	}

	/**
	 * LOGGER instantiation
	 */

	private static final Logger logger = LoggerFactory.getLogger(TermsAndConditionsUse.class);

	/**
	 * Declaring variable for the json string.
	 * */
	private String jsonString;
	
	
	/**
	 * This method creates the json for the terms and conditions pages.
	 * 
	 */
	@Override
	public void activate() throws Exception {
		logger.debug("Entering method Activate() of TermsAndConditionsUse class");
	
		Map<Integer, String> termsConditionsMap = new TreeMap<>();
		Resource rateTypeResource = getResource().getParent();
		if (null != rateTypeResource && !ResourceUtil.isNonExistingResource(rateTypeResource)) {
			Resource destinationCountryResource = rateTypeResource.getParent();
			if (null != destinationCountryResource) {
				Resource sourceCountryResource = destinationCountryResource.getParent();
				Map<String, String> sourcePredicateMap = new HashMap<>();
				Map<String, String> destinationPredicateMap = new HashMap<>();
				createPredicateMap(sourceCountryResource, sourcePredicateMap);
				logger.debug("The predicate map looks like this:- {}", sourcePredicateMap);
				createPredicateMap(destinationCountryResource, destinationPredicateMap);
				logger.debug("The predicate map looks like this:- {}", destinationPredicateMap);
				JCRService jcrService = getSlingScriptHelper().getService(JCRService.class);
				if (null != jcrService) {
					SearchResult sourceSearchResults = jcrService.searchResults(getResourceResolver(),
							sourcePredicateMap);
					logger.debug("Numbr of hits:- {}", sourceSearchResults.getHits().size());
					buildTermsAndConditionsMap(termsConditionsMap, rateTypeResource, sourceSearchResults);
					SearchResult destinationSearchResults = jcrService.searchResults(getResourceResolver(),
							destinationPredicateMap);
					logger.debug("Numbr of hits:- {}", destinationSearchResults.getHits().size());
					buildTermsAndConditionsMap(termsConditionsMap, rateTypeResource, destinationSearchResults);
				}

			}

		}
		logger.debug("The terms and conditions Map looks like this:- {}", termsConditionsMap);
		List<TermsAndConditionsSectionBean> list=new ArrayList<>();
		TermsAndConditionBean termsAndConditions=new TermsAndConditionBean();
		for(Map.Entry<Integer, String> entrySet : termsConditionsMap.entrySet()){
			TermsAndConditionsSectionBean bean=new TermsAndConditionsSectionBean();
			bean.setOrderNumber(entrySet.getKey());
			bean.setSectionDescription(entrySet.getValue());
			list.add(bean);
		}
		termsAndConditions.setTermsAndConditions(list);
		Gson gson = HertzUtils.initGsonBuilder(true, true, true);
		setJsonString(gson.toJson(termsAndConditions));
		logger.debug("Exiting method Activate() of TermsAndConditionsUse class");
	}

	/**
	 * This method iterates over all the nodes under par and creates the terms
	 * and conditions map that will consist of all the sections in ascending
	 * order of the order number.
	 * 
	 * 
	 * @param termsConditionsMap 
	 * 							Map containing the sections in order of order number
	 * @param rateTypeResource 
	 * 							Resource for the rate type
	 * @param sourceSearchResults 
	 * 							Search results of the query for all the par nodes.
	 * @throws RepositoryException
	 * @throws JSONException
	 */
	private void buildTermsAndConditionsMap(Map<Integer, String> termsConditionsMap, Resource rateTypeResource,
			SearchResult sourceSearchResults) throws RepositoryException, JSONException {
		for (Hit hit : sourceSearchResults.getHits()) {
			logger.debug("Processing for par resource:- {}", hit.getPath());
			Resource parResource = hit.getResource();
			Iterator<Resource> iterator=parResource.listChildren();
			while(iterator.hasNext()){
				Resource hertzRteResource=iterator.next();
				ValueMap map = hertzRteResource.getValueMap();
				String text = PropertiesUtil.toString(map.get(HertzConstants.TEXT), StringUtils.EMPTY);
				String[] rateTypesConfig = PropertiesUtil.toStringArray(map.get(HertzConstants.RATE_TYPE_CONFIG));
				if (null != rateTypesConfig && rateTypesConfig.length != 0) {
					setTermsMap(termsConditionsMap, rateTypeResource, text, rateTypesConfig);
				}
			}

		}
	}

	/**
	 * Sets the terms map.
	 *
	 * @param termsConditionsMap the terms conditions map
	 * @param rateTypeResource the rate type resource
	 * @param text the text
	 * @param rateTypesConfig the rate types config
	 * @throws JSONException the JSON exception
	 */
	private void setTermsMap(Map<Integer, String> termsConditionsMap, Resource rateTypeResource, String text,
			String[] rateTypesConfig) throws JSONException {
		for (int index = 0; index < rateTypesConfig.length; index++) {
			JSONObject object = new JSONObject(rateTypesConfig[index]);
			if (object.getString(HertzConstants.ORDER_NUMBER) != null && object.getString(HertzConstants.RATE_TYPE) != null) {
				Integer orderNumber = Integer.parseInt(object.getString(HertzConstants.ORDER_NUMBER));
				logger.debug("Order number : - " + orderNumber);
				String rateType = object.getString(HertzConstants.RATE_TYPE);
				logger.debug("rateType:- " + rateType);
				if (rateType.equalsIgnoreCase(rateTypeResource.getPath())) {
					termsConditionsMap.put(orderNumber, text);
				}
			}

		}
	}

	/**
	 * This method creates the predicate map for creating the query.
	 * 
	 * @param sourceCountryResource 
	 * 								Resource of the source country
	 * @param predicateMap 
	 * 						Map for the query
	 */
	private void createPredicateMap(Resource resource, Map<String, String> predicateMap) {
		predicateMap.put(HertzConstants.TYPE, "nt:unstructured");
		predicateMap.put(HertzConstants.PATH, resource.getPath());
		predicateMap.put("group.1_group.1_property", HertzConstants.SLING_RESOURCE_TYPE);
		predicateMap.put("group.1_group.1_property.value", HertzConstants.TOUCH_PARSYS_RES_TYPE);
		predicateMap.put("group.2_group.1_property", HertzConstants.SLING_RESOURCE_TYPE);
		predicateMap.put("group.2_group.1_property.value", HertzConstants.CLASSIC_PARSYS_RES_TYPE);
		predicateMap.put("group.3_group.1_property", HertzConstants.SLING_RESOURCE_TYPE);
		predicateMap.put("group.3_group.1_property.value", HertzConstants.RESPONSIVE_GRID_RES_TYPE);
		predicateMap.put("group.p.or", "true");
		predicateMap.put(HertzConstants.LIMIT, "-1");
	}

	/**
	 * This method is used to get the json string
	 * in the html.
	 * 
	 * @return the jsonString
	 */
	public String getJsonString() {
		return jsonString;
	}

	/**
	 * This method is used to set the json string.
	 * 
	 * @param jsonString 
	 * 					the jsonString to set
	 */
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

}
