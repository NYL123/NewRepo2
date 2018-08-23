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
import com.hertz.digital.platform.bean.RQRLinksBean;

import junit.framework.Assert;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestRQRSectionUse {
	
	@InjectMocks
	private RQRSectionUse rQRSectionUse ; 
	Logger log;
	
	List<RQRLinksBean> rQRLinksBeanList;
	
	@Mock
	RQRLinksBean rQRLinksBean;
	
	@Mock
	Property property;
	
	@Mock
	Property configurableLinks;
	
	@Mock
	Resource pageResource;
	
	@Mock
	Node node;
	
	String pagePath = "dummyPath";
	
	Value[] values = new Value [2];
	
	Value value;
	
	Value value1;
	
	Value value2;
	
	private String configurableItem1 = "{'linkName':'Insurance and Other Coverages','linkURL':'/content/hertz-rac','linkAltText':'Insurance and Other Coverages'}";
	private String configurableItem2 = "{'linkName':'Taxes','linkURL':'/content/hertz-rac','linkAltText':'Taxes'}";
	private String configurableItem = "[{'linkName':'Taxes','linkURL':'/content/hertz-rac','linkAltText':'Taxes'}]";

	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		rQRSectionUse=PowerMockito.mock(RQRSectionUse.class);
		rQRLinksBean=PowerMockito.mock(RQRLinksBean.class);
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
		rQRLinksBeanList=new ArrayList<RQRLinksBean>();
		rQRLinksBeanList.add(rQRLinksBean);
		Mockito.doCallRealMethod().when(rQRSectionUse).activate();
		Mockito.doCallRealMethod().when(rQRSectionUse).getConfiguredSectionLinkItems();
		Mockito.doCallRealMethod().when(rQRSectionUse).getConfiguredSectionLinkItemsMultiple(property, node);
		Mockito.doCallRealMethod().when(rQRSectionUse).getConfiguredSectionLinkItemsSingle(property, node);
		Mockito.doCallRealMethod().when(rQRSectionUse).getConfiguredSectionLinkItemsJson(configurableItem);
		Mockito.doCallRealMethod().when(rQRSectionUse).getRQRLinksBeanList();
		Mockito.doCallRealMethod().when(rQRSectionUse).setRQRLinksBeanList(rQRLinksBeanList);	
	}
	
	@Test
	public final void test() throws Exception{
		rQRSectionUse.setRQRLinksBeanList(rQRLinksBeanList);
		when(rQRSectionUse.getResource()).thenReturn(pageResource);
		when(pageResource.adaptTo(Node.class)).thenReturn(node);
		when(node.hasProperty(Mockito.anyString())).thenReturn(true);
		when(node.getProperty(Mockito.anyString())).thenReturn(property);
		when(property.isMultiple()).thenReturn(false);
		when(property.getValue()).thenReturn(value);
		when(value.getString()).thenReturn(configurableItem);
		rQRSectionUse.activate();
		
		when(rQRSectionUse.getResource()).thenReturn(pageResource);
		when(pageResource.adaptTo(Node.class)).thenReturn(node);
		when(node.hasProperty(Mockito.anyString())).thenReturn(true);
		when(node.getProperty(Mockito.anyString())).thenReturn(property);
		when(property.isMultiple()).thenReturn(true);
		when(property.getValues()).thenReturn(values);
		Value value1=PowerMockito.mock(Value.class);
		Value value2=PowerMockito.mock(Value.class);
		values[0]=value1;
		values[1]=value2;
		when(value1.getString()).thenReturn(configurableItem1);
		when(value2.getString()).thenReturn(configurableItem2);
		rQRSectionUse.activate();
		
		Assert.assertNotNull(rQRSectionUse.getRQRLinksBeanList());		
	}
}
