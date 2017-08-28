package org.scribble.ext.go.ast;

import java.util.Collections;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ast.name.simple.SimpleNameNode;
import org.scribble.del.ScribDel;
import org.scribble.ext.go.type.index.ParamIndexVar;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.RoleKind;
import org.scribble.type.name.Role;
import org.scribble.visit.AstVisitor;

public class ParamRoleDecl extends RoleDecl
{
	//public final List<ParamRoleParamNode> params;
	public final List<ParamIndexVar> params;
	
	//public ParamRoleDecl(CommonTree source, RoleNode name, List<ParamRoleParamNode> params)
	public ParamRoleDecl(CommonTree source, RoleNode name, List<ParamIndexVar> params)  // Separating params from base RoleNode better for subprotos?
	{
		super(source, name);
		this.params = Collections.unmodifiableList(params);
	}

	@Override
	protected ParamRoleDecl copy()
	{
		return new ParamRoleDecl(this.source, (RoleNode) this.name, this.params);
	}
	
	@Override
	public ParamRoleDecl clone(AstFactory af)
	{
		RoleNode role = (RoleNode) this.name.clone(af);
		//List<ParamRoleParamNode> params = ScribUtil.cloneList(af, this.params);
		List<ParamIndexVar> params = this.params;
		return ((ParamAstFactory) af).ParamRoleDecl(this.source, role, params);
	}

	@Override
	public ParamRoleDecl project(AstFactory af, Role self)
	{
		/*RoleDecl proj = super.project(af, self);
		return reconstruct((SimpleNameNode<RoleKind>) proj.name, this.params);  // FIXME: */
		throw new RuntimeException("[param] TODO: " + this);  // Not just project, but most passes after parsing
	}

	@Override
	public ParamRoleDecl reconstruct(SimpleNameNode<RoleKind> name)
	{
		throw new RuntimeException("[param] Shouldn't get in here: " + this);
	}

	//public ParamRoleDecl reconstruct(SimpleNameNode<RoleKind> name, List<ParamRoleParamNode> params)
	public ParamRoleDecl reconstruct(SimpleNameNode<RoleKind> name, List<ParamIndexVar> params)
	{
		ScribDel del = del();
		ParamRoleDecl rd = new ParamRoleDecl(this.source, (RoleNode) name, params);
		rd = (ParamRoleDecl) rd.del(del);
		return rd;
	}
	
	@Override
	public ParamRoleDecl visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleNode name = visitChildWithClassEqualityCheck(this, (RoleNode) this.name, nv);
		//List<ParamRoleParamNode> params = visitChildListWithClassEqualityCheck(this, this.params, nv);
		List<ParamIndexVar> params = this.params;
		return reconstruct(name, params);
	}
}
