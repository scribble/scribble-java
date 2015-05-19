package org.scribble2.model;

import java.util.LinkedList;
import java.util.List;

import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

// Cf. HeaderParameterDeclList
public abstract class DoArgumentList<T extends DoArgument<? extends DoArgumentNode>> extends ModelNodeBase  // "? extends InstantiationNode" not enforced here (e.g. can put "? extends ModelNode"), because ultimately any instantiation of this class needs an actual instance of "Instantiation" which has to have a parameter that extends "InstantiationNode"
{
	public final List<T> instans;

	public DoArgumentList(List<T> is)
	{
		this.instans = new LinkedList<>(is);
	}
	
	protected abstract DoArgumentList<T> reconstruct(List<T> instans);
	
	public abstract DoArgumentList<T> project(Role self);
	
	@Override
	public DoArgumentList<T> visitChildren(ModelVisitor nv) throws ScribbleException
	{
		List<T> nds = visitChildListWithClassCheck(this, this.instans, nv);
		return reconstruct(nds);
	}

	public int length()
	{
		return this.instans.size();
	}
}
