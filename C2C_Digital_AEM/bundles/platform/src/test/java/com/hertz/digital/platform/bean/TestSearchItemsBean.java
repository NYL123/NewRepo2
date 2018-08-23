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
public class TestSearchItemsBean {

	@InjectMocks
	private SearchItemsBean searchItemsBean;
	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		searchItemsBean=Mockito.mock(SearchItemsBean.class);
		Mockito.doCallRealMethod().when(searchItemsBean).setSearchIconAltText(Mockito.anyString());
		Mockito.doCallRealMethod().when(searchItemsBean).getSearchIconAltText();
		searchItemsBean.setSearchIconAltText("Search Icon Alt Text");
	}
	
	@Test
	public final void test(){
		Assert.assertNotNull(searchItemsBean.getSearchIconAltText());
	}
}
