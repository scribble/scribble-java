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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.NonRoleParamDecl;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.parser.scribble.ScribParser;
import org.scribble.parser.scribble.ScribParserUtil;
import org.scribble.sesstype.kind.NonRoleParamKind;
import org.scribble.util.ScribParserException;

public class AntlrNonRoleParamDeclList
{
	public static NonRoleParamDeclList parseNonRoleParamDeclList(ScribParser parser, CommonTree ct, AstFactory af) throws ScribParserException
	{
		List<NonRoleParamDecl<NonRoleParamKind>> pds = new LinkedList<>();
		for (CommonTree pd : getParamDeclChildren(ct))
		{
			NonRoleParamDecl<? extends NonRoleParamKind> parsed = (NonRoleParamDecl<?>) parser.parse(pd, af);
			@SuppressWarnings("unchecked")  // OK: the node is immutable -- will never "rewrite" the generic value (the kind) in it
			NonRoleParamDecl<NonRoleParamKind> tmp = (NonRoleParamDecl<NonRoleParamKind>) parsed;
			pds.add(tmp);
		}
		return af.NonRoleParamDeclList(ct, pds);
	}
	
	public static final List<CommonTree> getParamDeclChildren(CommonTree ct)
	{
		return (ct.getChildCount() == 0)
				? Collections.emptyList()
				: ScribParserUtil.toCommonTreeList(ct.getChildren());
	}
}
