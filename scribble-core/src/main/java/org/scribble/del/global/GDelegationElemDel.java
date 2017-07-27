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
package org.scribble.del.global;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.MessageTransfer;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.global.GDelegationElem;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.ProtocolName;
import org.scribble.type.name.Role;
import org.scribble.visit.context.ProtocolDeclContextBuilder;
import org.scribble.visit.wf.DelegationProtocolRefChecker;
import org.scribble.visit.wf.NameDisambiguator;

public class GDelegationElemDel extends ScribDelBase
{
	public GDelegationElemDel()
	{
	
	}

	// Duplicated from DoDel
	@Override
	public void enterDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb) throws ScribbleException
	{
		ModuleContext mc = disamb.getModuleContext();
		GDelegationElem de = (GDelegationElem) child;
		GProtocolName gpn = de.proto.toName();
		if (!mc.isVisibleProtocolDeclName(gpn))
		{
			throw new ScribbleException(de.proto.getSource(), "Protocol decl not visible: " + gpn);
		}
	}

	// Duplicated from DoDel
	//@Override
	//public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	public GDelegationElem visitForNameDisambiguation(NameDisambiguator disamb, GDelegationElem de) throws ScribbleException
	{
		//DelegationElem de = (DelegationElem) visited;
		ModuleContext mc = disamb.getModuleContext();
		GProtocolName fullname = (GProtocolName) mc.getVisibleProtocolDeclFullName(de.proto.toName());

		Role rn = de.role.toName();
		ProtocolDecl<Global> gpd = disamb.job.getContext().getModule(fullname.getPrefix()).getProtocolDecl(fullname.getSimpleName());
		if (!gpd.header.roledecls.getRoles().contains(rn))
		{
			throw new ScribbleException(de.role.getSource(), "Invalid delegation role: " + de);
		}

		GProtocolNameNode pnn = (GProtocolNameNode) disamb.job.af.QualifiedNameNode(de.proto.getSource(), fullname.getKind(), fullname.getElements());  // Not keeping original namenode del
		return de.reconstruct(pnn, de.role);
	}

	/*// Is this needed?  Or DataTypeNodes always created from AmbigNameNode? (in this same pass)
	@Override
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited)
			throws ScribbleException
	{
		ModuleContext mc = disamb.getModuleContext();
		DataTypeNode dtn = (DataTypeNode) visited;
		DataType fullname = mc.getVisibleDataTypeFullName(dtn.toName());
		return (DataTypeNode)
				AstFactoryImpl.FACTORY.QualifiedNameNode(DataTypeKind.KIND, fullname.getElements());  // Didn't keep original del
	}*/

	//@Override
	//public DelegationElem leaveProtocolDeclContextBuilding(ScribNodeScribNode parent, ScribNode child, ProtocolDeclContextBuilder builder, ScribNode visited) throws ScribbleException  // FIXME: cannot access MessageTransfer roles from here
	// FIXME: apply this pattern (delegate from parent back to child class) to all other existing instances
  // FIXME: should always be GMessageTransfer
	public void leaveMessageTransferInProtocolDeclContextBuilding(MessageTransfer<?> mt, GDelegationElem de, ProtocolDeclContextBuilder builder) throws ScribbleException
	{
		GProtocolName gpn = de.proto.toName();  // leaveDisambiguation has fully qualified the target name
		builder.addGlobalProtocolDependency(mt.src.toName(), gpn, de.role.toName());  // FIXME: does it make sense to use projection role as dependency target role? (seems to be used for Job.getProjections)
		mt.getDestinationRoles().forEach((r) -> builder.addGlobalProtocolDependency(r, gpn, de.role.toName()));
	}

	@Override
	public void enterDelegationProtocolRefCheck(ScribNode parent, ScribNode child, DelegationProtocolRefChecker checker) throws ScribbleException
	{
		GDelegationElem de = (GDelegationElem) child;
		ModuleContext mc = checker.getModuleContext();
		GProtocolName targetfullname = (GProtocolName) mc.getVisibleProtocolDeclFullName(de.proto.toName());
		GProtocolName rootfullname = (GProtocolName) mc.getVisibleProtocolDeclFullName(checker.getProtocolDeclOnEntry().header.getDeclName());
		if (targetfullname.equals(rootfullname))  // Explicit check here because ProtocolDeclContextBuilder dependencies explicitly include self protocoldecl dependencies (cf. GProtocolDeclDel.addSelfDependency)
		{
			throw new ScribbleException(de.getSource(), "Recursive protocol dependencies not supported for delegation types: " + de);
		}
		
		Set<GProtocolName> todo = new LinkedHashSet<GProtocolName>();
		ProtocolDecl<Global> targetgpd = checker.job.getContext().getModule(targetfullname.getPrefix()).getProtocolDecl(targetfullname.getSimpleName());  // target
		// FIXME: does this already contain transitive do-dependencies?  But doesn't contain transitive delegation-dependencies..?
		Set<GProtocolName> init = 
				((GProtocolDeclDel) targetgpd.del()).getProtocolDeclContext().getDependencyMap().getDependencies()
				.values().stream().flatMap((v) -> v.keySet().stream()).collect(Collectors.toSet());
		todo.addAll(init);

		Set<GProtocolName> seen = new HashSet<>();
		while (!todo.isEmpty())
		{
			Iterator<GProtocolName> it = todo.iterator();
			GProtocolName next = it.next();
			it.remove();
			seen.add(next);

			ProtocolName<Global> nextfullname = mc.getVisibleProtocolDeclFullName(next);
			if (rootfullname.equals(nextfullname))
			{
				throw new ScribbleException(de.getSource(), "Recursive protocol dependencies not supported for delegation types: " + de);
			}
			ProtocolDecl<Global> nextgpd = checker.job.getContext().getModule(targetfullname.getPrefix()).getProtocolDecl(nextfullname.getSimpleName());
			Set<GProtocolName> tmp = 
					((GProtocolDeclDel) nextgpd.del()).getProtocolDeclContext().getDependencyMap().getDependencies()
					.values().stream().flatMap((v) -> v.keySet().stream())
					.filter((n) -> !seen.contains(n))
					.collect(Collectors.toSet());
			todo.addAll(tmp);
		}
	}
}
