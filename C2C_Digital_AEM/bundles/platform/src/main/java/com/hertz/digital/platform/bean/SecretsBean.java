package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;
/**
 * Bean to store the secrets key and value
 * @author himanshu.i.sharma
 */
public class SecretsBean {
	
	/**
	 * Default Constructor
	 */
	public SecretsBean() {
		super();
	}

	/**
	 * Stores the key
	 */
	@SerializedName("key")
	private String key;
	
	/**
	 *  stores the value
	 */
	@SerializedName("value")
	private String value;
    
	/**
	 * get the key
	 * @return String that contains the key
	 */
	public String getKey() {
		return key;
	}
    
	/**
	 * set the key
	 * @param key that contains the key
	 */
	public void setKey(String key) {
		this.key = key;
	}
    
	/**
	 * get the value
	 * @return String that contains the value
	 */
	public String getValue() {
		return value;
	}
    
	/**
	 * set the value
	 * @param value that contains the value
	 */
	public void setValue(String value) {
		this.value = value;
	}
}

