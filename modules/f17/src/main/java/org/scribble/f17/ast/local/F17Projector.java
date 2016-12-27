package org.scribble.f17.ast.local;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.scribble.f17.ast.F17AstFactory;
import org.scribble.f17.ast.global.F17GChoice;
import org.scribble.f17.ast.global.F17GEnd;
import org.scribble.f17.ast.global.F17GRec;
import org.scribble.f17.ast.global.F17GRecVar;
import org.scribble.f17.ast.global.F17GType;
import org.scribble.f17.ast.global.action.F17GAction;
import org.scribble.f17.ast.global.action.F17GMessageTransfer;
import org.scribble.f17.ast.local.action.F17LAction;
import org.scribble.f17.ast.local.action.F17LReceive;
import org.scribble.f17.main.F17Exception;
import org.scribble.sesstype.name.RecVar;
import org.scribble.sesstype.name.Role;


public class F17Projector
{
	private final F17AstFactory factory = new F17AstFactory();

	public F17Projector()
	{

	}

	// merge is for projection of "delegation payload types"
	public F17LType project(F17GType gt, Role r, Set<RecVar> delta) throws F17Exception
	{
		if (gt instanceof F17GChoice)  // FIXME: differentiate unary case
		{
			F17GChoice gc = (F17GChoice) gt;
			Map<F17GAction, F17LType> aCases = new HashMap<>();
			Map<F17GAction, RecVar> rvCases = new HashMap<>();
			Set<F17GAction> eCases = new HashSet<>();
			for (Entry<F17GAction, F17GType> e : gc.cases.entrySet())
			{
				F17GAction ga = e.getKey();
				if (ga.getRoles().contains(r))
				{
					F17LType lt = project(e.getValue(), r, Collections.emptySet());
					/*F17LAction la = project(ga, r);
					cases.put(la, lt);*/
					aCases.put(ga, lt);
				}
				else
				{
					F17LType lt = project(e.getValue(), r, delta);
					if (lt instanceof F17LRecVar)
					{
						F17LRecVar grv = (F17LRecVar) lt;
						rvCases.put(ga, grv.var);
					}
					else if (lt instanceof F17LEnd)
					{
						eCases.add(ga);
					}
					else
					{
						if (!(lt instanceof F17LChoice) || ((F17LChoice) lt).cases.size() > 1)  // FIXME: generalise >1 cases
						{
							throw new F17Exception("[f17] Not projectable (non prefix-guarded case) for " + r + ": " + lt);
						}
						Entry<F17LAction, F17LType> tmp = ((F17LChoice) lt).cases.entrySet().iterator().next();
						aCases.put(ga, tmp.getValue());
					}
				}
			}
			if (!rvCases.isEmpty() && aCases.isEmpty() && eCases.isEmpty() && new HashSet<>(rvCases.values()).size() == 1)
			{
				return this.factory.LRecVar(rvCases.values().iterator().next());
			}
			else if (!eCases.isEmpty() && aCases.isEmpty() && rvCases.isEmpty())
			{
				return this.factory.LEnd();
			}
			else
			{
				for (RecVar rv : rvCases.values())
				{
					if (!delta.contains(rv))
					{
						throw new F17Exception("[f17] Not projectable (unguarded rec case) for " + r + ": " + rv);
					}
				}
				Map<F17LAction, F17LType> pCases = new HashMap<>();  // FIXME: replace aCases by just pCases
				for (Entry<F17GAction, F17LType> e : aCases.entrySet())
				{
					pCases.put(project(e.getKey(), r), e.getValue());
				}
				F17LAction firsta = pCases.keySet().iterator().next();
				if (firsta.isOutput())
				{
					if (pCases.keySet().stream().anyMatch((k) -> k.isInput()))
					{
						throw new F17Exception("[f17] Inconsistent choice: " + gc);
					}
				}
				else  // isInput
				{
					if (pCases.keySet().stream().anyMatch((k) -> k.isOutput()))
					{
						throw new F17Exception("[f17] Inconsistent choice: " + gc);
					}
					Role dest = ((F17LReceive) firsta).peer;  // FIXME: cast
					if (pCases.keySet().stream().anyMatch((k) -> !((F17LReceive) k).peer.equals(dest)))
					{
						throw new F17Exception("[f17] Inconsistent input choice subjects: " + gc);  // subject means global action subjs (although also means peer in local)
					}
				}
				return this.factory.LChoice(pCases);
			}
		}
		else if (gt instanceof F17GRec)
		{
			F17GRec gr = (F17GRec) gt;
			Set<RecVar> delta1 = new HashSet<>(delta);
			delta1.add(gr.recvar);
			F17LType lt = project(gr.body, r, delta1);
			if (lt instanceof F17LRecVar)
			{
				return this.factory.LEnd();
			}
			return this.factory.LRec(gr.recvar, lt);
		}
		else if (gt instanceof F17GRecVar)
		{
			RecVar rv = ((F17GRecVar) gt).var;
			/*if (!delta.contains(rv))
			{
				throw new F17Exception("[f17] Not projectable (unguarded local choice case): " + gt);
			}*/
			return this.factory.LRecVar(rv);
		}
		else if (gt instanceof F17GEnd)
		{
			return this.factory.LEnd();
		}
		else
		{
			throw new RuntimeException("[f17] Shouldn't get in here: " + gt);
		}
	}

	public F17LAction project(F17GAction ga, Role r) throws F17Exception
	{
		if (ga instanceof F17GMessageTransfer)
		{
			F17GMessageTransfer mt = (F17GMessageTransfer) ga;
			if (mt.src.equals(r))
			{
				return this.factory.LSend(r, mt.dest, mt.op, mt.pay);
			}
			else
			{
				return this.factory.LReceive(r, mt.dest, mt.op, mt.pay);
			}
		}
		else
		{
			throw new RuntimeException("[f17] Shouldn't get in here: " + ga);
		}
	}
}
