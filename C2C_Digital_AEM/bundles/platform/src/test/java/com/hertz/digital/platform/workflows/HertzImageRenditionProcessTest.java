package com.hertz.digital.platform.workflows;

import static org.powermock.api.mockito.PowerMockito.when;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.jcr.Session;
import javax.print.DocFlavor;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.jcr.resource.JcrResourceResolverFactory;
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
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.cq.dam.api.handler.AssetHandler;
import com.day.cq.dam.api.handler.store.AssetStore;
import com.day.cq.dam.commons.process.AbstractAssetWorkflowProcess;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.HistoryItem;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.Workflow;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.metadata.MetaDataMap;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.service.api.SystemUserService;

import junit.framework.Assert;
import junitx.util.PrivateAccessor;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoggerFactory.class, ImageIO.class,AbstractAssetWorkflowProcess.class })
public class HertzImageRenditionProcessTest {
	@InjectMocks
	private HertzImageRenditionProcess hertzImageRenditionProcess;
	
	
	@Mock
	List<HistoryItem> wfHistory;

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
	@Mock
	Map<String, Object> metadataMap = new HashMap<>();
	@Mock
	Asset asset;
	
	@Mock
    private JcrResourceResolverFactory jcrFactory;
	
	
	private WorkItem mockWorkItem;

	private WorkflowSession mockWorkflowSession;

	@Mock
	Rendition rendition;

	@Mock
	AssetHandler assetHandler;
	@Mock
	MetaDataMap mockMetaDataMap;
	private WorkflowData mockWorkflowData;
	private static final String DAM_PHYSICALHEIGHTININCHES = "dam:Physicalheightininches";
	private static final String DAM_PHYSICALWIDTHININCHES = "dam:Physicalwidthininches";
	private static final String MAXWIDTH = "MAXWIDTH";
	private static final String THRESHOLDWIDTH = "THRESHOLDWIDTH";
	@Mock
	Resource resource;
	@Mock
	Session session;

	@Mock
	AssetStore assetStore;
	@Mock
	Dimension dimension;
	@Mock
	BufferedImage image;
	@Mock
	ByteArrayOutputStream out;
	@Mock
	MetaDataMap args;
	
	@Mock
	JcrResourceResolverFactory factory;

	@Before
	public void setUp() throws Exception {
		hertzImageRenditionProcess = new HertzImageRenditionProcess();
		MockitoAnnotations.initMocks(this);
		mockResResolver = Mockito.mock(ResourceResolver.class);
		mockSession = Mockito.mock(Session.class);
		PowerMockito.when(mockResResolver.adaptTo(Session.class)).thenReturn(mockSession);
		servclass = hertzImageRenditionProcess.getClass();
		fields = servclass.getDeclaredFields();
		PrivateAccessor.setField(hertzImageRenditionProcess, "hertzConfigFactory", hConfigFactory);
		PrivateAccessor.setField(hertzImageRenditionProcess, "assetStore", assetStore);
		when(jcrFactory.getResourceResolver(session)).thenReturn(mockResResolver);
		String extension = "png";
		// String damApprover = "hertz-dam-approver";
		when(hConfigFactory.getStringPropertyValue(Mockito.eq("hertz.renditions.extension"))).thenReturn(extension);
		// when(hConfigFactory.getStringPropertyValue(Mockito.eq("hertz.default.dam.approver.groupname"))).thenReturn(damApprover);
		
	}

	@Test
	public void testExecute() throws Throwable {
		
		mockWorkItem = Mockito.mock(WorkItem.class);
		mockWorkflowSession = Mockito.mock(WorkflowSession.class);
		// mockMetaDataMap = Mockito.mock(MetaDataMap.class);
		mockWorkflowData = Mockito.mock(WorkflowData.class);
		Asset asset=PowerMockito.mock(Asset.class);
		//AbstractAssetWorkflowProcess process=PowerMockito.mock(AbstractAssetWorkflowProcess.class,Mockito.CALLS_REAL_METHODS);
		ResourceResolver resolver=PowerMockito.mock(ResourceResolver.class);
		Mockito.when(mockWorkItem.getWorkflow()).thenReturn(wf);
		Mockito.when(mockWorkItem.getWorkflowData()).thenReturn(mockWorkflowData);
		Mockito.when(mockWorkflowSession.getHistory(wf)).thenReturn(wfHistory);
		Mockito.when(wfHistory.isEmpty()).thenReturn(false);
		Mockito.when(historyIterator.hasNext()).thenReturn(true, false);
		Mockito.when(historyIterator.next()).thenReturn(item);
		Mockito.when(wfHistory.iterator()).thenReturn(historyIterator);
		Mockito.when(item.getWorkItem()).thenReturn(mockWorkItem);
		Mockito.when(mockWorkflowData.getPayload()).thenReturn("/content/hertz-rac/rac-web/us/en/abc.png");
		Mockito.when(mockWorkflowData.getPayloadType()).thenReturn("JCR_PATH1");
		Mockito.when(mockWorkItem.getMetaDataMap()).thenReturn(metaDataMap);
		Mockito.when(metaDataMap.getOrDefault(Mockito.anyString(), Mockito.anyString())).thenReturn("admin");
		Mockito.when(mockWorkflowData.getMetaDataMap()).thenReturn(metaDataMap);
		// mockMetaDataMap = Mockito.mock(MetaDataMap.class);
		when(mockMetaDataMap.get(Mockito.eq(THRESHOLDWIDTH), Mockito.any(String.class))).thenReturn("1536");
		when(mockMetaDataMap.get(Mockito.eq(MAXWIDTH), Mockito.any(String.class))).thenReturn("3072");
		when(mockMetaDataMap.get(Mockito.eq("MIME_TYPE"), Mockito.any(String.class))).thenReturn("image/png");
		when(mockMetaDataMap.get(Mockito.eq("QUALITY"), Mockito.any(String.class))).thenReturn("96");
		// metadataMap = Mockito.mock(ValueMap.class);
		metadataMap.put(DAM_PHYSICALHEIGHTININCHES, "16");
		metadataMap.put(DAM_PHYSICALWIDTHININCHES, "8");
		Mockito.when(asset.getMetadata()).thenReturn(metadataMap);
		Mockito.when(mockWorkflowSession.getSession()).thenReturn(session);
		
		when(assetHandler.getImage(Mockito.any(Rendition.class), Mockito.any(Dimension.class))).thenReturn(image);

		PowerMockito.mockStatic(ImageIO.class);
		PowerMockito.when(ImageIO.write(Mockito.any(BufferedImage.class), Mockito.anyString(),
				Mockito.any(ByteArrayOutputStream.class))).thenReturn(true);
		// hertzImageRenditionProcess.execute(mockWorkItem, mockWorkflowSession,
		// mockMetaDataMap);
		when(asset.getOriginal()).thenReturn(rendition);
		when(rendition.getPath()).thenReturn("path");
		when(image.getWidth()).thenReturn(900);
		when(image.getHeight()).thenReturn(900);
		
		hertzImageRenditionProcess.writeDependentRenditionsToRepository(1536.0, 3072.0, "image/png", mockSession, asset,
				"png", 16.0, 9.0, 96.0, 900, rendition, assetHandler);
		hertzImageRenditionProcess.writeDependentRenditionsToRepository(1536.0, 3072.0, "image/png", mockSession, asset,
				"png", 16.0, 9.0, 96.0, 1700, rendition, assetHandler);

		when(args.get(Mockito.anyString(), Mockito.anyString())).thenReturn("100.0");
		when(valueMap.get(Mockito.anyString(), Mockito.anyInt())).thenReturn(96);
		hertzImageRenditionProcess.getQuality("image/gif", valueMap);
		when(valueMap.get(Mockito.anyString(), Mockito.anyInt())).thenReturn(101);
		hertzImageRenditionProcess.getQuality("image/gif", valueMap);
		when(valueMap.get(Mockito.anyString(), Mockito.anyInt())).thenReturn(101);
	
		when(factory.getResourceResolver(mockSession)).thenReturn(mockResResolver);
		hertzImageRenditionProcess.execute(mockWorkItem, mockWorkflowSession, args);

	}
	
	@Test
	public final void testGetClamped(){
		MetaDataMap map=Mockito.mock(MetaDataMap.class);
		when(map.get(Mockito.anyString(),Double.valueOf(Mockito.anyDouble()))).thenReturn(new Double(100.0));
		Assert.assertTrue(hertzImageRenditionProcess.getClamped(map, "MAX_WIDTH", new Double(372), new Double(0), new Double(100))==100.0);
	}
}
