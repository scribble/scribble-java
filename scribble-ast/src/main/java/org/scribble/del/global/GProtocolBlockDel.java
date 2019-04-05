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

import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.del.ProtocolBlockDel;
import org.scribble.del.ScribDelBase;
import org.scribble.util.ScribException;
import org.scribble.visit.GTypeTranslator;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.context.Projector;
import org.scribble.visit.context.env.ProjectionEnv;
import org.scribble.visit.env.InlineProtocolEnv;

public class GProtocolBlockDel extends ProtocolBlockDel implements GDel
{
	
	@Override
	public GSeq translate(ScribNode n, GTypeTranslator t)
			throws ScribException
	{
		return (GSeq) ((GProtocolBlock) n).getInteractSeqChild().visitWith(t);
	}

	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child,
			ProtocolDefInliner inl, ScribNode visited) throws ScribException
	{
		GProtocolBlock gpb = (GProtocolBlock) visited;
		GInteractionSeq seq = (GInteractionSeq) ((InlineProtocolEnv) gpb
				.getInteractSeqChild().del().env()).getTranslation();
		GProtocolBlock inlined = 
				inl.job.config.af.GProtocolBlock(gpb.getSource(), seq);
		inl.pushEnv(inl.popEnv().setTranslation(inlined));
		return (GProtocolBlock) ScribDelBase.popAndSetVisitorEnv(this, inl, gpb);
	}
	
	@Override
	public void enterProjection(ScribNode parent, ScribNode child, Projector proj)
			throws ScribException
	{
		ScribDelBase.pushVisitorEnv(this, proj);
	}
	
	@Override
	public GProtocolBlock leaveProjection(ScribNode parent, ScribNode child,
			Projector proj, ScribNode visited) throws ScribException
	{
		GProtocolBlock gpb = (GProtocolBlock) visited;
		LInteractionSeq seq =
				(LInteractionSeq) ((ProjectionEnv) gpb.getInteractSeqChild().del()
						.env()).getProjection();
				//((GInteractionSeqDel) gpb.seq.del()).project(gpb.getInteractionSeq(), self);	
		LProtocolBlock projection = 
				gpb.project(proj.job.config.af, proj.peekSelf(), seq);
		proj.pushEnv(proj.popEnv().setProjection(projection));
		return (GProtocolBlock) ScribDelBase.popAndSetVisitorEnv(this, proj, gpb);
	}
}
