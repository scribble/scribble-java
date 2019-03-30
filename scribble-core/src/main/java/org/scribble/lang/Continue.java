package org.scribble.lang;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.scribble.ast.ProtocolKindNode;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.ModuleName;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

public abstract class Continue<K extends ProtocolKind>
		extends STypeBase<K> implements SType<K>
{
	public final RecVar recvar;

	public Continue(//org.scribble.ast.Continue<K> source,
			ProtocolKindNode<K> source,  // Due to inlining, do -> continue
			RecVar recvar)
	{
		super(source);
		this.recvar = recvar;
	}

	public abstract Continue<K> reconstruct(
			org.scribble.ast.ProtocolKindNode<K> source, RecVar recvar);
	
	@Override
	public Set<Role> getRoles()
	{
		return Collections.emptySet();
	}

	@Override
	public List<ModuleName> getDependencies()
	{
		return Collections.emptyList();
	}

	@Override
	public Continue<K> substitute(Substitutions subs)

	{
		return reconstruct(getSource(), this.recvar);
	}

	@Override
	public Continue<K> getInlined(STypeInliner i)
	{
		RecVar rv = i.getInlinedRecVar(this.recvar);
		return reconstruct(getSource(), rv);
	}
	
	@Override
	public org.scribble.ast.ProtocolKindNode<K> getSource()
	{
		return (org.scribble.ast.ProtocolKindNode<K>) super.getSource();
	}

	@Override
	public String toString()
	{
		return "continue " + this.recvar + ";";
	}

	@Override
	public int hashCode()
	{
		int hash = 3217;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.recvar.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Continue))
		{
			return false;
		}
		Continue<?> them = (Continue<?>) o;
		return super.equals(this)  // Does canEquals
				&& this.recvar.equals(them.recvar);
	}
}
