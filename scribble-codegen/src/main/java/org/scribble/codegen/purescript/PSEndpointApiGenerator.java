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

import java.util.*;

import org.scribble.ast.ImportDecl;
import org.scribble.ast.Module;
import org.scribble.ast.NonProtocolDecl;
import org.scribble.ast.global.GProtocolDecl;

// import org.scribble.codegen.java.endpointapi.SessionApiGenerator;
// import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
// import org.scribble.codegen.java.endpointapi.ioifaces.IOInterfacesGenerator;
import org.scribble.del.ModuleDel;
import org.scribble.main.Job;
import org.scribble.main.JobContext;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EState;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.Role;
import org.scribble.visit.util.MessageIdCollector;

public class PSEndpointApiGenerator
{
	public final Job job;

	public PSEndpointApiGenerator(Job job)
	{
		this.job = job;
	}

	public Map<String, String> generateApi(GProtocolName fullname) throws ScribbleException {
		this.job.debugPrintln("\n[Purescript API gen] Running for " + fullname);

		Module mod = this.job.getContext().getModule(fullname.getPrefix());
		GProtocolName simpname = fullname.getSimpleName();
		GProtocolDecl gpd = (GProtocolDecl) mod.getProtocolDecl(simpname);
		Set<Role> roles = new HashSet<>();
		for (Role r : gpd.header.roledecls.getRoles()) {
			System.out.println(r);
			roles.add(r);
		}

		StringBuilder sb = new StringBuilder();

		String moduleName = fullname.getPrefix().toString();
		String protocolName = fullname.getSimpleName().toString();

		sb.append("module Scribble.Protocol." + fullname + " where\n");
		sb.append("import Scribble.FSM (class Branch, class Initial, class Terminal, class Receive, class Select, class Send, kind Role)\n");
		sb.append("import Type.Row (Cons, Nil)\n");
		sb.append("import Data.Void (Void)\n");

		// Get the foreign type declarations
		// TODO: Only include imports that are used
		// TODO: Make it clear that JSON encoding/decoding instances are required
		// TODO: Group imports by module?
		for (NonProtocolDecl<?> foreignType : mod.getNonProtocolDecls()) {
			if (!foreignType.schema.equals("purescript")) {
				throw new ScribbleException(foreignType.getSource(), "Unsupported data type schema: " + foreignType.schema);
			}
			System.out.println(foreignType); // There is a bug in the parser "type <purescript> Int from Int as Int;"
			sb.append("import " + foreignType.extSource + " (" + foreignType.extName + ")\n");
		}

		// Go through the global protocol and pull out all the message 'data types'
		// TODO: Annotate MessageId with its arguments
		// TODO: Handle polymorphic messages (probably just throw an error)
		Set<MessageId<?>> mids = new HashSet<>();

		MessageIdCollector coll = new MessageIdCollector(this.job, ((ModuleDel) mod.del()).getModuleContext());
		gpd.accept(coll);
		for (MessageId<?> mid : coll.getNames())
		{
            System.out.println(mid);
		 	mids.add(mid);
		}

		System.out.println(mids);
		for (MessageId<?> mid : mids) {
		    String name = getMessageDataTypeName(mid);
			sb.append("data " + name + " = " + name + " -- TODO: Add arguments + derive JSON enc/dec\n");
		}

		// TODO: For each role make a projection, then traverse the graph getting the states + transitions
		for (Role role : roles) {
			String name = getRoleDataTypeName(role);
			sb.append("foreign import data " + role + " :: Role\n");

			JobContext jc = job.getContext();
			EGraph efsm = job.minEfsm ? jc.getMinimisedEGraph(fullname, role) : jc.getEGraph(fullname, role);
			EState init = efsm.init;
			EState term = EState.getTerminal(init);
			System.out.println("Init: " + init);
			System.out.println("Term: " + term);
		}


		Map<String, String> map = new HashMap<String, String>();
		map.put(makePath(moduleName, protocolName), sb.toString());

		return map;
	}

	private static String makePath(String module, String protocol)
	{
		return "Scribble/Protocol/" + module.replace('.', '/') + "/" + protocol + ".purs";
	}

	// Returns the data type name for a message - if it is not a capital letter it will be prefixed by 'M'
	public static String getMessageDataTypeName(MessageId<?> mid)
	{
		String s = mid.toString();
		return (s.isEmpty() || s.charAt(0) < 65 || s.charAt(0) > 90) ? "M" + s : s;  // Hacky? (Yes)
	}

	// Returns the data type name for a role - if it is not a capital letter it will be prefixed by 'R'
	public static String getRoleDataTypeName(Role r)
	{
		String s = r.toString();
		return (s.isEmpty() || s.charAt(0) < 65 || s.charAt(0) > 90) ? "R" + s : s;  // Hacky? (Yes)
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
