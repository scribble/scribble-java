package org.scribble2.sesstype.name;

import org.scribble2.sesstype.kind.Kind;


//public interface Named<T extends IName>
public interface Named<T extends Name<K>, K extends Kind>
{
	//public abstract Name toName();
	abstract T toName();
}
