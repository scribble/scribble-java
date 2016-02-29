package org.scribble.del.name;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.name.simple.AmbigNameNode;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.kind.SigKind;
import org.scribble.sesstype.name.AmbigName;
import org.scribble.visit.NameDisambiguator;

public class AmbigNameNodeDel extends ScribDelBase
{
	public AmbigNameNodeDel()
	{

	}

	@Override
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		ModuleContext mcontext = disamb.getModuleContext();
		AmbigName name = ((AmbigNameNode) visited).toName();
		// By well-formedness (checked later), payload type and parameter names are distinct
		if (mcontext.isDataTypeVisible(name.toDataType()))
		{
			return AstFactoryImpl.FACTORY.QualifiedNameNode(DataTypeKind.KIND, name.getElements());
		}
		else if (mcontext.isMessageSigNameVisible(name.toMessageSigName()))
		{
			return AstFactoryImpl.FACTORY.QualifiedNameNode(SigKind.KIND, name.getElements());
		}
		else if (disamb.isBoundParameter(name))
		{
			return AstFactoryImpl.FACTORY.NonRoleParamNode(disamb.getParameterKind(name), name.toString());
		}
		//throw new ScribbleException("Cannot disambiguate name: " + name);
		else // FIXME HACK
		{
			//return AstFactoryImpl.FACTORY.QualifiedNameNode(Local.KIND, name.getElements());
			return AstFactoryImpl.FACTORY.QualifiedNameNode(DataTypeKind.KIND , name.getElements());
		}
	}
}
