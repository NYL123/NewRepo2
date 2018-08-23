package com.hertz.digital.platform.bean;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestPartnerCategoryBean {
	@InjectMocks
	private PartnerCategoryBean bean;
	
	@Before
	public void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		bean=PowerMockito.mock(PartnerCategoryBean.class);
		Mockito.doCallRealMethod().when(bean).setPartnerCategoryName(Mockito.anyString());
		Mockito.doCallRealMethod().when(bean).getPartnerCategoryName();
		PartnerDetailsBean detailsBean = new PartnerDetailsBean();
		List<PartnerDetailsBean> partners = new ArrayList<>();
		partners.add(detailsBean);
		Mockito.doCallRealMethod().when(bean).setPartners(partners);
		Mockito.doCallRealMethod().when(bean).getPartners();
		Mockito.doCallRealMethod().when(bean).setPartnerCategory(Mockito.anyString());
		Mockito.doCallRealMethod().when(bean).getPartnerCategory();
		bean.setPartnerCategoryName("Name");
		bean.setPartners(partners);
		bean.setPartnerCategory("Category");
		
	}
	
	@Test
	public void test(){
		Assert.assertNotNull(bean.getPartnerCategoryName());
		Assert.assertNotNull(bean.getPartners());
		Assert.assertNotNull(bean.getPartnerCategory());
	}
	
}
