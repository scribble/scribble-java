package org.scribble.ast;

import java.util.Collections;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

// FIXME: visitChildren for modifiers
public abstract class ProtocolDecl<K extends ProtocolKind> extends ScribNodeBase implements ModuleMember, ProtocolKindNode<K>
{
	public static enum Modifiers { EXPLICIT, AUX }  // FIXME: factor out?  Header?

	// FIXME: lookup routines, e.g. isExplicit
	public final List<Modifiers> modifiers;

	// Maybe just use standard pattern, make private with casting getters -- works better (e.g. to use overridden getName)
	public final ProtocolHeader<K> header;
	public final ProtocolDef<K> def;

	protected ProtocolDecl(CommonTree source, List<Modifiers> modifiers, ProtocolHeader<K> header, ProtocolDef<K> def)
	{
		super(source);
		this.modifiers = Collections.unmodifiableList(modifiers);
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
	
	public boolean isExplicitModifier()
	{
		return this.modifiers.contains(Modifiers.EXPLICIT);
	}

	public boolean isAuxModifier()
	{
		return this.modifiers.contains(Modifiers.AUX);
	}
}
