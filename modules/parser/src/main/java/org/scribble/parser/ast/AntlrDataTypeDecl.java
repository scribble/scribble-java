package org.scribble.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.DataTypeDecl;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrSimpleName;

public class AntlrDataTypeDecl
{
	public static final int SCHEMA_CHILD_INDEX = 0;
	public static final int EXTNAME_CHILD_INDEX = 1;
	public static final int SOURCE_CHILD_INDEX = 2;
	public static final int ALIAS_CHILD_INDEX = 3;

	public static DataTypeDecl parseDataTypeDecl(ScribParser parser, CommonTree ct)
	{
		CommonTree tmp1 = getSchemaChild(ct);
		String schema = AntlrSimpleName.getName(tmp1);
		CommonTree tmp2 = getExtNameChild(ct);
		String extName = AntlrExtIdentifier.getName(tmp2);
		CommonTree tmp3 = getExtNameChild(ct);
		String source = AntlrExtIdentifier.getName(tmp3);
		DataTypeNode alias = AntlrSimpleName.toDataTypeNameNode(getAliasChild(ct));
		return AstFactoryImpl.FACTORY.DataTypeDecl(schema, extName, source, alias);
	}

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
