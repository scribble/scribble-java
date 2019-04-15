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
package org.scribble.del;

import org.scribble.ast.ScribNode;
import org.scribble.util.ScribException;
import org.scribble.visit.NameDisambiguator;

// Parameterise by AstNode type?  Would inhibit del sharing between types (but that's not currently needed)
public interface ScribDel
{
	default void enterDisambiguation(ScribNode child, NameDisambiguator disamb)
			throws ScribException
	{

	}

	default ScribNode leaveDisambiguation(ScribNode child,
			NameDisambiguator disamb, ScribNode visited) throws ScribException
	{
		return visited;
	}
}
