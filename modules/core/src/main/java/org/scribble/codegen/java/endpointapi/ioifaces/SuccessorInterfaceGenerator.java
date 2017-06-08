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

import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public class SuccessorInterfaceGenerator extends IOInterfaceGenerator
{
	private final EAction a;
	private final InterfaceBuilder ib = new InterfaceBuilder();

	public SuccessorInterfaceGenerator(StateChannelApiGenerator apigen, EState curr, EAction a)
	{
		super(apigen, curr);
		this.a = a;
	}

	@Override
	public InterfaceBuilder generateType()
	{
		this.ib.setName(getSuccessorInterfaceName(this.a));
		this.ib.setPackage(IOInterfacesGenerator.getIOInterfacePackageName(this.apigen.getGProtocolName(), this.apigen.getSelf()));
		this.ib.addModifiers(JavaBuilder.PUBLIC);
		//this.ib.addInterfaces(ifaces);(...State...);
		return ib;
	}
	
	public static String getSuccessorInterfaceName(EAction a)
	{
		return "Succ_" + ActionInterfaceGenerator.getActionInterfaceName(a);
	}
}
