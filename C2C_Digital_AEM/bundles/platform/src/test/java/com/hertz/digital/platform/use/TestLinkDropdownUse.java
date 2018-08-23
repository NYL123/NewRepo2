package com.hertz.digital.platform.use;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.ValueMap;
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
import com.hertz.digital.platform.bean.LinkDropdownBean;
import com.hertz.digital.platform.constants.HertzConstants;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestLinkDropdownUse {
	
	@InjectMocks
	LinkDropdownUse dropdownUse;
	
	@Mock
	ValueMap peoperties;
	
	Logger log;
	
	@Before
	public final void setup() throws Exception {
		dropdownUse =PowerMockito.mock(LinkDropdownUse.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);
		Mockito.doCallRealMethod().when(dropdownUse).activate();
		Mockito.doCallRealMethod().when(dropdownUse).getLinkDropdownBeanList();


	}
	
	@Test
	public final void testActivate() throws Exception {
		when(dropdownUse.getProperties()).thenReturn(peoperties);
		String str1 ="{\"dropdownValue\":\"India\",\"order\":\"1\",\"pageUrl\":\"/content/hertz-rac/rac-web\"}";
		String str2 ="{\"dropdownValue\":\"Canada\",\"order\":\"2\",\"pageUrl\":\"/content/hertz-rac/rac-web\"}";
		String[] str ={str1, str2};
		when(peoperties.get(HertzConstants.DROPDOWN_VALUE_LIST, String[].class)).thenReturn(str);
		LinkDropdownBean bean= Mockito.mock(LinkDropdownBean.class);
		List<LinkDropdownBean> linkDropdownBeanList = new ArrayList();
		linkDropdownBeanList.add(bean);
		Mockito.doCallRealMethod().when(dropdownUse).setLinkDropdownBeanList(linkDropdownBeanList);
		dropdownUse.setLinkDropdownBeanList(linkDropdownBeanList);
		dropdownUse.activate();
		assertNotNull(dropdownUse.getLinkDropdownBeanList());
	}
	
	@Test
	public final void testLinkDropdownBean() throws Exception {
		LinkDropdownBean bean= new LinkDropdownBean();
		bean.setDropdownValue("Test");
		bean.setOrder("Test");
		assertNotNull(bean.getDropdownValue());
		assertNotNull(bean.getOrder());
		
	}

}
