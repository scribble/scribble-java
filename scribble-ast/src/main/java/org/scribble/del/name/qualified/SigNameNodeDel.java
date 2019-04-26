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
package org.scribble.del.name.qualified;

import org.antlr.runtime.CommonToken;
import org.scribble.ast.SigDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.name.qualified.SigNameNode;
import org.scribble.ast.name.simple.IdNode;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.type.name.SigName;
import org.scribble.del.ScribDelBase;
import org.scribble.util.ScribException;
import org.scribble.visit.NameDisambiguator;

public class SigNameNodeDel extends ScribDelBase
{
	public SigNameNodeDel()
	{

	}

	// Is this needed?  Or DataTypeNodes always created from AmbigNameNode? (in this same pass)
	@Override
	public ScribNode leaveDisambiguation(ScribNode child,
			NameDisambiguator disamb, ScribNode visited) throws ScribException
	{
		ScribNode parent = child.getParent();
		if (parent instanceof SigDecl)  // Hacky? don't want to do for decl simplenames (generally, don't do if parent is namedeclnode)
		{
			return visited;
		}
		ModuleContext mc = disamb.getModuleContext();
		SigNameNode msnn = (SigNameNode) visited;
		SigName fullname = 
				mc.getVisibleMessageSigNameFullName(msnn.toName());
		/*return (MessageSigNameNode) disamb.job.config.af.QualifiedNameNode(
				msnn.getSource(), SigKind.KIND, fullname.getElements());*/
		SigNameNode res = new SigNameNode(msnn.token);
		for (String e : fullname.getElements())
		{
			IdNode n = new IdNode(new CommonToken(23, e));  // FIXME TODO: refactor ast into parser module to access token type constants
			res.addChild(n);
		}
		return res;
				// Don't keep original del (so not using clone)
	}
}
