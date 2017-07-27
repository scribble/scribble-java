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

import org.scribble.ast.Do;
import org.scribble.ast.DoArgList;
import org.scribble.ast.HeaderParamDeclList;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.main.JobContext;
import org.scribble.main.ScribbleException;
import org.scribble.type.name.ProtocolName;
import org.scribble.visit.wf.NameDisambiguator;

public abstract class DoArgListDel extends ScribDelBase
{
	public DoArgListDel()
	{

	}

	// Doing in leave allows the arguments to be individually checked first
	@Override
	public DoArgList<?> leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		DoArgList<?> dal = (DoArgList<?>) visited;
		List<?> args = dal.getDoArgs();
		ProtocolDecl<?> pd = getTargetProtocolDecl((Do<?>) parent, disamb);
		if (args.size() != getParamDeclList(pd).getDecls().size())
		{
			throw new ScribbleException(visited.getSource(), "Do arity mismatch for " + pd.header + ": " + args);
		}

		return dal;
	}

	// Not using Do#getTargetProtocolDecl, because that currently relies on namedisamb pass to convert targets to fullnames (because it just gets the full name dependency, it doesn't do visible name resolution)
	protected ProtocolDecl<?> getTargetProtocolDecl(Do<?> parent, NameDisambiguator disamb) throws ScribbleException
	{
		ModuleContext mc = disamb.getModuleContext();
		JobContext jc = disamb.job.getContext();
		Do<?> doo = (Do<?>) parent;
		ProtocolName<?> pn = doo.proto.toName();
		/*if (!mc.isVisibleProtocolDeclName(simpname))  // FIXME: should be checked somewhere else?  earlier (do-entry?) -- done
		{
			throw new ScribbleException("Protocol decl not visible: " + simpname);
		}*/
		ProtocolName<?> fullname = mc.getVisibleProtocolDeclFullName(pn);  // Lookup in visible names -- not deps, because do target name not disambiguated yet (will be done later this pass)
		return jc.getModule(fullname.getPrefix()).getProtocolDecl(pn.getSimpleName());
	}
	
	protected abstract HeaderParamDeclList<?> getParamDeclList(ProtocolDecl<?> pd);
}
