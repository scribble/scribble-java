/**
 */
package org.scribble.editor.dsl.scribbleDsl;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Local Receive</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalReceive#getMessage <em>Message</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalReceive#getFromRole <em>From Role</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalReceive()
 * @model
 * @generated
 */
public interface LocalReceive extends LlobalInteraction
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
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalReceive_Message()
   * @model containment="true"
   * @generated
   */
  Message getMessage();

  /**
   * Sets the value of the '{@link org.scribble.editor.dsl.scribbleDsl.LocalReceive#getMessage <em>Message</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Message</em>' containment reference.
   * @see #getMessage()
   * @generated
   */
  void setMessage(Message value);

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
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalReceive_FromRole()
   * @model
   * @generated
   */
  String getFromRole();

  /**
   * Sets the value of the '{@link org.scribble.editor.dsl.scribbleDsl.LocalReceive#getFromRole <em>From Role</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>From Role</em>' attribute.
   * @see #getFromRole()
   * @generated
   */
  void setFromRole(String value);

} // LocalReceive
