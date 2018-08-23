package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;
/**
 * Bean to store the Hours of Operation
 */
public class LocationBean {
	
	/**
	 * Default Constructor
	 */
	public LocationBean() {
		super();
	}

	/**
	 * Stores the region
	 */
	@SerializedName("region")
	private String region;
	
	/**
	 *  stores the country Name
	 */
	@SerializedName("country")
	private String country;
	
	/**
	 * Stores the Language Name
	 */
	@SerializedName("language")
	private String language;
	
	/**
	 * Stores the stateorProvince Name
	 */
	@SerializedName("stateOrProvince")
	private String stateOrProvince;
	
	/**
	 * Stores the City name
	 */
	@SerializedName("city")
	private String city;
	
	/**
	 * Stores the hoursOfOperation1 data
	 */
	@SerializedName("hoursOfOperation1")
	private String hoursOfOperation1;
	
	/**
	 * Stores the hoursOfOperation2 data
	 */
	@SerializedName("hoursOfOperation2")
	private String hoursOfOperation2;
	
	/**
	 * Stores the hoursOfOperation3 data
	 */
	@SerializedName("hoursOfOperation3")
	private String hoursOfOperation3;
	
	
	/**
	 * Stores the OAG code data
	 */
	@SerializedName("OAG")
	private String oag;
	
	/**
	 * getter of the OAG code 
	 * @return OAG code 
	 */
	public String getOag() {
		return oag;
	}

	/**
	 * Setter of the OAG code
	 * @param oag OAG Code
	 */
	public void setOag(String oag) {
		this.oag = oag;
	}

	/**
	 * get the Regions
	 * @return String that contains the region data
	 */
	public String getRegion() {
		return region;
	}
	
	/**
	 * set the region data
	 * @param region data that contains the region data
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * getter for the country
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * set the country data 
	 * @param country country data
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * getter for the language
	 * @return String that has language data
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Setter for the Language data
	 * @param language language data
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * getter for the province data
	 * @return Province data
	 */
	public String getStateOrProvince() {
		return stateOrProvince;
	}

	/**
	 * Setter for the province data
	 * @param stateOrProvince province data
	 */
	public void setStateOrProvince(String stateOrProvince) {
		this.stateOrProvince = stateOrProvince;
	}

	/**
	 * getter for the City data
	 * @return String that has City data
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Setter for the City data
	 * @param city City data
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * getter for the hoursOfOperation1 data
	 * @return String that contains the hoursOfOperation1 data
	 */
	public String getHoursOfOperation1() {
		return hoursOfOperation1;
	}

	/**
	 * Setter for the hoursOfOperation1
	 * @param hoursOfOperation1 hoursOfOperation1 data
	 */
	public void setHoursOfOperation1(String hoursOfOperation1) {
		this.hoursOfOperation1 = hoursOfOperation1;
	}

	/**
	 * getter for the hoursOfOperation2
	 * @return String that contains hoursOfOperation2
	 */
	public String getHoursOfOperation2() {
		return hoursOfOperation2;
	}

	/**
	 * Setter for the hoursOfOperation2
	 * @param hoursOfOperation2 hoursOfOperation2 data
	 */
	public void setHoursOfOperation2(String hoursOfOperation2) {
		this.hoursOfOperation2 = hoursOfOperation2;
	}

	/**
	 * Getter for the hoursOfOperation3
	 * @return hoursOfOperation3 data
	 */
	public String getHoursOfOperation3() {
		return hoursOfOperation3;
	}

	/**
	 * Setter for the hoursOfOperation3
	 * @param hoursOfOperation3 hoursOfOperation3 data
	 */
	public void setHoursOfOperation3(String hoursOfOperation3) {
		this.hoursOfOperation3 = hoursOfOperation3;
	}


}
