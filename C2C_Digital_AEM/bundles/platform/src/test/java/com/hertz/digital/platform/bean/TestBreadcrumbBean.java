package com.hertz.digital.platform.bean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestBreadcrumbBean {

    @InjectMocks
    private BreadcrumbBean breadcrumbBean = new BreadcrumbBean();

    @Before
    public final void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        breadcrumbBean.setURI("https://www.hertz.com");
        breadcrumbBean.setLabel("Link Text");
    }

    @Test
    public final void test() {
        Assert.assertNotNull(breadcrumbBean.getLabel());
        Assert.assertNotNull(breadcrumbBean.getURI());
    }
}
