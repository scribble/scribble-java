package org.scribble.lang.global;

import java.util.Map;
import java.util.Set;

import org.scribble.job.ScribbleException;
import org.scribble.lang.DisconnectAction;
import org.scribble.lang.Projector;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.lang.local.LDisconnect;
import org.scribble.lang.local.LSkip;
import org.scribble.lang.local.LType;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;

public class GDisconnect extends DisconnectAction<Global>
		implements GType
{

	public GDisconnect(org.scribble.ast.DisconnectAction<Global> source,
			Role left, Role right)
	{
		super(source, left, right);
	}

	@Override
	public GDisconnect reconstruct(
			org.scribble.ast.DisconnectAction<Global> source, Role left, Role right)
	{
		return new GDisconnect(source, left, right);
	}

	@Override
	public GDisconnect substitute(Substitutions subs)
	{
		return (GDisconnect) super.substitute(subs);
	}

	@Override
	public GDisconnect getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		return (GDisconnect) super.getInlined(i);
	}

	@Override
	public GDisconnect unfoldAllOnce(STypeUnfolder<Global> u)
	{
		return this;
	}
	
	@Override
	public LType projectInlined(Role self)
	{
		if (this.left.equals(self))
		{
			/*if (this.dst.equals(self))
			{
				// CHECKME: already checked?
			}*/
			return new LDisconnect(null, this.right);
		}
		else if (this.right.equals(self))
		{
			return new LDisconnect(null, this.left);
		}
		else
		{
			return LSkip.SKIP;
		}
	}

	@Override
	public LType project(Projector v)
	{
		return projectInlined(v.self);  // No need for "aux", no recursive call
	}

	@Override
	public Set<Role> checkRoleEnabling(Set<Role> enabled) throws ScribbleException
	{
		if (!enabled.contains(this.left))
		{
			throw new ScribbleException("Role not enabled: " + this.left);
		}
		if (!enabled.contains(this.right))
		{
			throw new ScribbleException("Role not enabled: " + this.right);
		}
		return enabled;
	}

	@Override
	public Map<Role, Role> checkExtChoiceConsistency(Map<Role, Role> enablers)
			throws ScribbleException
	{
		return enablers;
	}

	@Override
	public int hashCode()
	{
		int hash = 8747;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof GDisconnect))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GDisconnect;
	}
}
