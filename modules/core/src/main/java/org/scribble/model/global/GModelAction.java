package org.scribble.model.global;

import org.scribble.model.local.IOAction;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

// Mutable
// FIXME: should be Communication, cf. IOAction (superclass?)
public class GModelAction extends IOAction //implements PathElement
{
	//private static int counter = 1;

	//public final int id;
	public final Role src;
	//public final IOAction action;

	//private Set<GModelAction> deps = new HashSet<>();
	
	//public GModelAction(Role src, IOAction action)
	public GModelAction(Role src, Role dest, MessageId<?> mid, Payload payload)
	//public ModelAction(Role peer, MessageId mid)  // Should be a send/receive
	{
		/*this.id = counter++;
		this.src = src;
		this.action = action;*/
		super(dest, mid, payload);
		this.src = src;
	}
	
	/*public void addDependency(GModelAction ma)
	{
		this.deps.add(ma);
	}
	
	public Set<GModelAction> getDependencies()
	{
		return this.deps;
	}
	
	public boolean isDependentOn(GModelAction ma)
	{
		return this.deps.contains(ma);
	}*/
	
	@Override
	public int hashCode()
	{
		//return 827 * this.id;
		return 827 * this.src.hashCode() + super.hashCode();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof GModelAction))
		{
			return false;
		}
		GModelAction tmp = (GModelAction) o;
		//return this.id == ((GModelAction) o).id;
		return tmp.canEqual(this) && tmp.src.equals(this.src) && super.equals(o);
	}

	@Override
	public String toString()
	{
		//return this.id + ":" + this.src + ":" + this.action + " " + this.deps.stream().map((d) -> d.id).collect(Collectors.toList());
		return this.src + "->" + super.toString();
	}

	@Override
	protected String getCommSymbol()
	{
		return ": ";
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof GModelAction;
	}
}
