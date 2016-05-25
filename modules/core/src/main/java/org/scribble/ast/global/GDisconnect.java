package org.scribble.ast.global;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ConnectionAction;
import org.scribble.ast.Constants;
import org.scribble.ast.local.LNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.Role;

public class GDisconnect extends ConnectionAction<Global> implements GSimpleInteractionNode
{
	public GDisconnect(RoleNode src, RoleNode dest)
	{
		super(src, dest);
	}

	public LNode project(Role self)
	{
		Role srcrole = this.src.toName();
		Role destrole = this.dest.toName();
		LNode projection = null;
		if (srcrole.equals(self) || destrole.equals(self))
		{
			RoleNode src = (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, this.src.toName().toString());
			RoleNode dest = (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, this.dest.toName().toString());
			if (srcrole.equals(self))
			{
				projection = AstFactoryImpl.FACTORY.LDisconnect(src, dest);
			}
			if (destrole.equals(self))
			{
				projection = AstFactoryImpl.FACTORY.LDisconnect(dest, src);
			}
		}
		return projection;
	}


	@Override
	protected GDisconnect copy()
	{
		return new GDisconnect(this.src, this.dest);
	}
	
	@Override
	public GDisconnect clone()
	{
		RoleNode src = this.src.clone();
		RoleNode dest = this.dest.clone();
		return AstFactoryImpl.FACTORY.GDisconnect(src, dest);
	}

	@Override
	public GDisconnect reconstruct(RoleNode src, RoleNode dest)
	{
		ScribDel del = del();
		GDisconnect gc = new GDisconnect(src, dest);
		gc = (GDisconnect) gc.del(del);
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
		return Constants.DISCONNECT_KW + " " + this.src + " " + Constants.TO_KW + " " + this.dest + ";";
	}
}
