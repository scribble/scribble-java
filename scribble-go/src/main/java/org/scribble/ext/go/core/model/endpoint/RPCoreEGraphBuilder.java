package org.scribble.ext.go.core.model.endpoint;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.ext.go.core.ast.RPCoreMessage;
import org.scribble.ext.go.core.ast.RPCoreRecVar;
import org.scribble.ext.go.core.ast.local.RPCoreLActionKind;
import org.scribble.ext.go.core.ast.local.RPCoreLChoice;
import org.scribble.ext.go.core.ast.local.RPCoreLDotChoice;
import org.scribble.ext.go.core.ast.local.RPCoreLEnd;
import org.scribble.ext.go.core.ast.local.RPCoreLMultiChoices;
import org.scribble.ext.go.core.ast.local.RPCoreLRec;
import org.scribble.ext.go.core.ast.local.RPCoreLType;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EGraphBuilderUtil;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.Payload;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.RecVar;

public class RPCoreEGraphBuilder
{
	
	private final GoJob job;
	private final EGraphBuilderUtil util;  // Not using any features for unguarded choice/recursion/continue (recursion manually tracked here)
	
	public RPCoreEGraphBuilder(GoJob job)
	{
		this.job = job;
		this.util = new EGraphBuilderUtil(job.ef);
	}
	
	public EGraph build(RPCoreLType lt)
	{
		this.util.init(this.job.ef.newEState(Collections.emptySet()));
		build(lt, this.util.getEntry(), this.util.getExit(), new HashMap<>());
		return this.util.finalise();
	}
	
	private void build(RPCoreLType lt, EState s1, EState s2, Map<RecVar, EState> recs)
	{
		if (lt instanceof RPCoreLChoice)
		{
			if (lt instanceof RPCoreLMultiChoices)
			{
				RPCoreLMultiChoices lc = (RPCoreLMultiChoices) lt;
				List<MessageId<?>> mids = lc.cases.keySet().stream().map(x -> x.op).collect(Collectors.toList());
				List<Payload> pays = lc.cases.keySet().stream().map(x -> x.pay).collect(Collectors.toList());
				buildMultiChoicesEdgeAndContinuation(s1, s2, recs, lc.role, lc.getKind(), mids, pays, lc.getContinuation());
						// N.B. this directly constructs a single continuation for the multichoices-receiver
						// cf. multichoices sender side, standard cross-send with separate (but identical) continuations -- could also build single contination for sender by introducing explicit multichoices-send constructor
			}
			else
			{
				RPCoreLChoice lc = (RPCoreLChoice) lt;
				RPIndexExpr offset = (lc instanceof RPCoreLDotChoice)
						? ((RPCoreLDotChoice) lc).offset
						: null;
				lc.cases.entrySet().stream().forEach(e ->
					buildEdgeAndContinuation(s1, s2, recs, lc.role, lc.getKind(), e.getKey(), e.getValue(), offset)
				);
			}
		}
		else if (lt instanceof RPCoreLRec)
		{
			RPCoreLRec lr = (RPCoreLRec) lt;
			Map<RecVar, EState> tmp = new HashMap<>(recs);
			tmp.put(lr.recvar, s1);
			build(lr.body, s1, s2, tmp);
		}
		else
		{
			throw new RuntimeException("[param-core] Shouldn't get in here: " + lt);
		}
	}

	private void buildEdgeAndContinuation(EState s1, EState s2, Map<RecVar, EState> recs, 
			RPIndexedRole r, RPCoreLActionKind k, RPCoreMessage a, RPCoreLType cont, RPIndexExpr offset)
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
			EState s = this.util.ef.newEState(Collections.emptySet());  
			this.util.addEdge(s1, ea, s);
			build(cont, s, s2, recs);
		}
	}

	// FIXME: factor out with above
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
	}
	
	private EAction toEAction(RPIndexedRole r, RPCoreLActionKind k, RPCoreMessage a, RPIndexExpr offset)
	{
		RPCoreEModelFactory ef = (RPCoreEModelFactory) this.util.ef;  // FIXME: factor out
		if (k.equals(RPCoreLActionKind.CROSS_SEND))
		{
			return ef.newParamCoreECrossSend(r, a.op, a.pay);

		}
		else if (k.equals(RPCoreLActionKind.CROSS_RECEIVE))
		{
			return ef.newParamCoreECrossReceive(r, a.op, a.pay);
		}
		else if (k.equals(RPCoreLActionKind.DOT_SEND))
		{
			return ef.newParamCoreEDotSend(r, offset, a.op, a.pay);
		}
		else if (k.equals(RPCoreLActionKind.DOT_RECEIVE))
		{
			return ef.newParamCoreEDotReceive(r, offset, a.op, a.pay);
		}
		/*else if (k.equals(ParamCoreLActionKind.MULTICHOICES_RECEIVE))
		{
			return ef.newParamCoreEMultiChoicesReceive(r, a.op, a.pay);
		}*/
		else
		{
			throw new RuntimeException("[param-core] Shouldn't get in here: " + k);
		}
	}
}
