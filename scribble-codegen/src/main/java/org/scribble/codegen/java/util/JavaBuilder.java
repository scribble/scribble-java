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

public abstract class JavaBuilder
{
	public static final String ABSTRACT = "abstract";
	public static final String DEFAULT = "default";
	public static final String VOID = "void";
	public static final String THIS = "this";
	public static final String SYNCHRONIZED = "synchronized";
	public static final String SUPER = "super";
	public static final String STATIC = "static";
	public static final String RETURN = "return";
	public static final String PUBLIC = "public";
	public static final String PROTECTED = "protected";
	public static final String PRIVATE = "private";
	public static final String NEW = "new";
	public static final String FINAL = "final";

	protected String name;

	public JavaBuilder()
	{

	}

	public JavaBuilder(String name)
	{
		this.name = name;
	}
	
	public abstract String build();
	
	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		setterCheck(this.name);
		this.name = name;
	}

	protected void setterCheck(String s)
	{
		if (s != null)
		{
			throw new RuntimeException("Value already set: " + s);
		}
	}
}
