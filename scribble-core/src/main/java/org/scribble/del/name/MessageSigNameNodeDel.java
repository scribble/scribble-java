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

import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.SigKind;
import org.scribble.type.name.MessageSigName;
import org.scribble.visit.wf.NameDisambiguator;

public class MessageSigNameNodeDel extends ScribDelBase
{
	public MessageSigNameNodeDel()
	{

	}

	// Is this needed?  Or DataTypeNodes always created from AmbigNameNode? (in this same pass)
	@Override
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		if (parent instanceof MessageSigNameDecl)  // Hacky? don't want to do for decl simplenames (generally, don't do if parent is namedeclnode)
		{
			return visited;
		}
		ModuleContext mc = disamb.getModuleContext();
		MessageSigNameNode msnn = (MessageSigNameNode) visited;
		MessageSigName fullname = mc.getVisibleMessageSigNameFullName(msnn.toName());
		return (MessageSigNameNode) disamb.job.af.QualifiedNameNode(msnn.getSource(), SigKind.KIND, fullname.getElements());  // Didn't keep original del
	}
}
