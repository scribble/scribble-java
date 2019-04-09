package org.scribble.core.visit.global;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.core.visit.InlinedVisitor;
import org.scribble.util.ScribException;

// Pre: use on inlined or later (unsupported for Do, also Protocol)
public class ExtChoiceConsistencyChecker extends InlinedVisitor<Global, GSeq>
{
	private Map<Role, Role> enablers;  // Invariant: unmodifiable

	public ExtChoiceConsistencyChecker(Map<Role, Role> enabled)
	{
		setEnablers(enabled);
	}
	
	public Choice<Global, GSeq> visitChoice(Choice<Global, GSeq> n)
			throws ScribException
	{
		Map<Role, Role> subj = Stream.of(n.subj)
				.collect(Collectors.toMap(x -> x, x -> x));
		List<Map<Role, Role>> blocks = new LinkedList<>();
		ExtChoiceConsistencyChecker nested = new ExtChoiceConsistencyChecker(subj);  
				// Arg redundant, but better to keep a single constructor, for factory pattern
		for (GSeq block : n.blocks)
		{
			nested.setEnablers(subj);
			block.visitWith(nested);
			blocks.add(nested.getEnablers());
		}
		Map<Role, Role> res = new HashMap<>(getEnablers());
		Set<Entry<Role, Role>> all = blocks.stream()
				.flatMap(x -> x.entrySet().stream()).collect(Collectors.toSet());
		for (Entry<Role, Role> e : all)
		{
			Role enabled = e.getKey();
			Role enabler = e.getValue();
			if (all.stream().anyMatch(
					x -> x.getKey().equals(enabled) && !x.getValue().equals(enabler)))
			{
				throw new ScribException(
						"Inconsistent external choice subjects for " + enabled + ": "
								+ all.stream().filter(x -> x.getKey().equals(enabled))
										.collect(Collectors.toList()));
			}
			if (!res.containsKey(enabled))
			{
				res.put(enabled, enabler);
			}
		}
		setEnablers(res);
		return n;
	}

	@Override
	public DirectedInteraction<Global, GSeq> visitDirectedInteraction(
			DirectedInteraction<Global, GSeq> n) throws ScribException
	{
		Map<Role, Role> enablers = getEnablers();
		if (enablers.containsKey(n.dst))

		{
			return n;
		}
		Map<Role, Role> res = new HashMap<>(enablers);
		res.put(n.dst, n.src);
		setEnablers(res);
		return n;
	}
	
	public Map<Role, Role> getEnablers()
	{
		return this.enablers;
	}
	
	protected void setEnablers(Map<Role, Role> enabled)
	{
		this.enablers = Collections.unmodifiableMap(enabled);
	}
}
