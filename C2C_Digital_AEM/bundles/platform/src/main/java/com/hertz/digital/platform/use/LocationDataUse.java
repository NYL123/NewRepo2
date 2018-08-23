package com.hertz.digital.platform.use;

import javax.jcr.AccessDeniedException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.hertz.digital.platform.bean.LocationBean;
import com.hertz.digital.platform.bean.LocationDataBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * Use class for setting the Hours of Operation data in LocationDataBean
 * 
 * @author puneet.soni
 *
 */
public class LocationDataUse extends WCMUsePojo {

	/**
	 * Default Constructor Declaration
	 */
	public LocationDataUse() {
		super();
	}

	private static final String HOURS_OF_OPERATION_1 = "hoursOfOperation1";
	private static final String HOURS_OF_OPERATION_2 = "hoursOfOperation2";
	private static final String HOURS_OF_OPERATION_3 = "hoursOfOperation3";

	/**
	 * LOGGER instantiation
	 */
	private static final Logger logger = LoggerFactory.getLogger(LocationDataUse.class);

	/**
	 * JSON String
	 */
	private String jsonString;

	/**
	 * Activate method that sets the Hours of Operation data in LocationDataBean
	 */
	@Override
	public void activate() throws Exception {
		logger.debug("Start:- activate() of LocationDataUse");
		prepareLocationJson(getCurrentPage());
		logger.debug("End:- activate() of LocationDataUse");
	}

	/**
	 * Interfacing method, to be called from notification service. Accepts a
	 * page object and renders the containing JSON representation of data.
	 * 
	 * @param page
	 *            The page object.
	 * @return The JSON string.
	 * @throws AccessDeniedException
	 * @throws ItemNotFoundException
	 * @throws RepositoryException
	 */
	public String prepareLocationJson(Page page)
			throws AccessDeniedException, ItemNotFoundException, RepositoryException {
		if (null != page) {
			LocationDataBean locationDataBean = new LocationDataBean();
			LocationBean locationBean = new LocationBean();
			Node locatonDataNode = page.adaptTo(Node.class);
			setHoursOfOperationVariable(locatonDataNode, locationBean);
			locationBean.setOag(locatonDataNode.getName());
			Node cityNode = locatonDataNode.getParent();
			String cityTitle = getCityName(cityNode);
			locationBean.setCity(cityTitle);
			Node stateNode = cityNode.getParent();
			locationBean.setStateOrProvince(stateNode.getName());
			Node countryNode = stateNode.getParent();
			locationBean.setCountry(countryNode.getName());
			Node regionNode = countryNode.getParent();
			locationBean.setRegion(regionNode.getName());
			Node languageNode = regionNode.getParent();
			locationBean.setLanguage(languageNode.getName());
			locationDataBean.setLocationBean(locationBean);
			Gson gson = HertzUtils.initGsonBuilder(true, true, true);
			jsonString = gson.toJson(locationDataBean);
		}
		return jsonString;
	}

	/**
	 * Get the JCR:title property for the city Node
	 * 
	 * @param cityNode
	 *            Node for the city data Template
	 * @return String that contains the jcr:title of the city
	 * @throws RepositoryException
	 *             RepositoryException
	 */
	private String getCityName(Node cityNode) throws RepositoryException {
		String citytTitle = StringUtils.EMPTY;
		Node cityJCRNode = cityNode.getNode(HertzConstants.JCR_CONTENT);
		if (cityJCRNode.hasProperty(HertzConstants.JCR_TITLE_PROPERTY)) {
			Property cityTitleProperty = cityJCRNode.getProperty(HertzConstants.JCR_TITLE_PROPERTY);
			citytTitle = cityTitleProperty.getString();
		}
		return citytTitle;
	}

	/**
	 * Sets the Hours of Operation Variable in LocationBean
	 * 
	 * @param locatonDataNode
	 *            Node of the Location Data
	 * @param locationBean
	 *            Bean in which HOurs of Operation data is stored
	 * @throws RepositoryException
	 *             RepositoryException
	 */
	private void setHoursOfOperationVariable(Node locatonDataNode, LocationBean locationBean)
			throws RepositoryException {
		logger.debug("Start:- setHoursOfOperationVariable() of LocationDataUse");
		String hoursOfOperation1 = StringUtils.EMPTY;
		String hoursOfOperation2 = StringUtils.EMPTY;
		String hoursOfOperation3 = StringUtils.EMPTY;
		Node locatonDataJcrNode = locatonDataNode.getNode(HertzConstants.JCR_CONTENT);
		if (locatonDataJcrNode.hasProperty(HOURS_OF_OPERATION_1)) {
			Property hoursOfOperation1Property = locatonDataJcrNode.getProperty(HOURS_OF_OPERATION_1);
			hoursOfOperation1 = hoursOfOperation1Property.getString();
			locationBean.setHoursOfOperation1(hoursOfOperation1);
		}
		if (locatonDataJcrNode.hasProperty(HOURS_OF_OPERATION_2)) {
			Property hoursOfOperation2Property = locatonDataJcrNode.getProperty(HOURS_OF_OPERATION_2);
			hoursOfOperation2 = hoursOfOperation2Property.getString();
			locationBean.setHoursOfOperation2(hoursOfOperation2);
		}
		if (locatonDataJcrNode.hasProperty(HOURS_OF_OPERATION_3)) {
			Property hoursOfOperation3Property = locatonDataJcrNode.getProperty(HOURS_OF_OPERATION_3);
			hoursOfOperation3 = hoursOfOperation3Property.getString();
			locationBean.setHoursOfOperation3(hoursOfOperation3);
		}
		logger.debug("End:- setHoursOfOperationVariable() of LocationDataUse");
	}

	/**
	 * Getter for the JSON String
	 * 
	 * @return Json String
	 */
	public String getJsonString() {
		return jsonString;
	}

	/**
	 * Setter for the JSON String
	 * 
	 * @param jsonString
	 */
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

}
