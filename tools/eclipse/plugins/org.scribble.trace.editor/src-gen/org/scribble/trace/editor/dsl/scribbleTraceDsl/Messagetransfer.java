/**
 */
package org.scribble.trace.editor.dsl.scribbleTraceDsl;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Messagetransfer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.Messagetransfer#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.ScribbleTraceDslPackage#getMessagetransfer()
 * @model
 * @generated
 */
public interface Messagetransfer extends Stepdefn
{
  /**
   * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
   * The list contents are of type {@link org.scribble.trace.editor.dsl.scribbleTraceDsl.Parameter}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parameters</em>' containment reference list.
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.ScribbleTraceDslPackage#getMessagetransfer_Parameters()
   * @model containment="true"
   * @generated
   */
  EList<Parameter> getParameters();

} // Messagetransfer
