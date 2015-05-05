package org.scribble2.parser.ast.name;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.name.KindedNameNode;
import org.scribble2.model.name.qualified.MessageSignatureNameNode;
import org.scribble2.model.name.qualified.PayloadTypeNameNode;
import org.scribble2.sesstype.kind.GlobalProtocolKind;
import org.scribble2.sesstype.kind.ModuleKind;

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
	
	//public static ModuleNameNode toModuleNameNode(CommonTree ct)
	public static KindedNameNode<ModuleKind> toModuleNameNode(CommonTree ct)
	{
		//return new ModuleNameNode(getElements(ct));
		//return (ModuleNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(ModelFactory.QUALIFIED_NAME.MODULE, getElements(ct));
		return ModelFactoryImpl.FACTORY.KindedNameNode(ModuleKind.KIND, getElements(ct));
	}

	public static PayloadTypeNameNode toPayloadTypeNameNode(CommonTree ct)
	//public static KindedNameNode<...> toPayloadTypeNameNode(CommonTree ct)
	{
		//return new PayloadTypeNameNode(getElements(ct));
		//return (PayloadTypeNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(ModelFactory.QUALIFIED_NAME.PAYLOADTYPE, getElements(ct));
		//return ModelFactoryImpl.FACTORY.KindedNameNode(..., getElements(ct));
		throw new RuntimeException("TODO: " + ct);
	}

	public static MessageSignatureNameNode toMessageSignatureNameNode(CommonTree ct)
	//public static KindedNameNode<...> toPayloadTypeNameNode(CommonTree ct)
	{
		//return new MessageSignatureNameNode(getElements(ct));
		//return (MessageSignatureNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(ModelFactory.QUALIFIED_NAME.MESSAGESIGNATURE, getElements(ct));
		//return ModelFactoryImpl.FACTORY.KindedNameNode(..., getElements(ct));
		throw new RuntimeException("TODO: " + ct);
	}

	//public static ProtocolNameNode toProtocolNameNode(CommonTree ct)
	public static KindedNameNode<GlobalProtocolKind> toGlobalProtocolNameNode(CommonTree ct)
	{
		//return new ProtocolNameNode(getElements(ct));
		//return (ProtocolNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(ModelFactory.QUALIFIED_NAME.PROTOCOL, getElements(ct));
		return ModelFactoryImpl.FACTORY.KindedNameNode(GlobalProtocolKind.KIND, getElements(ct));
	}
}
