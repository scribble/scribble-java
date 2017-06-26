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
package org.scribble.parser.scribble.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.ModuleDecl;
import org.scribble.parser.scribble.ScribParser;
import org.scribble.parser.scribble.ast.name.AntlrQualifiedName;

public class AntlrModuleDecl
{
	public static final int MODULENAME_CHILD_INDEX = 0;

	public static ModuleDecl parseModuleDecl(ScribParser parser, CommonTree ct, AstFactory af)
	{
		return af.ModuleDecl(ct, AntlrQualifiedName.toModuleNameNode(getModuleNameChild(ct), af));
	}

	public static CommonTree getModuleNameChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(MODULENAME_CHILD_INDEX);
	}
}
