package org.scribble.sesstype;

import org.scribble.sesstype.kind.Kind;


// A subprotocol argument (DoArgNode): SigKind or PayloadTypeKind -- could factor out an ArgumentKind
public interface Arg<K extends Kind>
{
	//KindEnum getKindEnum();
	Kind getKind();

	//boolean isParameter();
}
