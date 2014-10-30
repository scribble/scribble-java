package org.scribble2.foo.ast;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.Token;

public abstract class InstantiationList<T extends Instantiation> extends ScribbleASTBase
{
	public final List<T> instans;

	public InstantiationList(Token t, List<T> is)
	{
		super(t);
		this.instans = new LinkedList<>(is);
	}

	public int length()
	{
		return this.instans.size();
	}
}
