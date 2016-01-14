package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.OpKind;

public class Op extends AbstractName<OpKind> implements MessageId<OpKind>
{
	private static final long serialVersionUID = 1L;
	
	public static final Op EMPTY_OPERATOR = new Op();

	protected Op()
	{
		super(OpKind.KIND);
	}

	public Op(String text)
	{
		super(OpKind.KIND, text);
	}

	@Override
	public boolean isOp()
	{
		return true;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Op))
		{
			return false;
		}
		Op n = (Op) o;
		return n.canEqual(this) && super.equals(o);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof Op;
	}

	@Override
	public int hashCode()
	{
		int hash = 2801;
		hash = 31 * super.hashCode();
		return hash;
	}
}
