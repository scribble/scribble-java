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
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.parser.scribble.ScribParser;
import org.scribble.util.ScribParserException;

public class AntlrGProtocolDefinition
{
	public static final int BLOCK_CHILD_INDEX = 0;

	public static GProtocolDef parseGProtocolDefinition(ScribParser parser, CommonTree ct, AstFactory af) throws ScribParserException
	{
		GProtocolBlock gpb = (GProtocolBlock) parser.parse(getBlockChild(ct), af);
		return af.GProtocolDef(ct, gpb);
	}

	public static CommonTree getBlockChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(BLOCK_CHILD_INDEX);
	}
}
