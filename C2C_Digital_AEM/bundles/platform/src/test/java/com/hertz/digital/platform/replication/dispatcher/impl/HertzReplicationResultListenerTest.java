package com.hertz.digital.platform.replication.dispatcher.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.Agent;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class HertzReplicationResultListenerTest {
	
	@InjectMocks
	private HertzReplicationResultListener rrListener = new HertzReplicationResultListener();
	
	@Mock
	Agent agent;
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public final void test() {
		rrListener.onEnd(agent, null, null);
		rrListener.onError(agent, null, null);
		rrListener.onMessage(null, null);
		rrListener.onStart(agent, null);
		rrListener.getResults();
	}
}
