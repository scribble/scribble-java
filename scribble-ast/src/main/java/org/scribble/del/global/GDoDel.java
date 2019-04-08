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
import org.scribble.core.type.name.GProtocolName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Arg;
import org.scribble.del.DoDel;
import org.scribble.util.ScribException;
import org.scribble.visit.GTypeTranslator;

public class GDoDel extends DoDel implements GSimpleSessionNodeDel
{
	@Override
	public org.scribble.core.type.session.global.GDo translate(ScribNode n,
			GTypeTranslator t) throws ScribException
	{
		GDo source = (GDo) n;

		// Resolve full name -- CHECKME: factor out? cf., NameDisambiguator, DoDel::enter/leaveDisambiguation
		GProtocolName proto = source.getProtocolNameNode().toName();
		ModuleContext modc = t.getModuleContext();
		if (!modc.isVisibleProtocolDeclName(proto))
		{
			throw new ScribException(source,
					"Protocol decl not visible: " + proto);
		}
		GProtocolName fullname = (GProtocolName) modc
				.getVisibleProtocolDeclFullName(proto);
		List<Role> roles = source.getRoleListChild().getRoles();
		List<Arg<? extends NonRoleParamKind>> params = source.getNonRoleListChild()
				.getParamKindArgs();		
		return new org.scribble.core.type.session.global.GDo(source, fullname,
				roles, params);
	}
}
