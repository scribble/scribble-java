/**
 */
package org.scribble.editor.dsl.scribbleDsl;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Local Interruptible</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalInterruptible#getScope <em>Scope</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalInterruptible#getBlock <em>Block</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalInterruptible#getThrow <em>Throw</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalInterruptible#getCatches <em>Catches</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalInterruptible()
 * @model
 * @generated
 */
public interface LocalInterruptible extends LlobalInteraction
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
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalInterruptible_Scope()
   * @model
   * @generated
   */
  String getScope();

  /**
   * Sets the value of the '{@link org.scribble.editor.dsl.scribbleDsl.LocalInterruptible#getScope <em>Scope</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Scope</em>' attribute.
   * @see #getScope()
   * @generated
   */
  void setScope(String value);

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
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalInterruptible_Block()
   * @model containment="true"
   * @generated
   */
  LocalProtocolBlock getBlock();

  /**
   * Sets the value of the '{@link org.scribble.editor.dsl.scribbleDsl.LocalInterruptible#getBlock <em>Block</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Block</em>' containment reference.
   * @see #getBlock()
   * @generated
   */
  void setBlock(LocalProtocolBlock value);

  /**
   * Returns the value of the '<em><b>Throw</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Throw</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Throw</em>' containment reference.
   * @see #setThrow(LocalThrow)
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalInterruptible_Throw()
   * @model containment="true"
   * @generated
   */
  LocalThrow getThrow();

  /**
   * Sets the value of the '{@link org.scribble.editor.dsl.scribbleDsl.LocalInterruptible#getThrow <em>Throw</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Throw</em>' containment reference.
   * @see #getThrow()
   * @generated
   */
  void setThrow(LocalThrow value);

  /**
   * Returns the value of the '<em><b>Catches</b></em>' containment reference list.
   * The list contents are of type {@link org.scribble.editor.dsl.scribbleDsl.LocalCatch}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Catches</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Catches</em>' containment reference list.
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalInterruptible_Catches()
   * @model containment="true"
   * @generated
   */
  EList<LocalCatch> getCatches();

} // LocalInterruptible
