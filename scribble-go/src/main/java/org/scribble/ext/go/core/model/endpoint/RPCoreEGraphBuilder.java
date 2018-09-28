package org.scribble.ext.go.core.model.endpoint;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.scribble.ext.go.core.ast.RPCoreForeach;
import org.scribble.ext.go.core.ast.RPCoreRecVar;
import org.scribble.ext.go.core.ast.local.RPCoreLActionKind;
import org.scribble.ext.go.core.ast.local.RPCoreLChoice;
import org.scribble.ext.go.core.ast.local.RPCoreLCont;
import org.scribble.ext.go.core.ast.local.RPCoreLEnd;
import org.scribble.ext.go.core.ast.local.RPCoreLForeach;
import org.scribble.ext.go.core.ast.local.RPCoreLRec;
import org.scribble.ext.go.core.ast.local.RPCoreLType;
import org.scribble.ext.go.core.type.RPAnnotatedInterval;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EGraphBuilderUtil;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.Message;
import org.scribble.type.MessageSig;
import org.scribble.type.Payload;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.RecVar;

public class RPCoreEGraphBuilder
{
	
	private final GoJob job;
	private final RPCoreEModelFactory ef;
	private final EGraphBuilderUtil util;  // Not using any features for unguarded choice/recursion/continue (recursion manually tracked here)
	
	public RPCoreEGraphBuilder(GoJob job)
	{
		this.job = job;
		this.ef = (RPCoreEModelFactory) job.ef;
		this.util = new EGraphBuilderUtil(job.ef);
	}
	
	/*private Pair<RPCoreEState, RPCoreLType> makeRPCoreEState(RPCoreLType lt)
	{
		RPCoreEState s;
		RPCoreLType seq;
		if (lt instanceof RPCoreLForeach)
		{
			RPCoreLForeach fe = (RPCoreLForeach) lt;
			s = this.ef.newRPCoreEState(Collections.emptySet(), fe.var,
					new RPInterval(fe.start, fe.end), (RPCoreEState) build(((RPCoreLForeach) lt).body).init);
			if (fe.seq instanceof RPCoreForeach)  // FIXME: consecutive foreach ("tau" action)
			{
				throw new RuntimeException("[rp-core] TODO: " + lt);
			}
			seq = fe.seq;
		}
		else
		{
			s = this.ef.newRPCoreEState(Collections.emptySet(), null, null, null);
			seq = lt;
		}
		return new Pair<>(s, seq);
	}*/
	
	public EGraph build(RPCoreLType lt)
	{
		/*Pair<RPCoreEState, RPCoreLType> p = makeRPCoreEState(lt);  // Currently always a top-level rec
		this.util.init(p.left);
		build(p.right, this.util.getEntry(), this.util.getExit(), new HashMap<>());*/
		this.util.init(this.ef.newEState(Collections.emptySet()));  // RPCoreEState
		build(lt, (RPCoreEState) this.util.getEntry(),
				(RPCoreEState) this.util.getExit(), new HashMap<>());  // lt is always a top-level rec
		EGraph foo = this.util.finalise();
		return foo;
	}
	
	// s1 is "current state", which may be mutably modified by foreach (and rec -- GraphBuilerUtil#addEntryLabel done by LRecursionDel)
	private void build(RPCoreLType lt, RPCoreEState s1, RPCoreEState s2, Map<RecVar, RPCoreEState> recs)
	{
		if (lt instanceof RPCoreLForeach)
		{
			RPCoreLForeach fe = (RPCoreLForeach) lt;
			RPCoreEState s = (RPCoreEState) s1;  // FIXME: modify method sig
			if (fe.ivals.size() > 1)
			{
				throw new RuntimeException("[rp-core] TODO: " + fe.ivals);
			}
			RPAnnotatedInterval iv = fe.ivals.stream().findFirst().get();
			s.setParam(iv.var);
			s.setInterval(new RPInterval(iv.start, iv.end));
			RPCoreEGraphBuilder nested = new RPCoreEGraphBuilder(this.job);
			s.setNested((RPCoreEState) nested.build(fe.body).init);
			if (fe.seq instanceof RPCoreForeach)  // FIXME: consecutive foreach ("tau" action)
			{
				throw new RuntimeException("[rp-core] TODO: " + lt);
			}
			else if (!(fe.seq instanceof RPCoreLEnd))
			{
				build(fe.seq, s1, s2, recs);
			}
		}
		else if (lt instanceof RPCoreLChoice)
		{
			/*if (lt instanceof RPCoreLMultiChoices)
			{
				RPCoreLMultiChoices lc = (RPCoreLMultiChoices) lt;
				List<MessageId<?>> mids = lc.cases.keySet().stream().map(x -> x.op).collect(Collectors.toList());
				List<Payload> pays = lc.cases.keySet().stream().map(x -> x.pay).collect(Collectors.toList());
				buildMultiChoicesEdgeAndContinuation(s1, s2, recs, lc.role, lc.getKind(), mids, pays, lc.getContinuation());
						// N.B. this directly constructs a single continuation for the multichoices-receiver
						// cf. multichoices sender side, standard cross-send with separate (but identical) continuations -- could also build single contination for sender by introducing explicit multichoices-send constructor
			}
			else*/
			{
				RPCoreLChoice lc = (RPCoreLChoice) lt;
				RPIndexExpr offset = //(lc instanceof RPCoreLDotChoice) ? ((RPCoreLDotChoice) lc).offset :
						null;
				lc.cases.entrySet().forEach(e ->
					buildEdgeAndContinuation(s1, s2, recs, lc.role, lc.getKind(), e.getKey(), e.getValue(), offset)
				);
			}
		}
		else if (lt instanceof RPCoreLRec)
		{
			RPCoreLRec lr = (RPCoreLRec) lt;
			Map<RecVar, RPCoreEState> tmp = new HashMap<>(recs);
			tmp.put(lr.recvar, s1);
			build(lr.body, s1, s2, tmp);
		}
		else if (lt instanceof RPCoreLCont)
		{
			// skip
		}
		else
		{
			throw new RuntimeException("[rp-core] Shouldn't get in here: " + lt);
		}
	}

	// CHECKME: offset redundant?
	private void buildEdgeAndContinuation(RPCoreEState s1, RPCoreEState s2, Map<RecVar, RPCoreEState> recs, 
			RPIndexedRole r, RPCoreLActionKind k, //RPCoreMessage a,
			Message a,
			RPCoreLType cont, RPIndexExpr offset)
	{
		EAction ea = toEAction(r, k, a, offset);
		if (cont instanceof RPCoreLEnd)
		{
			this.util.addEdge(s1, ea, s2);
		}
		else if (cont instanceof RPCoreRecVar)
		{
			EState s = recs.get(((RPCoreRecVar<?>) cont).recvar);
			this.util.addEdge(s1, ea, s);
		}
		else
		{
			RPCoreEState s = this.ef.newEState(Collections.emptySet());  
			this.util.addEdge(s1, ea, s);
			build(cont, s, s2, recs);
		}
	}

	/*// FIXME: factor out with above
	private void buildMultiChoicesEdgeAndContinuation(EState s1, EState s2, Map<RecVar, EState> recs, 
			RPIndexedRole r, RPCoreLActionKind k, List<MessageId<?>> mids, List<Payload> pays, RPCoreLType cont)
	{
		EAction ea = ((RPCoreEModelFactory) this.util.ef).newParamCoreEMultiChoicesReceive(r, mids, pays);
		if (cont instanceof RPCoreLEnd)
		{
			this.util.addEdge(s1, ea, s2);
		}
		else if (cont instanceof RPCoreRecVar)
		{
			EState s = recs.get(((RPCoreRecVar<?>) cont).recvar);
			this.util.addEdge(s1, ea, s);
		}
		else
		{
			EState s = this.util.ef.newEState(Collections.emptySet());  
			this.util.addEdge(s1, ea, s);
			build(cont, s, s2, recs);
		}
	}*/
	
	private EAction toEAction(RPIndexedRole r, RPCoreLActionKind k, //RPCoreMessage a,
			Message a,
			RPIndexExpr offset)
	{
		RPCoreEModelFactory ef = (RPCoreEModelFactory) this.util.ef;  // FIXME: factor out

		// Cf. LSendDel#leaveEGraphBuilding
		MessageId<?> mid = a.getId();
		Payload pay = (a instanceof MessageSig)  // Hacky?
					? ((MessageSig) a).payload
					: Payload.EMPTY_PAYLOAD;

		if (k.equals(RPCoreLActionKind.CROSS_SEND))
		{
			return ef.newParamCoreECrossSend(r, mid, pay);

		}
		else if (k.equals(RPCoreLActionKind.CROSS_RECEIVE))
		{
			return ef.newParamCoreECrossReceive(r, mid, pay);
		}
		/*else if (k.equals(RPCoreLActionKind.DOT_SEND))
		{
			return ef.newParamCoreEDotSend(r, offset, mid, pay);
		}
		else if (k.equals(RPCoreLActionKind.DOT_RECEIVE))
		{
			return ef.newParamCoreEDotReceive(r, offset, mid, pay);
		}
		else if (k.equals(ParamCoreLActionKind.MULTICHOICES_RECEIVE))
		{
			return ef.newParamCoreEMultiChoicesReceive(r, mid, pay);
		}*/
		else
		{
			throw new RuntimeException("[rp-core] Shouldn't get in here: " + k);
		}
	}
}
