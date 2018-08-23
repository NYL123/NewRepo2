package com.hertz.digital.platform.bean.spa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.hertz.digital.platform.bean.FooterContainerBean;
import com.hertz.digital.platform.bean.HeaderBean;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestSpaPageBean {

    @InjectMocks
    private SpaPageBean homePageBean;

    @Mock
    Map<String, String> parsysResources;

    @Mock
    List<Map<String, Object>> includedComponents;

    @Mock
    List<HeaderBean> headerBeanList;

    @Mock
    List<FooterContainerBean> footerContentList;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        homePageBean = PowerMockito.mock(SpaPageBean.class);
        Mockito.doCallRealMethod().when(homePageBean)
                .setPageTitle(Mockito.anyString());
        Mockito.doCallRealMethod().when(homePageBean)
                .setIncludedComponents(Mockito.anyList());
        Mockito.doCallRealMethod().when(homePageBean)
                .setParsysResources(Mockito.anyMap());
        Mockito.doCallRealMethod().when(homePageBean)
                .setConfiguredProps(new HashMap<String, Object>());
        Mockito.doCallRealMethod().when(homePageBean)
                .setMetaData(new HashMap<String, Object>());
        Mockito.doCallRealMethod().when(homePageBean)
                .setHertzLinks(new HashMap<String, Object>());
        Mockito.doCallRealMethod().when(homePageBean)
                .setHeaderBeanList(Mockito.anyList());
        Mockito.doCallRealMethod().when(homePageBean)
                .setFooterContentList(Mockito.anyList());
        Mockito.doCallRealMethod().when(homePageBean).getIncludedComponents();
        Mockito.doCallRealMethod().when(homePageBean).getPageTitle();
        Mockito.doCallRealMethod().when(homePageBean).getParsysResources();
        Mockito.doCallRealMethod().when(homePageBean).getConfiguredProps();
        Mockito.doCallRealMethod().when(homePageBean).getMetaData();
        Mockito.doCallRealMethod().when(homePageBean).getHertzLinks();
        Mockito.doCallRealMethod().when(homePageBean).getHeaderBeanList();
        Mockito.doCallRealMethod().when(homePageBean).getFooterContentList();
        homePageBean.setIncludedComponents(includedComponents);
        homePageBean.setPageTitle("pageTitle");
        homePageBean.setParsysResources(parsysResources);
        homePageBean.setConfiguredProps(new HashMap<String, Object>());
        homePageBean.setMetaData(new HashMap<String, Object>());
        homePageBean.setHertzLinks(new HashMap<String, Object>());
        homePageBean.setHeaderBeanList(headerBeanList);
        homePageBean.setFooterContentList(footerContentList);
    }

    @Test
    public void Test() {
        Assert.assertNotNull(homePageBean.getIncludedComponents());
        Assert.assertNotNull(homePageBean.getPageTitle());
        Assert.assertNotNull(homePageBean.getParsysResources());
        Assert.assertNotNull(homePageBean.getConfiguredProps());
        Assert.assertNotNull(homePageBean.getMetaData());
        Assert.assertNotNull(homePageBean.getHertzLinks());
        Assert.assertNotNull(homePageBean.getHeaderBeanList());
        Assert.assertNotNull(homePageBean.getFooterContentList());
    }

}
