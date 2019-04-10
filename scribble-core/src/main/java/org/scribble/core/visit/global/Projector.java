package org.scribble.core.visit.global;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.core.job.Job;
import org.scribble.core.job.JobContext;
import org.scribble.core.lang.global.GProtocol;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.GProtocolName;
import org.scribble.core.type.name.LProtocolName;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.name.Substitutions;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.core.type.session.local.LDo;
import org.scribble.core.type.session.local.LSkip;
import org.scribble.core.type.session.local.LType;

// Supports Do -- can use on parsed (intermed)
public class Projector extends InlinedProjector  // This way or the other way round?
{
	public Projector(Job job, Role self)
	{
		super(job, self);
	}

	@Override
	public <N extends ProtocolName<Global>> LType visitDo(Do<Global, GSeq, N> n)
	{
		if (!n.roles.contains(this.self))
		{
			return LSkip.SKIP;
		}

		JobContext jobc = this.job.getContext();
		GProtocolName proto = (GProtocolName) n.proto;
		GProtocol gpd = jobc.getIntermediate(proto);
		Role targSelf = gpd.roles.get(n.roles.indexOf(this.self));

		GProtocol imed = jobc.getIntermediate(proto);
		if (!imed.roles.contains(targSelf))  // CHECKME: because roles already pruned for intermed decl?
		{
			return LSkip.SKIP;
		}

		LProtocolName fullname = InlinedProjector.getFullProjectionName(proto,
				targSelf);
		Substitutions subs = new Substitutions(imed.roles, n.roles,
				Collections.emptyList(), Collections.emptyList());
		List<Role> used = jobc.getInlined(proto).roles.stream()
				.map(x -> subs.subsRole(x)).collect(Collectors.toList());
		List<Role> rs = n.roles.stream().filter(x -> used.contains(x))
				.map(x -> x.equals(this.self) ? Role.SELF : x)
						// CHECKME: "self" also explcitily used for Choice, but implicitly for MessageTransfer, inconsistent?
				.collect(Collectors.toList());
		return new LDo(null, fullname, rs, n.args);  // TODO CHECKME: prune args?
	}
}

