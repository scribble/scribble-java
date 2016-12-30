package org.scribble.ext.f17.lts;

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
import org.scribble.ext.f17.ast.local.action.F17LDisconnect;
import org.scribble.ext.f17.ast.local.action.F17LReceive;
import org.scribble.ext.f17.ast.local.action.F17LSend;
import org.scribble.ext.f17.lts.action.F17LBot;
import org.scribble.ext.f17.lts.action.F17LTSAction;
import org.scribble.model.MPrettyState;
import org.scribble.model.MState;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.Role;

public class F17Session extends MPrettyState<Void, F17LTSAction, F17Session, Global>
{
	private static final F17LBot BOT = F17LBot.BOT;  // Disconnected
	
	private Map<Role, F17LType> P = new HashMap<>();  // Note: F17LType already has self // By "eager" unfolding, L can only be choice or end
	private Map<Role, Map<Role, F17LSend>> Q = new HashMap<>();  // null value means connected and empty

	public F17Session(Map<Role, F17LType> P, boolean explicit)
	{
		this(P, makeQ(P.keySet(), explicit ? BOT : null));
	}

	protected F17Session(Map<Role, F17LType> P, Map<Role, Map<Role, F17LSend>> Q)
	{
		super(Collections.emptySet());

		// "Eager" unfold
		Map<Role, F17LType> tmp = P.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey(), (e) ->
			((e.getValue() instanceof F17LRec) ? ((F17LRec) e.getValue()).unfold() : e.getValue())));
		this.P = Collections.unmodifiableMap(tmp);

		this.Q = Collections.unmodifiableMap(Q);
	}

	public boolean isConnectionError()
	{
		return this.P.values().stream().anyMatch((L) -> 
			(L instanceof F17LChoice) && ((F17LChoice) L).cases.keySet().stream().anyMatch((a) ->
						(a instanceof F17LConnect && !(this.Q.get(a.self).get(((F17LConnect) a).peer) instanceof F17LBot))
				||	(a instanceof F17LAccept && !(this.Q.get(a.self).get(((F17LAccept) a).peer) instanceof F17LBot))
		));
	}

	public boolean isDisconnectedError()
	{
		return this.P.values().stream().anyMatch((L) -> 
			(L instanceof F17LChoice) && ((F17LChoice) L).cases.keySet().stream().anyMatch((a) ->
				(a instanceof F17LDisconnect && this.Q.get(a.self).get(((F17LDisconnect) a).peer) != null)
		));
	}

	public boolean isUnconnectedError()
	{
		return this.P.values().stream().anyMatch((L) -> 
			(L instanceof F17LChoice) && ((F17LChoice) L).cases.keySet().stream().anyMatch((a) ->
						(a instanceof F17LSend && (this.Q.get(a.self).get(((F17LSend) a).peer) instanceof F17LBot))
				||	(a instanceof F17LReceive && (this.Q.get(a.self).get(((F17LReceive) a).peer) instanceof F17LBot))
		));
	}

	public boolean isUnfinishedRoleError(Map<Role, F17LType> P0)
	{
		return this.isTerminal() &&
				this.P.entrySet().stream().anyMatch((e) -> isActive(e.getValue(), P0.get(e.getKey())));
	}
	
	// lt already eagerly unfolded
	private static boolean isActive(F17LType lt, F17LType init)
	{
		if (init instanceof F17LRec)
		{
			init = ((F17LRec) init).unfold();
		}
		return !(lt instanceof F17LEnd) &&
				!(lt.equals(init) && ((F17LChoice) lt).cases.keySet().stream().anyMatch((k) -> k instanceof F17LAccept));  // anyMatch should be enough by WF
	}
	
	public Map<Role, List<F17LTSAction>> getFireable()
	{
		Map<Role, List<F17LTSAction>> f = new HashMap<>();
		for (Entry<Role, F17LType> L : this.P.entrySet())
		{
			Role r = L.getKey();
			f.put(r, new LinkedList<>());
			F17LType lt = L.getValue();
			if (lt instanceof F17LChoice)
			{
				F17LChoice lc = (F17LChoice) lt;
				for (Entry<F17LAction, F17LType> e : lc.cases.entrySet())
				{
					F17LAction a = e.getKey();
					if (a instanceof F17LSend)
					{
						F17LSend ls = (F17LSend) a;
						if (!(this.Q.get(ls.self).get(ls.peer) instanceof F17LBot) && this.Q.get(ls.peer).get(ls.self) == null)
						{
							f.get(r).add(new F17LTSAction(ls));
						}
					}
					else if (a instanceof F17LReceive)
					{
						F17LReceive lr = (F17LReceive) a;
						F17LSend m = this.Q.get(lr.self).get(lr.peer);
						if (m != null && m.toDual().equals(lr))  //&& !(m instanceof F17LBot)
						{
							f.get(r).add(new F17LTSAction(lr));
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
									f.get(r).add(new F17LTSAction(lo));
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
									f.get(r).add(new F17LTSAction(la));
								}
							}
						}
					}
					else if (a instanceof F17LDisconnect)
					{
						F17LDisconnect ld = (F17LDisconnect) a;
						if (!(this.Q.get(ld.self).get(ld.peer) instanceof F17LBot) && this.Q.get(ld.self).get(ld.peer) == null)
						{
							f.get(r).add(new F17LTSAction(ld));
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
	
	// a.action already contains self
	public F17Session fire(Role r, F17LTSAction a)  // Deterministic
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
		else if (la instanceof F17LDisconnect)
		{
			F17LDisconnect ld = (F17LDisconnect) la;
			P.put(r, succ);
			Q.get(ld.self).put(ld.peer, BOT);
		}
		else
		{
			throw new RuntimeException("[f17] Shouldn't get in here: " + a);
		}
		return new F17Session(P, Q);
	}

	// "Synchronous version" of fire
	public F17Session sync(Role r1, F17LTSAction a1, Role r2, F17LTSAction a2)
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
		return new F17Session(P, Q);
	}
	
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
		if (!(o instanceof F17Session))
		{
			return false;
		}
		F17Session them = (F17Session) o;
		return them.canEquals(this) && this.P.equals(them.P) && this.Q.equals(them.Q);
	}

	@Override
	protected boolean canEquals(MState<?, ?, ?, ?> s)
	{
		return s instanceof F17Session;
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
	
	private static Map<Role, Map<Role, F17LSend>> copyQ(Map<Role, Map<Role, F17LSend>> Q)
	{
		Map<Role, Map<Role, F17LSend>> copy = new HashMap<>();
		for (Role r : Q.keySet())
		{
			copy.put(r, new HashMap<>(Q.get(r)));
		}
		return copy;
	}
}
