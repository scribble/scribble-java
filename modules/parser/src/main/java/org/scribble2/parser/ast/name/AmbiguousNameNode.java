package scribble2.ast.name;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.parser.ast.name.qualified.MessageSignatureNameNode;
import org.scribble2.parser.ast.name.simple.ParameterNode;

import scribble2.ast.ArgumentNode;
import scribble2.ast.MessageNode;
import scribble2.main.ScribbleException;
import scribble2.sesstype.Argument;
import scribble2.sesstype.Message;
import scribble2.sesstype.name.Kind;
import scribble2.sesstype.name.Name;
import scribble2.sesstype.name.PayloadTypeOrParameter;
import scribble2.sesstype.name.SimpleName;
import scribble2.visit.NameDisambiguator;

// Primitive payload type or parameter names only: if name is parsed as a CompoundNameNodes, it must be a payload type (not ambiguous in this case)
// No counterpart needed for MessageNode because MessageSignature values can be syntactically distinguished from sig parameters
public class AmbiguousNameNode extends PrimitiveNameNode implements //ArgumentNode
	PayloadElementName, MessageNode
{
	public AmbiguousNameNode(CommonTree ct, String name)
	{
		super(ct, name);
	}
	
	@Override
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
	}

	@Override
	public Name toName()
	{
		return new SimpleName(Kind.AMBIGUOUS, this.identifier);
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
	public boolean isAmbiguousNode()
	{
		return true;
	}
}
