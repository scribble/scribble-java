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

import java.util.stream.Collectors;

import org.scribble.ast.MessageTransfer;
import org.scribble.ast.PayElem;
import org.scribble.ast.ScribNode;
import org.scribble.ast.name.qualified.DataNameNode;
import org.scribble.ast.name.qualified.SigNameNode;
import org.scribble.ast.name.simple.AmbigNameNode;
import org.scribble.ast.name.simple.IdNode;
import org.scribble.ast.name.simple.SigParamNode;
import org.scribble.ast.name.simple.DataParamNode;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.type.kind.DataKind;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.kind.SigKind;
import org.scribble.core.type.name.AmbigName;
import org.scribble.del.ScribDelBase;
import org.scribble.util.ScribException;
import org.scribble.visit.NameDisambiguator;

public class AmbigNameNodeDel extends ScribDelBase
{
	public AmbigNameNodeDel()
	{

	}

	// Currently only in "message positions (see Scribble.g, ambiguousname)
	@Override
	public ScribNode leaveDisambiguation(ScribNode child,
			NameDisambiguator disamb, ScribNode visited) throws ScribException
	{
		ScribNode parent = child.getParent();
		ModuleContext mcontext = disamb.getModuleContext();
		AmbigNameNode ann = (AmbigNameNode) visited;
		AmbigName name = ann.toName();
		// By well-formedness (checked later), payload type and parameter names are distinct
		// FIXME: are conflicts checked elsewhere?
		if (mcontext.isDataTypeVisible(name.toDataName()))
		{
			if (parent instanceof MessageTransfer<?>)  // FIXME HACK: MessageTransfer assumes MessageNode (cast in visitChildren), so this needs to be caught here  // FIXME: other similar cases?
			{
				throw new ScribException(ann.getSource(),
						"Invalid occurrence of data type: " + parent);
			}
			/*return disamb.job.config.af.QualifiedNameNode(ann.getSource(),
					DataTypeKind.KIND, name.getElements());*/

			//DataTypeNode res = new DataTypeNode(ann.token);  // CHECKME: what should the Token be?
			//DataTypeNode res = new DataTypeNode(new CommonToken(73, "TYPENAME"));  // FIXME: use af
			DataNameNode res = (DataNameNode) disamb.lang.config.af
					.DataNameNode(ann.getElements().stream()
							.map(x -> disamb.lang.config.af.IdNode(x))
							.collect(Collectors.toList()));

			//res.addChildren(ann.getChildren());  // CHECKME: refactor factory for new ast, and do inside there?
			res.addChild(new IdNode(ann.token));

			return res;
		}
		else if (mcontext.isMessageSigNameVisible(name.toSigName()))
		{
			if (parent instanceof PayElem) // FIXME HACK
			{
				throw new ScribException(ann.getSource(),
						"Invalid occurrence of message signature name: " + parent);
			}
			/*return disamb.job.config.af.QualifiedNameNode(ann.getSource(),
					SigKind.KIND, name.getElements());*/

			//MessageSigNameNode res = new MessageSigNameNode(ann.token);  // CHECME: what should the Token be?
			//MessageSigNameNode res = new MessageSigNameNode(new CommonToken(67, "SIGNAME"));  // FIXME: use af
			SigNameNode res = (SigNameNode) disamb.lang.config.af
					.SigNameNode(ann.getElements().stream()
							.map(x -> disamb.lang.config.af.IdNode(x))
							.collect(Collectors.toList()));

			//res.addChildren(ann.getChildren());
			res.addChild(new IdNode(ann.token));

			return res;
		}
		else if (disamb.isBoundParameter(name))
		{
			/*return disamb.job.config.af.NonRoleParamNode(ann.getSource(),
					disamb.getParameterKind(name), name.toString());*/
			NonRoleParamKind kind = disamb.getParameterKind(name);
			if (kind.equals(DataKind.KIND))
			{
				//TypeParamNode res = new TypeParamNode(ann.token);  // CHECKME: what should the Token be?
				//TypeParamNode res = new TypeParamNode(75, ann.token);  // CHECKME: what should the Token be?
				DataParamNode res = disamb.lang.config.af.DataParamNode(ann.getText());  // CHECKME: what should the Token be?

				//res.addChildren(ann.getChildren());  // CHECKME: refactor factory for new ast, and do inside there?

				return res;
			}
			else if (kind.equals(SigKind.KIND))
			{
				//SigParamNode res = new SigParamNode(69, ann.token);  // CHECKME: what should the Token be?
				SigParamNode res = disamb.lang.config.af.SigParamNode(ann.getText());  // CHECKME: what should the Token be?

				//res.addChildren(ann.getChildren());  // CHECKME: refactor factory for new ast, and do inside there?

				return res;
			}
		}
		throw new ScribException(ann.getSource(),
				"Cannot disambiguate name: " + name);
	}
}
