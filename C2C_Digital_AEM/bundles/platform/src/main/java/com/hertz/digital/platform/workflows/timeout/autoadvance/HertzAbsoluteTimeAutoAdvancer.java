package com.hertz.digital.platform.workflows.timeout.autoadvance;

import java.util.List;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.Route;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.job.AbsoluteTimeoutHandler;
import com.day.cq.workflow.metadata.MetaDataMap;
import com.day.cq.workflow.model.WorkflowTransition;
import com.day.cq.workflow.timeout.autoadvance.AutoAdvancer;

/**
 * Custom Hertz Auto advance process for scheduled activation.
 * 
 * @author n.kumar.singhal
 *
 */
@Component(metatype = false)
@Service({ WorkflowProcess.class, AbsoluteTimeoutHandler.class })
@Properties({
		@org.apache.felix.scr.annotations.Property(name = "service.description", value = {
				"Hertz - Auto Advance Process" }),
		@org.apache.felix.scr.annotations.Property(name = "process.label", value = {
				"Hertz - Absolute Time Auto Advancer" }) })
public class HertzAbsoluteTimeAutoAdvancer extends AutoAdvancer implements AbsoluteTimeoutHandler {
	protected final Logger log = LoggerFactory.getLogger(HertzAbsoluteTimeAutoAdvancer.class);

	/**
	 * Default Constructors
	 */
	public HertzAbsoluteTimeAutoAdvancer() {
		super();
	}

	public void execute(WorkItem item, WorkflowSession session, MetaDataMap args) throws WorkflowException {
		try {
			log.debug("inside execute of time handler.");
			boolean advanced = false;
			List<Route> routes = session.getRoutes(item);
			for (Route route : routes) {
				if (route.hasDefault()) {
					String fromTitle = item.getNode().getTitle();
					String toTitle = ((WorkflowTransition) route.getDestinations().get(0)).getTo().getTitle();

					session.complete(item, route);
					this.log.debug(item.getId() + " advanced from " + fromTitle + " to " + toTitle);

					advanced = true;
				}
			}
			if (!advanced) {
				session.complete(item, (Route) routes.get(0));

				String fromTitle = item.getNode().getTitle();
				String toTitle = ((WorkflowTransition) ((Route) routes.get(0)).getDestinations().get(0)).getTo()
						.getTitle();

				this.log.debug(item.getId() + " advanced from " + fromTitle + " to " + toTitle);
			}
		} catch (WorkflowException e) {
			this.log.error("Could not advance workflow.", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.day.cq.workflow.job.AbsoluteTimeoutHandler#getTimeoutDate(com.day.cq.
	 * workflow.exec.WorkItem)
	 */
	public long getTimeoutDate(WorkItem workItem) {
		Long dateValue = -1L;
		log.debug("The activation/de-activation time is set to :- {}",
				(Long) workItem.getWorkflowData().getMetaDataMap().get("absoluteTime", Long.class));
		Long date = (Long) workItem.getWorkflowData().getMetaDataMap().get("absoluteTime", Long.class);
		if (date != null) {
			dateValue = date.longValue();
		}
		return dateValue;
	}
}
