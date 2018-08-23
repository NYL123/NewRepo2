/**
 * 
 */
package com.hertz.digital.platform.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.oak.commons.PropertiesUtil;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.commons.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.hertz.digital.platform.bean.FooterColumnBean;
import com.hertz.digital.platform.bean.FooterContainerBean;
import com.hertz.digital.platform.bean.FooterDetailBean;
import com.hertz.digital.platform.bean.FooterLinkBean;
import com.hertz.digital.platform.bean.ImageInfoBean;
import com.hertz.digital.platform.bean.LegalLinksBean;
import com.hertz.digital.platform.bean.SignUpForEmailBean;
import com.hertz.digital.platform.bean.SocialLinkBean;
import com.hertz.digital.platform.bean.SocialLinksContentBean;
import com.hertz.digital.platform.constants.HertzConstants;

/**
 * @author a.dhingra
 *
 */
public final class FooterUtils {

    /**
     * Default constructor declaration
     */
    private FooterUtils() {
        // To Prevent instantiation
    }

    /**
     * Instantiating Logger
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(FooterUtils.class);

    /**
     * This method gets a URL path as an input and return a String that is JSON.
     * JSON consists of the Footer Content.
     * 
     * @param path
     * @param resolver
     * @param session
     * @param builder
     * @return
     * @throws LoginException
     * @throws RepositoryException
     * @throws JSONException
     */
    public static List<FooterContainerBean> getFooterJSON(String path, ResourceResolver resolver)
            throws LoginException, RepositoryException, JSONException {
        LOGGER.debug("Entering inside getMenuJSON method of FooterUtils");
        
        List<FooterContainerBean> footerContainerBeanList = new ArrayList<
                FooterContainerBean>();
        Resource partnerPageResource = resolver.getResource(path);
            // Setting the Footer Container component Properties in the JSON
        setValuesInFooter(footerContainerBeanList, partnerPageResource);
        LOGGER.debug("Exiting getMenuJSON method of FooterUtils");
        return footerContainerBeanList;
    }

    /**
     * This method is used to set the values in the footer bean
     * 
     * @param resolver
     * @param footerContainerBeanList
     * @param hit
     * 
     * @throws RepositoryException
     * @throws JSONException
     */
    private static void setValuesInFooter(
            List<FooterContainerBean> footerContainerBeanList, Resource partnerPageResource)
            throws RepositoryException, JSONException {
        LOGGER.debug("Entering inside setValuesInFooter method of Fo");
        FooterContainerBean footerContainerBean = new FooterContainerBean();
        if (null != partnerPageResource) {
            if (null != partnerPageResource.getChild(HertzConstants.FOOTER)) {
                Resource footerChildResource = partnerPageResource
                        .getChild(HertzConstants.FOOTER);
                getFooter(footerContainerBean, footerChildResource);
                footerContainerBeanList.add(footerContainerBean);
            }
        }
        LOGGER.debug("Exiting setValuesInFooter method of FooterUtils");
    }

    /**
     * This method sets the values in footerContainerBean for getting all the
     * values for the footer component
     * 
     * @param footerContainerBean
     * @param footerChildResource
     * @param resolver
     * @return void
     * 
     * @throws JSONException
     */
    public static void getFooter(FooterContainerBean footerContainerBean,
            Resource footerChildResource) throws JSONException {
        LOGGER.debug("Entering inside getFooter method of FooterUtils");
        List<SocialLinksContentBean> socialLinksContentBeanList = new ArrayList<
                SocialLinksContentBean>();
        List<SignUpForEmailBean> signUpForEmailBeanList = new ArrayList<
                SignUpForEmailBean>();
        List<FooterColumnBean> footerLinkBeanList = new ArrayList<
                FooterColumnBean>();
        ValueMap footerContainerProperties = footerChildResource
                .adaptTo(ValueMap.class);
        setFooterComponentProperties(footerContainerBean,
                footerContainerProperties);
        setValuesSignUpForEmailBean(footerChildResource,
                signUpForEmailBeanList);
        setValuesSocialLinkContent(footerChildResource,
                socialLinksContentBeanList);
        setValuesFooterLinks(footerChildResource, footerLinkBeanList);
        
        String fileReferencePath = footerContainerProperties.get(HertzConstants.FILE_REFERENCE, String.class);
        if (StringUtils.isNotBlank(fileReferencePath)) {
        	ImageInfoBean imageInfo = new ImageInfoBean(footerChildResource);
            footerContainerBean.setSources(imageInfo.getSources());
        }
        footerContainerBean.setSignUpForEmail(signUpForEmailBeanList);
        footerContainerBean.setSocialLinks(socialLinksContentBeanList);
        footerContainerBean.setFooterlinks(footerLinkBeanList);
        LOGGER.debug("Exiting getFooter method of FooterUtils");
    }

    /**
     * This method gets all the property values set for the footer links
     * component
     * 
     * @param footerChildResource
     * @param footerLinkBeanList
     * @return void
     *
     */
    private static void setValuesFooterLinks(Resource footerChildResource,
            List<FooterColumnBean> footerLinkBeanList) throws JSONException {
        LOGGER.debug(
                "Entering inside setValuesFooterLinks method of FooterUtils");
        // Setting the Footer Link Component content
        if (null != footerChildResource.getChild(HertzConstants.FOOTER_LINKS)
                && !ResourceUtil.isNonExistingResource(footerChildResource
                        .getChild(HertzConstants.FOOTER_LINKS))) {
            setFooterLinks(footerLinkBeanList, footerChildResource);

        }
        LOGGER.debug("Exiting setValuesFooterLinks method of FooterUtils");
    }

    /**
     * This method gets all the values of the properties set in the social links
     * component
     * 
     * @param footerChildResource
     * @param resolver
     * @param socialLinksContentBeanList
     * 
     * @return void
     * 
     */
    private static void setValuesSocialLinkContent(Resource footerChildResource,
            List<SocialLinksContentBean> socialLinksContentBeanList) {
        LOGGER.debug(
                "Entering inside setValuesSocialLinkContent method of FooterUtils");
        // Setting the Social Link Component properties
        SocialLinksContentBean socialLinksContentBean = new SocialLinksContentBean();
        if (null != footerChildResource.getChild(HertzConstants.SOCIAL_LINKS)
                && !ResourceUtil.isNonExistingResource(footerChildResource
                        .getChild(HertzConstants.SOCIAL_LINKS))) {
            setSocialLinks(socialLinksContentBean, socialLinksContentBeanList,
                    footerChildResource);
        }
        LOGGER.debug(
                "Exiting setValuesSocialLinkContent method of FooterUtils");
    }

    /**
     * This method gets all the values of the properties set in the sign up for
     * email component
     * 
     * @param footerChildResource
     * @param signUpForEmailBeanList
     * @return void
     *
     * 
     */
    private static void setValuesSignUpForEmailBean(
            Resource footerChildResource,
            List<SignUpForEmailBean> signUpForEmailBeanList) {
        LOGGER.debug(
                "Entering inside setValuesSocialLinkContent method of FooterUtils");
        SignUpForEmailBean signUpForEmailBean = new SignUpForEmailBean();
        if (null != footerChildResource.getChild(HertzConstants.EMAIL_SIGNUP)
                && !ResourceUtil.isNonExistingResource(footerChildResource
                        .getChild(HertzConstants.EMAIL_SIGNUP))) {
            setSignupForEmail(signUpForEmailBean, signUpForEmailBeanList,
                    footerChildResource);
        }
        LOGGER.debug(
                "Exiting setValuesSignUpForEmailBean method of FooterUtils");
    }

    /**
     * This method gets all the values of the properties set in the footer
     * component
     * 
     * @param footerContainerBean
     * @param footerContainerProperties
     * @return void
     * 
     */
    private static void setFooterComponentProperties(
            FooterContainerBean footerContainerBean,
            ValueMap footerContainerProperties) {
        LOGGER.debug(
                "Entering inside setFooterComponentProperties method of FooterUtils");
        String legalDescriptionText = HertzUtils.getValueFromMap(
                footerContainerProperties,
                HertzConstants.LEGAL_DESRIPTION_TEXT);
        footerContainerBean.setLegalDescriptionText(legalDescriptionText);
        List<LegalLinksBean> legalLinks = new ArrayList<LegalLinksBean>();
        setLegalLinks(footerContainerProperties, legalLinks);
        footerContainerBean.setLegalLinks(legalLinks);
        LOGGER.debug(
                "Exiting setFooterComponentProperties method of FooterUtils");
    }

    /**
     * This method gets all the legal links related fields and adds them to a
     * list
     * 
     * @param footerContainerProperties
     * @param legalLinks
     */
    private static void setLegalLinks(ValueMap footerContainerProperties,
            List<LegalLinksBean> legalLinks) {
        LOGGER.debug("Entering inside setLegalLinks method of FooterUtils");
        LegalLinksBean legalLinksBean = new LegalLinksBean();
        String legalLinkLabelFirst = HertzUtils.getValueFromMap(
                footerContainerProperties, HertzConstants.LEGAL_LINK_LABEL_1);
        legalLinksBean.setLegalLinkLabel(legalLinkLabelFirst);
        String legalLinkURLFirst = HertzUtils.shortenIfPath(HertzUtils.getValueFromMap(
                footerContainerProperties, HertzConstants.LEGAL_LINK_URL_1));
        legalLinksBean
                .setLegalLinkURL(HertzUtils.shortenIfPath(legalLinkURLFirst));
        Boolean openNewWindowFirst = HertzUtils.getBooleanValueFromMap(
                footerContainerProperties,
                HertzConstants.OPEN_LEGAL_NEW_WINDOW_1);
        legalLinksBean.setOpenLegalLinkURLInNewWindow(openNewWindowFirst);
        legalLinksBean.setSeoNoFollow(HertzUtils.getBooleanValueFromMap(
                footerContainerProperties, HertzConstants.SEO_NOFOLLOW));
        legalLinks.add(legalLinksBean);
        LegalLinksBean legalLinksBeanSecond = new LegalLinksBean();
        String legalLinksLabelSecond = HertzUtils.getValueFromMap(
                footerContainerProperties, HertzConstants.LEGAL_LINK_LABEL_2);
        legalLinksBeanSecond.setLegalLinkLabel(legalLinksLabelSecond);
        String legalLinksURLSecond =  HertzUtils.shortenIfPath(HertzUtils.getValueFromMap(
                footerContainerProperties, HertzConstants.LEGAL_LINK_URL_2));
        legalLinksBeanSecond
                .setLegalLinkURL(HertzUtils.shortenIfPath(legalLinksURLSecond));
        Boolean openNewWindowSecond = HertzUtils.getBooleanValueFromMap(
                footerContainerProperties,
                HertzConstants.OPEN_LEGAL_NEW_WINDOW_2);
        legalLinksBeanSecond
                .setOpenLegalLinkURLInNewWindow(openNewWindowSecond);
        legalLinksBeanSecond.setSeoNoFollow(HertzUtils
                .getBooleanValueFromMap(footerContainerProperties, "rel2"));
        legalLinks.add(legalLinksBeanSecond);
        LOGGER.debug("Exiting setLegalLinks method of FooterUtils");
    }

    /**
     * This method is called from the setValuesFooterLinks method to fetch all
     * the properties set in the Footer Links component
     * 
     * @param footerLinkBeanList
     * @param footerChildResource
     * @return void
     * 
     * @throws JSONException
     */
    private static void setFooterLinks(
            List<FooterColumnBean> footerLinkBeanList,
            Resource footerChildResource) throws JSONException {
        LOGGER.debug("Entering inside getFooterLinks method of FooterUtils");
        FooterLinkBean footerLinkBean;
        Resource footerLinkChildResource = footerChildResource
                .getChild(HertzConstants.FOOTER_LINKS);

        Iterable<Resource> iterableComponents = footerLinkChildResource
                .getChildren();

        for (Resource columnContainerResource : iterableComponents) {
            // Resource footerHeadingsResource =
            // columnContainerResource.getChild("footerHeadings");
            if (null != columnContainerResource) {
                Iterable<
                        Resource> iterableColumnheadingResource = columnContainerResource
                                .getChildren();
                FooterColumnBean columnBean = new FooterColumnBean();
                for (Resource columnheadingResource : iterableColumnheadingResource) {
                    footerLinkBean = new FooterLinkBean();
                    readColumnHeadingComponent(columnheadingResource,
                            footerLinkBean);
                    columnBean.setColumn(footerLinkBean);
                }

                footerLinkBeanList.add(columnBean);
            }
        }

        LOGGER.debug("Exiting getFooterLinks method of FooterUtils");
    }

    /**
     * Reading the column Heading Component fields
     * 
     * @param columnheadingResource
     *            Resource related to the Column Heading Component
     * @param footerLinkBean
     *            Bean that contains values of Footer Columns
     */
    private static void readColumnHeadingComponent(
            Resource columnheadingResource, FooterLinkBean footerLinkBean) {
        ValueMap componentProperties = columnheadingResource.getValueMap();
        List<FooterDetailBean> list = new ArrayList<FooterDetailBean>();
        for (Map.Entry e : componentProperties.entrySet()) {
            if (e.getKey().toString().equalsIgnoreCase(HertzConstants.LINKS)) {
                String[] value = PropertiesUtil.toStringArray(
                        componentProperties.get(HertzConstants.LINKS));
                Gson gson = HertzUtils.initGsonBuilder(true, true, true);
                for (String string : value) {
                    FooterDetailBean bean = gson.fromJson(string,
                            FooterDetailBean.class);
                    bean.setLinkurl(HertzUtils.shortenIfPath(bean.getLinkurl()));
                    list.add(bean);
                }
                footerLinkBean.setFooterDetailBean(list);
            }
            if (e.getKey().toString()
                    .equalsIgnoreCase(HertzConstants.HEADING)) {
                footerLinkBean.setHeading(e.getValue().toString());
            }
        }

    }

    /**
     * This method gets called from the getFooterLinks method and is used to set
     * the values in the FooterLinkBean
     * 
     * @param footerLinkBean
     * @param object
     * @return void
     * 
     * @throws JSONException
     */
    /*
     * private static void setFooterLinkBean(FooterLinkBean footerLinkBean,
     * Resource resource) throws JSONException {
     * LOGGER.debug("Entering inside setFooterLinkBean method of FooterUtils");
     * List<FooterDetailBean> footerDetailList;
     * footerLinkBean.setHeading(HertzUtils.getStringValueFromObject(object,
     * HertzConstants.HEADING));
     * footerLinkBean.setDisplayDesktopHeading(object.getBoolean(HertzConstants.
     * DISPLAY_DESKTOP_HEADING));
     * footerLinkBean.setDisplayMobileHeading(object.getBoolean(HertzConstants.
     * DISPLAY_MOBILE_HEADING));
     * footerLinkBean.setDisplayTabletHeading(object.getBoolean(HertzConstants.
     * DISPLAY_TABLET_HEADING));
     * footerLinkBean.setDisplayAppHeading(object.getBoolean(HertzConstants.
     * DISPLAY_APP_HEADING)); footerDetailList = new
     * ArrayList<FooterDetailBean>(); //setFooterDetailBean(object,
     * footerDetailList); //
     * footerLinkBean.setFooterDetailBean(footerDetailList);
     * LOGGER.debug("Exiting setFooterLinkBean method of FooterUtils"); }
     */

    /**
     * This method gets called from the setFooterLinkBean method and is used to
     * set the FooterDetailBean with the properties set in the Links part of the
     * footer links component
     * 
     * @param object
     * @param footerDetailList
     * @return void
     *
     * @throws JSONException
     */
    /*
     * private static void setFooterDetailBean(JSONObject object,
     * List<FooterDetailBean> footerDetailList) throws JSONException {
     * LOGGER.debug("Entering inside setFooterDetailBean method of FooterUtils"
     * ); FooterDetailBean footerDetailBean; if (null !=
     * object.optJSONArray(HertzConstants.FOOTER_LINKS_ARRAY)) { JSONArray array
     * = object.getJSONArray(HertzConstants.FOOTER_LINKS_ARRAY); for (int j = 0;
     * j < array.length(); j++) { footerDetailBean = new FooterDetailBean();
     * JSONObject footerLinkObject = array.getJSONObject(j);
     * setValuesFooterDetailBean(footerDetailBean, footerLinkObject);
     * footerDetailList.add(footerDetailBean); } }
     * LOGGER.debug("Exiting setFooterDetailBean method of FooterUtils"); }
     */

    /**
     * This method is used to set the FooterDetailBean
     * 
     * @param footerDetailBean
     * @param footerLinkObject
     * @throws JSONException
     */
    /*
     * private static void setValuesFooterDetailBean(FooterDetailBean
     * footerDetailBean, JSONObject footerLinkObject) throws JSONException {
     * LOGGER.
     * debug("Entering method setValuesFooterDetailBean of FooterUtils.class");
     * footerDetailBean.setLinkText(HertzUtils.getStringValueFromObject(
     * footerLinkObject, HertzConstants.LINK_TEXT)); if
     * (StringUtils.isNotEmpty(footerLinkObject.optString(HertzConstants.
     * FOOTER_LINK_URL))) {
     * footerDetailBean.setLinkurl(HertzUtils.shortenIfPath(footerLinkObject.
     * getString(HertzConstants.FOOTER_LINK_URL))); }
     * if(footerLinkObject.has(HertzConstants.OPEN_URL_NEW_WINDOW)){
     * footerDetailBean.setOpenURLNewWindow(footerLinkObject.getBoolean(
     * HertzConstants.OPEN_URL_NEW_WINDOW)); }
     * 
     * if(footerLinkObject.has(HertzConstants.SEO_NOFOLLOW)){
     * footerDetailBean.setSeoNoFollow(footerLinkObject.getBoolean(
     * HertzConstants.SEO_NOFOLLOW)); }
     * footerDetailBean.setDisplayDesktopLink(footerLinkObject.getBoolean(
     * HertzConstants.DISPLAY_DESKTOP_LINK));
     * footerDetailBean.setDisplayMobileLink(footerLinkObject.getBoolean(
     * HertzConstants.DISPLAY_MOBILE_LINK));
     * footerDetailBean.setDisplayTabletLink(footerLinkObject.getBoolean(
     * HertzConstants.DISPLAY_TABLET_LINK));
     * footerDetailBean.setDisplayAppLink(footerLinkObject.getBoolean(
     * HertzConstants.DISPLAY_APP_LINK)); LOGGER.
     * debug("Exiting method setValuesFooterDetailBean of FooterUtils.class"); }
     */

    /**
     * This method gets caled from the setValuesSocialLinkContent method and is
     * used to get all the properties saved in the social links component
     * 
     * @param socialLinksContentBean
     * @param socialLinksContentBeanList
     * @param footerChildResource
     * @param resolver
     * 
     * @return void
     * 
     */
    private static void setSocialLinks(
            SocialLinksContentBean socialLinksContentBean,
            List<SocialLinksContentBean> socialLinksContentBeanList,
            Resource footerChildResource) {
        LOGGER.debug("Entering inside getSocialLinks method of FooterUtils");
        List<SocialLinkBean> socialLinkBeanList = new ArrayList<
                SocialLinkBean>();
        Resource socialLinkChildResource = footerChildResource
                .getChild(HertzConstants.SOCIAL_LINKS);
        ValueMap socialLinkChildResourceProperties = socialLinkChildResource
                .adaptTo(ValueMap.class);

        String socialLinksSubtitle = HertzUtils.getValueFromMap(
                socialLinkChildResourceProperties,
                HertzConstants.SOCIAL_LINKS_SUBTITLE);
        socialLinksContentBean.setSocialLinksSubtitle(socialLinksSubtitle);

        Resource socialLinksResource = socialLinkChildResource
                .getChild(HertzConstants.SOCIAL_LINKS_ARRAY);
        if (null != socialLinksResource
                && !ResourceUtil.isNonExistingResource(socialLinksResource)) {
            Iterable<Resource> iterable = socialLinksResource.getChildren();
            Iterator<Resource> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                Resource childResource = iterator.next();
                if (null != childResource) {
                    setSocialLinkBean(socialLinkBeanList, childResource);
                }
            }
            socialLinksContentBean.setSocialLinkBeanList(socialLinkBeanList);
        }
        socialLinksContentBeanList.add(socialLinksContentBean);

        LOGGER.debug("Exiting getSocialLinks method of FooterUtils");
    }

    /**
     * This method gets called from the getSocialLinks method and is used to set
     * all the values of the social links part of the social links component in
     * the SocialLinkBean
     *
     * @param resolver
     * @param socialLinkBeanList
     * @param childResource
     * @return void
     * 
     */
    private static void setSocialLinkBean(
            List<SocialLinkBean> socialLinkBeanList, Resource childResource) {
        LOGGER.debug("Entering inside setSocialLinkBean method of FooterUtils");
        SocialLinkBean socialLinkBean = new SocialLinkBean();
        ValueMap properties = childResource.getValueMap();
        socialLinkBean.setCssClass(HertzUtils.getValueFromMap(properties,
                HertzConstants.SOCIAL_ICON));
        socialLinkBean.setCtaAction(HertzUtils.getBooleanValueFromMap(
                properties, HertzConstants.OPEN_ICON_NEW_WINDOW));
        socialLinkBean.setSocialURL(HertzUtils.getValueFromMap(properties,
                HertzConstants.SOCIAL_URL));
        socialLinkBean.setAltText(HertzUtils.getValueFromMap(properties,
                HertzConstants.ALT_TEXT));
        socialLinkBean.setSeoNoFollow(HertzUtils.getBooleanValueFromMap(
                properties, HertzConstants.SEO_NOFOLLOW));
        socialLinkBeanList.add(socialLinkBean);
        LOGGER.debug("Exiting setSocialLinkBean method of FooterUtils");
    }

    /**
     * This method returns the map of all the renditions of social icon image
     * selected in the social links component
     * 
     * @param socialIconImageReference,
     *            resolver
     * @return Map<String,String>
     * 
     *//*
       * private static Map<String, String> getRenditions(String
       * socialIconImageReference, ResourceResolver resolver) {
       * LOGGER.debug("Entering inside getRenditions method of FooterUtils");
       * Map<String, String> renditionMap = new HashMap<String, String>(); if
       * (null != resolver && StringUtils.isNotEmpty(socialIconImageReference))
       * { Resource imageResource =
       * resolver.getResource(socialIconImageReference);
       * if(null!=imageResource){ Asset imageAsset =
       * imageResource.adaptTo(Asset.class); Iterator<? extends Rendition>
       * renditions = imageAsset.listRenditions(); while (renditions.hasNext())
       * { Rendition rendition = renditions.next();
       * renditionMap.put(rendition.getName(), rendition.getPath()); } } }
       * LOGGER.debug("Exiting getRenditions method of FooterUtils"); return
       * renditionMap; }
       */

    /**
     * This method gets called from the setValuesSignUpForEmailBean method and
     * is used to set all the properties set in the sign up for email component
     * in the SignUpForEmailBean
     * 
     * @param signUpForEmailBean
     * @param signUpForEmailBeanList
     * @param footerChildResource
     * @return void
     *
     */
    private static void setSignupForEmail(SignUpForEmailBean signUpForEmailBean,
            List<SignUpForEmailBean> signUpForEmailBeanList,
            Resource footerChildResource) {
        LOGGER.debug("Entering inside setSignupForEmail method of FooterUtils");
        Resource signUpForEmailResource = footerChildResource
                .getChild(HertzConstants.EMAIL_SIGNUP);
        ValueMap signUpForEmailProperties = signUpForEmailResource
                .adaptTo(ValueMap.class);
        String signupForEmailTitle = HertzUtils.getValueFromMap(
                signUpForEmailProperties, HertzConstants.SIGNUP_EMAIL_TITLE);
        signUpForEmailBean.setSignupForEmailTitle(signupForEmailTitle);
        String signupForEmailSubtitle = HertzUtils.getValueFromMap(
                signUpForEmailProperties, HertzConstants.SIGNUP_EMAIL_SUBTITLE);
        signUpForEmailBean.setSignupForEmailSubtitle(signupForEmailSubtitle);
        String signupForEmailPlaceholderText = HertzUtils.getValueFromMap(
                signUpForEmailProperties,
                HertzConstants.SIGNUP_EMAIL_PLACEHOLDER_TEXT);
        signUpForEmailBean.setSignupForEmailPlaceholderText(
                signupForEmailPlaceholderText);
        String signupForEmailButtonText = HertzUtils.getValueFromMap(
                signUpForEmailProperties,
                HertzConstants.SIGNUP_EMAIL_BUTTON_TEXT);
        signUpForEmailBean
                .setSignupForEmailButtonText(signupForEmailButtonText);
        String targetURL = HertzUtils.shortenIfPath(HertzUtils.getValueFromMap(
                signUpForEmailProperties, HertzConstants.TARGET_URL));
        signUpForEmailBean.setTargetURL(targetURL);
        Boolean openURLNewWindow = HertzUtils.getBooleanValueFromMap(
                signUpForEmailProperties, HertzConstants.OPEN_URL_NEW_WINDOW);
        signUpForEmailBean.setOpenUrlNewWindow(openURLNewWindow);
        signUpForEmailBean.setSeoNoFollow(HertzUtils.getBooleanValueFromMap(
                signUpForEmailProperties, HertzConstants.SEO_NOFOLLOW));
        signUpForEmailBeanList.add(signUpForEmailBean);
        LOGGER.debug("Exiting setSignupForEmail method of FooterUtils");
    }

    /**
     * 
     * @param footerList
     * @param hit
     * @param resolver
     * @param helper
     * @throws JSONException
     * @throws RepositoryException
     */
    public static void getFooterContent(List<FooterContainerBean> footerList,
            Resource footerContentResource, ResourceResolver resolver, SlingScriptHelper helper)
            throws JSONException, RepositoryException {
        FooterContainerBean footerContainerBean = new FooterContainerBean();
        ValueMap footerResProperties = footerContentResource.getValueMap();
        if (footerResProperties.containsKey(HertzConstants.FOOTER_REFERENCE)) {
            Resource referenceFooterResource = resolver
                    .getResource(footerResProperties
                            .get(HertzConstants.FOOTER_REFERENCE).toString());
            Resource jcrContentResource = referenceFooterResource
                    .getChild(HertzConstants.JCR_CONTENT);
            setFooterBeanList(jcrContentResource, footerContainerBean, resolver,
                    footerList);
        } else {
            setFooterBeanList(footerContentResource, footerContainerBean,
                    resolver, footerList);
        }
    }

    /**
     * 
     * @param contentResource
     * @param footerContainerBean
     * @param resolver
     * @param footerList
     * @throws JSONException
     */
    private static void setFooterBeanList(Resource contentResource,
            FooterContainerBean footerContainerBean, ResourceResolver resolver,
            List<FooterContainerBean> footerList) throws JSONException {
        Resource childFooterResource = null;
        if (null != contentResource.getChild(HertzConstants.FOOTER)) {
            childFooterResource = contentResource
                    .getChild(HertzConstants.FOOTER);
            FooterUtils.getFooter(footerContainerBean, childFooterResource);
            footerList.add(footerContainerBean);
        }
    }

}
