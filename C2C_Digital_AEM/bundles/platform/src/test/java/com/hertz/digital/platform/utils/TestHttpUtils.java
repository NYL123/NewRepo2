package com.hertz.digital.platform.utils;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.exceptions.SystemException;

/**
 * 
 * Test class for HttpUtils.java
 * 
 * @author a.dhingra
 *
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({ "javax.net.ssl.*" })
@PrepareForTest(LoggerFactory.class)
public class TestHttpUtils {

	Logger log;


	@Mock
	InputStream inputStream;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		Field field = HttpUtils.class.getDeclaredField("LOGGER");
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(null, log);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);

	}

	/*
	@Test
	public void testGet() throws IOException {
		when(log.isDebugEnabled()).thenReturn(true);
		Assert.assertNotNull(HttpUtils.get("http://www.hertz.com", "q=1"));
		Assert.assertNotNull(HttpUtils.get("http://www.hertz.com", "q=1", "http:"));
		Assert.assertNotNull(HttpUtils.get("https://www.hertz.com", "q=1", "http"));
		Assert.assertNotNull(HttpUtils.get("http://www.hertz.com", "q=1", "http:", "admin", "admin"));
		Assert.assertNotNull(HttpUtils.get("https://www.hertz.com", "q=1", "http", "admin", "admin"));
		when(log.isDebugEnabled()).thenReturn(false);
		Assert.assertNotNull(HttpUtils.get("http://www.hertz.com", "q=1"));
		Assert.assertNotNull(HttpUtils.get("http://www.hertz.com", "q=1", "http:"));
		Assert.assertNotNull(HttpUtils.get("https://www.hertz.com", "q=1", "http"));
		Assert.assertNotNull(HttpUtils.get("http://www.hertz.com", "q=1", "http:", "admin", "admin"));
		//Assert.assertNotNull(HttpUtils.get("https://www.hertz.com", "q=1", "http", "admin", "admin"));

	}
	*/

	/*@Test
	public void testGetUsingHttpClient() {
		when(log.isDebugEnabled()).thenReturn(true);
		Assert.assertNotNull(HttpUtils.getUsingHttpClient("http://www.apache.org", "q=1"));
		Assert.assertNotNull(HttpUtils.getUsingHttpClient("http://www.hertz.com", "q=1"));
		when(log.isDebugEnabled()).thenReturn(false);
		Assert.assertNotNull(HttpUtils.getUsingHttpClient("http://www.apache.org", "q=1"));
		Assert.assertNotNull(HttpUtils.getUsingHttpClient("http://www.hertz.com", "q=1"));

	}*/

	/*
	@Test
	public void testPost() {
		when(log.isDebugEnabled()).thenReturn(true);
		Assert.assertNotNull(HttpUtils.post("http://www.hertz.com~OneTime", "abcd", "application/json", false, ""));
		Assert.assertNotNull(HttpUtils.post("https://www.hertz.com", "abcd", "application/json", true, ""));
		when(log.isDebugEnabled()).thenReturn(false);
		Assert.assertNotNull(HttpUtils.post("https://www.hertz.com~OneTime", "abcd", "application/json", true, ""));
		//Assert.assertNotNull(HttpUtils.post("http://www.hertz.com", "abcd", "application/json", true, ""));
	}
	*/

	@Test
	public void testGet() {
		when(log.isDebugEnabled()).thenReturn(true);
		Assert.assertNotNull(HttpUtils.get("https://www.hertz.com", "q=1"));
		Assert.assertNotNull(HttpUtils.get("https://www.hertz.com", "q=1", HertzConstants.HTTP));
		Assert.assertNotNull(HttpUtils.get("https://www.hertz.com", "q=1", "Test"));
		Assert.assertNotNull(HttpUtils.get("https://www.hertz.com", "q=1", "Test", "admin", "admin"));
		Assert.assertNotNull(HttpUtils.get("https://www.hertz.com", StringUtils.EMPTY,HertzConstants.HTTP, "admin", "admin"));
/*		Assert.assertNotNull(HttpUtils.get("http://www.hertz.com", "abcd", "application/json", true, ""));
		when(log.isDebugEnabled()).thenReturn(false);
		Assert.assertNotNull(HttpUtils.get("http://www.hertz.com~OneTime", "abcd", "application/json", true, ""));
		Assert.assertNotNull(HttpUtils.get("http://www.hertz.com", "abcd", "application/json", true, ""));*/
	}
	
	@Test
	public void testConstructorIsPrivate() throws Exception {
		Constructor<HttpUtils> constructor = HttpUtils.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}
}
