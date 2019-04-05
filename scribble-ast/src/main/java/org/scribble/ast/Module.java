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

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.core.job.ScribbleException;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.kind.Kind;
import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.DataType;
import org.scribble.core.type.name.MessageSigName;
import org.scribble.core.type.name.ModuleName;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.del.ScribDel;
import org.scribble.visit.AstVisitor;

public class Module extends ScribNodeBase
{
	// ScribTreeAdaptor#create constructor
	public Module(Token payload)
	{
		super(payload);
		this.moddecl = null;
		this.imports = null;
		this.data = null;
		this.protos = null;
	}

	// Tree#dupNode constructor
	protected Module(Module node)
	{
		super(node);
		this.moddecl = null;
		this.imports = null;
		this.data = null;
		this.protos = null;
	}

	public ModuleName getFullModuleName()
	{
		//return this.moddecl.getFullModuleName();
		return getModuleDeclChild().getFullModuleName();
	}

	// Cf. CommonTree#dupNode
	@Override
	public Module dupNode()
	{
		return new Module(this);
	}
	
	// Set args as children on a dup of this -- children *not* cloned
	protected Module reconstruct(ModuleDecl moddecl, List<ImportDecl<?>> imports,
			List<NonProtocolDecl<?>> data, List<ProtocolDecl<?>> protos)
	{
		//Module m = new Module(this.source, moddecl, imports, data, protos);
		Module m = dupNode();
		ScribDel del = del();
		List<ScribNode> children = new LinkedList<>();
		children.add(moddecl);
		children.addAll(imports);
		children.addAll(data);
		children.addAll(protos);
		m.setChildren(children);
		m.setDel(del);  // No copy
		return m;
	}
	
	@Override
	public Module visitChildren(AstVisitor nv) throws ScribbleException
	{
		ModuleDecl moddecl = (ModuleDecl) visitChild(getModuleDeclChild(), nv);
		// class equality check probably too restrictive -- FIXME: remove class checks
		List<ImportDecl<?>> imports = ScribNodeBase
				.visitChildListWithClassEqualityCheck(this, getImportDeclChildren(), nv);
		List<NonProtocolDecl<?>> data = ScribNodeBase
				.visitChildListWithClassEqualityCheck(this, getNonProtoDeclChildren(), nv);
		List<ProtocolDecl<?>> protos = ScribNodeBase
				.visitChildListWithClassEqualityCheck(this, getProtoDeclChildren(), nv);
		return reconstruct(moddecl, imports, data, protos);
	}
	
	public ModuleDecl getModuleDeclChild()
	{
		List<ModuleDecl> tmp = getChildren().stream()
				.filter(x -> x instanceof ModuleDecl).map(x -> (ModuleDecl) x)
				.collect(Collectors.toList());
		if (tmp.size() != 1)
		{
			throw new RuntimeException("Shouldn't get in here: " + tmp);
		}
		return tmp.get(0);
	}
	
	public List<ImportDecl<?>> getImportDeclChildren()
	{
		return getChildren().stream().filter(x -> x instanceof ImportDecl<?>)
				.map(x -> (ImportDecl<?>) x).collect(Collectors.toList());
	}

	public List<NonProtocolDecl<?>> getNonProtoDeclChildren()
	{
		return getChildren().stream().filter(x -> x instanceof NonProtocolDecl<?>)
				.map(x -> (NonProtocolDecl<?>) x).collect(Collectors.toList());
	}

	// Arg is alias simple name
	public DataTypeDecl getDataTypeDeclChild(DataType simpname)  // Simple name (as for getProtocolDecl)
	{
		List<NonProtocolDecl<?>> filt = getNonProtoDeclChildren().stream()
				.filter(x -> ((NonProtocolDecl<?>) x).isDataTypeDecl()
						&& x.getDeclName().equals(simpname))
				.collect(Collectors.toList());
		if (filt.size() != 1)
		{
			throw new RuntimeException(
					"Data type decl not found (or duplicate name): " + simpname);
		}
		return (DataTypeDecl) filt.get(0);
	}

	// Arg is alias simple name
	public MessageSigNameDecl getMessageSigDeclChild(MessageSigName simpname)
	{
		List<NonProtocolDecl<?>> filt = getNonProtoDeclChildren().stream()
				.filter(x -> ((NonProtocolDecl<?>) x).isMessageSigNameDecl()
						&& x.getDeclName().equals(simpname))
				.collect(Collectors.toList());
		if (filt.size() != 1)
		{
			throw new RuntimeException(
					"Message sig name not found (or duplicate name): " + simpname);
		}
		return (MessageSigNameDecl) filt.get(0);
	}
	
	public List<ProtocolDecl<?>> getProtoDeclChildren()
	{
		//return Collections.unmodifiableList(this.protos);
		return getChildren().stream().filter(x -> x instanceof ProtocolDecl<?>)
				.map(x -> (ProtocolDecl<?>) x).collect(Collectors.toList());
	}
	
	public <K extends ProtocolKind> boolean hasProtocolDecl(
			ProtocolName<K> simpname)
	{
		List<? extends ProtocolDecl<?>> pds = 
				simpname.getKind().equals(Global.KIND) 
						? getGProtoDeclChildren()
						: getLProtoDeclChildren();
		return pds.stream().anyMatch(x -> x.getHeaderChild().getDeclName().equals(simpname));
	}
	
	// pn is simple name
	// Pre: hasProtocolDecl(simpname)
  // CHECKME: separate global/local?
	public <K extends ProtocolKind> ProtocolDecl<K> getProtocolDeclChild(
			ProtocolName<K> simpname)
	{
		List<? extends ProtocolDecl<?>> pds = 
				simpname.getKind().equals(Global.KIND) 
						? getGProtoDeclChildren()
						: getLProtoDeclChildren();
		List<? extends ProtocolDecl<?>> filt = pds.stream()
				.filter(x -> x.getHeaderChild().getDeclName().equals(simpname))
				.collect(Collectors.toList());
		if (filt.size() != 1)
		{
			throw new RuntimeException("Protocol not found (or duplicate name): " + simpname);
		}
		@SuppressWarnings("unchecked")
		ProtocolDecl<K> res = (ProtocolDecl<K>) filt.get(0);
		return res;
	}
	
	public List<GProtocolDecl> getGProtoDeclChildren()
	{
		return getChildren().stream().filter(IS_GPROTOCOLDECL).map(TO_GPROTOCOLDECL)
				.collect(Collectors.toList());
	}

	public List<LProtocolDecl> getLProtoDeclChildren()
	{
		return getChildren().stream().filter(IS_LPROTOCOLDECL).map(TO_LPROTOCOLDECL)
				.collect(Collectors.toList());
	}

	private static final Predicate<ScribNode> IS_GPROTOCOLDECL =
			pd -> (pd instanceof ProtocolDecl<?>) && ((ProtocolDecl<?>) pd).isGlobal();

	private static final Predicate<ScribNode> IS_LPROTOCOLDECL =
			pd -> (pd instanceof ProtocolDecl<?>) && ((ProtocolDecl<?>) pd).isLocal();

	private static final Function <ScribNode, GProtocolDecl> TO_GPROTOCOLDECL = 
			pd -> (GProtocolDecl) pd;

	private static final Function <ScribNode, LProtocolDecl> TO_LPROTOCOLDECL = 
			pd -> (LProtocolDecl) pd;

	@Override
	public String toString()
	{
		String s = getModuleDeclChild().toString();
		for (ImportDecl<? extends Kind> id : getImportDeclChildren())
		{
			s += "\n" + id;
		}
		for (NonProtocolDecl<? extends Kind> dtd : getNonProtoDeclChildren())
		{
			s += "\n" + dtd;
		}
		for (ProtocolDecl<? extends ProtocolKind> pd : getProtoDeclChildren())
		{
			s += "\n" + pd;
		}
		return s;
	}
			
			
			
			
			
			
			
			
			
			
			
			
	// Deprecate
			
	private final ModuleDecl moddecl;

	// Using (implicitly bounded) nested wildcards for mixed element lists (better practice to use separate lists?)
	private final List<ImportDecl<?>> imports;
	private final List<NonProtocolDecl<?>> data;
	private final List<ProtocolDecl<?>> protos;

	public Module(CommonTree source, ModuleDecl moddecl,
			List<ImportDecl<?>> imports, List<NonProtocolDecl<?>> data,
			List<ProtocolDecl<?>> protos)
	{
		super(source);
		this.moddecl = moddecl;
		this.imports = new LinkedList<>(imports);
		this.data = new LinkedList<>(data);
		this.protos = new LinkedList<>(protos);
	}

	/*@Override
	protected Module copy()
	{
		//return new Module(this.source, this.moddecl, this.imports, this.data, this.protos);
		/*ModuleDecl md = getModuleDecl();
		List<ImportDecl<?>> ids = getImportDecls();
		List<NonProtocolDecl<?>> npds = getNonProtocolDecls();
		List<ProtocolDecl<?>> pds = getProtocolDecls();* /
		//return new Module(this.source, md, ids, npds, pds);
		Module copy = dupNode();
		copy.setChildren(getChildren());
		return copy;
	}
	
	@Override
	public Module clone(AstFactory af)
	{
		ModuleDecl moddecl = (ModuleDecl) this.moddecl.clone(af);
		List<ImportDecl<?>> imports = ScribUtil.cloneList(af, this.imports);
		List<NonProtocolDecl<?>> data = ScribUtil.cloneList(af, this.data);
		List<ProtocolDecl<?>> protos = ScribUtil.cloneList(af, this.protos);
		return af.Module(this.source, moddecl, imports, data, protos);
		throw new RuntimeException("Deprecated");
	}*/
}
