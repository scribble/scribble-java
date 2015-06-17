package org.scribble.ast.visit;

import java.util.Map;

import org.scribble.ast.ArgNode;
import org.scribble.ast.MessageSigNode;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.name.qualified.DataTypeNameNode;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.ast.name.simple.NonRoleParamNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.sesstype.Arg;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.SigKind;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribbleException;

public class Substitutor extends ModelVisitor
{
	private final Map<Role, RoleNode> rolemap;
	//private final Map<Argument, ArgumentNode> argmap;
	private final Map<Arg<? extends Kind>, ArgNode> argmap;

	public Substitutor(Job job, Map<Role, RoleNode> rolemap, Map<Arg<? extends Kind>, ArgNode> argmap)
	{
		super(job);
		this.rolemap = rolemap;
		this.argmap = argmap;
	}
	
	@Override
	//public ModelNode leave(ModelNode parent, ModelNode child, ModelVisitor nv, ModelNode visited) throws ScribbleException
	public ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		//return visited.substituteNames((Substitutor) nv);
		return visited.substituteNames(this);
		//return visited.del().substituteNames((Substitutor) nv, visited);
	}

	public Role getRoleSubstitution(Role role)
	{
		return this.rolemap.get(role).toName();
	}

	/*public RoleNode getRoleSubstitution(Role role)
	{
		RoleNode rn = this.rolemap.get(role);
		return new RoleNode(rn.toName().toString());
	}*/

	//public ArgumentNode getArgumentSubstitution(Argument arg)
	public <K extends Kind> ArgNode getArgumentSubstitution(Arg<K> arg)
	{
		ArgNode an = (ArgNode) this.argmap.get(arg);
		if (an.isMessageSigNode())
		{
			MessageSigNode msn = (MessageSigNode) an;
			//return new MessageSigNode(msn.op, msn.payload);  // FIXME: use factory
			return (ArgNode) AstFactoryImpl.FACTORY.MessageSigNode(msn.op, msn.payload);
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
			//return Substitutor.copyParameterNode((ParameterNode<K>) an);
			NonRoleParamNode<K> pn = (NonRoleParamNode<K>) an;
			return AstFactoryImpl.FACTORY.NonRoleParamNode(pn.kind, pn.getIdentifier());
		}
		else
		{
			throw new RuntimeException("TODO: " + arg);
		}
	}

	/*private static <K extends Kind> ParameterNode<K> copyParameterNode(ParameterNode<K> an)
	{
		ParameterNode<K> pn = an;
		//return new ParameterNode(null, pn.toName().toString());//, pn.kind);
		//return (ParameterNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.PARAMETER, pn.identifier);
		return ModelFactoryImpl.FACTORY.ParameterNode(pn.kind, pn.identifier);
	}*/
}
