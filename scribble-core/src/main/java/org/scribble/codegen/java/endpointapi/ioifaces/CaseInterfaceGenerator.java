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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.codegen.java.endpointapi.CaseSocketGenerator;
import org.scribble.codegen.java.endpointapi.ScribSocketGenerator;
import org.scribble.codegen.java.endpointapi.SessionApiGenerator;
import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.util.AbstractMethodBuilder;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

public class CaseInterfaceGenerator extends IOStateInterfaceGenerator
{
	public CaseInterfaceGenerator(StateChannelApiGenerator apigen, Map<EAction, InterfaceBuilder> actions, EState curr)
	{
		super(apigen, actions, curr);
	}

	@Override
	protected void constructInterface() throws ScribbleException
	{
		super.constructInterface();
		addBranchEnumField();
		addCaseReceiveDiscardMethods();
	}

	@Override
	protected void addHeader()
	{
		GProtocolName gpn = this.apigen.getGProtocolName();
		Role self = this.apigen.getSelf();
		String packname = IOInterfacesGenerator.getIOInterfacePackageName(gpn, self);
		String ifname = getCasesInterfaceName(self, this.curr);

		this.ib.setName(ifname);
		this.ib.setPackage(packname);
		this.ib.addModifiers(JavaBuilder.PUBLIC);
	}

	/*@Override
	protected void addSuccessorInterfaces()
	{

	}*/
	
	protected void addBranchEnumField()
	{
		Role self = this.apigen.getSelf();
		String name = super.getIOStateInterfaceName(self, this.curr);

		AbstractMethodBuilder op = this.ib.newAbstractMethod("getOp");
		op.setReturn(name + "." + BranchInterfaceGenerator.getBranchInterfaceEnumName(self, this.curr));
	}
				
	protected void addCaseReceiveDiscardMethods()
	{
		GProtocolName gpn = this.apigen.getGProtocolName();
		//Set<EAction> as = this.curr.getActions();
		List<EAction> as = this.curr.getActions();

		int i = 1;
		this.ib.addImports(SessionApiGenerator.getOpsPackageName(gpn) + ".*");
		for (EAction a : as.stream().sorted(IOACTION_COMPARATOR).collect(Collectors.toList()))
		{
			MethodBuilder mb = this.ib.newAbstractMethod();
			CaseSocketGenerator.setCaseReceiveDiscardHeaderWithoutReturnType(this.apigen, a, mb); 
			EState succ = this.curr.getSuccessor(a);
			if (succ.isTerminal())
			{
				ScribSocketGenerator.setNextSocketReturnType(this.apigen, mb, succ);
			}
			else
			{
				mb.setReturn("__Succ" + i);  // Hacky?  // FIXME: factor out Succ
			}
			i++;
		}
	}
	
	//protected static String getCasesInterfaceName(String braif)
	// Pre: s is a branch state
	// Cf. IOStateInterfaceGenerator.getIOStateInterfaceName
	protected static String getCasesInterfaceName(Role self, EState s)
	{
		//return "Case_" + braif.substring("Branch_".length(), braif.length());
		return "Case_" + self + "_" + s.getActions().stream().sorted(IOACTION_COMPARATOR)
				.map((a) -> ActionInterfaceGenerator.getActionString(a)).collect(Collectors.joining("__"));
	}
}
