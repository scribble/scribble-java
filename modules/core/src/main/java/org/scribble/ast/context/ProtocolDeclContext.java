package org.scribble.ast.context;

import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.util.DependencyMap;

public interface ProtocolDeclContext<K extends ProtocolKind>
{
	DependencyMap<? extends ProtocolName<K>> getDependencyMap();
}
