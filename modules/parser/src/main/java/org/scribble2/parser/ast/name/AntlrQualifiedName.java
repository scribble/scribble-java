package org.scribble2.parser.ast.name;

import java.util.Arrays;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.name.qualified.DataTypeNameNode;
import org.scribble2.model.name.qualified.GProtocolNameNode;
import org.scribble2.model.name.qualified.LProtocolNameNode;
import org.scribble2.model.name.qualified.MessageSigNameNode;
import org.scribble2.model.name.qualified.ModuleNameNode;
import org.scribble2.sesstype.kind.Global;
import org.scribble2.sesstype.kind.Local;
import org.scribble2.sesstype.kind.ModuleKind;
import org.scribble2.sesstype.kind.SigKind;

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
		return (ModuleNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(ModuleKind.KIND, getElements(ct));
	}

	public static DataTypeNameNode toDataTypeNameNode(CommonTree ct)
	{
		//return new PayloadTypeNameNode(getElements(ct));
		//return (DataTypeNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(ModelFactory.QUALIFIED_NAME.PAYLOADTYPE, getElements(ct));
		//return (DataTypeNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(ModelFactory.QUALIFIED_NAME.PAYLOADTYPE, getElements(ct));
		throw new RuntimeException("TODO: " + ct);
	}

	public static MessageSigNameNode toMessageSignatureNameNode(CommonTree ct)
	{
		System.out.println("b: " + ct + ", " + Arrays.toString(getElements(ct)));
		
		//return new MessageSignatureNameNode(getElements(ct));
		//return (MessageSignatureNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(ModelFactory.QUALIFIED_NAME.MESSAGESIGNATURE, getElements(ct));
		return (MessageSigNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(SigKind.KIND, getElements(ct));
	}

	/*public static ProtocolNameNode toProtocolNameNode(CommonTree ct)
	{
		//return new ProtocolNameNode(getElements(ct));
		//return (ProtocolNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(ModelFactory.QUALIFIED_NAME.PROTOCOL, getElements(ct));
		return (ProtocolNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(ProtocolKind.KIND, getElements(ct));
	}*/

	public static GProtocolNameNode toGlobalProtocolNameNode(CommonTree ct)
	{
		return (GProtocolNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(Global.KIND, getElements(ct));
	}

	public static LProtocolNameNode toLocalProtocolNameNode(CommonTree ct)
	{
		return (LProtocolNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(Local.KIND, getElements(ct));
	}
}
