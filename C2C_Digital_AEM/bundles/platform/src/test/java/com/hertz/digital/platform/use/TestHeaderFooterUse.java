package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
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
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.bean.MenuItemBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.constants.TestConstants;
import com.hertz.digital.platform.service.api.MenuBuilderService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestHeaderFooterUse {
	
	@InjectMocks
	private HeaderFooterUse headerFooterUse;
	
	@Mock
	Page page;
	
	@Mock
	SlingScriptHelper helper;
	
	@Mock
	SlingHttpServletResponse response;
	
	@Mock
	ResourceResolver resolver;
	
	@Mock
	MenuBuilderService menuService;
	
	@Mock
	QueryBuilder builder;
	
	
	@Mock
	Resource partnerResource;
	
	@Mock
	Node partnerNode;
	
	@Mock
	Property property;
	
	@Mock
	List<MenuItemBean> list;
	
	@Mock
	Session session;
	
	@Mock
	Query query;
	
	@Mock
	SearchResult result;
	
	List<Hit> mockList;
	
	@Mock
	Hit hit;
	
	@Mock
	Resource resource1;
	
	@Mock
	Resource pageResource;
	
	@Mock
	Resource headerResource;
	
	@Mock
	Resource childHeaderResource;
	
	@Mock
	Resource childHeaderContentResource;
	
	@Mock
	Resource searchResource;
	
	@Mock
	ValueMap valueMap1;
	
	@Mock
	ValueMap hitResourceValueMap;
	
	@Mock
	Resource logoChildResource;
	
	@Mock
	ValueMap valueMap2;
	
	@Mock
	ValueMap valueMap4;
	
	@Mock
	Resource countryLanguageChildResource;
	
	@Mock
	Resource loginChildResource;
	
	@Mock
	ValueMap valueMap3;
	
	@Mock
	ValueMap valueMap5;
	
	@Mock
	Resource chatResource;
	
	@Mock
	ValueMap chatProperties;
	
	@Mock
	Node loginNode;
	
	@Mock
	Resource footerResource;
	
	@Mock
	Value value;
	
	private String  loginItem = "{'flyoutItemTxt':'flyout1','flyoutItemPath':'dummyPath','openUrlNewWindow':true}";
	
	private String headerReference = "/content/hertz-rac/rac-web/en-us/headers/irac_main";
	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		headerFooterUse=PowerMockito.mock(HeaderFooterUse.class);
		mockList=new ArrayList<>();
		Mockito.doCallRealMethod().when(headerFooterUse).activate();
		
	}
	
	@Test
	public final void testActivate() throws Exception{

			when(headerFooterUse.getCurrentPage()).thenReturn(page);
			when(page.getPath()).thenReturn(StringUtils.EMPTY);
			when(headerFooterUse.getSlingScriptHelper()).thenReturn(helper);
			when(headerFooterUse.getResponse()).thenReturn(response);
			when(headerFooterUse.getResourceResolver()).thenReturn(resolver);
			when(headerFooterUse.getResource()).thenReturn(pageResource);
			when(helper.getService(MenuBuilderService.class)).thenReturn(menuService);
			//when(resolver.adaptTo(QueryBuilder.class)).thenReturn(builder);
			when(resolver.getResource(TestConstants.PARTNER_PAGE_PATH+ HertzConstants.SLASH + HertzConstants.JCR_CONTENT)).thenReturn(partnerResource);
			when(partnerResource.adaptTo(Node.class)).thenReturn(partnerNode);
			when(partnerNode.hasProperty("topnavpath")).thenReturn(true);
			when(partnerNode.getProperty("topnavpath")).thenReturn(property);
			when(property.getString()).thenReturn("/topnav");
			when(menuService.getMenuJSON(partnerResource)).thenReturn(list);
			//when(resolver.adaptTo(Session.class)).thenReturn(session);
			//when(builder.createQuery(any(PredicateGroup.class), eq(session))).thenReturn(query);
			//when(query.getResult()).thenReturn(result);
			//mockList.add(hit);
			//when(result.getHits()).thenReturn(mockList);
			//when(hit.getResource()).thenReturn(resource1);
			when(pageResource.getResourceType()).thenReturn(HertzConstants.HEADER_RES_TYPE);
			when(pageResource.getValueMap()).thenReturn(hitResourceValueMap);
			when(hitResourceValueMap.containsKey(HertzConstants.HEADER_REFERENCE)).thenReturn(true);
			when(hitResourceValueMap.get(HertzConstants.HEADER_REFERENCE)).thenReturn(headerReference);
			when(resolver.getResource(headerReference)).thenReturn(childHeaderResource);
			when(childHeaderResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(childHeaderContentResource);
			when(childHeaderContentResource.getChild("header")).thenReturn(headerResource);
			when(childHeaderContentResource.getChild(HertzConstants.FOOTER)).thenReturn(footerResource);
			
			when(headerResource.getChild("headersearch")).thenReturn(searchResource);
			when(searchResource.adaptTo(ValueMap.class)).thenReturn(valueMap1);
			when(valueMap1.containsKey(Mockito.anyString())).thenReturn(true);
			when(valueMap1.get("searchPath", String.class)).thenReturn("searchPath");
			when(valueMap1.get("searchIconAltTxt", String.class)).thenReturn("searchIconAltTxt");
			when(valueMap1.get("searchPlaTxt", String.class)).thenReturn("searchPlaTxt");

			when(headerResource.getChild("logo")).thenReturn(logoChildResource);
			when(logoChildResource.adaptTo(ValueMap.class)).thenReturn(valueMap2);
			when(valueMap2.containsKey(Mockito.anyString())).thenReturn(true);
			when(valueMap2.get("fileReference", String.class)).thenReturn("fileReference");
			when(valueMap2.get("logoURL", String.class)).thenReturn("logoURL");
			when(valueMap2.get("logoImagealtTxt", String.class)).thenReturn("logoImagealtTxt");
			
			when(headerResource.getChild("countrylanguage")).thenReturn(countryLanguageChildResource);
			when(countryLanguageChildResource.adaptTo(ValueMap.class)).thenReturn(valueMap4);
			when(valueMap4.containsKey(Mockito.anyString())).thenReturn(true);
			when(valueMap4.get("countryLabel", String.class)).thenReturn("Country");
			when(valueMap4.get("languageLabel", String.class)).thenReturn("Language");
			when(valueMap4.get("updateLabel", String.class)).thenReturn("Update");
			
			when(headerResource.getChild("login")).thenReturn(loginChildResource);
			when(loginChildResource.adaptTo(Node.class)).thenReturn(loginNode);
			when(loginChildResource.adaptTo(ValueMap.class)).thenReturn(valueMap3);
			when(valueMap3.containsKey(Mockito.anyString())).thenReturn(true);
			when(valueMap3.get("loginBtnTxt", String.class)).thenReturn("loginBtnTxt");
			when(valueMap3.get("loginPlaTxt", String.class)).thenReturn("loginPlaTxt");
			when(headerResource.getChild(HertzConstants.CHAT_NODE)).thenReturn(chatResource);
			when(chatResource.adaptTo(ValueMap.class)).thenReturn(chatProperties);
			when(chatProperties.containsKey(Mockito.anyString())).thenReturn(true);
			when(chatProperties.get(Mockito.anyString())).thenReturn("chat");
			when(loginNode.hasProperty(HertzConstants.LOGIN_ITEMS)).thenReturn(true);
			when(loginNode.getProperty(HertzConstants.LOGIN_ITEMS)).thenReturn(property);
			when(property.isMultiple()).thenReturn(false);
			when(property.getValue()).thenReturn(value);
			when(value.getString()).thenReturn(loginItem);
			when(footerResource.adaptTo(ValueMap.class)).thenReturn(valueMap5);
			when(valueMap5.containsKey(Mockito.anyString())).thenReturn(true);
			when(valueMap5.get(HertzConstants.LEGAL_DESRIPTION_TEXT, String.class)).thenReturn("legalDescriptionText");
			when(valueMap5.get(HertzConstants.LEGAL_LINK_LABEL_1,String.class)).thenReturn("firsrLegalLink");
			when(valueMap5.get(HertzConstants.LEGAL_LINK_LABEL_2,String.class)).thenReturn("secondLegalLink");
			headerFooterUse.activate();
		
	}
	
	@Test
	public final void testGettersSetters(){
		Mockito.doCallRealMethod().when(headerFooterUse).getJsonString();
		Mockito.doCallRealMethod().when(headerFooterUse).setJsonString("jsonString");
		headerFooterUse.setJsonString("jsonString");
		Assert.assertTrue(headerFooterUse.getJsonString().equals("jsonString"));
	}
}
