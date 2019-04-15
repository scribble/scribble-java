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
import org.scribble.core.type.name.ProtoName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.Continue;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.DisconnectAction;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.SigLit;
import org.scribble.core.type.session.Payload;
import org.scribble.core.type.session.Recursion;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.local.LAcc;
import org.scribble.core.type.session.local.LContinue;
import org.scribble.core.type.session.local.LDisconnect;
import org.scribble.core.type.session.local.LRcv;
import org.scribble.core.type.session.local.LRecursion;
import org.scribble.core.type.session.local.LReq;
import org.scribble.core.type.session.local.LSend;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.type.session.local.LType;
import org.scribble.core.visit.STypeVisitorNoThrow;
import org.scribble.util.ScribException;

// Pre: use on inlined or later (unsupported for Do, also Protocol)
// Uses EGraphBuilderUtil2 to build graph "progressively" (working graph is mutable)
// Must use build as top-level call (does EGraphBuilderUtil2.finalise -- "resets" the util)
public class EGraphBuilder extends STypeVisitorNoThrow<Local, LSeq>
{
	public final Core core;

	private EGraphBuilderUtil util;

	public EGraphBuilder(Core core)
	{
		this.core = core;
		this.util = core.newEGraphBuilderUtil();
		this.util.init(null);
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
			if (first instanceof LRecursion)  // CHECKME: do this here?  refactor into builderutil?
			{
				EState entry = util.getEntry();
				EState exit = util.getExit();

				EState nestedEntry = util.newState(Collections.emptySet());
				util.setEntry(nestedEntry);
				if (elems.size() == 1)
				{
					first.visitWithNoThrow(this);
				}	
				else
				{
					// Reuse existing b, to directly add continue-edges back to the "outer" graph
					EState nestedExit = util.newState(Collections.emptySet());
					util.setExit(nestedExit);
					first.visitWithNoThrow(this);

					util.setEntry(nestedExit);  // Must be non null
					util.setExit(exit);
					LSeq tail = new LSeq(null, elems.subList(1, elems.size()));
					tail.visitWithNoThrow(this);
				}
				EState init = nestedEntry;
				for (EAction a : (Iterable<EAction>) 
						init.getAllActions().stream().distinct()::iterator)
						// Enabling actions
				{
					for (EState s : init.getSuccessors(a))
					{
						util.addEdge(entry, a, s);
					}
				}
				
				util.setEntry(entry);
				util.setExit(exit);
			}
			else if (first instanceof LContinue)
			{
				// Cannot treat choice-unguarded-continue in "a single pass" because may not have built all recursion enacting edges yet 
				// (Single-pass building would be sensitive to order of choice block visiting)
				LContinue cont = (LContinue) first;  // First and only element
				util.addContinueEdge(util.getEntry(), cont.recvar); 
			}
			else
			{
				util.pushChoiceBlock();  // CHECKME: still needed?  LContinue doesn't check isUnguardedInChoice any more
				block.visitWithNoThrow(this);
				util.popChoiceBlock();
			}
		}
		util.leaveChoice();
		return n;
	}

	@Override
	public SType<Local, LSeq> visitContinue(Continue<Local, LSeq> n)
	{
		// CHECKME: identical edges, i.e. same pred/prev/succ (e.g. rec X { choice at A { A->B:1 } or { A->B:1 } continue X; })  
		// Choice-guarded continue -- choice-unguarded continue detected and handled in LChoice
		EState curr = this.util.getEntry();
		for (EState pred : this.util.getAllPredecessors(curr))  // Does getAllSuccessors
		{
			for (EAction a : new LinkedList<>(pred.getAllActions()))
			{
				// Following is because pred.getSuccessor doesn't support non-det edges
				// FIXME: refactor actions/successor Lists in MState to list of edges?
				for (EState succ : pred.getSuccessors(a))
				{
					if (succ.equals(curr))
					{
						try
						{
							this.util.removeEdge(pred, a, curr);  //b.removeEdgeFromPredecessor(pred, prev);
							//b.addEdge(pred, a, entry);   //b.addRecursionEdge(pred, prev, b.getRecursionEntry(this.recvar));
							this.util.addRecursionEdge(pred, a, n.recvar);
						}
						catch (ScribException e)  // CHECKME: necessary for removeEdge to have throws?
						{
							throw new RuntimeException(e);
						}
					}
				}
			}
		}
		return n;
	}

	@Override
	public SType<Local, LSeq> visitDirectedInteraction(DirectedInteraction<Local, LSeq> n)
	{
		Role peer = ((n instanceof LSend) || (n instanceof LReq)) ? n.dst
				: ((n instanceof LRcv) || (n instanceof LAcc)) ? n.src
				: null;
		if (peer == null)
		{
			throw new RuntimeException("Unknown action type: " + n);
		}
		MsgId<?> mid = n.msg.getId();
		Payload payload = n.msg.isSigLit()  // CHECKME: generalise? (e.g., hasPayload)
				? ((SigLit) n.msg).payload
				: Payload.EMPTY_PAYLOAD;
		// TODO: add toAction method to base Interaction
		EAction a = (n instanceof LSend) ? this.util.ef.newESend(peer, mid, payload)
				: (n instanceof LRcv)  ? this.util.ef.newEReceive(peer, mid, payload)
				: (n instanceof LReq)  ? this.util.ef.newERequest(peer, mid, payload)
				: //(n instanceof LAcc)  ? 
					this.util.ef.newEAccept(peer, mid, payload);  // Action type already checked above
		this.util.addEdge(this.util.getEntry(), a, this.util.getExit());
		return n;
	}

	@Override
	public SType<Local, LSeq> visitDisconnect(DisconnectAction<Local, LSeq> n)
	{
		Role peer = ((LDisconnect) n).getPeer();  // CHECKME
		EAction a = this.util.ef.newEDisconnect(peer);  // TODO: add toAction method to base Interaction
		this.util.addEdge(this.util.getEntry(), a, this.util.getExit());
		return n;
	}

	@Override
	public final <N extends ProtoName<Local>> SType<Local, LSeq> visitDo(Do<Local, LSeq, N> n)
	{
		throw new RuntimeException(this.getClass() + " unsupported for Do: " + n);
	}

	@Override
	public SType<Local, LSeq> visitRecursion(Recursion<Local, LSeq> n)
	{
		this.util.addEntryLabel(n.recvar);
		this.util.pushRecursionEntry(n.recvar, this.util.getEntry());
		n.body.visitWithNoThrow(this);
		this.util.popRecursionEntry(n.recvar);
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
		util.setEntry(entry);
		return n;
	}
}
