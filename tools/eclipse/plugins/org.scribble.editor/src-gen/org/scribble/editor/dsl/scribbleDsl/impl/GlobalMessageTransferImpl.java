/**
 */
package org.scribble.editor.dsl.scribbleDsl.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

import org.scribble.editor.dsl.scribbleDsl.GlobalMessageTransfer;
import org.scribble.editor.dsl.scribbleDsl.Message;
import org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Global Message Transfer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalMessageTransferImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalMessageTransferImpl#getFromRole <em>From Role</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalMessageTransferImpl#getToRoles <em>To Roles</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GlobalMessageTransferImpl extends GlobalInteractionImpl implements GlobalMessageTransfer
{
  /**
   * The cached value of the '{@link #getMessage() <em>Message</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMessage()
   * @generated
   * @ordered
   */
  protected Message message;

  /**
   * The default value of the '{@link #getFromRole() <em>From Role</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFromRole()
   * @generated
   * @ordered
   */
  protected static final String FROM_ROLE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFromRole() <em>From Role</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFromRole()
   * @generated
   * @ordered
   */
  protected String fromRole = FROM_ROLE_EDEFAULT;

  /**
   * The cached value of the '{@link #getToRoles() <em>To Roles</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToRoles()
   * @generated
   * @ordered
   */
  protected EList<String> toRoles;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected GlobalMessageTransferImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return ScribbleDslPackage.Literals.GLOBAL_MESSAGE_TRANSFER;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Message getMessage()
  {
    return message;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetMessage(Message newMessage, NotificationChain msgs)
  {
    Message oldMessage = message;
    message = newMessage;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER__MESSAGE, oldMessage, newMessage);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMessage(Message newMessage)
  {
    if (newMessage != message)
    {
      NotificationChain msgs = null;
      if (message != null)
        msgs = ((InternalEObject)message).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER__MESSAGE, null, msgs);
      if (newMessage != null)
        msgs = ((InternalEObject)newMessage).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER__MESSAGE, null, msgs);
      msgs = basicSetMessage(newMessage, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER__MESSAGE, newMessage, newMessage));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getFromRole()
  {
    return fromRole;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFromRole(String newFromRole)
  {
    String oldFromRole = fromRole;
    fromRole = newFromRole;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER__FROM_ROLE, oldFromRole, fromRole));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getToRoles()
  {
    if (toRoles == null)
    {
      toRoles = new EDataTypeEList<String>(String.class, this, ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER__TO_ROLES);
    }
    return toRoles;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER__MESSAGE:
        return basicSetMessage(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER__MESSAGE:
        return getMessage();
      case ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER__FROM_ROLE:
        return getFromRole();
      case ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER__TO_ROLES:
        return getToRoles();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER__MESSAGE:
        setMessage((Message)newValue);
        return;
      case ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER__FROM_ROLE:
        setFromRole((String)newValue);
        return;
      case ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER__TO_ROLES:
        getToRoles().clear();
        getToRoles().addAll((Collection<? extends String>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER__MESSAGE:
        setMessage((Message)null);
        return;
      case ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER__FROM_ROLE:
        setFromRole(FROM_ROLE_EDEFAULT);
        return;
      case ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER__TO_ROLES:
        getToRoles().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER__MESSAGE:
        return message != null;
      case ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER__FROM_ROLE:
        return FROM_ROLE_EDEFAULT == null ? fromRole != null : !FROM_ROLE_EDEFAULT.equals(fromRole);
      case ScribbleDslPackage.GLOBAL_MESSAGE_TRANSFER__TO_ROLES:
        return toRoles != null && !toRoles.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (fromRole: ");
    result.append(fromRole);
    result.append(", toRoles: ");
    result.append(toRoles);
    result.append(')');
    return result.toString();
  }

} //GlobalMessageTransferImpl
