package com.hertz.digital.platform.use;

import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.engine.SlingRequestProcessor;
import org.apache.sling.settings.SlingSettingsService;
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
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.contentsync.handler.util.RequestResponseFactory;
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.constants.TestConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest({WCMUsePojo.class, LoggerFactory.class})
public class TestOfferDetailsUse {

	@InjectMocks
	private OfferDetailsUse offerDetailsUse;
	
	@Mock
	SlingHttpServletRequest request;
	
	@Mock
	Resource resource;
	
	@Mock
	Resource parResource;
	
	@Mock
	Page currentPage;
	
	@Mock
	ValueMap propMap;
	
	@Mock
	ValueMap childValueMap;
	
	@Mock
	SlingScriptHelper scriptHelper;
	
	@Mock
	RequestResponseFactory factory;
	
	@Mock
	SlingRequestProcessor reqProcessor;
	
	@Mock
	HertzConfigFactory configFactory;
	
	@Mock
	Iterable<Resource> iterable;
	
	@Mock
	Iterator<Resource> iterator;
	
	@Mock
	Resource mockChildResource;
	
	@Mock
	Resource marketingSlotOne;
	
	@Mock
	Resource  marketingSlotTwo;
	
	@Mock
	HttpServletRequest request1;
	
	@Mock
	Resource parentResource;
	
	@Mock
	HttpServletResponse response;
	
	@Mock
	RequestPathInfo requestPathInfo;
	
	@Mock
	SlingSettingsService slingService;
	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		offerDetailsUse =PowerMockito.mock(OfferDetailsUse.class);
		Mockito.doCallRealMethod().when(offerDetailsUse).activate();
		Mockito.doCallRealMethod().when(offerDetailsUse).setJsonString(Mockito.anyString());
		Mockito.doCallRealMethod().when(offerDetailsUse).getJsonString();
	}
	
	@Test
	public final void testActivate() throws RepositoryException, JSONException, ServletException, IOException{
		String [] allowedComponents=new String[]{"simplepair"};
		HashSet<String> set=new HashSet<String>();
		set.add("author");
		String [] seoArray=new String[]{"{'configurableSeoKey':'seoKeywords','configurableSeoValue':'SEO Keywords'}"};
		when(offerDetailsUse.getRequest()).thenReturn(request);
		when(request.getPathInfo()).thenReturn(TestConstants.OFFER_PATH);
		when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		when(requestPathInfo.getSelectors()).thenReturn(new String[]{"spa"});
		when(offerDetailsUse.getCurrentPage()).thenReturn(currentPage);
		when(currentPage.getTitle()).thenReturn("Offer Details Page");
		when(offerDetailsUse.getResource()).thenReturn(resource);
		when(resource.adaptTo(ValueMap.class)).thenReturn(propMap);
		when(resource.getParent()).thenReturn(parentResource);
		when(parentResource.getName()).thenReturn("partner");
		when(propMap.get(HertzConstants.CONFIGURABLE_MULTI_SEO_ITEMS, new String[]{})).thenReturn(seoArray);
		when(resource.getResourceType()).thenReturn(HertzConstants.HOME_PAGE_RES_TYPE);
		when(propMap.get(JcrConstants.JCR_TITLE,String.class)).thenReturn("Offer Details Page");
		when(propMap.get(JcrConstants.JCR_DESCRIPTION,String.class)).thenReturn("description");
		when(offerDetailsUse.getSlingScriptHelper()).thenReturn(scriptHelper);
		when(resource.getChild(HertzConstants.CONFIGTEXT_PARSYS)).thenReturn(parResource);
		when(scriptHelper.getService(RequestResponseFactory.class)).thenReturn(factory);
		when(scriptHelper.getService(SlingRequestProcessor.class)).thenReturn(reqProcessor);
		when(scriptHelper.getService(HertzConfigFactory.class)).thenReturn(configFactory);
		when(configFactory.getPropertyValue(HertzConstants.Hertz_SPA_ALLOWED_COMPONENTS)).thenReturn(allowedComponents);
		when(parResource.hasChildren()).thenReturn(true);
		when(parResource.getChildren()).thenReturn(iterable);
		when(iterable.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true,false);
		when(iterator.next()).thenReturn(mockChildResource);
		when(mockChildResource.getResourceType()).thenReturn("hertz/components/content/simplepair");
		when(mockChildResource.getValueMap()).thenReturn(childValueMap);
		when(childValueMap.get("key", String.class)).thenReturn("key");
		when(childValueMap.get("value", String.class)).thenReturn("value");
		when(resource.getChild(HertzConstants.MARKETING_SLOT_1)).thenReturn(marketingSlotOne);
		when(marketingSlotOne.getPath()).thenReturn(TestConstants.MARKETING_SLOT_1_PATH);
		when(factory.createRequest("GET", TestConstants.MARKETING_SLOT_1_PATH+".html")).thenReturn(request1);
		when(factory.createResponse(Mockito.any())).thenReturn(response);
		when(resource.getChild(HertzConstants.MARKETING_SLOT_2)).thenReturn(marketingSlotTwo);
		when(marketingSlotTwo.getPath()).thenReturn(TestConstants.MARKETING_SLOT_2_PATH);
		when(factory.createRequest("GET", TestConstants.MARKETING_SLOT_2_PATH+".html")).thenReturn(request1);
		when(resource.getValueMap()).thenReturn(propMap);
		when(propMap.get(JcrConstants.JCR_TITLE)).thenReturn("Offer Details Page");
        when(resource.getPath()).thenReturn("/account/rentals");
        when(scriptHelper.getService(SlingSettingsService.class)).thenReturn(slingService);
        when(slingService.getRunModes()).thenReturn(set);
		offerDetailsUse.activate();
		when(requestPathInfo.getSelectors()).thenReturn(new String[]{"native"});
		offerDetailsUse.activate();
	}
	
	@Test
	public final void testGetterSetters(){
		String jsonString="{'spacontent':{'title':'offer1','metaData':{'seoOfferTitle ':'SEO Offer Title ','seoFacebookOfferDescription':'SEO/Facebook Offer Description','og:type':'website','seoDescription':'SEO Description','seoKeywords':'SEO Keywords','title':'offer1'},'components':[{'marketingslot1':'<div class=\'aem-Grid aem-Grid--12 aem-Grid--default--12  \'> \n <div class=\'richtext aem-GridColumn aem-GridColumn--default--12\'> \n  <div class=\'rental-rewards-faq-body-content\'> \n   <div class=\'rental-rewards-faq-body-intro-msg\'> \n    <p><b>Lorem Ipsum&nbsp;is simply dummy text</b> of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p> \n   </div> \n  </div>\n </div> \n <div class=\'new section aem-Grid-newComponent\'> \n </div> \n</div>','marketingslot2':'<div class=\'aem-Grid aem-Grid--12 aem-Grid--default--12  \'> \n <div class=\'tablecontainer_b aem-GridColumn aem-GridColumn--default--12\'> \n  <div class=\'table-pattern-b-web table-pattern-b-web-section\'> \n   <div class=\'tablerowtxtB\'> \n   </div>\n   <table> \n    <tbody>\n     <tr> \n      <th> <span>Column-1</span> </th> \n      <th> <span>Column-2</span> </th> \n      <th> <span>Column-3</span> </th> \n</tr> \n     <tr> \n      <td> Hertz</td> \n      <td>Hertz</td> \n      <td> Hertz</td> \n     </tr>  \n    </tbody>\n   </table> \n  </div> \n </div> \n <div class=\'new section aem-Grid-newComponent\'> \n </div> \n</div>'}],'configuredProps':{'offerHeroImage':{'sources':[{'size':'small','renditions':[{'density':'1x','src':'/content/dam/hertz-rac/locations/SwissAlps-crop-660x420.jpg'},{'density':'2x','src':'/content/dam/hertz-rac/locations/SwissAlps-crop-660x420.jpg'}]},{'size':'large','renditions':[{'density':'1x','src':'/content/dam/hertz-rac/locations/SwissAlps-crop-660x420.jpg'},{'density':'2x','src':'/content/dam/hertz-rac/locations/SwissAlps-crop-660x420.jpg'}]}],'title':'Alt'},'titleTextField':{'label':'Go Ahead, Extend Your Weekend','ariaLabel':'Go Ahead, Extend Your Weekend','defaultValue':'Go Ahead, Extend Your Weekend'},'subtitleText':{'label':'Start your reservation and save 15%','ariaLabel':'Start your reservation and save 15%','defaultValue':'Start your reservation and save 15%'},'enterZipCodeCdpTextField':{'label':'Enter your zip code or CDP in order to apply offer','ariaLabel':'Enter your zip code or CDP in order to apply offer','defaultValue':'Enter your zip code or CDP in order to apply offer'},'zipCodePlaceholderTextField':{'label':'Zip Code','ariaLabel':'Zip Code','defaultValue':'Zip Code'},'cdpPlaceholderTextField':{'label':'CDP','ariaLabel':'CDP','defaultValue':'CDP'},'applyOfferCodeLabelField':{'label':'Apply Offer Code','ariaLabel':'Apply Offer Code','defaultValue':'Apply Offer Code'},'partnerProgramTextField':{'label':'Select your partner program in order to apply offer','ariaLabel':'Select your partner program in order to apply offer','defaultValue':'Select your partner program in order to apply offer'},'discountProgramLabelField':{'label':'Discount Program','ariaLabel':'Discount Program','defaultValue':'Discount Program'},'startYourReservationTextField':{'label':'Start Your Reservation','ariaLabel':'Start Your Reservation','defaultValue':'Start Your Reservation'},'pickupLocationPlaceholderTextField':{'label':'Pick-up Location','ariaLabel':'Pick-up Location','defaultValue':'Pick-up Location'},'dropOffLocationPlaceholderTextField':{'label':'Drop-off Location','ariaLabel':'Drop-off Location','defaultValue':'Drop-off Location'},'pickUpDateDropdownFieldTextField':{'label':'Pick-up Date','ariaLabel':'Pick-up Date','defaultValue':'Pick-up Date'},'pickUpTimeDropdownFieldTextField':{'label':'Pick-up Time','ariaLabel':'Pick-up Time','defaultValue':'Pick-up Time'},'dropOffDateDropdownFieldTextField':{'label':'Drop-Off','ariaLabel':'Drop-Off','defaultValue':'Drop-Off'},'dropOffTimeDropdownFieldTextField':{'label':'Drop-Off Time','ariaLabel':'Drop-Off Time','defaultValue':'Drop-Off Time'},'discountCodePlaceholderTextField':{'label':'Discount Code','ariaLabel':'Discount Code','defaultValue':'Discount Code','error':'Discount Code Error'},'reservationDiscountCodesModalField':{'label':'Discount Code Types','ariaLabel':'Discount Code Types'},'reservationCorporateDiscountCodeField':{'label':' separately imposing an Energy Surcharge. This change is not intended to reflect a precise measure of Hertz's energy costs incurred to serve a particular customer.','reservationPromotionalCouponField':{'label':'Promotional Coupon','ariaLabel':'Promotional Coupon'},'reservationPromotionalCouponFieldDescriptionField':'Separate and apart from the costs of fueling rental vehicles, energy costs represent a substantial portion of Hertz's operating expenses. To offset the significant costs of utility charges, bus fuel, oil and grease, and related costs, Hertz is separately imposing an Energy Surcharge. This change is not intended to reflect a precise measure of Hertz's energy costs incurred to serve a particular customer.','reservationRateCodeField':{'label':'Rate Code','ariaLabel':'Rate Code'},'reservationRateCodeFieldDescriptionField':'Separate and apart from the costs of fueling rental vehicles, energy costs represent a substantial portion of Hertz's operating expenses. To offset the significant costs of utility charges, bus fuel, oil and grease, and related costs, Hertz is separately imposing an Energy Surcharge. This change is not intended to reflect a precise measure of Hertz's energy costs incurred to serve a particular customer.','reservationConventionNumberField':{'label':'Convention Number','ariaLabel':'Convention Number'},'reservationConventionNumberFieldDescriptionField':' \'Separate and apart from the costs of fueling rental vehicles, energy costs represent a substantial portion of Hertz's operating expenses. To offset the significant costs of utility charges, bus fuel, oil and grease, and related costs, Hertz is separately imposing an Energy Surcharge. This change is not intended to reflect a precise measure of Hertz's energy costs incurred to serve a particular customer.','reservationVoucherNumberField':{'label':'Voucher Number','ariaLabel':'Voucher Number'},'reservationVoucherNumberFieldDescriptionField':'Separate and apart from the costs of fueling rental vehicles, energy costs represent a substantial portion of Hertz's operating expenses. To offset the significant costs of utility charges, bus fuel, oil and grease, and related costs, Hertz is separately imposing an Energy Surcharge. This change is not intended to reflect a precise measure of Hertz's energy costs incurred to serve a particular customer.','reservationAddDiscountCodeModalCancelCTAField':{'label':'Cancel','ariaLabel':'Cancel'},'reservationAddDiscountCodeModalApplyCTAField':{'label':'Apply','ariaLabel':'Apply'},'reservationCDPCodeSelectViewCDPCodeField':{'label':'Corporate Discount Code','ariaLabel':'Corporate Discount Code','error':'CDP Restricted Error'},'reservationCDPCodeSelectViewTravelTypeField':{'label':'What type of travel is this','ariaLabel':'What type of travel is this'},'reservationCDPCodeSelectViewTravelType1TextField':{'label':'Business','ariaLabel':'Business'},'reservationCDPCodeSelectViewTravelType2TextField':{'label':'Leisure','ariaLabel':'Leisure'},'reservationCDPCodeSelectViewTravelInformationField':{'label':'What type of travel is this Information text','ariaLabel':'What type of travel is this Information text'},'reservationCDPCodeSelectViewQuoteNegotiatedRateField':{'label':'Quote the Negotiated Rate','ariaLabel':'Quote the Negotiated Rate'},'reservationCDPCodeSelectViewContinueCTAField':{'label':'Continue','ariaLabel':'Continue'},'reservationCDPCodeSelectViewSavedCodesField':{'label':'Saved Codes','ariaLabel':'Saved Codes'},'reservationDiscountCodeField':{'label':'Discount Code','ariaLabel':'Discount Code','defaultValue':'Optional','error':'Discount Code error'},'reservationAgeSelectorHeaderField':{'label':'Select Your Age Range','ariaLabel':'Select Your Age Range'},'reservationAgeSelectorAgeButtonsLabels':{'label':'Age Range','ariaLabel':'Age Range','optionsList':[{'optionValue':'ageRange1','optionDisplayText':'18-19'},{'optionValue':'ageRange2','optionDisplayText':'20-22'},{'optionValue':'ageRange3','optionDisplayText':'23-24'},{'optionValue':'ageRange4','optionDisplayText':'25+'}]},'reservationAgeSelectorContinueCTAField':{'label':'Continue','ariaLabel':'Continue'},'reservationAgeSelectorFeesMayApplyHeaderField':{'label':'Fees May Apply','ariaLabel':'Fees May Apply'},'reservationAgeSelectorFeesMayApplyLinkField':{'href':'Fees May Apply','content':'/content/hertz-rac/rac-web/en-us/reservations'},'reservationAgeSelectorFeesMayApplyBodyField':'Separate and apart from the costs of fueling rental vehicles, energy costs represent a substantial portion of Hertz's operating expenses. To offset the significant costs of utility charges, bus fuel, oil and grease, and related costs, Hertz is separately imposing an Energy Surcharge. This change is not intended to reflect a precise measure of Hertz's energy costs incurred to serve a particular customer.','reservationAgeSelectorAgePolicyHeaderField':{'label':'Age Policy','ariaLabel':'Age Policy'},'reservationAgeSelectorAgePolicyModalTextField':'Separate and apart from the costs of fueling rental vehicles, energy costs represent a substantial portion of Hertz's operating expenses. To offset the significant costs of utility charges, bus fuel, oil and grease, and related costs, Hertz is separately imposing an Energy Surcharge. This change is not intended to reflect a precise measure of Hertz's energy costs incurred to serve a particular customer.','logInForRewardsLabelField':{'label':'Log In for Rewards','ariaLabel':'Log In for Rewards','defaultValue':'Log In for Rewards'},'continueLabelField':{'label':'Continue','ariaLabel':'Continue','defaultValue':'Continue'},'offers':[{'name':'offer1','attributes':{'pcCode':'PC Code','image':{'sources':[{'size':'small','renditions':[{'density':'1x','src':'/content/dam/hertz-rac/dam-workflow-test-folder/Canadav1.jpg'},{'density':'2x','src':'/content/dam/hertz-rac/dam-workflow-test-folder/Canadav1.jpg'}]},{'size':'large','renditions':[{'density':'1x','src':'/content/dam/hertz-rac/dam-workflow-test-folder/Canadav1.jpg'},{'density':'2x','src':'/content/dam/hertz-rac/dam-workflow-test-folder/Canadav1.jpg'}]}],'title':''},'badge':{'sources':[{'size':'small','renditions':[{'density':'1x','src':'/content/dam/hertz-rac/creditcard/img_americanexpress_cc_web@2x.png'},{'density':'2x','src':'/content/dam/hertz-rac/creditcard/img_americanexpress_cc_web@2x.png'}]},{'size':'large','renditions':[{'density':'1x','src':'/content/dam/hertz-rac/creditcard/img_americanexpress_cc_web@2x.png'},{'density':'2x','src':'/content/dam/hertz-rac/creditcard/img_americanexpress_cc_web@2x.png'}]}],'title':''},'partnerInfo':[{'partnerName':'Partner-1','cdpCode':'CDP Code-1'},{'partnerName':'Partner-2','cdpCode':'Cdp Code-2'}],'widgetToDisplay':'Partner Program Lookup','subhead':'subhead','logo':{'sources':[{'size':'small','renditions':[{'density':'1x','src':'/content/dam/hertz-rac/vehicles/ZEUSCCAR999.jpg'},{'density':'2x','src':'/content/dam/hertz-rac/vehicles/ZEUSCCAR999.jpg'}]},{'size':'large','renditions':[{'density':'1x','src':'/content/dam/hertz-rac/vehicles/ZEUSCCAR999.jpg'},{'density':'2x','src':'/content/dam/hertz-rac/vehicles/ZEUSCCAR999.jpg'}]}],'title':''},'cdpCode':'CDP Code','headline':'headline'},'metaData':{'cpCode':'CP Code','posCountries':'POS Countries','cdpRestriction':'CDP Restriction','channel':'web','contentExpiryDate':'10/07/2017','indexPageStartDate':'10/01/2017','indexPageExpiryDate':'10/07/2017'},'categories':[{'category-name':'/content/hertz-rac/rac-web/en-us/deals/partner'},{'category-name':'/content/hertz-rac/rac-web/en-us/deals/partner'}],'filter':[{'newBurstStartDate':'10/01/2017','newBurstEndDate':'10/07/2017','rank':1,'memberType':'non-member'}]}]}}}";
		offerDetailsUse.setJsonString(jsonString);
		Assert.assertTrue(offerDetailsUse.getJsonString().equalsIgnoreCase(jsonString));
		
	}
	
	@Test
	public void testActivate1() throws RepositoryException, JSONException, ServletException, IOException{
		Node node=PowerMockito.mock(Node.class);
		PropertyIterator iterator=PowerMockito.mock(PropertyIterator.class);
		Property property=PowerMockito.mock(Property.class);
		NodeIterator nIterator=PowerMockito.mock(NodeIterator.class);
		when(offerDetailsUse.getRequest()).thenReturn(request);
		when(request.getPathInfo()).thenReturn(TestConstants.OFFER_LANDING_PATH+"/jcr:content");
		when(offerDetailsUse.getResource()).thenReturn(resource);
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
		offerDetailsUse.activate();
	}
	
}
