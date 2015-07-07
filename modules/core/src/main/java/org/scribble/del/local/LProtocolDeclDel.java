package org.scribble.del.local;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Module;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.local.LProtocolDeclContext;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.ast.local.LProtocolHeader;
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
import org.scribble.visit.ProjectedRoleDeclFixer;

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
	public ScribNode leaveProjectedRoleDeclFixing(ScribNode parent, ScribNode child, ProjectedRoleDeclFixer fixer, ScribNode visited) throws ScribbleException
	{
		LProtocolDecl lpd = (LProtocolDecl) visited;
		// FIXME: ensure all role params are used, to avoid empty roledecllist
		Set<Role> occs = ((LProtocolDeclDel) lpd.del()).getProtocolDeclContext().getRoleOccurrences();
		List<RoleDecl> rds = lpd.header.roledecls.getRoleDecls().stream().filter((rd) -> 
				occs.contains(rd.getDeclName())).collect(Collectors.toList());
		RoleDeclList rdl = AstFactoryImpl.FACTORY.RoleDeclList(rds);
		LProtocolHeader header = lpd.getHeader().reconstruct(lpd.getHeader().getNameNode(), rdl, lpd.header.paramdecls);
		return super.leaveProjectedRoleDeclFixing(parent, child, fixer, lpd.reconstruct(header, lpd.def));
	}

	@Override
	public void enterFsmBuilding(ScribNode parent, ScribNode child, FsmBuilder conv)
	{
		conv.builder.reset();
	}

	@Override
	public ScribNode leaveFsmBuilding(ScribNode parent, ScribNode child, FsmBuilder conv, ScribNode visited)
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
