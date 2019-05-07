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
import org.scribble.ast.ProtoDecl;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.GProtoName;
import org.scribble.del.DelFactory;

public class GProtoDecl extends ProtoDecl<Global> implements GScribNode
{
	// ScribTreeAdaptor#create constructor
	public GProtoDecl(Token t)
	{
		super(t);
	}
	
	// Tree#dupNode constructor
	protected GProtoDecl(GProtoDecl node)
	{
		super(node);
	}

	@Override
	public GProtoHeader getHeaderChild()
	{
		return (GProtoHeader) getChild(ProtoDecl.HEADER_CHILD);
	}

	@Override
	public GProtoDef getDefChild()
	{
		return (GProtoDef) getChild(ProtoDecl.DEF_CHILD);
	}

	// Cf. CommonTree#dupNode
	@Override
	public GProtoDecl dupNode()
	{
		return new GProtoDecl(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.GProtoDecl(this);
	}

	@Override
	public GProtoName getFullMemberName(Module mod)  // TODO: remove mod from meth sig
	{
		Module m = (Module) getParent();
		return new GProtoName(m.getFullModuleName(),
				getHeaderChild().getDeclName());
	}
}
