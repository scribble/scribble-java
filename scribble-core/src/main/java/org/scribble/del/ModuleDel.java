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
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.ImportDecl;
import org.scribble.ast.Module;
import org.scribble.ast.ModuleDecl;
import org.scribble.ast.NonProtocolDecl;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.job.ScribbleException;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.LProtocolName;
import org.scribble.type.name.Role;
import org.scribble.visit.context.ModuleContextBuilder;
import org.scribble.visit.context.Projector;
import org.scribble.visit.wf.NameDisambiguator;

public class ModuleDel extends ScribDelBase
{
	private ModuleContext mcontext;
	
	public ModuleDel()
	{

	}

	private ModuleDel copy()
	{
		return new ModuleDel();
	}

	@Override
	public void enterModuleContextBuilding(ScribNode parent, ScribNode child,
			ModuleContextBuilder builder) throws ScribbleException
	{
		builder.setModuleContext(new ModuleContext(
				builder.job.getContext().getParsed(), (Module) child));
				// ModuleContext building is done solely by "new ModuleContext" (no deeper visiting needed)
				// The only thing ModuleContextBuilder really does is to set the ModuleContext in ModuleDel
				// FIXME: obtain from MainContext instead of creating on entry by this visitor
	}

	// Maybe better to create on enter, so can be used during the context build pass (Context would need to be "cached" in the visitor to be accessed)
	@Override
	public Module leaveModuleContextBuilding(ScribNode parent, ScribNode child,
			ModuleContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		ModuleDel del = setModuleContext(builder.getModuleContext());
		return (Module) visited.del(del);
	}
		
	@Override
	public Module leaveDisambiguation(ScribNode parent, ScribNode child,
			NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		Module mod = (Module) visited;
		// Imports checked in ModuleContext -- that is built before disamb is run
		List<NonProtocolDecl<?>> npds = mod.getNonProtoDeclChildren();
		List<String> npdnames = npds.stream()
				.map(x -> x.getDeclName().toString())
				.collect(Collectors.toList());
				// Have to use Strings, as can be different kinds (datatype, sig)
		final Set<String> dups1 = getDuplicates(npdnames);
		//if (npds.size() != npdnames.stream().distinct().count())
		if (!dups1.isEmpty())
		{
			NonProtocolDecl<?> first =
					npds.stream()
							.filter(x -> dups1.contains(x.getDeclName().toString()))
							.collect(Collectors.toList()).get(0);
			throw new ScribbleException(first.getSource(),
					"Duplicate non-protocol decls: " + first.getDeclName());
		}
		List<ProtocolDecl<?>> pds = mod.getProtoDeclChildren();
		List<String> pdnames = pds.stream()
				.map(pd -> pd.getHeaderChild().getDeclName().toString())
				.collect(Collectors.toList());
				// Have to use Strings, as can be different kinds (global, local)
		final Set<String> dups2 = getDuplicates(pdnames);
		if (pds.size() != pdnames.stream().distinct().count())
		if (!dups2.isEmpty())
		{
			ProtocolDecl<?> first =
						pds.stream()
								.filter(x -> dups2
										.contains(x.getHeaderChild().getDeclName().toString()))
								.collect(Collectors.toList()).get(0);
				throw new ScribbleException(first.getSource(),
						"Duplicate protocol decls: "
								+ first.getHeaderChild().getDeclName());
						// Global and locals also required to be distinct
		}
		return mod;
	}
	
	private static Set<String> getDuplicates(Collection<String> ss)
	{
		Set<String> uniques = new HashSet<>();
		Set
		<String> dups = new HashSet<>();
		for (String npd : ss)
		{
			if (!uniques.add(npd))
			{
				dups.add(npd);
			}
		}
		return dups;
	}

	@Override
	public Module leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited)
	{
		proj.job.getContext().addProjections(proj.getProjections());
		return (Module) visited;
	}

	// lpd is the projected local protocol
	public Module createModuleForProjection(Projector proj, Module root,
			GProtocolDecl gpd, LProtocolDecl lpd, Map<GProtocolName, Set<Role>> deps)
	{
		ModuleDecl rootModDecl = root.getModuleDeclChild();
		ModuleNameNode modname = Projector.makeProjectedModuleNameNode(
				proj.job.config.af, rootModDecl.getNameNodeChild().getSource(),
						// Or ignore blame source for purely generated?
				rootModDecl.getFullModuleName(), lpd.getHeaderChild().getDeclName());
		ModuleDecl modDecl = proj.job.config.af
				.ModuleDecl(root.getModuleDeclChild().getSource(), modname);
		List<ImportDecl<?>> imports = new LinkedList<>();
		for (GProtocolName gpn : deps.keySet())
		{
			for (Role role : deps.get(gpn))
			{
				LProtocolName targetsimpname = Projector
						.projectSimpleProtocolName(gpn.getSimpleName(), role);
				ModuleNameNode targetmodname = Projector.makeProjectedModuleNameNode(proj.job.config.af, //null,  // CHECKME? projected import sources?

						new CommonTree(),  // FIXME -- 

							gpn.getPrefix(), targetsimpname);
				if (!targetmodname.toName().equals(modname.toName()))  // Self dependency -- each projected local is in its own module now, so can compare module names
				{
					imports
							.add(proj.job.config.af.ImportModule(null, targetmodname, null));
							// CHECKME? projected import sources?
				}
			}
		}
		
		List<NonProtocolDecl<?>> data = 
				new LinkedList<>(root.getNonProtoDeclChildren());  
				// CHECKME: copy?  // FIXME: only project the dependencies
		List<ProtocolDecl<?>> protos = Arrays.asList(lpd);
		return proj.job.config.af.Module(gpd.getHeaderChild().getSource(), modDecl,
				imports, data, protos);
	}
	
	@Override 
	public String toString()
	{
		return (this.mcontext == null) ? null : this.mcontext.toString();  // null before and during context building
	}
	
	public ModuleContext getModuleContext()
	{
		return this.mcontext;
	}
	
	protected ModuleDel setModuleContext(ModuleContext mcontext)
	{
		ModuleDel copy = copy();  // FIXME: should be a deep clone in principle
		copy.mcontext = mcontext;
		return copy;
	}
}
