package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;

/**
 * JavaBean for the Header Search
 * @author himanshu.i.sharma
 *
 */
public class LoginItemsBean {
	
	/**
	 * Default Constructor
	 */
	public LoginItemsBean(){
		super();
	}
	/**
	 * Metadata loginBtnTxt
	 */
	@SerializedName("logintext")
	private String loginBtnTxt;
	
	/**
	 * Metadata loginPlaTxt
	 */
	@SerializedName("signuptext")
	private String loginPlaTxt;
	
	/**
	 * Metadata loginWelcomeText
	 */
	@SerializedName("welcometext")
	private String loginWelcomeText;

	public String getLoginBtnTxt() {
		return loginBtnTxt;
	}
	
	/**
	 * 
	 * @param fileReference
	 */
	public void setLoginBtnTxt(String loginBtnTxt) {
		this.loginBtnTxt = loginBtnTxt;
	}
	
	/**
	 * get value of loginPlaTxt
	 * @return
	 */
	public String getLoginPlaTxt() {
		return loginPlaTxt;
	}
	
	/**
	 * 
	 * @param loginPlaTxt
	 */
	public void setLoginPlaTxt(String loginPlaTxt) {
		this.loginPlaTxt = loginPlaTxt;
	}

	/**
	 * @return the loginWelcomeText
	 */
	public String getLoginWelcomeText() {
		return loginWelcomeText;
	}

	/**
	 * @param loginWelcomeText the loginWelcomeText to set
	 */
	public void setLoginWelcomeText(String loginWelcomeText) {
		this.loginWelcomeText = loginWelcomeText;
	}
}
