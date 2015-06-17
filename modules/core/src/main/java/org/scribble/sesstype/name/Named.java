package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.Kind;


//public interface Named<T extends IName>
//public interface Named<T extends Name<K>, K extends Kind>
public interface Named<K extends Kind>
{
	//public abstract Name toName();
	//abstract T toName();
	Name<K> toName();
}
