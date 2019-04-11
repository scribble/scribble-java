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

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GDelegationElem;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.GProtocolName;
import org.scribble.core.type.name.Role;
import org.scribble.del.ScribDelBase;
import org.scribble.util.ScribException;
import org.scribble.visit.NameDisambiguator;

public class GDelegationElemDel extends ScribDelBase
{
	public GDelegationElemDel()
	{
	
	}

	// Duplicated from DoDel
	@Override
	public void enterDisambiguation(ScribNode child,
			NameDisambiguator disamb) throws ScribException
	{
		ModuleContext mc = disamb.getModuleContext();
		GDelegationElem de = (GDelegationElem) child;
		GProtocolNameNode proto = de.getProtocolChild();
		GProtocolName gpn = proto.toName();
		if (!mc.isVisibleProtocolDeclName(gpn))
		{
			throw new ScribException(proto.getSource(),
					"Protocol decl not visible: " + gpn);
		}
	}

	// Duplicated from DoDel
	//@Override
	public GDelegationElem visitForNameDisambiguation(NameDisambiguator disamb,  // CHECKME: why "visitFor" pattern?
			GDelegationElem de) throws ScribException
	{
		ModuleContext mc = disamb.getModuleContext();
		GProtocolName fullname = (GProtocolName) mc
				.getVisibleProtocolDeclFullName(de.getProtocolChild().toName());
		RoleNode r = de.getRoleChild();

		Role rn = r.toName();
		ProtocolDecl<Global> gpd = disamb.lang.getContext()
				.getModule(fullname.getPrefix())
				.getGProtocolDeclChild(fullname.getSimpleName());
		if (!gpd.getHeaderChild().getRoleDeclListChild().getRoles().contains(rn))
		{
			throw new ScribException(r.getSource(), "Invalid delegation role: " + de);
		}

		GProtocolNameNode pnn = (GProtocolNameNode) disamb.lang.config.af
				.QualifiedNameNode(de.getProtocolChild().getSource(),
						fullname.getKind(), fullname.getElements());
				// Not keeping original namenode del
		return de.reconstruct(pnn, r);
	}
}
