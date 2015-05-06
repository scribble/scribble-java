package org.scribble2.model;

import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.name.KindedNameNode;
import org.scribble2.model.name.SimpleKindedNameNode;
import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.sesstype.kind.ModuleKind;
import org.scribble2.util.ScribbleException;

public class ImportModule extends ImportDecl
{
	/*public final ModuleNameNode modname;
	public final SimpleProtocolNameNode alias;*/
	public final KindedNameNode<ModuleKind> modname;
	public final SimpleKindedNameNode<ModuleKind> alias;

	// FIXME: make a no alias constructor
	//public ImportModule(ModuleNameNode modname, SimpleProtocolNameNode alias)  // FIXME: protocol name?
	public ImportModule(KindedNameNode<ModuleKind> modname, SimpleKindedNameNode<ModuleKind> alias)
	{
		this.modname = modname;
		this.alias = alias;
	}

	@Override
	protected ImportModule copy()
	{
		return new ImportModule(this.modname, this.alias);
	}
	
	//protected ImportModule reconstruct(ModuleNameNode modname, SimpleProtocolNameNode alias)
	protected ImportModule reconstruct(KindedNameNode<ModuleKind> modname, SimpleKindedNameNode<ModuleKind> alias)
	{
		ModelDelegate del = del();
		ImportModule im = new ImportModule(modname, alias);
		im = (ImportModule) im.del(del);
		return im;
	}

	@Override
	public ImportModule visitChildren(ModelVisitor nv) throws ScribbleException
	{
		/*ModuleNameNode modname = (ModuleNameNode) visitChild(this.modname, nv);
		SimpleProtocolNameNode alias = null;*/
		//KindedNameNode<ModuleKind> modname = KindedNameNode.castKindedNameNode(ModuleKind.KIND, visitChild(this.modname, nv));
		KindedNameNode<ModuleKind> modname = KindedNameNode.castKindedNameNode(this.modname, visitChild(this.modname, nv));
		//SimpleProtocolNameNode alias = null;
		SimpleKindedNameNode<ModuleKind> alias = null;
		if (isAliased())
		{
			//alias = (SimpleProtocolNameNode) visitChild(this.alias, nv);
			//alias = SimpleKindedNameNode.castSimpleKindedNameNode(ModuleKind.KIND, visitChild(this.alias, nv));
			alias = KindedNameNode.castKindedNameNode(this.alias, visitChild(this.alias, nv));
			////return new ImportModule(this.ct, modname, alias);
		}
		//return new ImportModule(this.ct, modname, null);
		return reconstruct(modname, alias);
	}
	
	@Override
	public boolean isAliased()
	{
		return this.alias != null;
	}
	
	/*@Override
	public ModuleName getVisibleName()
	{
		return isAliased() ? getAlias() : this.modname.toName();
	}*/
	
	//@Override
	//public ModuleName getModuleNameAlias()
	public SimpleKindedNameNode<ModuleKind> getModuleNameAlias()
	{
		//return new ModuleName(this.alias.identifier);
		return new SimpleKindedNameNode<>(ModuleKind.KIND, this.alias.identifier);
	}
	
	@Override
	public String toString()
	{
		String s = Constants.IMPORT_KW + " " + modname;
		if (isAliased())
		{
			s += " " + Constants.AS_KW + " " + this.alias;
		}
		return s + ";";
	}
	
	@Override
	public boolean isImportModule()
	{
		return true;
	}
}
