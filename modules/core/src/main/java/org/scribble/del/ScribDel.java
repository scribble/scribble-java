package org.scribble.del;

import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.DelegationProtocolRefChecker;
import org.scribble.visit.EndpointGraphBuilder;
import org.scribble.visit.GlobalModelChecker;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.MessageIdCollector;
import org.scribble.visit.ModuleContextBuilder;
import org.scribble.visit.NameDisambiguator;
import org.scribble.visit.ProjectedChoiceDoPruner;
import org.scribble.visit.ProjectedChoiceSubjectFixer;
import org.scribble.visit.ProjectedRoleDeclFixer;
import org.scribble.visit.Projector;
import org.scribble.visit.ProtocolDeclContextBuilder;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.RoleCollector;
import org.scribble.visit.UnguardedChoiceDoProjectionChecker;
import org.scribble.visit.WFChoiceChecker;
import org.scribble.visit.env.Env;

// Immutable except for pass-specific Envs (by visitors) only -- Envs considered transient, not treated immutably (i.e. non defensive setter on del)
// Parameterise by AstNode type?  Would inhibit del sharing between types (but that's not currently needed)
public interface ScribDel
{
	Env<?> env();
	void setEnv(Env<?> env);  // Non defensive

	default void enterModuleContextBuilding(ScribNode parent, ScribNode child, ModuleContextBuilder builder) throws ScribbleException
	{

	}

	default ScribNode leaveModuleContextBuilding(ScribNode parent, ScribNode child, ModuleContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	default void enterDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb) throws ScribbleException
	{

	}

	default ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	default void enterDelegationProtocolRefCheck(ScribNode parent, ScribNode child, DelegationProtocolRefChecker checker) throws ScribbleException
	{

	}

	default ScribNode leaveDelegationProtocolRefCheck(ScribNode parent, ScribNode child, DelegationProtocolRefChecker checker, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	default void enterProtocolDeclContextBuilding(ScribNode parent, ScribNode child, ProtocolDeclContextBuilder builder) throws ScribbleException
	{

	}

	default ScribNode leaveProtocolDeclContextBuilding(ScribNode parent, ScribNode child, ProtocolDeclContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	default void enterRoleCollection(ScribNode parent, ScribNode child, RoleCollector coll)
	{
		
	}

	default ScribNode leaveRoleCollection(ScribNode parent, ScribNode child, RoleCollector coll, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	default void enterProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl) throws ScribbleException
	{
		
	}

	default ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	default void enterInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf) throws ScribbleException
	{
		
	}

	// These are for "choice-rec" unfolding, i.e. InlinedProtocolUnfolder -- cf. UnfoldingVisitor, which does continue unfolding by revisiting a clone of the rec body
	default ScribNode leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	default void enterInlinedWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker) throws ScribbleException
	{
		
	}

	default ScribNode leaveInlinedWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker, ScribNode visited) throws ScribbleException
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

	default void enterEndpointGraphBuilding(ScribNode parent, ScribNode child, EndpointGraphBuilder graph)
	{
		
	}

	default ScribNode leaveEndpointGraphBuilding(ScribNode parent, ScribNode child, EndpointGraphBuilder graph, ScribNode visited) throws ScribbleException
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

	/*default void enterModelBuilding(ScribNode parent, ScribNode child, GlobalModelBuilder builder) throws ScribbleException
	{
		
	}

	default ScribNode leaveModelBuilding(ScribNode parent, ScribNode child, GlobalModelBuilder builder, ScribNode visited) throws ScribbleException
	{
		return visited;
	}*/
	
	/*//default void enterPathCollection(ScribNode parent, ScribNode child, PathCollectionVisitor<? extends PathEnv> coll) throws ScribbleException
	default void enterPathCollection(ScribNode parent, ScribNode child, PathCollectionVisitor coll) throws ScribbleException
	{
		
	}

	//default ScribNode leavePathCollection(ScribNode parent, ScribNode child, PathCollectionVisitor<? extends PathEnv> coll, ScribNode visited) throws ScribbleException
	default ScribNode leavePathCollection(ScribNode parent, ScribNode child, PathCollectionVisitor coll, ScribNode visited) throws ScribbleException
	{
		return visited;
	}*/
	
	/*default void enterWFChoicePathCheck(ScribNode parent, ScribNode child, WFChoicePathChecker coll) throws ScribbleException
	{
		
	}

	default ScribNode leaveWFChoicePathCheck(ScribNode parent, ScribNode child, WFChoicePathChecker coll, ScribNode visited) throws ScribbleException
	{
		return visited;
	}*/

	/*default void enterEnablingMessageCollection(ScribNode parent, ScribNode child, EnablingMessageCollector coll)
	{
		
	}

	default ScribNode leaveEnablingMessageCollection(ScribNode parent, ScribNode child, EnablingMessageCollector coll, ScribNode visited)
	{
		return visited;
	}*/
	
	default void enterCompatCheck(ScribNode parent, ScribNode child, GlobalModelChecker coll) throws ScribbleException
	{
		
	}

	default ScribNode leaveCompatCheck(ScribNode parent, ScribNode child, GlobalModelChecker coll, ScribNode visited) throws ScribbleException
	{
		return visited;
	}
	
	default void enterProjectedChoiceDoPruning(ScribNode parent, ScribNode child, ProjectedChoiceDoPruner pruner) throws ScribbleException
	{
		
	}

	default ScribNode leaveProjectedChoiceDoPruning(ScribNode parent, ScribNode child, ProjectedChoiceDoPruner proj, ScribNode visited) throws ScribbleException
	{
		return visited;
	}
	
	default void enterUnguardedChoiceDoProjectionCheck(ScribNode parent, ScribNode child, UnguardedChoiceDoProjectionChecker checker) throws ScribbleException
	{
		
	}

	default ScribNode leaveUnguardedChoiceDoProjectionCheck(ScribNode parent, ScribNode child, UnguardedChoiceDoProjectionChecker checker, ScribNode visited) throws ScribbleException
	{
		return visited;
	}
}
