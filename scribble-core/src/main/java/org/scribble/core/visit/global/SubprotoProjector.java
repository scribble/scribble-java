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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.core.job.Core;
import org.scribble.core.job.CoreContext;
import org.scribble.core.lang.global.GProtocol;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.LProtoName;
import org.scribble.core.type.name.ProtoName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.name.Substitutions;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.core.type.session.local.LSkip;
import org.scribble.core.type.session.local.LType;

// Supports Do -- can use on parsed (intermed)
public class SubprotoProjector extends InlinedProjector  // CHECKME: this way, or the other way round?
{
	protected SubprotoProjector(Core core, Role self)
	{
		super(core, self);
	}

	// Copy constructor for dup
	protected SubprotoProjector(SubprotoProjector v)
	{
		super(v);
	}
	
	@Override
	protected SubprotoProjector dup()
	{
		return new SubprotoProjector(this);
	}

	@Override
	public LType visitDo(Do<Global, GSeq> n)
	{
		if (!n.roles.contains(this.self))
		{
			return LSkip.SKIP;
		}

		CoreContext corec = this.core.getContext();
		ProtoName<Global> proto = n.proto;
		GProtocol imed = corec.getIntermediate(proto);
		Role targSelf = imed.roles.get(n.roles.indexOf(this.self));
		if (!imed.roles.contains(targSelf))  // CHECKME: because roles already pruned for intermed decl?
		{
			return LSkip.SKIP;
		}

		LProtoName fullname = InlinedProjector.getFullProjectionName(proto,
				targSelf);
		Substitutions subs = new Substitutions(imed.roles, n.roles,
				Collections.emptyList(), Collections.emptyList());
		List<Role> used = corec.getInlined(proto).roles.stream()  // N.B. global (inlined) roles -- still need to prune roles w.r.t. localised projection
				.map(x -> subs.subsRole(x)).collect(Collectors.toList());
		List<Role> rs = n.roles.stream().filter(x -> used.contains(x))
				.map(x -> x.equals(this.self) ? Role.SELF : x)  
						// CHECKME: syntax: "self" explictly used for Choice subject, but implicitly for MessageTransfer, inconsistent?
				.collect(Collectors.toList());
		return this.core.config.tf.local.LDo(null, fullname, rs, n.args);  // TODO CHECKME: prune args?
	}
}

