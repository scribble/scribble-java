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

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GInteractionNode;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LNode;
import org.scribble.del.InteractionSeqDel;
import org.scribble.del.ScribDelBase;
import org.scribble.job.ScribbleException;
import org.scribble.lang.global.GSeq;
import org.scribble.lang.global.GType;
import org.scribble.lang.global.GTypeTranslator;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.context.Projector;
import org.scribble.visit.context.RecRemover;
import org.scribble.visit.context.env.ProjectionEnv;
import org.scribble.visit.env.InlineProtocolEnv;

public class GInteractionSeqDel extends InteractionSeqDel implements GDel
{
	
	@Override
	public GSeq translate(ScribNode n, GTypeTranslator t)
			throws ScribbleException
	{
		GInteractionSeq source = (GInteractionSeq) n;
		List<GType> elems = new LinkedList<>();
		for (GInteractionNode c : source.getInteractNodeChildren())
		{
			elems.add(c.visitWith(t));  // throws ScribbleException
		}
		return new GSeq(source, elems);
	}

	// enter in super
	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child,
			ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		GInteractionSeq gis = (GInteractionSeq) visited;
		List<GInteractionNode> gins = new LinkedList<GInteractionNode>();
		for (GInteractionNode gi : gis.getInteractNodeChildren())
		{
			ScribNode inlined = ((InlineProtocolEnv) gi.del().env()).getTranslation();
			if (inlined instanceof GInteractionSeq)  // A do got inlined
			{
				gins.addAll(((GInteractionSeq) inlined).getInteractNodeChildren());
			}
			else
			{
				gins.add((GInteractionNode) inlined);
			}
		}
		GInteractionSeq inlined = inl.job.config.af.GInteractionSeq(gis.getSource(),
				gins);
		inl.pushEnv(inl.popEnv().setTranslation(inlined));
		return (GInteractionSeq) ScribDelBase.popAndSetVisitorEnv(this, inl, gis);
	}

	@Override
	public void enterProjection(ScribNode parent, ScribNode child, Projector proj)
			throws ScribbleException
	{
		ScribDelBase.pushVisitorEnv(this, proj);  // Unlike WF-choice and Reachability, Projection uses an Env for InteractionSequences
	}
	
	@Override
	public GInteractionSeq leaveProjection(ScribNode parent, ScribNode child,
			Projector proj, ScribNode visited) throws ScribbleException
	{
		GInteractionSeq gis = (GInteractionSeq) visited;
		List<LInteractionNode> lis = new LinkedList<>();
		for (GInteractionNode gi : gis.getInteractNodeChildren())  // FIXME: rewrite using flatMap
		{
			LNode ln = (LNode) ((ProjectionEnv) gi.del().env()).getProjection();
			//LNode ln = ((GInteractionNodeDel) gi.del()).project(gi, self);  // FIXME: won't work for do
			// TODO: move node-specific projects to G nodes (not dels) and take child projections as params, bit like reconstruct
			if (ln instanceof LInteractionSeq)  // Self comm sequence
			{
				lis.addAll(((LInteractionSeq) ln).getInteractNodeChildren());
			}
			else if (ln != null) // null is used for empty projection
			{
				lis.add((LInteractionNode) ln);
			}
		}
		/*if (lis.size() == 1)  // NO: needed for e.g. rec X { 1() from A to B; choice at A { continue X; } or { 2() from A to B; } } -- do instead in GRecursion
		{
			if (lis.get(0) instanceof Continue)
			{
				lis.clear();
			}
		}*/
		LInteractionSeq projection = gis.project(proj.job.config.af,
				proj.peekSelf(), lis);
		proj.pushEnv(proj.popEnv().setProjection(projection));
		return (GInteractionSeq) ScribDelBase.popAndSetVisitorEnv(this, proj, gis);
	}
	
	/*@Override
	public GInteractionSeq leaveF17Parsing(ScribNode parent, ScribNode child, F17Parser parser, ScribNode visited) throws ScribbleException
	{
		GInteractionSeq gis = (GInteractionSeq) visited;
		List<GInteractionNode> gins = gis.getInteractions();
		Iterator<GInteractionNode> i = gins.iterator();
		while (i.hasNext())
		{
			GInteractionNode gin = i.next();
			if (!i.hasNext())
			{
				break;
			}
			/*F17ParserEnv env = (F17ParserEnv) gin.del().env();
			if (env.isUnguarded())
			{
				throw new ScribbleException("[FASE17] unguarded choice case: " + gin);
			}* /
			if (!(gin instanceof GMessageTransfer || gin instanceof GConnect || gin instanceof GDisconnect))
			{
				throw new ScribbleException("[FASE17] Bad sequence composition following:\n" + gin);
			}
		}
		return gis;
	}*/
	
	@Override
	public GInteractionSeq leaveRecRemoval(ScribNode parent, ScribNode child,
			RecRemover rem, ScribNode visited) throws ScribbleException
	{
		GInteractionSeq gis = (GInteractionSeq) visited;
		List<GInteractionNode> gins = gis.getInteractNodeChildren().stream()
				.flatMap((gi) -> (gi instanceof GRecursion
						&& rem.toRemove(((GRecursion) gi).getRecVarChild().toName()))
								? ((GRecursion) gi).getBlockChild().getInteractSeqChild()
										.getInteractNodeChildren().stream()
								: Stream.of(gi))
				.collect(Collectors.toList());
		return rem.job.config.af.GInteractionSeq(gis.getSource(), gins);
	}
}
