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
package org.scribble.visit;

import java.util.HashMap;
import java.util.Map;

import org.scribble.ast.Do;
import org.scribble.ast.InteractionSeq;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.global.GDo;
import org.scribble.ast.local.LDo;
import org.scribble.del.ProtocolDefDel;
import org.scribble.del.global.GDoDel;
import org.scribble.del.local.LDoDel;
import org.scribble.main.Job;
import org.scribble.main.JobContext;
import org.scribble.main.ScribbleException;
import org.scribble.type.SubprotocolSig;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.RecVar;
import org.scribble.visit.env.InlineProtocolEnv;

public class ProtocolDefInliner extends SubprotocolVisitor<InlineProtocolEnv>
{
	private Map<SubprotocolSig, RecVar> subprotoRecVars = new HashMap<>();  // RecVars translation for subprotocol inlining

	private Map<RecVar, Integer> recvars = new HashMap<>();  // Original RecVars, nesting count
	
	public ProtocolDefInliner(Job job)
	{
		super(job);
	}
	
	public RecVar getSubprotocolRecVar(SubprotocolSig subsig)
	{
		return this.subprotoRecVars.get(subsig);
	}

	public void setSubprotocolRecVar(SubprotocolSig subsig)
	{
		this.subprotoRecVars.put(subsig, new RecVar(newSubprotocoolRecVarId(subsig)));
	}

	public void removeSubprotocolRecVar(SubprotocolSig subsig)
	{
		this.subprotoRecVars.remove(subsig);
	}
	
	private String newSubprotocoolRecVarId(SubprotocolSig sig)
	{
		// Hacky
		return sig.toString()
				.replace('.', '_')
				.replace('<', '_')
				.replace('>', '_')
				.replace('(', '_')
				.replace(')', '_')
				.replace(' ', '_')
				.replace(',', '_');
	}
	
	@Override
	protected InlineProtocolEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new InlineProtocolEnv();
	}

	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		enter(parent, child);
		ScribNode visited = visitForSubprotocols(parent, child);
		if (visited instanceof ProtocolDecl<?>)
		{
			ProtocolDecl<?> pd = (ProtocolDecl<?>) visited;
			this.job.debugPrintln("\n[DEBUG] Inlined root protocol "
						+ pd.getFullMemberName(this.job.getContext().getModule(getModuleContext().root)) + ":\n"
						+ ((ProtocolDefDel) pd.def.del()).getInlinedProtocolDef());
		}
		return leave(parent, child, visited);
	}

	@Override
	public ScribNode visitForSubprotocols(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof Do)
		{
			return visitOverrideForDo((InteractionSeq<?>) parent, (Do<?>) child);
		}
		ScribNode visited = super.visitForSubprotocols(parent, child);
		/*if (visited instanceof ProtocolDecl<?>)
		{
			ProtocolDecl<?> pd = (ProtocolDecl<?>) visited;
			getJob().debugPrintln("\n[DEBUG] Inlined protocol "
						+ pd.getFullMemberName(getJobContext().getModule(getModuleContext().root)) + ":\n"
						+ ((ProtocolDefDel) pd.def.del()).getInlinedProtocolDef());
		}*/
		return visited;
	}

	protected Do<?> visitOverrideForDo(InteractionSeq<?> parent, Do<?> child) throws ScribbleException
	{
		if (!isCycle())
		{
			//return (GDo) super.visitForSubprotocols(parent, child);
			// Duplicated from SubprotocolVisitor#visitOverrideFoDo and modified to access discarded env -- FIXME: factor out this facility better
			JobContext jc = this.job.getContext();
			ModuleContext mc = getModuleContext();
			ProtocolDecl<? extends ProtocolKind> pd = child.getTargetProtocolDecl(jc, mc);
			ScribNode seq = applySubstitutions(pd.def.block.seq.clone(this.job.af));
			seq = seq.accept(this);
			pushEnv(popEnv().setTranslation(((InlineProtocolEnv) seq.del().env()).getTranslation()));
			return child;
		}

		// If cycle, super routine does nothing anyway, so we can just replace with new stuff here
		return (child instanceof GDo)
				? ((GDoDel) child.del()).visitForSubprotocolInlining(this, (GDo) child)
				: ((LDoDel) child.del()).visitForSubprotocolInlining(this, (LDo) child);
	}
	
	@Override
	protected void subprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.subprotocolEnter(parent, child);
		child.del().enterProtocolInlining(parent, child, this);
	}
	
	@Override
	protected ScribNode subprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveProtocolInlining(parent, child, this, visited);
		return super.subprotocolLeave(parent, child, visited);
	}

	public void pushRecVar(RecVar rv)
	{
		if (!this.recvars.containsKey(rv))
		{
			this.recvars.put(rv, 0);
		}
		else
		{
			this.recvars.put(rv, this.recvars.get(rv) + 1);
		}
	}

	public void popRecVar(RecVar rv)
	{
		Integer i = this.recvars.get(rv);
		if (i == 0)
		{
			this.recvars.remove(rv);
		}
		else
		{
			this.recvars.put(rv, i - 1);
		}
	}

	// Refactored here from NameDisambiguation
	public String getCanonicalRecVarName(RecVar rv)
	{
		//return getCanonicalRecVarName(this.getModuleContext().root, this.root.header.getDeclName(), rv.toString() + "_" + this.recvars.get(rv));
		Integer i = this.recvars.get(rv);
		return (i == 0) ? rv.toString() : (rv.toString() + i);
	}
}
