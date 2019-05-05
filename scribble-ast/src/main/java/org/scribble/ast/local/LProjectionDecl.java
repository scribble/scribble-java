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
package org.scribble.ast.local;

import org.antlr.runtime.Token;
import org.scribble.ast.ProtoDef;
import org.scribble.ast.ProtoHeader;
import org.scribble.ast.ProtoModList;
import org.scribble.ast.name.qualified.GProtoNameNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.type.kind.Local;
import org.scribble.del.DelFactory;

public class LProjectionDecl extends LProtoDecl
{
	// Continues on from ProtoDecl, cf. ProtoDecl.DEF_CHILD
	public static final int GLOBAL_CHILD = 3;  // Cf. "parent", confusing with Antlr
	public static final int SELF_CHILD = 4;

	//public final GProtoName parent;  // FIXME: additional ast child?

	// ScribTreeAdaptor#create constructor
	public LProjectionDecl(Token t)
	{
		super(t);
	}
	
	// Tree#dupNode constructor
	protected LProjectionDecl(LProtoDecl node)
	{
		super(node);
	}

	public GProtoNameNode getGlobalChild()
	{
		return (GProtoNameNode) getChild(GLOBAL_CHILD);
	}

	public RoleNode getSelfChild()
	{
		return (RoleNode) getChild(SELF_CHILD);
	}

	// "add", not "set"
	public void addScribChildren(ProtoModList mods, ProtoHeader<Local> header,
			ProtoDef<Local> def, GProtoNameNode global, RoleNode self)
	{
		// Cf. above getters and Scribble.g children order
		super.addScribChildren(mods, header, def);
		addChild(global);
		addChild(self);
	}

	// Cf. CommonTree#dupNode
	@Override
	public LProtoDecl dupNode()
	{
		return new LProtoDecl(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.LProjectionDecl(this);
	}
	
	@Override
	public String toString()
	{
		// Duplicated from ProtoDecl
		ProtoModList mods = getModifierListChild();
		return (mods.isEmpty() ? "" : mods + " ") + getHeaderChild() + " "  // FIXME: self role decl (cf. LProtocol.rolesToString)
				+ "projects " +  getGlobalChild() + " "  // Cf. core.lang.local.LProjection
				+ getDefChild();
	}
}
