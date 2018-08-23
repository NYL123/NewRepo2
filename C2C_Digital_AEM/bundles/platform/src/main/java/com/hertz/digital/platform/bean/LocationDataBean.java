package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;
/**
 * Bean to store the Hours of Operation data
 * @author puneet.soni
 *
 */
public class LocationDataBean {
	/**
	 * Default Constructor
	 */
	public LocationDataBean() {
		super();
	}

	/**
	 * Bean to store the Hours of Operation
	 */
	@SerializedName("location")
	private  LocationBean locationBean;

	/**
	 * Getter of LocationBean
	 * @return LocationBean
	 */
	public LocationBean getLocationBean() {
		return locationBean;
	}

	/**
	 * Setter for the LocationBean
	 * @param locationBean Bean that stores the Hours of Operation data
	 */
	public void setLocationBean(LocationBean locationBean) {
		this.locationBean = locationBean;
	}

}
