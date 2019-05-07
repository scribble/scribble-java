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
package org.scribble.ast;

import org.antlr.runtime.Token;
import org.scribble.core.lang.ProtoMod;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

public abstract class ProtoModNode extends ScribNodeBase
{
	// ScribTreeAdaptor#create constructor
	public ProtoModNode(Token t)
	{
		super(t);
	}
	
	// Tree#dupNode constructor
	protected ProtoModNode(ProtoModNode node)
	{
		super(node);
	}
	
	public boolean isAux()
	{
		return false;
	}
	
	public boolean isExplicit()
	{
		return false;
	}
	
	@Override
	public ProtoModNode visitChildren(AstVisitor nv) throws ScribException
	{
		return this;
	}
	
	// cf. toName
	public ProtoMod toProtoMod()  // TODO: rename
	{
		switch (toString())  // Directly from Scribble.g KW
		{
			case "aux":      return ProtoMod.AUX;
			case "explicit": return ProtoMod.EXPLICIT;
			default:         throw new RuntimeException("Unknown modifier: " + this);
		}
	}
}
