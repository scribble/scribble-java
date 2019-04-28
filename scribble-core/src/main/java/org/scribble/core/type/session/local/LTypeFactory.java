package org.scribble.core.type.session.local;

import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.name.ProtoName;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Arg;
import org.scribble.core.type.session.Msg;

public interface LTypeFactory
{

	LAcc LAcc(CommonTree source, Role src, Msg msg);

	LChoice LChoice(CommonTree source, Role subj, List<LSeq> blocks);

	LContinue LContinue(CommonTree source, RecVar recvar);

	LDisconnect LDisconnect(CommonTree source, Role peer);

	LDo LDo(CommonTree source, ProtoName<Local> proto, List<Role> roles, 
			List<Arg<? extends NonRoleParamKind>> args);

	LRecursion LRecursion(CommonTree source, RecVar recvar, LSeq body);

	LRecv LRecv(CommonTree source, Role src, Msg msg);

	LReq LReq(CommonTree source, Msg msg, Role dst);

	LSend LSend(CommonTree source, Msg msg, Role dst);

	LSeq LSeq(CommonTree source, List<LType> elems);
}
