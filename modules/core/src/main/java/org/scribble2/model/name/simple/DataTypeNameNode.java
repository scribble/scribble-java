package org.scribble2.model.name.simple;

import org.scribble2.model.ArgumentNode;
import org.scribble2.model.name.PayloadElementNameNode;
import org.scribble2.sesstype.Argument;
import org.scribble2.sesstype.kind.DataTypeKind;
import org.scribble2.sesstype.name.DataType;
import org.scribble2.sesstype.name.Scope;

//public class PayloadTypeNameNode extends MemberNameNode implements PayloadElementNameNode, ArgumentNode//, PayloadTypeOrParameterNode
//public class PayloadTypeNameNode extends MemberNameNode implements PayloadElementNameNode, ArgumentNode
public class DataTypeNameNode extends SimpleNameNode<DataType, DataTypeKind> implements PayloadElementNameNode, ArgumentNode
{
	..HERE make qualified again (and MessageSigNames)

	//public PayloadTypeNameNodes(PrimitiveNameNode... ns)
	//public PayloadTypeNameNode(String... ns)
	public DataTypeNameNode(String identifier)
	{
		//super(ns);
		super(identifier);
	}

	@Override
	protected DataTypeNameNode copy()
	{
		//return new PayloadTypeNameNode(this.elems);
		return new DataTypeNameNode(this.identifier);
	}

	/*// Basically a copy without the AST
	@Override
	public PayloadTypeNameNode leaveProjection(Projector proj) //throws ScribbleException
	{
		PayloadTypeNameNode projection = new PayloadTypeNameNode(null, getElements());
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}*/
	
	@Override
	public DataType toName()
	{
		/*String membname = getLastElement();
		if (!isPrefixed())
		{
			return new DataType(membname);
		}
		//ModuleName modname = ModuleNameNodes.toModuleName(getModulePrefix());
		ModuleName modname = getModulePrefix().toName();
		return new DataType(modname, membname);*/
		return new DataType(this.identifier);
	}

	/*@Override
	public PayloadType toPayloadTypeOrParameter()
	{
		return toName();
	}*/

	@Override
	public boolean isMessageSignatureNode()
	{
		return false;
	}

	@Override
	public boolean isPayloadTypeNode()
	{
		return false;
	}

	@Override
	public boolean isParameterNode()
	{
		return false;
	}

	@Override
	public Argument<DataTypeKind> toArgument(Scope scope)  // FIXME: shouldn't be scoped
	{
		//return toName();
		return null;
	}

	@Override
	//public PayloadTypeOrParameter toPayloadTypeOrParameter()
	public DataType toPayloadType()
	{
		return toName();
	}

	/*@Override
	public PayloadType toArgument()
	{
		return toName();
	}

	public boolean isMessageSignatureNode()
	{
		return false;
	}

	@Override
	public boolean isPayloadTypeNode()
	{
		return true;
	}

	@Override 
	public boolean isParameterNode()
	{
		return false;
	}

	@Override
	public boolean isAmbiguousNode()
	{
		return false;
	}*/
}
