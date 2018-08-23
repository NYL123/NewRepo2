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
import com.hertz.digital.platform.bean.LoginFlyOutItemsBean;
import com.hertz.digital.platform.constants.HertzConstants;

public class LoginFlyoutItemsUse extends WCMUsePojo {
	/**
	 * Default Constructor
	 */
	public LoginFlyoutItemsUse(){
		super();
	}
	
	private static final Logger logger = LoggerFactory.getLogger(LoginFlyoutItemsUse.class);

	private List<LoginFlyOutItemsBean> flyOutItems = new ArrayList<>();
	
	@Override
	public void activate() throws PathNotFoundException, ValueFormatException, IllegalStateException, RepositoryException, JSONException{
		logger.debug("******Entering activate method of LoginFlyoutItemsUse*******");
		getFlyoutItemsValues();
	}
	
	public void getFlyoutItemsValues() throws PathNotFoundException, ValueFormatException, IllegalStateException, RepositoryException, JSONException {
		Node loginFlyoutItemsNode=getResource().adaptTo(Node.class);
		if(loginFlyoutItemsNode.hasProperty(HertzConstants.LOGIN_ITEMS)){
			getLoginFlyoutItems(loginFlyoutItemsNode);
		}
	}
	
	public void getLoginFlyoutItems(Node loginNode) throws PathNotFoundException, RepositoryException, JSONException{
		Property flyOutItemsProperty = loginNode.getProperty(HertzConstants.LOGIN_ITEMS);
		if(loginNode.getProperty(HertzConstants.LOGIN_ITEMS).isMultiple()){
			getLoginFlyoutItemsMultiple(flyOutItemsProperty, loginNode);
		}
		else{
			getLoginFlyoutItemsSingle(flyOutItemsProperty, loginNode);
		}
	}
	
	public void getLoginFlyoutItemsMultiple(Property flyOutItemsProperty, Node loginNode) throws PathNotFoundException, RepositoryException, JSONException{
		Value[] values = flyOutItemsProperty.getValues();
		for (Value v : values) {
			String strFlyOutItemsProperty = "["+v.getString()+"]";
			getLoginFlyoutItemsJson(strFlyOutItemsProperty);
		}
	}
	
	public void getLoginFlyoutItemsSingle(Property flyOutItemsProperty, Node loginNode) throws PathNotFoundException, RepositoryException, JSONException{
		Value value = flyOutItemsProperty.getValue();
		String strFlyOutItemsProperty = "["+value.getString()+"]";
		getLoginFlyoutItemsJson(strFlyOutItemsProperty);
	}
	
	public void getLoginFlyoutItemsJson(String strFlyOutItemsProperty) throws JSONException{
		LoginFlyOutItemsBean loginFlyOutItemsBean = new LoginFlyOutItemsBean();
		JSONArray jsonarray = new JSONArray(strFlyOutItemsProperty);
		for (int i=0; i < jsonarray.length(); i++){
			JSONObject flyoutJson = jsonarray.getJSONObject(i);
			String flyOutItemTxt = (String)flyoutJson.get("flyoutItemTxt");
			String flyOutItemPath = (String)flyoutJson.get("flyoutItemPath");
			Boolean ctaAction = (Boolean)flyoutJson.get("openUrlNewWindow");
			loginFlyOutItemsBean.setFlyoutItemTxt(flyOutItemTxt);
			loginFlyOutItemsBean.setFlyoutItemPath(flyOutItemPath);
			loginFlyOutItemsBean.setCtaAction(ctaAction);
			flyOutItems.add(loginFlyOutItemsBean);
		}
	}
	
	public List<LoginFlyOutItemsBean> getFlyOutItems() {
		return flyOutItems;
	}
	public void setFlyOutItems(List<LoginFlyOutItemsBean> flyOutItems) {
		this.flyOutItems = flyOutItems;
	}
}
