package org.scribble.ext.f17.model.global;

import java.util.Map;
import java.util.Set;

import org.scribble.model.endpoint.actions.ESend;
import org.scribble.sesstype.name.Role;

public class F17ProgressErrors
{
	public final Map<Role, Set<Set<F17SState>>> roleProgress;
	public final Map<ESend, Set<Set<F17SState>>> eventualReception;
	
	public F17ProgressErrors(Map<Role, Set<Set<F17SState>>> roleProgress, Map<ESend, Set<Set<F17SState>>> eventualReception)
	{
		this.roleProgress = roleProgress;
		this.eventualReception = eventualReception;
	}
	
	public boolean satisfiesProgress()
	{
		return this.roleProgress.isEmpty() && this.eventualReception.isEmpty();
	}
	
	@Override
	public String toString()
	{
		String m = "";
		if (!this.roleProgress.isEmpty())
		{
			m += "\n[f17] Role progress violation(s):\n  "
			//	+ this.roleProgress.stream().map((ts) -> format(ts)).collect(Collectors.joining("\n  "));
					+ roleProgress.toString();
		}
		if (!this.eventualReception.isEmpty())
		{
			m += "\n[f17] Eventual reception violation(s):\n  "
				//+ this.eventualReception.stream().map((ts) -> format(ts)).collect(Collectors.joining("\n  "));
				+ "\n" + eventualReception.toString();
		}
		if (m.length() != 0)
		{
			m = m.substring(1, m.length());
		}
		return m;
	}
	
	/*private static final String format(Set<F17SState> ts)
	{
		return ts.stream().map((s) -> s.toString()).collect(Collectors.joining(", "));
	}*/
}
