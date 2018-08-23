package com.hertz.digital.platform.bean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestTermsAndConditionsSectionBean {

	@InjectMocks
	private TermsAndConditionsSectionBean termsAndConditionsSectionBean=new TermsAndConditionsSectionBean();
	
	@Before
	public final void setup(){
		termsAndConditionsSectionBean.setOrderNumber(1);
		termsAndConditionsSectionBean.setSectionDescription("sectionDescription");
	}
	
	@Test
	public final void test(){
		Assert.assertTrue(termsAndConditionsSectionBean.getOrderNumber()==1);
		Assert.assertTrue(termsAndConditionsSectionBean.getSectionDescription().equals("sectionDescription"));
	}
	
}
