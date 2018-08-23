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
public class TestLinkDropdownBean {

	@InjectMocks
	private LinkDropdownBean linkDropdownBean=new LinkDropdownBean();
	
	@Mock
	List<LanguageItemsBean> list;
	
	@Before
	public final void setup() throws Exception{
		linkDropdownBean.setDropdownValue("India");
		linkDropdownBean.setOrder("order");
		linkDropdownBean.setUrl("url");
	}
	
	@Test
	public final void test(){
		Assert.assertTrue(linkDropdownBean.getDropdownValue().equals("India"));
		Assert.assertTrue(linkDropdownBean.getOrder().equals("order"));
		Assert.assertTrue(linkDropdownBean.getUrl().equals("url"));
	}
}