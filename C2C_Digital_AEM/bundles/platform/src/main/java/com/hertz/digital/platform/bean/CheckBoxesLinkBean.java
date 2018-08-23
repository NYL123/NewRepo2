package com.hertz.digital.platform.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class for storing the Link values of the checkBoxes component
 * 
 * @author puneet.soni
 *
 */
public class CheckBoxesLinkBean {
	
	

	/**
	 * Default constructorR
	 */
	public CheckBoxesLinkBean() {
		super();
	}

	/**
	 * Variable for elementGroup
	 */
	@SerializedName("elementGroup")
	@Expose
	private String elementGroup;

	/**
	 * Variable for element
	 */
	@SerializedName("element")
	@Expose
	private String element;

	/**
	 * Variable for linkText
	 */
	@SerializedName("linkText")
	@Expose
	private String linkText;

	/**
	 * Variable for ariaLabel
	 */
	@SerializedName("ariaLabel")
	@Expose
	private String ariaLabel;

	/**
	 * Variable for targetType
	 */
	@SerializedName("targetType")
	@Expose
	private String targetType;

	/**
	 * Variable for targetURL
	 */
	@SerializedName("targetURL")
	@Expose
	private String targetURL;

	/**
	 * Getter for elementGroup
	 * @return String that contains elementGroup
	 */
	public String getElementGroup() {
		return elementGroup;
	}

	/**
	 * Setter for Element Group
	 * @param elementGroup elementGroup
	 */
	public void setElementGroup(String elementGroup) {
		this.elementGroup = elementGroup;
	}

	/**
	 * Getter for Element
	 * @return String that contains element
	 */
	public String getElement() {
		return element;
	}

	/**
	 * Setter for Element 
	 * @param element element
	 */
	public void setElement(String element) {
		this.element = element;
	}

	/**
	 * Getter for linkText
	 * @return String that contains linkText
	 */
	public String getLinkText() {
		return linkText;
	}

	/**
	 * Setter for LinkText
	 * @param linkText linkText
	 */
	public void setLinkText(String linkText) {
		this.linkText = linkText;
	}

	/**
	 * Getter for ariaLabel
	 * @return  String that contains ariaLabel
	 */
	public String getAriaLabel() {
		return ariaLabel;
	}

	/**
	 * Setter for ariaLabel
	 * @param ariaLabel ariaLabel
	 */
	public void setAriaLabel(String ariaLabel) {
		this.ariaLabel = ariaLabel;
	}

	/**
	 * Getter for targetType
	 * @return String that contains targetType
	 */
	public String getTargetType() {
		return targetType;
	}

	/**
	 * Setter for targetType
	 * @param targetType targetType
	 */
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	/**
	 * Getter for targetURL
	 * @return String that contains targetURL
	 */
	public String getTargetURL() {
		return targetURL;
	}

	/**
	 * Setter for targetURL
	 * 
	 * @param targetURL targetURL
	 */
	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}

}
