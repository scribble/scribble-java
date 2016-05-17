package org.scribble.visit;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.main.ScribbleException;

// Duplicated from WFChoiceChecker
// Maybe refactor as PathVisitor (extended by WF checker)
//public class WFChoicePathChecker extends UnfoldingVisitor<WFChoicePathEnv> //PathCollectionVisitor
////public class WFChoicePathChecker extends InlinedProtocolVisitor<Env<?>>  // FIXME: should be unfolding visitor? GlobalModelBuilder should be too (need a correspondence between syntax nodes and model nodes)
public class WFChoicePathChecker extends ModuleContextVisitor
{
	// N.B. using pointer equality for checking if choice previously visited
	// So UnfoldingVisitor cannot visit a clone
	// equals method identity not suitable unless Ast nodes record additional info like syntactic position
	//private Set<Choice<?>> visited = new HashSet<>();	
	
	public WFChoicePathChecker(Job job)
	{
		super(job);
	}

	@Override
	public void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.enter(parent, child);
		child.del().enterWFChoicePathCheck(parent, child, this);
	}
	
	@Override
	public ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveWFChoicePathCheck(parent, child, this, visited);
		return super.leave(parent, child, visited);
	}

	/*@Override
	protected WFChoicePathEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new WFChoicePathEnv();
	}*/

	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		/*if (child instanceof Choice<?>)
		{
			return visitOverrideForChoice((InteractionSeq<?>) parent, (Choice<?>) child);
		}*/
		if (child instanceof ProtocolDecl<?>)
		{
			if (child instanceof GProtocolDecl)
			{
				return visitOverrideForGProtocolDecl((Module) parent, (GProtocolDecl) child);
			}
			else
			{
				return child;
			}
		}
		else
		{
			return super.visit(parent, child);
		}
	}

	private GProtocolDecl visitOverrideForGProtocolDecl(Module parent, GProtocolDecl child) throws ScribbleException
	{
		GProtocolDecl gpd = (GProtocolDecl) child;
		System.out.println("aaa: " + getJobContext().getGlobalModel(gpd.getFullMemberName(parent)));
		return child;
	}
	
	/*private ScribNode visitOverrideForChoice(InteractionSeq<?> parent, Choice<?> child) throws ScribbleException
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
	//protected void pathCollectionEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.unfoldingEnter(parent, child);
		//super.pathCollectionEnter(parent, child);
		child.del().enterWFChoicePathCheck(parent, child, this);
	}
	
	@Override
	protected ScribNode unfoldingLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	//protected ScribNode pathCollectionLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveWFChoicePathCheck(parent, child, this, visited);
		return super.unfoldingLeave(parent, child, visited);
		//return super.pathCollectionLeave(parent, child, visited);
		/* // NOTE: deviates from standard Visitor pattern: calls super first to collect paths -- maybe refactor more "standard way" by collecting paths in a prior pass and save them in context
		visited = super.pathCollectionLeave(parent, child, visited);
		return visited.del().leaveWFChoicePathCheck(parent, child, this, visited);* /
	}*/
}
