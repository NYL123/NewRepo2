/**
 * 
 */
package com.hertz.digital.platform.use;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.hertz.digital.platform.bean.HertzLinkDropdownBean;
import com.hertz.digital.platform.constants.HertzConstants;

/**
 * @author a.dhingra
 *
 */
public class HertzLinkDropdownUse extends WCMUsePojo {
	
	/**
	 * Default Constructor Declaration
	 */
	public HertzLinkDropdownUse(){
		super();
	}
	/**
	 * LOGGER instantiation.
	 */
	private static final Logger logger = LoggerFactory.getLogger(HertzLinkDropdownUse.class);
	
	private List<HertzLinkDropdownBean> valuesList=new ArrayList<>();
	
	@Override
	public void activate() throws Exception {
		logger.debug("Start:- Activate() method in HertzLinkDropdownUse ");
		ValueMap map=getResource().getValueMap();
		if(map.containsKey(HertzConstants.LINK)){
			String [] link=map.get(HertzConstants.LINK,String[].class);
			for(String string : link){
				JSONObject object=new JSONObject(string);
				HertzLinkDropdownBean linkDropDown=new HertzLinkDropdownBean();
				linkDropDown.setLabel(object.optString(HertzConstants.LABEL));
				linkDropDown.setLinkType(object.optString(HertzConstants.LINK_TYPE));
				linkDropDown.setSequenceId(object.optString(HertzConstants.SEQUENCE_ID));
				linkDropDown.setTargetType(object.optString(HertzConstants.TARGET_TYPE));
				linkDropDown.setCdpCode(object.optString(HertzConstants.CDP_CODE));
				linkDropDown.setRqCode(object.optString(HertzConstants.RQ_CODE));
				linkDropDown.setPcCode(object.optString(HertzConstants.PC_CODE));
				valuesList.add(linkDropDown);
			}
		}
		logger.debug("Exit : - Activate() method in HertzLinkDropdownUse ");
		
	}

	/**
	 * @return the valuesList
	 */
	public List<HertzLinkDropdownBean> getValuesList() {
		return valuesList;
	}

	/**
	 * @param valuesList the valuesList to set
	 */
	public void setValuesList(List<HertzLinkDropdownBean> valuesList) {
		this.valuesList = valuesList;
	}

}
