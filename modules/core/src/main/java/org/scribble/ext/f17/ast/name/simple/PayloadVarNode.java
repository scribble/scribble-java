package org.scribble.ext.f17.ast.name.simple;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.ast.name.simple.SimpleNameNode;
import org.scribble.ext.f17.sesstype.name.PayloadVar;
import org.scribble.sesstype.Arg;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.kind.NonRoleArgKind;
import org.scribble.sesstype.name.PayloadType;


//public class PayloadVarNode extends SimpleNameNode<PayloadVarKind> implements PayloadElemNameNode<PayloadVarKind>
public class PayloadVarNode extends SimpleNameNode<DataTypeKind> implements PayloadElemNameNode<DataTypeKind>
{
	public PayloadVarNode(CommonTree source, String identifier)
	{
		super(source, identifier);
	}

	@Override
	protected PayloadVarNode copy()
	{
		return new PayloadVarNode(this.source, getIdentifier());
	}
	
	@Override
	public PayloadVarNode clone()
	{
		//return (PayloadVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(this.source, PayloadVarKind.KIND, getIdentifier());
		return (PayloadVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(this.source, DataTypeKind.KIND, getIdentifier());
	}
	
	@Override
	public PayloadVar toName()
	{
		String id = getIdentifier();
		/*if (id.equals(EMPTY_OPERATOR_IDENTIFIER))  // Maybe subsume all payload vars as annotated, possibly by empty annot
		{
			return ;
		}*/
		return new PayloadVar(id);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof PayloadVarNode))
		{
			return false;
		}
		return ((PayloadVarNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof PayloadVarNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 347;
		hash = 31 * super.hashCode();
		return hash;
	}

	@Override
	public Arg<? extends NonRoleArgKind> toArg()
	{
		return (PayloadVar) toName();
	}

	@Override
	public PayloadType<DataTypeKind> toPayloadType()
	{
		return (PayloadVar) toName();
	}
}
