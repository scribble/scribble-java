package org.scribble.codegen;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ClassBuilder
{
	public static final String FINAL = "final";
	public static final String NEW = "new";
	public static final String PRIVATE = "private";
	public static final String PROTECTED = "protected";
	public static final String PUBLIC = "public";
	public static final String RETURN = "return";
	public static final String STATIC = "static";
	public static final String SUPER = "super";
	
	private String packname;  // null for non- top-level class
	private String name;
	private String superc;  // null if none explicit
	private final List<String> ifaces = new LinkedList<String>();

	private final List<String> imports = new LinkedList<String>();
	private final List<String> mods = new LinkedList<String>();
	
	// Maybe generalise as members (e.g. for classes)
	private final List<FieldBuilder> fields = new LinkedList<>();
	private final List<MethodBuilder> ctors = new LinkedList<>();
	private final List<MethodBuilder> methods = new LinkedList<>();
	private final List<EnumBuilder> enums = new LinkedList<>();

	public ClassBuilder()
	{
		
	}
	
	public void setPackage(String packname)
	{
		this.packname = packname;
	}

	public void setSuperClass(String superc)
	{
		this.superc = superc;
	}
	
	public void addInterfaces(String... ifaces)
	{
		this.ifaces.addAll(Arrays.asList(ifaces));
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
		this.fields.add(fb);
		return fb;
	}
	
	// Each par is the String: type + " " + name -- cf. MethodBuilder
	public MethodBuilder newConstructor(String... pars)
	{
		MethodBuilder mb = new MethodBuilder();
		mb.setName(this.name);
		mb.addParameters(pars);
		this.ctors.add(mb);
		return mb;
	}
	
	public MethodBuilder newMethod(String name)
	{
		MethodBuilder mb = new MethodBuilder();
		mb.setName(name);
		this.methods.add(mb);
		return mb;
	}
	
	public EnumBuilder newEnum(String name)
	{
		EnumBuilder eb = new EnumBuilder();
		eb.setName(name);
		this.enums.add(eb);
		return eb;
	}
	
	public String generate()
	{
		String clazz = "";
		if (this.packname != null)
		{
			clazz += "package " + packname + ";";
		}
		if (!this.imports.isEmpty())
		{
			clazz += "\n\nimport ";
			clazz += this.imports.stream().collect(Collectors.joining(";\nimport "));
			clazz += ";";
		}
		if (this.packname != null || !this.imports.isEmpty())
		{
			clazz += "\n\n";
		}
		if (!this.mods.isEmpty())
		{
			clazz += this.mods.stream().collect(Collectors.joining(" "));
			clazz += " ";
		}
		clazz += "class " + this.name;
		if (this.superc != null)
		{
			clazz += " extends " + this.superc;
		}
		if (!this.ifaces.isEmpty())
		{
			clazz += " implements ";
			clazz += this.ifaces.stream().collect(Collectors.joining(", "));
		}
		clazz += " {";
		if (!this.enums.isEmpty())
		{
			clazz += "\n";
			clazz += this.enums.stream().map((fb) -> fb.generate()).collect(Collectors.joining("\n"));
		}
		if (!this.fields.isEmpty())
		{
			clazz += "\n";
			clazz += this.fields.stream().map((fb) -> fb.generate()).collect(Collectors.joining("\n"));
		}
		if (!this.ctors.isEmpty())
		{
			clazz += "\n\n";
			clazz += this.ctors.stream().map((mb) -> mb.generate()).collect(Collectors.joining("\n\n"));
		}
		if (!this.methods.isEmpty())
		{
			clazz += "\n\n";
			clazz += this.methods.stream().map((mb) -> mb.generate()).collect(Collectors.joining("\n\n"));
		}
		clazz += "\n}";
		return clazz;
	}
}

class FieldBuilder
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

class MethodBuilder
{
	private String name;

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
	
	public void addModifiers(String... mods)
	{
		this.mods.addAll(Arrays.asList(mods));
	}
	
	public void setReturn(String ret)
	{
		this.ret = ret;
	}
	
	// Each par is the String: type + " " + name
  // Unsafe as public for constructors
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

class EnumBuilder
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