package org.scribble.ext.go.parser.scribble.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.ext.go.core.ast.RPCoreDelegDecl;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.parser.scribble.ast.AntlrDataTypeDecl;
import org.scribble.parser.scribble.ast.AntlrExtIdentifier;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;

// FIXME: should be RPDelegDecl (not Core, a "full" AST (Antlr) node)
// Duplicated from AntlrDataTypeDecl
public class RPCoreAntlrDelegDecl
{
	public static final int SCHEMA_CHILD_INDEX = 0;
	public static final int EXTNAME_CHILD_INDEX = 1;
	public static final int SOURCE_CHILD_INDEX = 2;
	public static final int ALIAS_CHILD_INDEX = 3;

	public static RPCoreDelegDecl parseDelegDecl(AntlrToScribParser parser, CommonTree ct, AstFactory af)
	{
		CommonTree tmp1 = AntlrDataTypeDecl.getSchemaChild(ct);
		String schema = AntlrSimpleName.getName(tmp1);
		CommonTree tmp2 = AntlrDataTypeDecl.getExtNameChild(ct);
		String extName = AntlrExtIdentifier.getName(tmp2);
		CommonTree tmp3 = AntlrDataTypeDecl.getSourceChild(ct);
		String source = AntlrExtIdentifier.getName(tmp3);
		DataTypeNode alias = AntlrSimpleName.toDataTypeNameNode(AntlrDataTypeDecl.getAliasChild(ct), af);  // FIXME: EMTPY_ALIAS?
		//return ((RPAstFactory) af).ParamCoreDelegDecl(ct, schema, extName, source, alias);
		throw new RuntimeException("Shouldn't get here: " + ct);
	}
}
