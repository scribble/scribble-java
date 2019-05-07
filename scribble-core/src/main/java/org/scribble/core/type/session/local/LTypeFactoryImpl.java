/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
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

public class LTypeFactoryImpl implements LTypeFactory
{

	@Override
	public LAcc LAcc(CommonTree source, Role src, Msg msg)
	{
		return new LAcc(source, src, msg);
	}

	@Override
	public LChoice LChoice(CommonTree source, Role subj, List<LSeq> blocks)
	{
		return new LChoice(source, subj, blocks);
	}

	@Override
	public LContinue LContinue(CommonTree source, RecVar recvar)
	{
		return new LContinue(source, recvar);
	}

	@Override
	public LDisconnect LDisconnect(CommonTree source, Role peer)
	{
		return new LDisconnect(source, peer);
	}	

	@Override
	public LDo LDo(CommonTree source, ProtoName<Local> proto, List<Role> roles,
			List<Arg<? extends NonRoleParamKind>> args)
	{
		return new LDo(source, proto, roles, args);
	}

	@Override
	public LRecursion LRecursion(CommonTree source, RecVar recvar, LSeq body)
	{
		return new LRecursion(source, recvar, body);
	}

	@Override
	public LRecv LRecv(CommonTree source, Role src, Msg msg)
	{
		return new LRecv(source, src, msg);
	}

	@Override
	public LReq LReq(CommonTree source, Msg msg, Role dst)
	{
		return new LReq(source, msg, dst);
	}

	@Override
	public LSend LSend(CommonTree source, Msg msg,
			Role dst)
	{
		return new LSend(source, msg, dst);
	}

	@Override
	public LSeq LSeq(CommonTree source, List<LType> elems)
	{
		return new LSeq(source, elems);
	}
}
