package org.scribble.codegen.java.endpointapi.ioifaces;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.codegen.java.endpointapi.HandlerInterfaceGenerator;
import org.scribble.codegen.java.endpointapi.ScribSocketGenerator;
import org.scribble.codegen.java.endpointapi.SessionApiGenerator;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

// Cf. HandlerInterfaceGenerator
public class HandleInterfaceGenerator extends IOStateInterfaceGenerator
{
	//private final IOInterfacesGenerator iogen;
	
	private final Map<IOAction, InterfaceBuilder> caseActions;

	public HandleInterfaceGenerator(IOInterfacesGenerator iogen, Map<IOAction, InterfaceBuilder> actions, EndpointState curr, Map<IOAction, InterfaceBuilder> caseActions)
	{
		super(iogen.apigen, actions, curr);
		//this.iogen = iogen;

		this.caseActions = caseActions;
	}

	@Override
	protected void constructInterface() throws ScribbleException
	{
		super.constructInterface();
		addHandleMethods();
	}

	@Override
	protected void addHeader()
	{
		GProtocolName gpn = this.apigen.getGProtocolName();
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
		for (IOAction a : this.curr.getTakeable().stream().sorted(IOACTION_COMPARATOR).collect(Collectors.toList()))
		{
			this.ib.addParameters("__Succ" + i + " extends " + SuccessorInterfaceGenerator.getSuccessorInterfaceName(a));
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

	protected void addHandleMethods() throws ScribbleException
	{
		GProtocolName gpn = this.apigen.getGProtocolName();
		//Role self = this.apigen.getSelf();
		Set<IOAction> as = this.curr.getTakeable();

		this.ib.addImports(SessionApiGenerator.getOpsPackageName(gpn) + ".*");
		int i = 1; 
		for (IOAction a : as.stream().sorted(IOACTION_COMPARATOR).collect(Collectors.toList()))
		{
			/*EndpointState succ = this.curr.accept(a);
			MethodBuilder mb = this.ib.newAbstractMethod();
			HandlerInterfaceGenerator.setHandleMethodHeaderWithoutParamTypes(this.apigen, mb);
			i = setHandleMethodSuccessorParam(this.iogen, self, succ, mb, i);
			HandlerInterfaceGenerator.addHandleMethodOpAndPayloadParams(this.apigen, a, mb);*/

			MethodBuilder mb = this.ib.newAbstractMethod();
			HandlerInterfaceGenerator.setHandleMethodHeaderWithoutParamTypes(this.apigen, mb);
			//setHandleMethodSuccessorParam(this.iogen, self, succ, mb);
			mb.addParameters("__Succ" + i++ + " schan");
			HandlerInterfaceGenerator.addHandleMethodOpAndPayloadParams(this.apigen, a, mb);
		}
	}

	//protected static int setHandleMethodSuccessorParam(IOInterfacesGenerator iogen, Role self, EndpointState succ, MethodBuilder mb, int i)
	protected static void setHandleMethodSuccessorParam(IOInterfacesGenerator iogen, Role self, EndpointState succ, MethodBuilder mb, List<IOAction> as, Map<IOAction, Integer> count)
	{
		if (succ.isTerminal())
		{
			mb.addParameters(ScribSocketGenerator.ENDSOCKET_CLASS + "<?, ?> end");
		}
		else
		{
			InterfaceBuilder next = iogen.getIOStateInterface(IOStateInterfaceGenerator.getIOStateInterfaceName(self, succ));  // Select/Receive/Branch
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
			for (IOAction a : succ.getTakeable().stream().sorted(IOStateInterfaceGenerator.IOACTION_COMPARATOR).collect(Collectors.toList()))
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
	public static String getHandleInterfaceName(Role self, EndpointState s)
	{
		// FIXME: factor out (CaseInterfaceGenerator, IOStateInterfaceGenerator.getIOStateInterfaceName)
		return "Handle_" + self + "_" + s.getTakeable().stream().sorted(IOACTION_COMPARATOR)
				.map((a) -> ActionInterfaceGenerator.getActionString(a)).collect(Collectors.joining("__"));
	}
}
