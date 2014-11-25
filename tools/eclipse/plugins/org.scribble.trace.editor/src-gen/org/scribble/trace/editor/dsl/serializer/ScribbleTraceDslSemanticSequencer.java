package org.scribble.trace.editor.dsl.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;
import org.scribble.trace.editor.dsl.scribbleTraceDsl.Messagetransfer;
import org.scribble.trace.editor.dsl.scribbleTraceDsl.Parameter;
import org.scribble.trace.editor.dsl.scribbleTraceDsl.ScribbleTraceDslPackage;
import org.scribble.trace.editor.dsl.scribbleTraceDsl.Trace;
import org.scribble.trace.editor.dsl.services.ScribbleTraceDslGrammarAccess;

@SuppressWarnings("all")
public class ScribbleTraceDslSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private ScribbleTraceDslGrammarAccess grammarAccess;
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == ScribbleTraceDslPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case ScribbleTraceDslPackage.MESSAGETRANSFER:
				if(context == grammarAccess.getMessagetransferRule() ||
				   context == grammarAccess.getStepdefnRule()) {
					sequence_Messagetransfer(context, (Messagetransfer) semanticObject); 
					return; 
				}
				else break;
			case ScribbleTraceDslPackage.PARAMETER:
				if(context == grammarAccess.getParameterRule()) {
					sequence_Parameter(context, (Parameter) semanticObject); 
					return; 
				}
				else break;
			case ScribbleTraceDslPackage.TRACE:
				if(context == grammarAccess.getTraceRule()) {
					sequence_Trace(context, (Trace) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     ((parameters+=Parameter parameters+=Parameter*)?)
	 */
	protected void sequence_Messagetransfer(EObject context, Messagetransfer semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (type=STRING value=STRING?)
	 */
	protected void sequence_Parameter(EObject context, Parameter semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (roles+=Roledefn* steps+=Stepdefn*)
	 */
	protected void sequence_Trace(EObject context, Trace semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
}
