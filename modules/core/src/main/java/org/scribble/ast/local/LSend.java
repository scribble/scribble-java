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


public class LSend extends MessageTransfer<Local> implements LSimpleInteractionNode
{
	public LSend(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		super(src, msg, dests);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LSend(this.src, this.msg, this.dests);
	}
	
	@Override
	public LSend clone()
	{
		RoleNode src = this.src.clone();
		MessageNode msg = this.msg.clone();
		List<RoleNode> dests = ScribUtil.cloneList(this.dests);
		return AstFactoryImpl.FACTORY.LSend(src, msg, dests);
	}

	@Override
	public LSend reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		ScribDel del = del();
		LSend ls = new LSend(src, msg, dests);
		ls = (LSend) ls.del(del);
		return ls;
	}
	
	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
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
		String s = this.msg + " " + Constants.TO_KW + " " + this.dests.get(0);
		for (RoleNode dest : this.dests.subList(1, this.dests.size()))
		{
			s += ", " + dest;
		}
		return s + ";";
	}
}
