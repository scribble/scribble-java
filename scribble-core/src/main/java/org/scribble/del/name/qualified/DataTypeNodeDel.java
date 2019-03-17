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
import org.scribble.ast.DataTypeDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.ast.name.simple.IdNode;
import org.scribble.del.ScribDelBase;
import org.scribble.job.ScribbleException;
import org.scribble.type.name.DataType;
import org.scribble.visit.wf.NameDisambiguator;

public class DataTypeNodeDel extends ScribDelBase
{
	public DataTypeNodeDel()
	{

	}

	// Is this needed?  Or DataTypeNodes always created from AmbigNameNode? (in this same pass)
	@Override
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child,
			NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		if (parent instanceof DataTypeDecl)  // Hacky? don't want to do for decl simplenames (generally, don't do if parent is namedeclnode)
		{
			return visited;
		}
		ModuleContext mc = disamb.getModuleContext();
		DataTypeNode dtn = (DataTypeNode) visited;
		DataType dt = dtn.toName();
		if (!mc.isVisibleDataType(dt))
		{
			throw new ScribbleException(dtn.getSource(),
					"Data type not visible: " + dt);
		}
		DataType fullname = mc.getVisibleDataTypeFullName(dt);
		/*return (DataTypeNode) disamb.job.config.af.QualifiedNameNode(
				dtn.getSource(), DataTypeKind.KIND, fullname.getElements());*/
		DataTypeNode res = new DataTypeNode(dtn.token);
		for (String e : fullname.getElements())
		{
			IdNode n = new IdNode(new CommonToken(23, e));  // FIXME TODO: refactor ast into parser module to access token type constants
			res.addChild(n);
		}
		return res;
				// Don't keep original del (so not using clone)
	}
}
