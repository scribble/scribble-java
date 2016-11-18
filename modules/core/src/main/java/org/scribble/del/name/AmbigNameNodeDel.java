package org.scribble.del.name;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.MessageTransfer;
import org.scribble.ast.PayloadElem;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.name.simple.AmbigNameNode;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.kind.SigKind;
import org.scribble.sesstype.name.AmbigName;
import org.scribble.visit.wf.NameDisambiguator;

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
		// FIXME: are conflicts checked elsewhere?
		if (mcontext.isDataTypeVisible(name.toDataType()))
		{
			if (parent instanceof MessageTransfer<?>)  // FIXME HACK: MessageTransfer assumes MessageNode (cast in visitChildren), so this needs to be caught here  // FIXME: other similar cases?
			{
				throw new ScribbleException("Invalid occurrence of data type: " + parent);
			}
			return AstFactoryImpl.FACTORY.QualifiedNameNode(DataTypeKind.KIND, name.getElements());
		}
		else if (mcontext.isMessageSigNameVisible(name.toMessageSigName()))
		{
			if (parent instanceof PayloadElem)  // FIXME HACK
			{
				throw new ScribbleException("Invalid occurrence of message signature name: " + parent);
			}
			return AstFactoryImpl.FACTORY.QualifiedNameNode(SigKind.KIND, name.getElements());
		}
		else if (disamb.isBoundParameter(name))
		{
			return AstFactoryImpl.FACTORY.NonRoleParamNode(disamb.getParameterKind(name), name.toString());
		}
		throw new ScribbleException("Cannot disambiguate name: " + name);
	}
}
