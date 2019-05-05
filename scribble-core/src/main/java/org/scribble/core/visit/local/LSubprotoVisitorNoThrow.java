package org.scribble.core.visit.local;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.core.job.Core;
import org.scribble.core.lang.local.LProtocol;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.visit.Substitutor;


// Pre: LRoleDeclAndDoArgFixer (for imed LProjections)
public interface LSubprotoVisitorNoThrow
{
	default LSeq prepareSubprotoForVisit(Core core, Do<Local, LSeq> n)
	{
		return prepareSubprotoForVisit(core, n, false);
	}

	default LSeq prepareSubprotoForVisit(Core core, Do<Local, LSeq> n,
			boolean passive)
	{
		LProtocol target = (LProtocol) n.getTarget(core);
		List<Role> tmp = target.roles.stream()
				.map(x -> x.equals(target.self) ? Role.SELF : x)  // FIXME: self roledecl not actually being a self role is a mess
				.collect(Collectors.toList());
		Substitutor<Local, LSeq> subs = core.config.vf
				.Substitutor(tmp, n.roles, target.params, n.args, passive);
		return subs.visitSeq(target.def);
	}
}
