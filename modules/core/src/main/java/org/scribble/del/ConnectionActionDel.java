package org.scribble.del;

import org.scribble.ast.ConnectionAction;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.RoleCollector;
import org.scribble.visit.env.UnfoldingEnv;

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
