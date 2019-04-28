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
package org.scribble.job;

import java.util.Collections;
import java.util.Map;

import org.scribble.ast.AstFactory;
import org.scribble.core.job.CoreArgs;
import org.scribble.core.type.name.ModuleName;
import org.scribble.core.type.session.STypeFactory;
import org.scribble.del.DelFactory;
import org.scribble.visit.VisitorFactory;

// The "static" (constant) info for Lang -- cf. LangContext "dynamic" state
public class JobConfig
{
	public final ModuleName main;  // Full name

	public final Map<CoreArgs, Boolean> args;  // CHECKME: Set<CoreArgs> ?

	public final AstFactory af;
	public final DelFactory df;
	public final VisitorFactory vf;
	public final STypeFactory tf;
	
	// N.B. MainContext is in a different non-visible (by Maven) package
	public JobConfig(ModuleName mainFullname, Map<CoreArgs, Boolean> args,
			AstFactory af, DelFactory df, VisitorFactory vf, STypeFactory tf)
	{
		this.main = mainFullname;
		this.args = Collections.unmodifiableMap(args);
		this.af = af;
		this.df = df;
		this.vf = vf;
		this.tf = tf;
	}
}
