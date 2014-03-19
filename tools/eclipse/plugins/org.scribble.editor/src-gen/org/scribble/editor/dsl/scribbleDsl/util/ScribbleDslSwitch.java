/**
 */
package org.scribble.editor.dsl.scribbleDsl.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.scribble.editor.dsl.scribbleDsl.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage
 * @generated
 */
public class ScribbleDslSwitch<T> extends Switch<T>
{
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static ScribbleDslPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ScribbleDslSwitch()
  {
    if (modelPackage == null)
    {
      modelPackage = ScribbleDslPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @parameter ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage)
  {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject)
  {
    switch (classifierID)
    {
      case ScribbleDslPackage.MODULE:
      {
        Module module = (Module)theEObject;
        T result = caseModule(module);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.MODULE_DECL:
      {
        ModuleDecl moduleDecl = (ModuleDecl)theEObject;
        T result = caseModuleDecl(moduleDecl);
        if (result == null) result = caseModule(moduleDecl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.IMPORT_DECL:
      {
        ImportDecl importDecl = (ImportDecl)theEObject;
        T result = caseImportDecl(importDecl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.IMPORT_MODULE:
      {
        ImportModule importModule = (ImportModule)theEObject;
        T result = caseImportModule(importModule);
        if (result == null) result = caseImportDecl(importModule);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.IMPORT_MEMBER:
      {
        ImportMember importMember = (ImportMember)theEObject;
        T result = caseImportMember(importMember);
        if (result == null) result = caseImportDecl(importMember);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.PAYLOAD_TYPE_DECL:
      {
        PayloadTypeDecl payloadTypeDecl = (PayloadTypeDecl)theEObject;
        T result = casePayloadTypeDecl(payloadTypeDecl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.MESSAGE_SIGNATURE:
      {
        MessageSignature messageSignature = (MessageSignature)theEObject;
        T result = caseMessageSignature(messageSignature);
        if (result == null) result = caseMessage(messageSignature);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.PAYLOAD_ELEMENT:
      {
        PayloadElement payloadElement = (PayloadElement)theEObject;
        T result = casePayloadElement(payloadElement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.GLOBAL_PROTOCOL_DECL:
      {
        GlobalProtocolDecl globalProtocolDecl = (GlobalProtocolDecl)theEObject;
        T result = caseGlobalProtocolDecl(globalProtocolDecl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.ROLE_DECL:
      {
        RoleDecl roleDecl = (RoleDecl)theEObject;
        T result = caseRoleDecl(roleDecl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.PARAMETER_DECL:
      {
        ParameterDecl parameterDecl = (ParameterDecl)theEObject;
        T result = caseParameterDecl(parameterDecl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.ROLE_INSTANTIATION:
      {
        RoleInstantiation roleInstantiation = (RoleInstantiation)theEObject;
        T result = caseRoleInstantiation(roleInstantiation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.ARGUMENT:
      {
        Argument argument = (Argument)theEObject;
        T result = caseArgument(argument);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.GLOBAL_PROTOCOL_BLOCK:
      {
        GlobalProtocolBlock globalProtocolBlock = (GlobalProtocolBlock)theEObject;
        T result = caseGlobalProtocolBlock(globalProtocolBlock);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.GLOBAL_INTERACTION:
      {
        GlobalInteraction globalInteraction = (GlobalInteraction)theEObject;
        T result = caseGlobalInteraction(globalInteraction);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER:
      {
        GlobalMessageTransfer globalMessageTransfer = (GlobalMessageTransfer)theEObject;
        T result = caseGlobalMessageTransfer(globalMessageTransfer);
        if (result == null) result = caseGlobalInteraction(globalMessageTransfer);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.MESSAGE:
      {
        Message message = (Message)theEObject;
        T result = caseMessage(message);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.GLOBAL_CHOICE:
      {
        GlobalChoice globalChoice = (GlobalChoice)theEObject;
        T result = caseGlobalChoice(globalChoice);
        if (result == null) result = caseGlobalInteraction(globalChoice);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.GLOBAL_RECURSION:
      {
        GlobalRecursion globalRecursion = (GlobalRecursion)theEObject;
        T result = caseGlobalRecursion(globalRecursion);
        if (result == null) result = caseGlobalInteraction(globalRecursion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.GLOBAL_CONTINUE:
      {
        GlobalContinue globalContinue = (GlobalContinue)theEObject;
        T result = caseGlobalContinue(globalContinue);
        if (result == null) result = caseGlobalInteraction(globalContinue);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.GLOBAL_PARALLEL:
      {
        GlobalParallel globalParallel = (GlobalParallel)theEObject;
        T result = caseGlobalParallel(globalParallel);
        if (result == null) result = caseGlobalInteraction(globalParallel);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.GLOBAL_INTERRUPTIBLE:
      {
        GlobalInterruptible globalInterruptible = (GlobalInterruptible)theEObject;
        T result = caseGlobalInterruptible(globalInterruptible);
        if (result == null) result = caseGlobalInteraction(globalInterruptible);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.GLOBAL_INTERRUPT:
      {
        GlobalInterrupt globalInterrupt = (GlobalInterrupt)theEObject;
        T result = caseGlobalInterrupt(globalInterrupt);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ScribbleDslPackage.GLOBAL_DO:
      {
        GlobalDo globalDo = (GlobalDo)theEObject;
        T result = caseGlobalDo(globalDo);
        if (result == null) result = caseGlobalInteraction(globalDo);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Module</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Module</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseModule(Module object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Module Decl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Module Decl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseModuleDecl(ModuleDecl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Import Decl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Import Decl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseImportDecl(ImportDecl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Import Module</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Import Module</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseImportModule(ImportModule object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Import Member</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Import Member</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseImportMember(ImportMember object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Payload Type Decl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Payload Type Decl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePayloadTypeDecl(PayloadTypeDecl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Message Signature</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Message Signature</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMessageSignature(MessageSignature object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Payload Element</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Payload Element</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePayloadElement(PayloadElement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Global Protocol Decl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Global Protocol Decl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGlobalProtocolDecl(GlobalProtocolDecl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Role Decl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Role Decl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRoleDecl(RoleDecl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Parameter Decl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Parameter Decl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseParameterDecl(ParameterDecl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Role Instantiation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Role Instantiation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRoleInstantiation(RoleInstantiation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Argument</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Argument</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseArgument(Argument object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Global Protocol Block</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Global Protocol Block</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGlobalProtocolBlock(GlobalProtocolBlock object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Global Interaction</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Global Interaction</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGlobalInteraction(GlobalInteraction object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Global Message Transfer</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Global Message Transfer</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGlobalMessageTransfer(GlobalMessageTransfer object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Message</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Message</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMessage(Message object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Global Choice</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Global Choice</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGlobalChoice(GlobalChoice object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Global Recursion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Global Recursion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGlobalRecursion(GlobalRecursion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Global Continue</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Global Continue</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGlobalContinue(GlobalContinue object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Global Parallel</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Global Parallel</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGlobalParallel(GlobalParallel object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Global Interruptible</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Global Interruptible</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGlobalInterruptible(GlobalInterruptible object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Global Interrupt</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Global Interrupt</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGlobalInterrupt(GlobalInterrupt object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Global Do</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Global Do</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGlobalDo(GlobalDo object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object)
  {
    return null;
  }

} //ScribbleDslSwitch
