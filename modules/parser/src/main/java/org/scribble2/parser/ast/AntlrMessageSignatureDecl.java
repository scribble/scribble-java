package org.scribble2.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.MessageSignatureDecl;
import org.scribble2.model.name.simple.SimpleMessageSignatureNameNode;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.ast.name.AntlrSimpleName;

// FIXME: factor out with AntlrPayloadTypeDecl
public class AntlrMessageSignatureDecl
{
	public static final int SCHEMA_CHILD_INDEX = 0;
	public static final int EXTNAME_CHILD_INDEX = 1;
	public static final int SOURCE_CHILD_INDEX = 2;
	public static final int ALIAS_CHILD_INDEX = 3;

	public static MessageSignatureDecl parseMessageSignatureDecl(ScribbleParser parser, CommonTree ct)
	{
		CommonTree tmp1 = getSchemaChild(ct);
		String schema = AntlrSimpleName.getName(tmp1);
		CommonTree tmp2 = getExtNameChild(ct);
		String extName = AntlrExtIdentifier.getName(tmp2);
		CommonTree tmp3 = getExtNameChild(ct);
		String source = AntlrExtIdentifier.getName(tmp3);
		SimpleMessageSignatureNameNode alias = AntlrSimpleName.toSimpleMessageSignatureNameNode(getAliasChild(ct));
		return new MessageSignatureDecl(schema, extName, source, alias);
	}

	/*public static MessageSignature getFullMessageSignatureName(CommonTree ct)
	{
		CommonTree moddecl = AntlrModule.getModuleDeclChild(AntlrMessageSignatureDecl.getModuleParent(ct));
		ModuleName fullmodname = AntlrQualifiedName.toModuleNameNodes(moddecl).toName();
		SimpleMessageSignatureNode alias = AntlrSimpleName.toSimpleMessageSignatureNode(getAliasChild(ct));
		return new MessageSignature(fullmodname + "." + alias.toName());
	}*/

	public static CommonTree getSchemaChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(SCHEMA_CHILD_INDEX);
	}

	public static CommonTree getExtNameChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(EXTNAME_CHILD_INDEX);
	}

	public static CommonTree getSourceChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(SOURCE_CHILD_INDEX);
	}

	public static CommonTree getAliasChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(ALIAS_CHILD_INDEX);
	}
	
	public static CommonTree getModuleParent(CommonTree ct)
	{
		return (CommonTree) ct.getParent();
	}
}
