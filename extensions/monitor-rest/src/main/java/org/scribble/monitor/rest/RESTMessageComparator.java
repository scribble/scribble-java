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
package org.scribble.monitor.rest;

import org.scribble.monitor.model.Annotation;
import org.scribble.monitor.model.MessageNode;
import org.scribble.monitor.model.Parameter;
import org.scribble.monitor.runtime.MessageComparator;

/**
 * This class provides the default message comparator implementation.
 *
 */
public class RESTMessageComparator implements MessageComparator {

	private String _method;
	private String[] _parts;
	private java.util.List<Parameter> _parameters;
	
	private boolean _evaluated=false;
	
	/**
	 * This is the constructor to initialize the message comparator.
	 * 
	 * @param node The message node
	 */
	public RESTMessageComparator(MessageNode node) {
		Annotation ann=node.getAnnotation("REST");
		
		if (ann != null) {
			_method = ann.getValue("method");
			
			String path=ann.getValue("path");
			
			java.util.List<String> parts=new java.util.ArrayList<String>();
			
			java.util.StringTokenizer iter=new java.util.StringTokenizer(path, "/");
			
			while (iter.hasMoreTokens()) {
				parts.add(iter.nextToken());
			}
			
			_parts = new String[parts.size()];
			
			parts.toArray(_parts);
		}
		
		_parameters = node.getParameters();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean isMatch(Object message) {
		boolean ret=false;
		
		if (message instanceof RESTMessage) {
			RESTMessage rm=(RESTMessage)message;
			
			if (_method.equals(rm.getMethod())
					&& rm.getPath().size() == _parts.length) {
				
				java.util.Map<String,String> pathParams=null;
				
				for (int i=0; i < _parts.length; i++) {
					if (_parts[i].charAt(0) == '{'
							&& _parts[i].charAt(_parts[i].length()-1) == '}') {
						
						if (_evaluated) {
							if (pathParams == null) {
								pathParams = new java.util.HashMap<String,String>();
							}
							pathParams.put(_parts[i].substring(1, _parts[i].length()-1), rm.getPath().get(i));
						}
						
					} else if (!rm.getPath().get(i).equals(_parts[i])) {
						// Mismatched path part
						return (false);
					}
				}
				
				if (_parameters != null && _evaluated) {
					String[] values=new String[_parameters.size()];
					boolean f_usedContent=false;

					// TODO: Maybe if single parameter without annotation, then
					// could be mapped to POST content? But then need to ensure only
					// single non-annotation parameter.
					
					// Check parameter mappings
					for (int i=0; i < _parameters.size(); i++) {
						Annotation pann=_parameters.get(i).getAnnotation("REST");
						
						if (pann != null) {
							String query=pann.getValue("query");
							
							if (query != null && rm.getQueryParameters().containsKey(query)) {
								values[i] = rm.getQueryParameters().get(query);
								
							} else {
								String path=pann.getValue("path");
								
								if (path != null && pathParams.containsKey(path)) {
									values[i] = pathParams.get(path);
								}
							}
						} else if (_method.equals("POST") && !f_usedContent) {
							values[i] = rm.getContent();
							f_usedContent = true;
						}
					}
					
					// TODO: Check that values for all parameters are available ???? Or
					// should assertions just fail?

					// TODO: Maybe could include a component for evaluating the message content format, which
					// would be used to extract info required for assertions. The extracted path parameters
					// and query parameters would also be relevant for assertions, with the mapping to the
					// operation parameters in the scribble message transfer.
				}
				
				return (true);
			}
		}
		
		return (ret);
	}

}
