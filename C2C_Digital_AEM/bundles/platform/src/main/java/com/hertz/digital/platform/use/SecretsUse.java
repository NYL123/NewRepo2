package com.hertz.digital.platform.use;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.hertz.digital.platform.bean.SecretsBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.utils.EncryptDecryptUtils;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * Use class for displaying the multifield of the secrets component
 * in the HTML and generate a secrets.bin file in the directory provided
 * by the author containing the encrypted HashMap info
 * 
 * @author himanshu.i.sharma
 *
 */
public class SecretsUse extends WCMUsePojo {

	/**
	 * Default Constructor Declaration
	 */
	public SecretsUse() {
		super();
	}

	/**
	 * LOGGER instantiation
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SecretsUse.class);
	
	/**
	 * Multi field key values are set in this bean list
	 */
	private List<SecretsBean> secretsBeanList = new ArrayList<>();
	
	/**
	 * Activate method
	 * @throws IOException 
	 */
	@Override
	public void activate() {
		LOGGER.debug("Start:- activate() of SecretsUse");
		try {
			getConfiguredSecretsKeyValuePairs();
		} catch (FileNotFoundException e) {
			LOGGER.error("SecretsUse.activate(): File not found exception {}", e);
		} catch (PathNotFoundException e) {
			LOGGER.error("SecretsUse.activate(): Path not found exception {}", e);
		} catch (RepositoryException e) {
			LOGGER.error("SecretsUse.activate(): Repository exception {}", e);
		} catch (JSONException e) {
			LOGGER.error("SecretsUse.activate(): JSON exception {}", e);
		} catch (IOException e) {
			LOGGER.error("SecretsUse.activate(): IOException {}", e);
		}
		LOGGER.debug("End:- activate() of SecretsUse");
	}
	
	/**
	 * This method reads the key and iv value from the config, reads the properties
	 * from resource valuemap, creates a secrets.bin file and calls the encryption utility
	 * to encrypt and write into secrets.bin file
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 * @throws JSONException
	 * @throws IOException
	 */
	public void getConfiguredSecretsKeyValuePairs() throws PathNotFoundException, RepositoryException, JSONException, IOException{
		LOGGER.debug("Start:- getConfiguredSecretsKeyValuePairs() of SecretsUse");
		
		Map<String, Object> configMap = new HashMap<>();
		HertzConfigFactory hertzConfigFactory=getSlingScriptHelper().getService(HertzConfigFactory.class);
		String apiKey = hertzConfigFactory.getStringPropertyValue(HertzConstants.HERTZ_API_KEY);
		String apiVector = hertzConfigFactory.getStringPropertyValue(HertzConstants.HERTZ_API_IV);
		Resource jcrContentResource = getResource();
		ValueMap componentProperties = jcrContentResource.getValueMap();
		Node jcrContentResourceNode = jcrContentResource.adaptTo(Node.class);
		Property configuredSecretsItems = jcrContentResourceNode.getProperty(HertzConstants.CONFIGURED_SECRETS);
		if(jcrContentResourceNode.getProperty(HertzConstants.CONFIGURED_SECRETS).isMultiple()){
			getConfiguredSecretsMultiple(configMap, configuredSecretsItems, jcrContentResourceNode);
		}
		else{
			getConfiguredSecretsSingle(configMap, configuredSecretsItems, jcrContentResourceNode);
		}
		if(jcrContentResourceNode.hasProperty(HertzConstants.HERTZ_SECRETS_FILEPATH)){
			String dirPath = HertzUtils.getValueFromMap(componentProperties, HertzConstants.HERTZ_SECRETS_FILEPATH);
			if(StringUtils.isNotBlank(dirPath)){
				 File file=new File(dirPath.trim(),FilenameUtils.getName(HertzConstants.SECRET_FILE_NAME));//NOSONAR
				 EncryptDecryptUtils.doEncrypt(configMap,apiKey,apiVector,file);
			}
		}
		
		LOGGER.debug("End:- getConfiguredSecretsKeyValuePairs() of SecretsUse");
	}
	
	/**
	 * This method retrieves the configured multiple values
	 * @param configMap
	 * @param configuredSecretsItems
	 * @param jcrContentResourceNode
	 * @throws ValueFormatException
	 * @throws RepositoryException
	 * @throws JSONException
	 */
	public void getConfiguredSecretsMultiple(Map<String, Object> configMap, Property configuredSecretsItems, Node jcrContentResourceNode) throws ValueFormatException, RepositoryException, JSONException{
		LOGGER.debug("Start:- getConfiguredSecretsMultiple() of SecretsUse");
		
		Value[] values = configuredSecretsItems.getValues();
		for (Value v : values) {
			String configuredSecretsProperty = "["+v.getString()+"]";
			getConfiguredSecretsItemsJson(configMap, configuredSecretsProperty);
		}
		LOGGER.debug("End:- getConfiguredSecretsMultiple() of SecretsUse");
	}
	
	/**
	 * This method retrieves the configured single values
	 * @param configMap
	 * @param configuredSecretsItems
	 * @param jcrContentResourceNode
	 * @throws ValueFormatException
	 * @throws RepositoryException
	 * @throws JSONException
	 */
	public void getConfiguredSecretsSingle(Map<String, Object> configMap, Property configuredSecretsItems, Node jcrContentResourceNode) throws ValueFormatException, RepositoryException, JSONException{
		LOGGER.debug("Start:- getConfiguredSecretsSingle() of SecretsUse");
		
		Value value = configuredSecretsItems.getValue();
		String configuredSecretsProperty = "["+value.getString()+"]";
		getConfiguredSecretsItemsJson(configMap, configuredSecretsProperty);
		
		LOGGER.debug("End:- getConfiguredSecretsSingle() of SecretsUse");
	}
	
	/**
	 * This method retrieves the key and value from the multi values and 
	 * sets them in the HashMap, sets them in the bean.
	 * @param configuredSecretsProperty
	 * @throws JSONException
	 */
	public void getConfiguredSecretsItemsJson(Map<String, Object> configMap, String configuredSecretsProperty) throws JSONException{
		LOGGER.debug("Start:- getConfiguredSecretsItemsJson() of SecretsUse");
		
		JSONArray jsonarray = new JSONArray(configuredSecretsProperty);
		for (int index=0; index < jsonarray.length(); index++){
			SecretsBean secretsBean = new SecretsBean();
			JSONObject configuredSecretsItemsJson = jsonarray.getJSONObject(index);
			String secretsKey = (String)configuredSecretsItemsJson.get(HertzConstants.KEY);
			String secretsValue = (String)configuredSecretsItemsJson.get(HertzConstants.VALUE);
			configMap.put(secretsKey, secretsValue);
			secretsBean.setKey(secretsKey);
			secretsBean.setValue(secretsValue);
			secretsBeanList.add(secretsBean);
		}
		
		LOGGER.debug("End:- getConfiguredSecretsItemsJson() of SecretsUse");
	}
	
	/**
	 * getter for bean list
	 * @return
	 */
	public List<SecretsBean> getSecretsBeanList() {
		return secretsBeanList;
	}
	
	/**
	 * setter for bean list
	 * @param secretsBeanList
	 */
	public void setSecretsBeanList(List<SecretsBean> secretsBeanList) {
		this.secretsBeanList = secretsBeanList;
	}

}
