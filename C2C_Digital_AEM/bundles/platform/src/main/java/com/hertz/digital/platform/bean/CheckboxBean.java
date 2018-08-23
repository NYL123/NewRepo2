package com.hertz.digital.platform.bean;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Bean class for Storing the Variables of CheckboxBean
 * @author puneet.soni
 *
 */
public class CheckboxBean {
	
	

	/**
	 * Default constructor
	 */
	public CheckboxBean() {
		super();
	}

	/**
	 * Variable for order
	 */
	@SerializedName("Order")
	@Expose
	private String order;
	
	/**
	 * Variable for element
	 */
	@SerializedName("Element")
	@Expose
	private String element;
	
	/**
	 * Variable for defaultSelection
	 */
	@SerializedName("DefaultSelection")
	@Expose
	private String defaultSelection;
	
	/**
	 * Variable for text
	 */
	@SerializedName("Text")
	@Expose
	private String text;
	
	/**
	 * Variable for ariaLabel
	 */
	@SerializedName("AriaLabel")
	@Expose
	private String ariaLabel;
	
	/**
	 * Variable for elementGroup
	 */
	@SerializedName("ElementGroup")
	@Expose
	private String elementGroup;
	
	/**
	 * List of CheckBoxesLinkBean
	 */
	@SerializedName("links")
	@Expose
	private List<CheckBoxesLinkBean> links;

	/**
	 * Getter for Order
	 * @return String the contains order 
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * Setter for order
	 * @param order order
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * Getter for elementGroup
	 * @return String the contains
	 */
	public String getElement() {
		return element;
	}

	/**
	 * Setter for element
	 * @param element element
	 */
	public void setElement(String element) {
		this.element = element;
	}

	/**
	 * Getter for elementGroup
	 * @return String the contains
	 */
	public String getDefaultSelection() {
		return defaultSelection;
	}

	/**
	 * Setter for defaultSelection
	 * @param defaultSelection defaultSelection
	 */
	public void setDefaultSelection(String defaultSelection) {
		this.defaultSelection = defaultSelection;
	}

	/**
	 * Getter for elementGroup
	 * @return String the contains
	 */
	public String getText() {
		return text;
	}

	/**
	 * Setter for text
	 * @param text text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Getter for elementGroup
	 * @return String the contains
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
	 * Getter for elementGroup
	 * @return String the contains
	 */
	public String getElementGroup() {
		return elementGroup;
	}

	/**
	 * Setter for elementGroup
	 * @param elementGroup elementGroup
	 */
	public void setElementGroup(String elementGroup) {
		this.elementGroup = elementGroup;
	}

	/**
	 * Getter for List of CheckBoxesLinkBean
	 */
	public List<CheckBoxesLinkBean> getLinks() {
		return links;
	}

	/**
	 * Setter for links
	 * @param links links
	 */
	public void setLinks(List<CheckBoxesLinkBean> links) {
		this.links = links;
	}

}
