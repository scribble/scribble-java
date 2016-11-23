package org.scribble.visit.wf;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.SubprotocolVisitor;
import org.scribble.visit.wf.env.ExplicitCorrelationEnv;

//public class ExplicitCorrelationChecker extends InlinedProtocolVisitor<ExplicitCorrelationEnv>
public class ExplicitCorrelationChecker extends SubprotocolVisitor<ExplicitCorrelationEnv>  // Need to follow /unfolded/ subprotos (inlined is already unfolded by context building when this pass is run)
{
	public ExplicitCorrelationChecker(Job job)
	{
		super(job);
	}
	
	
	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof GProtocolDecl)
		{
			GProtocolDecl gpd = (GProtocolDecl) child;
			if (!gpd.isAuxModifier() && gpd.isExplicitModifier())
			{
				Module mod = (Module) parent;
				GProtocolName gpn = gpd.getFullMemberName(mod);
				for (Role r : gpd.header.roledecls.getRoles())
				{
					Module proj = this.job.getContext().getProjection(gpn, r);
					proj.accept(this);
				}
			}
			return gpd;
		}
		return super.visit(parent, child);
	}

	@Override
	//public void inlinedEnter(ScribNode parent, ScribNode child) throws ScribbleException
	public void subprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		//super.inlinedEnter(parent, child);
		super.subprotocolEnter(parent, child);
		child.del().enterExplicitCorrelationCheck(parent, child, this);
	}
	
	@Override
	//public ScribNode inlinedLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	public ScribNode subprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveExplicitCorrelationCheck(parent, child, this, visited);
		//return super.inlinedLeave(parent, child, visited);
		return super.subprotocolLeave(parent, child, visited);
	}

	@Override
	protected ExplicitCorrelationEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new ExplicitCorrelationEnv();
	}
}
