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

public class GWrap extends ConnectionAction<Global> implements GSimpleInteractionNode
{
	public GWrap(RoleNode src, RoleNode dest)
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
				projection = AstFactoryImpl.FACTORY.LWrapClient(src, dest);  // src and dest (not self and peer)
			}
			if (destrole.equals(self))
			{
				projection = AstFactoryImpl.FACTORY.LWrapServer(src, dest);
			}
		}
		return projection;
	}

	@Override
	protected GWrap copy()
	{
		//return new GConnect(this.src, this.msg, this.dest);
		return new GWrap(this.src, this.dest);
	}
	
	@Override
	public GWrap clone()
	{
		RoleNode src = this.src.clone();
		RoleNode dest = this.dest.clone();
		return AstFactoryImpl.FACTORY.GWrap(src, dest);
	}

	@Override
	public GWrap reconstruct(RoleNode src, RoleNode dest)
	{
		ScribDel del = del();
		GWrap gc = new GWrap(src, dest);
		gc = (GWrap) gc.del(del);
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
		return Constants.WRAP_KW + " " + this.src + " " + Constants.TO_KW + " " + this.dest + ";";
	}
}
