package org.scribble.ext.f17.sesstype.name;

import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.name.AbstractName;
import org.scribble.sesstype.name.PayloadType;

// FIXME: refactor PayloadVarKind as simply DataTypeKind?
//public class PayloadVar extends AbstractName<PayloadVarKind> //implements PathElement
public class PayloadVar extends AbstractName<DataTypeKind>
		implements PayloadType<DataTypeKind>, AnnotType //implements PathElement
{
	private static final long serialVersionUID = 1L;

	protected PayloadVar()
	{
		//super(PayloadVarKind.KIND);
		super(DataTypeKind.KIND);
	}

	public PayloadVar(String text)
	{
		super(DataTypeKind.KIND, text);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof PayloadVar))
		{
			return false;
		}
		PayloadVar n = (PayloadVar) o;
		return n.canEqual(this) && super.equals(o);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof PayloadVar;
	}

	@Override
	public int hashCode()
	{
		int hash = 3217;
		hash = 31 * super.hashCode();
		return hash;
	}
}
