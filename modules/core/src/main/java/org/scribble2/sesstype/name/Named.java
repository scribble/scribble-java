package org.scribble2.sesstype.name;


public interface Named<T extends Name>
{
	//public abstract Name toName();
	abstract T toName();
}
