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
package org.scribble.del;

import org.scribble.ast.ConnectionAction;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.env.UnfoldingEnv;
import org.scribble.visit.util.RoleCollector;

// FIXME: factor with MessageTransferDel
public abstract class ConnectionActionDel extends SimpleInteractionNodeDel
{
	public ConnectionActionDel()
	{

	}

	@Override
	public ConnectionAction<?> leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		ConnectionAction<?> c = (ConnectionAction<?>) visited;
		ConnectionAction<?> inlined = (ConnectionAction<?>) c.clone();
		inl.pushEnv(inl.popEnv().setTranslation(inlined));
		return (ConnectionAction<?>) super.leaveProtocolInlining(parent, child, inl, c);
	}

	/*@Override
	public void enterChoiceUnguardedSubprotocolCheck(ScribNode parent, ScribNode child, ChoiceUnguardedSubprotocolChecker checker) throws ScribbleException
	{
		ChoiceUnguardedSubprotocolEnv env = checker.popEnv();
		env = env.disablePrune();
		checker.pushEnv(env);
	}*/

	@Override
	public void enterInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf) throws ScribbleException
	{
		UnfoldingEnv env = unf.popEnv();
		env = env.disableUnfold();
		unf.pushEnv(env);
	}

	@Override
	public ScribNode leaveRoleCollection(ScribNode parent, ScribNode child, RoleCollector coll, ScribNode visited)
	{
		ConnectionAction<?> c = (ConnectionAction<?>) visited;
		coll.addName(c.src.toName());
		coll.addName(c.dest.toName());
		return visited;
	}

	/*@Override
	public ScribNode leaveMessageIdCollection(ScribNode parent, ScribNode child, MessageIdCollector coll, ScribNode visited)
	{
		Connect<?> c = (Connect<?>) visited;
		if (c.msg.isMessageSigNode() || c.msg.isMessageSigNameNode())
		{
			coll.addName((MessageId<?>) c.msg.toMessage().getId());
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + c.msg);
		}
		return visited;
	}*/

	/*@Override
	public ScribNode leaveEnablingMessageCollection(ScribNode parent, ScribNode child, EnablingMessageCollector coll, ScribNode visited)
	{
		Connect<?> mt = (Connect<?>) visited;
		coll.addEnabling(mt.src.toName(), mt.getDestinations().get(0).toName(), mt.msg.toMessage().getId());  // FIXME: multicast
		return visited;
	}*/
}
