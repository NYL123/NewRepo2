/**
 * 
 */
package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Iterator;
import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
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
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.constants.TestConstants;

import junit.framework.Assert;

/**
 * @author a.dhingra
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestReservationUse {

	@InjectMocks
	private ReservationUse reservationUse;
	
	@Mock
	ResourceResolver resolver;
	
	@Mock
	Resource resource;
	
	private Logger log;
	
	@Mock
	Iterable<Resource> iterable;
	
	@Mock
	Iterator<Resource> iterator;
	
	@Mock
	Resource childPageResource;
	
	@Mock
	Map<String, Object> mockMap;
	
	@Before
	public final void setup() throws Exception {
		reservationUse=PowerMockito.mock(ReservationUse.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);
		Mockito.doCallRealMethod().when(reservationUse).activate();
		Mockito.doCallRealMethod().when(reservationUse).setRetMap(mockMap);
		Mockito.doCallRealMethod().when(reservationUse).getRetMap();
	}
	
	@Test
	public final void testActivate(){
		when(reservationUse.get(HertzConstants.PATH,String.class)).thenReturn(TestConstants.RESERVATION_CONFIG_PATH);
		when(reservationUse.getResourceResolver()).thenReturn(resolver);
		when(resolver.getResource(TestConstants.RESERVATION_CONFIG_PATH)).thenReturn(resource);
		when(resource.getChildren()).thenReturn(iterable);
		when(iterable.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true,false);
		when(iterator.next()).thenReturn(childPageResource);
		reservationUse.activate();
	}
	
	
	@Test
	public final void testGetSetRetMap(){
		reservationUse.setRetMap(mockMap);
		Assert.assertNotNull(reservationUse.getRetMap());
	}
	
}
