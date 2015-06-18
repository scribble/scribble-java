package org.scribble.ast;

import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.ast.visit.AstVisitor;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;

public abstract class Recursion<K extends ProtocolKind> extends CompoundInteractionNode<K>
{
	public final RecVarNode recvar;
	public final ProtocolBlock<K> block;

	protected Recursion(RecVarNode recvar, ProtocolBlock<K> block)//, CompoundInteractionNodeContext cicontext, Env env)
	{
		this.recvar = recvar;
		this.block = block;
	}

	protected abstract Recursion<K> reconstruct(RecVarNode recvar, ProtocolBlock<K> block);

	@Override
	public Recursion<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RecVarNode recvar = (RecVarNode) visitChild(this.recvar, nv);
		ProtocolBlock<K> block = visitChildWithClassCheck(this, this.block, nv);
		return reconstruct(recvar, block);//, getContext(), getEnv());
	}
	
	@Override
	public String toString()
	{
		return Constants.REC_KW + " " + this.recvar + " " + block;
	}
}
