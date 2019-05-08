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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.ProtoDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GDelegPayElem;
import org.scribble.ast.name.qualified.GProtoNameNode;
import org.scribble.ast.name.simple.IdNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.GProtoName;
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
		GDelegPayElem de = (GDelegPayElem) child;
		GProtoNameNode proto = de.getProtocolChild();
		
		System.out.println("aaa: " + proto);
		
		GProtoName gpn = proto.toName();
		if (!mc.isVisibleProtocolDeclName(gpn))
		{
			throw new ScribException(proto.getSource(),
					"Protocol decl not visible: " + gpn);
		}
	}

	// Duplicated from DoDel
	//@Override
	public GDelegPayElem visitForNameDisambiguation(NameDisambiguator disamb,  // CHECKME: why "visitFor" pattern?
			GDelegPayElem deleg) throws ScribException
	{
		ModuleContext mc = disamb.getModuleContext();
		GProtoNameNode proto = deleg.getProtocolChild();
		GProtoName fullname = (GProtoName) mc
				.getVisibleProtocolDeclFullName(proto.toName());
		RoleNode r = deleg.getRoleChild();

		Role rn = r.toName();
		ProtoDecl<Global> gpd = disamb.job.getContext()
				.getModule(fullname.getPrefix())
				.getGProtocolDeclChild(fullname.getSimpleName());
		if (!gpd.getHeaderChild().getRoleDeclListChild().getRoles().contains(rn))
		{
			throw new ScribException(r.getSource(), "Invalid delegation role: " + deleg);
		}
		List<IdNode> elems = Arrays.asList(fullname.getElements()).stream()
				.map(x -> disamb.job.config.af.IdNode(null, x)).collect(Collectors.toList());
		GProtoNameNode pnn = (GProtoNameNode) disamb.job.config.af
				.GProtoNameNode(proto.token, elems);
				// Not keeping original namenode del
		return deleg.reconstruct(pnn, r);
	}
}
