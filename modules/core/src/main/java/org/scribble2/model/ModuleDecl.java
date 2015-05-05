package org.scribble2.model;

import org.scribble2.model.name.KindedNameNode;
import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.sesstype.kind.ModuleKind;
import org.scribble2.sesstype.name.KindedName;
import org.scribble2.util.ScribbleException;

public class ModuleDecl extends NameDeclNode<ModuleKind>//NameDeclNode<ModuleNameNode, ModuleName>
{
	//public final ModuleNameNode fullmodname;
	public final KindedNameNode<ModuleKind> fullmodname;

	//public ModuleDecl(ModuleNameNode fullmodname)
	public ModuleDecl(KindedNameNode<ModuleKind> fullmodname)
	{
		this.fullmodname = fullmodname;
	}

	@Override
	public ModuleDecl visitChildren(ModelVisitor nv) throws ScribbleException
	{
		//ModuleNameNode fullmodname = (ModuleNameNode) visitChild(this.fullmodname, nv);
		//KindedNameNode<ModuleKind> fullmodname = visitChildWithClassCheck(this, this.fullmodname, nv);
		KindedNameNode<ModuleKind> fullmodname = KindedNameNode.castKindedNameNode(ModuleKind.KIND, visitChild(this.fullmodname, nv));
		return ModelFactoryImpl.FACTORY.ModuleDecl(fullmodname);
	}

	@Override
	public String toString()
	{
		return Constants.MODULE_KW + " " + this.fullmodname + ";";
	}

	@Override
	protected ModuleDecl copy()
	{
		return new ModuleDecl(this.fullmodname);
	}

	@Override
	//public ModuleName toName()
	public KindedName<ModuleKind> toName()
	{
		return this.fullmodname.toName();
	}
}
