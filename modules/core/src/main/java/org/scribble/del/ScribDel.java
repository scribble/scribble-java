package org.scribble.del;

import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ContextBuilder;
import org.scribble.visit.FsmConstructor;
import org.scribble.visit.InlineProtocolTranslator;
import org.scribble.visit.MessageIdCollector;
import org.scribble.visit.ModelBuilder;
import org.scribble.visit.NameDisambiguator;
import org.scribble.visit.Projector;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.WellFormedChoiceChecker;
import org.scribble.visit.env.Env;


// Mutable for pass-specific Envs (by visitors)
public interface ScribDel
{
	Env env();

	void enterDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb) throws ScribbleException;
	ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException;

	void enterContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder) throws ScribbleException;
	ScribNode leaveContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder, ScribNode visited) throws ScribbleException;

	void enterWFChoiceCheck(ScribNode parent, ScribNode child, WellFormedChoiceChecker checker) throws ScribbleException;
	ScribNode leaveWFChoiceCheck(ScribNode parent, ScribNode child, WellFormedChoiceChecker checker, ScribNode visited) throws ScribbleException;

	void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException;
	ScribNode leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException;

	void enterReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker) throws ScribbleException;
	ScribNode leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException;

	void enterFsmConstruction(ScribNode parent, ScribNode child, FsmConstructor fsmcon);
	ScribNode leaveFsmConstruction(ScribNode parent, ScribNode child, FsmConstructor fsmcon, ScribNode visited);

	void enterOpCollection(ScribNode parent, ScribNode child, MessageIdCollector coll);
	ScribNode leaveOpCollection(ScribNode parent, ScribNode child, MessageIdCollector coll, ScribNode visited);

	void enterInlineProtocolTranslation(ScribNode parent, ScribNode child, InlineProtocolTranslator builder) throws ScribbleException;
	ScribNode leaveInlineProtocolTranslation(ScribNode parent, ScribNode child, InlineProtocolTranslator builder, ScribNode visited) throws ScribbleException;

	void enterModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder) throws ScribbleException;
	ScribNode leaveModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder, ScribNode visited) throws ScribbleException;
}
