package org.scribble2.model;

import org.scribble2.model.name.qualified.MemberNameNode;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.name.MemberName;

// FIXME: rename to something better
// FIXME: should be a NameDeclNode?
public abstract class NonProtocolDecl<K extends Kind> extends ModelNodeBase //implements ModuleMember //implements NameDeclaration
{
	public final String schema;
	public final String extName;
	public final String source;
	//public final SimpleMemberNameNode alias;
	//public final SimpleNameNode<? extends Name<K>, K> alias;  // Fix MessageSignatureDecl to make this back to member name node?
	public final MemberNameNode<? extends MemberName<K>, K> alias;  // Fix MessageSignatureDecl to make this back to member name node?

	//public NonProtocolDecl(String schema, String extName, String source, MemberNameNode<? extends MemberName<? extends Kind>, ? extends Kind> alias)
	public NonProtocolDecl(String schema, String extName, String source, MemberNameNode<? extends MemberName<K>, K> alias)
	//public NonProtocolDecl(String schema, String extName, String source, SimpleNameNode<? extends Name<K>, K> alias)
	{
		this.schema = schema;
		this.extName = extName;
		this.source = source;
		this.alias = alias;
	}

	/*@Override
	public PayloadTypeDecl disambiguate(PayloadTypeOrParameterDisambiguator disamb) throws ScribbleException
	{
		PayloadTypeDecl visited = (PayloadTypeDecl) super.disambiguate(disamb);
		disamb.addPayloadType(visited);
		return visited;
	}*/
	
	/*protected abstract DataTypeDecl reconstruct(CommonTree ct, String schema, String extName, String source, PrimitiveNameNode alias);

	@Override
	public DataTypeDecl visitChildren(NodeVisitor nv) throws ScribbleException
	{
		//SimplePayloadTypeNode alias = (SimplePayloadTypeNode) visitChild(this.alias, nv);
		//return new DataTypeDecl(this.ct, this.schema, this.extName, this.source, alias);
		PrimitiveNameNode alias = (PrimitiveNameNode) visitChild(this.alias, nv);
		return reconstruct(ct, schema, extName, source, alias);
	}
	
	public Name getAliasName()
	{
		return this.alias.toName();
	}*/

	/*@Override
	public String toString()
	{
		return AntlrConstants.TYPE_KW + " <" + this.schema + "> " + this.extName
				+ " " + AntlrConstants.FROM_KW + " " + this.source + " "
				+ AntlrConstants.AS_KW + " " + this.alias + ";";
	}

	public PayloadType getFullPayloadTypeName()
	{
		ModuleName fullmodname = AntlrModule.getFullModuleName(AntlrPayloadTypeDecl.getModuleParent(this.ct));
		return new PayloadType(fullmodname + "." + this.alias.toName());
	}*/
}
