package com.hertz.digital.platform.bean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestCategoriesBean {
	
	@InjectMocks
	private CategoriesBean categoriesBean = new CategoriesBean();
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(LoggerFactory.class);
		categoriesBean.setCategory("category1");
	}
	
	@Test
	public void testGetterSetters(){
		Assert.assertTrue(categoriesBean.getCategory().equalsIgnoreCase("category1"));
	}

}
