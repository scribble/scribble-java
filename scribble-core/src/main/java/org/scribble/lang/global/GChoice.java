package org.scribble.lang.global;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.job.ScribbleException;
import org.scribble.lang.Choice;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.lang.local.LChoice;
import org.scribble.lang.local.LSeq;
import org.scribble.lang.local.LSkip;
import org.scribble.lang.local.LType;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;

public class GChoice extends Choice<Global, GSeq> implements GType
{
	public GChoice(org.scribble.ast.Choice<Global> source, Role subj,
			List<GSeq> blocks)
	{
		super(source, subj, blocks);
	}
	
	@Override
	public GChoice reconstruct(org.scribble.ast.Choice<Global> source, Role subj,
			List<GSeq> blocks)
	{
		return new GChoice(source, subj, blocks);
	}

	@Override
	public GChoice substitute(Substitutions subs)
	{
		List<GSeq> blocks = this.blocks.stream().map(x -> x.substitute(subs))
				.collect(Collectors.toList());
		return reconstruct(getSource(), subs.subsRole(this.subj), blocks);
	}

	@Override
	public GChoice getInlined(STypeInliner i )//GTypeTranslator t, Deque<SubprotoSig> stack)
	{
		org.scribble.ast.global.GChoice source = getSource();  // CHECKME: or empty source?
		List<GSeq> blocks = this.blocks.stream().map(x -> x.getInlined(i))
				.collect(Collectors.toList());
		return reconstruct(source, this.subj, blocks);
	}

	@Override
	public GChoice unfoldAllOnce(STypeUnfolder<Global> u)
	{
		org.scribble.ast.global.GChoice source = getSource();  // CHECKME: or empty source?
		List<GSeq> blocks = this.blocks.stream().map(x -> x.unfoldAllOnce(u))
				.collect(Collectors.toList());
		return reconstruct(source, this.subj, blocks);
	}
	
	@Override
	public LType project(Role self)
	{
		Role subj = this.subj.equals(self) ? Role.SELF : this.subj;
		List<LSeq> blocks = this.blocks.stream().map(x -> x.project(self))
				.filter(x -> !x.isEmpty())
				.collect(Collectors.toList());
		if (blocks.isEmpty())
		{
			return LSkip.SKIP;  // CHECKME: OK, or "empty" choice at subj still important?
		}
		return new LChoice(null, subj, blocks);
	}

	@Override
	public Set<Role> checkRoleEnabling(Set<Role> enabled) throws ScribbleException
	{
		if (!enabled.contains(this.subj))
		{
			throw new ScribbleException("Subject not enabled: " + this.subj);
		}
		Set<Role> subj = Stream.of(this.subj).collect(Collectors.toSet());
		List<Set<Role>> blocks = new LinkedList<>();
		for (GSeq block : this.blocks)
		{
			blocks.add(block.checkRoleEnabling(subj));
		}
		Set<Role> res = new HashSet<>(enabled);
		Set<Role> tmp = blocks.stream().flatMap(x -> x.stream())
				.filter(x -> blocks.stream().allMatch(y -> y.contains(x)))
				.collect(Collectors.toSet());
		res.addAll(tmp);
		return Collections.unmodifiableSet(res);
	}

	@Override
	public Map<Role, Role> checkExtChoiceConsistency(Map<Role, Role> enablers)
			throws ScribbleException
	{
		Map<Role, Role> subj = Stream.of(this.subj)
				.collect(Collectors.toMap(x -> x, x -> x));
		List<Map<Role, Role>> blocks = new LinkedList<>();
		for (GSeq block : this.blocks)
		{
			blocks.add(block.checkExtChoiceConsistency(subj));
		}
		Map<Role, Role> res = new HashMap<>(enablers);
		Set<Entry<Role, Role>> tmp = blocks.stream()
				.flatMap(x -> x.entrySet().stream()).collect(Collectors.toSet());
		for (Entry<Role, Role> e : tmp)
		{
			Role enabled = e.getKey();
			Role enabler = e.getValue();
			if (tmp.stream().anyMatch(
					x -> x.getKey().equals(enabled) && !x.getValue().equals(enabler)))
			{
				throw new ScribbleException(
						"Inconsistent external choice subjects for " + enabled + ": "
								+ tmp.stream().filter(x -> x.getKey().equals(enabled))
										.collect(Collectors.toList()));
			}
			if (!res.containsKey(enabled))
			{
				res.put(enabled, enabler);
			}
		}
		return Collections.unmodifiableMap(res);
	}

	@Override
	public org.scribble.ast.global.GChoice getSource()
	{
		return (org.scribble.ast.global.GChoice) super.getSource();
	}

	@Override
	public int hashCode()
	{
		int hash = 3067;
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
		if (!(o instanceof GChoice))
		{
			return false;
		}
		return super.equals(o);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GChoice;
	}
}
