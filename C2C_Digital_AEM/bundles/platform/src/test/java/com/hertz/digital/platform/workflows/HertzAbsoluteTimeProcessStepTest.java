package com.hertz.digital.platform.workflows;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.HistoryItem;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.Workflow;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ LoggerFactory.class })
public class HertzAbsoluteTimeProcessStepTest {
	@InjectMocks
	private HertzAbsoluteTimeProcessStep hertzAbsoluteTimeProcessStep;
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
		hertzAbsoluteTimeProcessStep = new HertzAbsoluteTimeProcessStep();
		MockitoAnnotations.initMocks(this);
		mockResResolver = Mockito.mock(ResourceResolver.class);
		mockSession = Mockito.mock(Session.class);

		PowerMockito.when(mockResResolver.adaptTo(Session.class)).thenReturn(mockSession);
		servclass = hertzAbsoluteTimeProcessStep.getClass();
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
		Mockito.when(wfHistory.isEmpty()).thenReturn(false);
		Mockito.when(historyIterator.hasNext()).thenReturn(true, false);
		Mockito.when(historyIterator.next()).thenReturn(item);
		Mockito.when(wfHistory.iterator()).thenReturn(historyIterator);
		Mockito.when(item.getWorkItem()).thenReturn(mockWorkItem);

		Mockito.when(mockWorkItem.getMetaDataMap()).thenReturn(metaDataMap);
		Mockito.when(mockWorkflowData.getMetaDataMap()).thenReturn(metaDataMap);
		Mockito.when(wf.getMetaDataMap()).thenReturn(metaDataMap);
		Mockito.when(metaDataMap.get(Mockito.anyString())).thenReturn("2017-02-13T22:08:00.000+05:30");
		Mockito.when(metaDataMap.put(Mockito.anyString(), Mockito.eq(10922042)))
				.thenReturn("2017-02-13T22:08:00.000+05:30");

		hertzAbsoluteTimeProcessStep.execute(mockWorkItem, mockWorkflowSession, mockMetaDataMap);
		
		mockWorkItem = Mockito.mock(WorkItem.class);
		mockWorkflowSession = Mockito.mock(WorkflowSession.class);
		mockMetaDataMap = Mockito.mock(MetaDataMap.class);
		mockWorkflowData = Mockito.mock(WorkflowData.class);
		Mockito.when(mockWorkItem.getWorkflow()).thenReturn(wf);
		
		
		
		Mockito.when(mockWorkItem.getWorkflowData()).thenReturn(mockWorkflowData);
		Mockito.when(mockWorkflowSession.getHistory(wf)).thenReturn(wfHistory);
		Mockito.when(wfHistory.isEmpty()).thenReturn(false);
		Mockito.when(historyIterator.hasNext()).thenReturn(true, false);
		Mockito.when(historyIterator.next()).thenReturn(item);
		Mockito.when(wfHistory.iterator()).thenReturn(historyIterator);
		Mockito.when(item.getWorkItem()).thenReturn(mockWorkItem);

		Mockito.when(mockWorkItem.getMetaDataMap()).thenReturn(metaDataMap);
		Mockito.when(mockWorkflowData.getMetaDataMap()).thenReturn(metaDataMap);
		Mockito.when(wf.getMetaDataMap()).thenReturn(metaDataMap);
		Mockito.when(metaDataMap.get(Mockito.anyString())).thenReturn("");
		Mockito.when(metaDataMap.put(Mockito.anyString(), Mockito.eq(10922042)))
				.thenReturn("");

		hertzAbsoluteTimeProcessStep.execute(mockWorkItem, mockWorkflowSession, mockMetaDataMap);
	}
}
