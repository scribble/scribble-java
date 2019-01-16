package org.scribble.ext.go.core.type;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ext.go.ast.global.RPGProtocolHeader;
import org.scribble.ext.go.type.annot.RPAnnotExpr;
import org.scribble.ext.go.type.index.RPIndexSelf;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.ext.go.util.Smt2Translator;
import org.scribble.ext.go.util.Z3Wrapper;

public class RPRoleVariant extends RPIndexedRole
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final
	Comparator<RPRoleVariant> COMPARATOR = new Comparator<RPRoleVariant>()
			{
				@Override
				public int compare(RPRoleVariant i1, RPRoleVariant i2)
				{
					return i1.toString().compareTo(i2.toString());
				}
			};
	
	public final Set<RPInterval> cointervals;
	
	public RPRoleVariant(String name, Set<RPInterval> ranges, Set<RPInterval> coranges)
	{
		super(name, ranges);
		this.cointervals = Collections.unmodifiableSet(coranges);
	}
	

	public boolean isPotentialPeer(Smt2Translator smt2t, RPIndexedRole subj, RPRoleVariant cand)
	{
		RPRoleVariant peerVariant = cand;
		
		if (!subj.getName().equals(peerVariant.getName()))
		{
			return false;
		}
		
		String smt2 = makePeerCheckSmt2(smt2t, subj, cand);

		smt2t.job.debugPrintln("[rp-core] Running Z3 on " + subj + " :\n" + smt2);
		
		boolean isSat = Z3Wrapper.checkSat(smt2t.job, smt2t.global, smt2);
		smt2t.job.debugPrintln("[rp-core] Checked sat: " + isSat);
		/*if (isSat)
		{
			peers.add(peerVariant);
			continue next;
		}*/
		return isSat;
	//*/
	}

	public String makePeerCheckSmt2(Smt2Translator smt2t, RPIndexedRole subj, RPRoleVariant cand)
	{
		RPRoleVariant self = this;
		RPRoleVariant peerVariant = cand;

		if (!subj.getName().equals(peerVariant.getName()))
		{
			throw new RuntimeException("[rp-core] Shouldn't get here: ");
		}
		if (subj.intervals.size() > 1)
		{
			throw new RuntimeException("[rp-core] TODO: " + subj);  // No?  Multiple intervals is not actually about multidim intervals, it's about constraint intersection?  (A multdim interval should be a single interval value?)
		}
		RPInterval d = subj.intervals.stream().findAny().get();

		Set<RPIndexVar> vars = Stream.concat(peerVariant.intervals.stream().flatMap(x -> x.getIndexVars().stream()), peerVariant.cointervals.stream().flatMap(x -> x.getIndexVars().stream()))
				.collect(Collectors.toSet());
		vars.addAll(subj.getIndexVars());

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
		
		return smt2;
	}
	
	public boolean isValid(Smt2Translator smt2t)
	{
		Set<RPInterval> cand = this.intervals;
		Set<RPInterval> coset = this.cointervals;
		String z3 = makePhiSmt2(smt2t, false);
		List<RPIndexVar> vars = Stream.concat(
					cand.stream().flatMap(c -> c.getIndexVars().stream()),
					coset.stream().flatMap(c -> c.getIndexVars().stream())
				).distinct().collect(Collectors.toList());
		if (!vars.isEmpty())
		{
			//z3 = "(exists (" + vars.stream().map(p -> "(" + p + " Int)").collect(Collectors.joining(" ")) + ") " + z3 + ")";
			List<String> tmp = vars.stream().map(v -> smt2t.makeLte(smt2t.getDefaultBaseValue(), v.toSmt2Formula(smt2t))).collect(Collectors.toList());

			if (smt2t.global.header instanceof RPGProtocolHeader)
			{
				// TODO FIXME: WIP
				RPAnnotExpr annot = RPAnnotExpr.parse(((RPGProtocolHeader) smt2t.global.header).annot);
				if (annot.getVars().stream().map(x -> x.toString()).allMatch(x ->
						vars.stream().map(y -> y.toString()).anyMatch(y -> x.equals(y))))  // TODO: refactor
				{
					tmp.add(annot.toSmt2Formula(smt2t));
				}
			}

			tmp.add(z3);
			z3 = smt2t.makeAnd(tmp);
			z3 = smt2t.makeExists(vars.stream().map(v -> v.toSmt2Formula(smt2t)).collect(Collectors.toList()), z3);
		}
		//z3 = smt2t.makeExists(Stream.of("self").collect(Collectors.toList()), z3);
		z3 = smt2t.makeAssert(z3);
		
		smt2t.job.debugPrintln("\n[rp-core] Variant candidate (" //+ i++ + "/" + size + "): " 
				+ cand);
		smt2t.job.debugPrintln("[rp-core] Co-set: " + coset);
		smt2t.job.debugPrintln("[rp-core] Running Z3 on:\n" + z3);
		
		boolean isSat = Z3Wrapper.checkSat(smt2t.job, smt2t.global, z3);
		/*if (isSat)
		{
			//protoRoles.get(r).add(cand);
			variants.get(r).add(new RPRoleVariant(r.toString(), cand, coset));
		}*/
		smt2t.job.debugPrintln("[rp-core] Checked sat: " + isSat);
		return isSat;
	}

	// Doesn't include "assert", nor exists-bind index vars (but does exists-bind self)
	public String makePhiSmt2(Smt2Translator smt2t, boolean not)
	{
		Set<RPInterval> cand = this.intervals;
		Set<RPInterval> coset = this.cointervals; 

		List<String> cs = new LinkedList<>();
		List<String> dom = new LinkedList<>();

		//if (cand.size() > 0)
		{
			//z3 += smt2t.makeAnd(
			cs.addAll(
				cand.stream().map(c -> 
					 //"(and (>= self " + c.start.toSmt2Formula() + ") (<= self " + c.end.toSmt2Formula() + ")" + ((!c.start.isConstant() || !c.end.isConstant()) ? " (<= " + c.start.toSmt2Formula() + " " + c.end.toSmt2Formula() + ")" : "") + ")"
					{
						List<String> tmp = new LinkedList<>();
						tmp.add(smt2t.makeGte("self", c.start.toSmt2Formula(smt2t)));
						tmp.add(smt2t.makeLte("self", c.end.toSmt2Formula(smt2t)));
						if (!c.start.isConstant() || !c.end.isConstant())
						{
							dom.add(smt2t.makeLte(c.start.toSmt2Formula(smt2t), c.end.toSmt2Formula(smt2t)));
						}
						return smt2t.makeAnd(tmp);
					})
				//.reduce((c1, c2) -> "(and " + c1 + " " + c2 +")").get();
				.collect(Collectors.toList())
			);
		}

		/*if (coset.size() > 0)
		{
			if (cand.size() > 0)
			{
				z3 = "(and " + z3 + " ";
			}*/
			cs.addAll(
				coset.stream().map(c -> 
				//z3 += coset.stream().map(c -> "(and (not (and (>= self " + c.start.toSmt2Formula() + ") (<= self " + c.end.toSmt2Formula() + ")))" + ((!c.start.isConstant() || !c.end.isConstant()) ? " (<= " + c.start.toSmt2Formula() + " " + c.end.toSmt2Formula() + ")" : "") + ")")
					{
						List<String> tmp = new LinkedList<>();
						//tmp.add(smt2t.makeOr(smt2t.makeLt("self", c.start.toSmt2Formula()), smt2t.makeGt("self", c.end.toSmt2Formula())));
						tmp.add(smt2t.makeNot(smt2t.makeAnd(smt2t.makeGte("self", c.start.toSmt2Formula(smt2t)), smt2t.makeLte("self", c.end.toSmt2Formula(smt2t)))));
						if (!c.start.isConstant() || !c.end.isConstant())
						{
							dom.add(smt2t.makeLte(c.start.toSmt2Formula(smt2t), c.end.toSmt2Formula(smt2t)));  // Must be outside the not (if one)
						}
						return (tmp.size() == 1) ? tmp.get(0) : smt2t.makeAnd(tmp); 
					})
				//.reduce((c1, c2) -> "(and " + c1 + " " + c2 +")").get();
				.collect(Collectors.toList())
				
				 //(and (not (and (and (>= self 1) (<= self K))) (<= 1 K)))
			);
			/*if (cand.size() > 0)
			{
				z3 += ")";
			}
		}*/

		String z3 = //"(exists ((self Int))" + z3 + ")";
				smt2t.makeExists(Stream.of("self").collect(Collectors.toList()), smt2t.makeAnd(cs));  // CHECKME: need explicit self >= 1?
		if (not)
		{
			z3 = smt2t.makeNot(z3);
		}
		dom.add(z3);
		z3 = smt2t.makeAnd(dom);

		return z3;
	}

	// self is irrelevant -- variants don't contain self
	@Override
	public RPRoleVariant minimise(int self)
	{
		Set<RPInterval> ivals = this.intervals.stream().map(x -> x.minimise(self)).collect(Collectors.toSet());
		Optional<RPInterval> o = ivals.stream().filter(x -> x.isSingleton()).findFirst();
		if (o.isPresent())  // FIXME: more general interval "merge"?
		{
			ivals = Stream.of(o.get()).collect(Collectors.toSet());
			return new RPRoleVariant(this.getLastElement(), ivals, Collections.emptySet());
		}
		Set<RPInterval> coivals = this.cointervals.stream().map(x -> x.minimise(self)).collect(Collectors.toSet());
		return new RPRoleVariant(this.getLastElement(), ivals, coivals);
	}
	
	/*@Override
	public boolean isSingleton()
	{
		return super.isSingleton();
	}*/

	@Override
	public Set<RPIndexVar> getIndexVars()
	{
		Set<RPIndexVar> ivars = super.getIndexVars();
		ivars.addAll(this.cointervals.stream()
				.flatMap(d -> Stream.of(d.start.getVars(), d.end.getVars()).flatMap(vs -> vs.stream()))
				.collect(Collectors.toSet()));
		return ivars;
	}

	// if equals, then toString.equals
	@Override
	public String toString()
	{
		// Duplicated from super to make rs1 braces mandatory 
		/*String rs1 = "{" + this.intervals.stream().map(Object::toString).collect(Collectors.joining(", ")) + "}";
		String rs2 = this.cointervals.isEmpty()
				? ""
				: "{" + this.cointervals.stream().map(Object::toString).collect(Collectors.joining(", ")) + "}";*/
		String rs1 = "{" + this.intervals.stream().sorted(RPInterval.COMPARATOR).map(Object::toString).collect(Collectors.joining(", ")) + "}";
		String rs2 = this.cointervals.isEmpty()
				? ""
				: "{" + this.cointervals.stream().sorted(RPInterval.COMPARATOR).map(Object::toString).collect(Collectors.joining(", ")) + "}";
		return getName() + rs1 + rs2;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7193;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.cointervals.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof RPRoleVariant))
		{
			return false;
		}
		RPRoleVariant them = (RPRoleVariant) obj;
		return super.equals(them)  // Does canEqual
				&& this.cointervals.equals(them.cointervals);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof RPRoleVariant;
	}
}
