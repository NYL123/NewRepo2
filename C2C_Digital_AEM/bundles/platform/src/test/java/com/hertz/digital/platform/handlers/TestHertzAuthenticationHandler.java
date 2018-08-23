package com.hertz.digital.platform.handlers;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.auth.core.spi.AuthenticationHandler;
import org.apache.sling.auth.core.spi.AuthenticationInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.Cache;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.service.api.PreviewToken;

import junit.framework.Assert;
import junitx.util.PrivateAccessor;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoggerFactory.class })
public class TestHertzAuthenticationHandler {

    @InjectMocks
    private HertzAuthenticationHandler hertzAuthenticationHandler;

    @Mock
    private PreviewToken previewTokenService;;

    @Mock
    private AuthenticationHandler wrappedHandler;

    Logger log;
    @Mock
    private Cache<String, String> cache;
    Class<?> servclass;
    Field fields[];

    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    AuthenticationInfo authenticationInfo;

    @Mock
    HttpServletResponse httpServletResponse;

    String previewToken = "a99a18c1-6156-4169-aaa1-200de989cd9b";
    String authToken = "a99a18c1-6156-4169-aaa1-200de989cd9b";

    @Before
    public void setUp() throws Exception {

        hertzAuthenticationHandler = PowerMockito.mock(HertzAuthenticationHandler.class);
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LoggerFactory.class);
        log = Mockito.mock(Logger.class);
        Field field = HertzAuthenticationHandler.class.getDeclaredField("log");
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, log);
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
        PrivateAccessor.setField(hertzAuthenticationHandler, "wrappedAuthHandler", wrappedHandler);
        Mockito.doCallRealMethod().when(hertzAuthenticationHandler).extractCredentials(httpServletRequest,
                httpServletResponse);
        Mockito.doCallRealMethod().when(hertzAuthenticationHandler).requestCredentials(httpServletRequest,
                httpServletResponse);
        Mockito.doCallRealMethod().when(hertzAuthenticationHandler).dropCredentials(httpServletRequest,
                httpServletResponse);
        Mockito.doCallRealMethod().when(hertzAuthenticationHandler).authenticationFailed(httpServletRequest,
                httpServletResponse, authenticationInfo);
    }

    @Test
    public void testExtractCredentials() throws Exception {
        when(httpServletRequest.getHeader(HertzConstants.PREVIEW_AUTH_TOKEN)).thenReturn(previewToken);
        when(previewTokenService.validate(previewToken)).thenReturn(authToken);
        when(httpServletRequest.getMethod()).thenReturn("GET");
        hertzAuthenticationHandler.extractCredentials(httpServletRequest, httpServletResponse);
    }

    @Test
    public void testExtractCredentialsNullPreviewToken() throws Exception {
        when(httpServletRequest.getHeader(HertzConstants.PREVIEW_AUTH_TOKEN)).thenReturn(null);
        when(httpServletRequest.getMethod()).thenReturn("POST");
        hertzAuthenticationHandler.extractCredentials(httpServletRequest, httpServletResponse);
    }

    @Test
    public void testExtractCredentialsFromCookie() throws Exception {
        Cookie[] cookies = new Cookie[]{new Cookie(HertzConstants.PREVIEW_AUTH_TOKEN, previewToken)};
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        when(previewTokenService.validate(previewToken)).thenReturn(authToken);
        when(httpServletRequest.getMethod()).thenReturn("GET");
        hertzAuthenticationHandler.extractCredentials(httpServletRequest, httpServletResponse);
    }

    @Test
    public void testExtractCredentialsFromCookieNullPreviewToken() throws Exception {
        Cookie[] cookies = new Cookie[]{new Cookie("logintoken", previewToken)};
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        when(previewTokenService.validate(previewToken)).thenReturn(authToken);
        when(httpServletRequest.getMethod()).thenReturn("GET");
        hertzAuthenticationHandler.extractCredentials(httpServletRequest, httpServletResponse);
    }
    
    @Test
    public void testExtractCredentialsNullGetMethod() throws Exception {
        when(httpServletRequest.getHeader(HertzConstants.PREVIEW_AUTH_TOKEN)).thenReturn(null);
        when(httpServletRequest.getMethod()).thenReturn(null);
        hertzAuthenticationHandler.extractCredentials(httpServletRequest, httpServletResponse);
    }

    @Test
    public void testRequestCredentials() throws IOException {
        when(wrappedHandler.requestCredentials(httpServletRequest, httpServletResponse)).thenReturn(true);
        hertzAuthenticationHandler.requestCredentials(httpServletRequest, httpServletResponse);
    }

    @Test
    public void testDropCredentials() throws IOException {
        hertzAuthenticationHandler.dropCredentials(httpServletRequest, httpServletResponse);
    }

    @Test
    public void testAuthentocationFailed() throws IOException {
        hertzAuthenticationHandler.authenticationFailed(httpServletRequest, httpServletResponse, authenticationInfo);
    }

    @Test
    public void testAuthentocationSucceded() throws IOException {
        Mockito.doCallRealMethod().when(hertzAuthenticationHandler).authenticationSucceeded(httpServletRequest,
                httpServletResponse, authenticationInfo);
        Assert.assertFalse(hertzAuthenticationHandler.authenticationSucceeded(httpServletRequest, httpServletResponse,
                authenticationInfo));
    }

    @Test
    public void testActivate() {
        final Map<String, String> config = new HashMap<>();
        Mockito.doCallRealMethod().when(hertzAuthenticationHandler).activate(config);
        hertzAuthenticationHandler.activate(config);
    }

    @Test
    public void testActivate1() throws NoSuchFieldException {
        final Map<String, String> config = new HashMap<>();
        PrivateAccessor.setField(hertzAuthenticationHandler, "wrappedAuthHandler", null);
        Mockito.doCallRealMethod().when(hertzAuthenticationHandler).activate(config);
        hertzAuthenticationHandler.activate(config);
    }

}
