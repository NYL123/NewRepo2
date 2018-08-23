package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.nodetype.PropertyDefinition;
import javax.servlet.ServletException;

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
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.bean.spa.SpaPageBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.constants.TestConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.use.spa.GenericPageUse;

import junit.framework.Assert;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestGenericPageUse {

	private static final String SPA_TEPMPLATE = "/apps/hertz/templates/spa";
	
	/**
	 * PARTNER_BASE_PAGE_PATH
	 */
	private static final String PARTNER_BASE_PAGE_PATH =  "partnerBasePagePath";
	
	
	@InjectMocks
	GenericPageUse genericPageUse;

	Logger log;
	
	@Mock
	ResourceResolver resourceResolver;
	
	@Mock
	Resource headerResource;
	
	@Mock
	Resource childHeaderResource;
	
	@Mock
	Resource partnerHomePageResource;
	
	@Mock
	Resource cpCodeResource;
	
	@Mock
	Resource headerParentResource;
	
	@Mock
	Resource childHeaderContentResource;
	
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
	ValueMap hitResourceValueMap;
	
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
	ValueMap hertzLinksProps;
	
	@Mock
	Iterator<Resource> mockIterator;
	
	@Mock 
	Resource requestPathResource;
	
	String requestPath = TestConstants.OFFER_LANDING_PATH;
	
	String pagePath = "/content/hertz-rac/rac-web/en-us/headers/irac_main";
	
	List<Hit> mockList;
	
	@Mock
	RequestPathInfo requestPathInfo;
	
	@Mock
	Resource jcrResource;
	
	@Mock
	Page requestPathPage;
	
	@Mock
	Resource footerResource;
	
	private String headerReference = "/content/hertz-rac/rac-web/en-us/headers/irac_main";
	
	String path = "path";

	@Before
	public final void setup() throws Exception {
		genericPageUse =PowerMockito.mock(GenericPageUse.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);
		Mockito.doCallRealMethod().when(genericPageUse).activate();

	}
	
	@Test
	
	public final void testActivate() throws Exception {
		mockList=new ArrayList<Hit>();
		String path = "path";
		String [] array=new String[]{"{'configurableSeoKey':'title','configurableSeoValue':'valie'}"};
		//when(genericPageUse.getRequest()).thenReturn(resource);
		when(mockRequest.getRequestPathInfo()).thenReturn(requestPathInfo);
		String[] selectors = {"spa", "a"};
		when(requestPathInfo.getSelectors()).thenReturn(selectors);
		ValueMap valueMap = Mockito.mock(ValueMap.class);
		when(genericPageUse.getPageProperties()).thenReturn(valueMap);
		String basePath= "/content/hertz-rac/rec-web/us/en/reservations";
		when(valueMap.get(PARTNER_BASE_PAGE_PATH)).thenReturn(basePath);
		when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver);
		when(resourceResolver.getResource(basePath)).thenReturn(partnerHomePageResource);
		when(partnerHomePageResource.getChild(Mockito.anyString())).thenReturn(cpCodeResource);
		when(resourceResolver.getResource(basePath + HertzConstants.SLASH + "a")).thenReturn(requestPathResource);		
		when(resourceResolver.getResource(basePath + HertzConstants.SLASH + "a" + HertzConstants.SLASH
				+ HertzConstants.JCR_CONTENT)).thenReturn(jcrResource);
		
		when(requestPathResource.adaptTo(Page.class)).thenReturn(requestPathPage);
		when(jcrResource.adaptTo(ValueMap.class)).thenReturn(valueMap2);
		when(jcrResource.getPath()).thenReturn( HertzConstants.SLASH + "a" + HertzConstants.SLASH
				+ HertzConstants.JCR_CONTENT);
		when(valueMap2.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS, new String[]{})).thenReturn(array);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		when(requestPathPage.getPath()).thenReturn(basePath + HertzConstants.SLASH + "a" + HertzConstants.SLASH
				+ HertzConstants.JCR_CONTENT);
		
		when(genericPageUse.getCurrentPage()).thenReturn(page);
		when(page.adaptTo(Node.class)).thenReturn(pageNode);
		when(pageNode.getNode(HertzConstants.JCR_CONTENT)).thenReturn(pageJCRNode);
		when(pageJCRNode.hasProperty(HertzConstants.JCR_CQ_TEMPLATE)).thenReturn(true);
		when(pageJCRNode.getProperty(HertzConstants.JCR_CQ_TEMPLATE)).thenReturn(cqTemplateProperty);
		when(cqTemplateProperty.getString()).thenReturn(SPA_TEPMPLATE);
		when(genericPageUse.getResource()).thenReturn(resource);
		when(resource.adaptTo(ValueMap.class)).thenReturn(valueMap2);
		when(valueMap2.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS, new String[]{})).thenReturn(array);
		when(resource.getPath()).thenReturn(path);
		
		when(genericPageUse.getResource()).thenReturn(resource);
		when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver);
		//when(genericPageUse.getResponse()).thenReturn(response);
		when(genericPageUse.getSlingScriptHelper()).thenReturn(slingScriptHelper);
		when(resource.getResourceType()).thenReturn(HertzConstants.HEADER_RES_TYPE);
		when(resource.getValueMap()).thenReturn(hitResourceValueMap);
		when(hitResourceValueMap.containsKey(HertzConstants.HEADER_REFERENCE)).thenReturn(false);
		when(resource.getParent()).thenReturn(headerParentResource);
		when(headerParentResource.getPath()).thenReturn(path);
		/*when(hitResourceValueMap.get(HertzConstants.HEADER_REFERENCE)).thenReturn(headerReference);
		when(resourceResolver.getResource(headerReference)).thenReturn(childHeaderResource);
		when(childHeaderResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(childHeaderContentResource);*/
		when(childHeaderContentResource.getChild("header")).thenReturn(headerResource);
		when(resourceResolver.getResource(Mockito.anyString())).thenReturn(requestPathResource);
		//when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver1);
		//when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		//when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver2);
		//when(resourceResolver.adaptTo(Session.class)).thenReturn(session);

		when(genericPageUse.getSlingScriptHelper()).thenReturn(slingScriptHelper);
		when(slingScriptHelper.getService(Externalizer.class)).thenReturn(externalizer);
		when(genericPageUse.getCurrentPage()).thenReturn(page);
		when(page.getTitle()).thenReturn("home");
		when(genericPageUse.getRequest()).thenReturn(mockRequest);
		when(mockRequest.getPathInfo()).thenReturn(TestConstants.HOME_JCR_CONTENT_PATH);
		when(resource.adaptTo(Node.class)).thenReturn(node);
		when(node.getProperties()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true,false);
		when(iterator.nextProperty()).thenReturn(property);
		when(iterator.next()).thenReturn(property);
		when(property.getType()).thenReturn(PropertyType.BINARY);
		when(property.getName()).thenReturn("homeproperty");
		when(property.getDefinition()).thenReturn(definition);
		when(property.getValue()).thenReturn(value);
		when(value.getType()).thenReturn(PropertyType.BINARY);
		when(node.getNodes()).thenReturn(nodeIterator);
		when(nodeIterator.hasNext()).thenReturn(false);
		when(nodeIterator.nextNode()).thenReturn(childNode);
		when(childNode.getName()).thenReturn("home");
		when(slingScriptHelper.getService(HertzConfigFactory.class)).thenReturn(configFactory);
		when(configFactory.getPropertyValue(HertzConstants.Hertz_SPA_ALLOWED_COMPONENTS)).thenReturn(null);
		when(queryBuilder.createQuery(any(PredicateGroup.class), eq(session))).thenReturn(query);
		when(query.getResult()).thenReturn(result);
		mockList.add(hit);
		genericPageUse.activate();
		when(mockRequest.getPathInfo()).thenReturn(TestConstants.HOME_PATH);
		genericPageUse.activate();
	}

	@Test
	
	public final void testVariables() {
		Mockito.doCallRealMethod().when(genericPageUse).setJsonString("jsonString");
		Mockito.doCallRealMethod().when(genericPageUse).getJsonString();
		genericPageUse.setJsonString("jsonString");
		Assert.assertNotNull(genericPageUse.getJsonString());
	}
	
	@Test
	
	public final void testVariables1() {
		Mockito.doCallRealMethod().when(genericPageUse).setSpaBean(spaBean);
		Mockito.doCallRealMethod().when(genericPageUse).getSpaBean();
		genericPageUse.setSpaBean(spaBean);
		Assert.assertNotNull(genericPageUse.getSpaBean());
	}

	@Test
	
	public final void testGetPagePropertiesConfigMap() throws JSONException{
		//Mockito.doCallRealMethod().when(genericPageUse).getPagePropertiesConfigMap(resource);
		String configuredText="{\"configurableTextElementName\":\"ExpandedTripSummaryField\",\"configurableTextLabelKey\":\"Label\",\"configurableTextLabelValue\":\"Trip Summary\",\"configurableTextAriaLabelKey\":\"ariaLabel\",\"configurableTextAriaLabelValue\":\"Trip Summary\",\"configurableTextErrorKey\":\"\",\"configurableTextErrorValue\":\"\"}";;
		when(resource.getValueMap()).thenReturn(map);
		when(map.get(HertzConstants.CONFIGURED_TEXT)).thenReturn(new String[]{configuredText});
		String configuredTextArea="{\"configurableTextAreaElementName\":\"textAreaElement\",\"configurableTextAreaKey\":\"textArea\",\"configurableTextAreaValue\":\"Description text\"}";
		when(map.get(HertzConstants.CONFIGURED_TEXTAREA)).thenReturn(new String[]{configuredTextArea});
		String configuredLinks="{\"configurableLinkElementGroup\":\"SelectDifferentVehicleLinkField\",\"configurableLinkElement\":\"SelectADifferentVehicle\",\"configurableLinkTextKey\":\"linkText\",\"configurableLinkTextValue\":\"Select a different vehicle\",\"configurableLinkAriaLabelKey\":\"ariaLabel\",\"configurableLinkAriaLabelValue\":\"Select a different vehicle\",\"configurableLinkURLKey\":\"\",\"configurableLinkURLValue\":\"\",\"configurableLinkTargetTypeKey\":\"\",\"configurableLinkTargetTypeValue\":false}";
		when(map.get(HertzConstants.CONFIGURED_LINKS)).thenReturn(new String[]{configuredLinks});
		String configuredCheckboxes="{\"configurableCheckboxElementGroup\":\"checkoutUserAgreementOfTermsAndConditionsField\",\"configurableCheckboxElementName\":\"checkoutUserAgreementOfTermsAndConditions\",\"configurableCheckboxKey\":\"Text\",\"configurableCheckboxValue\":\"By checking this box you affirm that you have agreed to the Hertz Terms & Conditions\",\"configurableCheckboxAriaLabelKey\":\"ariaLabel\",\"configurableCheckboxAriaLabelValue\":\"By checking this box you affirm that you have agreed to the Hertz Terms & Conditions\",\"configurableCheckboxOrderKey\":\"Order\",\"configurableCheckboxOrderValue\":\"1\",\"configurableCheckboxIsDefaultKey\":\"DefaultSelection\",\"configurableCheckboxIsDefaultValue\":false}";
		when(map.get(HertzConstants.CONFIGURED_CHECKBOXES)).thenReturn(new String[]{configuredCheckboxes});
		String configuredRadioButtons="{\"configurableRadioButtonElementGroup\":\"checkoutEarnPointsRadioButton1Field\",\"configurableRadioButtonElement\":\"checkoutEarnPointsRadioButton1\",\"configurableRadioButtonKey\":\"Text\",\"configurableRadioButtonValue\":\"I want to earn Hertz Gold Plus Rewards Points\",\"configurableRadioButtonAriaLabelKey\":\"ariaLabel\",\"configurableRadioButtonAriaLabelValue\":\"I want to earn Hertz Gold Plus Rewards Points\",\"configurableRadioButtonOrderKey\":\"Order\",\"configurableRadioButtonOrderValue\":\"1\",\"configurableRadioButtonIsDefaultKey\":\"DefaultSelection\",\"configurableRadioButtonIsDefaultValue\":true}";
		when(map.get(HertzConstants.CONFIGURED_RADIO_BUTTONS)).thenReturn(new String[]{configuredRadioButtons});
		when(resource.getChild(HertzConstants.CONFIGURED_IMAGE)).thenReturn(childResource);
		when(childResource.listChildren()).thenReturn(mockIterator);
		when(mockIterator.hasNext()).thenReturn(true,false);
		when(mockIterator.next()).thenReturn(child);
		when(child.getValueMap()).thenReturn(valueMap2);
		when(valueMap2.get(HertzConstants.CONFIGURED_IMAGE_ELEMENT, String.class)).thenReturn("imageElement");
		when(valueMap2.get(HertzConstants.KEY_IMAGE, String.class)).thenReturn("keyImage");
		when(valueMap2.get(HertzConstants.VALUE_IMAGE,String.class)).thenReturn("valueImage");
		when(valueMap2.get(HertzConstants.CONFIGURED_IMAGE_ALT_TEXT_KEY, String.class)).thenReturn("imageAltTxtKey");
		when(valueMap2.get(HertzConstants.CONFIGURED_IMAGE_ALT_TEXT_VALUE,String.class)).thenReturn("imageAltTxtValue");
		when(valueMap2.get(HertzConstants.KEY_IMAGE_URL_LINK, String.class)).thenReturn("keyurl");
		when(valueMap2.get(HertzConstants.VALUE_IMAGE_URL_LINK,String.class)).thenReturn("valueurl");
		when(valueMap2.get(HertzConstants.KEY_IMAGE_LINK_TARGET_TYPE,String.class)).thenReturn("targettype");
		when(valueMap2.get(HertzConstants.VALUE_IMAGE_LINK_TARGET_TYPE, Boolean.class)).thenReturn(true);
		String configuredGroupItems="{\"configurableMultiGroupsElementName\":\"checkoutCountry\",\"configurableMultiGroupsElementGroup\":\"checkoutCountryField\",\"multiGroups\":[{\"configurableMultiGroupCodeKey\":\"checkoutCountryID\",\"configurableMultiGroupCodeValue\":\"<US Country code provided by MS>\",\"configurableMultiGroupTextKey\":\"checkoutCountryText\",\"configurableMultiGroupTextValue\":\"Country\",\"configurableMultiGroupDescriptionKey\":\"checkoutDefaultCountryName\",\"configurableMultiGroupDescriptionValue\":\"United States\"}]}";
		when(map.get(HertzConstants.CONFIGURED_MULTI_GROUPS)).thenReturn(new String[]{configuredGroupItems});
		String configuredErrorMessages="{\"configurableErrorCodeKey\":\"Error Code\",\"configurableErrorCodeValue\":\"Error Code Value\",\"configurableErrorMessageTextKey\":\"errorMessage\",\"configurableErrorMessageTextValue\":\"Please select correct option.\"}";
		when(map.get(HertzConstants.CONFIGURED_MULTI_ERROR_MESSAGES_ITEMS)).thenReturn(new String[]{configuredErrorMessages});
		String configuredMultiValuesKey="tripSummaryAgeButtons";
		String configuredMultiValuesElementName="tripSummaryAgeButtons";
		String configuredMultiValues="{\"configurableMultiValueKey\":\"ageRange1\",\"configurableMultiValue\":\"18-19\"}";
		when(map.get(HertzConstants.CONFIGURED_MULTI_VALUES_KEY)).thenReturn(new String(configuredMultiValuesKey));
		when(map.get(HertzConstants.CONFIGURED_MULTI_VALUES_ELEMENT_NAME)).thenReturn(new String(configuredMultiValuesElementName));
		when(map.get(HertzConstants.CONFIGURED_MULTI_VALUES)).thenReturn(new String[]{configuredMultiValues});
		//Assert.assertNotNull(genericPageUse.getPagePropertiesConfigMap(resource));
	}
	
	@Test
	
	public void testActivate1() throws RepositoryException, JSONException, ServletException, IOException{
		String [] array=new String[]{"{'configurableSeoKey':'title','configurableSeoValue':'valie'}"};
		when(genericPageUse.getRequest()).thenReturn(request);
		when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		String[] selectors = {"spa", "a"};
		when(requestPathInfo.getSelectors()).thenReturn(selectors);
		ValueMap valueMap = Mockito.mock(ValueMap.class);
		when(genericPageUse.getPageProperties()).thenReturn(valueMap);
		String basePath= "/content/hertz-rac/rec-web/us/en/reservations";
		when(valueMap.get(PARTNER_BASE_PAGE_PATH)).thenReturn(basePath);
		when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver);
		when(resourceResolver.getResource(basePath)).thenReturn(partnerHomePageResource);
		when(partnerHomePageResource.getChild(Mockito.anyString())).thenReturn(cpCodeResource);
		when(resourceResolver.getResource(basePath + HertzConstants.SLASH + "a")).thenReturn(requestPathResource);		
		when(resourceResolver.getResource(basePath + HertzConstants.SLASH + "a" + HertzConstants.SLASH
				+ HertzConstants.JCR_CONTENT)).thenReturn(jcrResource);		
		when(requestPathResource.adaptTo(Page.class)).thenReturn(requestPathPage);
		when(requestPathPage.hasContent()).thenReturn(true);
		Resource jcrRes = Mockito.mock(Resource.class);
		when(requestPathPage.getContentResource()).thenReturn(jcrRes);
		//jcrRes.getResourceType().equals(HertzConstants.RAPID_RESOURCE_TYPE)
		when(jcrRes.getResourceType()).thenReturn(HertzConstants.RAPID_RESOURCE_TYPE, HertzConstants.SPLASH_RESOURCE_TYPE);
		ValueMap hertzLinksProps = Mockito.mock(ValueMap.class);		
		when(jcrRes.getValueMap()).thenReturn(hertzLinksProps);
		when(hertzLinksProps.containsKey(HertzConstants.LABEL)).thenReturn(false);
		//= page.getContentResource()
		when(jcrResource.adaptTo(ValueMap.class)).thenReturn(valueMap2);
		when(jcrResource.getPath()).thenReturn( HertzConstants.SLASH + "a" + HertzConstants.SLASH
				+ HertzConstants.JCR_CONTENT);
		when(valueMap2.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS, new String[]{})).thenReturn(array);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		when(requestPathPage.getPath()).thenReturn(basePath + HertzConstants.SLASH + "a" + HertzConstants.SLASH
				+ HertzConstants.JCR_CONTENT);
		when(queryBuilder.createQuery(any(PredicateGroup.class), eq(session))).thenReturn(query);
		when(query.getResult()).thenReturn(result);
		Node node=PowerMockito.mock(Node.class);
		PropertyIterator iterator=PowerMockito.mock(PropertyIterator.class);
		Property property=PowerMockito.mock(Property.class);
		NodeIterator nIterator=PowerMockito.mock(NodeIterator.class);

		when(request.getPathInfo()).thenReturn(TestConstants.OFFER_LANDING_PATH+"/jcr:content");
		when(genericPageUse.getResource()).thenReturn(resource);
		when(resource.adaptTo(Node.class)).thenReturn(node);
		when(node.getProperties()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true,false);
		when(iterator.nextProperty()).thenReturn(property);
		when(property.getType()).thenReturn(2);
		when(property.getName()).thenReturn("name");
		when(property.isMultiple()).thenReturn(true);
		when(property.getLength()).thenReturn(new Long(1));
		when(property.getLengths()).thenReturn(new long[]{new Long(1)});
		when(node.getNodes()).thenReturn(nIterator);
		when(nIterator.hasNext()).thenReturn(false);
		genericPageUse.activate();
		when(request.getPathInfo()).thenReturn(TestConstants.OFFER_LANDING_PATH+"_jcr_content");
		genericPageUse.activate();
	}
	
	@Test
	public void testActivate2() throws RepositoryException, JSONException, ServletException, IOException{
		
		mockList=new ArrayList<Hit>();
		String [] array=new String[]{"{'configurableSeoKey':'title','configurableSeoValue':'valie'}"};
		when(genericPageUse.getRequest()).thenReturn(request);
		when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		String[] selectors = {"spa"};
		when(requestPathInfo.getSelectors()).thenReturn(selectors);
		ValueMap valueMap = Mockito.mock(ValueMap.class);
		when(genericPageUse.getPageProperties()).thenReturn(valueMap);
		String basePath= "/content/hertz-rac/rec-web/us/en/reservations";
		when(valueMap.get(PARTNER_BASE_PAGE_PATH)).thenReturn(basePath);
		when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver);
		when(resourceResolver.getResource(basePath + HertzConstants.SLASH + "a")).thenReturn(requestPathResource);		
		when(resourceResolver.getResource(basePath + HertzConstants.SLASH + "a" + HertzConstants.SLASH
				+ HertzConstants.JCR_CONTENT)).thenReturn(jcrResource);		
		when(requestPathResource.adaptTo(Page.class)).thenReturn(requestPathPage);
		when(jcrResource.adaptTo(ValueMap.class)).thenReturn(valueMap2);
		when(jcrResource.getPath()).thenReturn( HertzConstants.SLASH + "a" + HertzConstants.SLASH
				+ HertzConstants.JCR_CONTENT);
		when(valueMap2.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS, new String[]{})).thenReturn(array);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		when(requestPathPage.getPath()).thenReturn(basePath + HertzConstants.SLASH + "a" + HertzConstants.SLASH
				+ HertzConstants.JCR_CONTENT);
		
		
		when(queryBuilder.createQuery(any(PredicateGroup.class), eq(session))).thenReturn(query);
		when(query.getResult()).thenReturn(result);
		when(request.getPathInfo()).thenReturn(requestPath);
		when(genericPageUse.getCurrentPage()).thenReturn(page);
		when(page.getTitle()).thenReturn("home");
		when(page.getPath()).thenReturn(pagePath);
		when(genericPageUse.getResource()).thenReturn(resource);
		when(resource.adaptTo(ValueMap.class)).thenReturn(valueMap2);
		when(valueMap2.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS, new String[]{})).thenReturn(array);
		when(page.hasContent()).thenReturn(true);
		when(page.getContentResource()).thenReturn(pageJCRResource);
		when(pageJCRResource.getResourceType()).thenReturn(HertzConstants.RAPID_RESOURCE_TYPE);
		when(resource.getPath()).thenReturn("/account/rentals");
		//when(genericPageUse.getCurrentPage()).thenReturn(iracPage);
	//	when(iracPage.getPath()).thenReturn(pagePath);
		when(genericPageUse.getResource()).thenReturn(resource);
		when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver);
		//when(genericPageUse.getResponse()).thenReturn(response);
		when(genericPageUse.getSlingScriptHelper()).thenReturn(slingScriptHelper);
		when(resource.getResourceType()).thenReturn(HertzConstants.HEADER_RES_TYPE);
		when(resource.getValueMap()).thenReturn(hitResourceValueMap);
		when(hitResourceValueMap.containsKey(HertzConstants.HEADER_REFERENCE)).thenReturn(false);
		when(resource.getParent()).thenReturn(headerParentResource);
		when(headerParentResource.getPath()).thenReturn(path);
		/*when(hitResourceValueMap.get(HertzConstants.HEADER_REFERENCE)).thenReturn(headerReference);
		when(resourceResolver.getResource(headerReference)).thenReturn(childHeaderResource);
		when(childHeaderResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(childHeaderContentResource);*/
		when(childHeaderContentResource.getChild("header")).thenReturn(headerResource);
		when(resourceResolver.getResource(Mockito.anyString())).thenReturn(requestPathResource);
		//when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver1);
		//when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		//when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver2);
		//when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(slingScriptHelper.getService(HertzConfigFactory.class)).thenReturn(configFactory);
		when(configFactory.getPropertyValue(HertzConstants.Hertz_SPA_ALLOWED_COMPONENTS)).thenReturn(null);
		when(queryBuilder.createQuery(any(PredicateGroup.class), eq(session))).thenReturn(query);
		when(query.getResult()).thenReturn(result);
		mockList.add(hit);
		
		genericPageUse.activate();
	}
	
	@Test
	public void testActivate3() throws RepositoryException, JSONException, ServletException, IOException{
		mockList=new ArrayList<Hit>();
		String [] array=new String[]{"{'configurableSeoKey':'title','configurableSeoValue':'valie'}"};
		when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		String[] selectors = {"spa", "a"};
		when(requestPathInfo.getSelectors()).thenReturn(selectors);
		ValueMap valueMap = Mockito.mock(ValueMap.class);
		when(genericPageUse.getPageProperties()).thenReturn(valueMap);
		String basePath= "/content/hertz-rac/rec-web/us/en/reservations";
		when(valueMap.get(PARTNER_BASE_PAGE_PATH)).thenReturn(basePath);
		when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver);
		when(resourceResolver.getResource(basePath)).thenReturn(partnerHomePageResource);
		when(partnerHomePageResource.getChild(Mockito.anyString())).thenReturn(cpCodeResource);
		when(resourceResolver.getResource(basePath + HertzConstants.SLASH + "a")).thenReturn(requestPathResource);		
		when(resourceResolver.getResource(basePath + HertzConstants.SLASH + "a" + HertzConstants.SLASH
				+ HertzConstants.JCR_CONTENT)).thenReturn(jcrResource);		
		when(requestPathResource.adaptTo(Page.class)).thenReturn(requestPathPage);
		when(jcrResource.adaptTo(ValueMap.class)).thenReturn(valueMap2);
		when(jcrResource.getPath()).thenReturn( HertzConstants.SLASH + "a" + HertzConstants.SLASH
				+ HertzConstants.JCR_CONTENT);
		when(valueMap2.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS, new String[]{})).thenReturn(array);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		when(requestPathPage.getPath()).thenReturn(basePath + HertzConstants.SLASH + "a" + HertzConstants.SLASH
				+ HertzConstants.JCR_CONTENT);
		when(queryBuilder.createQuery(any(PredicateGroup.class), eq(session))).thenReturn(query);
		when(query.getResult()).thenReturn(result);
		when(genericPageUse.getRequest()).thenReturn(request);
		when(request.getPathInfo()).thenReturn(requestPath);
		when(genericPageUse.getCurrentPage()).thenReturn(page);
		when(page.getTitle()).thenReturn("home");
		when(genericPageUse.getResource()).thenReturn(resource);
		when(resource.adaptTo(ValueMap.class)).thenReturn(valueMap2);
		when(valueMap2.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS, new String[]{})).thenReturn(array);
		when(page.hasContent()).thenReturn(true);
		when(page.getContentResource()).thenReturn(pageJCRResource);
		when(pageJCRResource.getResourceType()).thenReturn(HertzConstants.SPLASH_RESOURCE_TYPE);
		when(resource.getPath()).thenReturn("/account/rentals");
		when(pageJCRResource.getValueMap()).thenReturn(hertzLinksProps);
		when(hertzLinksProps.containsKey(HertzConstants.LABEL)).thenReturn(true);
		when(hertzLinksProps.containsKey(HertzConstants.VALUE)).thenReturn(true);
		when(hertzLinksProps.get(HertzConstants.LABEL)).thenReturn("label");
		when(hertzLinksProps.get(HertzConstants.VALUE)).thenReturn("value");
		when(genericPageUse.getCurrentPage()).thenReturn(iracPage);
		when(iracPage.getPath()).thenReturn(pagePath);
		
		when(genericPageUse.getResource()).thenReturn(resource);
		when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver);
		//when(genericPageUse.getResponse()).thenReturn(response);
		when(genericPageUse.getSlingScriptHelper()).thenReturn(slingScriptHelper);
		when(resource.getResourceType()).thenReturn(HertzConstants.HEADER_RES_TYPE);
		when(resource.getValueMap()).thenReturn(hitResourceValueMap);
		when(hitResourceValueMap.containsKey(HertzConstants.HEADER_REFERENCE)).thenReturn(false);
		when(resource.getParent()).thenReturn(headerParentResource);
		when(headerParentResource.getPath()).thenReturn(path);
		/*when(hitResourceValueMap.get(HertzConstants.HEADER_REFERENCE)).thenReturn(headerReference);
		when(resourceResolver.getResource(headerReference)).thenReturn(childHeaderResource);
		when(childHeaderResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(childHeaderContentResource);*/
		when(childHeaderContentResource.getChild("header")).thenReturn(headerResource);
		when(resourceResolver.getResource(Mockito.anyString())).thenReturn(requestPathResource);
		//when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver1);
		//when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		//when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver2);
		//when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		
		when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver);
		when(genericPageUse.getResponse()).thenReturn(response);
		when(genericPageUse.getSlingScriptHelper()).thenReturn(slingScriptHelper);
		//when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver1);
		when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		//when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver2);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(slingScriptHelper.getService(HertzConfigFactory.class)).thenReturn(configFactory);
		when(configFactory.getPropertyValue(HertzConstants.Hertz_SPA_ALLOWED_COMPONENTS)).thenReturn(null);
		when(queryBuilder.createQuery(any(PredicateGroup.class), eq(session))).thenReturn(query);
		when(query.getResult()).thenReturn(result);
		mockList.add(hit);
		genericPageUse.activate();
	}
	
	@Test
	public void testActivate4() throws RepositoryException, JSONException, ServletException, IOException{
		mockList=new ArrayList<Hit>();
		String [] array=new String[]{"{'configurableSeoKey':'title','configurableSeoValue':'valie'}"};
		when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		String[] selectors = {"spa", "a"};
		when(requestPathInfo.getSelectors()).thenReturn(selectors);
		ValueMap valueMap = Mockito.mock(ValueMap.class);
		when(genericPageUse.getPageProperties()).thenReturn(valueMap);
		String basePath= "/content/hertz-rac/rec-web/us/en/reservations";
		when(valueMap.get(PARTNER_BASE_PAGE_PATH)).thenReturn(basePath);
		when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver);
		when(resourceResolver.getResource(basePath)).thenReturn(partnerHomePageResource);
		when(partnerHomePageResource.getChild(Mockito.anyString())).thenReturn(cpCodeResource);
		when(resourceResolver.getResource(basePath + HertzConstants.SLASH + "a")).thenReturn(requestPathResource);		
		when(resourceResolver.getResource(basePath + HertzConstants.SLASH + "a" + HertzConstants.SLASH
				+ HertzConstants.JCR_CONTENT)).thenReturn(jcrResource);		
		when(requestPathResource.adaptTo(Page.class)).thenReturn(requestPathPage);
		when(jcrResource.adaptTo(ValueMap.class)).thenReturn(valueMap2);
		when(jcrResource.getPath()).thenReturn( HertzConstants.SLASH + "a" + HertzConstants.SLASH
				+ HertzConstants.JCR_CONTENT);
		when(valueMap2.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS, new String[]{})).thenReturn(array);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		when(requestPathPage.getPath()).thenReturn(basePath + HertzConstants.SLASH + "a" + HertzConstants.SLASH
				+ HertzConstants.JCR_CONTENT);
		when(queryBuilder.createQuery(any(PredicateGroup.class), eq(session))).thenReturn(query);
		when(query.getResult()).thenReturn(result);
		when(genericPageUse.getRequest()).thenReturn(request);
		when(request.getPathInfo()).thenReturn(requestPath);
		when(genericPageUse.getCurrentPage()).thenReturn(page);
		when(page.getTitle()).thenReturn("home");
		when(genericPageUse.getResource()).thenReturn(resource);
		when(resource.adaptTo(ValueMap.class)).thenReturn(valueMap2);
		when(valueMap2.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS, new String[]{})).thenReturn(array);
		when(page.hasContent()).thenReturn(true);
		when(page.getContentResource()).thenReturn(pageJCRResource);
		when(pageJCRResource.getResourceType()).thenReturn(HertzConstants.SPLASH_RESOURCE_TYPE);
		when(resource.getPath()).thenReturn("/account/rentals");
		when(pageJCRResource.getValueMap()).thenReturn(hertzLinksProps);
		when(hertzLinksProps.containsKey(HertzConstants.LABEL)).thenReturn(true);
		when(hertzLinksProps.containsKey(HertzConstants.VALUE)).thenReturn(true);
		when(hertzLinksProps.get(HertzConstants.LABEL)).thenReturn("label");
		when(hertzLinksProps.get(HertzConstants.VALUE)).thenReturn("value");
		when(page.getPath()).thenReturn(pagePath);
		
		when(genericPageUse.getResource()).thenReturn(resource);
		when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver);
		//when(genericPageUse.getResponse()).thenReturn(response);
		when(genericPageUse.getSlingScriptHelper()).thenReturn(slingScriptHelper);
		when(resource.getResourceType()).thenReturn(HertzConstants.HEADER_RES_TYPE);
		when(resource.getValueMap()).thenReturn(hitResourceValueMap);
		when(hitResourceValueMap.containsKey(HertzConstants.HEADER_REFERENCE)).thenReturn(false);
		when(resource.getParent()).thenReturn(headerParentResource);
		when(headerParentResource.getPath()).thenReturn(path);
		/*when(hitResourceValueMap.get(HertzConstants.HEADER_REFERENCE)).thenReturn(headerReference);
		when(resourceResolver.getResource(headerReference)).thenReturn(childHeaderResource);
		when(childHeaderResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(childHeaderContentResource);*/
		when(childHeaderContentResource.getChild("header")).thenReturn(headerResource);
		when(resourceResolver.getResource(Mockito.anyString())).thenReturn(requestPathResource);
		//when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver1);
		//when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		//when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver2);
		//when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		
		when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver);
		when(genericPageUse.getResponse()).thenReturn(response);
		when(genericPageUse.getSlingScriptHelper()).thenReturn(slingScriptHelper);
		//when(pagePath.startsWith(HertzConstants.CONTENT)).thenReturn(true);
		when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(slingScriptHelper.getService(HertzConfigFactory.class)).thenReturn(configFactory);
		when(queryBuilder.createQuery(any(PredicateGroup.class), eq(session))).thenReturn(query);
		when(query.getResult()).thenReturn(result);
		mockList.add(hit);
		
		genericPageUse.activate();
	}
	
	@Test
	public void testActivate5() throws RepositoryException, JSONException, ServletException, IOException{
		mockList=new ArrayList<Hit>();
		String [] array=new String[]{"{'configurableSeoKey':'title','configurableSeoValue':'valie'}"};
		String basePath= "/content/hertz-rac/rec-web/us/en/reservations";
		when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver);
		when(resourceResolver.getResource(basePath)).thenReturn(partnerHomePageResource);
		when(partnerHomePageResource.getChild(Mockito.anyString())).thenReturn(cpCodeResource);
		when(resourceResolver.getResource(basePath + HertzConstants.SLASH + "a")).thenReturn(requestPathResource);		
		when(resourceResolver.getResource(basePath + HertzConstants.SLASH + "a" + HertzConstants.SLASH
				+ HertzConstants.JCR_CONTENT)).thenReturn(jcrResource);		
		when(requestPathResource.adaptTo(Page.class)).thenReturn(requestPathPage);
		when(jcrResource.adaptTo(ValueMap.class)).thenReturn(valueMap2);
		when(jcrResource.getPath()).thenReturn( HertzConstants.SLASH + "a" + HertzConstants.SLASH
				+ HertzConstants.JCR_CONTENT);
		when(valueMap2.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS, new String[]{})).thenReturn(array);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		when(requestPathPage.getPath()).thenReturn(basePath + HertzConstants.SLASH + "a" + HertzConstants.SLASH
				+ HertzConstants.JCR_CONTENT);
		when(queryBuilder.createQuery(any(PredicateGroup.class), eq(session))).thenReturn(query);
		when(query.getResult()).thenReturn(result);
		when(genericPageUse.getRequest()).thenReturn(request);
		when(request.getPathInfo()).thenReturn(requestPath);
		when(genericPageUse.getRequest()).thenReturn(request);
		when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		when(genericPageUse.getCurrentPage()).thenReturn(page);
		when(page.getTitle()).thenReturn("home");
		when(genericPageUse.getResource()).thenReturn(resource);
		when(resource.adaptTo(ValueMap.class)).thenReturn(valueMap2);
		when(valueMap2.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS, new String[]{})).thenReturn(array);
		when(page.hasContent()).thenReturn(true);
		when(page.getContentResource()).thenReturn(pageJCRResource);
		when(pageJCRResource.getResourceType()).thenReturn(HertzConstants.RAPID_RESOURCE_TYPE);
		when(resource.getPath()).thenReturn("/account/rentals");
		when(page.getPath()).thenReturn(pagePath);
		
		when(genericPageUse.getResource()).thenReturn(resource);
		when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver);
		//when(genericPageUse.getResponse()).thenReturn(response);
		when(genericPageUse.getSlingScriptHelper()).thenReturn(slingScriptHelper);
		when(resource.getResourceType()).thenReturn(HertzConstants.HEADER_RES_TYPE);
		when(resource.getValueMap()).thenReturn(hitResourceValueMap);
		when(hitResourceValueMap.containsKey(HertzConstants.HEADER_REFERENCE)).thenReturn(false);
		when(resource.getParent()).thenReturn(headerParentResource);
		when(headerParentResource.getPath()).thenReturn(path);
		/*when(hitResourceValueMap.get(HertzConstants.HEADER_REFERENCE)).thenReturn(headerReference);
		when(resourceResolver.getResource(headerReference)).thenReturn(childHeaderResource);
		when(childHeaderResource.getChild(HertzConstants.JCR_CONTENT)).thenReturn(childHeaderContentResource);*/
		when(childHeaderContentResource.getChild("header")).thenReturn(headerResource);
		when(resourceResolver.getResource(Mockito.anyString())).thenReturn(requestPathResource);
		//when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver1);
		//when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		//when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver2);
		//when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		
		when(genericPageUse.getResourceResolver()).thenReturn(resourceResolver);
		when(genericPageUse.getResponse()).thenReturn(response);
		when(genericPageUse.getSlingScriptHelper()).thenReturn(slingScriptHelper);
		//when(pagePath.startsWith(HertzConstants.CONTENT)).thenReturn(true);
		when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(slingScriptHelper.getService(HertzConfigFactory.class)).thenReturn(configFactory);
		when(queryBuilder.createQuery(any(PredicateGroup.class), eq(session))).thenReturn(query);
		when(query.getResult()).thenReturn(result);
		mockList.add(hit);
		genericPageUse.activate();
	}
}
