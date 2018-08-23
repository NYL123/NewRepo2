package com.hertz.digital.platform.exporter.impl;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.engine.SlingRequestProcessor;
import org.apache.sling.settings.SlingSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.contentsync.handler.util.RequestResponseFactory;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.exporter.api.ComponentExporterService;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * The component specific implementation of the exporter service required to
 * return the specific requested bean object.
 * 
 * Notice the identifier parameter which must be kept unique for each of the
 * created services.
 * 
 * @author n.kumar.singhal
 *
 */
@Component(immediate = true, metatype = false)
@Service(value = ComponentExporterService.class)
@Properties(value = { @Property(name = "identifier", value = "fixedcontentslot", propertyPrivate = true) })
public class FixedContentSlotExporterImpl implements ComponentExporterService {

	/**
	 * Default Constructor Declaration
	 */
	public FixedContentSlotExporterImpl() {
		super();
	}

	private static final long serialVersionUID = 4539704302780929346L;

	/**
	 * LOGGER instantiation
	 */
	private static final Logger logger = LoggerFactory.getLogger(FixedContentSlotExporterImpl.class);

	/**
	 * This method would return the html for this the passed resource, based on
	 * whether the exposeAsHtml is set to true. It would render the resource
	 * using patternchoice selector.
	 * 
	 * exportAsJson(org.apache.sling.api.resource.Resource,
	 * org.apache.sling.api.resource.ResourceResolver)
	 */
	@Override
	public String exportAsJson(Resource component, ResourceResolver resolver) {
		ValueMap properties = component.getValueMap();
		StringBuilder stringBuilder = new StringBuilder();
		if (Boolean.valueOf((String) properties.get("exposeashtml"))) {
			RequestResponseFactory requestResponseFactory = HertzUtils
					.getServiceReference(RequestResponseFactory.class);
			SlingRequestProcessor requestProcessor = HertzUtils.getServiceReference(SlingRequestProcessor.class);
			SlingSettingsService slingService = HertzUtils.getServiceReference(SlingSettingsService.class);
			try {
				stringBuilder = HertzUtils.getHTMLofComponent(resolver, requestResponseFactory, requestProcessor,
						component.getPath() + HertzConstants.DOT + (String) properties.get("patternchoice"),slingService);
			} catch (ServletException | IOException e) {
				logger.error("Error occured {} {}", e, e.getStackTrace());
			}
		}
		return stringBuilder.toString();
	}

	/**
	 * exportAsBean(org.apache.sling.api.resource.Resource,
	 * org.apache.sling.api.resource.ResourceResolver)
	 */
	@Override
	public Object exportAsBean(Resource component, ResourceResolver resolver) {
		String object = StringUtils.EMPTY;
		logger.debug("Start:: exportAsBean()");
		if (null != component && component.hasChildren()) {
			object = exportAsJson(component, resolver);
		}
		logger.debug("Ends:: exportAsBean()");
		return object;
	}

	@Override
	public Object exportAsMap(ResourceResolver resolver, Resource configPageResource) {
		// left as Hero component does not have any configurable fields.
		return null;
	}

}
