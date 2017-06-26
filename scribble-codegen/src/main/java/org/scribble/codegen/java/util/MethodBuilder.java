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
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MethodBuilder extends JavaBuilder
{
	private final List<String> annots = new LinkedList<>();
	private final List<String> mods = new LinkedList<>();
	private String ret;  // null for constructor -- void must be set explicitly
	private final List<String> pars = new LinkedList<>();
	private final List<String> exceptions = new LinkedList<>();
	private final List<String> body = new LinkedList<>();

	protected MethodBuilder()
	{
		
	}

	protected MethodBuilder(String name)
	{
		super(name);
	}
	
	public void addAnnotations(String... mods)
	{
		this.annots.addAll(Arrays.asList(mods));
	}
	
	public void addModifiers(String... mods)
	{
		this.mods.addAll(Arrays.asList(mods));
	}
	
	public void setReturn(String ret)
	{
		setterCheck(this.ret);
		this.ret = ret;
	}

	public String getReturn()
	{
		return this.ret;
	}
	
	// Each par is the String: type + " " + name
	public void addParameters(String... par)
	{
		this.pars.addAll(Arrays.asList(par));
	}
	
	public List<String> getParameters()
	{
		return this.pars;
	}

	public void addExceptions(String... exceptions)
	{
		this.exceptions.addAll(Arrays.asList(exceptions));
	}
	
	public void addBodyLine(String ln)
	{
		this.body.add(ln);
	}

	public final void addBodyLine(int i, String ln)
	{
		for (int j = 0; j < i; j++)
		{
			ln = "\t" + ln;
		}
		addBodyLine(ln);
	}
	
	@Override
	public String build()
	{
		String meth = "";
		meth = buildSignature(meth);
		meth = buildBody(meth);
		return meth;
	}
	
	protected String buildSignature(String meth)
	{
		if (!this.annots.isEmpty())
		{
			meth += "\t";
			meth += this.annots.stream().collect(Collectors.joining("\n\t")) + "\n";
		}
		meth += "\t";
		if (!this.mods.isEmpty())
		{
			meth += this.mods.stream().collect(Collectors.joining(" "));
			meth += " ";
		}
		if (this.ret != null)
		{
			meth += this.ret + " ";
		}
		meth += name + "(";
		meth += this.pars.stream().collect(Collectors.joining(", "));
		meth += ")";
		if (!this.exceptions.isEmpty())
		{
			meth += " throws " + this.exceptions.stream().collect(Collectors.joining(", "));
		}
		return meth;
	}

	protected String buildBody(String meth)
	{
		meth += " {\n";
		if (!this.body.isEmpty())
		{
			meth += "\t\t";
			meth += this.body.stream().collect(Collectors.joining("\n\t\t"));
		}
		meth += "\n\t}";
		return meth;
	}
}
