package org.scribble2.model.del.global;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble2.model.Choice;
import org.scribble2.model.InteractionNode;
import org.scribble2.model.Interruptible;
import org.scribble2.model.MessageTransfer;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.Parallel;
import org.scribble2.model.ProtocolBlock;
import org.scribble2.model.Recursion;
import org.scribble2.model.SimpleInteractionNode;
import org.scribble2.model.global.GChoice;
import org.scribble2.model.local.LChoice;
import org.scribble2.model.local.LProtocolBlock;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.model.visit.env.WellFormedChoiceEnv;
import org.scribble2.sesstype.kind.Local;
import org.scribble2.sesstype.kind.RoleKind;
import org.scribble2.sesstype.name.MessageId;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.MessageIdMap;
import org.scribble2.util.ScribbleException;

public class GChoiceDel extends GCompoundInteractionNodeDel
{
	@Override
	//public WellFormedChoiceChecker enterWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker) throws ScribbleException
	public void enterWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker) throws ScribbleException
	{
		WellFormedChoiceEnv env = checker.peekEnv().enterContext();
		env = env.clear();
		env = env.enableChoiceSubject(((GChoice) child).subj.toName());
		checker.pushEnv(env);
		//return checker;
	}
	
	@Override
	public GChoice leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		GChoice cho = (GChoice) visited;
		Role subj = cho.subj.toName();
		if (!checker.peekParentEnv().isEnabled(subj))
		{
			throw new ScribbleException("Subject not enabled: " + subj);
		}
		
		//Map<Role, Set<ScopedMessage>> seen = null;
		//Map<Role, Set<Message>> seen = null;
		Map<Role, Set<MessageId>> seen = null;
		List<WellFormedChoiceEnv> benvs =
				cho.blocks.stream().map((b) -> (WellFormedChoiceEnv) b.del().env()).collect(Collectors.toList());
		//for (WellFormedChoiceEnv benv : cho.blocks.stream().map((b) -> (WellFormedChoiceEnv) b.del().getEnv()).collect(Collectors.toList()))
		for (WellFormedChoiceEnv benv : benvs)
		{
			//MessageMap<ScopedMessage> enabled = benv.getEnabled();
			//MessageMap<Message> enabled = benv.getEnabled();
			MessageIdMap enabled = benv.getEnabled();
			
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
						/*Set<ScopedMessage> current = seen.get(dest);
						Set<ScopedMessage> next = enabled.getMessages(dest);*/
						/*Set<Message> current = seen.get(dest);
						Set<Message> next = enabled.getMessages(dest);*/
						Set<MessageId> current = seen.get(dest);
						Set<MessageId> next = enabled.getMessages(dest);
						//for (Message msg : next)
						for (MessageId msg : next)
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
		return (GChoice) super.leaveWFChoiceCheck(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env
		
		// On leaving global choice, we're doing both the merging of block envs into the choice env, and the merging of the choice env to the parent-of-choice env
		// In principle, for the envLeave we should only be doing the latter (as countpart to enterEnv), but it is much more convenient for the compound-node (choice) to collect all the child block envs and merge here, rather than each individual block env trying to (partially) merge into the parent-choice as they are visited
	}
	
	private static Role getSubject(ProtocolBlock<Local> block)
	{
		InteractionNode<Local> ln = block.seq.actions.get(0);
		Role subj;
		if (ln instanceof SimpleInteractionNode)
		{
			// By well-formedness and projection, cannot be RecursionVar, but it can be a (recursive) subprotocol
			if (ln instanceof MessageTransfer)
			{
				subj = ((MessageTransfer<Local>) ln).src.toName();
			}
			else //if (ln instanceof Do)
			{
				throw new RuntimeException("TODO: " + ln);
			}
		}
		else //if (ln instanceof CompoundInteractionNode)
		{
			//CompoundInteractionNode<Local> cn = (CompoundInteractionNode<Local>) ln;
			// FIXME: streamline -- factor out CompoundBlockedNode?
			if (ln instanceof Choice)
			{
				return getSubject(((Choice<Local>) ln).blocks.get(0));
			}
			else if (ln instanceof Recursion)
			{
				return getSubject(((Recursion<Local>) ln).block);
			}
			else if (ln instanceof Parallel)
			{
				return getSubject(((Parallel<Local>) ln).blocks.get(0));
			}
			else if (ln instanceof Interruptible)
			{
				return getSubject(((Interruptible<Local>) ln).block);
			}
			else
			{
				throw new RuntimeException("Shoudln't get in here: " + ln);
			}
		}
		return subj;
	}
	
	@Override
	public GChoice leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException
	{
		GChoice gc = (GChoice) visited;
		//RoleNode subj = new RoleNode(gc.subj.toName().toString());  // Inconsistent to copy role nodes manually, but do via children visiting for other children
		//RoleNode subj = (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.ROLE, gc.subj.toName().toString());
		//RoleNode subj = (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, gc.subj.toName().toString());
		List<ProtocolBlock<Local>> blocks = 
				gc.blocks.stream().map((b) -> (LProtocolBlock) ((ProjectionEnv) b.del().env()).getProjection()).collect(Collectors.toList());	
		RoleNode subj = (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, getSubject(blocks.get(0)).toString());
		LChoice projection = null;  // Individual GlobalInteractionNodes become null if not projected -- projected seqs and blocks are never null though
		if (!blocks.get(0).isEmpty())  // WF allows this
		{
			projection = ModelFactoryImpl.FACTORY.LocalChoice(subj, blocks);
		}
		ProjectionEnv env = proj.popEnv();
		//proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		proj.pushEnv(new ProjectionEnv(projection));
		return (GChoice) super.leaveProjection(parent, child, proj, gc);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
