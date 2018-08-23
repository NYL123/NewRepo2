package com.hertz.digital.platform.servlets;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.NoSuchAlgorithmException;

import org.apache.jackrabbit.api.security.authentication.token.TokenCredentials;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.auth.core.spi.AuthenticationHandler;
import org.apache.sling.auth.core.spi.AuthenticationInfo;
import org.apache.sling.jcr.resource.JcrResourceConstants;
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

import com.hertz.digital.platform.service.api.PreviewToken;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoggerFactory.class })
public class TestPreviewTokenServlet {

    @InjectMocks
    private PreviewTokenServlet previewTokenServlet;

    Logger log;

    @Mock
    SlingHttpServletRequest request;

    @Mock
    SlingHttpServletResponse response;

    @Mock
    PreviewToken previewToken;
    @Mock
    AuthenticationHandler authHandler;

    @Mock
    AuthenticationInfo authInfoMap;

    TokenCredentials token;

    @Before
    public final void setup() throws Exception {

        previewTokenServlet = PowerMockito.mock(PreviewTokenServlet.class);
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LoggerFactory.class);
        log = Mockito.mock(Logger.class);
        Field field = PreviewTokenServlet.class.getDeclaredField("LOGGER");
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, log);
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
        when(authHandler.extractCredentials(request, response)).thenReturn(authInfoMap);
        token = new TokenCredentials("a99a18c1-6156-4169-aaa1-200de989cd9b");
        Mockito.doCallRealMethod().when(previewTokenServlet).doGet(request, response);
    }

    @Test
    public final void testDoGet() throws Exception {
        when(authInfoMap.get(JcrResourceConstants.AUTHENTICATION_INFO_CREDENTIALS)).thenReturn(token);
        when(previewToken.generate(Mockito.anyString())).thenReturn("a89a18c1-6156-4169-aaa1-200qce89cd9b");
        StringWriter stringWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(pWriter);
        previewTokenServlet.doGet(request, response);
        pWriter.flush();
        verify(request, atLeast(1));
        assertTrue(stringWriter.toString().contains("{\"previewAuthToken\":\"a89a18c1-6156-4169-aaa1-200qce89cd9b\"}"));
    }

    @Test
    public final void testDoGetNoSuchAlgorithmException() throws Exception {
        when(authInfoMap.get(JcrResourceConstants.AUTHENTICATION_INFO_CREDENTIALS)).thenReturn(token);
        when(previewToken.generate(Mockito.anyString())).thenThrow(new NoSuchAlgorithmException());
        StringWriter stringWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(pWriter);
        previewTokenServlet.doGet(request, response);
        // pWriter.flush();
        verify(request, atLeast(1));
        assertTrue(stringWriter.getBuffer().length() == 2);
    }

}
