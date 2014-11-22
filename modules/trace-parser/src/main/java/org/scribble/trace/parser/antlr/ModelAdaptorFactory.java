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
package org.scribble.trace.parser.antlr;

/**
 * This class provides the model adaptor factory.
 *
 */
public class ModelAdaptorFactory {
	
	private static java.util.Map<String,ModelAdaptor> _modelAdaptors=new java.util.HashMap<String,ModelAdaptor>();
	
	static {
		_modelAdaptors.put("trace", new TraceModelAdaptor());
		_modelAdaptors.put("roledefn", new RoleModelAdaptor());
		_modelAdaptors.put("messagetransfer", new MessageTransferModelAdaptor());
	}

	/**
	 * This method returns the model adaptor implementation associated with
	 * the supplied rule name.
	 * 
	 * @param ruleName The rule name
	 * @return The model adaptor, or null if not relevant
	 */
	public static ModelAdaptor getModelAdaptor(String ruleName) {
		return (_modelAdaptors.get(ruleName));
	}
}
