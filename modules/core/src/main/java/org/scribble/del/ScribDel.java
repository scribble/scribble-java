package org.scribble.del;

import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ContextBuilder;
import org.scribble.visit.FsmGenerator;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.InlinedWFChoiceChecker;
import org.scribble.visit.MessageIdCollector;
import org.scribble.visit.ModelBuilder;
import org.scribble.visit.NameDisambiguator;
import org.scribble.visit.ProjectedChoiceSubjectFixer;
import org.scribble.visit.ProjectedRoleDeclFixer;
import org.scribble.visit.Projector;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.RoleCollector;
import org.scribble.visit.WFChoiceChecker;
import org.scribble.visit.env.Env;

// Immutable except for pass-specific Envs (by visitors) only -- Envs considered transient, not treated immutably (i.e. non defensive setter on del)
public interface ScribDel
{
	Env env();
	void setEnv(Env env);  // Non defensive

	default void enterDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb) throws ScribbleException
	{

	}

	default ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	default void enterContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder) throws ScribbleException
	{

	}

	default ScribNode leaveContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	@Deprecated
	default void enterWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker) throws ScribbleException
	{

	}

	@Deprecated
	default ScribNode leaveWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	default ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	default void enterInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf) throws ScribbleException
	{
		
	}

	default ScribNode leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	default void enterInlinedWFChoiceCheck(ScribNode parent, ScribNode child, InlinedWFChoiceChecker checker) throws ScribbleException
	{
		
	}

	default void enterProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder) throws ScribbleException
	{
		
	}

	default ScribNode leaveInlinedWFChoiceCheck(ScribNode parent, ScribNode child, InlinedWFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	default void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException
	{

	}
	
	default ScribNode leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	default void enterProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		
	}

	default ScribNode leaveProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	default void enterProjectedRoleDeclFixing(ScribNode parent, ScribNode child, ProjectedRoleDeclFixer fixer)
	{
		
	}

	default ScribNode leaveProjectedRoleDeclFixing(ScribNode parent, ScribNode child, ProjectedRoleDeclFixer fixer, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	default void enterReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker) throws ScribbleException
	{

	}
	
	default ScribNode leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	default void enterFsmGeneration(ScribNode parent, ScribNode child, FsmGenerator fsmgen)
	{
		
	}

	default ScribNode leaveFsmGeneration(ScribNode parent, ScribNode child, FsmGenerator fsmgen, ScribNode visited)
	{
		return visited;
	}

	default void enterRoleCollection(ScribNode parent, ScribNode child, RoleCollector coll)
	{
		
	}

	default ScribNode leaveRoleCollection(ScribNode parent, ScribNode child, RoleCollector coll, ScribNode visited)
	{
		return visited;
	}

	default void enterMessageIdCollection(ScribNode parent, ScribNode child, MessageIdCollector coll)
	{
		
	}

	default ScribNode leaveMessageIdCollection(ScribNode parent, ScribNode child, MessageIdCollector coll, ScribNode visited)
	{
		return visited;
	}

	default void enterModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder) throws ScribbleException
	{
		
	}

	default ScribNode leaveModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder, ScribNode visited) throws ScribbleException
	{
		return visited;
	}
}
