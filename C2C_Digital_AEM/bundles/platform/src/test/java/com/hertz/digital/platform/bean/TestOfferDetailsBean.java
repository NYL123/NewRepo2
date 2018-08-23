package com.hertz.digital.platform.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import junit.framework.Assert;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestOfferDetailsBean {

	@InjectMocks
	private OfferDetailsBean offerDetailsBean;
	
	@Mock
	CategoriesBean categoriesBean;
	
	
	
	Map<String, Object> attributes=new HashMap<>();
	List<CategoriesBean> list=new ArrayList<>();
	List<Map<String,Object>> list1=new ArrayList<>();
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(LoggerFactory.class);
		offerDetailsBean=PowerMockito.mock(OfferDetailsBean.class);
		attributes.put("offerName", "offer1");
		list.add(categoriesBean);
		list1.add(attributes);
		Mockito.doCallRealMethod().when(offerDetailsBean).getAttributes();
		Mockito.doCallRealMethod().when(offerDetailsBean).setAttributes(attributes);
		Mockito.doCallRealMethod().when(offerDetailsBean).getCategories();
		Mockito.doCallRealMethod().when(offerDetailsBean).setCategories(list);
		Mockito.doCallRealMethod().when(offerDetailsBean).getFilter();
		Mockito.doCallRealMethod().when(offerDetailsBean).setFilter(list1);
		Mockito.doCallRealMethod().when(offerDetailsBean).getMetaData();
		Mockito.doCallRealMethod().when(offerDetailsBean).setMetaData(attributes);
		Mockito.doCallRealMethod().when(offerDetailsBean).getOfferName();
		Mockito.doCallRealMethod().when(offerDetailsBean).setOfferName("offer1");
		offerDetailsBean.setOfferName("offer1");
		offerDetailsBean.setMetaData(attributes);
		offerDetailsBean.setFilter(list1);
		offerDetailsBean.setCategories(list);
		offerDetailsBean.setAttributes(attributes);
	}
	
	@Test
	public void testGetterSetter(){
		Assert.assertTrue(offerDetailsBean.getOfferName().equalsIgnoreCase("offer1"));
		Assert.assertTrue(offerDetailsBean.getAttributes().get("offerName").toString().equalsIgnoreCase("offer1"));
		Assert.assertTrue(offerDetailsBean.getMetaData().get("offerName").toString().equalsIgnoreCase("offer1"));
		Assert.assertNotNull(offerDetailsBean.getCategories());
		Assert.assertNotNull(offerDetailsBean.getFilter());
	}
}
