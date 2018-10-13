package org.scribble.ext.go.type.index;

import java.util.Set;

import org.scribble.ext.go.util.Smt2Translator;

public abstract class RPIndexExpr
{
	public boolean isConstant()
	{
		return false;
	}
	
	public abstract RPIndexExpr minimise(int self);
	
	public abstract String toGoString();  // As basic Go expressions, but not (necessarily) actual code generation "ouput"
			// N.B. "value" expressions -- though may also be used for, e.g., names (e.g., RPCoreSTApiGenerator.getGeneratedNameLabel) 
	
	public abstract Set<RPIndexExpr> getVals();  // TODO: factor out a "value" interface
	public abstract Set<RPIndexVar> getVars();  // N.B. doesn't include foreach params

	public abstract String toSmt2Formula(Smt2Translator smt2t);  // Cf. toString -- but can be useful to separate, for debugging (and printing)
			// FIXME: inconsistency with toString
			// TODO: factor out Smt2 translation interface

	// N.B. "syntactic" comparison
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof RPIndexExpr))
		{
			return false;
		}
		return ((RPIndexExpr) o).canEqual(this);
	}

	protected abstract boolean canEqual(Object o);
	
	// In case subclasses do super
	@Override
	public int hashCode()
	{
		return 5869;
	}
}
