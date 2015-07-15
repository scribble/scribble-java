package org.scribble.visit;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.kind.ModuleKind;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.env.ProjectionEnv;

// FIXME: shouldn't be offset visitor -- currently does not match fsm builder
// Uses visitor infrastructure to traverse AST and generate local nodes from global with original nodes unchanged (so does not use normal visitChildren pattern -- env used to pass the working projections)
// uses envs but does not need to be a SubProtocolVisitor -- swap env and subprotocol visitors in hierarchy? Maybe not: e.g. GraphBuilder is a subprotocol visitor but not an env visitor -- maybe not any more, more like projector (uses pre-built dependencies)
//public class Projector extends OffsetSubprotocolVisitor<ProjectionEnv>
public class Projector extends SubprotocolVisitor<ProjectionEnv>
{
	private Stack<Role> selfs = new Stack<>();  // Is a stack needed? roles only pushed from GlobalProtocolDecl, which should be only done once at the root?
	
	// protocol name is full global protocol name
	private Map<GProtocolName, Map<Role, Module>> projections = new HashMap<>();
	
	public Projector(Job job)
	{
		super(job);
	}

	@Override
	protected ProjectionEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new ProjectionEnv();
	}

	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		// "Override" SubprotocolVisitor visitChildrenInSubprotocols pattern, avoids adding visitForProjection to every ModelNode
		// Or else need to build in a "visit override" mechanism into the parent visitors
		if (child instanceof GProtocolDecl)
		{
			return visitOverrideForGProtocolDecl((Module) parent, (GProtocolDecl) child);
		}
		else
		{
			return super.visit(parent, child);
		}
	}
	
  // Important: projection should not follow the subprotocol visiting pattern for do's -- projection uses some name mangling, which isn't compatible with subprotocol visitor name maps
	@Override
	//protected ScribNode visitForOffsetSubprotocols(ScribNode parent, ScribNode child) throws ScribbleException
	protected ScribNode visitForSubprotocols(ScribNode parent, ScribNode child) throws ScribbleException
	{
		return child.visitChildren(this);
	}

	// Projector uses this to "override" the base SubprotocolVisitor visitChildrenInSubprotocols pattern
	// Better to be in the visitor than in the del for visibility of visitor enter/leave -- also localises special visiting pattern inside the visitor, while keeping the del enter/leave methods uniform (e.g. GlobalProtocolDeclDelegate enter/leave relies on the same peekSelf API as for other nodes)
	protected GProtocolDecl visitOverrideForGProtocolDecl(Module parent, GProtocolDecl child) throws ScribbleException
	{
		for (Role self : child.header.roledecls.getRoles())
		{
			pushSelf(self);
			enter(parent, child);
			GProtocolDecl visited = (GProtocolDecl) child.visitChildren(this);  // enter/leave around visitChildren for this GlobalProtocolDecl done above -- cf. SubprotocolVisitor.visit
			visited = (GProtocolDecl) leave(parent, child, visited);  // projection will not change original global protocol (visited can be discarded)
			popSelf();
		}
		return child;
	}
	
	@Override
	//protected final void offsetSubprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	protected final void subprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		//super.offsetSubprotocolEnter(parent, child);
		super.subprotocolEnter(parent, child);
		child.del().enterProjection(parent, child, this);
	}
	
	@Override
	//protected ScribNode offsetSubprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	protected ScribNode subprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveProjection(parent, child, this, visited);
		//return super.offsetSubprotocolLeave(parent, child, visited);
		return super.subprotocolLeave(parent, child, visited);
	}

	public void pushSelf(Role self)
	{
		this.selfs.push(self);
	}
	
	public Role peekSelf()
	{
		return this.selfs.peek();
	}
	
	public Role popSelf()
	{
		return this.selfs.pop();
	}
	
	// gpn is full global protocol name, mod is a module with the single local protocol projection
	public void addProjection(GProtocolName gpn, Role role, Module mod)
	{
		Map<Role, Module> tmp = this.projections.get(gpn);
		if (tmp == null)
		{
			tmp = new HashMap<>();
			this.projections.put(gpn, tmp);
		}
		tmp.put(role, mod);
	}

	public Map<GProtocolName, Map<Role, Module>> getProjections()
	{
		return this.projections;
	}
	
	public static LProtocolName projectSimpleProtocolName(GProtocolName simpname, Role role)
	{
		return new LProtocolName(simpname.toString() + "_" + role.toString());
	}

	// Role is the target subprotocol parameter (not the current projector self -- actually the self just popped)
	public static LProtocolName projectFullProtocolName(GProtocolName fullname, Role role)
	{
		LProtocolName simplename = projectSimpleProtocolName(fullname.getSimpleName(), role);
		ModuleName modname = projectModuleName(fullname.getPrefix(), simplename);
		return new LProtocolName(modname, simplename);
	}

	// fullname is the un-projected name; localname is the already projected simple name
	public static ModuleName projectModuleName(ModuleName fullname, LProtocolName localname)
	{
		ModuleName simpname = new ModuleName(fullname.getSimpleName().toString() + "_" + localname.toString());
		return new ModuleName(fullname.getPrefix(), simpname);  // Supports unary fullname
	}
	
	public static LProtocolNameNode makeProjectedSimpleNameNode(GProtocolName simpname, Role role)
	{
		return (LProtocolNameNode)
				AstFactoryImpl.FACTORY.QualifiedNameNode(Local.KIND, projectSimpleProtocolName(simpname, role).toString());
	}

	public static LProtocolNameNode makeProjectedFullNameNode(GProtocolName fullname, Role role)
	{
		return (LProtocolNameNode)
				AstFactoryImpl.FACTORY.QualifiedNameNode(Local.KIND, projectFullProtocolName(fullname, role).getElements());
	}

	// fullname is the un-projected name; localname is the already projected simple name
	public static ModuleNameNode makeProjectedModuleNameNode(ModuleName fullname, LProtocolName localname)
	{
		return (ModuleNameNode)
				AstFactoryImpl.FACTORY.QualifiedNameNode(ModuleKind.KIND, projectModuleName(fullname, localname).getElements());
	}
}
