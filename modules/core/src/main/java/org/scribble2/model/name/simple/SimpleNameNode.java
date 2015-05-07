package org.scribble2.model.name.simple;

import org.scribble2.model.name.NameNode;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.name.Name;


// Parser Identifier
// The various kinds of names cannot be distinguished from parameter names at the parser level -- maybe no point to distinguish in the AST?
// Could make a subclass of CompoundNameNode (but not very convenient, and wouldn't match grammar def)
//public abstract class SimpleNameNode<T extends SimpleName> extends NameNode<T>
public abstract class SimpleNameNode<T extends Name<K>, K extends Kind> extends NameNode<T, K>
//public abstract class SimpleNameNode<T extends Name<K>, K extends Kind> extends CompoundNameNode<T, K>  // No: SimpleNameNode isn't a CompoundNameNode -- syntactically different; cf. Name, where they are not distinct
{
	public final String identifier;

	public SimpleNameNode(String identifier)
	{
		this.identifier = identifier;
	}

	/*@Override
	public Name toName()
	{
		return new SimpleName(Kind.AMBIGUOUS, this.identifier);
	}*/

	//protected abstract SimpleNameNode<T> reconstruct(String identifier);

	@Override
	public String toString()
	{
		//return this.identifier;
		return toName().toString();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		//if (o == null || this.getClass() != o.getClass())
		if (!(o instanceof SimpleNameNode))
		{
			return false;
		}
		//return this.identifier.equals(((SimpleNameNode) o).identifier);
		return this.identifier.equals(SimpleNameNode.class.cast(o).identifier);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 313;
		hash = 31 * hash + this.identifier.hashCode();
		return hash;
	}
}
