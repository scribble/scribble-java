package org.scribble.model.endpoint;

import org.scribble.model.MPrettyPrint;

public class EGraph implements MPrettyPrint
{
	public final EState init;
	public final EState term;

	protected EGraph(EState init, EState term)
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
	public final int hashCode()
	{
		int hash = 1051;
		hash = 31 * hash + this.init.hashCode();  // FIXME: uses (init) state ID only -- although OK since state IDs globall unique
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof EGraph))
		{
			return false;
		}
		EGraph them = (EGraph) o;
		return this.init.equals(them.init);// && this.term.equals(them.term);  // N.B. EState.equals checks state ID only, but OK because EStates have globally unique IDs -- any need to do a proper graph equality?
	}

	@Override
	public String toString()
	{
		return this.init.toString();
	}
}
