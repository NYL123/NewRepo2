package com.hertz.digital.platform.use;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestColumnContainerUse {
	@InjectMocks
	ColumnContainerUse columnContainerUse; 
	
	private Logger log;
	
	@Before
	public final void setup() throws Exception {
		columnContainerUse=PowerMockito.mock(ColumnContainerUse.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);
		Mockito.doCallRealMethod().when(columnContainerUse).activate();
		Mockito.doCallRealMethod().when(columnContainerUse).setList(Mockito.anyList());
		Mockito.doCallRealMethod().when(columnContainerUse).getList();
	}

	@Test
	public final void testActivate() throws Exception {
		ValueMap valueMap = Mockito.mock(ValueMap.class);
		when(columnContainerUse.getProperties()).thenReturn(valueMap);
		when(valueMap.get("columnHeading")).thenReturn("6");
		//assertNotNull(columnContainerUse.getList());

		columnContainerUse.activate();
	}
}
