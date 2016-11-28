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
