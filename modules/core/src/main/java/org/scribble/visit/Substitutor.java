package org.scribble.visit;

import java.util.Map;

import org.scribble.ast.NonRoleArgNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Arg;
import org.scribble.sesstype.kind.NonRoleArgKind;
import org.scribble.sesstype.name.Role;

public class Substitutor extends AstVisitor
{
	// name in the current protocoldecl scope -> the original name node in the root protocol decl
	// Applications of the maps (i.e. substitution) should reconstruct/clone the original nodes
	private final Map<Role, RoleNode> rolemap;
	private final Map<Arg<? extends NonRoleArgKind>, NonRoleArgNode> argmap;

	public Substitutor(Job job, Map<Role, RoleNode> rolemap, Map<Arg<? extends NonRoleArgKind>, NonRoleArgNode> argmap)
	{
		super(job);
		this.rolemap = rolemap;
		this.argmap = argmap;
	}
	
	@Override
	public ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		return visited.substituteNames(this);
	}

	public RoleNode getRoleSubstitution(Role role)
	{
		return this.rolemap.get(role).clone();
	}

	public NonRoleArgNode getArgumentSubstitution(Arg<? extends NonRoleArgKind> arg)
	{
		return (NonRoleArgNode) this.argmap.get(arg).clone();  // Makes new dels that will be discarded in NonRoleParamNode (just as calling the factory manually would)
	}
}
