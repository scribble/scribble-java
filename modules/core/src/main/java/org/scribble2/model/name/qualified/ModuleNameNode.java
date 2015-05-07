package org.scribble2.model.name.qualified;

import org.scribble2.sesstype.kind.ModuleKind;
import org.scribble2.sesstype.name.ModuleName;
import org.scribble2.sesstype.name.PackageName;




//public class ModuleNameNode extends QualifiedNameNode<ModuleName>
public class ModuleNameNode extends QualifiedNameNode<ModuleName, ModuleKind>
{
	//public ModuleNameNodes(PrimitiveNameNode... ns)
	public ModuleNameNode(String... ns)
	{
		super(ns);
	}

	@Override
	protected ModuleNameNode copy()
	{
		return new ModuleNameNode(this.elems);
	}
	
	/*public ModuleNameNodes(ModuleName mn)
	{
		super(decompileName(mn.text));
		if (mn.text.contains("."))
		{
			String[] split = mn.text.substring(0, mn.text.lastIndexOf('.')).split("\\.");
			LinkedList<PrimitiveNameNode> tmp = new LinkedList<>();
			for (String s : Arrays.asList(split))
			{
				tmp.add(new PrimitiveNameNode(null, s));
			}
			this.pn = new PackageNameNodes(tmp);
			int lastIndexOf = mn.text.lastIndexOf('.' + 1);
			this.smn = new PrimitiveNameNode(null, lastIndexOf == -1 ? mn.text : mn.text.substring(lastIndexOf));
		}
		else
		{
			this.pn = new PackageNameNodes(Collections.<PrimitiveNameNode>emptyList());
			this.smn = new PrimitiveNameNode(null, mn.text);
		}
	}
	
	private static List<PrimitiveNameNode> decompileName(String text)
	{
		List<PrimitiveNameNode> pnns = new LinkedList<>();
		for (String s : text.split("\\."))
		{
			pnns.add(new PrimitiveNameNode(null, s));
		}
		return pnns;
	}*/
	
	/*public ModuleName getSimpleModuleName()
	{
		return new ModuleName(PackageName.EMPTY_PACKAGENAME, smn);
	}*/
	
	@Override
	public ModuleName toName()
	{
		//return toModuleName(this);
		//String modname = getLastElement();
		ModuleName modname = new ModuleName(getLastElement());
		if (!isPrefixed())
		{
			//return new ModuleName(modname);
			return modname;
		}
		PackageName packname = new PackageName(getPrefixElements());
		return new ModuleName(packname, modname);
	}

	/*public static ModuleName toModuleName(CompoundNameNodes ns)
	{
		String modname = ns.getLastElement().identifier;
		if (!ns.isPrefixed())
		{
			return new ModuleName(modname);
		}
		PackageName packname = new PackageName(CompoundNameNodes.getIdentifiers(ns.getPrefixElements()));
		return new ModuleName(packname, modname);
	}*/

	/*private static PrimitiveNameNode[] compileModuleNameNodes(PackageNameNodes pn, PrimitiveNameNode smn)
	{
		PrimitiveNameNode[] ns = new PrimitiveNameNode[pn.elems.length + 1];
		System.arraycopy(pn.elems, 0, ns, 0, pn.elems.length);
		ns[ns.length - 1] = smn;
		return ns;
	}*/
}
