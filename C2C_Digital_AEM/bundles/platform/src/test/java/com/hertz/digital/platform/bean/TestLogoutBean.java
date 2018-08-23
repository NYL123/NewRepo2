package com.hertz.digital.platform.bean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestLogoutBean {

    @InjectMocks
    private LogoutBean logoutBean;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        logoutBean = PowerMockito.mock(LogoutBean.class);
        Mockito.doCallRealMethod().when(logoutBean)
                .setLogoutButtonText(Mockito.anyString());
        Mockito.doCallRealMethod().when(logoutBean).getLogoutButtonText();
        logoutBean.setLogoutButtonText("Logout Button");
        Mockito.doCallRealMethod().when(logoutBean)
                .setLogoutURL(Mockito.anyString());
        Mockito.doCallRealMethod().when(logoutBean).getLogoutURL();
        logoutBean.setLogoutURL("/Logout");
    }

    @Test
    public void test() {
        Assert.assertTrue(logoutBean.getLogoutButtonText()
                .equalsIgnoreCase("Logout Button"));
        Assert.assertTrue(
                logoutBean.getLogoutURL().equalsIgnoreCase("/Logout"));
    }
}
