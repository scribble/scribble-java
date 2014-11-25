package org.scribble.trace.editor.dsl.serializer;

import com.google.inject.Inject;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.GroupAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;
import org.scribble.trace.editor.dsl.services.ScribbleTraceDslGrammarAccess;

@SuppressWarnings("all")
public class ScribbleTraceDslSyntacticSequencer extends AbstractSyntacticSequencer {

	protected ScribbleTraceDslGrammarAccess grammarAccess;
	protected AbstractElementAlias match_Messagetransfer___CommaKeyword_7_0_IDTerminalRuleCall_7_1__a;
	protected AbstractElementAlias match_Messagetransfer___LeftParenthesisKeyword_2_0_RightParenthesisKeyword_2_2__q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (ScribbleTraceDslGrammarAccess) access;
		match_Messagetransfer___CommaKeyword_7_0_IDTerminalRuleCall_7_1__a = new GroupAlias(true, true, new TokenAlias(false, false, grammarAccess.getMessagetransferAccess().getCommaKeyword_7_0()), new TokenAlias(false, false, grammarAccess.getMessagetransferAccess().getIDTerminalRuleCall_7_1()));
		match_Messagetransfer___LeftParenthesisKeyword_2_0_RightParenthesisKeyword_2_2__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getMessagetransferAccess().getLeftParenthesisKeyword_2_0()), new TokenAlias(false, false, grammarAccess.getMessagetransferAccess().getRightParenthesisKeyword_2_2()));
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if(ruleCall.getRule() == grammarAccess.getIDRule())
			return getIDToken(semanticObject, ruleCall, node);
		else if(ruleCall.getRule() == grammarAccess.getTracedefnRule())
			return getTracedefnToken(semanticObject, ruleCall, node);
		return "";
	}
	
	/**
	 * terminal ID  		: '^'?('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;
	 */
	protected String getIDToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "";
	}
	
	/**
	 * Tracedefn: 'trace' ID ( 'by' Sentence ( 'shows' Sentence )? )? ';' ;
	 */
	protected String getTracedefnToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "trace;";
	}
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if(match_Messagetransfer___CommaKeyword_7_0_IDTerminalRuleCall_7_1__a.equals(syntax))
				emit_Messagetransfer___CommaKeyword_7_0_IDTerminalRuleCall_7_1__a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Messagetransfer___LeftParenthesisKeyword_2_0_RightParenthesisKeyword_2_2__q.equals(syntax))
				emit_Messagetransfer___LeftParenthesisKeyword_2_0_RightParenthesisKeyword_2_2__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Syntax:
	 *     (',' ID)*
	 */
	protected void emit_Messagetransfer___CommaKeyword_7_0_IDTerminalRuleCall_7_1__a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('(' ')')?
	 */
	protected void emit_Messagetransfer___LeftParenthesisKeyword_2_0_RightParenthesisKeyword_2_2__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
