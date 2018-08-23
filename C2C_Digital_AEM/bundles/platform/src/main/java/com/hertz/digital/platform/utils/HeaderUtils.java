package com.hertz.digital.platform.utils;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hertz.digital.platform.bean.ChatBean;
import com.hertz.digital.platform.bean.FlyoutBean;
import com.hertz.digital.platform.bean.HeaderBean;
import com.hertz.digital.platform.bean.LoginItemsBean;
import com.hertz.digital.platform.bean.LogoItemsBean;
import com.hertz.digital.platform.bean.LogoutBean;
import com.hertz.digital.platform.bean.MenuItemBean;
import com.hertz.digital.platform.bean.SearchItemsBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.service.api.MenuBuilderService;

public final class HeaderUtils {
    /**
     * Default Constructor
     */
    private HeaderUtils() {
        // To prevent instantiation
    }

    /**
     * LOGGER instantiation.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(HeaderUtils.class);

    /**
     * SEARCH_ICON_ALT_TEXT static field whose value is:
     * {@value #SEARCH_ICON_ALT_TEXT}
     */
    private static final String SEARCH_ICON_ALT_TEXT = "searchIconAltTxt";

    /**
     * LOGO_IMAGE_ALT_TEXT static field whose value is:
     * {@value #LOGO_IMAGE_ALT_TEXT}
     */
    private static final String LOGO_IMAGE_ALT_TEXT = "logoImagealtText";
    /**
     * LOGIN_BTN_TEXT static field whose value is: {@value #LOGIN_BTN_TEXT}
     */
    private static final String LOGIN_BTN_TEXT = "loginBtnTxt";
    /**
     * LOGIN_PLA_TEXT static field whose value is: {@value #LOGIN_PLA_TEXT}
     */
    private static final String LOGIN_PLA_TEXT = "loginPlaTxt";
    /**
     * LOGIN_PLA_TEXT static field whose value is: {@value #LOGIN_WELCOME_TEXT}
     */
    private static final String LOGIN_WELCOME_TEXT = "loginWelcomeTxt";
    /**
     * LOGOUT_BTN_TEXT static field whose value is: {@value #LOGOUT_BTN_TEXT}
     */
    private static final String LOGOUT_BTN_TEXT = "logoutButtonText";
    /**
     * LOGOUT_URL static field whose value is: {@value #LOGOUT_URL}
     */
    private static final String LOGOUT_URL = "logoutURL";

    /**
     * CHAT_ALT_TEXT static field whose value is: {@value #CHAT_ALT_TEXT}
     */
    private static final String CHAT_ALT_TEXT = "chatAltText";
    /**
     * LINKURL static field whose value is: {@value #LINKURL}
     */
    private static final String LINKURL = "linkurl";
    /**
     * HEADING static field whose value is: {@value #HEADING}
     */
    private static final String HEADING = "heading";
    /**
     * LINKS static field whose value is: {@value #LINKS}
     */
    private static final String LINKS = "links";

    /**
     * This method is used to get the List of HeaderBean that contains all the
     * properties of header for the json
     * 
     * @param path
     * @param response
     * @param resolver
     * @param menuBuilderService
     * @param builder
     * @param session
     * @return
     */
    public static List<HeaderBean> getOutputJSON(String path,
            SlingHttpServletResponse response, ResourceResolver resolver,
            MenuBuilderService menuBuilderService) {
        List<HeaderBean> headerBeanList = new ArrayList<HeaderBean>();
        String partnerPath = StringUtils.EMPTY;
        LOGGER.debug("Entering method getOutputJSON of HeaderUtils class");
        try {
            if (StringUtils.isNotEmpty(path)) {
                if (path.startsWith(HertzConstants.CONTENT)) {
                    partnerPath = path;
                } else {
                    partnerPath = HertzConstants.CONTENT + path;
                }
                LOGGER.debug("path-" + path);
                Resource partnerPageResource = resolver.getResource(partnerPath);
                String topnavPathPropertyValue = getTopnavPathValue(partnerPath,
                        resolver);
                String topNavPagePath = partnerPath + topnavPathPropertyValue;
                LOGGER.debug("topNavPagepath-" + topNavPagePath);
                List<MenuItemBean> menuItemList = menuBuilderService
                        .getMenuJSON(resolver.getResource(topNavPagePath));
                /* HeaderBean headerBean = */getHeaderJSON(headerBeanList,
                		partnerPageResource, resolver, menuItemList);
                // headerBeanList.add(headerBean);
            }
        } catch (RepositoryException | JSONException e) {
            LOGGER.error("Could not get JSON", e);
            response.setStatus(
                    SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        LOGGER.debug("Exiting method getOutputJSON of HeaderUtils class");
        return headerBeanList;
    }

    /**
     * This method is used to get the TopNavPathValue
     * 
     * @param path
     * @param resolver
     * @return
     * @throws RepositoryException
     */
    private static String getTopnavPathValue(String path,
            ResourceResolver resolver) throws RepositoryException {
        LOGGER.debug("Entering gettopnavPathValue method of HeaderUtils class");
        String pageResourceName = resolver.getResource(path).getName();
        String topNavPathPropertyValue = HertzConstants.SLASH + pageResourceName
                + "/topnav";
        LOGGER.debug("Exiting getTopnavPathValue method of HeaderUtils class");
        return topNavPathPropertyValue;
    }

    /**
     * This method gets a URL path as an input and return a String that is JSON.
     * JSON consists of the Menu Links and meta-data.
     * 
     * @param urlPath
     * @param resolver
     * @param builder
     * @param menuItems
     * @param session
     * @return
     * @throws RepositoryException
     * @throws JSONException
     */
    private static void getHeaderJSON(List<HeaderBean> headerBeanList,
            Resource pageResource, ResourceResolver resolver, List<MenuItemBean> menuItems)
            throws RepositoryException, JSONException {
        LOGGER.debug(
                "Entering inside getHeaderJSON method of HeaderUtils class");
        HeaderBean headerBean = new HeaderBean();
        // Setting the Footer Container component Properties in the JSON
        if (null != pageResource.getChild(HertzConstants.HEADER)) {
                Resource headerChildResource = pageResource
                        .getChild(HertzConstants.HEADER);
                setHeader(headerBean, headerChildResource, menuItems);
            }
        //if (result.getHits().size() > 0) {
            headerBeanList.add(headerBean);
        //}
        LOGGER.debug("Exiting getHeaderJSON method of HeaderUtils class");
        // return headerBean;
    }

    /**
     * This method is used all the properties in the headerBean
     * 
     * @param headerBean
     * @param headerChildResource
     * @param menuItems
     * @throws JSONException
     * @throws RepositoryException
     */
    public static void setHeader(HeaderBean headerBean,
            Resource headerChildResource, List<MenuItemBean> menuItems)
            throws JSONException, RepositoryException {
        LOGGER.debug("Entering method setHeader of HeaderUtils class");
        if (null != headerChildResource) {
            SearchItemsBean searchItems = new SearchItemsBean();
            LogoItemsBean logoItems = new LogoItemsBean();
            LoginItemsBean loginItems = new LoginItemsBean();
            LogoutBean logoutBean = new LogoutBean();
            ChatBean chatBean = new ChatBean();

            List<FlyoutBean> flyoutList = new ArrayList<>();

            setValuesSearchItems(headerChildResource, searchItems);
            setValuesLogoItems(headerChildResource, logoItems);
            setValuesLoginItems(headerChildResource, loginItems);
            setValuesChatItems(headerChildResource, chatBean);
            setValuesLogoutItems(headerChildResource, logoutBean);
            flyoutList = setValuesFlyoutItems(headerChildResource);

            headerBean.setMenus(menuItems);
            headerBean.setsearchItems(searchItems);
            headerBean.setLogoItems(logoItems);
            headerBean.setLoginItems(loginItems);
            headerBean.setChat(chatBean);
            headerBean.setLogout(logoutBean);
            headerBean.setFlyout(flyoutList);

        }
        LOGGER.debug("Exiting method setHeader of HeaderUtils class");
    }

    /**
     * This method is used to set all the searchItems in the bean
     * 
     * @param headerChildResource
     * @param searchItemsBean
     */
    private static void setValuesSearchItems(Resource headerChildResource,
            SearchItemsBean searchItemsBean) {
        LOGGER.debug(
                "Entering method setValuesSearchItems of HeaderUtils class");
        if (null != headerChildResource
                .getChild(HertzConstants.HEADER_SEARCH)) {
            setSearchItems(searchItemsBean, headerChildResource);
        }
        LOGGER.debug(
                "Exiting method setValuesSearchItems of HeaderUtils class");
    }

    /**
     * This method is called from setValuesSearchItems to set the properties in
     * beans
     * 
     * @param searchItemsBean
     * @param headerChildResource
     */
    private static void setSearchItems(SearchItemsBean searchItemsBean,
            Resource headerChildResource) {
        LOGGER.debug("Entering method setSearchItems of HeaderUtils class");
        if (null != headerChildResource
                .getChild(HertzConstants.HEADER_SEARCH)) {
            Resource headerSearchResource = headerChildResource
                    .getChild(HertzConstants.HEADER_SEARCH);
            ValueMap headerSearchProperties = headerSearchResource
                    .adaptTo(ValueMap.class);
            String searchIconAltTxt = HertzUtils.getValueFromMap(
                    headerSearchProperties, SEARCH_ICON_ALT_TEXT);
            searchItemsBean.setSearchIconAltText(searchIconAltTxt);
        }
        LOGGER.debug("Exiting method setSearchItems of HeaderUtils class");
    }

    /**
     * This method is used to set the Logo component properties in the beans
     * 
     * @param headerChildResource
     * @param logoItemsBean
     */
    private static void setValuesLogoItems(Resource headerChildResource,
            LogoItemsBean logoItemsBean) {
        LOGGER.debug("Entering method setValuesLogoItems of HeaderUtils class");

        if (null != headerChildResource.getChild(HertzConstants.LOGO)) {
            setLogoItems(logoItemsBean, headerChildResource);
        }
        LOGGER.debug("Exiting method setValuesLogoItems of HeaderUtils class");
    }

    /**
     * This method is called from setValuesLogoItems and is used to set the
     * properties in the beans
     * 
     * @param logoItemsBean
     * @param headerChildResource
     */
    private static void setLogoItems(LogoItemsBean logoItemsBean,
            Resource headerChildResource) {
        LOGGER.debug("Entering method setLogoItems of HeaderUtils class");
        if (null != headerChildResource.getChild(HertzConstants.LOGO)) {
            Resource logoResource = headerChildResource
                    .getChild(HertzConstants.LOGO);
            ValueMap logoProperties = logoResource.adaptTo(ValueMap.class);
            String logoImageAltText = HertzUtils.getValueFromMap(logoProperties,
                    LOGO_IMAGE_ALT_TEXT);
            logoItemsBean.setLogoImagealtText(logoImageAltText);
        }
        LOGGER.debug("Exiting method setLogoItems od HeaderUtils class");
    }

    /**
     * This method is used to set the properties of login component in the bean
     * 
     * @param headerChildResource
     * @param loginItemsBean
     * @throws RepositoryException
     * @throws JSONException
     */
    private static void setValuesLoginItems(Resource headerChildResource,
            LoginItemsBean loginItemsBean)
            throws RepositoryException, JSONException {
        if (null != headerChildResource.getChild(HertzConstants.LOGIN)) {
            LOGGER.debug(
                    "Entering method setValuesLoginItems of HeaderUtils class");
            setLoginItems(loginItemsBean, headerChildResource);
        }
        LOGGER.debug("Exiting method setValuesLoginItems of HeaderUtils class");
    }

    /**
     * This method is used to set the properties of logout component in the bean
     * 
     * @param headerChildResource
     * @param logoutBean
     * @throws RepositoryException
     * @throws JSONException
     */
    private static void setValuesLogoutItems(Resource headerChildResource,
            LogoutBean logoutBean) throws RepositoryException, JSONException {
        if (null != headerChildResource.getChild(HertzConstants.LOGOUT)) {
            LOGGER.debug(
                    "Entering method setValuesLogoutItems of HeaderUtils class");
            Resource logoutResource = headerChildResource
                    .getChild(HertzConstants.LOGOUT);
            if (null != logoutResource) {
                ValueMap logoutProperties = logoutResource
                        .adaptTo(ValueMap.class);
                String logoutBtnText = HertzUtils
                        .getValueFromMap(logoutProperties, LOGOUT_BTN_TEXT);
                logoutBean.setLogoutButtonText(logoutBtnText);
                String logoutURL = HertzUtils.shortenIfPath(HertzUtils.getValueFromMap(logoutProperties,
                        LOGOUT_URL));
                logoutBean.setLogoutURL(logoutURL);
            }
        }
        LOGGER.debug(
                "Exiting method setValuesLogoutItems of HeaderUtils class");
    }

    /**
     * This method is called from setValuesLoginItems and is used to set the
     * values in the bean.
     * 
     * @param loginItemsBean
     * @param headerChildResource
     * @throws RepositoryException
     * @throws JSONException
     */
    private static void setLoginItems(LoginItemsBean loginItemsBean,
            Resource headerChildResource)
            throws RepositoryException, JSONException {
        LOGGER.debug("Entering method setLoginItems of HeaderUtils class");
        if (null != headerChildResource.getChild(HertzConstants.LOGIN)) {
            Resource loginResource = headerChildResource
                    .getChild(HertzConstants.LOGIN);
            ValueMap loginProperties = loginResource.adaptTo(ValueMap.class);
            String loginBtnText = HertzUtils.getValueFromMap(loginProperties,
                    LOGIN_BTN_TEXT);
            loginItemsBean.setLoginBtnTxt(loginBtnText);
            String loginPlaText = HertzUtils.getValueFromMap(loginProperties,
                    LOGIN_PLA_TEXT);
            loginItemsBean.setLoginPlaTxt(loginPlaText);
            String loginWelcomeText = HertzUtils
                    .getValueFromMap(loginProperties, LOGIN_WELCOME_TEXT);
            loginItemsBean.setLoginWelcomeText(loginWelcomeText);
        }
        LOGGER.debug("Exiting method setLoginItems of HeaderUtils class");
    }

    /**
     * This method call the method to set all the chat Properties in the
     * ChatBean.
     * 
     * @param headerChildResource
     *            Resource that relates to the Header component
     * @param chatBean
     *            Bean class for the Chat component which will store the chat
     *            properties
     * @throws RepositoryException
     * @throws JSONException
     */
    private static void setValuesChatItems(Resource headerChildResource,
            ChatBean chatBean) throws RepositoryException, JSONException {
        LOGGER.debug("Entering setValuesChatItems in HeaderUtils Class");
        Resource chatResource = headerChildResource
                .getChild(HertzConstants.CHAT_NODE);
        if (null != chatResource) {
            getChatItems(chatResource, chatBean);
        }
        LOGGER.debug("Exiting setValuesChatItems in HeaderUtils Class");
    }

    /**
     * This Method get the properties of the chat component that is inside the
     * Header component.
     * 
     * @param chatResource
     *            Resource that pertain to the Chat component in Header
     * @param chatBean
     *            Bean class for the Chat component which will store the chat
     *            properties
     * @throws RepositoryException
     * @throws JSONException
     */
    private static void getChatItems(Resource chatResource, ChatBean chatBean)
            throws RepositoryException, JSONException {
        LOGGER.debug("Entering getChatItems in HeaderUtils Class");
        ValueMap chatProperties = chatResource.adaptTo(ValueMap.class);
        String chatAltText = HertzUtils.getValueFromMap(chatProperties,
                CHAT_ALT_TEXT);
        chatBean.setChatAltText(chatAltText);
        LOGGER.debug("Exiting getChatItems in HeaderUtils Class");
    }

    /**
     * This method call the method to set all the flyout Properties in the
     * ChatBean.
     * 
     * @param headerChildResource
     *            Resource that relates to the Header component
     * @param flyoutBean
     *            Bean class for the flyout component which will store the
     *            flyout properties
     * @throws RepositoryException
     * @throws JSONException
     */
    private static List<FlyoutBean> setValuesFlyoutItems(
            Resource headerChildResource)
            throws RepositoryException, JSONException {
        LOGGER.debug("Entering setValuesFlyoutItems in HeaderUtils Class");
        Resource flyoutResource = headerChildResource
                .getChild("authenticatedFlyoutComponent");
        List<FlyoutBean> flyoutList = new ArrayList<>();
        if (null != flyoutResource) {
            flyoutList = getFlyoutItems(flyoutResource);
        }

        LOGGER.debug("Exiting setValuesFlyoutItems in HeaderUtils Class");
        return flyoutList;
    }

    /**
     * This Method get the properties of the flyout component that is inside the
     * Header component.
     * 
     * @param chatResource
     *            Resource that pertain to the flyout component in Header
     * @param chatBean
     *            Bean class for the flyout component which will store the
     *            flyout properties
     * @throws RepositoryException
     * @throws JSONException
     */
    private static List<FlyoutBean> getFlyoutItems(Resource flyoutResource)
            throws RepositoryException, JSONException {
        LOGGER.debug("Entering getFlyoutItems in HeaderUtils Class");
        ValueMap flyoutProperties = flyoutResource.adaptTo(ValueMap.class);
        List<FlyoutBean> flyoutList = new ArrayList<>();
        if (flyoutProperties.containsKey(LINKS)) {
            String[] links = flyoutProperties.get(LINKS, String[].class);
            for (String str : links) {
                JSONObject obj = new JSONObject(str);
                FlyoutBean flyoutBean = new FlyoutBean();
                if (StringUtils.isNotEmpty(obj.optString(HEADING))
                        && StringUtils.isNotEmpty(obj.optString(LINKURL))) {
                    flyoutBean.setLinkurl(
                            HertzUtils.shortenIfPath(obj.getString(LINKURL)));
                    flyoutBean.setHeading(obj.getString(HEADING));
                }
                String openUrlInNewWindow = obj
                        .optString(HertzConstants.OPEN_URL_NEW_WINDOW);
                if (openUrlInNewWindow.equalsIgnoreCase("no")) {
                    flyoutBean.setOpenLegalLinkURLInNewWindow(false);
                } else if (openUrlInNewWindow.equalsIgnoreCase("yes")) {
                    flyoutBean.setOpenLegalLinkURLInNewWindow(true);
                }
                String seoNoFollow = obj.optString(HertzConstants.SEO_NOFOLLOW);
                if (seoNoFollow.equalsIgnoreCase("no")) {
                    flyoutBean.setSeoNoFollow(false);
                } else if (seoNoFollow.equalsIgnoreCase("yes")) {
                    flyoutBean.setSeoNoFollow(true);
                }
                flyoutList.add(flyoutBean);
            }
            LOGGER.debug("Exiting getFlyoutItems in HeaderUtils Class");
        }
        return flyoutList;
    }

    /**
     * 
     * @param headerBeanList
     * @param hit
     * @param resolver
     * @param helper
     * @throws JSONException
     * @throws RepositoryException
     * @throws LoginException
     */
    public static void getHeaderContent(List<HeaderBean> headerBeanList,
            Resource headerContentResource, ResourceResolver resolver, SlingScriptHelper helper)
            throws JSONException, RepositoryException {
        String topNavPagePath = StringUtils.EMPTY;
        HeaderBean headerBean = new HeaderBean();
        ValueMap headerResProperties = headerContentResource.getValueMap();
        if (headerResProperties.containsKey(HertzConstants.HEADER_REFERENCE)) {
            Resource referenceHeaderResource = resolver
                    .getResource(headerResProperties
                            .get(HertzConstants.HEADER_REFERENCE).toString());
            topNavPagePath = headerResProperties
                    .get(HertzConstants.HEADER_REFERENCE).toString()
                    + HertzConstants.SLASH_TOPNAV;
            Resource jcrContentResource = referenceHeaderResource
                    .getChild(HertzConstants.JCR_CONTENT);
            setHeaderBeanList(resolver, topNavPagePath, jcrContentResource,
                    referenceHeaderResource, headerBean, headerBeanList,
                    helper);
        } else {
            Resource parentHeaderResource = headerContentResource.getParent();
            String parentHeaderPagePath = parentHeaderResource.getPath();
            topNavPagePath = parentHeaderPagePath + HertzConstants.SLASH_TOPNAV;
            setHeaderBeanList(resolver, topNavPagePath, headerContentResource,
                    headerContentResource.getParent(), headerBean,
                    headerBeanList, helper);
        }
    }

    /**
     * 
     * @param headerResource
     * @param childHeaderResource
     * @param headerBean
     * @param headerBeanList
     * @param helper
     * @throws JSONException
     * @throws RepositoryException
     */
    private static void setHeaderBeanList(ResourceResolver resolver,
            String topNavPagePath, Resource contentResource,
            Resource headerResource, HeaderBean headerBean,
            List<HeaderBean> headerBeanList, SlingScriptHelper helper)
            throws JSONException, RepositoryException {
        Resource childHeaderResource = null;
        if(null!=contentResource){
        	 if (null != contentResource.getChild(HertzConstants.HEADER)) {
                 childHeaderResource = contentResource
                         .getChild(HertzConstants.HEADER);
                 List<MenuItemBean> menuItemList = helper
                         .getService(MenuBuilderService.class)
                         .getMenuJSON(resolver.getResource(topNavPagePath));
                 HeaderUtils.setHeader(headerBean, childHeaderResource,
                         menuItemList);
                 headerBeanList.add(headerBean);
             }
        }
       
    }
}
