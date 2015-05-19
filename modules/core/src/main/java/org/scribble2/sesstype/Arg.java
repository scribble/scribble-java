package org.scribble2.sesstype;

import org.scribble2.sesstype.kind.Kind;


// A subprotocol argument: SigKind or PayloadTypeKind -- could factor out an ArgumentKind
public interface Arg<K extends Kind>
{
	//KindEnum getKindEnum();
	Kind getKind();

	//boolean isParameter();
}
