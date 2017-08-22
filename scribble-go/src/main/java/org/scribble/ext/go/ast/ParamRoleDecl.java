package org.scribble.ext.go.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ast.name.simple.SimpleNameNode;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.RoleKind;
import org.scribble.type.name.Role;
import org.scribble.visit.AstVisitor;

public class ParamRoleDecl extends RoleDecl
{
	public final int start;
	public final int end;
	
	public ParamRoleDecl(CommonTree source, RoleNode name, int start, int end)
	{
		super(source, name);
		this.start = start;
		this.end = end;
	}

	@Override
	protected ParamRoleDecl copy()
	{
		return new ParamRoleDecl(this.source, (RoleNode) this.name, this.start, this.end);
	}
	
	@Override
	public ParamRoleDecl clone(AstFactory af)
	{
		RoleNode role = (RoleNode) this.name.clone(af);
		return ((ParamAstFactory) af).ParamRoleDecl(this.source, role, this.start, this.end);
	}

	@Override
	public ParamRoleDecl project(AstFactory af, Role self)
	{
		/*RoleDecl proj = super.project(af, self);
		return reconstruct((SimpleNameNode<RoleKind>) proj.name, this.start, this.end);*/
		throw new RuntimeException("[param] TODO: " + this);  // Not just project, but most passes after parsing
	}

	@Override
	public ParamRoleDecl reconstruct(SimpleNameNode<RoleKind> name)
	{
		throw new RuntimeException("[param] Shouldn't get in here: " + this);
	}

	public ParamRoleDecl reconstruct(SimpleNameNode<RoleKind> name, int start, int end)
	{
		ScribDel del = del();
		ParamRoleDecl rd = new ParamRoleDecl(this.source, (RoleNode) name, start, end);
		rd = (ParamRoleDecl) rd.del(del);
		return rd;
	}
	
	@Override
	public ParamRoleDecl visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleNode name = visitChildWithClassEqualityCheck(this, (RoleNode) this.name, nv);
		return reconstruct(name, this.start, this.end);
	}
}
