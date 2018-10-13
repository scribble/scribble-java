package org.scribble.ext.go.type.index;

import java.util.HashSet;
import java.util.Set;

import org.scribble.ext.go.util.Smt2Translator;


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
				case Mult: //return "*";
				default: throw new RuntimeException("[param] Shouldn't get in here: " + this);
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
	public RPIndexExpr minimise(int self)
	{
		// FIXME: consider associativity for "maximal" reduction (or normal forms)
		RPIndexExpr left = this.left.minimise(self);
		RPIndexExpr right = this.right.minimise(self);
		if (right instanceof RPIndexInt)
		{
			int y = ((RPIndexInt) right).val;
			if (left instanceof RPIndexInt)
			{
				int x = ((RPIndexInt) left).val;
				switch (this.op) 
				{
					case Add:  return RPIndexFactory.ParamIntVal(x + y);
					case Mult: return RPIndexFactory.ParamIntVal(x * y);
					case Subt: return RPIndexFactory.ParamIntVal(x - y);
					default: throw new RuntimeException("Shouldn't get in here: " + this);
				}
			}
			else
			{
				if (y < 0)
				{
					if  (this.op == Op.Add)
					{
						return RPIndexFactory.ParamBinIndexExpr(Op.Subt, left, RPIndexFactory.ParamIntVal(-y));
					}
					else if  (this.op == Op.Subt)
					{
						return RPIndexFactory.ParamBinIndexExpr(Op.Add, left, RPIndexFactory.ParamIntVal(-y));
					}
				}
			}
		}
		else
		{
			if (left instanceof RPIndexInt)
			{
				int x = ((RPIndexInt) left).val;
				if (x < 0 && this.op == Op.Add)
				{
					return RPIndexFactory.ParamBinIndexExpr(Op.Subt, right, RPIndexFactory.ParamIntVal(-x));
				}
			}
			else
			{
				if (right instanceof RPIndexPair)
				{
					RPIndexPair y = ((RPIndexPair) right);
					if (left instanceof RPIndexPair)
					{
						RPIndexPair x = ((RPIndexPair) left);
						switch (this.op) 
						{
							case Add:  return RPIndexFactory.RPIndexPair(RPIndexFactory.ParamIntVal(((RPIndexInt) x.left).val + ((RPIndexInt) y.left).val),
									RPIndexFactory.ParamIntVal(((RPIndexInt) x.right).val + ((RPIndexInt) y.right).val));
							case Subt: return RPIndexFactory.RPIndexPair(RPIndexFactory.ParamIntVal(((RPIndexInt) x.left).val - ((RPIndexInt) y.left).val),
									RPIndexFactory.ParamIntVal(((RPIndexInt) x.right).val - ((RPIndexInt) y.right).val));
							case Mult: //return RPIndexFactory.ParamIntVal(x * y);
							default: throw new RuntimeException("Shouldn't get in here: " + this);
						}
					}
					else
					{
						/*if (y < 0)  // TODO
						{
							if  (this.op == Op.Add)
							{
								return RPIndexFactory.ParamBinIndexExpr(Op.Subt, left, RPIndexFactory.ParamIntVal(-y));
							}
							else if  (this.op == Op.Subt)
							{
								return RPIndexFactory.ParamBinIndexExpr(Op.Add, left, RPIndexFactory.ParamIntVal(-y));
							}
						}*/
					}
				}
				else
				{
					/*if (left instanceof RPIndexPair)  // TODO
					{
						int x = ((RPIndexInt) left).val;
						if (x < 0 && this.op == Op.Add)
						{
							return RPIndexFactory.ParamBinIndexExpr(Op.Subt, right, RPIndexFactory.ParamIntVal(-x));
					}*/
				}
			}
		}
		return RPIndexFactory.ParamBinIndexExpr(this.op, left, right);
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
			case Add:  op = smt2t.getPlusOp(); break;
			case Subt: op = smt2t.getSubOp(); break;
			//case Mult: op = "*"; break;
			default:   throw new RuntimeException("[param] Shouldn't get in here: " + this.op);
		}
		return "(" + op + " " + left + " " + right + ")";
	}

	@Override
	public Set<RPIndexExpr> getVals()
	{
		Set<RPIndexExpr> vals = new HashSet<>(this.left.getVals());
		vals.addAll(this.right.getVals());
		return vals;
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
