package org.scribble.ast.local;

import java.util.Set;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.Recursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Message;
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
		//fixer.pushRec(this.recvar.toName());
		return getBlock().getInteractionSeq().getInteractions().get(0).inferLocalChoiceSubject(fixer);
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Local getKind()
	{
		return LCompoundInteractionNode.super.getKind();
	}

	@Override
	public LInteractionNode merge(LInteractionNode ln) throws ScribbleException
	{
		if (!(ln instanceof LRecursion) || !this.canMerge(ln))
		{
			throw new ScribbleException("Cannot merge " + this.getClass() + " and " + ln.getClass() + ": " + this + ", " + ln);
		}
		LRecursion them = ((LRecursion) ln);
		if (!this.recvar.equals(them.recvar))
		{
			throw new ScribbleException("Cannot merge recursions for " + this.recvar + " and " + them.recvar + ": " + this + ", " + ln);
		}
		return AstFactoryImpl.FACTORY.LRecursion(this.recvar.clone(), getBlock().merge(them.getBlock()));  // Not reconstruct: leave context building to post-projection passes
	}

	@Override
	public boolean canMerge(LInteractionNode ln)
	{
		return ln instanceof LRecursion;
	}

	@Override
	public Set<Message> getEnabling()
	{
		return getBlock().getEnabling();
	}
}
