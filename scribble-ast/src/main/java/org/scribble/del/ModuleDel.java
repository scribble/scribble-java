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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.NonProtoDecl;
import org.scribble.ast.ProtoDecl;
import org.scribble.ast.ScribNode;
import org.scribble.util.ScribException;
import org.scribble.visit.NameDisambiguator;

public class ModuleDel extends ScribDelBase
{
	public ModuleDel()
	{

	}
		
	@Override
	public Module leaveDisambiguation(ScribNode child, NameDisambiguator disamb,
			ScribNode visited) throws ScribException
	{
		Module mod = (Module) visited;
		// Imports checked in ModuleContext -- that is built before disamb is run
		List<NonProtoDecl<?>> npds = mod.getNonProtoDeclChildren();
		List<String> npdnames = npds.stream()
				.map(x -> x.getDeclName().toString())
				.collect(Collectors.toList());
				// Have to use Strings, as can be different kinds (datatype, sig)
		final Set<String> dups1 = getDuplicates(npdnames);
		if (!dups1.isEmpty())
		{
			NonProtoDecl<?> first =
					npds.stream()
							.filter(x -> dups1.contains(x.getDeclName().toString()))
							.collect(Collectors.toList()).get(0);
			throw new ScribException(first.getSource(),
					"Duplicate non-protocol decls: " + first.getDeclName());
		}
		List<ProtoDecl<?>> pds = mod.getProtoDeclChildren();
		List<String> pdnames = pds.stream()
				.map(pd -> pd.getHeaderChild().getDeclName().toString())
				.collect(Collectors.toList());
				// Have to use Strings, as can be different kinds (global, local)
		final Set<String> dups2 = getDuplicates(pdnames);
		if (pds.size() != pdnames.stream().distinct().count())
		if (!dups2.isEmpty())
		{
			ProtoDecl<?> first =
						pds.stream()
								.filter(x -> dups2
										.contains(x.getHeaderChild().getDeclName().toString()))
								.collect(Collectors.toList()).get(0);
				throw new ScribException(first.getSource(),
						"Duplicate protocol decls: "
								+ first.getHeaderChild().getDeclName());
						// Global and locals also required to be distinct
		}
		return mod;
	}
	
	private static Set<String> getDuplicates(Collection<String> ss)
	{
		Set<String> uniques = new HashSet<>();
		Set<String> dups = new HashSet<>();
		for (String npd : ss)
		{
			if (!uniques.add(npd))
			{
				dups.add(npd);
			}
		}
		return dups;
	}
}
