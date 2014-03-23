/**
 */
package org.scribble.editor.dsl.scribbleDsl.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.scribble.editor.dsl.scribbleDsl.Argument;
import org.scribble.editor.dsl.scribbleDsl.GlobalChoice;
import org.scribble.editor.dsl.scribbleDsl.GlobalContinue;
import org.scribble.editor.dsl.scribbleDsl.GlobalDo;
import org.scribble.editor.dsl.scribbleDsl.GlobalInteraction;
import org.scribble.editor.dsl.scribbleDsl.GlobalInterrupt;
import org.scribble.editor.dsl.scribbleDsl.GlobalInterruptible;
import org.scribble.editor.dsl.scribbleDsl.GlobalMessageTransfer;
import org.scribble.editor.dsl.scribbleDsl.GlobalParallel;
import org.scribble.editor.dsl.scribbleDsl.GlobalProtocolBlock;
import org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl;
import org.scribble.editor.dsl.scribbleDsl.GlobalRecursion;
import org.scribble.editor.dsl.scribbleDsl.ImportDecl;
import org.scribble.editor.dsl.scribbleDsl.ImportMember;
import org.scribble.editor.dsl.scribbleDsl.ImportModule;
import org.scribble.editor.dsl.scribbleDsl.LlobalInteraction;
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
import org.scribble.editor.dsl.scribbleDsl.ScribbleDslFactory;
import org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ScribbleDslPackageImpl extends EPackageImpl implements ScribbleDslPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass moduleEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass importDeclEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass importModuleEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass importMemberEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass payloadTypeDeclEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass messageSignatureEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass payloadElementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass globalProtocolDeclEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass roleDeclEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass parameterDeclEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass roleInstantiationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass argumentEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass globalProtocolBlockEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass globalInteractionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass globalMessageTransferEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass messageEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass globalChoiceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass globalRecursionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass globalContinueEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass globalParallelEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass globalInterruptibleEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass globalInterruptEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass globalDoEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass localProtocolDeclEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass localProtocolBlockEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass llobalInteractionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass localSendEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass localReceiveEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass localChoiceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass localRecursionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass localContinueEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass localParallelEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass localInterruptibleEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass localThrowEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass localCatchEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass localDoEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private ScribbleDslPackageImpl()
  {
    super(eNS_URI, ScribbleDslFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * 
   * <p>This method is used to initialize {@link ScribbleDslPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static ScribbleDslPackage init()
  {
    if (isInited) return (ScribbleDslPackage)EPackage.Registry.INSTANCE.getEPackage(ScribbleDslPackage.eNS_URI);

    // Obtain or create and register package
    ScribbleDslPackageImpl theScribbleDslPackage = (ScribbleDslPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ScribbleDslPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ScribbleDslPackageImpl());

    isInited = true;

    // Create package meta-data objects
    theScribbleDslPackage.createPackageContents();

    // Initialize created meta-data
    theScribbleDslPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theScribbleDslPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(ScribbleDslPackage.eNS_URI, theScribbleDslPackage);
    return theScribbleDslPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getModule()
  {
    return moduleEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getModule_Name()
  {
    return (EAttribute)moduleEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getModule_Imports()
  {
    return (EReference)moduleEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getModule_Types()
  {
    return (EReference)moduleEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getModule_Globals()
  {
    return (EReference)moduleEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getModule_Locals()
  {
    return (EReference)moduleEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getImportDecl()
  {
    return importDeclEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getImportDecl_Name()
  {
    return (EAttribute)importDeclEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getImportDecl_Alias()
  {
    return (EAttribute)importDeclEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getImportModule()
  {
    return importModuleEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getImportMember()
  {
    return importMemberEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getImportMember_Member()
  {
    return (EAttribute)importMemberEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getPayloadTypeDecl()
  {
    return payloadTypeDeclEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getPayloadTypeDecl_Schema()
  {
    return (EAttribute)payloadTypeDeclEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getPayloadTypeDecl_Type()
  {
    return (EAttribute)payloadTypeDeclEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getPayloadTypeDecl_Location()
  {
    return (EAttribute)payloadTypeDeclEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getPayloadTypeDecl_Alias()
  {
    return (EAttribute)payloadTypeDeclEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMessageSignature()
  {
    return messageSignatureEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMessageSignature_Operator()
  {
    return (EAttribute)messageSignatureEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMessageSignature_Types()
  {
    return (EReference)messageSignatureEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getPayloadElement()
  {
    return payloadElementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getPayloadElement_Name()
  {
    return (EAttribute)payloadElementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getPayloadElement_Type()
  {
    return (EAttribute)payloadElementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getGlobalProtocolDecl()
  {
    return globalProtocolDeclEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getGlobalProtocolDecl_Name()
  {
    return (EAttribute)globalProtocolDeclEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGlobalProtocolDecl_Parameters()
  {
    return (EReference)globalProtocolDeclEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGlobalProtocolDecl_Roles()
  {
    return (EReference)globalProtocolDeclEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGlobalProtocolDecl_Block()
  {
    return (EReference)globalProtocolDeclEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getGlobalProtocolDecl_Instantiates()
  {
    return (EAttribute)globalProtocolDeclEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGlobalProtocolDecl_Arguments()
  {
    return (EReference)globalProtocolDeclEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGlobalProtocolDecl_RoleInstantiations()
  {
    return (EReference)globalProtocolDeclEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRoleDecl()
  {
    return roleDeclEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getRoleDecl_Name()
  {
    return (EAttribute)roleDeclEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getRoleDecl_Alias()
  {
    return (EAttribute)roleDeclEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getParameterDecl()
  {
    return parameterDeclEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParameterDecl_Name()
  {
    return (EAttribute)parameterDeclEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParameterDecl_Alias()
  {
    return (EAttribute)parameterDeclEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRoleInstantiation()
  {
    return roleInstantiationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getRoleInstantiation_Name()
  {
    return (EAttribute)roleInstantiationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getRoleInstantiation_Alias()
  {
    return (EAttribute)roleInstantiationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getArgument()
  {
    return argumentEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getArgument_Signature()
  {
    return (EReference)argumentEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getArgument_Alias()
  {
    return (EAttribute)argumentEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getArgument_Name()
  {
    return (EAttribute)argumentEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getGlobalProtocolBlock()
  {
    return globalProtocolBlockEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGlobalProtocolBlock_Activities()
  {
    return (EReference)globalProtocolBlockEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getGlobalInteraction()
  {
    return globalInteractionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getGlobalMessageTransfer()
  {
    return globalMessageTransferEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGlobalMessageTransfer_Message()
  {
    return (EReference)globalMessageTransferEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getGlobalMessageTransfer_FromRole()
  {
    return (EAttribute)globalMessageTransferEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getGlobalMessageTransfer_ToRoles()
  {
    return (EAttribute)globalMessageTransferEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMessage()
  {
    return messageEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMessage_Signature()
  {
    return (EReference)messageEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMessage_Parameter()
  {
    return (EAttribute)messageEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getGlobalChoice()
  {
    return globalChoiceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getGlobalChoice_Role()
  {
    return (EAttribute)globalChoiceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGlobalChoice_Blocks()
  {
    return (EReference)globalChoiceEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getGlobalRecursion()
  {
    return globalRecursionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getGlobalRecursion_Label()
  {
    return (EAttribute)globalRecursionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGlobalRecursion_Block()
  {
    return (EReference)globalRecursionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getGlobalContinue()
  {
    return globalContinueEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getGlobalContinue_Label()
  {
    return (EAttribute)globalContinueEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getGlobalParallel()
  {
    return globalParallelEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGlobalParallel_Blocks()
  {
    return (EReference)globalParallelEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getGlobalInterruptible()
  {
    return globalInterruptibleEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getGlobalInterruptible_Scope()
  {
    return (EAttribute)globalInterruptibleEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGlobalInterruptible_Block()
  {
    return (EReference)globalInterruptibleEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGlobalInterruptible_Interrupts()
  {
    return (EReference)globalInterruptibleEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getGlobalInterrupt()
  {
    return globalInterruptEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGlobalInterrupt_Messages()
  {
    return (EReference)globalInterruptEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getGlobalInterrupt_Role()
  {
    return (EAttribute)globalInterruptEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getGlobalDo()
  {
    return globalDoEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getGlobalDo_Scope()
  {
    return (EAttribute)globalDoEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getGlobalDo_Member()
  {
    return (EAttribute)globalDoEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGlobalDo_Arguments()
  {
    return (EReference)globalDoEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGlobalDo_Roles()
  {
    return (EReference)globalDoEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLocalProtocolDecl()
  {
    return localProtocolDeclEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalProtocolDecl_Name()
  {
    return (EAttribute)localProtocolDeclEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalProtocolDecl_Role()
  {
    return (EAttribute)localProtocolDeclEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLocalProtocolDecl_Parameters()
  {
    return (EReference)localProtocolDeclEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLocalProtocolDecl_Roles()
  {
    return (EReference)localProtocolDeclEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLocalProtocolDecl_Block()
  {
    return (EReference)localProtocolDeclEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalProtocolDecl_Instantiates()
  {
    return (EAttribute)localProtocolDeclEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLocalProtocolDecl_Arguments()
  {
    return (EReference)localProtocolDeclEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLocalProtocolDecl_RoleInstantiations()
  {
    return (EReference)localProtocolDeclEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLocalProtocolBlock()
  {
    return localProtocolBlockEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLocalProtocolBlock_Activities()
  {
    return (EReference)localProtocolBlockEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLlobalInteraction()
  {
    return llobalInteractionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLocalSend()
  {
    return localSendEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLocalSend_Message()
  {
    return (EReference)localSendEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalSend_ToRoles()
  {
    return (EAttribute)localSendEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLocalReceive()
  {
    return localReceiveEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLocalReceive_Message()
  {
    return (EReference)localReceiveEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalReceive_FromRole()
  {
    return (EAttribute)localReceiveEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLocalChoice()
  {
    return localChoiceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalChoice_Role()
  {
    return (EAttribute)localChoiceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLocalChoice_Blocks()
  {
    return (EReference)localChoiceEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLocalRecursion()
  {
    return localRecursionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalRecursion_Label()
  {
    return (EAttribute)localRecursionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLocalRecursion_Block()
  {
    return (EReference)localRecursionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLocalContinue()
  {
    return localContinueEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalContinue_Label()
  {
    return (EAttribute)localContinueEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLocalParallel()
  {
    return localParallelEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLocalParallel_Blocks()
  {
    return (EReference)localParallelEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLocalInterruptible()
  {
    return localInterruptibleEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalInterruptible_Scope()
  {
    return (EAttribute)localInterruptibleEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLocalInterruptible_Block()
  {
    return (EReference)localInterruptibleEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLocalInterruptible_Throw()
  {
    return (EReference)localInterruptibleEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLocalInterruptible_Catches()
  {
    return (EReference)localInterruptibleEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLocalThrow()
  {
    return localThrowEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLocalThrow_Messages()
  {
    return (EReference)localThrowEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalThrow_ToRoles()
  {
    return (EAttribute)localThrowEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLocalCatch()
  {
    return localCatchEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLocalCatch_Messages()
  {
    return (EReference)localCatchEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalCatch_FromRole()
  {
    return (EAttribute)localCatchEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLocalDo()
  {
    return localDoEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalDo_Scope()
  {
    return (EAttribute)localDoEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalDo_Member()
  {
    return (EAttribute)localDoEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLocalDo_Arguments()
  {
    return (EReference)localDoEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLocalDo_Roles()
  {
    return (EReference)localDoEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ScribbleDslFactory getScribbleDslFactory()
  {
    return (ScribbleDslFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    moduleEClass = createEClass(MODULE);
    createEAttribute(moduleEClass, MODULE__NAME);
    createEReference(moduleEClass, MODULE__IMPORTS);
    createEReference(moduleEClass, MODULE__TYPES);
    createEReference(moduleEClass, MODULE__GLOBALS);
    createEReference(moduleEClass, MODULE__LOCALS);

    importDeclEClass = createEClass(IMPORT_DECL);
    createEAttribute(importDeclEClass, IMPORT_DECL__NAME);
    createEAttribute(importDeclEClass, IMPORT_DECL__ALIAS);

    importModuleEClass = createEClass(IMPORT_MODULE);

    importMemberEClass = createEClass(IMPORT_MEMBER);
    createEAttribute(importMemberEClass, IMPORT_MEMBER__MEMBER);

    payloadTypeDeclEClass = createEClass(PAYLOAD_TYPE_DECL);
    createEAttribute(payloadTypeDeclEClass, PAYLOAD_TYPE_DECL__SCHEMA);
    createEAttribute(payloadTypeDeclEClass, PAYLOAD_TYPE_DECL__TYPE);
    createEAttribute(payloadTypeDeclEClass, PAYLOAD_TYPE_DECL__LOCATION);
    createEAttribute(payloadTypeDeclEClass, PAYLOAD_TYPE_DECL__ALIAS);

    messageSignatureEClass = createEClass(MESSAGE_SIGNATURE);
    createEAttribute(messageSignatureEClass, MESSAGE_SIGNATURE__OPERATOR);
    createEReference(messageSignatureEClass, MESSAGE_SIGNATURE__TYPES);

    payloadElementEClass = createEClass(PAYLOAD_ELEMENT);
    createEAttribute(payloadElementEClass, PAYLOAD_ELEMENT__NAME);
    createEAttribute(payloadElementEClass, PAYLOAD_ELEMENT__TYPE);

    globalProtocolDeclEClass = createEClass(GLOBAL_PROTOCOL_DECL);
    createEAttribute(globalProtocolDeclEClass, GLOBAL_PROTOCOL_DECL__NAME);
    createEReference(globalProtocolDeclEClass, GLOBAL_PROTOCOL_DECL__PARAMETERS);
    createEReference(globalProtocolDeclEClass, GLOBAL_PROTOCOL_DECL__ROLES);
    createEReference(globalProtocolDeclEClass, GLOBAL_PROTOCOL_DECL__BLOCK);
    createEAttribute(globalProtocolDeclEClass, GLOBAL_PROTOCOL_DECL__INSTANTIATES);
    createEReference(globalProtocolDeclEClass, GLOBAL_PROTOCOL_DECL__ARGUMENTS);
    createEReference(globalProtocolDeclEClass, GLOBAL_PROTOCOL_DECL__ROLE_INSTANTIATIONS);

    roleDeclEClass = createEClass(ROLE_DECL);
    createEAttribute(roleDeclEClass, ROLE_DECL__NAME);
    createEAttribute(roleDeclEClass, ROLE_DECL__ALIAS);

    parameterDeclEClass = createEClass(PARAMETER_DECL);
    createEAttribute(parameterDeclEClass, PARAMETER_DECL__NAME);
    createEAttribute(parameterDeclEClass, PARAMETER_DECL__ALIAS);

    roleInstantiationEClass = createEClass(ROLE_INSTANTIATION);
    createEAttribute(roleInstantiationEClass, ROLE_INSTANTIATION__NAME);
    createEAttribute(roleInstantiationEClass, ROLE_INSTANTIATION__ALIAS);

    argumentEClass = createEClass(ARGUMENT);
    createEReference(argumentEClass, ARGUMENT__SIGNATURE);
    createEAttribute(argumentEClass, ARGUMENT__ALIAS);
    createEAttribute(argumentEClass, ARGUMENT__NAME);

    globalProtocolBlockEClass = createEClass(GLOBAL_PROTOCOL_BLOCK);
    createEReference(globalProtocolBlockEClass, GLOBAL_PROTOCOL_BLOCK__ACTIVITIES);

    globalInteractionEClass = createEClass(GLOBAL_INTERACTION);

    globalMessageTransferEClass = createEClass(GLOBAL_MESSAGE_TRANSFER);
    createEReference(globalMessageTransferEClass, GLOBAL_MESSAGE_TRANSFER__MESSAGE);
    createEAttribute(globalMessageTransferEClass, GLOBAL_MESSAGE_TRANSFER__FROM_ROLE);
    createEAttribute(globalMessageTransferEClass, GLOBAL_MESSAGE_TRANSFER__TO_ROLES);

    messageEClass = createEClass(MESSAGE);
    createEReference(messageEClass, MESSAGE__SIGNATURE);
    createEAttribute(messageEClass, MESSAGE__PARAMETER);

    globalChoiceEClass = createEClass(GLOBAL_CHOICE);
    createEAttribute(globalChoiceEClass, GLOBAL_CHOICE__ROLE);
    createEReference(globalChoiceEClass, GLOBAL_CHOICE__BLOCKS);

    globalRecursionEClass = createEClass(GLOBAL_RECURSION);
    createEAttribute(globalRecursionEClass, GLOBAL_RECURSION__LABEL);
    createEReference(globalRecursionEClass, GLOBAL_RECURSION__BLOCK);

    globalContinueEClass = createEClass(GLOBAL_CONTINUE);
    createEAttribute(globalContinueEClass, GLOBAL_CONTINUE__LABEL);

    globalParallelEClass = createEClass(GLOBAL_PARALLEL);
    createEReference(globalParallelEClass, GLOBAL_PARALLEL__BLOCKS);

    globalInterruptibleEClass = createEClass(GLOBAL_INTERRUPTIBLE);
    createEAttribute(globalInterruptibleEClass, GLOBAL_INTERRUPTIBLE__SCOPE);
    createEReference(globalInterruptibleEClass, GLOBAL_INTERRUPTIBLE__BLOCK);
    createEReference(globalInterruptibleEClass, GLOBAL_INTERRUPTIBLE__INTERRUPTS);

    globalInterruptEClass = createEClass(GLOBAL_INTERRUPT);
    createEReference(globalInterruptEClass, GLOBAL_INTERRUPT__MESSAGES);
    createEAttribute(globalInterruptEClass, GLOBAL_INTERRUPT__ROLE);

    globalDoEClass = createEClass(GLOBAL_DO);
    createEAttribute(globalDoEClass, GLOBAL_DO__SCOPE);
    createEAttribute(globalDoEClass, GLOBAL_DO__MEMBER);
    createEReference(globalDoEClass, GLOBAL_DO__ARGUMENTS);
    createEReference(globalDoEClass, GLOBAL_DO__ROLES);

    localProtocolDeclEClass = createEClass(LOCAL_PROTOCOL_DECL);
    createEAttribute(localProtocolDeclEClass, LOCAL_PROTOCOL_DECL__NAME);
    createEAttribute(localProtocolDeclEClass, LOCAL_PROTOCOL_DECL__ROLE);
    createEReference(localProtocolDeclEClass, LOCAL_PROTOCOL_DECL__PARAMETERS);
    createEReference(localProtocolDeclEClass, LOCAL_PROTOCOL_DECL__ROLES);
    createEReference(localProtocolDeclEClass, LOCAL_PROTOCOL_DECL__BLOCK);
    createEAttribute(localProtocolDeclEClass, LOCAL_PROTOCOL_DECL__INSTANTIATES);
    createEReference(localProtocolDeclEClass, LOCAL_PROTOCOL_DECL__ARGUMENTS);
    createEReference(localProtocolDeclEClass, LOCAL_PROTOCOL_DECL__ROLE_INSTANTIATIONS);

    localProtocolBlockEClass = createEClass(LOCAL_PROTOCOL_BLOCK);
    createEReference(localProtocolBlockEClass, LOCAL_PROTOCOL_BLOCK__ACTIVITIES);

    llobalInteractionEClass = createEClass(LLOBAL_INTERACTION);

    localSendEClass = createEClass(LOCAL_SEND);
    createEReference(localSendEClass, LOCAL_SEND__MESSAGE);
    createEAttribute(localSendEClass, LOCAL_SEND__TO_ROLES);

    localReceiveEClass = createEClass(LOCAL_RECEIVE);
    createEReference(localReceiveEClass, LOCAL_RECEIVE__MESSAGE);
    createEAttribute(localReceiveEClass, LOCAL_RECEIVE__FROM_ROLE);

    localChoiceEClass = createEClass(LOCAL_CHOICE);
    createEAttribute(localChoiceEClass, LOCAL_CHOICE__ROLE);
    createEReference(localChoiceEClass, LOCAL_CHOICE__BLOCKS);

    localRecursionEClass = createEClass(LOCAL_RECURSION);
    createEAttribute(localRecursionEClass, LOCAL_RECURSION__LABEL);
    createEReference(localRecursionEClass, LOCAL_RECURSION__BLOCK);

    localContinueEClass = createEClass(LOCAL_CONTINUE);
    createEAttribute(localContinueEClass, LOCAL_CONTINUE__LABEL);

    localParallelEClass = createEClass(LOCAL_PARALLEL);
    createEReference(localParallelEClass, LOCAL_PARALLEL__BLOCKS);

    localInterruptibleEClass = createEClass(LOCAL_INTERRUPTIBLE);
    createEAttribute(localInterruptibleEClass, LOCAL_INTERRUPTIBLE__SCOPE);
    createEReference(localInterruptibleEClass, LOCAL_INTERRUPTIBLE__BLOCK);
    createEReference(localInterruptibleEClass, LOCAL_INTERRUPTIBLE__THROW);
    createEReference(localInterruptibleEClass, LOCAL_INTERRUPTIBLE__CATCHES);

    localThrowEClass = createEClass(LOCAL_THROW);
    createEReference(localThrowEClass, LOCAL_THROW__MESSAGES);
    createEAttribute(localThrowEClass, LOCAL_THROW__TO_ROLES);

    localCatchEClass = createEClass(LOCAL_CATCH);
    createEReference(localCatchEClass, LOCAL_CATCH__MESSAGES);
    createEAttribute(localCatchEClass, LOCAL_CATCH__FROM_ROLE);

    localDoEClass = createEClass(LOCAL_DO);
    createEAttribute(localDoEClass, LOCAL_DO__SCOPE);
    createEAttribute(localDoEClass, LOCAL_DO__MEMBER);
    createEReference(localDoEClass, LOCAL_DO__ARGUMENTS);
    createEReference(localDoEClass, LOCAL_DO__ROLES);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    importModuleEClass.getESuperTypes().add(this.getImportDecl());
    importMemberEClass.getESuperTypes().add(this.getImportDecl());
    globalMessageTransferEClass.getESuperTypes().add(this.getGlobalInteraction());
    globalChoiceEClass.getESuperTypes().add(this.getGlobalInteraction());
    globalRecursionEClass.getESuperTypes().add(this.getGlobalInteraction());
    globalContinueEClass.getESuperTypes().add(this.getGlobalInteraction());
    globalParallelEClass.getESuperTypes().add(this.getGlobalInteraction());
    globalInterruptibleEClass.getESuperTypes().add(this.getGlobalInteraction());
    globalDoEClass.getESuperTypes().add(this.getGlobalInteraction());
    localSendEClass.getESuperTypes().add(this.getLlobalInteraction());
    localReceiveEClass.getESuperTypes().add(this.getLlobalInteraction());
    localChoiceEClass.getESuperTypes().add(this.getLlobalInteraction());
    localRecursionEClass.getESuperTypes().add(this.getLlobalInteraction());
    localContinueEClass.getESuperTypes().add(this.getLlobalInteraction());
    localParallelEClass.getESuperTypes().add(this.getLlobalInteraction());
    localInterruptibleEClass.getESuperTypes().add(this.getLlobalInteraction());
    localDoEClass.getESuperTypes().add(this.getLlobalInteraction());

    // Initialize classes and features; add operations and parameters
    initEClass(moduleEClass, Module.class, "Module", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getModule_Name(), ecorePackage.getEString(), "name", null, 0, 1, Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getModule_Imports(), this.getImportDecl(), null, "imports", null, 0, -1, Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getModule_Types(), this.getPayloadTypeDecl(), null, "types", null, 0, -1, Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getModule_Globals(), this.getGlobalProtocolDecl(), null, "globals", null, 0, -1, Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getModule_Locals(), this.getLocalProtocolDecl(), null, "locals", null, 0, -1, Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(importDeclEClass, ImportDecl.class, "ImportDecl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getImportDecl_Name(), ecorePackage.getEString(), "name", null, 0, 1, ImportDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getImportDecl_Alias(), ecorePackage.getEString(), "alias", null, 0, 1, ImportDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(importModuleEClass, ImportModule.class, "ImportModule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(importMemberEClass, ImportMember.class, "ImportMember", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getImportMember_Member(), ecorePackage.getEString(), "member", null, 0, 1, ImportMember.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(payloadTypeDeclEClass, PayloadTypeDecl.class, "PayloadTypeDecl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getPayloadTypeDecl_Schema(), ecorePackage.getEString(), "schema", null, 0, 1, PayloadTypeDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPayloadTypeDecl_Type(), ecorePackage.getEString(), "type", null, 0, 1, PayloadTypeDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPayloadTypeDecl_Location(), ecorePackage.getEString(), "location", null, 0, 1, PayloadTypeDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPayloadTypeDecl_Alias(), ecorePackage.getEString(), "alias", null, 0, 1, PayloadTypeDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(messageSignatureEClass, MessageSignature.class, "MessageSignature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getMessageSignature_Operator(), ecorePackage.getEString(), "operator", null, 0, 1, MessageSignature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMessageSignature_Types(), this.getPayloadElement(), null, "types", null, 0, -1, MessageSignature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(payloadElementEClass, PayloadElement.class, "PayloadElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getPayloadElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, PayloadElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPayloadElement_Type(), ecorePackage.getEString(), "type", null, 0, 1, PayloadElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(globalProtocolDeclEClass, GlobalProtocolDecl.class, "GlobalProtocolDecl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getGlobalProtocolDecl_Name(), ecorePackage.getEString(), "name", null, 0, 1, GlobalProtocolDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGlobalProtocolDecl_Parameters(), this.getParameterDecl(), null, "parameters", null, 0, -1, GlobalProtocolDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGlobalProtocolDecl_Roles(), this.getRoleDecl(), null, "roles", null, 0, -1, GlobalProtocolDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGlobalProtocolDecl_Block(), this.getGlobalProtocolBlock(), null, "block", null, 0, 1, GlobalProtocolDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getGlobalProtocolDecl_Instantiates(), ecorePackage.getEString(), "instantiates", null, 0, 1, GlobalProtocolDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGlobalProtocolDecl_Arguments(), this.getArgument(), null, "arguments", null, 0, -1, GlobalProtocolDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGlobalProtocolDecl_RoleInstantiations(), this.getRoleInstantiation(), null, "roleInstantiations", null, 0, -1, GlobalProtocolDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(roleDeclEClass, RoleDecl.class, "RoleDecl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getRoleDecl_Name(), ecorePackage.getEString(), "name", null, 0, 1, RoleDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getRoleDecl_Alias(), ecorePackage.getEString(), "alias", null, 0, 1, RoleDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(parameterDeclEClass, ParameterDecl.class, "ParameterDecl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getParameterDecl_Name(), ecorePackage.getEString(), "name", null, 0, 1, ParameterDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParameterDecl_Alias(), ecorePackage.getEString(), "alias", null, 0, 1, ParameterDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(roleInstantiationEClass, RoleInstantiation.class, "RoleInstantiation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getRoleInstantiation_Name(), ecorePackage.getEString(), "name", null, 0, 1, RoleInstantiation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getRoleInstantiation_Alias(), ecorePackage.getEString(), "alias", null, 0, 1, RoleInstantiation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(argumentEClass, Argument.class, "Argument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getArgument_Signature(), this.getMessageSignature(), null, "signature", null, 0, 1, Argument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getArgument_Alias(), ecorePackage.getEString(), "alias", null, 0, 1, Argument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getArgument_Name(), ecorePackage.getEString(), "name", null, 0, 1, Argument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(globalProtocolBlockEClass, GlobalProtocolBlock.class, "GlobalProtocolBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getGlobalProtocolBlock_Activities(), this.getGlobalInteraction(), null, "activities", null, 0, -1, GlobalProtocolBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(globalInteractionEClass, GlobalInteraction.class, "GlobalInteraction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(globalMessageTransferEClass, GlobalMessageTransfer.class, "GlobalMessageTransfer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getGlobalMessageTransfer_Message(), this.getMessage(), null, "message", null, 0, 1, GlobalMessageTransfer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getGlobalMessageTransfer_FromRole(), ecorePackage.getEString(), "fromRole", null, 0, 1, GlobalMessageTransfer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getGlobalMessageTransfer_ToRoles(), ecorePackage.getEString(), "toRoles", null, 0, -1, GlobalMessageTransfer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(messageEClass, Message.class, "Message", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getMessage_Signature(), this.getMessageSignature(), null, "signature", null, 0, 1, Message.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMessage_Parameter(), ecorePackage.getEString(), "parameter", null, 0, 1, Message.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(globalChoiceEClass, GlobalChoice.class, "GlobalChoice", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getGlobalChoice_Role(), ecorePackage.getEString(), "role", null, 0, 1, GlobalChoice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGlobalChoice_Blocks(), this.getGlobalProtocolBlock(), null, "blocks", null, 0, -1, GlobalChoice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(globalRecursionEClass, GlobalRecursion.class, "GlobalRecursion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getGlobalRecursion_Label(), ecorePackage.getEString(), "label", null, 0, 1, GlobalRecursion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGlobalRecursion_Block(), this.getGlobalProtocolBlock(), null, "block", null, 0, 1, GlobalRecursion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(globalContinueEClass, GlobalContinue.class, "GlobalContinue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getGlobalContinue_Label(), ecorePackage.getEString(), "label", null, 0, 1, GlobalContinue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(globalParallelEClass, GlobalParallel.class, "GlobalParallel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getGlobalParallel_Blocks(), this.getGlobalProtocolBlock(), null, "blocks", null, 0, -1, GlobalParallel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(globalInterruptibleEClass, GlobalInterruptible.class, "GlobalInterruptible", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getGlobalInterruptible_Scope(), ecorePackage.getEString(), "scope", null, 0, 1, GlobalInterruptible.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGlobalInterruptible_Block(), this.getGlobalProtocolBlock(), null, "block", null, 0, 1, GlobalInterruptible.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGlobalInterruptible_Interrupts(), this.getGlobalInterrupt(), null, "interrupts", null, 0, -1, GlobalInterruptible.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(globalInterruptEClass, GlobalInterrupt.class, "GlobalInterrupt", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getGlobalInterrupt_Messages(), this.getMessage(), null, "messages", null, 0, -1, GlobalInterrupt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getGlobalInterrupt_Role(), ecorePackage.getEString(), "role", null, 0, 1, GlobalInterrupt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(globalDoEClass, GlobalDo.class, "GlobalDo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getGlobalDo_Scope(), ecorePackage.getEString(), "scope", null, 0, 1, GlobalDo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getGlobalDo_Member(), ecorePackage.getEString(), "member", null, 0, 1, GlobalDo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGlobalDo_Arguments(), this.getArgument(), null, "arguments", null, 0, -1, GlobalDo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGlobalDo_Roles(), this.getRoleInstantiation(), null, "roles", null, 0, -1, GlobalDo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(localProtocolDeclEClass, LocalProtocolDecl.class, "LocalProtocolDecl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getLocalProtocolDecl_Name(), ecorePackage.getEString(), "name", null, 0, 1, LocalProtocolDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocalProtocolDecl_Role(), ecorePackage.getEString(), "role", null, 0, 1, LocalProtocolDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getLocalProtocolDecl_Parameters(), this.getParameterDecl(), null, "parameters", null, 0, -1, LocalProtocolDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getLocalProtocolDecl_Roles(), this.getRoleDecl(), null, "roles", null, 0, -1, LocalProtocolDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getLocalProtocolDecl_Block(), this.getLocalProtocolBlock(), null, "block", null, 0, 1, LocalProtocolDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocalProtocolDecl_Instantiates(), ecorePackage.getEString(), "instantiates", null, 0, 1, LocalProtocolDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getLocalProtocolDecl_Arguments(), this.getArgument(), null, "arguments", null, 0, -1, LocalProtocolDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getLocalProtocolDecl_RoleInstantiations(), this.getRoleInstantiation(), null, "roleInstantiations", null, 0, -1, LocalProtocolDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(localProtocolBlockEClass, LocalProtocolBlock.class, "LocalProtocolBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getLocalProtocolBlock_Activities(), this.getLlobalInteraction(), null, "activities", null, 0, -1, LocalProtocolBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(llobalInteractionEClass, LlobalInteraction.class, "LlobalInteraction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(localSendEClass, LocalSend.class, "LocalSend", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getLocalSend_Message(), this.getMessage(), null, "message", null, 0, 1, LocalSend.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocalSend_ToRoles(), ecorePackage.getEString(), "toRoles", null, 0, -1, LocalSend.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(localReceiveEClass, LocalReceive.class, "LocalReceive", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getLocalReceive_Message(), this.getMessage(), null, "message", null, 0, 1, LocalReceive.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocalReceive_FromRole(), ecorePackage.getEString(), "fromRole", null, 0, 1, LocalReceive.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(localChoiceEClass, LocalChoice.class, "LocalChoice", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getLocalChoice_Role(), ecorePackage.getEString(), "role", null, 0, 1, LocalChoice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getLocalChoice_Blocks(), this.getLocalProtocolBlock(), null, "blocks", null, 0, -1, LocalChoice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(localRecursionEClass, LocalRecursion.class, "LocalRecursion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getLocalRecursion_Label(), ecorePackage.getEString(), "label", null, 0, 1, LocalRecursion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getLocalRecursion_Block(), this.getLocalProtocolBlock(), null, "block", null, 0, 1, LocalRecursion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(localContinueEClass, LocalContinue.class, "LocalContinue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getLocalContinue_Label(), ecorePackage.getEString(), "label", null, 0, 1, LocalContinue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(localParallelEClass, LocalParallel.class, "LocalParallel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getLocalParallel_Blocks(), this.getLocalProtocolBlock(), null, "blocks", null, 0, -1, LocalParallel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(localInterruptibleEClass, LocalInterruptible.class, "LocalInterruptible", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getLocalInterruptible_Scope(), ecorePackage.getEString(), "scope", null, 0, 1, LocalInterruptible.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getLocalInterruptible_Block(), this.getLocalProtocolBlock(), null, "block", null, 0, 1, LocalInterruptible.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getLocalInterruptible_Throw(), this.getLocalThrow(), null, "throw", null, 0, 1, LocalInterruptible.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getLocalInterruptible_Catches(), this.getLocalCatch(), null, "catches", null, 0, -1, LocalInterruptible.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(localThrowEClass, LocalThrow.class, "LocalThrow", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getLocalThrow_Messages(), this.getMessage(), null, "messages", null, 0, -1, LocalThrow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocalThrow_ToRoles(), ecorePackage.getEString(), "toRoles", null, 0, -1, LocalThrow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(localCatchEClass, LocalCatch.class, "LocalCatch", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getLocalCatch_Messages(), this.getMessage(), null, "messages", null, 0, -1, LocalCatch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocalCatch_FromRole(), ecorePackage.getEString(), "fromRole", null, 0, 1, LocalCatch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(localDoEClass, LocalDo.class, "LocalDo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getLocalDo_Scope(), ecorePackage.getEString(), "scope", null, 0, 1, LocalDo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocalDo_Member(), ecorePackage.getEString(), "member", null, 0, 1, LocalDo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getLocalDo_Arguments(), this.getArgument(), null, "arguments", null, 0, -1, LocalDo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getLocalDo_Roles(), this.getRoleInstantiation(), null, "roles", null, 0, -1, LocalDo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //ScribbleDslPackageImpl
