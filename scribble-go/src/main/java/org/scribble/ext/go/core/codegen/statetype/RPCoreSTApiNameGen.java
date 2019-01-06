package org.scribble.ext.go.core.codegen.statetype;

import java.util.stream.Collectors;

import org.scribble.ext.go.core.type.RPFamily;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.type.index.RPBinIndexExpr;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexInt;
import org.scribble.ext.go.type.index.RPIndexIntPair;
import org.scribble.ext.go.type.index.RPIndexVar;

public class RPCoreSTApiNameGen
{
	private final RPCoreSTApiGenerator apigen; 

	public RPCoreSTApiNameGen(RPCoreSTApiGenerator apigen)
	{
		this.apigen = apigen;
	}
	
	public String getApiRootPackageFullPath()
	{
		return this.apigen.packpath + "/" + this.apigen.proto.toString().replaceAll("\\.", "/");
	}

	//@Override
	public String getApiRootPackageName()  
			// Derives only from simple proto name -- remainder of full name is (full) module name
			// CHECKME: use full proto name?  e.g., in gen API package decls
	{
		return this.apigen.proto.getSimpleName().toString();
	}

	public String getProtoTypeName()  
	{
		return this.apigen.proto.getSimpleName().toString();
	}

	/*public String makeApiRootPackageDecl()
	{
		return "package " + getApiRootPackageName();
	}*/

	//public String getFamilyPackageName(Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> family)
	public String getFamilyPackageName(RPFamily family)
	{
		return "family_" + this.apigen.families.get(family);
	}

	public String getEndpointKindPackageName(RPRoleVariant variant)
	{
		return getGeneratedRoleVariantName(variant);
	}
	
	// Role variant = Endpoint kind -- e.g., S_1To1, W_1Ton
	public String getEndpointKindTypeName(//GProtocolName simpname, 
			RPRoleVariant variant)
	{
		//return simpname + "_" + getGeneratedActualRoleName(r);
		return getGeneratedRoleVariantName(variant);
	}
	
	private String getGeneratedRoleVariantName(RPRoleVariant variant)
	{
		return variant.getName() + "_"
				+ variant.intervals.stream().map(g -> getGeneratedNameLabel(g.start) + "to" + getGeneratedNameLabel(g.end))
				    .sorted().collect(Collectors.joining("and"))
				+ (variant.cointervals.isEmpty()
						? ""
						: "_not_" + variant.cointervals.stream().map(g -> getGeneratedNameLabel(g.start) + "to" + getGeneratedNameLabel(g.end))
						    .sorted().collect(Collectors.joining("and")));
	}

	// For type name generation -- not actual code exprs, cf. RPCoreSTStateChanApiBuilder#generateIndexExpr
	// TODO: refactor toGoName alongside toGoString ?  (rename latter to toGoExpr)
	public static String getGeneratedNameLabel(RPIndexExpr e)
	{
		if (e instanceof RPIndexInt)
		{
			return e.toGoString();
		}
		else if (e instanceof RPIndexVar)
		{
			return e.toGoString();
		}
		else if (e instanceof RPIndexIntPair)
		{
			//return e.toGoString();  // No: that gives the "value" expression
			RPIndexIntPair p = (RPIndexIntPair) e;
			int l = ((RPIndexInt) p.left).val;
			int r = ((RPIndexInt) p.right).val;
			String ll = (l < 0) ? "neg" + (-1*l) : Integer.toString(l);
			String rr = (r < 0) ? "neg" + (-1*r) : Integer.toString(r);
			return "l" + ll + "r" + rr;
		}
		else if (e instanceof RPBinIndexExpr)
		{
			RPBinIndexExpr b = (RPBinIndexExpr) e;
			String op;
			switch (b.op)
			{
				case Add:  op = "plus"; break;
				case Subt: op = "sub";  break;
				case Mult: op = "mul";  //break;
				default: throw new RuntimeException("TODO: " + b.op);
			}
			return getGeneratedNameLabel(b.left) + op + getGeneratedNameLabel(b.right);  // FIXME: pre/postfix more consistent?
		}
		else
		{
			throw new RuntimeException("Shouldn't get here: " + e);
		} 
	}
}
