package com.hertz.digital.platform.service.impl;

import java.util.Dictionary;

import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.bean.CoordinatorConsumable;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.factory.MicroServicesConfigurationFactory;
import com.hertz.digital.platform.service.api.JCRService;
import com.hertz.digital.platform.service.api.MCDataCoordinatorService;
import com.hertz.digital.platform.service.api.SystemUserService;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * This class is used to form the request for the external splash notification
 * service.
 * 
 * @author himanshu.i.sharma
 *
 */
@Component(immediate = true, metatype = false, label = "Hertz - External Splash MC Data Coordinator Service", description = "Hertz - External Splash MC Data Coordinator Service")
@Service(value = MCDataCoordinatorService.class)
@Properties(value = @Property(name = "identifier", value = "externalsplash", propertyPrivate = true))
public class ExternalSplashCoordinatorServiceImpl extends AbstractMCDataCoordinator {

	/**
	 * LOGGER instantiation
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExternalSplashCoordinatorServiceImpl.class);

	@Reference
	JCRService jcrService;
	@Reference
	Externalizer externalizer;
	@Reference
	private HertzConfigFactory hConfigFactory;

	@Reference
	private MicroServicesConfigurationFactory mcFactory;

	@Reference
	private transient SystemUserService systemService;
	/** The properties. */
	private Dictionary<?, ?> properties;

	/**
	 * Default Constructors
	 */
	public ExternalSplashCoordinatorServiceImpl() {
		super();
	}

	/**
	 * Activate method: re-instantiates the singleton object for config.
	 *
	 * @param componentContext
	 *            the component context
	 */
	@Activate
	protected void activate(final ComponentContext componentContext) {
		LOGGER.debug("inside method: activate()");
		properties = componentContext.getProperties();
		LOGGER.debug("exiting method: activate()");
	}

	/**
	 * This method is used to set the parameters in the consumable object
	 * 
	 * @param path
	 *            The action path
	 * @return
	 * 
	 */
	@Override
	public CoordinatorConsumable getCollatedData(String path) throws JSONException, RepositoryException {
		LOGGER.debug("Inside getCollatedData() of {}", (String) properties.get(HertzConstants.IDENTIFIER));
		ResourceResolver resolver = systemService.getServiceResourceResolver();
		CoordinatorConsumable consumable = new CoordinatorConsumable();
		try {
			consumable.setIshealthy(isWhitelisted(path));
			consumable.setApiUrl(getAPIUrl((String) properties.get(HertzConstants.IDENTIFIER),
					(String[]) hConfigFactory.getPropertyValue(HertzConstants.HERTZ_DATA_API_MAPPING)));
			consumable.setBasePath(
					getBasePath(path, (String[]) hConfigFactory.getPropertyValue(HertzConstants.HERTZ_DATA_BASEPATH)));
			consumable.setContentType(HertzConstants.CONTENT_TYPE_APP_JSON);
			consumable.setDataPath(getSpecificDataPath(path, consumable.getBasePath(),
					(String[]) hConfigFactory.getPropertyValue(HertzConstants.HERTZ_DATA_COORDINATOR_MAPPING)));
			consumable.setRequestParams(
					formRequestParams(consumable.getDataPath(), resolver.getResource(consumable.getDataPath())));
			LOGGER.debug(
					"Paramters set are: - healthy: - {}, ApiUrl: - {}, BasePath: - {},  ContentType: - {},  DataPath: - {}, RequestParams: - {}",
					consumable.ishealthy(), consumable.getApiUrl(), consumable.getBasePath(),
					consumable.getContentType(), consumable.getDataPath(), consumable.getRequestParams());
		} finally {
			HertzUtils.closeResolverSession(resolver, null);
		}
		return consumable;
	}

	/**
	 * This method is used to form the payload for the request.
	 *
	 * @param dataPath            The path being replicated
	 * @param dataResource the data resource
	 * @return the string
	 * @throws JSONException the JSON exception
	 * @throws RepositoryException the repository exception
	 */
	@Override
	protected String formRequestParams(String dataPath, Resource dataResource)
			throws JSONException, RepositoryException {
		LOGGER.debug("Init :: formRequestParams()of {}", (String) properties.get(HertzConstants.IDENTIFIER));
		String jsonString = StringUtils.EMPTY;
		JSONObject finalJsonObject = new JSONObject();
		if (HertzUtils.isValidResource(dataResource)) {
			LOGGER.debug("path of dataResource :- " + dataResource.getPath());
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("correlationId", System.currentTimeMillis());
			Page page = dataResource.adaptTo(Page.class);
			Resource contentResource = null;
			if (HertzUtils.isValidPage(page)) {
				contentResource = page.getContentResource();
				if (HertzUtils.isValidResource(contentResource)) {
					getJsonBodyParams(finalJsonObject, jsonObject, contentResource);
				}
			}

			if (null != jsonObject && jsonObject.length() > 0) {
				jsonString = jsonObject.toString();
			}

		}
		LOGGER.debug("The JSON string to be sent as params would be: - {}", jsonString);
		LOGGER.debug("Exit :: formRequestParams()");
		return jsonString;
	}

	/**
	 * Gets the json body params.
	 *
	 * @param finalJsonObject the final json object
	 * @param jsonObject the json object
	 * @param contentResource the content resource
	 * @return the json body params
	 * @throws JSONException the JSON exception
	 */
	private void getJsonBodyParams(JSONObject finalJsonObject, JSONObject jsonObject, Resource contentResource)
			throws JSONException {
		Resource parResource = contentResource.getChild(HertzConstants.PAR);
		if (HertzUtils.isValidResource(parResource)) {
			Iterable<Resource> resourceIterator = parResource.getChildren();
			JSONObject extSplashObject = new JSONObject();
			JSONArray extSplashJsonArray = new JSONArray();
			String str = StringUtils.EMPTY;
			for (Resource subResource : resourceIterator) {
				JSONObject object = new JSONObject();
				String componentResourceType = subResource.getResourceType();
				str = componentResourceType.substring(componentResourceType.lastIndexOf('/'))
						.replaceAll("/", "");
				ValueMap subResourceMap = subResource.getValueMap();
				if (subResourceMap.containsKey(HertzConstants.KEY)) {
					object.put(HertzConstants.KEY,
							StringUtils.trim(subResourceMap.get(HertzConstants.KEY, String.class)));
					object.put(HertzConstants.VALUE,
							StringUtils.defaultIfBlank(
									StringUtils
											.trim(subResourceMap.get(HertzConstants.VALUE, String.class)),
									StringUtils.EMPTY));
					extSplashJsonArray.put(object);
				}
			}
			extSplashObject.accumulate(str, extSplashJsonArray);
			JSONArray jsonArray = new JSONArray();
			jsonArray.put(extSplashObject);
			finalJsonObject.accumulate(HertzConstants.COMPONENTS, jsonArray);

			JSONArray finalArray = new JSONArray();
			finalArray.put(finalJsonObject);
			jsonObject.put(HertzConstants.DATA_CONTENT, finalArray);
		}
	}

	/**
	 * This method is used to get the data path
	 * 
	 * @param actionPath
	 *            The page being replicated.
	 * @param databasePath
	 *            The base path for data hierarchy
	 * @param mappings
	 *            The map for mappings
	 * @return
	 */
	@Override
	protected String getSpecificDataPath(String actionPath, String dataBasePath, String[] mappings) {
		LOGGER.debug("Inside getSpecificDataPath methodof {}", (String) properties.get(HertzConstants.IDENTIFIER));
		LOGGER.debug("Exit getSpecificDataPath method");
		return actionPath;
	}

	/**
	 * This method is used to check if data path is valid
	 * 
	 * @param path
	 *            The base path for data hierarchy
	 */
	@Override
	public boolean isWhitelisted(String path) {
		LOGGER.debug("Inside isWhitelisted() of {}", (String) properties.get(HertzConstants.IDENTIFIER));
		boolean isWhitelisted = false;
		if (isValidDataPath(path, (String[]) hConfigFactory.getPropertyValue(HertzConstants.HERTZ_DATA_BASEPATH))
				&& checkOnlyMapping(path,
						(String[]) hConfigFactory.getPropertyValue(HertzConstants.HERTZ_DATA_COORDINATOR_MAPPING))) {
			LOGGER.debug("Toggling the whiltlist flag.");
			isWhitelisted = true;
		}
		LOGGER.debug("Exit isWhitelisted()");
		return isWhitelisted;
	}

	/**
	 * The check only mapping method to only check path mapping.
	 * 
	 * @param path
	 *            The action path.
	 * @param mappings
	 *            The mappings array.
	 * @return The flag indicating the action matches the path constraints.
	 */
	protected boolean checkOnlyMapping(String path, String[] mappings) {
		LOGGER.debug("Init :: checkOnlyMapping()");

		boolean isMatch = false;
		for (String mapping : mappings) {
			LOGGER.debug("Processing for :- {}", mapping);
			String[] pairs = mapping.split(HertzConstants.COLON);
			if (path.contains(pairs[0])) {
				LOGGER.debug("Match found. Setting match flag");
				isMatch = true;
				break;
			}
		}
		LOGGER.debug("Exit :: checkOnlyMapping()");
		return isMatch;
	}

}
