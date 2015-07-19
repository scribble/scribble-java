package org.scribble.codegen;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassBuilder
{
	private String packname;
	private List<String> imports;
	private List<String> mods;
	private String name;
	private Map<String, FieldBuilder> fields = new HashMap<>();
	private Map<List<String>, MethodBuilder> ctors = new HashMap<>();
	private Map<String, MethodBuilder> methods = new HashMap<>();

	public ClassBuilder()
	{
		
	}
	
	public void setPackageName(String packname)
	{
		
	}
	
	public void addImports(String... imports)
	{
		this.imports.addAll(Arrays.asList(imports));
	}
	
	public void addModifiers(String... mods)
	{
		this.mods.addAll(Arrays.asList(mods));
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public FieldBuilder newField(String name)
	{
		FieldBuilder fb = new FieldBuilder();
		fb.setName(name);
		this.fields.put(name, fb);
		return fb;
	}
	
	public MethodBuilder newConstructor(String... pars)
	{
		MethodBuilder mb = new MethodBuilder();
		mb.setName(this.name);
		mb.addParameters(pars);
		this.ctors.put(Arrays.asList(pars), mb);
		return mb;
	}
	
	/*public MethodBuilder getConstructor(String name)
	{
		return this.methods.getConstructor(name);
	}*/
	
	public MethodBuilder newMethod(String name)
	{
		MethodBuilder mb = new MethodBuilder();
		mb.setName(name);
		this.methods.put(name, mb);
		return mb;
	}
	
	/*public MethodBuilder getMethod(String name)
	{
		return this.methods.getMethod(name);
	}*/
	
	public String generate()
	{
		String clazz = "";
		clazz += "package " + packname + ";";
		if (this.imports.size() > 0)
		{
			clazz += "\n\n";
			clazz += this.imports.stream().collect(Collectors.joining(";\n"));
			clazz += ";";
		}
		clazz += "\n\n";
		if (this.mods.size() > 0)
		{
			clazz += this.mods.stream().collect(Collectors.joining(" "));
			clazz += " ";
		}
		clazz += "class " + this.name + " {";
		if (this.ctors.size() > 0)
		{
			clazz += "\n\n";
			clazz += this.ctors.values().stream().map((mb) -> mb.generate()).collect(Collectors.joining("\n\n"));
		}
		if (this.methods.size() > 0)
		{
			clazz += "\n\n";
			clazz += this.methods.values().stream().map((mb) -> mb.generate()).collect(Collectors.joining("\n\n"));
		}
		clazz += "\n}";
		return clazz;
	}
}

class FieldBuilder
{
	private List<String> mods = new LinkedList<>();
	private String type;
	private String name;
	private String expr;

	public FieldBuilder()
	{
		
	}
	
	public void addModifiers(String... mods)
	{
		this.mods.addAll(Arrays.asList(mods));
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	protected void setName(String name)
	{
		this.name = name;
	}
	
	protected void setExpression(String val)
	{
		this.name = val;
	}

	public String generate()
	{
		String field = "";
		field += "\t";
		if (this.mods.size() > 0)
		{
			field += this.mods.stream().collect(Collectors.joining(" "));
			field += " ";
		}
		field += this.type + " " + name;
		if (this.expr != null)
		{
			field += " = " + expr;
		}
		field += ";";
		return field;
	}
}

class MethodBuilder
{
	private List<String> mods = new LinkedList<>();
	private String ret;
	private String name;
	private List<String> pars = new LinkedList<>();
	private List<String> body = new LinkedList<>();

	public MethodBuilder()
	{
		
	}
	
	public void addModifiers(String... mods)
	{
		this.mods.addAll(Arrays.asList(mods));
	}
	
	public void setReturn(String ret)
	{
		this.ret = ret;
	}
	
	protected void setName(String name)
	{
		this.name = name;
	}
	
	public void addParameters(String... par)  // Unsafe as public for constructors
	{
		this.pars.addAll(Arrays.asList(par));
	}
	
	public void addBodyLine(String ln)
	{
		this.body.add(ln);
	}
	
	public String generate()
	{
		String meth = "";
		meth += "\t";
		if (this.mods.size() > 0)
		{
			meth += this.mods.stream().collect(Collectors.joining(" "));
			meth += " ";
		}
		meth += ((this.ret != null) ? this.ret : "void");
		meth += " " + name + "(";
		meth += this.pars.stream().collect(Collectors.joining(", "));
		meth += (") {\n");
		if (this.body.size() > 0)
		{
			meth += "\t\t";
			meth += this.body.stream().collect(Collectors.joining("\n\t\t"));
		}
		meth += "\n\t}";
		return meth;
	}
}

