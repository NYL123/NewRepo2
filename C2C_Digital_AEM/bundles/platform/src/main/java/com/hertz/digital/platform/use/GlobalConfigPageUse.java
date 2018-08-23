package com.hertz.digital.platform.use;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.hertz.digital.platform.bean.SecretsBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * Use class for checking if the default cp code has been
 * configured on the global config data page or not.
 * 
 * @author himanshu.i.sharma
 *
 */
public class GlobalConfigPageUse extends WCMUsePojo {

	/**
	 * Default Constructor Declaration
	 */
	public GlobalConfigPageUse() {
		super();
	}

	/**
	 * LOGGER instantiation
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalConfigPageUse.class);
	
	/**
	 * key values are set in this bean list
	 */
	private List<SecretsBean> beanList = new ArrayList<>();
	
	/**
	 * Activate method
	 */
	@Override
	public void activate() {
		LOGGER.debug("Start:- activate() of GlobalConfigPageUse");
		getConfiguredKeyValuePairs();
		LOGGER.debug("End:- activate() of GlobalConfigPageUse");
	}
	
	/**
	 * This method gets the page content resource and checks
	 * if par resource exists. Calls setKeyValueInBean if 
	 * component resources exist under par
	 */
	public void getConfiguredKeyValuePairs(){
		LOGGER.debug("Start:- getConfiguredSecretsKeyValuePairs() of GlobalConfigPageUse");
		
		Resource jcrContentResource = getResource();
		if(null!=jcrContentResource.getChild(HertzConstants.PAR)){
			Resource parResource = jcrContentResource.getChild(HertzConstants.PAR);
		    Iterable<Resource> componentResources = parResource.getChildren();
		    for(Resource componentResource : componentResources){
			    setKeyValueInBean(componentResource);
		    }
		}
		
		LOGGER.debug("End:- getConfiguredSecretsKeyValuePairs() of GlobalConfigPageUse");
	}
	
	/**
	 * This method retrieves the key value pair from
	 * component resource valuemap and sets the values 
	 * in a bean
	 * 
	 * @param componentResource
	 */
	public void setKeyValueInBean(Resource componentResource){
		LOGGER.debug("Start:- getConfiguredSecretsItemsJson() of GlobalConfigPageUse");
		
		ValueMap componentProperties = componentResource.getValueMap();
		SecretsBean bean = new SecretsBean();
		String key = HertzUtils.getValueFromMap(componentProperties, HertzConstants.KEY).trim();
		String value = HertzUtils.getValueFromMap(componentProperties, HertzConstants.VALUE).trim();
		if(!key.equals(StringUtils.EMPTY) && !value.equals(StringUtils.EMPTY)){
			bean.setKey(key);
		    bean.setKey(value);
		    beanList.add(bean);
		}
		
		LOGGER.debug("End:- getConfiguredSecretsItemsJson() of GlobalConfigPageUse");
	}
	
	/**
	 * getter for bean list
	 * @return
	 */
	public List<SecretsBean> getBeanList() {
		return beanList;
	}
	
	/**
	 * setter for bean list
	 * @param beanList
	 */
	public void setBeanList(List<SecretsBean> beanList) {
		this.beanList = beanList;
	}

}
