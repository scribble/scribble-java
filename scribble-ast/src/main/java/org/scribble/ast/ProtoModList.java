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
import org.scribble.del.DelFactory;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

// (Currently) a list of mod leaf nodes as AmbigNameNodes (cf., NameNode "elements")
public class ProtoModList extends ScribNodeBase
{
	// ScribTreeAdaptor#create constructor
	public ProtoModList(Token t)
	{
		super(t);
	}
	
	// Tree#dupNode constructor
	protected ProtoModList(ProtoModList node)
	{
		super(node);
	}
	
	public List<ProtoModNode> getModChildren()
	{
		return ((List<?>) getChildren()).stream().map(x -> (ProtoModNode) x)
				.collect(Collectors.toList());
	}
	
	// "add", not "set"
	public void addScribChildren(List<ProtoModNode> mods)
	{
		addChildren(mods);
	}

  // CHECKME: deprecate? cf. getModChildren
	// Cf., NameNode::getSimpleNameList
	public List<ProtoModNode> getModList()
	{
		return getModChildren();
	}
	
	public boolean hasAux()
	{
		return getModList().stream().anyMatch(x -> x.isAux());
	}
	
	public boolean hasExplicit()
	{
		return getModList().stream().anyMatch(x -> x.isExplicit());
	}

	public boolean isEmpty()
	{
		return getChildCount() == 0;
	}
	
	@Override
	public ProtoModList dupNode()
	{
		return new ProtoModList(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.ProtoModList(this);
	}
	
	@Override
	public ProtoModList visitChildren(AstVisitor nv) throws ScribException
	{
		// CHECKME: no child visiting, no reconstruct?
		return this;
	}

	@Override
	public String toString()
	{
		return getModList().stream().map(x -> x.toString())
				.collect(Collectors.joining(" "));
	}
}
