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
package org.scribble.visit.context;

import java.util.List;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LDo;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.ast.name.simple.DummyProjectionRoleNode;
import org.scribble.del.ModuleDel;
import org.scribble.main.Job;
import org.scribble.main.JobContext;
import org.scribble.main.ScribbleException;
import org.scribble.visit.context.env.UnguardedChoiceDoEnv;

// Basically infers all local choice subject candidates: if *none* are found for a given "choice-unguarded" do-call then the call is pruned (along with parent block and choice as necessary)
// i.e. prune if no choice subject candidates, because that means (passive) role is never enabled and thus not involved
// FIXME: ambiguous choice subject (i.e. > 1 candidate) is checked subsequently by ProjectedChoiceSubjectFixer -- should be better integrated (e.g. reuse ChoiceUnguardedSubprotocolChecker, rather than adhoc LInteractionNode.inferLocalChoiceSubject) -- NOTE: but cannot do all pruning and fixing in one pass, as fixing the subject roles here will interfere with the pruning algorithm (currently it looks for dummy role choices)
public class ProjectedChoiceDoPruner extends ModuleContextVisitor
{
	public ProjectedChoiceDoPruner(Job job)
	{
		super(job);
	}
	
	
	/*@Override
	protected ProjectedSubprotocolPruningEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new ProjectedSubprotocolPruningEnv();
	}*/

	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof LProtocolBlock && parent instanceof LChoice)
		{
			LChoice lc = (LChoice) parent;
			LProtocolBlock lb = (LProtocolBlock) child;
			LInteractionSeq lis = lb.getInteractionSeq();
			List<LInteractionNode> ins = lis.getInteractions();
			if (ins.get(0) instanceof LDo)  // Unlike GRecursion.prune, to-prune "do" could be followed by a continuation?
			{
				JobContext jc = this.job.getContext();
				LDo ld = (LDo) ins.get(0);
				LProtocolDecl lpd = ld.getTargetProtocolDecl(jc, getModuleContext());
				
				UnguardedChoiceDoProjectionChecker checker = new UnguardedChoiceDoProjectionChecker(
						this.job,
						((ModuleDel) jc.getModule(ld.proto.toName().getPrefix()).del()).getModuleContext(), 
						lc);
				lpd.accept(checker);
				
				if ((lc.subj instanceof DummyProjectionRoleNode) && checker.shouldCheckPrune())
				{
					UnguardedChoiceDoEnv env = (UnguardedChoiceDoEnv) lpd.def.block.del().env();  // Although AST is cloned, del is the same (HACKY?)
					if (env.subjs.isEmpty())  // Prune check
					{
						return this.job.af.LProtocolBlock(lb.getSource(),
								this.job.af.LInteractionSeq(lis.getSource(), ins.subList(1, ins.size())));  // Supports singleton case
					}
				}
			}
		}
		return super.visit(parent, child);
	}

	@Override
	//protected void subprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	public void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		//super.subprotocolEnter(parent, child);
		super.enter(parent, child);
		child.del().enterProjectedChoiceDoPruning(parent, child, this);
	}
	
	@Override
	//protected ScribNode subprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	public ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveProjectedChoiceDoPruning(parent, child, this, visited);
		//return super.subprotocolLeave(parent, child, visited);
		return super.leave(parent, child, visited);
	}
}
