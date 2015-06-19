package org.scribble.ast;

import org.scribble.ast.name.qualified.DataTypeNameNode;
import org.scribble.ast.name.qualified.MemberNameNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.name.DataType;
import org.scribble.sesstype.name.ModuleName;

public class DataTypeDecl extends NonProtocolDecl<DataTypeKind>
{
	public DataTypeDecl(String schema, String extName, String source, DataTypeNameNode name)
	{
		super(schema, extName, source, name);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new DataTypeDecl(this.schema, this.extName, this.source, (DataTypeNameNode) this.name);
	}

	@Override
	protected DataTypeDecl reconstruct(String schema, String extName, String source, MemberNameNode<DataTypeKind> name)
	{
		ScribDel del = del();
		DataTypeDecl dtd = new DataTypeDecl(schema, extName, source, (DataTypeNameNode) name);
		dtd = (DataTypeDecl) dtd.del(del);
		return dtd;
	}
	
	@Override
	public boolean isDataTypeDecl()
	{
		return true;
	}

	@Override
	public DataType getDeclName()
	{
		return (DataType) super.getDeclName();
	}

	@Override
	public DataType getFullMemberName(Module mod)
	{
		ModuleName fullmodname = mod.getFullModuleName();
		return new DataType(fullmodname, getDeclName());
	}

	@Override
	public String toString()
	{
		return Constants.TYPE_KW + " <" + this.schema + "> " + this.extName
				+ " " + Constants.FROM_KW + " " + this.source + " "
				+ Constants.AS_KW + " " + this.name + ";";
	}
}