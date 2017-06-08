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
package org.scribble.del.local;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Choice;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ChoiceDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.RecVar;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.context.EGraphBuilder;
import org.scribble.visit.context.ProjectedChoiceDoPruner;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;
import org.scribble.visit.context.UnguardedChoiceDoProjectionChecker;
import org.scribble.visit.context.env.UnguardedChoiceDoEnv;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.wf.ReachabilityChecker;
import org.scribble.visit.wf.env.ReachabilityEnv;

public class LChoiceDel extends ChoiceDel implements LCompoundInteractionNodeDel
{
	/* // Not needed: just enters and pushes, done by base routine (only overwite if do extra stuff, like pushChoiceParent)
	@Override
	public void enterUnguardedChoiceDoProjectionCheck(ScribNode parent, ScribNode child, ChoiceUnguardedSubprotocolChecker checker) throws ScribbleException
	{
		ChoiceUnguardedSubprotocolEnv env = checker.peekEnv().enterContext();
		checker.pushEnv(env);
	}*/

	@Override
	public ScribNode leaveUnguardedChoiceDoProjectionCheck(ScribNode parent, ScribNode child, UnguardedChoiceDoProjectionChecker checker, ScribNode visited) throws ScribbleException
	{
		Choice<?> cho = (Choice<?>) visited;
		List<UnguardedChoiceDoEnv> benvs =
				cho.getBlocks().stream().map((b) -> (UnguardedChoiceDoEnv) b.del().env()).collect(Collectors.toList());
		UnguardedChoiceDoEnv merged = checker.popEnv().mergeContexts(benvs); 
		checker.pushEnv(merged);
		return (Choice<?>) super.leaveUnguardedChoiceDoProjectionCheck(parent, child, checker, cho);  // Done merge of children here, super does merge into parent
	}

	@Override
	public ScribNode leaveProjectedChoiceDoPruning(ScribNode parent, ScribNode child, ProjectedChoiceDoPruner pruner, ScribNode visited) throws ScribbleException
	{
		LChoice lc = (LChoice) visited;
		List<LProtocolBlock> blocks = lc.getBlocks().stream().filter((b) -> !b.isEmpty()).collect(Collectors.toList());
		if (blocks.isEmpty())
		{
			return null;
		}
		return lc.reconstruct(lc.subj, blocks);
	}
	
	@Override
	public ScribNode leaveProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer, ScribNode visited) throws ScribbleException
	{
		LChoice lc = (LChoice) visited;
		List<LProtocolBlock> blocks = lc.getBlocks();
		
		Set<Role> subjs = blocks.stream()
				.map((b) -> b.getInteractionSeq().getInteractions().get(0).inferLocalChoiceSubject(fixer))
				//.filter((r) -> !r.toString().equals(DummyProjectionRoleNode.DUMMY_PROJECTION_ROLE))
				.collect(Collectors.toSet());
		if (subjs.size() == 0)
		{
			//throw new RuntimeScribbleException("TODO: unable to infer projection subject: " + parent);
			throw new RuntimeException("Shouldn't get in here: " + subjs);  // FIXME: should be OK now by model-based WF
		}
		else
		{
			subjs = subjs.stream()
					.map((r) -> fixer.isRecVarRole(r) ? fixer.getChoiceSubject(new RecVar(r.toString())) : r)  // Never needed?
					.collect(Collectors.toSet());
		}
		
		// HACK?  (for non- role-balanced choice cases)
		subjs = subjs.stream().filter((s) -> s != null).collect(Collectors.toSet());
		
		if (subjs.size() > 1)  // Unnecessary: due to WF check in GChoiceDel.leaveInlinedPathCollection -- would be better as a check on locals than in projection anyway
		{
			String self = fixer.getModuleContext().root.getSimpleName().toString();  // HACK
			self = self.substring(self.lastIndexOf('_')+1, self.length());  // FIXME: not sound (if role names include "_")
			throw new ScribbleException(lc.getSource(), "Cannot project onto " + self + " due to inconsistent local choice subjects: " + subjs);  // self not recorded -- can derive from LProtocolDecl RoleDeclList
			//throw new RuntimeException("Shouldn't get in here: " + subjs);
		}
		RoleNode subj = (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(null, RoleKind.KIND,  // FIXME? null source OK?
				//blocks.get(0).getInteractionSeq().getInteractions().get(0).inferLocalChoiceSubject(fixer).toString());
				subjs.iterator().next().toString());
		fixer.setChoiceSubject(subj.toName());
		LChoice projection = AstFactoryImpl.FACTORY.LChoice(lc.getSource(), subj, blocks);
		return projection;
	}

	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		LChoice lc = (LChoice) visited;
		List<LProtocolBlock> blocks = 
				lc.getBlocks().stream().map((b) -> (LProtocolBlock) ((InlineProtocolEnv) b.del().env()).getTranslation()).collect(Collectors.toList());	
		RoleNode subj = lc.subj.clone();
		LChoice inlined = AstFactoryImpl.FACTORY.LChoice(lc.getSource(), subj, blocks);
		inl.pushEnv(inl.popEnv().setTranslation(inlined));
		return (LChoice) super.leaveProtocolInlining(parent, child, inl, lc);
	}

	@Override
	public LChoice leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		LChoice cho = (LChoice) visited;
		List<ReachabilityEnv> benvs =
				cho.getBlocks().stream().map((b) -> (ReachabilityEnv) b.del().env()).collect(Collectors.toList());
		ReachabilityEnv merged = checker.popEnv().mergeForChoice(benvs);
		checker.pushEnv(merged);
		return (LChoice) LCompoundInteractionNodeDel.super.leaveReachabilityCheck(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}

	@Override
	public void enterEGraphBuilding(ScribNode parent, ScribNode child, EGraphBuilder graph)
	{
		super.enterEGraphBuilding(parent, child, graph);
		graph.util.enterChoice();
	}

	public LChoice visitForFsmConversion(EGraphBuilder graph, LChoice child)
	{
		try
		{
			for (LProtocolBlock block : child.getBlocks())
			{
				graph.util.pushChoiceBlock();
				block.accept(graph);
				graph.util.popChoiceBlock();
			}
		}
		catch (ScribbleException e)
		{
			throw new RuntimeException("Shouldn't get in here: " + e);
		}
		return child;
	}

	@Override
	public ScribNode leaveEGraphBuilding(ScribNode parent, ScribNode child, EGraphBuilder graph, ScribNode visited) throws ScribbleException
	{
		graph.util.leaveChoice();
		return super.leaveEGraphBuilding(parent, child, graph, visited);
	}
}
