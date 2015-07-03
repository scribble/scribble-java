package org.scribble.ast.name.qualified;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.sesstype.Arg;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.name.DataType;

public class DataTypeNameNode extends MemberNameNode<DataTypeKind> implements PayloadElemNameNode
{
	public DataTypeNameNode(String... elems)
	{
		super(elems);
	}

	@Override
	protected DataTypeNameNode copy()
	{
		return new DataTypeNameNode(this.elems);
	}
	
	@Override
	public DataTypeNameNode clone()
	{
		return (DataTypeNameNode) AstFactoryImpl.FACTORY.QualifiedNameNode(DataTypeKind.KIND, this.elems);
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
		if (!(o instanceof DataTypeNameNode))
		{
			return false;
		}
		return ((DataTypeNameNode) o).canEqual(this) && super.equals(o);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof DataTypeNameNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 409;
		hash = 31 * hash + this.elems.hashCode();
		return hash;
	}
}
