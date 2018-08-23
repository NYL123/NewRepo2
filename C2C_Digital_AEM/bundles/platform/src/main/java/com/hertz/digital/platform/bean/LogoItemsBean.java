package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;

/**
 * JavaBean for the Header Search
 * @author himanshu.i.sharma
 *
 */
public class LogoItemsBean {
	/**
	 * Default Constructor
	 */
	public LogoItemsBean(){
		super();
	}

	
	/**
	 * Metadata logoImagealtText
	 */
	@SerializedName("alttext")
	private String logoImagealtText;
	
	/**
	 * get value of logoImagealtText
	 * 
	 * @return
	 */
	public String getLogoImagealtText() {
		return logoImagealtText;
	}
	
	/**
	 * 
	 * @param logoImagealtText
	 */
	public void setLogoImagealtText(String logoImagealtText) {
		this.logoImagealtText = logoImagealtText;
	}

}
