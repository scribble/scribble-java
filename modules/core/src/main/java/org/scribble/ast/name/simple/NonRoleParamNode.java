package org.scribble.ast.name.simple;

import org.scribble.ast.NonRoleArgNode;
import org.scribble.ast.MessageNode;
import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.ast.visit.Substitutor;
import org.scribble.sesstype.Arg;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.SigKind;
import org.scribble.sesstype.name.DataType;
import org.scribble.sesstype.name.MessageSigName;
import org.scribble.sesstype.name.Name;
import org.scribble.sesstype.name.PayloadType;

//public class ParameterNode extends SimpleNameNode<Parameter> implements PayloadElementNameNode, MessageNode//, ArgumentInstantiation//, PayloadTypeOrParameterNode
//public class ParameterNode<K extends Kind> extends SimpleNameNode<Name<K>, K> implements PayloadElementNameNode, MessageNode//, ArgumentInstantiation//, PayloadTypeOrParameterNode
//public class ParamNode<K extends Kind> extends SimpleNameNode<Name<K>, K> implements
public class NonRoleParamNode<K extends Kind> extends SimpleNameNode<K> implements
		//ArgumentNode 
		MessageNode, PayloadElemNameNode
{
	// FIXME: maybe shouldn't be kinded, as just AST node?  or do name/kind disambiguation -- maybe disamb not needed, AmbiguousNameNode --disamb--> kinded Parameter
	
	public final K kind;
	
	//public ParameterNode(String identifier)//, Kind kind)
	public NonRoleParamNode(K kind, String identifier)//, Kind kind)
	{
		super(identifier);
		this.kind = kind;
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
	protected NonRoleParamNode<K> copy()
	{
		//return new ParameterNode<>(this.identifier);
		//return new ParamNode<>(this.kind, this.identifier);
		return new NonRoleParamNode<>(this.kind, getIdentifier());
	}
	
	/*// Only useful for MessageSignatureDecls -- FIXME: integrate sig decls properly
	@Override
	public ParameterNode leaveProjection(Projector proj) //throws ScribbleException
	{
		ParameterNode projection = new ParameterNode(null, toName().toString(), this.kind);
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}*/
	
	/*@Override
	public ArgumentNode substitute(Substitutor subs)
	{
		return subs.getArgumentSubstitution(toName());
	}*/

	@Override
	public NonRoleArgNode substituteNames(Substitutor subs)
	{
		Arg<K> arg = toArg();
		NonRoleArgNode an;
		if (this.kind.equals(SigKind.KIND))
		{
			an = subs.getArgumentSubstitution(arg);  // FIXME: reconstruct/clone?
		}
		else if (this.kind.equals(DataTypeKind.KIND))
		{
			an = subs.getArgumentSubstitution(arg);  // FIXME: reconstruct/clone?)
		}
		else
		{
			throw new RuntimeException("TODO: " + this);
		}
		an = (NonRoleArgNode) an.del(del());
		return an;
	}
	
	@Override
	//public Parameter toName()
	public Name<K> toName()
	{
		//return new Parameter(null, this.identifier);
		String id = getIdentifier();
		if (this.kind.equals(SigKind.KIND))
		{
			return Kind.castName(this.kind, new MessageSigName(id));
		}
		else if (this.kind.equals(DataTypeKind.KIND))
		{
			return Kind.castName(this.kind, new DataType(id));
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + this.kind);
		}
	}

	@Override
	public boolean isMessageSigNode()
	{
		return false;
	}

	@Override
	public boolean isMessageSigNameNode()
	{
		return false;
	}

	@Override
	public boolean isDataTypeNameNode()
	{
		return false;
	}

	@Override
	public boolean isParamNode()
	{
		return true;
	}

	/*//@Override
	public PayloadType toPayloadTypeOrParameter()
	{
		//if (this.kind != Kind.TYPE)
		{
			throw new RuntimeException("Not a type-kind parameter: " + this);
		}
		//return toName();
	}*/
	
	/*@Override
	public Operator getOperator()
	{
		return new Operator(toString());
	}*/

	@Override
	//public Parameter toArgument()
	//public Name<K> toArgument()
	public Arg<K> toArg()
	{
		//return toName();
		Arg<? extends Kind> arg;
		if (this.kind.equals(DataTypeKind.KIND))
		{
			arg = toPayloadType();
		}
		else if (this.kind.equals(SigKind.KIND))
		{
			 arg = toMessage();
		}
		else
		{
			throw new RuntimeException("Shouldn't get here: " + this);
		}
		return (Arg<K>) arg;
	}

	//@Override
	//public Parameter toMessage()
	//public Name<K> toMessage()
	public Message toMessage()
	{
		if (!this.kind.equals(SigKind.KIND))
		{
			throw new RuntimeException("Shouldn't get in here: " + this);
		}
		return (Message) toName();
	}

	@Override
	public PayloadType<? extends Kind> toPayloadType()
	{
		if (!this.kind.equals(DataTypeKind.KIND))
		{
			throw new RuntimeException("Shouldn't get in here: " + this);
		}
		return (DataType) toName();
	}

	/*@Override
	public Argument<? extends Kind> toArgument(Scope scope)
	{
		// TODO Auto-generated method stub
		return null;
	}*/

	/*@Override
	public boolean isAmbiguousNode()
	{
		return false;
	}*/
}
