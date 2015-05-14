package org.scribble2.model.name.qualified;

import org.scribble2.model.MessageNode;
import org.scribble2.sesstype.kind.SigKind;
import org.scribble2.sesstype.name.MessageSigName;
import org.scribble2.sesstype.name.ModuleName;

//public class MessageSignatureNameNode extends MemberNameNode implements MessageNode
//public class MessageSignatureNameNode extends SimpleNameNode<MessageSignatureName, SigKind> implements MessageNode
public class MessageSigNameNode extends MemberNameNode<MessageSigName, SigKind> implements MessageNode
{
	/*// FIXME: not syntax
	public final String schema;
	public final String extName;
	public final String source;*/
	
	//public PayloadTypeNameNodes(PrimitiveNameNode... ns)
	//public MessageSignatureNameNodes(CommonTree ct, String... ns, String schema, String extName, String source)
	public MessageSigNameNode(String... elems)
	//public MessageSignatureNameNode(String identifier)
	{
		super(elems);
		//super(identifier);
	}

	@Override
	protected MessageSigNameNode copy()
	{
		return new MessageSigNameNode(this.elems);
		//return new MessageSignatureNameNode(this.identifier);
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

	/*@Override
	public MessageNode substituteNames(Substitutor subs)
	{
		//return reconstruct(subs.getRoleSubstitution(toName()).toString());
		
		System.out.println("b: " + this + ", " + subs.getArgumentSubstitution(toArgument()));
		
		return (MessageNode) subs.getArgumentSubstitution(toArgument());  // FIXME: reconstruct/clone?
	}*/
	
	@Override
	public MessageSigName toName()
	{
		//String membname = getLastElement();
		MessageSigName membname = new MessageSigName(getLastElement());
		if (!isPrefixed())
		{
			//return new MessageSignatureName(membname);
			return membname;
		}
		//ModuleName modname = ModuleNameNodes.toModuleName(getModulePrefix());
		ModuleName modname = getModuleNamePrefix();
		return new MessageSigName(modname, membname);
		//return new MessageSignatureName(Scope.EMPTY_SCOPE, this.identifier);  // HACK?
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
	public boolean isParameterNode()
	{
		return false;
	}

	@Override
	//public Message toMessage(Scope scope)  // Difference between toName and toMessage is scope? does that make sense?
	public MessageSigName toMessage()  // Difference between toName and toMessage is scope? does that make sense?
	{
		//return toArgument();
		return toName();
		//return new MessageSignatureName(scope, this.identifier);
	}

	@Override
	//public Argument<? extends Kind> toArgument(Scope scope)
	public MessageSigName toArgument()
	{
		//return toMessage(scope);
		return toMessage();
	}

	/*@Override
	public boolean isAmbiguousNode()
	{
		return false;
	}*/
}
