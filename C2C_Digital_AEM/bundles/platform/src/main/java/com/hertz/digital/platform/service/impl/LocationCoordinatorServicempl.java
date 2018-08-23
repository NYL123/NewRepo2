package com.hertz.digital.platform.service.impl;

import java.util.Dictionary;

import javax.jcr.RepositoryException;

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

import com.day.cq.commons.Externalizer;
import com.hertz.digital.platform.bean.CoordinatorConsumable;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.factory.MicroServicesConfigurationFactory;
import com.hertz.digital.platform.service.api.JCRService;
import com.hertz.digital.platform.service.api.MCDataCoordinatorService;
import com.hertz.digital.platform.service.api.SystemUserService;
import com.hertz.digital.platform.use.DataUse;
import com.hertz.digital.platform.utils.HertzUtils;

@Component(immediate = true, metatype = false,
        label = "Hertz - Location MC Data Coordinator Service",
        description = "Hertz - Location MC Data Coordinator Service")
@Service(value = MCDataCoordinatorService.class)
@Properties(value = @Property(name = "identifier", value = "location",
        propertyPrivate = true))
public class LocationCoordinatorServicempl extends AbstractMCDataCoordinator {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(LocationCoordinatorServicempl.class);

    @Reference
    HertzConfigFactory hConfigFactory;

    @Reference
    MicroServicesConfigurationFactory mcFactory;

    @Reference
    private transient SystemUserService systemService;
    /** The properties. */
    private Dictionary<?, ?> properties;

    /**
     * Default Constructors
     */
    public LocationCoordinatorServicempl() {
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
        ResourceResolver resolver = null;
        LOGGER.debug("Inside getCollatedData() of {}",
                (String) properties.get(HertzConstants.IDENTIFIER));
        resolver = systemService.getServiceResourceResolver();
        CoordinatorConsumable consumable = new CoordinatorConsumable();
        try {
            consumable.setIshealthy(isWhitelisted(path));
            if (consumable.ishealthy()) {
                consumable.setApiUrl(getAPIUrl(
                        (String) properties.get(HertzConstants.IDENTIFIER),
                        (String[]) hConfigFactory.getPropertyValue(
                                HertzConstants.HERTZ_DATA_API_MAPPING)));
                consumable.setBasePath(getBasePath(path,
                        (String[]) hConfigFactory.getPropertyValue(
                                HertzConstants.HERTZ_DATA_BASEPATH)));
                consumable.setContentType(HertzConstants.CONTENT_TYPE_APP_JSON);
                consumable.setDataPath(getSpecificDataPath(path, null, null));
                consumable.setRequestParams(formRequestParams(
                        consumable.getDataPath(),
                        resolver.getResource(consumable.getDataPath())));
                LOGGER.debug(
                        "Paramters set are: - healthy: - {}, ApiUrl: - {}, BasePath: - {},  ContentType: - {},  DataPath: - {}, RequestParams: - {}",
                        consumable.ishealthy(), consumable.getApiUrl(),
                        consumable.getBasePath(), consumable.getContentType(),
                        consumable.getDataPath(),
                        consumable.getRequestParams());
                LOGGER.debug("Exit getCollatedData()");
            }
        } finally {
            HertzUtils.closeResolverSession(resolver, null);
        }
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
        ResourceResolver resolver = null;

        LOGGER.debug("Inside isWhitelisted() of {}",
                (String) properties.get(HertzConstants.IDENTIFIER));
        boolean isWhitelisted;
        try {
            resolver = systemService.getServiceResourceResolver();
            isWhitelisted = false;
            if (isValidDataPath(path,
                    (String[]) hConfigFactory.getPropertyValue(
                            HertzConstants.HERTZ_DATA_BASEPATH))
                    && checkMappingAndTemplate(resolver, path,
                            (String[]) hConfigFactory.getPropertyValue(
                                    HertzConstants.HERTZ_DATA_COORDINATOR_MAPPING),
                            (String[]) hConfigFactory.getPropertyValue(
                                    HertzConstants.HERTZ_DATA_TEMPLATE_WHITELIST),
                            (String) properties
                                    .get(HertzConstants.IDENTIFIER))) {
                LOGGER.debug("Toggling the whiltlist flag.");
                isWhitelisted = true;
            }
        } finally {
            HertzUtils.closeResolverSession(resolver, null);
        }
        LOGGER.debug("Exit isWhitelisted()");
        return isWhitelisted;
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
        LOGGER.debug("Inside formRequestParams() of {}",
                (String) properties.get(HertzConstants.IDENTIFIER));
        JSONObject object = new JSONObject();
        if (null != resource) {
            LOGGER.debug(
                    "The resource for location found. Rendering its Json.");
            JSONObject jsonObj = DataUse.getComponentlocationOverlay(
                    resource.getPath(), resource.getResourceResolver(),
                    HertzUtils.getServiceReference(JCRService.class),
                    HertzUtils.getServiceReference(Externalizer.class));
            JSONObject tempObj = (JSONObject) jsonObj.get("location");
            tempObj.put("OAG", resource.getName());
            object.accumulate("location", tempObj);
        }
        LOGGER.debug("The form params for this request is: - {}",
                object.toString());
        LOGGER.debug("Exit formRequestParams()");

        return object.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hertz.digital.platform.service.impl.AbstractMCDataCoordinator#
     * getSpecificDataPath(java.lang.String, java.lang.String,
     * java.lang.String[])
     */
    @Override
    protected String getSpecificDataPath(String path, String dataBasePath,
            String[] mappings) {
        LOGGER.debug("Inside getSpecificDataPath() of {}",
                (String) properties.get(HertzConstants.IDENTIFIER));

        LOGGER.debug("Exit getSpecificDataPath()");
        return path;
    }

}
