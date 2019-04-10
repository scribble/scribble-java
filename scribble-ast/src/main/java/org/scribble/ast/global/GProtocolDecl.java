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
import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ProtocolMod;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.GProtocolName;

public class GProtocolDecl extends ProtocolDecl<Global> implements GScribNode
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

	@Override
	public GProtocolHeader getHeaderChild()
	{
		return (GProtocolHeader) getChild(1);
	}

	@Override
	public GProtocolDef getDefChild()
	{
		return (GProtocolDef) getChild(2);
	}

	@Override
	public GProtocolName getFullMemberName(Module mod)  // TODO: remove mod from meth sig
	{
		/*ModuleName fullmodname = mod.getFullModuleName();
		return new GProtocolName(fullmodname, getHeaderChild().getDeclName());*/
		Module m = (Module) getParent();
		return new GProtocolName(m.getFullModuleName(),
				getHeaderChild().getDeclName());
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public GProtocolDecl(CommonTree source, List<ProtocolMod> modifiers, GProtocolHeader header, GProtocolDef def)
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
