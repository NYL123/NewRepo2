package com.hertz.digital.platform.service.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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

import com.google.common.cache.Cache;

import junit.framework.Assert;
import junitx.util.PrivateAccessor;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoggerFactory.class })
public class TestPreviewTokenImpl {

    @InjectMocks
    private PreviewTokenImpl previewTokenImpl;

    Logger log;
    @Mock
    private Cache<String, String> cache;
    Class<?> servclass;
    Field fields[];

    @Before
    public void setUp() throws Exception {

        previewTokenImpl = PowerMockito.mock(PreviewTokenImpl.class);
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LoggerFactory.class);
        log = Mockito.mock(Logger.class);
        Field field = PreviewTokenImpl.class.getDeclaredField("LOGGER");
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, log);
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);

        Mockito.doCallRealMethod().when(previewTokenImpl).generate("a99a18c1-6156-4169-aaa1-200de989cd9b");
        Mockito.doCallRealMethod().when(previewTokenImpl).validate("a99a18c1-6156-4169-aaa1-200de989cd9b");
    }

    @Test
    public void testConstructorIsPublic() throws Exception {
        Constructor<PreviewTokenImpl> constructor = PreviewTokenImpl.class.getDeclaredConstructor();
        assertTrue(Modifier.isPublic(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public final void testGenerate() throws Exception {
        String token = previewTokenImpl.generate("a99a18c1-6156-4169-aaa1-200de989cd9b");
        Assert.assertNotNull(token);
    }

    @Test
    public final void testValidateEmpty() throws Exception {
        String token = previewTokenImpl.validate("a99a18c1-6156-4169-aaa1-200de989cd9b");
        Assert.assertNotNull(token);
    }

    @Test
    public final void testValidateNotNull() throws Exception {

        servclass = previewTokenImpl.getClass();
        fields = servclass.getDeclaredFields();
        PrivateAccessor.setField(previewTokenImpl, "cache", cache);
        when(cache.getIfPresent("a99a18c1-6156-4169-aaa1-200de989cd9b"))
                .thenReturn("a99a18c1-6156-4169-aaa1-200de989cd9b");
        String token = previewTokenImpl.validate("a99a18c1-6156-4169-aaa1-200de989cd9b");
        Assert.assertNotNull(token);
    }
}
