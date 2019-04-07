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
package org.scribble.lang;

import java.util.Collections;
import java.util.Map;

import org.scribble.ast.AstFactory;
import org.scribble.core.job.JobArgs;
import org.scribble.core.model.endpoint.EModelFactory;
import org.scribble.core.type.name.ModuleName;

// The "static" (constant) info for Lang -- cf. LangContext "dynamic" state
public class LangConfig
{
	public final ModuleName main;  // Full name

	public final Map<JobArgs, Boolean> args;
			// CHECKME: verbose/debug printing parameter?

	public final AstFactory af;
	
	public final EModelFactory ef = null;  // FIXME: remove -- still referred to by old AST dels
	
	// N.B. MainContext is in a different non-visible (by Maven) package
	public LangConfig(ModuleName mainFullname, Map<JobArgs, Boolean> args,
			AstFactory af)
	{
		this.main = mainFullname;
		this.args = Collections.unmodifiableMap(args);
		this.af = af;
	}
}
