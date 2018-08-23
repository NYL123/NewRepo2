package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.engine.SlingRequestProcessor;
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
import com.day.cq.contentsync.handler.util.RequestResponseFactory;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.WCMMode;
import com.hertz.digital.platform.bean.spa.SpaPageBean;
import com.hertz.digital.platform.constants.HertzConstants;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class,WCMMode.class})
public class TestPartnerDetailUse {
	@InjectMocks
	PartnerDetailUse partnerDetailUse;
	
	Logger log;
	
	@Mock
	SlingHttpServletRequest sling;
	
	@Mock
	Page page;
	
	@Mock 
	Page currentPage;
	
	String compConfigArray[] = {"1:2"};

	@Mock
	Resource resource;

	@Mock
	Iterator<Resource> iterator;

	@Mock 
	ValueMap valueMap;
	
	@Mock
	List<Map<String, Object>> componentsList;
	
	@Mock
	RequestResponseFactory requestResponseFactory;

	@Mock
	SlingRequestProcessor requestProcessor;

	@Mock
	Map<String, Object> beanMap;
	
	@Mock
	HttpServletRequest request;
	@Mock
	SlingHttpServletResponse response;

	@Mock
	SlingScriptHelper scriptHelper;

	@Mock 
	Iterator<Map<String, Object>> mapIterator;

	@Mock
	ResourceResolver resolver;
	
	@Before
	public final void setup() throws Exception {
		partnerDetailUse =PowerMockito.mock(PartnerDetailUse.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);
		Mockito.doCallRealMethod().when(partnerDetailUse).activate();
		Mockito.doCallRealMethod().when(partnerDetailUse).prepareJsonString();
		Mockito.doCallRealMethod().when(partnerDetailUse).getHTMLofComponent(resolver, requestResponseFactory, requestProcessor, "pah/to/resource");
		
	}
	
	@Test
	public final void testPrepareJsonString() throws Exception {
		
		
		
		//beanMap.put("test", "test");
		//beanMap.put("marketingslot2", "value");
		componentsList.add(beanMap);
		when(beanMap.containsKey(Mockito.eq("marketingslot2"))).thenReturn(true);
		SpaPageBean spaPageBean = Mockito.mock(SpaPageBean.class);
		spaPageBean.setIncludedComponents(componentsList);
		when(partnerDetailUse.getSpaBean()).thenReturn(spaPageBean);
		when(spaPageBean.getIncludedComponents()).thenReturn(componentsList);
		when(componentsList.iterator()).thenReturn(mapIterator);
		when(mapIterator.hasNext()).thenReturn(true,false);
		when(mapIterator.next()).thenReturn(beanMap);
		
		when(partnerDetailUse.getCurrentPage()).thenReturn(page);
		when(page.getContentResource(Mockito.eq("marketingslot2"))).thenReturn(resource);
		when(resource.listChildren()).thenReturn(iterator);
		when(resource.hasChildren()).thenReturn(true);
		when(iterator.hasNext()).thenReturn(true,true,true,false);
		when(iterator.next()).thenReturn(resource);
		when(resource.getValueMap()).thenReturn(valueMap);
		
		when(partnerDetailUse.getSlingScriptHelper()).thenReturn(scriptHelper);
		when(partnerDetailUse.getResourceResolver()).thenReturn(resolver);
		
		when(resource.getPath()).thenReturn("/path/to/resource");
		when(scriptHelper.getService(RequestResponseFactory.class)).thenReturn(requestResponseFactory);
		when(scriptHelper.getService(SlingRequestProcessor.class)).thenReturn(requestProcessor);
		
		when(valueMap.get(Mockito.eq(HertzConstants.SLING_RESOURCE_TYPE))).thenReturn("hertz/components/content/tabletextaccordion");
		partnerDetailUse.prepareJsonString();
		
		when(mapIterator.hasNext()).thenReturn(true,false);
		when(iterator.hasNext()).thenReturn(true,true,true,false);
		when(valueMap.get(Mockito.eq(HertzConstants.SLING_RESOURCE_TYPE))).thenReturn("something/else");
		partnerDetailUse.prepareJsonString();
		
	}
	
	@Test
	public final void testGetHTMLofComponent() throws Exception {
		
		PowerMockito.mockStatic(WCMMode.class);
		when(requestResponseFactory.createRequest("GET", "pah/to/resource")).thenReturn(request);
		when(partnerDetailUse.getSlingScriptHelper()).thenReturn(scriptHelper);
		when(partnerDetailUse.getResourceResolver()).thenReturn(resolver);
		when(scriptHelper.getService(RequestResponseFactory.class)).thenReturn(requestResponseFactory);
		when(scriptHelper.getService(SlingRequestProcessor.class)).thenReturn(requestProcessor);
		partnerDetailUse.getHTMLofComponent(resolver, requestResponseFactory, requestProcessor, "pah/to/resource");
	}
}
