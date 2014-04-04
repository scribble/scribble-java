/*
 * Copyright 2009-11 www.scribble.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.scribble.trace.model;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.scribble.trace.simulation.SimulatorContext;

/**
 * This class represents the base class from which all simulation actions
 * are derived.
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({@Type(value=MessageTransfer.class)})
public abstract class Step {

	private boolean _succeeds=true;
	
	/**
	 * This method returns whether the action should succeed.
	 * 
	 * @return Whether the action should succeed
	 */
	public boolean getSucceeds() {
		return (_succeeds);
	}
	
	/**
	 * This method sets the 'to' roles.
	 * 
	 * @param roles The 'to' roles
	 * @return The message transfer
	 */
	public Step setSucceeds(boolean succeeds) {
		_succeeds = succeeds;
		return (this);
	}

	/**
	 * This method simulates the step against the supplied
	 * role simulators, taking into account whether the step
	 * is expected to succeed.
	 * 
	 * @param context The context
	 * @param roleSimulators The role simulators
	 * @return Whether the simulation of the step was successfully
	 */
	public abstract boolean simulate(SimulatorContext context,
					java.util.Map<String,RoleSimulator> roleSimulators);
	
}
