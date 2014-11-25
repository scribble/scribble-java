/**
 */
package org.scribble.trace.editor.dsl.scribbleTraceDsl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trace</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.Trace#getRoles <em>Roles</em>}</li>
 *   <li>{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.Trace#getSteps <em>Steps</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.ScribbleTraceDslPackage#getTrace()
 * @model
 * @generated
 */
public interface Trace extends EObject
{
  /**
   * Returns the value of the '<em><b>Roles</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Roles</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Roles</em>' attribute list.
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.ScribbleTraceDslPackage#getTrace_Roles()
   * @model unique="false"
   * @generated
   */
  EList<String> getRoles();

  /**
   * Returns the value of the '<em><b>Steps</b></em>' containment reference list.
   * The list contents are of type {@link org.scribble.trace.editor.dsl.scribbleTraceDsl.Stepdefn}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Steps</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Steps</em>' containment reference list.
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.ScribbleTraceDslPackage#getTrace_Steps()
   * @model containment="true"
   * @generated
   */
  EList<Stepdefn> getSteps();

} // Trace
