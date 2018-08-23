package com.hertz.digital.platform.exporter.api;

import java.io.Serializable;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONException;

/**
 * This is a generic interface which is to be used by the page specific use
 * classes to dynamically delegate to a underlying component exporter service.
 * The thus binded service would then return the populated bean/json object to
 * the calling class.
 * 
 * @author n.kumar.singhal
 *
 */
public interface ComponentExporterService extends Serializable, Cloneable {

	/**
	 * Left as is for any future implementation.
	 * 
	 * @param component
	 *            The component resource for conversion.
	 * @param resolver
	 *            The resolver object.
	 * @return The component JSON.
	 */
	String exportAsJson(Resource component, ResourceResolver resolver);

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
	Object exportAsBean(Resource component, ResourceResolver resolver);

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
	 * @throws Exception 
	 */
	Object exportAsMap(ResourceResolver resolver, Resource configPageResource) throws JSONException, RepositoryException;

}
