package com.hertz.digital.platform.bean;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

import junitx.util.PrivateAccessor;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestHeading {

	@InjectMocks
	private HeadingBean heading= new HeadingBean();
	
	@Mock
	SubMenuItemBean subMenuItem;
	
	@Mock
	List<SubMenuItemBean> list;
	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		heading=Mockito.mock(HeadingBean.class);
		subMenuItem=Mockito.mock(SubMenuItemBean.class);
		PrivateAccessor.setField(heading, "items", list);
		Mockito.doCallRealMethod().when(heading).setDisplayNative(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(heading).setDisplayMobile(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(heading).setDisplayTablet(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(heading).setDisplayDesktop(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(heading).setUrl(Mockito.anyString());
		Mockito.doCallRealMethod().when(heading).setTitle(Mockito.anyString());
		Mockito.doCallRealMethod().when(heading).isDisplayDesktop();
		Mockito.doCallRealMethod().when(heading).isDisplayTablet();
		Mockito.doCallRealMethod().when(heading).isDisplayMobile();
		Mockito.doCallRealMethod().when(heading).isDisplayNative();
		Mockito.doCallRealMethod().when(heading).isOpenUrlNewWindow();
		Mockito.doCallRealMethod().when(heading).getSeoNoFollow();
		Mockito.doCallRealMethod().when(heading).getTitle();
		Mockito.doCallRealMethod().when(heading).getUrl();
		Mockito.doCallRealMethod().when(heading).setItems(subMenuItem);
		Mockito.doCallRealMethod().when(heading).getItems();
		Mockito.doCallRealMethod().when(heading).setOpenUrlNewWindow(true);
		Mockito.doCallRealMethod().when(heading).setSeoNoFollow(true);
		heading.setItems(subMenuItem);
		heading.setDisplayNative(true);
		heading.setDisplayMobile(true);
		heading.setDisplayTablet(true);
		heading.setDisplayDesktop(true);
		heading.setUrl("url");
		heading.setTitle("title");
		heading.setOpenUrlNewWindow(true);
		heading.setSeoNoFollow(true);
	}
	
	@Test
	public final void test(){
		Assert.assertNotNull(heading.getTitle());
		Assert.assertNotNull(heading.getUrl());
		Assert.assertTrue(heading.isDisplayDesktop());
		Assert.assertTrue(heading.isDisplayTablet());
		Assert.assertTrue(heading.isDisplayMobile());
		Assert.assertTrue(heading.isDisplayNative());
		Assert.assertNotNull(heading.getItems());
		Assert.assertTrue(heading.isOpenUrlNewWindow());
		Assert.assertTrue(heading.getSeoNoFollow());
	}
}
