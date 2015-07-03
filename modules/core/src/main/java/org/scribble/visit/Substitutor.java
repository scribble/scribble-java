package org.scribble.visit;

import java.util.Map;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.MessageSigNode;
import org.scribble.ast.NonRoleArgNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.name.qualified.DataTypeNameNode;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.ast.name.simple.NonRoleParamNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Arg;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.kind.NonRoleArgKind;
import org.scribble.sesstype.kind.NonRoleParamKind;
import org.scribble.sesstype.kind.SigKind;
import org.scribble.sesstype.name.Role;

public class Substitutor extends AstVisitor
{
	private final Map<Role, RoleNode> rolemap;
	private final Map<Arg<? extends NonRoleArgKind>, NonRoleArgNode> argmap;

	public Substitutor(Job job, Map<Role, RoleNode> rolemap, Map<Arg<? extends NonRoleArgKind>, NonRoleArgNode> argmap)
	{
		super(job);
		this.rolemap = rolemap;
		this.argmap = argmap;
	}
	
	@Override
	public ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		return visited.substituteNames(this);
	}

	public Role getRoleSubstitution(Role role)
	{
		return this.rolemap.get(role).toName();
	}

	public <K extends NonRoleArgKind> NonRoleArgNode getArgumentSubstitution(Arg<K> arg)
	{
		NonRoleArgNode an = (NonRoleArgNode) this.argmap.get(arg);
		if (an.isMessageSigNode())
		{
			MessageSigNode msn = (MessageSigNode) an;
			return (NonRoleArgNode) AstFactoryImpl.FACTORY.MessageSigNode(msn.op, msn.payloads);
		}
		else if (an.isMessageSigNameNode())
		{
			MessageSigNameNode msn = (MessageSigNameNode) an;
			return (MessageSigNameNode) AstFactoryImpl.FACTORY.QualifiedNameNode(SigKind.KIND, msn.getElements());
		}
		else if (an.isDataTypeNameNode())
		{
			DataTypeNameNode dtn = (DataTypeNameNode) an;
			return (DataTypeNameNode) AstFactoryImpl.FACTORY.QualifiedNameNode(DataTypeKind.KIND, dtn.getElements());
		}
		else if (an.isParamNode())
		{
			NonRoleParamNode<? extends NonRoleParamKind> pn = (NonRoleParamNode<?>) an;
			return AstFactoryImpl.FACTORY.NonRoleParamNode(pn.kind, pn.getIdentifier());
		}
		else
		{
			throw new RuntimeException("TODO: " + arg);
		}
	}
}
