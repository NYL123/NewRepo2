package com.hertz.digital.platform.replication;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)

public class HertzParameterUtilTest {

    @Test
    public void testConvertToMap() throws Exception {
        String[] values = new String[] { "1", ",3", "2,2" };
        Assert.assertNotNull(HertzParameterUtil.convertToMap(values, ","));
        Assert.assertNotNull(HertzParameterUtil.convertToMap(values, ",", true, "default"));
        Assert.assertNotNull(HertzParameterUtil.convertToMap(null, ","));
        Assert.assertNotNull(HertzParameterUtil.convertToMap(values, ",", true, "default", true));
    }

    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor<HertzParameterUtil> constructor = HertzParameterUtil.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

}
