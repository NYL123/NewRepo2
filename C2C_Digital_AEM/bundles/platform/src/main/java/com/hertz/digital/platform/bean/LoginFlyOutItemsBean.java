package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;

/**
 * JavaBean for the Navigation Level 2
 * @author himanshu.i.sharma
 *
 */
public class LoginFlyOutItemsBean {
	/**
	 * Default Constructor
	 */
	public LoginFlyOutItemsBean(){
		super();
	}
		
	/**
	 * Metadata flyoutItemTxt
	 */
	@SerializedName("flyoutItemTxt")
	private String flyoutItemTxt;
	
	/**
	 * Metadata flyoutItemPath
	 */
	@SerializedName("flyoutItemPath")
	private String flyoutItemPath;
	
	@SerializedName("openUrlNewWindow")
	private boolean openUrlNewWindow;
	
	public void setFlyoutItemTxt( String flyoutItemTxt) {  
		this.flyoutItemTxt = flyoutItemTxt;
	}

	public String getFlyoutItemTxt() {  
	    return flyoutItemTxt;
	}
	
	public void setFlyoutItemPath( String flyoutItemPath) {  
		this.flyoutItemPath = flyoutItemPath;
	}

	public String getFlyoutItemPath() {  
	    return flyoutItemPath;
	}
	
	public Boolean getCtaAction() {
		return openUrlNewWindow;
	}

	public void setCtaAction(Boolean openUrlNewWindow) {
		this.openUrlNewWindow = openUrlNewWindow;
	}

}
