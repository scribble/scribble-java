package org.scribble.lang.local;

import org.scribble.job.ScribbleException;
import org.scribble.lang.SType;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.model.endpoint.EGraphBuilderUtil2;
import org.scribble.type.kind.Local;
import org.scribble.type.name.Role;

public interface LType extends SType<Local>
{
	boolean isSingleCont();

	@Override
	LType substitute(Substitutions<Role> subs);

	@Override
	LType getInlined(STypeInliner i);//, Deque<SubprotoSig> stack);

	@Override
	SType<Local> unfoldAllOnce(STypeUnfolder<Local> u);
	
	// Uses b to builds graph "progressively" (working graph is mutable)
	// Use EGraphBuilderUtil2::finalise for final result
	void buildGraph(EGraphBuilderUtil2 b);
	
	ReachabilityEnv checkReachability(ReachabilityEnv env)
			throws ScribbleException;
}
