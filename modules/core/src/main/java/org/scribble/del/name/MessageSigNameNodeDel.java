package org.scribble.del.name;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.SigKind;
import org.scribble.sesstype.name.MessageSigName;
import org.scribble.visit.NameDisambiguator;

public class MessageSigNameNodeDel extends ScribDelBase
{
	public MessageSigNameNodeDel()
	{

	}

	// Is this needed?  Or DataTypeNodes always created from AmbigNameNode? (in this same pass)
	@Override
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		if (parent instanceof MessageSigNameDecl)  // Hacky? don't want to do for decl simplenames (generally, don't do if parent is namedeclnode)
		{
			return visited;
		}
		ModuleContext mc = disamb.getModuleContext();
		MessageSigNameNode msnn = (MessageSigNameNode) visited;
		MessageSigName fullname = mc.getVisibleMessageSigNameFullName(msnn.toName());
		return (MessageSigNameNode) AstFactoryImpl.FACTORY.QualifiedNameNode(SigKind.KIND, fullname.getElements());  // Didn't keep original del
	}
}
