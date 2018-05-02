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

import org.scribble.ast.Module;
import org.scribble.ast.NonProtocolDecl;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.codegen.purescript.endpointapi.DataType;
import org.scribble.codegen.purescript.endpointapi.ForeignType;
import org.scribble.codegen.purescript.endpointapi.TypeClassInstance;
import org.scribble.main.Job;
import org.scribble.main.JobContext;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.Payload;
import org.scribble.type.kind.PayloadTypeKind;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.PayloadElemType;
import org.scribble.type.name.Role;
import org.scribble.util.Pair;

public class PSEndpointApiGenerator
{
    public static final String PURESCRIPT_SCHEMA = "purescript";
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

		String moduleName = fullname.getPrefix().toString();
        String protocolName = fullname.getSimpleName().toString();

        // Message actions and their corresponding datatype
        Map<String, Payload> datatypes = new HashMap<>();

		Map<String, ForeignType> foreignImports = new HashMap<>();
		// Get the foreign type declarations
		// TODO: Only include imports that are used
		// TODO: Make it clear that JSON encoding/decoding instances are required
		for (NonProtocolDecl<?> foreignType : mod.getNonProtocolDecls()) {
			if (!foreignType.schema.equals(PURESCRIPT_SCHEMA)) {
				throw new ScribbleException(foreignType.getSource(), "Unsupported data type schema: " + foreignType.schema);
			}
			System.out.println(foreignType); // There is a bug in the parser "type <purescript> Int from Int as Int;"
			foreignImports.put(foreignType.name.toString(), new ForeignType(foreignType.extName, foreignType.extSource));
		}

		Map<DataType, Pair<List<DataType>, List<TypeClassInstance>>> efsms = new HashMap();

		// For each role make a projection, then traverse the graph getting the states + transitions
		for (Role r : gpd.header.roledecls.getRoles()) {
			JobContext jc = job.getContext();
			EGraph efsm = job.minEfsm ? jc.getMinimisedEGraph(fullname, r) : jc.getEGraph(fullname, r);

			EState init = efsm.init;
			EState term = EState.getTerminal(init);

			DataType role = new DataType(r.toString(), null, "Role", true);

			List<TypeClassInstance> instances = new ArrayList<>();
			List<DataType> states = new ArrayList<>();

			// Add the instances for initial/terminal nodes
			instances.add(new TypeClassInstance(("initial" + role.name), "Initial", new String[] {role.name, getStateTypeName(init)}));
			String t = term == null ? "Void" : getStateTypeName(term);
            instances.add(new TypeClassInstance(("terminal" + role.name), "Terminal", new String[] {role.name, t}));

			Set<String> seen = new HashSet<>();
            Set<EState> level = new HashSet<>();

            // Perform a breadth first traversal over the graph
            level.add(init);
            while (!level.isEmpty()) {
                Set<EState> nextlevel = new HashSet<>();
                for (EState s : level) {
                    // Don't generate more than once
                    if (seen.contains(s.toString())) {
                        continue;
                    }
                    seen.add(s.toString());
                    // View the successors during the next level
                    nextlevel.addAll(s.getAllSuccessors());

                    // Generate the state type
                    String curr = getStateTypeName(s);
                    states.add(new DataType(curr, null, DataType.KIND_TYPE, true));

        			switch (s.getStateKind()) {
        				case OUTPUT: {
        				    if (s.getAllActions().size() == 1) {
        				        String next = getStateTypeName(s.getAllSuccessors().get(0));
                                EAction action = s.getAllActions().get(0);
        				        String type = action.mid.toString();
                                String to = action.obj.toString();

                                // Add the instance and message data type
                                instances.add(new TypeClassInstance(("send" + curr), "Send", new String[] {to, curr, next, type}));
                                addDatatype(datatypes, action, foreignImports);
                            } else {
                                // If there are multiple output actions, we should treat it as a branch and create some dummy states where we send the label
                                Set<Pair<String, String>> choices = new HashSet<>();
                                for (int i = 0; i < s.getAllActions().size(); i++) {
                                    EAction action = s.getAllActions().get(i);
                                    String next = getStateTypeName(s.getAllSuccessors().get(i));
                                    String labelState = getStateTypeName(s) + action.mid;
                                    String label = action.mid.toString().toLowerCase();
            				        String type = action.mid.toString();
                                    String to = action.obj.toString();
                                    choices.add(new Pair(label, labelState));

                                    // Add the instance and message data type and dummy state
                                    states.add(new DataType(labelState, null, DataType.KIND_TYPE, true));
                                    instances.add(new TypeClassInstance("send" + labelState, "Send", new String[] {to, labelState, next, type}));
                                    addDatatype(datatypes, action, foreignImports);
                                }

                                // All messages must be to the same role, so we can pick the first one
                                EAction action = s.getAllActions().get(0);
                                String to = action.obj.toString();

                                String end = "Nil";
                                StringBuilder labels = new StringBuilder();
                                for (Pair<String, String> option : choices) {
                                    labels.append("(Cons \"" + option.left + "\" " + option.right + " ");
                                    end += ")";
                                }
                                labels.append(end);

                                instances.add(new TypeClassInstance("select" + curr, "Select", new String[] {to, curr, labels.toString()}));
                            }
                        }
        					break;
        				case UNARY_INPUT: {
                                String next = getStateTypeName(s.getAllSuccessors().get(0));
                                EAction action = s.getAllActions().get(0);
        				        String type = action.mid.toString();
                                String from = action.obj.toString();

                                // Add the instance and message data type
                                instances.add(new TypeClassInstance("receive" + curr, "Receive", new String[] {from, curr, next, type}));
                                addDatatype(datatypes, action, foreignImports);
        				}
        					break;
        				case POLY_INPUT: {
                                Set<Pair<String, String>> choices = new HashSet<>();
                                for (int i = 0; i < s.getAllActions().size(); i++) {
                                    EAction action = s.getAllActions().get(i);
                                    String next = getStateTypeName(s.getAllSuccessors().get(i));
                                    String labelState = getStateTypeName(s) + action.mid;
                                    String label = action.mid.toString().toLowerCase();
            				        String type = action.mid.toString();
                                    String to = action.obj.toString();
                                    choices.add(new Pair(label, labelState));

                                    // Add the instance and message data type and dummy state
                                    states.add(new DataType(labelState, null, DataType.KIND_TYPE, true));
                                    instances.add(new TypeClassInstance("receive" + labelState, "Receive", new String[] {to, labelState, next, type}));
                                    addDatatype(datatypes, action, foreignImports);
                                }

                                // All messages must be to the same role, so we can pick the first one
                                EAction action = s.getAllActions().get(0);

                                String end = "Nil";
                                StringBuilder labels = new StringBuilder();
                                for (Pair<String, String> option : choices) {
                                    labels.append("(Cons \"" + option.left + "\" " + option.right + " ");
                                    end += ")";
                                }
                                labels.append(end);

                                instances.add(new TypeClassInstance("branch" + curr, "Branch", new String[] {role.name, curr, labels.toString()}));
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

            efsms.put(role, new Pair<>(states, instances));
        }

        // Perform the code generation
        List<String> sections = new ArrayList<>();
		sections.add(moduleDeclaration(moduleName, protocolName));
        sections.add(staticImports());

        // Foreign imports
        sections.add(ForeignType.generateImports(new HashSet<>(foreignImports.values())));

        // Message data types
        StringBuilder dt = new StringBuilder();
        for (String type : datatypes.keySet()) {
   	        List<ForeignType> arguments = new ArrayList<>();
            for (PayloadElemType<? extends PayloadTypeKind> elem : datatypes.get(type).elems) {
                arguments.add(foreignImports.get(elem.toString()));
            }
            dt.append(new DataType(type, arguments, DataType.KIND_TYPE, false).generateDataType());
        }
        sections.add(dt.toString());

        // EFSMs
        for (DataType role : efsms.keySet()) {
            sections.add(role.generateDataType());

            StringBuilder states = new StringBuilder();
            for (DataType state : efsms.get(role).left) {
                states.append(state.generateDataType());
            }
            sections.add(states.toString());

            StringBuilder instances = new StringBuilder();
            for (TypeClassInstance instance : efsms.get(role).right) {
                instances.append(instance.generateInstance());
            }
            sections.add(instances.toString());
        }

        // Add newlines between sections
        StringBuilder module = new StringBuilder();
        for (String section : sections) {
            module.append(section);
            module.append("\n");
        }

        Map<String, String> map = new HashMap<String, String>();
		map.put(makePath(moduleName, protocolName), module.toString());

		return map;
	}

    private static String moduleDeclaration(String moduleName, String protocolName) {
        return "module Scribble.Protocol." + moduleName + "." + protocolName + " where\n";
    }

    private static String staticImports() {
        StringBuilder sb = new StringBuilder();
		sb.append("import Scribble.FSM (class Branch, class Initial, class Terminal, class Receive, class Select, class Send, kind Role)\n");
		sb.append("import Type.Row (Cons, Nil)\n");
		sb.append("import Data.Void (Void)\n");
        return sb.toString();
    }

	private void addDatatype(Map<String, Payload> datatypes, EAction action, Map<String, ForeignType> foreignImports) throws RuntimeException {
	    String datatype = action.mid.toString();
	    if (datatypes.containsKey(datatype)) {
	        if (!datatypes.get(datatype).equals(action.payload)) {
                throw new RuntimeException("Messages with the same name `" + action.mid + "` must have the same payload");
            }
        } else {
            datatypes.put(datatype, action.payload);
        }
    }

    private static String makePath(String module, String protocol)
	{
		return "Scribble/Protocol/" + module.replace('.', '/') + "/" + protocol + ".purs";
	}

	public static String getStateTypeName(EState s)
    {
	    return "S" + s;
    }
	
}
