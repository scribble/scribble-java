package org.scribble.ext.go.ast;

import java.util.Collections;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ast.name.simple.SimpleNameNode;
import org.scribble.del.ScribDel;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.RoleKind;
import org.scribble.type.name.Role;
import org.scribble.visit.AstVisitor;

public class RPRoleDecl extends RoleDecl
{
	//public final List<ParamRoleParamNode> params;
	public final List<RPIndexVar> params;
	
	public RPRoleDecl(CommonTree source, RoleNode name)
	{
		this(source, name, Collections.emptyList());
	}

	//public ParamRoleDecl(CommonTree source, RoleNode name, List<ParamRoleParamNode> params)
	public RPRoleDecl(CommonTree source, RoleNode name, List<RPIndexVar> params)  // Separating params from base RoleNode better for subprotos?
	{
		super(source, name);
		this.params = Collections.unmodifiableList(params);
	}

	@Override
	protected RPRoleDecl copy()
	{
		return new RPRoleDecl(this.source, (RoleNode) this.name, this.params);
	}
	
	@Override
	public RPRoleDecl clone(AstFactory af)
	{
		RoleNode role = (RoleNode) this.name.clone(af);
		//List<ParamRoleParamNode> params = ScribUtil.cloneList(af, this.params);
		List<RPIndexVar> params = this.params;
		return ((RPAstFactory) af).ParamRoleDecl(this.source, role, params);
	}

	@Override
	public RPRoleDecl project(AstFactory af, Role self)
	{
		/*RoleDecl proj = super.project(af, self);
		return reconstruct((SimpleNameNode<RoleKind>) proj.name, this.params);  // FIXME: */
		throw new RuntimeException("[param] TODO: " + this);  // Not just project, but most passes after parsing
	}

	@Override
	public RPRoleDecl reconstruct(SimpleNameNode<RoleKind> name)
	{
		throw new RuntimeException("[param] Shouldn't get in here: " + this);
	}

	//public ParamRoleDecl reconstruct(SimpleNameNode<RoleKind> name, List<ParamRoleParamNode> params)
	public RPRoleDecl reconstruct(SimpleNameNode<RoleKind> name, List<RPIndexVar> params)
	{
		ScribDel del = del();
		RPRoleDecl rd = new RPRoleDecl(this.source, (RoleNode) name, params);
		rd = (RPRoleDecl) rd.del(del);
		return rd;
	}
	
	@Override
	public RPRoleDecl visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleNode name = visitChildWithClassEqualityCheck(this, (RoleNode) this.name, nv);
		//List<ParamRoleParamNode> params = visitChildListWithClassEqualityCheck(this, this.params, nv);
		List<RPIndexVar> params = this.params;
		return reconstruct(name, params);
	}
}
