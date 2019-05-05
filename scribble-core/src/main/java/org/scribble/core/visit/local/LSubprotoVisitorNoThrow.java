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
public interface LSubprotoVisitorNoThrow<T>  // T should be SType<K, B> for STypeVisitor
{

	// Subclasses of "Inlined" visitors should re-override using prepareSubprotoForVisit -- cf. LSubprotoVisitorNoThrow.super.visitDo(n)
	T visitDo(Do<Local, LSeq> n);

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
