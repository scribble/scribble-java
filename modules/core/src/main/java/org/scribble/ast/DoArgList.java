package org.scribble.ast;

import java.util.LinkedList;
import java.util.List;

import org.scribble.ast.visit.ModelVisitor;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribbleException;

// Cf. HeaderParameterDeclList -- but not kinded, because cannot determine Arg kind directly from node syntax itself (kinding for ModelNodes is to supplement syntactic information, not "typing" work)
public abstract class DoArgList<T extends DoArg<? extends DoArgNode>> extends ScribNodeBase  // "? extends InstantiationNode" not enforced here (e.g. can put "? extends ModelNode"), because ultimately any instantiation of this class needs an actual instance of "Instantiation" which has to have a parameter that extends "InstantiationNode"
//public abstract class DoArgumentList<K extends Kind> extends ModelNodeBase  // "? extends InstantiationNode" not enforced here (e.g. can put "? extends ModelNode"), because ultimately any instantiation of this class needs an actual instance of "Instantiation" which has to have a parameter that extends "InstantiationNode"
{
	public final List<T> args;
	//public final List<DoArgument<K>> doargs;

	public DoArgList(List<T> is)
	{
		this.args = new LinkedList<>(is);
	}
	
	protected abstract DoArgList<T> reconstruct(List<T> instans);
	
	public abstract DoArgList<T> project(Role self);
	
	@Override
	public DoArgList<T> visitChildren(ModelVisitor nv) throws ScribbleException
	{
		List<T> nds = visitChildListWithClassCheck(this, this.args, nv);
		return reconstruct(nds);
	}

	public int length()
	{
		return this.args.size();
	}
}
