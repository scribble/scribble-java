/**
 */
package org.scribble.editor.dsl.scribbleDsl;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Import Member</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.ImportMember#getMember <em>Member</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getImportMember()
 * @model
 * @generated
 */
public interface ImportMember extends ImportDecl
{
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
   * @see org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage#getImportMember_Member()
   * @model
   * @generated
   */
  String getMember();

  /**
   * Sets the value of the '{@link org.scribble.editor.dsl.scribbleDsl.ImportMember#getMember <em>Member</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Member</em>' attribute.
   * @see #getMember()
   * @generated
   */
  void setMember(String value);

} // ImportMember
