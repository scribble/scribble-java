package org.scribble2.sesstype.name;

import java.io.Serializable;

// Structured name for packages, modules, members etc; subsuming simple names like roles, operators, etc
public interface Name extends Serializable
{
	Kind getKind();
	/*boolean isEmpty();
	boolean isPrefixed();
	Name getPrefix();
	Name getSimpleName();
	String[] getElements();*/
}
