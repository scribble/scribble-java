package org.scribble.ext.f17.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.scribble.ext.f17.ast.local.F17LChoice;
import org.scribble.ext.f17.ast.local.F17LEnd;
import org.scribble.ext.f17.ast.local.F17LRec;
import org.scribble.ext.f17.ast.local.F17LType;
import org.scribble.ext.f17.ast.local.action.F17LAction;
import org.scribble.ext.f17.ast.local.action.F17LReceive;
import org.scribble.ext.f17.ast.local.action.F17LSend;
import org.scribble.ext.f17.model.action.F17Action;
import org.scribble.model.MPrettyState;
import org.scribble.model.MState;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.Role;

public class F17State extends MPrettyState<Void, F17Action, F17State, Global>
{
	private Map<Role, F17LType> P = new HashMap<>();  // F17LType already has self
	private Map<Role, F17LSend> Q = new HashMap<>();

	public F17State(Map<Role, F17LType> P)
	{
		this(P, Collections.emptyMap());
	}

	protected F17State(Map<Role, F17LType> P, Map<Role, F17LSend> Q)
	{
		super(Collections.emptySet());

		// "Eager" unfold
		Map<Role, F17LType> tmp = P.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey(), (e) ->
			((e.getValue() instanceof F17LRec) ? ((F17LRec) e.getValue()).unfold() : e.getValue())));
		this.P = Collections.unmodifiableMap(tmp);

		this.Q = Collections.unmodifiableMap(Q);
	}
	
	public Map<Role, List<F17Action>> getFireable()
	{
		Map<Role, List<F17Action>> f = new HashMap<>();
		for (Entry<Role, F17LType> P : this.P.entrySet())
		{
			Role r = P.getKey();
			f.put(r, new LinkedList<>());
			F17LType lt = P.getValue();
			if (lt instanceof F17LChoice)
			{
				F17LChoice lc = (F17LChoice) lt;
				for (Entry<F17LAction, F17LType> e : lc.cases.entrySet())
				{
					F17LAction a = e.getKey();
					if (a instanceof F17LSend)
					{
						F17LSend ls = (F17LSend) a;
						if (this.Q.get(ls.peer) == null)
						{
							f.get(r).add(new F17Action(ls));
						}
					}
					else if (a instanceof F17LReceive)
					{
						F17LReceive lr = (F17LReceive) a;
						F17LSend m = this.Q.get(lr.peer);
						if (m != null && m.toDual().equals(lr))
						{
							f.get(r).add(new F17Action(lr));
						}
					}
					/*else if (a instanceof F17LConnect)
					{
						
					}
					else if (a instanceof F17LAccept)
					{
						
					}*/
					else
					{
						throw new RuntimeException("[f17] Shouldn't get in here: " + a);
					}
				}
			}
			/*else if (lt instanceof F17LRec)
			{
				// Use "eager" unfolding
			}*/
			else if (lt instanceof F17LEnd)
			{
				// Nothing
			}
			else
			{
				throw new RuntimeException("[f17] Shouldn't get in here: " + lt);
			}
		}
		return f;
	}
	
	// a.action already contains self
	public F17State fire(Role r, F17Action a)  // Deterministic
	{
		Map<Role, F17LType> P = new HashMap<>(this.P);
		Map<Role, F17LSend> Q = new HashMap<>(this.Q);
		F17LAction la = a.action;
		F17LType succ = ((F17LChoice) P.get(r)).cases.get(la);
		if (succ instanceof F17LRec)
		{
			succ = ((F17LRec) succ).unfold();  // "Eager" unfolding
		}
		if (la instanceof F17LSend)
		{
			F17LSend ls = (F17LSend) la;
			P.put(r, succ);
			Q.put(ls.peer, null);
		}
		else if (la instanceof F17LReceive)
		{
			F17LReceive lr = (F17LReceive) la;
			P.put(r, succ);
			Q.put(lr.peer, lr.toDual());
		}
		else
		{
			throw new RuntimeException("[f17] Shouldn't get in here: " + a);
		}
		return new F17State(P, Q);
	}

	/*// "Synchronous version" of fire
	public List<SConfig> sync(Role r1, EAction a1, Role r2, EAction a2)
	{
		return this.config.sync(r1, a1, r2, a2);
	}
	
	public SStateErrors getErrors()
	{
		Map<Role, EReceive> stuck = this.config.getStuckMessages();
		Set<Set<Role>> waitfor = this.config.getWaitForErrors();
		//Set<Set<Role>> waitfor = Collections.emptySet();
		Map<Role, Set<ESend>> orphs = this.config.getOrphanMessages();
		Map<Role, EState> unfinished = this.config.getUnfinishedRoles();
		return new SStateErrors(stuck, waitfor, orphs, unfinished);
	}
	
	@Override
	protected String getNodeLabel()
	{
		String labs = this.config.toString();
		return "label=\"" + this.id + ":" + labs.substring(1, labs.length() - 1) + "\"";
	}*/
	
	@Override
	public final int hashCode()
	{
		int hash = 79;
		hash = 31 * hash + this.P.hashCode();
		hash = 31 * hash + this.Q.hashCode();
		return hash;
	}

	// Not using id, cf. ModelState -- FIXME? use a factory pattern that associates unique states and ids? -- use id for hash, and make a separate "semantic equals"
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof F17State))
		{
			return false;
		}
		F17State them = (F17State) o;
		return them.canEquals(this) && this.P.equals(them.P) && this.Q.equals(them.Q);
	}

	@Override
	protected boolean canEquals(MState<?, ?, ?, ?> s)
	{
		return s instanceof F17State;
	}
}
