/*
 * Copyright 2009-14 www.scribble.org
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

/**
 * This class represents a simulation configuration associated with a message trace
 *
 */
public class Simulation {

	private String _name;
	private String _description;
	private java.util.Map<String,RoleSimulator> _simulators=new java.util.HashMap<String,RoleSimulator>();
	
	/**
	 * This method returns the name of the trace.
	 * 
	 * @return The name of the trace
	 */
	public String getName() {
		return (_name);
	}
	
	/**
	 * This method sets the name of the trace.
	 * 
	 * @param name The name of the trace
	 * @return The trace
	 */
	public Simulation setName(String name) {
		_name = name;
		return (this);
	}
	
	/**
	 * This method returns the description of the trace.
	 * 
	 * @return The description of the trace
	 */
	public String getDescription() {
		return (_description);
	}
	
	/**
	 * This method sets the description of the trace.
	 * 
	 * @param description The description of the trace
	 * @return The trace
	 */
	public Simulation setDescription(String description) {
		_description = description;
		return (this);
	}
	
	/**
	 * This method returns the role simulators.
	 * 
	 * @return The role simulators
	 */
	public java.util.Map<String,RoleSimulator> getRoleSimulators() {
		return (_simulators);
	}
	
	/**
	 * This method sets the role simulators.
	 * 
	 * @param simulators The role simulators
	 * @return The simulation script
	 */
	public Simulation setRoleSimulators(java.util.Map<String,RoleSimulator> simulators) {
		_simulators = simulators;
		return (this);
	}

}
