package org.scribble.lang.local;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ast.Constants;
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
	public final Role self;

	public LProtocol(ProtocolDecl<Local> source, List<ProtocolMod> mods,
			LProtocolName fullname, List<Role> roles, Role self,
			List<MemberName<? extends NonRoleParamKind>> params, LSeq def)
	{
		super(source, mods, fullname, roles, params, def);
		this.self = self;
	}

	public LProtocol reconstruct(ProtocolDecl<Local> source,
			List<ProtocolMod> mods, LProtocolName fullname, List<Role> roles,
			Role self, List<MemberName<? extends NonRoleParamKind>> params, LSeq def)
	{
		return new LProtocol(source, mods, fullname, roles, self, params, def);
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
	
	// CHECKME: drop from Protocol (after removing Protocol from SType?)
	// Pre: stack.peek is the sig for the calling Do (or top-level entry)
	// i.e., it gives the roles/args at the call-site
	@Override
	public LProtocol getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		SubprotoSig sig = i.peek();
		Substitutions subs = new Substitutions(this.roles, sig.roles, this.params,
				sig.args);
		LSeq body = this.def.substitute(subs).getInlined(i).pruneRecs();
		RecVar rv = i.getInlinedRecVar(sig);
		LRecursion rec = new LRecursion(null, rv, body);  // CHECKME: or protodecl source?
		LProtocolDecl source = getSource();
		LSeq def = new LSeq(null, Stream.of(rec).collect(Collectors.toList()));
		return new LProtocol(source, this.mods, this.fullname, this.roles,
				this.self, this.params, def);
	}
	
	@Override
	public LProtocol unfoldAllOnce(STypeUnfolder<Local> u)
	{
		LSeq unf = (LSeq) this.def.unfoldAllOnce(u);
		return reconstruct(getSource(), this.mods, this.fullname, this.roles,
				this.self, this.params, unf);
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
	protected String rolesToString()
	{
		return "("
				+ this.roles.stream()
						.map(x -> x.equals(this.self)
								? Constants.SELF_KW + " " + this.self
								: Constants.ROLE_KW + " " + x)
						.collect(Collectors.joining(", "))
				+ ")";
	}

	@Override
	public int hashCode()
	{
		int hash = 11;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.self.hashCode();
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
