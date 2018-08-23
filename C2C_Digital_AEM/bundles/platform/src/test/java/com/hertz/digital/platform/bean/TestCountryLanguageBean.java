package com.hertz.digital.platform.bean;

import java.util.ArrayList;
import java.util.List;

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
public class TestCountryLanguageBean {

	@InjectMocks
	private CountryLanguageBean countryLanguageBean= new CountryLanguageBean();
	
	@Mock
	CountryLanguageItemsBean countryLanguageItemsBean;
	
	@Mock
	HeaderCountryLanguageItemsBean headerCountryLanguageItemsBean;
	
	@Mock
	HeaderCountryLangLoginRedirectItemsBean headerCountryLangLoginRedirectItemsBean;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(LoggerFactory.class);
		countryLanguageBean=Mockito.mock(CountryLanguageBean.class);
		List<CountryLanguageItemsBean> mockList=new ArrayList<CountryLanguageItemsBean>();
		mockList.add(countryLanguageItemsBean);
		Mockito.doCallRealMethod().when(countryLanguageBean).setCountryLanguageItemsBeanList(mockList);
		Mockito.doCallRealMethod().when(countryLanguageBean).getCountryLanguageItemsBeanList();
		Mockito.doCallRealMethod().when(countryLanguageBean).setHeaderCountryLanguageItemsBean(headerCountryLanguageItemsBean);
		Mockito.doCallRealMethod().when(countryLanguageBean).getHeaderCountryLanagueItemsBean();
		Mockito.doCallRealMethod().when(countryLanguageBean).getHeaderCountryLanguageItemsBean();
		Mockito.doCallRealMethod().when(countryLanguageBean).getHeaderCountryLanguageLoginRedirectItemsBean();
		Mockito.doCallRealMethod().when(countryLanguageBean).setHeaderCountryLanguageLoginRedirectItemsBean(headerCountryLangLoginRedirectItemsBean);
		countryLanguageBean.setCountryLanguageItemsBeanList(mockList);
		countryLanguageBean.setHeaderCountryLanguageItemsBean(headerCountryLanguageItemsBean);
		countryLanguageBean.setHeaderCountryLanguageLoginRedirectItemsBean(headerCountryLangLoginRedirectItemsBean);
	}
	
	@Test
	public final void test(){
		Assert.assertNotNull(countryLanguageBean.getCountryLanguageItemsBeanList());
		Assert.assertNotNull(countryLanguageBean.getHeaderCountryLanagueItemsBean());
		Assert.assertNotNull(countryLanguageBean.getHeaderCountryLanguageItemsBean());
		Assert.assertNotNull(countryLanguageBean.getHeaderCountryLanguageLoginRedirectItemsBean());
		
	}
	
	
	
}
