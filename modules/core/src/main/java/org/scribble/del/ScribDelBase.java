package org.scribble.del;

import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ContextBuilder;
import org.scribble.visit.EnvVisitor;
import org.scribble.visit.FsmBuilder;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.InlinedWFChoiceChecker;
import org.scribble.visit.MessageIdCollector;
import org.scribble.visit.ModelBuilder;
import org.scribble.visit.NameDisambiguator;
import org.scribble.visit.ProjectedChoiceSubjectFixer;
import org.scribble.visit.Projector;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.WFChoiceChecker;
import org.scribble.visit.env.Env;


// Mutable for pass-specific Envs (by visitors)
public abstract class ScribDelBase implements ScribDel
{
	private Env env;
	
	public ScribDelBase()
	{

	}
	
	@Override
	public Env env()
	{
		return this.env;
	}
	
	// "setEnv" rather than "env" as a non-defensive setter (cf. ModelNodeBase#del)
	@Override
	//protected void setEnv(Env env)
	public void setEnv(Env env)
	{
		this.env = env;
	}
	
	/*@Override
	public void enterDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb) throws ScribbleException
	{

	}

	@Override
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	@Override
	public void enterContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder) throws ScribbleException
	{
	}

	@Override
	public ScribNode leaveContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	@Override
	public void enterWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker) throws ScribbleException
	{

	}

	@Override
	public ScribNode leaveWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	@Override
	public void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException
	{

	}
	
	@Override
	public ScribNode leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		
	}

	@Override
	public ScribNode leaveProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer, ScribNode visited)
	{
		return visited;
	}

	@Override
	public void enterReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker) throws ScribbleException
	{

	}
	
	@Override
	public ScribNode leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	@Override
	public void enterFsmBuilder(ScribNode parent, ScribNode child, FsmBuilder conv)
	{
		
	}

	@Override
	public ScribNode leaveFsmBuilder(ScribNode parent, ScribNode child, FsmBuilder conv, ScribNode visited)
	{
		return visited;
	}

	@Override
	public void enterOpCollection(ScribNode parent, ScribNode child, MessageIdCollector coll)
	{
		
	}

	@Override
	public ScribNode leaveOpCollection(ScribNode parent, ScribNode child, MessageIdCollector coll, ScribNode visited)
	{
		return visited;
	}

	@Override
	public void enterProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder) throws ScribbleException
	{
		
	}

	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	@Override
	public void enterInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf) throws ScribbleException
	{
		
	}

	@Override
	public ScribNode leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	@Override
	public void enterInlinedWFChoiceCheck(ScribNode parent, ScribNode child, InlinedWFChoiceChecker checker) throws ScribbleException
	{
		
	}

	@Override
	public ScribNode leaveInlinedWFChoiceCheck(ScribNode parent, ScribNode child, InlinedWFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	@Override
	public void enterModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder) throws ScribbleException
	{
		
	}

	@Override
	public ScribNode leaveModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder, ScribNode visited) throws ScribbleException
	{
		return visited;
	}*/
	
	//protected static <T extends Env> void pushVisitorEnv(ScribDel del, ScribNode parent, ScribNode child, EnvVisitor<T> ev)
	//protected static <T extends Env> void pushVisitorEnv(ScribDel del, EnvVisitor<T> ev)
	public static <T extends Env> void pushVisitorEnv(ScribDel del, EnvVisitor<T> ev)
	{
		T env = castEnv(ev, ev.peekEnv().enterContext());  // By default: copy
		ev.pushEnv(env);
	}
	
	//protected static <T1 extends Env, T2 extends ScribNode>
	public static <T1 extends Env, T2 extends ScribNode>
			//T2 popAndSetVisitorEnv(ScribDel del, ScribNode parent, ScribNode child, EnvVisitor<T1> ev, T2 visited)
			T2 popAndSetVisitorEnv(ScribDel del, EnvVisitor<T1> ev, T2 visited)
	{
		// No merge here: merging of child contexts into parent context is handled "manually" for each pass and (compound) interaction node as needed (e.g. WF-choice and reachability)
		T1 env = ev.popEnv();
		((ScribDelBase) del).setEnv(env);
		return visited;
	}
	
	private static <T extends Env> T castEnv(EnvVisitor<T> ev, Env env) 
	{
		@SuppressWarnings("unchecked")
		T tmp = (T) env;
		return tmp;
	}
}
