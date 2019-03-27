package org.scribble.lang.local;

import java.util.HashSet;
import java.util.Set;

import org.scribble.ast.ProtocolKindNode;
import org.scribble.job.ScribbleException;
import org.scribble.lang.Recursion;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.model.endpoint.EGraphBuilderUtil2;
import org.scribble.type.kind.Local;
import org.scribble.type.name.RecVar;

public class LRecursion extends Recursion<Local, LSeq> implements LType
{
	public LRecursion(//org.scribble.ast.Recursion<Local> source, 
			ProtocolKindNode<Local> source,  // Due to inlining, protocoldecl -> rec
			RecVar recvar, LSeq body)
	{
		super(source, recvar, body);
	}
	
	@Override
	public LRecursion reconstruct(org.scribble.ast.ProtocolKindNode<Local> source,
			RecVar recvar, LSeq block)
	{
		return new LRecursion(source, recvar, block);
	}

	@Override
	public RecVar isSingleCont()
	{
		return null;
	}

	@Override
	public boolean isSingleConts(Set<RecVar> rvs)
	{
		Set<RecVar> tmp = new HashSet<>(rvs);
		tmp.add(this.recvar);
		return this.body.isSingleConts(tmp);
	}
	
	@Override
	public LRecursion substitute(Substitutions subs)
	{
		return reconstruct(getSource(), this.recvar, this.body.substitute(subs));
	}

	@Override
	public LRecursion getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		org.scribble.ast.ProtocolKindNode<Local> source = getSource();  // CHECKME: or empty source?
		RecVar rv = i.enterRec(//stack.peek(), 
				this.recvar);  // FIXME: make GTypeInliner, and record recvars to check freshness (e.g., rec X in two choice cases)
		LSeq body = this.body.getInlined(i);//, stack);
		LRecursion res = reconstruct(source, rv, body);
		i.exitRec(this.recvar);
		return res;
	}

	@Override
	public LType unfoldAllOnce(STypeUnfolder<Local> u)
	{
		if (!u.hasRec(this.recvar))  // N.B. doesn't work if recvars shadowed
		{
			u.pushRec(this.recvar, this.body);
			LType unf = (LType) this.body.unfoldAllOnce(u);
			u.popRec(this.recvar);  
					// Needed for, e.g., repeat do's in separate choice cases -- cf. stack.pop in GDo::getInlined, must pop sig there for Seqs
			return unf;
		}
		return this;
	}
	
	@Override
	public void buildGraph(EGraphBuilderUtil2 b)
	{
		b.addEntryLabel(this.recvar);
		b.pushRecursionEntry(this.recvar, b.getEntry());
		this.body.buildGraph(b);
		b.popRecursionEntry(this.recvar);
	}

	@Override
	public ReachabilityEnv checkReachability(ReachabilityEnv env)
			throws ScribbleException
	{
		env = this.body.checkReachability(env);
		if (env.recvars.contains(this.recvar))
		{
			Set<RecVar> tmp = new HashSet<>(env.recvars);
			tmp.remove(this.recvar);
			env = new ReachabilityEnv(env.postcont, tmp);
		}
		return env;
	}

	@Override
	public int hashCode()
	{
		int hash = 2309;
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
		if (!(o instanceof LRecursion))
		{
			return false;
		}
		return super.equals(o);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LRecursion;
	}
}
