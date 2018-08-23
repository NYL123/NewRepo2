package com.hertz.digital.platform.replication.dispatcher;

import aQute.bnd.annotation.ProviderType;
import com.day.cq.replication.Agent;
import com.day.cq.replication.AgentConfig;
import com.day.cq.replication.AgentFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ValueMap;

/**
 * Replication Agent Filter used to identify Flush agents.
 */
@ProviderType
public class HertzDispatcherFlushFilter implements AgentFilter {

	/**
	 * All: All Enabled Dispatcher Flush Agents. Hierarchical: "Normal" flush
	 * invalidation that effects entire content hierarchies. 
	 */
	public static enum HertzFlushType {
		All, Hierarchical, ResourceOnly
	}

	public static final HertzDispatcherFlushFilter ALL = new HertzDispatcherFlushFilter(HertzFlushType.All);
	public static final HertzDispatcherFlushFilter HIERARCHICAL = new HertzDispatcherFlushFilter(
			HertzFlushType.Hierarchical);

	private static final String SERIALIZATION_TYPE = "flush";
	private static final String HTTP = "http://";
	private static final String HTTPS = "https://";
	private static final String CQ_ACTION_HEADER = "CQ-Action:";
	private static final String CQ_SCOPE_ACTION_HEADER = "CQ-Action-Scope: ResourceOnly";

	private final HertzFlushType hertzFlushType;

	/**
	 * Default constructor; Same as: new DispatcherFlushFilter(FlushType.All);.
	 */
	public HertzDispatcherFlushFilter() {
		this.hertzFlushType = HertzFlushType.All;
	}

	/**
	 * Targets a set of Dispatcher Flush agents based on the parameter
	 * flushType.
	 *
	 * @param flushType
	 *            The type of Flush agents this Agent should target
	 */
	public HertzDispatcherFlushFilter(final HertzFlushType flushType) {
		this.hertzFlushType = flushType;
	}

	/**
	 * Checks if the @agent is considered an active Flush agent (Serialization
	 * Type ~> Flush and is enabled).
	 *
	 * @param agent
	 *            the agent to test test
	 * @return true is is considered an enabled Flush agent
	 */
	@Override
	public final boolean isIncluded(final Agent agent) {
		if (HertzFlushType.All.equals(this.hertzFlushType)) {
			return this.isIncludedCommon(agent);
		} else if (HertzFlushType.Hierarchical.equals(this.hertzFlushType)) {
			return this.isIncludedHierarchical(agent);
		}
		return false;
	}

	/**
	 * Returns the Dispatcher FlushType this filter was created with.
	 * 
	 * @return this filter's dispatcher flushType
	 */
	public final HertzFlushType getFlushType() {
		return this.hertzFlushType;
	}

	private boolean isIncludedCommon(final Agent agent) {
		return this.isFlushingAgent(agent) && this.isDispatcherTransportURI(agent) && this.isDispatcherHeaders(agent)
				&& this.isEnabled(agent);
	}

	private boolean isIncludedHierarchical(final Agent agent) {
		return this.isIncludedCommon(agent) && !this.isResourceOnly(agent);
	}

	/**
	 * Checks if the agent is enabled.
	 *
	 * @param agent
	 *            Agent to check
	 * @return true if the agent is enabled
	 */
	private boolean isEnabled(final Agent agent) {
		return agent.isEnabled();
	}

	/**
	 * Checks if the agent has a "flush" serialization type.
	 *
	 * @param agent
	 *            Agent to check
	 * @return true if the Agent's serialization type is "flush"
	 */
	private boolean isFlushingAgent(final Agent agent) {
		return StringUtils.equals(SERIALIZATION_TYPE, agent.getConfiguration().getSerializationType());
	}

	/**
	 * Checks if the agent has a valid transport URI set.
	 *
	 * @param agent
	 *            Agent to check
	 * @return true if the Agent's transport URI is in the proper form
	 */
	private boolean isDispatcherTransportURI(final Agent agent) {
		final String transportURI = agent.getConfiguration().getTransportURI();

		return (StringUtils.startsWith(transportURI, HTTP) || StringUtils.startsWith(transportURI, HTTPS));
	}

	/**
	 * Checks if the agent has a valid dispatcher headers.
	 *
	 * @param agent
	 *            Agent to check
	 * @return true if the Agent's headers contain the proper values
	 */
	private boolean isDispatcherHeaders(final Agent agent) {
		final ValueMap properties = agent.getConfiguration().getProperties();
		final String[] headers = properties.get(AgentConfig.PROTOCOL_HTTP_HEADERS, new String[] {});

		for (final String header : headers) {
			if (StringUtils.startsWith(header, CQ_ACTION_HEADER)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if the agent has valid CQ-Action-Scope: ResourceOnly header.
	 *
	 * @param agent
	 *            Agent to check
	 * @return true if the Agent's headers contain the expected values
	 */
	private boolean isResourceOnly(final Agent agent) {
		final ValueMap properties = agent.getConfiguration().getProperties();
		final String[] headers = properties.get(AgentConfig.PROTOCOL_HTTP_HEADERS, new String[] {});

		for (final String header : headers) {
			if (StringUtils.equals(header, CQ_SCOPE_ACTION_HEADER)) {
				return true;
			}
		}

		return false;
	}
}
