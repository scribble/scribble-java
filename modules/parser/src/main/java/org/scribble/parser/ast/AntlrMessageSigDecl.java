package org.scribble.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrSimpleName;

// FIXME: factor out with AntlrDataTypeDecl
public class AntlrMessageSigDecl
{
	public static final int SCHEMA_CHILD_INDEX = 0;
	public static final int EXTNAME_CHILD_INDEX = 1;
	public static final int SOURCE_CHILD_INDEX = 2;
	public static final int ALIAS_CHILD_INDEX = 3;

	public static MessageSigNameDecl parseMessageSigDecl(ScribParser parser, CommonTree ct)
	{
		CommonTree tmp1 = getSchemaChild(ct);
		String schema = AntlrSimpleName.getName(tmp1);
		CommonTree tmp2 = getExtNameChild(ct);
		String extName = AntlrExtIdentifier.getName(tmp2);
		CommonTree tmp3 = getSourceChild(ct);
		String source = AntlrExtIdentifier.getName(tmp3);
		MessageSigNameNode alias = AntlrSimpleName.toMessageSigNameNode(getAliasChild(ct));
		return AstFactoryImpl.FACTORY.MessageSigNameDecl(ct, schema, extName, source, alias);
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
