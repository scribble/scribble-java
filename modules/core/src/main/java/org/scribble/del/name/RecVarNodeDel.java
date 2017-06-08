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
import org.scribble.ast.ScribNode;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.wf.NameDisambiguator;

public class RecVarNodeDel extends ScribDelBase
{
	public RecVarNodeDel()
	{

	}

	@Override
	public RecVarNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		// Consistent with bound RoleNode checking
		RecVarNode rn = (RecVarNode) visited;
		RecVar rv = rn.toName();
		if (!disamb.isBoundRecVar(rv))
		{
			throw new ScribbleException(rn.getSource(), "Rec variable not bound: " + rn);
		}
		return (RecVarNode) super.leaveDisambiguation(parent, child, disamb, rn);
		//return super.leaveDisambiguation(parent, child, disamb, rn.reconstruct(disamb.getCanonicalRecVarName(rv)));
	}

	@Override
	public void enterProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inliner) throws ScribbleException
	{
		super.enterProtocolInlining(parent, child, inliner);
		ScribDelBase.pushVisitorEnv(this, inliner);
	}

	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inliner, ScribNode visited) throws ScribbleException
	{
		RecVarNode rn = (RecVarNode) visited;
		RecVar rv = rn.toName();
		//return super.leaveProtocolInlining(parent, child, inliner, rn.reconstruct(inliner.getCanonicalRecVarName(rv)));  // No, affects the source AST
		//RecVarNode inlined = rn.reconstruct(inliner.getCanonicalRecVarName(rv));
		RecVarNode inlined = (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(rn.getSource(), RecVarKind.KIND, inliner.getCanonicalRecVarName(rv));
		inliner.pushEnv(inliner.popEnv().setTranslation(inlined));
		return ScribDelBase.popAndSetVisitorEnv(this, inliner, rn);  // Not done by any super
	}
}
