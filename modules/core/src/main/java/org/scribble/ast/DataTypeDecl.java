package org.scribble.ast;

import org.scribble.ast.name.qualified.DataTypeNameNode;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.name.DataType;

//public class PayloadTypeDecl extends NonProtocolDecl //AbstractNode implements ModuleMember //implements NameDeclaration
//public class DataTypeDecl extends NonProtocolDecl<DataType, DataTypeKind>
public class DataTypeDecl extends NonProtocolDecl<DataTypeKind>
{
	//public PayloadTypeDecl(String schema, String extName, String source, SimplePayloadTypeNode alias)
	//public DataTypeDecl(String schema, String extName, String source, DataTypeNameNode alias)
	//public DataTypeDecl(String schema, String extName, String source, MemberNameNode<? extends DataType, DataTypeKind> alias)
	public DataTypeDecl(String schema, String extName, String source, DataTypeNameNode alias)
	{
		super(schema, extName, source, alias);
	}

	@Override
	public DataType getDeclName()
	{
		return (DataType) super.getDeclName();
	}

	/*@Override
	public PayloadTypeDecl disambiguate(PayloadTypeOrParameterDisambiguator disamb) throws ScribbleException
	{
		PayloadTypeDecl visited = (PayloadTypeDecl) super.disambiguate(disamb);
		disamb.addPayloadType(visited);
		return visited;
	}*/

	/*@Override
	public PayloadTypeDecl visitChildren(NodeVisitor nv) throws ScribbleException
	{
		SimplePayloadTypeNode alias = (SimplePayloadTypeNode) visitChild(this.alias, nv);
		return new PayloadTypeDecl(this.ct, this.schema, this.extName, this.source, alias);
	}*/

	/*@Override
	protected DataTypeDecl reconstruct(CommonTree ct, String schema, String extName, String source, PrimitiveNameNode alias)
	{
		return new PayloadTypeDecl(ct, schema, extName, source, (SimplePayloadTypeNode) alias);
	}*/
	
	/*@Override
	public DataType getAliasName()
	{
		return (DataType) super.getAliasName();
	}*/

	/*@Override
	public DataType toName()
	{
		return (DataType) super.toName(); 
	}*/
	
	@Override
	public boolean isDataTypeDecl()
	{
		return true;
	}

	@Override
	public String toString()
	{
		return Constants.TYPE_KW + " <" + this.schema + "> " + this.extName
				+ " " + Constants.FROM_KW + " " + this.source + " "
				+ Constants.AS_KW + " "
				//+ this.alias + ";";
				+ this.name + ";";
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new DataTypeDecl(this.schema, this.extName, this.source, (DataTypeNameNode) this.name);
	}

	/*public PayloadType getFullPayloadTypeName()
	{
		ModuleName fullmodname = AntlrModule.getFullModuleName(AntlrPayloadTypeDecl.getModuleParent(this.ct));
		return new PayloadType(fullmodname + "." + this.alias.toName());
	}

	@Override
	public PayloadType getAliasName()
	{
		return (PayloadType) this.alias.toName();
	}*/
}
