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

@Component(immediate = true, metatype = true, description = "FED Config Factory", label = "Hertz - Store house for FED configs.")
@Service(value = FedServicesConfigurationFactory.class)
@Properties({
		@Property(name = "hertz.fed.basepath", value = "", description = "FED Server URL", label = "Server Path"),
		@Property(name = "hertz.fed.passwordgrant", value = "", description = "Password Grant Service", label = "Password Grant Service"),
		@Property(name = "hertz.token.ingestion.path", value = "", description = "Hertz Poassword Grant Token Ingestion Path", label = "Token Ingestion Path")

})
public class FedServicesConfigurationFactory {
	/** Logger Instantiation. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FedServicesConfigurationFactory.class);

	/** The properties. */
	private Dictionary<?, ?> properties;

	/**
	 * Default Constructors
	 */
	public FedServicesConfigurationFactory() {
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
