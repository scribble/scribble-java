package org.scribble2.model.del.name;

import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.context.ModuleContext;
import org.scribble2.model.del.ModelDelBase;
import org.scribble2.model.name.simple.AmbigNameNode;
import org.scribble2.model.visit.NameDisambiguator;
import org.scribble2.sesstype.kind.DataTypeKind;
import org.scribble2.sesstype.kind.SigKind;
import org.scribble2.sesstype.name.AmbigName;
import org.scribble2.sesstype.name.DataType;
import org.scribble2.util.ScribbleException;


//public abstract class ModelDelegateBase implements ModelDelegate
public class AmbigNameNodeDel extends ModelDelBase
{
	//public AmbiguousNameDelegate(Env env)
	public AmbigNameNodeDel()
	{
		//super(env);
	}

	/*@Override
	public NameDisambiguator enterDisambiguation(ModelNode n, NameDisambiguator disamb) throws ScribbleException
	{
		return disamb;
	}*/

	@Override
	public ModelNode leaveDisambiguation(ModelNode parent, ModelNode child, NameDisambiguator disamb, ModelNode visited) throws ScribbleException
	{
		ModuleContext mcontext = disamb.getModuleContext();

		//IName name = ((Named) visited).toName();
		AmbigName name = ((AmbigNameNode) visited).toName();
		// By well-formedness (checked later), payload type and parameter names are distinct
		if (mcontext.isDataTypeVisible(name.toDataType()))
		{
			return ModelFactoryImpl.FACTORY.QualifiedNameNode(DataTypeKind.KIND, name.getElements());
		}
		else if (mcontext.isMessageSigNameVisible(name.toMessageSigName()))
		{
			return ModelFactoryImpl.FACTORY.QualifiedNameNode(SigKind.KIND, name.getElements());
		}
		else if (disamb.isBoundParameter(name))
		{
			//return ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.PARAMETER, name.toString());
			return ModelFactoryImpl.FACTORY.NonRoleParamNode(disamb.getParameterKind(name), name.toString());
		}
		throw new ScribbleException("Cannot disambiguate name: " + name);
	}

	/*@Override
	public ModelNode visit(ModelNodeBase n, ModelVisitor nv)// throws ScribbleException
	{
		return visitChild(null, n, nv);
	}
	
	protected ModelNode visitChild(ModelNodeBase parent, ModelNodeBase child, ModelVisitor nv)// throws ScribbleException
	{
		return nv.visit(parent, child);
	}*/
	
	/*// Overriding methods should use visitChild (and maybe a static reconstruct pattern)
	@Override
	public ModelNode visitChildren(ModelVisitor nv) throws ScribbleException
	{
		return this;
	}

	@Override
	public ModelNode visitChildrenInSubprotocols(SubprotocolVisitor spv) throws ScribbleException
	{
		return visitChildren(spv);
	}

	@Override
	public ModelNodeContext getContext()
	{
		return this.ncontext;
	}

	@Override
	public Env getEnv()
	{
		return this.env;
	}

	//@Override
	public void setEnv(Env env)  // Used from inside Env to modify the ModelNode (cf. context building done from inside ModelNode and modified using copy constructor) -- defensive copy responsibility left to leave call
	{  
		this.env = env;  // Inconsistent with immutable pattern?
	}

	@Override
	public String toString()
	{
		if (this.ct != null)
		{
			return this.ct.toString();
		}
		return super.toString();
	}
	
	// Requires visited ModelNode to be of the same class as the original ModelNode
	// Not suitable for general Visitor pattern: as well as the strict class check (so no substitutability), overriding is not convenient
	// However, this is convenient for visiting generic ModelNodes (cast would be unchecked) -- so those ModelNodes (e.g. ProtocolBlock -- done via visitAll for Choice/Parallel) must keep the same class
	// So this method is basically for the generic AST ModelNodes (ProtocolDecl/Def/Block, InteractionSequence)
	protected static <T extends ModelNode> T visitChildWithClassCheck(ModelNode parent, T child, ModelVisitor nv)// throws ScribbleException
	{
		ModelNode visited = ((AbstractModelNode) parent).visitChild(child, nv);
		if (visited.getClass() != child.getClass())  // Visitor is not allowed to replace the ModelNode by a different ModelNode type
		{
			throw new RuntimeException("Visitor generic visit error: " + child.getClass() + ", " + visited.getClass());
		}
		@SuppressWarnings("unchecked")
		T t = (T) visited;
		return t;
	}
	
	// Requires all visited ModelNodes to be of the same class as the original ModelNodes
	//public <T extends InteractionModelNode> List<T> visitAll(List<T> ModelNodes) throws ScribbleException
	protected static <T extends ModelNode> List<T> visitChildListWithClassCheck(ModelNode parent, List<T> children, ModelVisitor nv)// throws ScribbleException
	{
		List<T> visited = new LinkedList<>();
		for (T n : children)
		{
			visited.add(visitChildWithClassCheck(parent, n, nv));
		}
		return visited;
	}*/
}
