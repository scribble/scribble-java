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
package org.scribble.main;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.antlr.runtime.tree.CommonTree;

public class ScribbleException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ScribbleException()
	{
		// TODO Auto-generated constructor stub
	}

	public ScribbleException(CommonTree blame, String arg0)
	{
		// char position indexes are obscure because only certain (child) nodes/tokens are actually recorded, e.g., name nodes (the keyword nodes, e.g., global, have been discarded)
		// ...although even taking the above into account, indexes still seem off? -- may be due to tabs (counted as single chars)
		super(getRootModuleName(blame) + "(line " + blame.getLine() + ":" + (blame.getCharPositionInLine()) + "): " + arg0);
			// Cf., getTokenStartIndex/getTokenStopIndex ?  blame.token.getCharPositionInLine()?
	}
	
	// Cf., AntlrModule/AntlrModuleDecl -- but can't access parser classes from core (Maven dependencies)
	private static String getRootModuleName(CommonTree blame)  // Means root of this CommonTree (not the Scribble job root, i.e. main)
	{
		CommonTree root = blame;
		while (root.parent != null)
		{
			root = root.parent;
		}
		CommonTree moddecl = (CommonTree) root.getChild(0).getChild(0);
		int count = moddecl.getChildCount();
		return IntStream.range(0, count).mapToObj((i) -> moddecl.getChild(i).getText()).collect(Collectors.joining("."));
	}

	public ScribbleException(String arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ScribbleException(Throwable arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ScribbleException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ScribbleException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3)
	{
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}
}
