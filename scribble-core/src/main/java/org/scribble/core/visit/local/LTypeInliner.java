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

import org.scribble.core.job.Job;
import org.scribble.core.lang.SubprotoSig;
import org.scribble.core.lang.local.LProtocol;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.LProtocolName;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.local.LContinue;
import org.scribble.core.type.session.local.LRecursion;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.type.session.local.LType;
import org.scribble.core.visit.STypeInliner;
import org.scribble.core.visit.Substitutor;

public class LTypeInliner extends STypeInliner<Local, LSeq>
{
	public LTypeInliner(Job job)
	{
		super(job);
	}

	@Override
	public <N extends ProtocolName<Local>> LType visitDo(Do<Local, LSeq, N> n)
	{
		LProtocolName fullname = (LProtocolName) n.proto;
		SubprotoSig sig = new SubprotoSig(fullname, n.roles, n.args);
		RecVar rv = getInlinedRecVar(sig);
		if (hasSig(sig))
		{
			return new LContinue(n.getSource(), rv);
		}
		pushSig(sig);
		LProtocol p = this.job.getContext().getProjection(fullname);  // This line differs from GDo version
		Substitutor<Local, LSeq> subs = 
				new Substitutor<>(p.roles, n.roles, p.params, n.args);
		//LSeq inlined = (LSeq) p.def.visitWithNoEx(subs).visitWithNoEx(this);
		LSeq inlined = visitSeq(subs.visitSeq(p.def));
				// i.e. returning a Seq -- rely on parent Seq to inline
		popSig();
		return new LRecursion(null, rv, inlined);
	}
}
