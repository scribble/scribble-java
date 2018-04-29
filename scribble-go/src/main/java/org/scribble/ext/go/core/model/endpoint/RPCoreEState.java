package org.scribble.ext.go.core.model.endpoint;

import java.util.Set;

import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EModelFactory;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.RecVar;

// Cf. AssrtEState
public class RPCoreEState extends EState
{
	
	// Mutable -- not used in hashCode/equals (state ID only) -- cf. EState#hashCode/equals (and mutable label set)
	private RPIndexVar param;
	private RPInterval interval;
	private RPCoreEState nested;  // null if no nested FSM (then above also null)

	protected RPCoreEState(Set<RecVar> labs)
	{
		this(labs, null, null, null);
	}

	protected RPCoreEState(Set<RecVar> labs, RPIndexVar param, RPInterval interval, RPCoreEState nested)
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

	public RPIndexVar getParam()
	{
		return this.param;
	}

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
}
