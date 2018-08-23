package com.hertz.digital.platform.use;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.hertz.digital.platform.bean.spa.SpaPageBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.exporter.api.ComponentExporterService;
import com.hertz.digital.platform.use.spa.GenericPageUse;
import com.hertz.digital.platform.utils.HertzUtils;

public class NativeUse extends GenericPageUse {
    /**
     * Logger instantiation
     */
    private static final Logger logger = LoggerFactory
            .getLogger(NativeUse.class);

    /**
     * Default Constructor
     */
    public NativeUse() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hertz.digital.platform.use.spa.NativeUse#activate()
     */
    @Override
    public void activate() throws JSONException, RepositoryException,
            IOException, ServletException {

        super.activate();
        logger.debug("Received spa bean from generic page use is :- {}",
                getSpaBean());

        JSONObject object = new JSONObject();

        List<Map<String,
                Object>> componentsList = new ArrayList<Map<String, Object>>();

        Map<String, Object> beanMap = new LinkedHashMap<>();
        String[] components = HertzUtils.toArray(
                get(HertzConstants.COMPONENTS, String.class),
                HertzConstants.COMMA);
        Resource gridResource = getCurrentPage()
                .getContentResource("responsivegrid");
        if (null != gridResource) {
            for (String component : components) {
                String[] compConfigArray = component
                        .split(HertzConstants.BCK_SLASH + HertzConstants.DOT);
                ComponentExporterService[] exporterService = getSlingScriptHelper()
                        .getServices(ComponentExporterService.class,
                                "(identifier=" + compConfigArray[0] + ")");
                if (null != exporterService && exporterService.length > 0) {
                    logger.debug(
                            "Instance {} for the {} identifier found. Invoking for bean conversion.",
                            exporterService[0].getClass().getCanonicalName(),
                            compConfigArray[0]);

                    beanMap.put(getRelevantKey(compConfigArray),
                            exporterService[0].exportAsBean(gridResource,
                                    getResourceResolver()));

                }

            }
            if (beanMap.size() > 0) {
                componentsList.add(beanMap);
            }
            // overriding SPA
            getSpaBean().setIncludedComponents(componentsList);

            Gson gson = HertzUtils.initGsonBuilder(true, true, true);
            object.accumulate(HertzConstants.NATIVE_CONTENT, new JSONObject(
                    gson.toJson(getSpaBean(), SpaPageBean.class)));

            // overriding SPA
            setJsonString(object.toString());
        }
    }

    /**
     * This method assumes the below format for the key passage
     * "spa-route:componentname.customized-key-name" in the front-end components
     * attribute while calling this use class, where below extra combinations
     * are possible. The preference in which keys shall be picked:- custom-name
     * > componentname.
     * <ul>
     * <li>componentname</li>
     * <li>componentname.customized-key-name</li>
     * <li>spa-route:componentname</li>
     * </ul>
     * 
     * @param component
     * @return
     */
    private String getRelevantKey(String[] compConfigArray) {
        String keyName = StringUtils.EMPTY;
        if (compConfigArray.length > 1) {
            keyName = compConfigArray[1];
        } else {
            String[] tempArray = compConfigArray[0].split(HertzConstants.COLON);
            if (tempArray.length > 1) {
                keyName = tempArray[1];
            } else {
                keyName = tempArray[0];
            }
        }
        logger.debug("Key established here is : - {}", keyName);
        return keyName;
    }
}
