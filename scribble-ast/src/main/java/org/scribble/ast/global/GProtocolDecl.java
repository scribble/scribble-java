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

import org.antlr.runtime.Token;
import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
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

	@Override
	public GProtocolHeader getHeaderChild()
	{
		return (GProtocolHeader) getChild(ProtocolDecl.HEADER_CHILD);
	}

	@Override
	public GProtocolDef getDefChild()
	{
		return (GProtocolDef) getChild(ProtocolDecl.DEF_CHILD);
	}

	// Cf. CommonTree#dupNode
	@Override
	public GProtocolDecl dupNode()
	{
		return new GProtocolDecl(this);
	}

	@Override
	public GProtocolName getFullMemberName(Module mod)  // TODO: remove mod from meth sig
	{
		Module m = (Module) getParent();
		return new GProtocolName(m.getFullModuleName(),
				getHeaderChild().getDeclName());
	}
}
