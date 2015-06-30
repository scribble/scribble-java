package org.scribble.del.global;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Choice;
import org.scribble.ast.InteractionNode;
import org.scribble.ast.Interruptible;
import org.scribble.ast.MessageTransfer;
import org.scribble.ast.Parallel;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.Recursion;
import org.scribble.ast.ScribNode;
import org.scribble.ast.SimpleInteractionNode;
import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;
import org.scribble.util.MessageIdMap;
import org.scribble.visit.InlinedWFChoiceChecker;
import org.scribble.visit.Projector;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.WFChoiceChecker;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.env.InlinedWFChoiceEnv;
import org.scribble.visit.env.ProjectionEnv;
import org.scribble.visit.env.WFChoiceEnv;

public class GChoiceDel extends GCompoundInteractionNodeDel
{
	@Override
	public void enterWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker) throws ScribbleException
	{
		WFChoiceEnv env = checker.peekEnv().enterContext();
		env = env.clear();
		env = env.enableChoiceSubject(((GChoice) child).subj.toName());
		checker.pushEnv(env);
	}
	
	@Override
	public GChoice leaveWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		GChoice cho = (GChoice) visited;
		Role subj = cho.subj.toName();
		if (!checker.peekParentEnv().isEnabled(subj))
		{
			throw new ScribbleException("Subject not enabled: " + subj);
		}
		
		Map<Role, Set<MessageId>> seen = null;
		Map<Role, Role> enablers = null;
		List<WFChoiceEnv> benvs =
				cho.blocks.stream().map((b) -> (WFChoiceEnv) b.del().env()).collect(Collectors.toList());
		for (WFChoiceEnv benv : benvs)
		{
			MessageIdMap enabled = benv.getEnabled();
			Set<Role> dests = enabled.getLeftKeys();
			dests.remove(subj);
			if (seen == null)
			{
				seen = new HashMap<>();				enablers = new HashMap<>();
				for (Role dest : dests)
				{
					seen.put(dest, enabled.getMessages(dest));
					Set<Role> srcs = enabled.getRightKeys(dest);
					if (srcs.size() > 1)
					{
						throw new ScribbleException("Inconsistent enabler role for " + dest + ": " + srcs);
					}
					enablers.put(dest, srcs.iterator().next());
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
						Set<MessageId> current = seen.get(dest);
						Set<MessageId> next = enabled.getMessages(dest);
						for (MessageId msg : next)
						{
							if (current.contains(msg))
							{
								throw new ScribbleException("Duplicate initial choice message for " + dest + ": " + msg);
							}
						}
						current.addAll(next);
						checkEnablers(enabled, dest, enablers);
					}
					
					for (Role dest : seen.keySet())
					{
						if (!dests.contains(dest))
						{
							throw new ScribbleException("Mismatched role enabling: " + dest);
						}
						checkEnablers(enabled, dest, enablers);
					}
				}
			}
		}
		
		// On leaving global choice, we're doing both the merging of block envs into the choice env, and the merging of the choice env to the parent-of-choice env
		// In principle, for the envLeave we should only be doing the latter (as countpart to enterEnv), but it is much more convenient for the compound-node (choice) to collect all the child block envs and merge here, rather than each individual block env trying to (partially) merge into the parent-choice as they are visited
		WFChoiceEnv merged = checker.popEnv().mergeContexts(benvs); 
		checker.pushEnv(merged);  // Merges the child block envs into the current choice env; super call below merges this choice env into the parent env of the choice
		return (GChoice) super.leaveWFChoiceCheck(parent, child, checker, visited);
	}
	
	// FIXME: factor better
	private void checkEnablers(MessageIdMap enabled, Role dest, Map<Role, Role> enablers) throws ScribbleException
	{
		Set<Role> srcs = enabled.getRightKeys(dest);
		if (srcs.size() > 1)
		{
			throw new ScribbleException("Inconsistent enabler role for " + dest + ": " + srcs);
		}
		if (!enablers.get(dest).equals(srcs.iterator().next()))
		{
			throw new ScribbleException("Inconsistent enabler role for " + dest + ": " + enablers.get(dest) + ", " + srcs);
		}
		enablers.put(dest, srcs.iterator().next());
	}
	
	@Override
	public GChoice leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		GChoice gc = (GChoice) visited;
		List<LProtocolBlock> blocks = 
				gc.blocks.stream().map((b) -> (LProtocolBlock) ((ProjectionEnv) b.del().env()).getProjection()).collect(Collectors.toList());	
		LChoice projection = null;  // Individual GlobalInteractionNodes become null if not projected -- projected seqs and blocks are never null though
		if (!blocks.get(0).isEmpty())  // WF allows empty (blocks/seq are never null)
		{
			RoleNode subj = (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, getSubject(blocks.get(0)).toString());
			projection = AstFactoryImpl.FACTORY.LChoice(subj, blocks);
		}
		proj.pushEnv(proj.popEnv().setProjection(projection));
		return (GChoice) super.leaveProjection(parent, child, proj, gc);
	}
	
	// Relies on WF rule for same enabler role in all choice blocks
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
			// Factor out CompoundBlockedNode?
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
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
	{
		GChoice gc = (GChoice) visited;
		List<GProtocolBlock> blocks = 
				gc.blocks.stream().map((b) -> (GProtocolBlock) ((InlineProtocolEnv) b.del().env()).getTranslation()).collect(Collectors.toList());	
		RoleNode subj = (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, gc.subj.toName().toString());
		GChoice inlined = AstFactoryImpl.FACTORY.GChoice(subj, blocks);
		builder.pushEnv(builder.popEnv().setTranslation(inlined));
		return (GChoice) super.leaveProtocolInlining(parent, child, builder, gc);
	}

	@Override
	public void enterInlinedWFChoiceCheck(ScribNode parent, ScribNode child, InlinedWFChoiceChecker checker) throws ScribbleException
	{
		InlinedWFChoiceEnv env = checker.peekEnv().enterContext();
		env = env.clear();
		env = env.enableChoiceSubject(((GChoice) child).subj.toName());
		checker.pushEnv(env);
	}

	@Override
	public GChoice leaveInlinedWFChoiceCheck(ScribNode parent, ScribNode child, InlinedWFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		GChoice cho = (GChoice) visited;
		Role subj = cho.subj.toName();
		if (!checker.peekParentEnv().isEnabled(subj))
		{
			throw new ScribbleException("Subject not enabled: " + subj);
		}
		
		Map<Role, Set<MessageId>> seen = null;
		Map<Role, Role> enablers = null;
		List<InlinedWFChoiceEnv> benvs =
				cho.blocks.stream().map((b) -> (InlinedWFChoiceEnv) b.del().env()).collect(Collectors.toList());
		for (InlinedWFChoiceEnv benv : benvs)
		{
			MessageIdMap enabled = benv.getEnabled();
			Set<Role> dests = enabled.getLeftKeys();
			dests.remove(subj);
			if (seen == null)
			{
				seen = new HashMap<>();
				enablers = new HashMap<>();
				for (Role dest : dests)
				{
					seen.put(dest, enabled.getMessages(dest));
					Set<Role> srcs = enabled.getRightKeys(dest);
					if (srcs.size() > 1)
					{
						throw new ScribbleException("Inconsistent enabler role for " + dest + ": " + srcs);
					}
					enablers.put(dest, srcs.iterator().next());
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
						Set<MessageId> current = seen.get(dest);
						Set<MessageId> next = enabled.getMessages(dest);
						for (MessageId msg : next)
						{
							if (current.contains(msg))
							{
								throw new ScribbleException("Duplicate initial choice message for " + dest + ": " + msg);
							}
						}
						current.addAll(next);
						checkEnablers(enabled, dest, enablers);
					}
					
					for (Role dest : seen.keySet())
					{
						if (!dests.contains(dest))
						{
							throw new ScribbleException("Mismatched role enabling: " + dest);
						}
						checkEnablers(enabled, dest, enablers);
					}
				}
			}
		}
		
		// On leaving global choice, we're doing both the merging of block envs into the choice env, and the merging of the choice env to the parent-of-choice env
		// In principle, for the envLeave we should only be doing the latter (as countpart to enterEnv), but it is much more convenient for the compound-node (choice) to collect all the child block envs and merge here, rather than each individual block env trying to (partially) merge into the parent-choice as they are visited
		InlinedWFChoiceEnv merged = checker.popEnv().mergeContexts(benvs); 
		checker.pushEnv(merged);  // Merges the child block envs into the current choice env; super call below merges this choice env into the parent env of the choice
		return (GChoice) super.leaveInlinedWFChoiceCheck(parent, child, checker, visited);
	}
}
