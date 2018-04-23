package org.scribble.ext.go.core.ast.local;

import java.util.Set;

import org.scribble.ext.go.core.ast.RPCoreType;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.type.kind.Local;


public interface RPCoreLType extends RPCoreType<Local>
{
	Set<RPIndexVar> getIndexVars();  // FIXME: factor up?
}
