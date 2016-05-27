package org.scribble.visit;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.local.LChoice;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.env.ChoiceUnguardedSubprotocolEnv;

// FIXME: refactor as a choice subject candidate collector (i.e. NameCollector -- thought that is an OffsetSubprotocolCollector, does that make a difference?)
public class ChoiceUnguardedSubprotocolChecker extends SubprotocolVisitor<ChoiceUnguardedSubprotocolEnv>
//public class ChoiceUnguardedSubprotocolChecker extends NoEnvSubprotocolVisitor
{
	//private final LChoice cho;  // Useless: subprotocovisitor visits a role-substituted clone
	
	private boolean shouldPrune = false;

	public ChoiceUnguardedSubprotocolChecker(Job job, ModuleContext mcontext, LChoice cho)
	{
		super(job);
		setModuleContext(mcontext);
	}
	
	// HACK: not fully correct solution
	public void enablePrune()
	{
		this.shouldPrune = true;
	}
	
	public boolean shouldPrune()
	{
		return this.shouldPrune;
	}
	
	@Override
	protected ChoiceUnguardedSubprotocolEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new ChoiceUnguardedSubprotocolEnv();
	}

	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		/*if (child instanceof LChoice)
		{
			System.out.println("222a:\n" + child);
			System.out.println("222b:\n" + this.cho);
			System.out.println("222c:\n" + (child == this.cho));
			
			if (child == this.cho)
			{
				System.out.println("ABC: " + peekEnv().shouldPrune());
				if (peekEnv().shouldPrune())
				{
					this.SHOULD_PRUNE = true;
				}
				return child;
			}
		}*/
		return super.visit(parent, child);
	}

	@Override
	protected void subprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.subprotocolEnter(parent, child);
		child.del().enterChoiceUnguardedSubprotocolCheck(parent, child, this);
	}
	
	@Override
	protected ScribNode subprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveChoiceUnguardedSubprotocolCheck(parent, child, this, visited);
		return super.subprotocolLeave(parent, child, visited);
	}
}
