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
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.Do;
import org.scribble.ast.ScribNode;
import org.scribble.ast.name.qualified.ProtoNameNode;
import org.scribble.ast.name.simple.IdNode;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.type.kind.ProtoKind;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.ProtoName;
import org.scribble.util.ScribException;
import org.scribble.visit.NameDisambiguator;

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
		ProtoNameNode<?> proto = doo.getProtocolNameNode();
		ProtoName<?> simpname = proto.toName();
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
		return leaveDisambiguationAux((Do<?>) visited, disamb);
				// To introduce type parameter
	}
	
	// Convert all visible names to full names for protocol inlining: otherwise could get clashes if directly inlining external visible names under the root modulecontext
	// Not done in G/LProtocolNameNodeDel because it's only for do-targets that this is needed (cf. ProtocolHeader)
	private <K extends ProtoKind> ScribNode leaveDisambiguationAux(
			Do<K> visited, NameDisambiguator disamb) throws ScribException
	{
		ProtoNameNode<K> proto = visited.getProtocolNameNode();

		// CHECKME: do full name expansion in disamb?  or leave to imed translation? -- FIXME: sort out full name expansion between here and GDoDel.translate
		ProtoNameNode<K> n = makeProtoNameNode(disamb, proto);
		
		// Didn't keep original namenode del -- ?
		return visited.reconstruct(n,
				visited.getNonRoleListChild(), visited.getRoleListChild());
	}
	
	private <K extends ProtoKind> ProtoNameNode<K> makeProtoNameNode(
			NameDisambiguator disamb, ProtoNameNode<K> proto)
	{
		ModuleContext modc = disamb.getModuleContext();
		ProtoName<K> fullname = modc
				.getVisibleProtocolDeclFullName(proto.toName());
		if (fullname instanceof GProtoName)
		{
			List<IdNode> elems = Arrays.asList(fullname.getElements()).stream()
					.map(x -> disamb.job.config.af.IdNode(null, x))
					.collect(Collectors.toList());
			@SuppressWarnings("unchecked") // FIXME
			ProtoNameNode<K> cast = (ProtoNameNode<K>) disamb.job.config.af
					.GProtoNameNode(proto.token, elems);
			return cast;
		}
		throw new RuntimeException("[TODO] " + fullname.getKind() + ": " + fullname);
	}
}
