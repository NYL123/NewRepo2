/**
 * 
 */
package com.hertz.digital.platform.bean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

/**
 * @author a.dhingra
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestLanguageItemsBean {
	@InjectMocks
	private LanguageItemsBean languageItemsBean=new LanguageItemsBean();
	
	@Before
	public final void setup(){
		languageItemsBean.setCountryName("countryName");
		languageItemsBean.setDefaultLanguage(true);
		languageItemsBean.setLanguageCode("languageCode");
		languageItemsBean.setLanguageName("languageName");
		languageItemsBean.setLocale("locale");
		languageItemsBean.setURL("url");
		languageItemsBean.setIracLink("iracLink");
		//languageItemsBean.setUseLocaleInUrl(true);
		
	}
	
	@Test
	public final void testGetters(){
		//Assert.assertTrue(languageItemsBean.getUseLocaleInUrl());
		Assert.assertTrue(languageItemsBean.getCountryName().equals("countryName"));
		Assert.assertTrue(languageItemsBean.getLanguageCode().equals("languageCode"));
		Assert.assertTrue(languageItemsBean.getDefaultLanguage());
		Assert.assertTrue(languageItemsBean.getLanguageName().equals("languageName"));
		Assert.assertTrue(languageItemsBean.getLocale().equals("locale"));
		Assert.assertTrue(languageItemsBean.getURL().equals("url"));
		Assert.assertTrue(languageItemsBean.getIracLink().equals("iracLink"));
	}
}
