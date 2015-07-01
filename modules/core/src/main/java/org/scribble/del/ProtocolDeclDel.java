package org.scribble.del;

import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ProtocolDeclContext;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.SubprotocolSig;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.NameDisambiguator;
import org.scribble.visit.ProtocolDefInliner;

public abstract class ProtocolDeclDel<K extends ProtocolKind> extends ScribDelBase
{
	private ProtocolDeclContext<K> pdcontext;

	protected ProtocolDeclDel()
	{

	}
	
	protected abstract ProtocolDeclDel<K> copy();
	
	@Override
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		disamb.clear();
		return visited;
	}

	@Override
	public void enterProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder) throws ScribbleException
	{
		SubprotocolSig subsig = builder.peekStack();  // SubprotocolVisitor has already entered subprotocol
		builder.setRecVar(subsig);
	}

	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
	{
		return visited;
	}
	
	protected ProtocolDeclDel<K> setProtocolDeclContext(ProtocolDeclContext<K> pdcontext)
	{
		ProtocolDeclDel<K> copy = copy();  // FIXME: should be a deep clone in principle -- but if any other children are immutable, they can be shared
		copy.pdcontext = pdcontext;
		return copy;
	}
	
	public ProtocolDeclContext<K> getProtocolDeclContext()
	{
		return this.pdcontext;
	}
}
