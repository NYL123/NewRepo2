package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Bean class for Chat component
 * 
 * @author puneet.soni
 * 
 */
public class ChatBean {
	/**
	 * Default Constructor
	 */
	public ChatBean() {
		super();
	}
	
	/**
	 * Field for chat ALt Text
	 */
	@SerializedName("alttext")
	private String chatAltText;


	/**
	 * Gets the Alt text for the Chat Icon
	 * 
	 * @return Gets the Alt text for the Chat Icon
	 */
	public String getChatAltText() {
		return chatAltText;
	}
	
	/**
	 * Sets the Alt text for the Chat Icon
	 * 
	 * @param chatAltText
	 *            the Alt text for the Chat Icon
	 */
	public void setChatAltText(String chatAltText) {
		this.chatAltText = chatAltText;
	}


}
