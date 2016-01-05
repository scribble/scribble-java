package org.scribble.visit;

import java.util.HashSet;
import java.util.Set;

import org.scribble.ast.Choice;
import org.scribble.ast.InteractionSeq;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.env.WFChoiceEnv;

// FIXME: refactor as distinct enabling messages checker (cf. GChoiceDel, WFChoicePathChecker)
public class WFChoiceChecker extends UnfoldingVisitor<WFChoiceEnv>
{
	// N.B. using pointer equality for checking if choice previously visited
	// So UnfoldingVisitor cannot visit a clone
	// equals method identity not suitable unless Ast nodes record additional info like syntactic position
	private Set<Choice<?>> visited = new HashSet<>();	
	
	public WFChoiceChecker(Job job)
	{
		super(job);
	}

	@Override
	protected WFChoiceEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new WFChoiceEnv();
	}

	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof Choice<?>)
		{
			return visitOverrideForChoice((InteractionSeq<?>) parent, (Choice<?>) child);
		}
		else
		{
			return super.visit(parent, child);
		}
	}

	private ScribNode visitOverrideForChoice(InteractionSeq<?> parent, Choice<?> child) throws ScribbleException
	{
		if (child instanceof Choice<?>)
		{
			Choice<?> cho = (Choice<?>) child;
			if (!this.visited.contains(cho))
			{
				this.visited.add(cho);
				//ScribNode n = cho.visitChildren(this);
				ScribNode n = super.visit(parent, child);
				this.visited.remove(cho);
				return n;
			}
			else
			{
				return cho;
			}
		}
		else
		{
			return super.visit(parent, child);
		}
	}
	
	@Override
	protected void unfoldingEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.unfoldingEnter(parent, child);
		child.del().enterInlinedWFChoiceCheck(parent, child, this);
	}
	
	@Override
	protected ScribNode unfoldingLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveInlinedWFChoiceCheck(parent, child, this, visited);
		return super.unfoldingLeave(parent, child, visited);
	}
}
