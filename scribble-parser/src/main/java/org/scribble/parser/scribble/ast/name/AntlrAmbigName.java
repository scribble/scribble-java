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
package org.scribble.parser.scribble.ast.name;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.name.simple.AmbigNameNode;

// FIXME: refactor into AntlrSimple/QualifiedName? -- need to separate ambig simple and qualified?
public class AntlrAmbigName
{
	public static AmbigNameNode toAmbigNameNode(CommonTree ct, AstFactory af)
	{
		return af.AmbiguousNameNode(getNameChild(ct), getName(ct));
	}

	private static CommonTree getNameChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(0);
	}
	
	private static String getName(CommonTree ct)
	{
		return AntlrSimpleName.getName(getNameChild(ct));
	}
}
