package org.scribble.parser.ast.name;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.kind.ModuleKind;
import org.scribble.sesstype.kind.SigKind;

public class AntlrQualifiedName
{
	protected static String[] getElements(CommonTree ct)
	{
		int count = ct.getChildCount();
		String[] names = new String[count];
		for (int i = 0; i < count; i++)
		{
			names[i] = AntlrSimpleName.getName((CommonTree) ct.getChild(i));
		}
		return names;
	}
	
	public static ModuleNameNode toModuleNameNode(CommonTree ct)
	{
		return (ModuleNameNode) AstFactoryImpl.FACTORY.QualifiedNameNode(ModuleKind.KIND, getElements(ct));
	}

	public static DataTypeNode toDataTypeNameNode(CommonTree ct)
	{
		return (DataTypeNode) AstFactoryImpl.FACTORY.QualifiedNameNode(DataTypeKind.KIND, getElements(ct));
	}

	public static MessageSigNameNode toMessageSigNameNode(CommonTree ct)
	{
		return (MessageSigNameNode) AstFactoryImpl.FACTORY.QualifiedNameNode(SigKind.KIND, getElements(ct));
	}

	public static GProtocolNameNode toGProtocolNameNode(CommonTree ct)
	{
		return (GProtocolNameNode) AstFactoryImpl.FACTORY.QualifiedNameNode(Global.KIND, getElements(ct));
	}

	public static LProtocolNameNode toLProtocolNameNode(CommonTree ct)
	{
		return (LProtocolNameNode) AstFactoryImpl.FACTORY.QualifiedNameNode(Local.KIND, getElements(ct));
	}
}
