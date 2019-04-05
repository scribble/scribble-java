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

import org.scribble.ast.ConnectAction;
import org.scribble.ast.ScribNode;
import org.scribble.util.ScribException;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.env.UnfoldingEnv;
import org.scribble.visit.util.RoleCollector;

// TODO: factor with MessageTransferDel
public abstract class ConnectionActionDel extends SimpleSessionNodeDel
{
	public ConnectionActionDel()
	{

	}

	@Override
	public ConnectAction<?> leaveProtocolInlining(ScribNode parent,
			ScribNode child, ProtocolDefInliner inl, ScribNode visited)
			throws ScribException
	{
		ConnectAction<?> c = (ConnectAction<?>) visited;
		ConnectAction<?> inlined = (ConnectAction<?>) c.clone();//inl.job.af);
		inl.pushEnv(inl.popEnv().setTranslation(inlined));
		return (ConnectAction<?>) super.leaveProtocolInlining(parent, child, inl, c);
	}

	@Override
	public void enterInlinedProtocolUnfolding(ScribNode parent, ScribNode child,
			InlinedProtocolUnfolder unf) throws ScribException
	{
		UnfoldingEnv env = unf.popEnv();
		env = env.disableUnfold();
		unf.pushEnv(env);
	}

	@Override
	public ScribNode leaveRoleCollection(ScribNode parent, ScribNode child,
			RoleCollector coll, ScribNode visited)
	{
		ConnectAction<?> c = (ConnectAction<?>) visited;
		coll.addName(c.getSourceChild().toName());
		coll.addName(c.getDestinationChild().toName());
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

	/*@Override
	public void enterChoiceUnguardedSubprotocolCheck(ScribNode parent, ScribNode child, ChoiceUnguardedSubprotocolChecker checker) throws ScribbleException
	{
		ChoiceUnguardedSubprotocolEnv env = checker.popEnv();
		env = env.disablePrune();
		checker.pushEnv(env);
	}*/
}
