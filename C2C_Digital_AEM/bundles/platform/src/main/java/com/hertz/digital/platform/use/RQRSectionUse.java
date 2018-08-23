package com.hertz.digital.platform.use;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.hertz.digital.platform.bean.RQRLinksBean;
import com.hertz.digital.platform.constants.HertzConstants;

/**
 * Use class for displaying the multifield of the links in rqr section component
 * Component in the HTML
 * 
 * @author himanshu.i.sharma
 *
 */
public class RQRSectionUse extends WCMUsePojo {

	/**
	 * Default Constructor Declaration
	 */
	public RQRSectionUse() {
		super();
	}

	/**
	 * LOGGER instantiation
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RQRSectionUse.class);
	
	/**
	 * Multi field link values are set in this bean list
	 */
	private List<RQRLinksBean> rQRLinksBeanList = new ArrayList<>();
	
	/**
	 * Activate method
	 */
	@Override
	public void activate() throws PathNotFoundException, ValueFormatException, IllegalStateException, RepositoryException, JSONException{
		LOGGER.debug("Start:- activate() of RQRSectionUse");
		getConfiguredSectionLinkItems();
		LOGGER.debug("End:- activate() of RQRSectionUse");
	}
	
	/**
	 * 
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 * @throws JSONException
	 */
	public void getConfiguredSectionLinkItems() throws PathNotFoundException, RepositoryException, JSONException{
		LOGGER.debug("Start:- getConfiguredSectionLinkItems() of RQRSectionUse");
		Node jcrContentResourceNode = getResource().adaptTo(Node.class);
		Property configurableMultiLinkItems = jcrContentResourceNode.getProperty(HertzConstants.CONFIGURABLE_LINKS);
		if(jcrContentResourceNode.getProperty(HertzConstants.CONFIGURABLE_LINKS).isMultiple()){
			getConfiguredSectionLinkItemsMultiple(configurableMultiLinkItems, jcrContentResourceNode);
		}
		else{
			getConfiguredSectionLinkItemsSingle(configurableMultiLinkItems, jcrContentResourceNode);
		}
		LOGGER.debug("End:- getConfiguredSectionLinkItems() of RQRSectionUse");
	}
	
	/**
	 * 
	 * @param configurableMultiLinkItems
	 * @param jcrContentResourceNode
	 * @throws ValueFormatException
	 * @throws RepositoryException
	 * @throws JSONException
	 */
	public void getConfiguredSectionLinkItemsMultiple(Property configurableMultiLinkItems, Node jcrContentResourceNode) throws ValueFormatException, RepositoryException, JSONException{
		LOGGER.debug("Start:- getConfiguredSectionLinkItemsMultiple() of RQRSectionUse");
		
		Value[] values = configurableMultiLinkItems.getValues();
		for (Value v : values) {
			String configurableMultiLinkItemsProperty = "["+v.getString()+"]";
			getConfiguredSectionLinkItemsJson(configurableMultiLinkItemsProperty);
		}
		LOGGER.debug("End:- getConfiguredSectionLinkItemsMultiple() of RQRSectionUse");
	}
	
	/**
	 * 
	 * @param configurableMultiLinkItems
	 * @param jcrContentResourceNode
	 * @throws ValueFormatException
	 * @throws RepositoryException
	 * @throws JSONException
	 */
	public void getConfiguredSectionLinkItemsSingle(Property configurableMultiLinkItems, Node jcrContentResourceNode) throws ValueFormatException, RepositoryException, JSONException{
		LOGGER.debug("Start:- getConfiguredSectionLinkItemsSingle() of RQRSectionUse");
		
		Value value = configurableMultiLinkItems.getValue();
		String configurableMultiLinkItemsProperty = "["+value.getString()+"]";
		getConfiguredSectionLinkItemsJson(configurableMultiLinkItemsProperty);
		
		LOGGER.debug("End:- getConfiguredSectionLinkItemsSingle() of RQRSectionUse");
	}
	
	/**
	 * 
	 * @param configurableMultiLinkItemsProperty
	 * @throws JSONException
	 */
	public void getConfiguredSectionLinkItemsJson(String configurableMultiLinkItemsProperty) throws JSONException{
		LOGGER.debug("Start:- getConfiguredSectionLinkItemsJson() of RQRSectionUse");
		
		JSONArray jsonarray = new JSONArray(configurableMultiLinkItemsProperty);
		for (int i=0; i < jsonarray.length(); i++){
			RQRLinksBean rQRLinksBean = new RQRLinksBean();
			JSONObject configurableMultiLinkItemsJson = jsonarray.getJSONObject(i);
			String linkName = (String)configurableMultiLinkItemsJson.get(HertzConstants.LINK_NAME);
			String linkURL = (String)configurableMultiLinkItemsJson.get(HertzConstants.LINK_URL);
			String linkAltText = (String)configurableMultiLinkItemsJson.get(HertzConstants.LINK_ALT_TEXT);
			rQRLinksBean.setLinkName(linkName);
			rQRLinksBean.setLinkURL(linkURL);
			rQRLinksBean.setLinkAltText(linkAltText);
			rQRLinksBeanList.add(rQRLinksBean);
		}
		
		LOGGER.debug("End:- getConfiguredSectionLinkItemsJson() of RQRSectionUse");
	}
	
	public List<RQRLinksBean> getRQRLinksBeanList() {
		return rQRLinksBeanList;
	}
	public void setRQRLinksBeanList(List<RQRLinksBean> rQRLinksBeanList) {
		this.rQRLinksBeanList = rQRLinksBeanList;
	}

}
