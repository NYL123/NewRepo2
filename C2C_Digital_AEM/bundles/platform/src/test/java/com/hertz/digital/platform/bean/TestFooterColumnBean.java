/**
 * 
 */
package com.hertz.digital.platform.bean;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;

/**
 * @author a.dhingra
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestFooterColumnBean {

	@InjectMocks
	private FooterColumnBean columnBean=new FooterColumnBean();
	
	@Mock
	FooterLinkBean bean;
	
	@Before
	public final void setup(){
		columnBean.setColumn(bean);
	}
	
	@Test
	public final void testVariable(){
		Assert.assertNotNull(columnBean.getColumn());
	}
}
