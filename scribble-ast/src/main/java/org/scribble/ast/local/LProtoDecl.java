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
import org.scribble.ast.Module;
import org.scribble.ast.ProtoDecl;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.LProtoName;
import org.scribble.del.DelFactory;

// Maybe make abstract with concrete parsed (LParsedDecl) and projected (LProjectionDecl) subclasses
public class LProtoDecl extends ProtoDecl<Local> implements LSessionNode
{
	// ScribTreeAdaptor#create constructor
	public LProtoDecl(Token t)
	{
		super(t);
	}
	
	// Tree#dupNode constructor
	protected LProtoDecl(LProtoDecl node)
	{
		super(node);
	}

	@Override
	public LProtoHeader getHeaderChild()
	{
		return (LProtoHeader) getChild(ProtoDecl.HEADER_CHILD);
	}

	@Override
	public LProtoDef getDefChild()
	{
		return (LProtoDef) getChild(ProtoDecl.DEF_CHILD);
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
		df.LProtoDecl(this);
	}

	@Override
	public LProtoName getFullMemberName(Module mod)  // TODO: remove mod from meth sig
	{
		Module m = (Module) getParent();
		return new LProtoName(m.getFullModuleName(),
				getHeaderChild().getDeclName());
	}
}
