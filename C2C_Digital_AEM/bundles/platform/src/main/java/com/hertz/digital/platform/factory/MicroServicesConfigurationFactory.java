package com.hertz.digital.platform.factory;

import java.util.Dictionary;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, metatype = true, description = "Micro Services Config Factory", label = "Hertz - Store house for Micro Services configs.")
@Service(value = MicroServicesConfigurationFactory.class)
@Properties({
		@Property(name = "hertz.mc.basepath", value = "", description = "Micro Services Server URL", label = "Server Path"),
		@Property(name = "hertz.mc.passwordgrant", value = "", description = "Password Grant Service", label = "Password Grant Service"),
		@Property(name = "hertz.token.ingestion.path", value = "", description = "Hertz Poassword Grant Token Ingestion Path", label = "Token Ingestion Path")

})
public class MicroServicesConfigurationFactory {

	/** Logger Instantiation. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MicroServicesConfigurationFactory.class);

	/** The properties. */
	private Dictionary<?, ?> properties;

	/**
	 * Default Constructors
	 */
	public MicroServicesConfigurationFactory() {
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
