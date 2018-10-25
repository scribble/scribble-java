package org.scribble.ext.go.type.annot;

import java.util.Set;

import org.scribble.ext.go.type.annot.RPBinAnnotExpr.Op;
import org.scribble.ext.go.util.Smt2Translator;
import org.scribble.util.Pair;

public abstract class RPAnnotExpr
{
	public boolean isConstant()
	{
		return false;
	}
	
	public abstract String toGoString();  // As basic Go expressions, but not (necessarily) actual code generation "ouput"
			// N.B. "value" expressions -- though may also be used for, e.g., names (e.g., RPCoreSTApiGenerator.getGeneratedNameLabel) 
	
	public abstract Set<RPAnnotExpr> getVals();  // TODO: factor out a "value" interface
	public abstract Set<RPAnnotVar> getVars();  // N.B. doesn't include foreach params

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
		if (!(o instanceof RPAnnotExpr))
		{
			return false;
		}
		return ((RPAnnotExpr) o).canEqual(this);
	}

	protected abstract boolean canEqual(Object o);
	
	// In case subclasses do super
	@Override
	public int hashCode()
	{
		return 5869;
	}
	
	// TODO: parse properly using ANTLR, cf. Assrt
	public static RPAnnotExpr parse(String annot)
	{
		//for (String t = annot.trim(); !t.equals(""); )
		{
			RPAnnotExpr left;
			RPAnnotExpr right;
			String t = annot.trim();
			Pair<String, Integer> next = nextToken(t);
			t = t.substring(next.right, t.length()).trim();
			left = parseEle(next.left);
			Pair<String, Integer> op = nextToken(t);
			t = t.substring(op.right, t.length()).trim();
			next = nextToken(t);
			right = parseEle(next.left);
			switch (op.left)
			{
				case "<":  return RPAnnotFactory.ParamBinAnnotExpr(Op.Lt, left, right);
				case "<=": return RPAnnotFactory.ParamBinAnnotExpr(Op.Lte, left, right);
				case ">":  return RPAnnotFactory.ParamBinAnnotExpr(Op.Gt, left, right);
				case ">=": return RPAnnotFactory.ParamBinAnnotExpr(Op.Gte, left, right);
				default:  throw new RuntimeException("Shouldn't get in here: " + annot);
			}
		}
	}
	
	private static RPAnnotExpr parseEle(String e)
	{
		if (e.startsWith("("))
		{
			int comma = e.indexOf(",");
			RPAnnotExpr l = parseEle(e.substring(1, comma));
			RPAnnotExpr r = parseEle(e.substring(comma+1, e.length()-1));
			return RPAnnotFactory.RPAnnotPair(l, r);
		}
		else
		{
			try
			{
				return RPAnnotFactory.ParamIntVal(Integer.parseInt(e));
			}
			catch (NumberFormatException x)
			{
				return RPAnnotFactory.ParamIndexVar(e);
			}
		}
	}
	
	private static Pair<String, Integer> nextToken(String t)
	{
		for (int i = 0; i < t.length(); i++)
		{
			char c = t.charAt(i);
			if (c == ' ')
			{

			}
			else if (c == '(')
			{
				//int comma = t.indexOf(',', i+1);
				int j = t.indexOf(')', i+1) + 1;
				return new Pair<>(t.substring(i, j), j);
			}
			else if (c >= '0' && c <= '9')
			{
				int j = i;
				while (j < t.length() && c >= '0' && c <= '9')
				{
					j++;
					if (j == t.length())
					{
						break;
					}
					c = t.charAt(j);
				} 
				return new Pair<>(t.substring(i, j), j);
			}
			else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
			{
				int j = i;
				while (j < t.length() && (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
				{
					j++;
					if (j == t.length())
					{
						break;
					}
					c = t.charAt(j);
				} 
				return new Pair<>(t.substring(i, j), j);
			}
			else if (c == '<')
			{
				if (i+1 < t.length() && t.charAt(i+1) == '=')
				{
					return new Pair<>(t.substring(i, i+2), i+2);
				}
				return new Pair<>(t.substring(i, i+1), i+1);
			}
			else if (c == '>')
			{
				if (i+1 < t.length() && t.charAt(i+1) == '=')
				{
					return new Pair<>(t.substring(i, i+2), i+2);
				}
				return new Pair<>(t.substring(i, i+1), i+1);
			}
			else
			{
				throw new RuntimeException("TODO: " + t);
			}
		}
		throw new RuntimeException("TODO: " + t);
	}
}
