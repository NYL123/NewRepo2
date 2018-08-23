package com.hertz.digital.platform.exporter.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hertz.digital.platform.bean.HeroImageBean;
import com.hertz.digital.platform.bean.ImageInfoBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.exporter.api.ComponentExporterService;

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
@Component(immediate = true, metatype = true, label = "Hertz - Hero Image Service", description = "Hertz - Exposes the hero image component as bean/json")
@Service(value = ComponentExporterService.class)
@Properties(value = { @Property(name = "identifier", value = "hero") })
public class HeroImageExporterImpl implements ComponentExporterService {

	/**
	 * Default Constructor Declaration
	 */
	public HeroImageExporterImpl(){
		super();
	}
	
	private static final long serialVersionUID = 4539704302780929346L;
	
	
	/**
	 * LOGGER instantiation
	 */
	private static final Logger logger = LoggerFactory.getLogger(HeroImageExporterImpl.class);

	/**
	 * exportAsJson(org.apache.sling.api.resource.Resource,
	 * org.apache.sling.api.resource.ResourceResolver)
	 */
	@Override
	public String exportAsJson(Resource component, ResourceResolver resolver) {
		// left as an extension for later, if need be.
		return "{}";
	}

	/**
	 * exportAsBean(org.apache.sling.api.resource.Resource,
	 * org.apache.sling.api.resource.ResourceResolver)
	 */
	@Override
	public Object exportAsBean(Resource component, ResourceResolver resolver) {
		logger.debug("Start:: exportAsBean()");
		HeroImageBean heroImageBean = null;
		if (null!=component) {
			heroImageBean = new HeroImageBean();
			ValueMap properties = component.getValueMap();
			if(properties.containsKey(HertzConstants.ALT_TEXT)){
				heroImageBean.setAltText((String) properties.get(HertzConstants.ALT_TEXT));
			}
			if(properties.containsKey(HertzConstants.TAGLINE_TEXT)){
				heroImageBean.setTagLineText((String) properties.get(HertzConstants.TAGLINE_TEXT));
			}
			if(properties.containsKey(HertzConstants.HERO_SUB_TAGLINE_TEXT)){
				heroImageBean.setSubTagLineText((String) properties.get(HertzConstants.HERO_SUB_TAGLINE_TEXT));
			}
			
			ImageInfoBean imageInfo = new ImageInfoBean(component);
			heroImageBean.setSources(imageInfo.getSources());
		}
		logger.debug("Ends:: exportAsBean()");
		return heroImageBean;
	}

	@Override
	public Object exportAsMap(ResourceResolver resolver, Resource configPageResource) {
		//left as Hero component does not have any configurable fields.
		return null;
	}

}
