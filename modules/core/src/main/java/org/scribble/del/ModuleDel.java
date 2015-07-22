package org.scribble.del;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ImportDecl;
import org.scribble.ast.Module;
import org.scribble.ast.ModuleDecl;
import org.scribble.ast.NonProtocolDecl;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.ModuleContextBuilder;
import org.scribble.visit.NameDisambiguator;
import org.scribble.visit.Projector;

public class ModuleDel extends ScribDelBase
{
	private ModuleContext mcontext;
	
	public ModuleDel()
	{

	}

	private ModuleDel copy()
	{
		return new ModuleDel();
	}

	@Override
	public void enterModuleContextBuilding(ScribNode parent, ScribNode child, ModuleContextBuilder builder) throws ScribbleException
	{
		builder.setModuleContext(new ModuleContext(builder.getJobContext(), (Module) child));
	}

	// Maybe better to create on enter, so can be used during the context build pass (Context would need to be "cached" in the visitor to be accessed)
	@Override
	public Module leaveModuleContextBuilding(ScribNode parent, ScribNode child, ModuleContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		ModuleDel del = setModuleContext(builder.getModuleContext());
		return (Module) visited.del(del);
	}
		
	@Override
	public Module leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		Module mod = (Module) visited;
		// Imports checked in ModuleContext -- that is built before disamb is run
		List<NonProtocolDecl<?>> npds = mod.getNonProtocolDecls();
		List<String> npdnames = npds.stream().map((npd) -> npd.getDeclName().toString()).collect(Collectors.toList()); 
				// Have to use Strings, as can be different kinds (datatype, sig)
		if (npds.size() != npdnames.stream().distinct().count())
		{
			throw new ScribbleException("Duplicate non-protocol decls: " + npdnames);
		}
		List<ProtocolDecl<?>> pds = mod.getProtocolDecls();
		List<String> pdnames = pds.stream().map((pd) -> pd.header.getDeclName().toString()).collect(Collectors.toList());
				// Have to use Strings, as can be different kinds (global, local)
		if (pds.size() != pdnames.stream().distinct().count())
		{
			throw new ScribbleException("Duplicate protocol decls: " + pdnames);  // Global and locals also required to be distinct
		}
		return mod;
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
		List<ImportDecl<?>> imports = new LinkedList<>();
		for (GProtocolName gpn : deps.keySet())
		{
			for (Role role : deps.get(gpn))
			{
				LProtocolName targetsimpname = Projector.projectSimpleProtocolName(gpn.getSimpleName(), role);
				ModuleNameNode targetmodname = Projector.makeProjectedModuleNameNode(gpn.getPrefix(), targetsimpname);
				if (!targetmodname.toName().equals(modname.toName()))  // Self dependency -- each projected local is in its own module now, so can compare module names
				{
					imports.add(AstFactoryImpl.FACTORY.ImportModule(targetmodname, null));
				}
			}
		}
		
		List<NonProtocolDecl<?>> data = new LinkedList<>(root.getNonProtocolDecls());  // FIXME: copy?  // FIXME: only project the dependencies
		List<ProtocolDecl<?>> protos = Arrays.asList(lpd);
		return AstFactoryImpl.FACTORY.Module(moddecl, imports, data, protos);
	}
	
	@Override 
	public String toString()
	{
		return (this.mcontext == null) ? null : this.mcontext.toString();  // null before and during context building
	}
	
	public ModuleContext getModuleContext()
	{
		return this.mcontext;
	}
	
	protected ModuleDel setModuleContext(ModuleContext mcontext)
	{
		ModuleDel copy = copy();  // FIXME: should be a deep clone in principle
		copy.mcontext = mcontext;
		return copy;
	}
}
