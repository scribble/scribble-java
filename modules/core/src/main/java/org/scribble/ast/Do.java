package org.scribble.ast;

import java.util.Iterator;

import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.name.qualified.ProtocolNameNode;
import org.scribble.ast.visit.JobContext;
import org.scribble.ast.visit.AstVisitor;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.sesstype.name.Role;

public abstract class Do<K extends ProtocolKind> extends SimpleInteractionNode<K> //implements ScopedNode
{
	//public final ScopeNode scope;
	public final RoleArgList roles;
	public final NonRoleArgList args;
	public final ProtocolNameNode<K> proto;  // Maybe use an "Ambiguous" version until names resolved -- is a visible protocol, but not necessarily a simple or full member name

	protected Do(RoleArgList roleinstans, NonRoleArgList arginstans, ProtocolNameNode<K> proto)
	{
		//this.scope = scope;
		this.roles = roleinstans;
		this.args = arginstans;
		this.proto = proto;
	}

	protected abstract Do<K> reconstruct(RoleArgList roleinstans, NonRoleArgList arginstans, ProtocolNameNode<K> proto);//, SimpleInteractionNodeContext sicontext, Env env);

	@Override
	public Do<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		//ScopeNode scope = isScoped() ? (ScopeNode) visitChild(this.scope, nv) : null;
		RoleArgList ril = (RoleArgList) visitChild(this.roles, nv);
		NonRoleArgList al = (NonRoleArgList) visitChild(this.args, nv);
		ProtocolNameNode<K> proto = visitChildWithClassCheck(this, this.proto, nv);
		return reconstruct(ril, al, proto);
	}

	public ProtocolName<K> getTargetFullProtocolName(ModuleContext mcontext)
	{
		return mcontext.getFullProtocolName(this.proto.toName());
	}
	
	public ProtocolDecl<K> getTargetProtocolDecl(JobContext jcontext, ModuleContext mcontext)
	{
		ProtocolName<K> fullname = getTargetFullProtocolName(mcontext);
		return jcontext.getModule(fullname.getPrefix()).getProtocolDecl(fullname.getSimpleName());
	}
	
	public Role getTargetRoleParameter(JobContext jcontext, ModuleContext mcontext, Role role)
	{
		Iterator<Role> args = this.roles.getRoles().iterator();
		Iterator<Role> params = getTargetProtocolDecl(jcontext, mcontext).header.roledecls.getRoles().iterator();
		while (args.hasNext())
		{
			Role arg = args.next();
			Role param = params.next();
			if (arg.equals(role))
			{
				return param;
			}
		}
		throw new RuntimeException("Not an argument role: " + role);
	}
	
	/*@Override
	public boolean isEmptyScope()
	{
		return this.scope == null;
	}

	@Override
	//public Scope getScope()
	public SimpleName getScopeElement()
	{
		return this.scope.toName();
	}
	
	public boolean isScoped()
	{
		return this.scope != null;
	}*/

	@Override
	public String toString()
	{
		String s = Constants.DO_KW + " ";
		//if (!hasEmptyScopeNode())
		/*if (isScoped())
		{
			s += this.scope + ":";
		}*/
		return s + this.proto + this.args + this.roles + ";";
	}
}
