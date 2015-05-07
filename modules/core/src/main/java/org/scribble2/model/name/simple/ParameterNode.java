package org.scribble2.model.name.simple;

import org.scribble2.model.MessageNode;
import org.scribble2.model.name.PayloadElementNameNode;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.name.Name;
import org.scribble2.sesstype.name.Parameter;
import org.scribble2.sesstype.name.PayloadTypeOrParameter;

//public class ParameterNode extends SimpleNameNode<Parameter> implements PayloadElementNameNode, MessageNode//, ArgumentInstantiation//, PayloadTypeOrParameterNode
public class ParameterNode<K extends Kind> extends SimpleNameNode<Name<K>, K> implements PayloadElementNameNode, MessageNode//, ArgumentInstantiation//, PayloadTypeOrParameterNode
{
	//public final Kind kind;
	
	public ParameterNode(String identifier)//, Kind kind)
	{
		super(identifier);
		//this.kind = kind;
	}

	/*@Override
	protected ParameterNode reconstruct(String identifier)
	{
		ModelDel del = del();  // Default delegate assigned in ModelFactoryImpl for all simple names
		ParameterNode pn = new ParameterNode(identifier);
		pn = (ParameterNode) pn.del(del);
		return pn;
	}*/

	@Override
	protected ParameterNode<K> copy()
	{
		return new ParameterNode<>(this.identifier);
	}
	
	/*// Only useful for MessageSignatureDecls -- FIXME: integrate sig decls properly
	@Override
	public ParameterNode leaveProjection(Projector proj) //throws ScribbleException
	{
		ParameterNode projection = new ParameterNode(null, toName().toString(), this.kind);
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}
	
	@Override
	public ArgumentNode substitute(Substitutor subs)
	{
		return subs.getArgumentSubstitution(toName());
	}*/
	
	@Override
	public Parameter toName()
	{
		return new Parameter(null, this.identifier);
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
		return true;
	}

	@Override
	public PayloadTypeOrParameter toPayloadTypeOrParameter()
	{
		//if (this.kind != Kind.TYPE)
		{
			throw new RuntimeException("Not a type-kind parameter: " + this);
		}
		//return toName();
	}
	
	/*@Override
	public Operator getOperator()
	{
		return new Operator(toString());
	}*/

	@Override
	public Parameter toArgument()
	{
		return toName();
	}

	@Override
	public Parameter toMessage()
	{
		return toName();
	}

	/*@Override
	public boolean isAmbiguousNode()
	{
		return false;
	}*/
}
