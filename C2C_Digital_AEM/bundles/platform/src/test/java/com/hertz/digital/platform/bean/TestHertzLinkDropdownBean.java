package com.hertz.digital.platform.bean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestHertzLinkDropdownBean {

	@InjectMocks
	private HertzLinkDropdownBean bean=new HertzLinkDropdownBean();
	
	@Before
	public void setup() throws Exception{
		bean.setCdpCode("12345");
		bean.setLabel("Label");
		bean.setLinkType("linkType");
		bean.setPcCode("pcCode");
		bean.setRqCode("rqCode");
		bean.setSequenceId("sequenceId");
		bean.setTargetType("targetType");
	}
	
	@Test
	public void testVariables(){
		Assert.assertEquals("12345", bean.getCdpCode());
		Assert.assertEquals("Label", bean.getLabel());
		Assert.assertEquals("linkType", bean.getLinkType());
		Assert.assertEquals("pcCode", bean.getPcCode());
		Assert.assertEquals("rqCode", bean.getRqCode());
		Assert.assertEquals("sequenceId", bean.getSequenceId());
		Assert.assertEquals("targetType", bean.getTargetType());
	}
}
