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
package org.scribble.codegen.purescript;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.Module;
import org.scribble.ast.global.GProtocolDecl;

// import org.scribble.codegen.java.endpointapi.SessionApiGenerator;
// import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
// import org.scribble.codegen.java.endpointapi.ioifaces.IOInterfacesGenerator;
import org.scribble.main.Job;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;

public class PSEndpointApiGenerator
{
	public final Job job;

	public PSEndpointApiGenerator(Job job)
	{
		this.job = job;
	}

	public Map<String, String> generateApi(GProtocolName protocol) throws ScribbleException {
		this.job.debugPrintln("\n[Purescript API gen] Running for " + protocol);

		Module mod = this.job.getContext().getModule(protocol.getPrefix());
		GProtocolName simpname = protocol.getSimpleName();
		GProtocolDecl gpd = (GProtocolDecl) mod.getProtocolDecl(simpname);
		Set<Role> roles = new HashSet<>();
		for (Role r : gpd.header.roledecls.getRoles()) {
			System.out.println(r);
			roles.add(r);
		}

		StringBuilder sb = new StringBuilder();

		String moduleName = protocol.getPrefix().toString();
		String protocolName = protocol.getSimpleName().toString();

		sb.append("module Scribble.Protocol." + protocol + " where\n");
		sb.append("import Scribble.FSM (class Branch, class Initial, class Terminal, class Receive, class Select, class Send, kind Role)\n");
		sb.append("import Type.Row (Cons, Nil)\n");
		sb.append("import Data.Void (Void)\n");

		Map<String, String> map = new HashMap<String, String>();
		map.put(makePath(moduleName, protocolName), sb.toString());

		return map;
	}

	private static String makePath(String module, String protocol)
	{
		return "Scribble/Protocol/" + module.replace('.', '/') + "/" + protocol + ".purs";
	}

	
//	// FIXME: refactor an EndpointApiGenerator -- ?
//	public Map<String, String> generateStateChannelApi(GProtocolName fullname, Role self, boolean subtypes) throws ScribbleException
//	{
//		/*JobContext jc = this.job.getContext();
//		if (jc.getEndpointGraph(fullname, self) == null)
//		{
//			buildGraph(fullname, self);
//		}*/
//		job.debugPrintln("\n[Java API gen] Running " + StateChannelApiGenerator.class + " for " + fullname + "@" + self);
//		StateChannelApiGenerator apigen = new StateChannelApiGenerator(this.job, fullname, self);
//		IOInterfacesGenerator iogen = null;
//		try
//		{
//			iogen = new IOInterfacesGenerator(apigen, subtypes);
//		}
//		catch (RuntimeScribbleException e)  // FIXME: use IOInterfacesGenerator.skipIOInterfacesGeneration
//		{
//			//System.err.println("[Warning] Skipping I/O Interface generation for protocol featuring: " + fullname);
//			this.job.warningPrintln("Skipping I/O Interface generation for: " + fullname + "\n  Cause: " + e.getMessage());
//		}
//		// Construct the Generators first, to build all the types -- then call generate to "compile" all Builders to text (further building changes will not be output)
//		Map<String, String> api = new HashMap<>(); // filepath -> class source  // Store results?
//		api.putAll(apigen.generateApi());
//		if (iogen != null)
//		{
//			api.putAll(iogen.generateApi());
//		}
//		return api;
//	}
}
