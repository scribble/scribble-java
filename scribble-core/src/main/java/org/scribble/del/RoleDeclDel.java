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
package org.scribble.del;

import org.scribble.ast.RoleDecl;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.wf.NameDisambiguator;
import org.scribble.visit.wf.WFChoiceChecker;

public class RoleDeclDel extends ScribDelBase
{
	@Override
	public void enterDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb) throws ScribbleException
	{
		RoleDecl rd = (RoleDecl) child;
		disamb.addRole(rd.getDeclName());  // Could check distinct here, but doing it uniformly in HeaderParamDeclListDel
	}

	@Override
	public RoleDecl leaveInlinedWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		RoleDecl rd = (RoleDecl) visited;
		Role role = rd.getDeclName();
		// enabled even for explicit connection protocols: otherwise no way to bootstrap initial connection(s)
		checker.pushEnv(checker.popEnv().enableRoleForRootProtocolDecl(role));
		return (RoleDecl) super.leaveInlinedWFChoiceCheck(parent, child, checker, rd);
	}
}
