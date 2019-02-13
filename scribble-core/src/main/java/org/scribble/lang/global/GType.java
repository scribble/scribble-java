package org.scribble.lang.global;

import org.scribble.lang.SType;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;

public interface GType extends SType<Global>
{
	@Override
	GType substitute(Substitutions<Role> subs);

	@Override
	GType getInlined(STypeInliner i);//, Deque<SubprotoSig> stack);

	@Override
	default SType<Global> unfoldAllOnce(
			STypeUnfolder<Global> u)
	{
		throw new RuntimeException("Not supported for: " + this);
	}
}
