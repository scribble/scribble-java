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

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.scribble.editor.dsl.scribbleDsl.Argument;
import org.scribble.editor.dsl.scribbleDsl.GlobalDo;
import org.scribble.editor.dsl.scribbleDsl.RoleInstantiation;
import org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Global Do</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalDoImpl#getScope <em>Scope</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalDoImpl#getMember <em>Member</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalDoImpl#getArguments <em>Arguments</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalDoImpl#getRoles <em>Roles</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GlobalDoImpl extends GlobalInteractionImpl implements GlobalDo
{
  /**
   * The default value of the '{@link #getScope() <em>Scope</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getScope()
   * @generated
   * @ordered
   */
  protected static final String SCOPE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getScope() <em>Scope</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getScope()
   * @generated
   * @ordered
   */
  protected String scope = SCOPE_EDEFAULT;

  /**
   * The default value of the '{@link #getMember() <em>Member</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMember()
   * @generated
   * @ordered
   */
  protected static final String MEMBER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMember() <em>Member</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMember()
   * @generated
   * @ordered
   */
  protected String member = MEMBER_EDEFAULT;

  /**
   * The cached value of the '{@link #getArguments() <em>Arguments</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getArguments()
   * @generated
   * @ordered
   */
  protected EList<Argument> arguments;

  /**
   * The cached value of the '{@link #getRoles() <em>Roles</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRoles()
   * @generated
   * @ordered
   */
  protected EList<RoleInstantiation> roles;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected GlobalDoImpl()
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
    return ScribbleDslPackage.Literals.GLOBAL_DO;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getScope()
  {
    return scope;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setScope(String newScope)
  {
    String oldScope = scope;
    scope = newScope;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.GLOBAL_DO__SCOPE, oldScope, scope));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getMember()
  {
    return member;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMember(String newMember)
  {
    String oldMember = member;
    member = newMember;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.GLOBAL_DO__MEMBER, oldMember, member));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Argument> getArguments()
  {
    if (arguments == null)
    {
      arguments = new EObjectContainmentEList<Argument>(Argument.class, this, ScribbleDslPackage.GLOBAL_DO__ARGUMENTS);
    }
    return arguments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<RoleInstantiation> getRoles()
  {
    if (roles == null)
    {
      roles = new EObjectContainmentEList<RoleInstantiation>(RoleInstantiation.class, this, ScribbleDslPackage.GLOBAL_DO__ROLES);
    }
    return roles;
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
      case ScribbleDslPackage.GLOBAL_DO__ARGUMENTS:
        return ((InternalEList<?>)getArguments()).basicRemove(otherEnd, msgs);
      case ScribbleDslPackage.GLOBAL_DO__ROLES:
        return ((InternalEList<?>)getRoles()).basicRemove(otherEnd, msgs);
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
      case ScribbleDslPackage.GLOBAL_DO__SCOPE:
        return getScope();
      case ScribbleDslPackage.GLOBAL_DO__MEMBER:
        return getMember();
      case ScribbleDslPackage.GLOBAL_DO__ARGUMENTS:
        return getArguments();
      case ScribbleDslPackage.GLOBAL_DO__ROLES:
        return getRoles();
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
      case ScribbleDslPackage.GLOBAL_DO__SCOPE:
        setScope((String)newValue);
        return;
      case ScribbleDslPackage.GLOBAL_DO__MEMBER:
        setMember((String)newValue);
        return;
      case ScribbleDslPackage.GLOBAL_DO__ARGUMENTS:
        getArguments().clear();
        getArguments().addAll((Collection<? extends Argument>)newValue);
        return;
      case ScribbleDslPackage.GLOBAL_DO__ROLES:
        getRoles().clear();
        getRoles().addAll((Collection<? extends RoleInstantiation>)newValue);
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
      case ScribbleDslPackage.GLOBAL_DO__SCOPE:
        setScope(SCOPE_EDEFAULT);
        return;
      case ScribbleDslPackage.GLOBAL_DO__MEMBER:
        setMember(MEMBER_EDEFAULT);
        return;
      case ScribbleDslPackage.GLOBAL_DO__ARGUMENTS:
        getArguments().clear();
        return;
      case ScribbleDslPackage.GLOBAL_DO__ROLES:
        getRoles().clear();
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
      case ScribbleDslPackage.GLOBAL_DO__SCOPE:
        return SCOPE_EDEFAULT == null ? scope != null : !SCOPE_EDEFAULT.equals(scope);
      case ScribbleDslPackage.GLOBAL_DO__MEMBER:
        return MEMBER_EDEFAULT == null ? member != null : !MEMBER_EDEFAULT.equals(member);
      case ScribbleDslPackage.GLOBAL_DO__ARGUMENTS:
        return arguments != null && !arguments.isEmpty();
      case ScribbleDslPackage.GLOBAL_DO__ROLES:
        return roles != null && !roles.isEmpty();
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
    result.append(" (scope: ");
    result.append(scope);
    result.append(", member: ");
    result.append(member);
    result.append(')');
    return result.toString();
  }

} //GlobalDoImpl
