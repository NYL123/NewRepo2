package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Value;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.junit.Assert;
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
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.bean.LoginFlyOutItemsBean;

@RunWith(PowerMockRunner.class)
@PrepareForTest({WCMUsePojo.class, LoggerFactory.class})
public class TestLoginFlyoutItemsUse {

	@InjectMocks
	private LoginFlyoutItemsUse loginFlyoutItemsUse;
	
	Logger log;
	
	List<LoginFlyOutItemsBean> flyOutItemsList;
	
	@Mock
	Node node;
	
	@Mock
	Resource resource;
	
	@Mock
	LoginFlyOutItemsBean loginFlyOutItemsBean;
	
	@Mock
	Page parentPage;
	
	@Mock
	Page currentPage;
	
	@Mock
	SlingScriptHelper slingScriptHelper;
	
	@Mock
	Property property;
	
	@Mock
    Logger logger;
	
	Value[] values = new Value [2];
	
	private String  loginItem1 = "{'flyoutItemTxt':'flyout1','flyoutItemPath':'dummyPath','openUrlNewWindow':true}";
	private String  loginItem2 = "{'flyoutItemTxt':'flyout2','flyoutItemPath':'dummyPath','openUrlNewWindow':false}";
	private String  loginItem = "[{'flyoutItemTxt':'flyout1','flyoutItemPath':'dummyPath','openUrlNewWindow':true}]";
	
	Value value;
	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		loginFlyoutItemsUse=PowerMockito.mock(LoginFlyoutItemsUse.class);
		loginFlyOutItemsBean=PowerMockito.mock(LoginFlyOutItemsBean.class);
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
		flyOutItemsList=new ArrayList<LoginFlyOutItemsBean>();
		flyOutItemsList.add(loginFlyOutItemsBean);
		Mockito.doCallRealMethod().when(loginFlyoutItemsUse).activate();
		Mockito.doCallRealMethod().when(loginFlyoutItemsUse).getFlyoutItemsValues();
		Mockito.doCallRealMethod().when(loginFlyoutItemsUse).getLoginFlyoutItems(node);
		Mockito.doCallRealMethod().when(loginFlyoutItemsUse).getLoginFlyoutItemsMultiple(property, node);
		Mockito.doCallRealMethod().when(loginFlyoutItemsUse).getLoginFlyoutItemsSingle(property, node);
		Mockito.doCallRealMethod().when(loginFlyoutItemsUse).getLoginFlyoutItemsJson(loginItem);
		Mockito.doCallRealMethod().when(loginFlyoutItemsUse).getFlyOutItems();
		Mockito.doCallRealMethod().when(loginFlyoutItemsUse).setFlyOutItems(flyOutItemsList);	
	}
	
	@Test
	public final void test() throws Exception{
		loginFlyoutItemsUse.setFlyOutItems(flyOutItemsList);
		when(loginFlyoutItemsUse.getResource()).thenReturn(resource);
		when(resource.adaptTo(Node.class)).thenReturn(node);
		when(node.hasProperty(Mockito.anyString())).thenReturn(true);
		when(node.getProperty(Mockito.anyString())).thenReturn(property);
		when(property.isMultiple()).thenReturn(false);
		when(property.getValue()).thenReturn(value);
		when(value.getString()).thenReturn(loginItem);
		loginFlyoutItemsUse.activate();
		
		when(loginFlyoutItemsUse.getResource()).thenReturn(resource);
		when(resource.adaptTo(Node.class)).thenReturn(node);
		when(node.hasProperty(Mockito.anyString())).thenReturn(true);
		when(node.getProperty(Mockito.anyString())).thenReturn(property);
		when(property.isMultiple()).thenReturn(true);
		when(property.getValues()).thenReturn(values);
		Value value1=PowerMockito.mock(Value.class);
		Value value2=PowerMockito.mock(Value.class);
		values[0]=value1;
		values[1]=value2;
		when(value1.getString()).thenReturn(loginItem1);
		when(value2.getString()).thenReturn(loginItem2);
		loginFlyoutItemsUse.activate();
		
		Assert.assertNotNull(loginFlyoutItemsUse.getFlyOutItems());		
	}
	
}
