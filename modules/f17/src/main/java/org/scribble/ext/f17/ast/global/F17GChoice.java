package org.scribble.ext.f17.ast.global;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.scribble.ext.f17.ast.F17Choice;
import org.scribble.ext.f17.ast.global.action.F17GAction;
import org.scribble.ext.f17.main.F17Exception;
import org.scribble.sesstype.name.Role;

public class F17GChoice extends F17Choice<F17GAction, F17GType> implements F17GType
{
	public F17GChoice(Map<F17GAction, F17GType> cases)
	{
		super(cases);
	}

	/*@Override
	public F17GType onceUnfolding()
	{
		return F17AstFactory.FACTORY.GChoice(this.cases.entrySet().stream()
				.collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue().onceUnfolding())));
	}*/

	@Override
	public void checkRoleEnabling(Set<Role> enabled) throws F17Exception
	{
		if (this.cases.size() == 1)
		{
			F17GAction a = this.cases.keySet().iterator().next();
			if (!enabled.containsAll(a.subjs))
			{
				throw new F17Exception("[f17] Subject(s) not enabled: " + a);
			}
			Set<Role> tmp = new HashSet<>(enabled);
			tmp.addAll(a.objs);
			this.cases.get(a).checkRoleEnabling(tmp);
		}
		else
		{
			Role subj = null;
			for (Entry<F17GAction, F17GType> e : this.cases.entrySet())
			{
				Set<Role> subjs = e.getKey().subjs;
				if (subjs.size() != 1)
				{
					throw new F17Exception("[f17] Invalid subjects for non-unary choice: " + e.getKey());
				}
				Role r = subjs.iterator().next();
				if (subj == null)
				{
					if (!enabled.contains(r))
					{
						throw new F17Exception("[f17] Subject not enabled: " + e.getKey());
					}
					subj = r;
				}
				else
				{
					if (!subj.equals(r))
					{
						throw new F17Exception("[f17] Inconsistent global choice subjects: " + this.cases.keySet());
								// FIXME: subsumed by projection choice subject check?
								// FIXME: Scribble at-annotation lost (not checked) -- make "at" optional (for f17)?
					}
				}
				Set<Role> tmp = new HashSet<>();
				tmp.add(subj);  // FIXME: remove sub(pi) from orig def
				tmp.addAll(e.getKey().objs);
				e.getValue().checkRoleEnabling(tmp);
			}
		}
	}
	
	@Override
	public int hashCode()
	{
		int hash = 29;
		hash = 31 * hash + this.cases.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof F17GChoice))
		{
			return false;
		}
		F17GChoice them = (F17GChoice) obj;
		return them.canEquals(this) && this.cases.equals(them.cases);
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof F17GChoice;
	}
}
