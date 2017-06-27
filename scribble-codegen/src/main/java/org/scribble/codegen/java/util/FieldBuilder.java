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

public class FieldBuilder extends JavaBuilder
{
	private List<String> mods = new LinkedList<>();
	private String type;
	private String expr;  // null if none

	public FieldBuilder()
	{
		
	}
	
	public FieldBuilder(String name)
	{
		super(name);
	}
	
	public void addModifiers(String... mods)
	{
		this.mods.addAll(Arrays.asList(mods));
	}
	
	public void setType(String type)
	{
		setterCheck(this.type);
		this.type = type;
	}
	
	public void setExpression(String val)
	{
		setterCheck(this.expr);
		this.expr = val;
	}

	@Override
	public String build()
	{
		String field = "";
		field += "\t";
		if (!this.mods.isEmpty())
		{
			field += this.mods.stream().collect(Collectors.joining(" "));
			field += " ";
		}
		field += this.type + " " + this.name;
		if (this.expr != null)
		{
			field += " = " + this.expr;
		}
		field += ";";
		return field;
	}
}