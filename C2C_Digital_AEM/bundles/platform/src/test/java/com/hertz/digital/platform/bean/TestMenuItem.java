package com.hertz.digital.platform.bean;

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

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestMenuItem {

	@InjectMocks
	private MenuItemBean menuItem= new MenuItemBean();
	
	@Mock
	HeadingBean heading;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(LoggerFactory.class);
		menuItem=Mockito.mock(MenuItemBean.class);
		heading=Mockito.mock(HeadingBean.class);
		Mockito.doCallRealMethod().when(menuItem).setHeading(heading);
		Mockito.doCallRealMethod().when(menuItem).getHeading();
		menuItem.setHeading(heading);
	}
	
	@Test
	public final void test(){
		Assert.assertNotNull(menuItem.getHeading());
	}
	
	
	
}
