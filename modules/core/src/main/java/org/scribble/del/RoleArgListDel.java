package org.scribble.del;

import java.util.List;

import org.scribble.ast.Do;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.JobContext;
import org.scribble.visit.NameDisambiguator;

public class RoleArgListDel extends ScribDelBase
{
	public RoleArgListDel()
	{

	}

	// Doing in leave allows the arguments to be individually checked first
	// Not needed for NonRoleArgList
	@Override
	public RoleArgList leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		RoleArgList ral = (RoleArgList) visited;
		List<Role> roles = ral.getRoles();
		//if (roles.size() != new HashSet<>(roles).size())
		if (roles.size() != roles.stream().distinct().count())
		{
			throw new ScribbleException("Duplicate role args: " + roles);
		}

		JobContext jc = disamb.getJobContext();
		ModuleContext mc = disamb.getModuleContext();
		Do<?> doo = (Do<?>) parent;
		ProtocolName<?> fullname = mc.getFullProtocolDeclNameFromVisible(doo.proto.toName());  // Lookup in visible names -- not deps, because do target name not disambiguated yet (will be done later this pass)
		ProtocolDecl<?> pd = jc.getModule(fullname.getPrefix()).getProtocolDecl(fullname.getSimpleName());
		if (pd.header.roledecls.getRoles().size() != roles.size())
		{
			throw new ScribbleException("Role arity mismatch for " + pd.header + ": " + roles);
		}

		return ral;
	}
}
