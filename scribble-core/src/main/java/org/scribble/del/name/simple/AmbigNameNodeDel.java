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

import org.scribble.ast.MessageTransfer;
import org.scribble.ast.PayloadElem;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.ast.name.simple.AmbigNameNode;
import org.scribble.ast.name.simple.SigParamNode;
import org.scribble.ast.name.simple.TypeParamNode;
import org.scribble.del.ScribDelBase;
import org.scribble.job.ScribbleException;
import org.scribble.type.kind.DataTypeKind;
import org.scribble.type.kind.NonRoleParamKind;
import org.scribble.type.kind.SigKind;
import org.scribble.type.name.AmbigName;
import org.scribble.visit.wf.NameDisambiguator;

public class AmbigNameNodeDel extends ScribDelBase
{
	public AmbigNameNodeDel()
	{

	}

	// Currently only in "message positions (see Scribble.g ambiguousname)
	@Override
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child,
			NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		ModuleContext mcontext = disamb.getModuleContext();
		AmbigNameNode ann = (AmbigNameNode) visited;
		AmbigName name = ann.toName();
		// By well-formedness (checked later), payload type and parameter names are distinct
		// FIXME: are conflicts checked elsewhere?
		if (mcontext.isDataTypeVisible(name.toDataType()))
		{
			if (parent instanceof MessageTransfer<?>)  // FIXME HACK: MessageTransfer assumes MessageNode (cast in visitChildren), so this needs to be caught here  // FIXME: other similar cases?
			{
				throw new ScribbleException(ann.getSource(),
						"Invalid occurrence of data type: " + parent);
			}
			/*return disamb.job.config.af.QualifiedNameNode(ann.getSource(),
					DataTypeKind.KIND, name.getElements());*/
			DataTypeNode res = new DataTypeNode(ann.token);  // CHECKME: what should the Token be?
			res.addChildren(ann.getChildren());  // CHECKME: refactor factory for new ast, and do inside there?
			return res;
		}
		else if (mcontext.isMessageSigNameVisible(name.toMessageSigName()))
		{
			if (parent instanceof PayloadElem) // FIXME HACK
			{
				throw new ScribbleException(ann.getSource(),
						"Invalid occurrence of message signature name: " + parent);
			}
			/*return disamb.job.config.af.QualifiedNameNode(ann.getSource(),
					SigKind.KIND, name.getElements());*/
			MessageSigNameNode res = new MessageSigNameNode(ann.token);  // CHECME: what should the Token be?
			res.addChildren(ann.getChildren());
			return res;
		}
		else if (disamb.isBoundParameter(name))
		{
			/*return disamb.job.config.af.NonRoleParamNode(ann.getSource(),
					disamb.getParameterKind(name), name.toString());*/
			NonRoleParamKind kind = disamb.getParameterKind(name);
			if (kind.equals(DataTypeKind.KIND))
			{
				TypeParamNode res = new TypeParamNode(ann.token);  // CHECKME: what should the Token be?
				res.addChildren(ann.getChildren());  // CHECKME: refactor factory for new ast, and do inside there?
				return res;
			}
			else if (kind.equals(SigKind.KIND))
			{
				SigParamNode res = new SigParamNode(ann.token);  // CHECKME: what should the Token be?
				res.addChildren(ann.getChildren());  // CHECKME: refactor factory for new ast, and do inside there?
				return res;
			}
		}
		throw new ScribbleException(ann.getSource(),
				"Cannot disambiguate name: " + name);
	}
}
