package com.hertz.digital.platform.replication;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class ReplicationResultTest {

	@Test
	public final void test() {
		ReplicationResult repResult = new ReplicationResult("path", ReplicationResult.Status.REPLICATED);
		Assert.assertNotNull(repResult.getPath());
		Assert.assertNotNull(repResult.getStatus());
		Assert.assertNotNull(repResult.toString());
		
		repResult = new ReplicationResult("path", ReplicationResult.Status.REPLICATED, "version");
		Assert.assertNotNull(repResult.getVersion());
	}
	
	
}
