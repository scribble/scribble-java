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

import java.util.List;

import org.scribble.ast.HeaderParamDecl;
import org.scribble.ast.HeaderParamDeclList;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.wf.NameDisambiguator;

public abstract class HeaderParamDeclListDel extends ScribDelBase
{
	public HeaderParamDeclListDel()
	{

	}

	// Doing in leave allows the arguments to be individually checked first
	@Override
	public HeaderParamDeclList<?> leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		HeaderParamDeclList<?> pdl = (HeaderParamDeclList<?>) visited;
		List<? extends HeaderParamDecl<?>> decls = pdl.getDecls();  // grammar enforces RoleDeclList size > 0
		if (decls.size() != decls.stream().map((d) -> d.getDeclName()).distinct().count())
		{
			throw new ScribbleException(pdl.getSource(), "Duplicate header decls: " + pdl);
		}
		return pdl;
	}
}
