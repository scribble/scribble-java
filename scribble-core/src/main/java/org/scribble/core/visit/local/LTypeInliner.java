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
package org.scribble.core.visit.local;

import org.scribble.core.job.Core;
import org.scribble.core.lang.SubprotoSig;
import org.scribble.core.lang.local.LProtocol;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.ProtoName;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.type.session.local.LType;
import org.scribble.core.visit.STypeInliner;
import org.scribble.core.visit.Substitutor;

public class LTypeInliner extends STypeInliner<Local, LSeq>
{
	protected LTypeInliner(Core core)
	{
		super(core);
	}

	@Override
	public LType visitDo(Do<Local, LSeq> n)
	{
		ProtoName<Local> fullname = n.proto;
		SubprotoSig sig = new SubprotoSig(fullname, n.roles, n.args);
		RecVar rv = getInlinedRecVar(sig);
		if (hasSig(sig))
		{
			return this.core.config.tf.local.LContinue(n.getSource(), rv);
		}
		pushSig(sig);
		LProtocol p = this.core.getContext().getProjection(fullname);  // This line differs from GDo version
		Substitutor<Local, LSeq> subs = this.core.config.vf.Substitutor(p.roles,
				n.roles, p.params, n.args);
		//LSeq inlined = (LSeq) p.def.visitWithNoThrow(subs).visitWithNoThrow(this);
		LSeq inlined = visitSeq(subs.visitSeq(p.def));
				// i.e. returning a Seq -- rely on parent Seq to inline
		popSig();
		return this.core.config.tf.local.LRecursion(null, rv, inlined);
	}
}
