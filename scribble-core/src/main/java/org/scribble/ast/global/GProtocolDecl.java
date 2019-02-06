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

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.local.LProjectionDecl;
import org.scribble.ast.local.LProtocolDef;
import org.scribble.ast.local.LProtocolHeader;
import org.scribble.job.ScribbleException;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.ModuleName;
import org.scribble.type.name.Role;

public class GProtocolDecl extends ProtocolDecl<Global> implements GNode
{
	// ScribTreeAdaptor#create constructor
	public GProtocolDecl(Token t)
	{
		super(t);
	}
	
	// Tree#dupNode constructor
	protected GProtocolDecl(GProtocolDecl node)
	{
		super(node);
	}

	// Cf. CommonTree#dupNode
	@Override
	public GProtocolDecl dupNode()
	{
		return new GProtocolDecl(this);
	}
	
	// CHECKME: project modifiers?
	public LProjectionDecl project(AstFactory af, Module mod, Role self, LProtocolHeader hdr, LProtocolDef def) throws ScribbleException  // mod is just the parent?
	{
		/*//Role self = proj.peekSelf();
		GProtocolHeader gph = getHeader();
		LProtocolNameNode pn = Projector.makeProjectedSimpleNameNode(af, gph.getSource(), gph.getDeclName(), self);
		
		// Move to delegates? -- maybe fully integrate into projection pass
		RoleDeclList roledecls = this.header.roledecls.project(af, self);
		NonRoleParamDeclList paramdecls = this.header.paramdecls.project(af, self);
		LProtocolHeader lph = af.LProtocolHeader(this.header.getSource(), pn, roledecls, paramdecls);*/

		GProtocolName gpn = this.getFullMemberName(mod);
		LProjectionDecl proj = 
				af.LProjectionDecl(this.source, this.modifiers, gpn, self, hdr, def);
		return proj;
	}

	@Override
	public GProtocolHeader getHeaderChild()
	{
		return (GProtocolHeader) getChild(0);
	}

	@Override
	public GProtocolDef getDefChild()
	{
		return (GProtocolDef) getChild(1);
	}

	@Override
	public GProtocolName getFullMemberName(Module mod)
	{
		ModuleName fullmodname = mod.getFullModuleName();
		return new GProtocolName(fullmodname, getHeaderChild().getDeclName());
	}

	/*// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
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
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public GProtocolDecl(CommonTree source, List<Modifiers> modifiers, GProtocolHeader header, GProtocolDef def)
	{
		super(source, modifiers, header, def);
	}

	/*@Override
	protected GProtocolDecl copy()
	{
		return new GProtocolDecl(this.source, this.modifiers, getHeaderChild(), getDefChild());
	}
	
	@Override
	public GProtocolDecl clone(AstFactory af)
	{
		GProtocolHeader header = getHeaderChild().clone(af);
		GProtocolDef def = getDefChild().clone(af);
		return af.GProtocolDecl(this.source, this.modifiers, header, def);
	}*/

	/*@Override
	public GProtocolDecl reconstruct(ProtocolHeader<Global> header,
			ProtocolDef<Global> def)
	{
		
		ScribDel del = del();
		GProtocolDecl gpd = new GProtocolDecl(this.source, this.modifiers, (GProtocolHeader) header, (GProtocolDef) def);
		gpd = (GProtocolDecl) gpd.del(del);  // FIXME: does another shallow copy
		return gpd;
	}*/
}
