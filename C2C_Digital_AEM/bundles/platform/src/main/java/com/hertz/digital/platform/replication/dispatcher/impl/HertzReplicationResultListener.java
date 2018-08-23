package com.hertz.digital.platform.replication.dispatcher.impl;

import java.util.HashMap;
import java.util.Map;

import com.day.cq.replication.Agent;
import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationListener;
import com.day.cq.replication.ReplicationLog.Level;
import com.day.cq.replication.ReplicationResult;

/**
 * Replication Listener that stores replication results for a series of agents.
 */
public class HertzReplicationResultListener implements ReplicationListener {

	public HertzReplicationResultListener() {
		// Default constructor.
	}

	/**
	 * Variable to hold the replication results.
	 */
	private final Map<Agent, ReplicationResult> results = new HashMap<Agent, ReplicationResult>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.day.cq.replication.ReplicationListener#onStart(com.day.cq.replication
	 * .Agent, com.day.cq.replication.ReplicationAction)
	 */
	@Override
	public void onStart(Agent agent, ReplicationAction action) {
		// Not Required. If needed, implement in future.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.day.cq.replication.ReplicationListener#onMessage(com.day.cq.
	 * replication.ReplicationLog.Level, java.lang.String)
	 */
	@Override
	public void onMessage(Level level, String message) {
		// Not Required. If needed, implement in future.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.day.cq.replication.ReplicationListener#onEnd(com.day.cq.replication.
	 * Agent, com.day.cq.replication.ReplicationAction,
	 * com.day.cq.replication.ReplicationResult)
	 */
	@Override
	public void onEnd(Agent agent, ReplicationAction action, ReplicationResult result) {
		// Not Required. If needed, implement in future.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.day.cq.replication.ReplicationListener#onError(com.day.cq.replication
	 * .Agent, com.day.cq.replication.ReplicationAction, java.lang.Exception)
	 */
	@Override
	public void onError(Agent agent, ReplicationAction action, Exception error) {
		// Not Required. If needed, implement in future.
	}

	/**
	 * Gets the results of the Replication operation.
	 *
	 * @return the Mapped results between the Agent and ReplicationResult
	 */
	public final Map<Agent, ReplicationResult> getResults() {
		return this.results;
	}
}
