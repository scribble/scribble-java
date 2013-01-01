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
package org.scribble.protocol.validation;

import org.scribble.protocol.model.ModelObject;

public class TestValidationLogger extends ConsoleValidationLogger {
    	
	private java.util.List<String> _errors=new java.util.ArrayList<String>();
	private java.util.List<String> _warnings=new java.util.ArrayList<String>();
	
	public void error(String issue, ModelObject mobj) {
		super.error(issue, mobj);
		_errors.add(issue);
	}
	
	public java.util.List<String> getErrors() {
		return (_errors);
	}
	
	public void warning(String issue, ModelObject mobj) {
		super.warning(issue, mobj);
		_warnings.add(issue);
	}
	
	public void info(String issue, ModelObject mobj) {
		super.info(issue, mobj);
	}
	
	public boolean isErrorsOrWarnings() {
		return (_errors.size() > 0 || _warnings.size() > 0);
	}
}
