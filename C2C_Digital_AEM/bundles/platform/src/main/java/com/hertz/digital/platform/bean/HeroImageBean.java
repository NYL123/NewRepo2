package com.hertz.digital.platform.bean;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Bean for Hero Component. Sets the ImagePath, altText & tagLineText.
 * @author n.kumar.singhal
 *
 */
public class HeroImageBean {
	
	/**
	 * Default Constructor
	 */
	public HeroImageBean(){
		super();	
	}
	/**
	 * Field for Image Path 
	 */
	private String imagePath;
	
	/**
	 * Field for altText
	 */
	private String altText;
	
	/**
	 * Field for Label tagLine
	 */
	private String tagLineText;
	
	/**
	 * Field for Label subTagLineText
	 */
	private String subTagLineText;
	
	@Expose
    @SerializedName("sources")
    private List<Map<String, Object>> sources = new LinkedList<Map<String, Object>>();

	private ImageInfoBean imageInfo;
	/**
	 * Returns of ImagePath 
	 * @return String that contains imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}
	/**
	 * Sets the Value Of ImagePath 
	 * @param imagePath 
	 * 			Setting the ImagePath
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	/**
	 * gets the Alt text
	 * @return String that contains the alttext
	 */
	public String getAltText() {
		return altText;
	}
	/**
	 * Sets AltText in Hero Image
	 * @param altText
	 * 			String that contains altText 			
	 */
	public void setAltText(String altText) {
		this.altText = altText;
	}
	/**
	 *  String that contains TagLineText
	 * @return get the String that contains TagLineText
	 */
	public String getTagLineText() {
		return tagLineText;
	}
	/**
	 * Sets the  TagLineText
	 * @param tagLineText
	 * 			String that contains TagLine text 
	 */
	public void setTagLineText(String tagLineText) {
		this.tagLineText = tagLineText;
	}
	/**
	 * Gets the imageInfo 
	 * @return ImageInfoBean
	 */
	public ImageInfoBean getImageInfo() {
		return imageInfo;
	}
	/**
	 * Sets the image info in the bean
	 * @param imageInfo
	 */
	public void setImageInfo(ImageInfoBean imageInfo) {
		this.imageInfo = imageInfo;
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
	/**
	 * @return the subTagLineText
	 */
	public String getSubTagLineText() {
		return subTagLineText;
	}
	/**
	 * @param subTagLineText the subTagLineText to set
	 */
	public void setSubTagLineText(String subTagLineText) {
		this.subTagLineText = subTagLineText;
	}
}
