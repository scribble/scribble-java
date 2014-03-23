/**
 */
package org.scribble.editor.dsl.scribbleDsl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Local Protocol Decl</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalProtocolDecl#getName <em>Name</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalProtocolDecl#getRole <em>Role</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalProtocolDecl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalProtocolDecl#getRoles <em>Roles</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalProtocolDecl#getBlock <em>Block</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalProtocolDecl#getInstantiates <em>Instantiates</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalProtocolDecl#getArguments <em>Arguments</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalProtocolDecl#getRoleInstantiations <em>Role Instantiations</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalProtocolDecl()
 * @model
 * @generated
 */
public interface LocalProtocolDecl extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalProtocolDecl_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.scribble.editor.dsl.scribbleDsl.LocalProtocolDecl#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Role</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Role</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Role</em>' attribute.
   * @see #setRole(String)
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalProtocolDecl_Role()
   * @model
   * @generated
   */
  String getRole();

  /**
   * Sets the value of the '{@link org.scribble.editor.dsl.scribbleDsl.LocalProtocolDecl#getRole <em>Role</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Role</em>' attribute.
   * @see #getRole()
   * @generated
   */
  void setRole(String value);

  /**
   * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
   * The list contents are of type {@link org.scribble.editor.dsl.scribbleDsl.ParameterDecl}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parameters</em>' containment reference list.
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalProtocolDecl_Parameters()
   * @model containment="true"
   * @generated
   */
  EList<ParameterDecl> getParameters();

  /**
   * Returns the value of the '<em><b>Roles</b></em>' containment reference list.
   * The list contents are of type {@link org.scribble.editor.dsl.scribbleDsl.RoleDecl}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Roles</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Roles</em>' containment reference list.
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalProtocolDecl_Roles()
   * @model containment="true"
   * @generated
   */
  EList<RoleDecl> getRoles();

  /**
   * Returns the value of the '<em><b>Block</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Block</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Block</em>' containment reference.
   * @see #setBlock(LocalProtocolBlock)
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalProtocolDecl_Block()
   * @model containment="true"
   * @generated
   */
  LocalProtocolBlock getBlock();

  /**
   * Sets the value of the '{@link org.scribble.editor.dsl.scribbleDsl.LocalProtocolDecl#getBlock <em>Block</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Block</em>' containment reference.
   * @see #getBlock()
   * @generated
   */
  void setBlock(LocalProtocolBlock value);

  /**
   * Returns the value of the '<em><b>Instantiates</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Instantiates</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Instantiates</em>' attribute.
   * @see #setInstantiates(String)
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalProtocolDecl_Instantiates()
   * @model
   * @generated
   */
  String getInstantiates();

  /**
   * Sets the value of the '{@link org.scribble.editor.dsl.scribbleDsl.LocalProtocolDecl#getInstantiates <em>Instantiates</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Instantiates</em>' attribute.
   * @see #getInstantiates()
   * @generated
   */
  void setInstantiates(String value);

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
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalProtocolDecl_Arguments()
   * @model containment="true"
   * @generated
   */
  EList<Argument> getArguments();

  /**
   * Returns the value of the '<em><b>Role Instantiations</b></em>' containment reference list.
   * The list contents are of type {@link org.scribble.editor.dsl.scribbleDsl.RoleInstantiation}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Role Instantiations</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Role Instantiations</em>' containment reference list.
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalProtocolDecl_RoleInstantiations()
   * @model containment="true"
   * @generated
   */
  EList<RoleInstantiation> getRoleInstantiations();

} // LocalProtocolDecl
