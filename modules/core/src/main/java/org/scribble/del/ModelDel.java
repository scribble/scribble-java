package org.scribble.del;

import org.scribble.ast.ModelNode;
import org.scribble.ast.visit.ContextBuilder;
import org.scribble.ast.visit.FsmConstructor;
import org.scribble.ast.visit.MessageIdCollector;
import org.scribble.ast.visit.ModelBuilder;
import org.scribble.ast.visit.NameDisambiguator;
import org.scribble.ast.visit.Projector;
import org.scribble.ast.visit.ReachabilityChecker;
import org.scribble.ast.visit.WellFormedChoiceChecker;
import org.scribble.ast.visit.env.Env;
import org.scribble.util.ScribbleException;


// Mutable for Envs (by visitors) -- make immutable?
public interface ModelDel
{
	void enterDisambiguation(ModelNode parent, ModelNode child, NameDisambiguator disamb) throws ScribbleException;
	ModelNode leaveDisambiguation(ModelNode parent, ModelNode child, NameDisambiguator disamb, ModelNode visited) throws ScribbleException;

	/*void enterBoundNamesCheck(ModelNode parent, ModelNode child, BoundNameChecker checker) throws ScribbleException; 
	ModelNode leaveBoundNamesCheck(ModelNode parent, ModelNode child, BoundNameChecker checker, ModelNode visited) throws ScribbleException;*/
	
	//ContextBuilder enterContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder) throws ScribbleException;
	void enterContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder) throws ScribbleException;
	ModelNode leaveContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder, ModelNode visited) throws ScribbleException;

	/*WellFormedChoiceChecker enterWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker) throws ScribbleException;
	ModelNode leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException;*/
	void enterWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker) throws ScribbleException;
	ModelNode leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException;

	//Projector enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException;
	void enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException;
	ModelNode leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException;  // Move to GlobalModelNode? Cannot use visitor pattern then?

	//ReachabilityChecker enterReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker) throws ScribbleException;
	void enterReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker) throws ScribbleException;
	ModelNode leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException;

	void enterFsmConstruction(ModelNode parent, ModelNode child, FsmConstructor fsmcon);// throws ScribbleException;
	ModelNode leaveFsmConstruction(ModelNode parent, ModelNode child, FsmConstructor fsmcon, ModelNode visited);// throws ScribbleException;

	void enterOpCollection(ModelNode parent, ModelNode child, MessageIdCollector coll);// throws ScribbleException;
	ModelNode leaveOpCollection(ModelNode parent, ModelNode child, MessageIdCollector coll, ModelNode visited);// throws ScribbleException;

	void enterModelBuilding(ModelNode parent, ModelNode child, ModelBuilder builder) throws ScribbleException;
	ModelNode leaveModelBuilding(ModelNode parent, ModelNode child, ModelBuilder builder, ModelNode visited) throws ScribbleException;
	
	Env env();
	//void setEnv(Env env);  // No defensive copy -- used from inside Env during a env pass
}
