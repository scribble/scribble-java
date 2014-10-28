package org.scribble2.parser.ast;

import org.antlr.runtime.Token;

//public class ProtocolDecl extends AbstractNode
public abstract class ProtocolDecl<
				T1 extends ProtocolHeader,
				T2 extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>
		>
		extends ScribbleASTBase// implements ContextStackNode//, ModuleMember
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
	protected ProtocolDecl(Token t, T1 header, T2 def)//, ProtocolDeclContext pdcontext, Env env)
	{
		super(t);//, pdcontext, env);
		this.header = header;
		this.def = def;
	}

	/*protected abstract ProtocolDecl<T> reconstruct(CommonTree ct, SimpleProtocolNameNode name, RoleDeclList roledecls, ParameterDeclList paramdecls, T def, ProtocolDeclContext pdcontext, Env env);

	@Override
	public NameDisambiguator enterDisambiguation(NameDisambiguator disamb) throws ScribbleException
	{
		disamb.enterProtocolDecl(this);
		return disamb;
	}

	@Override
	public NodeContextBuilder enterContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		builder.clearProtocolDependencies();
		ProtocolDeclContext pdcontext = new ProtocolDeclContext();  // FIXME: use replaceProtocolDeclContext in builder? (here this initial value not used, set properly on leave)
		builder.pushContext(pdcontext);
		builder.addProtocolDependency(getFullProtocolName(builder.getModuleContext().root));
		//builder.pushContext(new CompoundInteractionContext(pdcontext));  // Not needed: def block pushes a context
		return builder;
	}

	@Override
	public ProtocolDecl<T> leaveContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		/*this.setContext(builder.popContext());
		return this;*
		//CompoundInteractionContext cicontext = (CompoundInteractionContext)
		//builder.popContext();  // Associate to the def? -- not currently merged into the protocol decl context
		//return new ProtocolDecl<T>(this.ct, this.name, this.roledecls, this.paramdecls, this.def, (ProtocolDeclContext) builder.popContext());
		
		//ProtocolDeclContext pdcontext = new ProtocolDeclContext((ProtocolDeclContext) builder.popContext(), builder.getProtocolDependencies());
		ProtocolDeclContext pdcontext = (ProtocolDeclContext) builder.popContext();
		return reconstruct(this.ct, this.name, this.roledecls, this.paramdecls, this.def, pdcontext, getEnv());
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
	}*
	
	//public abstract ProtocolName getFullProtocolName(Env ev) throws ScribbleException;

	@Override
	public ProtocolDecl<T> visitChildren(NodeVisitor nv) throws ScribbleException
	{
		RoleDeclList rdl = (RoleDeclList) visitChild(this.roledecls, nv);
		ParameterDeclList pdl = (ParameterDeclList) visitChild(this.paramdecls, nv);
		T def = visitChildWithClassCheck(this, this.def, nv);
		//return new ProtocolDecl<T>(this.ct, this.name, rdl, pdl, def, getContext(), getEnv());
		return reconstruct(this.ct, this.name, rdl, pdl, def, getContext(), getEnv());
	}
	
	@Override
	public ProtocolDecl<T> visitChildrenInSubprotocols(SubprotocolVisitor spv) throws ScribbleException
	{
		//ProtocolName fullname = getFullProtocolName();
		spv.enterRootProtocolDecl(this);  // Doesn't push proto stack, just for root role/arg names
		//return (ProtocolDecl<T>) super.visitChildrenInSubprotocols(spv);
		return visitChildren(spv);
	}

	// Module for ModuleContext creation and local projections (no context built yet)
	//public ProtocolName getFullProtocolName(ModuleContext mcontext)
	public ProtocolName getFullProtocolName(Module mod)
	{
		//ModuleName fullmodname = AntlrModule.getFullModuleName(AntlrGlobalProtocolDecl.getModuleParent(this.ct));  // FIXME: globalprotocoldecl same as local hack
		ModuleName fullmodname = mod.getFullModuleName();
		return new ProtocolName(fullmodname, this.name.toString());
	}
	
	public boolean isGlobal()
	{
		return false;
	}
		
	public boolean isLocal()
	{
		return false;
	}
	
	//public ProtocolBlock getBody()
	public ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>> getBody()
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
