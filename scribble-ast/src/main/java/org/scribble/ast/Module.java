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
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.antlr.runtime.Token;
import org.scribble.ast.global.GProtoDecl;
import org.scribble.core.type.kind.Kind;
import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.DataType;
import org.scribble.core.type.name.GProtocolName;
import org.scribble.core.type.name.MessageSigName;
import org.scribble.core.type.name.ModuleName;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

public class Module extends ScribNodeBase
{
	public static final int MODDECL_CHILD_INDEX = 0;
	
	// ScribTreeAdaptor#create constructor
	public Module(Token payload)
	{
		super(payload);
	}

	// Tree#dupNode constructor
	protected Module(Module node)
	{
		super(node);
	}

	public ModuleDecl getModuleDeclChild()
	{
		return (ModuleDecl) getChild(MODDECL_CHILD_INDEX);
	}
	
	public List<ImportDecl<?>> getImportDeclChildren()
	{
		return getMemberChildren(x -> x instanceof ImportDecl,
				x -> (ImportDecl<?>) x);
	}

	public List<NonProtoDecl<?>> getNonProtoDeclChildren()
	{
		return getMemberChildren(x -> x instanceof NonProtoDecl,
				x -> (NonProtoDecl<?>) x);
	}
	
	public List<ProtoDecl<?>> getProtoDeclChildren()
	{
		return getMemberChildren(x -> x instanceof ProtoDecl,
				x -> (ProtoDecl<?>) x);
	}

	// Not requiring T extends ModuleMember, ImportDecl is not a ModuleMember
	private <T> List<T> getMemberChildren(
			Predicate<ScribNode> instanceOf, Function<ScribNode, T> cast)
	{
		List<T> res = new LinkedList<>();
		boolean b = false;
		// Start collecting from first instance, and stop on first non-instance or end
		for (ScribNode c : getChildren())
		{
			if (!b && instanceOf.test(c)) b = true;
			else if (b && !instanceOf.test(c)) break;
			if (b) res.add(cast.apply(c));
		}
		return res;
	}

	public ModuleName getFullModuleName()
	{
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
			List<NonProtoDecl<?>> data, List<ProtoDecl<?>> protos)
	{
		Module dup = dupNode();
		dup.addChild(moddecl);
		dup.addChildren(imports);
		dup.addChildren(data);
		dup.addChildren(protos);
		dup.setDel(del());  // No copy
		return dup;
	}
	
	@Override
	public Module visitChildren(AstVisitor nv) throws ScribException
	{
		ModuleDecl moddecl = (ModuleDecl) visitChild(getModuleDeclChild(), nv);
		// class equality check probably too restrictive -- FIXME: remove class checks
		List<ImportDecl<?>> imports = ScribNodeBase
				.visitChildListWithClassEqualityCheck(this, getImportDeclChildren(), nv);
		List<NonProtoDecl<?>> data = ScribNodeBase
				.visitChildListWithClassEqualityCheck(this, getNonProtoDeclChildren(), nv);
		List<ProtoDecl<?>> protos = ScribNodeBase
				.visitChildListWithClassEqualityCheck(this, getProtoDeclChildren(), nv);
		return reconstruct(moddecl, imports, data, protos);
	}
	
	public DataDecl getDataTypeDeclChild(DataType simpname)  // Simple name (as for getProtocolDecl)
	{
		Optional<DataDecl> res = getNonProtoDeclChildren().stream()
				.filter(x -> (x instanceof DataDecl)
						&& x.getDeclName().equals(simpname))
				.map(x -> (DataDecl) x).findFirst();  // No duplication check, rely on WF
		if (!res.isPresent())
		{
			throw new RuntimeException("Type decl not found: " + simpname);
		}
		return res.get();
	}

	// CHECKME: allow sig and type decls with same decl name in same module?
	public SigDecl getMessageSigDeclChild(MessageSigName simpname)
	{
		Optional<SigDecl> res = getNonProtoDeclChildren().stream()
				.filter(x -> (x instanceof SigDecl)
						&& x.getDeclName().equals(simpname))
				.map(x -> (SigDecl) x).findFirst();  // No duplication check, rely on WF
		if (!res.isPresent())
		{
			throw new RuntimeException("Sig decl not found: " + simpname);
		}
		return res.get();
	}
	
	public List<GProtoDecl> getGProtoDeclChildren()
	{
		/*return getChildren().stream().filter(IS_GPROTOCOLDECL).map(TO_GPROTOCOLDECL)
				.collect(Collectors.toList());*/
		return getProtoDeclChildren().stream().filter(x -> x.isGlobal())
				.map(x -> (GProtoDecl) x).collect(Collectors.toList());
						// Less efficient, but smaller code
	}
	
	// CHECKME: allow global and local protocols with same simpname in same module? -- currently, no?
	public boolean hasGProtocolDecl(GProtocolName simpname)
	{
		return getGProtoDeclChildren().stream()
				.anyMatch(x -> x.getHeaderChild().getDeclName().equals(simpname));
	}
	
	// Pre: hasProtocolDecl(simpname)
	public GProtoDecl getGProtocolDeclChild(
			GProtocolName simpname)
	{
		Optional<GProtoDecl> res = getGProtoDeclChildren().stream()
				.filter(x -> x.getHeaderChild().getDeclName().equals(simpname))
				.findFirst();  // No duplication check, rely on WF
		if (!res.isPresent())
		{
			throw new RuntimeException("Proto decl not found: " + simpname);
		}
		return res.get();
	}

	@Override
	public String toString()
	{
		String s = getModuleDeclChild().toString();
		for (ImportDecl<? extends Kind> id : getImportDeclChildren())
		{
			s += "\n" + id;
		}
		for (NonProtoDecl<? extends Kind> dtd : getNonProtoDeclChildren())
		{
			s += "\n" + dtd;
		}
		for (ProtoDecl<? extends ProtocolKind> pd : getProtoDeclChildren())
		{
			s += "\n" + pd;
		}
		return s;
	}
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*private static final Predicate<ScribNode> IS_GPROTOCOLDECL =
			x -> (x instanceof ProtocolDecl<?>) && ((ProtocolDecl<?>) x).isGlobal();

	private static final Function <ScribNode, GProtocolDecl> TO_GPROTOCOLDECL = 
			x -> (GProtocolDecl) x;*/
