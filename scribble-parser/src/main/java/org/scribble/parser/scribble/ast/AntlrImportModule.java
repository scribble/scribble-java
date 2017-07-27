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
import org.scribble.ast.ImportModule;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.parser.scribble.ScribbleAntlrConstants;
import org.scribble.parser.scribble.ast.name.AntlrQualifiedName;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;

public class AntlrImportModule
{
	public static final int MODULENAME_CHILD_INDEX = 0;
	public static final int ALIAS_CHILD_INDEX = 1; 

	public static ImportModule parseImportModule(AntlrToScribParser parser, CommonTree ct, AstFactory af)
	{
		ModuleNameNode fmn = AntlrQualifiedName.toModuleNameNode(getModuleNameChild(ct), af);
		ModuleNameNode alias = (hasAlias(ct))
				? AntlrSimpleName.toModuleNameNode(getAliasChild(ct), af)
				: null;
		return af.ImportModule(ct, fmn, alias);
	}

	public static CommonTree getModuleNameChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(MODULENAME_CHILD_INDEX);
	}

	public static CommonTree getAliasChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(ALIAS_CHILD_INDEX);
	}
	
	public static boolean hasAlias(CommonTree ct)
	{
		return !ct.getChild(ALIAS_CHILD_INDEX).getText().equals(ScribbleAntlrConstants.EMPTY_ALIAS);
	}
}
