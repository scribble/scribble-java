package org.scribble.del.global;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ChoiceDel;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.model.global.Communication;
import org.scribble.model.global.Path;
import org.scribble.model.global.PathElement;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.Projector;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.WFChoiceChecker;
import org.scribble.visit.WFChoicePathChecker;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.env.WFChoicePathEnv;
import org.scribble.visit.env.ProjectionEnv;
import org.scribble.visit.env.WFChoiceEnv;

public class GChoiceDel extends ChoiceDel implements GCompoundInteractionNodeDel
{
	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		GChoice gc = (GChoice) visited;
		List<GProtocolBlock> blocks = 
				gc.getBlocks().stream().map((b) -> (GProtocolBlock) ((InlineProtocolEnv) b.del().env()).getTranslation()).collect(Collectors.toList());	
		RoleNode subj = gc.subj.clone();
		GChoice inlined = AstFactoryImpl.FACTORY.GChoice(subj, blocks);
		inl.pushEnv(inl.popEnv().setTranslation(inlined));
		return (GChoice) super.leaveProtocolInlining(parent, child, inl, gc);
	}

	@Override
	public void enterInlinedWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker) throws ScribbleException
	{
		WFChoiceEnv env = checker.peekEnv().enterContext();
		env = env.clear();
		env = env.enableChoiceSubject(((GChoice) child).subj.toName());
		checker.pushEnv(env);
	}

	@Override
	public GChoice leaveInlinedWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		GChoice cho = (GChoice) visited;
		Role subj = cho.subj.toName();
		if (!checker.peekParentEnv().isEnabled(subj))
		{
			throw new ScribbleException("Subject not enabled: " + subj);
		}
		
		// Enabled senders checked in GMessageTransferDel
		List<WFChoiceEnv> all =
				cho.getBlocks().stream().map((b) -> (WFChoiceEnv) b.del().env()).collect(Collectors.toList());
		if (all.size() > 1)
		{
			try
			{
				WFChoiceEnv benv0 = all.get(0);
				List<WFChoiceEnv> benvs = all.subList(1, all.size());

				Set<Role> dests = benv0.getEnabled().getDestinations();
				// Same roles enabled in every block
				benvs.stream().map((e) -> e.getEnabled().getDestinations()).forEach((rs) ->
						{
							if (!dests.equals(rs))
							{
								throw new RuntimeScribbleException("Mismatched enabled roles: " + dests + ", " + rs);
							}
						});
				
				dests.remove(subj);
				for (Role dest : dests)
				{
					// Same enabler(s) for each enabled role
					Set<Role> srcs = benv0.getEnabled().getSources(dest);  // Always singleton?
					benvs.stream().map((e) -> e.getEnabled().getSources(dest)).forEach((rs) ->
							{
								if (!srcs.equals(rs))
								{
									throw new RuntimeScribbleException("Mismatched enabler roles for " + dest + ": " + srcs + ", " + rs);
								}
							});
				
					// Distinct enabling messages
					Set<MessageId<?>> mids = benv0.getEnabled().getMessages(dest);
					benvs.stream().map((e) -> e.getEnabled().getMessages(dest)).forEach((ms) ->
							{
								if (!Collections.disjoint(mids, ms))
								{
									throw new RuntimeScribbleException("Non disjoint enabling messages for " + dest + ": " + mids + ", " + ms);
								}
								mids.addAll(ms);
							});
				}
			}
			catch (RuntimeScribbleException rse)  // Lambda hack
			{
				throw new ScribbleException(rse.getMessage(), rse.getCause());
			}
		}
		
		// On leaving global choice, we're doing both the merging of block envs into the choice env, and the merging of the choice env to the parent-of-choice env
		// In principle, for the envLeave we should only be doing the latter (as countpart to enterEnv), but it is much more convenient for the compound-node (choice) to collect all the child block envs and merge here, rather than each individual block env trying to (partially) merge into the parent-choice as they are visited
		WFChoiceEnv merged = checker.popEnv().mergeContexts(all); 
		checker.pushEnv(merged);  // Merges the child block envs into the current choice env; super call below merges this choice env into the parent env of the choice
		return (GChoice) super.leaveInlinedWFChoiceCheck(parent, child, checker, visited);  // Replaces base popAndSet to do pop, merge and set
	}
	
	@Override
	public GChoice leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		GChoice gc = (GChoice) visited;
		List<LProtocolBlock> blocks = 
				gc.getBlocks().stream().map((b) -> (LProtocolBlock) ((ProjectionEnv) b.del().env()).getProjection()).collect(Collectors.toList());	
		LChoice projection = null;  // Individual GlobalInteractionNodes become null if not projected -- projected seqs and blocks are never null though
		/*if (blocks.size() == 1)
		{
			if (!blocks.get(0).isEmpty())  // WF allows empty (blocks/seq are never null)
			{
				RoleNode subj = AstFactoryImpl.FACTORY.DummyProjectionRoleNode();
				projection = AstFactoryImpl.FACTORY.LChoice(subj, blocks);
			}
		}
		else //if (blocks.size() > 1)*/
		blocks = blocks.stream().filter((b) -> !b.isEmpty()).collect(Collectors.toList());
		if (!blocks.isEmpty())
		{
			List<LChoice> cs = blocks.stream().map((b) -> AstFactoryImpl.FACTORY.LChoice(AstFactoryImpl.FACTORY.DummyProjectionRoleNode(), Arrays.asList(b))).collect(Collectors.toList());
			LChoice merged = cs.get(0);
			for (int i = 1; i < cs.size(); i++)
			{
				merged = merged.merge(cs.get(i));
			}
			projection = merged;
		}
		proj.pushEnv(proj.popEnv().setProjection(projection));
		return (GChoice) GCompoundInteractionNodeDel.super.leaveProjection(parent, child, proj, gc);
		
		//..TODO: use merge (and disable balanced roles choice check for non-exit choice cases)

	}

	@Override
	public void enterWFChoicePathCheck(ScribNode parent, ScribNode child, WFChoicePathChecker coll) throws ScribbleException
	{
		WFChoicePathEnv env = coll.peekEnv().enterContext();
		//env = env.enableChoiceSubject(((GChoice) child).subj.toName()); // FIXME: record subject for enabled subject check
		env = env.clear();
		coll.pushEnv(env);
	}

	/*@Override
	public GChoice leavePathCollection(ScribNode parent, ScribNode child, PathCollectionVisitor coll, ScribNode visited) throws ScribbleException
	{
		GChoice cho = (GChoice) visited;
		List<PathEnv> all = cho.getBlocks().stream().map((b) -> (PathEnv) b.del().env()).collect(Collectors.toList());
		
		//PathEnv merged = coll.popEnv().mergeContexts(all); 
		PathEnv composed = all.get(0);
		for (PathEnv next : all.subList(1, all.size()))  // FIXME: factor out utility?
		{
			composed = composed.composeContext(next);
		}
		PathEnv merged = coll.popEnv().mergeContext(composed); 

		coll.pushEnv(merged);
		return (GChoice) super.leavePathCollection(parent, child, coll, visited);  // Replaces base popAndSet to do pop, merge and set
	}*/

	@Override
	public GChoice leaveWFChoicePathCheck(ScribNode parent, ScribNode child, WFChoicePathChecker coll, ScribNode visited) throws ScribbleException
	{
		GChoice cho = (GChoice) visited;
		List<WFChoicePathEnv> all = cho.getBlocks().stream().map((b) -> (WFChoicePathEnv) b.del().env()).collect(Collectors.toList());
		
		Set<Role> roles = all.stream().flatMap((env) -> env.getRoles().stream()).collect(Collectors.toSet());
		roles.remove(cho.subj.toName());
		Map<Role, Set<MessageId<?>>> enabling = new HashMap<>();
		Map<Role, Role> enablers = new HashMap<>();
		for (WFChoicePathEnv env : all) 
		{
			for (Path p : env.getPaths())
			{
				if (p.isExit())
				{
					R: for (Role r : roles)
					{
						for (PathElement pe : p.getElements())
						{
							Communication c = (Communication) pe;
							if (c.peer.equals(r))
							{
								Set<MessageId<?>> tmp = enabling.get(r);

								if (tmp == null)
								{
									tmp = new HashSet<>();
									enabling.put(r, tmp);
								}
								else
								{
									/* // Distinct enabling messages -- no: fails for nested choice paths (e.g. choice at A { 1() from A to B; choice at A { 2() from A to B; } or { 3() from A to B; }}), check in projection instead
									if (tmp.contains(c.mid))
									{
										throw new RuntimeScribbleException("Non disjoint enabling messages for " + r + ": " + tmp + ", " + c.mid);
									}*/
								}
								tmp.add(c.mid);
								if (!enablers.containsKey(r))
								{
									enablers.put(r, c.src);
								}
								else
								{
									Role tmp2 = enablers.get(r);
									// Same enabler(s) for each enabled role -- leave to projection/local choice subject inference?
									if (!tmp2.equals(c.src))
									{
										throw new RuntimeScribbleException("Mismatched enabler roles for " + r + ": " + tmp2 + ", " + c.src);
									}
								}

								continue R;
							}
							else if (c.src.equals(r))  // Currently checked in GMessageTransfer.leaveInlinedWFChoiceCheck (centralise here?)
							{
								// FIXME: path-collection wf choice needs choice subjects as extra data -- no: currently done in WFChoiceChecker
								//throw new ScribbleException("Role not enabled: " + r);
							}
						}
						// Same roles enabled in every block -- no: generalised by filtering out "recvar-paths"
						//throw new ScribbleException("Role not enabled: " + r);
					}
				}
			}
		}
		
		//PathEnv merged = coll.popEnv().mergeContexts(all); 
		WFChoicePathEnv composed = all.get(0);
		for (WFChoicePathEnv next : all.subList(1, all.size()))  // FIXME: factor out utility?
		{
			composed = composed.composeContext(next);
		}
		WFChoicePathEnv merged = coll.popEnv().mergeContext(composed); 
		coll.pushEnv(merged);
		return (GChoice) super.leaveWFChoicePathCheck(parent, child, coll, visited);  // Replaces base popAndSet to do pop, merge and set
	}
}
