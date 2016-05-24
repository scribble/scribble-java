package org.scribble.ast.global;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Connect;
import org.scribble.ast.Constants;
import org.scribble.ast.MessageNode;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LNode;
import org.scribble.ast.local.LReceive;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribUtil;

public class GConnect extends Connect<Global> implements GSimpleInteractionNode
{
	public GConnect(RoleNode src, MessageNode msg, RoleNode dest)
	{
		super(src, msg, dest);
	}

	public LNode project(Role self)
	{
		Role srcrole = this.src.toName();
		Role destrole = this.dest.toName();
		LNode projection = null;
		if (srcrole.equals(self) || destrole.equals(self))
		{
			RoleNode src = (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, this.src.toName().toString());
			MessageNode msg = (MessageNode) this.msg;  // FIXME: need namespace prefix update?
			RoleNode dest = (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, this.dest.toName().toString());
			if (srcrole.equals(self))
			{
				projection = AstFactoryImpl.FACTORY.LConnect(src, msg, dest);
			}
			if (destrole.equals(self))
			{
				projection = AstFactoryImpl.FACTORY.LAccept(src, msg, dest);
			}
		}
		return projection;
	}


	@Override
	protected GConnect copy()
	{
		return new GConnect(this.src, this.msg, this.dest);
	}
	
	@Override
	public GConnect clone()
	{
		RoleNode src = this.src.clone();
		MessageNode msg = this.msg.clone();
		RoleNode dest = this.dest.clone();
		return AstFactoryImpl.FACTORY.GConnect(src, msg, dest);
	}

	@Override
	public GConnect reconstruct(RoleNode src, MessageNode msg, RoleNode dest)
	{
		ScribDel del = del();
		GConnect gc = new GConnect(src, msg, dest);
		gc = (GConnect) gc.del(del);
		return gc;
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Global getKind()
	{
		return GSimpleInteractionNode.super.getKind();
	}

	@Override
	public String toString()
	{
		return this.msg + " " + Constants.CONNECT_KW + " " + this.src + " " + Constants.TO_KW + " " + this.dest + ";";
	}
}
