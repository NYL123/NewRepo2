package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Property;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.bean.MenuItemBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.constants.TestConstants;
import com.hertz.digital.platform.service.api.MenuBuilderService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestMenuBar {
    private static final String TOP_NAV_PATH = "topnavpath";
    @InjectMocks
    private MenuBarUse menuBar;

    Logger log;

    List<MenuItemBean> menuItems;

    @Mock
    MenuItemBean menuItem;
    @Mock
    ResourceResolver resolver;

    @Mock
    Resource resource;
    @Mock
    Page partnerPage;

    @Mock
    Page currentPage;

    @Mock
    Node partnerNode;
    @Mock
    Node partnerJCRNode;

    @Mock
    SlingScriptHelper slingScriptHelper;

    @Mock
    MenuBuilderService menuBuilderService;

    @Mock
    Property topNavPathProperty;

    @Before
    public final void setup() throws Exception {
        menuBar = PowerMockito.mock(MenuBarUse.class);
        menuItem = PowerMockito.mock(MenuItemBean.class);
        PowerMockito.mockStatic(LoggerFactory.class);
        log = Mockito.mock(Logger.class);
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
        menuItems = new ArrayList<MenuItemBean>();
        menuItems.add(menuItem);
        Mockito.doCallRealMethod().when(menuBar).activate();
        Mockito.doCallRealMethod().when(menuBar).getMenuItems();
        Mockito.doCallRealMethod().when(menuBar).setMenuItems(menuItems);
    }

    @Test
    public final void testActivate() throws Exception {
        menuBar.setMenuItems(menuItems);
        when(menuBar.getCurrentPage()).thenReturn(currentPage);
        when(currentPage.getParent()).thenReturn(partnerPage);
        when(partnerPage.adaptTo(Node.class)).thenReturn(partnerNode);
        when(partnerNode.getNode(HertzConstants.JCR_CONTENT))
                .thenReturn(partnerJCRNode);
        when(partnerJCRNode.hasProperty(TOP_NAV_PATH)).thenReturn(true);
        when(partnerJCRNode.getProperty(TOP_NAV_PATH))
                .thenReturn(topNavPathProperty);
        when(topNavPathProperty.getString()).thenReturn("/header/topnav");
        when(partnerPage.getPath())
                .thenReturn(TestConstants.HEADER_FOOTER_DEFAULT_PATH);
        when(menuBar.getSlingScriptHelper()).thenReturn(slingScriptHelper);
        when(slingScriptHelper.getService(MenuBuilderService.class))
                .thenReturn(menuBuilderService);
        when(menuBar.getResourceResolver()).thenReturn(resolver);
        when(resolver.getResource(
                TestConstants.HEADER_FOOTER_DEFAULT_PATH + "/header/topnav"))
                        .thenReturn(resource);
        when(menuBuilderService.getMenuJSON(resource)).thenReturn(menuItems);
        menuBar.activate();
        Assert.assertNotNull(menuBar.getMenuItems());
    }

    @Test
    public final void testActivateThrowsExceoption() throws Exception {
        menuBar.setMenuItems(menuItems);
        when(menuBar.getCurrentPage()).thenReturn(currentPage);
        when(currentPage.getParent()).thenReturn(partnerPage);
        when(partnerPage.adaptTo(Node.class)).thenReturn(partnerNode);
        when(partnerNode.getNode(HertzConstants.JCR_CONTENT))
                .thenReturn(partnerJCRNode);
        when(partnerJCRNode.hasProperty(TOP_NAV_PATH)).thenReturn(true);
        when(partnerJCRNode.getProperty(TOP_NAV_PATH))
                .thenReturn(topNavPathProperty);
        when(topNavPathProperty.getString()).thenReturn("/header/topnav");
        when(partnerPage.getPath())
                .thenReturn(TestConstants.HEADER_FOOTER_DEFAULT_PATH);
        when(menuBar.getSlingScriptHelper()).thenReturn(slingScriptHelper);
        when(slingScriptHelper.getService(MenuBuilderService.class))
                .thenReturn(menuBuilderService);
        when(menuBar.getResourceResolver()).thenReturn(resolver);
        when(resolver.getResource(
                TestConstants.HEADER_FOOTER_DEFAULT_PATH + "/header/topnav"))
                        .thenReturn(resource);
        // when(menuBuilderService.getMenuJSON(resource))
        // .thenThrow(RepositoryException.class);
        menuBar.activate();
    }

}
