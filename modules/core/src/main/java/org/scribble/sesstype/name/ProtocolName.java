package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.ProtocolKind;


// Potentially qualified/canonical protocol name; not the AST primitive identifier
public abstract class ProtocolName<K extends ProtocolKind> extends MemberName<K>
{
	private static final long serialVersionUID = 1L;

	public ProtocolName(K kind, ModuleName modname, ProtocolName<K> membname)
	{
		super(kind, modname, membname);
	}
	
	public ProtocolName(K kind, String membname)
	{
		super(kind, membname);
	}

	@Override
	public abstract ProtocolName<K> getSimpleName();
}
