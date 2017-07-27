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
import java.util.Set;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.MessageNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.main.ScribbleException;
import org.scribble.type.Message;

public class LThrows extends LInterrupt
{
	protected LThrows(CommonTree source, RoleNode src, List<MessageNode> msgs)
	{
		super(source, src, msgs);
	}

	@Override
	public LInteractionNode merge(AstFactory af, LInteractionNode ln) throws ScribbleException
	{
		throw new RuntimeException("TODO: " + this);
	}

	@Override
	public boolean canMerge(LInteractionNode ln)
	{
		throw new RuntimeException("TODO: " + this);
	}

	@Override
	public Set<Message> getEnabling()
	{
		throw new RuntimeException("TODO: " + this);
	}
	
	/*public LocalThrows(CommonTree ct, RoleNode src, List<MessageNode> msgs, List<RoleNode> dests)
	{
		this(ct, src, msgs, dests, null, null);
	}

	protected LocalThrows(CommonTree ct, RoleNode src, List<MessageNode> msgs, List<RoleNode> dests, GlobalInterruptContext gicontext, Env env)
	{
		super(ct, src, msgs, dests, gicontext, env);
	}

	@Override
	protected LocalThrows reconstruct(CommonTree ct, RoleNode src, List<MessageNode> msgs, List<RoleNode> dests, GlobalInterruptContext icontext, Env env)
	{
		return new LocalThrows(ct, src, msgs, dests, icontext, env);
	}
	
	/*@Override
	public LocalThrows visitChildren(NodeVisitor nv) throws ScribbleException
	{
		LocalInterrupt interr = super.visitChildren(nv);
		//return new LocalThrows(interr.ct, interr.src, interr.msgs, interr.dests, (GlobalInterruptContext) interr.getContext());
		return reconstruct(interr.ct, interr.src, interr.msgs, interr.dests, (GlobalInterruptContext) interr.getContext(), getEnv());
	}* /
	
	@Override
	public String toString()
	{
		String s = AntlrConstants.THROWS_KW + " " + this.msgs.get(0).toString();
		for (MessageNode msg : this.msgs.subList(1, this.msgs.size()))
		{
			s += ", " + msg;
		}
		s += " " + AntlrConstants.TO_KW + " " + this.dests.get(0);
		for (RoleNode dest : this.dests.subList(1, this.dests.size()))
		{
			s += ", " + dest.toName();
		}
		return s + ";";
	}
	
	/*@Override
	public void toGraph(GraphBuilder gb)
	{
		throw new RuntimeException("Shouldn't get in here.");
	}*/
}
