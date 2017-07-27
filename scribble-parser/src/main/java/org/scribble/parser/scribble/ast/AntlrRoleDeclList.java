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
import org.scribble.ast.RoleDecl;
import org.scribble.ast.RoleDeclList;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.parser.scribble.AntlrToScribParserUtil;
import org.scribble.util.ScribParserException;

public class AntlrRoleDeclList
{
	public static RoleDeclList parseRoleDeclList(AntlrToScribParser parser, CommonTree ct, AstFactory af) throws ScribParserException
	{
		//List<RoleDecl> rds = getRoleDeclChildren(ct).stream().map((pd) -> (RoleDecl) parser.parse(pd)).collect(Collectors.toList());
		List<RoleDecl> rds = new LinkedList<>();
		for (CommonTree pd : getRoleDeclChildren(ct))
		{
			rds.add((RoleDecl) parser.parse(pd, af));
		}
		return af.RoleDeclList(ct, rds);
	}

	public static List<CommonTree> getRoleDeclChildren(CommonTree ct)
	{
		return AntlrToScribParserUtil.toCommonTreeList(ct.getChildren());
	}
}
