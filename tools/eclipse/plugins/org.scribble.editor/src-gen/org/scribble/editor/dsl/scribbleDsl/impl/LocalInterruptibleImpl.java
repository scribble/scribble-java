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

import org.scribble.editor.dsl.scribbleDsl.LocalCatch;
import org.scribble.editor.dsl.scribbleDsl.LocalInterruptible;
import org.scribble.editor.dsl.scribbleDsl.LocalProtocolBlock;
import org.scribble.editor.dsl.scribbleDsl.LocalThrow;
import org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Local Interruptible</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.LocalInterruptibleImpl#getScope <em>Scope</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.LocalInterruptibleImpl#getBlock <em>Block</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.LocalInterruptibleImpl#getThrow <em>Throw</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.LocalInterruptibleImpl#getCatches <em>Catches</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LocalInterruptibleImpl extends LlobalInteractionImpl implements LocalInterruptible
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
   * The cached value of the '{@link #getBlock() <em>Block</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBlock()
   * @generated
   * @ordered
   */
  protected LocalProtocolBlock block;

  /**
   * The cached value of the '{@link #getThrow() <em>Throw</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getThrow()
   * @generated
   * @ordered
   */
  protected LocalThrow throw_;

  /**
   * The cached value of the '{@link #getCatches() <em>Catches</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCatches()
   * @generated
   * @ordered
   */
  protected EList<LocalCatch> catches;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected LocalInterruptibleImpl()
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
    return ScribbleDslPackage.Literals.LOCAL_INTERRUPTIBLE;
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
      eNotify(new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.LOCAL_INTERRUPTIBLE__SCOPE, oldScope, scope));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalProtocolBlock getBlock()
  {
    return block;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetBlock(LocalProtocolBlock newBlock, NotificationChain msgs)
  {
    LocalProtocolBlock oldBlock = block;
    block = newBlock;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.LOCAL_INTERRUPTIBLE__BLOCK, oldBlock, newBlock);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setBlock(LocalProtocolBlock newBlock)
  {
    if (newBlock != block)
    {
      NotificationChain msgs = null;
      if (block != null)
        msgs = ((InternalEObject)block).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ScribbleDslPackage.LOCAL_INTERRUPTIBLE__BLOCK, null, msgs);
      if (newBlock != null)
        msgs = ((InternalEObject)newBlock).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ScribbleDslPackage.LOCAL_INTERRUPTIBLE__BLOCK, null, msgs);
      msgs = basicSetBlock(newBlock, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.LOCAL_INTERRUPTIBLE__BLOCK, newBlock, newBlock));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalThrow getThrow()
  {
    return throw_;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetThrow(LocalThrow newThrow, NotificationChain msgs)
  {
    LocalThrow oldThrow = throw_;
    throw_ = newThrow;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.LOCAL_INTERRUPTIBLE__THROW, oldThrow, newThrow);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setThrow(LocalThrow newThrow)
  {
    if (newThrow != throw_)
    {
      NotificationChain msgs = null;
      if (throw_ != null)
        msgs = ((InternalEObject)throw_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ScribbleDslPackage.LOCAL_INTERRUPTIBLE__THROW, null, msgs);
      if (newThrow != null)
        msgs = ((InternalEObject)newThrow).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ScribbleDslPackage.LOCAL_INTERRUPTIBLE__THROW, null, msgs);
      msgs = basicSetThrow(newThrow, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.LOCAL_INTERRUPTIBLE__THROW, newThrow, newThrow));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<LocalCatch> getCatches()
  {
    if (catches == null)
    {
      catches = new EObjectContainmentEList<LocalCatch>(LocalCatch.class, this, ScribbleDslPackage.LOCAL_INTERRUPTIBLE__CATCHES);
    }
    return catches;
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
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE__BLOCK:
        return basicSetBlock(null, msgs);
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE__THROW:
        return basicSetThrow(null, msgs);
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE__CATCHES:
        return ((InternalEList<?>)getCatches()).basicRemove(otherEnd, msgs);
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
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE__SCOPE:
        return getScope();
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE__BLOCK:
        return getBlock();
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE__THROW:
        return getThrow();
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE__CATCHES:
        return getCatches();
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
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE__SCOPE:
        setScope((String)newValue);
        return;
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE__BLOCK:
        setBlock((LocalProtocolBlock)newValue);
        return;
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE__THROW:
        setThrow((LocalThrow)newValue);
        return;
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE__CATCHES:
        getCatches().clear();
        getCatches().addAll((Collection<? extends LocalCatch>)newValue);
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
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE__SCOPE:
        setScope(SCOPE_EDEFAULT);
        return;
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE__BLOCK:
        setBlock((LocalProtocolBlock)null);
        return;
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE__THROW:
        setThrow((LocalThrow)null);
        return;
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE__CATCHES:
        getCatches().clear();
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
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE__SCOPE:
        return SCOPE_EDEFAULT == null ? scope != null : !SCOPE_EDEFAULT.equals(scope);
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE__BLOCK:
        return block != null;
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE__THROW:
        return throw_ != null;
      case ScribbleDslPackage.LOCAL_INTERRUPTIBLE__CATCHES:
        return catches != null && !catches.isEmpty();
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
    result.append(')');
    return result.toString();
  }

} //LocalInterruptibleImpl
