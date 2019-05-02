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
import org.scribble.ast.Do;
import org.scribble.ast.name.qualified.LProtoNameNode;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.LProtoName;
import org.scribble.del.DelFactory;
import org.scribble.job.JobContext;

public class LDo extends Do<Local> implements LSimpleSessionNode
{

	// ScribTreeAdaptor#create constructor
	public LDo(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public LDo(LDo node)
	{
		super(node);
	}

	@Override
	public LProtoNameNode getProtocolNameNode()
	{
		return (LProtoNameNode) getChild(Do.NAME_CHILD_INDEX);
	}

	@Override
	public LProtoDecl getTargetProtocolDecl(JobContext jobc,
			ModuleContext modc)
	{
		LProtoName fullname = getTargetProtoDeclFullName(modc);
		return jobc.getModule(fullname.getPrefix())
				.getLProtocolDeclChild(fullname.getSimpleName());
	}
	
	@Override
	public LDo dupNode()
	{
		return new LDo(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.LDo(this);
	}

	@Override
	public LProtoName getTargetProtoDeclFullName(ModuleContext modc)
	{
		return (LProtoName) super.getTargetProtoDeclFullName(modc);
	}
}
