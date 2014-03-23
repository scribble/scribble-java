/**
 */
package org.scribble.editor.dsl.scribbleDsl;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage
 * @generated
 */
public interface ScribbleDslFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  ScribbleDslFactory eINSTANCE = org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Module</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Module</em>'.
   * @generated
   */
  Module createModule();

  /**
   * Returns a new object of class '<em>Import Decl</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Import Decl</em>'.
   * @generated
   */
  ImportDecl createImportDecl();

  /**
   * Returns a new object of class '<em>Import Module</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Import Module</em>'.
   * @generated
   */
  ImportModule createImportModule();

  /**
   * Returns a new object of class '<em>Import Member</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Import Member</em>'.
   * @generated
   */
  ImportMember createImportMember();

  /**
   * Returns a new object of class '<em>Payload Type Decl</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Payload Type Decl</em>'.
   * @generated
   */
  PayloadTypeDecl createPayloadTypeDecl();

  /**
   * Returns a new object of class '<em>Message Signature</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Message Signature</em>'.
   * @generated
   */
  MessageSignature createMessageSignature();

  /**
   * Returns a new object of class '<em>Payload Element</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Payload Element</em>'.
   * @generated
   */
  PayloadElement createPayloadElement();

  /**
   * Returns a new object of class '<em>Global Protocol Decl</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Global Protocol Decl</em>'.
   * @generated
   */
  GlobalProtocolDecl createGlobalProtocolDecl();

  /**
   * Returns a new object of class '<em>Role Decl</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Role Decl</em>'.
   * @generated
   */
  RoleDecl createRoleDecl();

  /**
   * Returns a new object of class '<em>Parameter Decl</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Parameter Decl</em>'.
   * @generated
   */
  ParameterDecl createParameterDecl();

  /**
   * Returns a new object of class '<em>Role Instantiation</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Role Instantiation</em>'.
   * @generated
   */
  RoleInstantiation createRoleInstantiation();

  /**
   * Returns a new object of class '<em>Argument</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Argument</em>'.
   * @generated
   */
  Argument createArgument();

  /**
   * Returns a new object of class '<em>Global Protocol Block</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Global Protocol Block</em>'.
   * @generated
   */
  GlobalProtocolBlock createGlobalProtocolBlock();

  /**
   * Returns a new object of class '<em>Global Interaction</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Global Interaction</em>'.
   * @generated
   */
  GlobalInteraction createGlobalInteraction();

  /**
   * Returns a new object of class '<em>Global Message Transfer</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Global Message Transfer</em>'.
   * @generated
   */
  GlobalMessageTransfer createGlobalMessageTransfer();

  /**
   * Returns a new object of class '<em>Message</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Message</em>'.
   * @generated
   */
  Message createMessage();

  /**
   * Returns a new object of class '<em>Global Choice</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Global Choice</em>'.
   * @generated
   */
  GlobalChoice createGlobalChoice();

  /**
   * Returns a new object of class '<em>Global Recursion</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Global Recursion</em>'.
   * @generated
   */
  GlobalRecursion createGlobalRecursion();

  /**
   * Returns a new object of class '<em>Global Continue</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Global Continue</em>'.
   * @generated
   */
  GlobalContinue createGlobalContinue();

  /**
   * Returns a new object of class '<em>Global Parallel</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Global Parallel</em>'.
   * @generated
   */
  GlobalParallel createGlobalParallel();

  /**
   * Returns a new object of class '<em>Global Interruptible</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Global Interruptible</em>'.
   * @generated
   */
  GlobalInterruptible createGlobalInterruptible();

  /**
   * Returns a new object of class '<em>Global Interrupt</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Global Interrupt</em>'.
   * @generated
   */
  GlobalInterrupt createGlobalInterrupt();

  /**
   * Returns a new object of class '<em>Global Do</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Global Do</em>'.
   * @generated
   */
  GlobalDo createGlobalDo();

  /**
   * Returns a new object of class '<em>Local Protocol Decl</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Local Protocol Decl</em>'.
   * @generated
   */
  LocalProtocolDecl createLocalProtocolDecl();

  /**
   * Returns a new object of class '<em>Local Protocol Block</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Local Protocol Block</em>'.
   * @generated
   */
  LocalProtocolBlock createLocalProtocolBlock();

  /**
   * Returns a new object of class '<em>Llobal Interaction</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Llobal Interaction</em>'.
   * @generated
   */
  LlobalInteraction createLlobalInteraction();

  /**
   * Returns a new object of class '<em>Local Send</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Local Send</em>'.
   * @generated
   */
  LocalSend createLocalSend();

  /**
   * Returns a new object of class '<em>Local Receive</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Local Receive</em>'.
   * @generated
   */
  LocalReceive createLocalReceive();

  /**
   * Returns a new object of class '<em>Local Choice</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Local Choice</em>'.
   * @generated
   */
  LocalChoice createLocalChoice();

  /**
   * Returns a new object of class '<em>Local Recursion</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Local Recursion</em>'.
   * @generated
   */
  LocalRecursion createLocalRecursion();

  /**
   * Returns a new object of class '<em>Local Continue</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Local Continue</em>'.
   * @generated
   */
  LocalContinue createLocalContinue();

  /**
   * Returns a new object of class '<em>Local Parallel</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Local Parallel</em>'.
   * @generated
   */
  LocalParallel createLocalParallel();

  /**
   * Returns a new object of class '<em>Local Interruptible</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Local Interruptible</em>'.
   * @generated
   */
  LocalInterruptible createLocalInterruptible();

  /**
   * Returns a new object of class '<em>Local Throw</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Local Throw</em>'.
   * @generated
   */
  LocalThrow createLocalThrow();

  /**
   * Returns a new object of class '<em>Local Catch</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Local Catch</em>'.
   * @generated
   */
  LocalCatch createLocalCatch();

  /**
   * Returns a new object of class '<em>Local Do</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Local Do</em>'.
   * @generated
   */
  LocalDo createLocalDo();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  ScribbleDslPackage getScribbleDslPackage();

} //ScribbleDslFactory
