package org.scribble2.model;

import java.util.LinkedList;
import java.util.List;

// Cf. HeaderParameterDeclList
public abstract class InstantiationList<T extends Instantiation<? extends InstantiationNode>> extends ModelNodeBase  // "? extends InstantiationNode" not enforced here (e.g. can put "? extends ModelNode"), because ultimately any instantiation of this class needs an actual instance of "Instantiation" which has to have a parameter that extends "InstantiationNode"
{
	public final List<T> instans;

	public InstantiationList(List<T> is)
	{
		this.instans = new LinkedList<>(is);
	}

	public int length()
	{
		return this.instans.size();
	}
}
