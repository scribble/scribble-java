package org.scribble2.sesstype;

import org.scribble2.sesstype.kind.Kind;


// A subprotocol argument: SigKind or DataTypeKind -- can factor out an ArgumentKind
public interface Argument<K extends Kind>
{
	//KindEnum getKindEnum();
	Kind getKind();

	//boolean isParameter();
}
