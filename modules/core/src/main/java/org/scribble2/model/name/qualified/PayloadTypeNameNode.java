package org.scribble2.model.name.qualified;

import org.scribble2.model.ArgumentNode;
import org.scribble2.model.name.PayloadElementNameNode;
import org.scribble2.sesstype.Argument;
import org.scribble2.sesstype.name.ModuleName;
import org.scribble2.sesstype.name.DataType;
import org.scribble2.sesstype.name.PayloadType;

public class PayloadTypeNameNode extends MemberNameNode implements PayloadElementNameNode, ArgumentNode//, PayloadTypeOrParameterNode
{
	//public PayloadTypeNameNodes(PrimitiveNameNode... ns)
	public PayloadTypeNameNode(String... ns)
	{
		super(ns);
	}

	@Override
	protected PayloadTypeNameNode copy()
	{
		return new PayloadTypeNameNode(this.elems);
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
		String membname = getLastElement();
		if (!isPrefixed())
		{
			return new DataType(membname);
		}
		//ModuleName modname = ModuleNameNodes.toModuleName(getModulePrefix());
		ModuleName modname = getModulePrefix().toName();
		return new DataType(modname, membname);
	}

	@Override
	public PayloadType toPayloadTypeOrParameter()
	{
		return toName();
	}

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
	public Argument toArgument()
	{
		return toName();
	}

	/*@Override
	public PayloadTypeOrParameter toPayloadTypeOrParameter()
	{
		return toName();
	}

	@Override
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
