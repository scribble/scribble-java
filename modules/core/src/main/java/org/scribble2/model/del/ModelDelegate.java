package org.scribble2.model.del;


public interface ModelDelegate
{
	/*ModelNode visit(ModelNodeBase n, ModelVisitor nv);// throws ScribbleException;
	ModelNode visitChild(ModelNode parent, ModelNode child, ModelVisitor nv);// throws ScribbleException;
	ModelNode visitChildren(ModelVisitor nv);// throws ScribbleException;*/
	//ModelNode visitChildrenInSubprotocols(SubprotocolVisitor spv);// throws ScribbleException;
	
	/*NameDisambiguator enterDisambiguation(NameDisambiguator disamb) throws ScribbleException;
	ModelNode leaveDisambiguation(NameDisambiguator disamb) throws ScribbleException;

	ModelNodeContextBuilder enterContextBuilding(ModelNodeContextBuilder builder) throws ScribbleException;
	ModelNode leaveContextBuilding(ModelNodeContextBuilder builder) throws ScribbleException;

	WellFormedChoiceChecker enterWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException;
	ModelNode leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException;

	// FIXME: factor out a project method (like a reconstruct) to GlobalModelNode (and use the below for recording/assembling the projections)
	Projector enterProjection(Projector proj);
	ModelNode visitForProjection(Projector proj) throws ScribbleException;
	ModelNode leaveProjection(Projector proj);  // Move to GlobalModelNode? Cannot use visitor pattern then?

	GraphBuilder enterGraphBuilding(GraphBuilder builder);
	ModelNode visitForGraphBuilding(GraphBuilder builder);
	ModelNode leaveGraphBuilding(GraphBuilder builder);

	ReachabilityChecker enterReachabilityCheck(ReachabilityChecker checker) throws ScribbleException;
	ModelNode visitForReachabilityChecking(ReachabilityChecker proj) throws ScribbleException;
	ModelNode leaveReachabilityCheck(ReachabilityChecker checker) throws ScribbleException;

	ModelNode substitute(Substitutor subs);
	
	ModelNodeContext getContext();
	Env getEnv();*/
	//void setEnv(Env env);  // No defensive copy -- used from inside Env during a env pass

	/*ModelNode checkWellFormedness(WellFormednessChecker wfc) throws ScribbleException;
	ModelNode project(Projector proj) throws ScribbleException;
	ModelNode checkReachability(ReachabilityChecker rc) throws ScribbleException;

	ModelNode collectRoles(RoleCollector rc) throws ScribbleException;*/
}
