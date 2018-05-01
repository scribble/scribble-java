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
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.Payload;
import org.scribble.type.kind.PayloadTypeKind;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.PayloadElemType;
import org.scribble.type.name.Role;
import org.scribble.util.Pair;
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
		Map<MessageId<?>, Payload> datatypes = new HashMap<>();

		MessageIdCollector coll = new MessageIdCollector(this.job, ((ModuleDel) mod.del()).getModuleContext());
		gpd.accept(coll);
		for (MessageId<?> mid : coll.getNames())
		{
            System.out.println(mid);
		 	mids.add(mid);
		}

		// TODO: For each role make a projection, then traverse the graph getting the states + transitions
		for (Role role : roles) {
			String name = getRoleDataTypeName(role);
			sb.append("foreign import data " + role + " :: Role\n");

			JobContext jc = job.getContext();
			EGraph efsm = job.minEfsm ? jc.getMinimisedEGraph(fullname, role) : jc.getEGraph(fullname, role);
			EState init = efsm.init;
			EState term = EState.getTerminal(init);

            sb.append("instance initial" + role + " " + getRoleDataTypeName(role) + " :: Initial " + getRoleDataTypeName(role) + " " + getStateTypeName(init) + "\n");
            if (term != null) {
                sb.append("instance terminal" + role + " " + getRoleDataTypeName(role) + " :: Terminal " + getRoleDataTypeName(role) + " " + getStateTypeName(term) + "\n");
            } else {
                sb.append("instance terminal" + role + " " + getRoleDataTypeName(role) + " :: Terminal " + getRoleDataTypeName(role) + "Void \n");
            }

			Set<EState> seen = new HashSet<>();
            Set<EState> level = new HashSet<>();

            // Perform a breadth first traversal over the graph
            level.add(init);

            while (!level.isEmpty()) {
                Set<EState> nextlevel = new HashSet<>();
                for (EState s : level) {
                    // Don't generate more than once
                    if (seen.contains(s)) break;
                    seen.add(s);

                    sb.append("foreign import data " + getStateTypeName(s) + " :: Type\n");

                    // View the successors during the next level
                    nextlevel.addAll(s.getAllSuccessors());

        			switch (s.getStateKind()) {
        				case OUTPUT: {
        				    assert (s.getAllActions().size() > 0);
        				    if (s.getAllActions().size() == 1) {
        				        EState to = s.getAllSuccessors().get(0);
                                EAction action = s.getAllActions().get(0);
        				        String type = getMessageDataTypeName(action.mid);
                                String toR = getRoleDataTypeName(action.obj);
                                sb.append("instance send" + getStateTypeName(s) + " :: Send " + toR + " " +  getStateTypeName(s) + " " + getStateTypeName(to) + " " + type + "\n");
                                addDatatype(datatypes, action);
                            } else {
                                Set<Pair<String, String>> options = new HashSet<>();
                                for (int i = 0; i < s.getAllActions().size(); i++) {
                                    // If there are multiple output actions, we should treat it as a branch and create some dummy states
                                    // where we send the label
                                    EAction action = s.getAllActions().get(i);
                                    addDatatype(datatypes, action);
                                    EState to = s.getAllSuccessors().get(i);
                                    String labelState = getStateTypeName(s) + action.mid;
                                    String label = getLabelFromMessage(action.mid);
            				        String type = getMessageDataTypeName(action.mid);
                                    String toR = getRoleDataTypeName(action.obj);
                                    sb.append("foreign import data " + labelState +  " :: Type\n");
                                    sb.append("instance send" + labelState + " :: Send " + toR + " " +  labelState + " " + getStateTypeName(to) + " " + type + "\n");

                                    options.add(new Pair(label,labelState));
                                }

                                // All messages must be to the same role
                                EAction action = s.getAllActions().get(0);
                                String toR = getRoleDataTypeName(action.obj);
                                sb.append("instance select" + getStateTypeName(s) + " :: Select " + toR + " " +  getStateTypeName(s) + " ");
                                String end = "Nil";
                                for (Pair<String, String> option : options) {
                                    sb.append("(Cons \"" + option.left + "\" " + option.right + " ");
                                    end += ")";
                                }
                                sb.append(end + "\n");
                            }
                        }
        					break;
        				case UNARY_INPUT: {
        				        EState from = s.getAllSuccessors().get(0);
                                EAction action = s.getAllActions().get(0);
                                addDatatype(datatypes, action);
        				        String type = getMessageDataTypeName(action.mid);
                                String fromR = getRoleDataTypeName(action.obj);
                                sb.append("instance receive" + getStateTypeName(s) + " :: Receive " + fromR + " " +  getStateTypeName(s) + " " + getStateTypeName(from) + " " + type + "\n");
        				}
        					break;
        				case POLY_INPUT: {
                                Set<Pair<String, String>> options = new HashSet<>();
                                for (int i = 0; i < s.getAllActions().size(); i++) {
                                    // If there are multiple output actions, we should treat it as a branch and create some dummy states
                                    // where we send the label
                                    EAction action = s.getAllActions().get(i);
                                    addDatatype(datatypes, action);
                                    EState to = s.getAllSuccessors().get(i);
                                    String labelState = getStateTypeName(s) + action.mid;
                                    String label = getLabelFromMessage(action.mid);
            				        String type = getMessageDataTypeName(action.mid);
                                    String fromR = getRoleDataTypeName(action.obj);
                                    sb.append("foreign import data " + labelState +  " :: Type\n");
                                    sb.append("instance receive" + labelState + " :: Receive " + fromR + " " +  labelState + " " + getStateTypeName(to) + " " + type + "\n");

                                    options.add(new Pair(label,labelState));
                                }

                                // All messages must be to the same role
                                EAction action = s.getAllActions().get(0);
                                String atR = getRoleDataTypeName(role);
                                sb.append("instance branch" + getStateTypeName(s) + " :: Branch " + atR + " " +  getStateTypeName(s) + " ");
                                String end = "Nil";
                                for (Pair<String, String> option : options) {
                                    sb.append("(Cons \"" + option.left + "\" " + option.right + " ");
                                    end += ")";
                                }
                                sb.append(end + "\n");
                        }
        					break;
        				case TERMINAL:
        					break;
        				case ACCEPT:
                            throw new ScribbleException(null, "Unsupported action " + s.getStateKind());
                        case WRAP_SERVER:
                            throw new ScribbleException(null, "Unsupported action " + s.getStateKind());
        			}

        		}


                level = nextlevel;
            }

        }

        for (MessageId<?> mid : datatypes.keySet()) {
            String name = getMessageDataTypeName(mid);
            sb.append("data " + name + " = " + name);
            for (PayloadElemType<? extends PayloadTypeKind> type : datatypes.get(mid).elems) {
                sb.append(" " + type);
            }
            sb.append("-- TODO: Derive JSON enc/dec\n");
        }

       System.out.println(mids);
       for (MessageId<?> mid : mids) {
           String name = getMessageDataTypeName(mid);
           sb.append("data " + name + " = " + name + " -- TODO: Add arguments + derive JSON enc/dec\n");
       }

		Map<String, String> map = new HashMap<String, String>();
		map.put(makePath(moduleName, protocolName), sb.toString());

		return map;
	}

	private void addDatatype(Map<MessageId<?>, Payload> datatypes, EAction action) throws ScribbleException {
	    if (datatypes.containsKey(action.mid)) {
	        System.out.println(action.mid + " already there");
	        System.out.println(datatypes.keySet().toString());
	        System.out.println(datatypes.get(action.mid) + " - " + action.payload);
	        if (!datatypes.get(action.mid).equals(action.payload)) {
                System.out.println("and different!");
                throw new ScribbleException(null, "Messages with the same name `" + action.mid + "` must have the same payload");
            }
        } else {
	        datatypes.put(action.mid, action.payload);
        }
    }

    private String getLabelFromMessage(MessageId<?> mid) {
        String s = mid.toString().toLowerCase();
        return (s.isEmpty() || s.charAt(0) < 97 || s.charAt(0) > 122) ? "l" + s : s;  // Hacky? (Yes)
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

	public static String getStateTypeName(EState s)
    {
	    return "S" + s;
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
