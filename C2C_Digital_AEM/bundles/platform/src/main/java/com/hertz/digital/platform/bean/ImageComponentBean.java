/**
 * 
 */
package com.hertz.digital.platform.bean;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This is the bean class for
 * rendering properties of image component
 * 
 * @author a.dhingra
 *
 */
public class ImageComponentBean {
	/**
	 * Default Constructor
	 */
	public ImageComponentBean(){
		super();	
	}
	
    private List<Map<String, Object>> sources = new LinkedList<Map<String, Object>>();
	
	/**
	 * Field for altText
	 */
	private String altText;


	/**
	 * @return the altText
	 */
	public String getAltText() {
		return altText;
	}

	/**
	 * @param altText the altText to set
	 */
	public void setAltText(String altText) {
		this.altText = altText;
	}
	
	/**
	 * This gets the list of sources for the image.
	 * 
	 * @return list of sources
	 */
	public List<Map<String, Object>> getSources() {
		return sources;
	}
	/**
	 * @param sources
	 */
	public void setSources(List<Map<String, Object>> sources) {
		this.sources = sources;
	}

}
