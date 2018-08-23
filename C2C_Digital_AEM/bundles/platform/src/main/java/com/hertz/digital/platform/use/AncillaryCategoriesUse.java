package com.hertz.digital.platform.use;

import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.adobe.cq.sightly.WCMUsePojo;
import com.google.common.collect.Iterables;

import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.utils.HertzUtils;
/**
 * Use class for setting the Ancillary Categorization
 * @author himanshu.i.sharma
 *
 */
public class AncillaryCategoriesUse extends WCMUsePojo {

	/**
	 * Default Constructor Declaration
	 */
	public AncillaryCategoriesUse() {
		super();
	}
	
	private static final String DESTINATION_COUNTRIES = "destinationcountries";
	private static final String CATEGORY_OPTIONS = "categoryoptions";
	private static final String CATEGORIES = "categories";
	private static final String SOURCE_COUNTRIES = "sourcecountries";
	private static final String ANCILLARY_CATEGORY_CONFIG = "ancillarycategory-config";
	private static final String CATEGORY_ID = "category-id";

	/**
	 * LOGGER instantiation
	 */
	private static final Logger logger = LoggerFactory.getLogger(AncillaryCategoriesUse.class);

	/**
	 * JSON String
	 */
	private String jsonString;
	
	/**
	 * Activate method that gets the page resource and sets the final JSON object
	 */
	@Override
	public void activate() throws Exception {
		logger.debug("Start:- activate() of AncillaryCategoriesUse");
		
		Resource pageResource = getResource().getParent();
		JSONObject ancillaryCategoriesObj = new JSONObject();
		JSONObject finalJsonObject = new JSONObject();
		checkForChildPages(pageResource, ancillaryCategoriesObj);
		finalJsonObject.put(ANCILLARY_CATEGORY_CONFIG, ancillaryCategoriesObj);
		setJsonString(finalJsonObject.toString());

		logger.debug("End:- activate() of AncillaryCategoriesUse");
	}
	
	/**
	 * this method checks for the child pages of the current resource
	 * @param pageResource
	 * @param ancillaryCategoriesObj
	 * @throws JSONException
	 * @throws RepositoryException
	 */
	private void checkForChildPages(Resource pageResource, JSONObject ancillaryCategoriesObj) throws JSONException, RepositoryException{
		logger.debug("Start:- checkForChildPages() of AncillaryCategoriesUse");
	
		int size = Iterables.size(pageResource.getChildren());
		if(size>1){
			getSourceCountry(pageResource, ancillaryCategoriesObj);
		}
		
		logger.debug("End:- checkForChildPages() of AncillaryCategoriesUse");
	}
	
	/**
	 * this method gets the child resources and sets the source country in JSON object
	 * @param pageResource
	 * @param ancillaryCategoriesObj
	 * @throws JSONException
	 * @throws RepositoryException
	 */
	private void getSourceCountry(Resource pageResource, JSONObject ancillaryCategoriesObj) throws JSONException, RepositoryException{
		logger.debug("Start:- getSourceCountry() of AncillaryCategoriesUse");

		Iterable<Resource> countryLevelResources=pageResource.getChildren();
		JSONArray sourceCountriesObj = new JSONArray();
		// Create a Query instance
		for (Resource countryLevelResource : countryLevelResources) {
			if(!countryLevelResource.getName().equalsIgnoreCase(HertzConstants.JCR_CONTENT)){
				getCategoriesPages(countryLevelResource, sourceCountriesObj);
			}
		}
		if(sourceCountriesObj.length()>0){
			ancillaryCategoriesObj.put(SOURCE_COUNTRIES, sourceCountriesObj);
		}
		
		logger.debug("End:- getSourceCountry() of AncillaryCategoriesUse");		
	}
	
	/**
	 * this method gets the child resources and sets the categories in JSON object
	 * @param countryLevelResource
	 * @param sourceCountriesObj
	 * @throws ValueFormatException
	 * @throws IllegalStateException
	 * @throws JSONException
	 * @throws RepositoryException
	 */
	public static void getCategoriesPages(Resource countryLevelResource, JSONArray sourceCountriesObj) throws ValueFormatException, IllegalStateException, JSONException, RepositoryException{
		logger.debug("Start:- getCategoriesPages() of AncillaryCategoriesUse");
		
		Iterable<Resource> categoryLevelResources=countryLevelResource.getChildren();
		String countryName = countryLevelResource.getName();
		JSONArray categoriesObj = new JSONArray();
		JSONObject childObject = new JSONObject();
		JSONObject object = new JSONObject();
		childObject.put(HertzConstants.COUNTRY_NAME, countryName);
		for (Resource categoryLevelResource : categoryLevelResources) {
			if(!categoryLevelResource.getName().equalsIgnoreCase(HertzConstants.JCR_CONTENT)){
				getCategories(categoryLevelResource, categoriesObj);
			}
		}
		if(childObject.length()>0 && categoriesObj.length()>0){
			object.put(CATEGORIES, categoriesObj);
			JSONObject mergedObj = manipulateJSONString(childObject, object);
			sourceCountriesObj.put(mergedObj);
		}
		else if(childObject.length()>0){
			sourceCountriesObj.put(childObject);
		}
		
		logger.debug("End:- getCategoriesPages() of AncillaryCategoriesUse");
	}
	
	/**
	 * this method gets the child resources from the category pages
	 * @param categoryLevelResource
	 * @param categoriesObj
	 * @throws ValueFormatException
	 * @throws IllegalStateException
	 * @throws JSONException
	 * @throws RepositoryException
	 */
	private static void getCategories(Resource categoryLevelResource, JSONArray categoriesObj) throws ValueFormatException, IllegalStateException, JSONException, RepositoryException{
		logger.debug("Start:- getCategories() of AncillaryCategoriesUse");
		
		Iterable<Resource> categoryResources=categoryLevelResource.getChildren();
		for (Resource categoryResource : categoryResources) {
			if(!categoryResource.getName().equalsIgnoreCase(HertzConstants.JCR_CONTENT)){
				getCategoriesOptions(categoryResource, categoriesObj);
			}
		}
		
		logger.debug("End:- getCategories() of AncillaryCategoriesUse");
	}
	
	/**
	 * this method gets the child resources and sets the category options in JSON object
	 * @param categoryResource
	 * @param categoriesObj
	 * @throws ValueFormatException
	 * @throws IllegalStateException
	 * @throws JSONException
	 * @throws RepositoryException
	 */
	private static void getCategoriesOptions(Resource categoryResource, JSONArray categoriesObj) throws ValueFormatException, IllegalStateException, JSONException, RepositoryException{
		logger.debug("Start:- getCategoriesOptions() of AncillaryCategoriesUse");
		
		ValueMap componentProperties = null;
		String categoryId = StringUtils.EMPTY;
		String categoryName = categoryResource.getName();
		JSONArray destinationCountriesObj = new JSONArray();
		JSONObject childObject = new JSONObject();
		JSONObject object = new JSONObject();
		Resource jcrContentResource = categoryResource.getChild(HertzConstants.JCR_CONTENT);
		Resource parResource = jcrContentResource.getChild(HertzConstants.PAR);
		Iterable<Resource> componentResources=parResource.getChildren();
		for (Resource componentResource : componentResources) {
			componentProperties = componentResource.getValueMap();
			categoryId = HertzUtils.getValueFromMap(componentProperties, HertzConstants.CATEGORY_ID).trim();
			childObject.put(HertzConstants.CATEGORY_NAME, categoryName);
			childObject.put(CATEGORY_ID, categoryId);
		}		
		Iterable<Resource> categoryOptionResources=categoryResource.getChildren();
		for (Resource categoryOptionResource : categoryOptionResources) {
			if(!categoryOptionResource.getName().equalsIgnoreCase(HertzConstants.JCR_CONTENT)){
				getDestinationCountry(categoryOptionResource, destinationCountriesObj);
			}
		}
		if(childObject.length()>0 && destinationCountriesObj.length()>0){
			object.put(CATEGORY_OPTIONS, destinationCountriesObj);
			JSONObject mergedObj = manipulateJSONString(childObject, object);
			categoriesObj.put(mergedObj);
		}
		else if(childObject.length()>0){
			categoriesObj.put(childObject);
		}
		
		logger.debug("End:- getCategoriesOptions() of AncillaryCategoriesUse");
	}
	
	/**
	 * this method gets the child resources and sets the destination country in JSON object
	 * @param categoryOptionResource
	 * @param destinationCountriesObj
	 * @throws ValueFormatException
	 * @throws IllegalStateException
	 * @throws JSONException
	 * @throws RepositoryException
	 */
	private static void getDestinationCountry(Resource categoryOptionResource, JSONArray destinationCountriesObj) throws ValueFormatException, IllegalStateException, JSONException, RepositoryException{
		logger.debug("Start:- getDestinationCountry() of AncillaryCategoriesUse");
		
		Iterable<Resource> destinationCountryResources=categoryOptionResource.getChildren();
		String categoryName = categoryOptionResource.getName();
		JSONObject destCountryPreselectedObj = new JSONObject();
		JSONObject childObject = new JSONObject();
		JSONObject object = new JSONObject();
		childObject.put(HertzConstants.CATEGORY_NAME, categoryName);
		for (Resource destinationCountryResource : destinationCountryResources) {
			if(!destinationCountryResource.getName().equalsIgnoreCase(HertzConstants.JCR_CONTENT)){
				getDestinationCountryValues(destinationCountryResource, destCountryPreselectedObj);
			}
		}
		if(childObject.length()>0 && destCountryPreselectedObj.length()>0){
			object.append(DESTINATION_COUNTRIES, destCountryPreselectedObj);
			JSONObject mergedObj = manipulateJSONString(childObject, object);
			destinationCountriesObj.put(mergedObj);
		}
		else if(childObject.length()>0){
			destinationCountriesObj.put(childObject);
		}
		
		logger.debug("End:- getDestinationCountry() of AncillaryCategoriesUse");
	}
	
	/**
	 * this method gets the child resources and sets the preselected value and destination country in JSON object
	 * @param destinationCountryResource
	 * @param object
	 * @throws ValueFormatException
	 * @throws IllegalStateException
	 * @throws JSONException
	 * @throws RepositoryException
	 */
	private static void getDestinationCountryValues(Resource destinationCountryResource, JSONObject object) throws ValueFormatException, IllegalStateException, JSONException, RepositoryException{
		logger.debug("Start:- getDestinationCountryValues() of AncillaryCategoriesUse");
		ValueMap componentProperties = null;
		String preselectedValue = StringUtils.EMPTY;
		String countryName = destinationCountryResource.getName();
		Resource jcrContentResource = destinationCountryResource.getChild(HertzConstants.JCR_CONTENT);
		Resource parResource = jcrContentResource.getChild(HertzConstants.PAR);
		Iterable<Resource> componentResources=parResource.getChildren();
		for (Resource componentResource : componentResources) {
			componentProperties = componentResource.getValueMap();
			preselectedValue = HertzUtils.getValueFromMap(componentProperties, HertzConstants.PRESELECTED_VALUE).trim();
			object.put(HertzConstants.COUNTRY_NAME, countryName);
			object.put(HertzConstants.PRESELECTED_VALUE, preselectedValue);
		}		
		logger.debug("End:- getDestinationCountryValues() of AncillaryCategoriesUse");
	}

	/**
	 * Getter for the JSON String
	 * @return Json String 
	 */
	public String getJsonString() {
		return jsonString;
	}

	/**
	 * Setter for the JSON String
	 * @param jsonString
	 */
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	
	public static JSONObject manipulateJSONString(JSONObject obj1, JSONObject obj2) throws JSONException{
		String json1 = obj1.toString().substring(0, obj1.toString().length()-1);
		String json2 = obj2.toString().replaceFirst("\\{", ",");
		String mergedString = json1.concat(json2);
		JSONObject newObj = new JSONObject(mergedString);
		return newObj;
	}

}
