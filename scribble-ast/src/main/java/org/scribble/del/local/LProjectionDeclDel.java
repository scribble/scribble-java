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

public class LProjectionDeclDel extends LProtoDeclDel
{
	public LProjectionDeclDel()
	{

	}

	/*@Override
	public ScribNode leaveProjectedRoleDeclFixing(ScribNode parent,
			ScribNode child, ProjectedRoleDeclFixer fixer, ScribNode visited)
			throws ScribException
	{
		LProjectionDecl lpd = (LProjectionDecl) visited;
		LProtoHeader hdrtmp = lpd.getHeaderChild();
		// FIXME: ensure all role params are used, to avoid empty roledecllist
		Set<Role> occs = ((LProtoDeclDel) lpd.del()).getProtoDeclContext()
				.getRoleOccurrences();
		List<RoleDecl> rds = hdrtmp.getRoleDeclListChild().getDeclChildren()
				.stream().filter(rd -> occs.contains(rd.getDeclName()))
				.collect(Collectors.toList());
		RoleDeclList rdl = fixer.job.af
				.RoleDeclList(hdrtmp.getRoleDeclListChild().getSource(), rds);
		LProtoHeader hdr = (LProtoHeader) hdrtmp.reconstruct(hdrtmp.getNameNodeChild(),
				hdrtmp.getParamDeclListChild(), rdl);
		LProjectionDecl fixed = (LProjectionDecl) lpd.reconstruct(lpd.getModifierListChild(), hdr, lpd.getDefChild());
		
		fixer.job.debugPrintln("\n[DEBUG] Projected " + lpd//.getParentChild()
				+ " for " + lpd.getSelfRole() + ":\n" + fixed);
		
		return super.leaveProjectedRoleDeclFixing(parent, child, fixer, fixed);
	}*/
}
