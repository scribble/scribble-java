package org.scribble.ast.name.qualified;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.sesstype.Arg;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.name.DataType;

public class DataTypeNode extends MemberNameNode<DataTypeKind> implements PayloadElemNameNode
{
	public DataTypeNode(String... elems)
	{
		super(elems);
	}

	@Override
	protected DataTypeNode copy()
	{
		return new DataTypeNode(this.elems);
	}
	
	@Override
	public DataTypeNode clone()
	{
		return (DataTypeNode) AstFactoryImpl.FACTORY.QualifiedNameNode(DataTypeKind.KIND, this.elems);
	}

	@Override
	public DataType toName()
	{
		DataType membname = new DataType(getLastElement());
		return isPrefixed()
				? new DataType(getModuleNamePrefix(), membname)
		    : membname;
	}

	@Override
	public boolean isDataTypeNameNode()
	{
		return true;
	}

	@Override
	public Arg<DataTypeKind> toArg()
	{
		return toPayloadType();
	}

	@Override
	public DataType toPayloadType()
	{
		return toName();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof DataTypeNode))
		{
			return false;
		}
		return ((DataTypeNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof DataTypeNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 409;
		hash = 31 * hash + this.elems.hashCode();
		return hash;
	}
}
