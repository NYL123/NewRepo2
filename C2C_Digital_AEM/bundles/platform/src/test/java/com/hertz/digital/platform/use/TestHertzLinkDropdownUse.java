package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
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
import com.hertz.digital.platform.bean.HertzLinkDropdownBean;
import com.hertz.digital.platform.constants.HertzConstants;

import junit.framework.Assert;
import junitx.util.PrivateAccessor;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestHertzLinkDropdownUse {
	
	@InjectMocks
	HertzLinkDropdownUse hertzLinkDropdownUse;
	
	Logger log;
	
	@Mock
	Resource resource;
	
	@Mock
	ValueMap map;
	
	@Mock
	List<HertzLinkDropdownBean> list=new ArrayList<>();
	
	@Before
	public final void setup() throws Exception {
		hertzLinkDropdownUse =PowerMockito.mock(HertzLinkDropdownUse.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);
		Mockito.doCallRealMethod().when(hertzLinkDropdownUse).activate();
	}
	
	@Test
	public final void testActivate() throws Exception{
		when(hertzLinkDropdownUse.getResource()).thenReturn(resource);
		when(resource.getValueMap()).thenReturn(map);
		when(map.containsKey(HertzConstants.LINK)).thenReturn(false);
		hertzLinkDropdownUse.activate();
	}
	
	@Test
	public final void testActivate1() throws Exception{
		PrivateAccessor.setField(hertzLinkDropdownUse, "valuesList", list);
		String[] array=new String[]{"{'label':'label','linkType':'linkType','sequenceId':'sequenceId','targetType':'targetType','cdpCode':'cdpCode','rqCode':'rqCode','pcCode':'pcCode'}"};
		when(hertzLinkDropdownUse.getResource()).thenReturn(resource);
		when(resource.getValueMap()).thenReturn(map);
		when(map.containsKey(HertzConstants.LINK)).thenReturn(true);
		when(map.get(HertzConstants.LINK,String[].class)).thenReturn(array);
		hertzLinkDropdownUse.activate();
		
	}
	
	@Test
	public final void testVariables(){
		Mockito.doCallRealMethod().when(hertzLinkDropdownUse).getValuesList();
		Mockito.doCallRealMethod().when(hertzLinkDropdownUse).setValuesList(list);
		hertzLinkDropdownUse.setValuesList(list);
		Assert.assertNotNull(hertzLinkDropdownUse.getValuesList());
	}

}
