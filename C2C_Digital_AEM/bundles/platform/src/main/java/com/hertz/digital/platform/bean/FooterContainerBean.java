package com.hertz.digital.platform.bean;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This is a Javabean for the Footer Component properties
 * 
 * @author a.dhingra
 * 
 */
public class FooterContainerBean {
	/**
	 * Default Constructor
	 */
	public FooterContainerBean() {
		super();
	}
	/**
	 * Legal Description Text field in the footer
	 */
	@SerializedName("copyrighttext")
	private String legalDescriptionText;
	/**
	 * Accessibility Icon in the footer
	 */
	@Expose
    @SerializedName("accessibilityIcon")
    private List<Map<String, Object>> accessibilityIcon = new LinkedList<Map<String, Object>>();

	/**
	 * List of all the legal links in the footer
	 */
	@SerializedName("legallinks")
	private List<LegalLinksBean> legalLinks;
	
	/**
	 * List for all the sign up for email component properties
	 */	
	@SerializedName("signupforemail") 
	private List<SignUpForEmailBean> signUpForEmail;
	/**
	 * List for all the social link component properties
	 */
	@SerializedName("sociallinks")
	private List<SocialLinksContentBean> socialLinks;
	/**
	 * List for all the footer link component properties
	 */
	@SerializedName("footerlinks")
	private List<FooterColumnBean> footerlinks;
	
	/**
	 * This method is used to get the list of all properties of the 
	 * sign up for email component
	 * 
	 * @return List<SignUpForEmailBean>
	 */
	public List<SignUpForEmailBean> getSignUpForEmail() {
		return signUpForEmail;
	}

	/**
	 * This method is used to set the list of all properties of the 
	 * sign up for email component
	 * 
	 * @param signUpForEmail
	 */
	public void setSignUpForEmail(List<SignUpForEmailBean> signUpForEmail) {
		this.signUpForEmail = signUpForEmail;
	}
	/**
	 * This method is used to get the list of all properties of the social
	 * links component.
	 * 
	 * @return List<SocialLinksContentBean>
	 */
	public List<SocialLinksContentBean> getSocialLinks() {
		return socialLinks;
	}
	/**
	 * This method is used to set the list of all properties of the social
	 * links component.
	 * 
	 * @param socialLinks
	 */
	public void setSocialLinks(List<SocialLinksContentBean> socialLinks) {
		this.socialLinks = socialLinks;
	}
	/**
	 * This method is used to get the list of all properties of the footer
	 * links component.
	 * 
	 * @return List<FooterLinkBean>
	 */
	public List<FooterColumnBean> getFooterlinks() {
		return footerlinks;
	}
	/**
	 * This method is used to set the list of all properties of the footer
	 * links component.
	 *
	 * @param footerlinks
	 */
	public void setFooterlinks(List<FooterColumnBean> footerlinks) {
		this.footerlinks = footerlinks;
	}

	/**
	 *This method is used to get the value of the 
	 *legal description text field stored in the footer.
	 
	 * @return String
	 */
	public String getLegalDescriptionText() {
		return legalDescriptionText;
	}

	/**
	 *This method is used to set the value of the 
	 *legal description text field stored in the footer.
	 *
	 * @param legalDescriptionText
	 */
	public void setLegalDescriptionText(String legalDescriptionText) {
		this.legalDescriptionText = legalDescriptionText;
	}

	/**
	 * This method is used to get the list of all the legal links
	 * in the footer
	 * 
	 * @return List<LegalLinksBean>
	 */
	public List<LegalLinksBean> getLegalLinks() {
		return legalLinks;
	}
	/**
	 * This method is used to set the list of all the legal links
	 * in the footer
	 *
	 * @param legalLinks
	 */
	public void setLegalLinks(List<LegalLinksBean> legalLinks) {
		this.legalLinks = legalLinks;
	}
	
	/**
	 * This gets the list of sources for the image.
	 * 
	 * @return list of sources
	 */
	public List<Map<String, Object>> getSources() {
		return accessibilityIcon;
	}
	/**
	 * @param sources
	 */
	public void setSources(List<Map<String, Object>> accessibilityIcon) {
		this.accessibilityIcon = accessibilityIcon;
	}
}

