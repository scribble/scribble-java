/**
 */
package org.scribble.editor.dsl.scribbleDsl.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.scribble.editor.dsl.scribbleDsl.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ScribbleDslFactoryImpl extends EFactoryImpl implements ScribbleDslFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static ScribbleDslFactory init()
  {
    try
    {
      ScribbleDslFactory theScribbleDslFactory = (ScribbleDslFactory)EPackage.Registry.INSTANCE.getEFactory(ScribbleDslPackage.eNS_URI);
      if (theScribbleDslFactory != null)
      {
        return theScribbleDslFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new ScribbleDslFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ScribbleDslFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case ScribbleDslPackage.MODULE: return createModule();
      case ScribbleDslPackage.IMPORT_DECL: return createImportDecl();
      case ScribbleDslPackage.IMPORT_MODULE: return createImportModule();
      case ScribbleDslPackage.IMPORT_MEMBER: return createImportMember();
      case ScribbleDslPackage.PAYLOAD_TYPE_DECL: return createPayloadTypeDecl();
      case ScribbleDslPackage.MESSAGE_SIGNATURE: return createMessageSignature();
      case ScribbleDslPackage.PAYLOAD_ELEMENT: return createPayloadElement();
      case ScribbleDslPackage.GLOBAL_PROTOCOL_DECL: return createGlobalProtocolDecl();
      case ScribbleDslPackage.ROLE_DECL: return createRoleDecl();
      case ScribbleDslPackage.PARAMETER_DECL: return createParameterDecl();
      case ScribbleDslPackage.ROLE_INSTANTIATION: return createRoleInstantiation();
      case ScribbleDslPackage.ARGUMENT: return createArgument();
      case ScribbleDslPackage.GLOBAL_PROTOCOL_BLOCK: return createGlobalProtocolBlock();
      case ScribbleDslPackage.GLOBAL_INTERACTION: return createGlobalInteraction();
      case ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER: return createGlobalMessageTransfer();
      case ScribbleDslPackage.MESSAGE: return createMessage();
      case ScribbleDslPackage.GLOBAL_CHOICE: return createGlobalChoice();
      case ScribbleDslPackage.GLOBAL_RECURSION: return createGlobalRecursion();
      case ScribbleDslPackage.GLOBAL_CONTINUE: return createGlobalContinue();
      case ScribbleDslPackage.GLOBAL_PARALLEL: return createGlobalParallel();
      case ScribbleDslPackage.GLOBAL_INTERRUPTIBLE: return createGlobalInterruptible();
      case ScribbleDslPackage.GLOBAL_INTERRUPT: return createGlobalInterrupt();
      case ScribbleDslPackage.GLOBAL_DO: return createGlobalDo();
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL: return createLocalProtocolDecl();
      case ScribbleDslPackage.LOCAL_PROTOCOL_BLOCK: return createLocalProtocolBlock();
      case ScribbleDslPackage.LLOBAL_INTERACTION: return createLlobalInteraction();
      case ScribbleDslPackage.LOCAL_SEND: return createLocalSend();
      case ScribbleDslPackage.LOCAL_RECEIVE: return createLocalReceive();
      case ScribbleDslPackage.LOCAL_CHOICE: return createLocalChoice();
      case ScribbleDslPackage.LOCAL_RECURSION: return createLocalRecursion();
      case ScribbleDslPackage.LOCAL_CONTINUE: return createLocalContinue();
      case ScribbleDslPackage.LOCAL_PARALLEL: return createLocalParallel();
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE: return createLocalInterruptible();
      case ScribbleDslPackage.LOCAL_THROW: return createLocalThrow();
      case ScribbleDslPackage.LOCAL_CATCH: return createLocalCatch();
      case ScribbleDslPackage.LOCAL_DO: return createLocalDo();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Module createModule()
  {
    ModuleImpl module = new ModuleImpl();
    return module;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ImportDecl createImportDecl()
  {
    ImportDeclImpl importDecl = new ImportDeclImpl();
    return importDecl;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ImportModule createImportModule()
  {
    ImportModuleImpl importModule = new ImportModuleImpl();
    return importModule;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ImportMember createImportMember()
  {
    ImportMemberImpl importMember = new ImportMemberImpl();
    return importMember;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PayloadTypeDecl createPayloadTypeDecl()
  {
    PayloadTypeDeclImpl payloadTypeDecl = new PayloadTypeDeclImpl();
    return payloadTypeDecl;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MessageSignature createMessageSignature()
  {
    MessageSignatureImpl messageSignature = new MessageSignatureImpl();
    return messageSignature;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PayloadElement createPayloadElement()
  {
    PayloadElementImpl payloadElement = new PayloadElementImpl();
    return payloadElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GlobalProtocolDecl createGlobalProtocolDecl()
  {
    GlobalProtocolDeclImpl globalProtocolDecl = new GlobalProtocolDeclImpl();
    return globalProtocolDecl;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RoleDecl createRoleDecl()
  {
    RoleDeclImpl roleDecl = new RoleDeclImpl();
    return roleDecl;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ParameterDecl createParameterDecl()
  {
    ParameterDeclImpl parameterDecl = new ParameterDeclImpl();
    return parameterDecl;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RoleInstantiation createRoleInstantiation()
  {
    RoleInstantiationImpl roleInstantiation = new RoleInstantiationImpl();
    return roleInstantiation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Argument createArgument()
  {
    ArgumentImpl argument = new ArgumentImpl();
    return argument;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GlobalProtocolBlock createGlobalProtocolBlock()
  {
    GlobalProtocolBlockImpl globalProtocolBlock = new GlobalProtocolBlockImpl();
    return globalProtocolBlock;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GlobalInteraction createGlobalInteraction()
  {
    GlobalInteractionImpl globalInteraction = new GlobalInteractionImpl();
    return globalInteraction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GlobalMessageTransfer createGlobalMessageTransfer()
  {
    GlobalMessageTransferImpl globalMessageTransfer = new GlobalMessageTransferImpl();
    return globalMessageTransfer;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Message createMessage()
  {
    MessageImpl message = new MessageImpl();
    return message;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GlobalChoice createGlobalChoice()
  {
    GlobalChoiceImpl globalChoice = new GlobalChoiceImpl();
    return globalChoice;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GlobalRecursion createGlobalRecursion()
  {
    GlobalRecursionImpl globalRecursion = new GlobalRecursionImpl();
    return globalRecursion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GlobalContinue createGlobalContinue()
  {
    GlobalContinueImpl globalContinue = new GlobalContinueImpl();
    return globalContinue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GlobalParallel createGlobalParallel()
  {
    GlobalParallelImpl globalParallel = new GlobalParallelImpl();
    return globalParallel;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GlobalInterruptible createGlobalInterruptible()
  {
    GlobalInterruptibleImpl globalInterruptible = new GlobalInterruptibleImpl();
    return globalInterruptible;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GlobalInterrupt createGlobalInterrupt()
  {
    GlobalInterruptImpl globalInterrupt = new GlobalInterruptImpl();
    return globalInterrupt;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GlobalDo createGlobalDo()
  {
    GlobalDoImpl globalDo = new GlobalDoImpl();
    return globalDo;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalProtocolDecl createLocalProtocolDecl()
  {
    LocalProtocolDeclImpl localProtocolDecl = new LocalProtocolDeclImpl();
    return localProtocolDecl;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalProtocolBlock createLocalProtocolBlock()
  {
    LocalProtocolBlockImpl localProtocolBlock = new LocalProtocolBlockImpl();
    return localProtocolBlock;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LlobalInteraction createLlobalInteraction()
  {
    LlobalInteractionImpl llobalInteraction = new LlobalInteractionImpl();
    return llobalInteraction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalSend createLocalSend()
  {
    LocalSendImpl localSend = new LocalSendImpl();
    return localSend;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalReceive createLocalReceive()
  {
    LocalReceiveImpl localReceive = new LocalReceiveImpl();
    return localReceive;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalChoice createLocalChoice()
  {
    LocalChoiceImpl localChoice = new LocalChoiceImpl();
    return localChoice;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalRecursion createLocalRecursion()
  {
    LocalRecursionImpl localRecursion = new LocalRecursionImpl();
    return localRecursion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalContinue createLocalContinue()
  {
    LocalContinueImpl localContinue = new LocalContinueImpl();
    return localContinue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalParallel createLocalParallel()
  {
    LocalParallelImpl localParallel = new LocalParallelImpl();
    return localParallel;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalInterruptible createLocalInterruptible()
  {
    LocalInterruptibleImpl localInterruptible = new LocalInterruptibleImpl();
    return localInterruptible;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalThrow createLocalThrow()
  {
    LocalThrowImpl localThrow = new LocalThrowImpl();
    return localThrow;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalCatch createLocalCatch()
  {
    LocalCatchImpl localCatch = new LocalCatchImpl();
    return localCatch;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalDo createLocalDo()
  {
    LocalDoImpl localDo = new LocalDoImpl();
    return localDo;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ScribbleDslPackage getScribbleDslPackage()
  {
    return (ScribbleDslPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static ScribbleDslPackage getPackage()
  {
    return ScribbleDslPackage.eINSTANCE;
  }

} //ScribbleDslFactoryImpl
