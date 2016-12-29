package org.scribble.ext.f17.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ext.f17.ast.local.F17LChoice;
import org.scribble.ext.f17.ast.local.F17LEnd;
import org.scribble.ext.f17.ast.local.F17LRec;
import org.scribble.ext.f17.ast.local.F17LType;
import org.scribble.ext.f17.ast.local.action.F17LAccept;
import org.scribble.ext.f17.ast.local.action.F17LAction;
import org.scribble.ext.f17.ast.local.action.F17LConnect;
import org.scribble.ext.f17.ast.local.action.F17LReceive;
import org.scribble.ext.f17.ast.local.action.F17LSend;
import org.scribble.ext.f17.model.action.F17Action;
import org.scribble.model.MPrettyState;
import org.scribble.model.MState;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

public class F17State extends MPrettyState<Void, F17Action, F17State, Global>
{
	private static final F17LBot BOT = new F17LBot();  // Disconnected
	
	private Map<Role, F17LType> P = new HashMap<>();  // Note: F17LType already has self
	private Map<Role, Map<Role, F17LSend>> Q = new HashMap<>();  // null value means connected and empty

	public F17State(Map<Role, F17LType> P, boolean explicit)
	{
		this(P, makeQ(P.keySet(), explicit ? BOT : null));
	}
	
	private static Map<Role, Map<Role, F17LSend>> makeQ(Set<Role> rs, F17LSend init)
	{
		/*return rs.stream().collect(Collectors.toMap((r) -> r, (r) ->
			rs.stream().filter((x) -> !x.equals(r))
				.collect(Collectors.toMap((x) -> x, (x) -> init))  // Doesn't work? (NPE)
		));*/
		Map<Role, Map<Role, F17LSend>> res = new HashMap<>();
		for (Role r : rs)
		{
			HashMap<Role, F17LSend> tmp = new HashMap<>();
			for (Role rr : rs)
			{
				if (!rr.equals(r))
				{
					tmp.put(rr, init);
				}
			}
			res.put(r, tmp);
		}
		return res;
	}

	protected F17State(Map<Role, F17LType> P, Map<Role, Map<Role, F17LSend>> Q)
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
						if (!(this.Q.get(ls.self) instanceof F17LBot) && this.Q.get(ls.peer).get(ls.self) == null)
						{
							f.get(r).add(new F17Action(ls));
						}
					}
					else if (a instanceof F17LReceive)
					{
						F17LReceive lr = (F17LReceive) a;
						F17LSend m = this.Q.get(lr.self).get(lr.peer);
						if (m != null && m.toDual().equals(lr))  //&& !(m instanceof F17LBot)
						{
							f.get(r).add(new F17Action(lr));
						}
					}
					else if (a instanceof F17LConnect)
					{
						F17LConnect lo = (F17LConnect) a;
						if (this.Q.get(lo.self).get(lo.peer) instanceof F17LBot && this.Q.get(lo.peer).get(lo.self) instanceof F17LBot)
						{
							F17LType plt = this.P.get(lo.peer);
							if (plt instanceof F17LChoice)
							{
								if (((F17LChoice) plt).cases.containsKey(lo.toDual()))
								{
									f.get(r).add(new F17Action(lo));
								}
							}
						}
					}
					else if (a instanceof F17LAccept)
					{
						F17LAccept la = (F17LAccept) a;
						if (this.Q.get(la.self).get(la.peer) instanceof F17LBot && this.Q.get(la.peer).get(la.self) instanceof F17LBot)
						{
							F17LType plt = this.P.get(la.peer);
							if (plt instanceof F17LChoice)
							{
								if (((F17LChoice) plt).cases.containsKey(la.toDual()))
								{
									f.get(r).add(new F17Action(la));
								}
							}
						}
					}
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
	
	private static Map<Role, Map<Role, F17LSend>> copyQ(Map<Role, Map<Role, F17LSend>> Q)
	{
		Map<Role, Map<Role, F17LSend>> copy = new HashMap<>();
		for (Role r : Q.keySet())
		{
			copy.put(r, new HashMap<>(Q.get(r)));
		}
		return copy;
	}
	
	// a.action already contains self
	public F17State fire(Role r, F17Action a)  // Deterministic
	{
		Map<Role, F17LType> P = new HashMap<>(this.P);
		Map<Role, Map<Role, F17LSend>> Q = copyQ(this.Q);
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
			Q.get(ls.peer).put(ls.self, ls);
		}
		else if (la instanceof F17LReceive)
		{
			F17LReceive lr = (F17LReceive) la;
			P.put(r, succ);
			Q.get(lr.self).put(lr.peer, null);
		}
		else
		{
			throw new RuntimeException("[f17] Shouldn't get in here: " + a);
		}
		return new F17State(P, Q);
	}

	// "Synchronous version" of fire
	public F17State sync(Role r1, F17Action a1, Role r2, F17Action a2)
	{
		Map<Role, F17LType> P = new HashMap<>(this.P);
		Map<Role, Map<Role, F17LSend>> Q = copyQ(this.Q);
		F17LAction la1 = a1.action;
		F17LAction la2 = a2.action;
		F17LType succ1 = ((F17LChoice) P.get(r1)).cases.get(la1);
		F17LType succ2 = ((F17LChoice) P.get(r2)).cases.get(la2);
		if (succ1 instanceof F17LRec)
		{
			succ1 = ((F17LRec) succ1).unfold();  // "Eager" unfolding
		}
		if (succ2 instanceof F17LRec)
		{
			succ2 = ((F17LRec) succ2).unfold();
		}
		if ((la1 instanceof F17LConnect && la2 instanceof F17LAccept)
				|| (la1 instanceof F17LAccept && la2 instanceof F17LConnect))
		{
			P.put(r1, succ1);
			P.put(r2, succ2);
			Q.get(r1).put(r2, null);
			Q.get(r2).put(r1, null);
		}
		else
		{
			throw new RuntimeException("[f17] Shouldn't get in here: " + a1 + ", " + a2);
		}
		return new F17State(P, Q);
	}
	
	/*public SStateErrors getErrors()
	{
		Map<Role, EReceive> stuck = this.config.getStuckMessages();
		Set<Set<Role>> waitfor = this.config.getWaitForErrors();
		//Set<Set<Role>> waitfor = Collections.emptySet();
		Map<Role, Set<ESend>> orphs = this.config.getOrphanMessages();
		Map<Role, EState> unfinished = this.config.getUnfinishedRoles();
		return new SStateErrors(stuck, waitfor, orphs, unfinished);
	}*/
	
	@Override
	protected String getNodeLabel()
	{
		String lab = "(" + this.P + ", " + this.Q + ")";
		//return "label=\"" + this.id + ":" + lab.substring(1, lab.length() - 1) + "\"";
		return "label=\"" + this.id + ":" + lab + "\"";
	}
	
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


class F17LBot extends F17LSend
{
	public F17LBot()
	{
		super(Role.EMPTY_ROLE, Role.EMPTY_ROLE, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);
	}

	@Override
	public F17LAction toDual()
	{
		//throw new RuntimeException("Shouldn't get in here: " + this);
		return this;
	}
	
	@Override
	public String toString()
	{
		return "#";
	} 

	@Override
	public int hashCode()
	{
		int hash = 2273;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof F17LBot))
		{
			return false;
		}
		return super.equals(obj);
	}
	
	@Override
	protected boolean canEquals(Object o)
	{
		return o instanceof F17LBot;
	}
}
