package org.scribble.codegen.java;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class EnumBuilder
{
	private String name;

	private List<String> mods = new LinkedList<>();
	private final List<String> ifaces = new LinkedList<String>();
	private final List<String> vals = new LinkedList<String>();

	protected EnumBuilder()
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
	
	public void addInterfaces(String... ifaces)
	{
		this.ifaces.addAll(Arrays.asList(ifaces));
	}
	
	public void addValues(String... vals)
	{
		this.vals.addAll(Arrays.asList(vals));
	}
	
	public String generate()
	{
		String enun = "";
		if (!this.mods.isEmpty())
		{
			enun += this.mods.stream().collect(Collectors.joining(" "));
			enun += " ";
		}
		enun += "enum " + this.name;
		if (!this.ifaces.isEmpty())
		{
			enun += " implements ";
			enun += this.ifaces.stream().collect(Collectors.joining(", "));
		}
		enun += " {\n";
		enun += "\t" + this.vals.stream().collect(Collectors.joining(", "));
		enun += "\n}";
		return enun;
	}
}