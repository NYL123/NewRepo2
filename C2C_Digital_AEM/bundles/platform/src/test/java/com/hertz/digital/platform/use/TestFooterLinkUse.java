/*package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
import com.hertz.digital.platform.bean.FooterDetailBean;
import com.hertz.digital.platform.bean.FooterLinkBean;

@RunWith(PowerMockRunner.class)
@PrepareForTest({WCMUsePojo.class, LoggerFactory.class})
public class TestFooterLinkUse {

	@InjectMocks
	private FooterLinksUse footerLinksUse;
	
	Logger log;
	
	List<FooterLinkBean> footerLinkBeanList;
	
	@Mock
	FooterLinkBean footerLinkBean;
	
	@Mock
	FooterDetailBean footerDetailBean;
	
	@Mock
	ValueMap map1;
	
	
	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		footerLinksUse=PowerMockito.mock(FooterLinksUse.class);
		footerLinkBean=PowerMockito.mock(FooterLinkBean.class);
		footerDetailBean=PowerMockito.mock(FooterDetailBean.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log=Mockito.mock(Logger.class);
		Field field=SocialLinksUse.class.getDeclaredField("logger");
		field.setAccessible(true);
		Field modifiersField=Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL); 
		field.set(null,log);	
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		footerLinkBeanList=new ArrayList<FooterLinkBean>();
		footerLinkBeanList.add(footerLinkBean);
		Mockito.doCallRealMethod().when(footerLinksUse).activate();
		Mockito.doCallRealMethod().when(footerLinksUse).getFooterLinkBeanList();
		Mockito.doCallRealMethod().when(footerLinksUse).setFooterLinkBeanList(footerLinkBeanList);	
	}
	
	@Test
	public final void test() throws Exception{
		
		String jsonObject="{'heading':'Company Information','displayDesktopHeading':true,'displayTabletHeading':true,'displayMobileHeading':true,'displayAppHeading':true,'footerLinks':[{'linkText':'About Hertz','internalLink':'','externalLink':'http://hertz.jobs/','openURLNewWindow':true,'displayDesktopLink':true,'displayTabletLink':true,'displayMobileLink':true,'displayAppLink':true},{'linkText':'Newsroom','internalLink':'http://newsroom.hertz.com/','externalLink':'','openURLNewWindow':true,'displayDesktopLink':true,'displayTabletLink':true,'displayMobileLink':true,'displayAppLink':true},{'linkText':'Investor Relations','internalLink':'','externalLink':'http://newsroom.hertz.com/','openURLNewWindow':true,'displayDesktopLink':true,'displayTabletLink':true,'displayMobileLink':false,'displayAppLink':false}]}";
		String[] mockArray=new String[]{jsonObject};
		footerLinksUse.setFooterLinkBeanList(footerLinkBeanList);
		when(footerLinksUse.getProperties()).thenReturn(map1);
		when(map1.get("links",String[].class)).thenReturn(mockArray);
		footerLinksUse.activate();
		Assert.assertNotNull(footerLinksUse.getFooterLinkBeanList());		
		
	}
	
}
*/