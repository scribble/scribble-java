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
import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.Role;
import org.scribble.del.ScribDel;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

// CHECKME: visitChildren for modifiers
public abstract class ProtoDecl<K extends ProtocolKind> extends ScribNodeBase
		implements ModuleMember, ProtocolKindNode<K>
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
	
	public abstract ProtoDecl<K> dupNode();

	public boolean isAux()
	{
		return getModifierListChild().hasAux();
	}

	public boolean isExplicit()
	{
		return getModifierListChild().hasExplicit();
	}
	
	public ProtoModList getModifierListChild()
	{
		return (ProtoModList) getChild(MODLIST_CHILD);
	}

	// Implement in subclasses to avoid generic cast
	public abstract ProtocolHeader<K> getHeaderChild();
	public abstract ProtocolDef<K> getDefChild();
	
	public List<Role> getRoles()
	{
		// WF disallows unused role declarations
		return getHeaderChild().getRoleDeclListChild().getRoles();
	}

	public ProtoDecl<K> reconstruct(ProtoModList mods,
			ProtocolHeader<K> header,	ProtocolDef<K> def)  
			//, ProtocolDeclContext pdcontext, Env env);
	{
		ProtoDecl<K> pd = dupNode();
		ScribDel del = del();
		pd.addChild(mods);
		pd.addChild(header);
		pd.addChild(def);
		pd.setDel(del);  // No copy
		return pd;
	}

	@Override
	public ProtoDecl<K> visitChildren(AstVisitor nv) throws ScribException
	{
		ProtoModList mods = visitChildWithClassEqualityCheck(this,
				getModifierListChild(), nv);
		ProtocolHeader<K> header = 
				visitChildWithClassEqualityCheck(this, getHeaderChild(), nv);
		ProtocolDef<K> def = visitChildWithClassEqualityCheck(this, getDefChild(), nv);
		return reconstruct(mods, header, def);
	}
	
	@Override
	public String toString()
	{
		ProtoModList mods = getModifierListChild();
		return (mods.isEmpty() ? "" : mods + " ") + getHeaderChild() + " "
				+ getDefChild();
	}
}
