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
	private String _author;
	private java.util.List<Role> _roles=new java.util.ArrayList<Role>();
	private java.util.List<Step> _steps=new java.util.ArrayList<Step>();
	
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
	 * This method returns the author of the trace.
	 * 
	 * @return The author of the trace
	 */
	public String getAuthor() {
		return (_author);
	}
	
	/**
	 * This method sets the author of the trace.
	 * 
	 * @param author The author of the trace
	 * @return The trace
	 */
	public Trace setAuthor(String author) {
		_author = author;
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
	 * This method returns the roles.
	 * 
	 * @return The roles
	 */
	public java.util.List<Role> getRoles() {
		return (_roles);
	}
	
	/**
	 * This method sets the roles.
	 * 
	 * @param simulations The roles
	 * @return The trace
	 */
	public Trace setRoles(java.util.List<Role> roles) {
		_roles = roles;
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
		buf.append("trace ");
		
    	if (_name != null && _name.trim().length() > 0) {    		
    		buf.append(_name.trim());
    	}
    	
    	if (_author != null && _author.trim().length() > 0) {
    		buf.append(" by "+_author.trim());
    	}
		
    	if (_description != null && _description.trim().length() > 0) {
    		buf.append(" shows "+_description.trim());
    	}
		
		buf.append(";\n\n");
    	
    	for (Role role : getRoles()) {
    		role.toText(buf, level);
    	}
    	
    	if (getRoles().size() > 0) {
    		buf.append("\n");
    	}
    	
    	for (Step step : getSteps()) {
    		step.toText(buf, level);
    	}
    	
    }

}
