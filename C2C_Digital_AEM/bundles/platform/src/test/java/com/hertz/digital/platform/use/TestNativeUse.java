package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.nodetype.PropertyDefinition;
import javax.servlet.ServletException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.commons.json.JSONException;
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
import com.day.cq.commons.Externalizer;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.bean.spa.SpaPageBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.constants.TestConstants;
import com.hertz.digital.platform.exporter.api.ComponentExporterService;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.use.spa.GenericPageUse;
import com.hertz.digital.platform.utils.HertzUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class, HertzUtils.class })
public class TestNativeUse {

	@InjectMocks
	NativeUse nativeUse;

	@Mock
	GenericPageUse genericPageUse;

	Logger log;

	@Mock
	SlingHttpServletRequest sling;

	@Mock
	SlingScriptHelper scriptHelper;

	@Mock
	Page currentPage;

	String compConfigArray[] = { "hero","rewards" };
	
	@Mock
	ResourceResolver resourceResolver;
	
	@Mock
	ResourceResolver resourceResolver1;
	
	@Mock
	ResourceResolver resourceResolver2;
	
	@Mock
	SpaPageBean spaBean;
	
	@Mock
	Query query;
	
	@Mock
	SlingScriptHelper slingScriptHelper;
	
	@Mock
	Resource partnerHomePageResource;
	
	@Mock
	Resource cpCodeResource;
	
	@Mock
	Externalizer externalizer;
	
	@Mock
	Page page;
	
	@Mock
	SearchResult result;
	
	@Mock
	Hit hit;
	
	@Mock
	Session session;
	
	@Mock
	QueryBuilder queryBuilder;
	
	@Mock
	Page iracPage;
	
	@Mock
	Resource resource;	
	
	@Mock
	Resource pageResource;
	
	@Mock
	Node pageNode;
	
	@Mock
	Node pageJCRNode;
	
	@Mock
	Resource pageJCRResource;
	
	@Mock
	SlingHttpServletRequest mockRequest;
	
	@Mock
	SlingScriptHelper slingExporterScriptHelper;
	
	@Mock
	Property cqTemplateProperty;
	
	@Mock
	Node node;
	
	@Mock
	Node childNode;
	
	@Mock
	SlingHttpServletRequest request;
	
	@Mock
	SlingHttpServletResponse response;
	
	@Mock
	PropertyIterator iterator;
	
	@Mock
	Property property;
	
	@Mock
	PropertyDefinition definition;
	
	@Mock
	Value value;
	
	@Mock
	NodeIterator nodeIterator;
	
	@Mock
	HertzConfigFactory configFactory;
	
	@Mock
	Resource childResource;
	
	@Mock
	Resource child;
	
	@Mock
	ValueMap map;
	
	@Mock
	ValueMap propMap;
	
	@Mock
	ValueMap valueMap2;
	
	@Mock
	ValueMap hitResourceValueMap;
	
	@Mock
	Resource headerParentResource;
	
	@Mock
	Resource headerResource;
	
	@Mock
	Resource jcrContentResource;
	
	
	ComponentExporterService componentExporterService2;
	
	
	ComponentExporterService componentExporterService3;
	
	@Mock
	Resource childHeaderContentResource;
	
	@Mock
	ValueMap hertzLinksProps;
	
	@Mock
	Iterator<Resource> mockIterator;
	
	@Mock 
	Resource requestPathResource;
	
	String requestPath = TestConstants.OFFER_LANDING_PATH;
	
	String pagePath = "/content/hertz-rac/rac-web/en-us/headers/irac_main";
	
	String path = "path";
	
	List<Hit> mockList;
	
	@Mock
	RequestPathInfo requestPathInfo;
	
	@Mock
	Resource jcrResource;
	
	@Mock
	Page requestPathPage;
	
	@Mock
	Iterator<Resource> resIterator;
	

	@Before
	public final void setup() throws Exception {
		nativeUse = PowerMockito.mock(NativeUse.class);
		componentExporterService2 = PowerMockito.mock(ComponentExporterService.class);
		componentExporterService3 = PowerMockito.mock(ComponentExporterService.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);
		Mockito.doCallRealMethod().when(nativeUse).activate();

	}

	@Test
	public final void testActivate() throws Exception {
		
		mockList=new ArrayList<Hit>();
		Node node = PowerMockito.mock(Node.class);
		PropertyIterator iterator = PowerMockito.mock(PropertyIterator.class);
		Property property = PowerMockito.mock(Property.class);
		NodeIterator nIterator = PowerMockito.mock(NodeIterator.class);
		when(node.getProperties()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true, false);
		when(iterator.nextProperty()).thenReturn(property);
		when(property.getType()).thenReturn(2);
		when(property.getName()).thenReturn("name");
		when(property.isMultiple()).thenReturn(true);
		when(property.getLength()).thenReturn(new Long(1));
		when(property.getLengths()).thenReturn(new long[] { new Long(1) });
		when(node.getNodes()).thenReturn(nIterator);
		when(nIterator.hasNext()).thenReturn(false);
		
		when(sling.getPathInfo()).thenReturn(
				TestConstants.OFFER_LANDING_PATH + "/jcr:content");
		when(nativeUse.getCurrentPage()).thenReturn(currentPage);
		when(currentPage.getTitle()).thenReturn("test");
		Resource resource = PowerMockito.mock(Resource.class);
		when(nativeUse.getResource()).thenReturn(resource);
		when(resource.adaptTo(Node.class)).thenReturn(node);
		when(nativeUse.get(HertzConstants.COMPONENTS, String.class))
				.thenReturn("hero,rewards");
		Resource contentResource = Mockito.mock(Resource.class);
		when(currentPage.getContentResource("responsivegrid")).thenReturn(
				contentResource);
		
		String [] array=new String[]{"{'configurableSeoKey':'title','configurableSeoValue':'valie'}"};
		when(nativeUse.getRequest()).thenReturn(mockRequest);
		when(mockRequest.getPathInfo()).thenReturn(requestPath);
		when(mockRequest.getRequestPathInfo()).thenReturn(requestPathInfo);
		when(nativeUse.getCurrentPage()).thenReturn(currentPage);
		when(currentPage.getTitle()).thenReturn("title");
		when(nativeUse.getResource()).thenReturn(pageResource);
		when(pageResource.adaptTo(ValueMap.class)).thenReturn(propMap);
		when(propMap.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS, new String[] {})).thenReturn(array);
		when(pageResource.getPath()).thenReturn(pagePath);
		when(pageResource.getResourceResolver()).thenReturn(resourceResolver);
		when(resourceResolver.getResource(Mockito.anyString())).thenReturn(jcrContentResource);
		when(jcrContentResource.adaptTo(ValueMap.class)).thenReturn(map);
		when(map.containsKey(NameConstants.PN_HIDE_IN_NAV)).thenReturn(true);
		
		when(pageResource.getResourceType()).thenReturn(HertzConstants.HEADER_RES_TYPE);
		when(pageResource.getValueMap()).thenReturn(hitResourceValueMap);
		when(hitResourceValueMap.containsKey(HertzConstants.HEADER_REFERENCE)).thenReturn(false);
		when(pageResource.getParent()).thenReturn(headerParentResource);
		when(headerParentResource.getPath()).thenReturn(path);
		when(childHeaderContentResource.getChild("header")).thenReturn(headerResource);
		when(resourceResolver1.getResource(Mockito.anyString())).thenReturn(requestPathResource);
		
		when(map.containsKey(NameConstants.PN_HIDE_IN_NAV)).thenReturn(true);
				
		ComponentExporterService[] exporterService1 = ArrayUtils.toArray(
				componentExporterService2, componentExporterService3);
		when(nativeUse.getSlingScriptHelper()).thenReturn(slingExporterScriptHelper);
		when(slingExporterScriptHelper.getServices(ComponentExporterService.class,
                "(identifier=" + compConfigArray[0] + ")")).thenReturn(exporterService1);
		SpaPageBean spaPageBean1 = Mockito.mock(SpaPageBean.class);
		when(nativeUse.getSpaBean()).thenReturn(spaPageBean1);

		nativeUse.activate();
	}
	
	@Test
	public final void testActivateWithNullGrid() throws RepositoryException, JSONException, IOException, ServletException{
		
		mockList=new ArrayList<Hit>();
		Node node = PowerMockito.mock(Node.class);
		PropertyIterator iterator = PowerMockito.mock(PropertyIterator.class);
		Property property = PowerMockito.mock(Property.class);
		NodeIterator nIterator = PowerMockito.mock(NodeIterator.class);
		when(node.getProperties()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true, false);
		when(iterator.nextProperty()).thenReturn(property);
		when(property.getType()).thenReturn(2);
		when(property.getName()).thenReturn("name");
		when(property.isMultiple()).thenReturn(true);
		when(property.getLength()).thenReturn(new Long(1));
		when(property.getLengths()).thenReturn(new long[] { new Long(1) });
		when(node.getNodes()).thenReturn(nIterator);
		when(nIterator.hasNext()).thenReturn(false);
		
		when(sling.getPathInfo()).thenReturn(
				TestConstants.OFFER_LANDING_PATH + "/jcr:content");
		when(nativeUse.getCurrentPage()).thenReturn(currentPage);
		when(currentPage.getTitle()).thenReturn("test");
		Resource resource = PowerMockito.mock(Resource.class);
		when(nativeUse.getResource()).thenReturn(resource);
		when(resource.adaptTo(Node.class)).thenReturn(node);
		when(nativeUse.get(HertzConstants.COMPONENTS, String.class)).thenReturn("he\\.ro,rewards");
		when(currentPage.getContentResource("responsivegrid")).thenReturn(
				null);
		String [] array=new String[]{"{'configurableSeoKey':'title','configurableSeoValue':'valie'}"};
		when(nativeUse.getRequest()).thenReturn(mockRequest);
		when(mockRequest.getPathInfo()).thenReturn(requestPath);
		when(mockRequest.getRequestPathInfo()).thenReturn(requestPathInfo);
		when(nativeUse.getCurrentPage()).thenReturn(currentPage);
		when(currentPage.getTitle()).thenReturn("title");
		when(nativeUse.getResource()).thenReturn(pageResource);
		when(pageResource.adaptTo(ValueMap.class)).thenReturn(propMap);
		when(propMap.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS, new String[] {})).thenReturn(array);
		when(pageResource.getPath()).thenReturn(pagePath);
		when(pageResource.getResourceResolver()).thenReturn(resourceResolver);
		when(resourceResolver.getResource(Mockito.anyString())).thenReturn(jcrContentResource);
		when(jcrContentResource.adaptTo(ValueMap.class)).thenReturn(map);
		when(map.containsKey(NameConstants.PN_HIDE_IN_NAV)).thenReturn(true);
		
		when(pageResource.getResourceType()).thenReturn(HertzConstants.HEADER_RES_TYPE);
		when(pageResource.getValueMap()).thenReturn(hitResourceValueMap);
		when(hitResourceValueMap.containsKey(HertzConstants.HEADER_REFERENCE)).thenReturn(false);
		when(pageResource.getParent()).thenReturn(headerParentResource);
		when(headerParentResource.getPath()).thenReturn(path);
		when(childHeaderContentResource.getChild("header")).thenReturn(headerResource);
		when(resourceResolver1.getResource(Mockito.anyString())).thenReturn(requestPathResource);
		
		when(map.containsKey(NameConstants.PN_HIDE_IN_NAV)).thenReturn(true);
				
		ComponentExporterService[] exporterService1 = ArrayUtils.toArray(
				componentExporterService2, componentExporterService3);
		when(nativeUse.getSlingScriptHelper()).thenReturn(slingExporterScriptHelper);
		when(slingExporterScriptHelper.getServices(ComponentExporterService.class,
                "(identifier=" + compConfigArray[0] + ")")).thenReturn(exporterService1);
		SpaPageBean spaPageBean1 = Mockito.mock(SpaPageBean.class);
		when(nativeUse.getSpaBean()).thenReturn(spaPageBean1);

		nativeUse.activate();
	}
	
	@Test
	public final void testActivateForNullService() throws Exception {
		
		mockList=new ArrayList<Hit>();
		Node node = PowerMockito.mock(Node.class);
		PropertyIterator iterator = PowerMockito.mock(PropertyIterator.class);
		Property property = PowerMockito.mock(Property.class);
		NodeIterator nIterator = PowerMockito.mock(NodeIterator.class);
		when(node.getProperties()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true, false);
		when(iterator.nextProperty()).thenReturn(property);
		when(property.getType()).thenReturn(2);
		when(property.getName()).thenReturn("name");
		when(property.isMultiple()).thenReturn(true);
		when(property.getLength()).thenReturn(new Long(1));
		when(property.getLengths()).thenReturn(new long[] { new Long(1) });
		when(node.getNodes()).thenReturn(nIterator);
		when(nIterator.hasNext()).thenReturn(false);
		
		when(sling.getPathInfo()).thenReturn(
				TestConstants.OFFER_LANDING_PATH + "/jcr:content");
		when(nativeUse.getCurrentPage()).thenReturn(currentPage);
		when(currentPage.getTitle()).thenReturn("test");
		Resource resource = PowerMockito.mock(Resource.class);
		when(nativeUse.getResource()).thenReturn(resource);
		when(resource.adaptTo(Node.class)).thenReturn(node);
		when(nativeUse.get(HertzConstants.COMPONENTS, String.class))
				.thenReturn("hero,rewards");
		Resource contentResource = Mockito.mock(Resource.class);
		when(currentPage.getContentResource("responsivegrid")).thenReturn(
				contentResource);
		
		String [] array=new String[]{"{'configurableSeoKey':'title','configurableSeoValue':'valie'}"};
		when(nativeUse.getRequest()).thenReturn(mockRequest);
		when(mockRequest.getPathInfo()).thenReturn(requestPath);
		when(mockRequest.getRequestPathInfo()).thenReturn(requestPathInfo);
		when(nativeUse.getCurrentPage()).thenReturn(currentPage);
		when(currentPage.getTitle()).thenReturn("title");
		when(nativeUse.getResource()).thenReturn(pageResource);
		when(pageResource.adaptTo(ValueMap.class)).thenReturn(propMap);
		when(propMap.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS, new String[] {})).thenReturn(array);
		when(pageResource.getPath()).thenReturn(pagePath);
		when(pageResource.getResourceResolver()).thenReturn(resourceResolver);
		when(resourceResolver.getResource(Mockito.anyString())).thenReturn(jcrContentResource);
		when(jcrContentResource.adaptTo(ValueMap.class)).thenReturn(map);
		when(map.containsKey(NameConstants.PN_HIDE_IN_NAV)).thenReturn(true);
		
		when(pageResource.getResourceType()).thenReturn(HertzConstants.HEADER_RES_TYPE);
		when(pageResource.getValueMap()).thenReturn(hitResourceValueMap);
		when(hitResourceValueMap.containsKey(HertzConstants.HEADER_REFERENCE)).thenReturn(false);
		when(pageResource.getParent()).thenReturn(headerParentResource);
		when(headerParentResource.getPath()).thenReturn(path);
		when(childHeaderContentResource.getChild("header")).thenReturn(headerResource);
		when(resourceResolver1.getResource(Mockito.anyString())).thenReturn(requestPathResource);
		
		when(map.containsKey(NameConstants.PN_HIDE_IN_NAV)).thenReturn(true);
				
		ComponentExporterService[] exporterService1 = ArrayUtils.toArray();
		when(nativeUse.getSlingScriptHelper()).thenReturn(slingExporterScriptHelper);
		when(slingExporterScriptHelper.getServices(ComponentExporterService.class,
                "(identifier=" + compConfigArray[0] + ")")).thenReturn(exporterService1);
		SpaPageBean spaPageBean1 = Mockito.mock(SpaPageBean.class);
		when(nativeUse.getSpaBean()).thenReturn(spaPageBean1);

		nativeUse.activate();
	}
	
	@Test
	public final void testActivateForServiceNameWithColon() throws Exception {
		String compConfigArray1[] = { "hero:hero","rewards" };
		mockList=new ArrayList<Hit>();
		Node node = PowerMockito.mock(Node.class);
		PropertyIterator iterator = PowerMockito.mock(PropertyIterator.class);
		Property property = PowerMockito.mock(Property.class);
		NodeIterator nIterator = PowerMockito.mock(NodeIterator.class);
		when(node.getProperties()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true, false);
		when(iterator.nextProperty()).thenReturn(property);
		when(property.getType()).thenReturn(2);
		when(property.getName()).thenReturn("name");
		when(property.isMultiple()).thenReturn(true);
		when(property.getLength()).thenReturn(new Long(1));
		when(property.getLengths()).thenReturn(new long[] { new Long(1) });
		when(node.getNodes()).thenReturn(nIterator);
		when(nIterator.hasNext()).thenReturn(false);
		
		when(sling.getPathInfo()).thenReturn(
				TestConstants.OFFER_LANDING_PATH + "/jcr:content");
		when(nativeUse.getCurrentPage()).thenReturn(currentPage);
		when(currentPage.getTitle()).thenReturn("test");
		Resource resource = PowerMockito.mock(Resource.class);
		when(nativeUse.getResource()).thenReturn(resource);
		when(resource.adaptTo(Node.class)).thenReturn(node);
		when(nativeUse.get(HertzConstants.COMPONENTS, String.class))
				.thenReturn("hero:hero,rewards");
		Resource contentResource = Mockito.mock(Resource.class);
		when(currentPage.getContentResource("responsivegrid")).thenReturn(
				contentResource);
		
		String [] array=new String[]{"{'configurableSeoKey':'title','configurableSeoValue':'valie'}"};
		when(nativeUse.getRequest()).thenReturn(mockRequest);
		when(mockRequest.getPathInfo()).thenReturn(requestPath);
		when(mockRequest.getRequestPathInfo()).thenReturn(requestPathInfo);
		when(nativeUse.getCurrentPage()).thenReturn(currentPage);
		when(currentPage.getTitle()).thenReturn("title");
		when(nativeUse.getResource()).thenReturn(pageResource);
		when(pageResource.adaptTo(ValueMap.class)).thenReturn(propMap);
		when(propMap.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS, new String[] {})).thenReturn(array);
		when(pageResource.getPath()).thenReturn(pagePath);
		when(pageResource.getResourceResolver()).thenReturn(resourceResolver);
		when(resourceResolver.getResource(Mockito.anyString())).thenReturn(jcrContentResource);
		when(jcrContentResource.adaptTo(ValueMap.class)).thenReturn(map);
		when(map.containsKey(NameConstants.PN_HIDE_IN_NAV)).thenReturn(true);
		
		when(pageResource.getResourceType()).thenReturn(HertzConstants.HEADER_RES_TYPE);
		when(pageResource.getValueMap()).thenReturn(hitResourceValueMap);
		when(hitResourceValueMap.containsKey(HertzConstants.HEADER_REFERENCE)).thenReturn(false);
		when(pageResource.getParent()).thenReturn(headerParentResource);
		when(headerParentResource.getPath()).thenReturn(path);
		when(childHeaderContentResource.getChild("header")).thenReturn(headerResource);
		when(resourceResolver1.getResource(Mockito.anyString())).thenReturn(requestPathResource);
		
		when(map.containsKey(NameConstants.PN_HIDE_IN_NAV)).thenReturn(true);
				
		ComponentExporterService[] exporterService1 = ArrayUtils.toArray(
				componentExporterService2, componentExporterService3);
		when(nativeUse.getSlingScriptHelper()).thenReturn(slingExporterScriptHelper);
		when(slingExporterScriptHelper.getServices(ComponentExporterService.class,
                "(identifier=" + compConfigArray1[0] + ")")).thenReturn(exporterService1);
		SpaPageBean spaPageBean1 = Mockito.mock(SpaPageBean.class);
		when(nativeUse.getSpaBean()).thenReturn(spaPageBean1);

		nativeUse.activate();
	}
	
}
