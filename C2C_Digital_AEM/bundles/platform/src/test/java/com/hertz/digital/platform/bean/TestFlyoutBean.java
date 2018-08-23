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
public class TestFlyoutBean {

	@InjectMocks
	private FlyoutBean flyoutBean;

	@Before
	public final void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		flyoutBean = PowerMockito.mock(FlyoutBean.class);

		Mockito.doCallRealMethod().when(flyoutBean).setHeading(Mockito.anyString());
		Mockito.doCallRealMethod().when(flyoutBean).getHeading();
		Mockito.doCallRealMethod().when(flyoutBean).setLinkurl(Mockito.anyString());
		Mockito.doCallRealMethod().when(flyoutBean).getLinkurl();
		Mockito.doCallRealMethod().when(flyoutBean).getOpenLegalLinkURLInNewWindow();
		Mockito.doCallRealMethod().when(flyoutBean).getSeoNoFollow();
		Mockito.doCallRealMethod().when(flyoutBean).setOpenLegalLinkURLInNewWindow(true);
		Mockito.doCallRealMethod().when(flyoutBean).setSeoNoFollow(true);
		flyoutBean.setHeading("heading");
		flyoutBean.setLinkurl("linkurl");
		flyoutBean.setOpenLegalLinkURLInNewWindow(true);
		flyoutBean.setSeoNoFollow(true);
	}

	@Test
	public final void test() {
		Assert.assertNotNull(flyoutBean.getHeading());
		Assert.assertNotNull(flyoutBean.getLinkurl());
		Assert.assertTrue(flyoutBean.getOpenLegalLinkURLInNewWindow());
		Assert.assertTrue(flyoutBean.getSeoNoFollow());
	}
}
