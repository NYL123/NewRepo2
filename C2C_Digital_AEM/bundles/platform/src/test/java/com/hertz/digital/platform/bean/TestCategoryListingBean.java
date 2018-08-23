/**
 * 
 */
package com.hertz.digital.platform.bean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

/**
 * @author a.dhingra
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestCategoryListingBean {
	
	@InjectMocks
	private CategoryListingBean categoryListingBean=new CategoryListingBean();
	
	@Mock
	OfferCategoryBean attributes;
	
	@Mock
	OfferCategoryBean subCategory;
	
	@Before
	public void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		categoryListingBean.setName("name");
		categoryListingBean.setRank("rank");
		categoryListingBean.setDb2Category("db2Category");
		categoryListingBean.setContentPath("contentPath");
		categoryListingBean.setAttributes(attributes);
		categoryListingBean.setSubCategory(subCategory);
	}
	
	@Test
	public final void test(){
		Assert.assertTrue(categoryListingBean.getName().equals("name"));
		Assert.assertTrue(categoryListingBean.getRank().equals("rank"));
		Assert.assertTrue(categoryListingBean.getDb2Category().equals("db2Category"));
		Assert.assertTrue(categoryListingBean.getContentPath().equals("contentPath"));
		Assert.assertNotNull(categoryListingBean.getAttributes());
		Assert.assertNotNull(categoryListingBean.getSubCategory());
	}

}
