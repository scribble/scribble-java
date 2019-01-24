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

import java.util.List;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.type.kind.Local;
import org.scribble.type.name.LProtocolName;
import org.scribble.type.name.ModuleName;
import org.scribble.type.name.Role;

// CHECKME: maybe make abstract with concrete parsed (LParsedDecl) and projected (LProjectionDecl) subclasses
public class LProtocolDecl extends ProtocolDecl<Local> implements LNode
{
	// ScribTreeAdaptor#create constructor
	public LProtocolDecl(Token t)
	{
		super(t);
	}
	
	// Tree#dupNode constructor
	protected LProtocolDecl(LProtocolDecl node)
	{
		super(node);
	}

	// Cf. CommonTree#dupNode
	@Override
	public LProtocolDecl dupNode()
	{
		return new LProtocolDecl(this);
	}

	@Override
	public LProtocolHeader getHeaderChild()
	{
		return (LProtocolHeader) getChild(0);
	}

	@Override
	public LProtocolDef getDefChild()
	{
		return (LProtocolDef) getChild(1);
	}

	@Override
	public LProtocolName getFullMemberName(Module mod)
	{
		ModuleName fullmodname = mod.getFullModuleName();
		return new LProtocolName(fullmodname, getHeaderChild().getDeclName());
	}
	
	public Role getSelfRole()
	{
		return getHeaderChild().getSelfRole();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public LProtocolDecl(CommonTree source, List<Modifiers> modifiers, LProtocolHeader header, LProtocolDef def)
	{
		super(source, modifiers, header, def);
	}

	/*@Override
	protected ScribNodeBase copy()
	{
		return new LProtocolDecl(this.source, this.modifiers, getHeaderChild(), getDefChild());
	}
	
	@Override
	public LProtocolDecl clone(AstFactory af)
	{
		LProtocolHeader header = getHeaderChild().clone(af);
		LProtocolDef def = getDefChild().clone(af);
		return af.LProtocolDecl(this.source, this.modifiers, header, def);
	}
	
	@Override
	public LProtocolDecl reconstruct(ProtocolHeader<Local> header, ProtocolDef<Local> def)
	{
		ScribDel del = del();
		LProtocolDecl lpd = new LProtocolDecl(this.source, this.modifiers, (LProtocolHeader) header, (LProtocolDef) def);
		lpd = (LProtocolDecl) lpd.del(del);
		return lpd;
	}*/
}
