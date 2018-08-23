package com.hertz.digital.platform.workflows;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.ParticipantStepChooser;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.service.api.SystemUserService;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * Implementation for Activation workflow participant chooser.
 */
@Component(enabled = true, metatype = true, immediate = true, label = "Hertz - Reviewer Participant step Chooser", description = "Includes logic for retriving reviewer based on path of payload")
@Service(ParticipantStepChooser.class)
@Properties({ @Property(name = Constants.SERVICE_DESCRIPTION, value = "Reviewer participant chooser"),
		@Property(name = ParticipantStepChooser.SERVICE_PROPERTY_LABEL, value = "Hertz Reviewer participant chooser") })
public class ReviewerParticipantChooser implements ParticipantStepChooser {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReviewerParticipantChooser.class);

	/**
	 * Declaring default constructor
	 */
	public ReviewerParticipantChooser() {
		super();
	}

	@Reference
	private transient SystemUserService systemService;

	@Reference
	private HertzConfigFactory hConfigFactory;

	/**
	 * Returns participant for workflow.
	 */
	@Override
	public String getParticipant(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metadataMap)
			throws WorkflowException {
		LOGGER.debug("Entering getParticipant method of ReviewParticipantChooser");
		String participant = StringUtils.EMPTY;
		String mappingPagePath = (String) hConfigFactory.getStringPropertyValue("hertz.pathtogroup.mappings.path");
		String payloadPath = workItem.getWorkflowData().getPayload().toString();
		ResourceResolver resolver = systemService.getServiceResourceResolver();
		if (StringUtils.isNotEmpty(mappingPagePath)) {
			try {
				participant = HertzUtils.getGroupName(payloadPath,
						resolver.getResource(mappingPagePath));
				if(StringUtils.isEmpty(participant)){
					LOGGER.debug("There is something not rightly configured @ /content/admin/path-to-group-mapping for the given payload {}", payloadPath);
				} else {
					LOGGER.debug("Participant found as : {}", participant);
				}
			} finally{
				HertzUtils.closeResolverSession(resolver, null);
			}
		} else {
			LOGGER.debug("Error: the page to group mapping is empty or mis-configured. Please check the HertzConfigurationFactory.");
			
		}
		
		LOGGER.debug("The participant is found to be {} for path {}", participant,
				workItem.getWorkflowData().getPayload().toString());
		return participant;
	}
}
