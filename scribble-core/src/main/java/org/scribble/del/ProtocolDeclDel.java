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

import java.util.Set;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ProtocolDeclContext;
import org.scribble.main.ScribbleException;
import org.scribble.type.SubprotocolSig;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.MemberName;
import org.scribble.type.name.ProtocolName;
import org.scribble.type.name.Role;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.context.ProtocolDeclContextBuilder;
import org.scribble.visit.util.RoleCollector;
import org.scribble.visit.wf.NameDisambiguator;

public abstract class ProtocolDeclDel<K extends ProtocolKind> extends ScribDelBase
{
	private ProtocolDeclContext<K> pdcontext;

	protected ProtocolDeclDel()
	{

	}
	
	protected abstract ProtocolDeclDel<K> copy();
		
	@Override
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		disamb.clear();
		return visited;
	}

	@Override
	public void enterProtocolDeclContextBuilding(ScribNode parent, ScribNode child, ProtocolDeclContextBuilder builder) throws ScribbleException
	{
		builder.clearProtocolDependencies();  // collect per protocoldecl all together, do not clear?

		Module main = (Module) parent;
		ProtocolDecl<?> pd = (ProtocolDecl<?>) child;
		MemberName<?> pn = pd.getFullMemberName(main);
		// Is it really needed to add self protocoldecl dependencies?
		pd.header.roledecls.getRoles().stream().forEach((r) -> addSelfDependency(builder, (ProtocolName<?>) pn, r));
	}
	
	protected abstract void addSelfDependency(ProtocolDeclContextBuilder builder, ProtocolName<?> proto, Role role);

	@Override
	public void enterProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl) throws ScribbleException
	{
		SubprotocolSig subsig = inl.peekStack();  // SubprotocolVisitor has already entered subprotocol
		inl.setSubprotocolRecVar(subsig);
	}

	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	@Override
	public ScribNode leaveRoleCollection(ScribNode parent, ScribNode child, RoleCollector coll, ScribNode visited) throws ScribbleException
	{
		ProtocolDecl<?> pd = (ProtocolDecl<?>) visited;
		Set<Role> occs = coll.getNames();
		// Needs ProtocolDeclContextBuilder to have built the context already
		ProtocolDeclDel<K> del = setProtocolDeclContext(getProtocolDeclContext().setRoleOccurrences(occs));
		return pd.del(del);
	}
	
	public ProtocolDeclContext<K> getProtocolDeclContext()
	{
		return this.pdcontext;
	}
	
	protected ProtocolDeclDel<K> setProtocolDeclContext(ProtocolDeclContext<K> pdcontext)
	{
		ProtocolDeclDel<K> copy = copy();  // FIXME: should be a deep clone in principle -- but if any other children are immutable, they can be shared
		copy.pdcontext = pdcontext;
		return copy;
	}
}
