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
package org.scribble.ext.go.del.global;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.global.GDelegationElem;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.global.GDelegationElemDel;
import org.scribble.ext.go.ast.global.RPGDelegationElem;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;
import org.scribble.visit.AstVisitor;
import org.scribble.visit.wf.NameDisambiguator;

public class RPGDelegationElemDel extends GDelegationElemDel
{
	public RPGDelegationElemDel()
	{
	
	}
	
	// Duplicated from super
	@Override
	public RPGDelegationElem visitForNameDisambiguation(NameDisambiguator disamb, GDelegationElem de) throws ScribbleException
	{
		ModuleContext mc = disamb.getModuleContext();
		GProtocolName fullname = (GProtocolName) mc.getVisibleProtocolDeclFullName(de.proto.toName());

		Role rn = de.role.toName();
		ProtocolDecl<Global> gpd = disamb.job.getContext().getModule(fullname.getPrefix()).getProtocolDecl(fullname.getSimpleName());
		if (!gpd.header.roledecls.getRoles().contains(rn))
		{
			String tmp = rn.toString();  // FIXME HACK (currently rely on checking valid variant later)
			if (tmp.indexOf("_") == -1)
			{
				throw new ScribbleException(de.role.getSource(), "Invalid delegation role: " + de);
			}
		}

		RPGDelegationElem rpde = (RPGDelegationElem) de;
		GProtocolNameNode root = (GProtocolNameNode) disamb.job.af.QualifiedNameNode(rpde.proto.getSource(), fullname.getKind(), fullname.getElements());  // Not keeping original namenode del
		GProtocolNameNode state = (GProtocolNameNode) disamb.job.af.QualifiedNameNode(rpde.state.getSource(), fullname.getKind(), fullname.getElements());  // Not keeping original namenode del
		return (RPGDelegationElem) rpde.reconstruct(root, state, de.role);
	}
}
