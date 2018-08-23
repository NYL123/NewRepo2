package com.hertz.digital.platform.bean;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * Bean representing the standard image information published through the
 * content service
 *
 * @author paul.m.mcmahon
 */
public class ImageInfoBean {

	private static final Logger logger = LoggerFactory.getLogger(ImageInfoBean.class);

	private static final String STANDARD_FILE_REF_PROPERTY = "fileReference";
	private static final String DENSITY = "density";
	private static final String SMALL = "small";
	private static final String LARGE = "large";

	private transient String large2xPath = "";
	private transient String large1xPath = "";
	private transient String small2xPath = "";
	private transient String small1xPath = "";

	@Expose
	@SerializedName("sources")
	private final List<Map<String, Object>> sources = new LinkedList<Map<String, Object>>();

	private String title = "";

	/**
	 * Constructor - assumes the component uses the standard file reference
	 * property name - fileReference.
	 *
	 * If the component uses a different property call ImageInfoBean(Resource,
	 * String)
	 *
	 * @param imageComponent
	 *            Sling Resource that contains the image properties
	 */
	public ImageInfoBean(Resource imageComponent) {
		super();
		initializeBean(imageComponent, STANDARD_FILE_REF_PROPERTY, false);
	}

	/**
	 * Constructor allow the file reference property to be passed in.
	 *
	 * @param imageComponent
	 *            Sling Resource that contains the image properties
	 * @param fileRefPath
	 *            String can be either a property name, or a child node name -
	 *            boolean indicates which
	 * @param onChild
	 *            - if set to true the bean assumes the fileRefPath is a node
	 *            name containing the image info with standard file names. If
	 *            false it assuumes the file ref paramtered refers to a property
	 *            name on the current node.
	 */
	public ImageInfoBean(Resource imageComponent, String fileRefPath, boolean onChild) {
		super();
		initializeBean(imageComponent, fileRefPath, onChild);
	}

	private void initializeBean(Resource imageComponent, String fileRefProperty, boolean onChild) {
		boolean errorFound = false;
		if (null == imageComponent) {
			logger.error("ImageInfoBean called with a null imageComponent");
			errorFound = true;
		}
		if (StringUtils.isBlank(fileRefProperty)) {
			logger.error("ImageInfoBean called with a empty fileRefProperty");
			errorFound = true;
		}

		infoBeanChild(errorFound, imageComponent, errorFound, fileRefProperty);

		if (!errorFound) {
			createRenditionMap(imageComponent, fileRefProperty, onChild);
		}
	}

	private void infoBeanChild(boolean errorFound, Resource imageComponent, boolean onChild, String fileRefProperty) {
		if (!errorFound && onChild && imageComponent.hasChildren()) {
			Resource child = imageComponent.getChild(fileRefProperty);
			if (null == child) {
				logger.error("ImageInfoBean no child node at {}  - can not be valid", fileRefProperty);
				errorFound = true;
			} else {
				ValueMap properties = child.getValueMap();
				if (StringUtils.isBlank(properties.get(STANDARD_FILE_REF_PROPERTY, String.class))) {
					logger.error(
							"ImageInfoBean child node at {} does not have a valid file ref property - can not be valid",
							fileRefProperty);
					errorFound = true;
				}
			}
		} else if (!errorFound && onChild) {
			logger.error("ImageInfoBean - on child parameter set to true, no child at {} - not a valid bean",
					fileRefProperty);
			errorFound = true;
		}
	}

	/**
	 * @param imageComponent
	 * @param fileRefProperty
	 * @param onChild
	 */
	private void createRenditionMap(Resource imageComponent, String fileRefProperty, boolean onChild) {
		String fileRefPropName = fileRefProperty;
		ValueMap properties = imageComponent.getValueMap();
		if (onChild) {
			fileRefPropName = STANDARD_FILE_REF_PROPERTY;
		}
		String fileReference = properties.get(fileRefPropName, String.class);
		large2xPath = fileReference;
		large1xPath = fileReference;
		small2xPath = fileReference;
		small1xPath = fileReference;
		String componentPath = imageComponent.getPath();

		LinkedHashMap<String, Object> smallImages = new LinkedHashMap<String, Object>();

		LinkedList<Map<String, String>> smallRenditions = new LinkedList<Map<String, String>>();

		LinkedHashMap<String, String> small1x = new LinkedHashMap<String, String>();
		small1x.put(DENSITY, "1x");
		small1x.put("src", HertzUtils.transformImageURL(imageComponent, SMALL, "1x", "png"));
		if (null == small1x.get("src"))
			small1x.put("src", StringUtils.EMPTY);
		smallRenditions.add(small1x);

		LinkedHashMap<String, String> small2x = new LinkedHashMap<String, String>();
		small2x.put(DENSITY, "2x");
		small2x.put("src", HertzUtils.transformImageURL(imageComponent, SMALL, "2x", "png"));
		if (null == small2x.get("src"))
			small2x.put("src", StringUtils.EMPTY);
		smallRenditions.add(small2x);

		smallImages.put("size", SMALL);
		smallImages.put("renditions", smallRenditions);

		sources.add(smallImages);

		LinkedHashMap<String, Object> largeImages = new LinkedHashMap<String, Object>();

		LinkedList<Map<String, String>> largeRenditions = new LinkedList<Map<String, String>>();

		LinkedHashMap<String, String> large1x = new LinkedHashMap<String, String>();
		large1x.put(DENSITY, "1x");
		large1x.put("src", HertzUtils.transformImageURL(imageComponent, LARGE, "1x", "png"));
		if (null == large1x.get("src"))
			large1x.put("src", StringUtils.EMPTY);
		largeRenditions.add(large1x);

		LinkedHashMap<String, String> large2x = new LinkedHashMap<String, String>();
		large2x.put(DENSITY, "2x");
		large2x.put("src", HertzUtils.transformImageURL(imageComponent, LARGE, "2x", "png"));
		if (null == large2x.get("src"))
			large2x.put("src", StringUtils.EMPTY);
		largeRenditions.add(large2x);

		largeImages.put("size", LARGE);
		largeImages.put("renditions", largeRenditions);

		sources.add(largeImages);
	}

	/**
	 * Getter for Sources List
	 *
	 * @return List
	 */
	public List<Map<String, Object>> getSources() {
		return sources;
	}

	/**
	 * getter for title
	 *
	 * @return String
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Getter for large 2x path
	 * 
	 * @return String
	 */
	public String getLarge2xPath() {
		return large2xPath;
	}

	/**
	 * Getter for large 1x path
	 * 
	 * @return String
	 */
	public String getLarge1xPath() {
		return large1xPath;
	}

	/**
	 * Getter for small 2x path
	 * 
	 * @return String
	 */
	public String getSmall2xPath() {
		return small2xPath;
	}

	/**
	 * Getter for smalle 1x path
	 * 
	 * @return String
	 */
	public String getSmall1xPath() {
		return small1xPath;
	}

	/**
	 * Setter for Title - alt text
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
