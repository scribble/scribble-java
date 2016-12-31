package org.scribble.ext.f17.model.global;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// Duplicated from F17LTSSafetyErrors
// Wait-for errors?
public class F17SafetyErrors
{
	public final Set<F17SState> connection;
	public final Set<F17SState> disconnect;
	public final Set<F17SState> unconnected;
	public final Set<F17SState> synchronisation;
	public final Set<F17SState> reception;
	public final Set<F17SState> unfinishedRole;
	public final Set<F17SState> orphan;
	private enum ERR { Connection, Disconnect, Unconnected, Synchronisation, Reception, UnfinishedRole, Orphan }
	
	private final Map<ERR, Set<F17SState>> errors = new LinkedHashMap<>();
	
	public F17SafetyErrors(Set<F17SState> connection, Set<F17SState> disconnect, Set<F17SState> unconnected,
			Set<F17SState> synchronisation, Set<F17SState> reception, Set<F17SState> unfinishedRole, Set<F17SState> orphan)
	{
		this.connection = connection;
		this.disconnect = disconnect;
		this.unconnected = unconnected;
		this.synchronisation = synchronisation;
		this.reception = reception;
		this.unfinishedRole = unfinishedRole;
		this.orphan = orphan;
		
		this.errors.put(ERR.Connection, connection);
		this.errors.put(ERR.Disconnect, disconnect);
		this.errors.put(ERR.Unconnected, unconnected);
		this.errors.put(ERR.Synchronisation, synchronisation);
		this.errors.put(ERR.Reception, reception);
		this.errors.put(ERR.UnfinishedRole, unfinishedRole);
		this.errors.put(ERR.Orphan, orphan);
	}
	
	public boolean isSafe()
	{
		return this.errors.values().stream().allMatch((es) -> es.isEmpty());
	}
	
	@Override
	public String toString()
	{
		String m = this.errors.entrySet().stream().map((e) -> 
				e.getValue().isEmpty() ? ""
					:	"\n[f17] " + e.getKey() + " errors:\n  "
						+ e.getValue().stream().map((s) -> getNodeLabel(s)).collect(Collectors.joining("\n  "))
				).collect(Collectors.joining(""));
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
