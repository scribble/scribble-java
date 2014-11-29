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
package org.scribble.monitor.rest;

/**
 * This class represents a message being sent or received by the
 * role being monitored.
 *
 */
public class RESTMessage {
	
	public static final String	METHOD_GET="GET";
	public static final String	METHOD_PUT="PUT";
	public static final String	METHOD_POST="POST";
	public static final String	METHOD_DELETE="DELETE";
	
	private String _method;
	private java.util.List<String> _path=new java.util.ArrayList<String>();
	private java.util.Map<String,String> _queryParameters=new java.util.HashMap<String,String>();
	private String _content;

	/**
	 * This method returns the method.
	 * 
	 * @return The method
	 */
	public String getMethod() {
		return (_method);
	}
	
	/**
	 * This method sets the method.
	 * 
	 * @param method The method
	 */
	public void setMethod(String method) {
		_method = method;
	}
	
	/**
	 * This method returns the path.
	 * 
	 * @return The path
	 */
	public java.util.List<String> getPath() {
		return (_path);
	}
	
	/**
	 * This method sets the path.
	 * 
	 * @param path The path
	 */
	public void setPath(java.util.List<String> path) {
		_path = path;
	}
	
	/**
	 * This method returns the path.
	 * 
	 * @return The path
	 */
	public java.util.Map<String,String> getQueryParameters() {
		return (_queryParameters);
	}
	
	/**
	 * This method sets the path.
	 * 
	 * @param path The path
	 */
	public void setQueryParameters(java.util.Map<String,String> params) {
		_queryParameters = params;
	}
	
	/**
	 * This method returns the content.
	 * 
	 * @return The content
	 */
	public String getContent() {
		return (_content);
	}
	
	/**
	 * This method sets the content.
	 * 
	 * @param content The content
	 */
	public void setContent(String content) {
		_content = content;
	}
}
