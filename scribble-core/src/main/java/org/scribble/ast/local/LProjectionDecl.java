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
import org.scribble.ast.ProtocolDef;
import org.scribble.ast.ProtocolHeader;
import org.scribble.ast.ScribNodeBase;
import org.scribble.del.ScribDel;
import org.scribble.del.local.LProjectionDeclDel;
import org.scribble.type.kind.Local;

public class LProjectionDecl extends LProtocolDecl
{
	public LProjectionDecl(CommonTree source, List<Modifiers> modifiers, LProtocolHeader header, LProtocolDef def)
	{
		super(source, modifiers, header, def);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LProjectionDecl(this.source, this.modifiers, getHeader(), getDef());
	}
	
	@Override
	public LProjectionDecl clone(AstFactory af)
	{
		LProtocolHeader header = getHeader().clone(af);
		LProtocolDef def = getDef().clone(af);
		LProjectionDeclDel del = (LProjectionDeclDel) del();
		return af.LProjectionDecl(this.source, this.modifiers, del.getSourceProtocol(), del.getSelfRole(), header, def);
	}
	
	@Override
	public LProjectionDecl reconstruct(ProtocolHeader<Local> header, ProtocolDef<Local> def)
	{
		ScribDel del = del();
		LProjectionDecl lpd = new LProjectionDecl(this.source, this.modifiers, (LProtocolHeader) header, (LProtocolDef) def);
		lpd = (LProjectionDecl) lpd.del(del);
		return lpd;
	}
}
