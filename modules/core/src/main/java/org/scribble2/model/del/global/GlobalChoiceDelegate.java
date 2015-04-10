package org.scribble2.model.del.global;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble2.model.ModelFactory;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.global.GlobalChoice;
import org.scribble2.model.local.LocalChoice;
import org.scribble2.model.local.LocalProtocolBlock;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.model.visit.env.WellFormedChoiceEnv;
import org.scribble2.sesstype.Message;
import org.scribble2.sesstype.ScopedMessage;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.MessageMap;
import org.scribble2.util.ScribbleException;

public class GlobalChoiceDelegate extends GlobalCompoundInteractionNodeDelegate
{
	@Override
	//public WellFormedChoiceChecker enterWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker) throws ScribbleException
	public void enterWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker) throws ScribbleException
	{
		WellFormedChoiceEnv env = checker.peekEnv().enterContext();
		env = env.clear();
		env = env.enableChoiceSubject(((GlobalChoice) child).subj.toName());
		checker.pushEnv(env);
		//return checker;
	}
	
	@Override
	public GlobalChoice leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		GlobalChoice cho = (GlobalChoice) visited;
		Role subj = cho.subj.toName();
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
		
		WellFormedChoiceEnv merged = checker.popEnv().mergeContexts(benvs); 
		checker.pushEnv(merged);  // Merges the child block envs into the current choice env; super call below merges this choice env into the parent env of the choice
		/*WellFormedChoiceEnv parent = pop();
		parent = parent.merge(merged);
		checker.setEnv(parent);	*/
		
		/*Choice<GlobalProtocolBlock> cho = super.leaveWFChoiceCheck(checker);
		//return new GlobalChoice(cho.ct, cho.subj, cho.blocks, cho.getContext(), cho.getEnv());
		// .. reconstruct*/
		return (GlobalChoice) super.leaveWFChoiceCheck(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env
		
		// On leaving global choice, we're doing both the merging of block envs into the choice env, and the merging of the choice env to the parent-of-choice env
		// In principle, for the envLeave we should only be doing the latter (as countpart to enterEnv), but it is much more convenient for the compound-node (choice) to collect all the child block envs and merge here, rather than each individual block env trying to (partially) merge into the parent-choice as they are visited
	}
	
	@Override
	public GlobalChoice leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException
	{
		GlobalChoice gc = (GlobalChoice) visited;
		//RoleNode subj = new RoleNode(gc.subj.toName().toString());  // Inconsistent to copy role nodes manually, but do via children visiting for other children
		RoleNode subj = (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.ROLE, gc.subj.toName().toString());
		List<LocalProtocolBlock> blocks = 
				gc.blocks.stream().map((b) -> (LocalProtocolBlock) ((ProjectionEnv) b.del().getEnv()).getProjection()).collect(Collectors.toList());	
		LocalChoice projection = null;  // Individual GlobalInteractionNodes become null if not projected -- projected seqs and blocks are never null though
		if (!blocks.get(0).isEmpty())  // WF allows this
		{
			projection = ModelFactoryImpl.FACTORY.LocalChoice(subj, blocks);
		}
		ProjectionEnv env = proj.popEnv();
		//proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		proj.pushEnv(new ProjectionEnv(projection));
		return (GlobalChoice) super.leaveProjection(parent, child, proj, gc);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
