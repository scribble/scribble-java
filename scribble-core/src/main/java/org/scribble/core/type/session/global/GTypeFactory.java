package org.scribble.core.type.session.global;

import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.name.ProtoName;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Arg;
import org.scribble.core.type.session.Msg;

public interface GTypeFactory
{

	GChoice GChoice(CommonTree source, Role subj, List<GSeq> blocks);

	GConnect GConnect(CommonTree source, Role src, Msg msg, Role dst);

	GContinue GContinue(CommonTree source, RecVar recvar);

	GDisconnect GDisconnect(CommonTree source, Role left, Role right);

	GDo GDo(CommonTree source, ProtoName<Global> proto, List<Role> roles, 
			List<Arg<? extends NonRoleParamKind>> args);

	GMessageTransfer GMessageTransfer(CommonTree source, Role src, Msg msg,
			Role dst);

	GRecursion GRecursion(CommonTree source, RecVar recvar, GSeq body);

	GSeq GSeq(CommonTree source, List<GType> elems);

}
