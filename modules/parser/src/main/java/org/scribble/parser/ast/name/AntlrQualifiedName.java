package org.scribble.parser.ast.name;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.name.qualified.DataTypeNameNode;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.ast.name.qualified.ModuleNameNode;
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
		//return new ModuleNameNode(getElements(ct));
		//return (ModuleNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(ModelFactory.QUALIFIED_NAME.MODULE, getElements(ct));
		return (ModuleNameNode) AstFactoryImpl.FACTORY.QualifiedNameNode(ModuleKind.KIND, getElements(ct));
	}

	public static DataTypeNameNode toDataTypeNameNode(CommonTree ct)
	{
		//return new PayloadTypeNameNode(getElements(ct));
		//return (DataTypeNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(ModelFactory.QUALIFIED_NAME.PAYLOADTYPE, getElements(ct));
		//return (DataTypeNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(ModelFactory.QUALIFIED_NAME.PAYLOADTYPE, getElements(ct));
		throw new RuntimeException("TODO: " + ct);
	}

	public static MessageSigNameNode toMessageSigNameNode(CommonTree ct)
	{
		//return new MessageSignatureNameNode(getElements(ct));
		//return (MessageSignatureNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(ModelFactory.QUALIFIED_NAME.MESSAGESIGNATURE, getElements(ct));
		return (MessageSigNameNode) AstFactoryImpl.FACTORY.QualifiedNameNode(SigKind.KIND, getElements(ct));
	}

	/*public static ProtocolNameNode toProtocolNameNode(CommonTree ct)
	{
		//return new ProtocolNameNode(getElements(ct));
		//return (ProtocolNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(ModelFactory.QUALIFIED_NAME.PROTOCOL, getElements(ct));
		return (ProtocolNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(ProtocolKind.KIND, getElements(ct));
	}*/

	public static GProtocolNameNode toGProtocolNameNode(CommonTree ct)
	{
		return (GProtocolNameNode) AstFactoryImpl.FACTORY.QualifiedNameNode(Global.KIND, getElements(ct));
	}

	public static LProtocolNameNode toLProtocolNameNode(CommonTree ct)
	{
		return (LProtocolNameNode) AstFactoryImpl.FACTORY.QualifiedNameNode(Local.KIND, getElements(ct));
	}
}
