package com.hertz.digital.platform.bean;

import java.util.List;

import com.google.gson.annotations.SerializedName;
/**
 * This is a Javabean for the Social Links Component properties
 * 
 * @author a.dhingra
 * 
 */
public class SocialLinksContentBean {
	/**
	 * Default Constructor
	 */
	public SocialLinksContentBean() {
		super();
	}
	/**
	 * Social Links Subtitle field in the Social Link Component
	 */
	@SerializedName("title")
	private String socialLinksSubtitle;
	/**
	 * List of all social icon properties in the Social Link Component
	 */
	@SerializedName("socialicons")
	private List<SocialLinkBean> socialLinkBeanList;

	/**
	 * This method is used to get the value of
	 * Social Links Subtitle field
	 */
	public String getSocialLinksSubtitle() {
		return socialLinksSubtitle;
	}
	/**
	 * This method is used to set the value of
	 * Social Links Subtitle field
	 */
	public void setSocialLinksSubtitle(String socialLinksSubtitle) {
		this.socialLinksSubtitle = socialLinksSubtitle;
	}
	/**
	 * This method is used to get the list of
	 * all properties of the social link component multifield
	 */
	public List<SocialLinkBean> getSocialLinkBeanList() {
		return socialLinkBeanList;
	}
	/**
	 * This method is used to set the list of
	 * all properties of the social link component multifield
	 */
	public void setSocialLinkBeanList(List<SocialLinkBean> socialLinkBeanList) {
		this.socialLinkBeanList = socialLinkBeanList;
	}	
}
