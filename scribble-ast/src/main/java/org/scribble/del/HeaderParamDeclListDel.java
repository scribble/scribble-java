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

import org.scribble.ast.ParamDecl;
import org.scribble.ast.ParamDeclList;
import org.scribble.ast.ScribNode;
import org.scribble.util.ScribException;
import org.scribble.visit.NameDisambiguator;

public abstract class HeaderParamDeclListDel extends ScribDelBase
{
	public HeaderParamDeclListDel()
	{

	}

	// Doing in leave allows the arguments to be individually checked first
	@Override
	public ParamDeclList<?> leaveDisambiguation(ScribNode child,
			NameDisambiguator disamb, ScribNode visited) throws ScribException
	{
		ParamDeclList<?> pdl = (ParamDeclList<?>) visited;
		List<? extends ParamDecl<?>> decls = pdl.getDeclChildren();
			// grammar enforces RoleDeclList size > 0
		if (decls.size() != decls.stream().map(d -> d.getDeclName()).distinct()
				.count())
		{
			throw new ScribException(pdl.getSource(),
					"Duplicate header decls: " + pdl);
		}
		return pdl;
	}
}
