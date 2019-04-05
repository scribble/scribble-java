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
package org.scribble.util;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactory;
import org.scribble.ast.ScribNode;

public class ScribNodeUtil
{
	// Strict class equality, cf. ScribNodeBase#visitChildWithClassCheck
	// C is expected to be of a ground class type
	// Maybe pointless (in terms of formal guarantees) to use equality instead of assignable
	public static <C extends ScribNode> C checkNodeClassEquality(C c, ScribNode n)
	{
		if (!c.getClass().equals(n.getClass()))
		{
			throw new RuntimeException(
					"Node class not equal: " + c.getClass() + ", " + n.getClass());
		}
		@SuppressWarnings("unchecked")
		C tmp = (C) n;
		return tmp;
	}

	// C is expected to be of a ground class type
	public static <C extends ScribNode> C castNodeByClass(C cast, ScribNode n)
	{
		if (!cast.getClass().isAssignableFrom(n.getClass()))
		{
			throw new RuntimeException(
					"Node class cast error: " + cast.getClass() + ", " + n.getClass());
		}
		@SuppressWarnings("unchecked")
		C tmp = (C) n;
		return tmp;
	}
	
	public static <N extends ScribNode> List<N> cloneList(AstFactory af,
			List<N> ns)
	{
		return ns.stream().map(n -> checkNodeClassEquality(n, // n.clone(af)))
				n.clone())).collect(Collectors.toList());
	}
}
