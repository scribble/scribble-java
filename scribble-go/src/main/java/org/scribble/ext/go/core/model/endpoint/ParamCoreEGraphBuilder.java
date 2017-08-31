package org.scribble.ext.go.core.model.endpoint;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.ext.go.core.ast.ParamCoreMessage;
import org.scribble.ext.go.core.ast.ParamCoreRecVar;
import org.scribble.ext.go.core.ast.local.ParamCoreLActionKind;
import org.scribble.ext.go.core.ast.local.ParamCoreLChoice;
import org.scribble.ext.go.core.ast.local.ParamCoreLDotChoice;
import org.scribble.ext.go.core.ast.local.ParamCoreLEnd;
import org.scribble.ext.go.core.ast.local.ParamCoreLMultiChoices;
import org.scribble.ext.go.core.ast.local.ParamCoreLRec;
import org.scribble.ext.go.core.ast.local.ParamCoreLType;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.ext.go.main.ParamJob;
import org.scribble.ext.go.type.index.ParamIndexExpr;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EGraphBuilderUtil;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.Payload;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.RecVar;

public class ParamCoreEGraphBuilder
{
	
	private final ParamJob job;
	private final EGraphBuilderUtil util;  // Not using any features for unguarded choice/recursion/continue (recursion manually tracked here)
	
	public ParamCoreEGraphBuilder(ParamJob job)
	{
		this.job = job;
		this.util = new EGraphBuilderUtil(job.ef);
	}
	
	public EGraph build(ParamCoreLType lt)
	{
		this.util.init(this.job.ef.newEState(Collections.emptySet()));
		build(lt, this.util.getEntry(), this.util.getExit(), new HashMap<>());
		return this.util.finalise();
	}
	
	private void build(ParamCoreLType lt, EState s1, EState s2, Map<RecVar, EState> recs)
	{
		if (lt instanceof ParamCoreLChoice)
		{
			if (lt instanceof ParamCoreLMultiChoices)
			{
				ParamCoreLMultiChoices lc = (ParamCoreLMultiChoices) lt;
				/*lc.cases.entrySet().stream().forEach(e ->  // FIXME: all conts are syntactically the same, so implicitly do the merge here?
					buildEdgeAndContinuation(s1, s2, recs, lc.role, lc.getKind(), e.getKey(), e.getValue(), null)
				);*/
				List<MessageId<?>> mids = lc.cases.keySet().stream().map(x -> x.op).collect(Collectors.toList());
				List<Payload> pays = lc.cases.keySet().stream().map(x -> x.pay).collect(Collectors.toList());
				buildMultiChoicesEdgeAndContinuation(s1, s2, recs, lc.role, lc.getKind(), mids, pays, lc.getContinuation());
			}
			else
			{
				ParamCoreLChoice lc = (ParamCoreLChoice) lt;
				ParamIndexExpr offset = (lc instanceof ParamCoreLDotChoice)
						? ((ParamCoreLDotChoice) lc).offset
						: null;
				lc.cases.entrySet().stream().forEach(e ->
					buildEdgeAndContinuation(s1, s2, recs, lc.role, lc.getKind(), e.getKey(), e.getValue(), offset)
				);
			}
		}
		else if (lt instanceof ParamCoreLRec)
		{
			ParamCoreLRec lr = (ParamCoreLRec) lt;
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
			ParamRole r, ParamCoreLActionKind k, ParamCoreMessage a, ParamCoreLType cont, ParamIndexExpr offset)
	{
		EAction ea = toEAction(r, k, a, offset);
		if (cont instanceof ParamCoreLEnd)
		{
			this.util.addEdge(s1, ea, s2);
		}
		else if (cont instanceof ParamCoreRecVar)
		{
			EState s = recs.get(((ParamCoreRecVar<?>) cont).recvar);
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
			ParamRole r, ParamCoreLActionKind k, List<MessageId<?>> mids, List<Payload> pays, ParamCoreLType cont)
	{
		EAction ea = ((ParamCoreEModelFactory) this.util.ef).newParamCoreEMultiChoicesReceive(r, mids, pays);
		if (cont instanceof ParamCoreLEnd)
		{
			this.util.addEdge(s1, ea, s2);
		}
		else if (cont instanceof ParamCoreRecVar)
		{
			EState s = recs.get(((ParamCoreRecVar<?>) cont).recvar);
			this.util.addEdge(s1, ea, s);
		}
		else
		{
			EState s = this.util.ef.newEState(Collections.emptySet());  
			this.util.addEdge(s1, ea, s);
			build(cont, s, s2, recs);
		}
	}
	
	private EAction toEAction(ParamRole r, ParamCoreLActionKind k, ParamCoreMessage a, ParamIndexExpr offset)
	{
		ParamCoreEModelFactory ef = (ParamCoreEModelFactory) this.util.ef;  // FIXME: factor out
		if (k.equals(ParamCoreLActionKind.CROSS_SEND))
		{
			return ef.newParamCoreECrossSend(r, a.op, a.pay);

		}
		else if (k.equals(ParamCoreLActionKind.CROSS_RECEIVE))
		{
			return ef.newParamCoreECrossReceive(r, a.op, a.pay);
		}
		else if (k.equals(ParamCoreLActionKind.DOT_SEND))
		{
			return ef.newParamCoreEDotSend(r, offset, a.op, a.pay);
		}
		else if (k.equals(ParamCoreLActionKind.DOT_RECEIVE))
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
