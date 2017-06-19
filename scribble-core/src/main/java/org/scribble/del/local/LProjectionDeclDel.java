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
package org.scribble.del.local;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.ast.local.LProtocolHeader;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.ProjectedRoleDeclFixer;

public class LProjectionDeclDel extends LProtocolDeclDel
{
	// Maybe better to store in context, but more convenient to pass to here via factory (than infer in context building) -- could alternatively store in projected module
	private final GProtocolName fullname;
	private final Role self;

	public LProjectionDeclDel(GProtocolName fullname, Role self)
	{
		this.fullname = fullname;
		this.self = self;
	}
	
	@Override
	protected LProtocolDeclDel copy()
	{
		return new LProjectionDeclDel(this.fullname, this.self);
	}

	@Override
	public ScribNode leaveProjectedRoleDeclFixing(ScribNode parent, ScribNode child, ProjectedRoleDeclFixer fixer, ScribNode visited) throws ScribbleException
	{
		LProtocolDecl lpd = (LProtocolDecl) visited;
		// FIXME: ensure all role params are used, to avoid empty roledecllist
		Set<Role> occs = ((LProtocolDeclDel) lpd.del()).getProtocolDeclContext().getRoleOccurrences();
		List<RoleDecl> rds = lpd.header.roledecls.getDecls().stream().filter((rd) -> 
				occs.contains(rd.getDeclName())).collect(Collectors.toList());
		RoleDeclList rdl = AstFactoryImpl.FACTORY.RoleDeclList(lpd.header.roledecls.getSource(), rds);
		LProtocolHeader header = lpd.getHeader().reconstruct(lpd.getHeader().getNameNode(), rdl, lpd.header.paramdecls);
		LProtocolDecl fixed = lpd.reconstruct(header, lpd.def);
		
		fixer.job.debugPrintln("\n[DEBUG] Projected " + getSourceProtocol() + " for " + getSelfRole() + ":\n" + fixed);
		
		return super.leaveProjectedRoleDeclFixing(parent, child, fixer, fixed);
	}
	
	public GProtocolName getSourceProtocol()
	{
		return this.fullname;
	}
	
	// Redundant with SelfRoleDecl in header
	public Role getSelfRole()
	{
		return this.self;
	}
}
