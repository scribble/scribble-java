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
import org.scribble.ast.global.GDisconnect;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.parser.scribble.ScribParser;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;

// Factor with AntlrGMessageTransfer/AntlrGConnect
public class AntlrGDisconnect
{
	public static final int SOURCE_CHILD_INDEX = 0;
	public static final int DESTINATION_CHILD_INDEX = 1;

	public static GDisconnect parseGDisconnect(ScribParser parser, CommonTree ct, AstFactory af)
	{
		RoleNode src = AntlrSimpleName.toRoleNode(getSourceChild(ct), af);
		RoleNode dest = AntlrSimpleName.toRoleNode(getDestinationChild(ct), af);
		return af.GDisconnect(ct, src, dest);
	}

	public static CommonTree getSourceChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(SOURCE_CHILD_INDEX);
	}

	public static CommonTree getDestinationChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(DESTINATION_CHILD_INDEX);
	}
}
