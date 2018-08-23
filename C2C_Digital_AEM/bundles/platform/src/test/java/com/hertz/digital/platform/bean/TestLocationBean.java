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
public class TestLocationBean {

	@InjectMocks
	private LocationBean locationBean;
	
	@Before
	public void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		locationBean=PowerMockito.mock(LocationBean.class);
		Mockito.doCallRealMethod().when(locationBean).setOag(Mockito.anyString());
		Mockito.doCallRealMethod().when(locationBean).setRegion(Mockito.anyString());
		Mockito.doCallRealMethod().when(locationBean).setCountry(Mockito.anyString());
		Mockito.doCallRealMethod().when(locationBean).setLanguage(Mockito.anyString());
		Mockito.doCallRealMethod().when(locationBean).setStateOrProvince(Mockito.anyString());
		Mockito.doCallRealMethod().when(locationBean).setCity(Mockito.anyString());
		Mockito.doCallRealMethod().when(locationBean).setHoursOfOperation1(Mockito.anyString());
		Mockito.doCallRealMethod().when(locationBean).setHoursOfOperation2(Mockito.anyString());
		Mockito.doCallRealMethod().when(locationBean).setHoursOfOperation3(Mockito.anyString());
		Mockito.doCallRealMethod().when(locationBean).getOag();
		Mockito.doCallRealMethod().when(locationBean).getRegion();
		Mockito.doCallRealMethod().when(locationBean).getCountry();
		Mockito.doCallRealMethod().when(locationBean).getLanguage();
		Mockito.doCallRealMethod().when(locationBean).getStateOrProvince();
		Mockito.doCallRealMethod().when(locationBean).getCity();
		Mockito.doCallRealMethod().when(locationBean).getHoursOfOperation1();
		Mockito.doCallRealMethod().when(locationBean).getHoursOfOperation2();
		Mockito.doCallRealMethod().when(locationBean).getHoursOfOperation3();
		locationBean.setOag("Oag");
		locationBean.setCity("City");
		locationBean.setCountry("Country");
		locationBean.setHoursOfOperation1("hoursOfOperation1");
		locationBean.setHoursOfOperation2("HoursofOperation2");
		locationBean.setHoursOfOperation3("hoursOfOperation3");
		locationBean.setLanguage("language");
		locationBean.setRegion("region");
		locationBean.setStateOrProvince("stateOrProvince");
		
	}
	
	@Test
	public void test(){
		Assert.assertTrue(locationBean.getOag().equalsIgnoreCase("Oag"));
		Assert.assertTrue(locationBean.getRegion().equalsIgnoreCase("region"));
		Assert.assertTrue(locationBean.getCountry().equalsIgnoreCase("Country"));
		Assert.assertTrue(locationBean.getLanguage().equalsIgnoreCase("language"));
		Assert.assertTrue(locationBean.getStateOrProvince().equalsIgnoreCase("stateOrProvince"));
		Assert.assertTrue(locationBean.getCity().equalsIgnoreCase("City"));
		Assert.assertTrue(locationBean.getHoursOfOperation1().equalsIgnoreCase("hoursOfOperation1"));
		Assert.assertTrue(locationBean.getHoursOfOperation2().equalsIgnoreCase("hoursOfOperation2"));
		Assert.assertTrue(locationBean.getHoursOfOperation3().equalsIgnoreCase("hoursOfOperation3"));
	}
	


}
