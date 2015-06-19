package org.scribble.del;

import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ContextBuilder;
import org.scribble.visit.EnvVisitor;
import org.scribble.visit.FsmConstructor;
import org.scribble.visit.MessageIdCollector;
import org.scribble.visit.ModelBuilder;
import org.scribble.visit.NameDisambiguator;
import org.scribble.visit.Projector;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.WellFormedChoiceChecker;
import org.scribble.visit.env.Env;


// Mutable for Envs (by visitors) -- make immutable?
public abstract class ScribDelBase implements ScribDel
{
	private Env env;
	
	//public ModelDelegateBase(Env env)
	public ScribDelBase()
	{
		//this.env = env;
	}
	
	@Override
	public void enterDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb) throws ScribbleException
	{

	}

	@Override
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	/*@Override
	public void enterBoundNamesCheck(ModelNode parent, ModelNode child, BoundNameChecker checker) throws ScribbleException
	{

	}

	@Override
	public ModelNode leaveBoundNamesCheck(ModelNode parent, ModelNode child, BoundNameChecker checker, ModelNode visited) throws ScribbleException
	{
		return visited;
	}*/

	@Override
	//public ContextBuilder enterContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder) throws ScribbleException
	public void enterContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder) throws ScribbleException
	{
		//return builder;
	}

	@Override
	public ScribNode leaveContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	@Override
	//public WellFormedChoiceChecker enterWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker) throws ScribbleException
	public void enterWFChoiceCheck(ScribNode parent, ScribNode child, WellFormedChoiceChecker checker) throws ScribbleException
	{
		//return checker;
	}

	@Override
	public ScribNode leaveWFChoiceCheck(ScribNode parent, ScribNode child, WellFormedChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	@Override
	//public Projector enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	public void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException
	{
		//return proj;
	}
	
	@Override
	public ScribNode leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	@Override
	//public ReachabilityChecker enterReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker) throws ScribbleException
	public void enterReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker) throws ScribbleException
	{
		//return checker;
	}
	
	@Override
	public ScribNode leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	@Override
	public void enterFsmConstruction(ScribNode parent, ScribNode child, FsmConstructor conv) //throws ScribbleException
	{
		
	}

	@Override
	public ScribNode leaveFsmConstruction(ScribNode parent, ScribNode child, FsmConstructor conv, ScribNode visited) //throws ScribbleException
	{
		return visited;
	}

	@Override
	public void enterOpCollection(ScribNode parent, ScribNode child, MessageIdCollector coll) //throws ScribbleException
	{
		
	}

	@Override
	public ScribNode leaveOpCollection(ScribNode parent, ScribNode child, MessageIdCollector coll, ScribNode visited) //throws ScribbleException
	{
		return visited;
	}

	public void enterModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder) throws ScribbleException
	{
		
	}

	public ScribNode leaveModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder, ScribNode visited) throws ScribbleException
	{
		return visited;
	}
	
	protected <T extends Env> EnvVisitor<T> pushVisitorEnv(ScribNode parent, ScribNode child, EnvVisitor<T> ev) //throws ScribbleException
	{
		//T env = ev.peekEnv().<T>push();
		//T env = ev.peekEnv().getClass().cast(ev.peekEnv().push());
		T env = castEnv(ev, ev.peekEnv().enterContext());  // By default: copy
		ev.pushEnv(env);
		return ev;
	}
	
	protected <T1 extends Env, T2 extends ScribNode>
			T2 popAndSetVisitorEnv(ScribNode parent, ScribNode child, EnvVisitor<T1> ev, T2 visited) //throws ScribbleException
	{
		T1 env = ev.popEnv();
		//env = checker.popEnv().merge(env);  // No merge here: merging of child blocks is handled "manually" by the compound interaction nodes
		//checker.pushEnv(env);
		setEnv(env);
		return visited;
	}
	
	private static <T extends Env> T castEnv(EnvVisitor<T> ev, Env env) 
	{
		@SuppressWarnings("unchecked")
		T tmp = (T) env;
		return tmp;
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
	}*/
	
	/*private void copyEnv(EnvVisitor ev)
	{
		if (ev.hasEnv())
		{
			//setEnv(ev.peekEnv().copy());  // FIXME: need a deep copy for Env -- no: Env immutable
			setEnv(ev.peekEnv());
		}
	}*/

	@Override
	public Env env()
	{
		return this.env;
	}
	
	// "setEnv" rather than "env" as a non-defensive setter (cf. ModelNodeBase#del)
	//@Override
	protected void setEnv(Env env)
	{
		this.env = env;
	}

	/* //@Override
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