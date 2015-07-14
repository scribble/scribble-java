package org.scribble.ast.local;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.Recursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.ProjectedChoiceSubjectFixer;


public class LRecursion extends Recursion<Local> implements LCompoundInteractionNode
{
	public LRecursion(RecVarNode recvar, LProtocolBlock block)
	{
		super(recvar, block);
	}

	@Override
	protected LRecursion copy()
	{
		return new LRecursion(this.recvar, getBlock());
	}

	@Override
	public LRecursion reconstruct(RecVarNode recvar, ProtocolBlock<Local> block)
	{
		ScribDel del = del();
		LRecursion lr = new LRecursion(recvar, (LProtocolBlock) block);
		lr = (LRecursion) lr.del(del);
		return lr;
	}
	
	@Override
	public LRecursion clone()
	{
		RecVarNode recvar = this.recvar.clone();
		LProtocolBlock block = getBlock().clone();
		return AstFactoryImpl.FACTORY.LRecursion(recvar, block);
	}
	
	@Override
	public LProtocolBlock getBlock()
	{
		return (LProtocolBlock) this.block;
	}

	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
		return getBlock().getInteractionSeq().getActions().get(0).inferLocalChoiceSubject(fixer);
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Local getKind()
	{
		return LCompoundInteractionNode.super.getKind();
	}
}
