package com.hertz.digital.platform.exporter.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.oak.commons.PropertiesUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.engine.SlingRequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.contentsync.handler.util.RequestResponseFactory;
import com.day.cq.wcm.api.WCMMode;
import com.hertz.digital.platform.bean.ImageInfoBean;
import com.hertz.digital.platform.exporter.api.ComponentExporterService;
import com.hertz.digital.platform.use.GoldPlusRewardsUse;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * Created by paul.m.mcmahon on 9/12/2017.
 */
@Component(immediate = true, metatype = true, label = "Hertz - Configurable Text Parsys Exporter", description = "Hertz - Exposes configurable text as a Map for JSON")
@Service(value = ComponentExporterService.class)
@Properties(value = { @Property(name = "identifier", value = "configtext-parsys") })
public class ConfigurableTextParSysExportor implements ComponentExporterService {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -720633218399897715L;

	/**
	 * Instantiates a new configurable text par sys exportor.
	 */
	public ConfigurableTextParSysExportor() {
		super();
	}

	@Reference
	static RequestResponseFactory requestResponseFactory;
	@Reference
	static SlingRequestProcessor requestProcessor;
	
	/** LOGGER instantiation. */
	private static final Logger logger = LoggerFactory.getLogger(ConfigurableTextParSysExportor.class);
	
	/** Constant for label. */
	private static final String LABEL = "label";
	
	/** Constant for aria label. */
	private static final String ARIA_LABEL = "ariaLabel";
	
	/** Constant for default value. */
	private static final String DEFAULT_VALUE = "defaultValue";
	
	/** Constant for error. */
	private static final String ERROR = "error";
	
	/** Constant for options list. */
	private static final String OPTIONS_LIST = "optionsList";
	
	/** Constant for option value. */
	private static final String OPTION_VALUE = "optionValue";
	
	/** Constant for option display text. */
	private static final String OPTION_DISPLAY_TEXT = "optionDisplayText";
	
	/** Constant for option checked by default. */
	private static final String CHECKED_BY_DEFAULT = "checkedByDefault";

	/**
	 * exportAsJson(org.apache.sling.api.resource.Resource,
	 * org.apache.sling.api.resource.ResourceResolver)
	 *
	 * @param component
	 *            the component
	 * @param resolver
	 *            the resolver
	 * @return the string
	 */
	@Override
	public String exportAsJson(Resource component, ResourceResolver resolver) {
		// left as an extension for later, if need be.
		return "{}";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hertz.digital.platform.exporter.api.ComponentExporterService#
	 * exportAsBean(org.apache.sling.api.resource.Resource,
	 * org.apache.sling.api.resource.ResourceResolver)
	 */
	@Override
	public Object exportAsBean(Resource component, ResourceResolver resolver) {
		return getConfigTextParsysMap(component, resolver);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hertz.digital.platform.exporter.api.ComponentExporterService#
	 * exportAsMap(org.apache.sling.api.resource.ResourceResolver,
	 * org.apache.sling.api.resource.Resource)
	 */
	@Override
	public Object exportAsMap(ResourceResolver resolver, Resource configPageResource)
			throws JSONException, RepositoryException {
		return null;
	}

	/**
	 * Gets the config text parsys map.
	 *
	 * @param component
	 *            the component
	 * @param resolver
	 *            the resolver
	 * @return the config text parsys map
	 */
	public static Map<String, Object> getConfigTextParsysMap(Resource component, ResourceResolver resolver) {
		LinkedHashMap<String, Object> configTextMap = null;
		if (component.hasChildren()) {
			configTextMap = new LinkedHashMap<String, Object>();
			for (Resource child : component.getChildren()) {
				String resourceType = child.getResourceType();
				ValueMap values = child.getValueMap();
				addComponentPropsToMap(resourceType, values, configTextMap, resolver, component, child);
			}
		}
		return configTextMap;
	}
	
	/**
	 * Adds component properties to map
	 * @param resourceType
	 * @param values
	 * @param configTextMap
	 * @param resolver
	 * @param component
	 * @param child
	 */
	public static void addComponentPropsToMap(String resourceType, ValueMap values, 
			LinkedHashMap<String, Object> configTextMap, ResourceResolver resolver, 
			Resource component, Resource child){
		addPairComponentPropsToMap(resourceType, values, configTextMap, resolver, component, child);
		addOtherComponentPropsToMap(resourceType, values, configTextMap, resolver, component, child);
	}
	
	/**
	 * Adds pair components to map
	 * @param resourceType
	 * @param values
	 * @param configTextMap
	 * @param resolver
	 * @param component
	 * @param child
	 */
	public static void addPairComponentPropsToMap(String resourceType, ValueMap values, 
			LinkedHashMap<String, Object> configTextMap, ResourceResolver resolver, 
			Resource component, Resource child){
		if (resourceType.equals("hertz/components/content/richtextpair")){
			addRichTextPairToMap(resolver, child, values, configTextMap);					
		} else if (resourceType.equals("hertz/components/content/simplepair")
				|| resourceType.equals("hertz/components/configtext/textareapair")) {
			addSimplePairToMap(values, configTextMap);
		} else if (resourceType.equals("hertz/components/content/linkpair")) {
			addLinkPairToMap(values, configTextMap);
		} else if (resourceType.equals("/apps/hertz/components/configtext/genericfieldpair")
				|| resourceType.equals("hertz/components/configtext/genericfieldpair")) {
			addGenericFieldPairToMap(values, configTextMap);
		} else if (resourceType.equals("hertz/components/content/checkboxpair")) {
			addCheckboxPairToMap(values, configTextMap);
		} else if (resourceType.equals("hertz/components/configtext/selectradiofieldpair")) {
			addSelectListRadioButtonPairToMap(values, configTextMap);
		} 
	}
	
	/**
	 * Adds other components to map
	 * @param resourceType
	 * @param values
	 * @param configTextMap
	 * @param resolver
	 * @param component
	 * @param child
	 */
    public static void addOtherComponentPropsToMap(String resourceType, ValueMap values, 
			LinkedHashMap<String, Object> configTextMap, ResourceResolver resolver, 
			Resource component, Resource child){
    	if (resourceType.equals("hertz/components/configtext/configreference") 
				|| resourceType.equals("hertz/components/content/rapidreference")) {
			addConfigTextRefereneceToMap(component.getPath(), values, configTextMap, resolver); 
		} else if (resourceType.equals("hertz/components/content/linkdropdown")) {
			addLinkDropDownToMap(child, configTextMap);
		} else if(resourceType.equals("hertz/components/content/checkboxpairdata")){
			addNegotiatedQuoteRateToMap(values, configTextMap);
		} else if (resourceType.equals("hertz/components/content/imagepair")) {
			addImagePairToMap(child, configTextMap);
		} else if (resourceType.equals("hertz/components/configtext/datepair")) {
			addDatePairToMap(child, configTextMap);
		}
	}
	
	/**
	 * Add link drop down values to map
	 * @param child
	 * @param configTextMap
	 */
	public static void addLinkDropDownToMap(Resource child, 
			LinkedHashMap<String, Object> configTextMap){
		try {
			GoldPlusRewardsUse.createJsonForLinkDropdown(child, configTextMap);
		} catch (JSONException e) {
			logger.error("Could not get JSON", e.getMessage());
		}
	}
	
	/**
	 * Add rich text pair values to map
	 * @param resolver
	 * @param child
	 * @param values
	 * @param configTextMap
	 */
	public static void addRichTextPairToMap(ResourceResolver resolver, Resource child, 
			ValueMap values, LinkedHashMap<String, Object> configTextMap){
		String requestPath = child.getPath() + ".html";
		String key = values.get("key", String.class);
		String value = values.get("value", String.class);
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
		    HttpServletRequest request = HertzUtils.getServiceReference(RequestResponseFactory.class).createRequest("GET", requestPath);
            WCMMode.DISABLED.toRequest(request);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            HttpServletResponse response = HertzUtils.getServiceReference(RequestResponseFactory.class).createResponse(out);
            try {
            	HertzUtils.getServiceReference(SlingRequestProcessor.class).processRequest(request, response, resolver);
				configTextMap.put(key, out.toString().replaceAll(key + " : ",""));
			} catch (ServletException | IOException e) {
				logger.error("Error occured : - {} {}", e, e.getCause());
			}
		}
	}
	
	private static void addNegotiatedQuoteRateToMap(ValueMap values, LinkedHashMap<String, Object> configTextMap) {
		// TODO Auto-generated method stub
		configTextMap.put("display", (Boolean)values.get("display", Boolean.class));
	}

	/**
	 * Adds the date pair to map.
	 *
	 * @param component
	 *            the component
	 * @param configTextMap
	 *            the config text map
	 */
	public static void addDatePairToMap(Resource component, Map<String, Object> configTextMap) {

		ValueMap values = component.getValueMap();
		String key = values.get("key", String.class);
		String label = values.get(LABEL, String.class);
		String ariaLabel = values.get(ARIA_LABEL, String.class);
		String datepair = values.get("datepair", String.class);
		ZonedDateTime zonedDateTime = ZonedDateTime.parse(datepair);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String formattedDate = null;

		try {
			if (datepair != null && !StringUtils.isBlank(datepair)) {
				formattedDate = formatter.format(zonedDateTime);
			}
		} catch (DateTimeException e) {
			logger.error("Date Exception", e);
		}

		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(datepair)) {
			LinkedHashMap<String, String> linkprops = new LinkedHashMap<String, String>();

			if (!StringUtils.isBlank(label)) {
				linkprops.put(LABEL, label);
			}
			if (!StringUtils.isBlank(ariaLabel)) {
				linkprops.put(ARIA_LABEL, ariaLabel);
			}
			if (!StringUtils.isBlank(datepair)) {
				linkprops.put("datePair", formattedDate.toString());
			}
			if (!linkprops.isEmpty()) {
				configTextMap.put(key, linkprops);
			}

		}
	}

	/**
	 * Adds the simple pair to map.
	 *
	 * @param values
	 *            the values
	 * @param configTextMap
	 *            the config text map
	 */
	public static void addSimplePairToMap(ValueMap values, Map<String, Object> configTextMap) {
		String key = values.get("key", String.class);
		String value = values.get("value", String.class);
		/*
		 * if (logger.isDebugEnabled()) {
		 * logger.debug("Component is simple pair key {} and value {}", key,
		 * value); }
		 */
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
			/*
			 * if (logger.isDebugEnabled()) { logger.debug("adding to map"); }
			 */
			configTextMap.put(key, value);
		}
	}

	/**
	 * Adds the link pair to map.
	 *
	 * @param values
	 *            the values
	 * @param configTextMap
	 *            the config text map
	 */
	public static void addLinkPairToMap(ValueMap values, Map<String, Object> configTextMap) {
		String key = values.get("key", String.class);
		String href = values.get("href", String.class);
		String target = values.get("target", String.class);
		String rel = values.get("rel", String.class);
		String content = values.get("content", String.class);
		/*
		 * if (logger.isDebugEnabled()) {
		 * logger.debug("Component is link pair key {} and href {}", key, href);
		 * }
		 */
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(href)) {
			/*
			 * if (logger.isDebugEnabled()) { logger.debug("adding to map"); }
			 */
			LinkedHashMap<String, String> linkprops = new LinkedHashMap<String, String>();
			linkprops.put("href", HertzUtils.shortenIfPath(href));
			if (!StringUtils.isBlank(target)) {
				linkprops.put("target", target);
			}
			if (!StringUtils.isBlank(rel)) {
				linkprops.put("rel", rel);
			}
			if (!StringUtils.isBlank(content)) {
				linkprops.put("content", content);
			}
			configTextMap.put(key, linkprops);
		}
	}

	/**
	 * Adds the generic field pair to map.
	 *
	 * @param values
	 *            the values
	 * @param configTextMap
	 *            the config text map
	 */
	public static void addGenericFieldPairToMap(ValueMap values, Map<String, Object> configTextMap) {
		boolean errorFound = false;
		if (null == values) {
			logger.error("Value map not provided, can not add Generic Field pair to map without it");
			errorFound = true;
		}
		if (null == configTextMap) {
			logger.error("Config Text Map not provided, can not add Generic Field pair to map without it");
			errorFound = true;
		}
		if (errorFound) {
			return;
		}
		String key = values.get("key", String.class);
		String label = values.get(LABEL, String.class);
		String ariaLabel = values.get(ARIA_LABEL, String.class);
		String defaultValue = values.get(DEFAULT_VALUE, String.class);
		String error = values.get(ERROR, String.class);
		/*
		 * if (logger.isDebugEnabled()) {
		 * logger.debug("Component is generic field pair key {}", key); }
		 */
		if (StringUtils.isNotBlank(key)) {
			LinkedHashMap<String, String> linkprops = new LinkedHashMap<String, String>();
			setLinkProps(label, ariaLabel, defaultValue, error, linkprops);
			if (!linkprops.isEmpty()) {
				configTextMap.put(key, linkprops);
			}
		}
	}

	/**
	 * Sets the link props.
	 *
	 * @param label
	 *            the label
	 * @param ariaLabel
	 *            the aria label
	 * @param defaultValue
	 *            the default value
	 * @param error
	 *            the error
	 * @param linkprops
	 *            the linkprops
	 */
	private static void setLinkProps(String label, String ariaLabel, String defaultValue, String error,
			LinkedHashMap<String, String> linkprops) {
		if (!StringUtils.isBlank(label)) {
			linkprops.put(LABEL, label);
		}
		if (!StringUtils.isBlank(ariaLabel)) {
			linkprops.put(ARIA_LABEL, ariaLabel);
		}
		if (!StringUtils.isBlank(defaultValue)) {
			linkprops.put(DEFAULT_VALUE, defaultValue);
		}
		if (!StringUtils.isBlank(error)) {
			linkprops.put(ERROR, error);
		}
	}

	/**
	 * Sets the link props.
	 *
	 * @param label
	 *            the label
	 * @param ariaLabel
	 *            the aria label
	 * @param defaultValue
	 *            the default value
	 * @param error
	 *            the error
	 * @param linkprops
	 *            the linkprops
	 */
	private static void setLinkProperties(String label, String ariaLabel, String defaultValue, String error,
			LinkedHashMap<String, Object> linkprops) {
		if (!StringUtils.isBlank(label)) {
			linkprops.put(LABEL, label);
		}
		if (!StringUtils.isBlank(ariaLabel)) {
			linkprops.put(ARIA_LABEL, ariaLabel);
		}
		if (!StringUtils.isBlank(defaultValue)) {
			linkprops.put(DEFAULT_VALUE, defaultValue);
		}
		if (!StringUtils.isBlank(error)) {
			linkprops.put(ERROR, error);
		}
	}

	/**
	 * Adds the select list radio button pair to map.
	 *
	 * @param values
	 *            the values
	 * @param configTextMap
	 *            the config text map
	 */
	public static void addSelectListRadioButtonPairToMap(ValueMap values, Map<String, Object> configTextMap) {
		boolean errorFound = false;
		if (null == values) {
			logger.error("Value map not provided, can not add Select List/Radio Button Field pair to map without it");
			errorFound = true;
		}
		if (null == configTextMap) {
			logger.error("Config Text Map not provided, can not add Select List/Radio Button pair to map without it");
			errorFound = true;
		}

		if (errorFound) {
			return;
		}

		String key = values.get("key", String.class);
		String label = values.get(LABEL, String.class);
		String ariaLabel = values.get(ARIA_LABEL, String.class);
		String defaultValue = values.get(DEFAULT_VALUE, String.class);
		String error = values.get(ERROR, String.class);
		if (StringUtils.isNotBlank(key)) {
			LinkedHashMap<String, Object> linkprops = new LinkedHashMap<String, Object>();
			setLinkProperties(label, ariaLabel, defaultValue, error, linkprops);
			if (null != values.get(OPTIONS_LIST)) {
				String[] configurableText = PropertiesUtil.toStringArray(values.get(OPTIONS_LIST));
				if (null != configTextMap) {
					setOptionListInMap(configurableText, linkprops);
				}
			}
			if (linkprops.size() != 0) {
				configTextMap.put(key, linkprops);
			}
		}
	}
	
	/**
	 * 
	 * @param configurableText
	 * @param linkprops
	 */
	private static void setOptionListInMap(String[] configurableText, 
			LinkedHashMap<String, Object> linkprops){
		LinkedList<Map<String, String>> optionsList = new LinkedList<Map<String, String>>();
		JSONArray propArray = new JSONArray(Arrays.asList(configurableText));
		setOptionList(optionsList, propArray);
		if (optionsList.size() != 0) {
			linkprops.put(OPTIONS_LIST, optionsList);
		}
	}

	/**
	 * Sets the option list.
	 *
	 * @param optionsList
	 *            the options list
	 * @param propArray
	 *            the prop array
	 */
	private static void setOptionList(LinkedList<Map<String, String>> optionsList, JSONArray propArray) {
		for (int index = 0; index < propArray.length(); index++) {
			Map<String, String> singleOptionValues = new LinkedHashMap<String, String>();
			try {
				JSONObject object = propArray.getJSONObject(index);
				if (null != object) {
					setOptionValueInMap(object, singleOptionValues);
					optionsList.add(singleOptionValues);
				}
			} catch (JSONException e) {
				logger.error("Exception will parsing an select list/radio button option", e);
			}
		}
	}
	
	/**
	 * 
	 * @param object
	 * @param singleOptionValues
	 * @throws JSONException
	 */
	private static void setOptionValueInMap(JSONObject object, 
			Map<String, String> singleOptionValues) throws JSONException{
		if (StringUtils.isNotEmpty(object.optString(OPTION_VALUE))) {
			singleOptionValues.put(OPTION_VALUE, object.getString(OPTION_VALUE));
		}
		if (StringUtils.isNotEmpty(object.optString(OPTION_DISPLAY_TEXT))) {
			singleOptionValues.put(OPTION_DISPLAY_TEXT, object.getString(OPTION_DISPLAY_TEXT));
		}
	}

	/**
	 * Adds the checkbox pair to map.
	 *
	 * @param values
	 *            the values
	 * @param configTextMap
	 *            the config text map
	 */
	public static void addCheckboxPairToMap(ValueMap values, Map<String, Object> configTextMap) {
		boolean errorFound = false;
		if (null == values) {
			logger.error("Value map not provided, can not add Checkbox pair to map without it");
			errorFound = true;
		}
		if (null == configTextMap) {
			logger.error("Config Text Map not provided, can not add Checkbox pair to map without it");
			errorFound = true;
		}

		if (errorFound) {
			return;
		}

		String key = values.get("key", String.class);
		String label = values.get(LABEL, String.class);
		String ariaLabel = values.get(ARIA_LABEL, String.class);
		String checkedByDefault = values.get(CHECKED_BY_DEFAULT, String.class);
		String error = values.get(ERROR, String.class);
		if (StringUtils.isNotBlank(key)) {

			LinkedHashMap<String, String> linkprops = new LinkedHashMap<String, String>();
			setLinkProps(label, ariaLabel, StringUtils.EMPTY, error, linkprops);
			if (linkprops.size() != 0) {
				if (StringUtils.isBlank(checkedByDefault)) {
					linkprops.put(CHECKED_BY_DEFAULT, "false");
				} else {
					linkprops.put(CHECKED_BY_DEFAULT, "true");
				}
				configTextMap.put(key, linkprops);
			}

		}
	}

	/**
	 * Adds the config text referenece to map.
	 *
	 * @param currentComponentPath
	 *            the current component path
	 * @param values
	 *            the values
	 * @param configTextMap
	 *            the config text map
	 * @param resolver
	 *            the resolver
	 */
	public static void addConfigTextRefereneceToMap(String currentComponentPath, ValueMap values,
			Map<String, Object> configTextMap, ResourceResolver resolver) {
		boolean errorFound = false;
		if (StringUtils.isBlank(currentComponentPath)) {
			logger.error(
					"Current Component Path was not provided, unable to add Configurable Text Reference to map without it.");
			errorFound = true;
		}
		if (null == values) {
			logger.error("Value map not provided, unable to add configurable text reference to map without it");
			errorFound = true;
		}
		if (null == configTextMap) {
			logger.error("Config Text Map not provided, unable to add configurable text reference to map without it");
			errorFound = true;
		}
		if (null == resolver) {
			logger.error("Resource Reolver not provided, unable to add configurable text reference to map without it");
			errorFound = true;
		}

		if (!errorFound) {
			String referencePath = values.get("reference", String.class);
			if (StringUtils.isNotBlank(referencePath)) {
				String configParsysFullPath = referencePath + "/jcr:content/configtext-parsys";
				Resource refereenceResource = resolver.getResource(configParsysFullPath);
				if (null != refereenceResource && !currentComponentPath.equals(referencePath)) {
					Map<String, Object> referenceMap = getConfigTextParsysMap(refereenceResource, resolver);
					configTextMap.putAll(referenceMap);
				}
			}
		}
	}

	/**
	 * Adds the image pair to map.
	 *
	 * @param component
	 *            the component
	 * @param configTextMap
	 *            the config text map
	 */
	private static void addImagePairToMap(Resource component, Map<String, Object> configTextMap) {
		ValueMap values = component.getValueMap();
		String key = values.get("key", String.class);
		String alt = values.get("alt", String.class);
		String fileReferencePath = values.get("fileReference", String.class);
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(fileReferencePath)) {
			ImageInfoBean sources = new ImageInfoBean(component);
			sources.setTitle(alt);
			configTextMap.put(key, sources);
		}
	}

}
