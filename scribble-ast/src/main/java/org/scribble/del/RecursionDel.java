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

import org.scribble.ast.Recursion;
import org.scribble.ast.ScribNode;
import org.scribble.core.type.name.RecVar;
import org.scribble.util.ScribException;
import org.scribble.visit.NameDisambiguator;

public abstract class RecursionDel extends CompoundInteractionDel
{
	public RecursionDel()
	{

	}

	@Override
	public void enterDisambiguation(ScribNode child,
			NameDisambiguator disamb) throws ScribException
	{
		Recursion<?> rec = (Recursion<?>) child;
		RecVar rv = rec.getRecVarChild().toName();
		disamb.pushRecVar(rv);
	}

	@Override
	public ScribNode leaveDisambiguation(ScribNode child,
			NameDisambiguator disamb, ScribNode visited) throws ScribException
	{
		Recursion<?> rec = (Recursion<?>) visited;
		RecVar rv = ((Recursion<?>) child).getRecVarChild().toName();  
				// visited may be already name mangled  // Not any more (refactored to inlining)
		disamb.popRecVar(rv);
		return rec;
	}
}
