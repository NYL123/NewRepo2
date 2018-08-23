
package com.hertz.digital.platform.replication.dispatcher.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.day.cq.replication.Agent;
import com.day.cq.replication.AgentConfig;
import com.day.cq.replication.AgentManager;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationOptions;
import com.day.cq.replication.Replicator;
import com.hertz.digital.platform.replication.dispatcher.HertzDispatcherFlusher;

public class HertzDispatcherFlusherImplTest {
    @Mock
    private Replicator replicator;

    @Mock
    private AgentManager agentManager;
    
    @Mock
    private SlingSettingsService slingSettings;

    @InjectMocks
    private HertzDispatcherFlusher dispatcherFlusher = new HertzDispatcherFlusherImpl();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        reset(replicator);
        reset(agentManager);
    }

    @Test
    public void testName() throws Exception {

    }

    @Test
    public void testFlush() throws Exception {
        final ResourceResolver resourceResolver = mock(ResourceResolver.class);
        final Session session = mock(Session.class);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);

        Set<String> runModes = new HashSet<>();
        runModes.add("publish");
        when(slingSettings.getRunModes()).thenReturn(runModes);
        
        
        final String path1 = "/content/foo";
        final String path2 = "/content/bar";
        
        

       dispatcherFlusher.flush(resourceResolver, path1, path2);

        verify(replicator, times(1)).replicate(eq(session), eq(ReplicationActionType.ACTIVATE), eq(path1),
                any(ReplicationOptions.class));

        verify(replicator, times(1)).replicate(eq(session), eq(ReplicationActionType.ACTIVATE), eq(path2),
                any(ReplicationOptions.class));

        verifyNoMoreInteractions(replicator);
    }


    @Test
    public void testFlush_2() throws Exception {
        final ResourceResolver resourceResolver = mock(ResourceResolver.class);
        final Session session = mock(Session.class);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);

        final ReplicationActionType actionType = ReplicationActionType.DELETE;
        final boolean synchronous = false;

        final String path1 = "/content/foo";
        final String path2 = "/content/bar";
        
        Set<String> runModes = new HashSet<>();
        runModes.add("publish");
        when(slingSettings.getRunModes()).thenReturn(runModes);

        dispatcherFlusher.flush(resourceResolver, actionType, synchronous, path1, path2);

        verify(replicator, times(1)).replicate(eq(session), eq(actionType), eq(path1),
                any(ReplicationOptions.class));

        verify(replicator, times(1)).replicate(eq(session), eq(actionType), eq(path2),
                any(ReplicationOptions.class));

        verifyNoMoreInteractions(replicator);
    }

    @Test
    public void testGetFlushAgents() throws Exception {
        final Agent agent1 = mock(Agent.class);
        final Agent agent2 = mock(Agent.class);

        final AgentConfig agentConfig1 = mock(AgentConfig.class);
        final AgentConfig agentConfig2 = mock(AgentConfig.class);

        @SuppressWarnings("unchecked")
        final Map<String, Agent> agents = mock(Map.class);
        final Collection<Agent> agentValues = Arrays.asList(new Agent[]{ agent1, agent2 });

        when(agentManager.getAgents()).thenReturn(agents);

        when(agents.values()).thenReturn(agentValues);

        when(agent1.getId()).thenReturn("Agent 1");
        when(agent1.isEnabled()).thenReturn(true);
        when(agent1.getConfiguration()).thenReturn(agentConfig1);

        when(agent2.getId()).thenReturn("Agent 2");
        when(agent2.isEnabled()).thenReturn(true);
        when(agent2.getConfiguration()).thenReturn(agentConfig2);

        when(agentConfig1.getSerializationType()).thenReturn("flush");
        when(agentConfig2.getSerializationType()).thenReturn("notflush");

        when(agentConfig1.getTransportURI()).thenReturn("http://localhost/dispatcher/invalidate.cache");
        when(agentConfig2.getTransportURI()).thenReturn("ftp://localhost/dispatcher/invalidate.cache");

        Map<String, Object> tmp = new HashMap<String, Object>();
        tmp.put(AgentConfig.PROTOCOL_HTTP_HEADERS, new String[] {"CQ-Action:{action}", "CQ-Handle:{path}",
                "CQ-Path: {path}"});

        when(agentConfig1.getProperties()).thenReturn(new ValueMapDecorator(tmp));
        when(agentConfig2.getProperties()).thenReturn(new ValueMapDecorator(tmp));


        final Agent[] actual = dispatcherFlusher.getFlushAgents();

        assertEquals(1, actual.length);

        assertEquals("Agent 1", actual[0].getId());
    }
}
