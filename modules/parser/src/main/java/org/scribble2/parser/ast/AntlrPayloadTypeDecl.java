package org.scribble2.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.PayloadTypeDecl;
import org.scribble2.model.name.simple.SimplePayloadTypeNode;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.ast.name.AntlrSimpleName;

public class AntlrPayloadTypeDecl
{
	public static final int SCHEMA_CHILD_INDEX = 0;
	public static final int EXTNAME_CHILD_INDEX = 1;
	public static final int SOURCE_CHILD_INDEX = 2;
	public static final int ALIAS_CHILD_INDEX = 3;

	public static PayloadTypeDecl parsePayloadTypeDecl(ScribbleParser parser, CommonTree ct)
	{
		CommonTree tmp1 = getSchemaChild(ct);
		String schema = AntlrSimpleName.getName(tmp1);
		CommonTree tmp2 = getExtNameChild(ct);
		String extName = AntlrExtIdentifier.getName(tmp2);
		CommonTree tmp3 = getExtNameChild(ct);
		String source = AntlrExtIdentifier.getName(tmp3);
		SimplePayloadTypeNode alias = AntlrSimpleName.toSimplePayloadTypeNode(getAliasChild(ct));
		return new PayloadTypeDecl(schema, extName, source, alias);
	}

	/*public static PayloadType getFullPayloadTypeName(CommonTree ct)
	{
		CommonTree moddecl = AntlrModule.getModuleDeclChild(AntlrPayloadTypeDecl.getModuleParent(ct));
		ModuleName fullmodname = AntlrQualifiedName.toModuleNameNodes(moddecl).toName();
		SimplePayloadTypeNode alias = AntlrSimpleName.toSimplePayloadTypeNode(getAliasChild(ct));
		return new PayloadType(fullmodname + "." + alias.toName());
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
