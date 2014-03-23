/**
 */
package org.scribble.editor.dsl.scribbleDsl.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.scribble.editor.dsl.scribbleDsl.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage
 * @generated
 */
public class ScribbleDslAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static ScribbleDslPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ScribbleDslAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = ScribbleDslPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ScribbleDslSwitch<Adapter> modelSwitch =
    new ScribbleDslSwitch<Adapter>()
    {
      @Override
      public Adapter caseModule(Module object)
      {
        return createModuleAdapter();
      }
      @Override
      public Adapter caseImportDecl(ImportDecl object)
      {
        return createImportDeclAdapter();
      }
      @Override
      public Adapter caseImportModule(ImportModule object)
      {
        return createImportModuleAdapter();
      }
      @Override
      public Adapter caseImportMember(ImportMember object)
      {
        return createImportMemberAdapter();
      }
      @Override
      public Adapter casePayloadTypeDecl(PayloadTypeDecl object)
      {
        return createPayloadTypeDeclAdapter();
      }
      @Override
      public Adapter caseMessageSignature(MessageSignature object)
      {
        return createMessageSignatureAdapter();
      }
      @Override
      public Adapter casePayloadElement(PayloadElement object)
      {
        return createPayloadElementAdapter();
      }
      @Override
      public Adapter caseGlobalProtocolDecl(GlobalProtocolDecl object)
      {
        return createGlobalProtocolDeclAdapter();
      }
      @Override
      public Adapter caseRoleDecl(RoleDecl object)
      {
        return createRoleDeclAdapter();
      }
      @Override
      public Adapter caseParameterDecl(ParameterDecl object)
      {
        return createParameterDeclAdapter();
      }
      @Override
      public Adapter caseRoleInstantiation(RoleInstantiation object)
      {
        return createRoleInstantiationAdapter();
      }
      @Override
      public Adapter caseArgument(Argument object)
      {
        return createArgumentAdapter();
      }
      @Override
      public Adapter caseGlobalProtocolBlock(GlobalProtocolBlock object)
      {
        return createGlobalProtocolBlockAdapter();
      }
      @Override
      public Adapter caseGlobalInteraction(GlobalInteraction object)
      {
        return createGlobalInteractionAdapter();
      }
      @Override
      public Adapter caseGlobalMessageTransfer(GlobalMessageTransfer object)
      {
        return createGlobalMessageTransferAdapter();
      }
      @Override
      public Adapter caseMessage(Message object)
      {
        return createMessageAdapter();
      }
      @Override
      public Adapter caseGlobalChoice(GlobalChoice object)
      {
        return createGlobalChoiceAdapter();
      }
      @Override
      public Adapter caseGlobalRecursion(GlobalRecursion object)
      {
        return createGlobalRecursionAdapter();
      }
      @Override
      public Adapter caseGlobalContinue(GlobalContinue object)
      {
        return createGlobalContinueAdapter();
      }
      @Override
      public Adapter caseGlobalParallel(GlobalParallel object)
      {
        return createGlobalParallelAdapter();
      }
      @Override
      public Adapter caseGlobalInterruptible(GlobalInterruptible object)
      {
        return createGlobalInterruptibleAdapter();
      }
      @Override
      public Adapter caseGlobalInterrupt(GlobalInterrupt object)
      {
        return createGlobalInterruptAdapter();
      }
      @Override
      public Adapter caseGlobalDo(GlobalDo object)
      {
        return createGlobalDoAdapter();
      }
      @Override
      public Adapter caseLocalProtocolDecl(LocalProtocolDecl object)
      {
        return createLocalProtocolDeclAdapter();
      }
      @Override
      public Adapter caseLocalProtocolBlock(LocalProtocolBlock object)
      {
        return createLocalProtocolBlockAdapter();
      }
      @Override
      public Adapter caseLlobalInteraction(LlobalInteraction object)
      {
        return createLlobalInteractionAdapter();
      }
      @Override
      public Adapter caseLocalSend(LocalSend object)
      {
        return createLocalSendAdapter();
      }
      @Override
      public Adapter caseLocalReceive(LocalReceive object)
      {
        return createLocalReceiveAdapter();
      }
      @Override
      public Adapter caseLocalChoice(LocalChoice object)
      {
        return createLocalChoiceAdapter();
      }
      @Override
      public Adapter caseLocalRecursion(LocalRecursion object)
      {
        return createLocalRecursionAdapter();
      }
      @Override
      public Adapter caseLocalContinue(LocalContinue object)
      {
        return createLocalContinueAdapter();
      }
      @Override
      public Adapter caseLocalParallel(LocalParallel object)
      {
        return createLocalParallelAdapter();
      }
      @Override
      public Adapter caseLocalInterruptible(LocalInterruptible object)
      {
        return createLocalInterruptibleAdapter();
      }
      @Override
      public Adapter caseLocalThrow(LocalThrow object)
      {
        return createLocalThrowAdapter();
      }
      @Override
      public Adapter caseLocalCatch(LocalCatch object)
      {
        return createLocalCatchAdapter();
      }
      @Override
      public Adapter caseLocalDo(LocalDo object)
      {
        return createLocalDoAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.Module <em>Module</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.Module
   * @generated
   */
  public Adapter createModuleAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.ImportDecl <em>Import Decl</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.ImportDecl
   * @generated
   */
  public Adapter createImportDeclAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.ImportModule <em>Import Module</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.ImportModule
   * @generated
   */
  public Adapter createImportModuleAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.ImportMember <em>Import Member</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.ImportMember
   * @generated
   */
  public Adapter createImportMemberAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.PayloadTypeDecl <em>Payload Type Decl</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.PayloadTypeDecl
   * @generated
   */
  public Adapter createPayloadTypeDeclAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.MessageSignature <em>Message Signature</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.MessageSignature
   * @generated
   */
  public Adapter createMessageSignatureAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.PayloadElement <em>Payload Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.PayloadElement
   * @generated
   */
  public Adapter createPayloadElementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl <em>Global Protocol Decl</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl
   * @generated
   */
  public Adapter createGlobalProtocolDeclAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.RoleDecl <em>Role Decl</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.RoleDecl
   * @generated
   */
  public Adapter createRoleDeclAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.ParameterDecl <em>Parameter Decl</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.ParameterDecl
   * @generated
   */
  public Adapter createParameterDeclAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.RoleInstantiation <em>Role Instantiation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.RoleInstantiation
   * @generated
   */
  public Adapter createRoleInstantiationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.Argument <em>Argument</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.Argument
   * @generated
   */
  public Adapter createArgumentAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalProtocolBlock <em>Global Protocol Block</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalProtocolBlock
   * @generated
   */
  public Adapter createGlobalProtocolBlockAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalInteraction <em>Global Interaction</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalInteraction
   * @generated
   */
  public Adapter createGlobalInteractionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalMessageTransfer <em>Global Message Transfer</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalMessageTransfer
   * @generated
   */
  public Adapter createGlobalMessageTransferAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.Message <em>Message</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.Message
   * @generated
   */
  public Adapter createMessageAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalChoice <em>Global Choice</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalChoice
   * @generated
   */
  public Adapter createGlobalChoiceAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalRecursion <em>Global Recursion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalRecursion
   * @generated
   */
  public Adapter createGlobalRecursionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalContinue <em>Global Continue</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalContinue
   * @generated
   */
  public Adapter createGlobalContinueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalParallel <em>Global Parallel</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalParallel
   * @generated
   */
  public Adapter createGlobalParallelAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalInterruptible <em>Global Interruptible</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalInterruptible
   * @generated
   */
  public Adapter createGlobalInterruptibleAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalInterrupt <em>Global Interrupt</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalInterrupt
   * @generated
   */
  public Adapter createGlobalInterruptAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalDo <em>Global Do</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalDo
   * @generated
   */
  public Adapter createGlobalDoAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.LocalProtocolDecl <em>Local Protocol Decl</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.LocalProtocolDecl
   * @generated
   */
  public Adapter createLocalProtocolDeclAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.LocalProtocolBlock <em>Local Protocol Block</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.LocalProtocolBlock
   * @generated
   */
  public Adapter createLocalProtocolBlockAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.LlobalInteraction <em>Llobal Interaction</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.LlobalInteraction
   * @generated
   */
  public Adapter createLlobalInteractionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.LocalSend <em>Local Send</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.LocalSend
   * @generated
   */
  public Adapter createLocalSendAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.LocalReceive <em>Local Receive</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.LocalReceive
   * @generated
   */
  public Adapter createLocalReceiveAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.LocalChoice <em>Local Choice</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.LocalChoice
   * @generated
   */
  public Adapter createLocalChoiceAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.LocalRecursion <em>Local Recursion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.LocalRecursion
   * @generated
   */
  public Adapter createLocalRecursionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.LocalContinue <em>Local Continue</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.LocalContinue
   * @generated
   */
  public Adapter createLocalContinueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.LocalParallel <em>Local Parallel</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.LocalParallel
   * @generated
   */
  public Adapter createLocalParallelAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.LocalInterruptible <em>Local Interruptible</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.LocalInterruptible
   * @generated
   */
  public Adapter createLocalInterruptibleAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.LocalThrow <em>Local Throw</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.LocalThrow
   * @generated
   */
  public Adapter createLocalThrowAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.LocalCatch <em>Local Catch</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.LocalCatch
   * @generated
   */
  public Adapter createLocalCatchAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.editor.dsl.scribbleDsl.LocalDo <em>Local Do</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.editor.dsl.scribbleDsl.LocalDo
   * @generated
   */
  public Adapter createLocalDoAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //ScribbleDslAdapterFactory
