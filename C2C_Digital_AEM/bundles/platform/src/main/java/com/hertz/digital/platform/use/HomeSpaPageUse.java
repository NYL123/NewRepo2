package com.hertz.digital.platform.use;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.oak.commons.PropertiesUtil;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.hertz.digital.platform.bean.FooterContainerBean;
import com.hertz.digital.platform.bean.HeaderBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.service.api.MenuBuilderService;
import com.hertz.digital.platform.utils.FooterUtils;
import com.hertz.digital.platform.utils.HeaderUtils;

/**
 * This class is used to get all the header-footer properties to render the
 * content on the home-page.
 * 
 * @author a.dhingra
 *
 */
public class HomeSpaPageUse extends WCMUsePojo {

    /**
     * Default constructor Declaration
     */
    public HomeSpaPageUse() {
        super();
    }

    /**
     * LOGGER instantiation.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(HomeSpaPageUse.class);

    /**
     * Declaring variable for list of all header properties.
     */
    private List<HeaderBean> headerList = new ArrayList<>();

    /**
     * Declaring variable for list of all footer properties.
     */
    private List<FooterContainerBean> footerList = new ArrayList<>();

    /**
     * Variable foe hero tag line text
     * 
     */
    private String tagLineText;

    /**
     * Variable foe hero image path
     * 
     */
    private String heroImagePath;

    /**
     * Variable for hero image alt text
     * 
     */
    private String heroImageAltText;

    /**
     * Variable for pick up location placeholder text
     */
    private String pickUpLocationPlaceholder;

    /**
     * Variable for pick up location cancel text
     */
    private String pickUpLocationCancel;

    @Override
    public void activate() {
        LOGGER.debug("Entering Activate() : - HeaderFooterHomePageUse.class");
        ResourceResolver resolver = getResourceResolver();
        try {
            String partnerPagePath = getProperties()
                    .get(HertzConstants.PARTNER_PAGE_PATH, String.class);
            if (StringUtils.isNotEmpty(partnerPagePath)) {
                LOGGER.debug("path of partner page:-" + partnerPagePath);
                headerList = HeaderUtils.getOutputJSON(partnerPagePath,
                        getResponse(), resolver, getSlingScriptHelper()
                                .getService(MenuBuilderService.class));
                LOGGER.debug("header list size" + headerList.size());
                footerList = FooterUtils.getFooterJSON(partnerPagePath, resolver);
                LOGGER.debug("footer List size" + footerList.size());

                Resource heroResource = getResource()
                        .getChild(HertzConstants.HERO);

                if (null != heroResource
                        && !ResourceUtil.isNonExistingResource(heroResource)) {
                    ValueMap heroProperties = heroResource.getValueMap();
                    setTagLineText(PropertiesUtil.toString(
                            heroProperties.get(HertzConstants.TAGLINE_TEXT),
                            StringUtils.EMPTY));
                    setHeroImagePath(PropertiesUtil.toString(
                            heroProperties.get(HertzConstants.BACKGROUND_IMAGE),
                            StringUtils.EMPTY));
                    setHeroImageAltText(PropertiesUtil.toString(
                            heroProperties.get(HertzConstants.ALT_TEXT),
                            StringUtils.EMPTY));
                }

            }
        } catch (LoginException | RepositoryException | JSONException e) {
            LOGGER.error(
                    "Error in activate method of HeaderFooterHomePageUse.class"
                            + e.getMessage());
        }
        LOGGER.debug("Exit Activate() :- HeaderFooterHomePageUse.class");
    }

    /**
     * @return the headerList
     */
    public List<HeaderBean> getHeaderList() {
        return headerList;
    }

    /**
     * @param headerList
     *            the headerList to set
     */
    public void setHeaderList(List<HeaderBean> headerList) {
        this.headerList = headerList;
    }

    /**
     * @return the footerList
     */
    public List<FooterContainerBean> getFooterList() {
        return footerList;
    }

    /**
     * @param footerList
     *            the footerList to set
     */
    public void setFooterList(List<FooterContainerBean> footerList) {
        this.footerList = footerList;
    }

    /**
     * @return the tagLineText
     */
    public String getTagLineText() {
        return tagLineText;
    }

    /**
     * @param tagLineText
     *            the tagLineText to set
     */
    public void setTagLineText(String tagLineText) {
        this.tagLineText = tagLineText;
    }

    /**
     * @return the heroImagePath
     */
    public String getHeroImagePath() {
        return heroImagePath;
    }

    /**
     * @param heroImagePath
     *            the heroImagePath to set
     */
    public void setHeroImagePath(String heroImagePath) {
        this.heroImagePath = heroImagePath;
    }

    /**
     * @return the heroImageAltText
     */
    public String getHeroImageAltText() {
        return heroImageAltText;
    }

    /**
     * @param heroImageAltText
     *            the heroImageAltText to set
     */
    public void setHeroImageAltText(String heroImageAltText) {
        this.heroImageAltText = heroImageAltText;
    }

    /**
     * @return the pickUpLocationPlaceholder
     */
    public String getPickUpLocationPlaceholder() {
        return pickUpLocationPlaceholder;
    }

    /**
     * @param pickUpLocationPlaceholder
     *            the pickUpLocationPlaceholder to set
     */
    public void setPickUpLocationPlaceholder(String pickUpLocationPlaceholder) {
        this.pickUpLocationPlaceholder = pickUpLocationPlaceholder;
    }

    /**
     * @return the pickUpLocationCancel
     */
    public String getPickUpLocationCancel() {
        return pickUpLocationCancel;
    }

    /**
     * @param pickUpLocationCancel
     *            the pickUpLocationCancel to set
     */
    public void setPickUpLocationCancel(String pickUpLocationCancel) {
        this.pickUpLocationCancel = pickUpLocationCancel;
    }

}
