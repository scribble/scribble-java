package org.scribble.lang;

import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.RecVar;

public abstract class Continue<K extends ProtocolKind>
		extends SessTypeBase<K> implements SessType<K>
{
	public final RecVar recvar;

	public Continue(org.scribble.ast.Continue<K> source, RecVar recvar)
	{
		super(source);
		this.recvar = recvar;
	}

	public abstract Continue<K> reconstruct(
			org.scribble.ast.Continue<K> source, RecVar recvar);
	
	@Override
	public String toString()
	{
		return "continue " + this.recvar + ";";
	}

	@Override
	public int hashCode()
	{
		int hash = 3217;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.recvar.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Continue))
		{
			return false;
		}
		Continue<?> them = (Continue<?>) o;
		return super.equals(this)  // Does canEquals
				&& this.recvar.equals(them.recvar);
	}
}
