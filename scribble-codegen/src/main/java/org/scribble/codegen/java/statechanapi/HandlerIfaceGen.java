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
package org.scribble.codegen.java.statechanapi;

import org.scribble.ast.SigDecl;
import org.scribble.ast.Module;
import org.scribble.codegen.java.sessionapi.SessionApiGenerator;
import org.scribble.codegen.java.util.AbstractMethodBuilder;
import org.scribble.codegen.java.util.ClassBuilder;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.core.model.endpoint.EState;
import org.scribble.core.model.endpoint.actions.EAction;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.SigName;
import org.scribble.util.ScribException;

// Factor out
public class HandlerIfaceGen extends AuxStateChanTypeGen
{
	private final EState curr;

	// Pre: cb is the BranchSocketBuilder
	public HandlerIfaceGen(StateChannelApiGenerator apigen, ClassBuilder parent, EState curr)
	{
		super(apigen, parent);
		this.curr = curr;
	}

	@Override
	public InterfaceBuilder generateType() throws ScribException
	{
		GProtoName gpn = this.apigen.getGProtocolName();

		// Handler interface
		InterfaceBuilder ib = new InterfaceBuilder();
		ib.setPackage(SessionApiGenerator.getStateChannelPackageName(gpn, this.apigen.getSelf()));  // FIXME: factor out with ScribSocketBuilder
		ib.addImports(SessionApiGenerator.getOpsPackageName(gpn) + ".*");  // FIXME: factor out with ScribSocketBuilder
		//ib.setName(getHandlerInterfaceName(parent));
		ib.setName(getHandlerInterfaceName(this.parent.getName()));
		ib.addModifiers(InterfaceBuilder.PUBLIC);

		for (EAction a : this.curr.getDetActions())  // Doesn't need to be sorted
		{
			EState succ = this.curr.getDetSuccessor(a);
			String nextClass = this.apigen.getSocketClassName(succ);

			AbstractMethodBuilder mb3 = ib.newAbstractMethod();
			if (!this.apigen.skipIOInterfacesGeneration)
			{
				mb3.addAnnotations("@Override");
			}
			setHandleMethodHeaderWithoutParamTypes(this.apigen, mb3);
			if (succ.isTerminal())
			{
				mb3.addParameters(ScribSockGen.GENERATED_ENDSOCKET_NAME + " schan");  // FIXME: factor out
			}
			else
			{
				mb3.addParameters(nextClass + " schan");  // FIXME: factor out
			}
			addHandleMethodOpAndPayloadParams(this.apigen, a, mb3);
			
			if (this.curr.getDetSuccessor(a).isTerminal())
			{
				// FIXME: potentially repeated (but OK)
				ib.addImports(SessionApiGenerator.getEndpointApiRootPackageName(gpn) + ".*");  // FIXME: factor out with ScribSocketBuilder
				ib.addImports(SessionApiGenerator.getRolesPackageName(this.apigen.getGProtocolName()) + ".*");
			}
		}

		return ib;
	}

	// void return type -- have to deal with Succ as param
	public static void setHandleMethodHeaderWithoutParamTypes(StateChannelApiGenerator apigen, MethodBuilder mb)
	{
		mb.setName("receive");
		mb.addModifiers(JavaBuilder.PUBLIC);
		mb.setReturn(InterfaceBuilder.VOID);
		mb.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "java.io.IOException", "ClassNotFoundException");
	}
	
	public static void addHandleMethodOpAndPayloadParams(StateChannelApiGenerator apigen, EAction a, MethodBuilder mb) throws ScribException
	{
		Module main = apigen.getMainModule();
		String opClass = SessionApiGenerator.getOpClassName(a.mid);

		mb.addParameters(opClass + " " + StateChannelApiGenerator.RECEIVE_OP_PARAM);  // More params added below

		if (a.mid.isOp())
		{	
			ReceiveSockGen.addReceiveOpParams(mb, apigen.getMainModule(), a, false);
		}
		else //if (a.mid.isMessageSigName())
		{
			SigDecl msd = main.getSigDeclChild(((SigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
			ReceiveSockGen.addReceiveMessageSigNameParams(mb, msd, false);
		}
	}

	// Pre: cb is the BranchSocketBuilder
	//public static String getHandlerInterfaceName(TypeBuilder cb)
	public static String getHandlerInterfaceName(String branchName)
	{
		return branchName + "_Handler";
	}
}
