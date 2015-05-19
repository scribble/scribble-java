package org.scribble2.model.name.simple;

import org.scribble2.model.MessageNode;
import org.scribble2.model.name.PayloadElementNameNode;
import org.scribble2.sesstype.Arg;
import org.scribble2.sesstype.Message;
import org.scribble2.sesstype.kind.AmbigKind;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.name.AmbigName;
import org.scribble2.sesstype.name.PayloadType;

// Primitive payload type or parameter names only: if name is parsed as a CompoundNameNodes, it must be a payload type (not ambiguous in this case)
// No counterpart needed for MessageNode because MessageSignature values can be syntactically distinguished from sig parameters
//public class AmbiguousNameNode extends SimpleNameNode implements //ArgumentNode
//public class AmbigNameNode extends SimpleNameNode<Name<AmbigKind>, AmbigKind> implements
public class AmbigNameNode extends SimpleNameNode<AmbigKind> implements
	PayloadElementNameNode, MessageNode
	//ArgumentNode<AmbiguousKind>
{
	public AmbigNameNode(String identifier)
	{
		super(identifier);
	}

	/*@Override
	protected SimpleNameNode reconstruct(String identifier)
	{
		throw new RuntimeException("Shouldn't get in here.");
	}*/

	@Override
	protected AmbigNameNode copy()
	{
		//return new AmbiguousNameNode(this.identifier);
		//return new AmbigNameNode(this.identifier);
		return new AmbigNameNode(getIdentifier());
	}
	
	/*@Override
	public ArgumentNode leaveDisambiguation(NameDisambiguator disamb) throws ScribbleException
	{
		Name name = toName();
		if (disamb.isVisiblePayloadType(name))  // By well-formedness (checked later), payload type and parameter names are distinct
		{
			return new PayloadTypeNameNodes(this.ct, name.toString());
		}
		else if (disamb.isVisibleMessageSignatureName(name))
		{
			return new MessageSignatureNameNode(this.ct, name.toString());
		}
		else if (disamb.isBoundParameter(name))
		{
			return new ParameterNode(this.ct, name.toString(), disamb.getParameterKind(name));
		}
		throw new ScribbleException("Cannot disambiguate name: " + name);
	}*/

	@Override
	//public Argument<? extends Kind> toArgument(Scope scope)
	public Arg<? extends Kind> toArg()
	//public Argument<AmbiguousKind> toArgument()
	{
		throw new RuntimeException("Ambiguous name node not disambiguated: " + this);
	}

	@Override
	//public Message toMessage(Scope scope)
	public Message toMessage()
	{
		throw new RuntimeException("Ambiguous name node not disambiguated: " + this);
	}

	@Override
	public PayloadType<? extends Kind> toPayloadType()
	{
		//throw new RuntimeException("Shouldn't get in here: " + this);
		throw new RuntimeException("Ambiguous name node not disambiguated: " + this);
	}

	@Override
	//public IName toName()
	public AmbigName toName()
	{
		//return new SimpleName(KindEnum.AMBIGUOUS, this.identifier);
		//return new AmbigName(this.identifier);
		return new AmbigName(getIdentifier());
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
		return false;
	}
}
