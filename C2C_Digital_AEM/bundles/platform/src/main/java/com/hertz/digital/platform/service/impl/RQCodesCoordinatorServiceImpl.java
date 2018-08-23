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
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hertz.digital.platform.bean.CoordinatorConsumable;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.factory.MicroServicesConfigurationFactory;
import com.hertz.digital.platform.service.api.MCDataCoordinatorService;
import com.hertz.digital.platform.service.api.SystemUserService;
import com.hertz.digital.platform.utils.GenericServiceUtils;
import com.hertz.digital.platform.utils.HertzUtils;

@Component(immediate = true, metatype = false,
        label = "Hertz - RQ Codes MC Data Coordinator Service",
        description = "Hertz - RQ Codes MC Data Coordinator Service")
@Service(value = MCDataCoordinatorService.class)
@Properties(value = @Property(name = "identifier", value = "rqcodes",
        propertyPrivate = true))
public class RQCodesCoordinatorServiceImpl extends AbstractMCDataCoordinator {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RQCodesCoordinatorServiceImpl.class);

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
    public RQCodesCoordinatorServiceImpl() {
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

    /*
     * (non-Javadoc)
     * 
     * @see com.hertz.digital.platform.service.api.MCDataCoordinatorService#
     * getCollatedData(java.lang.String)
     */
    @Override
    public CoordinatorConsumable getCollatedData(String path)
            throws JSONException, RepositoryException {
        LOGGER.debug("Inside getCollatedData() of {}",
                (String) properties.get(HertzConstants.IDENTIFIER));
        ResourceResolver resolver = systemService.getServiceResourceResolver();
        CoordinatorConsumable consumable = new CoordinatorConsumable();
        try {
            consumable.setIshealthy(isWhitelisted(path));
            consumable.setApiUrl(getAPIUrl(
                    (String) properties.get(HertzConstants.IDENTIFIER),
                    (String[]) hConfigFactory.getPropertyValue(
                            HertzConstants.HERTZ_DATA_API_MAPPING)));
            consumable.setBasePath(getBasePath(path, (String[]) hConfigFactory
                    .getPropertyValue(HertzConstants.HERTZ_DATA_BASEPATH)));
            consumable.setContentType(HertzConstants.CONTENT_TYPE_APP_JSON);
            consumable.setDataPath(getSpecificDataPath(path,
                    consumable.getBasePath(),
                    (String[]) hConfigFactory.getPropertyValue(
                            HertzConstants.HERTZ_DATA_COORDINATOR_MAPPING)));

            consumable.setRequestParams(
                    formRequestParams(consumable.getDataPath(),
                            resolver.getResource(consumable.getDataPath())
                                    .getParent()));
            LOGGER.debug(
                    "Paramters set are: - healthy: - {}, ApiUrl: - {}, BasePath: - {},  ContentType: - {},  DataPath: - {}, RequestParams: - {}",
                    consumable.ishealthy(), consumable.getApiUrl(),
                    consumable.getBasePath(), consumable.getContentType(),
                    consumable.getDataPath(), consumable.getRequestParams());
        } finally {
            HertzUtils.closeResolverSession(resolver, null);
        }

        LOGGER.debug("Exit getCollatedData()");
        return consumable;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hertz.digital.platform.service.impl.AbstractMCDataCoordinator#
     * isWhitelisted(java.lang.String)
     */
    @Override
    public boolean isWhitelisted(String path) {
        LOGGER.debug("Inside isWhitelisted() of {}",
                (String) properties.get(HertzConstants.IDENTIFIER));
        boolean isWhitelisted = false;
        if (isValidDataPath(path,
                (String[]) hConfigFactory
                        .getPropertyValue(HertzConstants.HERTZ_DATA_BASEPATH))
                && checkOnlyMapping(path,
                        (String[]) hConfigFactory.getPropertyValue(
                                HertzConstants.HERTZ_DATA_COORDINATOR_MAPPING))) {
            LOGGER.debug("Toggling the whiltlist flag.");
            isWhitelisted = true;
        }
        LOGGER.debug("Exit isWhitelisted()");
        return isWhitelisted;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hertz.digital.platform.service.impl.AbstractMCDataCoordinator#
     * getSpecificDataPath(java.lang.String, java.lang.String,
     * java.lang.String[])
     */
    @Override
    protected String getSpecificDataPath(String actionPath, String dataBasePath,
            String[] mappings) {
        LOGGER.debug("Inside getSpecificDataPath methodof {}",
                (String) properties.get(HertzConstants.IDENTIFIER));
        String jsonPath = StringUtils.EMPTY;
        for (String mapping : mappings) {
            String[] pairs = mapping.split(HertzConstants.COLON);
            if (pairs.length == 2 && actionPath.contains(pairs[0])) {
                LOGGER.debug("Processing for {} {}", pairs[0], pairs[1]);
                jsonPath = pairs[0];
                break;
            }
        }
        LOGGER.debug("The path from here is : - {}", dataBasePath + jsonPath);
        LOGGER.debug("Exit getSpecificDataPath method");
        return dataBasePath + jsonPath;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hertz.digital.platform.service.impl.AbstractMCDataCoordinator#
     * formRequestParams(java.lang.String,
     * org.apache.sling.api.resource.ResourceResolver)
     */
    @Override
    protected String formRequestParams(String dataPath, Resource resource)
            throws JSONException, RepositoryException {
        LOGGER.debug("Init :: formRequestParams()of {}",
                (String) properties.get(HertzConstants.IDENTIFIER));
        String jsonString = StringUtils.EMPTY;
        JSONObject jsonObject = GenericServiceUtils
                .setConfigJSONObject(resource);
        if (null != jsonObject && jsonObject.length() > 0) {
            jsonString = jsonObject.toString();
        }
        LOGGER.debug("The JSON string to be sent as params would be: - {}",
                jsonString);
        LOGGER.debug("Exit :: formRequestParams()");
        return jsonString;
    }
}
