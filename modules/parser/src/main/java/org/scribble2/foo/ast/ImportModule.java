package org.scribble2.foo.ast;

import org.antlr.runtime.Token;
import org.scribble2.foo.AntlrConstants;
import org.scribble2.foo.ast.name.qualified.ModuleNameNode;
import org.scribble2.foo.ast.name.simple.SimpleProtocolNameNode;

public class ImportModule extends ImportDecl
{
	public final ModuleNameNode modname;
	public final SimpleProtocolNameNode alias;

	// FIXME: make a no alias constructor
	public ImportModule(Token t, ModuleNameNode fmn, SimpleProtocolNameNode alias)
	{
		super(t);
		this.modname = fmn;
		this.alias = alias;
	}
	
	/*@Override
	public ImportModule project(Projector proj)
	{
		// Don't know target protocol
		PrimitiveNameNode pnn = new PrimitiveNameNode(null, proj.getProjectedModuleName(this.fmn.smn, ...));
		ModuleNameNode mnn = new ModuleNameNode(this.fmn.pn, pnn);
		return new ImportModule(null, mnn, this.alias);
	}*/

	/*@Override
	public ImportModule visitChildren(NodeVisitor nv) throws ScribbleException
	{
		ModuleNameNodes modname = (ModuleNameNodes) visitChild(this.modname, nv);
		if (isAliased())
		{
			SimpleProtocolNameNode alias = (SimpleProtocolNameNode) visitChild(this.alias, nv);
			return new ImportModule(this.ct, modname, alias);
		}
		return new ImportModule(this.ct, modname, null);
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
		String s = AntlrConstants.IMPORT_KW + " " + modname;
		if (isAliased())
		{
			s += " " + AntlrConstants.AS_KW + " " + this.alias;
		}
		return s + ";";
	}
	
	/*@Override
	public boolean isImportModule()
	{
		return true;
	}*/
}
