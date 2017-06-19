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
package org.scribble.ast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.DataType;
import org.scribble.sesstype.name.MessageSigName;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.util.ScribUtil;
import org.scribble.visit.AstVisitor;

public class Module extends ScribNodeBase
{
	public final ModuleDecl moddecl;

	// Using (implicitly bounded) nested wildcards for mixed element lists (better practice to use separate lists?)
	private final List<ImportDecl<?>> imports;
	private final List<NonProtocolDecl<?>> data;
	private final List<ProtocolDecl<?>> protos;
	
	public Module(CommonTree source, ModuleDecl moddecl, List<ImportDecl<?>> imports,
			List<NonProtocolDecl<?>> data, List<ProtocolDecl<?>> protos)
	{
		super(source);
		this.moddecl = moddecl;
		this.imports = new LinkedList<>(imports);
		this.data = new LinkedList<>(data);
		this.protos = new LinkedList<>(protos);
	}

	@Override
	protected Module copy()
	{
		return new Module(this.source, this.moddecl, this.imports, this.data, this.protos);
	}
	
	@Override
	public Module clone()
	{
		ModuleDecl moddecl = (ModuleDecl) this.moddecl.clone();
		List<ImportDecl<?>> imports = ScribUtil.cloneList(this.imports);
		List<NonProtocolDecl<?>> data = ScribUtil.cloneList(this.data);
		List<ProtocolDecl<?>> protos = ScribUtil.cloneList(this.protos);
		return AstFactoryImpl.FACTORY.Module(this.source, moddecl, imports, data, protos);
	}
	
	public Module reconstruct(ModuleDecl moddecl, List<ImportDecl<?>> imports, List<NonProtocolDecl<?>> data, List<ProtocolDecl<?>> protos)
	{
		ScribDel del = del();
		Module m = new Module(this.source, moddecl, imports, data, protos);
		m = (Module) m.del(del);
		return m;
	}
	
	@Override
	public Module visitChildren(AstVisitor nv) throws ScribbleException
	{
		ModuleDecl moddecl = (ModuleDecl) visitChild(this.moddecl, nv);
		// class equality check probably too restrictive
		List<ImportDecl<?>> imports = ScribNodeBase.visitChildListWithClassEqualityCheck(this, this.imports, nv);
		List<NonProtocolDecl<?>> data = ScribNodeBase.visitChildListWithClassEqualityCheck(this, this.data, nv);
		List<ProtocolDecl<?>> protos = ScribNodeBase.visitChildListWithClassEqualityCheck(this, this.protos, nv);
		return reconstruct(moddecl, imports, data, protos);
	}

	public ModuleName getFullModuleName()
	{
		return this.moddecl.getFullModuleName();
	}

	@Override
	public String toString()
	{
		String s = moddecl.toString();
		for (ImportDecl<? extends Kind> id : this.imports)
		{
			s += "\n" + id;
		}
		for (NonProtocolDecl<? extends Kind> dtd : this.data)
		{
			s += "\n" + dtd;
		}
		for (ProtocolDecl<? extends ProtocolKind> pd : this.protos)
		{
			s += "\n" + pd;
		}
		return s;
	}

	// ptn simple alias name
	public DataTypeDecl getDataTypeDecl(DataType simpname)  // Simple name (as for getProtocolDecl)
	{
		for (NonProtocolDecl<? extends Kind> dtd : this.data)
		{
			if (dtd.isDataTypeDecl() && dtd.getDeclName().equals(simpname))
			{
				return (DataTypeDecl) dtd;
			}
		}
		throw new RuntimeException("Data type not found: " + simpname);
	}

	// msn simple alias name
	public MessageSigNameDecl getMessageSigDecl(MessageSigName simpname)
	{
		for (NonProtocolDecl<?> dtd : this.data)
		{
			if (dtd instanceof MessageSigNameDecl && dtd.getDeclName().equals(simpname))
			{
				return (MessageSigNameDecl) dtd;
			}
		}
		throw new RuntimeException("Message signature not found: " + simpname);
	}
	
	public List<ImportDecl<?>> getImportDecls()
	{
		return Collections.unmodifiableList(this.imports);
	}

	public List<NonProtocolDecl<?>> getNonProtocolDecls()
	{
		return Collections.unmodifiableList(this.data);
	}
	
	public List<ProtocolDecl<?>> getProtocolDecls()
	{
		return Collections.unmodifiableList(this.protos);
	}
	
	public List<GProtocolDecl> getGlobalProtocolDecls()
	{
		return getProtocolDecls(IS_GLOBALPROTOCOLDECL, TO_GLOBALPROTOCOLDECL);
	}

	public List<LProtocolDecl> getLocalProtocolDecls()
	{
		return getProtocolDecls(IS_LOCALPROTOCOLDECL, TO_LOCALPROTOCOLDECL);
	}

	private <T extends ProtocolDecl<?>>
		List<T> getProtocolDecls(Predicate<ProtocolDecl<?>> filter, Function<ProtocolDecl<?>, T> cast)
	{
		return this.protos.stream().filter(filter).map(cast).collect(Collectors.toList());
	}
	
	public <K extends ProtocolKind> boolean hasProtocolDecl(ProtocolName<K> simpname)
	{
		return hasProtocolDecl(this.protos, simpname);
	}
	
	// pn is simple name
  // separate into global/local?
	public <K extends ProtocolKind> ProtocolDecl<K> getProtocolDecl(ProtocolName<K> simpname)
	{
		return getProtocolDecl(this.protos, simpname);
	}

	private static <K extends ProtocolKind>
			boolean hasProtocolDecl(List<ProtocolDecl<?>> pds, ProtocolName<K> simpname)
	{
		return pds.stream()
				.filter((pd) -> pd.header.getDeclName().equals(simpname)
						&& (simpname.getKind().equals(Global.KIND)) ? pd.isGlobal() : pd.isLocal())
				.count() > 0;
	}

	// pn is simple name
	private static <K extends ProtocolKind>
			ProtocolDecl<K> getProtocolDecl(List<ProtocolDecl<?>> pds, ProtocolName<K> simpname)
	{
		List<ProtocolDecl<?>> filtered = pds.stream()
				.filter((pd) -> pd.header.getDeclName().equals(simpname)
						&& (simpname.getKind().equals(Global.KIND)) ? pd.isGlobal() : pd.isLocal())
				.collect(Collectors.toList());
		if (filtered.size() == 0)
		{
			throw new RuntimeException("Protocol not found: " + simpname);
		}
		/*if (filtered.size() > 1)
		{
			throw new RuntimeException("Found duplicate protocol decls: " + simpname);  // Just return first -- allows Do/DoArgListDel name disambiguation to go through, and later caught on leaving Module
		}*/
		@SuppressWarnings("unchecked")
		ProtocolDecl<K> res = (ProtocolDecl<K>) filtered.get(0);
		return res;
	}

	private static final Predicate<ProtocolDecl<?>> IS_GLOBALPROTOCOLDECL = (pd) -> pd.isGlobal();

	private static final Predicate<ProtocolDecl<?>> IS_LOCALPROTOCOLDECL = (pd) -> pd.isLocal();

	private static final Function <ProtocolDecl<?>, GProtocolDecl> TO_GLOBALPROTOCOLDECL = (pd) -> (GProtocolDecl) pd;

	private static final Function <ProtocolDecl<?>, LProtocolDecl> TO_LOCALPROTOCOLDECL = (pd) -> (LProtocolDecl) pd;
}
