package com.hertz.digital.platform.factory;

import java.util.Dictionary;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.PropertyUnbounded;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration store house for all the common configuration hertz wide.
 * 
 * @author n.kumar.singhal
 *
 */
@Component(immediate = true, metatype = true, description = "Hertz Config Factory", label = "Hertz - Store house for all configs which are common.")
@Service(value = HertzConfigFactory.class)
@Properties({
		@Property(name = "hertz.data.basepath", unbounded = PropertyUnbounded.ARRAY, description = "Hertz Data Base Path", label = "Hertz Data Base Path"),
		@Property(name = "hertz.data.api.mapping", unbounded = PropertyUnbounded.ARRAY, description = "Hertz Data API Mapping", label = "Hertz Data API Mapping"),
		@Property(name = "hertz.data.template.whitelist", unbounded = PropertyUnbounded.ARRAY, description = "Hertz Data Templates Whitelist", label = "Hertz Data Templates Whitelist"),
		@Property(name = "hertz.data.coordinator.mapping", unbounded = PropertyUnbounded.ARRAY, description = "Hertz Data Coordinator Mapping", label = "Hertz Data Coordinator Mapping"),
		@Property(name = "hertz.default.content.approver.groupname", description = "Hertz Content Reviewer group", label = "Hertz Content Reviewer group"),
		@Property(name = "hertz.default.dam.approver.groupname", description = "Hertz DAM Reviewer group", label = "Hertz DAM Reviewer group"),
		@Property(name = "hertz.pathtogroup.mappings.path", description = "Hertz Path to Group Mapping Page Path", label = "Hertz Path to Group Mapping Page Path"),
		@Property(name = "hertz.data.allowedComponents", unbounded = PropertyUnbounded.ARRAY, description = "Hertz  Data JSON allowed Template", label = "Hertz  Data JSON allowed Template"),
		@Property(name = "hertz.spa.allowedComponents", unbounded = PropertyUnbounded.ARRAY, description = "Hertz  SPA JSON allowed Template", label = "Hertz  SPA JSON allowed Template"),
		@Property(name = "hertz.renditions.extension", description = "Hertz- Renditions default extension", label = "Hertz- Renditions default extension"),
		@Property(name = "hertz.renditions.names", unbounded = PropertyUnbounded.ARRAY, description = "Hertz- Renditions Names", label = "Hertz- Renditions Names"),
		@Property(name = "hertz.renditions.densities", unbounded = PropertyUnbounded.ARRAY, description = "Hertz- Renditions Densities", label = "Hertz- Renditions Densities"),
		@Property(name = "hertz.renditions.quality", description = "Hertz- Renditions Quality", label = "Hertz- Renditions Quality"),
		@Property(name = "hertz.renditions.selectornamesmapping", unbounded = PropertyUnbounded.ARRAY, description = "Hertz- Renditions Selector:Names Mapping", label = "Hertz- Renditions Selector:Names Mapping")})


public class HertzConfigFactory {

	/** Logger Instantiation. */
	private static final Logger LOGGER = LoggerFactory.getLogger(HertzConfigFactory.class);

	/** The properties. */
	private Dictionary<?, ?> properties;

	/**
	 * Default Constructors
	 */
	public HertzConfigFactory() {
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
	 * Re-initializes the property map on changes to the config dictionary.
	 * 
	 * @param componentContext
	 */
	@Modified
	protected void modified(final ComponentContext componentContext) {
		LOGGER.debug("inside method: modified()");
		properties = componentContext.getProperties();
		LOGGER.debug("exiting method: modified()");
	}

	/**
	 * Method to get String property value from config. If property can not be
	 * found or can not be cast to a string, return default string with key.
	 * This will make it apparent if there is some config missing
	 * 
	 * @param key
	 * @return configuration value String
	 */
	public String getStringPropertyValue(String key) {
		LOGGER.debug("inside method: getStringPropertyValue()");
		String property = (String) properties.get(key);
		LOGGER.debug("exiting method: getStringPropertyValue()");
		return property;
	}

	/**
	 * Method to get property value from config.
	 * 
	 * @param key
	 * @return configuration value String
	 */
	public Object getPropertyValue(String key) {
		LOGGER.debug("inside method: getPropertyValue()");
		Object propertyValue = properties.get(key);
		LOGGER.debug("exiting method: getPropertyValue()");
		return propertyValue;
	}
}
