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
package org.scribble.codegen.java.endpointapi;

import java.util.List;

import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public class AcceptSocketGenerator extends ScribSocketGenerator
{
	public AcceptSocketGenerator(StateChannelApiGenerator apigen, EState curr)
	{
		super(apigen, curr);
	}

	@Override
	protected String getSuperClassType()
	{
		return ACCEPTSOCKET_CLASS + "<" + getSessionClassName() + ", " + getSelfClassName() + ">";
	}

	@Override
	protected void addImports()
	{
		super.addImports();
		//this.cb.addImports(getOpsPackageName() + ".*");
	}

	@Override
	protected void addMethods()
	{
		//Set<EAction> as = curr.getActions();
		List<EAction> as = curr.getActions();
		if (as.size() > 1)
		{
			throw new RuntimeException("AcceptSocket generation not yet supported for accept-branches: " + as);
		}
		EAction a = as.iterator().next();
		EState succ = curr.getSuccessor(a);
		makeAcceptMethod(a, succ);
	}

	private void makeAcceptMethod(EAction a, EState succ)
	{
		MethodBuilder mb = makeAcceptHeader(a, succ);
		mb.addBodyLine(JavaBuilder.SUPER + ".accept(ss, " + getSessionApiRoleConstant(a.obj) + ");");
		addReturnNextSocket(mb, succ);
	}

	private MethodBuilder makeAcceptHeader(EAction a, EState succ)
	{
		MethodBuilder mb = this.cb.newMethod();
		setAcceptHeaderWithoutReturnType(this.apigen, a, mb);
		setNextSocketReturnType(this.apigen, mb, succ);
		return mb;
	}

	// Doesn't include return type
	//public static void makeReceiveHeader(StateChannelApiGenerator apigen, IOAction a, EndpointState succ, MethodBuilder mb)
	public static void setAcceptHeaderWithoutReturnType(StateChannelApiGenerator apigen, EAction a, MethodBuilder mb)
	{
		final String ROLE_PARAM = "role";
			
		mb.setName("accept");
		mb.addModifiers(JavaBuilder.PUBLIC);
		mb.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "java.io.IOException");
		mb.addParameters(SessionApiGenerator.getRoleClassName(a.obj) + " " + ROLE_PARAM, SCRIBSERVERSOCKET_CLASS + " ss");
	}
}
