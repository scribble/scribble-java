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
package org.scribble.parser.scribble.ast.global;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.global.GInteractionNode;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.parser.scribble.ScribParser;
import org.scribble.parser.scribble.ScribParserUtil;
import org.scribble.util.ScribParserException;

public class AntlrGInteractionSequence
{
	public static GInteractionSeq parseGInteractionSequence(ScribParser parser, CommonTree ct, AstFactory af) throws ScribParserException
	{
		/*List<GInteractionNode> gis =
				getInteractionChildren(ct).stream().map((gi) -> (GInteractionNode) parser.parse(gi)).collect(Collectors.toList());*/
		List<GInteractionNode> gis = new LinkedList<>();
		for (CommonTree gi : getInteractionChildren(ct))
		{
			gis.add((GInteractionNode) parser.parse(gi, af));
		}
		return af.GInteractionSeq(ct, gis);
	}

	public static List<CommonTree> getInteractionChildren(CommonTree ct)
	{
		return (ct.getChildCount() == 0)
				? Collections.emptyList()
				: ScribParserUtil.toCommonTreeList(ct.getChildren());
	}
}
