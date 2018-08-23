package com.hertz.digital.platform.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.constants.TestConstants;
import com.hertz.digital.platform.service.api.SystemUserService;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestMenuBuilderServiceImpl {

    private String JCR_CONTENT = "jcr:content";
    private String MENU_METADATA = "menuMetadata";
    private String DISPLAY_NATIVE = "display-native";
    private String DISPLAY_MOBILE = "display-mobile";
    private String DISPLAY_TABLET = "display-tablet";
    private String DISPLAY_DESKTOP = "display-desktop";
    private static final String NAVIGATIONAL_URL = "navigationurl";
    private static final String MENU_TEMPLATE = "/apps/hertz/templates/menu";
    private static final String OPEN_URL_NEW_WINDOW = "openUrlNewWindow";

    @InjectMocks
    private MenuBuilderServiceImpl menuBuilderServiceImpl;

    @Mock
    Session session;

    @Mock
    ResourceResolver resolver;

    @Mock
    private SystemUserService systemService;

    @Mock
    Query query;

    @Mock
    Hit hit;

    @Mock
    SearchResult result;

    @Mock
    QueryBuilder queryBuilder;

    @Mock
    Resource pageResource;

    @Mock
    Resource parentresource;

    @Mock
    Page page;

    @Mock
    Node childjcrNode;

    @Mock
    Node childNode;

    List<Hit> mockList;

    Logger logger;

    @Before
    public final void setup() throws Exception {

        menuBuilderServiceImpl = new MenuBuilderServiceImpl();

        mockStatic(LoggerFactory.class);
        logger = Mockito.mock(Logger.class);
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(logger);
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public final void testgetMenuJSON() throws Exception {
        mockList = new ArrayList<Hit>();
        when(systemService.getServiceResourceResolver()).thenReturn(resolver);
        Resource topNavResource = mock(Resource.class);
        when(resolver.getResource(TestConstants.URL_PATH))
                .thenReturn(topNavResource);
        when(topNavResource.adaptTo(Page.class)).thenReturn(page);
        Iterator pageIterator = mock(Iterator.class);
        when(page.listChildren(Mockito.any(PageFilter.class)))
                .thenReturn(pageIterator);
        when(pageIterator.hasNext()).thenReturn(true, true, true, true, false);
        Page childPage = mock(Page.class);
        when(pageIterator.next()).thenReturn(childPage);

        when(childPage.getTitle()).thenReturn("title");
        when(childPage.adaptTo(Node.class)).thenReturn(childNode);
        when(childNode.getNode(JCR_CONTENT)).thenReturn(childjcrNode);
        when(childjcrNode.hasProperty(NAVIGATIONAL_URL)).thenReturn(true);
        javax.jcr.Property externalURLProperty = mock(Property.class);
        when(childjcrNode.getProperty(NAVIGATIONAL_URL))
                .thenReturn(externalURLProperty);
        when(childjcrNode.hasProperty(MENU_METADATA)).thenReturn(true);
        Property metadataProperty = mock(Property.class);
        when(childjcrNode.getProperty(MENU_METADATA))
                .thenReturn(metadataProperty);

        when(childjcrNode.hasProperty(OPEN_URL_NEW_WINDOW)).thenReturn(true);
        Property newWindowProperty = mock(Property.class);
        when(childjcrNode.getProperty(OPEN_URL_NEW_WINDOW))
                .thenReturn(newWindowProperty);

        when(childjcrNode.hasProperty(HertzConstants.SEO_NOFOLLOW))
                .thenReturn(true);
        Property noFollowProperty = mock(Property.class);
        when(childjcrNode.getProperty(HertzConstants.SEO_NOFOLLOW))
                .thenReturn(noFollowProperty);

        when(metadataProperty.isMultiple()).thenReturn(true);
        Value[] metadatavalues = new Value[4];
        Value value1 = mock(Value.class);
        Value value2 = mock(Value.class);
        Value value3 = mock(Value.class);
        Value value4 = mock(Value.class);
        metadatavalues[0] = value1;
        metadatavalues[1] = value2;
        metadatavalues[2] = value3;
        metadatavalues[3] = value4;
        when(value1.toString()).thenReturn(DISPLAY_NATIVE);
        when(value2.toString()).thenReturn(DISPLAY_MOBILE);
        when(value3.toString()).thenReturn(DISPLAY_TABLET);
        when(value4.toString()).thenReturn(DISPLAY_DESKTOP);
        when(metadataProperty.getValues()).thenReturn(metadatavalues);
        Iterator subpageIterator = mock(Iterator.class);
        when(childPage.listChildren(Mockito.any(PageFilter.class)))
                .thenReturn(subpageIterator);
        when(subpageIterator.hasNext()).thenReturn(true, false);
        Page subpage = mock(Page.class);
        when(subpage.getTitle()).thenReturn("Title");
        when(subpage.getPath()).thenReturn(TestConstants.PATH);
        when(subpageIterator.next()).thenReturn(subpage);
        Node subchildNode = mock(Node.class);
        when(subpage.adaptTo(Node.class)).thenReturn(subchildNode);
        Node subnode = mock(Node.class);
        when(subchildNode.getNode(JCR_CONTENT)).thenReturn(subnode);
        when(subnode.hasProperty(NAVIGATIONAL_URL)).thenReturn(true);
        Property externalURLProperty1 = mock(Property.class);
        when(subnode.getProperty(NAVIGATIONAL_URL))
                .thenReturn(externalURLProperty1);

        when(subnode.hasProperty(OPEN_URL_NEW_WINDOW)).thenReturn(true);
        Property newWindowProperty1 = mock(Property.class);
        when(subnode.getProperty(OPEN_URL_NEW_WINDOW))
                .thenReturn(newWindowProperty1);
        when(subnode.hasProperty(HertzConstants.SEO_NOFOLLOW)).thenReturn(true);
        Property noFollowProperty1 = mock(Property.class);
        when(subnode.getProperty(HertzConstants.SEO_NOFOLLOW))
                .thenReturn(noFollowProperty1);

        when(subnode.hasProperty(MENU_METADATA)).thenReturn(true);
        Property submetadataProperty = mock(Property.class);
        when(subnode.getProperty(MENU_METADATA))
                .thenReturn(submetadataProperty);
        when(submetadataProperty.isMultiple()).thenReturn(true);
        Value[] metadatavalues2 = new Value[4];
        Value mvalue = mock(Value.class);
        Value mvalue1 = mock(Value.class);
        Value mvalue2 = mock(Value.class);
        Value mvalue3 = mock(Value.class);
        when(mvalue.toString()).thenReturn(DISPLAY_NATIVE);
        when(mvalue1.toString()).thenReturn(DISPLAY_MOBILE);
        when(mvalue2.toString()).thenReturn(DISPLAY_TABLET);
        when(mvalue3.toString()).thenReturn(DISPLAY_DESKTOP);
        metadatavalues2[0] = mvalue;
        metadatavalues2[1] = mvalue1;
        metadatavalues2[2] = mvalue2;
        metadatavalues2[3] = mvalue3;
        when(submetadataProperty.getValues()).thenReturn(metadatavalues2);

        menuBuilderServiceImpl.getMenuJSON(topNavResource);
    }

    @Test
    public final void testDoGetMultipleFalse() throws Exception {
        mockList = new ArrayList<Hit>();
        when(systemService.getServiceResourceResolver()).thenReturn(resolver);
        Resource topNavResource = mock(Resource.class);
        when(resolver.getResource(TestConstants.URL_PATH))
                .thenReturn(topNavResource);
        when(topNavResource.adaptTo(Page.class)).thenReturn(page);
        Iterator pageIterator = mock(Iterator.class);
        when(page.listChildren(Mockito.any(PageFilter.class)))
                .thenReturn(pageIterator);
        when(pageIterator.hasNext()).thenReturn(true, true, true, true, false);
        Page childPage = mock(Page.class);
        when(pageIterator.next()).thenReturn(childPage);

        when(childPage.getTitle()).thenReturn("title");
        when(childPage.adaptTo(Node.class)).thenReturn(childNode);
        when(childNode.getNode(JCR_CONTENT)).thenReturn(childjcrNode);
        when(childjcrNode.hasProperty(NAVIGATIONAL_URL)).thenReturn(true);
        javax.jcr.Property externalURLProperty = mock(Property.class);
        when(childjcrNode.getProperty(NAVIGATIONAL_URL))
                .thenReturn(externalURLProperty);
        when(childjcrNode.hasProperty(MENU_METADATA)).thenReturn(true);
        Property metadataProperty = mock(Property.class);
        when(childjcrNode.getProperty(MENU_METADATA))
                .thenReturn(metadataProperty);
        when(metadataProperty.isMultiple()).thenReturn(false);
        // Value[] metadatavalues = new Value[4];
        Value metadataValue = mock(Value.class);
        when(metadataValue.toString()).thenReturn(DISPLAY_NATIVE);

        when(metadataValue.toString()).thenReturn(DISPLAY_MOBILE);
        when(metadataValue.toString()).thenReturn(DISPLAY_TABLET);
        when(metadataValue.toString()).thenReturn(DISPLAY_DESKTOP);

        when(metadataProperty.getValue()).thenReturn(metadataValue);
        Iterator subpageIterator = mock(Iterator.class);
        when(childPage.listChildren(Mockito.any(PageFilter.class)))
                .thenReturn(subpageIterator);
        when(subpageIterator.hasNext()).thenReturn(true, false);
        Page subpage = mock(Page.class);
        when(subpage.getTitle()).thenReturn("Title");
        when(subpage.getPath()).thenReturn(TestConstants.PATH);
        when(subpageIterator.next()).thenReturn(subpage);
        Node subchildNode = mock(Node.class);
        when(subpage.adaptTo(Node.class)).thenReturn(subchildNode);
        Node subnode = mock(Node.class);
        when(subchildNode.getNode(JCR_CONTENT)).thenReturn(subnode);
        when(subnode.hasProperty(NAVIGATIONAL_URL)).thenReturn(true);
        javax.jcr.Property externalURLProperty1 = mock(Property.class);
        when(subnode.getProperty(NAVIGATIONAL_URL))
                .thenReturn(externalURLProperty1);
        when(subnode.hasProperty(MENU_METADATA)).thenReturn(true);
        Property submetadataProperty = mock(Property.class);
        when(subnode.getProperty(MENU_METADATA))
                .thenReturn(submetadataProperty);
        when(submetadataProperty.isMultiple()).thenReturn(false);
        Value mvalue = mock(Value.class);

        when(mvalue.toString()).thenReturn(DISPLAY_NATIVE);

        when(submetadataProperty.getValue()).thenReturn(mvalue);

        menuBuilderServiceImpl.getMenuJSON(topNavResource);
    }

    @Test(expected = RepositoryException.class)
    public final void testDoGetRepoEx() throws Exception {
        mockList = new ArrayList<Hit>();
        when(systemService.getServiceResourceResolver()).thenReturn(resolver);
        Resource topNavResource = mock(Resource.class);
        when(resolver.getResource(TestConstants.URL_PATH))
                .thenReturn(topNavResource);
        when(topNavResource.adaptTo(Page.class)).thenReturn(page);
        Iterator pageIterator = mock(Iterator.class);
        when(page.listChildren(Mockito.any(PageFilter.class)))
                .thenReturn(pageIterator);
        when(pageIterator.hasNext()).thenReturn(true, true, true, true, false);
        Page childPage = mock(Page.class);
        when(pageIterator.next()).thenReturn(childPage);

        when(childPage.getTitle()).thenReturn("title");
        when(childPage.adaptTo(Node.class)).thenReturn(childNode);
        when(childNode.getNode(JCR_CONTENT))
                .thenThrow(RepositoryException.class);// Return(childjcrNode);
        menuBuilderServiceImpl.getMenuJSON(topNavResource);
    }

    @Test
    public final void testDoGetMultipleFalse1() throws Exception {
        mockList = new ArrayList<Hit>();
        when(systemService.getServiceResourceResolver()).thenReturn(resolver);
        Resource topNavResource = mock(Resource.class);
        when(resolver.getResource(TestConstants.URL_PATH))
                .thenReturn(topNavResource);
        when(topNavResource.adaptTo(Page.class)).thenReturn(page);
        Iterator pageIterator = mock(Iterator.class);
        when(page.listChildren(Mockito.any(PageFilter.class)))
                .thenReturn(pageIterator);
        when(pageIterator.hasNext()).thenReturn(true, true, true, true, false);
        Page childPage = mock(Page.class);
        when(pageIterator.next()).thenReturn(childPage);

        when(childPage.getTitle()).thenReturn("title");
        when(childPage.adaptTo(Node.class)).thenReturn(childNode);
        when(childNode.getNode(JCR_CONTENT)).thenReturn(childjcrNode);
        when(childjcrNode.hasProperty(NAVIGATIONAL_URL)).thenReturn(true);
        javax.jcr.Property externalURLProperty = mock(Property.class);
        when(childjcrNode.getProperty(NAVIGATIONAL_URL))
                .thenReturn(externalURLProperty);
        when(childjcrNode.hasProperty(MENU_METADATA)).thenReturn(true);
        Property metadataProperty = mock(Property.class);
        when(childjcrNode.getProperty(MENU_METADATA))
                .thenReturn(metadataProperty);
        when(metadataProperty.isMultiple()).thenReturn(false);
        // Value[] metadatavalues = new Value[4];
        Value metadataValue = mock(Value.class);
        // when(metadataValue.toString()).thenReturn(DISPLAY_NATIVE);
        when(metadataValue.toString()).thenReturn(DISPLAY_MOBILE);

        when(metadataValue.toString()).thenReturn(DISPLAY_TABLET);
        when(metadataValue.toString()).thenReturn(DISPLAY_DESKTOP);

        when(metadataProperty.getValue()).thenReturn(metadataValue);
        Iterator subpageIterator = mock(Iterator.class);
        when(childPage.listChildren(Mockito.any(PageFilter.class)))
                .thenReturn(subpageIterator);
        when(subpageIterator.hasNext()).thenReturn(true, false);
        Page subpage = mock(Page.class);
        when(subpage.getTitle()).thenReturn("Title");
        when(subpage.getPath()).thenReturn(TestConstants.PATH);
        when(subpageIterator.next()).thenReturn(subpage);
        Node subchildNode = mock(Node.class);
        when(subpage.adaptTo(Node.class)).thenReturn(subchildNode);
        Node subnode = mock(Node.class);
        when(subchildNode.getNode(JCR_CONTENT)).thenReturn(subnode);
        when(subnode.hasProperty(NAVIGATIONAL_URL)).thenReturn(true);
        javax.jcr.Property externalURLProperty1 = mock(Property.class);
        when(subnode.getProperty(NAVIGATIONAL_URL))
                .thenReturn(externalURLProperty1);
        when(subnode.hasProperty(MENU_METADATA)).thenReturn(true);
        Property submetadataProperty = mock(Property.class);
        when(subnode.getProperty(MENU_METADATA))
                .thenReturn(submetadataProperty);
        when(submetadataProperty.isMultiple()).thenReturn(false);
        Value mvalue = mock(Value.class);

        when(mvalue.toString()).thenReturn(DISPLAY_NATIVE);

        when(submetadataProperty.getValue()).thenReturn(mvalue);

        menuBuilderServiceImpl.getMenuJSON(topNavResource);
    }

    @Test
    public final void testDoGetMultipleFalse2() throws Exception {
        mockList = new ArrayList<Hit>();
        when(systemService.getServiceResourceResolver()).thenReturn(resolver);
        Resource topNavResource = mock(Resource.class);
        when(resolver.getResource(TestConstants.URL_PATH))
                .thenReturn(topNavResource);
        when(topNavResource.adaptTo(Page.class)).thenReturn(page);
        Iterator pageIterator = mock(Iterator.class);
        when(page.listChildren(Mockito.any(PageFilter.class)))
                .thenReturn(pageIterator);
        when(pageIterator.hasNext()).thenReturn(true, true, true, true, false);
        Page childPage = mock(Page.class);
        when(pageIterator.next()).thenReturn(childPage);

        when(childPage.getTitle()).thenReturn("title");
        when(childPage.adaptTo(Node.class)).thenReturn(childNode);
        when(childNode.getNode(JCR_CONTENT)).thenReturn(childjcrNode);
        when(childjcrNode.hasProperty(NAVIGATIONAL_URL)).thenReturn(true);
        javax.jcr.Property externalURLProperty = mock(Property.class);
        when(childjcrNode.getProperty(NAVIGATIONAL_URL))
                .thenReturn(externalURLProperty);
        when(childjcrNode.hasProperty(MENU_METADATA)).thenReturn(true);
        Property metadataProperty = mock(Property.class);
        when(childjcrNode.getProperty(MENU_METADATA))
                .thenReturn(metadataProperty);
        when(metadataProperty.isMultiple()).thenReturn(false);
        // Value[] metadatavalues = new Value[4];
        Value metadataValue = mock(Value.class);
        // when(metadataValue.toString()).thenReturn(DISPLAY_NATIVE);
        // when(metadataValue.toString()).thenReturn(DISPLAY_MOBILE);
        when(metadataValue.toString()).thenReturn(DISPLAY_TABLET);
        // when(metadataValue.toString()).thenReturn(DISPLAY_DESKTOP);
        when(metadataProperty.getValue()).thenReturn(metadataValue);
        Iterator subpageIterator = mock(Iterator.class);
        when(childPage.listChildren(Mockito.any(PageFilter.class)))
                .thenReturn(subpageIterator);
        when(subpageIterator.hasNext()).thenReturn(true, false);
        Page subpage = mock(Page.class);
        when(subpage.getTitle()).thenReturn("Title");
        when(subpage.getPath()).thenReturn(TestConstants.PATH);
        when(subpageIterator.next()).thenReturn(subpage);
        Node subchildNode = mock(Node.class);
        when(subpage.adaptTo(Node.class)).thenReturn(subchildNode);
        Node subnode = mock(Node.class);
        when(subchildNode.getNode(JCR_CONTENT)).thenReturn(subnode);
        when(subnode.hasProperty(NAVIGATIONAL_URL)).thenReturn(true);
        javax.jcr.Property externalURLProperty1 = mock(Property.class);
        when(subnode.getProperty(NAVIGATIONAL_URL))
                .thenReturn(externalURLProperty1);
        when(subnode.hasProperty(MENU_METADATA)).thenReturn(true);
        Property submetadataProperty = mock(Property.class);
        when(subnode.getProperty(MENU_METADATA))
                .thenReturn(submetadataProperty);
        when(submetadataProperty.isMultiple()).thenReturn(false);
        Value mvalue = mock(Value.class);

        when(mvalue.toString()).thenReturn(DISPLAY_NATIVE);

        when(submetadataProperty.getValue()).thenReturn(mvalue);

        menuBuilderServiceImpl.getMenuJSON(topNavResource);
    }

    @Test
    public final void testDoGetMultipleFalse3() throws Exception {
        mockList = new ArrayList<Hit>();
        when(systemService.getServiceResourceResolver()).thenReturn(resolver);
        Resource topNavResource = mock(Resource.class);
        when(resolver.getResource(TestConstants.URL_PATH))
                .thenReturn(topNavResource);
        when(topNavResource.adaptTo(Page.class)).thenReturn(page);
        Iterator pageIterator = mock(Iterator.class);
        when(page.listChildren(Mockito.any(PageFilter.class)))
                .thenReturn(pageIterator);
        when(pageIterator.hasNext()).thenReturn(true, true, true, true, false);
        Page childPage = mock(Page.class);
        when(pageIterator.next()).thenReturn(childPage);

        when(childPage.getTitle()).thenReturn("title");
        when(childPage.adaptTo(Node.class)).thenReturn(childNode);
        when(childNode.getNode(JCR_CONTENT)).thenReturn(childjcrNode);
        when(childjcrNode.hasProperty(NAVIGATIONAL_URL)).thenReturn(true);
        javax.jcr.Property externalURLProperty = mock(Property.class);
        when(childjcrNode.getProperty(NAVIGATIONAL_URL))
                .thenReturn(externalURLProperty);
        when(childjcrNode.hasProperty(MENU_METADATA)).thenReturn(true);
        Property metadataProperty = mock(Property.class);
        when(childjcrNode.getProperty(MENU_METADATA))
                .thenReturn(metadataProperty);
        when(metadataProperty.isMultiple()).thenReturn(false);
        // Value[] metadatavalues = new Value[4];
        Value metadataValue = mock(Value.class);
        when(metadataValue.toString()).thenReturn(DISPLAY_DESKTOP);
        when(metadataProperty.getValue()).thenReturn(metadataValue);
        Iterator subpageIterator = mock(Iterator.class);
        when(childPage.listChildren(Mockito.any(PageFilter.class)))
                .thenReturn(subpageIterator);
        when(subpageIterator.hasNext()).thenReturn(true, false);
        Page subpage = mock(Page.class);
        when(subpage.getTitle()).thenReturn("Title");
        when(subpage.getPath()).thenReturn(TestConstants.PATH);
        when(subpageIterator.next()).thenReturn(subpage);
        Node subchildNode = mock(Node.class);
        when(subpage.adaptTo(Node.class)).thenReturn(subchildNode);
        Node subnode = mock(Node.class);
        when(subchildNode.getNode(JCR_CONTENT)).thenReturn(subnode);
        when(subnode.hasProperty(NAVIGATIONAL_URL)).thenReturn(true);
        javax.jcr.Property externalURLProperty1 = mock(Property.class);
        when(subnode.getProperty(NAVIGATIONAL_URL))
                .thenReturn(externalURLProperty1);
        when(subnode.hasProperty(MENU_METADATA)).thenReturn(true);
        Property submetadataProperty = mock(Property.class);
        when(subnode.getProperty(MENU_METADATA))
                .thenReturn(submetadataProperty);
        when(submetadataProperty.isMultiple()).thenReturn(false);
        Value mvalue = mock(Value.class);

        when(mvalue.toString()).thenReturn(DISPLAY_NATIVE);

        when(submetadataProperty.getValue()).thenReturn(mvalue);

        menuBuilderServiceImpl.getMenuJSON(topNavResource);
    }
}
