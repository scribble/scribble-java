package org.scribble.codegen.java.util;

import java.util.Arrays;
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
			if (mb.getReturn().equals(ret) && mb.getParameters().equals(Arrays.asList(params)))
			{
				return true;
			}
		}
		return false;
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
