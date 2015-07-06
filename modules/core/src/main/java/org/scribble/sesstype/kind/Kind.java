package org.scribble.sesstype.kind;

import org.scribble.sesstype.name.Name;

public interface Kind
{
	public static <K extends Kind> Name<K> castName(K kind, Name<? extends Kind> name)
	{
		kind.getClass().cast(name.getKind());
		@SuppressWarnings("unchecked")
		Name<K> tmp = (Name<K>) name;
		return tmp;
	}
}
