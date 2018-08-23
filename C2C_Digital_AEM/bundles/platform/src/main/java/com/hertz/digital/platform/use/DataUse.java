package com.hertz.digital.platform.use;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.oak.commons.PropertiesUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.commons.json.jcr.JsonItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.commons.Externalizer;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.hertz.digital.platform.bean.BadgeImageBean;
import com.hertz.digital.platform.bean.CountryLanguageBean;
import com.hertz.digital.platform.bean.CountryLanguageItemsBean;
import com.hertz.digital.platform.bean.HeaderCountryLangLoginRedirectItemsBean;
import com.hertz.digital.platform.bean.HeaderCountryLanguageItemsBean;
import com.hertz.digital.platform.bean.ImageInfoBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.service.api.JCRService;
import com.hertz.digital.platform.service.impl.CountryLanguageSelectorServiceImpl;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * The use class for Data Page. Invoked when hit using .data selector and
 * extension .json with Data page.
 * <ul>
 * Returns a JSON with :-
 * <li>Page Properties</li>
 * </ul>
 * 
 * @author puneet.soni
 *
 */
public class DataUse extends WCMUsePojo {

	/**
	 * Default Constructor Declaration
	 */
	public DataUse() {
		super();
	}

	/**
	 * LOGGER instantiation
	 */

	private static final Logger logger = LoggerFactory.getLogger(DataUse.class);
	private String jsonString;
	private static final String COMPONENTS = "components";
	private static final int ZERO = 0;
	private static final String MULTIFIELD_PAIR = "multifieldpair";

	/** The Constant SOURCES. */
	private static final String SOURCES = "sources";

	/**
	 * activate() for method for class DataUse
	 * 
	 * @throws JSONException
	 *             JSONException
	 * @throws RepositoryException
	 *             RepositoryException
	 */
	@Override
	public void activate() throws JSONException, RepositoryException {
		logger.debug("Start:- activate() of DataUse");
		String requestPath = getRequest().getPathInfo();
		JSONObject finalJsonObject = new JSONObject();
		JSONObject superJsonObject = new JSONObject();
		if (requestPath.contains(HertzConstants.JCR_CONTENT) || requestPath.contains(HertzConstants.JCR_CONTENT_REQ)) {
			Node node = getResource().adaptTo(Node.class);
			StringWriter stringWriter = new StringWriter();
			JsonItemWriter jsonWriter = new JsonItemWriter(null);
			jsonWriter.dump(node, stringWriter, -1);
			finalJsonObject = new JSONObject(stringWriter.toString());
			setJsonString(finalJsonObject.toString());
		} else {
			Page currentPage = getCurrentPage();
			Resource resource = currentPage.adaptTo(Resource.class);
			if (currentPage.getProperties().get(HertzConstants.JCR_CQ_TEMPLATE, String.class)
					.equalsIgnoreCase(HertzConstants.COUNTRY_LANGUAGE_TEMPLATE_PATH)) {
				CountryLanguageBean countryLanguageBean = new CountryLanguageBean();
				CountryLanguageSelectorServiceImpl countryLanguageSelectorServiceImpl = new CountryLanguageSelectorServiceImpl();
				HeaderCountryLanguageItemsBean headerCountryLanguageItemsBean = countryLanguageSelectorServiceImpl
						.getHeaderCountryLanguageJson(resource);
				HeaderCountryLangLoginRedirectItemsBean headerCountryLangLoginRedirectItemsBean = countryLanguageSelectorServiceImpl
						.getHeaderCountryLanguageRedirectJson(resource);
				List<CountryLanguageItemsBean> countryLanguageItemsBeanList = countryLanguageSelectorServiceImpl
						.getCountryLanguageJson(resource);
				countryLanguageBean.setHeaderCountryLanguageItemsBean(headerCountryLanguageItemsBean);
				countryLanguageBean
						.setHeaderCountryLanguageLoginRedirectItemsBean(headerCountryLangLoginRedirectItemsBean);
				countryLanguageBean.setCountryLanguageItemsBeanList(countryLanguageItemsBeanList);
				Gson gson = new Gson();
				setJsonString(gson.toJson(countryLanguageBean));
			} else {
				setResourceJson(finalJsonObject, superJsonObject, resource);
			}
		}

		logger.debug("End:- activate() of DataUse");

	}

	/**
	 * Sets the resource json.
	 *
	 * @param finalJsonObject
	 *            the final json object
	 * @param superJsonObject
	 *            the super json object
	 * @param resource
	 *            the resource
	 * @throws JSONException
	 *             the JSON exception
	 * @throws RepositoryException
	 *             the repository exception
	 */
	private void setResourceJson(JSONObject finalJsonObject, JSONObject superJsonObject, Resource resource)
			throws JSONException, RepositoryException {
		Iterable<Resource> resourceIterator = resource.getChildren();

		for (Resource subResource : resourceIterator) {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			setJSON(subResource, jsonObject, getSlingScriptHelper().getService(HertzConfigFactory.class),
					getSlingScriptHelper().getService(JCRService.class),
					getSlingScriptHelper().getService(Externalizer.class));

			if (jsonObject.length() != ZERO) {
				jsonArray.put(jsonObject);

				finalJsonObject.put(subResource.getName(), jsonArray);
			}
			if (finalJsonObject.has(HertzConstants.JCR_CONTENT)) {
				superJsonObject.put(HertzConstants.DATA_CONTENT, jsonArray);
			} else {
				superJsonObject.put(HertzConstants.DATA_CONTENT, finalJsonObject);
			}
		}
		setJsonString(superJsonObject.toString());
	}

	public static boolean stringContainsItemFromList(String inputStr, String[] items) {
		return Arrays.stream(items).parallel().anyMatch(inputStr::contains);
	}

	/**
	 * Gets the JSON for the resource
	 * 
	 * @param resource
	 *            resource for which json needs to be created
	 * @param jsonObject
	 *            for creating the JSON
	 * @throws JSONException
	 *             JSONException
	 * @throws RepositoryException
	 *             RepositoryException
	 */
	public static void setJSON(Resource resource, JSONObject jsonObject, HertzConfigFactory hertzConfigFactory,
			JCRService jcrService, Externalizer externalizer) throws JSONException, RepositoryException {
		logger.debug("Start:- setJSON() of DataUse");
		String[] dataAllowedComponents = (String[]) hertzConfigFactory
				.getPropertyValue(HertzConstants.Hertz_DATA_ALLOWED_COMPONENTS);
		String resourceName = resource.getName();
		if (StringUtils.isNotEmpty(resourceName)) {
			if (resourceName.equalsIgnoreCase(HertzConstants.JCR_CONTENT)) {
				setJsonHertzContent(resource, jsonObject, jcrService, externalizer, dataAllowedComponents);
			}
			if (!resourceName.equalsIgnoreCase(HertzConstants.JCR_CONTENT)) {
				Iterable<Resource> resourceIterator = resource.getChildren();
				for (Resource subResource : resourceIterator) {
					JSONArray jsonArray = new JSONArray();
					String subResourceName = subResource.getName();
					setJsonObj(jsonObject, hertzConfigFactory, jcrService, externalizer, subResource, jsonArray,
							subResourceName);
				}
			}
		}
		logger.debug("End:- setJSON() of DataUse");
	}

	/**
	 * Sets the json obj.
	 *
	 * @param jsonObject
	 *            the json object
	 * @param hertzConfigFactory
	 *            the hertz config factory
	 * @param jcrService
	 *            the jcr service
	 * @param externalizer
	 *            the externalizer
	 * @param subResource
	 *            the sub resource
	 * @param jsonArray
	 *            the json array
	 * @param subResourceName
	 *            the sub resource name
	 * @throws JSONException
	 *             the JSON exception
	 * @throws RepositoryException
	 *             the repository exception
	 */
	private static void setJsonObj(JSONObject jsonObject, HertzConfigFactory hertzConfigFactory, JCRService jcrService,
			Externalizer externalizer, Resource subResource, JSONArray jsonArray, String subResourceName)
			throws JSONException, RepositoryException {
		if (StringUtils.isNotEmpty(subResourceName)) {
			if (!subResourceName.equalsIgnoreCase(HertzConstants.JCR_CONTENT)) {
				JSONObject subJsonObject = new JSONObject();
				setJSON(subResource, subJsonObject, hertzConfigFactory, jcrService, externalizer);
				if (subJsonObject.length() != ZERO) {
					jsonArray.put(subJsonObject);
				}
			}
			if (subResourceName.equalsIgnoreCase(HertzConstants.JCR_CONTENT)) {
				setJSON(subResource, jsonObject, hertzConfigFactory, jcrService, externalizer);
			}
			if (jsonArray.length() != ZERO) {
				jsonObject.put(subResource.getName(), jsonArray);
			}
		}
	}

	/**
	 * @param resource
	 * @param jsonObject
	 * @param jcrService
	 * @param externalizer
	 * @param dataAllowedComponents
	 * @throws RepositoryException
	 * @throws JSONException
	 */
	private static void setJsonHertzContent(Resource resource, JSONObject jsonObject, JCRService jcrService,
			Externalizer externalizer, String[] dataAllowedComponents) throws RepositoryException, JSONException {
		boolean isKeyValuePairRequired = false;
		Iterable<Resource> iterableChildren = resource.getChildren();
		for (Resource componentChildren : iterableChildren) {
			if (StringUtils.equals(componentChildren.getName(), HertzConstants.PAR)) {
				Iterable<Resource> parChildren = componentChildren.getChildren();
				for (Resource parChild : parChildren) {
					isKeyValuePairRequired = IskeyValuePairRequired(dataAllowedComponents, isKeyValuePairRequired,
							parChild);
				}
			}
		}
		if (isKeyValuePairRequired) {
			JSONObject jsonObj = getComponentlocationOverlay(resource.getPath(), resource.getResourceResolver(),
					jcrService, externalizer);
			JSONArray jsonArray = new JSONArray("[" + jsonObj.toString() + "]");
			jsonObject.accumulate(COMPONENTS, jsonArray);
		}
	}

	private static boolean IskeyValuePairRequired(String[] dataAllowedComponents, boolean isKeyValuePairRequired,
			Resource parChild) {
		if (StringUtils.isNotEmpty(parChild.getName()) && null != parChild.getResourceType()) {
			if (stringContainsItemFromList(
					parChild.getResourceType().substring(parChild.getResourceType().lastIndexOf("/") + 1,
							parChild.getResourceType().length()),
					dataAllowedComponents)) {
				logger.debug("parChild.getName() :: " + parChild.getName());
				isKeyValuePairRequired = true;
			}
		}
		return isKeyValuePairRequired;
	}

	/**
	 * This method would be invoked from all the page specific Use objects which
	 * intend to produce HTMLs for underlying par/* components
	 * 
	 * @param parentPath
	 *            The parent path under which the query has to be fired. For e.g
	 *            /content/.../homepage/jcr:content
	 * @param resolver
	 *            The resolver object.
	 * @param jcrService
	 *            The JCRService for querying.
	 * @param externalizer
	 *            The externalizer for converting to instance specific
	 *            publisher/end-point url.
	 * @return The JSONObject that will have a map having the component name as
	 *         key and its HTML as its value.
	 * @throws RepositoryException
	 * @throws JSONException
	 */
	public static JSONObject getComponentlocationOverlay(String parentPath, ResourceResolver resolver,
			JCRService jcrService, Externalizer externalizer) throws RepositoryException, JSONException {
		logger.debug("Execution starts::getComponentlocationOverlay()");
		char SLASH = '/';
		Map<String, String> predicateMap = new HashMap<>();
		preparePredicateMap(parentPath, predicateMap);
		logger.debug("The predicate map looks like this:- {}", predicateMap);
		JSONObject object = new JSONObject();
		if (null != jcrService && null != externalizer) {
			SearchResult searchResults = jcrService.searchResults(resolver, predicateMap);
			for (Hit hit : searchResults.getHits()) {

				Resource hitResource = hit.getResource();

				Iterable<Resource> iterableComponents = hitResource.getChildren();

				for (Resource componentResource : iterableComponents) {
					getComponentProperty(SLASH, object, componentResource);
				}
			}
		}
		logger.debug("Execution ends::getComponentlocationOverlay()");
		return object;
	}

	/**
	 * Get the component properties.
	 * 
	 * @param SLASH
	 * @param object
	 * @param componentResource
	 * @throws JSONException
	 */
	private static void getComponentProperty(char SLASH, JSONObject object, Resource componentResource)
			throws JSONException {
		ValueMap componentProperties = null;
		if (componentResource.getName().contains(MULTIFIELD_PAIR)) {
			componentProperties = componentResource.getValueMap();
			String key = StringUtils.trim(HertzUtils.getValueFromMap(componentProperties, HertzConstants.KEY));
			String[] value = PropertiesUtil.toStringArray(componentProperties.get(HertzConstants.VALUE));
			Map map = new HashMap();
			map.put(HertzConstants.KEY, key);
			map.put(HertzConstants.VALUE, Arrays.toString(value));
			JSONObject jsonObject = new JSONObject(map);
			String componentResourceType = componentResource.getResourceType();
			String str = componentResourceType.substring(componentResourceType.lastIndexOf(SLASH)).replaceAll("/", "");
			object.accumulate(str, jsonObject);
		} else if (componentResource.getName().contains(HertzConstants.SELECT_RADIO_FIELD_PAIR)) {
			JSONObject jsonObject = getRadioFieldPairValues(componentResource);
			String componentResourceType = componentResource.getResourceType();
			String str = componentResourceType.substring(componentResourceType.lastIndexOf(SLASH)).replaceAll("/", "");
			object.accumulate(str, jsonObject);
		} else if (componentResource.getName().contains(HertzConstants.IMAGE_PAIR)) {
			Map<String, Object> configTextMap = new HashMap<>();
			ValueMap values = componentResource.getValueMap();
			String key = StringUtils.trim(values.get(HertzConstants.KEY, String.class));
			String alt = values.get(HertzConstants.ALT, String.class);
			String fileReferencePath = values.get(HertzConstants.FILE_REFERENCE, String.class);
			if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(fileReferencePath)) {
				ImageInfoBean sources = new ImageInfoBean(componentResource);
				sources.setTitle(alt);
				configTextMap.put(key, sources);
				Gson gson = HertzUtils.initGsonBuilder(true, true, true);
				String jsonStr = gson.toJson(sources);
				object.put(key, new JSONObject(jsonStr));
			}
		} else if (componentResource.getName().contains("badgeimagecomponent")) {
			setBadgeImageVariables(componentResource, object);
		} else {
			getDataCompProps(SLASH, object, componentResource);
		}
	}

	/**
	 * Gets the data comp props.
	 *
	 * @param SLASH the slash
	 * @param object the object
	 * @param componentResource the component resource
	 * @return the data comp props
	 * @throws JSONException the JSON exception
	 */
	private static void getDataCompProps(char SLASH, JSONObject object, Resource componentResource)
			throws JSONException {
		ValueMap componentProperties;
		HashMap hashMap = new HashMap();
		componentProperties = componentResource.getValueMap();
		for (Map.Entry e : componentProperties.entrySet()) {
			if (!e.getKey().toString().contains(HertzConstants.COLON)
					&& !e.getKey().toString().equalsIgnoreCase("textIsRich")
					&& !e.getKey().equals("fileReference")) {
				if (e.getValue() instanceof String) {
					hashMap.put(StringUtils.trim(e.getKey().toString()), StringUtils.trim((String) e.getValue()));
				} else if (e.getValue() instanceof String[]) {
					hashMap.put(StringUtils.trim(e.getKey().toString()),
							Arrays.toString(PropertiesUtil.toStringArray(e.getValue())));
				} else
					hashMap.put(StringUtils.trim(e.getKey().toString()), e.getValue());
			}
		}
		if (componentResource.hasChildren()) {
			Resource imageResource = (Resource) componentResource.getChild(HertzConstants.IMAGE);
			if (null != imageResource) {
				ImageInfoBean imageBean = new ImageInfoBean(imageResource);
				Gson gson = HertzUtils.initGsonBuilder(true, true, true);
				String imageJson = gson.toJson(imageBean, ImageInfoBean.class);
				JSONObject element = new JSONObject(imageJson);
				hashMap.put(SOURCES, element.get(SOURCES));
			}
		} else {
			if (componentResource.getValueMap().containsKey("fileReference")) {
				ImageInfoBean imageBean = new ImageInfoBean(componentResource);
				Gson gson = HertzUtils.initGsonBuilder(true, true, true);
				String imageJson = gson.toJson(imageBean, ImageInfoBean.class);
				JSONObject element = new JSONObject(imageJson);
				hashMap.put(SOURCES, element.get(SOURCES));
			}
		}
		JSONObject jsonObject = new JSONObject(hashMap);
		String componentResourceType = componentResource.getResourceType();
		String str = componentResourceType.substring(componentResourceType.lastIndexOf(SLASH)).replaceAll("/", "");
		object.accumulate(str, jsonObject);
	}

	/**
	 * 
	 * @param componentResource
	 *            resource for which properties needs to be retrieved
	 * @param object
	 * @return Map that contains the properties values
	 * @throws JSONException
	 *             JSONException
	 */
	private static void setBadgeImageVariables(Resource componentResource, JSONObject object) throws JSONException {
		logger.debug("Execution starts::setBadgeImageVariables()");
		ValueMap componentProperties = componentResource.getValueMap();
		String key = StringUtils.EMPTY;
		String tierName = StringUtils.EMPTY;
		ImageInfoBean badgeImageBean = null;
		if (componentProperties.containsKey(HertzConstants.KEY)) {
			key = StringUtils.trim(componentProperties.get(HertzConstants.KEY).toString());
		}
		if (componentProperties.containsKey(HertzConstants.TIER_NAME)) {
			tierName = componentProperties.get(HertzConstants.TIER_NAME).toString();
		}
		if (componentResource.hasChildren()) {
			Resource fileImageResource = (Resource) componentResource.getChild(HertzConstants.IMAGE);
			if (null != fileImageResource) {
				badgeImageBean = new ImageInfoBean(fileImageResource);
			}
		}
		BadgeImageBean bean = new BadgeImageBean();
		bean.setTierName(tierName);
		bean.setBadge(badgeImageBean);
		bean.setKey(key);
		Gson gson = HertzUtils.initGsonBuilder(true, true, true);
		object.accumulate("badgeimagecomponent", new JSONObject(gson.toJson(bean, BadgeImageBean.class)));
		logger.debug("Execution ends::setBadgeImageVariables()");
	}

	/**
	 * Get Fields of Radio Buttons Pair in Data Pages
	 * 
	 * @param componentResource
	 *            Resource related to the RadioButton Component
	 * @return JSONObject JSONObject
	 */
	private static JSONObject getRadioFieldPairValues(Resource componentResource) {
		logger.debug("Execution starts::getRadioFieldPairValues()");
		JSONArray array = new JSONArray();
		ValueMap componentProperties = componentResource.getValueMap();
		String key = StringUtils.trim(HertzUtils.getValueFromMap(componentProperties, HertzConstants.KEY));
		String label = HertzUtils.getValueFromMap(componentProperties, HertzConstants.LABEL);
		String defaultValue = HertzUtils.getValueFromMap(componentProperties, HertzConstants.DEFAULT_VALUE);
		String ariaLabel = HertzUtils.getValueFromMap(componentProperties, HertzConstants.ARIA_LABEL);
		String[] optionsList = PropertiesUtil.toStringArray(componentProperties.get(HertzConstants.OPTIONS_LIST));
		for (String optionString : optionsList) {
			try {
				JSONObject jsonOptionString = new JSONObject(optionString);
				array.put(jsonOptionString);
			} catch (JSONException e) {
				logger.debug("JSON Error : ", e);
			}
		}
		Map map = new HashMap();
		map.put(HertzConstants.KEY, key);
		map.put(HertzConstants.LABEL, label);
		map.put(HertzConstants.DEFAULT_VALUE, defaultValue);
		map.put(HertzConstants.ARIA_LABEL, ariaLabel);
		map.put(HertzConstants.OPTIONS_LIST, array);
		JSONObject jsonObject = new JSONObject(map);
		logger.debug("Execution ends::getRadioFieldPairValues()");
		return jsonObject;
	}

	/**
	 * @param parentPath
	 * @param predicateMap
	 */
	private static void preparePredicateMap(String parentPath, Map<String, String> predicateMap) {
		predicateMap.put(HertzConstants.TYPE, "nt:unstructured");
		predicateMap.put(HertzConstants.PATH, parentPath);
		predicateMap.put("group.1_group.1_property", HertzConstants.SLING_RESOURCE_TYPE);
		predicateMap.put("group.1_group.1_property.value", HertzConstants.TOUCH_PARSYS_RES_TYPE);
		predicateMap.put("group.2_group.1_property", HertzConstants.SLING_RESOURCE_TYPE);
		predicateMap.put("group.2_group.1_property.value", HertzConstants.CLASSIC_PARSYS_RES_TYPE);
		predicateMap.put("group.p.or", "true");
		predicateMap.put(HertzConstants.LIMIT, "-1");
	}

	/**
	 * getter for JSONString
	 * 
	 * @return String for JSON
	 */
	public String getJsonString() {
		return jsonString;
	}

	/**
	 * Setter for JSON String
	 * 
	 * @param jsonString
	 *            String for JSON
	 */
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
}
