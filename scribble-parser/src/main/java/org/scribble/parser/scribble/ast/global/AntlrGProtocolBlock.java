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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.util.ScribParserException;

public class AntlrGProtocolBlock
{
	public static final int INTERACTIONSEQUENCE_CHILD_INDEX = 0;

	public static GProtocolBlock parseGProtocolBlock(AntlrToScribParser parser, CommonTree ct, AstFactory af) throws ScribParserException
	{
		GInteractionSeq gis = (GInteractionSeq) parser.parse(getInteractionSequenceChild(ct), af);
		return af.GProtocolBlock(ct, gis);
	}

	public static final CommonTree getInteractionSequenceChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(INTERACTIONSEQUENCE_CHILD_INDEX);
	}
}
