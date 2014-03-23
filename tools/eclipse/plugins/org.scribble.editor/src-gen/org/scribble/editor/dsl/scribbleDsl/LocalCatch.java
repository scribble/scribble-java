/**
 */
package org.scribble.editor.dsl.scribbleDsl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Local Catch</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalCatch#getMessages <em>Messages</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalCatch#getFromRole <em>From Role</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalCatch()
 * @model
 * @generated
 */
public interface LocalCatch extends EObject
{
  /**
   * Returns the value of the '<em><b>Messages</b></em>' containment reference list.
   * The list contents are of type {@link org.scribble.editor.dsl.scribbleDsl.Message}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Messages</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Messages</em>' containment reference list.
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalCatch_Messages()
   * @model containment="true"
   * @generated
   */
  EList<Message> getMessages();

  /**
   * Returns the value of the '<em><b>From Role</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>From Role</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>From Role</em>' attribute.
   * @see #setFromRole(String)
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalCatch_FromRole()
   * @model
   * @generated
   */
  String getFromRole();

  /**
   * Sets the value of the '{@link org.scribble.editor.dsl.scribbleDsl.LocalCatch#getFromRole <em>From Role</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>From Role</em>' attribute.
   * @see #getFromRole()
   * @generated
   */
  void setFromRole(String value);

} // LocalCatch
