package org.scribble2.sesstype.name;

import org.scribble2.sesstype.kind.Kind;


public interface KindedNamed<K extends Kind>
{
	KindedName<K> toName();
}
