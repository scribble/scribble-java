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
package org.scribble.del.name;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.MessageTransfer;
import org.scribble.ast.PayloadElem;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.name.simple.AmbigNameNode;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.kind.SigKind;
import org.scribble.sesstype.name.AmbigName;
import org.scribble.visit.wf.NameDisambiguator;

public class AmbigNameNodeDel extends ScribDelBase
{
	public AmbigNameNodeDel()
	{

	}

	// Currently only in "message positions (see Scribble.g ambiguousname)
	@Override
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
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
				throw new ScribbleException(ann.getSource(), "Invalid occurrence of data type: " + parent);
			}
			return AstFactoryImpl.FACTORY.QualifiedNameNode(ann.getSource(), DataTypeKind.KIND, name.getElements());
		}
		else if (mcontext.isMessageSigNameVisible(name.toMessageSigName()))
		{
			if (parent instanceof PayloadElem)  // FIXME HACK
			{
				throw new ScribbleException(ann.getSource(), "Invalid occurrence of message signature name: " + parent);
			}
			return AstFactoryImpl.FACTORY.QualifiedNameNode(ann.getSource(), SigKind.KIND, name.getElements());
		}
		else if (disamb.isBoundParameter(name))
		{
			return AstFactoryImpl.FACTORY.NonRoleParamNode(ann.getSource(), disamb.getParameterKind(name), name.toString());
		}
		throw new ScribbleException(ann.getSource(), "Cannot disambiguate name: " + name);
	}
}
