package org.scribble2.sesstype.name;

// Potentially qualified/canonical protocol name; not the AST primitive identifier
public class ProtocolName extends MemberName
{
	private static final long serialVersionUID = 1L;

	public ProtocolName(ModuleName modname, String membname)
	{
		super(KindEnum.PROTOCOL, modname, membname);
	}
	
	public ProtocolName(String membname)
	{
		super(KindEnum.PROTOCOL, membname);
	}

	@Override
	public ProtocolName getSimpleName()
	{
		return new ProtocolName(getLastElement());
	}
}
