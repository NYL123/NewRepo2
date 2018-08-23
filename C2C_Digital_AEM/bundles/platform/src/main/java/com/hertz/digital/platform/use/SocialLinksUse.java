package com.hertz.digital.platform.use;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.adobe.cq.sightly.WCMUsePojo;
import com.hertz.digital.platform.bean.SocialLinkBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.utils.HertzUtils;

public class SocialLinksUse extends WCMUsePojo {
	/**
	 * LOGGER instantiation.
	 */
	private static final Logger logger = LoggerFactory.getLogger(SocialLinksUse.class);
	/**
	 * Default Constructor
	 */
	public SocialLinksUse() {
		super();
	}
	private String socialLinksSubtitle=StringUtils.EMPTY;

	private List<SocialLinkBean> socialLinksDetails=new ArrayList<SocialLinkBean>();
	/**
	 * 
	 * Activate method for SocialLinksUse class
	 * @param none
	 * @return void
	 * 
	 * */
	@Override
	public void activate() {
		logger.debug("******Entering activate method of SocialLinksUse*******");
		getSocialLinksValues();
		logger.debug("******Exiting activate method of SocialLinksUse*******");
	}
	
	/**
	 * 
	 * This method is called from the activate() method and
	 * is used to fetch all the properties of the social link component
	 * 
	 * @param none
	 * @return void
	 * 
	 * */
	public void getSocialLinksValues() {
		logger.debug("******Entering getSocialLinksValues method of SocialLinksUse*******");
		SocialLinkBean socialLinkBean;
		socialLinksSubtitle=getProperties().get(HertzConstants.SOCIAL_LINKS_SUBTITLE,String.class);
		Resource socialLinksResource=getResource().getChild(HertzConstants.SOCIAL_LINKS_ARRAY);
		if(null!=socialLinksResource && !ResourceUtil.isNonExistingResource(socialLinksResource)){
			Iterable<Resource> iterable=socialLinksResource.getChildren();
			Iterator<Resource> iterator=iterable.iterator();
			while(iterator.hasNext()){
				Resource childResource=iterator.next();
				if(null!=childResource){
					ValueMap properties=childResource.getValueMap();
					socialLinkBean = setSocialLinkBeanValues(properties);					
					socialLinksDetails.add(socialLinkBean);
				}	
			}	
		}
		logger.debug("******Exiting getSocialLinksValues method of SocialLinksUse*******");
	}
	
	/**
	 * 
	 * This method is called from the getSocialLinksValues() method and
	 * is used to set all the values in the SocialLinkBean
	 * 
	 * @param properties
	 * @return SocialLinkBean
	 * 
	 * */
	private SocialLinkBean setSocialLinkBeanValues(ValueMap properties) {
		logger.debug("******Entering setSocialLinkBeanValues method of SocialLinksUse*******");
		SocialLinkBean socialLinkBean=new SocialLinkBean();
		String socialIcon=StringUtils.EMPTY;
		String socialImage=StringUtils.EMPTY;
		Boolean ctaAction=Boolean.FALSE;
		String socialURL=StringUtils.EMPTY;
		String altText=StringUtils.EMPTY;
		socialLinkBean=new SocialLinkBean();
		socialIcon=HertzUtils.getValueFromMap(properties,HertzConstants.SOCIAL_ICON);
		//socialImage=HertzUtils.getValueFromMap(properties, HertzConstants.SOCIAL_ICON_IMAGE_REFERENCE);
		ctaAction=HertzUtils.getBooleanValueFromMap(properties,HertzConstants.OPEN_ICON_NEW_WINDOW);
		socialURL=HertzUtils.getValueFromMap(properties, HertzConstants.SOCIAL_URL);
		altText=HertzUtils.getValueFromMap(properties,HertzConstants.ALT_TEXT);
		socialLinkBean.setCssClass(socialIcon);
		//socialLinkBean.setSocialImage(socialImage);
		if(null!=ctaAction){
			socialLinkBean.setCtaAction(ctaAction);
		}
		socialLinkBean.setSocialURL(socialURL);
		socialLinkBean.setAltText(altText);
		Boolean seoNoFollow = HertzUtils.getBooleanValueFromMap(properties,HertzConstants.SEO_NOFOLLOW);
		if(seoNoFollow!=null) {
			socialLinkBean.setSeoNoFollow(seoNoFollow);
		}
		logger.debug("******Exiting setSocialLinkBeanValues method of SocialLinksUse*******");
		return socialLinkBean;
	}
	
	/**
	 * This method is used to get the List of all the 
	 * values of the Social Link Component
	 * */
	public List<SocialLinkBean> getSocialLinksDetails() {
		return socialLinksDetails;
	}
	/**
	 * This method is used to set the List of all the 
	 * values of the Social Link Component
	 * */
	public void setSocialLinksDetails(List<SocialLinkBean> socialLinksDetails) {
		this.socialLinksDetails = socialLinksDetails;
	}
	
	/**
	 * This method is used to get the 
	 * Social Links Subtitle field
	 * */
	public String getSocialLinksSubtitle() {
		return socialLinksSubtitle;
	}
	
	/**
	 * This method is used to set the 
	 * Social Links Subtitle field
	 * */
	public void setSocialLinksSubtitle(String socialLinksSubtitle) {
		this.socialLinksSubtitle = socialLinksSubtitle;
	}
}

