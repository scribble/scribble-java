package org.scribble.ast;

import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

public abstract class Continue<K extends ProtocolKind> extends SimpleInteractionNode<K>
{
	public final RecVarNode recvar;

	protected Continue(RecVarNode recvar)
	{
		this.recvar = recvar;
	}

	public abstract Continue<K> reconstruct(RecVarNode recvar);

	@Override
	public Continue<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RecVarNode recvar = (RecVarNode) visitChild(this.recvar, nv);
		return reconstruct(recvar);
	}

	@Override
	public String toString()
	{
		return Constants.CONTINUE_KW + " " + this.recvar + ";";
	}
}
