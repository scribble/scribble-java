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

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.Token;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

// (Currently) a list of mod leaf nodes as AmbigNameNodes (cf., NameNode "elements")
public class ProtocolModList extends ScribNodeBase
{
	// ScribTreeAdaptor#create constructor
	public ProtocolModList(Token t)
	{
		super(t);
	}
	
	// Tree#dupNode constructor
	protected ProtocolModList(ProtocolModList node)
	{
		super(node);
	}
	
	@Override
	public ProtocolModList dupNode()
	{
		return new ProtocolModList(this);
	}
	
	@Override
	public ProtocolModList visitChildren(AstVisitor nv) throws ScribException
	{
		// CHECKME: no child visiting, no reconstruct?
		return this;
	}

	// Cf., NameNode::getSimpleNameList
	public List<ProtocolMod> getModList()
	{
		/*return ((List<?>) getChildren()).stream()
				.map(x -> parseModifier(((CommonTree) x).getText()))
				.collect(Collectors.toList());
					// CHECKME: currently AmbigNameNode(?)
					// CHECKME: factor out getText?*/
		return ((List<?>) getChildren()).stream().map(x -> (ProtocolMod) x)
				.collect(Collectors.toList());
	}
	
	public boolean hasAux()
	{
		//return getModList().contains(ProtocolMod.AUX);
		return getModList().stream().anyMatch(x -> x.isAux());
	}
	
	public boolean hasExplicit()
	{
		//return getModList().contains(ProtocolMod.EXPLICIT);
		return getModList().stream().anyMatch(x -> x.isExplicit());
	}

	public boolean isEmpty()
	{
		return getChildCount() == 0;
	}

	@Override
	public String toString()
	{
		return getModList().stream().map(x -> x.toString())
				.collect(Collectors.joining(" "));
	}

	/*private ProtocolMod parseModifier(String mod)
	{
		switch (mod)
		{
			case "aux":      return ProtocolMod.AUX;
			case "explicit": return ProtocolMod.EXPLICIT;
			default: throw new RuntimeException("Unknown modifier: " + mod);
		}
	}*/
}
