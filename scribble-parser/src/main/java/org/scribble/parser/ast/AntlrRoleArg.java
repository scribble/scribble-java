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
package org.scribble.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.RoleArg;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrSimpleName;

public class AntlrRoleArg
{
	public static final int ARG_CHILD_INDEX = 0;

	public static RoleArg parseRoleArg(ScribParser parser, CommonTree ct)
	{
		RoleNode role = AntlrSimpleName.toRoleNode(getArgChild(ct));
		return AstFactoryImpl.FACTORY.RoleArg(ct, role);
	}

	public static CommonTree getArgChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(ARG_CHILD_INDEX);
	}
}