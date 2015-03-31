package org.scribble2.model;

import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.name.qualified.ModuleNameNode;
import org.scribble2.model.name.simple.SimpleProtocolNameNode;
import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.util.ScribbleException;

public class ImportModule extends ImportDecl
{
	public final ModuleNameNode modname;
	public final SimpleProtocolNameNode alias;

	// FIXME: make a no alias constructor
	public ImportModule(ModuleNameNode modname, SimpleProtocolNameNode alias)
	{
		this.modname = modname;
		this.alias = alias;
	}
	
	protected ImportModule reconstruct(ModuleNameNode modname, SimpleProtocolNameNode alias)
	{
		ModelDelegate del = del();
		ImportModule im = new ImportModule(modname, alias);
		im = (ImportModule) im.del(del);
		return im;
	}

	@Override
	public ImportModule visitChildren(ModelVisitor nv) throws ScribbleException
	{
		ModuleNameNode modname = (ModuleNameNode) visitChild(this.modname, nv);
		if (isAliased())
		{
			SimpleProtocolNameNode alias = (SimpleProtocolNameNode) visitChild(this.alias, nv);
			//return new ImportModule(this.ct, modname, alias);
			return reconstruct(modname, alias);
		}
		//return new ImportModule(this.ct, modname, null);
		return reconstruct(modname, null);
	}
	
	/*@Override
	public ImportModule project(Projector proj)
	{
		// Don't know target protocol
		PrimitiveNameNode pnn = new PrimitiveNameNode(null, proj.getProjectedModuleName(this.fmn.smn, ...));
		ModuleNameNode mnn = new ModuleNameNode(this.fmn.pn, pnn);
		return new ImportModule(null, mnn, this.alias);
	}*/
	
	@Override
	public boolean isAliased()
	{
		return this.alias != null;
	}
	
	/*@Override
	public ModuleName getVisibleName()
	{
		return isAliased() ? getAlias() : this.modname.toName();
	}
	
	@Override
	public ModuleName getAlias()
	{
		return new ModuleName(this.alias.identifier);
	}*/
	
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
	protected ModelNodeBase copy()
	{
		return new ImportModule(this.modname, this.alias);
	}
	
	/*@Override
	public boolean isImportModule()
	{
		return true;
	}*/
}
