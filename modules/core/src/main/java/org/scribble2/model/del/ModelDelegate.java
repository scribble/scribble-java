package org.scribble2.model.del;

import org.scribble2.model.ModelNode;
import org.scribble2.model.visit.ContextBuilder;
import org.scribble2.model.visit.NameDisambiguator;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.model.visit.env.Env;
import org.scribble2.util.ScribbleException;


public interface ModelDelegate
{
	/*ModelNode visit(ModelNodeBase n, ModelVisitor nv);// throws ScribbleException;
	ModelNode visitChild(ModelNode parent, ModelNode child, ModelVisitor nv);// throws ScribbleException;
	ModelNode visitChildren(ModelVisitor nv);// throws ScribbleException;*/
	//ModelNode visitChildrenInSubprotocols(SubprotocolVisitor spv);// throws ScribbleException;
	
	NameDisambiguator enterDisambiguation(ModelNode parent, ModelNode child, NameDisambiguator disamb) throws ScribbleException;
	ModelNode leaveDisambiguation(ModelNode parent, ModelNode child, NameDisambiguator disamb, ModelNode visited) throws ScribbleException;

	ContextBuilder enterContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder) throws ScribbleException;
	ModelNode leaveContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder, ModelNode visited) throws ScribbleException;

	WellFormedChoiceChecker enterWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker) throws ScribbleException;
	ModelNode leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException;

	// FIXME: factor out a project method (like a reconstruct) to GlobalModelNode (and use the below for recording/assembling the projections)
	Projector enterProjection(ModelNode parent, ModelNode child, Projector proj);
	//ModelNode visitForProjection(Projector proj) throws ScribbleException;
	ModelNode leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited);  // Move to GlobalModelNode? Cannot use visitor pattern then?

	/*ReachabilityChecker enterReachabilityCheck(ReachabilityChecker checker) throws ScribbleException;
	ModelNode visitForReachabilityChecking(ReachabilityChecker proj) throws ScribbleException;
	ModelNode leaveReachabilityCheck(ReachabilityChecker checker) throws ScribbleException;

	GraphBuilder enterGraphBuilding(GraphBuilder builder);
	ModelNode visitForGraphBuilding(GraphBuilder builder);
	ModelNode leaveGraphBuilding(GraphBuilder builder);

	ModelNode substitute(Substitutor subs);
	
	ModelNodeContext getContext();*/
	Env getEnv();
	//void setEnv(Env env);  // No defensive copy -- used from inside Env during a env pass

	/*ModelNode checkWellFormedness(WellFormednessChecker wfc) throws ScribbleException;
	ModelNode project(Projector proj) throws ScribbleException;
	ModelNode checkReachability(ReachabilityChecker rc) throws ScribbleException;

	ModelNode collectRoles(RoleCollector rc) throws ScribbleException;*/
}
