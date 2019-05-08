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

import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GDo;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Arg;
import org.scribble.del.DoDel;
import org.scribble.util.RuntimeScribException;
import org.scribble.visit.GTypeTranslator;

public class GDoDel extends DoDel implements GSimpleSessionNodeDel
{
	@Override
	public org.scribble.core.type.session.global.GDo translate(ScribNode n,
			GTypeTranslator t)
	{
		GDo source = (GDo) n;

		// Resolve full name -- CHECKME: factor out? cf., NameDisambiguator, DoDel::enter/leaveDisambiguation
		GProtoName proto = source.getProtocolNameNode().toName();
		ModuleContext modc = t.getModuleContext();
		if (!modc.isVisibleProtocolDeclName(proto))  // CHECKME: should be already checked by NameDisamb?
		{
			throw new RuntimeScribException(source,
					"Protocol decl not visible: " + proto);
		}
		GProtoName fullname = (GProtoName) modc  // FIXME: sort out full name expansion between here and DoDel.leaveDisambiguation
				.getVisibleProtocolDeclFullName(proto);
		List<Role> roles = source.getRoleListChild().getRoles();
		List<Arg<? extends NonRoleParamKind>> params = source.getNonRoleListChild()
				.getParamKindArgs();		
		return t.tf.global.GDo(source, fullname, roles, params);
	}
}
