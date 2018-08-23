package com.hertz.digital.platform.use;

import java.util.ArrayList;
import java.util.List;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hertz.digital.platform.bean.RQRLinksBean;
import com.hertz.digital.platform.bean.RQRSectionContainerBean;
import com.hertz.digital.platform.bean.RQRSectionsBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.utils.HertzUtils;
/**
 * Use class for setting the Rental Qualification Requirements
 * @author himanshu.i.sharma
 *
 */
public class RentalQualificationUse{

	/**
	 * Default Constructor Declaration
	 */
	public RentalQualificationUse(){
		super();
	}

	/**
	 * LOGGER instantiation
	 */
	private static final Logger logger = LoggerFactory.getLogger(RentalQualificationUse.class);
	
	/**
	 * This method gets the parent page resource in the content hierarchy
	 * @param pageResource
	 * @param rQRSectionContainerBean
	 * @throws JSONException
	 * @throws RepositoryException
	 */
	public void getParentPageResource(Resource pageResource, RQRSectionContainerBean rQRSectionContainerBean) throws JSONException, RepositoryException{
		logger.debug("Start:- getParentPageResource() of RentalQualificationUse");
		List<RQRSectionsBean> rQRSectionsBeanList = new ArrayList<RQRSectionsBean>();
		setSectionLinkValuesInBean(pageResource, rQRSectionsBeanList);
		Resource cityPageResource = null;
		Resource stateRegionPageResource = null;
		Resource countryPageResource = null;
		Resource globalPageResource = null;
		if(null!=pageResource.getParent()){
			cityPageResource = pageResource.getParent();
			setSectionLinkValuesInBean(cityPageResource, rQRSectionsBeanList);
		}
		if(null!=cityPageResource.getParent()){
			stateRegionPageResource = cityPageResource.getParent();
			setSectionLinkValuesInBean(stateRegionPageResource, rQRSectionsBeanList);
		}
		if(null!=stateRegionPageResource.getParent()){
			countryPageResource = stateRegionPageResource.getParent();
			setSectionLinkValuesInBean(countryPageResource, rQRSectionsBeanList);
		}
		if(null!=countryPageResource.getParent()){
			globalPageResource = countryPageResource.getParent();
			setSectionLinkValuesInBean(globalPageResource, rQRSectionsBeanList);
		}		
		rQRSectionContainerBean.setRQRSectionsBeanList(rQRSectionsBeanList);
		logger.debug("End:- getParentPageResource() of RentalQualificationUse");
	}
	
	/**
	 * This method gets the section link values and sets in the section bean
	 * @param sectionPageResource
	 * @param rQRSectionsBeanList
	 * @throws JSONException
	 * @throws RepositoryException
	 */
	private void setSectionLinkValuesInBean(Resource sectionPageResource, List<RQRSectionsBean> rQRSectionsBeanList) throws JSONException, RepositoryException{
		logger.debug("Start:- setSectionLinkValuesInBean() of RentalQualificationUse");
		
		RQRSectionsBean rQRSectionsBean = new RQRSectionsBean();
		List<RQRLinksBean> rQRLinksBeanList = new ArrayList<RQRLinksBean>();
		String sectionName = StringUtils.EMPTY;
		ValueMap componentProperties = null;
		Resource parResource = null;
		Resource jcrContentResource = sectionPageResource.getChild(HertzConstants.JCR_CONTENT);
		if (null != jcrContentResource.getChild(HertzConstants.PAR)) {
			parResource = jcrContentResource.getChild(HertzConstants.PAR);
			Iterable<Resource> sectionComponents = parResource.getChildren();
			for (Resource componentResource : sectionComponents) {
				componentProperties = componentResource.getValueMap();
				Node componentResourceNode = componentResource.adaptTo(Node.class);
				if(componentResourceNode.hasProperty(HertzConstants.SECTION_NAME) && componentResourceNode.hasProperty(HertzConstants.CONFIGURABLE_LINKS)){
					sectionName = HertzUtils.getValueFromMap(componentProperties, HertzConstants.SECTION_NAME).trim();
					getConfiguredSectionLinkItems(componentResourceNode, rQRSectionsBean, sectionName, rQRLinksBeanList);
					rQRSectionsBeanList.add(rQRSectionsBean);
				}
			}
		}
				
		logger.debug("End:- setSectionLinkValuesInBean() of RentalQualificationUse");
	}
	
	/**
	 * This method gets the configured links property
	 * @param jcrContentResourceNode
	 * @param rQRSectionsBean
	 * @param sectionName
	 * @param rQRLinksBeanList
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 * @throws JSONException
	 */
	private void getConfiguredSectionLinkItems(Node jcrContentResourceNode, RQRSectionsBean rQRSectionsBean, String sectionName, List<RQRLinksBean> rQRLinksBeanList) throws PathNotFoundException, RepositoryException, JSONException{
		logger.debug("Start:- getConfiguredSectionLinkItems() of RentalQualificationUse");
		
		Property configurableMultiLinkItems = jcrContentResourceNode.getProperty(HertzConstants.CONFIGURABLE_LINKS);
		if(jcrContentResourceNode.getProperty(HertzConstants.CONFIGURABLE_LINKS).isMultiple()){
			getConfiguredSectionLinkItemsMultiple(configurableMultiLinkItems, jcrContentResourceNode, rQRSectionsBean, sectionName, rQRLinksBeanList);
		}
		else{
			getConfiguredSectionLinkItemsSingle(configurableMultiLinkItems, jcrContentResourceNode, rQRSectionsBean, sectionName, rQRLinksBeanList);
		}
		
		logger.debug("End:- getConfiguredSectionLinkItems() of RentalQualificationUse");
	}
	
	/**
	 * This method gets the multiple configured links
	 * @param configurableMultiLinkItems
	 * @param jcrContentResourceNode
	 * @param rQRSectionsBean
	 * @param sectionName
	 * @param rQRLinksBeanList
	 * @throws ValueFormatException
	 * @throws RepositoryException
	 * @throws JSONException
	 */
	private void getConfiguredSectionLinkItemsMultiple(Property configurableMultiLinkItems, Node jcrContentResourceNode, RQRSectionsBean rQRSectionsBean, String sectionName, List<RQRLinksBean> rQRLinksBeanList) throws ValueFormatException, RepositoryException, JSONException{
		logger.debug("Start:- getConfiguredSectionLinkItemsMultiple() of RentalQualificationUse");
		
		Value[] values = configurableMultiLinkItems.getValues();
		for (Value v : values) {
			String configurableMultiLinkItemsProperty = "["+v.getString()+"]";
			getConfiguredSectionLinkItemsJson(configurableMultiLinkItemsProperty, rQRSectionsBean, sectionName, rQRLinksBeanList);
		}
		
		logger.debug("End:- getConfiguredSectionLinkItemsMultiple() of RentalQualificationUse");
	}
	
	/**
	 * This method gets the single configured link
	 * @param configurableMultiLinkItems
	 * @param jcrContentResourceNode
	 * @param rQRSectionsBean
	 * @param sectionName
	 * @param rQRLinksBeanList
	 * @throws ValueFormatException
	 * @throws RepositoryException
	 * @throws JSONException
	 */
	private void getConfiguredSectionLinkItemsSingle(Property configurableMultiLinkItems, Node jcrContentResourceNode, RQRSectionsBean rQRSectionsBean, String sectionName, List<RQRLinksBean> rQRLinksBeanList) throws ValueFormatException, RepositoryException, JSONException{
		logger.debug("Start:- getConfiguredSectionLinkItemsSingle() of RentalQualificationUse");
		
		Value value = configurableMultiLinkItems.getValue();
		String configurableMultiLinkItemsProperty = "["+value.getString()+"]";
		getConfiguredSectionLinkItemsJson(configurableMultiLinkItemsProperty, rQRSectionsBean, sectionName, rQRLinksBeanList);
		
		logger.debug("End:- getConfiguredSectionLinkItemsSingle() of RentalQualificationUse");
	}
	
	/**
	 * This method gets the configured links as json string and sets the values in links bean
	 * @param configurableMultiLinkItemsProperty
	 * @param rQRSectionsBean
	 * @param sectionName
	 * @param rQRLinksBeanList
	 * @throws JSONException
	 */
	private void getConfiguredSectionLinkItemsJson(String configurableMultiLinkItemsProperty, RQRSectionsBean rQRSectionsBean, String sectionName, List<RQRLinksBean> rQRLinksBeanList) throws JSONException{
		logger.debug("Start:- getConfiguredSectionLinkItemsJson() of RentalQualificationUse");
		
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
			rQRSectionsBean.setSectionName(sectionName);
			rQRSectionsBean.setRQRLinksBeanList(rQRLinksBeanList);
		}
		
		logger.debug("End:- getConfiguredSectionLinkItemsJson() of RentalQualificationUse");
	}
}
