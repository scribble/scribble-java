package org.scribble.ext.go.core.codegen.statetype;

import java.util.stream.Collectors;

import org.scribble.ext.go.core.type.RPFamily;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.type.index.RPBinIndexExpr;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexInt;
import org.scribble.ext.go.type.index.RPIndexIntPair;
import org.scribble.ext.go.type.index.RPIndexVar;

// Also some paths
public class RPCoreSTApiNameGen
{
	private final RPCoreSTApiGenerator parent; 

	public RPCoreSTApiNameGen(RPCoreSTApiGenerator apigen)
	{
		this.parent = apigen;
	}
	
	public String getApiRootPackageFullPath()
	{
		return this.parent.packpath + "/" + this.parent.proto.toString().replaceAll("\\.", "/");
	}

	//@Override
	public String getApiRootPackageName()  
			// Derives only from simple proto name -- remainder of full name is (full) module name
			// CHECKME: use full proto name?  e.g., in gen API package decls
	{
		return this.parent.proto.getSimpleName().toString();
	}

	public String getProtoTypeName()  
	{
		return this.parent.proto.getSimpleName().toString();
	}

	/*public String makeApiRootPackageDecl()
	{
		return "package " + getApiRootPackageName();
	}*/

	//public String getFamilyPackageName(Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> family)
	public String getFamilyPackageName(RPFamily family)
	{
		return "family_" + this.parent.families.get(family);
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
				+ variant.intervals.stream().map(g -> getGeneratedLabel(g.start) + "to" + getGeneratedLabel(g.end))
				    .sorted().collect(Collectors.joining("and"))
				+ (variant.cointervals.isEmpty()
						? ""
						: "_not_" + variant.cointervals.stream().map(g -> getGeneratedLabel(g.start) + "to" + getGeneratedLabel(g.end))
						    .sorted().collect(Collectors.joining("and")));
	}

	// Not variants -- just indexed roles (in EFSM actions) -- cf. ParamCoreSTEndpointApiGenerator#getGeneratedRoleVariantName
	public String getGeneratedIndexedRoleName(RPIndexedRole r) 
	{
		//return r.toString().replaceAll("\\[", "_").replaceAll("\\]", "_").replaceAll("\\.", "_");
		if (r.intervals.size() > 1)
		{
			throw new RuntimeException("[rp-core] TODO: " + r);
		}
		RPInterval g = r.intervals.iterator().next();
		return r.getName() + "_" + getGeneratedLabel(g.start)
				+ (g.start.equals(g.end) ? "" : "to" + getGeneratedLabel(g.end));
	}

	// For type name generation -- not actual code exprs, cf. RPCoreSTStateChanApiBuilder#generateIndexExpr
	// TODO: refactor toGoName alongside toGoString ?  (rename latter to toGoExpr)
	private String getGeneratedLabel(RPIndexExpr e)
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
			return getGeneratedLabel(b.left) + op + getGeneratedLabel(b.right);  // FIXME: pre/postfix more consistent?
		}
		else
		{
			throw new RuntimeException("Shouldn't get here: " + e);
		} 
	}

	// Returns path to target *directory* as an offset to -d -- does not include the target file itself
	// -- cf. packpath, "absolute" Go import path (github.com/...) -- would coincide if protocol full name (i.e., module) used "github.com/..."
	public String getEndpointKindDirPath(RPFamily family, RPRoleVariant variant, boolean isCommonEndpointKind)
	{
		String basedir = this.parent.proto.toString().replaceAll("\\.", "/") + "/";  // Full name
		return basedir
				+ (isCommonEndpointKind ? "" : "/" + this.parent.namegen.getFamilyPackageName(family))
				+ "/" + this.parent.namegen.getEndpointKindPackageName(variant);
				
				/*// "Syntactically" determining common endpoint kinds difficult because concrete peers depends on param (and foreachvar) values, e.g., M in PGet w.r.t. number of F's
				// Also, family factoring is more about dial/accept
				isCommonEndpointKind = true;
				Set<Role> peers = null;
				X: for (Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> fam : this.apigen.families.keySet())
				{
					if (fam.left.contains(variant))
					{
						EGraph g = this.apigen.variants.get(rname).get(variant);
						Set<RPCoreEAction> as = RPCoreEState.getReachableActions((RPCoreEModelFactory) this.apigen.job.ef, (RPCoreEState) g.init);
						Set<Role> tmp = as.stream().map(a -> a.getPeer()).collect(Collectors.toSet());
						if (peers == null)
						{
							peers = tmp;
						}
						else if (!peers.equals(tmp))
						{
							isCommonEndpointKind = false;
							break X;
						}
					}
				}
				//*/
	}

	// Duplicated from getEndpointKindFilePath
	// Returns path to target *directory* as an offset to -d -- does not include the target file itself
	public String getStateChannelDirPath(RPFamily family, RPRoleVariant variant, boolean isCommonEndpointKind)
	{
		String basedir = this.parent.proto.toString().replaceAll("\\.", "/") + "/";  // Full name
		return basedir
				+ (isCommonEndpointKind ? "" : "/" + this.parent.namegen.getFamilyPackageName(family))
				+ "/" + this.parent.namegen.getEndpointKindPackageName(variant);  // State chans located with Endpoint Kind API
	}
}
