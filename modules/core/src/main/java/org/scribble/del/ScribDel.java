package org.scribble.del;

import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ContextBuilder;
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
public interface ScribDel
{
	Env env();

	void enterDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb) throws ScribbleException;
	ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException;

	void enterContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder) throws ScribbleException;
	ScribNode leaveContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder, ScribNode visited) throws ScribbleException;

	void enterWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker) throws ScribbleException;
	ScribNode leaveWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker, ScribNode visited) throws ScribbleException;

	void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException;
	ScribNode leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException;

	void enterProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer);
	ScribNode leaveProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer, ScribNode visited);

	void enterReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker) throws ScribbleException;
	ScribNode leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException;

	void enterFsmBuilder(ScribNode parent, ScribNode child, FsmBuilder fsmcon);
	ScribNode leaveFsmBuilder(ScribNode parent, ScribNode child, FsmBuilder fsmcon, ScribNode visited);

	void enterOpCollection(ScribNode parent, ScribNode child, MessageIdCollector coll);
	ScribNode leaveOpCollection(ScribNode parent, ScribNode child, MessageIdCollector coll, ScribNode visited);

	void enterProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder) throws ScribbleException;
	ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException;

	void enterInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf) throws ScribbleException;
	ScribNode leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException;

	void enterInlinedWFChoiceCheck(ScribNode parent, ScribNode child, InlinedWFChoiceChecker checker) throws ScribbleException;
	ScribNode leaveInlinedWFChoiceCheck(ScribNode parent, ScribNode child, InlinedWFChoiceChecker checker, ScribNode visited) throws ScribbleException;

	void enterModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder) throws ScribbleException;
	ScribNode leaveModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder, ScribNode visited) throws ScribbleException;
}
