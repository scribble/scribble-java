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

import java.util.Arrays;

import org.scribble.ast.Do;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.name.qualified.ProtocolNameNode;
import org.scribble.ast.name.simple.AmbigNameNode;
import org.scribble.job.JobContext;
import org.scribble.job.ScribbleException;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.ProtocolName;
import org.scribble.type.name.Role;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.context.ProtocolDeclContextBuilder;
import org.scribble.visit.wf.NameDisambiguator;

import antlr.Token;

public abstract class DoDel extends SimpleInteractionNodeDel
{
	public DoDel()
	{

	}

	@Override
	public void enterDisambiguation(ScribNode parent, ScribNode child,
			NameDisambiguator disamb) throws ScribbleException
	{
		ModuleContext mc = disamb.getModuleContext();
		Do<?> doo = (Do<?>) child;
		ProtocolNameNode<?> proto = doo.getProtocolNameNode();
		ProtocolName<?> simpname = proto.toName();
		if (!mc.isVisibleProtocolDeclName(simpname))  // CHECKME: do on entry here, before visiting DoArgListDel
		{
			throw new ScribbleException(proto.getSource(),
					"Protocol decl not visible: " + simpname);
		}
	}

	@Override
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child,
			NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		return leaveDisambiguationAux(parent, child, disamb, visited);
				// To introduce type parameter
	}
	
	// Convert all visible names to full names for protocol inlining: otherwise could get clashes if directly inlining external visible names under the root modulecontext
	// Not done in G/LProtocolNameNodeDel because it's only for do-targets that this is needed (cf. ProtocolHeader)
	// TODO: refactor into G/LDoDel to bypass generic cast issues
	private <K extends ProtocolKind> ScribNode leaveDisambiguationAux(
			ScribNode parent, ScribNode child, NameDisambiguator disamb,
			ScribNode visited) throws ScribbleException
	{
		@SuppressWarnings("unchecked")  // Doesn't matter what K is, just need to propagate it down
		Do<K> doo = (Do<K>) visited;
		ModuleContext mc = disamb.getModuleContext();
		ProtocolNameNode<K> proto = doo.getProtocolNameNode();
		ProtocolName<K> fullname = mc
				.getVisibleProtocolDeclFullName(proto.toName());

		ProtocolNameNode<K> pnn;/* = (ProtocolNameNode<K>) disamb.job.config.af
				.QualifiedNameNode(proto.getSource(), fullname.getKind(),
						fullname.getElements());*/
		
		// FIXME: refactor, make an AST node and set its children -- rework AstFactory?
		// FIXME: issue is to make an AST node, need a Token (but those are mainly obtained by parsing only)
		/*pnn = (ProtocolNameNode<K>) new GProtocolNameNode(proto.token);
		pnn.addChildren(proto.getChildren());*/
		/*pnn = (ProtocolNameNode<K>) proto.dupNode();  // Token and start/stopIndex
		pnn.addChildren(Arrays.asList(fullname.getElements()).stream().map(x -> new AmbigNameNode(new Token(arg0, arg1))));*/
		pnn = proto;
		/*(fullname.getKind().equals(Global.KIND) ?
			new GProtocolNameNode(proto.getSource(), fullname.getElements()) :
					new LProtocolNameNode(proto.getSource(), fullname.getElements())
		);*/
		
		//HERE either hack Token to make new AST node, or redo proto context building on intermed -- consideration is projection output a la parsed AST subprotos, or from (inlined?) intermed
		// probably hack Token for now -- or just skip protocol context building (do inlning and WF/valid first)
		
						// Didn't keep original namenode del
		return doo.reconstruct(doo.getRoleListChild(), doo.getNonRoleListChild(),
				pnn);
	}

	@Override
	public Do<?> leaveProtocolDeclContextBuilding(ScribNode parent,
			ScribNode child, ProtocolDeclContextBuilder builder, ScribNode visited)
			throws ScribbleException
	{
		JobContext jcontext = builder.job.getJobContext();
		ModuleContext mcontext = builder.getModuleContext();
		Do<?> doo = (Do<?>) visited;
		ProtocolName<?> pn = doo.getProtocolNameNode().toName();  // leaveDisambiguation has fully qualified the target name
		doo.getRoleListChild().getRoles()
				.forEach(r -> addProtocolDependency(builder, r, pn,
						doo.getTargetRoleParameter(jcontext, mcontext, r)));
		return doo;
	}

	protected abstract void addProtocolDependency(
			ProtocolDeclContextBuilder builder, Role self, ProtocolName<?> proto,
			Role target);

	@Override
	public void enterProtocolInlining(ScribNode parent, ScribNode child,
			ProtocolDefInliner inl) throws ScribbleException
	{
		super.enterProtocolInlining(parent, child, inl);
		if (!inl.isCycle())
		{
			SubprotoSig subsig = inl.peekStack();  // SubprotocolVisitor has already entered subprotocol
			inl.setSubprotocolRecVar(subsig);
		}
	}
}
