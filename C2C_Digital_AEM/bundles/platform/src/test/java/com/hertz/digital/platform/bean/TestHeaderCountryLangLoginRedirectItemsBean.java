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
public class TestHeaderCountryLangLoginRedirectItemsBean {

	@InjectMocks
	private HeaderCountryLangLoginRedirectItemsBean bean;

	@Before
	public final void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		bean = Mockito.mock(HeaderCountryLangLoginRedirectItemsBean.class);
		Mockito.doCallRealMethod().when(bean).setCancelButton(Mockito.anyString());
		Mockito.doCallRealMethod().when(bean).setContinueButton(Mockito.anyString());
		Mockito.doCallRealMethod().when(bean).setRedirectHeading(Mockito.anyString());
		Mockito.doCallRealMethod().when(bean).setRedirectMessage(Mockito.anyString());
		Mockito.doCallRealMethod().when(bean).getCancelButton();
		Mockito.doCallRealMethod().when(bean).getContinueButton();
		Mockito.doCallRealMethod().when(bean).getRedirectHeading();
		Mockito.doCallRealMethod().when(bean).getRedirectMessage();
		
		
		bean.setCancelButton("Country");
		bean.setContinueButton("Language");
		bean.setRedirectHeading("Update");
		bean.setRedirectMessage("TargetURL");
	}

	@Test
	public final void test() {
		Assert.assertNotNull(bean.getCancelButton());
		Assert.assertNotNull(bean.getContinueButton());
		Assert.assertNotNull(bean.getRedirectHeading());
		Assert.assertNotNull(bean.getRedirectMessage());
	}
}
