package org.scribble.ext.go.type.index;

import java.util.HashSet;
import java.util.Set;


// Binary arithmetic
public class RPBinIndexExpr extends RPIndexExpr
{
	public enum Op
	{
		Add,
		Subt,
		Mult;
		
		@Override
		public String toString()
		{
			switch (this)
			{
				case Add:  return "+";
				case Subt: return "-";
				case Mult: return "*";
				default: throw new RuntimeException("[param] Won't get in here: " + this);
			}
		}
	}

	public final Op op;
	public final RPIndexExpr left;
	public final RPIndexExpr right;

	protected RPBinIndexExpr(Op op, RPIndexExpr left, RPIndexExpr right)
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
	public String toSmt2Formula()
	{
		String left = this.left.toSmt2Formula();
		String right = this.right.toSmt2Formula();
		String op;
		switch(this.op)
		{
			case Add:  op = "+"; break;
			case Subt: op = "-"; break;
			case Mult: op = "*"; break;
			default:   throw new RuntimeException("[param] Shouldn't get in here: " + this.op);
		}
		return "(" + op + " " + left + " " + right + ")";
	}

	@Override
	public Set<RPIndexVar> getVars()
	{
		Set<RPIndexVar> vars = new HashSet<>(this.left.getVars());
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
		if (!(o instanceof RPBinIndexExpr))
		{
			return false;
		}
		RPBinIndexExpr f = (RPBinIndexExpr) o;
		return super.equals(this)  // Does canEqual
				&& this.op.equals(f.op) && this.left.equals(f.left) && this.right.equals(f.right);  
						// Storing left/right as a Set could give commutativity in equals, but not associativity
						// Better to keep "syntactic" equality, and do via additional routines for, e.g., normal forms
	}
	
	@Override
	protected boolean canEqual(Object o)
	{
		return o instanceof RPBinIndexExpr;
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
