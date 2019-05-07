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
package org.scribble.core.visit.local;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.scribble.core.job.Core;
import org.scribble.core.model.endpoint.EGraph;
import org.scribble.core.model.endpoint.EGraphBuilderUtil;
import org.scribble.core.model.endpoint.EState;
import org.scribble.core.model.endpoint.actions.EAction;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.MsgId;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.Continue;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.DisconnectAction;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.Payload;
import org.scribble.core.type.session.Recursion;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.SigLit;
import org.scribble.core.type.session.local.LAcc;
import org.scribble.core.type.session.local.LContinue;
import org.scribble.core.type.session.local.LDisconnect;
import org.scribble.core.type.session.local.LRecursion;
import org.scribble.core.type.session.local.LRecv;
import org.scribble.core.type.session.local.LReq;
import org.scribble.core.type.session.local.LSend;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.type.session.local.LType;
import org.scribble.core.visit.STypeVisitorNoThrow;

// Pre: use on inlined or later (unsupported for Do, also Protocol)
// Uses EGraphBuilderUtil2 to build graph "progressively" (working graph is mutable)
// Must use build as top-level call (does EGraphBuilderUtil2.finalise -- "resets" the util)
public class EGraphBuilder extends STypeVisitorNoThrow<Local, LSeq>
{
	public final Core core;

	private EGraphBuilderUtil util;

	protected EGraphBuilder(Core core)
	{
		this.core = core;
		this.util = core.config.mf.local.EGraphBuilderUtil();
	}
	
	public EGraph finalise()
	{
		return this.util.finalise();
	}	
	
	@Override
	public SType<Local, LSeq> visitChoice(Choice<Local, LSeq> n)
	{
		EGraphBuilderUtil util = this.util;
		util.enterChoice();
		for (LSeq block : n.blocks)
		{
			List<LType> elems = block.getElements();
			LType first = elems.get(0);
			if (first instanceof LContinue)
			{
				// Cannot treat choice-unguarded-continue in "a single pass", because may not have built all recursion enacting edges yet 
				// (Single-pass building would be sensitive to order of choice block visiting)
				LContinue cont = (LContinue) first;  // First and only element
				util.addUnguardedContinueEdge(util.getEntry(), cont.recvar); 
			}
			else if (first instanceof LRecursion)  // CHECKME: do this here?  refactor into builderutil?
			{
				buildUnguardedRecursion(util, elems);
			}
			else
			{
				util.enterChoiceBlock();
				block.visitWithNoThrow(this);
				util.leaveChoiceBlock();
			}
		}
		util.leaveChoice();
		return n;
	}

	// Recursion is first element of choice case
	private void buildUnguardedRecursion(EGraphBuilderUtil util,
			List<LType> elems)
	{
		LRecursion first = (LRecursion) elems.get(0);
		EState entry = util.getEntry();

		EState nestedEntry = util.newState(Collections.emptySet());
		util.setEntry(nestedEntry);
		if (elems.size() == 1)  // Cf. visitSeq last element or not
		{
			first.visitWithNoThrow(this);
		}	
		else
		{
			EState exit = util.getExit();
			EState nestedExit = util.newState(Collections.emptySet());
			util.setExit(nestedExit);
			first.visitWithNoThrow(this);  // entry to nestedExit -- reuse existing builder, to directly add continue-edges back to the "outer" graph

			util.setEntry(nestedExit);  // Must be non null
			util.setExit(exit);
			LSeq tail = this.core.config.tf.local.LSeq(null,
					elems.subList(1, elems.size()));
			tail.visitWithNoThrow(this);  // nestedExit to exit
		}

		Iterator<EAction> as = nestedEntry.getActions().iterator();  // "Enacting" actions
		Iterator<EState> succs = nestedEntry.getSuccs().iterator();
		while (as.hasNext())
		{
			EAction a = as.next();
			EState succ = succs.next();
			util.addEdge(entry, a, succ);  // Add edges from original entry into "nested" graph, offset by enacting to nestedEntry succs
		}
		
		util.setEntry(entry);  // EGraphBuilderUtil contract, entry on leaving a node should be the same as on entering
	}

	@Override
	public SType<Local, LSeq> visitContinue(Continue<Local, LSeq> n)
	{
		// Choice-guarded continue -- choice-unguarded continue detected and handled above in visitChoice
		EState curr = this.util.getEntry();
		for (EState pred : this.util.getPreds(curr))  // Does getSuccs (i.e., gets all), e.g., choice sequenced to continue
		{
			// CHECKME: identical edges? i.e. same pred/prev/succ (e.g. rec X { choice at A { A->B:1 } or { A->B:1 } continue X; })  
			for (EAction a : new LinkedList<>(pred.getActions()))
			{
				// Following caters for non-det edges (to different succs)
				for (EState succ : pred.getSuccs(a))
				{
					if (succ.equals(curr))
					{
						this.util.fixContinueEdge(pred, a, curr, n.recvar);
					}
				}
			}
		}
		return n;
	}

	@Override
	public SType<Local, LSeq> visitDirectedInteraction(
			DirectedInteraction<Local, LSeq> n)
	{
		Role peer = ((n instanceof LSend) || (n instanceof LReq)) ? n.dst  // CHECKME: refactor LType getSelf/Peer? cf. ast
				: ((n instanceof LRecv) || (n instanceof LAcc)) ? n.src
				: null;
		if (peer == null)
		{
			throw new RuntimeException("Unknown action type: " + n);
		}
		MsgId<?> mid = n.msg.getId();
		Payload pay = n.msg.isSigLit()  // CHECKME: generalise? (e.g., hasPayload)
				? ((SigLit) n.msg).payload
				: Payload.EMPTY_PAYLOAD;
		// TODO: add toAction method to BasicInteraction (cf. toName methods of NameNodes)
		EAction a = (n instanceof LSend) ? this.util.mf.local.ESend(peer, mid, pay)
				: (n instanceof LRecv) ? this.util.mf.local.ERecv(peer, mid, pay)
				: (n instanceof LReq) ? this.util.mf.local.EReq(peer, mid, pay)
				: //(n instanceof LAcc) ? 
					this.util.mf.local.EAcc(peer, mid, pay);  // Action type already checked above
		this.util.addEdge(this.util.getEntry(), a, this.util.getExit());
		return n;
	}

	@Override
	public SType<Local, LSeq> visitDisconnect(DisconnectAction<Local, LSeq> n)
	{
		Role peer = ((LDisconnect) n).getPeer();  // CHECKME -- ?
		EAction a = this.util.mf.local.EDisconnect(peer);  // TODO: add toAction method to BasicInteraction
		this.util.addEdge(this.util.getEntry(), a, this.util.getExit());
		return n;
	}

	@Override
	public final SType<Local, LSeq> visitDo(Do<Local, LSeq> n)
	{
		throw new RuntimeException(this.getClass() + " unsupported for Do: " + n);
	}

	@Override
	public SType<Local, LSeq> visitRecursion(Recursion<Local, LSeq> n)
	{
		this.util.addEntryLabel(n.recvar);
		this.util.enterRecursion(n.recvar, this.util.getEntry());
		n.body.visitWithNoThrow(this);
		this.util.leaveRecursion(n.recvar);
		return n;
	}

	@Override
	public LSeq visitSeq(LSeq n)
	{
		EGraphBuilderUtil util = this.util;
		EState entry = util.getEntry();
		EState exit = util.getExit();
		List<LType> elems = n.getElements();
		if (elems.isEmpty())
		{
			throw new RuntimeException("Shouldn't get here: " + this);
		}
		for (Iterator<LType> i = n.getElements().iterator(); i.hasNext(); )
		{
			LType next = i.next();
			if (!i.hasNext())
			{
				util.setExit(exit);
				next.visitWithNoThrow(this);
			}
			else
			{
				EState tmp = util.newState(Collections.emptySet());
				util.setExit(tmp);
				next.visitWithNoThrow(this);
				util.setEntry(util.getExit());
						// CHECKME: exit may not be tmp, entry/exit can be modified, e.g. continue
			}
		}
		util.setEntry(entry);  // EGraphBuilderUtil contract, entry on leaving a node should be the same as on entering
		return n;
	}
}
