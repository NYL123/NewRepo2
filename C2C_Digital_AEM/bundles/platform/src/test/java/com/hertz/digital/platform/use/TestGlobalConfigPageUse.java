package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
public class TestGlobalConfigPageUse {
	
	@InjectMocks
	private GlobalConfigPageUse globalConfigPageUse ; 
	Logger log;
	
	@Mock
	Property property;
	
	List<SecretsBean> beanList;
	
	@Mock
	SecretsBean secretsBean;
	
	@Mock
	Property configurableLinks;
	
	@Mock
	Resource pageResource;
	
	@Mock
	Iterable<Resource> iterable;
	
	@Mock
	Iterator<Resource> iterator;
	
	@Mock
	Resource parResource;
	
	@Mock
	Resource componentResource;
	
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
		globalConfigPageUse=PowerMockito.mock(GlobalConfigPageUse.class);
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
		beanList=new ArrayList<SecretsBean>();
		beanList.add(secretsBean);
		Mockito.doCallRealMethod().when(globalConfigPageUse).activate();
		Mockito.doCallRealMethod().when(globalConfigPageUse).getConfiguredKeyValuePairs();
		Mockito.doCallRealMethod().when(globalConfigPageUse).setKeyValueInBean(componentResource);
		Mockito.doCallRealMethod().when(globalConfigPageUse).getBeanList();
		Mockito.doCallRealMethod().when(globalConfigPageUse).setBeanList(beanList);	
	}
	
	@Test
	public final void test() throws Exception{
		globalConfigPageUse.setBeanList(beanList);
		when(globalConfigPageUse.getResource()).thenReturn(pageResource);
		when(pageResource.getChild(HertzConstants.PAR)).thenReturn(parResource);
		when(parResource.getChildren()).thenReturn(iterable);
		when(iterable.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true,true,false);
		when(iterator.next()).thenReturn(componentResource);
		when(componentResource.getValueMap()).thenReturn(componentProperties);
		when(componentProperties.containsKey(Mockito.anyString())).thenReturn(true);
		when(componentProperties.get("key", String.class)).thenReturn("key");
		when(componentProperties.get("value", String.class)).thenReturn("value");
		globalConfigPageUse.activate();
		
		Assert.assertNotNull(globalConfigPageUse.getBeanList());	
	}
}
