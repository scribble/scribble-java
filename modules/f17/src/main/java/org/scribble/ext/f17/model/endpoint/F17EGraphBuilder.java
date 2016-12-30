package org.scribble.ext.f17.model.endpoint;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.scribble.ext.f17.ast.F17RecVar;
import org.scribble.ext.f17.ast.local.F17LChoice;
import org.scribble.ext.f17.ast.local.F17LEnd;
import org.scribble.ext.f17.ast.local.F17LRec;
import org.scribble.ext.f17.ast.local.F17LType;
import org.scribble.ext.f17.ast.local.action.F17LAccept;
import org.scribble.ext.f17.ast.local.action.F17LAction;
import org.scribble.ext.f17.ast.local.action.F17LConnect;
import org.scribble.ext.f17.ast.local.action.F17LDisconnect;
import org.scribble.ext.f17.ast.local.action.F17LReceive;
import org.scribble.ext.f17.ast.local.action.F17LSend;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAccept;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.model.endpoint.actions.EConnect;
import org.scribble.model.endpoint.actions.EDisconnect;
import org.scribble.model.endpoint.actions.EReceive;
import org.scribble.model.endpoint.actions.ESend;
import org.scribble.sesstype.name.RecVar;

public class F17EGraphBuilder
{
	private F17EGraphBuilderUtil util = new F17EGraphBuilderUtil();
	
	public EGraph build(F17LType lt)
	{
		this.util.reset();
		build(lt, this.util.getEntry(), this.util.getExit(), new HashMap<>());
		return this.util.finalise();
	}
	
	private void build(F17LType lt, EState s1, EState s2, Map<RecVar, EState> f)
	{
		if (lt instanceof F17LChoice)
		{
			F17LChoice lc = (F17LChoice) lt;
			if (lc.cases.size() == 1)
			{
				Entry<F17LAction, F17LType> e = lc.cases.entrySet().iterator().next();
				F17LAction a = e.getKey();
				F17LType L = e.getValue();
				if (L instanceof F17LEnd)
				{
					this.util.addEdge(s1, toEAction(a), s2);
				}
				else if (L instanceof F17RecVar)
				{
					this.util.addEdge(s1, toEAction(a), f.get(((F17RecVar) L).var));
				}
				else
				{
					EState s = this.util.newState(Collections.emptySet());
					this.util.addEdge(s1, toEAction(a), s);
					build(L, s, s2, f);
				}
			}
			else // lc.cases.size() > 1
			{
				for (Entry<F17LAction, F17LType> e : lc.cases.entrySet())
				{
					Map<F17LAction, F17LType> tmp = new HashMap<>();
					tmp.put(e.getKey(), e.getValue());
					build(new F17LChoice(tmp), s1, s2, f);
				}
			}
		}
		else if (lt instanceof F17LRec)
		{
			F17LRec lr = (F17LRec) lt;
			Map<RecVar, EState> tmp = new HashMap<>(f);
			tmp.put(lr.recvar, s1);
			build(lr.body, s1, s2, tmp);
		}
		else
		{
			throw new RuntimeException("[f17] Shouldn't get in here: " + lt);
		}
	}
	
	private static EAction toEAction(F17LAction a)
	{
		if (a instanceof F17LSend)
		{
			F17LSend ls = (F17LSend) a;
			return new ESend(a.peer, ls.op, ls.pay);
		}
		else if (a instanceof F17LReceive)
		{
			F17LReceive lr = (F17LReceive) a;
			return new EReceive(a.peer, lr.op, lr.pay);
		}
		else if (a instanceof F17LConnect)
		{
			F17LConnect lc = (F17LConnect) a;
			return new EConnect(a.peer, lc.op, lc.pay);
		}
		else if (a instanceof F17LAccept)
		{
			F17LAccept la = (F17LAccept) a;
			return new EAccept(a.peer, la.op, la.pay);
		}
		else if (a instanceof F17LDisconnect)
		{
			return new EDisconnect(a.peer);
		}
		else
		{
			throw new RuntimeException("[f17] Shouldn't get in here: " + a);
		}
	}
}
