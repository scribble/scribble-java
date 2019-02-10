package org.scribble.lang.global;

import java.util.Deque;

import org.scribble.lang.SessType;
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
}
