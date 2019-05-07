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

public class GTypeFactoryImpl implements GTypeFactory
{

	@Override
	public GChoice GChoice(CommonTree source, Role subj, List<GSeq> blocks)
	{
		return new GChoice(source, subj, blocks);
	}

	@Override
	public GConnect GConnect(CommonTree source, Role src, Msg msg, Role dst)
	{
		return new GConnect(source, src, msg, dst);
	}

	@Override
	public GContinue GContinue(CommonTree source, RecVar recvar)
	{
		return new GContinue(source, recvar);
	}

	@Override
	public GDisconnect GDisconnect(CommonTree source, Role left, Role right)
	{
		return new GDisconnect(source, left, right);
	}	

	@Override
	public GDo GDo(CommonTree source, ProtoName<Global> proto, List<Role> roles, 
			List<Arg<? extends NonRoleParamKind>> args)
	{
		return new GDo(source, proto, roles, args);
	}

	@Override
	public GMessageTransfer GMessageTransfer(CommonTree source, Role src, Msg msg,
			Role dst)
	{
		return new GMessageTransfer(source, src, msg, dst);
	}

	@Override
	public GRecursion GRecursion(CommonTree source, RecVar recvar, GSeq body)
	{
		return new GRecursion(source, recvar, body);
	}

	@Override
	public GSeq GSeq(CommonTree source, List<GType> elems)
	{
		return new GSeq(source, elems);
	}
}
