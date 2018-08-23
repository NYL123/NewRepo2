package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Session;
import javax.jcr.Value;

import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
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
import com.hertz.digital.platform.bean.FooterContainerBean;
import com.hertz.digital.platform.bean.HeaderBean;
import com.hertz.digital.platform.bean.MenuItemBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.constants.TestConstants;
import com.hertz.digital.platform.service.api.MenuBuilderService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestHomePageSpaUse {

    @InjectMocks
    private HomeSpaPageUse homePageSpaUse = new HomeSpaPageUse();

    @Mock
    ValueMap map;

    @Mock
    ResourceResolver resolver;

    @Mock
    QueryBuilder builder;

    @Mock
    SlingScriptHelper helper;

    @Mock
    ResourceResolverFactory factory;

    @Mock
    SlingHttpServletResponse response;

    @Mock
    MenuBuilderService menuService;

    @Mock
    Resource resource;

    @Mock
    Resource resource1;

    @Mock
    Resource headerResource;

    @Mock
    Resource heroResource;

    @Mock
    ValueMap heroMap;

    @Mock
    Resource pageResource;

    @Mock
    Property property;

    @Mock
    Node partnerNode;

    @Mock
    List<MenuItemBean> list;

    @Mock
    Session session;

    @Mock
    Query query;

    @Mock
    SearchResult result;

    @Mock
    Resource searchResource;

    @Mock
    Hit hit;

    List<Hit> mockList;

    @Mock
    Resource logoChildResource;

    @Mock
    Resource loginChildResource;

    @Mock
    ValueMap valueMap1;

    @Mock
    ValueMap valueMap2;

    @Mock
    ValueMap valueMap3;

    @Mock
    ValueMap valueMap4;

    @Mock
    ValueMap chatProperties;

    @Mock
    PrintWriter writer;

    @Mock
    Resource countryLanguageChildResource;

    @Mock
    Node loginNode;

    @Mock
    Resource chatResource;

    @Mock
    Value value;

    @Mock
    List<FooterContainerBean> footerList;

    @Mock
    List<HeaderBean> headerList;

    private String loginItem = "{'flyoutItemTxt':'flyout1','flyoutItemPath':'dummyPath','openUrlNewWindow':true}";

    @Before
    public final void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        homePageSpaUse = PowerMockito.mock(HomeSpaPageUse.class);
        mockList = new ArrayList<>();
        Mockito.doCallRealMethod().when(homePageSpaUse).activate();
    }

    @Test
    public final void testActivate() throws Exception {
        when(homePageSpaUse.getProperties()).thenReturn(map);
        when(map.get(HertzConstants.PARTNER_PAGE_PATH, String.class))
                .thenReturn(TestConstants.PARTNER_PAGE_PATH);
        when(homePageSpaUse.getResourceResolver()).thenReturn(resolver);
        when(resolver.adaptTo(QueryBuilder.class)).thenReturn(builder);
        when(homePageSpaUse.getSlingScriptHelper()).thenReturn(helper);
        when(helper.getService(ResourceResolverFactory.class))
                .thenReturn(factory);
        when(homePageSpaUse.getResponse()).thenReturn(response);
        when(helper.getService(MenuBuilderService.class))
                .thenReturn(menuService);
        when(homePageSpaUse.getResource()).thenReturn(resource);
        when(resource.getChild(HertzConstants.HERO)).thenReturn(heroResource);
        when(heroResource.getValueMap()).thenReturn(heroMap);
        when(heroMap.get(HertzConstants.TAGLINE_TEXT))
                .thenReturn("tagLineText");
        when(heroMap.get(HertzConstants.BACKGROUND_IMAGE))
                .thenReturn(TestConstants.CONTENT_DATA_PATH);
        when(heroMap.get(HertzConstants.ALT_TEXT)).thenReturn("altText");
        when(resolver.getResource(Mockito.anyString()))
                .thenReturn(pageResource);
        when(pageResource.getName()).thenReturn("irac_main");
        when(menuService.getMenuJSON(pageResource)).thenReturn(list);
        when(resolver.adaptTo(Session.class)).thenReturn(session);
        when(builder.createQuery(any(PredicateGroup.class), eq(session)))
                .thenReturn(query);
        when(query.getResult()).thenReturn(result);
        mockList.add(hit);
        when(result.getHits()).thenReturn(mockList);
        when(hit.getResource()).thenReturn(resource1);
        when(resource1.getChild("header")).thenReturn(headerResource);
        when(headerResource.getChild("headersearch"))
                .thenReturn(searchResource);
        when(searchResource.adaptTo(ValueMap.class)).thenReturn(valueMap1);
        when(valueMap1.containsKey(Mockito.anyString())).thenReturn(true);
        when(valueMap1.get("searchPath", String.class))
                .thenReturn("searchPath");
        when(valueMap1.get("searchIconAltTxt", String.class))
                .thenReturn("searchIconAltTxt");
        when(valueMap1.get("searchPlaTxt", String.class))
                .thenReturn("searchPlaTxt");

        when(headerResource.getChild("logo")).thenReturn(logoChildResource);
        when(logoChildResource.adaptTo(ValueMap.class)).thenReturn(valueMap2);
        when(valueMap2.containsKey(Mockito.anyString())).thenReturn(true);
        when(valueMap2.get("fileReference", String.class))
                .thenReturn("fileReference");
        when(valueMap2.get("logoURL", String.class)).thenReturn("logoURL");
        when(valueMap2.get("logoImagealtTxt", String.class))
                .thenReturn("logoImagealtTxt");

        when(headerResource.getChild("countrylanguage"))
                .thenReturn(countryLanguageChildResource);
        when(countryLanguageChildResource.adaptTo(ValueMap.class))
                .thenReturn(valueMap4);
        when(valueMap4.containsKey(Mockito.anyString())).thenReturn(true);
        when(valueMap4.get("countryLabel", String.class)).thenReturn("Country");
        when(valueMap4.get("languageLabel", String.class))
                .thenReturn("Language");
        when(valueMap4.get("updateLabel", String.class)).thenReturn("Update");

        when(headerResource.getChild("login")).thenReturn(loginChildResource);
        when(loginChildResource.adaptTo(Node.class)).thenReturn(loginNode);
        when(loginChildResource.adaptTo(ValueMap.class)).thenReturn(valueMap3);
        when(valueMap3.containsKey(Mockito.anyString())).thenReturn(true);
        when(valueMap3.get("loginBtnTxt", String.class))
                .thenReturn("loginBtnTxt");
        when(valueMap3.get("loginPlaTxt", String.class))
                .thenReturn("loginPlaTxt");

        when(headerResource.getChild(HertzConstants.CHAT_NODE))
                .thenReturn(chatResource);
        when(chatResource.adaptTo(ValueMap.class)).thenReturn(chatProperties);
        when(chatProperties.containsKey(Mockito.anyString())).thenReturn(true);
        when(chatProperties.get(Mockito.anyString())).thenReturn("chat");
        when(loginNode.hasProperty(HertzConstants.LOGIN_ITEMS))
                .thenReturn(true);
        when(loginNode.getProperty(HertzConstants.LOGIN_ITEMS))
                .thenReturn(property);
        when(property.isMultiple()).thenReturn(false);
        when(property.getValue()).thenReturn(value);
        when(value.getString()).thenReturn(loginItem);
        when(resolver.isLive()).thenReturn(true);
        when(session.isLive()).thenReturn(true);
        when(response.getWriter()).thenReturn(writer);
        homePageSpaUse.activate();
        when(resource.getChild(HertzConstants.HERO)).thenReturn(null);
        homePageSpaUse.activate();
    }

    @Test
    public final void testGetterSetters() {
        Mockito.doCallRealMethod().when(homePageSpaUse).getFooterList();
        Mockito.doCallRealMethod().when(homePageSpaUse).getTagLineText();
        Mockito.doCallRealMethod().when(homePageSpaUse).getHeroImagePath();
        Mockito.doCallRealMethod().when(homePageSpaUse).getHeroImageAltText();
        Mockito.doCallRealMethod().when(homePageSpaUse)
                .getPickUpLocationCancel();
        Mockito.doCallRealMethod().when(homePageSpaUse)
                .getPickUpLocationPlaceholder();
        Mockito.doCallRealMethod().when(homePageSpaUse).getHeaderList();
        Mockito.doCallRealMethod().when(homePageSpaUse)
                .setFooterList(footerList);
        Mockito.doCallRealMethod().when(homePageSpaUse)
                .setHeaderList(headerList);
        Mockito.doCallRealMethod().when(homePageSpaUse)
                .setHeroImageAltText(Mockito.anyString());
        Mockito.doCallRealMethod().when(homePageSpaUse)
                .setHeroImagePath(Mockito.anyString());
        Mockito.doCallRealMethod().when(homePageSpaUse)
                .setPickUpLocationCancel(Mockito.anyString());
        Mockito.doCallRealMethod().when(homePageSpaUse)
                .setPickUpLocationPlaceholder(Mockito.anyString());
        Mockito.doCallRealMethod().when(homePageSpaUse)
                .setTagLineText(Mockito.anyString());
        homePageSpaUse.setFooterList(footerList);
        homePageSpaUse.setHeaderList(headerList);
        homePageSpaUse.setHeroImageAltText("altText");
        homePageSpaUse.setHeroImagePath("heroImagePath");
        homePageSpaUse.setPickUpLocationCancel("cancel");
        homePageSpaUse
                .setPickUpLocationPlaceholder("pickUpLocationPlaceholder");
        homePageSpaUse.setTagLineText("tagLineText");
        Assert.assertNotNull(homePageSpaUse.getFooterList());
        Assert.assertNotNull(homePageSpaUse.getHeaderList());
        Assert.assertTrue(
                homePageSpaUse.getHeroImageAltText().equals("altText"));
        Assert.assertTrue(
                homePageSpaUse.getHeroImagePath().equals("heroImagePath"));
        Assert.assertTrue(
                homePageSpaUse.getPickUpLocationCancel().equals("cancel"));
        Assert.assertTrue(homePageSpaUse.getPickUpLocationPlaceholder()
                .equals("pickUpLocationPlaceholder"));
        Assert.assertNotNull(
                homePageSpaUse.getTagLineText().equals("tagLineText"));

    }

}
