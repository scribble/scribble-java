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
import org.scribble.ast.ProtoHeader;
import org.scribble.ast.name.qualified.LProtoNameNode;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.LProtoName;
import org.scribble.del.DelFactory;
import org.scribble.util.Constants;

public class LProtoHeader extends ProtoHeader<Local> implements LSessionNode
{
	// ScribTreeAdaptor#create constructor
	public LProtoHeader(Token t)
	{
		super(t);
	}
	
	// Tree#dupNode constructor
	protected LProtoHeader(LProtoHeader node)
	{
		super(node);
	}

	@Override
	public LProtoHeader dupNode()
	{
		return new LProtoHeader(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.LProtoHeader(this);
	}
	
	@Override
	public LProtoNameNode getNameNodeChild()
	{
		return (LProtoNameNode) getRawNameNodeChild();
	}

	@Override
	public LProtoName getDeclName()
	{
		return (LProtoName) super.getDeclName();
	}
	
	@Override
	public String toString()
	{
		return Constants.LOCAL_KW + " " + super.toString();
	}
}
