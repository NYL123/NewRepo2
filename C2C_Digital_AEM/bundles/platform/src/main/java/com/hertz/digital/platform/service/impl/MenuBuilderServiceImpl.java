package com.hertz.digital.platform.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.hertz.digital.platform.bean.HeadingBean;
import com.hertz.digital.platform.bean.MenuItemBean;
import com.hertz.digital.platform.bean.SubMenuItemBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.service.api.MenuBuilderService;

/**
 * Menu Builder Service will set up the Menu Object Iterate over Pages that are
 * Menu Pages and Menu Sub Pages, Fetch the navigationlURL Properties and
 * MenuMetadata properties.
 * 
 * @author puneet.soni
 * 
 */
@Component(metatype = false, label = "Hertz - Hertz Menu Builder Service",
        description = "Hertz - Service to Build a Menu")
@Service
public class MenuBuilderServiceImpl implements MenuBuilderService {
    /**
     * Default Constructor
     */
    public MenuBuilderServiceImpl() {
        super();
    }

    /**
     * Logger Instantiation
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MenuBuilderServiceImpl.class);
    /**
     * MENU_METADATA static field whose value is: {@value #MENU_METADATA}
     */
    private static final String MENU_METADATA = "menuMetadata";
    /**
     * DISPLAY_NATIVE static field whose value is: {@value #DISPLAY_NATIVE}
     */
    private static final String DISPLAY_NATIVE = "display-native";
    /**
     * DISPLAY_MOBILE static field whose value is: {@value #DISPLAY_MOBILE}
     */
    private static final String DISPLAY_MOBILE = "display-mobile";
    /**
     * DISPLAY_TABLET static field whose value is: {@value #DISPLAY_TABLET}
     */
    private static final String DISPLAY_TABLET = "display-tablet";
    /**
     * DISPLAY_DESKTOP static field whose value is: {@value #DISPLAY_DESKTOP}
     */
    private static final String DISPLAY_DESKTOP = "display-desktop";
    /**
     * MENU_TEMPLATE static field whose value is: {@value #MENU_TEMPLATE}
     */
    private static final String MENU_TEMPLATE = "/apps/hertz/templates/menu";
    /**
     * NAVIGATIONAL_URL static field whose value is: {@value #NAVIGATIONAL_URL}
     */
    private static final String NAVIGATIONAL_URL = "navigationurl";

    /**
     * OPEN_NAVIGATION_URL_NEW_WINDOW static field whose value is:
     * {@value #OPEN_NAVIGATION_URL_NEW_WINDOW}
     */
    private static final String OPEN_URL_NEW_WINDOW = "openUrlNewWindow";

    // private ResourceResolver resolver;

    /**
     * This method gets a URL path as an input and return list of MenuItems.
     * that consists of the Menu Links and meta-data. It iterates and collect
     * property values from Menu Pages and Menu SubPages
     */
    @Override
    public List<MenuItemBean> getMenuJSON(Resource resource)
            throws RepositoryException {
        LOGGER.debug(
                "Entering inside getMenuJSON method of MenuBuilderServiceImpl");
        List<MenuItemBean> menuItemList = null;
        try {
            // Iterate over Pages that are Menu Pages and Menu Sub Pages
            menuItemList = iteratePages(resource);

        } catch (RepositoryException e) {
            LOGGER.error("Could not get JSON: {} : {}", e.getMessage(), e);
            throw e;
        } /*
           * finally { HertzUtils.closeResolverSession(resolver, null); }
           */
        LOGGER.debug("Exiting getMenuJSON method of MenuBuilderServiceImpl");
        return menuItemList;
    }

    /**
     * Iterate Menu Pages and Menu SubPages. Fetch the MenuMetadata Properties
     * and URL.
     * 
     * @param topNavPagePath
     *            Absolute path of the Topnav Page
     * @return List<MenuItemBean> that contains the properties of all the Menu
     *         Pages
     * @throws RepositoryException
     */
    private List<MenuItemBean> iteratePages(Resource topNavResource)
            throws RepositoryException {
        LOGGER.debug(
                "Entering inside iteratePages method of MenuBuilderServiceImpl");
        List<MenuItemBean> menuItemList = new ArrayList<MenuItemBean>();
        if (topNavResource == null) {
            return menuItemList;
        }
        Page page = topNavResource.adaptTo(Page.class);
        // Search for the Menu Pages
        Iterator<Page> pageIterator = getPageIterator(page);
        while (pageIterator.hasNext()) {
            Page childPage = (Page) pageIterator.next();
            MenuItemBean menuItem = new MenuItemBean();
            HeadingBean heading = new HeadingBean();

            heading.setTitle(childPage.getTitle());

            Node childNode = childPage.adaptTo(Node.class);
            Node childjcrNode = childNode.getNode(HertzConstants.JCR_CONTENT);
            // Get the NavigationalURL
            if (childjcrNode.hasProperty(NAVIGATIONAL_URL)) {
                Property navigationalURLProperty = childjcrNode
                        .getProperty(NAVIGATIONAL_URL);
                heading.setUrl(navigationalURLProperty.getString());
            }
            // Get navigation URL target window
            if (childjcrNode.hasProperty(OPEN_URL_NEW_WINDOW)) {
                Property openUrlNewWindowProperty = childjcrNode
                        .getProperty(OPEN_URL_NEW_WINDOW);
                heading.setOpenUrlNewWindow(
                        openUrlNewWindowProperty.getBoolean());
            }
            // SEO NOFOLLOW
            Boolean seoNoFollow = false;
            if (childjcrNode.hasProperty(HertzConstants.SEO_NOFOLLOW)) {
                Property seoNoFollowProp = childjcrNode
                        .getProperty(HertzConstants.SEO_NOFOLLOW);
                seoNoFollow = seoNoFollowProp.getBoolean();
            }
            heading.setSeoNoFollow(seoNoFollow);
            // Get all the metadata
            getAllMetaData(childjcrNode, heading);
           
            searchSubPages(childPage, heading);
            menuItem.setHeading(heading);
            menuItemList.add(menuItem);
        }
        LOGGER.debug("Exiting iteratePages method of MenuBuilderServiceImpl");
        return menuItemList;
    }

    private void getAllMetaData(Node childjcrNode, HeadingBean heading) {
		// TODO Auto-generated method stub
    	 try {
    		 if (childjcrNode.hasProperty(MENU_METADATA)) {
             

            
            	 Property metadataProperty = childjcrNode
                         .getProperty(MENU_METADATA);
				if (metadataProperty.isMultiple()) {
				     setMultipleMetadataProperties(metadataProperty.getValues(),
				             heading);
				 }
				if (!metadataProperty.isMultiple()) {
	                 setSingleMetadataProperties(metadataProperty.getValue(),
	                         heading);
	             }
             }
			} catch (ValueFormatException e) {
				// TODO Auto-generated catch block
				
	            
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				LOGGER.error("RepositoryException: {} : {}", e.getMessage(), e);
			}
             
         }
	

	/**
     * 
     * @param metadataValue
     *            Values of Property menuMetadata
     * @param heading
     *            Object of Heading Bean
     */
    private void setSingleMetadataProperties(Value metadataValue,
            HeadingBean heading) {
        LOGGER.debug(
                "Inside setSingleMetadataProperties method of MenuBuilderServiceImpl");
        if (metadataValue.toString().equals(DISPLAY_NATIVE)) {
            heading.setDisplayNative(true);
        }
        if (metadataValue.toString().equals(DISPLAY_MOBILE)) {
            heading.setDisplayMobile(true);
        }
        if (metadataValue.toString().equals(DISPLAY_TABLET)) {
            heading.setDisplayTablet(true);
        }
        if (metadataValue.toString().equals(DISPLAY_DESKTOP)) {
            heading.setDisplayDesktop(true);
        }
        LOGGER.debug(
                "Inside setSingleMetadataProperties method of MenuBuilderServiceImpl");
    }

    /**
     * 
     * @param metadatavalues
     *            Values of Property menuMetadata
     * @param heading
     *            Object of Heading Bean
     */
    private void setMultipleMetadataProperties(Value[] metadatavalues,
            HeadingBean heading) {
        LOGGER.debug(
                "Inside setMultipleMetadataProperties method of MenuBuilderServiceImpl");
        for (Value value : metadatavalues) {
            if (value.toString().equals(DISPLAY_NATIVE)) {
                heading.setDisplayNative(true);
            }
            if (value.toString().equals(DISPLAY_MOBILE)) {
                heading.setDisplayMobile(true);
            }
            if (value.toString().equals(DISPLAY_TABLET)) {
                heading.setDisplayTablet(true);
            }
            if (value.toString().equals(DISPLAY_DESKTOP)) {
                heading.setDisplayDesktop(true);
            }
        }
        LOGGER.debug(
                "Exiting setMultipleMetadataProperties method of MenuBuilderServiceImpl");
    }

    /**
     * Method to search the Sub Pages in Menu and set the MenuMetadata
     * Properties
     * 
     * @param childPage
     *            Page Object for Sub Menu Pages
     * @param heading
     *            Object of Heading Bean
     * @throws PathNotFoundException
     * @throws RepositoryException
     */
    private void searchSubPages(Page childPage, HeadingBean heading)
            throws PathNotFoundException, RepositoryException {
        LOGGER.debug("Inside searchSubPages method of MenuBuilderServiceImpl");
        // search for the pages that have navigation level 2
        Iterator<Page> subpageIterator = getPageIterator(childPage);

        while (subpageIterator.hasNext()) {
            SubMenuItemBean subItem = new SubMenuItemBean();
            Page subPage = (Page) subpageIterator.next();
            Node subChildNode = subPage.adaptTo(Node.class);
            Node subChildJCRNode = subChildNode
                    .getNode(HertzConstants.JCR_CONTENT);

            subItem.setTitle(subPage.getTitle());
            // Get the NavigationalURL
            if (subChildJCRNode.hasProperty(NAVIGATIONAL_URL)) {
                Property navigationalURLProperty = subChildJCRNode
                        .getProperty(NAVIGATIONAL_URL);
                subItem.setUrl(navigationalURLProperty.getString());
            }

            // Get NavigationalURL target window
            if (subChildJCRNode.hasProperty(OPEN_URL_NEW_WINDOW)) {
                Property openUrlNewWindowProperty = subChildJCRNode
                        .getProperty(OPEN_URL_NEW_WINDOW);
                subItem.setOpenUrlNewWindow(
                        openUrlNewWindowProperty.getBoolean());
            }

            Boolean seoNoFollow = false;
            if (subChildJCRNode.hasProperty(HertzConstants.SEO_NOFOLLOW)) {
                Property seoNoFollowProp = subChildJCRNode
                        .getProperty(HertzConstants.SEO_NOFOLLOW);
                seoNoFollow = seoNoFollowProp.getBoolean();
            }
            subItem.setSeoNoFollow(seoNoFollow);

            // Get MenuMetadata
            if (subChildJCRNode.hasProperty(MENU_METADATA)) {
                Property subMetadataProperty = subChildJCRNode
                        .getProperty(MENU_METADATA);

                if (subMetadataProperty.isMultiple()) {
                    setMultipleMetadataPropertiesForSubPages(
                            subMetadataProperty.getValues(), subItem);
                }
                if (!subMetadataProperty.isMultiple()) {
                    setSingleMetadataPropertiesForSubPages(
                            subMetadataProperty.getValue(), subItem);
                }
            }
            heading.setItems(subItem);
        }
        LOGGER.debug("Exiting searchSubPages method of MenuBuilderServiceImpl");
    }

    /**
     * This Method will set the MenuMetaData for the Sub Pages
     * 
     * @param metadataValue
     *            Values of Property menuMetadata
     * @param subItem
     *            Object of the SubMenuItemBean
     */
    private void setSingleMetadataPropertiesForSubPages(Value metadataValue,
            SubMenuItemBean subItem) {
        LOGGER.debug(
                "Inside setSingleMetadataPropertiesForSubPages method of MenuBuilderServiceImpl");
        if (metadataValue.toString().equals(DISPLAY_NATIVE)) {
            subItem.setDisplayNative(true);
        }
        if (metadataValue.toString().equals(DISPLAY_MOBILE)) {
            subItem.setDisplayMobile(true);
        }
        if (metadataValue.toString().equals(DISPLAY_TABLET)) {
            subItem.setDisplayTablet(true);
        }
        if (metadataValue.toString().equals(DISPLAY_DESKTOP)) {
            subItem.setDisplayDesktop(true);
        }
        LOGGER.debug(
                "Exiting setSingleMetadataPropertiesForSubPages method of MenuBuilderServiceImpl");
    }

    /**
     * This Method will set the MenuMetaData for the Sub Pages
     * 
     * @param metadatavalues
     *            Values of Property menuMetadata
     * @param subItem
     *            Object of the SubMenuItemBean
     */
    private void setMultipleMetadataPropertiesForSubPages(
            Value[] metadatavalues, SubMenuItemBean subItem) {
        LOGGER.debug(
                "Inside setMultipleMetadataPropertiesForSubPages method of MenuBuilderServiceImpl");
        for (Value value : metadatavalues) {
            if (value.toString().equals(DISPLAY_NATIVE)) {
                subItem.setDisplayNative(true);
            }
            if (value.toString().equals(DISPLAY_MOBILE)) {
                subItem.setDisplayMobile(true);
            }
            if (value.toString().equals(DISPLAY_TABLET)) {
                subItem.setDisplayTablet(true);
            }
            if (value.toString().equals(DISPLAY_DESKTOP)) {
                subItem.setDisplayDesktop(true);
            }
        }
        LOGGER.debug(
                "Exiting setMultipleMetadataPropertiesForSubPages method of MenuBuilderServiceImpl");
    }

    /**
     * Iterating the children pages based on a filter
     * 
     * @param page
     *            Menu Page
     * @return Iterator<Page> of the Menu Page based on the Page filter
     */
    private Iterator<Page> getPageIterator(Page page) {
        LOGGER.debug("Inside getPageIterator method of MenuBuilderServiceImpl");
        Iterator<Page> children = page.listChildren(getPageFilter());
        LOGGER.debug(
                "Exiting getPageIterator method of MenuBuilderServiceImpl");
        return children;
    }

    /**
     * Page Filter Will filter out the Pages based on the Menu Template
     * 
     * @return PageFilter That return boolean value whether the page meets the
     *         condition
     */
    private PageFilter getPageFilter() {
        LOGGER.debug("Inside getPageFilter method of MenuBuilderServiceImpl");
        PageFilter pageFilter = new PageFilter() {
            public boolean includes(Page page) {
                Boolean value = Boolean.FALSE;
                ValueMap props = page.getProperties();
                String templatePath = props.get(HertzConstants.JCR_CQ_TEMPLATE,
                        String.class);

                if (MENU_TEMPLATE.equals(templatePath)) {
                    value = Boolean.TRUE;
                }
                return value;
            }
        };
        LOGGER.debug("Exiting getPageFilter method of MenuBuilderServiceImpl");
        return pageFilter;
    }
}
