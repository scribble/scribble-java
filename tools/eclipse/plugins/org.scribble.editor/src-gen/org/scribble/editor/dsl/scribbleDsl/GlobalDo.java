/**
 */
package org.scribble.editor.dsl.scribbleDsl;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Global Do</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.GlobalDo#getScope <em>Scope</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.GlobalDo#getMember <em>Member</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.GlobalDo#getArguments <em>Arguments</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.GlobalDo#getRoles <em>Roles</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getGlobalDo()
 * @model
 * @generated
 */
public interface GlobalDo extends GlobalInteraction
{
  /**
   * Returns the value of the '<em><b>Scope</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Scope</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Scope</em>' attribute.
   * @see #setScope(String)
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getGlobalDo_Scope()
   * @model
   * @generated
   */
  String getScope();

  /**
   * Sets the value of the '{@link org.scribble.editor.dsl.scribbleDsl.GlobalDo#getScope <em>Scope</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Scope</em>' attribute.
   * @see #getScope()
   * @generated
   */
  void setScope(String value);

  /**
   * Returns the value of the '<em><b>Member</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Member</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Member</em>' attribute.
   * @see #setMember(String)
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getGlobalDo_Member()
   * @model
   * @generated
   */
  String getMember();

  /**
   * Sets the value of the '{@link org.scribble.editor.dsl.scribbleDsl.GlobalDo#getMember <em>Member</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Member</em>' attribute.
   * @see #getMember()
   * @generated
   */
  void setMember(String value);

  /**
   * Returns the value of the '<em><b>Arguments</b></em>' containment reference list.
   * The list contents are of type {@link org.scribble.editor.dsl.scribbleDsl.Argument}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Arguments</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Arguments</em>' containment reference list.
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getGlobalDo_Arguments()
   * @model containment="true"
   * @generated
   */
  EList<Argument> getArguments();

  /**
   * Returns the value of the '<em><b>Roles</b></em>' containment reference list.
   * The list contents are of type {@link org.scribble.editor.dsl.scribbleDsl.RoleInstantiation}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Roles</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Roles</em>' containment reference list.
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getGlobalDo_Roles()
   * @model containment="true"
   * @generated
   */
  EList<RoleInstantiation> getRoles();

} // GlobalDo
