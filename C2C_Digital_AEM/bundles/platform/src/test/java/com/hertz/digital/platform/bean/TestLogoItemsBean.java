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
public class TestLogoItemsBean {

	@InjectMocks
	private LogoItemsBean logoItemsBean;
	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		logoItemsBean=Mockito.mock(LogoItemsBean.class);
		Mockito.doCallRealMethod().when(logoItemsBean).setLogoImagealtText(Mockito.anyString());
		Mockito.doCallRealMethod().when(logoItemsBean).getLogoImagealtText();
		logoItemsBean.setLogoImagealtText("Logo Image Alt Text");
	}
	
	@Test
	public final void test(){
		Assert.assertNotNull(logoItemsBean.getLogoImagealtText());
	}
}
