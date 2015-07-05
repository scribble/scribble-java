package org.scribble.visit;

import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.ast.NonRoleArgNode;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Arg;
import org.scribble.sesstype.kind.NonRoleArgKind;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.env.Env;


public abstract class OffsetSubprotocolVisitor<T extends Env> extends SubprotocolVisitor<T>
{
	public OffsetSubprotocolVisitor(Job job)
	{
		super(job);
	}
	
	// Doesn't push a subprotocol signature; only records the roles/args -- why? because sigs are based on vals (from the first do), not the root proto params? -- but it would be fine to use the params?
	@Override
	protected void enterRootProtocolDecl(ProtocolDecl<? extends ProtocolKind> pd)
	{
		Map<Role, RoleNode> rolemap =
				pd.header.roledecls.getRoleDecls().stream()
						.collect(Collectors.toMap((r) -> r.getDeclName(), (r) -> (RoleNode) r.name));
		Map<Arg<? extends NonRoleArgKind>, NonRoleArgNode> argmap =
				pd.header.paramdecls.getParamDecls().stream()
						.collect(Collectors.toMap((p) -> (Arg<?>) p.getDeclName(), (p) -> (NonRoleArgNode) p.name));
		this.rolemaps.push(rolemap);
		this.argmaps.push(argmap);
	}
	
	@Override
	protected final ScribNode visitForSubprotocols(ScribNode parent, ScribNode child) throws ScribbleException
	{
		return visitForOffsetSubprotocols(parent, child);
	}
	
	protected ScribNode visitForOffsetSubprotocols(ScribNode parent, ScribNode child) throws ScribbleException
	{
		return super.visitForSubprotocols(parent, child);
	}

	@Override
	protected final void envLeaveProtocolDeclOverride(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		this.rolemaps.pop();
		this.argmaps.pop();
	}

	@Override
	protected final void subprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.subprotocolEnter(parent, child);
		offsetSubprotocolEnter(parent, child);
	}

	@Override
	protected final ScribNode subprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		ScribNode n = offsetSubprotocolLeave(parent, child, visited);
		return super.subprotocolLeave(parent, child, n);
	}

	protected void offsetSubprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{

	}

	protected ScribNode offsetSubprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		return visited;
	}
}
