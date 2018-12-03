package org.scribble.ext.go.core.model.endpoint;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ext.go.core.model.endpoint.action.RPCoreEAction;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreECrossReceive;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreECrossSend;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.type.index.RPIndexVar;
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
