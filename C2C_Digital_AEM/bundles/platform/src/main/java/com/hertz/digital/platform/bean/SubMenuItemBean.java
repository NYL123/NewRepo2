package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;

/**
 * JavaBean for the SubPages in the Menu it Stores the
 * Title, URL and the MenuMetadata.
 * @author puneet.soni
 *
 */
public class SubMenuItemBean {
	/**
	 * Default Constructor
	 */
	public SubMenuItemBean() {
		super();
	}

	/**
	 *Title Field in the heading 
	 */
	private String title;
	
	/**
	 * URl field in the Heading
	 */
	private String url;
	
	/**
	 * Open Heading URL in new window
	 */
	@SerializedName("openurlnewwindow")
	private boolean openUrlNewWindow;

	@SerializedName ("nofollow")
	private Boolean seoNoFollow;

	/**
	 * Metadata display-native
	 */
	@SerializedName("display-native")
	private boolean displayNative;
	
	/**
	 * Metadata display-mobile
	 */
	@SerializedName("display-mobile")
	private boolean displayMobile;
	
	/**
	 * Metadata display-tablet
	 */
	@SerializedName("display-tablet")
	private boolean displayTablet;
	
	/**
	 * Metadata display-desktop
	 */
	@SerializedName("display-desktop")
	private boolean  displayDesktop;

	/**
	 * getter for title
	 * @return String that contains the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 *  setter for title
	 * @param title Title of the Menu Page
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * getter for URL
	 * @return String that contains the URL
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * setter for URL
	 * @param url of the Menu Page
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * boolean for openUrlNewWindow
	 * 
	 * @return Boolean
	 */
	public boolean isOpenUrlNewWindow() {
		return openUrlNewWindow;
	}
	
	/**
	 * Setter of display-native
	 * 
	 * @param openUrlNewWindow
	 *            Boolean value of Menu URL open in new window
	 */
	public void setOpenUrlNewWindow(boolean openUrlNewWindow) {
		this.openUrlNewWindow = openUrlNewWindow;
	}
	
	/**
	 * boolean for display_native
	 * @return boolean
	 */
	public boolean isDisplayNative() {
		return displayNative;
	}
	
	/**
	 * Setter for display_native
	 * @param displayNative Boolean value of MenuMetatdata display-native
	 */
	public void setDisplayNative(boolean displayNative) {
		this.displayNative = displayNative;
	}
	
	/**
	 * get value of display_mobile
	 * @return boolean
	 */
	public boolean isDisplayMobile() {
		return displayMobile;
	}
	
	/**
	 * Setter for display_mobile
	 * @param displayMobile Boolean value of MenuMetatdata display-mobile
	 */
	public void setDisplayMobile(boolean displayMobile) {
		this.displayMobile = displayMobile;
	}
	
	/**
	 * get value of display_tablet
	 * 
	 * @return boolean
	 */
	public boolean isDisplayTablet() {
		return displayTablet;
	}
	
	/**
	 * Setter for display_tablet
	 * @param displayTablet Boolean value of MenuMetatdata display-tablet
	 */
	public void setDisplayTablet(boolean displayTablet) {
		this.displayTablet = displayTablet;
	}
	
	/**
	 * get value of display_desktop
	 * @return boolean
	 */
	public boolean isDisplayDesktop() {
		return displayDesktop;
	}
	
	/**
	 * Setter for display_desktop
	 * @param displayDesktop Boolean value of MenuMetatdata display-desktop
	 * 							
	 */
	public void setDisplayDesktop(boolean displayDesktop) {
		this.displayDesktop = displayDesktop;
	}

	public Boolean getSeoNoFollow() {
		return seoNoFollow;
	}

	public void setSeoNoFollow(Boolean seoNoFollow) {
		this.seoNoFollow = seoNoFollow;
	}
}
