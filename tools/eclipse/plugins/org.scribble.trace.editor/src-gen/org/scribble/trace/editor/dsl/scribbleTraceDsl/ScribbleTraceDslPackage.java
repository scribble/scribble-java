/**
 */
package org.scribble.trace.editor.dsl.scribbleTraceDsl;

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
 * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.ScribbleTraceDslFactory
 * @model kind="package"
 * @generated
 */
public interface ScribbleTraceDslPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "scribbleTraceDsl";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.scribble.org/trace/editor/dsl/ScribbleTraceDsl";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "scribbleTraceDsl";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  ScribbleTraceDslPackage eINSTANCE = org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.ScribbleTraceDslPackageImpl.init();

  /**
   * The meta object id for the '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.TraceImpl <em>Trace</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.TraceImpl
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.ScribbleTraceDslPackageImpl#getTrace()
   * @generated
   */
  int TRACE = 0;

  /**
   * The feature id for the '<em><b>Roles</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACE__ROLES = 0;

  /**
   * The feature id for the '<em><b>Steps</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACE__STEPS = 1;

  /**
   * The number of structural features of the '<em>Trace</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACE_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.StepdefnImpl <em>Stepdefn</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.StepdefnImpl
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.ScribbleTraceDslPackageImpl#getStepdefn()
   * @generated
   */
  int STEPDEFN = 1;

  /**
   * The number of structural features of the '<em>Stepdefn</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STEPDEFN_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.MessagetransferImpl <em>Messagetransfer</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.MessagetransferImpl
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.ScribbleTraceDslPackageImpl#getMessagetransfer()
   * @generated
   */
  int MESSAGETRANSFER = 2;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGETRANSFER__PARAMETERS = STEPDEFN_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Messagetransfer</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGETRANSFER_FEATURE_COUNT = STEPDEFN_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.ParameterImpl <em>Parameter</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.ParameterImpl
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.ScribbleTraceDslPackageImpl#getParameter()
   * @generated
   */
  int PARAMETER = 3;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER__TYPE = 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER__VALUE = 1;

  /**
   * The number of structural features of the '<em>Parameter</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER_FEATURE_COUNT = 2;


  /**
   * Returns the meta object for class '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.Trace <em>Trace</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Trace</em>'.
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.Trace
   * @generated
   */
  EClass getTrace();

  /**
   * Returns the meta object for the attribute list '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.Trace#getRoles <em>Roles</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Roles</em>'.
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.Trace#getRoles()
   * @see #getTrace()
   * @generated
   */
  EAttribute getTrace_Roles();

  /**
   * Returns the meta object for the containment reference list '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.Trace#getSteps <em>Steps</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Steps</em>'.
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.Trace#getSteps()
   * @see #getTrace()
   * @generated
   */
  EReference getTrace_Steps();

  /**
   * Returns the meta object for class '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.Stepdefn <em>Stepdefn</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Stepdefn</em>'.
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.Stepdefn
   * @generated
   */
  EClass getStepdefn();

  /**
   * Returns the meta object for class '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.Messagetransfer <em>Messagetransfer</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Messagetransfer</em>'.
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.Messagetransfer
   * @generated
   */
  EClass getMessagetransfer();

  /**
   * Returns the meta object for the containment reference list '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.Messagetransfer#getParameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameters</em>'.
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.Messagetransfer#getParameters()
   * @see #getMessagetransfer()
   * @generated
   */
  EReference getMessagetransfer_Parameters();

  /**
   * Returns the meta object for class '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.Parameter <em>Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Parameter</em>'.
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.Parameter
   * @generated
   */
  EClass getParameter();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.Parameter#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type</em>'.
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.Parameter#getType()
   * @see #getParameter()
   * @generated
   */
  EAttribute getParameter_Type();

  /**
   * Returns the meta object for the attribute '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.Parameter#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.Parameter#getValue()
   * @see #getParameter()
   * @generated
   */
  EAttribute getParameter_Value();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  ScribbleTraceDslFactory getScribbleTraceDslFactory();

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
     * The meta object literal for the '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.TraceImpl <em>Trace</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.TraceImpl
     * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.ScribbleTraceDslPackageImpl#getTrace()
     * @generated
     */
    EClass TRACE = eINSTANCE.getTrace();

    /**
     * The meta object literal for the '<em><b>Roles</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TRACE__ROLES = eINSTANCE.getTrace_Roles();

    /**
     * The meta object literal for the '<em><b>Steps</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRACE__STEPS = eINSTANCE.getTrace_Steps();

    /**
     * The meta object literal for the '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.StepdefnImpl <em>Stepdefn</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.StepdefnImpl
     * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.ScribbleTraceDslPackageImpl#getStepdefn()
     * @generated
     */
    EClass STEPDEFN = eINSTANCE.getStepdefn();

    /**
     * The meta object literal for the '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.MessagetransferImpl <em>Messagetransfer</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.MessagetransferImpl
     * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.ScribbleTraceDslPackageImpl#getMessagetransfer()
     * @generated
     */
    EClass MESSAGETRANSFER = eINSTANCE.getMessagetransfer();

    /**
     * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MESSAGETRANSFER__PARAMETERS = eINSTANCE.getMessagetransfer_Parameters();

    /**
     * The meta object literal for the '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.ParameterImpl <em>Parameter</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.ParameterImpl
     * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.ScribbleTraceDslPackageImpl#getParameter()
     * @generated
     */
    EClass PARAMETER = eINSTANCE.getParameter();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARAMETER__TYPE = eINSTANCE.getParameter_Type();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARAMETER__VALUE = eINSTANCE.getParameter_Value();

  }

} //ScribbleTraceDslPackage
