package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.Kind;


public interface Named<K extends Kind>
{
	Name<K> toName();
}
