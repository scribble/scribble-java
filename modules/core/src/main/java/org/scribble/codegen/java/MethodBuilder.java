package org.scribble.codegen.java;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MethodBuilder
{
	private String name;

	private List<String> annots = new LinkedList<>();
	private List<String> mods = new LinkedList<>();
	private String ret;  // null for constructor -- void must be set explicitly
	private List<String> pars = new LinkedList<>();
	private List<String> exceptions = new LinkedList<>();
	private List<String> body = new LinkedList<>();

	protected MethodBuilder()
	{
		
	}
	
	protected void setName(String name)
	{
		this.name = name;
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
		this.ret = ret;
	}
	
	// Each par is the String: type + " " + name
	public void addParameters(String... par)
	{
		this.pars.addAll(Arrays.asList(par));
	}

	public void addExceptions(String... exceptions)
	{
		this.exceptions.addAll(Arrays.asList(exceptions));
	}
	
	public void addBodyLine(String ln)
	{
		this.body.add(ln);
	}

	public void addBodyLine(int i, String ln)
	{
		for (int j = 0; j < i; j++)
		{
			ln = "\t" + ln;
		}
		this.body.add(ln);
	}
	
	public String generate()
	{
		String meth = "";
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