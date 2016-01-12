package org.scribble.sesstype.name;

import org.scribble.model.global.PathElement;
import org.scribble.sesstype.kind.RecVarKind;

public class RecVar extends AbstractName<RecVarKind> implements PathElement
{
	private static final long serialVersionUID = 1L;

	protected RecVar()
	{
		super(RecVarKind.KIND);
	}

	public RecVar(String text)
	{
		super(RecVarKind.KIND, text);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof RecVar))
		{
			return false;
		}
		RecVar n = (RecVar) o;
		return n.canEqual(this) && super.equals(o);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof RecVar;
	}

	@Override
	public int hashCode()
	{
		int hash = 2819;
		hash = 31 * super.hashCode();
		return hash;
	}
}
