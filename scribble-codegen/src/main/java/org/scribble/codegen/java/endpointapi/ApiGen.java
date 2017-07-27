/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.codegen.java.endpointapi;

import java.util.Map;

import org.scribble.main.Job;
import org.scribble.type.name.GProtocolName;

// Basic pattern: use TypeGenerators to create all necessary TypeBuilders and cache them, and generateApi should call build on all as a final step
public abstract class ApiGen
{
	protected final Job job;
	protected final GProtocolName gpn;  // full name

	public ApiGen(Job job, GProtocolName fullname)
	{
		this.job = job;
		this.gpn = fullname;
	}
	
	// Return: key (package and Java class file path) -> val (Java class source) 
	// FIXME: Path instead of String key?
	public abstract Map<String, String> generateApi();
	
	public Job getJob()
	{
		return this.job;
	}
}
