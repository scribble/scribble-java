package org.scribble.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Continue;
import org.scribble.ast.local.LContinue;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.sesstype.name.Role;

public class GContinue extends Continue<Global> implements GSimpleInteractionNode
{
	public GContinue(CommonTree source, RecVarNode recvar)
	{
		super(source, recvar);
	}

	public LContinue project(Role self)
	{
		RecVarNode recvar = (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(this.recvar.getSource(), RecVarKind.KIND, this.recvar.toName().toString());  // clone?
		LContinue projection = AstFactoryImpl.FACTORY.LContinue(this.source, recvar);
		return projection;
	}

	@Override
	protected GContinue copy()
	{
		return new GContinue(this.source, this.recvar);
	}
	
	@Override
	public GContinue clone()
	{
		RecVarNode rv = this.recvar.clone();
		return AstFactoryImpl.FACTORY.GContinue(this.source, rv);
	}

	@Override
	public GContinue reconstruct(RecVarNode recvar)
	{
		ScribDel del = del();
		GContinue gc = new GContinue(this.source, recvar);
		gc = (GContinue) gc.del(del);
		return gc;
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Global getKind()
	{
		return GSimpleInteractionNode.super.getKind();
	}
}
