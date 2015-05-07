package org.scribble2.model;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.global.GlobalProtocolDecl;
import org.scribble2.model.local.LocalProtocolDecl;
import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.name.ModuleName;
import org.scribble2.sesstype.name.Name;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.util.ScribbleException;


public class Module extends ModelNodeBase
{
	public final ModuleDecl moddecl;
	//public final List<? extends ImportDecl> imports;
	public final List<ImportDecl> imports;
	public final List<DataTypeDecl> data;
	//public final List<? extends AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>>
	public final List<ProtocolDecl<? extends Kind>>
			protos;
	
	public Module( 
			ModuleDecl moddecl,
			//List<? extends ImportDecl> imports,
			List<ImportDecl> imports,
			List<DataTypeDecl> data,
			//List<? extends AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>> protos)
			List<ProtocolDecl<? extends Kind>> protos)
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
		//for (AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd : this.protos)
		for (ProtocolDecl<? extends Kind> pd : this.protos)
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
	
	protected Module reconstruct(
			ModuleDecl moddecl,
			//List<? extends ImportDecl> imports,
			List<ImportDecl> imports,
			List<DataTypeDecl> data,
			//List<? extends AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>> protos)
			List<ProtocolDecl<? extends Kind>> protos)
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
		//List<? extends ImportDecl> imports = visitChildListWithClassCheck(this, this.imports, nv);
		List<ImportDecl> imports = visitChildListWithClassCheck(this, this.imports, nv);
		List<DataTypeDecl> data = visitChildListWithClassCheck(this, this.data, nv);
		//List<? extends AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>>
		List<ProtocolDecl<? extends Kind>>
				protos = visitChildListWithClassCheck(this, this.protos, nv);
		//return ModelFactoryImpl.FACTORY.Module(moddecl, imports, data, protos);//, getContext(), getEnv());
		//return new Module(moddecl, imports, data, protos);//, getContext(), getEnv());
		return reconstruct(moddecl, imports, data, protos);
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

	public List<LocalProtocolDecl> getLocalProtocolDecls()
	{
		//return Util.listCast(this.protos.stream().filter(LocalProtocolDecl.isLocalProtocolDecl).collect(Collectors.toList()), LocalProtocolDecl.toLocalProtocolDecl);
		//return this.protos.stream().filter(LocalProtocolDecl.isLocalProtocolDecl).map(LocalProtocolDecl.toLocalProtocolDecl).collect(Collectors.toList());
		//return getProtocolDecls(LocalProtocolDecl.isLocalProtocolDecl, LocalProtocolDecl.toLocalProtocolDecl);
		return getProtocolDecls(IS_LOCALPROTOCOLDECL, TO_LOCALPROTOCOLDECL);
	}

	/*public <T extends AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>>
			List<T> getProtocolDecls(
					Predicate<AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>> filter,
					Function<AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>, T> cast)*/
	private <T extends ProtocolDecl<? extends Kind>>
			List<T> getProtocolDecls(
					Predicate<ProtocolDecl<? extends Kind>> filter,
					Function<ProtocolDecl<? extends Kind>, T> cast)
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

	/*public GlobalProtocolDecl getGlobalProtocolDecl(ProtocolName pn)
	{
		ProtocolDecl pd = getProtocolDecl(this.protos, pn);
		if (!pd.isGlobal())
		{
			throw new RuntimeException("Not a GlobalProtocolDecl: " + pn);
		}
		return (GlobalProtocolDecl) pd;
	}

	public LocalProtocolDecl getLocalProtocolDecl(ProtocolName pn)
	{
		ProtocolDecl pd = getProtocolDecl(this.protos, pn);
		if (!pd.isLocal())
		{
			throw new RuntimeException("Not a LocalProtocolDecl: " + pn);
		}
		return (LocalProtocolDecl) pd;
	}*/
	
	public
			//AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>
			ProtocolDecl<? extends Kind>
			getProtocolDecl(ProtocolName pn)
	{
		//.. HERE: refactor to get global/local protocol decl
		
		return getProtocolDecl(this.protos, pn);
	}

	// pn is simple name
	private static 
			//<T extends AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>>
			//<T extends ProtocolDecl>
			//T getProtocolDecl(List<T> pds, ProtocolName pn)
			//AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>
			ProtocolDecl<? extends Kind>
			getProtocolDecl(List<ProtocolDecl<? extends Kind>> pds, ProtocolName pn)
	{
		//List<T>
		List<ProtocolDecl<? extends Kind>> filtered = pds.stream().filter((pd) -> ((AbstractProtocolDecl) pd).header.name.toName().equals(pn)).collect(Collectors.toList());
		if (filtered.size() != 1)
		{
			throw new RuntimeException("Protocol not found: " + pn);
		}
		return (AbstractProtocolDecl) filtered.get(0);
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

	private static final 
			//Predicate<AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>>
			Predicate<ProtocolDecl<? extends Kind>>
			IS_GLOBALPROTOCOLDECL =
					//(AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
					(ProtocolDecl<? extends Kind> pd)
							-> pd.isGlobal();

	private static final
			//Predicate<AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>>
			Predicate<ProtocolDecl<? extends Kind>>
			IS_LOCALPROTOCOLDECL =
					//(AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
					(ProtocolDecl<? extends Kind> pd)
							-> pd.isLocal();

	private static final Function
			<
				//AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>,
				ProtocolDecl<? extends Kind>,
				GlobalProtocolDecl
			>
			TO_GLOBALPROTOCOLDECL =
					//(AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
					(ProtocolDecl<? extends Kind> pd)
							-> (GlobalProtocolDecl) pd;

	private static final Function
			<
				//AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>,
				ProtocolDecl<? extends Kind>,
				LocalProtocolDecl
			>
			TO_LOCALPROTOCOLDECL =
					//(AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
					(ProtocolDecl<? extends Kind> pd)
							-> (LocalProtocolDecl) pd;
}
