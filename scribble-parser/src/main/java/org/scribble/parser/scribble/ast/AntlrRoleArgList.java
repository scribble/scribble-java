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

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.RoleArg;
import org.scribble.ast.RoleArgList;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.parser.scribble.AntlrToScribParserUtil;
import org.scribble.util.ScribParserException;

public class AntlrRoleArgList
{
	public static RoleArgList parseRoleArgList(AntlrToScribParser parser, CommonTree ct, AstFactory af) throws ScribParserException
	{
		//List<RoleArg> ris = getRoleInstantiationChildren(ct).stream().map((ri) -> (RoleArg) parser.parse(ri)).collect(Collectors.toList());
		List<RoleArg> ris = new LinkedList<>();
		for (CommonTree ri : getRoleInstantiationChildren(ct))
		{
			ris.add((RoleArg) parser.parse(ri, af));
		}
		return af.RoleArgList(ct, ris);
	}

	public static final List<CommonTree> getRoleInstantiationChildren(CommonTree ct)
	{
		return AntlrToScribParserUtil.toCommonTreeList(ct.getChildren());
	}
}
