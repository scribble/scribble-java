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


import org.scribble.ast.MessageSigNode;
import org.scribble.ast.MessageTransfer;
import org.scribble.ast.PayloadElem;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GDelegationElem;
import org.scribble.del.global.GDelegationElemDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.MessageId;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.context.ProtocolDeclContextBuilder;
import org.scribble.visit.env.UnfoldingEnv;
import org.scribble.visit.util.MessageIdCollector;
import org.scribble.visit.util.RoleCollector;

public abstract class MessageTransferDel extends SimpleInteractionNodeDel
{
	public MessageTransferDel()
	{

	}

	@Override
	public MessageTransfer<?> leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		MessageTransfer<?> lr = (MessageTransfer<?>) visited;
		MessageTransfer<?> inlined = (MessageTransfer<?>) lr.clone(inl.job.af);
		inl.pushEnv(inl.popEnv().setTranslation(inlined));
		return (MessageTransfer<?>) super.leaveProtocolInlining(parent, child, inl, lr);
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
		MessageTransfer<?> mt = (MessageTransfer<?>) visited;
		coll.addName(mt.src.toName());
		mt.getDestinationRoles().stream().forEach((rd) -> coll.addName(rd));
		return visited;
	}

	@Override
	public ScribNode leaveMessageIdCollection(ScribNode parent, ScribNode child, MessageIdCollector coll, ScribNode visited)
	{
		MessageTransfer<?> mt = (MessageTransfer<?>) visited;
		if (mt.msg.isMessageSigNode() || mt.msg.isMessageSigNameNode())
		{
			coll.addName((MessageId<?>) mt.msg.toMessage().getId());
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + mt.msg);
		}
		return visited;
	}

	@Override
	public MessageTransfer<?> leaveProtocolDeclContextBuilding(ScribNode parent, ScribNode child, ProtocolDeclContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		MessageTransfer<?> mt = (MessageTransfer<?>) visited;
		if (mt.msg.isMessageSigNode())
		{
			for (PayloadElem<?> pe : ((MessageSigNode) mt.msg).payloads.getElements())
			{
				if (pe.isGlobalDelegationElem())  // FIXME: should always be GMessageTransfer
				{
					((GDelegationElemDel) pe.del()).leaveMessageTransferInProtocolDeclContextBuilding(mt, (GDelegationElem) pe, builder);
				}
			}
		}
		return mt;
	}

	/*@Override
	public ScribNode leaveEnablingMessageCollection(ScribNode parent, ScribNode child, EnablingMessageCollector coll, ScribNode visited)
	{
		MessageTransfer<?> mt = (MessageTransfer<?>) visited;
		coll.addEnabling(mt.src.toName(), mt.getDestinations().get(0).toName(), mt.msg.toMessage().getId());  // FIXME: multicast
		return visited;
	}*/
}
