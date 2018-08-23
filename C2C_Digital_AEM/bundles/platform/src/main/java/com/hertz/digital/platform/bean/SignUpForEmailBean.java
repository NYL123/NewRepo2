package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;

/**
 * This is a Javabean for the Sign Up For Email Component properties
 * 
 * @author a.dhingra
 * 
 */
public class SignUpForEmailBean {
	/**
	 * Default Constructor
	 */
	public SignUpForEmailBean() {
		super();
	}
	/**
	 * Sign up for Email title field
	 */
	@SerializedName("title")
	private String signupForEmailTitle;
	/**
	 * Sign up for Email sub-title field
	 */
	@SerializedName("subtitle")
	private String signupForEmailSubtitle;
	/**
	 * Sign up for Email Placeholder field
	 */
	@SerializedName("placeholder")
	private String signupForEmailPlaceholderText;
	/**
	 * Sign up for Email Button Text field
	 */
	@SerializedName("button")
	private String signupForEmailButtonText;
	/**
	 * Target URL field
	 */
	@SerializedName("url")
	private String targetURL;
	/**
	 * Open URL in New Window field
	 */
	@SerializedName("openurlnewwindow")
	private Boolean openUrlNewWindow;

	@SerializedName ("nofollow")
	private Boolean seoNoFollow;

	/**
	 * This method is used to get the value of 
	 * the Signup For Email Title field.
	 */
	public String getSignupForEmailTitle() {
		return signupForEmailTitle;
	}
	/**
	 * This method is used to set the value of 
	 * the Signup For Email Title field.
	 */
	public void setSignupForEmailTitle(String signupForEmailTitle) {
		this.signupForEmailTitle = signupForEmailTitle;
	}
	/**
	 * This method is used to get the value of 
	 * the Target URL field.
	 */
	public String getTargetURL() {
		return targetURL;
	}
	/**
	 * This method is used to set the value of 
	 * the Target URL field.
	 */
	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}
	/**
	 * This method is used to get the value of 
	 * the Signup For Email Sub-Title field.
	 */
	public String getSignupForEmailSubtitle() {
		return signupForEmailSubtitle;
	}
	/**
	 * This method is used to set the value of 
	 * the Signup For Email Sub-Title field.
	 */
	public void setSignupForEmailSubtitle(String signupForEmailSubtitle) {
		this.signupForEmailSubtitle = signupForEmailSubtitle;
	}
	/**
	 * This method is used to get the value of 
	 * the Signup For Email Placeholder Text field.
	 */
	public String getSignupForEmailPlaceholderText() {
		return signupForEmailPlaceholderText;
	}
	/**
	 * This method is used to set the value of 
	 * the Signup For Email Placeholder Text field.
	 */
	public void setSignupForEmailPlaceholderText(String signupForEmailPlaceholderText) {
		this.signupForEmailPlaceholderText = signupForEmailPlaceholderText;
	}
	/**
	 * This method is used to get the value of 
	 * the Signup For Email Button Text field.
	 */
	public String getSignupForEmailButtonText() {
		return signupForEmailButtonText;
	}
	/**
	 * This method is used to set the value of 
	 * the Signup For Email Button Text field.
	 */
	public void setSignupForEmailButtonText(String signupForEmailButtonText) {
		this.signupForEmailButtonText = signupForEmailButtonText;
	}
	/**
	 * This method is used to get the value of 
	 * the Open URL in New Window field.
	 */
	public Boolean getOpenUrlNewWindow() {
		return openUrlNewWindow;
	}
	/**
	 * This method is used to set the value of 
	 * the Open URL in New Window field.
	 */
	public void setOpenUrlNewWindow(Boolean openUrlNewWindow) {
		this.openUrlNewWindow = openUrlNewWindow;
	}

	public Boolean getSeoNoFollow() {
		return seoNoFollow;
	}

	public void setSeoNoFollow(Boolean seoNoFollow) {
		this.seoNoFollow = seoNoFollow;
	}
}

