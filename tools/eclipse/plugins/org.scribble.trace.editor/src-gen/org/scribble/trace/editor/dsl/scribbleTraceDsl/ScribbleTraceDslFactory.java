/**
 */
package org.scribble.trace.editor.dsl.scribbleTraceDsl;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.ScribbleTraceDslPackage
 * @generated
 */
public interface ScribbleTraceDslFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  ScribbleTraceDslFactory eINSTANCE = org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.ScribbleTraceDslFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Trace</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Trace</em>'.
   * @generated
   */
  Trace createTrace();

  /**
   * Returns a new object of class '<em>Stepdefn</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Stepdefn</em>'.
   * @generated
   */
  Stepdefn createStepdefn();

  /**
   * Returns a new object of class '<em>Messagetransfer</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Messagetransfer</em>'.
   * @generated
   */
  Messagetransfer createMessagetransfer();

  /**
   * Returns a new object of class '<em>Parameter</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Parameter</em>'.
   * @generated
   */
  Parameter createParameter();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  ScribbleTraceDslPackage getScribbleTraceDslPackage();

} //ScribbleTraceDslFactory
