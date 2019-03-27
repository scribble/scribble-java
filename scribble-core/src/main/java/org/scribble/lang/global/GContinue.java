package org.scribble.lang.global;

import java.util.Map;
import java.util.Set;

import org.scribble.ast.ProtocolKindNode;
import org.scribble.job.ScribbleException;
import org.scribble.lang.Continue;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.lang.local.LContinue;
import org.scribble.type.kind.Global;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

public class GContinue extends Continue<Global> implements GType
{
	public GContinue(//org.scribble.ast.Continue<Global> source, 
			ProtocolKindNode<Global> source,  // Due to inlining, do -> continue
			RecVar recvar)
	{
		super(source, recvar);
	}
	
	@Override
	public GContinue reconstruct(org.scribble.ast.ProtocolKindNode<Global> source,
			RecVar recvar)
	{
		return new GContinue(source, recvar);
	}

	@Override
	public GContinue substitute(Substitutions subs)
	{
		return (GContinue) super.substitute(subs);
	}

	@Override
	public GContinue getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		return (GContinue) super.getInlined(i);
	} 

	@Override
	public GRecursion unfoldAllOnce(STypeUnfolder<Global> u)
	{
		return new GRecursion(getSource(), this.recvar,
				(GSeq) u.getRec(this.recvar));
				// CHECKME: Continue (not Recursion) as the source of the unfolding ?
	}

	@Override
	public LContinue project(Role self)
	{
		return new LContinue(null, this.recvar);
	}

	@Override
	public Set<Role> checkRoleEnabling(Set<Role> enabled) throws ScribbleException
	{
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
		int hash = 3457;
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
		if (!(o instanceof GContinue))
		{
			return false;
		}
		return super.equals(o);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GContinue;
	}
}
