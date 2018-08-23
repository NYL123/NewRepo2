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
public class TestHeaderCountryLanguageItemsBean {

	@InjectMocks
	private HeaderCountryLanguageItemsBean headerCountryLanguageItemsBean;

	@Before
	public final void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		headerCountryLanguageItemsBean = Mockito.mock(HeaderCountryLanguageItemsBean.class);
		Mockito.doCallRealMethod().when(headerCountryLanguageItemsBean).setCountryLabel(Mockito.anyString());
		Mockito.doCallRealMethod().when(headerCountryLanguageItemsBean).setLanguageLabel(Mockito.anyString());
		Mockito.doCallRealMethod().when(headerCountryLanguageItemsBean).setUpdateLabel(Mockito.anyString());
		Mockito.doCallRealMethod().when(headerCountryLanguageItemsBean).setTargetUrl(Mockito.anyString());
		Mockito.doCallRealMethod().when(headerCountryLanguageItemsBean).setUpdateMessage(Mockito.anyString());
		Mockito.doCallRealMethod().when(headerCountryLanguageItemsBean).setOpenUrlNewWindow(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(headerCountryLanguageItemsBean).setSeoNoFollow(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(headerCountryLanguageItemsBean).setNoResultsMessage(Mockito.anyString());
		Mockito.doCallRealMethod().when(headerCountryLanguageItemsBean).getCountryLabel();
		Mockito.doCallRealMethod().when(headerCountryLanguageItemsBean).getLanguageLabel();
		Mockito.doCallRealMethod().when(headerCountryLanguageItemsBean).getUpdateLabel();
		Mockito.doCallRealMethod().when(headerCountryLanguageItemsBean).getTargetUrl();
		Mockito.doCallRealMethod().when(headerCountryLanguageItemsBean).getUpdateMessage();
		Mockito.doCallRealMethod().when(headerCountryLanguageItemsBean).getOpenUrlNewWindow();
		Mockito.doCallRealMethod().when(headerCountryLanguageItemsBean).getSeoNoFollow();
		Mockito.doCallRealMethod().when(headerCountryLanguageItemsBean).getNoResultsMessage();
		headerCountryLanguageItemsBean.setCountryLabel("Country");
		headerCountryLanguageItemsBean.setLanguageLabel("Language");
		headerCountryLanguageItemsBean.setUpdateLabel("Update");
		headerCountryLanguageItemsBean.setTargetUrl("TargetURL");
		headerCountryLanguageItemsBean.setUpdateMessage("UpdateMessage");
		headerCountryLanguageItemsBean.setOpenUrlNewWindow(true);
		headerCountryLanguageItemsBean.setSeoNoFollow(true);
		headerCountryLanguageItemsBean.setNoResultsMessage("No results found");
	}

	@Test
	public final void test() {
		Assert.assertNotNull(headerCountryLanguageItemsBean.getCountryLabel());
		Assert.assertNotNull(headerCountryLanguageItemsBean.getLanguageLabel());
		Assert.assertNotNull(headerCountryLanguageItemsBean.getUpdateLabel());
		Assert.assertNotNull(headerCountryLanguageItemsBean.getTargetUrl());
		Assert.assertNotNull(headerCountryLanguageItemsBean.getUpdateMessage());
		Assert.assertNotNull(headerCountryLanguageItemsBean.getOpenUrlNewWindow());
		Assert.assertNotNull(headerCountryLanguageItemsBean.getSeoNoFollow());
		Assert.assertTrue(headerCountryLanguageItemsBean.getNoResultsMessage().equals("No results found"));
	}
}
