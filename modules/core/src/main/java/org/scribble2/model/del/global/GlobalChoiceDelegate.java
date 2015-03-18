package org.scribble2.model.del.global;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble2.model.ModelNode;
import org.scribble2.model.del.CompoundInteractionNodeDelegate;
import org.scribble2.model.global.GlobalChoice;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.model.visit.env.WellFormedChoiceEnv;
import org.scribble2.sesstype.Message;
import org.scribble2.sesstype.ScopedMessage;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.MessageMap;
import org.scribble2.util.ScribbleException;

public class GlobalChoiceDelegate extends CompoundInteractionNodeDelegate
{
	@Override
	public WellFormedChoiceChecker enterWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker) throws ScribbleException
	{
		WellFormedChoiceEnv env = checker.peekEnv().push();
		env = env.clear();
		env = env.enableChoiceSubject(((GlobalChoice) child).subj.toName());
		checker.pushEnv(env);
		return checker;
	}
	
	@Override
	public GlobalChoice leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		GlobalChoice cho = (GlobalChoice) visited;
		Role subj = cho.subj.toName();
		
		//System.out.println("1: " + subj + ", " + checker.peekParentEnv().getEnabled());
		
		if (!checker.peekParentEnv().isEnabled(subj))
		{
			throw new ScribbleException("Subject not enabled: " + subj);
		}
		
		Map<Role, Set<ScopedMessage>> seen = null;
		List<WellFormedChoiceEnv> benvs =
				cho.blocks.stream().map((b) -> (WellFormedChoiceEnv) b.del().getEnv()).collect(Collectors.toList());
		//for (WellFormedChoiceEnv benv : cho.blocks.stream().map((b) -> (WellFormedChoiceEnv) b.del().getEnv()).collect(Collectors.toList()))
		for (WellFormedChoiceEnv benv : benvs)
		{
			MessageMap<ScopedMessage> enabled = benv.getEnabled();
			
			Set<Role> dests = enabled.getLeftKeys();
			dests.remove(subj);
			if (seen == null)
			{
				seen = new HashMap<>();				//dests.forEach((left) -> seen.put(left, enabled.getMessages(left)));
				for (Role dest : dests)
				{
					seen.put(dest, enabled.getMessages(dest));
				}
			}
			else
			{
				if (dests.isEmpty())
				{
					if (!seen.keySet().isEmpty())
					{
						throw new ScribbleException("Mismatched role enabling: " + seen.keySet());
					}
				}
				else 
				{
					for (Role dest : dests)
					{
						if (!seen.containsKey(dest))
						{
							throw new ScribbleException("Mismatched role enabling: " + dest);
						}
						Set<ScopedMessage> current = seen.get(dest);
						Set<ScopedMessage> next = enabled.getMessages(dest);
						for (Message msg : next)
						{
							if (current.contains(msg))
							{
								throw new ScribbleException("Duplicate initial choice message for " + dest + ": " + msg);
							}
						}
						current.addAll(next);
					}
					
					for (Role dest : seen.keySet())
					{
						if (!dests.contains(dest))
						{
							throw new ScribbleException("Mismatched role enabling: " + dest);
						}
					}
				}
			}
		}
		
		WellFormedChoiceEnv merged = checker.popEnv().merge(benvs);
		checker.pushEnv(merged);
		/*WellFormedChoiceEnv parent = pop();
		parent = parent.merge(merged);
		checker.setEnv(parent);	*/
		
		/*Choice<GlobalProtocolBlock> cho = super.leaveWFChoiceCheck(checker);
		//return new GlobalChoice(cho.ct, cho.subj, cho.blocks, cho.getContext(), cho.getEnv());
		// .. reconstruct*/
		return (GlobalChoice) super.leaveWFChoiceCheck(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
