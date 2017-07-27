/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.visit.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.Local;
import org.scribble.type.kind.ModuleKind;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.LProtocolName;
import org.scribble.type.name.ModuleName;
import org.scribble.type.name.Role;
import org.scribble.visit.EnvVisitor;
import org.scribble.visit.context.env.ProjectionEnv;

// Uses visitor infrastructure to traverse AST and generate local nodes from global with original nodes unchanged (so does not use normal visitChildren pattern -- env used to pass the working projections)
public class Projector extends EnvVisitor<ProjectionEnv>
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
	protected final void envEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.envEnter(parent, child);
		child.del().enterProjection(parent, child, this);
	}
	
	@Override
	protected ScribNode envLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveProjection(parent, child, this, visited);
		return super.envLeave(parent, child, visited);
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
	
	public static LProtocolNameNode makeProjectedSimpleNameNode(AstFactory af, CommonTree source, GProtocolName simpname, Role role)
	{
		return (LProtocolNameNode) af.QualifiedNameNode(source, Local.KIND, projectSimpleProtocolName(simpname, role).toString());
	}

	public static LProtocolNameNode makeProjectedFullNameNode(AstFactory af, CommonTree source, GProtocolName fullname, Role role)
	{
		return (LProtocolNameNode) af.QualifiedNameNode(source, Local.KIND, projectFullProtocolName(fullname, role).getElements());
	}

	// fullname is the un-projected name; localname is the already projected simple name
	public static ModuleNameNode makeProjectedModuleNameNode(AstFactory af, CommonTree source, ModuleName fullname, LProtocolName localname)
	{
		return (ModuleNameNode) af.QualifiedNameNode(source, ModuleKind.KIND, projectModuleName(fullname, localname).getElements());
	}
	
	// Returns true if should ignore for projection
	/*public static boolean prune(LProtocolBlock block)
	{
		if (block.isEmpty())
		{
			return true;
		}
		List<? extends LInteractionNode> lis = block.getInteractionSeq().getInteractions();
		if (lis.size() > 1)
		{
			return false;
		}
		else //if (lis.size() == 1)
		{
			LInteractionNode lin = lis.get(0);
			if (lin instanceof LContinue)
			{
				return true;
			}
			else if (lin instanceof MessageTransfer<?>)
			{
				return false;
			}
			else
			{
				if (lin instanceof LChoice)
				{
					for (LProtocolBlock b : ((LChoice) lin).getBlocks())
					{
						if (!prune(b))
						{
							return false;
						}
					}
					return true;
				}
				else if (lin instanceof LRecursion)
				{
					return prune(((LRecursion) lin).getBlock());
				}
				else
				{
					throw new RuntimeException("TODO: " + lin);
				}
			}
		}
	}*/
}
