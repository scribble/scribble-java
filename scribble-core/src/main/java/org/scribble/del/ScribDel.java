/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.del;

import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.context.EGraphBuilder;
import org.scribble.visit.context.ModuleContextBuilder;
import org.scribble.visit.context.ProjectedChoiceDoPruner;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;
import org.scribble.visit.context.ProjectedRoleDeclFixer;
import org.scribble.visit.context.Projector;
import org.scribble.visit.context.ProtocolDeclContextBuilder;
import org.scribble.visit.context.RecRemover;
import org.scribble.visit.context.UnguardedChoiceDoProjectionChecker;
import org.scribble.visit.env.Env;
import org.scribble.visit.util.MessageIdCollector;
import org.scribble.visit.util.RecVarCollector;
import org.scribble.visit.util.RoleCollector;
import org.scribble.visit.validation.GProtocolValidator;
import org.scribble.visit.wf.DelegationProtocolRefChecker;
import org.scribble.visit.wf.ExplicitCorrelationChecker;
import org.scribble.visit.wf.NameDisambiguator;
import org.scribble.visit.wf.ReachabilityChecker;
import org.scribble.visit.wf.WFChoiceChecker;

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

	default void enterEGraphBuilding(ScribNode parent, ScribNode child, EGraphBuilder graph)
	{
		
	}

	default ScribNode leaveEGraphBuilding(ScribNode parent, ScribNode child, EGraphBuilder graph, ScribNode visited) throws ScribbleException
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
	
	default void enterValidation(ScribNode parent, ScribNode child, GProtocolValidator coll) throws ScribbleException
	{
		
	}

	default ScribNode leaveValidation(ScribNode parent, ScribNode child, GProtocolValidator coll, ScribNode visited) throws ScribbleException
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

	default void enterExplicitCorrelationCheck(ScribNode parent, ScribNode child, ExplicitCorrelationChecker checker) throws ScribbleException
	{
		
	}

	default ScribNode leaveExplicitCorrelationCheck(ScribNode parent, ScribNode child, ExplicitCorrelationChecker checker, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	default void enterRecVarCollection(ScribNode parent, ScribNode child, RecVarCollector coll)
	{
		
	}

	default ScribNode leaveRecVarCollection(ScribNode parent, ScribNode child, RecVarCollector coll, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	default void enterRecRemoval(ScribNode parent, ScribNode child, RecRemover rem)
	{
		
	}

	default ScribNode leaveRecRemoval(ScribNode parent, ScribNode child, RecRemover rem, ScribNode visited) throws ScribbleException
	{
		return visited;
	}
}
