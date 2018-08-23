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
public class TestSubMenuItem {

	@InjectMocks
	private SubMenuItemBean subMenuItem;
	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		subMenuItem=Mockito.mock(SubMenuItemBean.class);
		Mockito.doCallRealMethod().when(subMenuItem).setDisplayNative(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(subMenuItem).setDisplayMobile(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(subMenuItem).setDisplayTablet(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(subMenuItem).setDisplayDesktop(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(subMenuItem).setUrl(Mockito.anyString());
		Mockito.doCallRealMethod().when(subMenuItem).setTitle(Mockito.anyString());
		Mockito.doCallRealMethod().when(subMenuItem).setOpenUrlNewWindow(true);
		Mockito.doCallRealMethod().when(subMenuItem).setSeoNoFollow(true);
		Mockito.doCallRealMethod().when(subMenuItem).isDisplayDesktop();
		Mockito.doCallRealMethod().when(subMenuItem).isDisplayTablet();
		Mockito.doCallRealMethod().when(subMenuItem).isDisplayMobile();
		Mockito.doCallRealMethod().when(subMenuItem).isDisplayNative();
		Mockito.doCallRealMethod().when(subMenuItem).getTitle();
		Mockito.doCallRealMethod().when(subMenuItem).getUrl();
		Mockito.doCallRealMethod().when(subMenuItem).isOpenUrlNewWindow();
		Mockito.doCallRealMethod().when(subMenuItem).getSeoNoFollow();
		subMenuItem.setDisplayNative(true);
		subMenuItem.setDisplayMobile(true);
		subMenuItem.setDisplayTablet(true);
		subMenuItem.setDisplayDesktop(true);
		subMenuItem.setUrl("url");
		subMenuItem.setTitle("title");
		subMenuItem.setOpenUrlNewWindow(true);
		subMenuItem.setSeoNoFollow(true);
	}
	
	@Test
	public final void test(){
		Assert.assertNotNull(subMenuItem.getTitle());
		Assert.assertNotNull(subMenuItem.getUrl());
		Assert.assertTrue(subMenuItem.isDisplayDesktop());
		Assert.assertTrue(subMenuItem.isDisplayTablet());
		Assert.assertTrue(subMenuItem.isDisplayMobile());
		Assert.assertTrue(subMenuItem.isDisplayNative());
		Assert.assertTrue(subMenuItem.isOpenUrlNewWindow());
		Assert.assertTrue(subMenuItem.getSeoNoFollow());
	}
}
