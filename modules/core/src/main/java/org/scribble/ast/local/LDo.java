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
package org.scribble.ast.local;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Do;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.ProtocolNameNode;
import org.scribble.del.ScribDel;
import org.scribble.main.JobContext;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

public class LDo extends Do<Local> implements LSimpleInteractionNode
{
	public LDo(CommonTree source, RoleArgList roleinstans, NonRoleArgList arginstans, LProtocolNameNode proto)
	{
		super(source, roleinstans, arginstans, proto);
	}

	@Override
	protected LDo copy()
	{
		return new LDo(this.source, this.roles, this.args, getProtocolNameNode());
	}
	
	@Override
	public LDo clone()
	{
		RoleArgList roles = this.roles.clone();
		NonRoleArgList args = this.args.clone();
		LProtocolNameNode proto = this.getProtocolNameNode().clone();
		return AstFactoryImpl.FACTORY.LDo(this.source, roles, args, proto);
	}
	
	@Override
	public LDo reconstruct(RoleArgList roles, NonRoleArgList args, ProtocolNameNode<Local> proto)
	{
		ScribDel del = del();
		LDo ld = new LDo(this.source, roles, args, (LProtocolNameNode) proto);
		ld = (LDo) ld.del(del);
		return ld;
	}

	@Override
	public LProtocolNameNode getProtocolNameNode()
	{
		return (LProtocolNameNode) this.proto;
	}

	@Override
	public LProtocolName getTargetProtocolDeclFullName(ModuleContext mcontext)
	{
		return (LProtocolName) super.getTargetProtocolDeclFullName(mcontext);
	}

	@Override
	public LProtocolDecl getTargetProtocolDecl(JobContext jcontext, ModuleContext mcontext)
	{
		return (LProtocolDecl) super.getTargetProtocolDecl(jcontext, mcontext);
	}

	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
		ModuleContext mc = fixer.getModuleContext();
		JobContext jc = fixer.job.getContext();
		Role subj = getTargetProtocolDecl(jc, mc).getDef().getBlock()
				.getInteractionSeq().getInteractions().get(0).inferLocalChoiceSubject(fixer);
		// FIXME: need equivalent of (e.g) rec X { continue X; } pruning (cf GRecursion.prune) for irrelevant recursive-do (e.g. proto(A, B, C) { choice at A {A->B.do Proto(A,B,C)} or {A->B.B->C} }))
		Iterator<Role> roleargs = this.roles.getRoles().iterator();
		for (Role decl : getTargetProtocolDecl(jc, mc).header.roledecls.getRoles())
		{
			Role arg = roleargs.next();
			if (decl.equals(subj))
			{
				return arg;
			}
		}
		throw new RuntimeException("Shouldn't get here: " + this);
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Local getKind()
	{
		return LSimpleInteractionNode.super.getKind();
	}

	@Override
	public LInteractionNode merge(LInteractionNode ln) throws ScribbleException
	{
		throw new RuntimeScribbleException("Invalid merge on LDo: " + this);
	}

	@Override
	public boolean canMerge(LInteractionNode ln)
	{
		return false;
	}

	@Override
	public Set<Message> getEnabling()
	{
		return Collections.emptySet();
	}
}
