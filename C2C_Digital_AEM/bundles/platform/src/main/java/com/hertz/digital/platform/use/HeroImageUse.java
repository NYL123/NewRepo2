package com.hertz.digital.platform.use;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.commons.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.adobe.granite.asset.api.Asset;

/**
 * The Use class for Hero component.
 * 
 * @author n.kumar.singhal
 *
 */
public class HeroImageUse extends WCMUsePojo {

	/**
	 * Default Constructor Declaration
	 */
	public HeroImageUse(){
		super();
	}
	/**
	 * LOGGER instantiation.
	 */
	private static final Logger logger = LoggerFactory.getLogger(HeroImageUse.class);
	/**
	 * Field for Path of Image
	 */
	private String imagePath;
	/**
	 * Fiel for altText
	 */
	private String altText;
	/**
	 * Field for the tagline
	 */
	private String tagLineText;
	
	/**
	 * Field for the subTagLine
	 * */
	private String subTagLineText;

	private static final Map<String, String> mimeTypes = createMap();

	private static final String DEFAULT_EXTENSION = "jpg";

	/**
	 * A method to create a Map of all the valid formats
	 * 
	 * @return Map of all the valid file formats
	 */
	private static Map<String, String> createMap() {
		Map<String, String> myMap = new HashMap<String, String>();
		myMap.put("image/png", "png");
		myMap.put("image/jpg", "jpg");
		myMap.put("image/jpeg", "jpg");
		myMap.put("image/gif", "gif");
		return myMap;
	}

	/**
	 * 
	 * Activate method for HeroImageUse Class. This method sets the Alt Text,
	 * TagLine Text and Image Path to be used in the Hero component.
	 * 
	 * @return void
	 * @throws JSONException
	 * 
	 */
	@Override
	public void activate() throws JSONException {
		logger.debug("******Entering activate method of HeroImageUse*******");

		setAltText(getProperties().get("altText", String.class));
		setTagLineText(getProperties().get("taglineText", String.class));
		setImagePath(processAssetPath(getProperties().get("fileReference", String.class)));
		setSubTagLineText(getProperties().get("subTagLineText", String.class));
		logger.debug("******Exiting activate method of HeroImageUse*******");
	}

	/**
	 * This method gets the path of the image.
	 * 
	 * @param image
	 *            String that contains the path of the Image in Hero Component
	 * @return Path of the Image
	 */
	private String processAssetPath(String image) {
		logger.debug("******Entering processAssetPath method of HeroImageUse*******");
		String processedAssetPath = StringUtils.EMPTY;
		if (StringUtils.isNotBlank(image) && null != getResourceResolver().getResource(image).adaptTo(Asset.class)) {

			processedAssetPath = image;

		} else if (null != getResourceResolver().getResource(getResource().getPath() + "/file")) {
			processedAssetPath = getResource().getPath() + "/file" + "."
					+ getExtension((String) getProperties().get("fileName"));
		}
		logger.debug("******Exiting processAssetPath method of HeroImageUse*******");
		return processedAssetPath;
	}

	/**
	 * This method gets the file extension of the Image in Hero Image
	 * 
	 * @param fileName
	 *            File Name of the image in the Hero Component
	 * @return String that contains the filetype of the Image
	 */
	private String getExtension(String fileName) {
		logger.debug("******Entering getExtension method of HeroImageUse*******");
		boolean isValid = false;
		String extension = StringUtils.EMPTY;
		if (StringUtils.isNotEmpty(fileName)) {
			extension = StringUtils.substring(fileName, StringUtils.lastIndexOf(fileName, ".") + 1,
					fileName.length() + 1);
			for (Entry<String, String> entry : mimeTypes.entrySet()) {
				if (entry.getValue().equalsIgnoreCase(extension)) {
					isValid = true;
					break;
				}
			}
		}
		if (isValid) {
			logger.debug("******Exiting getExtension method of HeroImageUse*******");
			return extension;
		} else {
			logger.debug("******Exiting getExtension method of HeroImageUse*******");
			return DEFAULT_EXTENSION;
		}
	}

	/*
	 * private String getExtension(Asset asset) { String extension =
	 * DEFAULT_EXTENSION; for (Entry<String, String> entry :
	 * mimeTypes.entrySet()) { if (entry.getKey().equalsIgnoreCase((String)
	 * asset.getValueMap().get("dc:format"))) { extension = entry.getValue(); }
	 * } return extension; }
	 */

	/**
	 * Gets the Path of the Image used in Hero Component
	 * 
	 * @return Path of the Image
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * Sets the Path of Image
	 * 
	 * @param imagePath
	 *            Path of the Image
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * Gets the AltText String in the Hero component
	 * 
	 * @return String that contains the ALt Text
	 */
	public String getAltText() {
		return altText;
	}

	/**
	 * Sets the Alt Text
	 * 
	 * @param altText
	 *            Value of the Alt text
	 */
	public void setAltText(String altText) {
		this.altText = altText;
	}

	/**
	 * Gets the Tag Line Text
	 * 
	 * @return String that contains the TagLine
	 */
	public String getTagLineText() {
		return tagLineText;
	}

	/**
	 * Sets the Tagline for the Hero component
	 * 
	 * @param tagLineText
	 *            String that is set as TagLine for the Hero Component
	 */
	public void setTagLineText(String tagLineText) {
		this.tagLineText = tagLineText;
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
