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
package org.scribble.ast.name.simple;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.antlr.runtime.Token;
import org.scribble.ast.name.NameNode;
import org.scribble.core.type.kind.Kind;

// Names that are necessarily "simple", i.e., never compound qualified (cf. qualified, that may be simple or compound)
// "Identifier" in parser grammar
public abstract class SimpleNameNode<K extends Kind> extends NameNode<K>
{
	// ScribTreeAdaptor#create constructor
	public SimpleNameNode(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected SimpleNameNode(SimpleNameNode<K> node)
	{
		super(node);
	}
	
	@Override
	protected String getLastElement()
	{
		return this.token.getText();
	}
	
	@Override
	public String getText()
	{
		return getLastElement();
	}
	
	@Override
	protected List<String> getSimpleNameList()
	{
		return Stream.of(getText()).collect(Collectors.toList());
	}
	
	@Override
	public String[] getElements()
	{
		return getSimpleNameList().toArray(new String[0]);
	}
	
	@Override
	protected String[] getPrefixElements()
	{
		return new String[0];
	}
}
