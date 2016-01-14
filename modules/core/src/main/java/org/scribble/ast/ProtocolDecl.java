package org.scribble.ast;

import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

public abstract class ProtocolDecl<K extends ProtocolKind> extends ScribNodeBase implements ModuleMember, ProtocolKindNode<K>
{
	// Maybe just use standard pattern, make private with casting getters -- works better (e.g. to use overridden getName)
	public final ProtocolHeader<K> header;
	public final ProtocolDef<K> def;

	protected ProtocolDecl(ProtocolHeader<K> header, ProtocolDef<K> def)
	{
		this.header = header;
		this.def = def;
	}

	public abstract ProtocolDecl<K> reconstruct(ProtocolHeader<K> header, ProtocolDef<K> def);//, ProtocolDeclContext pdcontext, Env env);

	@Override
	public ProtocolDecl<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		ProtocolHeader<K> header = visitChildWithClassEqualityCheck(this, this.header, nv);
		ProtocolDef<K> def = visitChildWithClassEqualityCheck(this, this.def, nv);
		return reconstruct(header, def);
	}
	
	public abstract ProtocolHeader<K> getHeader();
	public abstract ProtocolDef<K> getDef();

	//public abstract ProtocolName<? extends ProtocolKind> getFullProtocolName(Module mod);
	
	@Override
	public String toString()
	{
		return this.header + " " + this.def;
	}
}
