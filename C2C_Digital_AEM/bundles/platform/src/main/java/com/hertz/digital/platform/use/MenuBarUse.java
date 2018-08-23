package com.hertz.digital.platform.use;

import java.util.List;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.resource.LoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.bean.MenuItemBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.service.api.MenuBuilderService;

/**
 * This is a method that calls MenuBuilderService to get List of MenuItem by
 * passing the Path of the component+path of the TopNav Page which we get from
 * the Partner Page property.
 * 
 * @author puneet.soni
 * 
 */
public class MenuBarUse extends WCMUsePojo {
    protected static final Logger logger = LoggerFactory
            .getLogger(MenuBarUse.class);
    private static final String TOP_NAV_PATH = "topnavpath";
    /**
     * Reference of MenuBuilder
     */
    @Reference
    MenuBuilderService menuBuilderService;

    /**
     * Default Constructor
     */
    public MenuBarUse() {
        super();
    }

    /**
     * Collection that contains the Image and Alttext
     */
    private List<MenuItemBean> menuItems;

    /**
     * get the list
     * 
     * @return List of MenuItemBean that contains Properties of Menu Page
     */
    public List<MenuItemBean> getMenuItems() {
        return menuItems;
    }

    /**
     * Setter of the Menu Items
     * 
     * @param menuItems
     *            List of MenuItemBean that contains Properties of Menu Page
     */
    public void setMenuItems(List<MenuItemBean> menuItems) {
        this.menuItems = menuItems;
    }

    /**
     * Activate method of the MenuBar class. It calls the MenuBuilderService and
     * gets the List of MenuItemBean
     * 
     * @throws RepositoryException
     *             Throws RepositoryException
     */
    @Override
    public void activate() throws RepositoryException {
        logger.debug("Entering inside activate method of MenuBar");
        Page partnerPage = null;
        if (null != getCurrentPage().getParent()) {
            partnerPage = getCurrentPage().getParent();
            String topNavPathValue = getTopNavpath(partnerPage);
            String path = partnerPage.getPath();
            // path of the topNav
            String topNavPath = path + topNavPathValue;
            try {
                logger.debug("Path sent to MenuBuilder service is  : {}",
                        topNavPath);
                menuItems = getSlingScriptHelper()
                        .getService(MenuBuilderService.class).getMenuJSON(
                                getResourceResolver().getResource(topNavPath));
            } catch (RepositoryException e) {
                logger.error("Error during administrative login : {} : {} ",
                        e.getMessage(), e);
                throw e;
            }
        }
        logger.debug("Exiting inside activate method of MenuBar");

    }

    /**
     * This Method gets the topnavpath property from the Partner Page.
     * 
     * @param partnerPage
     *            Page Object of the Partner Page
     * @return String that contains the path of the TopNavigation page
     * @throws RepositoryException
     */
    private String getTopNavpath(Page partnerPage) throws RepositoryException {
        logger.debug("Entering getTopNavpath method of MenuBar");
        String topNavPathValue = StringUtils.EMPTY;
        Node partnerNode = partnerPage.adaptTo(Node.class);
        Node partnerJCRNode = null;
        if (null != partnerNode.getNode(HertzConstants.JCR_CONTENT)) {
            partnerJCRNode = partnerNode.getNode(HertzConstants.JCR_CONTENT);
            if (partnerJCRNode.hasProperty(TOP_NAV_PATH)) {
                Property topNavPathProperty = partnerJCRNode
                        .getProperty(TOP_NAV_PATH);
                topNavPathValue = topNavPathProperty.getString();
                logger.debug("TopNavPath Value: {}",
                        topNavPathProperty.getString());
            }
        }
        logger.debug("Exiting getTopNavpath method of MenuBar");
        return topNavPathValue;
    }
}
