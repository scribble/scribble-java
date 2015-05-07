package org.scribble2.sesstype.name;


// Potentially qualified/canonical payload type name; not the AST primitive identifier
public class PayloadType extends MemberName implements PayloadTypeOrParameter
{
	private static final long serialVersionUID = 1L;

	public PayloadType(ModuleName modname, String membname)
	{
		super(Kind.TYPE, modname, membname);
	}
	
	public PayloadType(String simplename)
	{
		super(Kind.TYPE, simplename);
	}

	@Override
	public PayloadType getSimpleName()
	{
		return new PayloadType(getLastElement());
	}
	
	@Override
	public boolean isParameter()
	{
		return false;
	}
}
