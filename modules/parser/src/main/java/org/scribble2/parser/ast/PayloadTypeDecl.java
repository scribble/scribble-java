package org.scribble2.parser.ast;

import org.antlr.runtime.Token;
import org.scribble2.parser.AntlrConstants;
import org.scribble2.parser.ast.name.simple.SimplePayloadTypeNode;

public class PayloadTypeDecl extends DataTypeDecl //AbstractNode implements ModuleMember //implements NameDeclaration
{
	public PayloadTypeDecl(Token t, String schema, String extName, String source, SimplePayloadTypeNode alias)
	{
		super(t, schema, extName, source, alias);
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

	@Override
	public String toString()
	{
		return AntlrConstants.TYPE_KW + " <" + this.schema + "> " + this.extName
				+ " " + AntlrConstants.FROM_KW + " " + this.source + " "
				+ AntlrConstants.AS_KW + " " + this.alias + ";";
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
