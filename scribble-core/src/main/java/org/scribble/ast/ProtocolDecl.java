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

import java.util.Collections;
import java.util.List;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.Role;
import org.scribble.visit.AstVisitor;

// FIXME: visitChildren for modifiers
public abstract class ProtocolDecl<K extends ProtocolKind> extends ScribNodeBase
		implements ModuleMember, ProtocolKindNode<K>
{
	public static enum Modifiers { EXPLICIT, AUX }  // CHECKME: factor out?  Move to Header?

	public final List<Modifiers> modifiers;

	// ScribTreeAdaptor#create constructor
	protected ProtocolDecl(Token t)
	{
		super(t);
		this.modifiers = null;
		this.header = null;
		this.def = null;
	}
	
	// Tree#dupNode constructor
	protected ProtocolDecl(ProtocolDecl<K> node)
	{
		super(node);
		this.modifiers = null;
		this.header = null;
		this.def = null;
	}
	
	public abstract ProtocolDecl<K> dupNode();

	public boolean isExplicit()
	{
		return this.modifiers.contains(Modifiers.EXPLICIT);
	}

	public boolean isAux()
	{
		return this.modifiers.contains(Modifiers.AUX);
	}
	
	// Implement in subclasses to avoid generic cast
	public abstract ProtocolHeader<K> getHeaderChild();
	public abstract ProtocolDef<K> getDefChild();

	//public abstract ProtocolName<? extends ProtocolKind> getFullProtocolName(Module mod);

	public ProtocolDecl<K> reconstruct(ProtocolHeader<K> header,
			ProtocolDef<K> def)  //, ProtocolDeclContext pdcontext, Env env);
	{
		ProtocolDecl<K> pd = dupNode();
		ScribDel del = del();
		pd.addChild(header);
		pd.addChild(def);
		pd.setDel(del);  // No copy
		return pd;
	}

	@Override
	public ProtocolDecl<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		ProtocolHeader<K> header = 
				visitChildWithClassEqualityCheck(this, getHeaderChild(), nv);
		ProtocolDef<K> def = visitChildWithClassEqualityCheck(this, getDefChild(), nv);
		return reconstruct(header, def);
	}
	
	public List<Role> getRoles()
	{
		// WF disallows unused role declarations
		return getHeaderChild().getRoleDeclListChild().getRoles();
	}
	
	@Override
	public String toString()
	{
		return getHeaderChild() + " " + getDefChild();
	}
	
	
	
	
	
	
	
	
	
	

	// Maybe just use standard pattern, make private with casting getters -- works better (e.g. to use overridden getName)
	private final ProtocolHeader<K> header;
	private final ProtocolDef<K> def;

	protected ProtocolDecl(CommonTree source, List<Modifiers> modifiers,
			ProtocolHeader<K> header, ProtocolDef<K> def)
	{
		super(source);
		this.modifiers = Collections.unmodifiableList(modifiers);
		this.header = header;
		this.def = def;
	}
}
