package org.scribble.ext.go.core.model.endpoint;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.scribble.ext.go.core.ast.ParamCoreMessage;
import org.scribble.ext.go.core.ast.ParamCoreRecVar;
import org.scribble.ext.go.core.ast.local.ParamCoreLActionKind;
import org.scribble.ext.go.core.ast.local.ParamCoreLChoice;
import org.scribble.ext.go.core.ast.local.ParamCoreLEnd;
import org.scribble.ext.go.core.ast.local.ParamCoreLRec;
import org.scribble.ext.go.core.ast.local.ParamCoreLType;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.ext.go.main.ParamJob;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EGraphBuilderUtil;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
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
			ParamCoreLChoice lc = (ParamCoreLChoice) lt;
			lc.cases.entrySet().stream().forEach(e ->
				buildEdgeAndContinuation(s1, s2, recs, lc.role, lc.kind, e.getKey(), e.getValue())
			);
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
			ParamRole r, ParamCoreLActionKind k, ParamCoreMessage a, ParamCoreLType cont)
	{
		if (cont instanceof ParamCoreLEnd)
		{
			this.util.addEdge(s1, toEAction(r, k, a), s2);
		}
		else if (cont instanceof ParamCoreRecVar)
		{
			EState s = recs.get(((ParamCoreRecVar<?>) cont).recvar);
			this.util.addEdge(s1, toEAction(r, k, a), s);
		}
		else
		{
			EState s = this.util.ef.newEState(Collections.emptySet());  
			this.util.addEdge(s1, toEAction(r, k, a), s);
			build(cont, s, s2, recs);
		}
	}
	
	private EAction toEAction(ParamRole r, ParamCoreLActionKind k, ParamCoreMessage a)
	{
		ParamCoreEModelFactory ef = (ParamCoreEModelFactory) this.util.ef;  // FIXME: factor out
		if (k.equals(ParamCoreLActionKind.SEND_ALL))
		{
			return ef.newParamCoreESend(r, a.op, a.pay);

		}
		else if (k.equals(ParamCoreLActionKind.RECEIVE_ALL))
		{
			return ef.newParamCoreEReceive(r, a.op, a.pay);
		}
		else
		{
			throw new RuntimeException("[param-core] Shouldn't get in here: " + k);
		}
	}
}
