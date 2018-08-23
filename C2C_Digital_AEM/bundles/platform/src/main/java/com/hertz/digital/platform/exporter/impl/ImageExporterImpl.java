package com.hertz.digital.platform.exporter.impl;

import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hertz.digital.platform.bean.ImageComponentBean;
import com.hertz.digital.platform.bean.ImageInfoBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.exporter.api.ComponentExporterService;
import com.hertz.digital.platform.service.api.JCRService;


/**
 * The component specific implementation of the exporter service required to
 * return the specific requested bean object.
 * 
 * Notice the identifier parameter which must be kept unique for each of the
 * created services.
 * 
 * @author a.dhingra
 *
 */
@Component(immediate = true, metatype = true, label = "Hertz - Image Service", description = "Hertz - Exposes the hero image component as bean/json")
@Service(value = ComponentExporterService.class)
@Properties(value = { @Property(name = "identifier", value = "image") })
public class ImageExporterImpl  implements ComponentExporterService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor Declaration
	 */
	public ImageExporterImpl() {
		super();
	}
	
	@Reference
	private transient JCRService jcrService;
	
	
	/**
	 * LOGGER instantiation
	 */
	private static final Logger logger = LoggerFactory.getLogger(HeroImageExporterImpl.class);
	
	@Override
	public String exportAsJson(Resource component, ResourceResolver resolver) {
		return null;
	}

	@Override
	public Object exportAsBean(Resource component, ResourceResolver resolver) {
		logger.debug("Start:: exportAsBean()");
		ImageComponentBean bean=new ImageComponentBean();
		if (null!=component) {
			ValueMap properties = component.getValueMap();
			bean.setAltText(PropertiesUtil.toString(properties.get(HertzConstants.ALT), StringUtils.EMPTY));
			ImageInfoBean imageInfo=new ImageInfoBean(component);
			bean.setSources(imageInfo.getSources());
		}
		return bean;
	}

	@Override
	public Object exportAsMap(ResourceResolver resolver, Resource configPageResource)
			throws JSONException, RepositoryException {
		
		return null;
	}
	
}
