package com.hertz.digital.platform.bean;

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
public class TestLocationDataBean {

	@InjectMocks
	private LocationDataBean locationDataBean;
	LocationBean locationBean = new LocationBean();
	
	@Before
	public void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		locationDataBean=PowerMockito.mock(LocationDataBean.class);
		Mockito.doCallRealMethod().when(locationDataBean).setLocationBean(locationBean);
		Mockito.doCallRealMethod().when(locationDataBean).getLocationBean();
		locationDataBean.setLocationBean(locationBean);
		locationBean.setOag("Oag");

		
	}
	
	@Test
	public void test(){
		Assert.assertNotNull(locationDataBean.getLocationBean());

	}
	


}
