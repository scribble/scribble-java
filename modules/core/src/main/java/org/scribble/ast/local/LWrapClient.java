package org.scribble.ast.local;

import java.util.Collections;
import java.util.Set;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Constants;
import org.scribble.ast.MessageNode;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.global.GWrap;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

public class LWrapClient extends LConnectionAction implements LSimpleInteractionNode
{
	public LWrapClient(RoleNode src, RoleNode dest)
	{
		super(src, GWrap.UNIT_MESSAGE_SIG_NODE, dest);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LWrapClient(this.src, this.dest);
	}
	
	@Override
	public LWrapClient clone()
	{
		RoleNode src = this.src.clone();
		RoleNode dest = this.dest.clone();
		return AstFactoryImpl.FACTORY.LWrapClient(src, dest);
	}

	@Override
	public LWrapClient reconstruct(RoleNode src, MessageNode msg, RoleNode dest)
	//public LWrapClient reconstruct(RoleNode src, RoleNode dest)
	{
		ScribDel del = del();
		LWrapClient ls = new LWrapClient(src, dest);
		ls = (LWrapClient) ls.del(del);
		return ls;
	}

	// Could make a LMessageTransfer to factor this out with LReceive
	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
		return this.src.toName();
		//throw new RuntimeException("TODO: " + this);
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Local getKind()
	{
		return LSimpleInteractionNode.super.getKind();
	}

	@Override
	public String toString()
	{
		return Constants.WRAP_KW + " " + Constants.TO_KW + " " + this.dest.toString() + ";";
	}

	@Override
	public LInteractionNode merge(LInteractionNode ln) throws ScribbleException
	{
		throw new RuntimeScribbleException("Invalid merge on LWrapClient: " + this);
	}

	@Override
	public boolean canMerge(LInteractionNode ln)
	{
		return false;
	}

	@Override
	public Set<Message> getEnabling()
	{
		return Collections.emptySet();
	}
}
