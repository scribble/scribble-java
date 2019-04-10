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
import org.scribble.ast.Do;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.GProtocolName;
import org.scribble.lang.LangContext;

public class GDo extends Do<Global> implements GSimpleSessionNode
{
	// ScribTreeAdaptor#create constructor
	public GDo(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public GDo(GDo node)
	{
		super(node);
	}

	@Override
	public GProtocolNameNode getProtocolNameNode()
	{
		return (GProtocolNameNode) getChild(Do.NAME_CHILD_INDEX);
	}
	
	@Override
	public GDo dupNode()
	{
		return new GDo(this);
	}

	@Override
	public GProtocolName getTargetProtocolDeclFullName(ModuleContext mcontext)
	{
		return (GProtocolName) super.getTargetProtocolDeclFullName(mcontext);
	}

	@Override
	public GProtocolDecl getTargetProtocolDecl(LangContext jcontext,
			ModuleContext mcontext)
	{
		return (GProtocolDecl) super.getTargetProtocolDecl(jcontext, mcontext);
	}
}
