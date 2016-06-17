package org.scribble.ast.local;

import java.util.Collections;
import java.util.Set;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ConnectionAction;
import org.scribble.ast.Constants;
import org.scribble.ast.MessageNode;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.ProjectedChoiceSubjectFixer;

public class LConnect extends ConnectionAction<Local> implements LSimpleInteractionNode
{
	public LConnect(RoleNode src, MessageNode msg, RoleNode dest)
	//public LConnect(RoleNode src, RoleNode dest)
	{
		super(src, msg, dest);
		//super(src, dest);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LConnect(this.src, this.msg, this.dest);
		//return new LConnect(this.src, this.dest);
	}
	
	@Override
	public LConnect clone()
	{
		RoleNode src = this.src.clone();
		MessageNode msg = this.msg.clone();
		RoleNode dest = this.dest.clone();
		return AstFactoryImpl.FACTORY.LConnect(src, msg, dest);
		//return AstFactoryImpl.FACTORY.LConnect(src, dest);
	}

	@Override
	public LConnect reconstruct(RoleNode src, MessageNode msg, RoleNode dest)
	//public LConnect reconstruct(RoleNode src, RoleNode dest)
	{
		ScribDel del = del();
		LConnect ls = new LConnect(src, msg, dest);
		//LConnect ls = new LConnect(src, dest);
		ls = (LConnect) ls.del(del);
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
		return (isUnitMessage() ? "" : this.msg+ " ") + Constants.CONNECT_KW + " " + this.dest.toString() + ";";
	}

	@Override
	public LInteractionNode merge(LInteractionNode ln) throws ScribbleException
	{
		throw new RuntimeScribbleException("Invalid merge on LConnect: " + this);
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
