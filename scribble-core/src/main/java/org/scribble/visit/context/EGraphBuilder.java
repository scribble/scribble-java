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

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.del.local.LChoiceDel;
import org.scribble.del.local.LInteractionSeqDel;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.EGraphBuilderUtil;
import org.scribble.visit.NoEnvInlinedProtocolVisitor;

// Changed from offsetsubprot visitor to inlined visitor to reduce state label accumulation to rec only -- then, wfc-checking for "unguarded" recursive-do-as-continue in choice blocks handled by unfolding inlineds
// Inlined visitor, not unfolding -- but the inlined is already statically unfolded; this just means we don't do a "dynamic" unfolding as part of the AST visit
public class EGraphBuilder extends NoEnvInlinedProtocolVisitor
//public class EndpointGraphBuilder extends NoEnvUnfoldingVisitor  // Doesn't work
{
	public final EGraphBuilderUtil util;// = new EGraphBuilderUtil();
	
	public EGraphBuilder(Job job)
	{
		super(job);
		this.util = job.newEGraphBuilderUtil();
	}

	// Override visitInlinedProtocol -- not visit, or else enter/exit is lost
	@Override
	public ScribNode visitInlinedProtocol(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof LInteractionSeq)
		{
			return visitOverrideForLInteractionSeq((LProtocolBlock) parent, (LInteractionSeq) child);
		}
		else if (child instanceof LChoice)
		{
			return visitOverrideForLChoice((LInteractionSeq) parent, (LChoice) child);
		}
		else
		{
			return super.visitInlinedProtocol(parent, child);
		}
	}

	protected LInteractionSeq visitOverrideForLInteractionSeq(LProtocolBlock parent, LInteractionSeq child) throws ScribbleException
	{
		return ((LInteractionSeqDel) child.del()).visitForFsmConversion(this, child);
	}

	protected LChoice visitOverrideForLChoice(LInteractionSeq parent, LChoice child)
	{
		return ((LChoiceDel) child.del()).visitForFsmConversion(this, child);
	}

	@Override
	protected final void inlinedEnter(ScribNode parent, ScribNode child) throws ScribbleException
	//protected final void unfoldingEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.inlinedEnter(parent, child);
		//super.unfoldingEnter(parent, child);
		child.del().enterEGraphBuilding(parent, child, this);
	}
	
	@Override
	protected ScribNode inlinedLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	//protected ScribNode unfoldingLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveEGraphBuilding(parent, child, this, visited);
		return super.inlinedLeave(parent, child, visited);
		//return super.unfoldingLeave(parent, child, visited);
	}
}
