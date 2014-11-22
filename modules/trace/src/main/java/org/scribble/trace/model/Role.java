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
 * This class represents a role associated with a message trace
 *
 */
public class Role {

	private String _name;
	private RoleSimulator _simulator;
	
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
	 * @return The role
	 */
	public Role setName(String name) {
		_name = name;
		return (this);
	}
	
	/**
	 * This method returns the optional simulator for the role.
	 * 
	 * @return The optional simulator for the role
	 */
	public RoleSimulator getSimulator() {
		return (_simulator);
	}
	
	/**
	 * This method sets the optional simulator for the role.
	 * 
	 * @param description The optional simulator for the role
	 * @return The role
	 */
	public Role setSimulator(RoleSimulator simulator) {
		_simulator = simulator;
		return (this);
	}
	
    /**
     * {@inheritDoc}
     */
    public String toString() {
    	StringBuffer buf=new StringBuffer();
    	toText(buf, 0);
    	
    	return (buf.toString());
    }
    
    /**
     * {@inheritDoc}
     */
    public void toText(StringBuffer buf, int level) {
		buf.append("role ");
		
    	if (_name != null && _name.trim().length() > 0) {    		
    		buf.append(_name.trim());
    	}
    	
    	if (_simulator != null) {
    		_simulator.toText(buf);
    	}
    	
		buf.append(";\n");
    }

}
