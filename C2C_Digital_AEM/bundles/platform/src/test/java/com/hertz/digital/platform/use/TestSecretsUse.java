package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Value;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.hertz.digital.platform.bean.SecretsBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;

import junit.framework.Assert;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestSecretsUse {
	
	@InjectMocks
	private SecretsUse secretsUse ; 
	Logger log;
	
	@Mock
	Property property;
	
	List<SecretsBean> secretsBeanList;
	
	@Mock
	SecretsBean secretsBean;
	
	@Mock
	Property configurableLinks;
	
	@Mock
	Resource pageResource;
	
	@Mock
	ValueMap componentProperties;
	
	@Mock
	SlingScriptHelper scriptHelper;
	
	@Mock
	Node jcrContentResourceNode;
	
	@Mock
    HertzConfigFactory hertzConfigFactory;
	
	String pagePath = "dummyPath";
	
	String apiKey = "key";
	
	String apiVector = "vector";
	
	String filePath = "filePath";
	
	Value[] values = new Value [2];
	
	Value value;
	
	Value value1;
	
	Value value2;
	
	Map<String, Object> configMap = new HashMap<>();
	
	private String configurableItem1 = "{'key':'username','value':'password'}";
	private String configurableItem2 = "{'key':'username1','value':'password1'}";
	private String configuredSecretsProperty = "[{'key':'username','value':'password'}]";

	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		secretsUse=PowerMockito.mock(SecretsUse.class);
		secretsBean=PowerMockito.mock(SecretsBean.class);
		value=PowerMockito.mock(Value.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log=Mockito.mock(Logger.class);
		Field field=LoginFlyoutItemsUse.class.getDeclaredField("logger");
		field.setAccessible(true);
		Field modifiersField=Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL); 
		field.set(null,log);	
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		secretsBeanList=new ArrayList<SecretsBean>();
		secretsBeanList.add(secretsBean);
		Mockito.doCallRealMethod().when(secretsUse).activate();
		Mockito.doCallRealMethod().when(secretsUse).getConfiguredSecretsKeyValuePairs();
		Mockito.doCallRealMethod().when(secretsUse).getConfiguredSecretsMultiple(configMap, property, jcrContentResourceNode);
		Mockito.doCallRealMethod().when(secretsUse).getConfiguredSecretsSingle(configMap, property, jcrContentResourceNode);
		Mockito.doCallRealMethod().when(secretsUse).getConfiguredSecretsItemsJson(configMap, configuredSecretsProperty);
		Mockito.doCallRealMethod().when(secretsUse).getSecretsBeanList();
		Mockito.doCallRealMethod().when(secretsUse).setSecretsBeanList(secretsBeanList);	
	}
	
	@Test
	public final void test() throws Exception{
		secretsUse.setSecretsBeanList(secretsBeanList);
		when(secretsUse.getSlingScriptHelper()).thenReturn(scriptHelper);
		when(scriptHelper.getService(HertzConfigFactory.class)).thenReturn(hertzConfigFactory);
		when(hertzConfigFactory.getStringPropertyValue(HertzConstants.HERTZ_API_KEY)).thenReturn(apiKey);
		when(hertzConfigFactory.getStringPropertyValue(HertzConstants.HERTZ_API_IV)).thenReturn(apiVector);
		when(secretsUse.getResource()).thenReturn(pageResource);
		when(pageResource.getValueMap()).thenReturn(componentProperties);
		when(pageResource.adaptTo(Node.class)).thenReturn(jcrContentResourceNode);
		when(jcrContentResourceNode.hasProperty(Mockito.anyString())).thenReturn(true);
		when(jcrContentResourceNode.getProperty(Mockito.anyString())).thenReturn(property);
		when(property.isMultiple()).thenReturn(false);
		when(property.getValue()).thenReturn(value);
		when(value.getString()).thenReturn(configuredSecretsProperty);
		when(componentProperties.containsKey(Mockito.anyString())).thenReturn(true);
		when(componentProperties.get("dirPath", String.class)).thenReturn(filePath);
		secretsUse.activate();
		
		when(secretsUse.getSlingScriptHelper()).thenReturn(scriptHelper);
		when(scriptHelper.getService(HertzConfigFactory.class)).thenReturn(hertzConfigFactory);
		when(hertzConfigFactory.getStringPropertyValue(HertzConstants.HERTZ_API_KEY)).thenReturn(apiKey);
		when(hertzConfigFactory.getStringPropertyValue(HertzConstants.HERTZ_API_IV)).thenReturn(apiVector);
		when(secretsUse.getResource()).thenReturn(pageResource);
		when(pageResource.getValueMap()).thenReturn(componentProperties);
		when(pageResource.adaptTo(Node.class)).thenReturn(jcrContentResourceNode);
		when(jcrContentResourceNode.hasProperty(Mockito.anyString())).thenReturn(true);
		when(jcrContentResourceNode.getProperty(Mockito.anyString())).thenReturn(property);
		when(property.isMultiple()).thenReturn(true);
		when(property.getValues()).thenReturn(values);
		Value value1=PowerMockito.mock(Value.class);
		Value value2=PowerMockito.mock(Value.class);
		values[0]=value1;
		values[1]=value2;
		when(value1.getString()).thenReturn(configurableItem1);
		when(value2.getString()).thenReturn(configurableItem2);
		when(componentProperties.containsKey(Mockito.anyString())).thenReturn(true);
		when(componentProperties.get("dirPath", String.class)).thenReturn(filePath);
		secretsUse.activate();	
		
		Assert.assertNotNull(secretsUse.getSecretsBeanList());	
	}
}
