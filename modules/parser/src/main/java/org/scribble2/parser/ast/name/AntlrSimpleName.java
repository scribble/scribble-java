package org.scribble2.parser.ast.name;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.name.qualified.SimpleProtocolNameNode;
import org.scribble2.model.name.simple.OperatorNode;
import org.scribble2.model.name.simple.ParamNode;
import org.scribble2.model.name.simple.RecVarNode;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.model.name.simple.ScopeNode;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.kind.OperatorKind;
import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.sesstype.kind.RecVarKind;
import org.scribble2.sesstype.kind.RoleKind;

public class AntlrSimpleName
{
	private static final String ANTLR_EMPTY_OPERATOR = "EMPTY_OPERATOR";
	private static final String ANTLR_NO_SCOPE = "NO_SCOPE";
	//private static final String ANTLR_EMPTY_SCOPE = "EMPTY_SCOPENAME";
	
	public static SimpleProtocolNameNode toSimpleProtocolNameNode(CommonTree ct)
	{
		//return new SimpleProtocolNameNode(AntlrSimpleName.getName(ct));
		//return (SimpleProtocolNameNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.PROTOCOL, getName(ct));
		return (SimpleProtocolNameNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ProtocolKind.KIND, getName(ct));
	}

	/*public static SimplePayloadTypeNode toSimplePayloadTypeNode(CommonTree ct)
	{
		//return new SimplePayloadTypeNode(AntlrSimpleName.getName(ct));
		//return (SimplePayloadTypeNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.PAYLOADTYPE, getName(ct));
		throw new RuntimeException("TODO: " + ct);
	}

	public static SimpleMessageSignatureNameNode toSimpleMessageSignatureNameNode(CommonTree ct)
	{
		//return new SimpleMessageSignatureNameNode(AntlrSimpleName.getName(ct));
		//return (SimpleMessageSignatureNameNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.MESSAGESIGNATURE, getName(ct));
		throw new RuntimeException("TODO: " + ct);
	}*/

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
	public static <K extends Kind> ParamNode<K> toParameterNode(K kind, CommonTree ct)
	{
		//return new ParameterNode(getName(ct), kind);
		//return new ParameterNode(getName(ct));
		//return (ParameterNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.PARAMETER, getName(ct));
		//return ModelFactoryImpl.FACTORY.ParameterNode(AmbiguousKind.KIND, getName(ct));
		return ModelFactoryImpl.FACTORY.ParamNode(kind, getName(ct));
	}
	
	/*public static AmbiguousNameNode toAmbiguousNameNode(CommonTree ct)
	{
		//return new AmbiguousNameNode(getName(ct));
		
		System.out.println("4: " + AntlrSimpleName.getName(ct));
		
		return (AmbiguousNameNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.AMBIG, AntlrSimpleName.getName(ct));
	}*/

	public static OperatorNode toOperatorNode(CommonTree ct)
	{
		String op = getName(ct);
		if (op.equals(ANTLR_EMPTY_OPERATOR))
		{
			//return new OperatorNode(OperatorNode.EMPTY_OPERATOR_IDENTIFIER);
			//return (OperatorNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.OPERATOR, OperatorNode.EMPTY_OPERATOR_IDENTIFIER);
			return (OperatorNode) ModelFactoryImpl.FACTORY.SimpleNameNode(OperatorKind.KIND, OperatorNode.EMPTY_OPERATOR_IDENTIFIER);
		}
		//return new OperatorNode(op);
		return (OperatorNode) ModelFactoryImpl.FACTORY.SimpleNameNode(OperatorKind.KIND, getName(ct));
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
	
	public static RecVarNode toRecursionVarNode(CommonTree ct)
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
