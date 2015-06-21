package org.scribble.sesstype;

import org.scribble.sesstype.kind.NonRoleArgKind;


// A subprotocol argument (DoArgNode): SigKind or PayloadTypeKind -- could factor out an ArgumentKind
public interface Arg<K extends NonRoleArgKind>
{
	NonRoleArgKind getKind();
}
