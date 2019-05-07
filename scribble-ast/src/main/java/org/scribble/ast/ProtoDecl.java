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

import java.util.List;

import org.antlr.runtime.Token;
import org.scribble.core.type.kind.ProtoKind;
import org.scribble.core.type.name.Role;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

// CHECKME: visitChildren for modifiers
public abstract class ProtoDecl<K extends ProtoKind> extends ScribNodeBase
		implements ModuleMember, ProtoKindNode<K>
{
	public static final int MODLIST_CHILD = 0;
	public static final int HEADER_CHILD = 1;
	public static final int DEF_CHILD = 2;

	// ScribTreeAdaptor#create constructor
	protected ProtoDecl(Token t)
	{
		super(t);
	}
	
	// Tree#dupNode constructor
	protected ProtoDecl(ProtoDecl<K> node)
	{
		super(node);
	}
	
	public ProtoModList getModifierListChild()
	{
		return (ProtoModList) getChild(MODLIST_CHILD);
	}

	// Implement in subclasses to avoid generic cast
	public abstract ProtoHeader<K> getHeaderChild();
	public abstract ProtoDef<K> getDefChild();

	// "add", not "set"
	public void addScribChildren(ProtoModList mods, ProtoHeader<K> header,
			ProtoDef<K> def)
	{
		// Cf. above getters and Scribble.g children order
		addChild(mods);
		addChild(header);
		addChild(def);
	}

	public boolean isAux()
	{
		return getModifierListChild().hasAux();
	}

	public boolean isExplicit()
	{
		return getModifierListChild().hasExplicit();
	}
	
	public abstract ProtoDecl<K> dupNode();

	public ProtoDecl<K> reconstruct(ProtoModList mods, ProtoHeader<K> header,
			ProtoDef<K> def)
	{
		ProtoDecl<K> dup = dupNode();
		dup.addScribChildren(mods, header, def);
		dup.setDel(del());  // No copy
		return dup;
	}

	@Override
	public ProtoDecl<K> visitChildren(AstVisitor v) throws ScribException
	{
		ProtoModList mods = visitChildWithClassEqualityCheck(this,
				getModifierListChild(), v);
		ProtoHeader<K> header = 
				visitChildWithClassEqualityCheck(this, getHeaderChild(), v);
		ProtoDef<K> def = visitChildWithClassEqualityCheck(this, getDefChild(), v);
		return reconstruct(mods, header, def);
	}
	
	public List<Role> getRoles()
	{
		// WF disallows unused role declarations
		return getHeaderChild().getRoleDeclListChild().getRoles();
	}
	
	@Override
	public String toString()
	{
		ProtoModList mods = getModifierListChild();
		return (mods.isEmpty() ? "" : mods + " ") + getHeaderChild() + " "
				+ getDefChild();
	}
}
