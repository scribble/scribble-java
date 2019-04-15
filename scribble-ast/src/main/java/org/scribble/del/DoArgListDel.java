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

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.Do;
import org.scribble.ast.DoArgList;
import org.scribble.ast.ParamDeclList;
import org.scribble.ast.Module;
import org.scribble.ast.ProtoDecl;
import org.scribble.ast.ScribNode;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.type.name.ProtoName;
import org.scribble.job.JobContext;
import org.scribble.util.ScribException;
import org.scribble.visit.NameDisambiguator;

public abstract class DoArgListDel extends ScribDelBase
{
	public DoArgListDel()
	{

	}

	// Doing in leave allows the arguments to be individually checked first
	@Override
	public DoArgList<?> leaveDisambiguation(ScribNode child,
			NameDisambiguator disamb, ScribNode visited) throws ScribException
	{
		ScribNode parent = child.getParent();
		DoArgList<?> dal = (DoArgList<?>) visited;
		List<?> args = dal.getArgChildren();
		ProtoDecl<?> pd = getTargetProtocolDecl((Do<?>) parent, disamb);
		if (args.size() != getDeclList(pd).getDeclChildren().size())
		{
			throw new ScribException(child.getSource(),
					"Do arity mismatch for " + pd.getHeaderChild() + ": " + args);
		}

		return dal;
	}

	// Not using Do#getTargetProtocolDecl, because that currently relies on namedisamb pass to convert targets to fullnames (because it just gets the full name dependency, it doesn't do visible name resolution)
	protected ProtoDecl<?> getTargetProtocolDecl(Do<?> parent,
			NameDisambiguator disamb) throws ScribException
	{
		ModuleContext mc = disamb.getModuleContext();
		JobContext jc = disamb.job.getContext();
		Do<?> doo = (Do<?>) parent;
		ProtoName<?> pn = doo.getProtocolNameNode().toName();
		/*if (!mc.isVisibleProtocolDeclName(simpname))  // FIXME: should be checked somewhere else?  earlier (do-entry?) -- done
		{
			throw new ScribbleException("Protocol decl not visible: " + simpname);
		}*/
		ProtoName<?> fullname = mc.getVisibleProtocolDeclFullName(pn);  // Lookup in visible names -- not deps, because do target name not disambiguated yet (will be done later this pass)
		Module mod = jc.getModule(fullname.getPrefix());
		//return mod.getProtocolDeclChild(pn.getSimpleName());
		List<ProtoDecl<?>> pd = mod.getProtoDeclChildren().stream()
				.filter(  // cf. Module::hasProtocolDecl
						x -> x.getHeaderChild().getDeclName().equals(pn.getSimpleName()))
				.collect(Collectors.toList());
		if (pd.size() != 1)
		{
			throw new ScribException("[disamb] Target protocol ambiguous or not found: " + pn);
		}
		return pd.get(0);
	}
	
	protected abstract ParamDeclList<?> getDeclList(
			ProtoDecl<?> pd);
}
