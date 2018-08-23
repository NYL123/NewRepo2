package com.hertz.digital.platform.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.jackrabbit.oak.commons.PropertiesUtil;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.engine.SlingRequestProcessor;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.apache.sling.settings.SlingSettingsService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.asset.api.Asset;
import com.adobe.granite.asset.api.Rendition;
import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.contentsync.handler.util.RequestResponseFactory;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.replication.ReplicationStatus;
import com.day.cq.replication.Replicator;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.WCMMode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.hertz.digital.platform.bean.BreadcrumbBean;
import com.hertz.digital.platform.bean.FooterContainerBean;
import com.hertz.digital.platform.bean.HeaderBean;
import com.hertz.digital.platform.bean.HeaderFooterBean;
import com.hertz.digital.platform.bean.HeroImageBean;
import com.hertz.digital.platform.bean.ImageInfoBean;
import com.hertz.digital.platform.bean.PasswordGrantBean;
import com.hertz.digital.platform.bean.spa.SpaPageBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.exporter.api.ComponentExporterService;
import com.hertz.digital.platform.exporter.impl.ConfigurableTextParSysExportor;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.service.api.JCRService;
import com.hertz.digital.platform.service.api.SystemUserService;

/*
 * This class serves as a utility class for methods that are used frequently
 * 
 */
public final class HertzUtils {

    private static final String THE_POPULATED_BEAN_MAP_LOOKS_LIKE = "The populated beanMap looks like:- {}";
	private static final String IDENTIFIER = "(identifier=";
	private static final String HTML = ".html";

	private HertzUtils() {
        // to prevent instantiation.
    }

    /** LOGGER instantiation. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(HertzUtils.class);

    /**
     * This method checks a particular key of type String in the property map
     * and returns that value
     * 
     * Parameters: ValueMap, String ReturnType: String
     * 
     */
    public static String getValueFromMap(ValueMap properties, String key) {

        if (properties.containsKey(key)) {
            return properties.get(key, String.class);
        } else {
            return StringUtils.EMPTY;
        }
    }

	/**
	 * This method checks a particular key of type String in the property map
	 * and returns that value
	 * 
	 * Parameters: ValueMap, String ReturnType: String
	 * 
	 */
	public static String getLocaleFromPath(String path) {
		String locale = "en";
		if (StringUtils.isNotBlank(path)) {
			LOGGER.debug("The path obtained is : - {}", path);
			String[] pathTokens = path.split("/");
			if (pathTokens.length >= 5) {
				String nodename = pathTokens[4];
				LOGGER.debug("The nodename @ 4rth position is : - {}", nodename);

				int count = 0;
				for (int j = 0; j < nodename.length(); j++) {
					char character = nodename.charAt(j);
					count++;
				}
				if (count == 5) {
					locale = nodename.split("-")[0];
					LOGGER.debug("Setting locale to {}.", locale);
				}

			} else {
				LOGGER.debug("The path is shorter than 5 tokens. Setting default locale to en.");
			}
		}
		return locale;
	}

    /**
     * This method checks a particular key of type String in the property map
     * and returns that value
     * 
     * Parameters: ValueMap, String ReturnType: String
     * 
     */
    public static Integer getIntegerValueFromMap(ValueMap properties,
            String key) {

        if (properties.containsKey(key)) {
            return properties.get(key, Integer.class);
        } else {
            return 0;
        }
    }

    /**
     * This method checks a particular key of type Boolean in the property map
     * and returns that value
     * 
     * Parameters: ValueMap, String ReturnType: Boolean
     * 
     */
    public static Boolean getBooleanValueFromMap(ValueMap properties,
            String key) {

        if (properties.containsKey(key)) {
            return properties.get(key, Boolean.class);
        } else {
            return Boolean.FALSE;
        }
    }

    /**
     * Method to get String value from object
     * 
     * @param object,key
     * @return string
     */

    public static String getStringValueFromObject(JSONObject object, String key)
            throws JSONException {
        if (null != object && StringUtils.isNotEmpty(object.optString(key))) {
            return HertzUtils.shortenIfPath(object.getString(key));
        } else {
            return StringUtils.EMPTY;
        }

    }

    /**
     * Close resolver and session if live
     * 
     * @param resolver,
     *            session
     * @return
     */
    public static void closeResolverSession(ResourceResolver resolver,
            Session session) {
        if (resolver != null && resolver.isLive()) {
            resolver.close();
        }
        if (session != null && session.isLive()) {
            session.logout();
        }
    }

    /**
     * Creating path from the selector
     * 
     * @param selectors
     * @return
     */
    public static String createPath(String[] selectors) {
        StringBuilder builder = new StringBuilder();

        for (String string : selectors) {
            if (builder.length() > 0) {
                builder.append("/");
            }
            builder.append(string);
        }

        String path = builder.toString();

        return path;

    }

    /**
     * @param response
     */
    public static void setResponseParameters(
            final SlingHttpServletResponse response) {
        response.setCharacterEncoding(HertzConstants.UTF8);
        response.setContentType(HertzConstants.APPLICATION_JSON);
    }

    /**
     * This would contain all the custom adapters which we would need in the
     * GSON object and this would be centrally used by all the JOSN Document
     * implementations.
     *
     * @param initDataTypeAdapter
     *            Whether to initialize data type adapter or not.
     * @param initCollectionsAdapter
     *            Whether to initialize collections type adapter or not.
     * @param escapeHtml
     *            Whether to escape HTML or not.
     * @return GSON Object Initialized GSON object.
     * 
     */

    public static Gson initGsonBuilder(final boolean initDataTypeAdapter,
            final boolean initCollectionsAdapter, final boolean escapeHtml) {
        LOGGER.debug("Inside method initGsonBuilder()");
        GsonBuilder gson = new GsonBuilder();

        if (initDataTypeAdapter) {
            initDataTypeAdapter(gson);
        }

        if (initCollectionsAdapter) {
            initCOllectionsAdapter(gson);
        }
        if (escapeHtml) {
            gson.disableHtmlEscaping();
        }
        return gson.create();

    }

    /**
     * Internal Method to register type hierarchy adapter on GSON object.
     * 
     * @param gson
     *            The passed gson object.
     */
    private static void initCOllectionsAdapter(final GsonBuilder gson) {
        /*
         * This adapter deals with removing empty collections from within the
         * resulting JSON. Would not work for empty maps. Set them null
         * explicitly
         */
        gson.registerTypeHierarchyAdapter(Collection.class,
                new JsonSerializer<Collection<?>>() {

                    @Override
                    public JsonElement serialize(Collection<?> src,
                            Type typeOfSrc, JsonSerializationContext context) {
                        if (src == null || src.isEmpty()) // exclusion is
                        { // made here
                            return null;
                        }
                        JsonArray array = new JsonArray();

                        for (Object child : src) {
                            JsonElement element = context.serialize(child);
                            array.add(element);
                        }

                        return array;
                    }
                });

    }

    /**
     * Internal Method to register type hierarchy adapter on GSON object.
     * 
     * @param gson
     *            The passed gson object.
     */
    private static void initDataTypeAdapter(final GsonBuilder gson) {
        /*
         * This adapter deals with identifying several data types and dumping
         * them accordingly. Use it to handle more data types later as per
         * requirements.
         */
        gson.registerTypeAdapter(Object.class, new JsonDeserializer<Object>() {

            @Override
            public Object deserialize(JsonElement json, Type typeOfT,
                    JsonDeserializationContext context)
                    throws JsonParseException {
                JsonPrimitive value = json.getAsJsonPrimitive();
                if (value.isBoolean()) {
                    return value.getAsBoolean();
                } else if (value.isNumber()) {
                    return value.getAsNumber();
                } else {
                    return value.getAsString();
                }
            }
        });
    }

    /**
     * Splits input on identifier provided, using lambda functions.
     * 
     * @param input
     *            The input string.
     * @param identifier
     *            The identifier for split feature.
     * @return The exploded array
     */
    public static String[] toArray(String input, String identifier) {
        LOGGER.debug(
                "Execution starts::toArray() for input {} and identifier {}",
                input, identifier);
        String[] output = null;
        LOGGER.debug("Execution starts::toArray()");
        if (StringUtils.isNotEmpty(input)
                && StringUtils.isNotEmpty(identifier)) {
            output = Arrays.stream(input.split(identifier)).map(String::trim)
                    .toArray(String[]::new);
        }
        LOGGER.debug("Execution ends::toArray()");
        return output;
    }

    /**
     * This method would be invoked from all the page specific Use objects which
     * intend to produce HTMLs for underlying par/* components
     * 
     * @param jcrResource
     *            The jcrResource of the request path resource.
     * @param resolver
     *            The resolver object.
     * @param jcrService
     *            The JCRService for querying.
     * @param externalizer
     *            The externalizer for converting to instance specific
     *            publisher/end-point url.
     * @return The map having the component name as key and its HTML as its
     *         value.
     * @throws RepositoryException
     * @throws JSONException
     * @throws IOException
     * @throws ServletException
     */
	public static void getAllComponentHTMLUnderPar(Resource jcrResource, ResourceResolver resolver,
			SlingScriptHelper scriptHelper, Map<String, Object> beanMap)
			throws RepositoryException, JSONException, ServletException, IOException {
		LOGGER.debug("Execution starts::getAllComponentHTMLUnderPar()");
		Map<String, String> predicateMap = new HashMap<>();
		StringBuilder stringBuilder = new StringBuilder();
		RequestResponseFactory requestResponseFactory = scriptHelper.getService(RequestResponseFactory.class);
		SlingRequestProcessor requestProcessor = scriptHelper.getService(SlingRequestProcessor.class);
		preparePredicateMap(jcrResource.getPath(), predicateMap);
		LOGGER.debug("The predicate map looks like this:- {}", predicateMap);
		JCRService jcrService = scriptHelper.getService(JCRService.class);
		HertzConfigFactory configFactory = scriptHelper.getService(HertzConfigFactory.class);
		String[] spaAllowedComponents = (String[]) configFactory
				.getPropertyValue(HertzConstants.Hertz_SPA_ALLOWED_COMPONENTS);
		if (null != jcrService) {
			// Getting all the responsivegrid on the page
			SearchResult searchResults = jcrService.searchResults(resolver, predicateMap);
			LOGGER.debug("Number of hits:- {}", searchResults.getHits().size());
			// Iterating over all the responsive grids on the page
			for (Hit hit : searchResults.getHits()) {
				Resource hitResource = hit.getResource();
				if (hitResource.getResourceType().equalsIgnoreCase("wcm/foundation/components/responsivegrid")
						&& hitResource.hasChildren() == false) {
					LOGGER.debug("hitResource No Children");
				} else {
					putDatainBeanMap(hitResource, requestResponseFactory, requestProcessor, resolver, spaAllowedComponents, 
							scriptHelper, hitResource, beanMap);

				}
			}
			populateForConfigParsys(jcrResource, beanMap, stringBuilder,
					spaAllowedComponents, requestResponseFactory, requestProcessor, resolver, scriptHelper);
		}
		LOGGER.debug("Execution ends::getAllComponentHTMLUnderPar()");

	}
    
	/**
	 * To reduce cyclomatic complexity.
	 */
	private static void populateForConfigParsys(Resource jcrResource, Map<String, Object> beanMap, StringBuilder stringBuilder, String[] spaAllowedComponents, RequestResponseFactory requestResponseFactory, 
			SlingRequestProcessor requestProcessor, ResourceResolver resolver, SlingScriptHelper scriptHelper) throws ServletException, IOException {
		Resource parResource = jcrResource.getChild(HertzConstants.CONFIGTEXT_PARSYS);
		if (null != parResource) {
			if (!ResourceUtil.isNonExistingResource(parResource)) {
				Iterable<Resource> parChildren = parResource.getChildren();
				for (Resource componentResource : parChildren) {
					String componentResourceType = componentResource.getResourceType();
					popluateBeanMapForConfigParsys(beanMap, componentResource, componentResourceType, stringBuilder,
							spaAllowedComponents, requestResponseFactory, requestProcessor, resolver, scriptHelper);
				}
			}
		}
		
	}

	/**
	 * To reduce cyclomatic complexity.
	 */
	private static void putDatainBeanMap(Resource hitResource, RequestResponseFactory requestResponseFactory,
			SlingRequestProcessor requestProcessor, ResourceResolver resolver, String[] spaAllowedComponents,
			SlingScriptHelper scriptHelper, Resource hitResource2, Map<String, Object> beanMap)
			throws ServletException, IOException {
		String requestPath = hitResource.getPath() + HTML;
		HttpServletRequest request = requestResponseFactory.createRequest("GET", requestPath);
		WCMMode.DISABLED.toRequest(request);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		HttpServletResponse response = requestResponseFactory.createResponse(out);
		requestProcessor.processRequest(request, response, resolver);
		Document doc = Jsoup.parse(out.toString());
		for (String componentName : spaAllowedComponents) {
			doc.getElementsByClass(componentName).remove();
		}
		doc.select("style,link").remove();
		replaceImageTagWithPicture(doc, resolver);
		removePatternAComponentClass(doc, resolver);
		removeHtmlExt(scriptHelper, doc, resolver);
		Element body = doc.body();
		beanMap.put(hitResource.getName(), body.html());

	}

	/**
     * To reduce cylomatic complexity.
     */
    private static void popluateBeanMapForConfigParsys(Map<String, Object> beanMap, Resource componentResource, String componentResourceType, StringBuilder stringBuilder, String[] spaAllowedComponents, RequestResponseFactory requestResponseFactory, SlingRequestProcessor requestProcessor, ResourceResolver resolver, SlingScriptHelper scriptHelper) throws ServletException, IOException {
    	char SLASH = '/';
    	if (componentResourceType.indexOf(SLASH) != -1) {
            String componentName = componentResource.getResourceType().substring(componentResourceType.lastIndexOf(SLASH)).replaceAll("/", "");
            if (!Arrays.asList(spaAllowedComponents)
                    .contains(componentName)) {
                String requestPath = componentResource.getPath()
                        + HTML;
                stringBuilder = getHTMLofComponent(resolver,
                        requestResponseFactory, requestProcessor,
                        requestPath,scriptHelper.getService(SlingSettingsService.class));
                if (StringUtils
                        .isNotBlank(stringBuilder.toString())) {
                    beanMap.put(HertzConstants.PARSYS,
                            stringBuilder);
                }
            }

        }
		
	}

	/**
     * This method removes the html extension of html markup
     * 
     * @param resolver
     * @param scriptHelper
     * @param doc
     */

    private static void removeHtmlExt(SlingScriptHelper scriptHelper, Document doc , ResourceResolver resolver) {
    	SlingSettingsService slingService=scriptHelper.getService(SlingSettingsService.class);
        if(slingService.getRunModes().contains("author")){
        	removeHtmlExtensionFromUrl(doc, resolver);
        }
		
	}

	/**
     * This method gets the html of the component
     * 
     * @param resolver
     * @param requestResponseFactory
     * @param requestProcessor
     * @param requestPath
     * @throws ServletException
     * @throws IOException
     */
    public static StringBuilder getHTMLofComponent(ResourceResolver resolver,
            RequestResponseFactory requestResponseFactory,
            SlingRequestProcessor requestProcessor, String requestPath, SlingSettingsService slingService)
            throws ServletException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        HttpServletRequest request = requestResponseFactory.createRequest("GET",
                requestPath);
        WCMMode.DISABLED.toRequest(request);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpServletResponse response = requestResponseFactory
                .createResponse(out);
        requestProcessor.processRequest(request, response, resolver);
        Document doc = Jsoup.parse(out.toString());
        doc.select("script,style,link").remove();
        HertzUtils.replaceImageTagWithPicture(doc, resolver);
        if(slingService.getRunModes().contains("author")){
        	removeHtmlExtensionFromUrl(doc, resolver);
        }
        Element body = doc.body();
        return stringBuilder.append(body.unwrap());
    }

    /**
     * This method prepares the predicate map for the search
     * 
     * @param parentPath
     * @param predicateMap
     */
    public static void preparePredicateMap(String parentPath,
            Map<String, String> predicateMap) {
        predicateMap.put(HertzConstants.TYPE, "nt:unstructured");
        predicateMap.put(HertzConstants.PATH, parentPath);
        predicateMap.put("property", HertzConstants.SLING_RESOURCE_TYPE);
        predicateMap.put("property.value",
                HertzConstants.RESPONSIVE_GRID_RES_TYPE);
        predicateMap.put(HertzConstants.LIMIT, "-1");
        predicateMap.put("path.flat", "true");
    }

    public static Map<String, Object> getRapidComponents(Resource resource, ResourceResolver resourceResolver) {

        LinkedHashMap<String,Object> configRapidMap = new LinkedHashMap<String, Object>();
        LinkedHashMap<String,Object> configRapidReferenceMap = new LinkedHashMap<String, Object>();

        if (resource.hasChildren()) {
            Iterator<Resource> rapidComponents = resource.listChildren();
            while (rapidComponents.hasNext()) {
            	Resource component = rapidComponents.next();
            	extractRapidProperties(resource, component, configRapidReferenceMap, configRapidReferenceMap, resourceResolver);
            	
            }
        }
        configRapidMap.putAll(configRapidReferenceMap);     
        return configRapidMap;
    }

	/**
	 * To reduce cyclomatic complexity.
	 * @param component2 
	 * @param configRapidReferenceMap 
	 * @param resourceResolver 
	 */
	private static void extractRapidProperties(Resource resource, Resource component, LinkedHashMap<String, Object> configRapidMap, LinkedHashMap<String, Object> configRapidReferenceMap, ResourceResolver resourceResolver) {
		
		 
         if(component.getResourceType().equalsIgnoreCase("hertz/components/content/rapidreference")) {
         	configRapidReferenceMap = (LinkedHashMap<String, Object>) ConfigurableTextParSysExportor.getConfigTextParsysMap(resource, resourceResolver);                 	
         } else {

         	Node componentNode = component.adaptTo(Node.class);
             try {
                 if (componentNode.hasProperties()) {
                     String key = componentNode.getName();
                     PropertyIterator props = componentNode.getProperties();
                     LinkedHashMap<String, Object> linkprops = new LinkedHashMap<String, Object>();
     				while (props.hasNext()) {
     					
     					
     						extractProperties(component, linkprops , props);
     					
     				}

                     populateMap(linkprops, linkprops, key);
                 }

             } catch (RepositoryException e) {
                 LOGGER.error(e.toString());
             }
         
         	}
	}

	private static void populateMap(Map<String, Object> linkprops, Map<String, Object> configRapidMap, String key) {
		// TODO Auto-generated method stub
		if (!linkprops.isEmpty()) {
            configRapidMap.put(key, linkprops);
        }
		
	}

	/**
	 * @param component
	 * @param linkprops
	 * @param props 
	 * @param property
	 * @param props 
	 * @throws RepositoryException
	 * @throws ValueFormatException
	 */
	private static void extractProperties(Resource component, LinkedHashMap<String, Object> linkprops, PropertyIterator props
			) throws RepositoryException, ValueFormatException {
		Property property = props.nextProperty();
		if (property != null) {
			String propName = property.getName();
		
		if (!propName.equals(HertzConstants.JCR_CREATED_BY) && !propName.equals(HertzConstants.JCR_CREATED)
				&& !propName.equals(HertzConstants.SLING_RESOURCE_TYPE)) {
			if (!propName.equals(HertzConstants.JCR_LAST_MODIFIED_BY)
					&& !propName.equals(HertzConstants.JCR_LAST_MODIFIED)
					&& !propName.equals(HertzConstants.JCR_PRIMARY_TYPE)) {
				if (property.isMultiple()) {
					Value[] values = property.getValues();
					linkprops.put(propName, Arrays.toString(values));
				} else {
					setImageInfoExtractProperties(propName, property, propName, propName, linkprops, component);
				}
			}
		}
	}
	}
    
    /**
     *TO reduce cyclomatic complexity.
     */
    private static void setImageInfoExtractProperties(String propName, Property property, String propName2, String propName3, LinkedHashMap<String, Object> linkprops, Resource component) throws ValueFormatException, RepositoryException {
    	if (propName.equalsIgnoreCase(HertzConstants.FILE_REFERENCE)) {
			// Generate renditions for the image.
			String fileReference = property.getString();
			if (StringUtils.isNotBlank(fileReference)) {
				ImageInfoBean sources = new ImageInfoBean(component);
				linkprops.put(propName, sources);
			}

		} else if (propName.equalsIgnoreCase("href")) {
			linkprops.put(propName, HertzUtils.shortenIfPath(property.getString()));
		} else {
			linkprops.put(propName, property.getString());
		}
		
	}

	/**
     * This method returns the map of hertz links page properties
     * @param resource
     * @return hertzLinksMap
     */
    public static Map<String, Object> getHertzLinksProps(Resource resource) {
    	ValueMap hertzLinksProps = resource.getValueMap();
    	Map<String, Object> hertzLinksPropsMap = new HashMap<>();
    	Map<String, Object> hertzLinksMap = new HashMap<>();
    	if(hertzLinksProps.containsKey(HertzConstants.LABEL) && hertzLinksProps.containsKey(HertzConstants.VALUE)){
    		hertzLinksPropsMap.put(HertzConstants.LABEL, hertzLinksProps.get(HertzConstants.LABEL));
    		hertzLinksPropsMap.put(HertzConstants.VALUE, hertzLinksProps.get(HertzConstants.VALUE));
    	}
    	if(MapUtils.isNotEmpty(hertzLinksPropsMap)){
    		hertzLinksMap.put("cp", hertzLinksPropsMap);
    	}
        return hertzLinksMap;
    }

    /**
     * @param jcrResource
     * @param components
     * @param resolver
     * @param scriptHelper
     * @return
     * @throws JSONException
     * @throws RepositoryException
     */
    public static Map<String, Object> getStaticIncludedComponentsAsBean(
            Resource jcrResource, String[] components,
            ResourceResolver resolver, SlingScriptHelper scriptHelper)
            throws JSONException, RepositoryException {
        Map<String, Object> beanMap = new LinkedHashMap<>();
        if (!ArrayUtils.isEmpty(components)) {
            Page page = jcrResource.getParent().adaptTo(Page.class);
            LOGGER.debug("Resolved page path is :- {}", page.getPath());
            for (String component : components) {
                String[] compConfigArray = toArray(component,
                        HertzConstants.COLON);
                LOGGER.debug("Calling exporter service for component:- {}",
                        compConfigArray[0]);
                ComponentExporterService[] exporterService = scriptHelper
                        .getServices(ComponentExporterService.class,
                                IDENTIFIER + compConfigArray[0] + ")");
                putBeanInBeanMap(exporterService, compConfigArray, beanMap, resolver, page);
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(THE_POPULATED_BEAN_MAP_LOOKS_LIKE, beanMap);
            }
        }
        return beanMap;
    }

    /**
     * To reduce cyclomatic complexity.
     *
     */
    private static void putBeanInBeanMap(ComponentExporterService[] exporterService, String[] compConfigArray, Map<String, Object> beanMap, ResourceResolver resolver, Page page) {
    	if (null != exporterService && exporterService.length > 0) {
            LOGGER.debug(
                    "Instance {} for the {} identifier found. Invoking for bean conversion.",
                    exporterService[0].getClass().getCanonicalName(),
                    compConfigArray[0]);
            Object bean = exporterService[0].exportAsBean(
                    page.getContentResource(compConfigArray[0]),
                    resolver);
            if (null != bean) {
                beanMap.put(compConfigArray[0], bean);
            }
        }
		
	}

	/**
     * This method would be called by all the page specific Use classes
     * intending to expose directly included components as a part of their JSON
     * document.
     * 
     * @param jcrResource
     *            The resource under which the components have to be converted.
     *            For e.g /content/.../homepage/jcr:content
     * @param components
     *            The component names as they would be found under respective
     *            jcr:content. Supplied from the page.spa.html Use inclusion.
     * @param resolver
     *            The resolver object.
     * @param scriptHelper
     *            The script helper object.
     * 
     * @return The map having the component name as key and the populated custom
     *         bean as value.
     * @throws JSONException
     * @throws RepositoryException
     * @throws IOException
     * @throws ServletException
     */
    public static Map<String, Object> getIncludedComponentsAsBeans(
    		RequestPathInfo requestPathInfo, Resource jcrResource, String[] components,
            ResourceResolver resolver, SlingScriptHelper scriptHelper)
            throws JSONException, RepositoryException, ServletException,
            IOException {
        LOGGER.debug("Execution starts::getIncludedComponentsAsBeans()");
        // for keeping the insertion order of the nodes.
        Map<String, Object> beanMap = new LinkedHashMap<>();
        if (null != jcrResource && null != resolver && null != scriptHelper) {
            if (!ArrayUtils.isEmpty(components)) {
                Page page = jcrResource.getParent().adaptTo(Page.class);
                LOGGER.debug("Resolved page path is :- {}", page.getPath());
                for (String component : components) {
                    String[] compConfigArray = toArray(component,
                            HertzConstants.COLON);
                    // if (null != page.getContentResource(compConfigArray[0]))
                    // {
                    LOGGER.debug("Calling exporter service for component:- {}",
                            compConfigArray[0]);
                    ComponentExporterService[] exporterService = scriptHelper
                            .getServices(ComponentExporterService.class,
                                    IDENTIFIER + compConfigArray[0] + ")");
                    getUsingExporterService(exporterService, compConfigArray, jcrResource, resolver, beanMap, page);
                }
                String cpCodePagePath = getCPCodePagePath(resolver, requestPathInfo, jcrResource);
                if(StringUtils.isNotEmpty(cpCodePagePath)){
                	setPartnerHomeDataInJSON(scriptHelper, resolver, beanMap, cpCodePagePath);
                }
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(THE_POPULATED_BEAN_MAP_LOOKS_LIKE,
                            beanMap);
                }
            }
            HertzUtils.getAllComponentHTMLUnderPar(jcrResource, resolver,
                    scriptHelper, beanMap);
        }
        LOGGER.debug(THE_POPULATED_BEAN_MAP_LOOKS_LIKE, beanMap);
        LOGGER.debug("Execution ends::getIncludedComponentsAsBeans()");
        return beanMap;
    }
    
    /**
     * To reduce cyclomatic complexity.
     */
    private static void getUsingExporterService(ComponentExporterService[] exporterService, String[] compConfigArray, Resource jcrResource, ResourceResolver resolver, Map<String, Object> beanMap, Page page) {
    	if (null != exporterService && exporterService.length > 0) {
            LOGGER.debug(
                    "Instance {} for the {} identifier found. Invoking for bean conversion.",
                    exporterService[0].getClass()
                            .getCanonicalName(),
                    compConfigArray[0]);
            /*
             * Change here for fixed slots, RAC-11246
             * 
             * The spa json will send the component name and not
             * node name.
             */
            String resourceType = "hertz/components/content/"
                    + compConfigArray[0];
            getFixedSlotData(jcrResource, resolver, beanMap, page,
                    compConfigArray, exporterService, resourceType);
        }
	}

	public static void setPartnerHomeDataInJSON(SlingScriptHelper scriptHelper, 
    		ResourceResolver resolver, Map<String, Object> beanMap, String cpCodePagePath){
    	LOGGER.debug("Start:- setPartnerHomeDataInJSON() of HertzUtils");
    	Resource cpCodePageResource = resolver.getResource(cpCodePagePath);
    	Resource cpCodeJcrResource = cpCodePageResource.getChild(HertzConstants.JCR_CONTENT);
    	Iterator<Resource> iterator = cpCodeJcrResource.listChildren();
        while (iterator.hasNext()) {
            Resource resource = iterator.next();
            String resourceType = resource.getResourceType();
            if (HertzConstants.HERO_RES_TYPE
                    .equalsIgnoreCase(resourceType)) {
            	HeroImageBean heroImageBean=new HeroImageBean();
            	ValueMap map=resource.getValueMap();
                if(map.containsKey(HertzConstants.ALT_TEXT)) {
                    heroImageBean.setAltText((String) map.get(HertzConstants.ALT_TEXT));
                }
                if(map.containsKey(HertzConstants.TAGLINE_TEXT)){
    				heroImageBean.setTagLineText((String) map.get(HertzConstants.TAGLINE_TEXT));
    			}
                if(map.containsKey(HertzConstants.FILE_REFERENCE)){
                    ImageInfoBean imageInfo = new ImageInfoBean(resource);
                    heroImageBean.setSources(imageInfo.getSources());
                }
                beanMap.put(resource.getName(),heroImageBean);
            }
            if (HertzConstants.FIXED_CONTENT_SLOT_RES_TYPE
                    .equalsIgnoreCase(resourceType)) {
            	String identifier = resourceType
                        .substring(resourceType
                                .lastIndexOf('/'))
                        .replaceAll("/", "");
                ComponentExporterService[] exporterService = scriptHelper
                        .getServices(ComponentExporterService.class,
                                IDENTIFIER + identifier + ")");
                beanMap.put(resource.getName(),
                        exporterService[0].exportAsBean(resource,resolver));
            }
        }
        LOGGER.debug("End:- setPartnerHomeDataInJSON() of HertzUtils");
    }

    /**
     * Generate fixed slot data
     * 
     * @param jcrResource
     * @param resolver
     * @param beanMap
     * @param page
     * @param compConfigArray
     * @param exporterService
     * @param resourceType
     */
    public static void getFixedSlotData(Resource jcrResource,
            ResourceResolver resolver, Map<String, Object> beanMap, Page page,
            String[] compConfigArray,
            ComponentExporterService[] exporterService, String resourceType) {
        if (resourceType
                .equalsIgnoreCase("hertz/components/content/browsepartners")) {

            beanMap.put(compConfigArray[0],
                    exporterService[0].exportAsBean(jcrResource, resolver));
        } else if(resourceType.equalsIgnoreCase("hertz/components/content/image")){
            getDefautImageResource(jcrResource, exporterService, page, resolver, beanMap, compConfigArray);
        }else if(resourceType.equalsIgnoreCase("hertz/components/content/locationdirectoryhero")){
            getHeroImageResource(jcrResource, page, beanMap, exporterService, resolver);
        }else {
            Iterator<Resource> iterator = jcrResource.listChildren();
            while (iterator.hasNext()) {
                Resource resource = iterator.next();
                if (resourceType
                        .equalsIgnoreCase((String) resource.getValueMap()
                                .get(HertzConstants.SLING_RESOURCE_TYPE))) {

                    beanMap.put(resource.getName(),
                            exporterService[0].exportAsBean(
                                    page.getContentResource(resource.getName()),
                                    resolver));
                }
            }
        }
    
    }
    
    /**
     * To reduce cyclomatic complexity.
     */
    private static void getHeroImageResource(Resource jcrResource, Page page, Map<String, Object> beanMap, ComponentExporterService[] exporterService, ResourceResolver resolver) {
    	Resource imageResource=jcrResource.getChild(HertzConstants.HERO);
        if(null==imageResource){
            Page parentPage=page.getAbsoluteParent(4);
            HeroImageBean heroImageBean=new HeroImageBean();
            if(null!=parentPage){
                Resource heroImageResource=parentPage.getContentResource(HertzConstants.HERO);
                if(null!=heroImageResource && heroImageResource.getValueMap().containsKey(HertzConstants.ALT_TEXT)){
                    
                        heroImageBean.setAltText((String) heroImageResource.getValueMap().get(HertzConstants.ALT_TEXT));
                    
                } else if (null!=heroImageResource && heroImageResource.getValueMap().containsKey(HertzConstants.FILE_REFERENCE)){
                	    ImageInfoBean imageInfo = new ImageInfoBean(heroImageResource);
                        heroImageBean.setSources(imageInfo.getSources());
                    }
                    beanMap.put(heroImageResource.getName(),heroImageBean);
                }
        }else{
            beanMap.put(imageResource.getName(),
                    exporterService[0].exportAsBean(
                            page.getContentResource(imageResource.getName()),
                            resolver));
        }
		
	}

	/**
     * To reduce cyclomatic complexity.
     */
    private static void getDefautImageResource(Resource jcrResource, ComponentExporterService[] exporterService, Page page, ResourceResolver resolver, Map<String, Object> beanMap, String[] compConfigArray) {
    	Resource imageResource=jcrResource.getChild(HertzConstants.IMAGE);
        if(null==imageResource){
            Resource defaultImageResource=getDefaultImageResource(jcrResource,resolver);
            if(null!=defaultImageResource){
                beanMap.put(defaultImageResource.getName(),
                        exporterService[0].exportAsBean(
                                defaultImageResource,resolver));
            }
        }else{
            if(imageResource.getValueMap().containsKey(HertzConstants.FILE_REFERENCE)){
                beanMap.put(imageResource.getName(),
                        exporterService[0].exportAsBean(
                                page.getContentResource(imageResource.getName()),
                                resolver));
            }else{
                Resource defaultImageResource=getDefaultImageResource(jcrResource,resolver);
                if(null!=defaultImageResource){
                beanMap.put(defaultImageResource.getName(),
                        exporterService[0].exportAsBean(
                                defaultImageResource,resolver));
                }
            }                           
            
        }
	}

	/**
	 * Method to get the Path of CPCode Page
	 * @return String cpCodePagePath
	 */
	public static String getCPCodePagePath(ResourceResolver resolver, RequestPathInfo requestPathInfo, Resource jcrResource) {
		LOGGER.debug("Start:- getCPCodePagePath() of HertzUtils");
		String cpCode = StringUtils.EMPTY;
		String cpCodePagePath = StringUtils.EMPTY;
		String[] selectors = requestPathInfo.getSelectors();
		ValueMap pageProperties = jcrResource.getValueMap();
		if (selectors.length > 1) {
			cpCode = selectors[1];
			String pagePath = pageProperties.get(HertzConstants.PARTNER_BASE_PAGE_PATH, String.class);
			if (StringUtils.isNotEmpty(pagePath) && isValidCpCode(resolver, cpCode, pagePath)) {
				cpCodePagePath = pagePath + HertzConstants.SLASH + cpCode;
			} else {
				cpCodePagePath = StringUtils.EMPTY;
			}
		}
		LOGGER.debug("End:- getCPCodePagePath() of HertzUtils");
		return cpCodePagePath;
	}
	
	/**
	 * Method to check if cpcode is valid
	 * @param cpCode
	 * @param pagePath
	 * @return
	 */
	public static boolean isValidCpCode(ResourceResolver resolver, String cpCode, String pagePath){
		boolean isValid=false;
		Resource partnerHomePageResource = resolver.getResource(pagePath);
		if(HertzUtils.isValidResource(partnerHomePageResource.getChild(cpCode))){
			isValid=true;
		}
		return isValid;
	}

    /**
     * @param jcrResource
     * @param resolver
     * @return
     */
    public static Resource getDefaultImageResource(Resource jcrResource,
            ResourceResolver resolver) {

        String path = jcrResource.getParent().getPath();
        Resource pageResource = resolver.getResource(path);
        if (null != pageResource) {
            Resource defaultResource = pageResource.getParent()
                    .getChild(HertzConstants.DEFAULT);
            if (null == defaultResource) {
                return getMainDefaultImage(jcrResource, resolver);
            } else {
                Resource defaultJcrResource = defaultResource
                        .getChild(HertzConstants.JCR_CONTENT);
                if (null != defaultJcrResource) {
                    return getChildImageResource(defaultJcrResource, jcrResource, resolver);
                }
            }
        }
        return null;
    }

    /**
     * To reduce cyclomatic complexity.
     */
    private static Resource getChildImageResource(Resource defaultJcrResource, Resource jcrResource, ResourceResolver resolver) {
    	Resource imageResource = defaultJcrResource
                .getChild(HertzConstants.IMAGE);
        if (null == imageResource) {
            return getMainDefaultImage(jcrResource, resolver);
        } else {
            if (imageResource.getValueMap()
                    .containsKey(HertzConstants.FILE_REFERENCE)) {
                return imageResource;
            } else {
                return getMainDefaultImage(jcrResource, resolver);
            }
        }
		
	}

	/**
     * @param jcrResource
     * @param resolver
     * @return
     */
    private static Resource getMainDefaultImage(Resource jcrResource,
            ResourceResolver resolver) {

        String path = jcrResource.getParent().getPath();
        StringBuilder finalString = new StringBuilder();

        String[] pathArray = path.split("/");
        if (pathArray.length == 8) {
            for (int index = 1; index < 6; index++) {
                finalString.append("/").append(pathArray[index]);
            }
            finalString = finalString.append("/default/default");
        }

        Resource defaultResource = resolver.getResource(finalString.toString());
        if (null != defaultResource) {
            Resource defaultJcrResource = defaultResource
                    .getChild(HertzConstants.JCR_CONTENT);
            if (null != defaultJcrResource) {
                Resource imageResource = defaultJcrResource
                        .getChild(HertzConstants.IMAGE);
                if (null != imageResource && imageResource.getValueMap()
                        .containsKey(HertzConstants.FILE_REFERENCE)) {
                   
                        return imageResource;
                   
                }
            }
        }
        return null;
    }

    /**
     * This method is used to set the configured properties in the map.
     * 
     * @param configuredPropertiesMap
     * @param object
     * @throws JSONException
     */
    private static void setConfiguredPropertiesMap(
            Map<String, Object> configuredPropertiesMap, JSONObject object)
            throws JSONException {
        setInMap(object, configuredPropertiesMap, HertzConstants.ELEMENT,
                HertzConstants.CONFIGURED_CHECKBOX_ELEMENT);
        setInMap(object, configuredPropertiesMap, HertzConstants.ELEMENT_GROUP,
                HertzConstants.CONFIGURED_CHECKBOX_ELEMENT_GROUP);
        setInMapDynamic(object, configuredPropertiesMap,
                HertzConstants.CHECKBOX_KEY, HertzConstants.CHECKBOX_VALUE);
        setInMapDynamic(object, configuredPropertiesMap,
                HertzConstants.CONFIGURED_CHECKBOX_ARIA_LABEL_KEY,
                HertzConstants.CONFIGURED_CHECKBOX_ARIA_LABEL_VALUE);
        setInMapDynamic(object, configuredPropertiesMap,
                HertzConstants.CONFIGURED_CHECKBOX_ORDER_KEY,
                HertzConstants.CONFIGURED_CHECKBOX_ORDER_VALUE);
        if (StringUtils.isNotEmpty(
                object.getString(HertzConstants.IS_DEFAULT_CHECKBOX_KEY))) {
            configuredPropertiesMap.put(
                    object.getString(HertzConstants.IS_DEFAULT_CHECKBOX_KEY),
                    object.getBoolean(
                            HertzConstants.IS_DEFAULT_CHECKBOX_VALUE));
        }
    }

    /**
     * This method sets all the configured checkbox field properties in the map.
     * 
     * @param configuredPropertiesMap
     * @param configurableText
     * @throws JSONException
     */
    private static void setConfigurableErrorMessagesFieldsInMap(
            Map<String, Object> configuredPropertiesMap,
            String[] configurableText) throws JSONException {
        LOGGER.debug(
                "Execution starts- setConfigurableErrorMessagesFieldsInMap");
        if (null != configurableText) {
            JSONArray propArray = new JSONArray(
                    Arrays.asList(configurableText));
            for (int index = 0; index < propArray.length(); index++) {
                JSONObject object = propArray.getJSONObject(index);
                if (null != object && StringUtils
                        .isNotEmpty(object.getString(
                                HertzConstants.CONFIGURED_ERROR_MESSAGE_KEY))
                        && StringUtils.isNotEmpty(object.getString(
                                HertzConstants.CONFIGURED_ERROR_MESSAGE_VALUE))) {
                   
                        configuredPropertiesMap.put(
                                object.getString(
                                        HertzConstants.CONFIGURED_ERROR_MESSAGE_KEY),
                                object.getString(
                                        HertzConstants.CONFIGURED_ERROR_MESSAGE_VALUE));
                    
                }
            }
        }
        LOGGER.debug("Execution ends- setConfigurableErrorMessagesFieldsInMap");
    }

    /**
     * This method sets all the configured checkbox field properties in the map.
     * 
     * @param configuredPropertiesMap
     * @param configurableText
     * @throws JSONException
     */
    private static void setConfigurableMultiValuesFieldsInMap(
            Map<String, Object> configuredPropertiesMap,
            String configurableMultiValuesKey,
            String configurableMultiValuesElementName,
            String[] configurableMultiValues) throws JSONException {
        LOGGER.debug("Execution starts- setConfigurableMultiValuesFieldsInMap");
        if (null != configurableMultiValues) {
            List<Map<String, Object>> multiValuesList = new ArrayList<>();
            JSONArray propArray = new JSONArray(
                    Arrays.asList(configurableMultiValues));
            for (int index = 0; index < propArray.length(); index++) {
                Map<String, Object> keyValueMap = new HashMap<>();
                JSONObject object = propArray.getJSONObject(index);
                if (null != object && StringUtils
                        .isNotEmpty(object.getString(
                                HertzConstants.CONFIGURED_MULTI_VALUE_KEY))
                        && StringUtils.isNotEmpty(object.getString(
                                HertzConstants.CONFIGURED_MULTI_VALUE))) {
                    
                        keyValueMap.put(
                                object.getString(
                                        HertzConstants.CONFIGURED_MULTI_VALUE_KEY),
                                object.getString(
                                        HertzConstants.CONFIGURED_MULTI_VALUE));
                    
                    multiValuesList.add(keyValueMap);
                }
            }
            configuredPropertiesMap.put(configurableMultiValuesKey,
                    multiValuesList);
            if (StringUtils.isNotEmpty(configurableMultiValuesElementName)) {
                configuredPropertiesMap.put(HertzConstants.ELEMENT,
                        configurableMultiValuesElementName);
            }
        }
        LOGGER.debug("Execution ends- setConfigurableMultiValuesFieldsInMap");
    }

     /**
     * This method is used to set the configured radio button properties in map.
     * 
     * @param configuredPropertiesMap
     * @param object
     * @throws JSONException
     */
    private static void setConfiguredRadioPropertiesInMap(
            Map<String, Object> configuredPropertiesMap, JSONObject object)
            throws JSONException {
        setInMap(object, configuredPropertiesMap, HertzConstants.ELEMENT,
                HertzConstants.CONFIGURED_RADIO_BUTTON_ELEMENT);
        setInMap(object, configuredPropertiesMap, HertzConstants.ELEMENT_GROUP,
                HertzConstants.CONFIGURED_RADIO_BUTTON_ELEMENT_GROUP);
        setInMapDynamic(object, configuredPropertiesMap,
                HertzConstants.RADIO_BUTTON_KEY,
                HertzConstants.RADIO_BUTTON_VALUE);
        setInMapDynamic(object, configuredPropertiesMap,
                HertzConstants.CONFIGURED_RADIO_BUTTON_ARIA_LABEL_KEY,
                HertzConstants.CONFIGURED_RADIO_BUTTON_ARIA_LABEL_VALUE);
        setInMapDynamic(object, configuredPropertiesMap,
                HertzConstants.CONFIGURED_RADIO_BUTTON_ORDER_KEY,
                HertzConstants.CONFIGURED_RADIO_BUTTON_ORDER_VALUE);
        if (StringUtils.isNotEmpty(
                object.getString(HertzConstants.IS_DEFAULT_RADIO_BUTTON_KEY))) {
            configuredPropertiesMap.put(
                    object.getString(
                            HertzConstants.IS_DEFAULT_RADIO_BUTTON_KEY),
                    object.getBoolean(
                            HertzConstants.IS_DEFAULT_RADIO_BUTTON_VALUE));
        }
    }

    /**
     * This method is used to get all the page properties that are made
     * configurable.
     * 
     * @param jcrResource
     * @return configuredPropertiesMap
     * @throws JSONException
     */
    public static Map<String, Object> getPagePropertiesConfigData(
            Resource jcrResource) throws JSONException {
        LOGGER.debug("Execution starts- getPagePropertiesConfigData");
        Map<String, Object> configuredPropertiesMap = new HashMap<>();
        ValueMap propertiesMap = jcrResource.getValueMap();
        if (MapUtils.isNotEmpty(propertiesMap)) {
            setTextFields(configuredPropertiesMap, propertiesMap);
            setCheckboxFields(configuredPropertiesMap, propertiesMap);
            setRadioFields(configuredPropertiesMap, propertiesMap);
            setTextAreaFields(configuredPropertiesMap, propertiesMap);
            setMultiValuesFields(configuredPropertiesMap, propertiesMap);
            setMultiGroupsFields(configuredPropertiesMap, propertiesMap);
            setErrorFields(configuredPropertiesMap, propertiesMap);
            setLinkFields(configuredPropertiesMap, propertiesMap);
            if (null != jcrResource.getChild(HertzConstants.CONFIGURED_IMAGE)) {
                Resource imageResource = jcrResource
                        .getChild(HertzConstants.CONFIGURED_IMAGE);
                setConfigurableImagesInMap(configuredPropertiesMap,
                        imageResource);
            }
        }
        LOGGER.debug("The configuration properties map will look like:- {}",
                configuredPropertiesMap);
        LOGGER.debug("Execution ends - getPagePropertiesConfigData");
        return configuredPropertiesMap;
    }

    /**
     * @param configuredPropertiesMap
     * @param propertiesMap
     * @throws JSONException
     */
    private static void setLinkFields(
            Map<String, Object> configuredPropertiesMap, ValueMap propertiesMap)
            throws JSONException {
        if (ArrayUtils.isNotEmpty(PropertiesUtil.toStringArray(
                propertiesMap.get(HertzConstants.CONFIGURED_LINKS)))) {
            String[] configurableLinks = PropertiesUtil.toStringArray(
                    propertiesMap.get(HertzConstants.CONFIGURED_LINKS));
            setConfigurableLinksInMap(configuredPropertiesMap,
                    configurableLinks);
        }
    }

    /**
     * @param configuredPropertiesMap
     * @param propertiesMap
     * @throws JSONException
     */
    private static void setErrorFields(
            Map<String, Object> configuredPropertiesMap, ValueMap propertiesMap)
            throws JSONException {
        if (ArrayUtils.isNotEmpty(PropertiesUtil.toStringArray(propertiesMap
                .get(HertzConstants.CONFIGURED_MULTI_ERROR_MESSAGES_ITEMS)))) {
            String[] configurableErrorMessages = PropertiesUtil
                    .toStringArray(propertiesMap.get(
                            HertzConstants.CONFIGURED_MULTI_ERROR_MESSAGES_ITEMS));
            setConfigurableErrorMessagesFieldsInMap(configuredPropertiesMap,
                    configurableErrorMessages);
        }
    }

    /**
     * @param configuredPropertiesMap
     * @param propertiesMap
     * @throws JSONException
     */
    private static void setMultiGroupsFields(
            Map<String, Object> configuredPropertiesMap, ValueMap propertiesMap)
            throws JSONException {
        if (ArrayUtils.isNotEmpty(PropertiesUtil.toStringArray(
                propertiesMap.get(HertzConstants.CONFIGURED_MULTI_GROUPS)))) {
            String[] configurableMultiGroupItems = PropertiesUtil.toStringArray(
                    propertiesMap.get(HertzConstants.CONFIGURED_MULTI_GROUPS));
            setConfigurableMultiGroupItemsInMap(configuredPropertiesMap,
                    configurableMultiGroupItems);
        }
    }

    /**
     * @param configuredPropertiesMap
     * @param propertiesMap
     * @throws JSONException
     */
    private static void setMultiValuesFields(
            Map<String, Object> configuredPropertiesMap, ValueMap propertiesMap)
            throws JSONException {
        if (StringUtils.isNotEmpty(
                (HertzConstants.CONFIGURED_MULTI_VALUES_KEY).toString())
                && ArrayUtils.isNotEmpty(
                        PropertiesUtil.toStringArray(propertiesMap.get(
                                HertzConstants.CONFIGURED_MULTI_VALUES)))) {
            String configurableMultiValuesKey = propertiesMap
                    .get(HertzConstants.CONFIGURED_MULTI_VALUES_KEY).toString();
            String configurableMultiValuesElementName = propertiesMap
                    .get(HertzConstants.CONFIGURED_MULTI_VALUES_ELEMENT_NAME)
                    .toString();
            String[] configurableMultiValues = PropertiesUtil.toStringArray(
                    propertiesMap.get(HertzConstants.CONFIGURED_MULTI_VALUES));
            setConfigurableMultiValuesFieldsInMap(configuredPropertiesMap,
                    configurableMultiValuesKey,
                    configurableMultiValuesElementName,
                    configurableMultiValues);
        }
    }

    /**
     * @param configuredPropertiesMap
     * @param propertiesMap
     * @throws JSONException
     */
    private static void setTextAreaFields(
            Map<String, Object> configuredPropertiesMap, ValueMap propertiesMap)
            throws JSONException {
        if (ArrayUtils.isNotEmpty(PropertiesUtil.toStringArray(
                propertiesMap.get(HertzConstants.CONFIGURED_TEXTAREA)))) {
            String[] configurableTextArea = PropertiesUtil.toStringArray(
                    propertiesMap.get(HertzConstants.CONFIGURED_TEXTAREA));
            setConfigurableTextAreasInMap(configuredPropertiesMap,
                    configurableTextArea);
        }
    }

    /**
     * @param configuredPropertiesMap
     * @param propertiesMap
     * @throws JSONException
     */
    private static void setRadioFields(
            Map<String, Object> configuredPropertiesMap, ValueMap propertiesMap)
            throws JSONException {
        if (ArrayUtils.isNotEmpty(PropertiesUtil.toStringArray(
                propertiesMap.get(HertzConstants.CONFIGURED_RADIO_BUTTONS)))) {
            String[] configurableRadioButtons = PropertiesUtil.toStringArray(
                    propertiesMap.get(HertzConstants.CONFIGURED_RADIO_BUTTONS));
            setConfigurableRadioButtonFieldsInMap(configuredPropertiesMap,
                    configurableRadioButtons);
        }
    }

    /**
     * @param configuredPropertiesMap
     * @param propertiesMap
     * @throws JSONException
     */
    private static void setCheckboxFields(
            Map<String, Object> configuredPropertiesMap, ValueMap propertiesMap)
            throws JSONException {
        if (ArrayUtils.isNotEmpty(PropertiesUtil.toStringArray(
                propertiesMap.get(HertzConstants.CONFIGURED_CHECKBOXES)))) {
            String[] configurableCheckbox = PropertiesUtil.toStringArray(
                    propertiesMap.get(HertzConstants.CONFIGURED_CHECKBOXES));
            setConfigurableCheckboxFieldsInMap(configuredPropertiesMap,
                    configurableCheckbox);
        }
    }

    /**
     * @param configuredPropertiesMap
     * @param propertiesMap
     * @throws JSONException
     */
    private static void setTextFields(
            Map<String, Object> configuredPropertiesMap, ValueMap propertiesMap)
            throws JSONException {
        if (ArrayUtils.isNotEmpty(PropertiesUtil.toStringArray(
                propertiesMap.get(HertzConstants.CONFIGURED_TEXT)))) {
            String[] configurableText = PropertiesUtil.toStringArray(
                    propertiesMap.get(HertzConstants.CONFIGURED_TEXT));
            setConfigurableTextFieldsInMap(configuredPropertiesMap,
                    configurableText);
        }
    }

   /**
     * This method sets all the configured checkbox field properties in the map.
     * 
     * @param configuredPropertiesMap
     * @param configurableText
     * @throws JSONException
     */
    private static void setConfigurableMultiGroupItemsInMap(
            Map<String, Object> configuredPropertiesMap,
            String[] configurableMultiGroupItems) throws JSONException {
        LOGGER.debug("Execution starts- setConfigurableMultiGroupItemsInMap");
        
        if (null != configurableMultiGroupItems) {
            JSONArray propArray = new JSONArray(
                    Arrays.asList(configurableMultiGroupItems));
            for (int index = 0; index < propArray.length(); index++) {
                JSONObject object = propArray.getJSONObject(index);
                if (null != object) {
                   evaluateMultiGroupItemsInMap(object, configurableMultiGroupItems, configuredPropertiesMap);
                }
            }
        }

        LOGGER.debug("Execution ends- setConfigurableMultiGroupItemsInMap");
    }

    /**
     * To reduce cyclomatic complexity.
     */
    private static void evaluateMultiGroupItemsInMap(JSONObject object, String[] configurableMultiGroupItems,
		Map<String, Object> configuredPropertiesMap) throws JSONException {
    	String elementGroupName = StringUtils.EMPTY;
        String elementName = StringUtils.EMPTY;
        
    	 if (StringUtils.isNotEmpty(object.getString(
                 HertzConstants.CONFIGURED_MULTI_GROUPS_ELEMENT_GROUP))) {
             elementGroupName = object.getString(
                     HertzConstants.CONFIGURED_MULTI_GROUPS_ELEMENT_GROUP);
         }
         if (StringUtils.isNotEmpty(object.getString(
                 HertzConstants.CONFIGURED_MULTI_GROUPS_ELEMENT_NAME))) {
             elementName = object.getString(
                     HertzConstants.CONFIGURED_MULTI_GROUPS_ELEMENT_NAME);
         }
         configuredPropertiesMap.put(HertzConstants.ELEMENT_GROUP,
                 elementGroupName);
         configuredPropertiesMap.put(HertzConstants.ELEMENT,
                 elementName);
         JSONArray multiGroupsArray = object
                 .getJSONArray(HertzConstants.MULTI_GROUPS);
         for (int arrayIndex = 0; arrayIndex < multiGroupsArray
                 .length(); arrayIndex++) {
             JSONObject groupObject = multiGroupsArray
                     .getJSONObject(arrayIndex);
             if (StringUtils.isNotEmpty(groupObject.getString(
                     HertzConstants.CONFIGURED_MULTI_GROUPS_CODE_KEY))) {
                 configuredPropertiesMap.put(
                         groupObject.getString(
                                 HertzConstants.CONFIGURED_MULTI_GROUPS_CODE_KEY),
                         groupObject.getString(
                                 HertzConstants.CONFIGURED_MULTI_GROUPS_CODE_VALUE));
             }
             if (StringUtils.isNotEmpty(groupObject.getString(
                     HertzConstants.CONFIGURED_MULTI_GROUPS_TEXT_KEY))) {
                 configuredPropertiesMap.put(
                         groupObject.getString(
                                 HertzConstants.CONFIGURED_MULTI_GROUPS_TEXT_KEY),
                         groupObject.getString(
                                 HertzConstants.CONFIGURED_MULTI_GROUPS_TEXT_VALUE));
             }
             if (StringUtils.isNotEmpty(groupObject.getString(
                     HertzConstants.CONFIGURED_MULTI_GROUPS_DESCRIPTION_KEY))) {
                 configuredPropertiesMap.put(
                         groupObject.getString(
                                 HertzConstants.CONFIGURED_MULTI_GROUPS_DESCRIPTION_KEY),
                         groupObject.getString(
                                 HertzConstants.CONFIGURED_MULTI_GROUPS_DESCRIPTION_VALUE));
             }
         }
	
}

	/**
     * This method sets all the configured checkbox field properties in the map.
     * 
     * @param configuredPropertiesMap
     * @param configurableText
     * @throws JSONException
     */
    private static void setConfigurableCheckboxFieldsInMap(
            Map<String, Object> configuredPropertiesMap,
            String[] configurableText) throws JSONException {
        LOGGER.debug("Execution starts- setConfigurableCheckboxFieldsInMap");
        if (null != configurableText) {
            JSONArray propArray = new JSONArray(
                    Arrays.asList(configurableText));
            for (int index = 0; index < propArray.length(); index++) {
                JSONObject object = propArray.getJSONObject(index);
                if (null != object) {
                    setConfiguredPropertiesMap(configuredPropertiesMap, object);
                }
            }
        }
        LOGGER.debug("Execution ends- setConfigurableCheckboxFieldsInMap");
    }

    /**
     * This method sets all the configured radio button field properties in the
     * map.
     * 
     * @param configuredPropertiesMap
     * @param configurableText
     * @throws JSONException
     */
    private static void setConfigurableRadioButtonFieldsInMap(
            Map<String, Object> configuredPropertiesMap,
            String[] configurableText) throws JSONException {
        LOGGER.debug("Execution starts- setConfigurableRadioButtonFieldsInMap");
        if (null != configurableText) {
            JSONArray propArray = new JSONArray(
                    Arrays.asList(configurableText));
            for (int index = 0; index < propArray.length(); index++) {
                JSONObject object = propArray.getJSONObject(index);
                if (null != object) {
                    setConfiguredRadioPropertiesInMap(configuredPropertiesMap,
                            object);
                }
            }
        }
        LOGGER.debug("Execution ends- setConfigurableRadioButtonFieldsInMap");
    }

    /**
     * @param configuredPropertiesMap
     * @param imageResource
     */
    private static void setConfigurableImagesInMap(
            Map<String, Object> configuredPropertiesMap,
            Resource imageResource) {
        LOGGER.debug("Execution starts- setConfigurableImagesInMap");
        if (null != imageResource
                && !ResourceUtil.isNonExistingResource(imageResource)) {
            LOGGER.debug("Path of image resource - " + imageResource.getPath());
            Iterator<Resource> iterator = imageResource.listChildren();
            while (iterator.hasNext()) {
                Resource childResource = iterator.next();
                ValueMap imageMap = childResource.getValueMap();
                if (MapUtils.isNotEmpty(imageMap)) {
                    
                	evaluateForImagesInMap(imageMap, configuredPropertiesMap);
                }
            }
        }
        LOGGER.debug("Execution ends- setConfigurableImagesInMap");
    }

    /**
     * To reduce cyclomatic complexity.
     */
    private static void evaluateForImagesInMap(ValueMap imageMap, Map<String, Object> configuredPropertiesMap) {
    	if (StringUtils.isNotEmpty(imageMap.get(
                HertzConstants.CONFIGURED_IMAGE_ELEMENT,
                String.class))) {
            configuredPropertiesMap.put(HertzConstants.ELEMENT,
                    imageMap.get(
                            HertzConstants.CONFIGURED_IMAGE_ELEMENT,
                            String.class));
        }
        constructImageMap(configuredPropertiesMap, imageMap,
                HertzConstants.KEY_IMAGE,
                HertzConstants.VALUE_IMAGE);
        constructImageMap(configuredPropertiesMap, imageMap,
                HertzConstants.CONFIGURED_IMAGE_ALT_TEXT_KEY,
                HertzConstants.CONFIGURED_IMAGE_ALT_TEXT_VALUE);
        constructImageMap(configuredPropertiesMap, imageMap,
                HertzConstants.KEY_IMAGE_URL_LINK,
                HertzConstants.VALUE_IMAGE_URL_LINK);
        if (StringUtils.isNotEmpty(imageMap.get(
                HertzConstants.KEY_IMAGE_LINK_TARGET_TYPE,
                String.class))) {
            configuredPropertiesMap.put(
                    imageMap.get(
                            HertzConstants.KEY_IMAGE_LINK_TARGET_TYPE,
                            String.class),
                    imageMap.get(
                            HertzConstants.VALUE_IMAGE_LINK_TARGET_TYPE,
                            Boolean.class));
        }
        constructImageMap(configuredPropertiesMap, imageMap,
                HertzConstants.CONFIGURABLE_IMAGE_KEY_2,
                HertzConstants.CONFIGURABLE_IMAGE_VALUE_2);
    }

	/**
     * @param configuredPropertiesMap
     * @param imageMap
     * @param key
     * @param value
     */
    private static void constructImageMap(
            Map<String, Object> configuredPropertiesMap, ValueMap imageMap,
            String key, String value) {
        if (StringUtils.isNotEmpty(imageMap.get(key, String.class))
                && StringUtils.isNotEmpty(imageMap.get(value, String.class))) {
            configuredPropertiesMap.put(imageMap.get(key, String.class),
                    imageMap.get(value, String.class));
        }
    }

    /**
     * @param configuredPropertiesMap
     * @param object
     * @throws JSONException
     */
    private static void setConfiguredLinkPropertiesInMap(
            Map<String, Object> configuredPropertiesMap, JSONObject object)
            throws JSONException {
        setInMap(object, configuredPropertiesMap, HertzConstants.ELEMENT,
                HertzConstants.CONFIGURED_LINK_ELEMENT);
        setInMap(object, configuredPropertiesMap, HertzConstants.ELEMENT_GROUP,
                HertzConstants.CONFIGURED_LINK_ELEMENT_GROUP);
        setInMapDynamic(object, configuredPropertiesMap,
                HertzConstants.KEY_LINK_TEXT, HertzConstants.VALUE_LINK_TEXT);
        setInMapDynamic(object, configuredPropertiesMap,
                HertzConstants.CONFIGURED_LINK_ARIA_LABEL_KEY,
                HertzConstants.CONFIGURED_LINK_ARIA_LABEL_VALUE);
        setInMapDynamic(object, configuredPropertiesMap,
                HertzConstants.KEY_LINK_URL, HertzConstants.VALUE_LINK_URL);
        if (StringUtils.isNotEmpty(
                object.getString(HertzConstants.KEY_LINK_TARGET_TYPE))) {
            configuredPropertiesMap.put(
                    object.getString(HertzConstants.KEY_LINK_TARGET_TYPE),
                    object.getBoolean(HertzConstants.VALUE_LINK_TARGET_TYPE));
        }
    }

    /**
     * This method is used to set all the configurable link values in Map
     * 
     * @param configuredPropertiesMap
     * @param configurableLinks
     * @throws JSONException
     */
    private static void setConfigurableLinksInMap(
            Map<String, Object> configuredPropertiesMap,
            String[] configurableLinks) throws JSONException {
        LOGGER.debug("Execution starts- setConfigurableLinksInMap");
        if (null != configurableLinks) {
            JSONArray propArray = new JSONArray(
                    Arrays.asList(configurableLinks));
            for (int index = 0; index < propArray.length(); index++) {
                JSONObject object = propArray.getJSONObject(index);
                if (null != object) {
                    setConfiguredLinkPropertiesInMap(configuredPropertiesMap,
                            object);
                }
            }
        }
        LOGGER.debug("Execution ends- setConfigurableLinksInMap");
    }

    /**
     * This method sets all the configured textarea properties in the map
     * 
     * @param configuredPropertiesMap
     * @param configurableTextArea
     * @throws JSONException
     */
    private static void setConfigurableTextAreasInMap(
            Map<String, Object> configuredPropertiesMap,
            String[] configurableTextArea) throws JSONException {
        LOGGER.debug("Execution starts- setConfigurableTextAreasInMap");
        if (null != configurableTextArea) {
            JSONArray propArray = new JSONArray(
                    Arrays.asList(configurableTextArea));
            for (int index = 0; index < propArray.length(); index++) {
                JSONObject object = propArray.getJSONObject(index);
                if (null != object) {
                   evaluateForTextAreasInMap(object, configurableTextArea,configuredPropertiesMap);
                }
            }
        }
        LOGGER.debug("Execution ends- setConfigurableTextAreasInMap");
    }

    /**
     * To reduce cyclomatic complexity.
     */
    private static void evaluateForTextAreasInMap(JSONObject object, String[] configurableTextArea,
			Map<String, Object> configuredPropertiesMap) throws JSONException {
    	 if (StringUtils.isNotEmpty(object.getString(
                 HertzConstants.CONFIGURED_TEXT_AREA_ELEMENT_NAME))) {
             configuredPropertiesMap.put(HertzConstants.ELEMENT,
                     object.getString(
                             HertzConstants.CONFIGURED_TEXT_AREA_ELEMENT_NAME));
         }
         if (StringUtils.isNotEmpty(
                 object.getString(HertzConstants.KEY_TEXTAREA))
                 && StringUtils.isNotEmpty(object.getString(
                         HertzConstants.VALUE_TEXTAREA))) {
             configuredPropertiesMap.put(
                     object.getString(HertzConstants.KEY_TEXTAREA),
                     object.getString(
                             HertzConstants.VALUE_TEXTAREA));
         }
		
	}

	/**
     * This method sets all the configured text field properties in the map.
     * 
     * @param configuredPropertiesMap
     * @param configurableText
     * @throws JSONException
     */
    private static void setConfigurableTextFieldsInMap(
            Map<String, Object> configuredPropertiesMap,
            String[] configurableText) throws JSONException {
        LOGGER.debug("Execution starts- setConfigurableTextFieldsInMap");
        if (null != configurableText) {
            JSONArray propArray = new JSONArray(
                    Arrays.asList(configurableText));
            for (int index = 0; index < propArray.length(); index++) {
                JSONObject object = propArray.getJSONObject(index);
                if (null != object) {
                   evaluateForTextFieldsInMap(object, configurableText, configuredPropertiesMap);
                }
            }
        }
        LOGGER.debug("Execution ends- setConfigurableTextFieldsInMap");
    }

    /**
     * To reduce cyclomatic complexity.
     */
    private static void evaluateForTextFieldsInMap(JSONObject object, String[] configurableText,
			Map<String, Object> configuredPropertiesMap) throws JSONException {
    	 if (StringUtils.isNotEmpty(object.getString(
                 HertzConstants.CONFIGURED_TEXT_ELEMENT))) {
             configuredPropertiesMap.put(HertzConstants.ELEMENT,
                     object.getString(
                             HertzConstants.CONFIGURED_TEXT_ELEMENT));
         }
         if (StringUtils.isNotEmpty(
                 object.getString(HertzConstants.KEY_TEXT))
                 && StringUtils.isNotEmpty(object
                         .getString(HertzConstants.VALUE_TEXT))) {
             configuredPropertiesMap.put(
                     object.getString(HertzConstants.KEY_TEXT),
                     object.getString(HertzConstants.VALUE_TEXT));
         }
         if (StringUtils
                 .isNotEmpty(object.getString(
                         HertzConstants.CONFIGURED_TEXT_ARIA_LABEL_KEY))
                 && StringUtils.isNotEmpty(object.getString(
                         HertzConstants.CONFIGURED_TEXT_ARIA_LABEL_VALUE))) {
             configuredPropertiesMap.put(
                     object.getString(
                             HertzConstants.CONFIGURED_TEXT_ARIA_LABEL_KEY),
                     object.getString(
                             HertzConstants.CONFIGURED_TEXT_ARIA_LABEL_VALUE));
         }
         if (StringUtils
                 .isNotEmpty(object.getString(
                         HertzConstants.CONFIGURED_TEXT_ERROR_KEY))
                 && StringUtils.isNotEmpty(object.getString(
                         HertzConstants.CONFIGURED_TEXT_ERROR_VALUE))) {
             configuredPropertiesMap.put(
                     object.getString(
                             HertzConstants.CONFIGURED_TEXT_ERROR_KEY),
                     object.getString(
                             HertzConstants.CONFIGURED_TEXT_ERROR_VALUE));
         }
		
	}

	/**
     * This method returns the config page path by searching the parents of the
     * current page resource
     * 
     * @param pageResource
     * @param propertyName
     * @return
     */
    public static Resource getConfigPagePath(Resource pageResource,
            String propertyName, ResourceResolver resolver) {
        InheritanceValueMap valueMap = new HierarchyNodeInheritanceValueMap(
                pageResource);
        return resolver
                .getResource(valueMap.getInherited(propertyName, String.class));
    }

    /**
     * Get the jcr:title property for a country from country node
     * 
     * @param countryNode
     * @return
     * @throws RepositoryException
     */
    public static String getJCRTitleOfANode(Node jcrNode)
            throws RepositoryException {
        String jcrTitle = StringUtils.EMPTY;
        Node pageJCRNode = jcrNode.getNode(HertzConstants.JCR_CONTENT);
        if (pageJCRNode.hasProperty(HertzConstants.JCR_TITLE_PROPERTY)) {
            Property nodeTitleProperty = pageJCRNode
                    .getProperty(HertzConstants.JCR_TITLE_PROPERTY);
            jcrTitle = nodeTitleProperty.getString();
        }
        return jcrTitle;
    }

    /**
     * Returns date in milliseconds.
     * 
     * @param savedDate
     *            Date in other format.
     * @param format
     *            The saved date format provided.
     * @return
     */
    public static long getDateInMillis(String savedDate, String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);

        if (StringUtils.isNotEmpty(savedDate)) {
            try {
                date = sdf.parse(savedDate);
            } catch (ParseException e) {
                LOGGER.error("Error in date conversion in millisconds :- {} {}",
                        e.getCause(), e);
            }
        }
        return date.getTime();
    }

    /**
     * Gets the access token from the AEM, if not present then try to set it and
     * return the same
     * 
     * @param tokenPath
     *            The path at which the token would be created.
     * @param nodeName
     *            The name of the node.
     * @param session
     *            The admin session object.
     * @return The token value.
     */

    public static String getOrCreateAccessToken(String tokenPath,
            String nodeName, String serviceUrl, Session session) {
        String token = StringUtils.EMPTY;
        Node tokenNode = null;
        try {
            tokenNode = JcrUtils.getOrCreateByPath(
                    tokenPath + HertzConstants.FRWD_SLASH + nodeName,
                    JcrResourceConstants.NT_SLING_FOLDER,
                    JcrConstants.NT_UNSTRUCTURED, session, true);
            if (!tokenNode.hasProperty(HertzConstants.VALUE)) {
                LOGGER.debug(
                        "Value property not found, hitting the password grant service to set a new value.");
                Gson gson = HertzUtils.initGsonBuilder(true, true, false);
                String response = HttpUtils.post(serviceUrl,
                        new StringBuilder().append(HertzConstants.GRANT_TYPE)
                                .append(HertzConstants.EQUALTO)
                                .append(GrantTypes.CLIENT_CREDS).toString(),
                        HertzConstants.CONTENT_TYPE_FORM_ENCODED, false, null);
                if (StringUtils.isNotEmpty(response)) {
                    PasswordGrantBean grantBean = gson.fromJson(response,
                            PasswordGrantBean.class);
                    tokenNode.setProperty(HertzConstants.VALUE,
                            grantBean.getAccessToken());
                }
            }
            session.save();
            LOGGER.debug("Token value is : - {}",
                    tokenNode.getProperty(HertzConstants.VALUE).getString());
            token = tokenNode.getProperty(HertzConstants.VALUE).getString();
        } catch (RepositoryException e) {
            LOGGER.error("Error in date conv :- {} {}", e.getCause(),
                    e);
        }
        return token;
    }

    /**
     * Enum providing the different grant types as per micro services standards.
     * 
     * @author n.kumar.singhal
     *
     */
    public enum GrantTypes {
        PASSWORD("password"), CLIENT_CREDS("client_credentials"), REFRESH_TOKEN(
                "refresh_token");
        private final String value;

        private GrantTypes(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return getValue();
        }
    }

    /**
     * Checks whether the page has been stuck in queue or delivered already.
     * 
     * @param replicator
     *            The replicator object.
     * @param session
     *            The session object.
     * @param path
     *            The action path.
     * @return The delivery status of payload
     * @throws InterruptedException
     */
    public static boolean isDelivered(Replicator replicator, Session session,
            String path) throws InterruptedException {
    	TimeUnit.SECONDS.sleep(3);
        ReplicationStatus repStatus = replicator.getReplicationStatus(session,
                path);
        if(repStatus==null){
        	return false;
        } else {
        	return repStatus.isDelivered();
        }
        
    }

    /**
     * This method is used if only updation in the token is required from within
     * the scheduler.
     ** 
     * @param tokenPath
     *            The path at which the token would be created.
     * @param nodeName
     *            The name of the node.
     * @param session
     *            The admin session object.
     * @return The token value.
     * 
     */
    public static String updateAccessToken(String tokenPath, String nodeName,
            String serviceUrl, Session session) {
        Node tokenNode = null;
        String message = StringUtils.EMPTY;
        try {
            if (StringUtils.isNotEmpty(tokenPath)
                    && StringUtils.isNotEmpty(nodeName)) {
                tokenNode = JcrUtils.getOrCreateByPath(
                        tokenPath + HertzConstants.FRWD_SLASH + nodeName,
                        JcrResourceConstants.NT_SLING_FOLDER,
                        JcrConstants.NT_UNSTRUCTURED, session, true);
            }
            if (null != tokenNode) {
                if (tokenNode.hasProperty(HertzConstants.VALUE)) {
                    LOGGER.debug("Removing pre-set value: - {}", tokenNode
                            .getProperty(HertzConstants.VALUE).getString());
                    tokenNode.getProperty(HertzConstants.VALUE).remove();
                }
                Gson gson = HertzUtils.initGsonBuilder(true, true, false);
                String response = HttpUtils.post(serviceUrl,
                        new StringBuilder().append(HertzConstants.GRANT_TYPE)
                                .append(HertzConstants.EQUALTO)
                                .append(GrantTypes.CLIENT_CREDS).toString(),
                        HertzConstants.CONTENT_TYPE_FORM_ENCODED, false, null);
                PasswordGrantBean grantBean = gson.fromJson(response,
                        PasswordGrantBean.class);
                tokenNode.setProperty(HertzConstants.VALUE,
                        grantBean.getAccessToken());
                LOGGER.debug("Newly Set value is: - {}",
                        grantBean.getAccessToken());
                message = HertzConstants.SUCCESS;
                session.save();
            }
        } catch (RepositoryException e) {
            message = HertzConstants.FAILURE;
            LOGGER.error("Error during date conversion :- {} {}", e.getCause(),
                    e);
        }
        return message;
    }

    /**
     * Return the service identifier.
     * 
     * @param actionPath
     *            The path which is replicated.
     * @param mappings
     *            The mappings in the configuration.
     * 
     * @return The service identifier.
     */
    public static String getIdentifier(String actionPath, String[] mappings) {
        String identifier = StringUtils.EMPTY;
        LOGGER.debug("The mappings array here is: - {}", mappings);
        for (String mapping : mappings) {
            String[] pairs = mapping.split(HertzConstants.COLON);
            if (pairs.length == 2 && actionPath.contains(pairs[0])) {
                identifier = pairs[1];
                break;
            }
        }
        LOGGER.debug("The MS API identifier here is: - {}", identifier);
        return identifier;
    }

    /**
     * @param object
     * @param configuredPropertiesMap
     * @param key
     * @param value
     * @throws JSONException
     */
    private static void setInMapDynamic(JSONObject object,
            Map<String, Object> configuredPropertiesMap, String key,
            String value) throws JSONException {
        if (StringUtils.isNotEmpty(object.getString(key))
                && StringUtils.isNotEmpty(object.getString(value))) {
            configuredPropertiesMap.put(object.getString(key),
                    object.getString(value));
        }

    }

    /**
     * @param configuredPropertiesMap
     * @param element
     * @param configuredRadioButtonElement
     * @throws JSONException
     */
    private static void setInMap(JSONObject object,
            Map<String, Object> configuredPropertiesMap, String key,
            String valueToGetFromObject) throws JSONException {
        if (StringUtils.isNotEmpty(object.getString(valueToGetFromObject))) {
            configuredPropertiesMap.put(key,
                    object.getString(valueToGetFromObject));
        }

    }

    /**
     * The method to match the passed path with the configurations done on the
     * page path passed as a second parameter.
     * 
     * @param payloadPath
     *            The path on which workflow has been triggered.
     * @param pageResource
     *            The page path on which the mapping has been maintained.
     * @return
     */
    public static String getGroupName(String payloadPath,
            Resource pageResource) {
        LOGGER.debug("The payload path and resource is found to be {} {}",
                payloadPath, pageResource.getPath());
        String name = StringUtils.EMPTY;
        Resource resource = pageResource.adaptTo(Page.class)
                .getContentResource().getChild(HertzConstants.PAR);
        if (null != resource) {
        	name = getPathToGroupName(payloadPath, resource);
        }
        return name;
    }

    /**
     * To reduce cyclomatic complexity.
     * 
     */
    private static String getPathToGroupName(String payloadPath, Resource resource) {
    	String name = StringUtils.EMPTY;
    	Iterator<Resource> iterator = resource.listChildren();
    	while (iterator.hasNext()) {
            Resource pathToGroupResource = iterator.next();
            LOGGER.debug("The concerned resource is : {}",
                    pathToGroupResource.getPath());
    	if (pathToGroupResource.isResourceType(
                "hertz/components/content/pathtogroup")) {
            String sitepath = (String) pathToGroupResource.getValueMap()
                    .get("sitepath");
            String groupName = (String) pathToGroupResource
                    .getValueMap().get("groupname");
            LOGGER.debug("The sitename and group name are {} {}: ",
                    sitepath, groupName);
            if (payloadPath.contains(sitepath)
                    && payloadPath.startsWith(sitepath)) {
                name = groupName;
                break;
            }
        }
    }
		return name;
		
    }

	/**
     * This method checks if the node already exists in jcr
     * 
     * @param rootNode
     * @param nodeName
     * @return
     * @throws PathNotFoundException
     * @throws RepositoryException
     */
    public static int doesNodeExist(Node rootNode, String nodeName)
            throws PathNotFoundException, RepositoryException {
        LOGGER.debug("Execution starts- doesNodeExist");

        int childRecs = 0;
        NodeIterator custNodeIterator = rootNode.getNodes();
        while (custNodeIterator.hasNext()) {
            Node nextNode = custNodeIterator.nextNode();
            if (nextNode.getName().equals(nodeName)) {
                childRecs++;
            }
        }
        LOGGER.debug("Execution ends- doesNodeExist");

        return childRecs;
    }

    /**
     * This method sets the key value pairs for text properties and returns a
     * JSON object
     * 
     * @param element
     * @param textKey
     * @param textValue
     * @param ariaKey
     * @param ariaValue
     * @return jsonObject
     * @throws JSONException
     */
    /*
     * public static JSONObject getJsonObject(String element, String textKey,
     * String textValue, String ariaKey, String ariaValue) throws JSONException
     * { JSONObject jsonObject = new JSONObject();
     * jsonObject.put(HertzConstants.CONFIGURED_TEXT_ELEMENT, element);
     * jsonObject.put(HertzConstants.KEY_TEXT, textKey);
     * jsonObject.put(HertzConstants.VALUE_TEXT, textValue);
     * jsonObject.put(HertzConstants.CONFIGURED_TEXT_ARIA_LABEL_KEY, ariaKey);
     * jsonObject.put(HertzConstants.CONFIGURED_TEXT_ARIA_LABEL_VALUE,
     * ariaValue); jsonObject.put(HertzConstants.CONFIGURED_TEXT_ERROR_KEY,
     * StringUtils.EMPTY);
     * jsonObject.put(HertzConstants.CONFIGURED_TEXT_ERROR_VALUE,
     * StringUtils.EMPTY); return jsonObject; }
     */

    /**
	 * Utility method to transform the image URLs
	 * 
	 * @param imageComponent
	 * @param renditionType
	 * @param density
	 * @param extension
	 * @return
	 */
	public static String transformImageURL(Resource imageComponent, String renditionType, String density,
			String extension) {
		String transformedUrl = StringUtils.EMPTY;
		String ext = extension;
		if (StringUtils.isEmpty(extension)) {
			LOGGER.debug("No extension available in config. Setting the default to PNG");
			ext = ".png";
		}
		ValueMap properties = imageComponent.getValueMap();
		String fileReference = StringUtils.defaultIfEmpty((String) properties.get("fileReference"), StringUtils.EMPTY);
		
		
		if (StringUtils.isNotEmpty(fileReference)) {
			transformedUrl = fileReference;
			ResourceResolver resolver = imageComponent.getResourceResolver();
			if (null == resolver.getResource(fileReference)) {
				LOGGER.debug(
						"The fileReference Attribute is not found on this image resource. Please check configuraitons for path {}.",
						imageComponent.getPath());
			} else {
				Asset assetResource = resolver.getResource(fileReference).adaptTo(Asset.class);
				Rendition rendition = assetResource.getRendition(renditionType + "@" + density + ".png");
				if (rendition != null) {
					transformedUrl = imageComponent.getPath() + ".enscale." + renditionType + "-" + density + ".png" + HertzConstants.SLASH + System.currentTimeMillis() + ".png"; 
					LOGGER.debug(
							"Custom Hertz REndition exists for this image at {}, setting the transformed URL as {}.",
							rendition.getPath(), transformedUrl);
				}
			}
		}
		return transformedUrl;
	}
    /**
     * This method retrieves an OSGi service reference inside a non-OSGi service
     * class. This method makes use of the bundle context to locate the
     * reference of a service using the bundle classloader.
     *
     * @param <T>
     *            the generic type
     * @param clazz
     *            the clazz
     * @return the service reference
     */
    public static <T extends Object> T getServiceReference(Class<T> clazz) {
        T t = null;
        Bundle bundle = FrameworkUtil.getBundle(clazz);
        if (null != bundle) {
            BundleContext bundleContext = bundle.getBundleContext();
            if (null != bundleContext) {
                ServiceReference reference = bundleContext
                        .getServiceReference(clazz.getName());
                t = (T) bundleContext.getService(reference);
            }
        }
        return t;
    }

    /**
     * To set the SEO MetaData in SpaBean
     * 
     * @param spaBean
     * @param pageResource
     * @throws JSONException
     */
    public static void setSeoMetaDataInBean(SpaPageBean spaBean,
            Resource pageResource, RequestPathInfo requestPathInfo,
            ResourceResolver resolver) throws JSONException {
        Map<String, Object> seoMap = new HashMap<String, Object>();
        ValueMap propMap = pageResource.adaptTo(ValueMap.class);
        String[] seoItems = PropertiesUtil.toStringArray(propMap.get(
                HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS, new String[] {}));
        if (seoItems.length >= 0) {
            for (String seoItem : seoItems) {
                JSONObject object = new JSONObject(seoItem);
                if (StringUtils.isNotEmpty(
                        object.optString(HertzConstants.CONFIGURABLE_SEO_KEY))
                        && StringUtils.isNoneEmpty(object.optString(
                                HertzConstants.CONFIGURABLE_SEO_VALUE)))
                    seoMap.put(
                            object.getString(
                                    HertzConstants.CONFIGURABLE_SEO_KEY),
                            object.getString(
                                    HertzConstants.CONFIGURABLE_SEO_VALUE));
            }
        }
        
        if(pageResource.getResourceType().equalsIgnoreCase(HertzConstants.HOME_PAGE_RES_TYPE)){
        	setPartnerHomePageValue(seoMap, pageResource, requestPathInfo, resolver);
        }

        setSeoExtraProperties(seoMap, propMap);
        setSeoJsonScriptProperties(seoMap, propMap);
        seoMap.put(HertzConstants.OG_TYPE, HertzConstants.WEBSITE);
        setBreadcrumbProperties(seoMap, pageResource);
        if (MapUtils.isNotEmpty(seoMap)) {
            spaBean.setMetaData(seoMap);
        }
    }
    
    /**
     * This method sets the partnerHomePage value in metadata
     * @param seoMap
     * @param pageResource
     * @param requestPathInfo
     * @param resolver
     */
    public static void setPartnerHomePageValue(Map<String, Object> seoMap, 
    		Resource pageResource, RequestPathInfo requestPathInfo, 
    		ResourceResolver resolver){
    	boolean partnerHomePage = false;
    	String cpCodePagePath = getCPCodePagePath(resolver, requestPathInfo, pageResource);
    	if(StringUtils.isNotEmpty(cpCodePagePath)){
    		partnerHomePage = true;
    	}
    	seoMap.put(HertzConstants.PARTNER_HOME_PAGE, partnerHomePage);
    }

    /**
     * Sets the seo json script properties.
     *
     * @param seoMap
     *            the seo map
     * @param propMap
     *            the prop map
     */
    private static void setSeoJsonScriptProperties(Map<String, Object> seoMap,
            ValueMap propMap) {
        if (propMap.containsKey(HertzConstants.JSON_SCRIPT)) {
            seoMap.put(HertzConstants.JSON_SCRIPT, StringUtils.trim(
                    propMap.get(HertzConstants.JSON_SCRIPT, String.class)));
        }
    }

    /**
     * Sets the seo extra properties.
     *
     * @param seoMap
     *            the seo map
     * @param propMap
     *            the prop map
     */
    private static void setSeoExtraProperties(Map<String, Object> seoMap,
            ValueMap propMap) {
        if (!seoMap.containsKey(HertzConstants.TITLE)) {
            if (propMap.containsKey(JcrConstants.JCR_TITLE)) {
                seoMap.put(HertzConstants.TITLE,
                        propMap.get(JcrConstants.JCR_TITLE, String.class));
            }
        }
        setBasicTabProperties(seoMap, propMap);
        if (propMap.containsKey(
                HertzConstants.CONFIGURABLE_SEO_GOOGLE_VERIFICATION_TAG)) {
            seoMap.put(HertzConstants.GOOGLE_SITE_VERIFICATION,
                    propMap.get(
                            HertzConstants.CONFIGURABLE_SEO_GOOGLE_VERIFICATION_TAG,
                            String.class));
        }
        if (propMap.containsKey(
                HertzConstants.CONFIGURABLE_SEO_BING_VERIFICATION_TAG)) {
            seoMap.put(HertzConstants.BING_SITE_VERIFICATION,
                    propMap.get(
                            HertzConstants.CONFIGURABLE_SEO_BING_VERIFICATION_TAG,
                            String.class));
        }
        if (!seoMap.containsKey(HertzConstants.DESCRIPTION)) {
            if (propMap.containsKey(JcrConstants.JCR_DESCRIPTION)) {
                seoMap.put(HertzConstants.DESCRIPTION, propMap
                        .get(JcrConstants.JCR_DESCRIPTION, String.class));
            }
        }
    }

    /**
     * Sets the Breadcrumb Properties
     * 
     * @param seoMap
     * @param propMap
     * @param pageResource
     * @throws JSONException
     */
    private static void setBreadcrumbProperties(Map<String, Object> seoMap,
            Resource pageResource) throws JSONException {
        ArrayList<BreadcrumbBean> arrayList = new ArrayList<BreadcrumbBean>();
        String[] pagePath = pageResource.getPath().split(HertzConstants.SLASH);
        StringBuilder constructedPath = new StringBuilder();
        StringBuilder URI = new StringBuilder();
        for (int i = 1; i < pagePath.length - 1; i++) {
            constructedPath.append(HertzConstants.SLASH).append(pagePath[i]);
            if (i > 4) {
                URI.append(HertzConstants.SLASH).append(pagePath[i]);
                ValueMap propMap = pageResource.getResourceResolver()
                        .getResource(constructedPath.toString()
                                + HertzConstants.SLASH
                                + JcrConstants.JCR_CONTENT).adaptTo(ValueMap.class);
                if(!propMap.containsKey(NameConstants.PN_HIDE_IN_NAV)){
                    BreadcrumbBean breadcrumbBean = new BreadcrumbBean();
                    breadcrumbBean.setLabel(PropertiesUtil.toString(
                            propMap.get(NameConstants.PN_NAV_TITLE),
                            StringUtils.EMPTY));
                    breadcrumbBean.setURI(URI.toString());
                    arrayList.add(breadcrumbBean);
                }
            }
        }
        seoMap.put("breadcrumbs", arrayList);
    }

    /**
     * Sets the Basci Tab Properties
     * 
     * @param seoMap
     * @param propMap
     */
    private static void setBasicTabProperties(Map<String, Object> seoMap,
            ValueMap propMap) {
        if (propMap.containsKey(NameConstants.PN_NAV_TITLE)) {
            seoMap.put(NameConstants.PN_NAV_TITLE,
                    propMap.get(NameConstants.PN_NAV_TITLE, String.class));
        }
        if (propMap.containsKey(NameConstants.PN_PAGE_TITLE)) {
            seoMap.put(NameConstants.PN_PAGE_TITLE,
                    propMap.get(NameConstants.PN_PAGE_TITLE, String.class));
        }
        if (propMap.containsKey(HertzConstants.SUB_TITLE)) {
            seoMap.put(HertzConstants.SUB_TITLE,
                    propMap.get(HertzConstants.SUB_TITLE, String.class));
        }
    }

    /**
     * This method uses the passed formatter and formats the passed date with
     * it.
     * 
     * @param date
     *            The date to be formatted.
     * @param parsableFormat
     *            The format required to parse the passed date.
     * @param converterFormat
     *            The format in whcih this date is required.
     * @return The formatted date.
     */
    public static String formatDate(String date, String parsableFormat,
            String converterFormat) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("The date, parsable format are :- {}{}", date,
                    parsableFormat);
            LOGGER.debug("The converterFormat is received as :-  :- {}",
                    converterFormat);
        }
        String fDate = StringUtils.EMPTY;
        DateFormat iFormatter = new SimpleDateFormat(parsableFormat,
                Locale.ENGLISH);
        try {
            Date iDate = iFormatter.parse(date);
            if (StringUtils.isNotEmpty(converterFormat)) {
                DateFormat fFormatter = new SimpleDateFormat(converterFormat,
                        Locale.ENGLISH);
                fDate = fFormatter.format(iDate);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("The parsed date is :- {}", fDate);
                }
            } else {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(
                            "No Converted format detected, returning original string as date");
                }
                fDate = date;
            }
        } catch (ParseException exception) {
            LOGGER.error(exception.getMessage(), exception);
            fDate = date;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("The final parsed date :- {}", fDate);
        }
        return fDate;
    }

    /**
     * @param date
     * @param parsableFormat
     * @return
     */
    public static Long convertToMillis(String date, String parsableFormat) {
        Long fDate = -1L;
        DateFormat iFormatter = new SimpleDateFormat(parsableFormat,
                Locale.ENGLISH);
        try {
            Date iDate = iFormatter.parse(date);
            fDate = iDate.getTime();
        } catch (ParseException exception) {
            LOGGER.error(exception.getMessage(), exception);
        }
        return fDate;
    }

    /**
     * @param dateString
     * @return Calendar object. This method is used for converting String to
     *         Calendar object to store the same in jcr property as "Date".
     */
    public static Calendar dateFormatter(String dateString) {
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ssX", Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(dateString);
            calendar.setTime(date);
        } catch (ParseException e) {
            LOGGER.error("ParseException in dateFormatter method::: ", e);
            throw new RuntimeException(e);
        }
        return calendar;
    }

    /**
     * This helps generate a 12 digit long random number.
     * 
     * @return 12 digit random number.
     */
    public static long generateRandom() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // first not 0 digit
        sb.append(random.nextInt(9) + 1);

        // rest of 11 digits
        for (int i = 0; i < 11; i++) {
            sb.append(random.nextInt(10));
        }

        return Long.valueOf(sb.toString()).longValue();
    }

    /**
     * <p>
     * This method reads a node of type 'nt:file' and returns an input stream.
     * </p>
     *
     * @param nodePath
     *            the node path {@link String}
     * @param session
     *            the session {@link Session}
     * @return the input stream {@link InputStream}
     */
    public static InputStream readFileNode(String nodePath, Session session) {
        LOGGER.debug("Inside method: readFileNode()");
        InputStream inputStream = null;
        try {
            Node fileNode = JcrUtils.getNodeIfExists(
                    nodePath + "/jcr:content/renditions/original", session);
            if (null != fileNode) {
                inputStream = JcrUtils.readFile(fileNode);
            }
        } catch (RepositoryException e) {
            LOGGER.debug("Error occured while reading excel sheet {} {}", e,
                    e.getStackTrace());
        }
        LOGGER.debug("Exiting method: readFileNode()");
        return inputStream;
    }

    /**
     * This method is to clean the underling resource before a fresh ingestion.
     * 
     * @param gridResource
     *            The parent responsive grid object.
     * @param resolver
     *            The resource resolver object.
     * @throws PersistenceException
     *             The persistence exception.
     */
    public static void clean(Resource gridResource, ResourceResolver resolver,
            String resourceType) throws PersistenceException {
        // cleaning the prior data !!
        Iterator<Resource> iterator = gridResource.listChildren();
        while (iterator.hasNext()) {
            Resource resource = iterator.next();
            if ((resourceType).equals(resource.getValueMap()
                    .get(HertzConstants.SLING_RESOURCE_TYPE))) {
                resolver.delete(resource);
                resolver.commit();
            }
        }
    }

    /**
	 * @param pagePath
	 * @param resolver
	 * @param helper
	 * @param builder
	 * @param session
	 * @return
	 * @throws LoginException
	 * @throws RepositoryException
	 * @throws JSONException
	 */
	public static HeaderFooterBean setHeaderFooterContent(Resource pageResource, ResourceResolver resolver, SlingScriptHelper helper) {
		HeaderFooterBean headerFooterBean = new HeaderFooterBean();
		try {
			List<HeaderBean> headerBeanList = new ArrayList<>();
			List<FooterContainerBean> footerBeanList = new ArrayList<>();
			if(pageResource.getResourceType().equalsIgnoreCase(HertzConstants.HEADER_RES_TYPE)){
				HeaderUtils.getHeaderContent(headerBeanList, pageResource, resolver, helper);
			}
			else if(pageResource.getResourceType().equalsIgnoreCase(HertzConstants.FOOTER_RES_TYPE)){
			    FooterUtils.getFooterContent(footerBeanList, pageResource, resolver, helper);
			}
			else{
				getChildHeaderFooterResource(headerBeanList, footerBeanList, pageResource, resolver, helper);
			}
			if(headerBeanList.size()>0){
				headerFooterBean.setHeaderBeanList(headerBeanList);
			}
			if(footerBeanList.size()>0){
			    headerFooterBean.setFooterContentList(footerBeanList);
			}
		} catch (JSONException e) {
			LOGGER.error("Error in JSON {} {}", e, e.getMessage());
		} catch (RepositoryException e) {
			LOGGER.error("Error while execution in the repository {} {}", e, e.getMessage());
		}
		return headerFooterBean;
	}
	
	/**
	 * 
	 * @param headerBeanList
	 * @param footerBeanList
	 * @param contentResource
	 * @param resolver
	 * @param helper
	 * @throws JSONException
	 * @throws RepositoryException
	 */
	public static void getChildHeaderFooterResource(List<HeaderBean> headerBeanList, List<FooterContainerBean> footerBeanList, 
			Resource contentResource, ResourceResolver resolver, SlingScriptHelper helper) throws JSONException, RepositoryException{
		Resource parentPageResoure = contentResource.getParent();
		Iterable<Resource> childResources = parentPageResoure.getChildren();
		for(Resource childResource : childResources){
			if(!childResource.getName().equalsIgnoreCase(HertzConstants.JCR_CONTENT)){
			getHeaderFooterContent(childResource, headerBeanList, footerBeanList, resolver, helper);
			}
		}
	}
	
	/**
	 * To go away with cyclomatic complexity.
	 */
	private static void getHeaderFooterContent(Resource childResource, List<HeaderBean> headerBeanList, List<FooterContainerBean> footerBeanList, ResourceResolver resolver, SlingScriptHelper helper) throws JSONException, RepositoryException {
		Resource childJcrResource = childResource.getChild(HertzConstants.JCR_CONTENT);
		if(null != childJcrResource){
	    if(childJcrResource.getResourceType().equalsIgnoreCase(HertzConstants.HEADER_RES_TYPE)){
		    HeaderUtils.getHeaderContent(headerBeanList, childJcrResource, resolver, helper);
	    }
	    if(childJcrResource.getResourceType().equalsIgnoreCase(HertzConstants.FOOTER_RES_TYPE)){
		    FooterUtils.getFooterContent(footerBeanList, childJcrResource, resolver, helper);
	    }
		}else {
			LOGGER.error("This page has missing JCR Content : " +  childResource.getPath());
		}
		
	}

	/**
	 * Method to return the SPA complaint URLs from AEM full URLs. It uses
	 * resolver.map() internaly to convert the content URLs
	 * 
	 * @param objectString
	 *            The AEM content path
	 * @return The shortened path.
	 */
	public static String shortenIfPath(String objectString) {
		SystemUserService systemUserService = getServiceReference(SystemUserService.class);
		StringBuilder finalString = new StringBuilder();

		ResourceResolver resolver = null;
		try {
			if (null != systemUserService && StringUtils.isNotEmpty(objectString)) {
				resolver = systemUserService.getServiceResourceResolver();
				if (null != resolver) {
					checkHashInPath(objectString, finalString, resolver);
					
				}
			}
		} finally {
			if (null != resolver && resolver.isLive()) {
				resolver.close();
			}
		}

		return finalString.toString();
	}

    /**
     * To reduce cyclomatic complexity
     */
    private static void checkHashInPath(String objectString, StringBuilder finalString, ResourceResolver resolver) {
		// TODO Auto-generated method stub
    	if (StringUtils.startsWith(objectString, "#")) {
			finalString.append(objectString);
		} else {
			if (StringUtils.contains(objectString, "#")) {
				String[] tokens = objectString.split("#");
				String fullPath = tokens[0];
				String anchor = tokens[1];
				Resource resource = resolver.resolve(fullPath);
				if (ResourceUtil.isNonExistingResource(resource)
						|| resource.isResourceType(DamConstants.NT_DAM_ASSET)) {
					finalString.append(objectString);
				} else {
					finalString.append(resolver.map(fullPath)).append("#").append(anchor);
				}
			} else {
				Resource resource = resolver.resolve(objectString);
				if (ResourceUtil.isNonExistingResource(resource)
						|| resource.isResourceType(DamConstants.NT_DAM_ASSET)) {

					finalString.append(objectString);
				} else {
					finalString.append(resolver.map(objectString));
				}
			}
		}
  }

	/**
     * This method is used to replace the image tag with picture tag with the
     * required renditions
     * 
     * @param doc
     * @param resolver
     */
    public static void replaceImageTagWithPicture(Document doc,
            ResourceResolver resolver) {

        Elements elements = doc.getElementsByTag("img");
        Iterator<Element> iterator = elements.iterator();
        while (iterator.hasNext()) {

            Element imgElement = iterator.next();
            String src = imgElement.attr("src");
            LOGGER.debug("src in image : - {0}", src);
            String dataPath = imgElement.attr("data-path");
            LOGGER.debug("data-path- {0}", dataPath);
            String altText = imgElement.attr("alt");
            LOGGER.debug("alt- {0}", altText);
            String cssClass = imgElement.attr("class");
            LOGGER.debug("css classes are :- " + cssClass);
            Resource imgResource = resolver.getResource(dataPath);
           
            if (null != imgResource) {
                String small1xPath = transformImageURL(imgResource, "small",
                        "1x", "png");
                LOGGER.debug("small1xpath : - {0}", small1xPath);
                String small2xPath = transformImageURL(imgResource, "small",
                        "2x", "png");
                LOGGER.debug("small2xpath : - {0}", small2xPath);
                String large1xPath = transformImageURL(imgResource, "large",
                        "1x", "png");
                LOGGER.debug("large1xPath : - {0}", large1xPath);
                String large2xPath = transformImageURL(imgResource, "large",
                        "2x", "png");
                LOGGER.debug("large2xPath : - {0}", large2xPath);
                if (StringUtils.isNotBlank(cssClass)) {
                    imgElement
                            .replaceWith(new Element(Tag.valueOf("picture"), "")
                                    .html("<source srcset='" + small1xPath
                                            + " 1x," + small2xPath
                                            + " 2x' media='(max-width: 468px)'><source srcset='"
                                            + large1xPath + " 1x," + large2xPath
                                            + " 2x' media='(min-width: 469px)'><img class = '"
                                            + cssClass + "' src='"+large1xPath+"' alt='"
                                            + altText + "'>"));
                } else {
                    imgElement
                            .replaceWith(new Element(Tag.valueOf("picture"), "")
                                    .html("<source srcset='" + small1xPath
                                            + " 1x," + small2xPath
                                            + " 2x' media='(max-width: 468px)'><source srcset='"
                                            + large1xPath + " 1x," + large2xPath
                                            + " 2x' media='(min-width: 469px)'><img src='"+large1xPath+"' alt='"
                                            + altText + "'>"));
                }

            }

        }
    }
    
    /**
     * This method is used to strip the 
     * HTML extension from the AEM internal URL's
     * 
     * @param doc
     * @param resolver
     */
    public static void removeHtmlExtensionFromUrl(Document doc,
            ResourceResolver resolver) {
    LOGGER.debug("Start of method :- removeHtmlExtensionFromUrl");

    Elements elements = doc.select("a[href]");
    Iterator<Element> iterator = elements.iterator();
    while (iterator.hasNext()) {
    	Element anchorElement = iterator.next();
    	String url = anchorElement.attr("href");
    	LOGGER.debug("href in anchor : - {0}", url);

    	if(StringUtils.startsWith(url, "/") && url.contains(HTML)){
    		anchorElement.attr("href", url.replace(HTML, ""));
    	}
    	
    }
    LOGGER.debug("Exit method :- removeHtmlExtensionFromUrl");
    	
    }
    
    /**
     * This method is used to remove the pattern a
     * component class from responsive grid div
     * 
     * @param doc
     * @param resolver
     */
    public static void removePatternAComponentClass(Document doc,
            ResourceResolver resolver) {
    LOGGER.debug("Start of method :- removePatternAComponentClass");

    Elements elements = doc.select("div.patternAComponent");
    Iterator<Element> iterator = elements.iterator();
    while (iterator.hasNext()) {
    	Element patternAElement = iterator.next();
    	patternAElement.removeClass("patternAComponent");
    	
    }
    LOGGER.debug("Exit method :- removePatternAComponentClass");
    	
    }
    
    /**
     * @param page
     * @return
     */
    public static Boolean isValidPage(Page page){
    	Boolean valid=true;
    	if(page==null){
    		valid=false;
    	}
    	return valid;    	
    }
    
    
    /**
     * @param resource
     * @return
     */
    public static Boolean isValidResource(Resource resource){
    	Boolean valid=true;
    	if(resource==null){
    		valid=false;
    	}
    	return valid;    	
    }
    

}
