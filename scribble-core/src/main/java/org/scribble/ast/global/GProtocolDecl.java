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
package org.scribble.ast.global;

import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Module;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ProtocolDef;
import org.scribble.ast.ProtocolHeader;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.ast.local.LProtocolDef;
import org.scribble.ast.local.LProtocolHeader;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.Projector;

public class GProtocolDecl extends ProtocolDecl<Global> implements GNode
{
	public GProtocolDecl(CommonTree source, List<Modifiers> modifiers, GProtocolHeader header, GProtocolDef def)
	{
		super(source, modifiers, header, def);
	}
	
	// FIXME? project modifiers?
	public LProtocolDecl project(Module mod, Role self, LProtocolDef def) throws ScribbleException  // mod is just the parent?
	{
		//Role self = proj.peekSelf();
		GProtocolHeader gph = getHeader();
		LProtocolNameNode pn = Projector.makeProjectedSimpleNameNode(gph.getSource(), gph.getDeclName(), self);
		
		// Move to delegates? -- maybe fully integrate into projection pass
		RoleDeclList roledecls = this.header.roledecls.project(self);
		NonRoleParamDeclList paramdecls = this.header.paramdecls.project(self);
		LProtocolHeader lph = AstFactoryImpl.FACTORY.LProtocolHeader(this.header.getSource(), pn, roledecls, paramdecls);
		GProtocolName gpn = this.getFullMemberName(mod);
		LProtocolDecl projected = AstFactoryImpl.FACTORY.LProjectionDecl(this.source, this.modifiers, gpn, self, lph, def);
		return projected;
	}

	@Override
	protected GProtocolDecl copy()
	{
		return new GProtocolDecl(this.source, this.modifiers, getHeader(), getDef());
	}
	
	@Override
	public GProtocolDecl clone()
	{
		GProtocolHeader header = getHeader().clone();
		GProtocolDef def = getDef().clone();
		return AstFactoryImpl.FACTORY.GProtocolDecl(this.source, this.modifiers, header, def);
	}

	@Override
	public GProtocolDecl reconstruct(ProtocolHeader<Global> header, ProtocolDef<Global> def)
	{
		
		ScribDel del = del();
		GProtocolDecl gpd = new GProtocolDecl(this.source, this.modifiers, (GProtocolHeader) header, (GProtocolDef) def);
		gpd = (GProtocolDecl) gpd.del(del);  // FIXME: does another shallow copy
		return gpd;
	}

	@Override
	public GProtocolHeader getHeader()
	{
		return (GProtocolHeader) this.header;
	}

	@Override
	public GProtocolDef getDef()
	{
		return (GProtocolDef) this.def;
	}

	@Override
	public GProtocolName getFullMemberName(Module mod)
	{
		ModuleName fullmodname = mod.getFullModuleName();
		return new GProtocolName(fullmodname, this.header.getDeclName());
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public boolean isGlobal()
	{
		return GNode.super.isGlobal();
	}
	
	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Global getKind()
	{
		return GNode.super.getKind();
	}
}
