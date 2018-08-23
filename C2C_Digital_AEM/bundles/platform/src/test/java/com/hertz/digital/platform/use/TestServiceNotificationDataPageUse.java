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
import javax.jcr.Session;
import javax.jcr.Value;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
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
public class TestServiceNotificationDataPageUse {
	
	@InjectMocks
	private ServiceNotificationDataPageUse serviceNotificationDataPageUse ; 
	Logger log;
	
	@Mock
	Property property;
	
	@Mock
	Resource parentResource;
	
	@Mock
	Iterable<Resource> componentResources;
	
	@Mock
	Resource parentContentResource;
	
	@Mock
	ResourceResolver resolver;
	
	@Mock
	Resource jcrContentResource;
	
	@Mock
	Resource pageResource;
	
	@Mock
	Node jcrContentNode;
	
	@Mock
	Session session;
	
	@Mock
	ValueMap resourceProperties;
	
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
	
	String serviceNameFromParent = "InFact";
	
	

	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		serviceNotificationDataPageUse=PowerMockito.mock(ServiceNotificationDataPageUse.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log=Mockito.mock(Logger.class);
		Field field=LoginFlyoutItemsUse.class.getDeclaredField("logger");
		field.setAccessible(true);
		Field modifiersField=Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL); 
		field.set(null,log);	
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		Mockito.doCallRealMethod().when(serviceNotificationDataPageUse).activate();
		Mockito.doCallRealMethod().when(serviceNotificationDataPageUse).getServiceName();
		Mockito.doCallRealMethod().when(serviceNotificationDataPageUse).getServiceNameFromParent(parentResource,jcrContentResource);
		Mockito.doCallRealMethod().when(serviceNotificationDataPageUse).getServiceNameComponent(parResource,jcrContentResource);
		Mockito.doCallRealMethod().when(serviceNotificationDataPageUse).setServiceNameInChild(serviceNameFromParent,jcrContentResource);
	}
	
	@Test
	public final void test() throws Exception{
		when(serviceNotificationDataPageUse.getResourceResolver()).thenReturn(resolver);
		when(resolver.adaptTo(Session.class)).thenReturn(session);
		when(serviceNotificationDataPageUse.getResource()).thenReturn(jcrContentResource);
		when(jcrContentResource.getParent()).thenReturn(pageResource);
		when(jcrContentResource.getValueMap()).thenReturn(resourceProperties);
		when(resourceProperties.containsKey(Mockito.anyString())).thenReturn(true);
		when(resourceProperties.get("service", String.class)).thenReturn(serviceNameFromParent);
		serviceNotificationDataPageUse.activate();	
	}
	
	@Test
	public final void test1() throws Exception{
		when(serviceNotificationDataPageUse.getResourceResolver()).thenReturn(resolver);
		when(resolver.adaptTo(Session.class)).thenReturn(session);
		when(serviceNotificationDataPageUse.getResource()).thenReturn(jcrContentResource);
		when(jcrContentResource.getParent()).thenReturn(pageResource);
		when(jcrContentResource.getValueMap()).thenReturn(resourceProperties);
		when(resourceProperties.containsKey(Mockito.anyString())).thenReturn(false);
		when(pageResource.getParent()).thenReturn(parentResource);
		when(parentResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(parentContentResource);
		when(parentContentResource.getChild(HertzConstants.PAR)).thenReturn(parResource);
		when(parResource.getChildren()).thenReturn(componentResources);
		when(componentResources.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true,false);
		when(iterator.next()).thenReturn(componentResource);
		when(componentResource.getResourceType()).thenReturn(HertzConstants.SERVICE_NAME_COMPONENT_RES_TYPE);
		when(componentResource.getValueMap()).thenReturn(componentProperties);
		when(componentProperties.containsKey(HertzConstants.SERVICE_NAME)).thenReturn(true);
		when(componentProperties.get(HertzConstants.SERVICE_NAME, String.class)).thenReturn(serviceNameFromParent);
		when(jcrContentResource.adaptTo(Node.class)).thenReturn(jcrContentNode);
		serviceNotificationDataPageUse.activate();	
	}
}
