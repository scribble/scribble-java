package org.scribble.ext.f17.lts;

import java.util.Set;
import java.util.stream.Collectors;

public class F17LTSSafetyErrors
{
	public final Set<F17Session> connection;
	public final Set<F17Session> disconnect;
	public final Set<F17Session> unconnected;
	public final Set<F17Session> unfinishedRole;
	
	public F17LTSSafetyErrors(Set<F17Session> connection, Set<F17Session> disconnected, Set<F17Session> unconnected, Set<F17Session> unfinishedRole)
	{
		this.connection = connection;
		this.disconnect = disconnected;
		this.unconnected = unconnected;
		this.unfinishedRole = unfinishedRole;
	}
	
	public boolean isSafe()
	{
		return this.connection.isEmpty() && this.disconnect.isEmpty() && this.unconnected.isEmpty() && this.unfinishedRole.isEmpty();
	}
	
	@Override
	public String toString()
	{
		String m = "";
		if (!this.connection.isEmpty())
		{
			m += "\n[f17] Connection errors:\n  "
				+ this.connection.stream().map((s) -> getNodeLabel(s)).collect(Collectors.joining("\n  "));
		}
		if (!this.disconnect.isEmpty())
		{
			m += "\n[f17] Disconnect errors:\n  "
				+ this.disconnect.stream().map((s) -> getNodeLabel(s)).collect(Collectors.joining("\n  "));
		}
		if (!this.unconnected.isEmpty())
		{
			m += "\n[f17] Unconnected errors:\n  "
				+ this.unconnected.stream().map((s) -> getNodeLabel(s)).collect(Collectors.joining("\n  "));
		}
		if (!this.unfinishedRole.isEmpty())
		{
			m += "\n[f17] Unfinished role errors:\n  "
				+ this.unfinishedRole.stream().map((s) -> getNodeLabel(s)).collect(Collectors.joining("\n  "));
		}
		if (m.length() != 0)
		{
			m = m.substring(1, m.length());
		}
		return m;
	}
	
	private static final String getNodeLabel(F17Session s)
	{
		String m = s.getNodeLabel();
		return m.substring("label=\"".length(), m.length() - 1);
	}
}
