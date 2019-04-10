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
package org.scribble.core.job;

import java.util.Collections;
import java.util.Map;

import org.scribble.core.model.ModelFactory;
import org.scribble.core.type.name.ModuleName;

// The "static" (constant) info for Jobs -- cf. JobContext "dynamic" state
public class JobConfig
{
	public final ModuleName main;  // Full name
	// CHECKME: verbose/debug printing parameter?

	public final Map<JobArgs, Boolean> args;

	public final ModelFactory ef;
	public final ModelFactory sf;
	
	// N.B. MainContext is in a different non-visible (by Maven) package
	public JobConfig(ModuleName main, Map<JobArgs, Boolean> args,
			ModelFactory ef, ModelFactory sf)
	{
		this.main = main;
		this.args = Collections.unmodifiableMap(args);
		this.ef = ef;
		this.sf = sf;
	}
}
