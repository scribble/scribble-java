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
package org.scribble.ast.local;

import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.Interrupt;
import org.scribble.ast.MessageNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

public abstract class LInterrupt extends Interrupt implements LSimpleInteractionNode
{
	protected LInterrupt(CommonTree source, RoleNode src, List<MessageNode> msgs)
	{
		super(source, src, msgs);
	}
	
	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
		throw new RuntimeException("TODO: " + this);
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Local getKind()
	{
		return LSimpleInteractionNode.super.getKind();
	}

	/*public final List<RoleNode> dests;

	/*public LocalInterrupt(CommonTree ct, RoleNode src, List<MessageNode> msgs, List<RoleNode> dests)
	{
		this(ct, src, msgs, dests, null, null);
	}* /

	protected LocalInterrupt(CommonTree ct, RoleNode src, List<MessageNode> msgs, List<RoleNode> dests, GlobalInterruptContext gicontext, Env env)
	{
		super(ct, src, msgs, gicontext, env);
		this.dests = new LinkedList<>(dests);
	}

	/* //@Override
	protected LocalInterrupt reconstruct(CommonTree ct, RoleNode src, List<MessageNode> msgs, List<RoleNode> dests, GlobalInterruptContext icontext, Env env)
	{
		return new LocalInterrupt(ct, src, msgs, dests, icontext, env);
	}* /
	protected abstract LocalInterrupt reconstruct(CommonTree ct, RoleNode src, List<MessageNode> msgs, List<RoleNode> dests, GlobalInterruptContext icontext, Env env);

	@Override
	public LocalInterrupt leaveContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		LocalInterrupt intt = (LocalInterrupt) super.leaveContextBuilding(builder);
		GlobalInterruptContext icontext =
				new GlobalInterruptContext(intt.dests.stream().map((rn) -> rn.toName()).collect(Collectors.toList()));
		//return new LocalInterrupt(intt.ct, intt.src, intt.msgs, intt.dests, icontext);
		return reconstruct(intt.ct, intt.src, intt.msgs, intt.dests, icontext, getEnv());
	}

	@Override
	public LocalInterrupt visitChildren(NodeVisitor nv) throws ScribbleException
	{
		RoleNode src = (RoleNode) visitChild(this.src, nv);
		List<MessageNode> msgs = new LinkedList<>();
		for (MessageNode msg : this.msgs)
		{
			msgs.add((MessageNode) visitChild(msg, nv));
		}
		List<RoleNode> dests = new LinkedList<RoleNode>();
		for (RoleNode dest : this.dests)
		{
			dests.add((RoleNode) visitChild(dest, nv));
		}
		//return new LocalInterrupt(this.ct, src, msgs, this.dests, getContext());
		return reconstruct(this.ct, src, msgs, this.dests, getContext(), getEnv());
	}*/
}
