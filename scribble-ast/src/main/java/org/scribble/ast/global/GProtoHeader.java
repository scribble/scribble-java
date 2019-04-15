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
import org.scribble.ast.ProtocolHeader;
import org.scribble.ast.name.qualified.GProtoNameNode;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.GProtocolName;
import org.scribble.util.Constants;

public class GProtoHeader extends ProtocolHeader<Global> implements GScribNode
{
	// ScribTreeAdaptor#create constructor
	public GProtoHeader(Token t)
	{
		super(t);
	}
	
	// Tree#dupNode constructor
	protected GProtoHeader(GProtoHeader node)
	{
		super(node);
	}

	@Override
	public GProtoHeader dupNode()
	{
		return new GProtoHeader(this);
	}
	
	@Override
	public GProtoNameNode getNameNodeChild()
	{
		return (GProtoNameNode) getRawNameNodeChild();
	}

	@Override
	public GProtocolName getDeclName()
	{
		return (GProtocolName) super.getDeclName();
	}
	
	@Override
	public String toString()
	{
		return Constants.GLOBAL_KW + " " + super.toString();
	}
}
