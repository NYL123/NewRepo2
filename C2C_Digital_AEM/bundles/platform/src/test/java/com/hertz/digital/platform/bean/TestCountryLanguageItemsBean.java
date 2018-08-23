package com.hertz.digital.platform.bean;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestCountryLanguageItemsBean {

	@InjectMocks
	private CountryLanguageItemsBean countryLanguageItemsBean=new CountryLanguageItemsBean();
	
	@Mock
	List<LanguageItemsBean> list;
	
	@Before
	public final void setup() throws Exception{
		countryLanguageItemsBean.setCountryCode("countryCode");
		countryLanguageItemsBean.setCountryName("countryName");
		countryLanguageItemsBean.setLanguageItemsBeanList(list);
		countryLanguageItemsBean.setDomain("domain");
		countryLanguageItemsBean.setPos("pos");
		countryLanguageItemsBean.setIsirac(true);
	}
	
	@Test
	public final void test(){
		Assert.assertTrue(countryLanguageItemsBean.getCountryCode().equals("countryCode"));
		Assert.assertTrue(countryLanguageItemsBean.getCountryName().equals("countryName"));
		Assert.assertNotNull(countryLanguageItemsBean.getLanguageItemsBeanList());
		Assert.assertTrue(countryLanguageItemsBean.getDomain().equals("domain"));
		Assert.assertTrue(countryLanguageItemsBean.getPos().equals("pos"));
		Assert.assertTrue(countryLanguageItemsBean.getIsirac());
	}
}