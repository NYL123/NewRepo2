package com.hertz.digital.platform.exceptions;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.Assert;
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

import junitx.util.PrivateAccessor;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestSystemException {

	@InjectMocks
	private SystemException systemException;
	
	Logger log;
	
	@Before
	public void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(LoggerFactory.class);
		log=Mockito.mock(Logger.class);
		Field field=SystemException.class.getDeclaredField("LOGGER");
		field.setAccessible(true);
		Field modifiersField=Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL); 
		field.set(null,log);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
	
		
	}
	
	@Test
	public void test() throws NoSuchFieldException{
		systemException=new SystemException();
		systemException=new SystemException("System Exception", new Throwable("Sent from Test Class"));
		PrivateAccessor.setField(systemException, "errorCode", "402");
		Assert.assertTrue(systemException.getErrorCode().equals("402"));
	}
}
