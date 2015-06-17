package org.scribble.del;

import org.scribble.ast.ScribNode;
import org.scribble.ast.visit.ContextBuilder;
import org.scribble.ast.visit.FsmConstructor;
import org.scribble.ast.visit.MessageIdCollector;
import org.scribble.ast.visit.ModelBuilder;
import org.scribble.ast.visit.NameDisambiguator;
import org.scribble.ast.visit.Projector;
import org.scribble.ast.visit.ReachabilityChecker;
import org.scribble.ast.visit.WellFormedChoiceChecker;
import org.scribble.ast.visit.env.Env;
import org.scribble.main.ScribbleException;


// Mutable for Envs (by visitors) -- make immutable?
public interface ScribDel
{
	void enterDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb) throws ScribbleException;
	ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException;

	/*void enterBoundNamesCheck(ModelNode parent, ModelNode child, BoundNameChecker checker) throws ScribbleException; 
	ModelNode leaveBoundNamesCheck(ModelNode parent, ModelNode child, BoundNameChecker checker, ModelNode visited) throws ScribbleException;*/
	
	//ContextBuilder enterContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder) throws ScribbleException;
	void enterContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder) throws ScribbleException;
	ScribNode leaveContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder, ScribNode visited) throws ScribbleException;

	/*WellFormedChoiceChecker enterWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker) throws ScribbleException;
	ModelNode leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException;*/
	void enterWFChoiceCheck(ScribNode parent, ScribNode child, WellFormedChoiceChecker checker) throws ScribbleException;
	ScribNode leaveWFChoiceCheck(ScribNode parent, ScribNode child, WellFormedChoiceChecker checker, ScribNode visited) throws ScribbleException;

	//Projector enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException;
	void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException;
	ScribNode leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException;  // Move to GlobalModelNode? Cannot use visitor pattern then?

	//ReachabilityChecker enterReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker) throws ScribbleException;
	void enterReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker) throws ScribbleException;
	ScribNode leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException;

	void enterFsmConstruction(ScribNode parent, ScribNode child, FsmConstructor fsmcon);// throws ScribbleException;
	ScribNode leaveFsmConstruction(ScribNode parent, ScribNode child, FsmConstructor fsmcon, ScribNode visited);// throws ScribbleException;

	void enterOpCollection(ScribNode parent, ScribNode child, MessageIdCollector coll);// throws ScribbleException;
	ScribNode leaveOpCollection(ScribNode parent, ScribNode child, MessageIdCollector coll, ScribNode visited);// throws ScribbleException;

	void enterModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder) throws ScribbleException;
	ScribNode leaveModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder, ScribNode visited) throws ScribbleException;
	
	Env env();
	//void setEnv(Env env);  // No defensive copy -- used from inside Env during a env pass
}
