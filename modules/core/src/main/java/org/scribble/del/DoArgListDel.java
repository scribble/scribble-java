package org.scribble.del;

import java.util.List;

import org.scribble.ast.Do;
import org.scribble.ast.DoArgList;
import org.scribble.ast.HeaderParamDeclList;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.visit.JobContext;
import org.scribble.visit.NameDisambiguator;

public abstract class DoArgListDel extends ScribDelBase
{
	public DoArgListDel()
	{

	}

	// Doing in leave allows the arguments to be individually checked first
	@Override
	public DoArgList<?> leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		DoArgList<?> dal = (DoArgList<?>) visited;
		List<?> args = dal.getDoArgs();
		ProtocolDecl<?> pd = getTargetProtocolDecl((Do<?>) parent, disamb);
		if (args.size() != getParamDeclList(pd).getDecls().size())
		{
			throw new ScribbleException("Do arity mismatch for " + pd.header + ": " + args);
		}

		return dal;
	}

	// Not using Do#getTargetProtocolDecl, because that currently relies on namedisamb pass to convert targets to fullnames (because it just gets the full name dependency, it doesn't do visible name resolution)
	protected ProtocolDecl<?> getTargetProtocolDecl(Do<?> parent, NameDisambiguator disamb) throws ScribbleException
	{
		ModuleContext mc = disamb.getModuleContext();
		JobContext jc = disamb.getJobContext();
		Do<?> doo = (Do<?>) parent;
		ProtocolName<?> pn = doo.proto.toName();
		/*if (!mc.isVisibleProtocolDeclName(simpname))  // FIXME: should be checked somewhere else?  earlier (do-entry?) -- done
		{
			throw new ScribbleException("Protocol decl not visible: " + simpname);
		}*/
		ProtocolName<?> fullname = mc.getVisibleProtocolDeclFullName(pn);  // Lookup in visible names -- not deps, because do target name not disambiguated yet (will be done later this pass)
		return jc.getModule(fullname.getPrefix()).getProtocolDecl(pn.getSimpleName());
	}
	
	protected abstract HeaderParamDeclList<?> getParamDeclList(ProtocolDecl<?> pd);
}
