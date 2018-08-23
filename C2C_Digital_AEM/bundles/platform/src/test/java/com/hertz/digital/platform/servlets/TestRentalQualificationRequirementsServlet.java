package com.hertz.digital.platform.servlets;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.PrintWriter;
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

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
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

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.service.api.SystemUserService;

import junitx.util.PrivateAccessor;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoggerFactory.class})
public class TestRentalQualificationRequirementsServlet {

	@InjectMocks
	private RentalQualificationRequirementsServlet rentalQualificationRequirementsServlet;
	
	Logger log;
	
	@Mock
	SlingHttpServletRequest request;
	
	@Mock
	SlingHttpServletResponse response;
	
	@Mock
	PrintWriter writer;
	
	@Mock
	RequestPathInfo pathInfo;
	
	@Mock
	Property configurableLinks;
	
	@Mock
	Resource resource;
	
	@Mock
	Resource parResource;
	
	@Mock
	Resource componentResource;
	
	@Mock
	ValueMap componentProperties;
	
	@Mock
	Node componentNode;
	
	@Mock
	Resource jcrContentResource;
	
	@Mock
	private SystemUserService systemService;
	
	@Mock
	Iterator<Resource> mockIterator1;
	
	@Mock
	Resource globalPageResource;
	
	@Mock
	Resource cityResource;
	
	@Mock
	Resource stateResource;
	
	@Mock
	Resource countryResource;
	
	@Mock
	Iterable<Resource> sectionComponents;
	
	@Mock
	Iterator<Resource> mockIterator;
	
	@Mock
	ResourceResolver resolver;
	
	@Mock
	Session session;
	
	@Mock
	QueryBuilder mockBuilder;
	
	@Mock 
	Query query;
	
	@Mock
	SearchResult result;
	
	@Mock
	Resource childJcrResource;
	
	@Mock
	Resource stateJcrResource;
	
	@Mock
	Resource countryJcrResource;
	
	@Mock
	Resource globalJcrResource;
	
	@Mock
	Hit hit;
	
	private String [] selectors = new String[]{"hertz-rac","data","en-us","rentalqualification-config","oag"};
	List<Hit> mockList;
	Value[] values = new Value [2];
	private String configurableItem1 = "{'linkName':'Insurance and Other Coverages','linkURL':'/content/hertz-rac','linkAltText':'Insurance and Other Coverages'}";
	private String configurableItem2 = "{'linkName':'Taxes','linkURL':'/content/hertz-rac','linkAltText':'Taxes'}";
	
	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		rentalQualificationRequirementsServlet=PowerMockito.mock(RentalQualificationRequirementsServlet.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log=Mockito.mock(Logger.class);
		Field field=RentalQualificationRequirementsServlet.class.getDeclaredField("LOGGER");
		field.setAccessible(true);
		Field modifiersField=Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL); 
		field.set(null,log);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		Mockito.doCallRealMethod().when(rentalQualificationRequirementsServlet).doGet(request, response);
		Mockito.doCallRealMethod().when(rentalQualificationRequirementsServlet).getRqrJson(selectors,request);
	}
	
	@Test
	public final void testDoGet() throws Exception{
		mockList=new ArrayList<Hit>();
		when(request.getRequestPathInfo()).thenReturn(pathInfo);		
		when(pathInfo.getSelectors()).thenReturn(selectors);
		//PrivateAccessor.setField(rentalQualificationRequirementsServlet, "systemService", systemService);
		//when(systemService.getServiceResourceResolver()).thenReturn(resolver);
		when(request.getResourceResolver()).thenReturn(resolver);
		when(resolver.adaptTo(Session.class)).thenReturn(session);
		Map<String, String> map = new HashMap<String, String>();
		map.put("path", HertzConstants.CONTENT + "hertz-rac/data/en-us/rentalqualification-config/oag");
		map.put("type", "cq:Page");
		map.put("nodename", "oag");
		map.put("property.and", "true");
		map.put("p.limit", "-1");
		PrivateAccessor.setField(rentalQualificationRequirementsServlet, "builder", mockBuilder);
		when(mockBuilder.createQuery(any(PredicateGroup.class), eq(session))).thenReturn(query);
		when(query.getResult()).thenReturn(result);
		mockList.add(hit);
		when(result.getHits()).thenReturn(mockList);
		when(hit.getResource()).thenReturn(resource);
		when(resource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(jcrContentResource);
		when(jcrContentResource.getChild(HertzConstants.PAR)).thenReturn(parResource);
		when(parResource.getChildren()).thenReturn(sectionComponents);
		when(sectionComponents.iterator()).thenReturn(mockIterator);
		when(mockIterator.hasNext()).thenReturn(true,true,true,true,false);
		when(mockIterator.next()).thenReturn(componentResource);
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
		
		when(resource.getParent()).thenReturn(cityResource);		
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
		
		when(response.getWriter()).thenReturn(writer);
		rentalQualificationRequirementsServlet.doGet(request, response);
		selectors=new String[]{"hertz-rac","data","en-us","rentalqualification-config"};
		when(pathInfo.getSelectors()).thenReturn(selectors);
		rentalQualificationRequirementsServlet.doGet(request, response);
	}
}
