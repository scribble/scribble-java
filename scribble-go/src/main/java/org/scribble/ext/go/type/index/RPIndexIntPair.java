package org.scribble.ext.go.type.index;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ext.go.util.Smt2Translator;


// Currently only considered as a value
public class RPIndexIntPair extends RPIndexExpr implements RPIndexVal
{
	public final RPIndexExpr left;
	public final RPIndexExpr right;

	protected RPIndexIntPair(RPIndexExpr left, RPIndexExpr right)
	{
		//if (!isUnaryExpr(left) || !isUnaryExpr(right))
		if (!(left instanceof RPIndexInt) || !(right instanceof RPIndexInt))
		{
			throw new RuntimeException("TODO: (" + left + ", " + right + ")");
		}
		this.left = left;
		this.right = right;
	}
	
	/*private static boolean isUnaryExpr(RPIndexExpr e)
	{
		return (e instanceof RPIndexInt) || (e instanceof RPIndexVar);
	}*/

	@Override
	public boolean gtEq(RPIndexVal them)
	{
		RPIndexIntPair p = (RPIndexIntPair) them;
		return ((RPIndexInt) this.left).val >= ((RPIndexInt) p.left).val && ((RPIndexInt) this.right).val >= ((RPIndexInt) p.right).val;
	}

	@Override
	public RPIndexExpr minimise(int self)
	{
		return RPIndexFactory.RPIndexPair(this.left.minimise(self), this.right.minimise(self));
	}
	
	@Override
	public String toGoString()
	{
		return "(session2.XY(" + this.left.toGoString() + ", " + this.right.toGoString() + "))";
	}
	
	@Override
	public String toSmt2Formula(Smt2Translator smt2t)
	{
		String left = this.left.toSmt2Formula(smt2t);
		String right = this.right.toSmt2Formula(smt2t);
		return "(mk-pair " + left + " " + right + ")";
	}

	@Override
	public Set<RPIndexExpr> getVals()
	{
		return Stream.of(this).collect(Collectors.toSet());
	}

	@Override
	public Set<RPIndexVar> getVars()
	{
		return Stream.of(this.left.getVars(), this.right.getVars()).flatMap(x -> x.stream()).collect(Collectors.toSet());
		//return Collections.emptySet();  // Currently only considered as a value
	}

	@Override
	public String toString()
	{
		return "(" + this.left.toString() + ", " + this.right.toString() + ")";
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof RPIndexIntPair))
		{
			return false;
		}
		RPIndexIntPair f = (RPIndexIntPair) o;
		return super.equals(this)  // Does canEqual
				&& this.left.equals(f.left) && this.right.equals(f.right);  
	}
	
	@Override
	protected boolean canEqual(Object o)
	{
		return o instanceof RPIndexIntPair;
	}

	@Override
	public int hashCode()
	{
		int hash = 6311;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.left.hashCode();
		hash = 31 * hash + this.right.hashCode();
		return hash;
	}
}
