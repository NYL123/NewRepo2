package com.hertz.digital.platform.workflows;

import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
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
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.service.api.SystemUserService;

import junitx.util.PrivateAccessor;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ LoggerFactory.class })
public class ReviewerParticipantChooseTest {
	@InjectMocks
	private ReviewerParticipantChooser reviewerParticipantChooser;
	@Mock
	private SystemUserService systemService;
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
	HertzConfigFactory hConfigFactory;
	@Mock
	HistoryItem item;
	private ResourceResolver mockResResolver;
	private Session mockSession;
	Class<?> servclass;
	Field fields[];
	Dictionary<?, ?> properties;
	Method methods[];
	@Mock
	ValueMap valueMap;
	private WorkItem mockWorkItem;

	private WorkflowSession mockWorkflowSession;

	private MetaDataMap mockMetaDataMap;
	private WorkflowData mockWorkflowData;
	@Mock
	Resource resource;
	
	@Mock
	Iterator mockIterator;
	@Mock 
	Page page;
	
	@Before
	public void setUp() throws Exception {
		reviewerParticipantChooser = new ReviewerParticipantChooser();
		MockitoAnnotations.initMocks(this);
		mockResResolver = Mockito.mock(ResourceResolver.class);
		mockSession = Mockito.mock(Session.class);
		
		PowerMockito.when(systemService.getServiceResourceResolver()).thenReturn(mockResResolver);
		PowerMockito.when(mockResResolver.adaptTo(Session.class)).thenReturn(mockSession);
		servclass = reviewerParticipantChooser.getClass();
		fields = servclass.getDeclaredFields();
		PrivateAccessor.setField(reviewerParticipantChooser, "hConfigFactory", hConfigFactory);
		String contentApprover = "hertz-content-approver";
		String damApprover = "hertz-dam-approver";
		when(hConfigFactory.getStringPropertyValue(Mockito.eq("hertz.default.content.approver.groupname"))).thenReturn(contentApprover);
		when(hConfigFactory.getStringPropertyValue(Mockito.eq("hertz.default.dam.approver.groupname"))).thenReturn(damApprover);
		
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
		Mockito.when(mockWorkflowData.getPayload()).thenReturn("/content/hertz-rac/rac-web/us/en/abc");
		
		Mockito.when(mockWorkItem.getMetaDataMap()).thenReturn(metaDataMap);
		Mockito.when(metaDataMap.getOrDefault(Mockito.anyString(), Mockito.anyString())).thenReturn("admin");
		Mockito.when(mockWorkflowData.getMetaDataMap()).thenReturn(metaDataMap);
		
		String participant = reviewerParticipantChooser.getParticipant(mockWorkItem, mockWorkflowSession, mockMetaDataMap);
		
		when(hConfigFactory.getStringPropertyValue(Mockito.eq("hertz.pathtogroup.mappings.path"))).thenReturn("/content/data/path-to-group-mappings");
		Mockito.when(mockResResolver.getResource(Mockito.anyString())).thenReturn(resource);
		
		Mockito.when(resource.adaptTo(Page.class)).thenReturn(page);
		Mockito.when(page.getContentResource()).thenReturn(resource);
		Mockito.when(resource.getChild(Mockito.any(String.class))).thenReturn(resource);
		Mockito.when(resource.listChildren()).thenReturn(mockIterator);
		Mockito.when(mockIterator.hasNext()).thenReturn(true,false);
		Mockito.when(mockIterator.next()).thenReturn(resource);
		Mockito.when(resource.isResourceType("hertz/components/content/pathtogroup")).thenReturn(true);
		Mockito.when(resource.getValueMap()).thenReturn(valueMap);
		Mockito.when(valueMap.get("sitepath")).thenReturn("/content/hertz-rac/rac-web/us/en");
		Mockito.when(valueMap.get("groupname")).thenReturn("hertz-group-name");
		participant = reviewerParticipantChooser.getParticipant(mockWorkItem, mockWorkflowSession, mockMetaDataMap);
		Assert.assertNotNull(participant);
		
		Mockito.when(mockWorkflowData.getPayload()).thenReturn("/content/dam/hertz-rac/");
		 participant = reviewerParticipantChooser.getParticipant(mockWorkItem, mockWorkflowSession, mockMetaDataMap);
	}
				
}
