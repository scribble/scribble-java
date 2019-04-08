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

import org.scribble.ast.Do;
import org.scribble.ast.ScribNode;
import org.scribble.ast.name.qualified.ProtocolNameNode;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.util.ScribException;
import org.scribble.visit.wf.NameDisambiguator;

public abstract class DoDel extends SimpleSessionNodeDel
{
	public DoDel()
	{

	}

	@Override
	public void enterDisambiguation(ScribNode child, NameDisambiguator disamb)
			throws ScribException
	{
		ModuleContext mc = disamb.getModuleContext();
		Do<?> doo = (Do<?>) child;
		ProtocolNameNode<?> proto = doo.getProtocolNameNode();
		ProtocolName<?> simpname = proto.toName();
		if (!mc.isVisibleProtocolDeclName(simpname))  // CHECKME: do on entry here, before visiting DoArgListDel
		{
			throw new ScribException(proto.getSource(),
					"Protocol decl not visible: " + simpname);
		}
	}

	@Override
	public ScribNode leaveDisambiguation(ScribNode child,
			NameDisambiguator disamb, ScribNode visited) throws ScribException
	{
		return leaveDisambiguationAux(child, disamb, visited);
				// To introduce type parameter
	}
	
	// Convert all visible names to full names for protocol inlining: otherwise could get clashes if directly inlining external visible names under the root modulecontext
	// Not done in G/LProtocolNameNodeDel because it's only for do-targets that this is needed (cf. ProtocolHeader)
	// TODO: refactor into G/LDoDel to bypass generic cast issues
	private <K extends ProtocolKind> ScribNode leaveDisambiguationAux(
			ScribNode child, NameDisambiguator disamb,
			ScribNode visited) throws ScribException
	{
		@SuppressWarnings("unchecked")  // Doesn't matter what K is, just need to propagate it down
		Do<K> doo = (Do<K>) visited;
		ProtocolNameNode<K> proto = doo.getProtocolNameNode();
		/*ModuleContext mc = disamb.lang.getMainModuleContext();
		ProtocolName<K> fullname = mc
				.getVisibleProtocolDeclFullName(proto.toName());*/

		ProtocolNameNode<K> pnn;/* = (ProtocolNameNode<K>) disamb.job.config.af
				.QualifiedNameNode(proto.getSource(), fullname.getKind(),
						fullname.getElements());*/
		
		// CHECKME: refactor, make an AST node and set its children -- rework AstFactory?
		// CHECKME: issue is to make an AST node, need a Token (but those are mainly obtained by parsing only)
		/*pnn = (ProtocolNameNode<K>) new GProtocolNameNode(proto.token);
		pnn.addChildren(proto.getChildren());*/
		/*pnn = (ProtocolNameNode<K>) proto.dupNode();  // Token and start/stopIndex
		pnn.addChildren(Arrays.asList(fullname.getElements()).stream().map(x -> new AmbigNameNode(new Token(arg0, arg1))));*/
		pnn = proto;
		
		//HERE either hack Token to make new AST node, or redo proto context building on intermed -- consideration is projection output a la parsed AST subprotos, or from (inlined?) intermed
		// probably hack Token for now -- or just skip protocol context building (do inlning and WF/valid first)
		
		// Didn't keep original namenode del -- ?
		return doo.reconstruct(doo.getRoleListChild(), doo.getNonRoleListChild(),
				pnn);
	}
}
