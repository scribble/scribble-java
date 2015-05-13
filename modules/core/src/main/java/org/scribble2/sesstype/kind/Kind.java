package org.scribble2.sesstype.kind;

import org.scribble2.sesstype.name.Name;

public abstract class Kind
{
	public static <K extends Kind> Name<K> castName(K kind, Name<? extends Kind> name)
	{
		kind.getClass().cast(name.kind);
		@SuppressWarnings("unchecked")
		Name<K> tmp = (Name<K>) name;
		return tmp;
	}
	
	@Override
	public String toString()
	{
		return this.getClass().toString();
	}
	
	
	@Override
	public abstract boolean equals(Object o);
}
