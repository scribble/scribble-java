package org.scribble.ast.name.simple;

import org.scribble.ast.NonRoleArgNode;
import org.scribble.ast.MessageNode;
import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.sesstype.Arg;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.SigKind;
import org.scribble.sesstype.name.DataType;
import org.scribble.sesstype.name.MessageSigName;
import org.scribble.sesstype.name.Name;
import org.scribble.sesstype.name.PayloadType;
import org.scribble.visit.Substitutor;

public class NonRoleParamNode<K extends Kind> extends SimpleNameNode<K> implements MessageNode, PayloadElemNameNode
{
	public final K kind;  // AmbiguousNameNode --disamb--> kinded Parameter
	
	public NonRoleParamNode(K kind, String identifier)
	{
		super(identifier);
		this.kind = kind;
	}

	@Override
	protected NonRoleParamNode<K> copy()
	{
		return new NonRoleParamNode<>(this.kind, getIdentifier());
	}
	
	@Override
	public NonRoleArgNode substituteNames(Substitutor subs)
	{
		Arg<K> arg = toArg();
		NonRoleArgNode an;
		if (this.kind.equals(SigKind.KIND))
		{
			an = subs.getArgumentSubstitution(arg);  // Reconstruct/clone?
		}
		else if (this.kind.equals(DataTypeKind.KIND))
		{
			an = subs.getArgumentSubstitution(arg);  // Reconstruct/clone?)
		}
		else
		{
			throw new RuntimeException("TODO: " + this);
		}
		an = (NonRoleArgNode) an.del(del());
		return an;
	}
	
	@Override
	public Name<K> toName()
	{
		String id = getIdentifier();
		if (this.kind.equals(SigKind.KIND))
		{
			return Kind.castName(this.kind, new MessageSigName(id));
		}
		else if (this.kind.equals(DataTypeKind.KIND))
		{
			return Kind.castName(this.kind, new DataType(id));
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + this.kind);
		}
	}

	@Override
	public boolean isParamNode()
	{
		return true;
	}

	@Override
	public Arg<K> toArg()
	{
		Arg<? extends Kind> arg;
		if (this.kind.equals(DataTypeKind.KIND))  // FIXME: payload kind hardcorded to data type kinds
		{
			arg = toPayloadType();
		}
		else if (this.kind.equals(SigKind.KIND))
		{
			 arg = toMessage();
		}
		else
		{
			throw new RuntimeException("Shouldn't get here: " + this);
		}
		@SuppressWarnings("unchecked")
		Arg<K> tmp = (Arg<K>) arg;
		return tmp;
	}

	@Override
	public Message toMessage()
	{
		if (!this.kind.equals(SigKind.KIND))
		{
			throw new RuntimeException("Not a sig kind parameter: " + this);
		}
		return (Message) toName();
	}

	@Override
	public PayloadType<? extends Kind> toPayloadType()
	{
		if (!this.kind.equals(DataTypeKind.KIND)) // FIXME: payload kind hardcorded to data type kinds
		{
			throw new RuntimeException("Not a payload kind parameter: " + this);
		}
		return (DataType) toName();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof NonRoleParamNode))
		{
			return false;
		}
		NonRoleParamNode<?> n = (NonRoleParamNode<?>) o;
		return n.canEqual(this) && this.kind.equals(n.kind) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof NonRoleParamNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 317;
		hash = 31 * super.hashCode();
		return hash;
	}
}