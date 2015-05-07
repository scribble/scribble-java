package org.scribble2.model;

import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.sesstype.name.ModuleName;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.util.ScribbleException;


//public class ProtocolDecl extends AbstractNode
public abstract class AbstractProtocolDecl<
				T1 extends ProtocolHeader,
				T2 extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>
		>
		//extends ModelNodeBase// implements ContextStackNode//, ModuleMember
		extends ProtocolDecl
{
	//public final ProtocolDefinition def;
	public final T1 header;
	public final T2 def;

	//public ProtocolDecl(CommonTree ct, SimpleProtocolNameNode name, RoleDeclList rdl, ParameterDeclList pdl, ProtocolDefinition def)
	/*protected ProtocolDecl(CommonTree ct, SimpleProtocolNameNode name, RoleDeclList roledecls, ParameterDeclList paramdecls, T def)
	{
		this(ct, name, roledecls, paramdecls, def, null);
	}

	protected ProtocolDecl(CommonTree ct, SimpleProtocolNameNode name, RoleDeclList roledecls, ParameterDeclList paramdecls, T def, ProtocolDeclContext pdcontext)
	{
		this(ct, name, roledecls, paramdecls, def, pdcontext, null);
	}*/

	//protected ProtocolDecl(Token t, SimpleProtocolNameNode name, RoleDeclList roledecls, ParameterDeclList paramdecls, T def)//, ProtocolDeclContext pdcontext, Env env)
	protected AbstractProtocolDecl(T1 header, T2 def)//, ProtocolDeclContext pdcontext, Env env)
	{
		this.header = header;
		this.def = def;
	}
	
	// Keeps the current del (shallow reconstruct with new children)
	protected abstract AbstractProtocolDecl<T1, T2> reconstruct(T1 header, T2 def);//, ProtocolDeclContext pdcontext, Env env);

	/*@Override
	public NameDisambiguator enterDisambiguation(NameDisambiguator disamb) throws ScribbleException
	{
		disamb.enterProtocolDecl(this);
		return disamb;
	}
	
	/*public ProtocolSignature getProtocolSignature(ProtocolName fmn)// throws ScribbleException
	{
		//ProtocolName fmn = getFullProtocolName(env);  // simple name may not be directly visible
		List<Role> roles = new LinkedList<>();
		for (RoleDecl rd : this.rdl.rds)
		{
			roles.add(rd.name.toName());
		}
		List<Parameter> params = new LinkedList<>();
		for (ParameterDecl pd : this.pdl.pds)
		{
			params.add(pd.name.toName());
		}
		return new ProtocolSignature(fmn, roles, params);
	}*/
	
	//public abstract ProtocolName getFullProtocolName(Env ev) throws ScribbleException;

	@Override
	public AbstractProtocolDecl<T1, T2> visitChildren(ModelVisitor nv) throws ScribbleException
	{
		T1 header = visitChildWithClassCheck(this, this.header, nv);
		T2 def = visitChildWithClassCheck(this, this.def, nv);
		return reconstruct(header, def);//, getContext(), getEnv());
	}
	
	/*@Override
	public ProtocolDecl<T1, T2> visitChildrenInSubprotocols(SubprotocolVisitor spv) throws ScribbleException
	{
		//ProtocolName fullname = getFullProtocolName();
		spv.enterRootProtocolDecl(this);  // Doesn't push proto stack, just for root role/arg names
		//return (ProtocolDecl<T>) super.visitChildrenInSubprotocols(spv);
		return visitChildren(spv);
	}*/

	// Module for ModuleContext creation and local projections (no context built yet)
	//public ProtocolName getFullProtocolName(ModuleContext mcontext)
	@Override
	public ProtocolName getFullProtocolName(Module mod)
	{
		//ModuleName fullmodname = AntlrModule.getFullModuleName(AntlrGlobalProtocolDecl.getModuleParent(this.ct));  // FIXME: globalprotocoldecl same as local hack
		ModuleName fullmodname = mod.getFullModuleName();
		return new ProtocolName(fullmodname, this.header.name.toString());
	}
	
	/*public boolean isGlobal()
	{
		return false;
	}
		
	public boolean isLocal()
	{
		return false;
	}*/
	
	/* //public ProtocolBlock getBody()
	/*public ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>> getBody()
	{
		return this.def.block;
	}*/
	
	/*@Override
	public ProtocolDeclContext getContext()
	{
		return (ProtocolDeclContext) super.getContext();
	}*/

	@Override
	public String toString()
	{
		return this.header + " " + this.def;
	}
}
