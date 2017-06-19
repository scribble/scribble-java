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
package org.scribble.del.global;

import java.util.Collections;
import java.util.List;
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
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.context.Projector;
import org.scribble.visit.context.env.ProjectionEnv;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.wf.WFChoiceChecker;
import org.scribble.visit.wf.env.WFChoiceEnv;

public class GChoiceDel extends ChoiceDel implements GCompoundInteractionNodeDel
{
	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		GChoice gc = (GChoice) visited;
		List<GProtocolBlock> blocks = 
				gc.getBlocks().stream().map((b) -> (GProtocolBlock) ((InlineProtocolEnv) b.del().env()).getTranslation()).collect(Collectors.toList());	
		RoleNode subj = gc.subj.clone();
		GChoice inlined = AstFactoryImpl.FACTORY.GChoice(gc.getSource(), subj, blocks);
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
			throw new ScribbleException(cho.subj.getSource(), "Subject not enabled: " + subj);
		}
		
		// Enabled senders checked in GMessageTransferDel
		List<WFChoiceEnv> all =
				cho.getBlocks().stream().map((b) -> (WFChoiceEnv) b.del().env()).collect(Collectors.toList());
		if (checker.job.useOldWf)  // ****
		{
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
		}
		
		/*// No: do via Env merge, with "trinary state semantics"
		ConnectedMap c0 = all.get(0).getConnected();
		//if (all.subList(1, all.size() - 1).stream().anyMatch((e) -> !c0.equals(e.getConnected())))
		for (WFChoiceEnv e : all.subList(1, all.size()))
		{
			ConnectedMap c = e.getConnected();
			if (!c0.equals(c))
			{
				// FIXME: check on model?
				throw new ScribbleException("Inconsistent connection configurations:\n  " + c0 + "\n  " + c);
			}
		}*/
		
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
				//gc.getBlocks().stream().map((b) -> ((GProtocolBlockDel) b.del()).project(b, self)).collect(Collectors.toList());
		LChoice projection = gc.project(proj.peekSelf(), blocks);
		proj.pushEnv(proj.popEnv().setProjection(projection));
		return (GChoice) GCompoundInteractionNodeDel.super.leaveProjection(parent, child, proj, gc);
	}
}
