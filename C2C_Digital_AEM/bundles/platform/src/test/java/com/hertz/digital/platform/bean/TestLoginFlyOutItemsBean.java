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
public class TestLoginFlyOutItemsBean {

	@InjectMocks
	private LoginFlyOutItemsBean loginFlyOutItemsBean;
	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		loginFlyOutItemsBean=Mockito.mock(LoginFlyOutItemsBean.class);
		Mockito.doCallRealMethod().when(loginFlyOutItemsBean).setCtaAction(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(loginFlyOutItemsBean).setFlyoutItemPath(Mockito.anyString());
		Mockito.doCallRealMethod().when(loginFlyOutItemsBean).setFlyoutItemTxt(Mockito.anyString());
		Mockito.doCallRealMethod().when(loginFlyOutItemsBean).getCtaAction();
		Mockito.doCallRealMethod().when(loginFlyOutItemsBean).getFlyoutItemTxt();
		Mockito.doCallRealMethod().when(loginFlyOutItemsBean).getFlyoutItemPath();
		loginFlyOutItemsBean.setCtaAction(true);
		loginFlyOutItemsBean.setFlyoutItemPath("flyoutitempath");
		loginFlyOutItemsBean.setFlyoutItemTxt("flyoutitemtext");
	}
	
	@Test
	public final void test(){
		Assert.assertNotNull(loginFlyOutItemsBean.getFlyoutItemPath());
		Assert.assertNotNull(loginFlyOutItemsBean.getFlyoutItemTxt());
		Assert.assertTrue(loginFlyOutItemsBean.getCtaAction());
	}
}
