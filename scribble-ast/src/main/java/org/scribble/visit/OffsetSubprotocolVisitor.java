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
package org.scribble.visit;

import java.util.Map;

import org.scribble.ast.NonRoleArgNode;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.type.kind.NonRoleArgKind;
import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Arg;
import org.scribble.lang.Lang;
import org.scribble.util.ScribException;
import org.scribble.visit.env.Env;

// Can probably be fully replaced by SubprotocolVisitor
// Projector, ReachabilityChecker, etc don't need to be offset visitors
public abstract class OffsetSubprotocolVisitor<T extends Env<?>>
		extends SubprotocolVisitor<T>
{
	public OffsetSubprotocolVisitor(Lang job)
	{
		super(job);
	}
	
	// Doesn't push a subprotocol signature (i.e. on root entry); only records the roles/args -- why? because sigs are based on vals (from the first do), not the root proto params? -- but it would be fine to use the params?
	@Override
	protected void enterRootProtocolDecl(ProtocolDecl<? extends ProtocolKind> pd)
	{
		Map<Role, RoleNode> rolemap = makeRootRoleSubsMap(
				pd.getHeaderChild().getRoleDeclListChild());
		Map<Arg<? extends NonRoleArgKind>, NonRoleArgNode> argmap = 
				makeRootNonRoleSubsMap(pd.getHeaderChild().getParamDeclListChild());
		this.rolemaps.push(rolemap);
		this.argmaps.push(argmap);
	}
	
	@Override
	protected final ScribNode visitForSubprotocols(ScribNode parent,
			ScribNode child) throws ScribException
	{
		return visitForOffsetSubprotocols(parent, child);
	}
	
	protected ScribNode visitForOffsetSubprotocols(ScribNode parent,
			ScribNode child) throws ScribException
	{
		return super.visitForSubprotocols(parent, child);
	}

	@Override
	protected final void envLeaveProtocolDeclOverride(ScribNode parent,
			ScribNode child, ScribNode visited) throws ScribException
	{
		this.rolemaps.pop();
		this.argmaps.pop();
	}

	@Override
	protected final void subprotocolEnter(ScribNode parent, ScribNode child)
			throws ScribException
	{
		super.subprotocolEnter(parent, child);
		offsetSubprotocolEnter(parent, child);
	}

	@Override
	protected final ScribNode subprotocolLeave(ScribNode parent, ScribNode child,
			ScribNode visited) throws ScribException
	{
		ScribNode n = offsetSubprotocolLeave(parent, child, visited);
		return super.subprotocolLeave(parent, child, n);
	}

	protected void offsetSubprotocolEnter(ScribNode parent, ScribNode child)
			throws ScribException
	{

	}

	protected ScribNode offsetSubprotocolLeave(ScribNode parent, ScribNode child,
			ScribNode visited) throws ScribException
	{
		return visited;
	}
}
