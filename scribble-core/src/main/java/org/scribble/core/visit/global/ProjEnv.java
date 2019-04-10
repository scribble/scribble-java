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
package org.scribble.core.visit.global;

import org.scribble.core.job.Job;
import org.scribble.core.type.name.GProtocolName;
import org.scribble.core.type.name.LProtocolName;
import org.scribble.core.type.name.ModuleName;
import org.scribble.core.type.name.Role;

public class ProjEnv
{
	public final Job job;
	public final Role self;

	public ProjEnv(Job job, Role self)
	{
		this.job = job;
		this.self = self;
	}
	
	public static LProtocolName projectSimpleProtocolName(GProtocolName simpname,
			Role role)
	{
		return new LProtocolName(simpname.toString() + "_" + role.toString());
	}

	// Role is the target subprotocol parameter (not the current projector self -- actually the self just popped)
	public static LProtocolName projectFullProtocolName(GProtocolName fullname,
			Role role)
	{
		LProtocolName simplename = projectSimpleProtocolName(
				fullname.getSimpleName(), role);
		ModuleName modname = projectModuleName(fullname.getPrefix(), simplename);
		return new LProtocolName(modname, simplename);
	}

	// fullname is the un-projected name; localname is the already projected simple name
	public static ModuleName projectModuleName(ModuleName fullname,
			LProtocolName localname)
	{
		ModuleName simpname = new ModuleName(
				fullname.getSimpleName().toString() + "_" + localname.toString());
		return new ModuleName(fullname.getPrefix(), simpname); // Supports unary
																														// fullname
	}
}
