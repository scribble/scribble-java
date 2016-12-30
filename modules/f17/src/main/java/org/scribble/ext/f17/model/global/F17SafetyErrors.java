package org.scribble.ext.f17.model.global;

import java.util.Set;
import java.util.stream.Collectors;

// Duplicated from F17LTSSafetyErrors
public class F17SafetyErrors
{
	public final Set<F17SState> connection;
	public final Set<F17SState> disconnect;
	public final Set<F17SState> unconnected;
	public final Set<F17SState> unfinishedRole;
	
	public F17SafetyErrors(Set<F17SState> connection, Set<F17SState> disconnected, Set<F17SState> unconnected, Set<F17SState> unfinishedRole)
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
	
	private static final String getNodeLabel(F17SState s)
	{
		String m = s.getNodeLabel();
		return m.substring("label=\"".length(), m.length() - 1);
	}
}
