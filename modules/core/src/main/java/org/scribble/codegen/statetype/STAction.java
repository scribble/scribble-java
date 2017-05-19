package org.scribble.codegen.statetype;

import java.util.List;

import org.scribble.model.endpoint.actions.EAction;

// Target method signature
public abstract class STAction
{
	//public final String name;  // State type op name
	//public final String target;  // Target method name
	public final EAction act;
	
	public STAction(EAction act)//, String ret)
	{
		//this.name = name;
		this.act = act;
	}
	
	/*private static List<String> getArgs(EAction send)
	{
		List<String> args = new LinkedList<>();
		args.add(send.obj.toString());
		args.add(send.mid.toString());
		send.payload.elems.stream().forEach((e) -> args.add(e.toString()));
		return args;
	}*/

	public abstract String makeName();

	public abstract List<String> makeArgs();
	
	public abstract String makeBody();

	//public abstract <S extends STState<?, ?>> String makeRet(S curr, S succ);
	public abstract String makeRet(STState curr, STState succ);
	
	/*@Override
	public int hashCode()
	{
		int hash = 79;
		hash = 31 * hash + this.name.hashCode();
		hash = 31 * hash + this.act.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof STAction))
		{
			return false;
		}
		STAction a = (STAction) o;
		return a.canEqual(this) && 
				this.name.equals(a.name) && this.act.equals(a.act);// && this.ret.equals(a.ret);
	}
	
	public abstract boolean canEqual(Object o);*/
}
