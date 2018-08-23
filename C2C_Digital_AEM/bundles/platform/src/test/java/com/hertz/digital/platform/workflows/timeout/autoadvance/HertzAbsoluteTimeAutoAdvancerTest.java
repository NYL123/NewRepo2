package com.hertz.digital.platform.workflows.timeout.autoadvance;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.slf4j.LoggerFactory;

import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.HistoryItem;
import com.day.cq.workflow.exec.Route;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.Workflow;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.metadata.MetaDataMap;
import com.day.cq.workflow.model.WorkflowNode;
import com.day.cq.workflow.model.WorkflowTransition;
import com.hertz.digital.platform.service.api.SystemUserService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ LoggerFactory.class })
public class HertzAbsoluteTimeAutoAdvancerTest {
	@InjectMocks
	private HertzAbsoluteTimeAutoAdvancer hertzAbsoluteTimeAutoAdvancer;
	@Mock
	List<HistoryItem> wfHistory;
	@Mock
	WorkflowSession wfSession;
	@Mock
	Workflow wf;
	@Mock
	Iterator<HistoryItem> historyIterator;
	@Mock
	MetaDataMap metaDataMap;
	@Mock
	HistoryItem item;

	@Mock
	Route route;
	@Mock
	WorkflowNode wfnode;

	@Mock
	WorkflowTransition transition;
	private ResourceResolver mockResResolver;
	private Session mockSession;
	Class<?> servclass;
	Field fields[];
	Dictionary<?, ?> properties;
	Method methods[];

	private WorkItem mockWorkItem;

	private WorkflowSession mockWorkflowSession;

	private MetaDataMap mockMetaDataMap;
	private WorkflowData mockWorkflowData;

	@Before
	public void setUp() throws Exception {
		hertzAbsoluteTimeAutoAdvancer = new HertzAbsoluteTimeAutoAdvancer();
		MockitoAnnotations.initMocks(this);
		mockResResolver = Mockito.mock(ResourceResolver.class);
		mockSession = Mockito.mock(Session.class);

		PowerMockito.when(mockResResolver.adaptTo(Session.class)).thenReturn(mockSession);
		servclass = hertzAbsoluteTimeAutoAdvancer.getClass();
		fields = servclass.getDeclaredFields();

	}

	@Test
	public void testExecute() throws Exception {
		mockWorkItem = Mockito.mock(WorkItem.class);
		mockWorkflowSession = Mockito.mock(WorkflowSession.class);
		mockMetaDataMap = Mockito.mock(MetaDataMap.class);
		mockWorkflowData = Mockito.mock(WorkflowData.class);
		Mockito.when(mockWorkItem.getWorkflow()).thenReturn(wf);
		Mockito.when(mockWorkItem.getWorkflowData()).thenReturn(mockWorkflowData);
		Mockito.when(mockWorkflowSession.getHistory(wf)).thenReturn(wfHistory);

		List<Route> routes = PowerMockito.mock(ArrayList.class);
		routes = new ArrayList<>();
		routes.add(route);
		Mockito.when(mockWorkflowSession.getRoutes(Mockito.any(WorkItem.class))).thenReturn(routes);
		Mockito.when(route.hasDefault()).thenReturn(true);
		Mockito.when(mockWorkItem.getNode()).thenReturn(wfnode);
		Mockito.when(wfnode.getTitle()).thenReturn("title");

		Mockito.when(wfHistory.isEmpty()).thenReturn(false);
		Mockito.when(historyIterator.hasNext()).thenReturn(true, false);
		Mockito.when(historyIterator.next()).thenReturn(item);
		Mockito.when(wfHistory.iterator()).thenReturn(historyIterator);
		Mockito.when(item.getWorkItem()).thenReturn(mockWorkItem);

		List<WorkflowTransition> transitions = PowerMockito.mock(ArrayList.class);
		transitions = new ArrayList<>();
		transitions.add(transition);
		Mockito.when(route.getDestinations()).thenReturn(transitions);
		Mockito.when(transition.getTo()).thenReturn(wfnode);

		Mockito.when(mockWorkItem.getMetaDataMap()).thenReturn(metaDataMap);
		Mockito.when(mockWorkflowData.getMetaDataMap()).thenReturn(metaDataMap);
		Mockito.when(metaDataMap.get(Mockito.anyString())).thenReturn("2017-02-13T22:08:00.000+05:30");
		Mockito.when(metaDataMap.put(Mockito.anyString(), Mockito.eq(10922042)))
				.thenReturn("2017-02-13T22:08:00.000+05:30");

		hertzAbsoluteTimeAutoAdvancer.execute(mockWorkItem, mockWorkflowSession, mockMetaDataMap);

		Mockito.when(route.hasDefault()).thenReturn(false);
		hertzAbsoluteTimeAutoAdvancer.execute(mockWorkItem, mockWorkflowSession, mockMetaDataMap);

		Mockito.when(metaDataMap.put(Mockito.anyString(), Mockito.any(Long.class))).thenReturn(1233341234);
		hertzAbsoluteTimeAutoAdvancer.getTimeoutDate(mockWorkItem);
		Mockito.when(metaDataMap.put(Mockito.anyString(), Mockito.any(Long.class))).thenReturn(null);
		hertzAbsoluteTimeAutoAdvancer.getTimeoutDate(mockWorkItem);

	}
}
