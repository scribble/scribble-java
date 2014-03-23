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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.scribble.editor.dsl.scribbleDsl.LocalCatch;
import org.scribble.editor.dsl.scribbleDsl.Message;
import org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Local Catch</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.LocalCatchImpl#getMessages <em>Messages</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.LocalCatchImpl#getFromRole <em>From Role</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LocalCatchImpl extends MinimalEObjectImpl.Container implements LocalCatch
{
  /**
   * The cached value of the '{@link #getMessages() <em>Messages</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMessages()
   * @generated
   * @ordered
   */
  protected EList<Message> messages;

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
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected LocalCatchImpl()
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
    return ScribbleDslPackage.Literals.LOCAL_CATCH;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Message> getMessages()
  {
    if (messages == null)
    {
      messages = new EObjectContainmentEList<Message>(Message.class, this, ScribbleDslPackage.LOCAL_CATCH__MESSAGES);
    }
    return messages;
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
      eNotify(new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.LOCAL_CATCH__FROM_ROLE, oldFromRole, fromRole));
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
      case ScribbleDslPackage.LOCAL_CATCH__MESSAGES:
        return ((InternalEList<?>)getMessages()).basicRemove(otherEnd, msgs);
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
      case ScribbleDslPackage.LOCAL_CATCH__MESSAGES:
        return getMessages();
      case ScribbleDslPackage.LOCAL_CATCH__FROM_ROLE:
        return getFromRole();
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
      case ScribbleDslPackage.LOCAL_CATCH__MESSAGES:
        getMessages().clear();
        getMessages().addAll((Collection<? extends Message>)newValue);
        return;
      case ScribbleDslPackage.LOCAL_CATCH__FROM_ROLE:
        setFromRole((String)newValue);
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
      case ScribbleDslPackage.LOCAL_CATCH__MESSAGES:
        getMessages().clear();
        return;
      case ScribbleDslPackage.LOCAL_CATCH__FROM_ROLE:
        setFromRole(FROM_ROLE_EDEFAULT);
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
      case ScribbleDslPackage.LOCAL_CATCH__MESSAGES:
        return messages != null && !messages.isEmpty();
      case ScribbleDslPackage.LOCAL_CATCH__FROM_ROLE:
        return FROM_ROLE_EDEFAULT == null ? fromRole != null : !FROM_ROLE_EDEFAULT.equals(fromRole);
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
    result.append(')');
    return result.toString();
  }

} //LocalCatchImpl
