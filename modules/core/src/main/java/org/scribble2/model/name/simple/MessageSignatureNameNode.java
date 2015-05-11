package org.scribble2.model.name.simple;

import org.scribble2.model.MessageNode;
import org.scribble2.sesstype.Argument;
import org.scribble2.sesstype.Message;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.kind.SigKind;
import org.scribble2.sesstype.name.MessageSignatureName;
import org.scribble2.sesstype.name.Scope;

//public class MessageSignatureNameNode extends MemberNameNode implements MessageNode
public class MessageSignatureNameNode extends SimpleNameNode<MessageSignatureName, SigKind> implements MessageNode
{
	/*// FIXME: not syntax
	public final String schema;
	public final String extName;
	public final String source;*/
	
	//public PayloadTypeNameNodes(PrimitiveNameNode... ns)
	//public MessageSignatureNameNodes(CommonTree ct, String... ns, String schema, String extName, String source)
	//public MessageSignatureNameNode(String... ns)
	public MessageSignatureNameNode(String identifier)
	{
		//super(ns);
		super(identifier);
	}

	@Override
	protected MessageSignatureNameNode copy()
	{
		//return new MessageSignatureNameNode(this.elems);
		return new MessageSignatureNameNode(this.identifier);
	}

	/*// Basically a copy without the AST
	@Override
	public MessageSignatureNameNodes leaveProjection(Projector proj) //throws ScribbleException
	{
		//MessageSignatureNameNodes projection = new MessageSignatureNameNodes(null, getElements(), this.schema, this.extName, this.source);
		MessageSignatureNameNodes projection = new MessageSignatureNameNodes(null, getElements());
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}*/
	
	@Override
	public MessageSignatureName toName()
	{
		/*String membname = getLastElement();
		if (!isPrefixed())
		{
			return new MessageSignatureName(membname);
		}
		//ModuleName modname = ModuleNameNodes.toModuleName(getModulePrefix());
		ModuleName modname = getModulePrefix().toName();
		return new MessageSignatureName(modname, membname);*/
		return new MessageSignatureName(Scope.EMPTY_SCOPE, this.identifier);  // HACK?
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
	public Message toMessage(Scope scope)  // Difference between toName and toMessage is scope? does that make sense?
	{
		//return toArgument();
		//return toName();
		return new MessageSignatureName(scope, this.identifier);
	}

	@Override
	public Argument<? extends Kind> toArgument(Scope scope)
	{
		return toMessage(scope);
	}

	/*@Override
	public boolean isAmbiguousNode()
	{
		return false;
	}*/
}
