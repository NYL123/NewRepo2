package com.hertz.digital.platform.bean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestFooterDetailBean {

	@InjectMocks
	private FooterDetailBean footerDetailBean;

	@Before
	public final void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		footerDetailBean = Mockito.mock(FooterDetailBean.class);
		Mockito.doCallRealMethod().when(footerDetailBean).setDisplayAppLink(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(footerDetailBean).setDisplayDesktopLink(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(footerDetailBean).setDisplayMobileLink(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(footerDetailBean).setDisplayTabletLink(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(footerDetailBean).setLinkurl(Mockito.anyString());
		Mockito.doCallRealMethod().when(footerDetailBean).setLinktext(Mockito.anyString());
		Mockito.doCallRealMethod().when(footerDetailBean).setRel(Mockito.anyString());
		Mockito.doCallRealMethod().when(footerDetailBean).setTargetwindow(Mockito.anyString());
		Mockito.doCallRealMethod().when(footerDetailBean).getDisplayAppLink();
		Mockito.doCallRealMethod().when(footerDetailBean).getDisplayDesktopLink();
		Mockito.doCallRealMethod().when(footerDetailBean).getDisplayMobileLink();
		Mockito.doCallRealMethod().when(footerDetailBean).getDisplayTabletLink();
		Mockito.doCallRealMethod().when(footerDetailBean).getLinkurl();
		Mockito.doCallRealMethod().when(footerDetailBean).getLinktext();
		Mockito.doCallRealMethod().when(footerDetailBean).getRel();
		Mockito.doCallRealMethod().when(footerDetailBean).getTargetwindow();
		footerDetailBean.setDisplayAppLink(true);
		footerDetailBean.setDisplayDesktopLink(true);
		footerDetailBean.setDisplayMobileLink(false);
		footerDetailBean.setDisplayTabletLink(false);
		footerDetailBean.setLinkurl("https://www.hertz.com");
		footerDetailBean.setLinktext("Link Text");
		footerDetailBean.setLinkurl("https://www.hertz.com");
		footerDetailBean.setLinktext("Link Text");
		footerDetailBean.setRel("https://www.hertz.com");
		footerDetailBean.setTargetwindow("yes");
	}

	@Test
	public final void test() {
		Assert.assertTrue(footerDetailBean.getDisplayAppLink());
		Assert.assertTrue(footerDetailBean.getDisplayDesktopLink());
		Assert.assertFalse(footerDetailBean.getDisplayMobileLink());
		Assert.assertFalse(footerDetailBean.getDisplayTabletLink());
		Assert.assertNotNull(footerDetailBean.getLinkurl());
		Assert.assertNotNull(footerDetailBean.getLinktext());
		Assert.assertNotNull(footerDetailBean.getRel());
		Assert.assertNotNull(footerDetailBean.getTargetwindow());
	}
}
