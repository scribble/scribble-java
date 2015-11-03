package org.scribble.codegen.java;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FieldBuilder
{
	private String name;

	private List<String> mods = new LinkedList<>();
	private String type;
	private String expr;  // null if none

	protected FieldBuilder()
	{
		
	}
	
	protected void setName(String name)
	{
		this.name = name;
	}
	
	public void addModifiers(String... mods)
	{
		this.mods.addAll(Arrays.asList(mods));
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	protected void setExpression(String val)
	{
		this.expr = val;
	}

	public String generate()
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