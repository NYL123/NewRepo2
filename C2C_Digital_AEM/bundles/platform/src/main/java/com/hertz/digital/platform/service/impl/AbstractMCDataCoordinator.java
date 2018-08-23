package com.hertz.digital.platform.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.service.api.MCDataCoordinatorService;

/**
 * The parent class for child co-ordinators for clubbing common related methods.
 * 
 * @author n.kumar.singhal
 *
 */
public abstract class AbstractMCDataCoordinator
        implements MCDataCoordinatorService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AbstractMCDataCoordinator.class);
    
    private static final String PROCESSING = "Processing for :- {}"; 
    
    /**
     * Checks the valid data path.
     * 
     * @param path
     *            The data path to be checked.
     * @param basePaths
     *            The base path options.
     * @return The flag indicating path validity.
     */
    protected boolean isValidDataPath(String path, String[] basePaths) {
        LOGGER.debug("Init :: isValiDataPath()");
        boolean isValid = false;
        if (StringUtils.isNotEmpty(path)) {
            for (String mapping : basePaths) {
                LOGGER.debug(PROCESSING, mapping);
                if (path.contains(mapping)) {
                    LOGGER.debug("Match found. Setting valid flag");
                    isValid = true;
                }
            }

        }
        LOGGER.debug("Exit :: isValiDataPath()");
        return isValid;

    }

    /**
     * Method to return the Base Path.
     * 
     * @param path
     *            The action path.
     * @param basePaths
     *            The base path options.
     * @return The base path.
     */
    protected String getBasePath(String path, String[] basePaths) {
        LOGGER.debug("Init :: getBasePath()");
        String basePath = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(path)) {
            for (String mapping : basePaths) {
                LOGGER.debug(PROCESSING, mapping);
                if (path.contains(mapping)) {
                    basePath = mapping;
                    LOGGER.debug("Setting  Base Path:- {}", basePath);
                    break;
                }
            }

        }
        LOGGER.debug("Exit :: getBasePath()");
        return basePath;
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
            LOGGER.debug(PROCESSING, mapping);
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

    /**
     * To check both path and template mapping(for locations).
     * 
     * @param resolver
     *            The resolver object.
     * @param path
     *            The path variable.
     * @param mappings
     *            The mappings array.
     * @param templateMappings
     *            The template mappings array.
     * @return The flag indicating the match.
     */
    protected boolean checkMappingAndTemplate(ResourceResolver resolver,
            String path, String[] mappings, String[] templateMappings,
            String identifier) {
        LOGGER.debug("Init :: checkMappingAndTemplate()");
        Map<String, String> whiteTemplates = new HashMap<>();
        for (String mapping : templateMappings) {
            String[] pairs = mapping.split(HertzConstants.COLON);
            if (pairs.length == 2) {
                LOGGER.debug("Processing mapping for :- {} {}", pairs[0], pairs[1]);
                whiteTemplates.put(pairs[0], pairs[1]);
            }
        }
        LOGGER.debug("Map formed here is : - {}", whiteTemplates);
        boolean isMatch = false;
        Page page = resolver.adaptTo(PageManager.class).getPage(path);
        for (String mapping : mappings) {
            LOGGER.debug(PROCESSING, mapping);
            String[] pairs = mapping.split(HertzConstants.COLON);
            if (path.contains(pairs[0])
                    && whiteTemplates.containsKey(identifier)
                    && page.getContentResource().getValueMap()
                            .get(HertzConstants.JCR_CQ_TEMPLATE)
                            .equals(whiteTemplates.get(identifier))) {
                LOGGER.debug("Match Found. Setting the match flag.");
                isMatch = true;
                break;
            }
        }
        LOGGER.debug("Exit :: checkMappingAndTemplate()");
        return isMatch;
    }

    /**
     * Provides the API URL for which the action path matched.
     * 
     * @param actionPath
     *            The action path variable.
     * @param mapping
     *            The mappings array.
     * 
     * @return The api url.
     */
    protected String getAPIUrl(String identifier, String[] mappings) {
        LOGGER.debug("Init :: getAPIUrl()");
        String apiUrl = StringUtils.EMPTY;
        for (String mapping : mappings) {
            LOGGER.debug(PROCESSING, mapping);
            String[] pairs = mapping.split(HertzConstants.COLON);
            if (pairs.length == 2 && identifier.contains(pairs[0])) {
                apiUrl = pairs[1];
                break;
            }
        }
        LOGGER.debug("Set API URL as :- {}", apiUrl);
        LOGGER.debug("Exit :: getAPIUrl()");
        return apiUrl;
    }

    /**
     * The method to set the request params per each request.
     * 
     * @param dataPath
     *            The set data path.
     * @param Resource
     *            The resource.
     * @return The request param as string.
     * @throws JSONException
     *             The exception.
     * @throws RepositoryException
     *             The exception.
     */
    protected abstract String formRequestParams(String dataPath,
            Resource resource) throws JSONException, RepositoryException;

    /**
     * Provides the Data Path for preparing param JSON, with respect to the
     * action path matched.
     * 
     * @param actionPath
     *            The action path.
     * @param dataBasePath
     *            The data base path.
     * @param mapping
     *            The data-coordinator mapping.
     * @return The hittable data base path
     */
    protected abstract String getSpecificDataPath(String actionPath,
            String dataBasePath, String[] mappings);
}
