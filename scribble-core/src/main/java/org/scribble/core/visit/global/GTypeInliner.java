package org.scribble.core.visit.global;

import org.scribble.core.job.Job;
import org.scribble.core.lang.SubprotoSig;
import org.scribble.core.lang.global.GProtocol;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.GProtocolName;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.name.Substitutions;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.global.GContinue;
import org.scribble.core.type.session.global.GRecursion;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.core.type.session.global.GType;
import org.scribble.core.visit.STypeInliner;

public class GTypeInliner extends STypeInliner<Global, GSeq>
{
	public GTypeInliner(Job job)
	{
		super(job);
	}

	@Override
	public <N extends ProtocolName<Global>> GType visitDo(Do<Global, GSeq, N> n)
	{
		GProtocolName fullname = (GProtocolName) n.proto;
		SubprotoSig sig = new SubprotoSig(fullname, n.roles, n.args);
		RecVar rv = getInlinedRecVar(sig);
		if (hasSig(sig))
		{
			return new GContinue(n.getSource(), rv);
		}
		pushSig(sig);
		GProtocol g = this.job.getContext().getIntermediate(fullname);
		Substitutions subs = 
				new Substitutions(g.roles, n.roles, g.params, n.args);
		GSeq inlined = g.def.substitute(subs).visitWith(this);
				// i.e. returning a GSeq -- rely on parent GSeq to inline
		popSig();
		return new GRecursion(null, rv, inlined);
	}
}
