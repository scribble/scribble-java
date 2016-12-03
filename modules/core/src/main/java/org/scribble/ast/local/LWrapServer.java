package org.scribble.ast.local;

import java.util.Collections;
import java.util.Set;

import org.antlr.runtime.tree.CommonTree;
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

public class LWrapServer extends LConnectionAction implements LSimpleInteractionNode
{
	public LWrapServer(CommonTree source, RoleNode src, RoleNode dest)
	{
		super(source, src, GWrap.UNIT_MESSAGE_SIG_NODE, dest);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LWrapServer(this.source, this.src, this.dest);
	}
	
	@Override
	public LWrapServer clone()
	{
		RoleNode src = this.src.clone();
		RoleNode dest = this.dest.clone();
		return AstFactoryImpl.FACTORY.LWrapServer(this.source, src, dest);
	}

	@Override
	public LWrapServer reconstruct(RoleNode src, MessageNode msg, RoleNode dest)
	//public LWrapServer reconstruct(RoleNode src, RoleNode dest)
	{
		ScribDel del = del();
		LWrapServer lr = new LWrapServer(this.source, src, dest);
		lr = (LWrapServer) lr.del(del);
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
		return Constants.WRAP_KW + " " + Constants.FROM_KW + " " + this.src + ";";
	}

	@Override
	public LInteractionNode merge(LInteractionNode ln) throws ScribbleException
	{
		throw new RuntimeScribbleException("Invalid merge on LWrapServer: " + this);
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
