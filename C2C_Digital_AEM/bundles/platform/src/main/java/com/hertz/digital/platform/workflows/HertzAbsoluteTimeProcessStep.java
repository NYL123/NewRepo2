package com.hertz.digital.platform.workflows;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.HistoryItem;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.Workflow;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.hertz.digital.platform.utils.HertzUtils;

@Component
@Service
@Properties({
		@Property(name = Constants.SERVICE_DESCRIPTION, value = "Custom Process step to process the absolute time for the next step"),
		@Property(name = Constants.SERVICE_VENDOR, value = "Accenture"),
		@Property(name = "process.label", value = "Hertz Absolute Time Processor") })
public class HertzAbsoluteTimeProcessStep implements WorkflowProcess {

	protected final Logger log = LoggerFactory.getLogger(HertzAbsoluteTimeProcessStep.class);
	private final String ABSOLUTE_DATE ="absoluteDate";

	/**
	 * Default Constructors
	 */
	public HertzAbsoluteTimeProcessStep() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.adobe.granite.workflow.exec.WorkflowProcess#execute(com.adobe.granite
	 * .workflow.exec.WorkItem, com.adobe.granite.workflow.WorkflowSession,
	 * com.adobe.granite.workflow.metadata.MetaDataMap)
	 */
	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metadataMap)
			throws WorkflowException {
		log.debug("Entering execute method of HertzAbsoluteTimeProcessStep");
		String savedDate = StringUtils.EMPTY;
		Workflow wf = workItem.getWorkflow();
		List<HistoryItem> wfHistory = workflowSession.getHistory(wf);
		if (!wfHistory.isEmpty()) {
			Iterator<HistoryItem> historyIterator = wfHistory.iterator();
			while (historyIterator.hasNext()) {
				HistoryItem item = historyIterator.next();
				if (item.getWorkItem().getMetaDataMap().get(ABSOLUTE_DATE) != null) {
					savedDate = (String) item.getWorkItem().getMetaDataMap().get(ABSOLUTE_DATE);
				}
			}
		}
		// search in workflow metadata.
		if (StringUtils.isEmpty(savedDate)) {
			if (null != wf.getMetaDataMap().get(ABSOLUTE_DATE)) {
				savedDate = wf.getMetaDataMap().get(ABSOLUTE_DATE).toString();
			}
			log.debug("property found on workflow metadata {}", savedDate);
		}
		if (StringUtils.isNotEmpty(savedDate)) {
			log.debug("Setting the found property on required location in milliseconds format");
			workItem.getWorkflowData().getMetaDataMap().put("absoluteTime",
					HertzUtils.getDateInMillis(savedDate, "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));

		} else {
			/*
			 * Case where the activation date is left blank intentionally by the
			 * executor. Supposedly for immediate activation ? Would still put
			 * workflow-exec-process in the replicateBy property.
			 * 
			 * Node Date Format :- 2017-05-05T11:18:00.000+05:30
			 */

			log.debug(
					"Scheduled Time property found blank. Falling back, proceeding with setting it to current date and time for immediate activation. :- {}",
					Calendar.getInstance().getTime().toString());
			workItem.getWorkflowData().getMetaDataMap().put("absoluteTime", HertzUtils
					.getDateInMillis(Calendar.getInstance().getTime().toString(), "EEE MMM dd HH:mm:ss z yyyy"));
		}
	}

}
