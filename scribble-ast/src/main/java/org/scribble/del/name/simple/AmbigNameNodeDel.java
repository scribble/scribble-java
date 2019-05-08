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
package org.scribble.del.name.simple;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.MsgTransfer;
import org.scribble.ast.PayElem;
import org.scribble.ast.ScribNode;
import org.scribble.ast.name.simple.AmbigNameNode;
import org.scribble.ast.name.simple.IdNode;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.type.kind.DataKind;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.name.AmbigName;
import org.scribble.del.ScribDelBase;
import org.scribble.util.ScribException;
import org.scribble.visit.NameDisambiguator;

public class AmbigNameNodeDel extends ScribDelBase
{
	public AmbigNameNodeDel()
	{

	}

	@Override
	public ScribNode leaveDisambiguation(ScribNode child,
			NameDisambiguator disamb, ScribNode visited) throws ScribException
	{
		ScribNode parent = child.getParent();
		ModuleContext modc = disamb.getModuleContext();
		AmbigNameNode a = (AmbigNameNode) visited;
		AmbigName name = a.toName();
		// By well-formedness (checked later), payload type and parameter names are distinct
		// CHECKME: are conflicts checked elsewhere? -- ?
		if (modc.isDataTypeVisible(name.toDataName()))
		{
			// CHECKME HACK: MsgTransfer assumes MessageNode (cast in visitChildren), so this needs to be caught here  
			// CHECKME: any other similar cases?
			if (parent instanceof MsgTransfer<?>)
			{
				throw new ScribException(a.getSource(),
						"Invalid occurrence of data name: " + parent);
			}
			List<IdNode> elems = a.getElements().stream()
					.map(x -> disamb.job.config.af.IdNode(null, x))
					.collect(Collectors.toList());
			return disamb.job.config.af.DataNameNode(a.token, elems);
		}
		else if (modc.isMessageSigNameVisible(name.toSigName()))
		{
			if (parent instanceof PayElem) // FIXME HACK
			{
				throw new ScribException(a.getSource(),
						"Invalid occurrence of message signature name: " + parent);
			}
			List<IdNode> elems = a.getElements().stream()
					.map(x -> disamb.job.config.af.IdNode(null, x))
					.collect(Collectors.toList());
			return disamb.job.config.af.SigNameNode(a.token, elems);
		}
		else if (disamb.isBoundParam(name))
		{
			NonRoleParamKind kind = disamb.getParamKind(name);
			return kind.equals(DataKind.KIND) 
					? disamb.job.config.af.DataParamNode(a.token, a.getText())
					// else if(kind.equals(SigKind.KIND))
					: disamb.job.config.af.SigParamNode(a.token, a.getText());
		}
		throw new ScribException(a.getSource(),
				"Cannot disambiguate name: " + name);
	}
}
