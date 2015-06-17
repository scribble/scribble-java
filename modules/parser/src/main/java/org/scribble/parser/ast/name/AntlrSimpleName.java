package org.scribble.parser.ast.name;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.ModelFactoryImpl;
import org.scribble.ast.name.qualified.DataTypeNameNode;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.ast.name.simple.NonRoleParamNode;
import org.scribble.ast.name.simple.OpNode;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ast.name.simple.ScopeNode;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.kind.OpKind;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.kind.SigKind;

public class AntlrSimpleName
{
	private static final String ANTLR_EMPTY_OPERATOR = "EMPTY_OPERATOR";
	private static final String ANTLR_NO_SCOPE = "NO_SCOPE";
	//private static final String ANTLR_EMPTY_SCOPE = "EMPTY_SCOPENAME";
	
	/*public static SimpleProtocolNameNode toSimpleProtocolNameNode(CommonTree ct)
	{
		//return new SimpleProtocolNameNode(AntlrSimpleName.getName(ct));
		//return (SimpleProtocolNameNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.PROTOCOL, getName(ct));
		//return (SimpleProtocolNameNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ProtocolKind.KIND, getName(ct));
		throw new RuntimeException("TODO: " + ct);
	}*/

	public static GProtocolNameNode toGProtocolNameNode(CommonTree ct)
	{
		return (GProtocolNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(Global.KIND, getName(ct));  // Cannot use SimpleNameNode because qualified uses the node's elements, not the node's text itself
		//return AntlrQualifiedName.toGlobalProtocolNameNode(ct);
	}

	public static LProtocolNameNode toLProtocolNameNode(CommonTree ct)
	{
		return (LProtocolNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(Local.KIND, getName(ct));
		//return AntlrQualifiedName.toLocalProtocolNameNode(ct);
	}

	//public static SimplePayloadTypeNode toSimplePayloadTypeNode(CommonTree ct)
	public static DataTypeNameNode toDataTypeNameNode(CommonTree ct)
	{
		//return new SimplePayloadTypeNode(AntlrSimpleName.getName(ct));
		//return (SimplePayloadTypeNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.PAYLOADTYPE, getName(ct));
		return (DataTypeNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(DataTypeKind.KIND, getName(ct));
		//return AntlrQualifiedName.toDataTypeNameNode(ct);
	}

	//public static SimpleMessageSignatureNameNode toSimpleMessageSignatureNameNode(CommonTree ct)
	public static MessageSigNameNode toMessageSigNameNode(CommonTree ct)
	{
		//return new SimpleMessageSignatureNameNode(AntlrSimpleName.getName(ct));
		//return (SimpleMessageSignatureNameNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.MESSAGESIGNATURE, getName(ct));
		return (MessageSigNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(SigKind.KIND, getName(ct));
		//return AntlrQualifiedName.toMessageSigNameNode(ct);  // No: qualified uses the node's elements, not the node's text itself
	}

	public static RoleNode toRoleNode(CommonTree ct)
	{
		//return new RoleNode(getName(ct));
		//return (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.ROLE, getName(ct));
		return (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, getName(ct));
	}

	/*public static ParameterNode toParameterNode(CommonTree ct)
	{
		return toParameterNode(ct, Kind.AMBIGUOUS);
	}*/

	//public static ParameterNode toParameterNode(CommonTree ct, Kind kind)
	public static <K extends Kind> NonRoleParamNode<K> toParamNode(K kind, CommonTree ct)
	{
		//return new ParameterNode(getName(ct), kind);
		//return new ParameterNode(getName(ct));
		//return (ParameterNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.PARAMETER, getName(ct));
		//return ModelFactoryImpl.FACTORY.ParameterNode(AmbiguousKind.KIND, getName(ct));
		return ModelFactoryImpl.FACTORY.NonRoleParamNode(kind, getName(ct));
	}
	
	/*public static AmbiguousNameNode toAmbiguousNameNode(CommonTree ct)
	{
		//return new AmbiguousNameNode(getName(ct));
		
		System.out.println("4: " + AntlrSimpleName.getName(ct));
		
		return (AmbiguousNameNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.AMBIG, AntlrSimpleName.getName(ct));
	}*/

	public static OpNode toOpNode(CommonTree ct)
	{
		String op = getName(ct);
		if (op.equals(ANTLR_EMPTY_OPERATOR))
		{
			//return new OperatorNode(OperatorNode.EMPTY_OPERATOR_IDENTIFIER);
			//return (OperatorNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.OPERATOR, OperatorNode.EMPTY_OPERATOR_IDENTIFIER);
			return (OpNode) ModelFactoryImpl.FACTORY.SimpleNameNode(OpKind.KIND, OpNode.EMPTY_OPERATOR_IDENTIFIER);
		}
		//return new OperatorNode(op);
		return (OpNode) ModelFactoryImpl.FACTORY.SimpleNameNode(OpKind.KIND, getName(ct));
	}
	
	public static ScopeNode toScopeNode(CommonTree ct)
	{
		String scope = getName(ct);
		if (scope.equals(ANTLR_NO_SCOPE))
		{
			return null;
		}
		//return new ScopeNode(scope);
		throw new RuntimeException("TODO: " + ct);
	}
	
	public static RecVarNode toRecVarNode(CommonTree ct)
	{
		//return new RecursionVarNode(getName(ct));
		//return (RecursionVarNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.RECURSIONVAR, getName(ct));
		return (RecVarNode) ModelFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND, getName(ct));
	}

	public static String getName(CommonTree ct)
	{
		return ct.getText();
	}
}
