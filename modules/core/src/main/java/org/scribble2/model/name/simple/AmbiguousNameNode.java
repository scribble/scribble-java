package org.scribble2.model.name.simple;

import org.scribble2.model.MessageNode;
import org.scribble2.model.name.PayloadElementNameNode;
import org.scribble2.sesstype.name.Kind;
import org.scribble2.sesstype.name.Name;
import org.scribble2.sesstype.name.SimpleName;

// Primitive payload type or parameter names only: if name is parsed as a CompoundNameNodes, it must be a payload type (not ambiguous in this case)
// No counterpart needed for MessageNode because MessageSignature values can be syntactically distinguished from sig parameters
public class AmbiguousNameNode extends SimpleNameNode implements //ArgumentNode
	PayloadElementNameNode, MessageNode
{
	public AmbiguousNameNode(String identifier)
	{
		super(identifier);
	}

	@Override
	protected AmbiguousNameNode copy()
	{
		return new AmbiguousNameNode(this.identifier);
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
	}

	@Override
	public Argument toArgument()
	{
		throw new RuntimeException("Ambiguous name node not disambiguated: " + this);
	}

	@Override
	public Message toMessage()
	{
		throw new RuntimeException("Ambiguous name node not disambiguated: " + this);
	}

	@Override
	public PayloadTypeOrParameter toPayloadTypeOrParameter()
	{
		//throw new RuntimeException("Shouldn't get in here: " + this);
		throw new RuntimeException("Ambiguous name node not disambiguated: " + this);
	}*/

	@Override
	public Name toName()
	{
		return new SimpleName(Kind.AMBIGUOUS, this.identifier);
	}

	/*@Override
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
	public boolean isAmbiguousNode()
	{
		return true;
	}*/
}
