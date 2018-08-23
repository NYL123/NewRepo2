package com.hertz.digital.platform.exporter.impl;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hertz.digital.platform.exporter.api.ComponentExporterService;
import com.hertz.digital.platform.utils.GenericServiceUtils;

/**
 * The component specific implementation of the exporter service required to
 * return the specific requested bean object.
 * 
 * Notice the identifier parameter which must be kept unique for each of the
 * created services.
 * 
 * @author himanshu.i.sharma
 *
 */
@Component(immediate = true, metatype = true, label = "Hertz - Reservation Service", description = "Hertz - Exposes the reservation component as bean/json")
@Service(value = ComponentExporterService.class)
@Properties(value = { @Property(name = "identifier", value = "reservation") })
public class ReservationExporterImpl implements ComponentExporterService {

	/**
	 * Declaring default public constructor
	 */
	public ReservationExporterImpl(){
		super();
	}
	
	private static final long serialVersionUID = -4386605044818895414L;
	private static final Logger logger = LoggerFactory.getLogger(ReservationExporterImpl.class);

	/**
	 * Left as is for any future implementation.
	 * 
	 * @param component
	 *            The component resource for conversion.
	 * @param resolver
	 *            The resolver object.
	 * @return The component JSON.
	 */
	@Override
	public String exportAsJson(Resource component, ResourceResolver resolver) {
		// left as an extension for later, if need be.
		return "{}";
	}

	/**
	 * It will populate the custom bean object for the passed component
	 * resource.
	 * 
	 * @param component
	 *            The component resource for conversion.
	 * @param resolver
	 *            The resolver object.
	 * @return The component bean.
	 */
	@Override
	public Object exportAsBean(Resource component, ResourceResolver resolver) {
		logger.debug("Start:: exportAsBean()");
			//left as there are no authorable fields in Reservation Component
		logger.debug("Ends:: exportAsBean()");
		return null;
	}
	
	/**
	 * It will return all the configured properties of the component
	 * in a map.
	 * 
	 * @param component
	 * 				 The component resource for conversion.
	 * @param resolver
	 * 				The resolver object.
	 * @param propertyValue
	 * 				The path of the configuration page
	 * @return
	 * @throws JSONException 
	 * @throws RepositoryException 
	 */
	@Override
	public Object exportAsMap(ResourceResolver resolver, Resource configPageResource) throws JSONException, RepositoryException {
		logger.debug("Start:: exportAsBean()");
		
		JSONObject configObject;
			configObject = GenericServiceUtils.setConfigJSONObject(configPageResource);
			Map<String, Object> retMap = new Gson().fromJson(
				    configObject.toString(), new TypeToken<HashMap<String, Object>>() {}.getType());
			logger.debug("Ends:: exportAsBean()");
			return retMap;
	}
}
