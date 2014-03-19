/**
 */
package org.scribble.editor.dsl.scribbleDsl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslFactory
 * @model kind="package"
 * @generated
 */
public interface ScribbleDslPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "scribbleDsl";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.scribble.org/editor/dsl/ScribbleDsl";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "scribbleDsl";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  ScribbleDslPackage eINSTANCE = org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl.init();

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.ModuleImpl <em>Module</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ModuleImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getModule()
   * @generated
   */
  int MODULE = 0;

  /**
   * The number of structural features of the '<em>Module</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODULE_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.ModuleDeclImpl <em>Module Decl</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ModuleDeclImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getModuleDecl()
   * @generated
   */
  int MODULE_DECL = 1;

  /**
   * The feature id for the '<em><b>Imports</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODULE_DECL__IMPORTS = MODULE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Types</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODULE_DECL__TYPES = MODULE_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Globals</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODULE_DECL__GLOBALS = MODULE_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODULE_DECL__NAME = MODULE_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Module Decl</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODULE_DECL_FEATURE_COUNT = MODULE_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.ImportDeclImpl <em>Import Decl</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ImportDeclImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getImportDecl()
   * @generated
   */
  int IMPORT_DECL = 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT_DECL__NAME = 0;

  /**
   * The feature id for the '<em><b>Alias</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT_DECL__ALIAS = 1;

  /**
   * The number of structural features of the '<em>Import Decl</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT_DECL_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.ImportModuleImpl <em>Import Module</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ImportModuleImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getImportModule()
   * @generated
   */
  int IMPORT_MODULE = 3;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT_MODULE__NAME = IMPORT_DECL__NAME;

  /**
   * The feature id for the '<em><b>Alias</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT_MODULE__ALIAS = IMPORT_DECL__ALIAS;

  /**
   * The number of structural features of the '<em>Import Module</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT_MODULE_FEATURE_COUNT = IMPORT_DECL_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.ImportMemberImpl <em>Import Member</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ImportMemberImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getImportMember()
   * @generated
   */
  int IMPORT_MEMBER = 4;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT_MEMBER__NAME = IMPORT_DECL__NAME;

  /**
   * The feature id for the '<em><b>Alias</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT_MEMBER__ALIAS = IMPORT_DECL__ALIAS;

  /**
   * The feature id for the '<em><b>Member</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT_MEMBER__MEMBER = IMPORT_DECL_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Import Member</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT_MEMBER_FEATURE_COUNT = IMPORT_DECL_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.PayloadTypeDeclImpl <em>Payload Type Decl</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.PayloadTypeDeclImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getPayloadTypeDecl()
   * @generated
   */
  int PAYLOAD_TYPE_DECL = 5;

  /**
   * The feature id for the '<em><b>Schema</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PAYLOAD_TYPE_DECL__SCHEMA = 0;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PAYLOAD_TYPE_DECL__TYPE = 1;

  /**
   * The feature id for the '<em><b>Location</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PAYLOAD_TYPE_DECL__LOCATION = 2;

  /**
   * The feature id for the '<em><b>Alias</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PAYLOAD_TYPE_DECL__ALIAS = 3;

  /**
   * The number of structural features of the '<em>Payload Type Decl</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PAYLOAD_TYPE_DECL_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.MessageImpl <em>Message</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.MessageImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getMessage()
   * @generated
   */
  int MESSAGE = 16;

  /**
   * The feature id for the '<em><b>Parameter</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE__PARAMETER = 0;

  /**
   * The number of structural features of the '<em>Message</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.MessageSignatureImpl <em>Message Signature</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.MessageSignatureImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getMessageSignature()
   * @generated
   */
  int MESSAGE_SIGNATURE = 6;

  /**
   * The feature id for the '<em><b>Parameter</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE_SIGNATURE__PARAMETER = MESSAGE__PARAMETER;

  /**
   * The feature id for the '<em><b>Operator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE_SIGNATURE__OPERATOR = MESSAGE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Types</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE_SIGNATURE__TYPES = MESSAGE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Message Signature</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE_SIGNATURE_FEATURE_COUNT = MESSAGE_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.PayloadElementImpl <em>Payload Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.PayloadElementImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getPayloadElement()
   * @generated
   */
  int PAYLOAD_ELEMENT = 7;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PAYLOAD_ELEMENT__NAME = 0;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PAYLOAD_ELEMENT__TYPE = 1;

  /**
   * The number of structural features of the '<em>Payload Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PAYLOAD_ELEMENT_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalProtocolDeclImpl <em>Global Protocol Decl</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalProtocolDeclImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalProtocolDecl()
   * @generated
   */
  int GLOBAL_PROTOCOL_DECL = 8;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_PROTOCOL_DECL__NAME = 0;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_PROTOCOL_DECL__PARAMETERS = 1;

  /**
   * The feature id for the '<em><b>Roles</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_PROTOCOL_DECL__ROLES = 2;

  /**
   * The feature id for the '<em><b>Block</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_PROTOCOL_DECL__BLOCK = 3;

  /**
   * The feature id for the '<em><b>Instantiates</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_PROTOCOL_DECL__INSTANTIATES = 4;

  /**
   * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_PROTOCOL_DECL__ARGUMENTS = 5;

  /**
   * The feature id for the '<em><b>Role Instantiations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_PROTOCOL_DECL__ROLE_INSTANTIATIONS = 6;

  /**
   * The number of structural features of the '<em>Global Protocol Decl</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_PROTOCOL_DECL_FEATURE_COUNT = 7;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.RoleDeclImpl <em>Role Decl</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.RoleDeclImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getRoleDecl()
   * @generated
   */
  int ROLE_DECL = 9;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLE_DECL__NAME = 0;

  /**
   * The feature id for the '<em><b>Alias</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLE_DECL__ALIAS = 1;

  /**
   * The number of structural features of the '<em>Role Decl</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLE_DECL_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.ParameterDeclImpl <em>Parameter Decl</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ParameterDeclImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getParameterDecl()
   * @generated
   */
  int PARAMETER_DECL = 10;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER_DECL__NAME = 0;

  /**
   * The feature id for the '<em><b>Alias</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER_DECL__ALIAS = 1;

  /**
   * The number of structural features of the '<em>Parameter Decl</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER_DECL_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.RoleInstantiationImpl <em>Role Instantiation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.RoleInstantiationImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getRoleInstantiation()
   * @generated
   */
  int ROLE_INSTANTIATION = 11;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLE_INSTANTIATION__NAME = 0;

  /**
   * The feature id for the '<em><b>Alias</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLE_INSTANTIATION__ALIAS = 1;

  /**
   * The number of structural features of the '<em>Role Instantiation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLE_INSTANTIATION_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.ArgumentImpl <em>Argument</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ArgumentImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getArgument()
   * @generated
   */
  int ARGUMENT = 12;

  /**
   * The feature id for the '<em><b>Signature</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARGUMENT__SIGNATURE = 0;

  /**
   * The feature id for the '<em><b>Alias</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARGUMENT__ALIAS = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARGUMENT__NAME = 2;

  /**
   * The number of structural features of the '<em>Argument</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARGUMENT_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalProtocolBlockImpl <em>Global Protocol Block</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalProtocolBlockImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalProtocolBlock()
   * @generated
   */
  int GLOBAL_PROTOCOL_BLOCK = 13;

  /**
   * The feature id for the '<em><b>Activities</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_PROTOCOL_BLOCK__ACTIVITIES = 0;

  /**
   * The number of structural features of the '<em>Global Protocol Block</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_PROTOCOL_BLOCK_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalInteractionImpl <em>Global Interaction</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalInteractionImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalInteraction()
   * @generated
   */
  int GLOBAL_INTERACTION = 14;

  /**
   * The number of structural features of the '<em>Global Interaction</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_INTERACTION_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalMessageTransferImpl <em>Global Message Transfer</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalMessageTransferImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalMessageTransfer()
   * @generated
   */
  int GLOBAL_MESSAGE_TRANSFER = 15;

  /**
   * The feature id for the '<em><b>Message</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_MESSAGE_TRANSFER__MESSAGE = GLOBAL_INTERACTION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>From Role</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_MESSAGE_TRANSFER__FROM_ROLE = GLOBAL_INTERACTION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>To Role</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_MESSAGE_TRANSFER__TO_ROLE = GLOBAL_INTERACTION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Global Message Transfer</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_MESSAGE_TRANSFER_FEATURE_COUNT = GLOBAL_INTERACTION_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalChoiceImpl <em>Global Choice</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalChoiceImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalChoice()
   * @generated
   */
  int GLOBAL_CHOICE = 17;

  /**
   * The feature id for the '<em><b>Role</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_CHOICE__ROLE = GLOBAL_INTERACTION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Blocks</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_CHOICE__BLOCKS = GLOBAL_INTERACTION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Global Choice</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_CHOICE_FEATURE_COUNT = GLOBAL_INTERACTION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalRecursionImpl <em>Global Recursion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalRecursionImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalRecursion()
   * @generated
   */
  int GLOBAL_RECURSION = 18;

  /**
   * The feature id for the '<em><b>Label</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_RECURSION__LABEL = GLOBAL_INTERACTION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Block</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_RECURSION__BLOCK = GLOBAL_INTERACTION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Global Recursion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_RECURSION_FEATURE_COUNT = GLOBAL_INTERACTION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalContinueImpl <em>Global Continue</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalContinueImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalContinue()
   * @generated
   */
  int GLOBAL_CONTINUE = 19;

  /**
   * The feature id for the '<em><b>Label</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_CONTINUE__LABEL = GLOBAL_INTERACTION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Global Continue</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_CONTINUE_FEATURE_COUNT = GLOBAL_INTERACTION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalParallelImpl <em>Global Parallel</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalParallelImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalParallel()
   * @generated
   */
  int GLOBAL_PARALLEL = 20;

  /**
   * The feature id for the '<em><b>Blocks</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_PARALLEL__BLOCKS = GLOBAL_INTERACTION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Global Parallel</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_PARALLEL_FEATURE_COUNT = GLOBAL_INTERACTION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalInterruptibleImpl <em>Global Interruptible</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalInterruptibleImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalInterruptible()
   * @generated
   */
  int GLOBAL_INTERRUPTIBLE = 21;

  /**
   * The feature id for the '<em><b>Scope</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_INTERRUPTIBLE__SCOPE = GLOBAL_INTERACTION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Block</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_INTERRUPTIBLE__BLOCK = GLOBAL_INTERACTION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Interrupts</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_INTERRUPTIBLE__INTERRUPTS = GLOBAL_INTERACTION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Global Interruptible</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_INTERRUPTIBLE_FEATURE_COUNT = GLOBAL_INTERACTION_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalInterruptImpl <em>Global Interrupt</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalInterruptImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalInterrupt()
   * @generated
   */
  int GLOBAL_INTERRUPT = 22;

  /**
   * The feature id for the '<em><b>Messages</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_INTERRUPT__MESSAGES = 0;

  /**
   * The feature id for the '<em><b>Role</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_INTERRUPT__ROLE = 1;

  /**
   * The number of structural features of the '<em>Global Interrupt</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_INTERRUPT_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalDoImpl <em>Global Do</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalDoImpl
   * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalDo()
   * @generated
   */
  int GLOBAL_DO = 23;

  /**
   * The feature id for the '<em><b>Module</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_DO__MODULE = GLOBAL_INTERACTION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Scope</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_DO__SCOPE = GLOBAL_INTERACTION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_DO__ARGUMENTS = GLOBAL_INTERACTION_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Roles</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_DO__ROLES = GLOBAL_INTERACTION_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Global Do</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GLOBAL_DO_FEATURE_COUNT = GLOBAL_INTERACTION_FEATURE_COUNT + 4;


  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.Module <em>Module</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Module</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.Module
   * @generated
   */
  EClass getModule();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.ModuleDecl <em>Module Decl</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Module Decl</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.ModuleDecl
   * @generated
   */
  EClass getModuleDecl();

  /**
   * Returns the meta object for the containment reference list '{@link org.scribble.editor.dsl.scribbleDsl.ModuleDecl#getImports <em>Imports</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Imports</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.ModuleDecl#getImports()
   * @see #getModuleDecl()
   * @generated
   */
  EReference getModuleDecl_Imports();

  /**
   * Returns the meta object for the containment reference list '{@link org.scribble.editor.dsl.scribbleDsl.ModuleDecl#getTypes <em>Types</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Types</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.ModuleDecl#getTypes()
   * @see #getModuleDecl()
   * @generated
   */
  EReference getModuleDecl_Types();

  /**
   * Returns the meta object for the containment reference list '{@link org.scribble.editor.dsl.scribbleDsl.ModuleDecl#getGlobals <em>Globals</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Globals</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.ModuleDecl#getGlobals()
   * @see #getModuleDecl()
   * @generated
   */
  EReference getModuleDecl_Globals();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.ModuleDecl#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.ModuleDecl#getName()
   * @see #getModuleDecl()
   * @generated
   */
  EAttribute getModuleDecl_Name();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.ImportDecl <em>Import Decl</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Import Decl</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.ImportDecl
   * @generated
   */
  EClass getImportDecl();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.ImportDecl#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.ImportDecl#getName()
   * @see #getImportDecl()
   * @generated
   */
  EAttribute getImportDecl_Name();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.ImportDecl#getAlias <em>Alias</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Alias</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.ImportDecl#getAlias()
   * @see #getImportDecl()
   * @generated
   */
  EAttribute getImportDecl_Alias();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.ImportModule <em>Import Module</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Import Module</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.ImportModule
   * @generated
   */
  EClass getImportModule();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.ImportMember <em>Import Member</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Import Member</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.ImportMember
   * @generated
   */
  EClass getImportMember();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.ImportMember#getMember <em>Member</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Member</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.ImportMember#getMember()
   * @see #getImportMember()
   * @generated
   */
  EAttribute getImportMember_Member();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.PayloadTypeDecl <em>Payload Type Decl</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Payload Type Decl</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.PayloadTypeDecl
   * @generated
   */
  EClass getPayloadTypeDecl();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.PayloadTypeDecl#getSchema <em>Schema</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Schema</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.PayloadTypeDecl#getSchema()
   * @see #getPayloadTypeDecl()
   * @generated
   */
  EAttribute getPayloadTypeDecl_Schema();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.PayloadTypeDecl#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.PayloadTypeDecl#getType()
   * @see #getPayloadTypeDecl()
   * @generated
   */
  EAttribute getPayloadTypeDecl_Type();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.PayloadTypeDecl#getLocation <em>Location</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Location</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.PayloadTypeDecl#getLocation()
   * @see #getPayloadTypeDecl()
   * @generated
   */
  EAttribute getPayloadTypeDecl_Location();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.PayloadTypeDecl#getAlias <em>Alias</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Alias</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.PayloadTypeDecl#getAlias()
   * @see #getPayloadTypeDecl()
   * @generated
   */
  EAttribute getPayloadTypeDecl_Alias();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.MessageSignature <em>Message Signature</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Message Signature</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.MessageSignature
   * @generated
   */
  EClass getMessageSignature();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.MessageSignature#getOperator <em>Operator</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Operator</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.MessageSignature#getOperator()
   * @see #getMessageSignature()
   * @generated
   */
  EAttribute getMessageSignature_Operator();

  /**
   * Returns the meta object for the containment reference list '{@link org.scribble.editor.dsl.scribbleDsl.MessageSignature#getTypes <em>Types</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Types</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.MessageSignature#getTypes()
   * @see #getMessageSignature()
   * @generated
   */
  EReference getMessageSignature_Types();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.PayloadElement <em>Payload Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Payload Element</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.PayloadElement
   * @generated
   */
  EClass getPayloadElement();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.PayloadElement#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.PayloadElement#getName()
   * @see #getPayloadElement()
   * @generated
   */
  EAttribute getPayloadElement_Name();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.PayloadElement#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.PayloadElement#getType()
   * @see #getPayloadElement()
   * @generated
   */
  EAttribute getPayloadElement_Type();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl <em>Global Protocol Decl</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Global Protocol Decl</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl
   * @generated
   */
  EClass getGlobalProtocolDecl();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl#getName()
   * @see #getGlobalProtocolDecl()
   * @generated
   */
  EAttribute getGlobalProtocolDecl_Name();

  /**
   * Returns the meta object for the containment reference list '{@link org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl#getParameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameters</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl#getParameters()
   * @see #getGlobalProtocolDecl()
   * @generated
   */
  EReference getGlobalProtocolDecl_Parameters();

  /**
   * Returns the meta object for the containment reference list '{@link org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl#getRoles <em>Roles</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Roles</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl#getRoles()
   * @see #getGlobalProtocolDecl()
   * @generated
   */
  EReference getGlobalProtocolDecl_Roles();

  /**
   * Returns the meta object for the containment reference '{@link org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl#getBlock <em>Block</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Block</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl#getBlock()
   * @see #getGlobalProtocolDecl()
   * @generated
   */
  EReference getGlobalProtocolDecl_Block();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl#getInstantiates <em>Instantiates</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Instantiates</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl#getInstantiates()
   * @see #getGlobalProtocolDecl()
   * @generated
   */
  EAttribute getGlobalProtocolDecl_Instantiates();

  /**
   * Returns the meta object for the containment reference list '{@link org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl#getArguments <em>Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Arguments</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl#getArguments()
   * @see #getGlobalProtocolDecl()
   * @generated
   */
  EReference getGlobalProtocolDecl_Arguments();

  /**
   * Returns the meta object for the containment reference list '{@link org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl#getRoleInstantiations <em>Role Instantiations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Role Instantiations</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl#getRoleInstantiations()
   * @see #getGlobalProtocolDecl()
   * @generated
   */
  EReference getGlobalProtocolDecl_RoleInstantiations();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.RoleDecl <em>Role Decl</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Role Decl</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.RoleDecl
   * @generated
   */
  EClass getRoleDecl();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.RoleDecl#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.RoleDecl#getName()
   * @see #getRoleDecl()
   * @generated
   */
  EAttribute getRoleDecl_Name();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.RoleDecl#getAlias <em>Alias</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Alias</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.RoleDecl#getAlias()
   * @see #getRoleDecl()
   * @generated
   */
  EAttribute getRoleDecl_Alias();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.ParameterDecl <em>Parameter Decl</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Parameter Decl</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.ParameterDecl
   * @generated
   */
  EClass getParameterDecl();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.ParameterDecl#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.ParameterDecl#getName()
   * @see #getParameterDecl()
   * @generated
   */
  EAttribute getParameterDecl_Name();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.ParameterDecl#getAlias <em>Alias</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Alias</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.ParameterDecl#getAlias()
   * @see #getParameterDecl()
   * @generated
   */
  EAttribute getParameterDecl_Alias();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.RoleInstantiation <em>Role Instantiation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Role Instantiation</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.RoleInstantiation
   * @generated
   */
  EClass getRoleInstantiation();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.RoleInstantiation#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.RoleInstantiation#getName()
   * @see #getRoleInstantiation()
   * @generated
   */
  EAttribute getRoleInstantiation_Name();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.RoleInstantiation#getAlias <em>Alias</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Alias</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.RoleInstantiation#getAlias()
   * @see #getRoleInstantiation()
   * @generated
   */
  EAttribute getRoleInstantiation_Alias();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.Argument <em>Argument</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Argument</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.Argument
   * @generated
   */
  EClass getArgument();

  /**
   * Returns the meta object for the containment reference '{@link org.scribble.editor.dsl.scribbleDsl.Argument#getSignature <em>Signature</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Signature</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.Argument#getSignature()
   * @see #getArgument()
   * @generated
   */
  EReference getArgument_Signature();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.Argument#getAlias <em>Alias</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Alias</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.Argument#getAlias()
   * @see #getArgument()
   * @generated
   */
  EAttribute getArgument_Alias();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.Argument#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.Argument#getName()
   * @see #getArgument()
   * @generated
   */
  EAttribute getArgument_Name();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalProtocolBlock <em>Global Protocol Block</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Global Protocol Block</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalProtocolBlock
   * @generated
   */
  EClass getGlobalProtocolBlock();

  /**
   * Returns the meta object for the containment reference list '{@link org.scribble.editor.dsl.scribbleDsl.GlobalProtocolBlock#getActivities <em>Activities</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Activities</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalProtocolBlock#getActivities()
   * @see #getGlobalProtocolBlock()
   * @generated
   */
  EReference getGlobalProtocolBlock_Activities();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalInteraction <em>Global Interaction</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Global Interaction</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalInteraction
   * @generated
   */
  EClass getGlobalInteraction();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalMessageTransfer <em>Global Message Transfer</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Global Message Transfer</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalMessageTransfer
   * @generated
   */
  EClass getGlobalMessageTransfer();

  /**
   * Returns the meta object for the containment reference '{@link org.scribble.editor.dsl.scribbleDsl.GlobalMessageTransfer#getMessage <em>Message</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Message</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalMessageTransfer#getMessage()
   * @see #getGlobalMessageTransfer()
   * @generated
   */
  EReference getGlobalMessageTransfer_Message();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.GlobalMessageTransfer#getFromRole <em>From Role</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>From Role</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalMessageTransfer#getFromRole()
   * @see #getGlobalMessageTransfer()
   * @generated
   */
  EAttribute getGlobalMessageTransfer_FromRole();

  /**
   * Returns the meta object for the attribute list '{@link org.scribble.editor.dsl.scribbleDsl.GlobalMessageTransfer#getToRole <em>To Role</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>To Role</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalMessageTransfer#getToRole()
   * @see #getGlobalMessageTransfer()
   * @generated
   */
  EAttribute getGlobalMessageTransfer_ToRole();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.Message <em>Message</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Message</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.Message
   * @generated
   */
  EClass getMessage();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.Message#getParameter <em>Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Parameter</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.Message#getParameter()
   * @see #getMessage()
   * @generated
   */
  EAttribute getMessage_Parameter();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalChoice <em>Global Choice</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Global Choice</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalChoice
   * @generated
   */
  EClass getGlobalChoice();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.GlobalChoice#getRole <em>Role</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Role</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalChoice#getRole()
   * @see #getGlobalChoice()
   * @generated
   */
  EAttribute getGlobalChoice_Role();

  /**
   * Returns the meta object for the containment reference list '{@link org.scribble.editor.dsl.scribbleDsl.GlobalChoice#getBlocks <em>Blocks</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Blocks</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalChoice#getBlocks()
   * @see #getGlobalChoice()
   * @generated
   */
  EReference getGlobalChoice_Blocks();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalRecursion <em>Global Recursion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Global Recursion</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalRecursion
   * @generated
   */
  EClass getGlobalRecursion();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.GlobalRecursion#getLabel <em>Label</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Label</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalRecursion#getLabel()
   * @see #getGlobalRecursion()
   * @generated
   */
  EAttribute getGlobalRecursion_Label();

  /**
   * Returns the meta object for the containment reference '{@link org.scribble.editor.dsl.scribbleDsl.GlobalRecursion#getBlock <em>Block</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Block</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalRecursion#getBlock()
   * @see #getGlobalRecursion()
   * @generated
   */
  EReference getGlobalRecursion_Block();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalContinue <em>Global Continue</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Global Continue</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalContinue
   * @generated
   */
  EClass getGlobalContinue();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.GlobalContinue#getLabel <em>Label</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Label</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalContinue#getLabel()
   * @see #getGlobalContinue()
   * @generated
   */
  EAttribute getGlobalContinue_Label();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalParallel <em>Global Parallel</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Global Parallel</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalParallel
   * @generated
   */
  EClass getGlobalParallel();

  /**
   * Returns the meta object for the containment reference list '{@link org.scribble.editor.dsl.scribbleDsl.GlobalParallel#getBlocks <em>Blocks</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Blocks</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalParallel#getBlocks()
   * @see #getGlobalParallel()
   * @generated
   */
  EReference getGlobalParallel_Blocks();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalInterruptible <em>Global Interruptible</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Global Interruptible</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalInterruptible
   * @generated
   */
  EClass getGlobalInterruptible();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.GlobalInterruptible#getScope <em>Scope</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Scope</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalInterruptible#getScope()
   * @see #getGlobalInterruptible()
   * @generated
   */
  EAttribute getGlobalInterruptible_Scope();

  /**
   * Returns the meta object for the containment reference '{@link org.scribble.editor.dsl.scribbleDsl.GlobalInterruptible#getBlock <em>Block</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Block</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalInterruptible#getBlock()
   * @see #getGlobalInterruptible()
   * @generated
   */
  EReference getGlobalInterruptible_Block();

  /**
   * Returns the meta object for the containment reference list '{@link org.scribble.editor.dsl.scribbleDsl.GlobalInterruptible#getInterrupts <em>Interrupts</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Interrupts</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalInterruptible#getInterrupts()
   * @see #getGlobalInterruptible()
   * @generated
   */
  EReference getGlobalInterruptible_Interrupts();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalInterrupt <em>Global Interrupt</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Global Interrupt</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalInterrupt
   * @generated
   */
  EClass getGlobalInterrupt();

  /**
   * Returns the meta object for the containment reference list '{@link org.scribble.editor.dsl.scribbleDsl.GlobalInterrupt#getMessages <em>Messages</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Messages</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalInterrupt#getMessages()
   * @see #getGlobalInterrupt()
   * @generated
   */
  EReference getGlobalInterrupt_Messages();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.GlobalInterrupt#getRole <em>Role</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Role</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalInterrupt#getRole()
   * @see #getGlobalInterrupt()
   * @generated
   */
  EAttribute getGlobalInterrupt_Role();

  /**
   * Returns the meta object for class '{@link org.scribble.editor.dsl.scribbleDsl.GlobalDo <em>Global Do</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Global Do</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalDo
   * @generated
   */
  EClass getGlobalDo();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.GlobalDo#getModule <em>Module</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Module</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalDo#getModule()
   * @see #getGlobalDo()
   * @generated
   */
  EAttribute getGlobalDo_Module();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.editor.dsl.scribbleDsl.GlobalDo#getScope <em>Scope</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Scope</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalDo#getScope()
   * @see #getGlobalDo()
   * @generated
   */
  EAttribute getGlobalDo_Scope();

  /**
   * Returns the meta object for the containment reference list '{@link org.scribble.editor.dsl.scribbleDsl.GlobalDo#getArguments <em>Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Arguments</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalDo#getArguments()
   * @see #getGlobalDo()
   * @generated
   */
  EReference getGlobalDo_Arguments();

  /**
   * Returns the meta object for the containment reference list '{@link org.scribble.editor.dsl.scribbleDsl.GlobalDo#getRoles <em>Roles</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Roles</em>'.
   * @see org.scribble.editor.dsl.scribbleDsl.GlobalDo#getRoles()
   * @see #getGlobalDo()
   * @generated
   */
  EReference getGlobalDo_Roles();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  ScribbleDslFactory getScribbleDslFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.ModuleImpl <em>Module</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ModuleImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getModule()
     * @generated
     */
    EClass MODULE = eINSTANCE.getModule();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.ModuleDeclImpl <em>Module Decl</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ModuleDeclImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getModuleDecl()
     * @generated
     */
    EClass MODULE_DECL = eINSTANCE.getModuleDecl();

    /**
     * The meta object literal for the '<em><b>Imports</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MODULE_DECL__IMPORTS = eINSTANCE.getModuleDecl_Imports();

    /**
     * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MODULE_DECL__TYPES = eINSTANCE.getModuleDecl_Types();

    /**
     * The meta object literal for the '<em><b>Globals</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MODULE_DECL__GLOBALS = eINSTANCE.getModuleDecl_Globals();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MODULE_DECL__NAME = eINSTANCE.getModuleDecl_Name();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.ImportDeclImpl <em>Import Decl</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ImportDeclImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getImportDecl()
     * @generated
     */
    EClass IMPORT_DECL = eINSTANCE.getImportDecl();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute IMPORT_DECL__NAME = eINSTANCE.getImportDecl_Name();

    /**
     * The meta object literal for the '<em><b>Alias</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute IMPORT_DECL__ALIAS = eINSTANCE.getImportDecl_Alias();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.ImportModuleImpl <em>Import Module</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ImportModuleImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getImportModule()
     * @generated
     */
    EClass IMPORT_MODULE = eINSTANCE.getImportModule();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.ImportMemberImpl <em>Import Member</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ImportMemberImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getImportMember()
     * @generated
     */
    EClass IMPORT_MEMBER = eINSTANCE.getImportMember();

    /**
     * The meta object literal for the '<em><b>Member</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute IMPORT_MEMBER__MEMBER = eINSTANCE.getImportMember_Member();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.PayloadTypeDeclImpl <em>Payload Type Decl</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.PayloadTypeDeclImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getPayloadTypeDecl()
     * @generated
     */
    EClass PAYLOAD_TYPE_DECL = eINSTANCE.getPayloadTypeDecl();

    /**
     * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PAYLOAD_TYPE_DECL__SCHEMA = eINSTANCE.getPayloadTypeDecl_Schema();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PAYLOAD_TYPE_DECL__TYPE = eINSTANCE.getPayloadTypeDecl_Type();

    /**
     * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PAYLOAD_TYPE_DECL__LOCATION = eINSTANCE.getPayloadTypeDecl_Location();

    /**
     * The meta object literal for the '<em><b>Alias</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PAYLOAD_TYPE_DECL__ALIAS = eINSTANCE.getPayloadTypeDecl_Alias();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.MessageSignatureImpl <em>Message Signature</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.MessageSignatureImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getMessageSignature()
     * @generated
     */
    EClass MESSAGE_SIGNATURE = eINSTANCE.getMessageSignature();

    /**
     * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MESSAGE_SIGNATURE__OPERATOR = eINSTANCE.getMessageSignature_Operator();

    /**
     * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MESSAGE_SIGNATURE__TYPES = eINSTANCE.getMessageSignature_Types();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.PayloadElementImpl <em>Payload Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.PayloadElementImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getPayloadElement()
     * @generated
     */
    EClass PAYLOAD_ELEMENT = eINSTANCE.getPayloadElement();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PAYLOAD_ELEMENT__NAME = eINSTANCE.getPayloadElement_Name();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PAYLOAD_ELEMENT__TYPE = eINSTANCE.getPayloadElement_Type();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalProtocolDeclImpl <em>Global Protocol Decl</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalProtocolDeclImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalProtocolDecl()
     * @generated
     */
    EClass GLOBAL_PROTOCOL_DECL = eINSTANCE.getGlobalProtocolDecl();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GLOBAL_PROTOCOL_DECL__NAME = eINSTANCE.getGlobalProtocolDecl_Name();

    /**
     * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GLOBAL_PROTOCOL_DECL__PARAMETERS = eINSTANCE.getGlobalProtocolDecl_Parameters();

    /**
     * The meta object literal for the '<em><b>Roles</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GLOBAL_PROTOCOL_DECL__ROLES = eINSTANCE.getGlobalProtocolDecl_Roles();

    /**
     * The meta object literal for the '<em><b>Block</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GLOBAL_PROTOCOL_DECL__BLOCK = eINSTANCE.getGlobalProtocolDecl_Block();

    /**
     * The meta object literal for the '<em><b>Instantiates</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GLOBAL_PROTOCOL_DECL__INSTANTIATES = eINSTANCE.getGlobalProtocolDecl_Instantiates();

    /**
     * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GLOBAL_PROTOCOL_DECL__ARGUMENTS = eINSTANCE.getGlobalProtocolDecl_Arguments();

    /**
     * The meta object literal for the '<em><b>Role Instantiations</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GLOBAL_PROTOCOL_DECL__ROLE_INSTANTIATIONS = eINSTANCE.getGlobalProtocolDecl_RoleInstantiations();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.RoleDeclImpl <em>Role Decl</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.RoleDeclImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getRoleDecl()
     * @generated
     */
    EClass ROLE_DECL = eINSTANCE.getRoleDecl();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ROLE_DECL__NAME = eINSTANCE.getRoleDecl_Name();

    /**
     * The meta object literal for the '<em><b>Alias</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ROLE_DECL__ALIAS = eINSTANCE.getRoleDecl_Alias();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.ParameterDeclImpl <em>Parameter Decl</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ParameterDeclImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getParameterDecl()
     * @generated
     */
    EClass PARAMETER_DECL = eINSTANCE.getParameterDecl();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARAMETER_DECL__NAME = eINSTANCE.getParameterDecl_Name();

    /**
     * The meta object literal for the '<em><b>Alias</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARAMETER_DECL__ALIAS = eINSTANCE.getParameterDecl_Alias();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.RoleInstantiationImpl <em>Role Instantiation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.RoleInstantiationImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getRoleInstantiation()
     * @generated
     */
    EClass ROLE_INSTANTIATION = eINSTANCE.getRoleInstantiation();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ROLE_INSTANTIATION__NAME = eINSTANCE.getRoleInstantiation_Name();

    /**
     * The meta object literal for the '<em><b>Alias</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ROLE_INSTANTIATION__ALIAS = eINSTANCE.getRoleInstantiation_Alias();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.ArgumentImpl <em>Argument</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ArgumentImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getArgument()
     * @generated
     */
    EClass ARGUMENT = eINSTANCE.getArgument();

    /**
     * The meta object literal for the '<em><b>Signature</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ARGUMENT__SIGNATURE = eINSTANCE.getArgument_Signature();

    /**
     * The meta object literal for the '<em><b>Alias</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ARGUMENT__ALIAS = eINSTANCE.getArgument_Alias();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ARGUMENT__NAME = eINSTANCE.getArgument_Name();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalProtocolBlockImpl <em>Global Protocol Block</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalProtocolBlockImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalProtocolBlock()
     * @generated
     */
    EClass GLOBAL_PROTOCOL_BLOCK = eINSTANCE.getGlobalProtocolBlock();

    /**
     * The meta object literal for the '<em><b>Activities</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GLOBAL_PROTOCOL_BLOCK__ACTIVITIES = eINSTANCE.getGlobalProtocolBlock_Activities();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalInteractionImpl <em>Global Interaction</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalInteractionImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalInteraction()
     * @generated
     */
    EClass GLOBAL_INTERACTION = eINSTANCE.getGlobalInteraction();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalMessageTransferImpl <em>Global Message Transfer</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalMessageTransferImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalMessageTransfer()
     * @generated
     */
    EClass GLOBAL_MESSAGE_TRANSFER = eINSTANCE.getGlobalMessageTransfer();

    /**
     * The meta object literal for the '<em><b>Message</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GLOBAL_MESSAGE_TRANSFER__MESSAGE = eINSTANCE.getGlobalMessageTransfer_Message();

    /**
     * The meta object literal for the '<em><b>From Role</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GLOBAL_MESSAGE_TRANSFER__FROM_ROLE = eINSTANCE.getGlobalMessageTransfer_FromRole();

    /**
     * The meta object literal for the '<em><b>To Role</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GLOBAL_MESSAGE_TRANSFER__TO_ROLE = eINSTANCE.getGlobalMessageTransfer_ToRole();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.MessageImpl <em>Message</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.MessageImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getMessage()
     * @generated
     */
    EClass MESSAGE = eINSTANCE.getMessage();

    /**
     * The meta object literal for the '<em><b>Parameter</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MESSAGE__PARAMETER = eINSTANCE.getMessage_Parameter();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalChoiceImpl <em>Global Choice</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalChoiceImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalChoice()
     * @generated
     */
    EClass GLOBAL_CHOICE = eINSTANCE.getGlobalChoice();

    /**
     * The meta object literal for the '<em><b>Role</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GLOBAL_CHOICE__ROLE = eINSTANCE.getGlobalChoice_Role();

    /**
     * The meta object literal for the '<em><b>Blocks</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GLOBAL_CHOICE__BLOCKS = eINSTANCE.getGlobalChoice_Blocks();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalRecursionImpl <em>Global Recursion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalRecursionImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalRecursion()
     * @generated
     */
    EClass GLOBAL_RECURSION = eINSTANCE.getGlobalRecursion();

    /**
     * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GLOBAL_RECURSION__LABEL = eINSTANCE.getGlobalRecursion_Label();

    /**
     * The meta object literal for the '<em><b>Block</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GLOBAL_RECURSION__BLOCK = eINSTANCE.getGlobalRecursion_Block();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalContinueImpl <em>Global Continue</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalContinueImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalContinue()
     * @generated
     */
    EClass GLOBAL_CONTINUE = eINSTANCE.getGlobalContinue();

    /**
     * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GLOBAL_CONTINUE__LABEL = eINSTANCE.getGlobalContinue_Label();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalParallelImpl <em>Global Parallel</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalParallelImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalParallel()
     * @generated
     */
    EClass GLOBAL_PARALLEL = eINSTANCE.getGlobalParallel();

    /**
     * The meta object literal for the '<em><b>Blocks</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GLOBAL_PARALLEL__BLOCKS = eINSTANCE.getGlobalParallel_Blocks();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalInterruptibleImpl <em>Global Interruptible</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalInterruptibleImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalInterruptible()
     * @generated
     */
    EClass GLOBAL_INTERRUPTIBLE = eINSTANCE.getGlobalInterruptible();

    /**
     * The meta object literal for the '<em><b>Scope</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GLOBAL_INTERRUPTIBLE__SCOPE = eINSTANCE.getGlobalInterruptible_Scope();

    /**
     * The meta object literal for the '<em><b>Block</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GLOBAL_INTERRUPTIBLE__BLOCK = eINSTANCE.getGlobalInterruptible_Block();

    /**
     * The meta object literal for the '<em><b>Interrupts</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GLOBAL_INTERRUPTIBLE__INTERRUPTS = eINSTANCE.getGlobalInterruptible_Interrupts();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalInterruptImpl <em>Global Interrupt</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalInterruptImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalInterrupt()
     * @generated
     */
    EClass GLOBAL_INTERRUPT = eINSTANCE.getGlobalInterrupt();

    /**
     * The meta object literal for the '<em><b>Messages</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GLOBAL_INTERRUPT__MESSAGES = eINSTANCE.getGlobalInterrupt_Messages();

    /**
     * The meta object literal for the '<em><b>Role</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GLOBAL_INTERRUPT__ROLE = eINSTANCE.getGlobalInterrupt_Role();

    /**
     * The meta object literal for the '{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalDoImpl <em>Global Do</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.editor.dsl.scribbleDsl.impl.GlobalDoImpl
     * @see org.scribble.editor.dsl.scribbleDsl.impl.ScribbleDslPackageImpl#getGlobalDo()
     * @generated
     */
    EClass GLOBAL_DO = eINSTANCE.getGlobalDo();

    /**
     * The meta object literal for the '<em><b>Module</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GLOBAL_DO__MODULE = eINSTANCE.getGlobalDo_Module();

    /**
     * The meta object literal for the '<em><b>Scope</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GLOBAL_DO__SCOPE = eINSTANCE.getGlobalDo_Scope();

    /**
     * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GLOBAL_DO__ARGUMENTS = eINSTANCE.getGlobalDo_Arguments();

    /**
     * The meta object literal for the '<em><b>Roles</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GLOBAL_DO__ROLES = eINSTANCE.getGlobalDo_Roles();

  }

} //ScribbleDslPackage
