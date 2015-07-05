package org.scribble.del.local;

import org.scribble.ast.Module;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.LProtocolDeclContext;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.del.ProtocolDeclDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.ScribFsm;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.ContextBuilder;
import org.scribble.visit.FsmBuilder;
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

	/*@Override
	public void enterContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder) throws ScribbleException
	{
		builder.clearProtocolDependencies();  // collect per protocoldecl all together, do not clear?

		Module main = (Module) parent;
		LProtocolDecl lpd = (LProtocolDecl) child;
		LProtocolName lpn = lpd.getFullMemberName(main);
		lpd.header.roledecls.getRoles().stream().forEach(
				(r) -> builder.addLocalProtocolDependency(r, lpn, r));  // Is it needed to add self protocol decl?
	}*/

	@Override
	protected void addSelfDependency(ContextBuilder builder, ProtocolName<?> proto, Role role)
	{
		builder.addLocalProtocolDependency(role, (LProtocolName) proto, role);
	}
	
	@Override
	public LProtocolDecl leaveContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		LProtocolDecl gpd = (LProtocolDecl) visited;
		LProtocolDeclContext gcontext = new LProtocolDeclContext(builder.getLocalProtocolDependencyMap());
		LProtocolDeclDel del = (LProtocolDeclDel) setProtocolDeclContext(gcontext);
		return (LProtocolDecl) gpd.del(del);
	}

	@Override
	public void enterFsmBuilder(ScribNode parent, ScribNode child, FsmBuilder conv)
	{
		conv.builder.reset();
	}

	@Override
	public ScribNode leaveFsmBuilder(ScribNode parent, ScribNode child, FsmBuilder conv, ScribNode visited)
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
