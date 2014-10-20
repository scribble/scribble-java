package org.scribble2.parser.ast.name.simple;

import org.antlr.runtime.Token;
import org.scribble2.parser.ast.ScribbleASTBase;

// Parser Identifier
// The various kinds of names cannot be distinguished from parameter names at the parser level -- maybe no point to distinguish in the AST?
// Could make a subclass of CompoundNameNode (but not very convenient, and wouldn't match grammar def)
public abstract class SimpleNameNode extends ScribbleASTBase //implements Named
{
	public final String identifier;

	public SimpleNameNode(Token t, String name)
	{
		super(t);
		this.identifier = name;
	}

	/*@Override
	public Name toName()
	{
		return new SimpleName(Kind.AMBIGUOUS, this.identifier);
	}*/

	@Override
	public String toString()
	{
		return this.identifier;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || this.getClass() != o.getClass())
		{
			return false;
		}
		return this.identifier.equals(((SimpleNameNode) o).identifier);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 313;
		hash = 31 * hash + this.identifier.hashCode();
		return hash;
	}
}
