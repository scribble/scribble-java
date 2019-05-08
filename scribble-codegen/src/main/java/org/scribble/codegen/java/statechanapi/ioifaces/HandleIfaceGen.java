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
package org.scribble.codegen.java.statechanapi.ioifaces;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.codegen.java.sessionapi.SessionApiGenerator;
import org.scribble.codegen.java.statechanapi.HandlerIfaceGen;
import org.scribble.codegen.java.statechanapi.ScribSockGen;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.core.model.endpoint.EState;
import org.scribble.core.model.endpoint.actions.EAction;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.Role;
import org.scribble.util.ScribException;

// Cf. HandlerInterfaceGenerator
public class HandleIfaceGen extends IOStateIfaceGen
{
	//private final IOInterfacesGenerator iogen;
	
	private final Map<EAction, InterfaceBuilder> caseActions;

	public HandleIfaceGen(IOInterfacesGenerator iogen, Map<EAction, InterfaceBuilder> actions, EState curr, Map<EAction, InterfaceBuilder> caseActions)
	{
		super(iogen.apigen, actions, curr);
		//this.iogen = iogen;

		this.caseActions = caseActions;
	}

	@Override
	protected void constructInterface() throws ScribException
	{
		super.constructInterface();
		addHandleMethods();
	}

	@Override
	protected void addHeader()
	{
		GProtoName gpn = this.apigen.getGProtocolName();
		Role self = this.apigen.getSelf();
		String packname = IOInterfacesGenerator.getIOInterfacePackageName(gpn, self);
		String ifname = getHandleInterfaceName(self, this.curr);

		this.ib.setName(ifname);
		this.ib.setPackage(packname);
		this.ib.addModifiers(JavaBuilder.PUBLIC);
	}

	@Override
	protected void addCastField()
	{

	}

	@Override
	protected void addSuccessorParamsAndActionInterfaces()
	{
		//Role self = this.apigen.getSelf();

		int i = 1;
		//for (IOAction a : getHandleInterfaceIOActionParams(this.curr))  // Branch successor state successors, not the "direct" successors
		// Duplicated from BranchInterfaceGenerator
		for (EAction a : this.curr.getDetActions().stream().sorted(IOACTION_COMPARATOR).collect(Collectors.toList()))
		{
			this.ib.addParameters("__Succ" + i + " extends " + SuccessorIfaceGen.getSuccessorInterfaceName(a));
			this.ib.addInterfaces(this.caseActions.get(a).getName() + "<__Succ" + i + ">");
			i++;
		}
		/*for (InterfaceBuilder ib : this.succifs)  // Already sorted
		{
			this.ib.addParameters("__Succ" + i + " extends " + ib.getName());
			i++;
		}*/
	}

	/*// Pre: curr is a branch state
	private static List<IOAction> getHandleInterfaceIOActionParams(EndpointState curr)
	{
		List<IOAction> as = new LinkedList<>();
		for (IOAction a : curr.getAcceptable().stream().sorted(IOACTION_COMPARATOR).collect(Collectors.toList()))
		{
			EndpointState succ = curr.accept(a);
			/*InterfaceBuilder iostateif = this.iogen.getIOStateInterface(IOStateInterfaceGenerator.getIOStateInterfaceName(self, succ));
			for (String param : iostateif.getParameters())
			{
				this.ib.addParameters("__Succ" + i + " extends " + SuccessorInterfaceGenerator.getSuccessorInterfaceName(this.curr, a));
			}* /
			for (IOAction b : succ.getAcceptable().stream().sorted(IOACTION_COMPARATOR).collect(Collectors.toList()))
			{
				//if (!as.contains(b))
				{
					as.add(b);
				}
			}
		}
		return as;
	}*/

	protected void addHandleMethods() throws ScribException
	{
		GProtoName gpn = this.apigen.getGProtocolName();
		//Role self = this.apigen.getSelf();
		//Set<EAction> as = this.curr.getActions();
		List<EAction> as = this.curr.getDetActions();

		this.ib.addImports(SessionApiGenerator.getOpsPackageName(gpn) + ".*");
		int i = 1; 
		for (EAction a : as.stream().sorted(IOACTION_COMPARATOR).collect(Collectors.toList()))
		{
			/*EndpointState succ = this.curr.accept(a);
			MethodBuilder mb = this.ib.newAbstractMethod();
			HandlerInterfaceGenerator.setHandleMethodHeaderWithoutParamTypes(this.apigen, mb);
			i = setHandleMethodSuccessorParam(this.iogen, self, succ, mb, i);
			HandlerInterfaceGenerator.addHandleMethodOpAndPayloadParams(this.apigen, a, mb);*/

			MethodBuilder mb = this.ib.newAbstractMethod();
			HandlerIfaceGen.setHandleMethodHeaderWithoutParamTypes(this.apigen, mb);
			//setHandleMethodSuccessorParam(this.iogen, self, succ, mb);
			mb.addParameters("__Succ" + i++ + " schan");
			HandlerIfaceGen.addHandleMethodOpAndPayloadParams(this.apigen, a, mb);
		}
	}

	//protected static int setHandleMethodSuccessorParam(IOInterfacesGenerator iogen, Role self, EndpointState succ, MethodBuilder mb, int i)
	protected static void setHandleMethodSuccessorParam(IOInterfacesGenerator iogen, Role self, EState succ, MethodBuilder mb, List<EAction> as, Map<EAction, Integer> count)
	{
		if (succ.isTerminal())
		{
			mb.addParameters(ScribSockGen.ENDSOCKET_CLASS + "<?, ?> end");
		}
		else
		{
			InterfaceBuilder next = iogen.getIOStateInterface(IOStateIfaceGen.getIOStateInterfaceName(self, succ));  // Select/Receive/Branch
			String ret = next.getName() + "<";
			/*//ret += "<" + next.getParameters().stream().map((p) -> "__Succ" + i++).collect(Collectors.joining(", ")) + ">";  // FIXME: fragile?
			boolean first = true;
			for (String p : next.getParameters())
			{
				if (first)
				{
					first = false;
				}
				else
				{
					ret += ", ";
				}
				ret += "__Succ" + i++;
			}
			ret += ">";
			mb.addParameters(ret + " schan");
			
			// duplicates possible?  -- can have repeat continuations, but the branch operation ops themselves will be distinct
		}
		return i;*/
		
			//Map<IOAction, Integer> ount = new HashMap<>();
			boolean first = true;
			for (EAction a : succ.getDetActions().stream().sorted(IOStateIfaceGen.IOACTION_COMPARATOR).collect(Collectors.toList()))
			{
				int offset;
				if (!count.containsKey(a))
				{
					offset = 0;
					count.put(a, 0);
				}
				else
				{
					offset = count.get(a) + 1;
					count.put(a, offset);
				}
				//if (count.keySet().size() > 1)
				if (first)
				{
					first = false;
				}
				else
				{
					ret += ", ";
				}
				ret += "__Succ" + (as.indexOf(a) + 1 + offset);
			}
			ret += ">";
			mb.addParameters(ret + " schan");
		}
	}

	// Pre: s is a branch state
	public static String getHandleInterfaceName(Role self, EState s)
	{
		// FIXME: factor out (CaseInterfaceGenerator, IOStateInterfaceGenerator.getIOStateInterfaceName)
		String name = "Handle_" + self + "_" + s.getDetActions().stream().sorted(IOACTION_COMPARATOR)
				.map((a) -> ActionIfaceGen.getActionString(a)).collect(Collectors.joining("__"));
		IOStateIfaceGen.checkIOStateInterfaceNameLength(name);
		return name;
	}
}
