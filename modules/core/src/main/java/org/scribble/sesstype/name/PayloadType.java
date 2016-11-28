package org.scribble.sesstype.name;

import org.scribble.sesstype.Arg;
import org.scribble.sesstype.kind.PayloadTypeKind;


public interface PayloadType<K extends PayloadTypeKind> extends Arg<K>
{
	default boolean isDataType()
	{
		return false;
	}

	default boolean isGDelegationType()
	{
		return false;
	}
	
	/*public boolean isLDelegationType()
	{
		return true;
	}*/
}
