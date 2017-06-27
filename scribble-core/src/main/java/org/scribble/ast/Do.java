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
package org.scribble.ast;

import java.util.Iterator;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.name.qualified.ProtocolNameNode;
import org.scribble.main.JobContext;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.AstVisitor;

public abstract class Do<K extends ProtocolKind> extends SimpleInteractionNode<K> //implements ScopedNode
{
	//public final ScopeNode scope;
	public final RoleArgList roles;
	public final NonRoleArgList args;
	public final ProtocolNameNode<K> proto;  // Maybe use an "Ambiguous" version until names resolved -- is a visible protocol, but not necessarily a simple or full member name

	protected Do(CommonTree source, RoleArgList roleinstans, NonRoleArgList arginstans, ProtocolNameNode<K> proto)
	{
		super(source);
		//this.scope = scope;
		this.roles = roleinstans;
		this.args = arginstans;
		this.proto = proto;
	}

	public abstract Do<K> reconstruct(RoleArgList roleinstans, NonRoleArgList arginstans, ProtocolNameNode<K> proto);
	
	public abstract ProtocolNameNode<K> getProtocolNameNode();

	@Override
	public Do<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		//ScopeNode scope = isScoped() ? (ScopeNode) visitChild(this.scope, nv) : null;
		RoleArgList ril = (RoleArgList) visitChild(this.roles, nv);
		NonRoleArgList al = (NonRoleArgList) visitChild(this.args, nv);
		ProtocolNameNode<K> proto = visitChildWithClassEqualityCheck(this, this.proto, nv);
		return reconstruct(ril, al, proto);
	}

	// FIXME: mcontext now redundant because NameDisambiguator converts all targets to full names
	// To get full name from original target name, use mcontext visible names (e.g. in or before name disambiguation pass)
	// This is still useful for subclass casting to G/LProtocolName
	public ProtocolName<K> getTargetProtocolDeclFullName(ModuleContext mcontext)
	{
		//return mcontext.checkProtocolDeclDependencyFullName(this.proto.toName());
		return this.proto.toName();  // Pre: use after name disambiguation (maybe drop FullName suffix)
	}
	
	// mcontext redundant because redundant for getTargetProtocolDeclFullName
	public ProtocolDecl<K> getTargetProtocolDecl(JobContext jcontext, ModuleContext mcontext)
	{
		ProtocolName<K> fullname = getTargetProtocolDeclFullName(mcontext);
		return jcontext.getModule(fullname.getPrefix()).getProtocolDecl(fullname.getSimpleName());
	}
	
	public Role getTargetRoleParameter(JobContext jcontext, ModuleContext mcontext, Role role)
	{
		Iterator<Role> args = this.roles.getRoles().iterator();
		Iterator<Role> params = getTargetProtocolDecl(jcontext, mcontext).header.roledecls.getRoles().iterator();
		while (args.hasNext())
		{
			Role arg = args.next();
			Role param = params.next();
			if (arg.equals(role))
			{
				return param;
			}
		}
		throw new RuntimeException("Not an argument role: " + role);
	}
	
	/*@Override
	public boolean isEmptyScope()
	{
		return this.scope == null;
	}

	@Override
	//public Scope getScope()
	public SimpleName getScopeElement()
	{
		return this.scope.toName();
	}
	
	public boolean isScoped()
	{
		return this.scope != null;
	}*/

	@Override
	public String toString()
	{
		String s = Constants.DO_KW + " ";
		//if (!hasEmptyScopeNode())
		/*if (isScoped())
		{
			s += this.scope + ":";
		}*/
		return s + this.proto + this.args + this.roles + ";";
	}
}
