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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.ModuleDecl;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.type.kind.ModuleKind;

public class AntlrModuleDecl
{
	public static final int MODULENAME_CHILD_INDEX = 0;

	public static ModuleDecl parseModuleDecl(AntlrToScribParser parser, CommonTree ct, AstFactory af)
	{
		//return af.ModuleDecl(ct, AntlrQualifiedName.toModuleNameNode(getModuleNameChild(ct), af));

		System.out.println("bbb: " + ct.getChildren().stream().map(x -> x.getClass().toString()).collect(Collectors.joining("")));
		System.out.println("ccc: " + ((CommonTree) ct.getChild(0)).getChildren().stream().map(x -> x.getClass().toString()).collect(Collectors.joining("")));

		// HACK FIXME
		//List<String> collect = ((Stream<?>) ct.getChildren().stream()).map(x -> ((CommonTree) x).getText()).collect(Collectors.toList());
		List<String> collect = ((Stream<?>) ((CommonTree) ct.getChild(0)).getChildren().stream()).map(x -> ((CommonTree) x).getText()).collect(Collectors.toList());
		
		return af.ModuleDecl(ct, (ModuleNameNode) af.QualifiedNameNode(ct, ModuleKind.KIND, collect.toArray(new String[0])));
	}

	public static CommonTree getModuleNameChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(MODULENAME_CHILD_INDEX);
	}
}
