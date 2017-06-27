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
package org.scribble.codegen.java.util;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class InterfaceBuilder extends TypeBuilder
{
	private List<MethodBuilder> defaults = new LinkedList<>();

	public InterfaceBuilder()
	{
		
	}

	public InterfaceBuilder(String name)
	{
		super(name);
	}
	
	public MethodBuilder newDefaultMethod()
	{
		MethodBuilder mb = new MethodBuilder();
		mb.addModifiers(JavaBuilder.DEFAULT);
		this.defaults.add(mb);
		return mb;
	}

	public final MethodBuilder newDefaultMethod(String name)
	{
		MethodBuilder mb = newDefaultMethod();
		mb.setName(name);
		return mb;
	}
	
	public final List<MethodBuilder> getDefaultMethods()
	{
		return this.defaults;
	}
	
	@Override
	protected String buildSignature(String clazz)
	{
		if (!this.mods.isEmpty())
		{
			clazz += this.mods.stream().collect(Collectors.joining(" "));
			clazz += " ";
		}
		clazz += "interface " + this.name;
		if (!this.params.isEmpty())
		{
			clazz += "<" + this.params.stream().collect(Collectors.joining(", ")) + ">";
		}
		if (!this.ifaces.isEmpty())
		{
			clazz += " extends ";
			clazz += this.ifaces.stream().collect(Collectors.joining(", "));
		}
		return clazz;
	}

	@Override
	protected String buildMethods(String clazz)
	{
		clazz = super.buildMethods(clazz);
		if (!this.defaults.isEmpty())
		{
			clazz += "\n\n";
			clazz += this.defaults.stream().map((mb) -> mb.build()).collect(Collectors.joining("\n\n"));
		}
		return clazz;
	}
}
