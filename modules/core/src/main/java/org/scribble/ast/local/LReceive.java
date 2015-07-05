package org.scribble.ast.local;

import java.util.List;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Constants;
import org.scribble.ast.MessageNode;
import org.scribble.ast.MessageTransfer;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribUtil;
import org.scribble.visit.ProjectedChoiceSubjectFixer;

public class LReceive extends MessageTransfer<Local> implements LSimpleInteractionNode
{
	public LReceive(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		super(src, msg, dests);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LReceive(this.src, this.msg, this.dests);
	}
	
	@Override
	public LReceive clone()
	{
		RoleNode src = this.src.clone();
		MessageNode msg = this.msg.clone();
		List<RoleNode> dests = ScribUtil.cloneList(this.dests);
		return AstFactoryImpl.FACTORY.LReceive(src, msg, dests);
	}

	@Override
	protected LReceive reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		ScribDel del = del();
		LReceive lr = new LReceive(src, msg, dests);
		lr = (LReceive) lr.del(del);
		return lr;
	}

	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
		return this.src.toName();
	}

	@Override
	public String toString()
	{
		return this.msg + " " + Constants.FROM_KW + " " + this.src + ";";
	}
}
