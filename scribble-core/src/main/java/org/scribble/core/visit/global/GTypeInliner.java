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

import org.scribble.core.job.Core;
import org.scribble.core.lang.SubprotoSig;
import org.scribble.core.lang.global.GProtocol;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.ProtoName;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.core.visit.STypeInliner;
import org.scribble.core.visit.Substitutor;

public class GTypeInliner extends STypeInliner<Global, GSeq>
{
	protected GTypeInliner(Core core)
	{
		super(core);
	}

	@Override
	public SType<Global, GSeq> visitDo(Do<Global, GSeq> n)
	{
		ProtoName<Global> fullname = n.proto;
		SubprotoSig sig = new SubprotoSig(fullname, n.roles, n.args);
		RecVar rv = getInlinedRecVar(sig);
		if (hasSig(sig))
		{
			return this.core.config.tf.global.GContinue(n.getSource(), rv);
		}
		pushSig(sig);
		GProtocol g = this.core.getContext().getIntermediate(fullname);
		Substitutor<Global, GSeq> subs = this.core.config.vf.Substitutor(g.roles,
				n.roles, g.params, n.args);
		//GSeq inlined = (GSeq) g.def.visitWithNoEx(subs).visitWithNoEx(this);
		GSeq inlined = visitSeq(subs.visitSeq(g.def));
				// i.e. returning a GSeq -- rely on parent GSeq to inline
		popSig();
		return this.core.config.tf.global.GRecursion(null, rv, inlined);
	}
}
