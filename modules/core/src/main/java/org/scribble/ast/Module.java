package org.scribble.ast;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.kind.ImportKind;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.NonProtocolKind;
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
	public final List<ImportDecl<? extends ImportKind>> imports;
	public final List<NonProtocolDecl<? extends NonProtocolKind>> data;
	public final List<ProtocolDecl<? extends ProtocolKind>> protos;
	
	public Module( 
			ModuleDecl moddecl,
			List<ImportDecl<? extends ImportKind>> imports,
			List<NonProtocolDecl<? extends NonProtocolKind>> data,
			List<ProtocolDecl<? extends ProtocolKind>> protos)
	{
		this.moddecl = moddecl;
		this.imports = new LinkedList<>(imports);
		this.data = new LinkedList<>(data);
		this.protos = new LinkedList<>(protos);
	}

	@Override
	protected Module copy()
	{
		return new Module(this.moddecl, this.imports, this.data, this.protos);
	}
	
	@Override
	public Module clone()
	{
		ModuleDecl moddecl = (ModuleDecl) this.moddecl.clone();
		List<ImportDecl<?>> imports = ScribUtil.cloneList(this.imports);
		List<NonProtocolDecl<?>> data = ScribUtil.cloneList(this.data);
		List<ProtocolDecl<?>> protos = ScribUtil.cloneList(this.protos);
		return AstFactoryImpl.FACTORY.Module(moddecl, imports, data, protos);
	}
	
	protected Module reconstruct(
			ModuleDecl moddecl,
			List<ImportDecl<? extends ImportKind>> imports,
			List<NonProtocolDecl<? extends NonProtocolKind>> data,
			List<ProtocolDecl<? extends ProtocolKind>> protos)
	{
		ScribDel del = del();
		Module m = new Module(moddecl, imports, data, protos);
		m = (Module) m.del(del);
		return m;
	}
	
	@Override
	public Module visitChildren(AstVisitor nv) throws ScribbleException
	{
		ModuleDecl moddecl = (ModuleDecl) visitChild(this.moddecl, nv);
		List<ImportDecl<? extends ImportKind>> imports = visitChildListWithStrictClassCheck(this, this.imports, nv);
		List<NonProtocolDecl<? extends NonProtocolKind>> data = visitChildListWithStrictClassCheck(this, this.data, nv);
		List<ProtocolDecl<? extends ProtocolKind>> protos = visitChildListWithStrictClassCheck(this, this.protos, nv);
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
	public DataTypeDecl getDataTypeDecl(DataType ptn)  // Simple name (as for getProtocolDecl)
	{
		for (NonProtocolDecl<? extends Kind> dtd : this.data)
		{
			if (dtd.isDataTypeDecl() && dtd.getDeclName().equals(ptn))
			{
				return (DataTypeDecl) dtd;
			}
		}
		throw new RuntimeException("Data type not found: " + ptn);
	}

	// msn simple alias name
	public MessageSigNameDecl getMessageSigDecl(MessageSigName msn)
	{
		for (NonProtocolDecl<? extends Kind> dtd : this.data)
		{
			if (dtd instanceof MessageSigNameDecl && dtd.getDeclName().equals(msn))
			{
				return (MessageSigNameDecl) dtd;
			}
		}
		throw new RuntimeException("Message signature not found: " + msn);
	}
	
	public List<GProtocolDecl> getGlobalProtocolDecls()
	{
		return getProtocolDecls(IS_GLOBALPROTOCOLDECL, TO_GLOBALPROTOCOLDECL);
	}

	public List<LProtocolDecl> getLocalProtocolDecls()
	{
		return getProtocolDecls(IS_LOCALPROTOCOLDECL, TO_LOCALPROTOCOLDECL);
	}

	private <T extends ProtocolDecl<? extends ProtocolKind>>
			List<T> getProtocolDecls(
					Predicate<ProtocolDecl<? extends ProtocolKind>> filter,
					Function<ProtocolDecl<? extends ProtocolKind>, T> cast)
	{
		return this.protos.stream().filter(filter).map(cast).collect(Collectors.toList());
	}
	
	// pn is simple name
  // separate into global/local?
	public <K extends ProtocolKind> ProtocolDecl<K> getProtocolDecl(ProtocolName<K> pn)
	{
		return getProtocolDecl(this.protos, pn);
	}

	// pn is simple name
	private static <K extends ProtocolKind>
			ProtocolDecl<K> getProtocolDecl(List<ProtocolDecl<? extends ProtocolKind>> pds, ProtocolName<K> pn)
	{
		List<ProtocolDecl<? extends ProtocolKind>> filtered = pds.stream()
				.filter((pd) -> pd.header.getDeclName().equals(pn))
				.filter((pd) -> (pn.kind.equals(Global.KIND)) ? pd.isGlobal() : pd.isLocal())
				.collect(Collectors.toList());
		if (filtered.size() != 1)
		{
			throw new RuntimeException("Protocol not found: " + pn);
		}
		@SuppressWarnings("unchecked")
		ProtocolDecl<K> res = (ProtocolDecl<K>) filtered.get(0);
		return res;
	}

	private static final Predicate<ProtocolDecl<? extends ProtocolKind>>
			IS_GLOBALPROTOCOLDECL = (pd) -> pd.isGlobal();

	private static final Predicate<ProtocolDecl<? extends ProtocolKind>>
			IS_LOCALPROTOCOLDECL = (pd) -> pd.isLocal();

	private static final Function <ProtocolDecl<? extends ProtocolKind>, GProtocolDecl>
			TO_GLOBALPROTOCOLDECL = (pd) -> (GProtocolDecl) pd;

	private static final Function <ProtocolDecl<? extends ProtocolKind>, LProtocolDecl>
			TO_LOCALPROTOCOLDECL = (pd) -> (LProtocolDecl) pd;
}
