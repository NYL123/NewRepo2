package com.hertz.digital.platform.bean;

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

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestLoginItemsBean {

	@InjectMocks
	private LoginItemsBean loginItemsBean;
	
	@Mock
	LoginFlyOutItemsBean flyoutitems;
	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		loginItemsBean=Mockito.mock(LoginItemsBean.class);
		Mockito.doCallRealMethod().when(loginItemsBean).setLoginBtnTxt(Mockito.anyString());
		Mockito.doCallRealMethod().when(loginItemsBean).setLoginPlaTxt(Mockito.anyString());
		Mockito.doCallRealMethod().when(loginItemsBean).setLoginWelcomeText(Mockito.anyString());
		Mockito.doCallRealMethod().when(loginItemsBean).getLoginBtnTxt();
		Mockito.doCallRealMethod().when(loginItemsBean).getLoginPlaTxt();
		Mockito.doCallRealMethod().when(loginItemsBean).getLoginWelcomeText();
		loginItemsBean.setLoginBtnTxt("Login Button Text");
		loginItemsBean.setLoginPlaTxt("Login Pla Text");
		loginItemsBean.setLoginWelcomeText("Welcome");
	}
	
	@Test
	public final void test(){
		Assert.assertNotNull(loginItemsBean.getLoginPlaTxt());
		Assert.assertNotNull(loginItemsBean.getLoginBtnTxt());
		Assert.assertTrue(loginItemsBean.getLoginWelcomeText().equals("Welcome"));
	}
}
