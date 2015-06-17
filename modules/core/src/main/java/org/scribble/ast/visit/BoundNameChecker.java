package org.scribble.ast.visit;

import java.util.HashSet;
import java.util.Set;

import org.scribble.ast.ModelNode;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.Name;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribbleException;

// FIXME: integrate with NameDisambiguator
@Deprecated
public class BoundNameChecker extends ModelVisitor
{
	private Set<Role> roles = new HashSet<>();
	private Set<Name<? extends Kind>> params = new HashSet<>();
	
	public BoundNameChecker(Job job)
	{
		super(job);
	}
	
	@Override
	public void enter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		//child.del().enterBoundNamesCheck(parent, child, this);
	}
	
	@Override
	public ModelNode leave(ModelNode parent, ModelNode child, ModelNode visited) throws ScribbleException
	{
		return null;// visited.del().leaveBoundNamesCheck(parent, child, this, visited);
	}
	
	public void clear()
	{
		this.roles.clear();
		this.params.clear();
	}
	
	public void addRole(Role role)
	{
		this.roles.add(role);
	}

	public void addParameter(Name<? extends Kind> param)
	{
		this.params.add(param);
	}
	
	public boolean isBoundRole(Role role)
	{
		return this.roles.contains(role);
	}

	public boolean isBoundParam(Name<? extends Kind> param)
	{
		return this.params.contains(param);
	}
}
