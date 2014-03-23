/**
 */
package org.scribble.editor.dsl.scribbleDsl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Local Protocol Block</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalProtocolBlock#getActivities <em>Activities</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalProtocolBlock()
 * @model
 * @generated
 */
public interface LocalProtocolBlock extends EObject
{
  /**
   * Returns the value of the '<em><b>Activities</b></em>' containment reference list.
   * The list contents are of type {@link org.scribble.editor.dsl.scribbleDsl.LlobalInteraction}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Activities</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Activities</em>' containment reference list.
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalProtocolBlock_Activities()
   * @model containment="true"
   * @generated
   */
  EList<LlobalInteraction> getActivities();

} // LocalProtocolBlock
