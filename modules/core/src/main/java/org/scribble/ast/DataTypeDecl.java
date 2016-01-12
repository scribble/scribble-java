package org.scribble.ast;

import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.ast.name.qualified.MemberNameNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.name.DataType;
import org.scribble.sesstype.name.ModuleName;

public class DataTypeDecl extends NonProtocolDecl<DataTypeKind>
{
	public DataTypeDecl(String schema, String extName, String source, DataTypeNode name)
	{
		super(schema, extName, source, name);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new DataTypeDecl(this.schema, this.extName, this.source, getNameNode());
	}
	
	@Override
	public DataTypeDecl clone()
	{
		DataTypeNode name = (DataTypeNode) this.name.clone();
		return AstFactoryImpl.FACTORY.DataTypeDecl(this.schema, this.extName, this.source, name);
	}

	@Override
	public DataTypeDecl reconstruct(String schema, String extName, String source, MemberNameNode<DataTypeKind> name)
	{
		ScribDel del = del();
		DataTypeDecl dtd = new DataTypeDecl(schema, extName, source, (DataTypeNode) name);
		dtd = (DataTypeDecl) dtd.del(del);
		return dtd;
	}
	
	@Override
	public boolean isDataTypeDecl()
	{
		return true;
	}

	@Override
	public DataTypeNode getNameNode()
	{
		return (DataTypeNode) super.getNameNode();
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
