package org.scribble.f17.visit;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.InlinedProtocolVisitor;

// FIXME: rename like F17SyntaxChecker (and deprecate)
public class F17Parser extends InlinedProtocolVisitor<F17ParserEnv> //NoEnvInlinedProtocolVisitor
{
	public F17Parser(Job job)
	{
		super(job);
	}

	@Override
	protected F17ParserEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new F17ParserEnv();
	}

	/*@Override
	public ScribNode visitInlinedProtocol(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof LInteractionSeq)
		{
			return visitOverrideForLInteractionSeq((LProtocolBlock) parent, (LInteractionSeq) child);
		}
		else if (child instanceof LChoice)
		{
			return visitOverrideForLChoice((LInteractionSeq) parent, (LChoice) child);
		}
		else
		{
			return super.visitInlinedProtocol(parent, child);
		}
	}

	protected ScribNode visitForDisamb(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof GDelegationElem)
		{
			//return visitOverrideForDelegationElem(parent, (Do<?>) child);
			return ((GDelegationElemDel) child.del()).visitForNameDisambiguation(this, (GDelegationElem) child);
		}
		else
		{
			return child.visitChildren(this); 
		}
	}*/

	@Override
	public void inlinedEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.inlinedEnter(parent, child);
		child.del().enterF17Parsing(parent, child, this);
	}
	
	@Override
	public ScribNode inlinedLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveF17Parsing(parent, child, this, visited);
		return super.inlinedLeave(parent, child, visited);
	}
}
