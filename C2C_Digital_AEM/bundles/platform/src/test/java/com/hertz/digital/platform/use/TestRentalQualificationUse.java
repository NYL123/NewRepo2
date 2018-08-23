package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;
import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Value;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
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
import com.hertz.digital.platform.bean.RQRSectionContainerBean;
import com.hertz.digital.platform.constants.HertzConstants;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestRentalQualificationUse {
	
	@InjectMocks
	private RentalQualificationUse rentalQualificationUse ; 
	Logger log;
	
	@Mock
	Node sectionPageNode;
	
	@Mock
	RQRSectionContainerBean rQRSectionContainerBean;
	
	@Mock
	Node componentNode;
	
	@Mock
	Node jcrContentResourceNode;
	
	@Mock
	Property property;
	
	@Mock
	Property configurableLinks;
	
	@Mock
	Node jcrNode;
	
	@Mock
	Page page;
	
	@Mock
	Page childPage;
	
	@Mock
	Page globalPage;
	
	@Mock
	Page parentPage;
	
	@Mock
	Page parentPage1;
	
	@Mock
	Page parentPage2;
	
	@Mock
	Iterable<Resource> mockIterable;
	
	@Mock
	Iterator<Resource> mockIterator;
	
	@Mock
	Iterable<Resource> mockIterable1;
	
	@Mock
	Iterable<Resource> sectionComponents;
	
	@Mock
	Iterator<Resource> mockIterator1;
	
	@Mock
	Iterable<Resource> mockIterable2;
	
	@Mock
	Iterator<Resource> mockIterator2;
	
	@Mock
	Iterable<Resource> mockIterable3;
	
	@Mock
	Iterator<Resource> mockIterator3;
	
	@Mock
	Iterator<Page> pageIterator;
	
	@Mock
	Resource globalPageResource;
	
	@Mock
	Resource cityResource;
	
	@Mock
	Resource stateResource;
	
	@Mock
	Resource countryResource;
	
	@Mock
	Resource pageResource;
	
	@Mock
	Resource parResource;
	
	@Mock
	Resource parentResource;
	
	@Mock
	Resource componentResource;
	
	@Mock
	Resource childResource;
	
	@Mock
	Resource childJcrResource;
	
	@Mock
	Resource stateJcrResource;
	
	@Mock
	Resource countryJcrResource;
	
	@Mock
	Resource globalJcrResource;
	
	@Mock
	Resource jcrContentResource;
	
	@Mock
	ValueMap componentProperties;
	
	@Mock
	ValueMap valueMap;
	
	String pagePath = "dummyPath";
	
	Value[] values = new Value [2];
	
	Value value;
	
	Value value1;
	
	Value value2;
	
	Value value3;
	
	private String configurableItem1 = "{'linkName':'Insurance and Other Coverages','linkURL':'/content/hertz-rac','linkAltText':'Insurance and Other Coverages'}";
	private String configurableItem2 = "{'linkName':'Taxes','linkURL':'/content/hertz-rac','linkAltText':'Taxes'}";

	
	@Before
	public final void setup() throws Exception {
		rentalQualificationUse =PowerMockito.mock(RentalQualificationUse.class);
		componentProperties = PowerMockito.mock(ValueMap.class);
		value=PowerMockito.mock(Value.class);
		value1=PowerMockito.mock(Value.class);
		value2=PowerMockito.mock(Value.class);
		value3=PowerMockito.mock(Value.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);
		Mockito.doCallRealMethod().when(rentalQualificationUse).getParentPageResource(pageResource, rQRSectionContainerBean);
	}
	
	@Test
	public final void testConfigurableMultiLinkItemsMultiple() throws Exception {		
		when(pageResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(jcrContentResource);
		when(jcrContentResource.getChild(HertzConstants.PAR)).thenReturn(parResource);
		when(parResource.getChildren()).thenReturn(sectionComponents);
		when(sectionComponents.iterator()).thenReturn(mockIterator1);
		when(mockIterator1.hasNext()).thenReturn(true,true,true,true,false);
		when(mockIterator1.next()).thenReturn(componentResource);
		when(componentResource.getValueMap()).thenReturn(componentProperties);
		when(componentResource.adaptTo(Node.class)).thenReturn(componentNode);
		when(componentNode.hasProperty(HertzConstants.SECTION_NAME)).thenReturn(true);
		when(componentNode.hasProperty(HertzConstants.CONFIGURABLE_LINKS)).thenReturn(true);
		when(componentNode.getProperty(HertzConstants.CONFIGURABLE_LINKS)).thenReturn(configurableLinks);
		when(configurableLinks.isMultiple()).thenReturn(true);
		when(configurableLinks.getValues()).thenReturn(values);
		Value value1=PowerMockito.mock(Value.class);
		Value value2=PowerMockito.mock(Value.class);
		values[0]=value1;
		values[1]=value2;
		when(value1.getString()).thenReturn(configurableItem1);
		when(value2.getString()).thenReturn(configurableItem2);	
		
		when(pageResource.getParent()).thenReturn(cityResource);		
		when(cityResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(childJcrResource);
		when(childJcrResource.getChild(HertzConstants.PAR)).thenReturn(parResource);
		when(parResource.getChildren()).thenReturn(sectionComponents);
		when(sectionComponents.iterator()).thenReturn(mockIterator1);
		when(mockIterator1.hasNext()).thenReturn(true,true,true,true,false);
		when(mockIterator1.next()).thenReturn(componentResource);
		when(componentResource.getValueMap()).thenReturn(componentProperties);
		when(componentResource.adaptTo(Node.class)).thenReturn(componentNode);
		when(componentNode.hasProperty(HertzConstants.SECTION_NAME)).thenReturn(true);
		when(componentNode.hasProperty(HertzConstants.CONFIGURABLE_LINKS)).thenReturn(true);
		when(componentNode.getProperty(HertzConstants.CONFIGURABLE_LINKS)).thenReturn(configurableLinks);
		when(configurableLinks.isMultiple()).thenReturn(true);
		when(configurableLinks.getValues()).thenReturn(values);
		Value value3=PowerMockito.mock(Value.class);
		Value value4=PowerMockito.mock(Value.class);
		values[0]=value3;
		values[1]=value4;
		when(value3.getString()).thenReturn(configurableItem1);
		when(value4.getString()).thenReturn(configurableItem2);	
		
		when(cityResource.getParent()).thenReturn(stateResource);		
		when(stateResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(stateJcrResource);
		when(stateJcrResource.getChild(HertzConstants.PAR)).thenReturn(parResource);
		when(parResource.getChildren()).thenReturn(sectionComponents);
		when(sectionComponents.iterator()).thenReturn(mockIterator1);
		when(mockIterator1.hasNext()).thenReturn(true,true,true,true,false);
		when(mockIterator1.next()).thenReturn(componentResource);
		when(componentResource.getValueMap()).thenReturn(componentProperties);
		when(componentResource.adaptTo(Node.class)).thenReturn(componentNode);
		when(componentNode.hasProperty(HertzConstants.SECTION_NAME)).thenReturn(true);
		when(componentNode.hasProperty(HertzConstants.CONFIGURABLE_LINKS)).thenReturn(true);
		when(componentNode.getProperty(HertzConstants.CONFIGURABLE_LINKS)).thenReturn(configurableLinks);
		when(configurableLinks.isMultiple()).thenReturn(true);
		when(configurableLinks.getValues()).thenReturn(values);
		Value value5=PowerMockito.mock(Value.class);
		Value value6=PowerMockito.mock(Value.class);
		values[0]=value5;
		values[1]=value6;
		when(value5.getString()).thenReturn(configurableItem1);
		when(value6.getString()).thenReturn(configurableItem2);	
		
		when(stateResource.getParent()).thenReturn(countryResource);		
		when(countryResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(countryJcrResource);
		when(countryJcrResource.getChild(HertzConstants.PAR)).thenReturn(parResource);
		when(parResource.getChildren()).thenReturn(sectionComponents);
		when(sectionComponents.iterator()).thenReturn(mockIterator1);
		when(mockIterator1.hasNext()).thenReturn(true,true,true,true,false);
		when(mockIterator1.next()).thenReturn(componentResource);
		when(componentResource.getValueMap()).thenReturn(componentProperties);
		when(componentResource.adaptTo(Node.class)).thenReturn(componentNode);
		when(componentNode.hasProperty(HertzConstants.SECTION_NAME)).thenReturn(true);
		when(componentNode.hasProperty(HertzConstants.CONFIGURABLE_LINKS)).thenReturn(true);
		when(componentNode.getProperty(HertzConstants.CONFIGURABLE_LINKS)).thenReturn(configurableLinks);
		when(configurableLinks.isMultiple()).thenReturn(true);
		when(configurableLinks.getValues()).thenReturn(values);
		Value value7=PowerMockito.mock(Value.class);
		Value value8=PowerMockito.mock(Value.class);
		values[0]=value7;
		values[1]=value8;
		when(value7.getString()).thenReturn(configurableItem1);
		when(value8.getString()).thenReturn(configurableItem2);	
		
		when(countryResource.getParent()).thenReturn(globalPageResource);		
		when(globalPageResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(globalJcrResource);
		when(globalJcrResource.getChild(HertzConstants.PAR)).thenReturn(parResource);
		when(parResource.getChildren()).thenReturn(sectionComponents);
		when(sectionComponents.iterator()).thenReturn(mockIterator1);
		when(mockIterator1.hasNext()).thenReturn(true,true,true,true,false);
		when(mockIterator1.next()).thenReturn(componentResource);
		when(componentResource.getValueMap()).thenReturn(componentProperties);
		when(componentResource.adaptTo(Node.class)).thenReturn(componentNode);
		when(componentNode.hasProperty(HertzConstants.SECTION_NAME)).thenReturn(true);
		when(componentNode.hasProperty(HertzConstants.CONFIGURABLE_LINKS)).thenReturn(true);
		when(componentNode.getProperty(HertzConstants.CONFIGURABLE_LINKS)).thenReturn(configurableLinks);
		when(configurableLinks.isMultiple()).thenReturn(true);
		when(configurableLinks.getValues()).thenReturn(values);
		Value value9=PowerMockito.mock(Value.class);
		Value value10=PowerMockito.mock(Value.class);
		values[0]=value9;
		values[1]=value10;
		when(value9.getString()).thenReturn(configurableItem1);
		when(value10.getString()).thenReturn(configurableItem2);	
		
		rentalQualificationUse.getParentPageResource(pageResource, rQRSectionContainerBean);		
	}
	
	@Test
	public final void testConfigurableMultiLinkItemsSingle() throws Exception {		
		when(pageResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(jcrContentResource);
		when(jcrContentResource.getChild(HertzConstants.PAR)).thenReturn(parResource);
		when(parResource.getChildren()).thenReturn(sectionComponents);
		when(sectionComponents.iterator()).thenReturn(mockIterator1);
		when(mockIterator1.hasNext()).thenReturn(true,true,true,true,false);
		when(mockIterator1.next()).thenReturn(componentResource);
		when(componentResource.getValueMap()).thenReturn(componentProperties);
		when(componentResource.adaptTo(Node.class)).thenReturn(componentNode);
		when(componentNode.hasProperty(HertzConstants.SECTION_NAME)).thenReturn(true);
		when(componentNode.hasProperty(HertzConstants.CONFIGURABLE_LINKS)).thenReturn(true);
		when(componentNode.getProperty(HertzConstants.CONFIGURABLE_LINKS)).thenReturn(configurableLinks);
		when(configurableLinks.isMultiple()).thenReturn(false);
		when(configurableLinks.getValue()).thenReturn(value);
		when(value.getString()).thenReturn(configurableItem1);
		
		when(pageResource.getParent()).thenReturn(cityResource);		
		when(cityResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(childJcrResource);
		when(childJcrResource.getChild(HertzConstants.PAR)).thenReturn(parResource);
		when(parResource.getChildren()).thenReturn(sectionComponents);
		when(sectionComponents.iterator()).thenReturn(mockIterator1);
		when(mockIterator1.hasNext()).thenReturn(true,true,true,true,false);
		when(mockIterator1.next()).thenReturn(componentResource);
		when(componentResource.getValueMap()).thenReturn(componentProperties);
		when(componentResource.adaptTo(Node.class)).thenReturn(componentNode);
		when(componentNode.hasProperty(HertzConstants.SECTION_NAME)).thenReturn(true);
		when(componentNode.hasProperty(HertzConstants.CONFIGURABLE_LINKS)).thenReturn(true);
		when(componentNode.getProperty(HertzConstants.CONFIGURABLE_LINKS)).thenReturn(configurableLinks);
		when(configurableLinks.isMultiple()).thenReturn(false);
		when(configurableLinks.getValue()).thenReturn(value1);
		when(value1.getString()).thenReturn(configurableItem1);
		
		when(cityResource.getParent()).thenReturn(stateResource);		
		when(stateResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(stateJcrResource);
		when(stateJcrResource.getChild(HertzConstants.PAR)).thenReturn(parResource);
		when(parResource.getChildren()).thenReturn(sectionComponents);
		when(sectionComponents.iterator()).thenReturn(mockIterator1);
		when(mockIterator1.hasNext()).thenReturn(true,true,true,true,false);
		when(mockIterator1.next()).thenReturn(componentResource);
		when(componentResource.getValueMap()).thenReturn(componentProperties);
		when(componentResource.adaptTo(Node.class)).thenReturn(componentNode);
		when(componentNode.hasProperty(HertzConstants.SECTION_NAME)).thenReturn(true);
		when(componentNode.hasProperty(HertzConstants.CONFIGURABLE_LINKS)).thenReturn(true);
		when(componentNode.getProperty(HertzConstants.CONFIGURABLE_LINKS)).thenReturn(configurableLinks);
		when(configurableLinks.isMultiple()).thenReturn(false);
		when(configurableLinks.getValue()).thenReturn(value2);
		when(value2.getString()).thenReturn(configurableItem1);
		
		when(stateResource.getParent()).thenReturn(countryResource);		
		when(countryResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(countryJcrResource);
		when(countryJcrResource.getChild(HertzConstants.PAR)).thenReturn(parResource);
		when(parResource.getChildren()).thenReturn(sectionComponents);
		when(sectionComponents.iterator()).thenReturn(mockIterator1);
		when(mockIterator1.hasNext()).thenReturn(true,true,true,true,false);
		when(mockIterator1.next()).thenReturn(componentResource);
		when(componentResource.getValueMap()).thenReturn(componentProperties);
		when(componentResource.adaptTo(Node.class)).thenReturn(componentNode);
		when(componentNode.hasProperty(HertzConstants.SECTION_NAME)).thenReturn(true);
		when(componentNode.hasProperty(HertzConstants.CONFIGURABLE_LINKS)).thenReturn(true);
		when(componentNode.getProperty(HertzConstants.CONFIGURABLE_LINKS)).thenReturn(configurableLinks);
		when(configurableLinks.isMultiple()).thenReturn(false);
		when(configurableLinks.getValue()).thenReturn(value3);
		when(value3.getString()).thenReturn(configurableItem1);
		rentalQualificationUse.getParentPageResource(pageResource, rQRSectionContainerBean);
	}
}
