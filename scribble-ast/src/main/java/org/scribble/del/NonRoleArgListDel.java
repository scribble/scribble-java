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

import java.util.Iterator;

import org.scribble.ast.Do;
import org.scribble.ast.NonRoleArg;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.NonRoleArgNode;
import org.scribble.ast.NonRoleParamDecl;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.ProtoDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.name.simple.NonRoleParamNode;
import org.scribble.core.type.kind.DataKind;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.kind.SigKind;
import org.scribble.util.ScribException;
import org.scribble.visit.NameDisambiguator;

public class NonRoleArgListDel extends DoArgListDel
{
	public NonRoleArgListDel()
	{

	}

	// Doing in leave allows the arguments to be individually checked first
	@Override
	public NonRoleArgList leaveDisambiguation(ScribNode child,
			NameDisambiguator disamb, ScribNode visited) throws ScribException
	{
		NonRoleArgList nral = (NonRoleArgList) super.leaveDisambiguation(child,
				disamb, visited);
				// Checks matching arity

		Do<?> parent = (Do<?>) child.getParent();
		ProtoDecl<?> pd = getTargetProtocolDecl((Do<?>) parent, disamb);
		Iterator<NonRoleArg> args = nral.getArgChildren().iterator();
		for (NonRoleParamDecl<?> param : pd.getHeaderChild().getParamDeclListChild()
				.getDeclChildren())
		{
			NonRoleParamKind pkind = param.kind;
			NonRoleArg arg = args.next();
			NonRoleArgNode val = arg.getArgNodeChild();
			if (val.isDataParamNode() || val.isSigParamNode()) 
			{
				if (!((NonRoleParamNode<?>) val).kind.equals(pkind))
				{
					throw new ScribException(arg.getSource(),
							"Invalid arg " + arg + " for param kind: " + pkind);
				}
			}
			else if (pkind.equals(SigKind.KIND))
			{
				if (!val.isSigLitNode() && !val.isSigNameNode())
				{
					throw new ScribException(arg.getSource(),
							"Invalid arg " + arg + " for param kind: " + pkind);
				}
			}
			else if (pkind.equals(DataKind.KIND))
			{
				if (!val.isDataNameNode())
				{
					throw new ScribException(arg.getSource(),
							"Invalid arg " + arg + " for param kind: " + pkind);
				}
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + pkind);
			}
		}

		return nral;
	}

	@Override
	protected NonRoleParamDeclList getDeclList(ProtoDecl<?> pd)
	{
		return pd.getHeaderChild().getParamDeclListChild();
	}
}
