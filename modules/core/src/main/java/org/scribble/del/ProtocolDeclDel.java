package org.scribble.del;

import java.util.Set;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ProtocolDeclContext;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.SubprotocolSig;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.MemberName;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.NameDisambiguator;
import org.scribble.visit.ProtocolDeclContextBuilder;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.RoleCollector;

public abstract class ProtocolDeclDel<K extends ProtocolKind> extends ScribDelBase
{
	private ProtocolDeclContext<K> pdcontext;

	protected ProtocolDeclDel()
	{

	}
	
	protected abstract ProtocolDeclDel<K> copy();
		
	@Override
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		disamb.clear();
		return visited;
	}

	@Override
	public void enterProtocolDeclContextBuilding(ScribNode parent, ScribNode child, ProtocolDeclContextBuilder builder) throws ScribbleException
	{
		builder.clearProtocolDependencies();  // collect per protocoldecl all together, do not clear?

		Module main = (Module) parent;
		ProtocolDecl<?> lpd = (ProtocolDecl<?>) child;
		MemberName<?> lpn = lpd.getFullMemberName(main);
		// Is it really needed to add self protocoldecl dependencies?
		lpd.header.roledecls.getRoles().stream().forEach((r) -> addSelfDependency(builder, (ProtocolName<?>) lpn, r));
	}
	
	protected abstract void addSelfDependency(ProtocolDeclContextBuilder builder, ProtocolName<?> proto, Role role);

	@Override
	public void enterProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl) throws ScribbleException
	{
		SubprotocolSig subsig = inl.peekStack();  // SubprotocolVisitor has already entered subprotocol
		inl.setRecVar(subsig);
	}

	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	@Override
	public ScribNode leaveRoleCollection(ScribNode parent, ScribNode child, RoleCollector coll, ScribNode visited) throws ScribbleException
	{
		ProtocolDecl<?> pd = (ProtocolDecl<?>) visited;
		Set<Role> occs = coll.getNames();
		// Needs ProtocolDeclContextBuilder to have built the context already
		ProtocolDeclDel<K> del = setProtocolDeclContext(getProtocolDeclContext().setRoleOccurrences(occs));
		return pd.del(del);
	}
	
	public ProtocolDeclContext<K> getProtocolDeclContext()
	{
		return this.pdcontext;
	}
	
	protected ProtocolDeclDel<K> setProtocolDeclContext(ProtocolDeclContext<K> pdcontext)
	{
		ProtocolDeclDel<K> copy = copy();  // FIXME: should be a deep clone in principle -- but if any other children are immutable, they can be shared
		copy.pdcontext = pdcontext;
		return copy;
	}
}
