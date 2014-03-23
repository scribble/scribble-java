/**
 */
package org.scribble.editor.dsl.scribbleDsl;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Local Continue</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.LocalContinue#getLabel <em>Label</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalContinue()
 * @model
 * @generated
 */
public interface LocalContinue extends LlobalInteraction
{
  /**
   * Returns the value of the '<em><b>Label</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Label</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Label</em>' attribute.
   * @see #setLabel(String)
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getLocalContinue_Label()
   * @model
   * @generated
   */
  String getLabel();

  /**
   * Sets the value of the '{@link org.scribble.editor.dsl.scribbleDsl.LocalContinue#getLabel <em>Label</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Label</em>' attribute.
   * @see #getLabel()
   * @generated
   */
  void setLabel(String value);

} // LocalContinue
