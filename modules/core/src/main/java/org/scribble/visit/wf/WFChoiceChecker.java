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
package org.scribble.visit.wf;

import java.util.HashSet;
import java.util.Set;

import org.scribble.ast.Choice;
import org.scribble.ast.InteractionSeq;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.UnfoldingVisitor;
import org.scribble.visit.wf.env.WFChoiceEnv;

// Note: now only used for choice subject and enabled checking 
// FIXME: refactor as distinct enabling messages checker (cf. GChoiceDel, WFChoicePathChecker)
public class WFChoiceChecker extends UnfoldingVisitor<WFChoiceEnv>
{
	// N.B. using pointer equality for checking if choice previously visited
	// So UnfoldingVisitor cannot visit a clone
	// equals method identity not suitable unless Ast nodes record additional info like syntactic position
	private Set<Choice<?>> visited = new HashSet<>();	
	
	public WFChoiceChecker(Job job)
	{
		super(job);
	}

	@Override
	protected WFChoiceEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new WFChoiceEnv(new HashSet<>(pd.header.roledecls.getRoles()),
				!(pd.isGlobal() && ((GProtocolDecl) pd).modifiers.contains(GProtocolDecl.Modifiers.EXPLICIT)));  // FIXME: consider locals; also, explicit modifier should be carried over to local projections
	}

	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof GProtocolDecl)
		{
			GProtocolDecl gpd = (GProtocolDecl) child;
			if (gpd.isAuxModifier())
			{
				return child;  // bypass aux protocols  // FIXME: integrate bypass functionality into made enter/visit/leave pattern
			}
		}

		if (this.job.useOldWf)
		{
			if (child instanceof Choice<?>)  // Only needed for old WF (for distinct enabling message checking)  // FIXME: maybe move connectedness checking to a separate pass, i.e. vanilla UnfoldingVisitor (if retained as syntactic check)
			{
				return visitOverrideForChoice((InteractionSeq<?>) parent, (Choice<?>) child);
			}
		}
		return super.visit(parent, child);
	}

	private ScribNode visitOverrideForChoice(InteractionSeq<?> parent, Choice<?> child) throws ScribbleException
	{
		if (child instanceof Choice<?>)
		{
			Choice<?> cho = (Choice<?>) child;
			if (!this.visited.contains(cho))  // ** Old WF breaks connectedness checking, e.g. rec X { choice at A { connect A to B; continue X; } } because of choice visit pruning
			{
				this.visited.add(cho);
				//ScribNode n = cho.visitChildren(this);
				ScribNode n = super.visit(parent, child);
				this.visited.remove(cho);
				return n;
			}
			else
			{
				return cho;
			}
		}
		else
		{
			return super.visit(parent, child);
		}
	}
	
	@Override
	protected void unfoldingEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.unfoldingEnter(parent, child);
		child.del().enterInlinedWFChoiceCheck(parent, child, this);
	}
	
	@Override
	protected ScribNode unfoldingLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveInlinedWFChoiceCheck(parent, child, this, visited);
		return super.unfoldingLeave(parent, child, visited);
	}
}
