package org.scribble.ext.go.type.annot;

import java.util.HashSet;
import java.util.Set;

import org.scribble.ext.go.util.Smt2Translator;


// Binary arithmetic
public class RPBinAnnotExpr extends RPAnnotExpr
{
	public enum Op
	{
		Gt,
		Gte,
		Lt,
		Lte;
		
		@Override
		public String toString()
		{
			switch (this)
			{
				case Gt:  return ">";
				case Gte: return ">=";
				case Lt:  return "<";
				case Lte: return "<=";
				default: throw new RuntimeException("[param] Shouldn't get in here: " + this);
			}
		}
	}

	public final Op op;
	public final RPAnnotExpr left;
	public final RPAnnotExpr right;

	protected RPBinAnnotExpr(Op op, RPAnnotExpr left, RPAnnotExpr right)
	{
		this.left = left;
		this.right = right;
		this.op = op;
	}
	
	@Override
	public String toGoString()
	{
		//throw new RuntimeException("[param-core] TODO: " + this);
		return toString();
	}
	
	@Override
	public String toSmt2Formula(Smt2Translator smt2t)
	{
		String left = this.left.toSmt2Formula(smt2t);
		String right = this.right.toSmt2Formula(smt2t);
		String op;
		switch(this.op)
		{
			case Gt:  op = smt2t.getGtOp(); break;
			case Gte: op = smt2t.getGteOp(); break;
			case Lt:  op = smt2t.getLtOp(); break;
			case Lte: op = smt2t.getLteOp(); break;
			//case Mult: op = "*"; break;
			default:   throw new RuntimeException("[param] Shouldn't get in here: " + this.op);
		}
		return "(" + op + " " + left + " " + right + ")";
	}

	@Override
	public Set<RPAnnotExpr> getVals()
	{
		Set<RPAnnotExpr> vals = new HashSet<>(this.left.getVals());
		vals.addAll(this.right.getVals());
		return vals;
	}

	@Override
	public Set<RPAnnotVar> getVars()
	{
		Set<RPAnnotVar> vars = new HashSet<>(this.left.getVars());
		vars.addAll(this.right.getVars());
		return vars;
	}

	@Override
	public String toString()
	{
		return "(" + this.left.toString() + this.op + this.right.toString() + ")";
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof RPBinAnnotExpr))
		{
			return false;
		}
		RPBinAnnotExpr f = (RPBinAnnotExpr) o;
		return super.equals(this)  // Does canEqual
				&& this.op.equals(f.op) && this.left.equals(f.left) && this.right.equals(f.right);  
						// Storing left/right as a Set could give commutativity in equals, but not associativity
						// Better to keep "syntactic" equality, and do via additional routines for, e.g., normal forms
	}
	
	@Override
	protected boolean canEqual(Object o)
	{
		return o instanceof RPBinAnnotExpr;
	}

	@Override
	public int hashCode()
	{
		int hash = 5879;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.op.hashCode();
		hash = 31 * hash + this.left.hashCode();
		hash = 31 * hash + this.right.hashCode();
		return hash;
	}
}
