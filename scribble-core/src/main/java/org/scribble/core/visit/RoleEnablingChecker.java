package org.scribble.core.visit;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.DisconnectAction;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.util.ScribException;

// Not supported for Do
public class RoleEnablingChecker extends InlinedVisitor<Global, GSeq>
{
	private Set<Role> enabled;  // Invariant: unmodifiable

	public RoleEnablingChecker()
	{
		this.enabled = Collections.emptySet();  // Already immutable
	}

	public RoleEnablingChecker(Set<Role> enabled)
	{
		setEnabled(enabled);
	}
	
	public Choice<Global, GSeq> visitChoice(Choice<Global, GSeq> n)
			throws ScribException
	{
		Set<Role> enabled = getEnabled();
		if (!enabled.contains(n.subj))
		{
			throw new ScribException("Subject not enabled: " + n.subj);
		}
		Set<Role> subj = Stream.of(n.subj).collect(Collectors.toSet());
		RoleEnablingChecker nested = new RoleEnablingChecker();
		List<Set<Role>> blocks = new LinkedList<>();
		for (GSeq block : n.blocks)
		{
			nested.setEnabled(subj);  // Copies defensively
			block.visitWith(nested);
			blocks.add(nested.getEnabled());
		}
		Set<Role> res = new HashSet<>(enabled);
		Set<Role> tmp = blocks.stream().flatMap(x -> x.stream())
				.filter(x -> blocks.stream().allMatch(y -> y.contains(x)))
				.collect(Collectors.toSet());
		res.addAll(tmp);
		setEnabled(res);
		return n;
	}

	@Override
	public DirectedInteraction<Global, GSeq> visitDirectedInteraction(
			DirectedInteraction<Global, GSeq> n) throws ScribException
	{
		Set<Role> enabled = getEnabled();
		if (!enabled.contains(n.src))
		{
			throw new ScribException("Source role not enabled: " + n.src);
		}
		if (enabled.contains(n.dst))
		{
			return n;
		}
		Set<Role> res = new HashSet<>(enabled);
		res.add(n.dst);
		setEnabled(res);
		return n;
	}

	@Override
	public DisconnectAction<Global, GSeq> visitDisconnect(
			DisconnectAction<Global, GSeq> n) throws ScribException
	{
		Set<Role> enabled = getEnabled();
		if (!enabled.contains(n.left))
		{
			throw new ScribException("Role not enabled: " + n.left);
		}
		if (!enabled.contains(n.right))
		{
			throw new ScribException("Role not enabled: " + n.right);
		}
		return n;
	}
	
	public Set<Role> getEnabled()
	{
		return this.enabled;
	}
	
	protected void setEnabled(Set<Role> enabled)
	{
		this.enabled = Collections.unmodifiableSet(enabled);
	}
}
