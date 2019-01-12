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

        if (!gpd.isExplicitModifier()) {
            throw new RuntimeScribbleException("Only protocols with explicit connections are currently supported in this version");
        }

		String moduleName = fullname.getPrefix().toString();
        String protocolName = fullname.getSimpleName().toString();

        // Generate protocol type-level information
        DataType protocolType = new DataType(protocolName, null, "Protocol", true);
//        TypeClassInstance protocolNameInst = new TypeClassInstance("protocolName" + protocolType.name, "ProtocolName", new String[] {protocolType.name, ("\"" + protocolType.name + "\"")});

//        StringBuilder roleNames = new StringBuilder("(");
//        // For each role make a projection, then traverse the graph getting the states + transitions
//        for (Role r : gpd.header.roledecls.getRoles()) {
//            roleNames.append("\"" +  r.toString() + "\" ::: ");
//        }
//        roleNames.append("SNil)");
//        TypeClassInstance protocolRoleNames = new TypeClassInstance("protocolRoleNames" + protocolType.name, "ProtocolRoleNames", new String[] {protocolType.name, roleNames.toString()});

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
                                String to = action.obj.toString();
                                if (action.isSend()) {
                                    String type = action.mid.toString();
                                    // Add the instance and message data type
                                    instances.add(new TypeClassInstance(("send" + curr), "Send", new String[]{to, curr, next, type}));
                                    addDatatype(datatypes, action, foreignImports);
                                } else if (action.isDisconnect()) {
                                    instances.add(new TypeClassInstance(("disconnect" + curr), "Disconnect", new String[]{r.toString(), to, curr, next}));

                                } else if (action.isRequest()) {
                                    String connectedState = curr + "Connected";
                                    instances.add(new TypeClassInstance(("connect" + curr), "Connect", new String[]{r.toString(), to, curr, connectedState}));
                                    String type = action.mid.toString();
                                    // Add the instance and message data type
                                    states.add(new DataType(curr + "Connected", null, DataType.KIND_TYPE, true));
                                    instances.add(new TypeClassInstance(("send" + curr), "Send", new String[]{to, connectedState, next, type}));
                                    addDatatype(datatypes, action, foreignImports);
                                } else {
                                    // TODO: What is wrap-client + do we need to handle it?
                                    throw new ScribbleException(null, "Unsupported action " + s.getStateKind());
                                }
                            } else {
                                // If there are multiple output actions, we should treat it as a branch and create some dummy states where we send the label
                                // TODO: I'm making the assumption that if there are multiple outputs, they are all send -- probably should test this
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
                            EAction action = s.getAllActions().get(0);
                            String next = getStateTypeName(s.getAllSuccessors().get(0));
                            String to = action.obj.toString();
                            instances.add(new TypeClassInstance(("accept" + curr), "Accept", new String[]{r.toString(), to, curr, next}));
                            break;
                        case WRAP_SERVER:
                            throw new RuntimeScribbleException("Unsupported action " + s.getStateKind());
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

        sections.add(jsonImports());

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


        // Protocol
        sections.add(protocolType.generateDataType());
//        sections.add(protocolNameInst.generateInstance());
//        sections.add(protocolRoleNames.generateInstance());

        // ProtocolRoleNames

        // EFSMs
        for (DataType role : efsms.keySet()) {
            sections.add(role.generateDataType());
            TypeClassInstance roleName = new TypeClassInstance("roleName" + role.name, "RoleName", new String[] {role.name, ("\"" + role.name + "\"")});
            sections.add(roleName.generateInstance());

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
		sb.append("import Scribble.FSM\n");
//        sb.append("import Scribble.Type.SList (type (:::), SLProxy(..), SNil, symbols)\n");
		sb.append("import Type.Row (Cons, Nil)\n");
		sb.append("import Data.Void (Void)\n");
        sb.append("import Data.Tuple (Tuple)\n");
        return sb.toString();
    }

    private static String jsonImports() {
        StringBuilder sb = new StringBuilder();
        sb.append("-- From purescript-argonaut-codecs\n");
        sb.append("import Data.Argonaut.Decode (class DecodeJson)\n");
        sb.append("import Data.Argonaut.Encode (class EncodeJson)\n");
        sb.append("import Data.Argonaut.Core (Json) -- From purescript-argonaut-core\n");
        sb.append("import Data.Generic.Rep (class Generic) -- From purescript-generics-rep\n");
        sb.append("-- From purescript-argonaut-generic\n");
        sb.append("import Data.Argonaut.Decode.Generic.Rep (genericDecodeJson)\n");
        sb.append("import Data.Argonaut.Encode.Generic.Rep (genericEncodeJson)\n");
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
