
package com.hertz.digital.platform.replication.dispatcher;

import com.day.cq.replication.Agent;
import com.day.cq.replication.AgentConfig;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HertzDispatcherFlushFilterTest {
    private Agent agent;
    private AgentConfig agentConfig;
    private ValueMap invalidProperties;
    private ValueMap hierarchicalProperties;
    private ValueMap resourceOnlyProperties;

    @Before
    public void setUp() throws Exception {
        agent = mock(Agent.class);
        agentConfig = mock(AgentConfig.class);

        when(agent.getId()).thenReturn("mock-agent");
        when(agent.getConfiguration()).thenReturn(agentConfig);

        Map<String, Object> tmp = new HashMap<String, Object>();
        tmp.put(AgentConfig.PROTOCOL_HTTP_HEADERS, new String[] {"CQ-Action:{action}", "CQ-Handle:{path}",
                "CQ-Path: {path}"});
        hierarchicalProperties = new ValueMapDecorator(tmp);

        tmp = new HashMap<String, Object>();
        tmp.put(AgentConfig.PROTOCOL_HTTP_HEADERS, new String[] {"CQ-Action:{action}", "CQ-Handle:{path}",
                "CQ-Path: {path}", "CQ-Action-Scope: ResourceOnly"});
        resourceOnlyProperties = new ValueMapDecorator(tmp);

        tmp = new HashMap<String, Object>();
        tmp.put(AgentConfig.PROTOCOL_HTTP_HEADERS, new String[] {"Foo-Action:{action}", "Foo-Handle:{path}",
                "Foo-Path: {path}"});
        invalidProperties = new ValueMapDecorator(tmp);
    }

    @Test
    public void testIsIncluded_isAgent() throws Exception {
        final HertzDispatcherFlushFilter filter = new HertzDispatcherFlushFilter();

        when(agent.isEnabled()).thenReturn(true);
        when(agentConfig.getTransportURI()).thenReturn("http://localhost:80/dispatcher/invalidate.cache");
        when(agentConfig.getProperties()).thenReturn(hierarchicalProperties);
        when(agentConfig.getSerializationType()).thenReturn("flush");

        final boolean expected = true;
        final boolean actual = filter.isIncluded(agent);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsIncluded_disabled_flush() throws Exception {
        final HertzDispatcherFlushFilter filter = new HertzDispatcherFlushFilter();

        when(agent.isEnabled()).thenReturn(false);
        when(agentConfig.getTransportURI()).thenReturn("https://localhost:80/dispatcher/invalidate.cache");
        when(agentConfig.getProperties()).thenReturn(hierarchicalProperties);
        when(agentConfig.getSerializationType()).thenReturn("flush");

        final boolean expected = false;
        final boolean actual = filter.isIncluded(agent);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsIncluded_notflush() throws Exception {
        final HertzDispatcherFlushFilter filter = new HertzDispatcherFlushFilter();

        when(agent.isEnabled()).thenReturn(true);
        when(agentConfig.getTransportURI()).thenReturn("http://localhost:80/dispatcher/invalidate.cache");
        when(agentConfig.getProperties()).thenReturn(hierarchicalProperties);
        when(agentConfig.getSerializationType()).thenReturn("notflush");

        final boolean expected = false;
        final boolean actual = filter.isIncluded(agent);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsIncluded_enabled_invalidTransportURI() throws Exception {
        final HertzDispatcherFlushFilter filter = new HertzDispatcherFlushFilter();

        when(agent.isEnabled()).thenReturn(true);
        when(agentConfig.getTransportURI()).thenReturn("ftp://localhost:80/not/dispatcher");
        when(agentConfig.getProperties()).thenReturn(hierarchicalProperties);
        when(agentConfig.getSerializationType()).thenReturn("flush");

        final boolean expected = false;
        final boolean actual = filter.isIncluded(agent);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsIncluded_enabled_invalidHTTPHeaders() throws Exception {
        final HertzDispatcherFlushFilter filter = new HertzDispatcherFlushFilter();

        when(agent.isEnabled()).thenReturn(true);
        when(agentConfig.getTransportURI()).thenReturn("http://localhost:80/dispatcher/invalidate.cache");
        when(agentConfig.getProperties()).thenReturn(invalidProperties);
        when(agentConfig.getSerializationType()).thenReturn("flush");

        final boolean expected = false;
        final boolean actual = filter.isIncluded(agent);

        Assert.assertEquals(expected, actual);
    }



    @Test
    public void testIsIncluded_all_hierarchicalIncluded() throws Exception {
        final HertzDispatcherFlushFilter filter = new HertzDispatcherFlushFilter();

        when(agent.isEnabled()).thenReturn(true);
        when(agentConfig.getTransportURI()).thenReturn("http://localhost:80/dispatcher/invalidate.cache");
        when(agentConfig.getProperties()).thenReturn(hierarchicalProperties);
        when(agentConfig.getSerializationType()).thenReturn("flush");

        final boolean expected = true;
        final boolean actual = filter.isIncluded(agent);

        Assert.assertEquals(expected, actual);
    }


    @Test
    public void testIsIncluded_all_resourceOnlyIncluded() throws Exception {
        final HertzDispatcherFlushFilter filter = new HertzDispatcherFlushFilter();

        when(agent.isEnabled()).thenReturn(true);
        when(agentConfig.getTransportURI()).thenReturn("http://localhost:80/dispatcher/invalidate.cache");
        when(agentConfig.getProperties()).thenReturn(resourceOnlyProperties);
        when(agentConfig.getSerializationType()).thenReturn("flush");

        final boolean expected = true;
        final boolean actual = filter.isIncluded(agent);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsIncluded_hierarchical_included() throws Exception {
        final HertzDispatcherFlushFilter filter = new HertzDispatcherFlushFilter(HertzDispatcherFlushFilter.HertzFlushType.Hierarchical);

        when(agent.isEnabled()).thenReturn(true);
        when(agentConfig.getTransportURI()).thenReturn("http://localhost:80/dispatcher/invalidate.cache");
        when(agentConfig.getProperties()).thenReturn(hierarchicalProperties);
        when(agentConfig.getSerializationType()).thenReturn("flush");

        final boolean expected = true;
        final boolean actual = filter.isIncluded(agent);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsIncluded_hierarchical_notIncluded() throws Exception {
        final HertzDispatcherFlushFilter filter = new HertzDispatcherFlushFilter(HertzDispatcherFlushFilter.HertzFlushType.Hierarchical);

        when(agent.isEnabled()).thenReturn(true);
        when(agentConfig.getTransportURI()).thenReturn("http://localhost:80/dispatcher/invalidate.cache");
        when(agentConfig.getProperties()).thenReturn(resourceOnlyProperties);
        when(agentConfig.getSerializationType()).thenReturn("flush");

        final boolean expected = false;
        final boolean actual = filter.isIncluded(agent);

        Assert.assertEquals(expected, actual);
    }
}
