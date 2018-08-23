package com.hertz.digital.platform.exporter.impl;

import javax.jcr.RepositoryException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.hertz.digital.platform.bean.HeroImageBean;
import com.hertz.digital.platform.bean.ImageInfoBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.exporter.api.ComponentExporterService;

@Component(immediate = true, metatype = true, label = "Hertz - Location Directory Hero Image Service", description = "Hertz - Exposes the location directory hero image component as bean/json")
@Service(value = ComponentExporterService.class)
@Properties(value = { @Property(name = "identifier", value = "locationdirectoryhero") })
public class LocationDirectoryHeroImageImpl implements ComponentExporterService {

	/**
	 * Default Constructor Declaration
	 */
	public LocationDirectoryHeroImageImpl(){
		super();
	}

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * LOGGER instantiation
	 */
	private static final Logger logger = LoggerFactory.getLogger(LocationDirectoryHeroImageImpl.class);

	@Override
	public String exportAsJson(Resource component, ResourceResolver resolver) {
		
		return null;
	}

	@Override
	public Object exportAsBean(Resource component, ResourceResolver resolver) {
		logger.debug("Start:: exportAsBean()");
		HeroImageBean heroImageBean = null;
		if (null != component) {
			heroImageBean = new HeroImageBean();
			ValueMap properties = component.getValueMap();
			if (properties.containsKey(HertzConstants.ALT_TEXT)) {
				heroImageBean.setAltText((String) properties.get(HertzConstants.ALT_TEXT));
			}
			if (properties.containsKey(HertzConstants.FILE_REFERENCE)) {
				ImageInfoBean imageInfo = new ImageInfoBean(component);
				heroImageBean.setSources(imageInfo.getSources());
			} else {
				PageManager pageManager=resolver.adaptTo(PageManager.class);
				Page page = pageManager.getContainingPage(component);
				setHeroImageSource(heroImageBean, page);

			}
		}
		logger.debug("Ends:: exportAsBean()");
		return heroImageBean;
	}

    /**
     * Sets the hero image source.
     *
     * @param heroImageBean the hero image bean
     * @param page the page
     */
    public void setHeroImageSource(HeroImageBean heroImageBean, Page page) {
        if (null != page) {
        	Page parentPage = page.getAbsoluteParent(4);
        	if (null != parentPage) {
        		setHeroImageSourceInBean(parentPage, heroImageBean);
        	}
        }
    }
    
    /**
     * Sets the hero image source in bean.
     * @param parentPage
     * @param heroImageBean
     */
    public void setHeroImageSourceInBean(Page parentPage, HeroImageBean heroImageBean) {
    	Resource heroImageResource = parentPage.getContentResource(HertzConstants.HERO);
		if (null != heroImageResource) {
			ValueMap map = heroImageResource.getValueMap();
			if (map.containsKey(HertzConstants.FILE_REFERENCE)) {
				ImageInfoBean imageInfo = new ImageInfoBean(heroImageResource);
				heroImageBean.setSources(imageInfo.getSources());
			}
		}
    }

	@Override
	public Object exportAsMap(ResourceResolver resolver, Resource configPageResource)
			throws JSONException, RepositoryException {
	
		return null;
	}

}
