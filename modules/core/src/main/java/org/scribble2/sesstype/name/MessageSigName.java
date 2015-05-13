package org.scribble2.sesstype.name;

import org.scribble2.sesstype.Message;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.kind.SigKind;


// The name of a declared (imported) message signature member
//public class MessageSignatureName extends MemberName implements Message
//public class MessageSignatureName extends Name<SigKind> implements Message
public class MessageSigName extends MemberName<SigKind> implements Message
{
	private static final long serialVersionUID = 1L;
	
	//public final Scope scope;

	/*public MessageSignatureName(ModuleName modname, String membname)
	{
		super(KindEnum.TYPE, modname, membname);
	}*/
	
	//public MessageSignatureName(Scope scope, MessageSignatureName simplename)  // FIXME: could make a compound name with scope built into it
	public MessageSigName(ModuleName modname, MessageSigName simplename)
	{
		/*super(SigKind.KIND, simplename);
		this.scope = scope;*/
		//super(SigKind.KIND, Name.compileElements(scope.getElements(), simplename.getLastElement()));
		super(SigKind.KIND, modname, simplename);
	}

	public MessageSigName(String simplename)
	{
		super(SigKind.KIND, simplename);
	}

	@Override
	public Kind getKind()
	{
		return SigKind.KIND;  // Same as this.kind
	}

	/*@Override
	public Name<? extends Kind> getPrefix()
	{
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public MessageSigName getSimpleName()
	{
		return new MessageSigName(getLastElement());
	}

	/*@Override
	public MessageSignatureName getSimpleName()
	{
		return new MessageSignatureName(getLastElement());
	}
	
	@Override
	public boolean isParameter()
	{
		return false;
	}

	@Override
	public ScopedMessage toScopedMessage(Scope scope)
	{
		// TODO Auto-generated method stub
		return null;
	}*/

	/*@Override
	public ScopedMessageSignatureName toScopedMessage(Scope scope)
	{
		return new ScopedMessageSignatureName(scope, this.toString());
	}*/
}
