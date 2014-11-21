package org.scribble2.model;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.global.GlobalProtocolDecl;
import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.sesstype.name.ModuleName;
import org.scribble2.sesstype.name.Name;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.util.ScribbleException;


public class Module extends ModelNodeBase
{
	public final ModuleDecl moddecl;
	public final List<? extends ImportDecl> imports;
	public final List<DataTypeDecl> data;
	public final List<? extends ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>>
			protos;
	
	public Module( 
			ModuleDecl moddecl,
			List<? extends ImportDecl> imports,
			List<DataTypeDecl> data,
			List<? extends ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>> protos)
	{
		this.moddecl = moddecl;
		this.imports = new LinkedList<>(imports);
		this.data = new LinkedList<>(data);
		this.protos = new LinkedList<>(protos);
	}

	@Override
	public String toString()
	{
		String s = moddecl.toString();
		for (ImportDecl id : this.imports)
		{
			s += "\n" + id;
		}
		for (DataTypeDecl dtd : this.data)
		{
			s += "\n" + dtd;
		}
		for (ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd : this.protos)
		{
			s += "\n" + pd;
		}
		return s;
	}

	@Override
	protected Module copy()
	{
		return new Module(this.moddecl, this.imports, this.data, this.protos);
	}

	public ModuleName getFullModuleName()
	{
		return this.moddecl.fullmodname.toName();
	}

	// FIXME: refactor
	// ptn simple alias name
	public PayloadTypeDecl getPayloadTypeDecl(Name ptn)
	{
		for (DataTypeDecl dtd : this.data)
		{
			if (dtd instanceof PayloadTypeDecl && dtd.alias.toName().equals(ptn))
			{
				return (PayloadTypeDecl) dtd;
			}
		}
		throw new RuntimeException("Payload type not found: " + ptn);
	}

	// ptn simple alias name
	public MessageSignatureDecl getMessageSignatureDecl(Name msn)
	{
		for (DataTypeDecl dtd : this.data)
		{
			if (dtd instanceof MessageSignatureDecl && dtd.alias.toName().equals(msn))
			{
				return (MessageSignatureDecl) dtd;
			}
		}
		throw new RuntimeException("Message signature not found: " + msn);
	}
	
	protected Module reconstruct(
			ModuleDecl moddecl,
			List<? extends ImportDecl> imports,
			List<DataTypeDecl> data,
			List<? extends ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>> protos)
	{
		ModelDelegate del = del();
		Module m = new Module(moddecl, imports, data, protos);
		m = (Module) m.del(del);
		return m;
	}
	
	@Override
	public Module visitChildren(ModelVisitor nv) throws ScribbleException
	{
		ModuleDecl moddecl = (ModuleDecl) visitChild(this.moddecl, nv);
		List<? extends ImportDecl> imports = visitChildListWithClassCheck(this, this.imports, nv);
		List<DataTypeDecl> data = visitChildListWithClassCheck(this, this.data, nv);
		List<? extends ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>>
				protos = visitChildListWithClassCheck(this, this.protos, nv);
		//return ModelFactoryImpl.FACTORY.Module(moddecl, imports, data, protos);//, getContext(), getEnv());
		//return new Module(moddecl, imports, data, protos);//, getContext(), getEnv());
		return reconstruct(moddecl, imports, data, protos);
	}
	
	/*public
			<T extends ProtocolDecl<? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>>
					List<T> getProtocolDecls
							(Function<ProtocolDecl<? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>, T> filter)
	{
		List<T> pds = new LinkedList<>();
		for (ProtocolDecl<? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd : this.protos)
		{
			T t = filter.apply(pd);
			if (t != null)
			{
				pds.add(t);
			}
		}
		return pds;
	}*/
	
	public List<GlobalProtocolDecl> getGlobalProtocolDecls()
	{
		/*List<GlobalProtocolDecl> gpds =
				this.<GlobalProtocolDecl>getProtocolDecls(
					(ProtocolDecl<? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
						-> pd.isGlobal() ? (GlobalProtocolDecl) pd : null);
		return gpds;*/
		//return Util.listCast(this.protos.stream().filter(GlobalProtocolDecl.isGlobalProtocolDecl).collect(Collectors.toList()), GlobalProtocolDecl.toGlobalProtocolDecl);
		//return this.protos.stream().filter(GlobalProtocolDecl.isGlobalProtocolDecl).map(GlobalProtocolDecl.toGlobalProtocolDecl).collect(Collectors.toList());
		return getProtocolDecls(IS_GLOBALPROTOCOLDECL, TO_GLOBALPROTOCOLDECL);
	}

	/*public List<LocalProtocolDecl> getLocalProtocolDecls()
	{
		//return Util.listCast(this.protos.stream().filter(LocalProtocolDecl.isLocalProtocolDecl).collect(Collectors.toList()), LocalProtocolDecl.toLocalProtocolDecl);
		//return this.protos.stream().filter(LocalProtocolDecl.isLocalProtocolDecl).map(LocalProtocolDecl.toLocalProtocolDecl).collect(Collectors.toList());
		return getProtocolDecls(LocalProtocolDecl.isLocalProtocolDecl, LocalProtocolDecl.toLocalProtocolDecl);
	}*/

	public <T extends ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>>
			List<T> getProtocolDecls(
					Predicate<ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>> filter,
					Function<ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>, T> cast)
	{
		return this.protos.stream().filter(filter).map(cast).collect(Collectors.toList());
	}

	/*public GlobalProtocolDecl getGlobalProtocolDecl(ProtocolName simplename)
	{
		return getProtocolDecl(getGlobalProtocolDecls(), simplename);
	}

	public LocalProtocolDecl getLocalProtocolDecl(ProtocolName simplename)
	{
		return getProtocolDecl(getLocalProtocolDecls(), simplename);
	}*/

	public ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>
			getProtocolDecl(ProtocolName pn)
	{
		return getProtocolDecl(this.protos, pn);
	}

	// pn is simple name
	private static <T extends ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>> T
			getProtocolDecl(List<T> pds, ProtocolName pn)
	{
		List<T> filtered = pds.stream().filter((pd) -> pd.header.name.toName().equals(pn)).collect(Collectors.toList());
		if (filtered.size() != 1)
		{
			throw new RuntimeException("Protocol not found: " + pn);
		}
		return filtered.get(0);
	}

	/*public List<LocalProtocolDecl> getLocalProtocolDecls()
	{
		List<LocalProtocolDecl> lpds = new LinkedList<>();
		for (ProtocolDecl pd : this.pds)
		{
			if (pd.isLocal())
			{
				lpds.add((LocalProtocolDecl) pd);
			}
		}
		return lpds;
	}

	public LocalProtocolDecl getLocalProtocolDecl(ProtocolName pn) throws ScribbleException
	{
		for (ProtocolDecl pd : this.pds)
		{
			if (pd.isLocal())
			{
				LocalProtocolDecl lpd = (LocalProtocolDecl) pd;
				if (lpd.name.toName().equals(pn))
				{
					return lpd;
				}
			}
		}
		throw new ScribbleException("Protocol not found: " + pn);
	}*/

	/*@Override
	public Node buildJobEnv(JobEnvBuilder nv)
	{
		JobEnv job = nv.job.addModule(this);
		nv.job = job;
		return this;
	}*/
	
	/*@Override
	public ModuleContext getContext()
	{
		return (ModuleContext) super.getContext();
	}*/

	private static final Predicate<ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>>
			IS_GLOBALPROTOCOLDECL =
					(ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
							-> pd.isGlobal();

	private static final Function<ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>, GlobalProtocolDecl>
			TO_GLOBALPROTOCOLDECL =
					(ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
							-> (GlobalProtocolDecl) pd;
}
