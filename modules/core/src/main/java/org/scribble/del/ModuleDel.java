package org.scribble.del;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ImportDecl;
import org.scribble.ast.Module;
import org.scribble.ast.ModuleDecl;
import org.scribble.ast.NonProtocolDecl;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.ast.local.LProtocolHeader;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.ContextBuilder;
import org.scribble.visit.Projector;

public class ModuleDel extends ScribDelBase
{
	private ModuleContext context;
	
	public ModuleDel()
	{

	}

	private ModuleDel copy()
	{
		return new ModuleDel();
	}

	@Override
	public void enterContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder)
	{
		builder.setModuleContext(new ModuleContext(builder.getJobContext(), (Module) child));
	}

	// Maybe better to create on enter, so can be used during the context build pass (Context would need to be "cached" in the visitor to be accessed)
	@Override
	public Module leaveContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		ModuleDel del = copy();  // FIXME: should be a deep clone in principle
		del.context = builder.getModuleContext();
		return (Module) visited.del(del);
	}

	@Override
	public Module leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited)
	{
		proj.getJobContext().addProjections(proj.getProjections());
		return (Module) visited;
	}

	// lpd is the projected local protocol
	public Module createModuleForProjection(Projector proj, Module root, LProtocolDecl lpd, Map<GProtocolName, Set<Role>> deps)
	{
		ModuleNameNode modname = Projector.makeProjectedModuleNameNode(root.moddecl.getFullModuleName(), lpd.getHeader().getDeclName());
		ModuleDecl moddecl = AstFactoryImpl.FACTORY.ModuleDecl(modname);
		List<ImportDecl<? extends Kind>> imports = new LinkedList<>();
		for (GProtocolName gpn : deps.keySet())
		{
			for (Role role : deps.get(gpn))
			{
				LProtocolNameNode targetfullname = Projector.makeProjectedFullNameNode(gpn, role);
				LProtocolNameNode targetsimpname = Projector.makeProjectedSimpleNameNode(gpn.getSimpleName(), role);
				ModuleNameNode targetmodname = Projector.makeProjectedModuleNameNode(gpn.getPrefix(), targetsimpname.toName());
				if (!targetfullname.toName().getPrefix().equals(modname.toName()))
				{
					imports.add(AstFactoryImpl.FACTORY.ImportModule(targetmodname, null));
				}
			}
		}
		
		List<NonProtocolDecl<? extends Kind>> data = new LinkedList<>(root.data);  // FIXME: copy?  // FIXME: only project the dependencies
		List<ProtocolDecl<? extends ProtocolKind>> protos = Arrays.asList(lpd);
		return AstFactoryImpl.FACTORY.Module(moddecl, imports, data, protos);
	}
	
	@Override 
	public String toString()
	{
		return (this.context == null) ? null : this.context.toString();  // null before and during context building
	}
	
	public ModuleContext getModuleContext()
	{
		return this.context;
	}
}
