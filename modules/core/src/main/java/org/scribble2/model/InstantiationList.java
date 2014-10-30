package org.scribble2.model;

import java.util.LinkedList;
import java.util.List;

public abstract class InstantiationList<T extends Instantiation> extends ModelNodeBase
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
