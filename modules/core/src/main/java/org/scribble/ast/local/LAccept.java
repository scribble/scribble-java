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

public class LAccept extends ConnectionAction<Local> implements LSimpleInteractionNode
{
	public LAccept(RoleNode src, MessageNode msg, RoleNode dest)
	//public LAccept(RoleNode src, RoleNode dest)
	{
		super(src, msg, dest);
		//super(src, dest);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LAccept(this.src, this.msg, this.dest);
		//return new LAccept(this.src, this.dest);
	}
	
	@Override
	public LAccept clone()
	{
		RoleNode src = this.src.clone();
		MessageNode msg = this.msg.clone();
		RoleNode dest = this.dest.clone();
		return AstFactoryImpl.FACTORY.LAccept(src, msg, dest);
		//return AstFactoryImpl.FACTORY.LAccept(src, dest);
	}

	@Override
	public LAccept reconstruct(RoleNode src, MessageNode msg, RoleNode dest)
	//public LAccept reconstruct(RoleNode src, RoleNode dest)
	{
		ScribDel del = del();
		LAccept lr = new LAccept(src, msg, dest);
		//LAccept lr = new LAccept(src, dest);
		lr = (LAccept) lr.del(del);
		return lr;
	}

	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
		fixer.setChoiceSubject(this.src.toName());
		return this.src.toName();
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
		return (isUnitMessage() ? "" : this.msg + " ") + Constants.ACCEPT_KW + " " + this.src + ";";
	}

	@Override
	public LInteractionNode merge(LInteractionNode ln) throws ScribbleException
	{
		throw new RuntimeScribbleException("Invalid merge on LAccept: " + this);
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
