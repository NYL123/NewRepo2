package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.ValueMap;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.hertz.digital.platform.bean.FlyoutBean;
import com.hertz.digital.platform.constants.HertzConstants;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestFlyoutUse {
	
	@InjectMocks
	FlyoutUse use ; 
	Logger log;
	
	@Mock
	ValueMap propMap;
	
	
	@Before
	public final void setup() throws Exception {
		use =PowerMockito.mock(FlyoutUse.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);
		Mockito.doCallRealMethod().when(use).activate();
		Mockito.doCallRealMethod().when(use).setFlyoutBeanList(Mockito.anyList());
		Mockito.doCallRealMethod().when(use).getFlyoutBeanList();
	}
	@Test 
	public final void testActivate() throws Exception {
		when(use.getProperties()).thenReturn(propMap);
		String str ="{\"heading\":\"Account Summary\",\"linkurl\":\"/content/hertz-rac/rac-web/en-us/account\",\"openURLNewWindow\":\"no\",\"rel\":\"no\"}";
		String str1 ="{\"heading\":\"Rental Rewards\",\"linkurl\":\"/content/hertz-rac/rac-web/en-us/account\",\"openURLNewWindow\":\"yes\",\"rel\":\"yes\"}";
		String str2 ="{\"heading\":\"Rewards FAQ\",\"linkurl\":\"/content/hertz-rac/rac-web/en-us/account\",\"openURLNewWindow\":\"no\",\"rel\":\"yes\"}";
		String str3 ="{\"heading\":\"Rewards FAQ\",\"linkurl\":\"/content/hertz-rac/rac-web/en-us/account\",\"openURLNewWindow\":\"yes\",\"rel\":\"no\"}";
		String[] headerArray ={str, str1, str2};
		List<FlyoutBean> flyoutBeanList=new ArrayList<FlyoutBean>();
		use.setFlyoutBeanList(flyoutBeanList);
		when(propMap.get(HertzConstants.LINKS, String[].class)).thenReturn(headerArray);
		use.activate();
		Assert.assertNotNull(use.getFlyoutBeanList());
	}
	@Test 
	public final void testActivateNull() throws Exception {
		when(use.getProperties()).thenReturn(propMap);
		String[] headerArray =null;
		List<FlyoutBean> flyoutBeanList=new ArrayList<FlyoutBean>();
		use.setFlyoutBeanList(flyoutBeanList);
		when(propMap.get(HertzConstants.LINKS, String[].class)).thenReturn(headerArray);
		use.activate();
		Assert.assertNotNull(use.getFlyoutBeanList());
	}
	
}
