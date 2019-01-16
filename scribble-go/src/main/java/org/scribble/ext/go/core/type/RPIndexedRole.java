package org.scribble.ext.go.core.type;

import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.type.name.Role;

// FIXME: make distinct kind?  i.e., don't extend Role? -- yes: ParamRole is not a Role (Role should actually be a ParamRole in that sense)
public class RPIndexedRole extends Role
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final
	Comparator<RPIndexedRole> COMPARATOR = new Comparator<RPIndexedRole>()
			{
				@Override
				public int compare(RPIndexedRole i1, RPIndexedRole i2)
				{
					return i1.toString().compareTo(i2.toString());  // CHECKME: consistent with namegen string ordering?
				}
			};
	

	//public final ParamRange range;
	public final Set<RPInterval> intervals;  // size >= 1 -- size == 1 for parsed syntax
			// FIXME: this set was meant for multidim nat intervals, but multidim should be factored into the RPInterval itself
			// No, above is wrong -- this set is actually for multiple intervals, but currently only non-singleton at local level (i.e, variants)
	
	public RPIndexedRole(String name, Set<? extends RPInterval> intervals)
	{
		super(name);
		/*if (intervals.size() != 1) 
			// FIXME: this set was meant for multidim nat intervals, but multidim should be factored into the RPInterval itself
			// No: above is true for parsed globals, but Set needed for variants (subclass of this)
		{
			throw new RuntimeException("TODO: " + intervals);
		}*/
		this.intervals = Collections.unmodifiableSet(intervals);
	}
	
	/*
	public boolean isPotentialPeer(Smt2Translator smt2t, RPRoleVariant self, RPRoleVariant cand)
	{
		RPRoleVariant peerVariant = cand;
		
		if (!getName().equals(peerVariant.getName()))
		{
			return false;
		}

		if (intervals.size() > 1)
		{
			throw new RuntimeException("[rp-core] TODO: multi-dimension intervals: " + this);  // No?  Multiple intervals is not actually about multidim intervals, it's about constraint intersection?  (A multdim interval should be a single interval value?)
		}
		RPInterval d = this.intervals.stream().findAny().get();
		Set<RPIndexVar> vars = Stream.concat(peerVariant.intervals.stream().flatMap(x -> x.getIndexVars().stream()), peerVariant.cointervals.stream().flatMap(x -> x.getIndexVars().stream()))
				.collect(Collectors.toSet());
		vars.addAll(getIndexVars());

		// CHECKME: need to further constrain K? (by family?)
		
		List<String> cs = new LinkedList<>();
		cs.addAll(vars.stream().map(x -> smt2t.makeGte(x.toSmt2Formula(smt2t), smt2t.getDefaultBaseValue())).collect(Collectors.toList()));  // FIXME: generalise, parameter domain annotations
		
		if (smt2t.global.header instanceof RPGProtocolHeader)
		{
			// FIXME: WIP
			RPAnnotExpr annot = RPAnnotExpr.parse(((RPGProtocolHeader) smt2t.global.header).annot);
			if (annot.getVars().stream().map(x -> x.toString()).allMatch(x ->
					vars.stream().map(y -> y.toString()).anyMatch(y -> x.equals(y))))  // TODO: refactor
			{
				cs.add(annot.toSmt2Formula(smt2t));
			}
		}
		
		for (RPInterval ival : peerVariant.intervals)  // Is there a peer index inside all the peer-variant intervals
		{
			cs.add(smt2t.makeGte("peer", ival.start.toSmt2Formula(smt2t)));
			cs.add(smt2t.makeLte("peer", ival.end.toSmt2Formula(smt2t)));
		}
		if (!peerVariant.cointervals.isEmpty())
		{
			// ...and the peer index is outside one of the peer-variant cointervals
			/*cs.add(smt2t.makeOr(
				self.cointervals.stream().flatMap(x ->
						Stream.of(smt2t.makeLt("peer", x.start.toSmt2Formula(smt2t)), smt2t.makeGt("peer", x.end.toSmt2Formula(smt2t)))
				).collect(Collectors.toList())));
				* /
			cs.addAll(peerVariant.cointervals.stream().map(x ->
						smt2t.makeOr(smt2t.makeLt("peer", x.start.toSmt2Formula(smt2t)), smt2t.makeGt("peer", x.end.toSmt2Formula(smt2t)))
			).collect(Collectors.toList()));
		}
		// ...and the peer index is inside our I/O action interval -- then this is peer-variant is a peer
		cs.add(smt2t.makeGte("peer", d.start.toSmt2Formula(smt2t)));
		cs.add(smt2t.makeLte("peer", d.end.toSmt2Formula(smt2t)));

		if (vars.contains(RPIndexSelf.SELF))
		{
			// If self name is peer name, peer index is not self index
			if (peerVariant.getName().equals(self.getName()))
			{
				cs.add(smt2t.makeNot(smt2t.makeEq("peer", "self")));
			}
			// TODO: factor out variant/covariant inclusion/exclusion with above
			for (RPInterval ival : self.intervals)  // Is there a self index inside all the self-variant intervals
			{
				cs.add(smt2t.makeGte("self", ival.start.toSmt2Formula(smt2t)));
				cs.add(smt2t.makeLte("self", ival.end.toSmt2Formula(smt2t)));
			}
			if (!self.cointervals.isEmpty())
			{
				// ...and the self index is outside one of the self-variant cointervals
				/*cs.add(smt2t.makeOr(
					self.cointervals.stream().flatMap(x ->
							Stream.of(smt2t.makeLt("self", x.start.toSmt2Formula(smt2t)), smt2t.makeGt("self", x.end.toSmt2Formula(smt2t)))
					).collect(Collectors.toList())));
					* /
				cs.addAll(self.cointervals.stream().map(x ->
						smt2t.makeOr(smt2t.makeLt("self", x.start.toSmt2Formula(smt2t)), smt2t.makeGt("self", x.end.toSmt2Formula(smt2t)))
				).collect(Collectors.toList()));
			}
		}

		String smt2 = smt2t.makeAnd(cs);
		List<String> tmp = new LinkedList<>();
		tmp.add("peer");
		tmp.addAll(vars.stream().map(x -> x.toSmt2Formula(smt2t)).collect(Collectors.toList()));
		smt2 = smt2t.makeExists(tmp, smt2); 
		smt2 = smt2t.makeAssert(smt2);
		
		smt2t.job.debugPrintln("[rp-core] Running Z3 on " + d + " :\n" + smt2);
		
		boolean isSat = Z3Wrapper.checkSat(smt2t.job, smt2t.global, smt2);
		smt2t.job.debugPrintln("[rp-core] Checked sat: " + isSat);
		/*if (isSat)
		{
			peers.add(peerVariant);
			continue next;
		}* /
		return isSat;
	}*/
	
	// FIXME: rename
	public RPIndexedRole minimise(int self)
	{
		Set<RPInterval> ivals = this.intervals.stream().map(x -> x.minimise(self)).collect(Collectors.toSet());
		Optional<RPInterval> o = ivals.stream().filter(x -> x.isSingleton()).findFirst();
		if (o.isPresent())  // FIXME: more general interval "merge"?
		{
			ivals = Stream.of(o.get()).collect(Collectors.toSet());
		}
		return new RPIndexedRole(this.getLastElement(), ivals);
	}
	
	// FIXME: currently just a syntactic singleton check on a single interval -- false negatives possible in general case (multiple intervals)
	public boolean isSingleton()
	{
		/*if (this.intervals.size() != 1)
		{
			return false;
		}
		RPInterval ival = this.intervals.stream().findFirst().get();
		return ival.isSingleton();*/
		return this.intervals.stream().anyMatch(x -> x.isSingleton());
	}

	public Set<RPIndexVar> getIndexVars()
	{
		return this.intervals.stream()
				.flatMap(d -> Stream.of(d.start.getVars(), d.end.getVars()).flatMap(vs -> vs.stream()))
				.collect(Collectors.toSet());
	}
	
	public RPInterval getParsedRange()
	{
		if (this.intervals.size() > 1)
		{
			throw new RuntimeException("[param-core] Shouldn't get in here: " + this.intervals);
		}
		return this.intervals.iterator().next();
	}
	
	// CHECKME
	public Role getName()
	{
		return new Role(this.getLastElement());
	}

	// if equals, then toString.equals
	@Override
	public String toString()
	{
		String rs = this.intervals.stream()
				.sorted(RPInterval.COMPARATOR)
				.map(Object::toString)
				.collect(Collectors.joining(", "));
		if (this.intervals.size() > 1)
		{
			rs = "{" + rs + "}";
		}
		return super.toString() + rs;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7121;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.intervals.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof RPIndexedRole))
		{
			return false;
		}
		RPIndexedRole them = (RPIndexedRole) obj;
		return super.equals(them)  // Does canEqual
				&& this.intervals.equals(them.intervals);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof RPIndexedRole;
	}
}
