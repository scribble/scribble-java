package org.scribble.sesstype.name;

import java.io.Serializable;

import org.scribble.sesstype.kind.Kind;

// Structured name for packages, modules, members etc; subsuming simple names like roles, operators, etc
@Deprecated
public interface IName extends Serializable
{
	KindEnum getKindEnum();
	/*boolean isEmpty();
	boolean isPrefixed();
	Name getPrefix();
	Name getSimpleName();
	String[] getElements();*/
	
	Kind getKind();
}
