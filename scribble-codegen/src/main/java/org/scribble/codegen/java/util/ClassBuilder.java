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

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ClassBuilder extends TypeBuilder
{
	protected String superclass;  // null if none explicit

	protected final List<MethodBuilder> ctors = new LinkedList<>();

	// No name par because so far useful to start constructing before setting the name
	public ClassBuilder()
	{
		
	}
	
	public ClassBuilder(String name)
	{
		super(name);
	}

	public void setSuperClass(String superc)
	{
		setterCheck(this.superclass);
		this.superclass = superc;
	}
	
	// Each par is the String: type + " " + name -- cf. MethodBuilder
	public ConstructorBuilder newConstructor(String... pars)  // FIXME: ConstructorBuilder
	{
		ConstructorBuilder mb = new ConstructorBuilder(this.name);
		mb.addParameters(pars);
		this.ctors.add(mb);
		return mb;
	}

	public MethodBuilder newMethod()
	{
		MethodBuilder mb = new MethodBuilder();
		this.methods.add(mb);
		return mb;
	}

	public final MethodBuilder newMethod(String name)
	{
		MethodBuilder mb = newMethod();
		mb.setName(name);
		return mb;
	}
	
	public final boolean hasMethodSignature(String ret, String... params)
	{
		for (MethodBuilder mb : this.methods)
		{
			if (mb.getReturn().equals(ret) && equalParamSigs(Arrays.asList(params), mb.getParameters()))
			{
				return true;
			}
		}
		return false;
	}
	
	// FIXME: factor out with TypeBuilder
	//private static boolean equalParamSigs(MethodBuilder mb1, MethodBuilder mb2)
	private static boolean equalParamSigs(List<String> mb1, List<String> mb2)
	{
		if (mb1.size() == 0)
		{
			if (mb2.size() != 0)
			{
				return false;
			}
		}
		else
		{
			Iterator<String> params2 = mb2.iterator();
			for (String param1 : mb1)
			{
				if (!params2.hasNext())
				{
					return false;
				}
				String param2 = params2.next();
			
				if (param2.contains("<"))
				{
					param2 = param2.substring(0, param2.indexOf("<"));
				}
				else
				{
					param2 = param2.substring(0, param2.indexOf(" "));
				}
				if (param1.contains("<"))
				{
					param1 = param1.substring(0, param1.indexOf("<"));
				}
				else
				{
					param1 = param1.substring(0, param1.indexOf(" "));
				}
				if (!param1.equals(param2))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	protected String buildSignature(String clazz)
	{
		if (!this.mods.isEmpty())
		{
			clazz += this.mods.stream().collect(Collectors.joining(" "));
			clazz += " ";
		}
		clazz += "class " + this.name;
		if (!this.params.isEmpty())
		{
			clazz += "<" + this.params.stream().collect(Collectors.joining(", ")) + ">";
		}
		if (this.superclass != null)
		{
			clazz += " extends " + this.superclass;
		}
		if (!this.ifaces.isEmpty())
		{
			clazz += " implements ";
			clazz += this.ifaces.stream().collect(Collectors.joining(", "));
		}
		return clazz;
	}
	
	@Override
	public String buildBody(String clazz)
	{
		clazz += " {";
		clazz = buildFields(clazz);
		clazz = buildConstructors(clazz);
		clazz = buildMethods(clazz);
		clazz = buildMemberTypes(clazz);
		clazz += "\n}";
		return clazz;
	}

	protected String buildConstructors(String clazz)
	{
		if (!this.ctors.isEmpty())
		{
			clazz += "\n\n";
			clazz += this.ctors.stream().map((mb) -> mb.build()).collect(Collectors.joining("\n\n"));
		}
		return clazz;
	}
}
