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

import org.scribble.editor.dsl.scribbleDsl.GlobalInterrupt;
import org.scribble.editor.dsl.scribbleDsl.GlobalInterruptible;
import org.scribble.editor.dsl.scribbleDsl.GlobalProtocolBlock;
import org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Global Interruptible</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalInterruptibleImpl#getScope <em>Scope</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalInterruptibleImpl#getBlock <em>Block</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.GlobalInterruptibleImpl#getInterrupts <em>Interrupts</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GlobalInterruptibleImpl extends GlobalInteractionImpl implements GlobalInterruptible
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
  protected GlobalProtocolBlock block;

  /**
   * The cached value of the '{@link #getInterrupts() <em>Interrupts</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInterrupts()
   * @generated
   * @ordered
   */
  protected EList<GlobalInterrupt> interrupts;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected GlobalInterruptibleImpl()
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
    return ScribbleDslPackage.Literals.GLOBAL_INTERRUPTIBLE;
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
      eNotify(new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__SCOPE, oldScope, scope));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GlobalProtocolBlock getBlock()
  {
    return block;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetBlock(GlobalProtocolBlock newBlock, NotificationChain msgs)
  {
    GlobalProtocolBlock oldBlock = block;
    block = newBlock;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__BLOCK, oldBlock, newBlock);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setBlock(GlobalProtocolBlock newBlock)
  {
    if (newBlock != block)
    {
      NotificationChain msgs = null;
      if (block != null)
        msgs = ((InternalEObject)block).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__BLOCK, null, msgs);
      if (newBlock != null)
        msgs = ((InternalEObject)newBlock).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__BLOCK, null, msgs);
      msgs = basicSetBlock(newBlock, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__BLOCK, newBlock, newBlock));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<GlobalInterrupt> getInterrupts()
  {
    if (interrupts == null)
    {
      interrupts = new EObjectContainmentEList<GlobalInterrupt>(GlobalInterrupt.class, this, ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__INTERRUPTS);
    }
    return interrupts;
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
      case ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__BLOCK:
        return basicSetBlock(null, msgs);
      case ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__INTERRUPTS:
        return ((InternalEList<?>)getInterrupts()).basicRemove(otherEnd, msgs);
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
      case ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__SCOPE:
        return getScope();
      case ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__BLOCK:
        return getBlock();
      case ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__INTERRUPTS:
        return getInterrupts();
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
      case ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__SCOPE:
        setScope((String)newValue);
        return;
      case ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__BLOCK:
        setBlock((GlobalProtocolBlock)newValue);
        return;
      case ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__INTERRUPTS:
        getInterrupts().clear();
        getInterrupts().addAll((Collection<? extends GlobalInterrupt>)newValue);
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
      case ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__SCOPE:
        setScope(SCOPE_EDEFAULT);
        return;
      case ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__BLOCK:
        setBlock((GlobalProtocolBlock)null);
        return;
      case ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__INTERRUPTS:
        getInterrupts().clear();
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
      case ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__SCOPE:
        return SCOPE_EDEFAULT == null ? scope != null : !SCOPE_EDEFAULT.equals(scope);
      case ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__BLOCK:
        return block != null;
      case ScribbleDslPackage.GLOBAL_INTERRUPTIBLE__INTERRUPTS:
        return interrupts != null && !interrupts.isEmpty();
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

} //GlobalInterruptibleImpl
