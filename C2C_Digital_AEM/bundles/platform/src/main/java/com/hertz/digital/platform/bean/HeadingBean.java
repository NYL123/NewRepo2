package com.hertz.digital.platform.bean;

import java.util.ArrayList;
import java.util.List;

import com.day.cq.theme.Theme;
import com.google.gson.annotations.SerializedName;

/**
 * This is a Javabean for the Menu Pages field in the menu It Stores the
 * MenuMetadata and the navigationalURL as well as the List of SubPages
 * (SubMenuItemBean)
 * 
 * @author puneet.soni
 * 
 */
public class HeadingBean {
	/**
	 * Default Constructor
	 */
	public HeadingBean() {
		super();
	}

	/**
	 * Title Field in the heading
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
	 * List of the sub Menu Pages
	 */
	@SerializedName("items")
	private final List<SubMenuItemBean> items = new ArrayList<SubMenuItemBean>();

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
	private boolean displayDesktop;

	/**
	 * Getter for {@link Theme} List
	 * 
	 * @return A List of SubMenuItemBean that contains properties of the Sub
	 *         Pages
	 */
	public List<SubMenuItemBean> getItems() {
		return items;
	}

	/**
	 * Setter for the MenuItems
	 * 
	 * @param item
	 *            Object of SubMenuItemBean
	 */
	public void setItems(SubMenuItemBean item) {
		items.add(item);
	}

	/**
	 * Getter for title
	 * 
	 * @return String that has the value of the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for title of the Menu Page
	 * 
	 * @param title
	 *            Title of the Menu Page
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * getter for URL of the Menu Page
	 * 
	 * @return URL of the Menu page
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * setter for URL of the Menu Page
	 * 
	 * @param url
	 *            Setter of URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * boolean for openNavigationUrlNewWindow
	 * 
	 * @return Boolean
	 */
	public boolean isOpenUrlNewWindow() {
		return openUrlNewWindow;
	}
	
	/**
	 * Setter of display-native
	 * 
	 * @param openNavigationUrlNewWindow
	 *            Boolean value of Menu URL open in new window
	 */
	public void setOpenUrlNewWindow(boolean openUrlNewWindow) {
		this.openUrlNewWindow = openUrlNewWindow;
	}

	/**
	 * boolean for display_native
	 * 
	 * @return Boolean
	 */
	public boolean isDisplayNative() {
		return displayNative;
	}

	/**
	 * Setter of display-native
	 * 
	 * @param displayNative
	 *            Boolean value of MenuMetatdata display-native
	 */
	public void setDisplayNative(boolean displayNative) {
		this.displayNative = displayNative;
	}

	/**
	 * get value of display_mobile
	 * 
	 * @return Boolean
	 */
	public boolean isDisplayMobile() {
		return displayMobile;
	}

	/**
	 * Setter of display-mobile
	 * 
	 * @param displayMobile
	 *            Boolean value of MenuMetatdata display-mobile
	 */
	public void setDisplayMobile(boolean displayMobile) {
		this.displayMobile = displayMobile;
	}

	/**
	 * get value of display_tablet
	 * 
	 * @return Boolean
	 */
	public boolean isDisplayTablet() {
		return displayTablet;
	}

	/**
	 * Setter of display-tablet
	 * 
	 * @param displayTablet
	 *            Boolean value of MenuMetatdata display-tablet
	 */
	public void setDisplayTablet(boolean displayTablet) {
		this.displayTablet = displayTablet;
	}

	/**
	 * get value of display-desktop Metadata
	 * 
	 * @return Boolean
	 */
	public boolean isDisplayDesktop() {
		return displayDesktop;
	}

	/**
	 * Setter of display-desktop
	 * 
	 * @param displayDesktop
	 *            Boolean value of MenuMetatdata display-desktop
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
