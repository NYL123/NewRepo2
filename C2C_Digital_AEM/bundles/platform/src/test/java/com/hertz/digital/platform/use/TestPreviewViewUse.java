package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
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
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.constants.TestConstants;
import com.hertz.digital.platform.factory.FedServicesConfigurationFactory;

import junit.framework.Assert;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestPreviewViewUse {

    @InjectMocks
    PreviewViewUse previewViewUse;

    Logger log;

    @Mock
    ValueMap map;

    @Mock
    SlingScriptHelper scriptHelper;

    @Mock
    FedServicesConfigurationFactory fedServicesConfigurationFactory;

    @Mock
    SlingHttpServletRequest mockRequest;
    
    @Mock
    Page page;

    @Before
    public final void setup() throws Exception {
        previewViewUse = PowerMockito.mock(PreviewViewUse.class);
        PowerMockito.mockStatic(LoggerFactory.class);
        log = Mockito.mock(Logger.class);
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
        when(previewViewUse.getRequest()).thenReturn(mockRequest);
        when(mockRequest.getScheme()).thenReturn("http");
        MockitoAnnotations.initMocks(this);
        Mockito.doCallRealMethod().when(previewViewUse).activate();
        Mockito.doCallRealMethod().when(previewViewUse).getPreviewLinkTarget();
    }

    @Test
    public final void testActivate() throws Exception {
        when(previewViewUse.getPageProperties()).thenReturn(map);
        when(map.get(HertzConstants.PREVIEW_MODE_PATH)).thenReturn(TestConstants.OFFER_LANDING_PATH);
        when(previewViewUse.getSlingScriptHelper()).thenReturn(scriptHelper);
        when(scriptHelper.getService(FedServicesConfigurationFactory.class))
                .thenReturn(fedServicesConfigurationFactory);
        when(map.get(HertzConstants.JCR_CQ_TEMPLATE, String.class)).thenReturn(HertzConstants.DATA_TEMPLATE_PATH);
        when(previewViewUse.getCurrentPage()).thenReturn(page);
        when(page.getLanguage(false)).thenReturn(new Locale("en", "US"));
        previewViewUse.activate();
    }

    @Test
    public final void testActivate1() throws Exception {
        when(previewViewUse.getPageProperties()).thenReturn(map);
        when(map.get(HertzConstants.PREVIEW_MODE_PATH)).thenReturn(null);
        when(previewViewUse.getSlingScriptHelper()).thenReturn(scriptHelper);
        when(scriptHelper.getService(FedServicesConfigurationFactory.class))
                .thenReturn(fedServicesConfigurationFactory);
        when(previewViewUse.getCurrentPage()).thenReturn(page);
        when(page.getLanguage(false)).thenReturn(new Locale("en", "US"));
        when(map.get(HertzConstants.JCR_CQ_TEMPLATE, String.class)).thenReturn(HertzConstants.DATA_TEMPLATE_PATH);
        previewViewUse.activate();
    }

    @Test
    public final void testActivate2() throws Exception {
        when(previewViewUse.getPageProperties()).thenReturn(map);
        when(map.get(HertzConstants.PREVIEW_MODE_PATH)).thenReturn(null);
        when(previewViewUse.getSlingScriptHelper()).thenReturn(scriptHelper);
        when(scriptHelper.getService(FedServicesConfigurationFactory.class))
                .thenReturn(fedServicesConfigurationFactory);
        when(map.get(HertzConstants.JCR_CQ_TEMPLATE, String.class)).thenReturn(null);
        when(previewViewUse.getCurrentPage()).thenReturn(page);
        when(page.getPath()).thenReturn(TestConstants.OFFER_PATH);
        previewViewUse.activate();
    }

    @Test
    public final void testActivate3() throws Exception {
        when(previewViewUse.getPageProperties()).thenReturn(map);
        when(map.get(HertzConstants.PREVIEW_MODE_PATH)).thenReturn(null);
        when(previewViewUse.getSlingScriptHelper()).thenReturn(scriptHelper);
        when(scriptHelper.getService(FedServicesConfigurationFactory.class))
                .thenReturn(fedServicesConfigurationFactory);
        when(map.get(HertzConstants.JCR_CQ_TEMPLATE, String.class)).thenReturn("/apps");
        when(previewViewUse.getCurrentPage()).thenReturn(page);
        when(page.getPath()).thenReturn(TestConstants.OFFER_PATH);
        previewViewUse.activate();
    }

    @Test
    public final void testGetterSetter() throws Exception {
        when(previewViewUse.getPageProperties()).thenReturn(map);
        when(previewViewUse.getSlingScriptHelper()).thenReturn(scriptHelper);
        when(scriptHelper.getService(FedServicesConfigurationFactory.class))
                .thenReturn(fedServicesConfigurationFactory);
        when(map.get(HertzConstants.PREVIEW_MODE_PATH)).thenReturn(TestConstants.PREVIEW_VIEW_PATH);
        when(previewViewUse.getCurrentPage()).thenReturn(page);
        when(page.getLanguage(false)).thenReturn(new Locale("en", "US"));
        previewViewUse.activate();
        Assert.assertTrue(StringUtils.contains(previewViewUse.getPreviewLinkTarget(), TestConstants.PREVIEW_VIEW_PATH));
    }

}
