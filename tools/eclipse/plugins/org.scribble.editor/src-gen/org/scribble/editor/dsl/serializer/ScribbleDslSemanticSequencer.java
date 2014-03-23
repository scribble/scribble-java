package org.scribble.editor.dsl.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticNodeProvider.INodesForEObjectProvider;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;
import org.scribble.editor.dsl.scribbleDsl.Argument;
import org.scribble.editor.dsl.scribbleDsl.GlobalChoice;
import org.scribble.editor.dsl.scribbleDsl.GlobalContinue;
import org.scribble.editor.dsl.scribbleDsl.GlobalDo;
import org.scribble.editor.dsl.scribbleDsl.GlobalInterrupt;
import org.scribble.editor.dsl.scribbleDsl.GlobalInterruptible;
import org.scribble.editor.dsl.scribbleDsl.GlobalMessageTransfer;
import org.scribble.editor.dsl.scribbleDsl.GlobalParallel;
import org.scribble.editor.dsl.scribbleDsl.GlobalProtocolBlock;
import org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl;
import org.scribble.editor.dsl.scribbleDsl.GlobalRecursion;
import org.scribble.editor.dsl.scribbleDsl.ImportMember;
import org.scribble.editor.dsl.scribbleDsl.ImportModule;
import org.scribble.editor.dsl.scribbleDsl.LocalCatch;
import org.scribble.editor.dsl.scribbleDsl.LocalChoice;
import org.scribble.editor.dsl.scribbleDsl.LocalContinue;
import org.scribble.editor.dsl.scribbleDsl.LocalDo;
import org.scribble.editor.dsl.scribbleDsl.LocalInterruptible;
import org.scribble.editor.dsl.scribbleDsl.LocalParallel;
import org.scribble.editor.dsl.scribbleDsl.LocalProtocolBlock;
import org.scribble.editor.dsl.scribbleDsl.LocalProtocolDecl;
import org.scribble.editor.dsl.scribbleDsl.LocalReceive;
import org.scribble.editor.dsl.scribbleDsl.LocalRecursion;
import org.scribble.editor.dsl.scribbleDsl.LocalSend;
import org.scribble.editor.dsl.scribbleDsl.LocalThrow;
import org.scribble.editor.dsl.scribbleDsl.Message;
import org.scribble.editor.dsl.scribbleDsl.MessageSignature;
import org.scribble.editor.dsl.scribbleDsl.Module;
import org.scribble.editor.dsl.scribbleDsl.ParameterDecl;
import org.scribble.editor.dsl.scribbleDsl.PayloadElement;
import org.scribble.editor.dsl.scribbleDsl.PayloadTypeDecl;
import org.scribble.editor.dsl.scribbleDsl.RoleDecl;
import org.scribble.editor.dsl.scribbleDsl.RoleInstantiation;
import org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage;
import org.scribble.editor.dsl.services.ScribbleDslGrammarAccess;

@SuppressWarnings("all")
public class ScribbleDslSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private ScribbleDslGrammarAccess grammarAccess;
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == ScribbleDslPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case ScribbleDslPackage.ARGUMENT:
				if(context == grammarAccess.getArgumentRule()) {
					sequence_Argument(context, (Argument) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.GLOBAL_CHOICE:
				if(context == grammarAccess.getGlobalChoiceRule() ||
				   context == grammarAccess.getGlobalInteractionRule()) {
					sequence_GlobalChoice(context, (GlobalChoice) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.GLOBAL_CONTINUE:
				if(context == grammarAccess.getGlobalContinueRule() ||
				   context == grammarAccess.getGlobalInteractionRule()) {
					sequence_GlobalContinue(context, (GlobalContinue) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.GLOBAL_DO:
				if(context == grammarAccess.getGlobalDoRule() ||
				   context == grammarAccess.getGlobalInteractionRule()) {
					sequence_GlobalDo(context, (GlobalDo) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.GLOBAL_INTERRUPT:
				if(context == grammarAccess.getGlobalInterruptRule()) {
					sequence_GlobalInterrupt(context, (GlobalInterrupt) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.GLOBAL_INTERRUPTIBLE:
				if(context == grammarAccess.getGlobalInteractionRule() ||
				   context == grammarAccess.getGlobalInterruptibleRule()) {
					sequence_GlobalInterruptible(context, (GlobalInterruptible) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER:
				if(context == grammarAccess.getGlobalInteractionRule() ||
				   context == grammarAccess.getGlobalMessageTransferRule()) {
					sequence_GlobalMessageTransfer(context, (GlobalMessageTransfer) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.GLOBAL_PARALLEL:
				if(context == grammarAccess.getGlobalInteractionRule() ||
				   context == grammarAccess.getGlobalParallelRule()) {
					sequence_GlobalParallel(context, (GlobalParallel) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.GLOBAL_PROTOCOL_BLOCK:
				if(context == grammarAccess.getGlobalProtocolBlockRule()) {
					sequence_GlobalProtocolBlock(context, (GlobalProtocolBlock) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.GLOBAL_PROTOCOL_DECL:
				if(context == grammarAccess.getGlobalProtocolDeclRule()) {
					sequence_GlobalProtocolDecl(context, (GlobalProtocolDecl) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.GLOBAL_RECURSION:
				if(context == grammarAccess.getGlobalInteractionRule() ||
				   context == grammarAccess.getGlobalRecursionRule()) {
					sequence_GlobalRecursion(context, (GlobalRecursion) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.IMPORT_MEMBER:
				if(context == grammarAccess.getImportDeclRule() ||
				   context == grammarAccess.getImportMemberRule()) {
					sequence_ImportMember(context, (ImportMember) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.IMPORT_MODULE:
				if(context == grammarAccess.getImportDeclRule() ||
				   context == grammarAccess.getImportModuleRule()) {
					sequence_ImportModule(context, (ImportModule) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.LOCAL_CATCH:
				if(context == grammarAccess.getLocalCatchRule()) {
					sequence_LocalCatch(context, (LocalCatch) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.LOCAL_CHOICE:
				if(context == grammarAccess.getLlobalInteractionRule() ||
				   context == grammarAccess.getLocalChoiceRule()) {
					sequence_LocalChoice(context, (LocalChoice) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.LOCAL_CONTINUE:
				if(context == grammarAccess.getLlobalInteractionRule() ||
				   context == grammarAccess.getLocalContinueRule()) {
					sequence_LocalContinue(context, (LocalContinue) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.LOCAL_DO:
				if(context == grammarAccess.getLlobalInteractionRule() ||
				   context == grammarAccess.getLocalDoRule()) {
					sequence_LocalDo(context, (LocalDo) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.LOCAL_INTERRUPTIBLE:
				if(context == grammarAccess.getLlobalInteractionRule() ||
				   context == grammarAccess.getLocalInterruptibleRule()) {
					sequence_LocalInterruptible(context, (LocalInterruptible) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.LOCAL_PARALLEL:
				if(context == grammarAccess.getLlobalInteractionRule() ||
				   context == grammarAccess.getLocalParallelRule()) {
					sequence_LocalParallel(context, (LocalParallel) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.LOCAL_PROTOCOL_BLOCK:
				if(context == grammarAccess.getLocalProtocolBlockRule()) {
					sequence_LocalProtocolBlock(context, (LocalProtocolBlock) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.LOCAL_PROTOCOL_DECL:
				if(context == grammarAccess.getLocalProtocolDeclRule()) {
					sequence_LocalProtocolDecl(context, (LocalProtocolDecl) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.LOCAL_RECEIVE:
				if(context == grammarAccess.getLlobalInteractionRule() ||
				   context == grammarAccess.getLocalReceiveRule()) {
					sequence_LocalReceive(context, (LocalReceive) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.LOCAL_RECURSION:
				if(context == grammarAccess.getLlobalInteractionRule() ||
				   context == grammarAccess.getLocalRecursionRule()) {
					sequence_LocalRecursion(context, (LocalRecursion) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.LOCAL_SEND:
				if(context == grammarAccess.getLlobalInteractionRule() ||
				   context == grammarAccess.getLocalSendRule()) {
					sequence_LocalSend(context, (LocalSend) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.LOCAL_THROW:
				if(context == grammarAccess.getLocalThrowRule()) {
					sequence_LocalThrow(context, (LocalThrow) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.MESSAGE:
				if(context == grammarAccess.getMessageRule()) {
					sequence_Message(context, (Message) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.MESSAGE_SIGNATURE:
				if(context == grammarAccess.getMessageSignatureRule()) {
					sequence_MessageSignature(context, (MessageSignature) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.MODULE:
				if(context == grammarAccess.getModuleRule()) {
					sequence_Module(context, (Module) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.PARAMETER_DECL:
				if(context == grammarAccess.getParameterDeclRule()) {
					sequence_ParameterDecl(context, (ParameterDecl) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.PAYLOAD_ELEMENT:
				if(context == grammarAccess.getPayloadElementRule()) {
					sequence_PayloadElement(context, (PayloadElement) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.PAYLOAD_TYPE_DECL:
				if(context == grammarAccess.getPayloadTypeDeclRule()) {
					sequence_PayloadTypeDecl(context, (PayloadTypeDecl) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.ROLE_DECL:
				if(context == grammarAccess.getRoleDeclRule()) {
					sequence_RoleDecl(context, (RoleDecl) semanticObject); 
					return; 
				}
				else break;
			case ScribbleDslPackage.ROLE_INSTANTIATION:
				if(context == grammarAccess.getRoleInstantiationRule()) {
					sequence_RoleInstantiation(context, (RoleInstantiation) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     ((signature=MessageSignature alias=ID?) | (name=ID alias=ID?))
	 */
	protected void sequence_Argument(EObject context, Argument semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (role=ID blocks+=GlobalProtocolBlock blocks+=GlobalProtocolBlock*)
	 */
	protected void sequence_GlobalChoice(EObject context, GlobalChoice semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     label=ID
	 */
	protected void sequence_GlobalContinue(EObject context, GlobalContinue semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, ScribbleDslPackage.Literals.GLOBAL_CONTINUE__LABEL) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ScribbleDslPackage.Literals.GLOBAL_CONTINUE__LABEL));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getGlobalContinueAccess().getLabelIDTerminalRuleCall_1_0(), semanticObject.getLabel());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (scope=ID? member=ID (arguments+=Argument arguments+=Argument*)? roles+=RoleInstantiation roles+=RoleInstantiation*)
	 */
	protected void sequence_GlobalDo(EObject context, GlobalDo semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (messages+=Message messages+=Message* role=ID)
	 */
	protected void sequence_GlobalInterrupt(EObject context, GlobalInterrupt semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (scope=ID? block=GlobalProtocolBlock interrupts+=GlobalInterrupt*)
	 */
	protected void sequence_GlobalInterruptible(EObject context, GlobalInterruptible semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (message=Message fromRole=ID toRoles+=ID toRoles+=ID*)
	 */
	protected void sequence_GlobalMessageTransfer(EObject context, GlobalMessageTransfer semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (blocks+=GlobalProtocolBlock blocks+=GlobalProtocolBlock*)
	 */
	protected void sequence_GlobalParallel(EObject context, GlobalParallel semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (activities+=GlobalInteraction*)
	 */
	protected void sequence_GlobalProtocolBlock(EObject context, GlobalProtocolBlock semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         (parameters+=ParameterDecl parameters+=ParameterDecl*)? 
	 *         roles+=RoleDecl 
	 *         roles+=RoleDecl* 
	 *         (
	 *             block=GlobalProtocolBlock | 
	 *             (instantiates=ID (arguments+=Argument arguments+=Argument*)? roleInstantiations+=RoleInstantiation roleInstantiations+=RoleInstantiation*)
	 *         )
	 *     )
	 */
	protected void sequence_GlobalProtocolDecl(EObject context, GlobalProtocolDecl semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (label=ID block=GlobalProtocolBlock)
	 */
	protected void sequence_GlobalRecursion(EObject context, GlobalRecursion semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, ScribbleDslPackage.Literals.GLOBAL_RECURSION__LABEL) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ScribbleDslPackage.Literals.GLOBAL_RECURSION__LABEL));
			if(transientValues.isValueTransient(semanticObject, ScribbleDslPackage.Literals.GLOBAL_RECURSION__BLOCK) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ScribbleDslPackage.Literals.GLOBAL_RECURSION__BLOCK));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getGlobalRecursionAccess().getLabelIDTerminalRuleCall_1_0(), semanticObject.getLabel());
		feeder.accept(grammarAccess.getGlobalRecursionAccess().getBlockGlobalProtocolBlockParserRuleCall_2_0(), semanticObject.getBlock());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name=ModuleName member=ID alias=ID?)
	 */
	protected void sequence_ImportMember(EObject context, ImportMember semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ModuleName alias=ID?)
	 */
	protected void sequence_ImportModule(EObject context, ImportModule semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (messages+=Message messages+=Message* fromRole=ID)
	 */
	protected void sequence_LocalCatch(EObject context, LocalCatch semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (role=ID blocks+=LocalProtocolBlock blocks+=LocalProtocolBlock*)
	 */
	protected void sequence_LocalChoice(EObject context, LocalChoice semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     label=ID
	 */
	protected void sequence_LocalContinue(EObject context, LocalContinue semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, ScribbleDslPackage.Literals.LOCAL_CONTINUE__LABEL) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ScribbleDslPackage.Literals.LOCAL_CONTINUE__LABEL));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getLocalContinueAccess().getLabelIDTerminalRuleCall_1_0(), semanticObject.getLabel());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (scope=ID? member=ID (arguments+=Argument arguments+=Argument*)? roles+=RoleInstantiation roles+=RoleInstantiation*)
	 */
	protected void sequence_LocalDo(EObject context, LocalDo semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (scope=ID? block=LocalProtocolBlock throw=LocalThrow? catches+=LocalCatch*)
	 */
	protected void sequence_LocalInterruptible(EObject context, LocalInterruptible semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (blocks+=LocalProtocolBlock blocks+=LocalProtocolBlock*)
	 */
	protected void sequence_LocalParallel(EObject context, LocalParallel semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (activities+=LlobalInteraction*)
	 */
	protected void sequence_LocalProtocolBlock(EObject context, LocalProtocolBlock semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         role=ID 
	 *         (parameters+=ParameterDecl parameters+=ParameterDecl*)? 
	 *         roles+=RoleDecl 
	 *         roles+=RoleDecl* 
	 *         (
	 *             block=LocalProtocolBlock | 
	 *             (instantiates=ID (arguments+=Argument arguments+=Argument*)? roleInstantiations+=RoleInstantiation roleInstantiations+=RoleInstantiation*)
	 *         )
	 *     )
	 */
	protected void sequence_LocalProtocolDecl(EObject context, LocalProtocolDecl semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (message=Message fromRole=ID)
	 */
	protected void sequence_LocalReceive(EObject context, LocalReceive semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, ScribbleDslPackage.Literals.LOCAL_RECEIVE__MESSAGE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ScribbleDslPackage.Literals.LOCAL_RECEIVE__MESSAGE));
			if(transientValues.isValueTransient(semanticObject, ScribbleDslPackage.Literals.LOCAL_RECEIVE__FROM_ROLE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ScribbleDslPackage.Literals.LOCAL_RECEIVE__FROM_ROLE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getLocalReceiveAccess().getMessageMessageParserRuleCall_0_0(), semanticObject.getMessage());
		feeder.accept(grammarAccess.getLocalReceiveAccess().getFromRoleIDTerminalRuleCall_2_0(), semanticObject.getFromRole());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (label=ID block=LocalProtocolBlock)
	 */
	protected void sequence_LocalRecursion(EObject context, LocalRecursion semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, ScribbleDslPackage.Literals.LOCAL_RECURSION__LABEL) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ScribbleDslPackage.Literals.LOCAL_RECURSION__LABEL));
			if(transientValues.isValueTransient(semanticObject, ScribbleDslPackage.Literals.LOCAL_RECURSION__BLOCK) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ScribbleDslPackage.Literals.LOCAL_RECURSION__BLOCK));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getLocalRecursionAccess().getLabelIDTerminalRuleCall_1_0(), semanticObject.getLabel());
		feeder.accept(grammarAccess.getLocalRecursionAccess().getBlockLocalProtocolBlockParserRuleCall_2_0(), semanticObject.getBlock());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (message=Message toRoles+=ID toRoles+=ID*)
	 */
	protected void sequence_LocalSend(EObject context, LocalSend semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (messages+=Message messages+=Message* toRoles+=ID toRoles+=ID*)
	 */
	protected void sequence_LocalThrow(EObject context, LocalThrow semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (operator=ID? (types+=PayloadElement types+=PayloadElement*)?)
	 */
	protected void sequence_MessageSignature(EObject context, MessageSignature semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (signature=MessageSignature | parameter=ID)
	 */
	protected void sequence_Message(EObject context, Message semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ModuleName imports+=ImportDecl* types+=PayloadTypeDecl* (globals+=GlobalProtocolDecl | locals+=LocalProtocolDecl)*)
	 */
	protected void sequence_Module(EObject context, Module semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((name=ID alias=ID?) | (name=ID alias=ID?))
	 */
	protected void sequence_ParameterDecl(EObject context, ParameterDecl semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID? type=ID)
	 */
	protected void sequence_PayloadElement(EObject context, PayloadElement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (schema=ID type=STRING location=STRING alias=ID)
	 */
	protected void sequence_PayloadTypeDecl(EObject context, PayloadTypeDecl semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, ScribbleDslPackage.Literals.PAYLOAD_TYPE_DECL__SCHEMA) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ScribbleDslPackage.Literals.PAYLOAD_TYPE_DECL__SCHEMA));
			if(transientValues.isValueTransient(semanticObject, ScribbleDslPackage.Literals.PAYLOAD_TYPE_DECL__TYPE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ScribbleDslPackage.Literals.PAYLOAD_TYPE_DECL__TYPE));
			if(transientValues.isValueTransient(semanticObject, ScribbleDslPackage.Literals.PAYLOAD_TYPE_DECL__LOCATION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ScribbleDslPackage.Literals.PAYLOAD_TYPE_DECL__LOCATION));
			if(transientValues.isValueTransient(semanticObject, ScribbleDslPackage.Literals.PAYLOAD_TYPE_DECL__ALIAS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ScribbleDslPackage.Literals.PAYLOAD_TYPE_DECL__ALIAS));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getPayloadTypeDeclAccess().getSchemaIDTerminalRuleCall_2_0(), semanticObject.getSchema());
		feeder.accept(grammarAccess.getPayloadTypeDeclAccess().getTypeSTRINGTerminalRuleCall_4_0(), semanticObject.getType());
		feeder.accept(grammarAccess.getPayloadTypeDeclAccess().getLocationSTRINGTerminalRuleCall_6_0(), semanticObject.getLocation());
		feeder.accept(grammarAccess.getPayloadTypeDeclAccess().getAliasIDTerminalRuleCall_8_0(), semanticObject.getAlias());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID alias=ID?)
	 */
	protected void sequence_RoleDecl(EObject context, RoleDecl semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID alias=ID?)
	 */
	protected void sequence_RoleInstantiation(EObject context, RoleInstantiation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
}
