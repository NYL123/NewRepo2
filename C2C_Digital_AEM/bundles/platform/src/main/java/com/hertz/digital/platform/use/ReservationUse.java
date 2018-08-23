package com.hertz.digital.platform.use;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.utils.GenericServiceUtils;

/**
 * This class is used for generating the values of the reservation component to
 * display on the home page.
 * 
 * @author a.dhingra
 *
 */
public class ReservationUse extends WCMUsePojo {

    /**
     * Default Constructor Declaration
     */
    public ReservationUse() {
        super();
    }

    /**
     * LOGGER instantiation
     */

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ReservationUse.class);

    Map<String, Object> retMap;

    /**
     * This method gets the map of all the configured properties and returns it
     * to the reservation.html
     * 
     */
    @Override
    public void activate() {
        LOGGER.debug("Start:- activate() of ReservationUse");
        Resource pageResource = null;
        JSONObject object = new JSONObject();
        try {
            String reservationPath = get(HertzConstants.PATH, String.class);
            pageResource = getResourceResolver().getResource(reservationPath);
            GenericServiceUtils.setConfigObject(pageResource, object);
            retMap = new Gson().fromJson(object.toString(),
                    new TypeToken<HashMap<String, Object>>() {
                    }.getType());
        } catch (JSONException | RepositoryException e) {
            LOGGER.error("Error in ReservationUse class:- " + e.getMessage());
        }
        LOGGER.debug("Exit:- activate() of ReservationUse");

    }

    /**
     * @return the retMap
     */
    public Map<String, Object> getRetMap() {
        return retMap;
    }

    /**
     * @param retMap
     *            the retMap to set
     */
    public void setRetMap(Map<String, Object> retMap) {
        this.retMap = retMap;
    }

}
