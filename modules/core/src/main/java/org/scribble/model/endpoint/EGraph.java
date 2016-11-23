package org.scribble.model.endpoint;

import org.scribble.model.MPrettyPrint;

public class EGraph implements MPrettyPrint
{
	public final EState init;
	public final EState term;

	public EGraph(EState init, EState term)
	{
		this.init = init;
		this.term = term;
	}

	public EFSM toFsm()
	{
		return new EFSM(this);
	}

	@Override
	public String toAut()
	{
		return this.init.toAut();
	}

	@Override
	public String toDot()
	{
		return this.init.toDot();
	}

	@Override
	public String toString()
	{
		// return this.init.toDot();
		return this.init.toString();
	}
}
