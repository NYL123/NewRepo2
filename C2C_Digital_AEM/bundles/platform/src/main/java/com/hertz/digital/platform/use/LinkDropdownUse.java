package com.hertz.digital.platform.use;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import com.adobe.cq.sightly.WCMUsePojo;
import com.hertz.digital.platform.bean.LinkDropdownBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.utils.HertzUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the use class for link drop down multifield values
 * 
 * @author himanshu.i.sharma
 * 
 */
public class LinkDropdownUse extends WCMUsePojo {
	/**
	 * Default Constructor
	 */
	public LinkDropdownUse(){
		super();
	}
	/**
	 * LOGGER instantiation.
	 */
	private static final Logger logger = LoggerFactory.getLogger(LinkDropdownUse.class);
	
	List<LinkDropdownBean> linkDropdownBeanList=new ArrayList<LinkDropdownBean>();
	/**
	 * 
	 * Activate method for LinkDropdownUse Class
	 * @param none
	 * @return void
	 * @throws JSONException
	 * 
	 * */
	@Override
	public void activate() throws JSONException  {
		logger.debug("******Entering activate method of LinkDropdownUse*******");
		getLinkDropdownValues();
		logger.debug("******Exiting activate method of LinkDropdownUse*******");
	}
	/**
	 * 
	 *This method is used to get all the Link Dropdown  component values
	 * @param none
	 * @return void
	 * @throws JSONException
	 * 
	 * */
	private void getLinkDropdownValues() throws JSONException {
		logger.debug("******Entering getLinkDropdownValues method of LinkDropdownUse*******");
		
		LinkDropdownBean linkDropdownBean;	
		String[] linkDropdownValuesArray = getProperties().get(HertzConstants.DROPDOWN_VALUE_LIST, String[].class);
		if(null!=linkDropdownValuesArray){
			for (int index = 0; index < linkDropdownValuesArray.length; index++) {
				JSONObject object = new JSONObject(linkDropdownValuesArray[index]);
				linkDropdownBean = new LinkDropdownBean();
				if (null != object) {
					setLinkDropdownBeanValues(linkDropdownBean, object);
				}
				linkDropdownBeanList.add(linkDropdownBean);
			}
		}

		logger.debug("******Exiting getLinkDropdownValues method of LinkDropdownUse*******");
	}
	
	/**
	 * This method is called from the getLinkDropdownValues() method and 
	 * is used to set all the values in the linkDropdownBean
	 * @param linkDropdownBean, object
	 * @return void
	 * @throws JSONException
	 * 
	 * */
	private void setLinkDropdownBeanValues(LinkDropdownBean linkDropdownBean, JSONObject object) throws JSONException {
		logger.debug("******Entering setLinkDropdownBeanValues method of LinkDropdownUse*******");
		
		if(StringUtils.isNotEmpty(object.optString(HertzConstants.DROPDOWN_VALUE))){
			String dropdownValue = object.optString(HertzConstants.DROPDOWN_VALUE);
			linkDropdownBean.setDropdownValue(dropdownValue);
		}
		if(StringUtils.isNotEmpty(object.optString(HertzConstants.ORDER))){
			String order = object.optString(HertzConstants.ORDER);
			linkDropdownBean.setOrder(order);
		}
		if(StringUtils.isNotEmpty(object.optString(HertzConstants.LINK_DROPDOWN_PAGE_URL))){
			String pageUrl = object.optString(HertzConstants.LINK_DROPDOWN_PAGE_URL);
			linkDropdownBean.setUrl(pageUrl);
		}
		
		logger.debug("******Exiting setLinkDropdownBeanValues method of LinkDropdownUse*******");
	}


	/**
	 * This method is called from the sightly code of link dropdown component and 
	 * is used to get the list of all the values of the link dropdown component
	 * @param none
	 * @return List<LinkDropdownBean>
	 *
	 * 
	 * */
	public List<LinkDropdownBean> getLinkDropdownBeanList() {
		return linkDropdownBeanList;
	}
	/**
	 * This method is used to set the list of all the values of the Link Dropdown  component
	 * @param linkDropdownBeanList
	 * @return void
	 *
	 * */
	public void setLinkDropdownBeanList(List<LinkDropdownBean> linkDropdownBeanList) {
		this.linkDropdownBeanList = linkDropdownBeanList;
	}

}

