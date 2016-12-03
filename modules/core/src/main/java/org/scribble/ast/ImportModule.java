package org.scribble.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ModuleKind;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.visit.AstVisitor;

public class ImportModule extends ImportDecl<ModuleKind>
{
	public final ModuleNameNode modname;
	public final ModuleNameNode alias;  // Factor up to ImportDecl

	public ImportModule(CommonTree source, ModuleNameNode modname, ModuleNameNode alias)
	{
		super(source);
		this.modname = modname;
		this.alias = alias;
	}

	@Override
	protected ImportModule copy()
	{
		return new ImportModule(this.source, this.modname, this.alias);
	}
	
	@Override
	public ImportModule clone()
	{
		ModuleNameNode name = (ModuleNameNode) this.modname.clone();
		ModuleNameNode alias = (ModuleNameNode) this.alias.clone();
		return AstFactoryImpl.FACTORY.ImportModule(this.source, name, alias);
	}
	
	public ImportModule reconstruct(ModuleNameNode modname, ModuleNameNode alias)  // Factor up
	{
		ScribDel del = del();
		ImportModule im = new ImportModule(this.source, modname, alias);
		im = (ImportModule) im.del(del);
		return im;
	}

	@Override
	public ImportModule visitChildren(AstVisitor nv) throws ScribbleException
	{
		ModuleNameNode modname = (ModuleNameNode) visitChild(this.modname, nv);
		ModuleNameNode alias = (isAliased()) ? (ModuleNameNode) visitChild(this.alias, nv) : null;
		return reconstruct(modname, alias);
	}
	
	@Override
	public boolean isImportModule()
	{
		return true;
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
	
	@Override
	public ModuleName getAlias()
	{
		return this.alias.toName();
	}
	
	@Override
	public String toString()
	{
		String s = Constants.IMPORT_KW + " " + this.modname;
		if (isAliased())
		{
			s += " " + Constants.AS_KW + " " + this.alias;
		}
		return s + ";";
	}
}
