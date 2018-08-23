package com.hertz.digital.platform.bean;

public class BadgeImageBean {
	/**
	 * Holds the value of key
	 */
	String key ;
	
	/**
	 *  Holds the value of alt
	 */
	String tierName ;


	/**
	 * Holds the value of badge
	 */
	ImageInfoBean badge ;
	
	/**
	 * Default Constructor
	 */
	public BadgeImageBean() {
		super();
	}
	
	/**
	 * Getter for Key
	 * @return String for Key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Setter for Key
	 * @param key  String for Key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Getter for Tier Name
	 * @return String for Tier Name
	 */	
	public String getTierName() {
		return tierName;
	}
	/**
	 * Setter for Tier Name
	 * @param alt String for Tier Name
	 */
	public void setTierName(String tierName) {
		this.tierName = tierName;
	}

	/**
	 * Getter for badge
	 * @return ImageInfoBean for badge
	 */
	public ImageInfoBean getBadge() {
		return badge;
	}

	/**
	 * Setter for Badge
	 * @param badge ImageInfoBean Object for badge
	 */
	public void setBadge(ImageInfoBean badge) {
		this.badge = badge;
	}


}
