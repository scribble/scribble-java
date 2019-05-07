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
package org.scribble.del.global;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtoDecl;
import org.scribble.core.lang.ProtoMod;
import org.scribble.core.lang.global.GProtocol;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.del.ProtoDeclDel;
import org.scribble.visit.GTypeTranslator;

public class GProtoDeclDel extends ProtoDeclDel<Global> implements GDel
{
	public GProtoDeclDel()
	{

	}
	
	@Override
	public GProtocol translate(ScribNode n, GTypeTranslator t) 
	{
		GProtoDecl source = (GProtoDecl) n;
		Module m = (Module) n.getParent();
		List<ProtoMod> mods = source.getModifierListChild().getModList().stream()
				.map(x -> x.toProtoMod()).collect(Collectors.toList());
		GProtoName fullname = new GProtoName(m.getFullModuleName(),
				source.getHeaderChild().getDeclName());
		List<Role> rs = source.getRoles();
		List<MemberName<? extends NonRoleParamKind>> ps = source.getHeaderChild()
				.getParamDeclListChild().getParams();  // CHECKME: make more uniform with source::getRoles ?
		GSeq body = (GSeq) source.getDefChild().getBlockChild().visitWith(t);
		return new GProtocol(source, mods, fullname, rs, ps, body);
	}
}
