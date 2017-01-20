package org.scribble.ext.f17.sesstype.kind;

import org.scribble.sesstype.kind.AbstractKind;
import org.scribble.sesstype.kind.PayloadTypeKind;

@Deprecated
public class PayloadVarKind extends AbstractKind implements PayloadTypeKind  // PayloadVars directly kinded as DataTypeKind
{
	public static final PayloadVarKind KIND = new PayloadVarKind();
	
	protected PayloadVarKind()
	{

	}

	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this)
		{
			return true;
		}
		if (!(o instanceof PayloadVarKind))
		{
			return false;
		}
		return ((PayloadVarKind) o).canEqual(this);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof PayloadVarKind;
	}
}
