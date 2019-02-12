package org.scribble.lang.global;

import java.util.Deque;

import org.scribble.lang.Seq;
import org.scribble.lang.SessType;
import org.scribble.lang.SessTypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;

public interface GType extends SessType<Global>
{
	@Override
	GType substitute(Substitutions<Role> subs);

	@Override
	GType getInlined(GTypeTranslator t, Deque<SubprotoSig> stack);

	@Override
	default SessType<Global> unfoldAllOnce(SessTypeUnfolder<Global, ? extends Seq<Global>> u)
	{
		throw new RuntimeException("Not supported for: " + this);
	}
}
