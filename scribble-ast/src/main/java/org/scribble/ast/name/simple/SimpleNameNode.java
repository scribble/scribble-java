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

import java.util.Arrays;
import java.util.List;

import org.antlr.runtime.Token;
import org.scribble.ast.name.NameNode;
import org.scribble.core.type.kind.Kind;

// "ID" in Scribble.g, token type is ScribbleParser.ID, token text is the string value
// Names that are necessarily "simple", i.e., never compound qualified (cf. qualified, that may be simple or compound)
// CHECKME: refactor NameNode elem structure down the QualifiedNameNode side? -- e.g., NameNode.getChildren is misleading for SimpleNameNode (should use getElements instead)
public abstract class SimpleNameNode<K extends Kind> extends NameNode<K>
{
	// token type is ScribbleParser.ID, token text is the string value
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
	public String getText()
	{
		return this.token.getText();
	}
	
	@Override
	public List<String> getElements()
	{
		return Arrays.asList(getText());
	}
}
