package org.scribble.ext.go.core.model.endpoint;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ext.go.ast.global.RPGProtocolHeader;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEAction;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreECrossReceive;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreECrossSend;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.type.annot.RPAnnotExpr;
import org.scribble.ext.go.type.index.RPIndexSelf;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.ext.go.util.Smt2Translator;
import org.scribble.ext.go.util.Z3Wrapper;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EModelFactory;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.model.endpoint.actions.EReceive;
import org.scribble.model.endpoint.actions.ESend;
import org.scribble.type.name.RecVar;

// Cf. AssrtEState
public class RPCoreEState extends EState
{
	
	// Mutable -- not used in hashCode/equals (state ID only) -- cf. EState#hashCode/equals (and mutable label set)
	//private RPForeachVar param;
	private RPIndexVar param;
	private RPInterval interval;
	private RPCoreEState nested;  // null if no nested FSM (then above also null)

	protected RPCoreEState(Set<RecVar> labs)
	{
		this(labs, null, null, null);
	}

	protected RPCoreEState(Set<RecVar> labs, //RPForeachVar param, 
			RPIndexVar param, 
			RPInterval interval, RPCoreEState nested)
	{
		super(labs);
		this.param = param;
		this.interval = interval;
		this.nested = nested;
	}

	public boolean isPeer(Smt2Translator smt2t, RPRoleVariant self, RPRoleVariant cand) 
	{
		RPRoleVariant peerVariant = cand;
		
		Set<RPIndexedRole> actionIRs = //MState.getReachableActions
				RPCoreEState.getReachableActions((RPCoreEModelFactory) smt2t.job.ef,
						this).stream()  // FIXME: static "overloading" (cf. MState) error prone
				.map(a -> ((RPCoreEAction) a).getPeer()).collect(Collectors.toSet());
						// CHECKME: in presence of foreach, actions will include unqualified foreachvars -- is that what the following Z3 assertion is/should be doing?

		/*Set<RPRoleVariant> peers = new HashSet<>();
		next: for (RPRoleVariant peerVariant : (Iterable<RPRoleVariant>)  // Candidate
				this.E0.values().stream()
						.flatMap(m -> m.keySet().stream())::iterator)*/
		{
			/*if (//!peerVariant.equals(self) &&   // No: e.g., pipe/ring middlemen
					!peers.contains(peerVariant))*/
			{
				smt2t.job.debugPrintln("\n[rp-core] For " + self + ", checking peer candidate: " + peerVariant);
						// "checking potential peer" means checking if any of our action-peer indexed roles fits the candidate variant-peer (so we would need a dial/accept for them)
				
				for (RPIndexedRole ir : actionIRs)
				{
					if (ir.getName().equals(peerVariant.getName()))
					{
						if (ir.intervals.size() > 1)
						{
							throw new RuntimeException("[rp-core] TODO: multi-dimension intervals: " + ir);  // No?  Multiple intervals is not actually about multidim intervals, it's about constraint intersection?  (A multdim interval should be a single interval value?)
						}
						RPInterval d = ir.intervals.stream().findAny().get();
						Set<RPIndexVar> vars = Stream.concat(peerVariant.intervals.stream().flatMap(x -> x.getIndexVars().stream()), peerVariant.cointervals.stream().flatMap(x -> x.getIndexVars().stream()))
								.collect(Collectors.toSet());
						vars.addAll(ir.getIndexVars());

						// CHECKME: need to further constrain K? (by family?)
						
						List<String> cs = new LinkedList<>();
						cs.addAll(vars.stream().map(x -> smt2t.makeGte(x.toSmt2Formula(smt2t), smt2t.getDefaultBaseValue())).collect(Collectors.toList()));  // FIXME: generalise, parameter domain annotations
						
						if (smt2t.global.header instanceof RPGProtocolHeader)
						{
							// FIXME: WIP
							RPAnnotExpr annot = RPAnnotExpr.parse(((RPGProtocolHeader) smt2t.global.header).annot);
							if (annot.getVars().stream().map(x -> x.toString()).allMatch(x ->
									vars.stream().map(y -> y.toString()).anyMatch(y -> x.equals(y))))  // TODO: refactor
							{
								cs.add(annot.toSmt2Formula(smt2t));
							}
						}
						
						for (RPInterval ival : peerVariant.intervals)  // Is there a peer index inside all the peer-variant intervals
						{
							cs.add(smt2t.makeGte("peer", ival.start.toSmt2Formula(smt2t)));
							cs.add(smt2t.makeLte("peer", ival.end.toSmt2Formula(smt2t)));
						}
						if (!peerVariant.cointervals.isEmpty())
						{
							// ...and the peer index is outside one of the peer-variant cointervals
							cs.addAll(peerVariant.cointervals.stream().map(x ->
										smt2t.makeOr(smt2t.makeLt("peer", x.start.toSmt2Formula(smt2t)), smt2t.makeGt("peer", x.end.toSmt2Formula(smt2t)))
							).collect(Collectors.toList()));
						}
						// ...and the peer index is inside our I/O action interval -- then this is peer-variant is a peer
						cs.add(smt2t.makeGte("peer", d.start.toSmt2Formula(smt2t)));
						cs.add(smt2t.makeLte("peer", d.end.toSmt2Formula(smt2t)));

						if (vars.contains(RPIndexSelf.SELF))
						{
							// If self name is peer name, peer index is not self index
							if (peerVariant.getName().equals(self.getName()))
							{
								cs.add(smt2t.makeNot(smt2t.makeEq("peer", "self")));
							}
							// TODO: factor out variant/covariant inclusion/exclusion with above
							for (RPInterval ival : self.intervals)  // Is there a self index inside all the self-variant intervals
							{
								cs.add(smt2t.makeGte("self", ival.start.toSmt2Formula(smt2t)));
								cs.add(smt2t.makeLte("self", ival.end.toSmt2Formula(smt2t)));
							}
							if (!self.cointervals.isEmpty())
							{
								// ...and the self index is outside one of the self-variant cointervals
								cs.addAll(self.cointervals.stream().map(x ->
										smt2t.makeOr(smt2t.makeLt("self", x.start.toSmt2Formula(smt2t)), smt2t.makeGt("self", x.end.toSmt2Formula(smt2t)))
								).collect(Collectors.toList()));
							}
						}

						String smt2 = smt2t.makeAnd(cs);
						List<String> tmp = new LinkedList<>();
						tmp.add("peer");
						tmp.addAll(vars.stream().map(x -> x.toSmt2Formula(smt2t)).collect(Collectors.toList()));
						smt2 = smt2t.makeExists(tmp, smt2); 
						smt2 = smt2t.makeAssert(smt2);
						
						smt2t.job.debugPrintln("[rp-core] Running Z3 on " + d + " :\n" + smt2);
						
						boolean isSat = Z3Wrapper.checkSat(smt2t.job, smt2t.global, smt2);
						smt2t.job.debugPrintln("[rp-core] Checked sat: " + isSat);
						/*if (isSat)
						{
							peers.add(peerVariant);
							continue next;
						}*/
						return isSat;
					}
				}
			}
		}
		
		return false;
	}

	@Override
	protected RPCoreEState cloneNode(EModelFactory ef, Set<RecVar> labs)
	{
		return ((RPCoreEModelFactory) ef).newRPCoreEState(labs, this.param, this.interval, this.nested);
	}

	public boolean hasNested()
	{
		return this.nested != null;
	}
	
	// Mutable getters/setters -- cf. super label set

	//public RPForeachVar getParam()
	public RPIndexVar getParam()
	{
		return this.param;
	}

	//public void setParam(RPForeachVar param)
	public void setParam(RPIndexVar param)
	{
		this.param = param;
	}

	public RPInterval getInterval()
	{
		return this.interval;
	}

	public void setInterval(RPInterval interval)
	{
		this.interval = interval;
	}

	public RPCoreEState getNested()
	{
		return this.nested;
	}

	public void setNested(RPCoreEState nested)
	{
		this.nested = nested;
	}

	@Override
	protected String getNodeLabel()
	{
		String labs = this.labs.toString();
		return "label=\"" + this.id + ": " + labs.substring(1, labs.length() - 1)  // From super.getNodeLabel
				+ (hasNested() ? this.param + ":" + this.interval + "; " + this.nested.id : "")
				+ "\"";  // FIXME: would be more convenient for this method to return only the label body
	}
	
	@Override
	public RPCoreEState getSuccessor(EAction a)
	{
		return (RPCoreEState) super.getSuccessor(a);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 4817;
		hash = 31 * hash + super.hashCode();  
		// N.B. use state ID only -- following super pattern -- to allow, e.g., mutable label set
		/*if (hasNested())
		{
			hash = 31 * hash + this.param.hashCode();
			hash = 31 * hash + this.interval.hashCode();
			hash = 31 * hash + this.nested.hashCode();
		}*/
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof RPCoreEState))
		{
			return false;
		}
		//RPCoreEState them = (RPCoreEState) o;
		return super.equals(o);   // Checks canEquals
				/*&& hasNested()  // No: use state ID only
						? (them.hasNested() && this.param.equals(them.param) && this.interval.equals(them.interval) && this.nested.equals(them.nested))
						: !them.hasNested();*/
	}

	@Override
	protected boolean canEquals(MState<?, ?, ?, ?> s)
	{
		return s instanceof RPCoreEState;
	}

	// FIXME: make not static -- otherwise choosing statics (cf. MState) by overloading, error prone
	public static Set<RPCoreEState> getReachableStates(RPCoreEState start)
	{
		Set<RPCoreEState> res = MState.getReachableStates(start).stream()
				.map(s -> (RPCoreEState) s).collect(Collectors.toSet());

		//System.out.println("ccc: " + start.id + ",, " + res);

		Set<RPCoreEState> nested = res.stream()
				.flatMap(s -> s.hasNested() ? Stream.concat(Stream.of(s.nested), getReachableStates(s.nested).stream()) : Stream.of()).collect(Collectors.toSet());
		res.addAll(nested);
		if (start.hasNested())  // N.B. start itself is not considered reachable -- correct?
		{
			res.add(start.nested);
			res.addAll(getReachableStates(start.nested));
		}
		return res;
	}

	// FIXME: make not static -- otherwise choosing statics (cf. MState) by overloading, error prone
	// Note: returns "syntactic" results, e.g., foreachvars directly returned unqualified
	// cf. RPCoreGForeach#getIndexedRoles
	public static Set<RPCoreEAction> getReachableActions(RPCoreEModelFactory ef, RPCoreEState start)
	{
		Set<RPCoreEState> rs = getReachableStates(start);
		rs.add(start);
		Set<RPCoreEAction> tmp = rs.stream()
				.flatMap(s -> s.getAllActions().stream().map(a -> (RPCoreEAction) a)).collect(Collectors.toSet());
		
		if (start.hasNested())
		{
			Set<RPCoreEAction> tmp2 = new HashSet<>();
			for (RPCoreEAction a : tmp)
			{
				if (a.getPeer().intervals.stream().flatMap(d -> d.getIndexVars().stream()).anyMatch(x -> x.toString().equals(start.param.toString())))
				{
					Set<RPInterval> blah = new HashSet<>();
					for (RPInterval d : a.getPeer().intervals)
					{
						// FIXME: current assuming if any I occurrence, then interval is be [I,I]
						if (d.getIndexVars().stream().anyMatch(x -> x.toString().equals(start.param.toString())))
						{
							blah.add(start.interval);
						}
						else
						{
							blah.add(d);
						}
					}
					RPIndexedRole b = new RPIndexedRole(a.getPeer().getName().toString(), blah);

					if (a instanceof RPCoreECrossSend)
					{
						tmp2.add(ef.newParamCoreECrossSend(b, ((ESend) a).mid, ((ESend) a).payload));
					}
					else if (a instanceof RPCoreECrossReceive)
					{
						tmp2.add(ef.newParamCoreECrossReceive(b, ((EReceive) a).mid, ((EReceive) a).payload));
					}
					else
					{
						throw new RuntimeException("[rp-core] Shouldn't get in here: " + a);
					}
				}
				else
				{
					tmp2.add(a);
				}
			}
			tmp = tmp2;
		}
		
		//System.out.println("bbb: " + start.id + ",, " + rs + ",, " + tmp);
		
		return tmp;
	}
}
