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
package org.scribble.codegen.java.endpointapi.ioifaces;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.java.endpointapi.ApiGen;
import org.scribble.codegen.java.endpointapi.CaseSockGen;
import org.scribble.codegen.java.endpointapi.HandlerIfaceGen;
import org.scribble.codegen.java.endpointapi.ScribSockGen;
import org.scribble.codegen.java.endpointapi.SessionApiGenerator;
import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.util.AbstractMethodBuilder;
import org.scribble.codegen.java.util.ClassBuilder;
import org.scribble.codegen.java.util.FieldBuilder;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.codegen.java.util.TypeBuilder;
import org.scribble.main.JobContext;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.EStateKind;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;

// Cf. StateChannelApiGenerator
// TODO: concrete state channel "to" casts for supertype i/f's (the info is there in the Java type hierachy though)
// Maybe record subtype hierarchy explicitly
// TODO: check if generated subtypes scalability issue is just Eclipse or also javac
public class IOInterfacesGenerator extends ApiGen
{
	private final boolean SUBTYPES;
	
	protected StateChannelApiGenerator apigen;

	private final Map<EAction, InterfaceBuilder> actions = new HashMap<>();
	private final Map<EAction, InterfaceBuilder> succs = new HashMap<>();
	private final Map<String, InterfaceBuilder> iostates = new HashMap<>();  // Key is interface simple name
	
	private final Map<EAction, InterfaceBuilder> caseActions = new HashMap<>();
	
	private final Map<EState, Set<EAction>> preActions = new HashMap<>();  // Pre set: the actions that lead to each state
	private final Map<EState, Set<InterfaceBuilder>> preds = new HashMap<>();
	
	private final Map<EState, Set<EAction>> branchPostActions = new HashMap<>();
	//private final Map<EndpointState, Set<InterfaceBuilder>> branchSuccs = new HashMap<>();
	private final Map<String, List<EAction>> branchSuccs = new HashMap<>();  // key: HandleInterface name  // Sorted when collected
	
	public IOInterfacesGenerator(StateChannelApiGenerator apigen, boolean subtypes) throws RuntimeScribbleException, ScribbleException
	{
		super(apigen.getJob(), apigen.getGProtocolName());
		this.apigen = apigen;
		this.SUBTYPES = subtypes;

		GProtocolName fullname = apigen.getGProtocolName();
		Role self = getSelf();
		//EndpointState init = this.job.getContext().getEndpointGraph(fullname, self).init;
		JobContext jc = this.job.getContext();
		EState init = this.job.minEfsm ? jc.getMinimisedEGraph(fullname, self).init : jc.getEGraph(fullname, self).init;
		
		//if (IOInterfacesGenerator.skipIOInterfacesGeneration(init))
		{
			// FIXME: factor out with skipIOInterfacesGeneration
			Set<EAction> as = EState.getReachableActions(init);
			if (as.stream().anyMatch((a) -> !a.isSend() && !a.isReceive()))  // HACK FIXME (connect/disconnect)
			{
				throw new RuntimeScribbleException("[TODO] I/O Interface generation not supported for: " + as.stream().filter((a) -> !a.isSend() && !a.isReceive()).collect(Collectors.toList()));
			}
		}

		generateActionAndSuccessorInterfacesAndCollectPreActions(new HashSet<>(), init);
		generateIOStateInterfacesFirstPass(new HashSet<>(), init);
		collectPreds();
		
		//EndpointState term = EndpointState.findTerminalState(new HashSet<>(), init);
		EState term = EState.getTerminal(init);
		ClassBuilder endsock = null;
		if (term != null)
		{
			endsock = (ClassBuilder) this.apigen.getType(ScribSockGen.GENERATED_ENDSOCKET_NAME);
			for (InterfaceBuilder ib : this.preds.get(term))
			{
				endsock.addInterfaces(ib.getName());

				MethodBuilder mb2 = addToCastMethod(ib, "EndSocket");
				if (mb2 != null)
				{
					ib.addImports(SessionApiGenerator.getStateChannelPackageName(this.gpn, self) + ".EndSocket");
				}
			}
			endsock.addImports(getIOInterfacePackageName(this.gpn, self) + ".*");
		}
		
		generateIOStateInterfacesSecondPass(new HashSet<>(), init);
		collectBranchSuccs();
		generateHandleInterfaces(new HashSet<>(), init);
		generateHandleInterfacesSecondPass(new HashSet<>(), init);
		addIOStateInterfacesToStateChannels(new HashSet<>(), init);  // Except EndSocket
		
		if (this.SUBTYPES)
		{
			addSupertypeInterfaces();
		}

		// Successor I/f's for EndSocket
		// FIXME: refactor EndSocket into main collection of generated types (this.apigen.getType)
		//EndpointState term = EndpointState.findTerminalState(new HashSet<>(), init);
		if (term != null)
		{
			//endsock = (ClassBuilder) this.apigen.getType(ScribSocketGenerator.GENERATED_ENDSOCKET_NAME);
			//endsock.addImports(getIOInterfacePackageName(this.gpn, self) + ".*");
			for (InterfaceBuilder ib : this.preds.get(term))
			{
				//endsock.addInterfaces(ib.getName());

				// Duplicated from generateIOStateInterfacesSecondPass
				for (MethodBuilder cast : ib.getDefaultMethods())  // cast is a default method (a to cast -- hacky) in pred
				{
					// Overriding every Successor I/f to methods in the I/O State I/f, even if unnecessary
					if (!cast.getReturn().equals("EndSocket"))
					{
						MethodBuilder mb = addEndSocketToCastMethod(endsock, cast.getReturn(), "throw new RuntimeScribbleException(\"Invalid cast of EndSocket: \" + cast);");
						if (mb != null)

						{
							mb.addModifiers(JavaBuilder.PUBLIC);
							mb.addAnnotations("@Override");
							endsock.addImports("org.scribble.main.RuntimeScribbleException");
						}
					}
				}
			}

			/*MethodBuilder mb2 = addToCastMethod(ib, "EndSocket");
			if (mb2 != null)
			{
				ib.addImports(SessionApiGenerator.getStateChannelPackageName(this.gpn, self) + ".EndSocket");
			}*/
			MethodBuilder mb3 = addEndSocketToCastMethod(endsock, "EndSocket", "return (EndSocket) this;");
			if (mb3 != null)
			{
				mb3.addModifiers(JavaBuilder.PUBLIC);
				mb3.addAnnotations("@Override");
			}
		}
	}

	/*public static boolean skipIOInterfacesGeneration(EndpointState init)
	{
		Set<IOAction> as = EndpointState.getAllReachableActions(init);
		if (as.stream().anyMatch((a) -> !a.isSend() && !a.isReceive()))  // HACK FIXME (connect/disconnect)
		{
			return true;
		}
		return false;
	}*/

	@Override
	public Map<String, String> generateApi()
	{
		Map<String, String> output = new HashMap<>();
		String prefix = getIOInterfacePackageName(this.gpn, getSelf()).replace('.', '/') + "/";
		this.actions.values().stream().forEach((ib) -> output.put(prefix + ib.getName() + ".java", ib.build()));
		this.succs.values().stream().forEach((ib) -> output.put(prefix + ib.getName() + ".java", ib.build()));
		this.iostates.values().stream().forEach((tb) -> output.put(prefix + tb.getName() + ".java", tb.build()));

		this.caseActions.values().stream().forEach((ib) -> output.put(prefix + ib.getName() + ".java", ib.build()));

		return output;
	}
	
	// Factor out FSM visitor?
	private void generateActionAndSuccessorInterfacesAndCollectPreActions(Set<EState> visited, EState s) throws ScribbleException
	{
		if (visited.contains(s) || s.isTerminal())
		{
			return;
		}
		visited.add(s);

		//Set<EAction> as = s.getActions();
		List<EAction> as = s.getActions();
		for (EAction a : as.stream().sorted(IOStateIfaceGen.IOACTION_COMPARATOR).collect(Collectors.toList()))
		{
			if (!a.isSend() && !a.isReceive())  // TODO (connect/disconnect)
			{
				throw new RuntimeException("TODO: " + a);
			}

			if (!this.actions.containsKey(a))
			{
				this.actions.put(a, new ActionIfaceGen(this.apigen, s, a).generateType());
				this.succs.put(a, new SuccessorIfaceGen(this.apigen, s, a).generateType());

				if (s.getStateKind() == EStateKind.POLY_INPUT)
				{
					// Duplicated from ActionInterfaceGenerator
					InterfaceBuilder ib = new InterfaceBuilder();
					ib.setName("Callback_" + ActionIfaceGen.getActionString(a));
					ib.setPackage(IOInterfacesGenerator.getIOInterfacePackageName(this.apigen.getGProtocolName(), this.apigen.getSelf()));
					ib.addImports("java.io.IOException");
					ib.addImports(SessionApiGenerator.getEndpointApiRootPackageName(this.gpn) + ".*");
					ib.addImports(SessionApiGenerator.getRolesPackageName(this.gpn) + ".*");
					ib.addImports(SessionApiGenerator.getOpsPackageName(this.gpn) + ".*");
					ib.addModifiers(JavaBuilder.PUBLIC);
					ib.addParameters("__Succ extends " + SuccessorIfaceGen.getSuccessorInterfaceName(a));
					AbstractMethodBuilder mb = ib.newAbstractMethod();
					// Duplicated from HandleInterfaceGenerator
					HandlerIfaceGen.setHandleMethodHeaderWithoutParamTypes(this.apigen, mb);
					mb.addParameters("__Succ schan");
					HandlerIfaceGen.addHandleMethodOpAndPayloadParams(this.apigen, a, mb);
					this.caseActions.put(a, ib);
				}
			}
			
			EState succ = s.getSuccessor(a);
			putPreAction(succ, a);

			if (s.getStateKind() == EStateKind.POLY_INPUT)
			{
				/*for (IOAction b : s.accept(a).getAcceptable().stream().sorted(IOStateInterfaceGenerator.IOACTION_COMPARATOR).collect(Collectors.toList()))
				{
					putBranchPostAction(s, b);
				}*/
				putBranchPostAction(s, a);
			}

			generateActionAndSuccessorInterfacesAndCollectPreActions(visited, succ);
			/*Set<EndpointState> tmp = new HashSet<>(visited);
			tmp.add(s);  // merge paths visited multiple times
			generateActionAndSuccessorInterfacesAndCollectPredecessors(tmp, succ);*/
		}
	}

	// Generates partial IO State Interfaces
	private void generateIOStateInterfacesFirstPass(Set<EState> visited, EState s) throws ScribbleException
	{
		if (visited.contains(s) || s.isTerminal())
		{
			return;
		}
		
		String key = IOStateIfaceGen.getIOStateInterfaceName(getSelf(), s);
		if (!this.iostates.containsKey(key))  // Don't generate if one already exists (up to this pass, repeats will all be the same, i.e. name, Action Interfaces, and action-succ parameters)
		{
			// Make the partial I/O State Interface (Successor Interfaces and cast methods added later -- different states may share same State I/f)
			IOStateIfaceGen ifgen = null;
			switch (s.getStateKind())
			{
				case OUTPUT:  // Send, connect, disconnect
					ifgen = new SelectIfaceGen(this.apigen, this.actions, s);
					break;
				case UNARY_INPUT:
					ifgen = new ReceiveIfaceGen(this.apigen, this.actions, s);
					break;
				case POLY_INPUT:
					InterfaceBuilder cases = new CaseIfaceGen(this.apigen, this.actions, s).generateType();
					this.iostates.put(cases.getName(), cases);
					ifgen = new BranchIfaceGen(this.apigen, this.actions, s);
					break;
				case TERMINAL:
				default:
					throw new RuntimeException("(TODO) I/O interface generation: " + s.getStateKind());
			}
			this.iostates.put(key, ifgen.generateType());
		}
		
		visited.add(s);
		for (EAction a : s.getActions())
		{
			generateIOStateInterfacesFirstPass(visited, s.getSuccessor(a));
		}
	}

	// Adds Successor Interfaces and to-cast methods to IO State Interfaces
	private void generateIOStateInterfacesSecondPass(Set<EState> visited, EState s)
	{
		if (visited.contains(s) || s.isTerminal())
		{
			return;
		}

		Set<InterfaceBuilder> succifs = this.preds.get(s);  // Successor interfaces to be implemented by IO State Interface of s
		if (succifs != null)
		{
			InterfaceBuilder iostate = this.iostates.get(IOStateIfaceGen.getIOStateInterfaceName(getSelf(), s));
			if (this.SUBTYPES) // HACK moved here from addSupertypeInterfaces to ensure duplicate inherited to's get overridden
			{
				// Generate Succ I/f supertype "to" cast methods for supertype to's
				String name = iostate.getName();
				if (name.startsWith("Select"))
				{
					List<String> ifs = this.iostates.get(name).getInterfaces();
					List<String> outs = ifs.stream().filter((i) -> i.startsWith("Out"))
							.map((o) -> (o = o.substring(0, o.indexOf("<"))).substring(o.indexOf("_") + 1, o.length()))
							.sorted((s1, s2) -> s1.compareTo(s2))
							.collect(Collectors.toList());
					addSupertypeInterfaceToMethods(s, outs, "Select", "Out");
				}
			}

			for (InterfaceBuilder pred : succifs)  // pred is a Successor Interface for the state s 
			{
				// May already have "visited" this State I/f for a different state -- Interfaces recorded as a Set, to support repeated adds
				iostate.addInterfaces(pred.getName());  // Adds Successor Interfaces to this I/O State Interface
				String ret = iostate.getName() + "<"
						+ IntStream.range(0, iostate.getParameters().size()).mapToObj((i) -> "?").collect(Collectors.joining(", ")) + ">";
				addToCastMethod(pred, ret);
				
				for (MethodBuilder cast : pred.getDefaultMethods())  // cast is a default method (a to cast -- hacky) in pred
				{
					// Overriding every Successor I/f "to" method in the I/O State I/f, even if unnecessary
					MethodBuilder mb = addToCastMethod(iostate, cast.getReturn());
					if (mb != null)
					{
						mb.addAnnotations("@Override");
					}
				}
			}
		}

		visited.add(s);
		for (EAction a : s.getActions())
		{
			generateIOStateInterfacesSecondPass(visited, s.getSuccessor(a));
		}
	}

	private void generateHandleInterfaces(Set<EState> visited, EState s) throws ScribbleException
	{
		if (visited.contains(s) || s.isTerminal())
		{
			return;
		}

		if (s.getStateKind() == EStateKind.POLY_INPUT)
		{
			//Set<InterfaceBuilder> succifs = this.preds.get(s);
			String key = HandleIfaceGen.getHandleInterfaceName(getSelf(), s);
			if (!this.iostates.containsKey(key))
			{
				IOStateIfaceGen ifgen = new HandleIfaceGen(this, this.actions, s, this.caseActions);
				this.iostates.put(key, ifgen.generateType());
			}
		}
		
		visited.add(s);
		for (EAction a : s.getActions())
		{
			generateHandleInterfaces(visited, s.getSuccessor(a));
		}
	}

	private void generateHandleInterfacesSecondPass(Set<EState> visited, EState s)
	{
		if (visited.contains(s) || s.isTerminal())
		{
			return;
		}

		if (s.getStateKind() == EStateKind.POLY_INPUT)
		{
			//GProtocolName gpn = this.apigen.getGProtocolName();
			Role self = this.apigen.getSelf();

			//String foo = HandlerInterfaceGenerator.getHandlerInterfaceName(IOStateInterfaceGenerator.getIOStateInterfaceName(self, s)); 
			String key = HandleIfaceGen.getHandleInterfaceName(self, s);
			List<EAction> succifs = this.branchSuccs.get(key);
			if (succifs != null)
			{
				////InterfaceBuilder handleif = this.iostates.get(HandleInterfaceGenerator.getHandleInterfaceName(self, s));
				//InterfaceBuilder handleif = this.iostates.get(key);
				
				/*if (handleif.getParameters().isEmpty())  // Hacky?
				{
					int i = 1;
					for (IOAction b : succifs)  // Already sorted
					{
						handleif.addParameters("__Succ" + i + " extends " + SuccessorInterfaceGenerator.getSuccessorInterfaceName(b));//this.succs.get(b).getName());
						i++;
					}

					handleif.addImports(SessionApiGenerator.getOpsPackageName(gpn) + ".*");
					//int j = 1; 
					/*Iterator<IOAction> foo = s.getAcceptable().stream().sorted(IOStateInterfaceGenerator.IOACTION_COMPARATOR).iterator();
					EndpointState succ = s.accept(foo.next());* /
				}*/

				////Map<IOAction, Integer> count = new HashMap<>();
				//List<IOAction> tmp = this.branchSuccs.get(key);
				////tmp.stream().forEach((a) -> count.put(a, (int) tmp.stream().filter((b) -> b.equals(a)).count()));
				//Map<IOAction, Integer> count = new HashMap<>();
				/*for (IOAction a : this.branchPostActions.get(s).stream().sorted(IOStateInterfaceGenerator.IOACTION_COMPARATOR).collect(Collectors.toList()))
				{
				/*for (IOAction b : succifs)
				{
					if (!succ.isAcceptable(b))
					{
						succ = s.accept(foo.next());
					}* /
					EndpointState succ = s.accept(a);
					MethodBuilder mb = handleif.newAbstractMethod();
					HandlerInterfaceGenerator.setHandleMethodHeaderWithoutParamTypes(this.apigen, mb);
					//j = HandleInterfaceGenerator.setHandleMethodSuccessorParam(this, self, succ, mb, j);
					HandleInterfaceGenerator.setHandleMethodSuccessorParam(this, self, succ, mb, tmp, count);
					/*for (IOAction b : count.keySet())
					{
						for (int j = 0; j < count.get(b); j++)
						{
							tmp.remove(b);
						}
					}* /
					HandlerInterfaceGenerator.addHandleMethodOpAndPayloadParams(this.apigen, a, mb);
					
					handleif.checkDuplicateMethods(mb);  // Hacky
				//}
				}*/
			}
		}
				
		visited.add(s);
		for (EAction a : s.getActions())
		{
			generateHandleInterfacesSecondPass(visited, s.getSuccessor(a));
		}
	}
	
	// Except EndSocket
	private void addIOStateInterfacesToStateChannels(Set<EState> visited, EState s)
	{
		if (visited.contains(s) || s.isTerminal())
		{
			return;
		}

		Role self = getSelf();
		String scname = this.apigen.getSocketClassName(s);
		String ioname = IOStateIfaceGen.getIOStateInterfaceName(self, s);
		
		TypeBuilder tb = this.apigen.getType(scname);

		// Add I/O State Interface to each ScribSocket (except CaseSocket)
		// Do here (not inside I/O State Interface generators) because multiple states can share the same I/O State Interface
		tb.addImports(getIOInterfacePackageName(this.gpn, self) + ".*");
		tb.addInterfaces(ioname + getConcreteSuccessorParameters(s));
	
		InterfaceBuilder iostate = this.iostates.get(ioname);
		MethodBuilder mb = addToCastMethod(iostate, scname);
		if (mb != null)
		{
			iostate.addImports(SessionApiGenerator.getStateChannelPackageName(this.gpn, self) + ".*");
		}

		if (s.getStateKind() == EStateKind.POLY_INPUT)
		{
			// Add CaseInterface to each CaseSocket
			TypeBuilder cases = this.apigen.getType(CaseSockGen.getCaseSocketName(this.apigen.getSocketClassName(s)));
			cases.addImports(getIOInterfacePackageName(this.gpn, self) + ".*");
			cases.addInterfaces(CaseIfaceGen.getCasesInterfaceName(self, s) + getConcreteSuccessorParameters(s));
			
			// Add HandleInterface to each HandlerInterface
			InterfaceBuilder handler = (InterfaceBuilder) this.apigen.getType(HandlerIfaceGen.getHandlerInterfaceName(this.apigen.getSocketClassName(s)));
			handler.addImports(getIOInterfacePackageName(this.gpn, self) + ".*");
			// FIXME: factor out with HandleInterfaceGenerator and getConcreteSuccessorParameters
			String tmp = "";
			boolean first = true;
			/*for (IOAction a : s.getAcceptable().stream().sorted(IOStateInterfaceGenerator.IOACTION_COMPARATOR).collect(Collectors.toList()))
			{
				EndpointState succ = s.accept(a);
				for (IOAction b : succ.getAcceptable().stream().sorted(IOStateInterfaceGenerator.IOACTION_COMPARATOR).collect(Collectors.toList()))
				{
					if (first)
					{
						first = false;
					}
					else
					{
						tmp += ", ";
					}
					tmp += this.getSuccName.apply(succ.accept(b));
				}
			}*/
			String handle = HandleIfaceGen.getHandleInterfaceName(self, s);
			/*List<IOAction> foo1 = new LinkedList<>();
			List<EndpointState> bar1 = new LinkedList<>();
			for (IOAction a : s.getAcceptable().stream().sorted(IOStateInterfaceGenerator.IOACTION_COMPARATOR).collect(Collectors.toList()))
			{
				EndpointState succ = s.accept(a);
				for (IOAction b : succ.getAcceptable().stream().sorted(IOStateInterfaceGenerator.IOACTION_COMPARATOR).collect(Collectors.toList()))
				{
					foo1.add(b);
					bar1.add(succ);
				}
			}
			System.out.println("BBB: " + handle);*/
			//for (IOAction a : this.branchSuccs.get(handle))
			// FIXME: move back into HandlerInterfaceGenerator
			for (EAction a : s.getActions().stream().sorted(IOStateIfaceGen.IOACTION_COMPARATOR).collect(Collectors.toList()))
			{
				if (first)
				{
					first = false;
				}
				else
				{
					tmp += ", ";
				}
				/*if (foo1.contains(a))
				{
					EndpointState succ = bar1.get(foo1.indexOf(a));
					tmp += this.getSuccName.apply(succ.accept(a));
					foo1.remove(a);
					bar1.remove(succ);
				}
				else
				{
					tmp += SuccessorInterfaceGenerator.getSuccessorInterfaceName(a);
				}*/
				EState succ = s.getSuccessor(a);
				if (succ.isTerminal())
				{
					tmp += ScribSockGen.GENERATED_ENDSOCKET_NAME;
				}
				else
				{
					tmp += this.apigen.getSocketClassName(succ);
				}
			}	
			
			handler.addInterfaces(handle + "<" + tmp + ">");
			
			/*// Override abstract handle methods with default cast implementation
			for (IOAction a : s.getAcceptable().stream().sorted(IOStateInterfaceGenerator.IOACTION_COMPARATOR).collect(Collectors.toList()))
			{
				EndpointState succ = s.accept(a);
				this.iostates.get(HandleInterfaceGenerator.getHandleInterfaceName(self, succ));
				MethodBuilder override = handler.newDefaultMethod();
				//override.addModifiers(JavaBuilder.FINAL);  // Default methods cannot be final
				HandlerInterfaceGenerator.setHandleMethodHeaderWithoutParamTypes(this.apigen, override);
				//HandleInterfaceGenerator.setHandleMethodSuccessorParam(this, self, succ, override);
				// FIXME: factor out with HandleInterfaceGenerator.setHandleMethodSuccessorParam
				String nextClass = this.apigen.getSocketClassName(succ);
				if (succ.isTerminal())
				{
					override.addParameters(ScribSocketGenerator.ENDSOCKET_CLASS + "<?, ?> end");
				}
				else
				{
					InterfaceBuilder next = getIOStateInterface(IOStateInterfaceGenerator.getIOStateInterfaceName(self, succ));  // Select/Receive/Branch
					String ret = next.getName() + "<";
					//ret += "<" + next.getParameters().stream().map((p) -> "__Succ" + i).collect(Collectors.joining(", ")) + ">";  // FIXME: fragile?
					boolean bar = true;
					for (IOAction b : succ.getAcceptable().stream().sorted(IOStateInterfaceGenerator.IOACTION_COMPARATOR).collect(Collectors.toList()))  // FIXME: factor out with getHandleInterfaceIOActionParams
					{
						if (bar)
						{
							bar = false;
						}
						else
						{
							ret += ", ";
						}
						EndpointState foo = succ.accept(b);
						if (foo.isTerminal())
						{
							ret += ScribSocketGenerator.GENERATED_ENDSOCKET_NAME;
						}
						else
						{
							ret += this.apigen.getSocketClassName(foo);
						}
					}
					ret += ">";
					override.addParameters(ret + " schan");
				}
				HandlerInterfaceGenerator.addHandleMethodOpAndPayloadParams(this.apigen, a, override);
				// FIXME: factor out
				String ln = "receive((";
				if (succ.isTerminal())  // factor out
				{
					ln += "EndSocket) end";
				}
				else
				{
					ln += nextClass + ") schan";
				}
				ln += ", op";
				String args;
				if (a.mid.isOp())  // factor out
				{
					args = IntStream.rangeClosed(1, a.payload.elems.size()).mapToObj((i) -> "arg" + i).collect(Collectors.joining(", "));
					if (!args.equals(""))
					{
						args = ", " + args;
					}
				}
				else
				{
					args = ", arg";
				}
				ln += args + ");";
				override.addBodyLine(ln);
				override.addAnnotations("@Override");
			}*/
		}
		
		visited.add(s);
		for (EAction a : s.getActions())
		{
			addIOStateInterfacesToStateChannels(visited, s.getSuccessor(a));
		}
	}
	
	private void addSupertypeInterfaces()
	{
		Map<String, InterfaceBuilder> subtypeifs = new HashMap<>();
		for (InterfaceBuilder ib : this.iostates.values())
		{
			String name = ib.getName();
			if (!subtypeifs.containsKey(name))
			{
				if (name.startsWith("Select") || name.startsWith("Handle"))
				{
					subtypeifs.put(name, ib);

					Map<String, InterfaceBuilder> res = new HashMap<>();
					if (name.startsWith("Select"))
					{
						List<String> ifs = ib.getInterfaces();  // Could also use params to integrate with Handle
						List<String> outs = ifs.stream().filter((i) -> i.startsWith("Out"))
								.map((o) -> (o = o.substring(0, o.indexOf("<"))).substring(o.indexOf("_") + 1, o.length()))
								.sorted((s1, s2) -> s1.compareTo(s2))
								.collect(Collectors.toList());
						buildIOStateSuperInterfaces(res, outs, "Select", "Out", "Out");
					}
					else //if (base.startsWith("Handle"))
					{
						List<String> params = ib.getParameters();
						List<String> ins = params.stream().map((i) -> i.substring(i.indexOf("Succ_In_") + "Succ_In_".length()))
								.sorted((s1, s2) -> s1.compareTo(s2))
								.collect(Collectors.toList());
						//buildSuperHandleInterfaces(res, ins);
						buildIOStateSuperInterfaces(res, ins, "Handle", "Callback", "In");
						//buildIOStateSuperInterfaces(res, ins, "Branch", "", "In");  // No: subtyping for branch interfaces unsafe -- that's the point of handle interfaces
						/*Map<String, InterfaceBuilder> tmp = new HashMap<>();
						buildIOStateSuperInterfaces(tmp, ins, "Branch", "", "In");
						for (InterfaceBuilder bra : tmp.values())
						{
							addBranchSupertypeInterfaceMethods(ib, bra, ins, getSelf());
						}
						res.putAll(tmp);*/
					}
					res.values().forEach((r) -> subtypeifs.put(r.getName(), r));
				}
			}
		}
		subtypeifs.values().forEach((r) -> this.iostates.put(r.getName(), r));
		
		for (InterfaceBuilder ib : this.iostates.values())
		{
			String name = ib.getName();
			if (name.startsWith("Select"))
			{
				List<String> ifs = ib.getInterfaces();
				List<String> outs = ifs.stream().filter((i) -> i.startsWith("Out"))
						.map((o) -> (o = o.substring(0, o.indexOf("<"))).substring(o.indexOf("_") + 1, o.length()))
						.sorted((s1, s2) -> s1.compareTo(s2))
						.collect(Collectors.toList());
				addIOStateSuperInterfaces(ib, outs, "Select", "Out");
			}
			else if (name.startsWith("Handle"))
			{
				List<String> params = ib.getParameters();
				List<String> ins = params.stream().map((i) -> i.substring(i.indexOf("Succ_In_") + "Succ_In_".length()))
						.sorted((s1, s2) -> s1.compareTo(s2))
						.collect(Collectors.toList());
				addIOStateSuperInterfaces(ib, ins, "Handle", "In");
			}
			/*else if (name.startsWith("Branch"))  // No: subtyping for branch interfaces unsafe -- that's the point of handle interfaces
			{
				List<String> params = ib.getParameters();
				List<String> ins = params.stream().map((i) -> i.substring(i.indexOf("Succ_In_") + "Succ_In_".length()))
						.sorted((s1, s2) -> s1.compareTo(s2))
						.collect(Collectors.toList());
				addIOStateSuperInterfaces(ib, ins, "Branch", "In");
			}*/
		}

		/*// Generate Succ I/f supertype "to" cast methods -- moved earlier, to be supported by to-overriding pass (i.e. when a direct I/O State I/f inherits duplicate to's from Succs)
		Role self = getSelf();
		for (EndpointState s : this.preds.keySet())
		{
			if (s.isTerminal())
			{
				continue;
			}
			String name = IOStateInterfaceGenerator.getIOStateInterfaceName(self, s);
			if (name.startsWith("Select"))
			{
				
				System.out.println("AAA: " + name);
				
				List<String> ifs = this.iostates.get(name).getInterfaces();
				List<String> outs = ifs.stream().filter((i) -> i.startsWith("Out"))
						.map((o) -> (o = o.substring(0, o.indexOf("<"))).substring(o.indexOf("_") + 1, o.length()))
						.sorted((s1, s2) -> s1.compareTo(s2))
						.collect(Collectors.toList());
				addSupertypeInterfaceToMethods(s, outs, "Select", "Out");
			}
			/*else if (name.startsWith("Branch"))
			{
				List<String> params = this.iostates.get(name).getParameters();
				List<String> ins = params.stream().map((i) -> i.substring(i.indexOf("Succ_In_") + "Succ_In_".length()))
						.sorted((s1, s2) -> s1.compareTo(s2))
						.collect(Collectors.toList());
				addSupertypeInterfaceToMethods(s, ins, "Branch", "In");
			}* /
		}*/
	}

	// a = e.g. C_1_Int
	// Pre: as sorted
	private void buildIOStateSuperInterfaces(Map<String, InterfaceBuilder> res, List<String> as, String superPref, String actPref, String succPref)
	{
		for (String exclude : as)
		{
			List<String> foo = as.stream().filter((s) -> !s.equals(exclude)).collect(Collectors.toList());
			if (foo.size() > 0)
			{
				String tmp = foo.stream().collect(Collectors.joining("__"));
				String superName = superPref + "_" + getSelf() + "_" + tmp;
				
				if (!this.iostates.containsKey(superName) && !res.containsKey(superName))
				{
					InterfaceBuilder ib = new InterfaceBuilder(superName);
					ib.setPackage(getIOInterfacePackageName(this.gpn, getSelf()));
					ib.addModifiers(JavaBuilder.PUBLIC);
					int i = 1;
					for (String a : foo)
					{
						ib.addParameters("__Succ" + i + " extends " + "Succ_" + succPref + "_" + a);
						if (!superPref.equals("Branch"))  // Hacky
						{
							ib.addInterfaces(actPref + "_" + a + "<__Succ" + i + ">");
						}
						i++;
					}
					FieldBuilder cast = ib.newField("cast");  // FIXME: factor out
					cast.addModifiers(JavaBuilder.PUBLIC, JavaBuilder.STATIC, JavaBuilder.FINAL);
					cast.setType(superName + "<" + IntStream.range(0, foo.size()).mapToObj((x) -> "?").collect(Collectors.joining(", ")) + ">");
					cast.setExpression("null");
					res.put(superName, ib);
					buildIOStateSuperInterfaces(res, foo, superPref, actPref, succPref);
				}
			}
		}
	}
	
	/*//private void addBranchSupertypeInterfaceMethods(EndpointState s, InterfaceBuilder ib, Role self)
	private void addBranchSupertypeInterfaceMethods(InterfaceBuilder root, InterfaceBuilder ib, List<String> foo, Role self)
	{
		//Duplicated from BranchInterfaceGenerator
		/*AbstractMethodBuilder bra = ib.newAbstractMethod("branch");
		String ret = CaseInterfaceGenerator.getCasesInterfaceName(self, s)
				+ "<" + IntStream.range(1, as.size()+1).mapToObj((x) -> "__Succ" + x).collect(Collectors.joining(", ")) + ">";  // FIXME: factor out
		bra.setReturn(ret);
		bra.addParameters(SessionApiGenerator.getRoleClassName(as.iterator().next().peer) + " role");
		bra.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "java.io.IOException", "ClassNotFoundException");* /

						List<String> params = ib.getParameters();
						List<String> ins = params.stream().map((i) -> i.substring(i.indexOf("Succ_In_") + "Succ_In_".length()))
								.sorted((s1, s2) -> s1.compareTo(s2))
								.collect(Collectors.toList());
		
		String handleName = "Handle" + ib.getName().substring("Branch".length(), ib.getName().length());
		System.out.println("AAA: " + ib.getName() + ", " + handleName);
		//String peer = ib.getName().substring(ib.getName().indexOf("_") + 1, ib.getName().indexOf("_", ib.getName().indexOf("_") + 1));
		String peer = ins.get(0).substring(0, ins.get(0).indexOf("_"));

		AbstractMethodBuilder bra2 = ib.newAbstractMethod("branch");
		bra2.setReturn(JavaBuilder.VOID);
		bra2.addParameters(peer + " role");
		String next = handleName + "<" + IntStream.range(1, ins.size() + 1).mapToObj((i) -> "__Succ" + i).collect(Collectors.joining(", ")) + ">";
		bra2.addParameters(next + " handler");
		bra2.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "java.io.IOException", "ClassNotFoundException");
		//bra2.addBodyLine("branch(role, (" + root.getName() + ") handler);");
		
		AbstractMethodBuilder bra3 = ib.newAbstractMethod("handle");
		bra3.setReturn(JavaBuilder.VOID);
		bra3.addParameters(peer + " role");
		String handle = handleName + "<" + ins.stream().map((in) -> "Succ_In_" + in).collect(Collectors.joining(", ")) + ">";
		bra3.addParameters(handle + " handler");
		bra3.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "java.io.IOException", "ClassNotFoundException");
		//bra3.addBodyLine("branch(role, (" + root.getName() + ") handler);");
		
		ib.addImports(SessionApiGenerator.getRolesPackageName(this.gpn) + ".*");
		// No: generating "super" branch/handle methods for supertypes is not safe
	}*/
	
	// a = e.g. C_1_Int
	// Pre: as sorted
	private void addIOStateSuperInterfaces(InterfaceBuilder ib, List<String> as, String superPref, String succPref)
	{
		for (String exclude : as)
		{
			List<String> foo = as.stream().filter((s) -> !s.equals(exclude)).collect(Collectors.toList());
			if (foo.size() > 0)
			{
				List<String> params = new LinkedList<>();
				for (String a : foo)
				{
					int i = 1;
					for (String param : ib.getParameters())
					{
						if (param.endsWith("Succ_" + succPref + "_" + a))
						{
							break;
						}
						i++;
					}
					params.add("__Succ" + i);
				}
				
				String tmp = foo.stream().collect(Collectors.joining("__"));
				String select = superPref + "_" + getSelf() + "_" + tmp + "<" + params.stream().collect(Collectors.joining(", ")) + ">";
				ib.addInterfaces(select);
			}
		}
	}
	
	// Pre: s has "preds", i.e. s implements a Successor I/f
	private void addSupertypeInterfaceToMethods(EState s, List<String> as, String superPref, String succPref)
	{
		for (String exclude : as)
		{
			List<String> foo = as.stream().filter((x) -> !x.equals(exclude)).collect(Collectors.toList());
			if (foo.size() > 0)
			{
				String tmp = foo.stream().collect(Collectors.joining("__"));
				String superName = superPref + "_" + getSelf() + "_" + tmp;
				for (InterfaceBuilder succif : this.preds.get(s))
				{
					addToCastMethod(succif, superName + "<" + IntStream.range(0, foo.size()).mapToObj((f) -> "?").collect(Collectors.joining(", ")) +">");
				}
				
				if (!this.iostates.containsKey(superName))
				{
					addSupertypeInterfaceToMethods(s, foo, superPref, succPref);
				}
			}
		}
	}
	
	// Pre: ib is a Successor I/f for the cast IO State I/f
	// Returns MethodBuilder, or null if none built
	private static MethodBuilder addToCastMethod(InterfaceBuilder ib, String ret)
	{
		if (ib.getDefaultMethods().stream().filter((def) -> def.getReturn().equals(ret)).count() > 0)
		{
			// Merge states entered from multiple paths, don't want to add cast multiple times -- still true for this case?
			// But duplicate cast check cast still needed anyway?
			return null;
		}
		MethodBuilder mb = ib.newDefaultMethod("to");
		mb.setReturn(ret);
		mb.addParameters(ret + " cast");
		if (!ret.equals("EndSocket") || ib.getName().startsWith("Succ"))  // HACK
		{
			mb.addBodyLine(JavaBuilder.RETURN + " (" + ret + ") this;");
		}
		else
		{
			ib.addImports("org.scribble.main.RuntimeScribbleException");
			mb.addBodyLine("throw new RuntimeScribbleException(\"Invalid cast to EndSocket: \" + cast);");
		}
		return mb;
	}

	private static MethodBuilder addEndSocketToCastMethod(ClassBuilder cb, String ret, String body)
	{
		//if (ib.getMethods().stream().filter((def) -> def.getReturn().equals(ret)).count() > 0)
		if (cb.hasMethodSignature(ret, ret + " "))  // Hacky
		{
			// Merge states entered from multiple paths, don't want to add cast multiple times -- still true for this case?
			// But duplicate cast check cast still needed anyway?
			return null;
		}
		MethodBuilder mb = cb.newMethod("to");
		mb.setReturn(ret);
		mb.addParameters(ret + " cast");
		//mb.addBodyLine("throw new RuntimeScribbleException(\"Invalid cast of EndSocket: \" + cast);");
		mb.addBodyLine(body);
		return mb;
	}

	private final
		Function<EState, String> getSuccName = (succ) ->
				(succ.isTerminal())
						? ScribSockGen.GENERATED_ENDSOCKET_NAME
						: this.apigen.getSocketClassName(succ);

	private String getConcreteSuccessorParameters(EState s)
	{
		return "<" +
				s.getActions().stream().sorted(IOStateIfaceGen.IOACTION_COMPARATOR)
						.map((a) -> this.getSuccName.apply(s.getSuccessor(a))).collect(Collectors.joining(", "))
				+ ">";
	}
	
	private void putPreAction(EState s, EAction a)
	{
		putMapSet(this.preActions, s, a);
	}

	private void putBranchPostAction(EState s, EAction a)
	{
		putMapSet(this.branchPostActions, s, a);
	}

	private static <K, V> void putMapSet(Map<K, Set<V>> map, K k, V v)
	{
		Set<V> tmp = map.get(k);
		if (tmp == null)
		{
			tmp = new LinkedHashSet<>();
			map.put(k, tmp);
		}
		tmp.add(v);
	}
	
	// Successor I/f's to be implemented by each I/O State I/f
	private void collectPreds()
	{
		for (EState s : this.preActions.keySet())
		{
			Set<InterfaceBuilder> tmp = new HashSet<>();
			for (EAction a : this.preActions.get(s))  // sort?
			{
				if (!a.isSend() && !a.isReceive())  // TODO (connect/disconnect)
				{
					throw new RuntimeException("TODO: " + a);
				}
				tmp.add(this.succs.get(a));
			}
			this.preds.put(s, tmp);
		}
	}
	
	private void collectBranchSuccs()
	{
		Role self = getSelf();
		for (EState s : this.branchPostActions.keySet())
		{
			//String key = HandlerInterfaceGenerator.getHandlerInterfaceName(IOStateInterfaceGenerator.getIOStateInterfaceName(self, s));
			String key = HandleIfaceGen.getHandleInterfaceName(self, s);  // HandleInterface name

			List<EAction> curr1 = new LinkedList<>();
			this.branchPostActions.get(s).forEach((a) -> curr1.addAll(s.getSuccessor(a).getActions()));  // TODO: flatmap
			//List<IOAction> curr2 = curr1.stream().sorted(IOStateInterfaceGenerator.IOACTION_COMPARATOR).collect(Collectors.toList());
			
			List<EAction> tmp = this.branchSuccs.get(key);
			if (tmp == null)
			{
				tmp = new LinkedList<>();
				//this.branchSuccs.put(key, tmp);
				tmp.addAll(curr1);
			}
			else
			{
				for (EAction a : curr1)
				{
					long n = curr1.stream().filter((x) -> x.equals(a)).count();
					long m = tmp.stream().filter((x) -> x.equals(a)).count();
					//System.out.println("EEE: " + curr1 + ",,, " + tmp);
					if (n > m)
					{
						for (int i = 0; i < n-m; i++)
						{
							tmp.add(a);
						}
					}
				}
			}
				
			this.branchSuccs.put(key, tmp.stream().sorted(IOStateIfaceGen.IOACTION_COMPARATOR).collect(Collectors.toList()));
			//System.out.println("AAA: " + this.branchSuccs.get(key));

			/*List<IOAction> tmp = this.branchSuccs.get(key);
			if (tmp == null)
			{
				tmp = new LinkedList<>();
				this.branchSuccs.put(key, tmp);
			}
			//this.branchPostActions.get(s).stream()//.sorted(IOStateInterfaceGenerator.IOACTION_COMPARATOR)
					//.forEach((a) -> { tmp.add(this.succs.get(a)); });
			for (IOAction a : this.branchPostActions.get(s))  // Already sorted -- guaranteed pairwise distinct (branch actions)  //.sorted(IOStateInterfaceGenerator.IOACTION_COMPARATOR)
			{
				// Not necessarily distinct (actions of the branch successor state)
				for (IOAction b : s.accept(a).getAcceptable().stream().sorted(IOStateInterfaceGenerator.IOACTION_COMPARATOR).collect(Collectors.toList()))
				{
					//tmp.add(this.succs.get(b));
					tmp.add(b);
				}
			}
			tmp = tmp.stream().so*/
		}
	}
	
	protected Role getSelf()
	{
		return this.apigen.getSelf();
	}

	protected static String getIOInterfacePackageName(GProtocolName gpn, Role self)
	{
		return SessionApiGenerator.getStateChannelPackageName(gpn, self) + ".ioifaces";
	}
	
	protected InterfaceBuilder getIOStateInterface(String name)
	{
		return this.iostates.get(name);
	}
}
