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
public class TestHeaderFooter {
	
	@InjectMocks
	private HeaderFooterBean headerFooter;
	
	@Mock
	HeaderBean headerBean;
	
	@Mock
	FooterContainerBean footerBean;
	
	List<HeaderBean> listHeader=new ArrayList<HeaderBean>();
	List<FooterContainerBean> listfooter= new ArrayList<FooterContainerBean>();
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(LoggerFactory.class);
		headerFooter=PowerMockito.mock(HeaderFooterBean.class);
		listHeader.add(headerBean);
		listfooter.add(footerBean);
		Mockito.doCallRealMethod().when(headerFooter).setFooterContentList(listfooter);
		Mockito.doCallRealMethod().when(headerFooter).setHeaderBeanList(listHeader);
		Mockito.doCallRealMethod().when(headerFooter).getFooterContentList();
		Mockito.doCallRealMethod().when(headerFooter).getHeaderBeanList();
		headerFooter.setFooterContentList(listfooter);
		headerFooter.setHeaderBeanList(listHeader);
		
	}

	@Test
	public void test(){
		Assert.assertNotNull(headerFooter.getFooterContentList());
		Assert.assertNotNull(headerFooter.getHeaderBeanList());
		
	}
}
