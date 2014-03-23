/**
 */
package org.scribble.editor.dsl.scribbleDsl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Module</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.Module#getName <em>Name</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.Module#getImports <em>Imports</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.Module#getTypes <em>Types</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.Module#getGlobals <em>Globals</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.Module#getLocals <em>Locals</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getModule()
 * @model
 * @generated
 */
public interface Module extends EObject
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
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getModule_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.scribble.editor.dsl.scribbleDsl.Module#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Imports</b></em>' containment reference list.
   * The list contents are of type {@link org.scribble.editor.dsl.scribbleDsl.ImportDecl}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Imports</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Imports</em>' containment reference list.
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getModule_Imports()
   * @model containment="true"
   * @generated
   */
  EList<ImportDecl> getImports();

  /**
   * Returns the value of the '<em><b>Types</b></em>' containment reference list.
   * The list contents are of type {@link org.scribble.editor.dsl.scribbleDsl.PayloadTypeDecl}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Types</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Types</em>' containment reference list.
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getModule_Types()
   * @model containment="true"
   * @generated
   */
  EList<PayloadTypeDecl> getTypes();

  /**
   * Returns the value of the '<em><b>Globals</b></em>' containment reference list.
   * The list contents are of type {@link org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Globals</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Globals</em>' containment reference list.
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getModule_Globals()
   * @model containment="true"
   * @generated
   */
  EList<GlobalProtocolDecl> getGlobals();

  /**
   * Returns the value of the '<em><b>Locals</b></em>' containment reference list.
   * The list contents are of type {@link org.scribble.editor.dsl.scribbleDsl.LocalProtocolDecl}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Locals</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Locals</em>' containment reference list.
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getModule_Locals()
   * @model containment="true"
   * @generated
   */
  EList<LocalProtocolDecl> getLocals();

} // Module
