package org.scribble.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.ModelFactoryImpl;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.parser.ScribbleParser;
import org.scribble.parser.ast.name.AntlrSimpleName;

// FIXME: factor out with AntlrPayloadTypeDecl
public class AntlrMessageSigDecl
{
	public static final int SCHEMA_CHILD_INDEX = 0;
	public static final int EXTNAME_CHILD_INDEX = 1;
	public static final int SOURCE_CHILD_INDEX = 2;
	public static final int ALIAS_CHILD_INDEX = 3;

	public static MessageSigNameDecl parseMessageSigDecl(ScribbleParser parser, CommonTree ct)
	{
		CommonTree tmp1 = getSchemaChild(ct);
		String schema = AntlrSimpleName.getName(tmp1);
		CommonTree tmp2 = getExtNameChild(ct);
		String extName = AntlrExtIdentifier.getName(tmp2);
		CommonTree tmp3 = getSourceChild(ct);
		String source = AntlrExtIdentifier.getName(tmp3);
		//SimpleMessageSignatureNameNode alias = AntlrSimpleName.toSimpleMessageSignatureNameNode(getAliasChild(ct));
		//MessageSigNameNode alias = AntlrQualifiedName.toMessageSignatureNameNode(getAliasChild(ct)); -- no: that expects a compound messigname node with elems
		//MessageSigNameNode alias = (MessageSigNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(SigKind.KIND, getAliasChild(ct).getText());
		MessageSigNameNode alias = AntlrSimpleName.toMessageSigNameNode(getAliasChild(ct));
		//return new MessageSigDecl(schema, extName, source, alias);
		return ModelFactoryImpl.FACTORY.MessageSigDecl(schema, extName, source, alias);
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
