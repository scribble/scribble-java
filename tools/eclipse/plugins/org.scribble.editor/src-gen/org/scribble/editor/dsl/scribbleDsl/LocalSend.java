/**
 */
package org.scribble.editor.dsl.scribbleDsl;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Local Send</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalSend#getMessage <em>Message</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalSend#getToRoles <em>To Roles</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalSend()
 * @model
 * @generated
 */
public interface LocalSend extends LlobalInteraction
{
  /**
   * Returns the value of the '<em><b>Message</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Message</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Message</em>' containment reference.
   * @see #setMessage(Message)
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalSend_Message()
   * @model containment="true"
   * @generated
   */
  Message getMessage();

  /**
   * Sets the value of the '{@link org.scribble.editor.dsl.scribbleDsl.LocalSend#getMessage <em>Message</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Message</em>' containment reference.
   * @see #getMessage()
   * @generated
   */
  void setMessage(Message value);

  /**
   * Returns the value of the '<em><b>To Roles</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>To Roles</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>To Roles</em>' attribute list.
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalSend_ToRoles()
   * @model unique="false"
   * @generated
   */
  EList<String> getToRoles();

} // LocalSend
