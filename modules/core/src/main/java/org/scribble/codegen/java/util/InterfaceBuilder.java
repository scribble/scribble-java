package org.scribble.codegen.java.util;

import java.util.stream.Collectors;

public class InterfaceBuilder extends TypeBuilder
{
	public InterfaceBuilder()
	{
		
	}

	public InterfaceBuilder(String name)
	{
		
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
}
