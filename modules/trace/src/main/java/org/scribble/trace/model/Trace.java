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
 * This class represents a message trace.
 *
 */
public class Trace {

	private String _name;
	private String _description;
	private java.util.List<Step> _steps=new java.util.ArrayList<Step>();
	private java.util.List<Simulation> _simulations=new java.util.ArrayList<Simulation>();
	
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
	public Trace setName(String name) {
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
	public Trace setDescription(String description) {
		_description = description;
		return (this);
	}
	
	/**
	 * This method returns the trace steps.
	 * 
	 * @return The trace steps
	 */
	public java.util.List<Step> getSteps() {
		return (_steps);
	}
	
	/**
	 * This method sets the trace steps.
	 * 
	 * @param steps The trace steps
	 * @return The trace
	 */
	public Trace setSteps(java.util.List<Step> steps) {
		_steps = steps;
		return (this);
	}

	/**
	 * This method returns the simulations.
	 * 
	 * @return The simulations
	 */
	public java.util.List<Simulation> getSimulations() {
		return (_simulations);
	}
	
	/**
	 * This method sets the simulations.
	 * 
	 * @param simulations The simulations
	 * @return The trace
	 */
	public Trace setSimulations(java.util.List<Simulation> simulations) {
		_simulations = simulations;
		return (this);
	}

}
