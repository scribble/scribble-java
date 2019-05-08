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

import org.antlr.runtime.Token;
import org.scribble.ast.name.qualified.ProtoNameNode;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.type.kind.ProtoKind;
import org.scribble.core.type.name.ProtoName;
import org.scribble.core.type.name.Role;
import org.scribble.job.JobContext;
import org.scribble.util.Constants;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

public abstract class Do<K extends ProtoKind>
		extends SimpleSessionNode<K> // implements ScopedNode
{
	public static final int NAME_CHILD_INDEX = 0;
	public static final int ARG_LIST_CHILD_INDEX = 1;
	public static final int ROLE_LIST_CHILD_INDEX = 2;
	
	// ScribTreeAdaptor#create constructor
	public Do(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public Do(Do<K> node)
	{
		super(node);
	}
	
	public abstract ProtoNameNode<K> getProtocolNameNode();

	public NonRoleArgList getNonRoleListChild()
	{
		return (NonRoleArgList) getChild(ARG_LIST_CHILD_INDEX);
	}
	
	// CHECKME: maybe wrap up role args and non-role args within the same container?
	public RoleArgList getRoleListChild()
	{
		return (RoleArgList) getChild(ROLE_LIST_CHILD_INDEX);
	}

	// "add", not "set"
	public void addScribChildren(ProtoNameNode<K> proto, NonRoleArgList as,
			RoleArgList rs)
	{
		// Cf. above getters and Scribble.g children order
		addChild(proto);  // Order re. getter indices
		addChild(as);
		addChild(rs);
	}
	
	public abstract Do<K> dupNode();

	public Do<K> reconstruct(ProtoNameNode<K> proto, NonRoleArgList as,
			RoleArgList rs)
	{
		Do<K> dup = dupNode();
		dup.addScribChildren(proto, as, rs);
		dup.setDel(del());  // No copy
		return dup;
	}

	@Override
	public Do<K> visitChildren(AstVisitor nv) throws ScribException
	{
		RoleArgList ril = (RoleArgList) visitChild(getRoleListChild(), nv);
		NonRoleArgList al = (NonRoleArgList) visitChild(getNonRoleListChild(), nv);
		ProtoNameNode<K> proto = visitChildWithClassEqualityCheck(this,
				getProtocolNameNode(), nv);
		return reconstruct(proto, al, ril);
	}

	// FIXME: mcontext now redundant because NameDisambiguator converts all targets to full names -- NO: currently disamb doesn't
	// To get full name from original target name, use mcontext visible names (e.g. in or before name disambiguation pass)
	// This is still useful for subclass casting to G/LProtocolName
	public ProtoName<K> getTargetProtoDeclFullName(ModuleContext mcontext)
	{
		//return mcontext.checkProtocolDeclDependencyFullName(this.proto.toName());
		return getProtocolNameNode().toName();  // Pre: use after name disambiguation (maybe drop FullName suffix)
	}
	
	// CHECKME: mcontext redundant, because redundant for getTargetProtocolDeclFullName
	public abstract ProtoDecl<K> getTargetProtocolDecl(JobContext jcontext,
			ModuleContext mcontext);
	
	public Role getTargetRoleParameter(JobContext jcontext,
			ModuleContext mcontext, Role role)
	{
		Iterator<Role> args = getRoleListChild().getRoles().iterator();
		Iterator<Role> params = getTargetProtocolDecl(jcontext, mcontext)
				.getHeaderChild().getRoleDeclListChild().getRoles().iterator();
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

	@Override
	public String toString()
	{
		String s = Constants.DO_KW + " ";
		return s + getProtocolNameNode() + getNonRoleListChild() + getRoleListChild()
				+ ";";
	}
}
