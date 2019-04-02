package org.scribble.lang.local;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.job.Job;
import org.scribble.job.ScribbleException;
import org.scribble.lang.Protocol;
import org.scribble.lang.ProtocolMod;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EGraphBuilderUtil2;
import org.scribble.model.endpoint.EState;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.Local;
import org.scribble.type.kind.NonRoleParamKind;
import org.scribble.type.name.LProtocolName;
import org.scribble.type.name.MemberName;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

public class LProtocol extends
		Protocol<Local, LProtocolName, LSeq> implements LType
{
	public LProtocol(ProtocolDecl<Local> source, List<ProtocolMod> mods,
			LProtocolName fullname, List<Role> roles,
			List<MemberName<? extends NonRoleParamKind>> params, LSeq def)
	{
		super(source, mods, fullname, roles, params, def);
	}

	@Override
	public LProtocol reconstruct(ProtocolDecl<Local> source,
			List<ProtocolMod> mods, LProtocolName fullname, List<Role> roles,
			List<MemberName<? extends NonRoleParamKind>> params, LSeq def)
	{
		return new LProtocol(source, mods, fullname, roles, params, def);
	}
	
	@Override
	public RecVar isSingleCont()
	{
		throw new RuntimeException("Unsupported for LProtocol:\n" + this);
	}

	@Override
	public boolean isSingleConts(Set<RecVar> rvs)
	{
		throw new RuntimeException("Unsupported for LProtocol:\n" + this);
	}

	@Override
	public LType substitute(Substitutions subs)
	{
		/*List<Role> roles = this.roles.stream().map(x -> subs.subsRole(x))
				.collect(Collectors.toList());
		List<Arg<NonRoleParamKind>> params = this.params.stream().map(x -> ...)
				.collect(Collectors.toList());
		return reconstruct(getSource(), this.mods, this.fullname, roles, params,
				this.def.substitute(subs));*/
		throw new RuntimeException("Unsupported for LProtocol:\n" + this);
	}
	
	// Pre: stack.peek is the sig for the calling Do (or top-level entry)
	// i.e., it gives the roles/args at the call-site
	@Override
	public LRecursion getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		SubprotoSig sig = i.peek();
		Substitutions subs = new Substitutions(this.roles, sig.roles, this.params,
				sig.args);
		LSeq body = this.def.substitute(subs).getInlined(i);//, stack);
		LProtocolDecl source = getSource();  // CHECKME: or empty source?
		RecVar rv = i.getInlinedRecVar(sig);
		return new LRecursion(source, rv, body);
	}
	
	@Override
	public LProtocol unfoldAllOnce(STypeUnfolder<Local> u)
	{
		LSeq unf = (LSeq) this.def.unfoldAllOnce(u);
		return reconstruct(getSource(), this.mods, this.fullname, this.roles,
				this.params, unf);
	}

	public EGraph toEGraph(Job job)
	{
		EGraphBuilderUtil2 b = new EGraphBuilderUtil2(job.config.ef);
		b.init(null);  // FIXME: init param not used
		if (this.def.isEmpty())  // Empty Seq special case for top-level -- in general, Seq must be non-empty, cf. LSeq::buildGraph entry/exit
		{
			EState s = b.getEntry();
			return new EGraph(s, s);
		}
		this.def.buildGraph(b);
		EGraph g = b.finalise();
		return g;
		/*EState init = toGraph(b);
		EState term = init.getTerminal();
		return new EGraph(init, term);*/
	}
	
	@Override
	public void buildGraph(EGraphBuilderUtil2 b)
	{
		this.def.buildGraph(b);
	}

	@Override
	public ReachabilityEnv checkReachability(ReachabilityEnv env)
			throws ScribbleException
	{
		throw new RuntimeException("Unsupported for Protocol: " + this);
	}

	public ReachabilityEnv checkReachability()
			throws ScribbleException
	{
		return this.def
				.checkReachability(new ReachabilityEnv(false, Collections.emptySet()));
	}
	
	@Override
	public LProtocolDecl getSource()
	{
		return (LProtocolDecl) super.getSource();
	}
	
	@Override
	public String toString()
	{
		return this.mods.stream().map(x -> x.toString() + " ")
				.collect(Collectors.joining(" ")) + "local " + super.toString();
	}

	@Override
	public int hashCode()
	{
		int hash = 11;
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
		if (!(o instanceof LProtocol))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LProtocol;
	}
}
