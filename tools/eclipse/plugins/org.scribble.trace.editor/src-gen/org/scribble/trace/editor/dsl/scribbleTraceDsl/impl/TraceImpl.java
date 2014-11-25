/**
 */
package org.scribble.trace.editor.dsl.scribbleTraceDsl.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.scribble.trace.editor.dsl.scribbleTraceDsl.ScribbleTraceDslPackage;
import org.scribble.trace.editor.dsl.scribbleTraceDsl.Stepdefn;
import org.scribble.trace.editor.dsl.scribbleTraceDsl.Trace;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Trace</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.TraceImpl#getRoles <em>Roles</em>}</li>
 *   <li>{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.impl.TraceImpl#getSteps <em>Steps</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TraceImpl extends MinimalEObjectImpl.Container implements Trace
{
  /**
   * The cached value of the '{@link #getRoles() <em>Roles</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRoles()
   * @generated
   * @ordered
   */
  protected EList<String> roles;

  /**
   * The cached value of the '{@link #getSteps() <em>Steps</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSteps()
   * @generated
   * @ordered
   */
  protected EList<Stepdefn> steps;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TraceImpl()
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
    return ScribbleTraceDslPackage.Literals.TRACE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getRoles()
  {
    if (roles == null)
    {
      roles = new EDataTypeEList<String>(String.class, this, ScribbleTraceDslPackage.TRACE__ROLES);
    }
    return roles;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Stepdefn> getSteps()
  {
    if (steps == null)
    {
      steps = new EObjectContainmentEList<Stepdefn>(Stepdefn.class, this, ScribbleTraceDslPackage.TRACE__STEPS);
    }
    return steps;
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
      case ScribbleTraceDslPackage.TRACE__STEPS:
        return ((InternalEList<?>)getSteps()).basicRemove(otherEnd, msgs);
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
      case ScribbleTraceDslPackage.TRACE__ROLES:
        return getRoles();
      case ScribbleTraceDslPackage.TRACE__STEPS:
        return getSteps();
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
      case ScribbleTraceDslPackage.TRACE__ROLES:
        getRoles().clear();
        getRoles().addAll((Collection<? extends String>)newValue);
        return;
      case ScribbleTraceDslPackage.TRACE__STEPS:
        getSteps().clear();
        getSteps().addAll((Collection<? extends Stepdefn>)newValue);
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
      case ScribbleTraceDslPackage.TRACE__ROLES:
        getRoles().clear();
        return;
      case ScribbleTraceDslPackage.TRACE__STEPS:
        getSteps().clear();
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
      case ScribbleTraceDslPackage.TRACE__ROLES:
        return roles != null && !roles.isEmpty();
      case ScribbleTraceDslPackage.TRACE__STEPS:
        return steps != null && !steps.isEmpty();
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
    result.append(" (roles: ");
    result.append(roles);
    result.append(')');
    return result.toString();
  }

} //TraceImpl
