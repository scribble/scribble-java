/**
 */
package org.scribble.editor.dsl.scribbleDsl;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Global Interruptible</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.GlobalInterruptible#getScope <em>Scope</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.GlobalInterruptible#getBlock <em>Block</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.GlobalInterruptible#getInterrupts <em>Interrupts</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getGlobalInterruptible()
 * @model
 * @generated
 */
public interface GlobalInterruptible extends GlobalInteraction
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
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getGlobalInterruptible_Scope()
   * @model
   * @generated
   */
  String getScope();

  /**
   * Sets the value of the '{@link org.scribble.editor.dsl.scribbleDsl.GlobalInterruptible#getScope <em>Scope</em>}' attribute.
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
   * @see #setBlock(GlobalProtocolBlock)
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getGlobalInterruptible_Block()
   * @model containment="true"
   * @generated
   */
  GlobalProtocolBlock getBlock();

  /**
   * Sets the value of the '{@link org.scribble.editor.dsl.scribbleDsl.GlobalInterruptible#getBlock <em>Block</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Block</em>' containment reference.
   * @see #getBlock()
   * @generated
   */
  void setBlock(GlobalProtocolBlock value);

  /**
   * Returns the value of the '<em><b>Interrupts</b></em>' containment reference list.
   * The list contents are of type {@link org.scribble.editor.dsl.scribbleDsl.GlobalInterrupt}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Interrupts</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Interrupts</em>' containment reference list.
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getGlobalInterruptible_Interrupts()
   * @model containment="true"
   * @generated
   */
  EList<GlobalInterrupt> getInterrupts();

} // GlobalInterruptible
