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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ProtocolDef;
import org.scribble.ast.ProtocolHeader;
import org.scribble.ast.ScribNodeBase;
import org.scribble.del.ScribDel;
import org.scribble.type.kind.Local;
import org.scribble.type.name.LProtocolName;
import org.scribble.type.name.ModuleName;
import org.scribble.type.name.Role;

// Maybe make abstract with concrete parsed (LParsedDecl) and projected (LProjectionDecl) subclasses
public class LProtocolDecl extends ProtocolDecl<Local> implements LNode
{
	public LProtocolDecl(CommonTree source, List<Modifiers> modifiers, LProtocolHeader header, LProtocolDef def)
	{
		super(source, modifiers, header, def);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LProtocolDecl(this.source, this.modifiers, getHeader(), getDef());
	}
	
	@Override
	public LProtocolDecl clone(AstFactory af)
	{
		LProtocolHeader header = getHeader().clone(af);
		LProtocolDef def = getDef().clone(af);
		return af.LProtocolDecl(this.source, this.modifiers, header, def);
	}
	
	@Override
	public LProtocolDecl reconstruct(ProtocolHeader<Local> header, ProtocolDef<Local> def)
	{
		ScribDel del = del();
		LProtocolDecl lpd = new LProtocolDecl(this.source, this.modifiers, (LProtocolHeader) header, (LProtocolDef) def);
		lpd = (LProtocolDecl) lpd.del(del);
		return lpd;
	}

	@Override
	public LProtocolHeader getHeader()
	{
		return (LProtocolHeader) this.header;
	}

	@Override
	public LProtocolDef getDef()
	{
		return (LProtocolDef) this.def;
	}

	@Override
	public LProtocolName getFullMemberName(Module mod)
	{
		ModuleName fullmodname = mod.getFullModuleName();
		return new LProtocolName(fullmodname, this.header.getDeclName());
	}
	
	public Role getSelfRole()
	{
		return getHeader().getSelfRole();
	}
}
