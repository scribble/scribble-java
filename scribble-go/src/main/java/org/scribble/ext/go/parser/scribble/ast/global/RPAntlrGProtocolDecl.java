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
package org.scribble.ext.go.parser.scribble.ast.global;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.ast.global.GProtocolHeader;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.parser.scribble.ast.global.AntlrGProtocolDecl;
import org.scribble.util.ScribParserException;

@Deprecated
public class RPAntlrGProtocolDecl
{
	public static final String NO_MODS = "PARAM_NOMODS";
	
	public static final int ANNOT_CHILD_INDEX = 3;

	public static GProtocolDecl parseGPrototocolDecl(AntlrToScribParser parser, CommonTree ct, AstFactory af) throws ScribParserException
	{
		GProtocolHeader header = (GProtocolHeader) parser.parse(AntlrGProtocolDecl.getHeaderChild(ct), af);
		GProtocolDef def = (GProtocolDef) parser.parse(AntlrGProtocolDecl.getBodyChild(ct), af);
		List<GProtocolDecl.Modifiers> modifiers = new LinkedList<>();
		if (RPAntlrGProtocolDecl.hasModifiersChild(ct))
		{
			for (CommonTree mod : AntlrGProtocolDecl.getModifierChildren(ct))
			{
				switch (mod.getText())
				{
					case "aux":      modifiers.add(GProtocolDecl.Modifiers.AUX); break;
					case "explicit": modifiers.add(GProtocolDecl.Modifiers.EXPLICIT); break;
					default: throw new RuntimeException("TODO: " + mod);
				}
			}
		}
		return null;//((RPAstFactory) af).RPGProtocolDecl(ct, modifiers, header, def);
	}

	public static boolean hasModifiersChild(CommonTree ct)
	{
		return ct.getChildCount() > AntlrGProtocolDecl.MODIFIERS_CHILD_INDEX
				&& !ct.getChild(AntlrGProtocolDecl.MODIFIERS_CHILD_INDEX).getText().equals(NO_MODS);
	}
}
