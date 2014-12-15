package org.scribble2.model.del.global;

import java.util.stream.Collectors;

import org.scribble2.model.ModelNode;
import org.scribble2.model.del.SimpleInteractionNodeDelegate;
import org.scribble2.model.global.GlobalMessageTransfer;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.model.visit.env.WellFormedChoiceEnv;
import org.scribble2.sesstype.Message;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

public class GlobalMessageTransferDelegate extends SimpleInteractionNodeDelegate
{
	@Override
	public GlobalMessageTransfer leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		GlobalMessageTransfer msgtrans = (GlobalMessageTransfer) visited;
		
		Role src = msgtrans.src.toName();
		Message msg = msgtrans.msg.toMessage();
		WellFormedChoiceEnv env = checker.popEnv();
		for (Role dest : msgtrans.dests.stream().map((rn) -> rn.toName()).collect(Collectors.toList()))
		{
			//checker.setEnv(checker.getEnv().addMessageForSubprotocol(checker, src, dest, msg.toScopedMessage(checker.getScope())));
			env = env.addMessageForSubprotocol(checker, src, dest, msg.toScopedMessage(checker.getScope()));
		}
		checker.pushEnv(env);
		
		if (!checker.peekEnv().isEnabled(src))
		{
			throw new ScribbleException("Role not enabled: " + src);
		}
		return msgtrans;
	}
}
