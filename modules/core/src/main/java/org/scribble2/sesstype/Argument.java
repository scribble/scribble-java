package org.scribble2.sesstype;

import org.scribble2.sesstype.name.Kind;

public interface Argument
{
	Kind getKind();

	boolean isParameter();
}
