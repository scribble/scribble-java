package org.scribble2.model.del;

import org.scribble2.model.ModelNode;
import org.scribble2.model.visit.ContextBuilder;
import org.scribble2.model.visit.NameDisambiguator;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.ReachabilityChecker;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.model.visit.env.Env;
import org.scribble2.util.ScribbleException;


// Mutable for Envs (by visitors) -- make immutable?
public interface ModelDelegate
{
	NameDisambiguator enterDisambiguation(ModelNode parent, ModelNode child, NameDisambiguator disamb) throws ScribbleException;
	ModelNode leaveDisambiguation(ModelNode parent, ModelNode child, NameDisambiguator disamb, ModelNode visited) throws ScribbleException;

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

	/*GraphBuilder enterGraphBuilding(GraphBuilder builder);
	ModelNode visitForGraphBuilding(GraphBuilder builder);
	ModelNode leaveGraphBuilding(GraphBuilder builder);*/

	Env env();
	//void setEnv(Env env);  // No defensive copy -- used from inside Env during a env pass
}
