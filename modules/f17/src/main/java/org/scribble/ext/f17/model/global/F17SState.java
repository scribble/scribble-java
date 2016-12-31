package org.scribble.ext.f17.model.global;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.scribble.model.MPrettyState;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.EStateKind;
import org.scribble.model.endpoint.actions.EAccept;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.model.endpoint.actions.EConnect;
import org.scribble.model.endpoint.actions.EDisconnect;
import org.scribble.model.endpoint.actions.EReceive;
import org.scribble.model.endpoint.actions.ESend;
import org.scribble.model.global.actions.SAction;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

// Not extending SState -- not reusing SConfig, SBuffers, etc
// FSM version of F17Session
// Wait-for errors?
public class F17SState extends MPrettyState<Void, SAction, F17SState, Global>
{
	private static final F17EBot BOT = new F17EBot();
	
	private Map<Role, EState> P = new HashMap<>();  // Actually, F17EState
	private Map<Role, Map<Role, ESend>> Q = new HashMap<>();  // null value means connected and empty
	
	private final Set<Role> subjs = new HashSet<>();  // Hacky: most because EState has no self

	public F17SState(Map<Role, EState> P, boolean explicit)
	{
		this(P, makeQ(P.keySet(), explicit ? BOT : null));
	}
	
	public void addSubject(Role subj)
	{
		this.subjs.add(subj);
	}
	
	public Set<Role> getSubjects()
	{
		return Collections.unmodifiableSet(this.subjs);
	}

	public Map<Role, EState> getP()
	{
		return this.P;
	}
	
	public Map<Role, Map<Role, ESend>> getQ()
	{
		return this.Q;
	}

	protected F17SState(Map<Role, EState> P, Map<Role, Map<Role, ESend>> Q)
	{
		super(Collections.emptySet());
		this.P = Collections.unmodifiableMap(P);
		this.Q = Collections.unmodifiableMap(Q);
	}

	public boolean isConnectionError()
	{
		return this.P.entrySet().stream().anyMatch((e) -> 
			e.getValue().getActions().stream().anyMatch((a) ->
				(a.isConnect() || a.isAccept()) && isConnected(e.getKey(), a.peer)
		));
	}

	public boolean isDisconnectedError()
	{
		return this.P.entrySet().stream().anyMatch((e) -> 
			e.getValue().getActions().stream().anyMatch((a) ->
				a.isDisconnect() && this.Q.get(e.getKey()).get(a.peer) != null
		));
	}

	public boolean isUnconnectedError()
	{
		return this.P.entrySet().stream().anyMatch((e) -> 
			e.getValue().getActions().stream().anyMatch((a) ->
				(a.isSend() || a.isReceive()) && !isConnected(e.getKey(), a.peer)
		));
	}

	public boolean isSynchronisationError()
	{
		return this.P.entrySet().stream().anyMatch((e) -> 
			e.getValue().getActions().stream().anyMatch((a) ->
				{
					EState peer;
					return a.isConnect() && (peer = this.P.get(a.peer)).getStateKind() == EStateKind.ACCEPT
							&& !(peer.getActions().contains(a.toDual(e.getKey())));
				}
		));
	}

	public boolean isReceptionError()
	{
		return this.Q.entrySet().stream().anyMatch((e1)
				-> e1.getValue().entrySet().stream().anyMatch((e2) ->
					{
						EState s;
						return hasMessage(e1.getKey(), e2.getKey())
								&& ((s = this.P.get(e1.getKey())).getStateKind() == EStateKind.UNARY_INPUT
										|| s.getStateKind() == EStateKind.POLY_INPUT)
								&& !s.getActions().contains(e2.getValue().toDual(e2.getKey()));
					}
				));
	}

	public boolean isUnfinishedRoleError(Map<Role, EState> E0)
	{
		return this.isTerminal() &&
				this.P.entrySet().stream().anyMatch((e) -> isActive(e.getValue(), E0.get(e.getKey()).id));
	}

	public boolean isOrphanError(Map<Role, EState> E0)
	{
		return this.P.entrySet().stream().anyMatch((e) -> isInactive(e.getValue(), E0.get(e.getKey()).id)
				&& this.P.keySet().stream().anyMatch((r) -> hasMessage(e.getKey(), r)));
	}
	
	public Map<Role, List<EAction>> getFireable()
	{
		Map<Role, List<EAction>> res = new HashMap<>();
		for (Entry<Role, EState> e : this.P.entrySet())
		{
			Role self = e.getKey();
			EState s = e.getValue();
			res.put(self, new LinkedList<>());
			for (EAction a : s.getActions())
			{
				if (a.isSend())
				{
					ESend es = (ESend) a;
					if (!(this.Q.get(self).get(es.peer) instanceof F17EBot) && this.Q.get(es.peer).get(self) == null)
					{
						res.get(self).add(es);
					}
				}
				else if (a.isReceive())
				{
					EReceive er = (EReceive) a;
					ESend m = this.Q.get(self).get(er.peer);
					if (m != null && er.toDual(self).equals(m))  //&& !(m instanceof F17EBot)
					{
						res.get(self).add(er);
					}
				}
				else if (a.isConnect())
				{
					EConnect lo = (EConnect) a;
					if (this.Q.get(self).get(lo.peer) instanceof F17EBot
							&& this.Q.get(lo.peer).get(self) instanceof F17EBot)
					{
						EState plt = this.P.get(lo.peer);
						if (plt.getActions().contains(lo.toDual(self)))
						{
							res.get(self).add(lo);
						}
					}
				}
				else if (a.isAccept())
				{
					EAccept la = (EAccept) a;
					if (this.Q.get(self).get(la.peer) instanceof F17EBot
							&& this.Q.get(la.peer).get(self) instanceof F17EBot)
					{
						EState plt = this.P.get(la.peer);
						if (plt.getActions().contains(la.toDual(self)))
						{
							res.get(self).add(la);
						}
					}
				}
				else if (a.isDisconnect())
				{
					EDisconnect ld = (EDisconnect) a;
					if (!(this.Q.get(self).get(ld.peer) instanceof F17EBot)
							&& this.Q.get(self).get(ld.peer) == null)
					{
						res.get(self).add(ld);
					}
				}
				else
				{
					throw new RuntimeException("[f17] Shouldn't get in here: " + a);
				}
			}
		}
		return res;
	}
	
	public F17SState fire(Role self, EAction a)  // Deterministic
	{
		Map<Role, EState> P = new HashMap<>(this.P);
		Map<Role, Map<Role, ESend>> Q = copyQ(this.Q);
		EState succ = P.get(self).getSuccessor(a);

		if (a.isSend())
		{
			ESend es = (ESend) a;
			P.put(self, succ);
			Q.get(es.peer).put(self, es);
		}
		else if (a.isReceive())
		{
			EReceive lr = (EReceive) a;
			P.put(self, succ);
			Q.get(self).put(lr.peer, null);
		}
		else if (a.isDisconnect())
		{
			EDisconnect ld = (EDisconnect) a;
			P.put(self, succ);
			Q.get(self).put(ld.peer, BOT);
		}
		else
		{
			throw new RuntimeException("[f17] Shouldn't get in here: " + a);
		}
		return new F17SState(P, Q);
	}

	// "Synchronous version" of fire
	public F17SState sync(Role r1, EAction a1, Role r2, EAction a2)
	{
		Map<Role, EState> P = new HashMap<>(this.P);
		Map<Role, Map<Role, ESend>> Q = copyQ(this.Q);
		EState succ1 = P.get(r1).getSuccessor(a1);
		EState succ2 = P.get(r2).getSuccessor(a2);

		if ((a1.isConnect() && a2.isAccept())
				|| (a1.isAccept() && a2.isConnect()))
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
		return new F17SState(P, Q);
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
		if (!(o instanceof F17SState))
		{
			return false;
		}
		F17SState them = (F17SState) o;
		return them.canEquals(this) && this.P.equals(them.P) && this.Q.equals(them.Q);
	}

	@Override
	protected boolean canEquals(MState<?, ?, ?, ?> s)
	{
		return s instanceof F17SState;
	}
	
	private static Map<Role, Map<Role, ESend>> makeQ(Set<Role> rs, ESend init)
	{
		/*return rs.stream().collect(Collectors.toMap((r) -> r, (r) ->
			rs.stream().filter((x) -> !x.equals(r))
				.collect(Collectors.toMap((x) -> x, (x) -> init))  // Doesn't work? (NPE)
		));*/
		Map<Role, Map<Role, ESend>> res = new HashMap<>();
		for (Role r : rs)
		{
			HashMap<Role, ESend> tmp = new HashMap<>();
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
	
	private static Map<Role, Map<Role, ESend>> copyQ(Map<Role, Map<Role, ESend>> Q)
	{
		Map<Role, Map<Role, ESend>> copy = new HashMap<>();
		for (Role r : Q.keySet())
		{
			copy.put(r, new HashMap<>(Q.get(r)));
		}
		return copy;
	}
	
	// Direction sensitive (not symmetric)
	private boolean isConnected(Role r1, Role r2)
	{
		return !(this.Q.get(r1).get(r2) instanceof F17EBot);
	}
	
	private boolean hasMessage(Role self, Role peer)
	{
		ESend m = this.Q.get(self).get(peer);
		return m != null && !(m instanceof F17EBot);
	}
	
	// isActive(SState, Role) becomes isActive(EState)
	public static boolean isActive(EState s, int init)
	{
		return !isInactive(s, init);
	}
	
	private static boolean isInactive(EState s, int init)
	{
		return s.isTerminal() || (s.id == init && s.getStateKind() == EStateKind.ACCEPT);
	}
}

class F17EBot extends ESend
{
	public F17EBot()
	{
		super(Role.EMPTY_ROLE, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);
	}

	@Override
	public EReceive toDual(Role self)
	{
		throw new RuntimeException("Shouldn't get in here: " + this);
		//return this;
	}
	
	@Override
	public boolean isSend()
	{
		return false;
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
		if (!(obj instanceof F17EBot))
		{
			return false;
		}
		return super.equals(obj);
	}
	
	@Override
	public boolean canEqual(Object o)  // FIXME: rename canEquals
	{
		return o instanceof F17EBot;
	}
}
