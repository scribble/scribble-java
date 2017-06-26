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
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

// An abstract type declaration builder
public abstract class TypeBuilder extends JavaBuilder
{
	protected String packname;  // null for non- top-level type declaration
	protected final LinkedHashSet<String> imports = new LinkedHashSet<>();

	protected final LinkedHashSet<String> mods = new LinkedHashSet<>();
	protected final LinkedHashSet<String> ifaces = new LinkedHashSet<>();
	protected final List<String> params = new LinkedList<>();
	
	protected final List<FieldBuilder> fields = new LinkedList<>();
	protected final List<MethodBuilder> methods = new LinkedList<>();

	protected final List<TypeBuilder> topLevelTypes = new LinkedList<>();
	protected final List<TypeBuilder> memberTypes = new LinkedList<>();

	// No name par because so far useful to start constructing before setting the name
	public TypeBuilder()
	{
		
	}

	public TypeBuilder(String name)
	{
		super(name);
	}
	
	public void setPackage(String packname)
	{
		setterCheck(this.packname);
		this.packname = packname;
	}
	
	public void addImports(String... imports)
	{
		this.imports.addAll(Arrays.asList(imports));
	}
	
	public void addModifiers(String... mods)
	{
		this.mods.addAll(Arrays.asList(mods));
	}
	
	public void addInterfaces(String... ifaces)
	{
		this.ifaces.addAll(Arrays.asList(ifaces));
	}
	
	public List<String> getInterfaces()
	{
		return new LinkedList<>(this.ifaces);
	}
	
	public void addParameters(String... params)
	{
		this.params.addAll(Arrays.asList(params));
	}
	
	public List<String> getParameters()
	{
		return this.params;
	}
	
	public FieldBuilder newField(String name)
	{
		FieldBuilder fb = new FieldBuilder();
		fb.setName(name);
		this.fields.add(fb);
		return fb;
	}
	
	public AbstractMethodBuilder newAbstractMethod()
	{
		AbstractMethodBuilder mb = new AbstractMethodBuilder();
		this.methods.add(mb);
		return mb;
	}

	public final AbstractMethodBuilder newAbstractMethod(String name)
	{
		AbstractMethodBuilder mb = newAbstractMethod();
		mb.setName(name);
		return mb;
	}
	
	// HACK: up to generic erasure
	public final void checkDuplicateMethods(MethodBuilder mb)
	{
		List<MethodBuilder> toRemove = new LinkedList<>();
		X: for (MethodBuilder tmp : this.methods)
		{
			if (!tmp.equals(mb) && tmp.getReturn().equals(mb.getReturn()))
			{
				if (tmp.getParameters().size() == 0)
				{
					if (mb.getParameters().size() != 0)
					{
						continue X;
					}
				}
				else
				{
					Iterator<String> mbparams = mb.getParameters().iterator();
					for (String tmpparam : tmp.getParameters())
					{
						if (!mbparams.hasNext())
						{
							continue X;
						}
						String mbparam = mbparams.next();
					
						if (mbparam.contains("<"))
						{
							mbparam = mbparam.substring(0, mbparam.indexOf("<"));
						}
						else
						{
							mbparam = mbparam.substring(0, mbparam.indexOf(" "));
						}
						if (tmpparam.contains("<"))
						{
							tmpparam = tmpparam.substring(0, tmpparam.indexOf("<"));
						}
						else
						{
							tmpparam = tmpparam.substring(0, tmpparam.indexOf(" "));
						}
						if (!tmpparam.equals(mbparam))
						{
							continue X;
						}
					}
				}
				toRemove.add(mb);
			}
		}
		this.methods.removeAll(toRemove);
	}
	
	public EnumBuilder newMemberEnum(String name)
	{
		EnumBuilder eb = new EnumBuilder();
		eb.setName(name);
		this.memberTypes.add(eb);
		return eb;
	}
	
	public ClassBuilder newTopLevelClass()
	{
		ClassBuilder cb = new ClassBuilder();
		this.topLevelTypes.add(cb);
		return cb;
	}
	
	// No validation here: javac is for that
	@Override
	public String build()
	{
		String clazz = "";
		clazz = buildHeader(clazz);
		clazz = buildSignature(clazz);
		clazz = buildBody(clazz);
		clazz = buildTopLevelTypes(clazz);
		return clazz;
	}
	
	protected String buildBody(String clazz)
	{
		clazz += " {";
		clazz = buildFields(clazz);
		clazz = buildMethods(clazz);
		clazz = buildMemberTypes(clazz);
		clazz += "\n}";
		return clazz;
	}

	protected String buildHeader(String clazz)
	{
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
		return clazz;
	}
	
	protected abstract String buildSignature(String clazz);
	
	protected String buildFields(String clazz)
	{
		if (!this.fields.isEmpty())
		{
			clazz += "\n";
			clazz += this.fields.stream().map((fb) -> fb.build()).collect(Collectors.joining("\n"));
		}
		return clazz;
	}

	protected String buildMethods(String clazz)
	{
		if (!this.methods.isEmpty())
		{
			clazz += "\n\n";
			clazz += this.methods.stream().map((mb) -> mb.build()).collect(Collectors.joining("\n\n"));
		}
		return clazz;
	}

	protected String buildMemberTypes(String clazz)
	{
		if (!this.memberTypes.isEmpty())
		{
			clazz += "\n\n";
			clazz += this.memberTypes.stream().map((cb) -> cb.build()).collect(Collectors.joining("\n\n"));
		}
		return clazz;
	}

	protected String buildTopLevelTypes(String clazz)
	{
		if (!this.topLevelTypes.isEmpty())
		{
			clazz += "\n\n";
			clazz += this.topLevelTypes.stream().map((cb) -> cb.build()).collect(Collectors.joining("\n\n"));
		}
		return clazz;
	}
}
