package com.hertz.digital.platform.utils;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.hertz.digital.platform.bean.LoginFlyOutItemsBean;
import com.hertz.digital.platform.bean.MenuItemBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.service.api.MenuBuilderService;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestHeaderUtils {

    @Mock
    Value value;

    @Mock
    Resource resource;

    @Mock
    ResourceResolverFactory resolverFactory;

    @Mock
    MenuBuilderService menuBuilderService;

    @Mock
    SlingHttpServletResponse response;

    @Mock
    SlingHttpServletRequest request;

    @Mock
    PrintWriter writer;

    @Mock
    QueryBuilder queryBuilder;

    @Mock
    RequestPathInfo pathInfo;

    @Mock
    List<MenuItemBean> list;

    @Mock
    ResourceResolver resolver;

    @Mock
    Session session;

    @Mock
    Query query;

    @Mock
    SearchResult result;

    @Mock
    Hit hit;

    @Mock
    Resource headerChildResource;

    @Mock
    Resource parterResource;

    @Mock
    Resource pageResource;

    @Mock
    Resource searchResource;

    @Mock
    Resource searchChildResource;

    @Mock
    Resource loginChildResource;

    @Mock
    Resource logoChildResource;

    @Mock
    Resource chatResource;

    @Mock
    Resource logoutResource;

    @Mock
    Resource countryLanguageChildResource;

    @Mock
    Resource loginPropertyResource;

    @Mock
    Resource mockChildResource;

    @Mock
    ValueMap valueMap;

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
    Node loginNode;

    @Mock
    Resource authenticatedFlyoutComponent;

    @Mock
    Node partnerNode;

    @Mock
    ValueMap flyoutValueMap;

    @Mock
    Property property;

    @Mock
    private Iterable<Resource> mockIterable;

    Logger log;

    Value[] values = new Value[2];

    List<Hit> mockList;

    private String loginItem = "{'flyoutItemTxt':'flyout1','flyoutItemPath':'dummyPath','openUrlNewWindow':true}";
    private String loginItem1 = "[{'flyoutItemTxt':'flyout1','flyoutItemPath':'dummyPath','openUrlNewWindow':true}]";
    private String loginItem2 = "[{'flyoutItemTxt':'flyout2','flyoutItemPath':'dummyPath','openUrlNewWindow':false}]";
    List<LoginFlyOutItemsBean> loginFlyoutItemsBeanList;
    String path = "dummyPath";
    String path1 = "/content/dummyPath";

    @Before
    public final void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LoggerFactory.class);
        log = Mockito.mock(Logger.class);
        Field field = HeaderUtils.class.getDeclaredField("LOGGER");
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, log);
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
    }

    @Test
    public final void testGetOutputJSON()
            throws NoSuchFieldException, RepositoryException, LoginException,
            JSONException, ServletException, IOException,
            org.apache.sling.api.resource.LoginException {
        mockList = new ArrayList<Hit>();
        String[] links = new String[] {
                "{'heading':'Account Summary','linkurl':'/content/hertz-rac/rac-web/en-us/account','openURLNewWindow':'No','rel':'No'}" };
        String[] links1 = new String[] {
                "{'heading':'Account Summary','linkurl':'/content/hertz-rac/rac-web/en-us/account','openURLNewWindow':'Yes','rel':'Yes'}" };
        when(resolver.getResource(Mockito.anyString()))
                .thenReturn(pageResource);
        when(pageResource.getName()).thenReturn("irac_main");
        when(menuBuilderService.getMenuJSON(pageResource)).thenReturn(list);
        //when(resolver.adaptTo(Session.class)).thenReturn(session);
        //when(queryBuilder.createQuery(any(PredicateGroup.class), eq(session)))
               // .thenReturn(query);
       // when(query.getResult()).thenReturn(result);
       // mockList.add(hit);
       // when(result.getHits()).thenReturn(mockList);
       // when(hit.getResource()).thenReturn(resource);
        when(pageResource.getChild("header")).thenReturn(headerChildResource);
        when(headerChildResource.getChild("headersearch"))
                .thenReturn(searchResource);
        when(searchResource.adaptTo(ValueMap.class)).thenReturn(valueMap1);
        when(valueMap1.containsKey(Mockito.anyString())).thenReturn(true);
        when(valueMap1.get("searchPath", String.class))
                .thenReturn("searchPath");
        when(valueMap1.get("searchIconAltTxt", String.class))
                .thenReturn("searchIconAltTxt");
        when(valueMap1.get("searchPlaTxt", String.class))
                .thenReturn("searchPlaTxt");

        when(headerChildResource.getChild("logo"))
                .thenReturn(logoChildResource);
        when(logoChildResource.adaptTo(ValueMap.class)).thenReturn(valueMap2);
        when(valueMap2.containsKey(Mockito.anyString())).thenReturn(true);
        when(valueMap2.get("fileReference", String.class))
                .thenReturn("fileReference");
        when(valueMap2.get("logoURL", String.class)).thenReturn("logoURL");
        when(valueMap2.get("logoImagealtTxt", String.class))
                .thenReturn("logoImagealtTxt");

        when(headerChildResource.getChild("countrylanguage"))
                .thenReturn(countryLanguageChildResource);
        when(countryLanguageChildResource.adaptTo(ValueMap.class))
                .thenReturn(valueMap4);
        when(valueMap4.containsKey(Mockito.anyString())).thenReturn(true);
        when(valueMap4.get("countryLabel", String.class)).thenReturn("Country");
        when(valueMap4.get("languageLabel", String.class))
                .thenReturn("Language");
        when(valueMap4.get("updateLabel", String.class)).thenReturn("Update");

        when(headerChildResource.getChild("login"))
                .thenReturn(loginChildResource);
        when(loginChildResource.adaptTo(Node.class)).thenReturn(loginNode);
        when(loginChildResource.adaptTo(ValueMap.class)).thenReturn(valueMap3);
        when(valueMap3.containsKey(Mockito.anyString())).thenReturn(true);
        when(valueMap3.get("loginBtnTxt", String.class))
                .thenReturn("loginBtnTxt");
        when(valueMap3.get("loginPlaTxt", String.class))
                .thenReturn("loginPlaTxt");
        when(valueMap3.get("loginWelcomeTxt", String.class))
                .thenReturn("welcome");

        when(headerChildResource.getChild(HertzConstants.CHAT_NODE))
                .thenReturn(chatResource);
        when(chatResource.adaptTo(ValueMap.class)).thenReturn(chatProperties);
        when(chatProperties.containsKey(Mockito.anyString())).thenReturn(true);
        when(chatProperties.get(Mockito.anyString())).thenReturn("chat");

        when(headerChildResource.getChild(HertzConstants.LOGOUT))
                .thenReturn(logoutResource);
        when(logoutResource.adaptTo(ValueMap.class)).thenReturn(chatProperties);
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
        when(headerChildResource.getChild("authenticatedFlyoutComponent"))
                .thenReturn(authenticatedFlyoutComponent);
        when(authenticatedFlyoutComponent.adaptTo(ValueMap.class))
                .thenReturn(flyoutValueMap);
        when(flyoutValueMap.containsKey(Mockito.anyString())).thenReturn(true);
        when(flyoutValueMap.get("links", String[].class)).thenReturn(links);

        HeaderUtils.getOutputJSON(path, response, resolver, menuBuilderService);
        when(property.isMultiple()).thenReturn(true);
        when(property.getValues()).thenReturn(values);
        Value value1 = PowerMockito.mock(Value.class);
        Value value2 = PowerMockito.mock(Value.class);
        values[0] = value1;
        values[1] = value2;
        when(value1.getString()).thenReturn(loginItem1);
        when(value2.getString()).thenReturn(loginItem2);
        HeaderUtils.getOutputJSON(path, response, resolver, menuBuilderService);
        when(resolver.getResource(
                path1 + HertzConstants.SLASH + HertzConstants.JCR_CONTENT))
                        .thenReturn(parterResource);
        when(menuBuilderService.getMenuJSON(parterResource)).thenReturn(list);
        HeaderUtils.getOutputJSON(path1, response, resolver, menuBuilderService);
        when(flyoutValueMap.get("links", String[].class)).thenReturn(links1);
        HeaderUtils.getOutputJSON(path1, response, resolver, menuBuilderService);
    }

    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor<HeaderUtils> constructor = HeaderUtils.class
                .getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

}
