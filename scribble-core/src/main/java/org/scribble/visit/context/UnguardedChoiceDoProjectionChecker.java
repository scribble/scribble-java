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

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LDo;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.SubprotocolSig;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.visit.SubprotocolVisitor;
import org.scribble.visit.context.env.UnguardedChoiceDoEnv;

// FIXME: refactor as a choice subject candidate collector (i.e. NameCollector -- thought that is an OffsetSubprotocolCollector, does that make a difference?)
public class UnguardedChoiceDoProjectionChecker extends SubprotocolVisitor<UnguardedChoiceDoEnv>
//public class ChoiceUnguardedSubprotocolChecker extends NoEnvSubprotocolVisitor
{
	//private final LChoice cho;  // Useless: subprotocovisitor visits a role-substituted clone
	
	private boolean shouldPrune = false;  // OK here, or reinstant in env?

	public UnguardedChoiceDoProjectionChecker(Job job, ModuleContext mcontext, LChoice cho)
	{
		super(job);
		setModuleContext(mcontext);
	}
	
	public void setPruneCheck()
	{
		this.shouldPrune = true;
	}
	
	public boolean shouldCheckPrune()
	{
		return this.shouldPrune;
	}
	
	@Override
	protected UnguardedChoiceDoEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new UnguardedChoiceDoEnv();
	}

	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof LChoice)
		{
			LChoice lc = (LChoice) child;
			return visitOverrideForLDoPruning(parent, lc);
		}
		return super.visit(parent, child);
	}

	private ScribNode visitOverrideForLDoPruning(ScribNode parent, LChoice lc) throws ScribbleException
	{
		for (LProtocolBlock b : lc.getBlocks())
		{
			LInteractionNode in = b.getInteractionSeq().getInteractions().get(0);
			if (in instanceof LDo)
			{
				LDo ld = (LDo) in;
				ProtocolName<?> fullname = getModuleContext().checkProtocolDeclDependencyFullName(ld.proto.toName());
				SubprotocolSig sig = new SubprotocolSig(fullname, ld.roles.getRoles(), ld.args.getArguments());
				
				if (sig.equals(getStack().get(0)))  // Cf. SubprotocolVisitor.isRootedCycle
				{
					//if (isRootedCycle())  // ChoiceUnguardedSubprotocolChecker is a (regular) SubprotocolVisitor which pushes a subprotosig on root decl entry (ProjectedSubprotocolPruner.visit)
																	// Check for "rooted" cycle to ensure it's specifically the cycle from the root proto decl (back) to the target do
																	// FIXME: but cycle to specific "target do" is not ensured: could be another instance of a do with the same subprotosig... an inherent issue of the current subprotocolvisitor framework
					// FIXME: this algorithm works for some use cases for is still wrong (prunes some that it shouldn't -- e.g. mutually pruneable choice-unguarded do's)
					// *** what we really need is to check for 0 inferred choice subjects up to recursion back (if any) to to the parent choice -- problem is current framework doesn't make identifying (e.g. ==) the original choice easy ***
					// no?: we don't need to come back to specifically the original choice: we follow the control flow into the target protocoldecl and if we encounter a recursion, regardless of whether it is back to the original choice-do or not, then it's a recursion and that's where the control flow (from this original do) will remain
					// the issue is arising since WF was relaxed to allow unbalanced choice case roles: with balanced, subject inference is always fine as long as roles are used? (and prev assumed no choice-unguarded do's?)
					
					{
						setPruneCheck();
						return lc;
					}
				}
			}
		}
		return super.visit(parent, lc);
	}

	@Override
	protected void subprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.subprotocolEnter(parent, child);
		child.del().enterUnguardedChoiceDoProjectionCheck(parent, child, this);
	}
	
	@Override
	protected ScribNode subprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveUnguardedChoiceDoProjectionCheck(parent, child, this, visited);
		return super.subprotocolLeave(parent, child, visited);
	}
}
