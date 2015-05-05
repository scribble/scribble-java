package org.scribble2.model.name;

import org.scribble2.model.MessageNode;
import org.scribble2.sesstype.Argument;
import org.scribble2.sesstype.Message;
import org.scribble2.sesstype.kind.AmbiguousKind;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.name.PayloadTypeOrParameter;

public class AmbiguousKindedNameNode extends SimpleKindedNameNode<Kind> implements MessageNode, PayloadElementNameNode
{
	public AmbiguousKindedNameNode(String identifier)
	{
		super(AmbiguousKind.KIND, identifier);
	}

	@Override
	protected AmbiguousKindedNameNode copy()
	{
		return new AmbiguousKindedNameNode(this.identifier);
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
		throw new RuntimeException("Ambiguous name node not disambiguated: " + this);
	}

	@Override
	public PayloadTypeOrParameter toPayloadTypeOrParameter()
	{
		throw new RuntimeException("Ambiguous name node not disambiguated: " + this);
	}

	@Override
	public Message toMessage()
	{
		throw new RuntimeException("Ambiguous name node not disambiguated: " + this);
	}
}
