package org.scribble.ext.go.core.ast;

import org.scribble.type.kind.ProtocolKind;

// ast here means "core syntax" of session types -- it does not link the actual Scribble source (cf. base ast classes)
public interface RPCoreType<K extends ProtocolKind>
{
	boolean canEquals(Object o);
}
