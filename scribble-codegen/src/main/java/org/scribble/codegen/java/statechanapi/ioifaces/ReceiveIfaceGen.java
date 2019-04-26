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

import java.util.Map;

import org.scribble.codegen.java.sessionapi.SessionApiGenerator;
import org.scribble.codegen.java.statechanapi.InputFutureGen;
import org.scribble.codegen.java.statechanapi.ReceiveSockGen;
import org.scribble.codegen.java.statechanapi.ScribSockGen;
import org.scribble.codegen.java.statechanapi.StateChannelApiGenerator;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.core.model.endpoint.EState;
import org.scribble.core.model.endpoint.actions.EAction;
import org.scribble.core.type.name.GProtoName;
import org.scribble.util.ScribException;

public class ReceiveIfaceGen extends IOStateIfaceGen
{
	public ReceiveIfaceGen(StateChannelApiGenerator apigen, Map<EAction, InterfaceBuilder> actions, EState curr)
	{
		super(apigen, actions, curr);
	}

	@Override
	public InterfaceBuilder generateType() throws ScribException
	{
		if (this.curr.getActions().stream().anyMatch((a) -> !a.isReceive())) // TODO (connect/disconnect)
		{
			//return null;
			throw new RuntimeException("TODO: " + this.curr);
		}
		return super.generateType();
	}

	@Override
	protected void constructInterface() throws ScribException
	{
		super.constructInterface();
		addAsyncDiscardMethod();
	}

	protected void addAsyncDiscardMethod()
	{
		GProtoName gpn = this.apigen.getGProtocolName();
		EAction first = this.curr.getDetActions().iterator().next();

		MethodBuilder mb = this.ib.newAbstractMethod();
		ReceiveSockGen.setAsyncDiscardHeaderWithoutReturnType(this.apigen, first, mb, InputFutureGen.getInputFutureName(this.apigen.getSocketClassName(this.curr)));
		this.ib.addImports(SessionApiGenerator.getOpsPackageName(gpn) + ".*");
		EState succ = this.curr.getDetSuccessor(first);
		if (succ.isTerminal())
		{
			ScribSockGen.setNextSocketReturnType(this.apigen, mb, succ);
		}
		else
		{
			mb.setReturn("__Succ1");  // Hacky?  // FIXME: factor out Succ
		}
	}
}
