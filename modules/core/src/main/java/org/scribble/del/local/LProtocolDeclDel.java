package org.scribble.del.local;

import org.scribble.ast.Module;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.local.LProtocolDeclContext;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.del.ProtocolDeclDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.ScribFsm;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.ContextBuilder;
import org.scribble.visit.FsmGenerator;
import org.scribble.visit.JobContext;

public class LProtocolDeclDel extends ProtocolDeclDel<Local>
{
	public LProtocolDeclDel()
	{

	}
	
	@Override
	protected LProtocolDeclDel copy()
	{
		return new LProtocolDeclDel();
	}

	@Override
	protected void addSelfDependency(ContextBuilder builder, ProtocolName<?> proto, Role role)
	{
		builder.addLocalProtocolDependency(role, (LProtocolName) proto, role);
	}
	
	@Override
	public LProtocolDecl leaveContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		LProtocolDecl lpd = (LProtocolDecl) visited;
		LProtocolDeclContext lcontext = new LProtocolDeclContext(builder.getLocalProtocolDependencyMap());
		LProtocolDeclDel del = (LProtocolDeclDel) setProtocolDeclContext(lcontext);
		return (LProtocolDecl) lpd.del(del);
	}

	@Override
	public void enterFsmGeneration(ScribNode parent, ScribNode child, FsmGenerator conv)
	{
		conv.builder.reset();
	}

	@Override
	public ScribNode leaveFsmGeneration(ScribNode parent, ScribNode child, FsmGenerator conv, ScribNode visited)
	{
		LProtocolDecl lpd = (LProtocolDecl) visited;
		ScribFsm fsm = new ScribFsm(conv.builder.getEntry(), conv.builder.getExit());
		JobContext jc = conv.getJobContext();
		jc.addFsm(lpd.getFullMemberName((Module) parent), fsm);
		return visited;
	}
	
	@Override
	public LProtocolDeclContext getProtocolDeclContext()
	{
		return (LProtocolDeclContext) super.getProtocolDeclContext();
	}
}
